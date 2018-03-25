insert into Customer (id, email, first_name, last_name) values (10, 'dave@dmband.com', 'Dave', 'Matthews');
insert into Customer (id, email, first_name, last_name) values (20, 'carter@dmband.com', 'Carter', 'Beauford');
insert into Customer (id, email, first_name, last_name) values (30, 'boyd@dmband.com', 'Boyd', 'Tinsley');

insert into Address (id, street, city, country, customer_id) values (10, '27 Broadway', 'New York', 'United States', 10);
insert into Address (id, street, city, country, customer_id) values (20, '27 Broadway', 'New York', 'United States', 10);

insert into Product (id, name, description, price) values (1, 'iPad', 'Apple tablet device', 499.0);
insert into Product (id, name, description, price) values (2, 'MacBook Pro', 'Apple notebook', 1299.0);
insert into Product (id, name, description, price) values (3, 'Dock', 'Dock for iPhone/iPad', 49.0);

insert into Product_Attributes (attributes_key, product_id, attributes) values ('connector', 1, 'socket');
insert into Product_Attributes (attributes_key, product_id, attributes) values ('connector', 3, 'plug');

insert into Orders (id, customer_id, shipping_address_id) values (1, 10, 20);
insert into Line_Item (id, product_id, amount, order_id, price) values (1, 1, 2, 1, 499.0);
insert into Line_Item (id, product_id, amount, order_id, price) values (2, 2, 1, 1, 1299.0);
