package com.licious.dao;

import com.licious.database.DBConnectionFactory;
import com.licious.exceptions.DaoException;
import com.licious.exceptions.DeliveryCenterNotFoundException;
import com.licious.model.DeliveryCenter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryCenterDao {

    Connection connection = null;
    Statement statement = null;

    public DeliveryCenterDao() {
    try {
            connection = new DBConnectionFactory().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public void editDeliveryCenter(DeliveryCenter deliveryCenter) {
        try {
            String query = "UPDATE deliverycenter SET address= " + deliveryCenter.getAddress()
                    + " , location= " + deliveryCenter.getLocation() +" WHERE deliveryCenterId= " + deliveryCenter.getId();

            statement = connection.createStatement();


            statement.executeUpdate(query);
            System.out.println("DeliveryCenter with Id " + deliveryCenter.getId() + " Updated Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewDeliveryCenter(DeliveryCenter dc) throws DaoException{

        String insertQuery = "insert into deliverycenter values(?, ?, ?)";

        try {
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, dc.getId());
            insertStatement.setString(2, dc.getAddress());
            insertStatement.setInt(3, dc.getLocation());


            int count = insertStatement.executeUpdate();
            System.out.println(count + " no. of row(s) updated");

            insertStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e, "DeliveryCenter not added. Error: " + e.getMessage());
        }
    }

    public DeliveryCenter getDeliveryCenterDetails(int id) throws DeliveryCenterNotFoundException, DaoException {
        DeliveryCenter deliveryCenter = new DeliveryCenter();

        int rowCount = 0;

        String selectQuery = "select * from deliverycenter where deliveryCenterId = " + id;
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {

                rowCount++;
                deliveryCenter.setId(resultSet.getInt(1));
                deliveryCenter.setAddress(resultSet.getString(2));
                deliveryCenter.setLocation(resultSet.getInt(3));
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e, e.getMessage());
        }

        if (rowCount == 0) {
            throw new DeliveryCenterNotFoundException("Delivery Center with id:" + id + " not found.");
        }

        return deliveryCenter;
    }


    public List<DeliveryCenter> getDeliveryCenterWithinDistance(int location, int distance) throws DeliveryCenterNotFoundException, DaoException {
        List<DeliveryCenter> deliveryCenterList = new ArrayList<>();
        DeliveryCenter deliveryCenter = null;

        int rowCount = 0;

        String selectQuery = "select * from deliverycenter where location between " + (location - distance) + " and " + (location + distance) ;
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                deliveryCenter = new DeliveryCenter();
                rowCount++;
                deliveryCenter.setId(resultSet.getInt(1));
                deliveryCenter.setAddress(resultSet.getString(2));
                deliveryCenter.setLocation(resultSet.getInt(3));
                deliveryCenterList.add(deliveryCenter);
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e, e.getMessage());
        }

        if (rowCount == 0) {
            throw new DeliveryCenterNotFoundException("Delivery Center within distance:" + distance + " not found.");
        }

        return deliveryCenterList;
    }


    public List<DeliveryCenter> getAllDeliveryCenters() throws DeliveryCenterNotFoundException, DaoException {
        ArrayList<DeliveryCenter> deliveryCenterList = new ArrayList<DeliveryCenter>();
        DeliveryCenter deliveryCenter = null;

        int rowCount = 0;

        String selectQuery = "select * from deliverycenter";
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                deliveryCenter = new DeliveryCenter();
                rowCount++;
                deliveryCenter.setId(resultSet.getInt(1));
                deliveryCenter.setAddress(resultSet.getString(2));
                deliveryCenter.setLocation(resultSet.getInt(3));
                deliveryCenterList.add(deliveryCenter);
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException(e, e.getMessage());
        }

        if (rowCount == 0) {
            throw new DeliveryCenterNotFoundException("No delivery Center found");
        }

        return deliveryCenterList;
    }
}
