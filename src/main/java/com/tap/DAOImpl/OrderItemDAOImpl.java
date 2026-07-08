package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.OrderItemDAO;
import com.tap.model.OrderItem;
import com.tap.utility.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    private static final String INSERT_ORDER_ITEM =
            "INSERT INTO order_items (order_id, item_id, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?)";

    private static final String GET_ORDER_ITEM_BY_ID =
            "SELECT * FROM order_items WHERE order_item_id = ?";

    private static final String GET_ORDER_ITEMS_BY_ORDER_ID =
            "SELECT * FROM order_items WHERE order_id = ?";

    private static final String GET_ALL_ORDER_ITEMS =
            "SELECT * FROM order_items";

    private static final String UPDATE_ORDER_ITEM =
            "UPDATE order_items SET order_id = ?, item_id = ?, quantity = ?, price = ?, subtotal = ? WHERE order_item_id = ?";

    private static final String DELETE_ORDER_ITEM =
            "DELETE FROM order_items WHERE order_item_id = ?";

    @Override
    public int addOrderItem(OrderItem orderItem) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_ORDER_ITEM)) {

            pstmt.setInt(1, orderItem.getOrderId());
            pstmt.setInt(2, orderItem.getItemId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setBigDecimal(4, orderItem.getPrice());
            pstmt.setBigDecimal(5, orderItem.getSubtotal());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public OrderItem getOrderItemById(int orderItemId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ORDER_ITEM_BY_ID)) {

            pstmt.setInt(1, orderItemId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractOrderItem(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
	public List<OrderItem> getOrderItemsByOrderId(int orderId) {
	
	    List<OrderItem> orderItemList = new ArrayList<>();
	
	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement pstmt = connection.prepareStatement(GET_ORDER_ITEMS_BY_ORDER_ID)) {
	
	        pstmt.setInt(1, orderId);
	
	        ResultSet rs = pstmt.executeQuery();
	
	        while (rs.next()) {
	
	            orderItemList.add(extractOrderItem(rs));
	
	        }
	
	    } catch (SQLException e) {
	
	        e.printStackTrace();
	
	    }
	
	    return orderItemList;
	}

    @Override
    public List<OrderItem> getAllOrderItems() {

        List<OrderItem> orderItemList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ALL_ORDER_ITEMS)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                orderItemList.add(extractOrderItem(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItemList;
    }

    @Override
    public int updateOrderItem(OrderItem orderItem) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_ORDER_ITEM)) {

            pstmt.setInt(1, orderItem.getOrderId());
            pstmt.setInt(2, orderItem.getItemId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setBigDecimal(4, orderItem.getPrice());
            pstmt.setBigDecimal(5, orderItem.getSubtotal());
            pstmt.setInt(6, orderItem.getOrderItemId());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteOrderItem(int orderItemId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_ORDER_ITEM)) {

            pstmt.setInt(1, orderItemId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private OrderItem extractOrderItem(ResultSet rs) throws SQLException {

        return new OrderItem(

                rs.getInt("order_item_id"),
                rs.getInt("order_id"),
                rs.getInt("item_id"),
                rs.getInt("quantity"),
                rs.getBigDecimal("price"),
                rs.getBigDecimal("subtotal"),
                rs.getTimestamp("created_at")

        );
    }

}