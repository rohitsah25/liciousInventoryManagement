package com.licious.dao;

import com.licious.database.DBConnectionFactory;
import com.licious.exceptions.DaoException;
import com.licious.exceptions.ProductNotFoundException;
import com.licious.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    Connection connection = null;
    Statement statement = null;

    public ProductDao() {
    try {
            connection = new DBConnectionFactory().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product getProductDetails(int id) throws ProductNotFoundException, DaoException {
        Product product = new Product();

        int rowCount = 0;

        String selectQuery = "select * from product where productId = " + id;
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                rowCount++;
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setPrice(resultSet.getFloat(3));
            }
            statement.close();


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e, e.getMessage());
        }

        if (rowCount == 0) {
            throw new ProductNotFoundException("Product with id:" + id + " not found.");
        }

        return product;
    }

    public List<Product> getAllProducts() throws DaoException, ProductNotFoundException{

        ArrayList<Product> productsList = new ArrayList<Product>();
        Product product = null;
        int rowCount = 0;
        String selectAllQuery = "select * from product";


        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectAllQuery);

            while (rs.next()) {
                rowCount++;
                product = new Product();
                product.setId(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getFloat(3));

                productsList.add(product);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e, e.getMessage());
        }

        if (rowCount == 0) {
            throw new ProductNotFoundException("No product found");
        }


        return productsList;
    }

    public void addNewProduct(Product product) throws DaoException{

        String insertQuery = "insert into product values(?, ?, ?)";

        try {
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, product.getId());
            insertStatement.setString(2, product.getName());
            insertStatement.setDouble(3, product.getPrice());


            int count = insertStatement.executeUpdate();
            System.out.println(count + " no. of row(s) updated");

            insertStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e, "Product not added. Error: " + e.getMessage());
        }
    }

    public void editProduct(Product product) {
        try {
            String query = "UPDATE product SET name= " + product.getName() + " , price= "
                    + product.getPrice() + " WHERE productId= " + product.getId();
            statement = connection.createStatement();


            statement.executeUpdate(query);
            System.out.println("Product with Id " + product.getId() + " Updated Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
