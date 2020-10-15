package com.licious;


import com.licious.exceptions.DaoException;
import com.licious.exceptions.DeliveryCenterNotFoundException;
import com.licious.exceptions.ProductNotFoundException;
import com.licious.exceptions.QuantityExceedsException;
import com.licious.model.DeliveryCenter;
import com.licious.model.Inventory;
import com.licious.model.Product;
import com.licious.service.InventoryService;
import lombok.Getter;

public class InventoryManagementMain {

    static InventoryService inventoryService;

    public static void main(String[] args) {
        System.out.println("Started");
        inventoryService = new InventoryService();

        Product product = inventoryService.getProductDetails(1);
        System.out.println("ProductId: " + product.getId() + " productName: " + product.getName() + " price: " + product.getPrice());

        DeliveryCenter dc = inventoryService.getDeliveryCenterDetails(100);
        System.out.println("deliveryCenterId: " + dc.getId() + " address: " + dc.getAddress() + " location: " + dc.getLocation());


        System.out.println("--------- Before addition --------------");
        printlist();
        System.out.println("------------------------------------------");


        Product product2 = new Product("Mutton Minced", 350.24);
        DeliveryCenter dc2 = new DeliveryCenter("HAL Road", 5);

        inventoryService.addProduct(product2);
        inventoryService.addDeliveryCenter(dc2);


        System.out.println("--------- After addition --------------");
        printlist();
        System.out.println("------------------------------------------");

        Inventory inventory2 = new Inventory(product2.getId(), dc2.getId(), 49);
        inventoryService.addItem(inventory2);

        System.out.println("------------Before any operation--------------");
        printInventory();

        
        //add to inventory
        inventoryService.addProductToInventory(product2.getId(), dc2.getId(), 25);
        System.out.println("after adding 25 units to productId: "+ product2.getId() + " in deliveryCenter: " + dc2.getId());

        printInventory();

        //deduct from inventory
        try {
            inventoryService.deductProductFromInventory(product.getId(), dc.getId(), 10);
        } catch (QuantityExceedsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("after removing 10 units for productId: "+ product.getId() + " from deliveryCenter: " + dc.getId());
        printInventory();
        
        
        
        // fetch inventory details for productId and deliveryCenterId
        
        Inventory inventory = inventoryService.getInventoryDetails(product.getId(), dc.getId());
        System.out.println("Id              ProductId           DeliveryCenterId       Quantity");
        System.out.println("  " + inventory.getId() + "           " + inventory.getProductId() + "           "
                    + inventory.getDeliveryCenterId() + "           " + inventory.getQuantity());  
      
    }


    public static void printlist() {
        System.out.println("\nList of available products\n");
        System.out.println("productId      ProductName           Price");
        for (Product product1 : inventoryService.getProductList()) {
            System.out.println("  " + product1.getId() + "           " + product1.getName() + "           " + product1.getPrice());
        }


        System.out.println("\nList of available delivery centers\n");
        System.out.println("deliveryCenterId          Address                       Location");
        for (DeliveryCenter dc1 : inventoryService.getDeliveryCenterList()) {
            System.out.println("   " + dc1.getId() + "           " + dc1.getAddress() + "     " + dc1.getLocation());
        }

    }

    public static void printInventory() {
       System.out.println("\n---------------------------Inventory List----------------------------\n");
       System.out.println("Id              ProductId           DeliveryCenterId       Quantity");
       for (Inventory inventory : inventoryService.getInventoryList()) {
            System.out.println("  " + inventory.getId() + "           " + inventory.getProductId() + "           "
                    + inventory.getDeliveryCenterId() + "           " + inventory.getQuantity());
        }
       System.out.println("---------------------------------------------------------------------");
    }
}
