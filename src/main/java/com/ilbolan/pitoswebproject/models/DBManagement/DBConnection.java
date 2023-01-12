package com.ilbolan.pitoswebproject.models.DBManagement;

import com.ilbolan.pitoswebproject.utils.AppLogger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Class that encapsulates database connection
 * and ensures its unique existence though the
 * implementation of Singleton pattern
 * NOTE: singleton pattern is redundant since only one connection
 * exists in the JNDI
 */
public class DBConnection implements Serializable {

    private static final AppLogger logger = AppLogger.getLogger(DBConnection.class);

    private static DataSource ds;

    private DBConnection(){} // Private constructor since we are implementing Singleton Pattern

    /**
     * Singleton implementation for DB connection
     *
     * @return Connection object to database
     *
     * @throws SQLException if connection fails
     */
    public static Connection getInstance() throws SQLException {
        try {
            if(ds == null)
                ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/pitosdb");
        } catch (NamingException e){
            logger.log(Level.SEVERE, "UNABLE TO CONNECT TO DATABASE");
            e.printStackTrace();
        } finally {
            return ds.getConnection();
        }
    }
}
