package org.example.samns.repositorio;

import org.example.samns.modelo.Producto;

import java.util.List;

public interface Repositorio<T>{

    List<T> listar();

    T porId(long id);

    void guardar(T t);

    void eliminar(long id);

    List<Producto> obtenerProductosByfechaDesc();

    List<Producto> obtenerProductosByfechaAsc();

    List<Producto> obtenerProductosByPrecioAsc();

    List<Producto> obtenerProductosByPrecioDesc();

}
