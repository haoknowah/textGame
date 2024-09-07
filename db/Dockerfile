FROM postgres:16.4-bookworm
ENV POSTGRES_USER project0-Noah
ENV POSTGRES_PASSWORD project0-Noah
ADD 1_schema.sql /docker-entrypoint-initdb.d
EXPOSE 5432