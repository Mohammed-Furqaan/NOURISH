package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.OrderDAO;
import com.tap.model.Order;
import com.tap.utility.DBConnection;

public class OrderDAOImpl implements OrderDAO {

    private static final String INSERT_ORDER =
            "INSERT INTO orders(user_id, restaurant_id, address_id, coupon_id, total_amount, delivery_fee, tax_amount, discount_amount, final_amount, payment_method, payment_status, order_status, estimated_delivery_time, delivered_at) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String GET_ORDER_BY_ID =
            "SELECT * FROM orders WHERE order_id=?";

    private static final String GET_ORDERS_BY_USER_ID =
            "SELECT * FROM orders WHERE user_id=?";

    private static final String GET_ORDERS_BY_RESTAURANT_ID =
            "SELECT * FROM orders WHERE restaurant_id=?";

    private static final String GET_ALL_ORDERS =
            "SELECT * FROM orders";

    private static final String UPDATE_ORDER =
            "UPDATE orders SET user_id=?, restaurant_id=?, address_id=?, coupon_id=?, total_amount=?, delivery_fee=?, tax_amount=?, discount_amount=?, final_amount=?, payment_method=?, payment_status=?, order_status=?, estimated_delivery_time=?, delivered_at=? WHERE order_id=?";

    private static final String DELETE_ORDER =
            "DELETE FROM orders WHERE order_id=?";
    
    private static final String ORDER_COUNT =
    		"SELECT COUNT(*) FROM orders";

     private static final String TOTAL_REVENUE =
    		"SELECT IFNULL(SUM(final_amount),0) FROM orders WHERE payment_status='SUCCESS'";

    @Override
	public int addOrder(Order order) {
	
	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement pstmt = connection.prepareStatement(
	                 INSERT_ORDER,
	                 PreparedStatement.RETURN_GENERATED_KEYS)) {
	
	        pstmt.setInt(1, order.getUserId());
	        pstmt.setInt(2, order.getRestaurantId());
	        pstmt.setInt(3, order.getAddressId());
	
	        if (order.getCouponId() != null) {
	            pstmt.setInt(4, order.getCouponId());
	        } else {
	            pstmt.setNull(4, Types.INTEGER);
	        }
	
	        pstmt.setBigDecimal(5, order.getTotalAmount());
	        pstmt.setBigDecimal(6, order.getDeliveryFee());
	        pstmt.setBigDecimal(7, order.getTaxAmount());
	        pstmt.setBigDecimal(8, order.getDiscountAmount());
	        pstmt.setBigDecimal(9, order.getFinalAmount());
	        pstmt.setString(10, order.getPaymentMethod());
	        pstmt.setString(11, order.getPaymentStatus());
	        pstmt.setString(12, order.getOrderStatus());
	        pstmt.setTimestamp(13, order.getEstimatedDeliveryTime());
	        pstmt.setTimestamp(14, order.getDeliveredAt());
	
	        int rows = pstmt.executeUpdate();
	
	        if (rows > 0) {
	
	            ResultSet rs = pstmt.getGeneratedKeys();
	
	            if (rs.next()) {
	
	                return rs.getInt(1);
	
	            }
	        }
	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	
	    return 0;
	}
    
    @Override
    public int addOrderAndReturnId(Order order) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt =
                     connection.prepareStatement(
                             INSERT_ORDER,
                             PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getRestaurantId());
            pstmt.setInt(3, order.getAddressId());

            if (order.getCouponId() != null) {
                pstmt.setInt(4, order.getCouponId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }

            pstmt.setBigDecimal(5, order.getTotalAmount());
            pstmt.setBigDecimal(6, order.getDeliveryFee());
            pstmt.setBigDecimal(7, order.getTaxAmount());
            pstmt.setBigDecimal(8, order.getDiscountAmount());
            pstmt.setBigDecimal(9, order.getFinalAmount());
            pstmt.setString(10, order.getPaymentMethod());
            pstmt.setString(11, order.getPaymentStatus());
            pstmt.setString(12, order.getOrderStatus());
            pstmt.setTimestamp(13, order.getEstimatedDeliveryTime());
            pstmt.setTimestamp(14, order.getDeliveredAt());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Order getOrderById(int orderId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ORDER_BY_ID)) {

            pstmt.setInt(1, orderId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractOrder(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {

        List<Order> orderList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ORDERS_BY_USER_ID)) {

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                orderList.add(extractOrder(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    @Override
    public List<Order> getOrdersByRestaurantId(int restaurantId) {

        List<Order> orderList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ORDERS_BY_RESTAURANT_ID)) {

            pstmt.setInt(1, restaurantId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                orderList.add(extractOrder(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    @Override
    public List<Order> getAllOrders() {

        List<Order> orderList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ALL_ORDERS)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                orderList.add(extractOrder(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    @Override
    public int updateOrder(Order order) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_ORDER)) {

            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getRestaurantId());
            pstmt.setInt(3, order.getAddressId());

            if (order.getCouponId() != null) {
                pstmt.setInt(4, order.getCouponId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }

            pstmt.setBigDecimal(5, order.getTotalAmount());
            pstmt.setBigDecimal(6, order.getDeliveryFee());
            pstmt.setBigDecimal(7, order.getTaxAmount());
            pstmt.setBigDecimal(8, order.getDiscountAmount());
            pstmt.setBigDecimal(9, order.getFinalAmount());
            pstmt.setString(10, order.getPaymentMethod());
            pstmt.setString(11, order.getPaymentStatus());
            pstmt.setString(12, order.getOrderStatus());
            pstmt.setTimestamp(13, order.getEstimatedDeliveryTime());
            pstmt.setTimestamp(14, order.getDeliveredAt());
            pstmt.setInt(15, order.getOrderId());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteOrder(int orderId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_ORDER)) {

            pstmt.setInt(1, orderId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Order extractOrder(ResultSet rs) throws SQLException {

        return new Order(

                rs.getInt("order_id"),
                rs.getInt("user_id"),
                rs.getInt("restaurant_id"),
                rs.getInt("address_id"),
                (Integer) rs.getObject("coupon_id"),
                rs.getTimestamp("order_date"),
                rs.getBigDecimal("total_amount"),
                rs.getBigDecimal("delivery_fee"),
                rs.getBigDecimal("tax_amount"),
                rs.getBigDecimal("discount_amount"),
                rs.getBigDecimal("final_amount"),
                rs.getString("payment_method"),
                rs.getString("payment_status"),
                rs.getString("order_status"),
                rs.getTimestamp("estimated_delivery_time"),
                rs.getTimestamp("delivered_at"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")

        );
    }
    
    @Override
    public int getOrderCount() {

        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(ORDER_COUNT)){

            ResultSet rs=ps.executeQuery();

            if(rs.next()){

                return rs.getInt(1);

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public double getTotalRevenue() {

        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(TOTAL_REVENUE)){

            ResultSet rs=ps.executeQuery();

            if(rs.next()){

                return rs.getDouble(1);

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return 0;
    }
}