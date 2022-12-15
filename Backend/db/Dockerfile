FROM mysql

EXPOSE 3306

COPY ./my.cnf /etc/mysql/conf.d/my.cnf
COPY ./initdb.d /docker-entrypoint-initdb.d

CMD ["mysqld"]
