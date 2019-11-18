START TRANSACTION;

INSERT INTO projectcash.invoice_status (status_id, status_description) VALUES (0, 'CREATED');
INSERT INTO projectcash.invoice_status (status_id, status_description) VALUES (1, 'FINISHED');
INSERT INTO projectcash.invoice_status (status_id, status_description) VALUES (2, 'CANCELLED');

INSERT INTO projectcash.user_roles (role_id, role_description) VALUES (0, 'USER');
INSERT INTO projectcash.user_roles (role_id, role_description) VALUES (1, 'CASHIER');
INSERT INTO projectcash.user_roles (role_id, role_description) VALUES (2, 'SENIOR_CASHIER');
INSERT INTO projectcash.user_roles (role_id, role_description) VALUES (3, 'MERCHANT');
INSERT INTO projectcash.user_roles (role_id, role_description) VALUES (4, 'ADMIN');

INSERT INTO projectcash.users (user_name, user_password, user_email, role_id, user_notes)
VALUES ('Vania', 'vania', 'vania@gmal.com', 0, '');
INSERT INTO projectcash.users (user_name, user_password, user_email, role_id, user_notes)
VALUES ('Guest', '', '', 0, '');
INSERT INTO projectcash.users (user_name, user_password, user_email, role_id, user_notes)
VALUES ('Cashier', 'cashier', 'cashier@company.com', 1, '');
INSERT INTO projectcash.users (user_name, user_password, user_email, role_id, user_notes)
VALUES ('Senior cashier', 'senior cashier', 's_cashier@company.com', 2, '');
INSERT INTO projectcash.users (user_name, user_password, user_email, role_id, user_notes)
VALUES ('Merchant', 'merchant', 'merchant_dep@company.com', 3, '');
INSERT INTO projectcash.users (user_name, user_password, user_email, role_id, user_notes)
VALUES ('Admin', 'admin', 'admin@company.com', 4, '');

INSERT INTO projectcash.products (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D001',
  'Salami',
  'Салямі',
  'Tasty Salami',
  'Вкусна салямі',
  200.00,
  1500,
  'kg',
  'кг',
  '',
  ''
);
INSERT INTO projectcash.products (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D002',
  'Lemon',
  'Лімон',
  'Tasty Lemon',
  'Вкусний лімон',
  10.00,
  1500,
  'kg',
  'кг',
  '',
  ''
);
INSERT INTO projectcash.products (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D003',
  'Royal Canin Mini Junior',
  'Royal Canin Mini Junior',
  'Royal Canin dry feed for small puppets',
  'Корм Роял Канин для щенков мeлких пород',
  226.25,
  100,
  'kg',
  'кг',
  '',
  ''
);
INSERT INTO projectcash.products (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D004',
  'Cheese',
  'Сир',
  'Tasty Cheese',
  'Вкусний сир',
  250.00,
  1500,
  'kg',
  'кг',
  '',
  ''
);
INSERT INTO projectcash.products (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D005',
'Sausage',
  'Сосісочки',
  'Tasty Sausage',
  'Вкусні сосісочки',
  150.00,
  1500,
  'kg',
  'кг',
  '',
  ''
);
INSERT INTO projectcash.products (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D006',
'Bread',
  'Хліб',
  'Tasty Bread',
  'Вкусний хліб',
  20.00,
  1500,
  'kg',
  'кг',
  '',
  ''
);
INSERT INTO projectcash.products (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D007',
  'Cucumber',
  'Огірки',
  'Tasty Cucumber',
  'Вкусні огірки',
  7.00,
  1500,
  'kg',
  'кг',
  '',
  ''
);
INSERT INTO projectcash.products (
  is_available,
  product_code,
  product_name_en,
  product_name_ru,
  product_description_en,
  product_description_ru,
  product_cost,
  product_quantity,
  product_uom_en,
  product_uom_ru,
  product_notes_en,
  product_notes_ru
) VALUES (
  TRUE,
  'D008',
 'Beaf',
  'Телятина',
  'Tasty Beaf',
  'Вкусна телятина',
  200.00,
  1500,
  'kg',
  'кг',
  '',
  ''
);

INSERT INTO projectcash.invoices (invoice_code, user_name, status_id, invoice_notes)
VALUES (2, 'Guest', 0, '');
INSERT INTO projectcash.payments (invoice_code, product_code, quantity, payment_value, status_id, payment_notes)
VALUES (2, 'D003', 3.0, 0.02, 0, '');
INSERT INTO projectcash.payments (invoice_code, product_code, quantity, payment_value, status_id, payment_notes)
VALUES (2, 'D008', 10, 0.02, 0, '');

INSERT INTO projectcash.invoices (invoice_code, user_name, status_id, invoice_notes)
VALUES (3, 'Guest', 0, '');
INSERT INTO projectcash.payments (invoice_code, product_code, quantity, payment_value, status_id, payment_notes)
VALUES (3, 'D008', 20, 0.03, 0, '');
INSERT INTO projectcash.payments (invoice_code, product_code, quantity, payment_value, status_id, payment_notes)
VALUES (3, 'D002', 50, 0.03, 0, '');
INSERT INTO projectcash.payments (invoice_code, product_code, quantity, payment_value, status_id, payment_notes)
VALUES (3, 'D001', 50, 0.03, 0, '');

COMMIT;