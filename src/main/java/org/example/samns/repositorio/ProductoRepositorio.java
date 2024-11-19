package org.example.samns.repositorio;

import org.example.samns.modelo.Producto;
import org.example.samns.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductoRepositorio implements Repositorio<Producto>{

    private Connection getConection() throws SQLException {

        return ConexionBaseDatos.getInstancia();
    }

    @Override
    public List<Producto> listar() {

        List<Producto> productos = new ArrayList<>();

        try (Statement stmt = getConection().createStatement();
             ResultSet resultSet = stmt.executeQuery("select * from \"java-curso\"")) {

            while(resultSet.next()){

                Producto producto = getProducto(resultSet);

                productos.add(producto);

            }


        }catch (Exception e) {

             productos = null;
        }

        return productos;
    }

    private static Producto getProducto(ResultSet resultSet) throws SQLException {

        Producto producto = new Producto();
        producto.setId(resultSet.getLong("id"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setPrecio(resultSet.getInt("precio"));
        producto.setFecha_registro(resultSet.getDate("fecha_registro"));
        return producto;
    }

    @Override
    public Producto porId(long id) {

        Producto producto = null;

         try(PreparedStatement stmt = getConection().prepareStatement("select * from \"java-curso\" where id = ?")){

            stmt.setLong(1 , id);

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()){

                producto = getProducto(resultSet);
            }

         }catch (Exception e) {

             System.out.println("Ocurrio un problema con la base de datos");

         }


        return producto;
    }

    @Override
    public void guardar(Producto producto) {

        String sql;

        if(producto.getId() > 0) {

            sql = "UPDATE \"java-curso\" SET nombre=? , precio=? where id=?";

        }else {

            sql = "INSERT INTO \"java-curso\"(nombre, precio , fecha_registro) VALUES(?, ? ,?)";

        }

        try(PreparedStatement stmt = getConection().prepareStatement(sql)){

            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getPrecio());

            if(producto.getId() > 0){

                stmt.setLong(3 , producto.getId());

            }else {

                stmt.setDate(3, (Date) producto.getFecha_registro());

            }

            stmt.executeQuery();

        }catch (Exception e){

            System.out.println(e.getMessage());
        }

    }

    @Override
    public void eliminar(long id) {

        try(PreparedStatement stmt = getConection().prepareStatement("DELETE FROM \"java-curso\" where id = ?")){
            stmt.setLong(1 , id);
            stmt.execute();

        }catch (Exception e) {

            System.out.println(e.getMessage());;
        }
    }

    public List<Producto> obtenerProductosByfechaDesc(){

        List<Producto> productos = new ArrayList<>();


        try(Statement stmt = getConection().createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from \"java-curso\" order by fecha_registro desc")){

            while(resultSet.next()){

                Producto producto = getProducto(resultSet);
                productos.add(producto);

            }

        }catch (Exception e){

            System.out.println(e.getMessage());
        }

        return productos;
    }

    public List<Producto> obtenerProductosByfechaAsc(){

        List<Producto> productos = new ArrayList<>();

        try(Statement stmt = getConection().createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from \"java-curso\" order by fecha_registro asc")){


            while(resultSet.next()){

                Producto producto = getProducto(resultSet);
                productos.add(producto);

            }

        }catch (Exception e){

            productos = null;
            System.out.println(e.getMessage());
        }




        return productos;
    }

    public List<Producto> obtenerProductosByPrecioAsc(){

        List<Producto> productos = new ArrayList<>();

        try(Statement stmt = getConection().createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from \"java-curso\" order by precio asc")){

            while(resultSet.next()){

                Producto producto = getProducto(resultSet);
                productos.add(producto);

            }

        }catch (Exception e){

            System.out.println(e.getMessage());
        }

        return productos;
    }

    public List<Producto> obtenerProductosByPrecioDesc(){

        List<Producto> productos = new ArrayList<>();


        try(Statement stmt = getConection().createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from \"java-curso\" order by precio desc")){

            while(resultSet.next()){

                Producto producto = getProducto(resultSet);
                productos.add(producto);

            }

        }catch (Exception e){

            System.out.println(e.getMessage());
        }

        return productos;
    }

}
