INSERT INTO cashregister.Invoices (Invoice_project_name, Invoice_description)
VALUES ('Barbershop', 'Create web project about barbershop service'),
       ('Online-shop', 'Create web project of online shop, which sell new year`s gift');

INSERT INTO cashregister.s (_name, Invoice_id)
VALUES ('Create model of barbershop', 1),
       ('Create entities which depend on model', 1),
       ('Make some services', 1),
       ('Create tests of barbershop services', 1),
       ('Make web-site of the project', 1),
       ('Create model of online-shop', 2),
       ('Create entities which depend model', 2),
       ('Make some services', 2),
       ('Create tests of online-shop services', 2),
       ('Make web-site of the project', 2);

INSERT INTO cashregister.payment (Payment_name, Payment_spent_time, Payment_description, Payment_status, _id)
VALUES ('User table', '03:00:00', 'Create table of users', 'To do', 1),
       ('Product table', '02:00:00', 'Create table of Products', 'To do', 1),
       ('Comment table', '01:30:00', 'Create table of comments', 'To do', 1),
       ('UserEntity', '02:00:00', 'Create user entity', 'To do', 2),
       ('ProductEntity', '02:30:00', 'Create Product entity', 'To do', 2),
       ('CommentEntity', '02:00:00', 'Create comment entity', 'To do', 2),
       ('UserService', '04:00:00', 'Create user service', 'To do', 3),
       ('ProductService', '04:00:00', 'Create Product service', 'To do', 3),
       ('CommentService', '04:00:00', 'Create comment service', 'To do', 3),
       ('UserTest', '02:00:00', 'Create tests for user service', 'To do', 4),
       ('ProductService', '02:00:00', 'Create tests for Product service', 'To do', 4),
       ('CommentService', '02:00:00', 'Create tests for comment service', 'To do', 4),
       ('Main page', '04:00:00', 'Create main page', 'To do', 5),
       ('Service pages', '10:00:00', 'Create service pages', 'To do', 5),
       ('Auth service', '04:00:00', 'Create authentication service page', 'To do', 5),
       ('User table', '03:00:00', 'Create table of users', 'To do', 6),
       ('Product table', '02:00:00', 'Create table of Products', 'To do', 6),
       ('Comment table', '01:30:00', 'Create table of comments', 'To do', 6),
       ('UserEntity', '02:00:00', 'Create user entity', 'To do', 7),
       ('ProductEntity', '02:30:00', 'Create Product entity', 'To do', 7),
       ('CommentEntity', '02:00:00', 'Create comment entity', 'To do', 7),
       ('UserService', '04:00:00', 'Create user service', 'To do', 8),
       ('ProductService', '04:00:00', 'Create Product service', 'To do', 8),
       ('CommentService', '04:00:00', 'Create comment service', 'To do', 8),
       ('UserTest', '02:00:00', 'Create tests for user service', 'To do', 9),
       ('ProductService', '02:00:00', 'Create tests for Product service', 'To do', 9),
       ('CommentService', '02:00:00', 'Create tests for comment service', 'To do', 9),
       ('Main page', '04:00:00', 'Create main page', 'To do', 10),
       ('Service pages', '10:00:00', 'Create service pages', 'To do', 10),
       ('Auth service', '04:00:00', 'Create authentication service page', 'To do', 10);



INSERT INTO cashregister.users (user_name, user_surname, user_email, user_password, user_role)
VALUES ('Ihor', 'Volchkov', 'igorik@gmail.com', 'pass', 'Admin');

INSERT INTO cashregister.users (user_name, user_surname, user_email, user_password, user_role, Invoice_id)
VALUES ('Fred', 'Smith', 'fred@gmail.com', 'pass', 'Scrum master', 1),
       ('John', 'Johnson', 'john@gmail.com', 'pass', 'Cashier', 1),
       ('Michael', 'Morty', 'michael@gmail.com', 'pass', 'Cashier', 1),
       ('Robert', 'Bukovsky', 'robert@gmail.com', 'pass', 'Cashier', 1),
       ('Andrew', 'Martin', 'martin@gmail.com', 'pass', 'Cashier', 1),
       ('Luke', 'Skywalker', 'luke@gmail.com', 'pass', 'Cashier', 1),
       ('Steve', 'Rogers', 'captain@gmail.com', 'pass', 'Scrum master', 2),
       ('Backie', 'Barnes', 'barnes@gmail.com', 'pass', 'Cashier', 2),
       ('Tony', 'Stark', 'stark@gmail.com', 'pass', 'Cashier', 2),
       ('Peter', 'Parker', 'spider@gmail.com', 'pass', 'Cashier', 2),
       ('Chris', 'Hermsword', 'tor@gmail.com', 'pass', 'Cashier', 2),
       ('Steven', 'Strange', 'doctor@gmail.com', 'pass', 'Cashier', 2);
