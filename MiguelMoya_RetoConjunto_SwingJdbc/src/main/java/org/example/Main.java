package org.example;

import org.example.dao.CopiaDAO;
import org.example.dao.PeliculaDAO;
import org.example.dao.UsuarioDAO;
import org.example.model.Copia;
import org.example.model.Pelicula;
import org.example.model.Usuario;

public class Main {
    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PeliculaDAO peliculaDAO = new PeliculaDAO();
        CopiaDAO copiaDAO = new CopiaDAO();

        usuarioDAO.deleteAllUsuarios();
        peliculaDAO.deleteAllPeliculas();

        copiaDAO.deleteAllCopias();

        Usuario usuario1 = new Usuario("Miguel", "123");
        Usuario usuario2 = new Usuario("Jose", "qwe");

        usuarioDAO.save(usuario1);
        usuarioDAO.save(usuario2);

        //Creamos las peliculas
        Pelicula pelicula1 = new Pelicula("Star Wars", "Acción", 1977, "Una película del espacio.", "George Lucas", "bueno", "DVD");
        Pelicula pelicula2 = new Pelicula("Matrix", "Ciencia Ficción", 1999, "Una película sobre hackers.", "Hermanos Wachowski", "bueno", "Blu-ray");

        //Aqui las guardamos en la base de datos
        peliculaDAO.save(pelicula1);
        peliculaDAO.save(pelicula2);

        //Asocioamos peliculas a los usuarios
        Copia copia1 = new Copia(pelicula1.getId(), usuario1.getId(), "bueno", "DVD");
        Copia copia2 = new Copia(pelicula2.getId(), usuario1.getId(), "bueno", "Blu-ray");
        //Guardamos las copias asociadas a la base de datos
        copiaDAO.save(copia1);
        copiaDAO.save(copia2);


        Login login=new Login();
        login.ventana.setVisible(true);
    }
}
