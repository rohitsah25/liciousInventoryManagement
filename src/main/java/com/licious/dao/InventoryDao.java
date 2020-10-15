package com.licious.dao;

import com.licious.database.DBConnectionFactory;
import com.licious.exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.licious.exceptions.InventoryNotFoundException;
import com.licious.model.Inventory;

public class InventoryDao {

    Connection connection = null;
    Statement statement = null;

    public InventoryDao() {
        try {
            connection = new DBConnectionFactory().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Inventory getInventoryDetailsByInventoryId(int id) throws InventoryNotFoundException, DaoException {
        String selectQuery = "select * from inventory where inventoryId = " + id;
        return getInventoryDetails(selectQuery);
    }

    public Inventory getInventoryDetailsByProductDCId(int productId, int deliveryCenterId) throws InventoryNotFoundException, DaoException {
        String selectQuery = "select * from inventory where productId = " + productId + " and deliveryCenterId = " + deliveryCenterId;
        return getInventoryDetails(selectQuery);
    }

    private Inventory getInventoryDetails(String selectQuery) throws InventoryNotFoundException, DaoException {
        Inventory inventory = new Inventory();
        int rowCount = 0;


        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                rowCount++;
                inventory.setId(resultSet.getInt(1));
                inventory.setProductId(resultSet.getInt(2));
                inventory.setDeliveryCenterId(resultSet.getInt(3));
                inventory.setQuantity(resultSet.getInt(4));
            }
            statement.close();


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e, e.getMessage());
        }

        if (rowCount == 0) {
            throw new InventoryNotFoundException("Inventory not found.");
        }
        return inventory;
    }



    public void updateInventory(int id, int quantity) {

        try {
            String query = "UPDATE inventory SET quantity= " + quantity +  " WHERE inventoryId = " + id;
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Inventory entry with Id " + id + " Updated Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Inventory> getInventoryListByProduct(int productId) throws DaoException, InventoryNotFoundException{

        String selectAllQuery = "select * from inventory where productId = " + productId;
        return getAllInventoryListCommon(selectAllQuery);
    }

    public List<Inventory> getInventoryListByDeliveryCenter(int deliveryCenterId) throws DaoException, InventoryNotFoundException {

        String selectAllQuery = "select * from inventory where deliveryCenterId = " + deliveryCenterId;
        return getAllInventoryListCommon(selectAllQuery);
    }


    public List<Inventory> getAllInventoryList() throws DaoException, InventoryNotFoundException{

        String selectAllQuery = "select * from inventory";
        return getAllInventoryListCommon(selectAllQuery);
    }


    private List<Inventory> getAllInventoryListCommon(String selectAllQuery) throws DaoException, InventoryNotFoundException {

        ArrayList<Inventory> inventoryList = new ArrayList<Inventory>();
        Inventory inventory = null;
        int rowCount=0;

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectAllQuery);

            while (rs.next()) {
                rowCount++;
                inventory = new Inventory();
                inventory.setId(rs.getInt(1));
                inventory.setProductId(rs.getInt(2));
                inventory.setDeliveryCenterId(rs.getInt(3));
                inventory.setQuantity(rs.getInt(4));

                inventoryList.add(inventory);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e,e.getMessage());
        }

        if (rowCount == 0) {
            throw new InventoryNotFoundException("No inventory item found");
        }
        return inventoryList;
    }


    public void addNewItem(Inventory inventory) throws DaoException {

        String insertQuery = "insert into inventory values(?, ?, ?, ?)";

        try {
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, inventory.getId());
            insertStatement.setInt(2, inventory.getProductId());
            insertStatement.setInt(3, inventory.getDeliveryCenterId());
            insertStatement.setInt(4, inventory.getQuantity());


            int count = insertStatement.executeUpdate();
            System.out.println(count + " no. of row(s) updated");

            insertStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e, "Item not added. Error: " + e.getMessage());
        }
    }


}