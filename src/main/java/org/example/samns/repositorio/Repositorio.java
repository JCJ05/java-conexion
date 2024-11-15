package org.example.samns.repositorio;

import java.util.List;

public interface Repositorio<T>{

    List<T> listar();

    T porId(long id);

    void guardar(T t);

    void eliminar(long id);

}
