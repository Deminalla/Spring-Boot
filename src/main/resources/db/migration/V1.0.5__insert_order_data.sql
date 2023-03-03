use applicationdb;

INSERT INTO `order` (order_id, user_id, created_at, price, company_id, status)
VALUES (1, 11111111111, '2023-02-25', 1.99, 1, 'NOT_STARTED'),
       (2, 21111111111, '2023-02-26', 10.23, 1, 'NOT_STARTED'),
       (3, 31111111111, '2023-01-13', 5.35, 3, 'IN_PROGRESS'),
       (4, 11111111111, '2023-01-28', 38.97, 4, 'FINISHED');