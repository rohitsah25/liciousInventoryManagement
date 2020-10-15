package com.licious.service;

import com.licious.exceptions.QuantityExceedsException;
import com.licious.model.DeliveryCenter;
import com.licious.model.Inventory;
import com.licious.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;


public class InventoryServiceTest {
    static InventoryService inventoryService;

    @BeforeAll
    public static void initClass() {
        inventoryService = new InventoryService();
    }

    @Test
    @DisplayName("Create product test")
    public void createProductTest() {
        Product product = new Product("oysters", 450.00);
        assertNotNull(product);
        assertEquals("oysters", product.getName());
    }

    @Test
    @DisplayName("Create delivery center test")
    public void createDeliveryCenterTest() {
        DeliveryCenter dc = new DeliveryCenter("abc street", 45);
        assertNotNull(dc);
        assertEquals(45, dc.getLocation());
    }

    @Test
    @DisplayName("Create inventory test")
    public void createInventoryTest() {
        Product product1 = new Product("clams", 230.23);
        DeliveryCenter dc1 = new DeliveryCenter("xy street", 120);
        Inventory inventory = new Inventory(product1.getId(), dc1.getId(), 120);
        assertNotNull(inventory);
        assertEquals(120, inventory.getQuantity());
        assertEquals(product1.getId(), inventory.getProductId());
        assertEquals(dc1.getId(), inventory.getDeliveryCenterId());
    }

    @Test
    @DisplayName("Update and fetch inventory test")
    public void UpdateAndFetchInventoryTest() {
        Product product1 = new Product("clams" + ThreadLocalRandom.current().nextInt(1, 1000), 230.23);
        DeliveryCenter dc1 = new DeliveryCenter(ThreadLocalRandom.current().nextInt(1, 1000) + ", xy street", 120);

        inventoryService.addProduct(product1);
        inventoryService.addDeliveryCenter(dc1);

        int initialQuantity = 120;

        Inventory inventory = new Inventory(product1.getId(), dc1.getId(), initialQuantity);

        inventoryService.addItem(inventory);

        assertNotNull(inventory);
        assertEquals(initialQuantity, inventory.getQuantity());
        assertEquals(product1.getId(), inventory.getProductId());
        assertEquals(dc1.getId(), inventory.getDeliveryCenterId());

        int quantityAdded = 30;
        inventoryService.addProductToInventory(product1.getId(), dc1.getId(), quantityAdded);
        int quantityReduced = 50;
        try {
            inventoryService.deductProductFromInventory(product1.getId(), dc1.getId(), quantityReduced);
        } catch (QuantityExceedsException e) {
            System.out.println("requested deduction more than what is available, " + e.getMessage());
        }

        Inventory inventory1 = inventoryService.getInventoryDetails(product1.getId(), dc1.getId());
        assertNotNull(inventory);
        assertEquals(inventory1.getQuantity(), initialQuantity + quantityAdded - quantityReduced);
    }

    @Test
    @DisplayName("get lists test")
    public void getListsTest() {

        int productSize = inventoryService.getProductList().size();
        int deliveryCenterSize = inventoryService.getDeliveryCenterList().size();
        int inventorySize = inventoryService.getInventoryList().size();

        Product product1 = new Product("lobsters" + ThreadLocalRandom.current().nextInt(1, 1000), 270.23);
        DeliveryCenter dc1 = new DeliveryCenter(ThreadLocalRandom.current().nextInt(1, 1000) + ", yemen street", 110);

        inventoryService.addProduct(product1);
        inventoryService.addDeliveryCenter(dc1);

        Inventory inventory = new Inventory(product1.getId(), dc1.getId(), 20);
        inventoryService.addItem(inventory);

        Product productNew = inventoryService.getProductDetails(product1.getId());
        DeliveryCenter dcNew = inventoryService.getDeliveryCenterDetails(dc1.getId());
        assertNotNull(productNew);
        assertNotNull(dcNew);

        assertEquals(productSize + 1, inventoryService.getProductList().size());
        assertEquals(deliveryCenterSize + 1, inventoryService.getDeliveryCenterList().size());
        assertEquals(inventorySize + 1, inventoryService.getInventoryList().size());

        assertNotEquals(0, inventoryService.getDeliveryCenterDetailsWithinDistance(100, 50));

    }
}
