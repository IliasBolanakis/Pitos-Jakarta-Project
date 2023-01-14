package com.ilbolan.pitoswebproject.models;

import com.ilbolan.pitoswebproject.forms.RegisterForm;
import com.ilbolan.pitoswebproject.models.DBManagement.DBConnection;
import com.ilbolan.pitoswebproject.models.beans.User;
import com.ilbolan.pitoswebproject.security.Crypto;
import com.ilbolan.pitoswebproject.utils.AppLogger;
import jakarta.servlet.http.HttpSession;

import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Data-Access-Object for {@link User} bean
 */
public class UserDAO {

    private static final AppLogger logger = AppLogger.getLogger(AppLogger.class);

    private UserDAO(){} // No point in creating instances since class is used as a DAO

    /**
     * Creates a user from a given result set
     *
     * @param resultSet  The {@link ResultSet} of a query containing a {@link User} object
     *
     * @return The {@link User} object in resultSet
     *
     * @throws SQLException If drawn data don't match the User object params
     */
    private static User prepareUserObject(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setAddress(resultSet.getString("address"));
        user.setPassword(resultSet.getString("password"));
        user.setFullName(resultSet.getString("fullName"));
        user.setEmail(resultSet.getString("email"));
        user.setTel(resultSet.getString("tel"));
        user.setStatus(resultSet.getString("status"));
        user.setCode(resultSet.getString("code"));

        return user;
    }

    /**
     * Draws a {@link User} Object from Database given his username
     *
     * @param username The username to be queried
     *
     * @return The {@link User} that matches username parameter
     */
    public static User getUserByUsername(String username) {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "SELECT * FROM user WHERE username=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next())
                user = prepareUserObject(resultSet);

            logger.log(Level.INFO, "Successfully retrieved user by username from the database");
            resultSet.close();
            statement.close();
            return user;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO RETRIEVE USER BY USERNAME FROM THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Draws a user from Database given his email
     *
     * @param email The email to be queried
     *
     * @return The {@link User} that matches email parameter
     */
    public static User getUserByEmail(String email) {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "SELECT * FROM user WHERE email=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next())
                user = prepareUserObject(resultSet);

            logger.log(Level.INFO, "Successfully retrieved user by email from the database");
            resultSet.close();
            statement.close();
            return user;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO RETRIEVE USER BY EMAIL FROM THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Stores a user from a registration form into
     * the Database with UNVERIFIED status
     *
     * @param registerForm The {@link RegisterForm} to draw the data from
     *
     * @return The newly stored user's ID;
     */
    public static int storeUserUnverified(RegisterForm registerForm) {
        try(Connection connection = DBConnection.getInstance()) {

            // Store user
            SecureRandom sr = new SecureRandom();
            String code = String.valueOf(sr.nextInt(100000));

            // encode password (hash + salt)
            String salt = Crypto.salt();
            String encryptedPassword = Crypto.hash(registerForm.getPassword(), salt);

            String query = "INSERT INTO pitosdb.user (username, password, fullname, address, email, tel, status, code, salt) " +
                    "       VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, registerForm.getUsername());
            statement.setString(2, encryptedPassword);
            statement.setString(3, registerForm.getFullName());
            statement.setString(4, registerForm.getAddress());
            statement.setString(5, registerForm.getEmail());
            statement.setString(6, registerForm.getTel());
            statement.setString(7, "unverified");
            statement.setString(8, code);
            statement.setString(9, salt);
            statement.executeUpdate();

            ResultSet genKeys = statement.getGeneratedKeys();
            genKeys.next();
            int userId = (int) genKeys.getLong(1);
            statement.close();
            genKeys.close();

            // Store user role
            query = "INSERT INTO pitosdb.user_role (user_id, role_id) " +
                    "       VALUES(?, 2)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.executeUpdate();

            logger.log(Level.INFO, "Successfully stored unverified user in the database");
            statement.close();
            genKeys.close();

            return userId;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO STORE UNVERIFIED USER IN THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Draws a user from the Database given his ID
     *
     * @param id The ID to be queried
     *
     * @return The {@link User} that matches ID parameter
     */
    public static User getUserById(int id) {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "SELECT * FROM user WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            User user = prepareUserObject(resultSet);

            logger.log(Level.INFO, "Successfully retrieved user by ID from the database");
            resultSet.close();
            statement.close();
            return user;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO RETRIEVE USER BY EMAIL FROM THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets user's status to verified in Database
     *
     * @param code The unique verification code sent to the user via email
     *
     * @return {@link Boolean#TRUE} if user successfully verified
     */
    public static boolean verifyUser(String code) {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "SELECT * FROM user WHERE code=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();

            boolean result = false;
            if (resultSet.next()) {
                User user = prepareUserObject(resultSet);
                if (user.getStatus().equals("unverified")) {
                    query = "UPDATE pitosdb.user " +
                            "  SET status='verified' " +
                            "  WHERE id=? " ;
                    statement = connection.prepareStatement(query);
                    statement.setInt(1, user.getId());
                    statement.executeUpdate();

                    statement.close();
                    result = true;
                }
            }

            logger.log(Level.INFO, "Successfully verified user in the database");
            resultSet.close();
            statement.close();
            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO VERIFY USER IN THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Draws from the database the bellow information:
     * 1) Number of unverified Users
     * 2) Verified users with non-null verification code
     *
     * @return A {@link List} with the above statistics
     */
    public static List<Integer> getAdminStats() {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "SELECT COUNT(*) AS cnt " +
                    "FROM user " +
                    "WHERE status='unverified'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int cntUnverified = resultSet.getInt("cnt");
            resultSet.close();
            statement.close();

            query = "SELECT COUNT(*) AS cnt " +
                    "FROM user " +
                    "WHERE status='verified' and code IS NOT NULL";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            resultSet.next();
            int cntVerifiedCodes = resultSet.getInt("cnt");
            resultSet.close();
            statement.close();


            List<Integer> adminStats = new ArrayList<>();
            adminStats.add(cntUnverified);
            adminStats.add(cntVerifiedCodes);

            logger.log(Level.INFO, "Successfully retrieved admin stats from the database");
            return adminStats;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO RETRIEVE ADMIN STATS FROM THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes from database all users with unverified status
     */
    public static void deleteUnverifiedUsers() {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "DELETE FROM user WHERE status='unverified' ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();

            statement.close();
            logger.log(Level.INFO, "Successfully deleted unverified from the database");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO DELETE UNVERIFIED USERS FROM THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets all verified users codes to null
     */
    public static void updateVerifiedUsersCode() {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "UPDATE user SET code=NULL WHERE status='verified' and code IS NOT NULL ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();

            logger.log(Level.INFO, "Successfully nullified verified users' codes in the database");
            statement.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO NULLIFY VERIFIED USERS' CODES IN THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a user for attempted login (drawn from database)
     *
     * @param username The username used to login
     * @param password The password used to login
     *
     * @return The queried {@link User} object whose username & password match the parameters
     */
    public static User login(String username, String password) {
        try(Connection connection = DBConnection.getInstance()) {

            // encrypt password (hash + salt)
            String salt = getSalt(username); // get salt of user

            if(salt == null) // if salt == null -> return null user
                return null;

            String encryptedPassword = Crypto.hash(password, salt);

            String query = "SELECT * FROM user WHERE username=? AND password=? AND status='verified'";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, encryptedPassword);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next())
                user = prepareUserObject(resultSet);

            logger.log(Level.INFO, "Successful login from the database");
            resultSet.close();
            statement.close();
            return user;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO LOGIN FROM THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Gives the salt of a given user stored in the Database
     *
     * @param username The username to be queried
     *
     * @return The salt code the user
     */
    public static String getSalt(String username) {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "SELECT salt FROM user WHERE username=? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            String salt = null;
            if (resultSet.next())
                salt = resultSet.getString("salt");

            logger.log(Level.INFO, "Successfully retrieved user's salt from the database");
            resultSet.close();
            statement.close();
            return salt;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE RETRIEVE USER'S SALT FROM THE DATABASE");
            throw new RuntimeException(e);
        }

    }

    /**
     * Changes the User's password in the database
     *
     * @param password The new password to be set
     *
     * @param code The unique verification code sent to the user
     */
    public static void updateUserNewPassword(String password, int code) {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "UPDATE user SET password=?, code=NULL, status='verified' WHERE code=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, password);
            statement.setInt(2,code);
            statement.executeUpdate();

            logger.log(Level.INFO, "Successfully updated user's password in the database");
            statement.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE UPDATE USER'S PASSWORD IN THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets a unique verification code for given user for password reset
     *
     * @param email The email of the user
     * @param code The verification code to be inserted in the database
     */
    public static void updateUserResetPassword(String email, int code) {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "UPDATE user SET code=?, status='reset' WHERE email=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, code);
            statement.setString(2, email);
            statement.executeUpdate();

            logger.log(Level.INFO, "Successfully set user's status to reset in the database");
            statement.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE SET USER'S STATUS TO RESET IN THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Stores a user's session to database
     *
     * @param username The username of the user whose session is stored
     * @param session The {@link HttpSession#getId()} code to be stored
     */
    public static void storeSession(String username, String session){
        try(Connection connection = DBConnection.getInstance()) {

            String query = "UPDATE user SET session=? WHERE username=?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, session);
            statement.setString(2, username);
            statement.executeUpdate();

            logger.log(Level.INFO, "Successfully stored user's session in the database");
            statement.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE STORE USER'S SESSION IN THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a user for a given session
     *
     * @param session The user's {@link HttpSession#getId()} code
     *
     * @return The corresponding {@link User} object for given session from Database
     */
    public static User getUserBySession(String session) {

        try(Connection connection = DBConnection.getInstance()) {

            String query = "SELECT * FROM user WHERE session = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, session);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if(resultSet.next())
                user = prepareUserObject(resultSet);

            logger.log(Level.INFO, "Successfully retrieved user by session from the database");
            statement.close();
            return user;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE RETRIEVE USER BY SESSION FROM THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the user role for given session object
     *
     * @param session The {@link HttpSession#getId()} to draw role from
     *
     * @return The user's role (user/admin)
     */
    public static String getRole(String session) {
        try (Connection connection = DBConnection.getInstance()) {

            String query = "SELECT role.description AS role " +
                    "FROM user " +
                    "JOIN user_role ON user.id  = user_role.user_id " +
                    "JOIN role ON user_role.role_id = role.role_id " +
                    "WHERE user.session = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, session);
            ResultSet resultSet = statement.executeQuery();

            String role = null;
            if (resultSet.next())
                role = resultSet.getString("role");

            logger.log(Level.INFO, "Successfully retrieved user's role by session from the database");
            resultSet.close();
            statement.close();
            return role;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE RETRIEVE USER'S ROLE BY SESSION FROM THE DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a user's session from Database when logging-out
     *
     * @param session The {@link HttpSession#getId()} code to be nullified
     *
     * @return True if user successfully logged-out
     */
    public static boolean logout(String session) {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "SELECT username FROM user WHERE session=? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, session);
            ResultSet resultSet = statement.executeQuery();

            String username = null;
            if (resultSet.next())
                username = resultSet.getString("username");

            resultSet.close();
            statement.close();

            if (username!=null) {
                query = "UPDATE user SET session=NULL WHERE username=?";

                statement = connection.prepareStatement(query);
                statement.setString(1, username);
                statement.executeUpdate();

                statement.close();
            }
            logger.log(Level.INFO, "Successfully attempted to logout user from the database");
            return username!=null;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO ATTEMPT USER LOGOUT FROM THE DATABASE");
            throw new RuntimeException(e);
        }
    }
}
