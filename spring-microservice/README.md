## Setting Up Database

On a postgreSQL instance (local or remote) create a dev user and database.

```sql
CREATE USER twitter_clone_user_dev WITH password '<CHOOSE_PASS_WORD>';
CREATE DATABASE twitter_clone_dev WITH OWNER twitter_clone_user_dev;
```

We chose to create and manage the database schema manually.

To generate the PostgreSQL database schema, run the sql satements in `../sql/generate_schema.sql`.



## Running the application

To run the API using maven, create the following bash script and run it.
```bash
#  start_dev.sh
./mvnw clean
export REALM_NAME=<KEYCLOACK_REALM>
export KEYCLOAK_SERVER_URL=<KEY_CLOACK_SERVER_URL_WITH_PORT>
export CLIENT_ID=<KEYCLOAK_CLIENT_ID>
export CLIENT_SECRET=<KEYCLOAK_CLIENT_SECRET>

export POSTGRESQL_DATABASE_NAME=<DATABASE_NAME>
export POSTGRESQL_PORT=<DATABASE_PORT> # 5432
export POSTGRESQL_URL=<DABASE_URL> # example.com, localhost
export POSTGRESQL_USER=<DATABASE_URL>
export POSTGRESQL_PASSWORD=<DATABASE_PASSWORD>
export WEBAPP_URL=<F># http://localhost:3000

export MQ_HOST=<RABBITMQ_HOST> # example.com, localhost
export MQ_USER=<RABBITMQ_HOST>
export MQ_PASSWORD=<RABBITMQ_PASSWORD>
export MQ_QUEUE=<RABBITMQ_QUEUE_NAME>

./mvnw spring-boot:run -Dspring.profiles.active=dev
```

Make the bash script executable:
```bash
chmod +x ./start_dev.sh
```

To run it:
```bash
# Make the bash script executable
./start_dev.sh
```


