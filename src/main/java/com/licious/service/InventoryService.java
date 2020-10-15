package com.licious.service;

import com.licious.dao.DeliveryCenterDao;
import com.licious.dao.InventoryDao;
import com.licious.dao.ProductDao;
import com.licious.exceptions.*;
import com.licious.model.DeliveryCenter;
import com.licious.model.Inventory;
import com.licious.model.Product;

import java.util.List;

public class InventoryService {

    private InventoryDao inventoryDao = new InventoryDao();
    private ProductDao productDao = new ProductDao();
    private DeliveryCenterDao deliveryCenterDao = new DeliveryCenterDao();


    public void addProductToInventory(int productId, int deliveryCenterId, int quantity) {
        Inventory inventory = getInventoryDetails(productId, deliveryCenterId);
        if (inventory != null) {
            inventoryDao.updateInventory(inventory.getId(), inventory.getQuantity() + quantity);
        }
    }

    public void deductProductFromInventory(int productId, int deliveryCenterId, int quantity) throws QuantityExceedsException {
        Inventory inventory = getInventoryDetails(productId, deliveryCenterId);
        if (inventory != null) {

            if (inventory.getQuantity() < quantity) {
                throw new QuantityExceedsException("Requested quantity not available for productId: " + productId +
                        " deliveryCenterId: " + deliveryCenterId);
            }
            inventoryDao.updateInventory(inventory.getId(), inventory.getQuantity() - quantity);
        }
    }

    public Inventory getInventoryDetails(int productId, int deliveryCenterId) {
        Inventory inventory = null;
        try {
            inventory = inventoryDao.getInventoryDetailsByProductDCId(productId, deliveryCenterId);
        } catch (InventoryNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (DaoException ex) {
            System.out.println(ex.getMessage());
        }
        return inventory;
    }

    public Product getProductDetails(int id) {
        Product product = null;
        try {
            product = productDao.getProductDetails(id);

        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (DaoException ex) {
            System.out.println(ex.getMessage());
        }
        return product;
    }

    public DeliveryCenter getDeliveryCenterDetails(int id) {
        DeliveryCenter deliveryCenter = null;
        try {
            deliveryCenter = deliveryCenterDao.getDeliveryCenterDetails(id);
        } catch (DeliveryCenterNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (DaoException ex) {
            System.out.println(ex.getMessage());
        }
        return deliveryCenter;
    }

    public List<DeliveryCenter> getDeliveryCenterDetailsWithinDistance(int location, int distance) {
        List<DeliveryCenter> deliveryCenterList = null;
        try {
            deliveryCenterList = deliveryCenterDao.getDeliveryCenterWithinDistance(location, distance);
        } catch (DeliveryCenterNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (DaoException ex) {
            System.out.println(ex.getMessage());
        }
        return deliveryCenterList;
    }

    public void addProduct(Product product) {
        try {
            productDao.addNewProduct(product);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addItem(Inventory inventory) {
        try {
            inventoryDao.addNewItem(inventory);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addDeliveryCenter(DeliveryCenter deliveryCenter) {
        try {
            deliveryCenterDao.addNewDeliveryCenter(deliveryCenter);
        } catch (DaoException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Product> getProductList() {
        List<Product> productList = null;
        try {
            productList = productDao.getAllProducts();
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (DaoException ex) {
            System.out.println(ex.getMessage());
        }
        return productList;
    }

    public List<DeliveryCenter> getDeliveryCenterList() {
        List<DeliveryCenter> dcList = null;
        try {
            dcList = deliveryCenterDao.getAllDeliveryCenters();
        } catch (DeliveryCenterNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (DaoException ex) {
            System.out.println(ex.getMessage());
        }
        return dcList;
    }


    public List<Inventory> getInventoryList() {
        List<Inventory> inventoryList = null;
        try {
            inventoryList = inventoryDao.getAllInventoryList();
        } catch (InventoryNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (DaoException ex) {
            System.out.println(ex.getMessage());
        }
        return inventoryList;
    }
}


