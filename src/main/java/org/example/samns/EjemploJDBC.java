package org.example.samns;

import org.example.samns.modelo.Producto;
import org.example.samns.repositorio.ConsultasRepositorio;
import org.example.samns.repositorio.ProductoRepositorio;
import org.example.samns.repositorio.Repositorio;
import org.example.samns.util.ConexionBaseDatos;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class EjemploJDBC {


    public static ConsultasRepositorio getConsultas(){

        return new ConsultasRepositorio();

    }

    public static Repositorio<Producto> getRepositorio(){

        Repositorio<Producto> repositorio;

        return new ProductoRepositorio();
    }


    public static void main(String[] args) {

            try(Connection connection = ConexionBaseDatos.getInstancia()) {

                int opcion = solicitarOpcion();
                verificarOpcion(opcion, args);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }

    private static void verificarOpcion(int opcion , String[] args) {

        switch (opcion){

            case 0:
                System.out.println("Debes ingresar un numero del 1 al 5, asi que por favor vuelve a intentanrlo");
                main(args);
                break;

            case 1:
                System.out.println("***************Procedemos a registrar un nuevo producto***************");
                registrarProducto(args);
                break;

            case 2:
                System.out.println("***************Procedemos a eliminar un nuevo producto***************");
                eliminarProducto(args);
                break;

            case 3:
                System.out.println("***************PROCEDEMOS A ACTUALIZAR UN PRODUCTO***************");
                actualizarProducto(args);
                break;

            case 4:
                System.out.println("***************PROCEDEMOS A LISTAR TODOS LOS PRODUCTOS***************");
                listarProductos(args);
                break;

            default:
                System.out.println("***************PROCEDEMOS A SALIR DEL SISTEMA***************");
                System.out.println("*****GRACIAS POR SU VISITA LO ESPERAMOS PRONTO*****");
                System.exit(0);

        }

    }

    private static void listarProductos(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("********************************");
        System.out.println("******LISTAR PRODUCTOS******");

        System.out.println("*****Elija como quiere que se le listen los productos");

        System.out.println("1). Por orden de fecha de manera descendente");
        System.out.println("2). Por orden de fecha de manera ascendente");
        System.out.println("3). Por precio de manera ascendente");
        System.out.println("4). Por precio de manera descendente");
        System.out.println("5). Listar todos los productos");

        int opcion = 0;

        try {

            opcion = scanner.nextInt();

        }catch (InputMismatchException e) {

            System.out.println("Tiene que ingresar una de las ocpiones presentadas del 1-5");
            System.out.println(e.getMessage());
            main(args);

        }


        List<Producto> productos = new ArrayList<>();

        System.out.println(opcion);

        switch (opcion){

            case 1: productos = getRepositorio().obtenerProductosByfechaDesc();
                    break;

            case 2: productos = getRepositorio().obtenerProductosByfechaAsc();
                    break;

            case 3: productos = getRepositorio().obtenerProductosByPrecioAsc();
                    break;

            case 4: productos = getRepositorio().obtenerProductosByPrecioDesc();
                    break;

            default: productos = getRepositorio().listar();
        }

            productos.forEach(producto -> {
                System.out.println("Producto: " + producto.getId());
                System.out.println("Nombre: " + producto.getNombre());
                System.out.println("Precio: S./" + producto.getPrecio());
                System.out.println("Fecha de registro: " + producto.getFecha_registro());
            });

            main(args);

    }

    private static void actualizarProducto(String[] args) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("******INGRESE EL ID DEL PRODUCTO QUE DESEA ACTUALIZAR******");
            int id_producto = scanner.nextInt();

            Optional<Producto> producto = getConsultas().verificarProducto(id_producto);

            if(producto != null){

                int opcion;

                System.out.println("*****PRODUCTO ENCONTRADO*****");

                System.out.println("Nombre del producto: " + producto.get().getNombre());
                System.out.println("Precio del producto: " + producto.get().getPrecio());
                System.out.println("Fecha de registro del producto: " + producto.get().getFecha_registro());

                System.out.println("********************************");
                System.out.println("******ACTUALIZAR PRODUCTO******");
                System.out.println("Si desea actualizar el nombre ingrese 1 sino ingrese 0: ");

                try {

                    opcion = scanner.nextInt();

                    if(opcion == 0){

                        System.out.println("*****Procederemos a actualizar el Precio del producto*****");
                        System.out.println("INGRESE EL NUEVO PRECIO DEL PRODUCTO: " + producto.get().getPrecio());
                        int nuevo_precio = scanner.nextInt();

                        producto.get().setPrecio(nuevo_precio);

                    }else if(opcion == 1 ){

                        System.out.println("*****Procederemos a actualizar el nombre*****");
                        System.out.println("INGRESE EL NUEVO NOMBRE DEL PRODUCTO: " + producto.get().getNombre());

                        String producto_nombre = scanner.next();
                        scanner.nextLine();
                        producto.get().setNombre(producto_nombre);

                        System.out.println("Si desea actualizar el precio ingrese 1 sino ingrese 0: ");

                        opcion = Integer.parseInt(scanner.next());


                        if(opcion == 1){

                            System.out.println("*****Procederemos a actualizar el Precio del producto*****");
                            System.out.println("INGRESE EL NUEVO PRECIO DEL PRODUCTO: " + producto.get().getPrecio());
                            int nuevo_precio = scanner.nextInt();

                            producto.get().setPrecio(nuevo_precio);
                        }

                    }else {

                        System.out.println("A ingresado una opcion que no existe en nuestro sistema");
                        main(args);
                    }


                }catch (NumberFormatException e){

                    System.out.println(e.getMessage());

                }

            }else {

                System.out.println("EL ID DEL PRODUCTO QUE A INGRESADO NO EXISTE POR FAVOR VUELVA A INTENTARLO");
                main(args);
            }

            System.out.println("El producto: " + producto.get().getNombre() + " S./"  + producto.get().getPrecio() + " a sido actualizado correctamente") ;
            getRepositorio().guardar(producto.get());
            main(args);

    }

    private static void eliminarProducto(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("********************************");
        System.out.println("******ELIMINAR PRODUCTO******");

        System.out.println("******INGRESE EL ID DEL PRODUCTO QUE DESEA ACTUALIZAR******");
        int id_producto = scanner.nextInt();

        Optional<Producto> producto = getConsultas().verificarProducto(id_producto);

        if(producto != null){

            getRepositorio().eliminar(id_producto);
            System.out.println("El producto a sido eliminado satisfactoriamente");

        }else {

            System.out.println("EL ID QUE A INGRESADO NO EXISTE VUELVA A INTENTARLO");
            main(args);

        }

    }

    private static void registrarProducto(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("********************************");
        System.out.println("******REGISTRAR PRODUCTO******");

        System.out.println("INGRESE EL NOMBRE DEL PRODUCTO");
        String nombre_producto = scanner.next();
        scanner.nextLine();

        System.out.println("INGRESE EL PRECIO DEL PRODUCTO: " + nombre_producto);
        int precio = scanner.nextInt();

        Date fecha_hoy = new Date();
        java.sql.Date fecha_convertida = new java.sql.Date(fecha_hoy.getTime());

        Producto producto = new Producto();
        producto.setNombre(nombre_producto);
        producto.setPrecio(precio);
        producto.setFecha_registro(fecha_convertida);

        getRepositorio().guardar(producto);
        System.out.println("El producto se guardo satisfactoriamente");

        main(args);

    }

    private static int solicitarOpcion() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("**********SELECCIONE UNA OPCION QUE QUIERE REALIZAR EN EL SISTEMA DE INVENTARIOS**********");
        System.out.println(" ");
        System.out.println("1) PARA INSERTAR UN PRODUCTO");
        System.out.println("2) PARA ELIMINAR UN PRODUCTO");
        System.out.println("3) PARA ACTUALIZAR UN PRODUCTO");
        System.out.println("4) PARA LISTAR LOS PRODUCTOS");
        System.out.println("5) PARA SALIR DEL SISTEMA DE INVENTARIOS");
        System.out.println(" ");
        System.out.println("******************************************************************************************");

        int opcionElegida = 0;

        try{

            opcionElegida = scanner.nextInt();


        }catch (NumberFormatException e){

            System.out.println(e.getMessage());
        }

        return opcionElegida;
    }

}
