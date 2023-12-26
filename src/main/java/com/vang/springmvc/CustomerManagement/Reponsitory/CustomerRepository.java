package com.vang.springmvc.CustomerManagement.Reponsitory;


import com.vang.springmvc.CustomerManagement.Model.Customer;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository {
    private String url = "jdbc:mysql://localhost:3306/QLCUSMER";
    private String user = "root";
    private String password = "anhnam2005";

    private static final String SELECT_USER_ALL = "SELECT * FROM user;";
    private static final String SELECT_USER_BY_ID_ALL = "SELECT * FROM user where id=?;";
    private static final String UPDATE = "UPDATE user SET name = ?, email = ?, address = ? WHERE id = ?;";

    public Connection connection() throws ClassNotFoundException {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    public List<Customer> showAccUser() throws SQLException, ClassNotFoundException {
        List<Customer> list = new ArrayList<>();
        PreparedStatement statement = connection().prepareStatement(SELECT_USER_ALL);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String address = rs.getString("address");
            list.add(new Customer(id, name, email, address));
        }
        return list;
    }

    public Customer getUserByID(int id) throws ClassNotFoundException, SQLException {
        PreparedStatement statement = connection().prepareStatement(SELECT_USER_BY_ID_ALL);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int userId = resultSet.getInt("id");
            String username = resultSet.getString("name");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");

            Customer user = new Customer(userId, username, email, address);
            return user;
        }
        return null;
    }

    public void updateCustomer(Customer customer) throws ClassNotFoundException, SQLException {
        PreparedStatement statement = connection().prepareStatement(UPDATE);
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getEmail());
        statement.setString(3, customer.getAddress());
        statement.setInt(4, customer.getId());
        statement.executeLargeUpdate();
    }
}
