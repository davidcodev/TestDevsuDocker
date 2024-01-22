insert into account (client_id, nro_cuenta, tipo_cuenta, saldo_inicial, estado) values (1, 1, 'AHORROS', 0, true);
insert into account (client_id, nro_cuenta, tipo_cuenta, saldo_inicial, estado) values (1, 1, 'CORRIENTE', 0, true);
insert into account (client_id, nro_cuenta, tipo_cuenta, saldo_inicial, estado) values (2, 2, 'AHORROS', 50, true);

--insert into movement (id, nro_cuenta, tipo_cuenta, fecha, tipo_movimiento, valor, saldo) values (1, 1, 'AHORROS', '2023-01-17 12:00:00', 'DEPOSITO', 120, 120);
--insert into movement (id, nro_cuenta, tipo_cuenta, fecha, tipo_movimiento, valor, saldo) values (2, 1, 'AHORROS', '2023-01-17 12:05:00', 'DEPOSITO', 130, 150);
--insert into movement (id, nro_cuenta, tipo_cuenta, fecha, tipo_movimiento, valor, saldo) values (3, 2, 'AHORROS', '2023-01-17 11:00:00', 'DEPOSITO', 10, 10);
--SELECT setval('movement_id_seq', 4, false);