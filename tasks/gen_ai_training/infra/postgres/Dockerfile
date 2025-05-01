from postgres:17.4

run apt update && apt install -y postgresql-17-pgvector

copy /init.sql /docker-entrypoint-initdb.d/init.sql
