package org.example.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Usuario implements Serializable {
    private Integer id;
    private String nombre;
    private String pass;

    private List<Pelicula> peliculas=new ArrayList<>();
    public Usuario() {
    }

    public Usuario(String nombre, String pass) {
        this.nombre = nombre;
        this.pass = pass;
    }

}
