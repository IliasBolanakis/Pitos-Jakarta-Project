package com.ilbolan.pitoswebproject.models;

import com.ilbolan.pitoswebproject.forms.OrderForm;
import com.ilbolan.pitoswebproject.models.DBManagement.DBConnection;
import com.ilbolan.pitoswebproject.models.beans.Order;
import com.ilbolan.pitoswebproject.models.beans.User;
import com.ilbolan.pitoswebproject.utils.AppLogger;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Data-Access-Object for {@link Order} bean
 */
public class OrderDAO {

    private static final AppLogger logger = AppLogger.getLogger(AreaDAO.class);
    /**
     * Nested class used for retrieving recent orders
     */
    public static class RecentOrderList {
        private String stamp;
        private Map<String, Integer> orderItems = new HashMap<>();

        public RecentOrderList() {}

        public RecentOrderList(String stamp, Map<String, Integer> orderItems) {
            this.stamp = stamp;
            this.orderItems = orderItems;
        }

        public String getStamp() {
            return stamp;
        }

        public void setStamp(String stamp) {
            this.stamp = stamp;
        }

        public Map<String, Integer> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(Map<String, Integer> orderItems) {
            this.orderItems = orderItems;
        }

        @Override
        public String toString() {
            return "RecentOrderHistoryItem{" +
                    "stamp=" + stamp +
                    ", orderItems=" + orderItems +
                    '}';
        }
    }

    /**
     * Stores the order in the database
     *
     * @param formOrder is the {@link OrderForm} to draw data from
     */
    public static void storeOrder(OrderForm formOrder, User user) {
        try(Connection connection = DBConnection.getInstance()) {

            String query = "INSERT INTO pitosdb.order (fullname, address, area_id, email, tel, comments, offer, payment, stamp, user_id) " +
                    "       VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, formOrder.getFullName());
            statement.setString(2, formOrder.getAddress());
            statement.setInt(3, formOrder.getAreaId());
            statement.setString(4, formOrder.getEmail());
            statement.setString(5, formOrder.getTel());
            statement.setString(6, formOrder.getComments());
            statement.setBoolean(7, formOrder.getOffer());
            statement.setString(8, formOrder.getPayment());
            statement.setTimestamp(9, Timestamp.valueOf(formOrder.getTimestamp()));
            statement.setInt(10, user.getId());
            statement.executeUpdate();

            ResultSet genKeys = statement.getGeneratedKeys();
            genKeys.next();
            int orderId = (int) genKeys.getLong(1);
            statement.close();
            genKeys.close();

            logger.log(Level.FINE, "Order successfully inserted in database");
            for (var item : formOrder.getOrderItems()) {
                item.setOrderId(orderId);
                OrderItemDAO.storeOrderItem(item);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO STORE ORDER IN DATABASE");
            throw new RuntimeException(e);
        }
    }

    /**
     * Nested class that stores the pie contents
     */
    private static class OrderItemDAO {

        /**
         * Stores the order contents in database
         *
         * @param orderItem is the {@link Order} object to draw data from
         */
        public static void storeOrderItem(Order orderItem) {
            try(Connection connection = DBConnection.getInstance()) {

                String query = "INSERT INTO  order_item(order_id, pie_id, quantity) VALUES(?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, orderItem.getOrderId());
                statement.setInt(2, orderItem.getPieId());
                statement.setInt(3, orderItem.getQuantity());
                statement.executeUpdate();

                statement.close();
                logger.log(Level.FINE, "Successfully inserted order item in database");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "UNABLE TO STORE ORDER ITEM IN DATABASE");
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Retrieves recent User orders from the Database
     *
     * @param user The {@link User} object to retrieve orders for
     * @param numOfOrders The number of orders to be retrieved
     *
     * @return A {@link List} of numberOfOrders {@link RecentOrderList} objects
     */
    public static List<RecentOrderList> getRecentUserOrders(User user, int numOfOrders) {

        try(Connection connection = DBConnection.getInstance()) {

            String query =
                    "SELECT o.stamp as stamp, p.name as pie, oi.quantity as quantity\n" +
                    "FROM order_item oi \n" +
                    "\tJOIN pie p ON p.id = oi.pie_id\n" +
                    "    JOIN pitosdb.order o ON o.id = oi.order_id\n" +
                    "    JOIN pitosdb.user u ON u.id = o.user_id\n" +
                    "WHERE u.id = ?\n" +
                    "ORDER BY stamp DESC, p.id\n" +
                    "LIMIT ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            statement.setInt(2, 4*numOfOrders);
            ResultSet resultSet = statement.executeQuery();

            List<RecentOrderList> orders = new ArrayList<>();
            LocalDateTime previous = null;
            while (resultSet.next()) {
                if (Integer.parseInt(resultSet.getString("quantity"))>0) {
                    LocalDateTime stamp = resultSet.getTimestamp("stamp").toLocalDateTime();
                    if (!stamp.equals(previous)) {
                        orders.add(new RecentOrderList(
                                stamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy(HH:mm:ss)")),
                                new HashMap<>()
                        ));
                        orders.get(orders.size() - 1).getOrderItems().put(resultSet.getString("pie"), resultSet.getInt("quantity"));
                        previous = stamp;
                    } else {
                        orders.get(orders.size() - 1).getOrderItems().put(resultSet.getString("pie"), resultSet.getInt("quantity"));
                    }
                }
            }
            logger.log(Level.INFO, "Successfully read recent orders from the Database");
            resultSet.close();
            statement.close();
            return orders;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "UNABLE TO RETRIEVE RECENT ORDERS FROM THE DATABASE");
            throw new RuntimeException(e);
        }
    }
}