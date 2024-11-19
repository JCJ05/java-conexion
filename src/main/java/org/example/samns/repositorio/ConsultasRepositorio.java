package org.example.samns.repositorio;

import org.example.samns.modelo.Producto;
import org.example.samns.util.ConexionBaseDatos;

import java.sql.*;
import java.util.Optional;

public class ConsultasRepositorio {

    private Connection getConexion() throws SQLException {

        return ConexionBaseDatos.getInstancia();
    }

    public Optional<Producto> verificarProducto(long id){

        Optional<Producto> producto = null;

       try(PreparedStatement stmt = getConexion().prepareStatement("SELECT * FROM \"java-curso\" WHERE id = ?")){

           stmt.setLong(1 , id);
           ResultSet resultSet = stmt.executeQuery();

           if(resultSet.next()){

               producto = Optional.of(getProducto(resultSet));

           }


       }catch(Exception e) {

           System.out.println(e.getMessage());
        }

        return producto;
    }

    private static Producto getProducto(ResultSet resultSet) throws SQLException {

        Producto producto = new Producto();
        producto.setId(resultSet.getLong("id"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setPrecio(resultSet.getInt("precio"));
        producto.setFecha_registro(resultSet.getDate("fecha_registro"));
        return producto;
    }


}
