package com.ilbolan.pitoswebproject.models;

import com.ilbolan.pitoswebproject.utils.AppLogger;
import com.ilbolan.pitoswebproject.models.DBManagement.DBConnection;
import com.ilbolan.pitoswebproject.models.beans.Area;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Data-Access-Object for Area bean
 */
public class AreaDAO {

    private static final AppLogger logger = AppLogger.getLogger(AreaDAO.class);

    private AreaDAO(){} // No point in creating instances since class is used as a DAO

    /**
     * Creates Area bean from database
     * @param resultSet the result set from database query
     * @return Area bean from given query
     */
    private static Area prepareAreaBean(ResultSet resultSet) throws SQLException {
        Area area = new Area();
        area.setId(resultSet.getInt("id"));
        area.setDescription(resultSet.getString("description"));
        return area;
    }

    /**
     * Get Area bean based on its id
     * @param id of area to search on database
     * @return Area bean with given id
     */
    public static Area getAreaById(int id) {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "SELECT * FROM area WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Area area = prepareAreaBean(resultSet);

            resultSet.close();
            statement.close();
            return area;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO READ AREA FROM DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all the areas in the database
     * @return a list of all the areas
     */
    public static List<Area> getAreas() {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "SELECT * FROM area";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<Area> areas = new ArrayList<>();
            while (resultSet.next()) {
                Area area = prepareAreaBean(resultSet);
                areas.add(area);
            }

            resultSet.close();
            statement.close();
            return areas;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO READ AREAS FROM DATABASE");
            throw new RuntimeException(e);
        }
    }
}
