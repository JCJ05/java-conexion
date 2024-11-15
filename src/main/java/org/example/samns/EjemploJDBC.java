package org.example.samns;

import org.example.samns.modelo.Producto;
import org.example.samns.repositorio.ProductoRepositorio;
import org.example.samns.repositorio.Repositorio;
import org.example.samns.util.ConexionBaseDatos;

import java.sql.*;
import java.util.Date;

public class EjemploJDBC {


    public static void main(String[] args) {


        System.out.println("SELECCIONE UNA OPCION QUE QUIERE IMPARTIR");

        try (Connection connect = ConexionBaseDatos.getInstancia()) {

            Repositorio<Producto> repositorio = new ProductoRepositorio();

            Date utilDate = new Date(); // un objeto java.util.Date
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());


            Producto producto1 = new Producto();
            producto1.setNombre("Licuadora 500");
            producto1.setPrecio(4000);
            producto1.setFecha_registro(sqlDate);
            repositorio.guardar(producto1);

            System.out.println("Producto guardado satisfactoriamente");


        } catch (SQLException e) {

            System.out.println("Hubo un error en la conexion con la base de datos");
            throw new RuntimeException(e);

        }

    }

}
