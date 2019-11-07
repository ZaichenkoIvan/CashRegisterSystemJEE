CREATE DATABASE project;

CREATE TABLE project.products (
  product_id INT NOT NULL AUTO_INCREMENT KEY,
  product_code VARCHAR(12) NOT NULL UNIQUE,
  product_name VARCHAR(128),
  product_description VARCHAR(255),
  product_cost INT NOT NULL DEFAULT 0,
  product_quantity INT NOT NULL DEFAULT 0,
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.users (
  user_id INT NOT NULL AUTO_INCREMENT KEY,
  user_name VARCHAR(128),
  user_surname VARCHAR(128),
  user_email VARCHAR(48) NOT NULL DEFAULT '''',
  user_password VARCHAR(32) NOT NULL DEFAULT '''',
  user_role VARCHAR(32) NOT NULL DEFAULT 'USER'
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.invoices (
  invoice_id INT NOT NULL AUTO_INCREMENT KEY,
  invoice_cost INT NOT NULL DEFAULT 0,
  invoice_is_paid BIT(1) NOT NULL DEFAULT FALSE,
  user_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES project.users (user_id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  invoice_status VARCHAR(32) NOT NULL DEFAULT 'CANCELLED'
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.payments (
  payment_id INT NOT NULL AUTO_INCREMENT KEY,
  payment_value INT NOT NULL DEFAULT 0,
  invoice_id INT NOT NULL,
  user_id INT NOT NULL,
  FOREIGN KEY (invoice_id) REFERENCES project.invoices(invoice_id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
FOREIGN KEY (user_id) REFERENCES project.users(user_id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.order (
   order_id INT NOT NULL AUTO_INCREMENT KEY,
   order_number INT NOT NULL,
   invoice_id INT NOT NULL,
   product_id INT NOT NULL,
  FOREIGN KEY (invoice_id) REFERENCES project.invoices(invoice_id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
 FOREIGN KEY (product_id) REFERENCES project.products(product_id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE InnoDB DEFAULT CHARSET=utf8;
