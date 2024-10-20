package org.example.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Copia implements Serializable {
    private Integer id;
    private Integer idPelicula;
    private Integer idUsuario;
    private String estado;
    private String soporte;

    public Copia() {

    }

    public Copia(Integer idPelicula, Integer idUsuario, String estado, String soporte) {
        this.idPelicula = idPelicula;
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.soporte = soporte;
    }
}
