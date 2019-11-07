INSERT INTO project.users (user_name, user_surname, user_email, user_password, user_role)
VALUES ('Vas', 'Vas', 'vas@gmail.com', 'vasia', 'ADMIN');
INSERT INTO project.users (user_name, user_surname, user_email, user_password, user_role)
VALUES ('Van', 'Van', 'van@gmail.com', 'vanya', 'ADMIN');
INSERT INTO project.users (user_name, user_surname, user_email, user_password, user_role)
VALUES ('Vov', 'Vov', 'vov@gmail.com', 'vovak', 'SENIOR_CASHIER');
INSERT INTO project.users (user_name, user_surname, user_email, user_password, user_role)
VALUES ('Hhhh', 'Hhhh', 'hhhh@gmail.com', 'Hhhh', 'MERCHANT');
INSERT INTO project.users (user_name, user_surname, user_email, user_password, user_role)
VALUES ('Mmmmm', 'Mmmmm', 'mmmmm@gmail.com', 'Mmmmm', 'USER');
INSERT INTO project.users (user_name, user_surname, user_email, user_password, user_role)
VALUES ('Kkkk', 'Kkkk', 'kkkk@gmail.com', 'Kkkk', 'USER');
INSERT INTO project.users (user_name, user_surname, user_email, user_password, user_role)
VALUES ('Lllll', 'Lllll', 'lllll@gmail.com', 'Lllll', 'CASHIER');
INSERT INTO project.users (user_name, user_surname, user_email, user_password, user_role)
VALUES ('Ppppp', 'Ppppp', 'ppppp@gmail.com', 'Ppppp', 'CASHIER');

INSERT INTO project.invoices (invoice_Cost, invoice_is_paid, user_id, invoice_status)
VALUES (100, true, 2, 'FINISHED');
INSERT INTO project.invoices (invoice_Cost, invoice_is_paid, user_id, invoice_status)
VALUES (200, true, 3, 'FINISHED');
INSERT INTO project.invoices (invoice_Cost, invoice_is_paid, user_id, invoice_status)
VALUES (100, false, 7, 'FINISHED');
INSERT INTO project.invoices (invoice_Cost, invoice_is_paid, user_id, invoice_status)
VALUES (300, true, 2, 'CANCELLED');
INSERT INTO project.invoices (invoice_Cost, invoice_is_paid, user_id, invoice_status)
VALUES (300, false, 7, 'FINISHED');
INSERT INTO project.invoices (invoice_Cost, invoice_is_paid, user_id, invoice_status)
VALUES (600, true, 2, 'CANCELLED');
INSERT INTO project.invoices (invoice_Cost, invoice_is_paid, user_id, invoice_status)
VALUES (500, false, 3, 'FINISHED');
INSERT INTO project.invoices (invoice_Cost, invoice_is_paid, user_id, invoice_status)
VALUES (400, true, 4, 'CANCELLED');

INSERT INTO project.payments (payment_value, invoice_id, user_id)
VALUES (1100, 1, 2);
INSERT INTO project.payments (payment_value, invoice_id, user_id)
VALUES (2100, 1, 3);
INSERT INTO project.payments (payment_value, invoice_id, user_id)
VALUES (1030, 1, 7);
INSERT INTO project.payments (payment_value, invoice_id, user_id)
VALUES (3003, 5, 2);
INSERT INTO project.payments (payment_value, invoice_id, user_id)
VALUES (3020, 6, 7);
INSERT INTO project.payments (payment_value, invoice_id, user_id)
VALUES (6003, 3, 2);
INSERT INTO project.payments (payment_value, invoice_id, user_id)
VALUES (5020, 3, 3);
INSERT INTO project.payments (payment_value, invoice_id, user_id)
VALUES (4020, 4, 4);

INSERT INTO project.products (product_code, product_name, product_description, product_cost, product_quantity)
VALUES ('salo228', 'salo', 'vkysne salo', 120, 100);
INSERT INTO project.products (product_code, product_name, product_description, product_cost, product_quantity)
VALUES ('borsch228', 'borsch', 'vkysnii borsch', 200, 4);
INSERT INTO project.products (product_code, product_name, product_description, product_cost, product_quantity)
VALUES ('varenik228', 'varenik', 'vkysnii varenik', 10, 10);
INSERT INTO project.products (product_code, product_name, product_description, product_cost, product_quantity)
VALUES ('pampyshka228', 'pampyshka', 'vkysna pampyshka', 10, 50);
INSERT INTO project.products (product_code, product_name, product_description, product_cost, product_quantity)
VALUES ('banderik228', 'banderik', 'vkysnii banderik', 220, 40);
INSERT INTO project.products (product_code, product_name, product_description, product_cost, product_quantity)
VALUES ('galyshka228', 'galyshka', 'vkysna galyshka', 70, 20);
INSERT INTO project.products (product_code, product_name, product_description, product_cost, product_quantity)
VALUES ('mandzarik228', 'mandzarik', 'vkysnii mandzarik', 300, 300);
INSERT INTO project.products (product_code, product_name, product_description, product_cost, product_quantity)
VALUES ('kovbasa228', 'kovbasa', 'vkysna kovbasa', 150, 400);

INSERT INTO project.order (order_number, invoice_id, product_id)
VALUES (100, 1, 2);
INSERT INTO project.order (order_number, invoice_id, product_id)
VALUES (200, 1, 3);
INSERT INTO project.order (order_number, invoice_id, product_id)
VALUES (100, 1, 7);
INSERT INTO project.order (order_number, invoice_id, product_id)
VALUES (300, 5, 2);
INSERT INTO project.order (order_number, invoice_id, product_id)
VALUES (300, 6, 7);
INSERT INTO project.order (order_number, invoice_id, product_id)
VALUES (600, 3, 2);
INSERT INTO project.order (order_number, invoice_id, product_id)
VALUES (500, 3, 3);
INSERT INTO project.order (order_number, invoice_id, product_id)
VALUES (400, 4, 4);
