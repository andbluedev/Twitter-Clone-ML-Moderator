import os

import psycopg2

try:
    POSTGRESQL_URL = os.environ['POSTGRESQL_URL']
    POSTGRESQL_USER = os.environ['POSTGRESQL_USER']
    POSTGRESQL_PASSWORD = os.environ['POSTGRESQL_PASSWORD']
    POSTGRESQL_DATABASE_NAME = os.environ['POSTGRESQL_DATABASE_NAME']
    POSTGRESQL_PORT = os.environ['POSTGRESQL_PORT']

except KeyError as e:
    raise Exception(
        f"Missing (at least ) {str(e)} environment variable...\nVisit Github for more information.")


class DatabaseManager:
    """
        Abstraction to connect with searchix postgres database
    """

    def __init__(self):
        self.connection = psycopg2.connect(
            dbname=POSTGRESQL_DATABASE_NAME,
            user=POSTGRESQL_USER,
            password=POSTGRESQL_PASSWORD,
            host=POSTGRESQL_URL,
            port=POSTGRESQL_PORT,
        )

    def query(self, query):
        cursor = self.connection.cursor()
        result = ()
        error_msg = ""
        try:
            cursor.execute(query)
            result = cursor.fetchall()

        except psycopg2.Error as e:
            if e.pgerror:
                error_msg = e.pgerror
        except Exception as e2:
            error_msg = str(e2)
        finally:
            self.connection.commit()
            cursor.close()

            if len(error_msg) > 1:
                # means that there was some error
                raise Exception(
                    f"DataManager Thrown during query {query}",
                    error_msg)
            return result
