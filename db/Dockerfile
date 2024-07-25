FROM postgres:16.3-bookworm
ENV POSTGRES_USER project0-Noah
ENV POSTGRES_PASSWORD project0-Noah
ADD 1_schema.sql /docker-entrypoint-initdb.d
EXPOSE 5432