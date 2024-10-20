package org.example.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Pelicula implements Serializable {
   private Integer id;
   private String titulo;
   private String genero;
   private int age;
   private String descripcion;
   private String director;

    private String estado;
    private String soporte;

    public Pelicula() {
    }

    public Pelicula(String titulo, String genero, int age, String descripcion, String director) {
        this.titulo = titulo;
        this.genero = genero;
        this.age = age;
        this.descripcion = descripcion;
        this.director = director;
    }

    public Pelicula(String titulo, String genero, int age, String descripcion, String director, String estado, String soporte) {
        this.titulo = titulo;
        this.genero = genero;
        this.age = age;
        this.descripcion = descripcion;
        this.director = director;
        this.estado = estado;
        this.soporte = soporte;
    }

}
