package org.example.samns.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {

    private static String url = "jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:6543/postgres?user=postgres.usnkljbjppxcddbbrkat&password=Refi507Kmr0";
    private static Connection connection;
    public static Connection getInstancia() throws SQLException {

        if(connection == null) {
            connection = DriverManager.getConnection(url);
        }

        return connection;
    }

}
