CREATE DATABASE project;

CREATE TABLE project.products (
  product_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
  product_code VARCHAR(12) NOT NULL UNIQUE,
  is_available BIT(1) NOT NULL DEFAULT FALSE,
  product_name VARCHAR(128),
  product_description VARCHAR(255),
  product_cost DOUBLE NOT NULL DEFAULT 0,
  product_quantity DOUBLE NOT NULL DEFAULT 0,
  reserved_quantity DOUBLE NOT NULL DEFAULT 0
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.users (
  user_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
  user_name VARCHAR(128),
  user_password VARCHAR(128),
  user_phone VARCHAR(32) NOT NULL DEFAULT '''',
  user_email VARCHAR(48) NOT NULL DEFAULT '''',
  role_user VARCHAR(32) NOT NULL DEFAULT ''USER''
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.invoices (
  invoice_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
  invoice_code BIGINT UNIQUE NOT NULL,
   cashier_id SMALLINT NOT NULL,
  is_paid BIT(1) NOT NULL DEFAULT FALSE,
  status_id SMALLINT NOT NULL,
  FOREIGN KEY (cashier_id) REFERENCES project.users (user_id)
    ON UPDATE CASCADE
     ON DELETE RESTRICT,
  status_invoice VARCHAR(32) NOT NULL DEFAULT ''CANCELLED''
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.payments (
  payment_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
   payment_value DOUBLE NOT NULL DEFAULT 0,
  invoice_code BIGINT NOT NULL,
  customer_id SMALLINT NOT NULL,
  FOREIGN KEY (invoice_code) REFERENCES project.invoices(invoice_code)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
FOREIGN KEY (customer_id) REFERENCES project.users(user_id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE project.order (
   order_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
   invoice_id SMALLINT NOT NULL,
   product_id SMALLINT NOT NULL,
  FOREIGN KEY (invoice_id) REFERENCES project.invoices(invoice_id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
 FOREIGN KEY (product_id) REFERENCES project.products(product_id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE InnoDB DEFAULT CHARSET=utf8;