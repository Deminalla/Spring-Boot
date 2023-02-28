use demodb;

INSERT INTO `order` (order_number, client_id_number, created_at, price, company_number, status, paid)
VALUES (1, 11111111111, '2023-02-25', 1.99, 1, 'NOT_STARTED', false),
       (2, 21111111111, '2023-02-26', 10.23, 1, 'NOT_STARTED', true),
       (3, 31111111111, '2023-01-13', 5.35, 3, 'IN_PROGRESS', false),
       (4, 11111111111, '2023-01-28', 38.97, 4, 'FINISHED', true);