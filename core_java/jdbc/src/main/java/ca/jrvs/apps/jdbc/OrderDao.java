package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao extends DataAccessObject<Order> {

    private static final String GET_BY_ID = "SELECT c.first_name, c.last_name, c.email, "
            + "o.order_id, o.creation_date, o.total_due, o.status, "
            + "s.first_name, s.last_name, s.email, "
            + "ol.quantity, "
            + "p.code, p.name, p.size, p.variety, p.price "
            + "FROM orders o "
            + "JOIN customer c ON o.customer_id = c.customer_id "
            + "JOIN salesperson s ON o.salesperson_id = s.salesperson_id "
            + "JOIN order_item ol on ol.order_id = o.order_id "
            + "JOIN product p on ol.product_id = p.product_id "
            + "WHERE o.order_id = ?";

    public OrderDao(Connection connection) {
        super(connection);
    }

    @Override
    public Order findById(long id) {
        Order order = new Order();
        try (PreparedStatement statement = this.connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            long orderId = 0;
            List<OrderLine> orderLines = new ArrayList<>();

            while (resultSet.next()) {
                if (orderId == 0) {
                    order.setCustomerFirstName(resultSet.getString(1));
                    order.setCustomerLastName(resultSet.getString(2));
                    order.setCustomerEmail(resultSet.getString(3));
                    order.setOrderId(resultSet.getLong(4));
                    orderId = order.getId();
                    order.setOrderCreationDate(new Date(resultSet.getDate(5).getTime()));
                    order.setOrderTotalDue(resultSet.getDouble(6));
                    order.setOrderStatus(resultSet.getString(7));
                    order.setSalesFirstName(resultSet.getString(8));
                    order.setSalesLastName(resultSet.getString(9));
                    order.setSalesEmail(resultSet.getString(10));
                }

                OrderLine orderLine = new OrderLine();
                orderLine.setQuantity(resultSet.getInt(11));
                orderLine.setProductCode(resultSet.getString(12));
                orderLine.setProductName(resultSet.getString(13));
                orderLine.setProductSize(resultSet.getInt(14));
                orderLine.setProductVariety(resultSet.getString(15));
                orderLine.setProductPrice(resultSet.getDouble(16));
                orderLines.add(orderLine);
            }

            order.setOrderLines(orderLines);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order update(Order dto) {
        return null;
    }

    @Override
    public Order create(Order dto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}