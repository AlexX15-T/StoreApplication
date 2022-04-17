insert into schooldb.student(id,name, address, age, email) values (1233, "Alex Tiugan", "Aleea Peana, nr.9", 20, "alex.tiugan@yahoo.com");
insert into schooldb.client(id,name, address, age, email) values (1233, "Alex Tiugan", "Aleea Peana, nr.9", 20, "alex.tiugan@yahoo.com");
insert into schooldb.product(id, name, price, manufacturer, stock) values(1234, "banane", 10.3, "Lidl", 4);
insert into schooldb.order(id,idProduct, nameProduct, idClient, nameClient, quantity) values (1111, 1234, "banane", 1234,"Alex Tiugan", 2.35);

select * from schooldb.product;
select * from schooldb.order;
SELECT * FROM schooldb.student;
select * from schooldb.client;
UPDATE schooldb.client SET name = 'Tiugan Alex- Mihail' , adress = 'Aleea Peana, nr.9' , email = 'alex.tiugan@yahoo.com' , age = 20  WHERE id = 1234;
select * from schooldb.order where nameClient = "Alex Tiugan";