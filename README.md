# Licious Inventory Management System

This is a simple inventory management system for multiple delivery centers.

**Requirements**

* Users can fetch list of products
* Users can fetch list of deliveryCenters
* Users can fetch inventory list

* Users can fetch item for given productId and deliveryCenters
* Users can add an item to inventory (only for existing productId and deliveryCenterId)
* Users can deduct an item to inventory (only for existing productId and deliveryCenterId) throws an error if available quantity is less.


**Additional features**

Users can fetch all the deliveryCenters within a given distance(for simplicity consider this in a radius of distance).
this can be improved based on latitude and longitudes


```java
List<Product> getProductList();

List<DeliveryCenter> getDeliveryCenterList();

List<Inventory> getInventoryList();

Inventory getInventoryDetails(int productId, int deliveryCenterId);

void addProductToInventory(int productId, int deliveryCenterId, int quantity);

void deductProductFromInventory(int productId, int deliveryCenterId, int quantity)

```

## Implementation Details

- Programming Language: Java
- API: Java DataBase Connectivity (JDBC)
- Back-end (Relational Database): MySQL
- Design Pattern: Data Access Object (DAO)


## TO RUN THIS
* add mysql java connector(8) to classpath
* change user password and url (com.licious.databse.DBConnectionFactory) 
* run {project-dir}/inventory.sql for intial database setup
* change user password and url for mysql 

```java
mvn clean install
mvn exec:java -Dexec.mainClass="com.licious.InventoryManagementMain" -Dexec.cleanupDaemonThreads=false
or
java -cp target/liciousInventory-1.0-SNAPSHOT.jar com.licious.InventoryManagementMain
```

## References
- [JDBC](https://docs.oracle.com/javase/tutorial/jdbc/overview/index.html)
- [MySQL](https://www.mysql.com)
- [DAO Design Pattern](https://www.oracle.com/technetwork/java/dataaccessobject-138824.html)