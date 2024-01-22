-- Verifica si el usuario ya existe y, si no, crea el usuario postgres
DO
$do$
    BEGIN
        IF NOT EXISTS (
            SELECT FROM pg_catalog.pg_user
            WHERE  usename = 'postgres') THEN

            CREATE USER postgres WITH PASSWORD '000000';
        END IF;
    END
$do$;

-- Verifica si la base de datos ya existe y, si no, crea la base de datos
DO
$do$
    BEGIN
        IF NOT EXISTS (
            SELECT FROM pg_catalog.pg_database
            WHERE datname = 'bank') THEN

            CREATE DATABASE bank;
        END IF;
    END
$do$;

-- Asigna permisos al usuario (esto se puede hacer de forma segura asumiendo que el usuario existe)
GRANT ALL PRIVILEGES ON DATABASE bank TO postgres;