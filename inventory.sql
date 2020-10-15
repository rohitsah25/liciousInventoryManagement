create database licious_inventory;
use licious_inventory;


CREATE USER 'licious_admin'@'localhost' IDENTIFIED BY 'welcome1';
GRANT ALL ON licious_inventory.* TO 'licious_admin'@'localhost';


create table product (productId int NOT NULL, productName varchar(20), itemPrice double, primary key(productId));
create table deliveryCenter (deliveryCenterId int NOT NULL, address varchar(256), location int, primary key(deliveryCenterId));
create table inventory(inventoryId int NOT NULL, productId int NOT NULL,
 deliveryCenterId int NOT NULL, quantity int,
 PRIMARY KEY (productId, deliveryCenterId),
 CONSTRAINT FK_Product_id FOREIGN KEY (productId) REFERENCES product(productId),
 CONSTRAINT FK_dc_id FOREIGN KEY (deliveryCenterId) REFERENCES deliveryCenter(deliveryCenterId));


insert into product values(1, 'Chicken Curry Cut', 200);
insert into product values(2, 'Chicken Small Cut', 250);
insert into product values(3, 'Fish Rohu', 300);
insert into product values(4, 'Fish Katla', 300);
insert into product values(5, 'Fish Pomfret', 600);
insert into product values(6, 'Fish Salmon', 800);
insert into product values(7, 'Prawns', 800);
insert into product values(8, 'Crabs', 900);

insert into deliveryCenter values(100, '11th cross road', 121);
insert into deliveryCenter values(101, 'panathur main road', 80);
insert into deliveryCenter values(102, '21st main road', 158);
insert into deliveryCenter values(103, 'mg road', 11);

insert into inventory values(1001,1,101,50);
insert into inventory values(1002,1,102,23);
insert into inventory values(1010,1,103,40);
insert into inventory values(1006,1,100,1);
insert into inventory values(1005,2,100,0);
insert into inventory values(1003,2,101,11);
insert into inventory values(1007,2,103,20);
insert into inventory values(1008,2,102,15);
insert into inventory values(1004,3,103,10);
insert into inventory values(1009,3,100,5);
insert into inventory values(1013,3,101,30);


select * from product;
select * from deliveryCenter;
select * from inventory;


select * from inventory where productId=1 order by quantity desc;
select * from inventory where productId = 1;

/*gives all the inventory for product(Chicken curry cut) where delivery center's location is 50m away from position 100
in sorted order by quantity
 */

select a.deliveryCenterId,a.location, b.productId, b.inventoryId, b.quantity  from DELIVERYCENTER as a
inner join inventory as b on a.DELIVERYCENTERID=b.deliveryCenterId
where a.location between 50 and 150 and b.productId =1 order by b.quantity desc;



