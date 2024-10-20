package org.example.dao;

import org.example.model.Pelicula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO implements DAO<Pelicula> {


    /**
     * Devuelve una lista con todas las peliculas que hay en la base de datos insertadas
     * @return
     */
    @Override
    public List<Pelicula> findAll() {
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = "SELECT * FROM pelicula";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {

            ResultSet rs = pS.executeQuery();

            while (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setId(rs.getInt("id"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setGenero(rs.getString("genero"));
                pelicula.setAge(rs.getInt("año"));
                pelicula.setDescripcion(rs.getString("descripcion"));
                pelicula.setDirector(rs.getString("director"));
                peliculas.add(pelicula);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return peliculas;
    }


    /**
     * Devuelve la pelicula que tenga el id especificado por parametro
     * @param id = id de la pelicula que queremos encontrar
     * @return
     */
    @Override
    public Pelicula findById(Integer id) {
        Pelicula pelicula = null;
        String sql = "SELECT * FROM pelicula WHERE id = ?";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {

            pS.setInt(1, id);
            ResultSet rs = pS.executeQuery();

            if (rs.next()) {
                pelicula = new Pelicula();
                pelicula.setId(rs.getInt("id"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setGenero(rs.getString("genero"));
                pelicula.setAge(rs.getInt("año"));
                pelicula.setDescripcion(rs.getString("descripcion"));
                pelicula.setDirector(rs.getString("director"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pelicula;
    }


    /**
     * Guarda o inserta una pelicula en la base de datos
     * @param pelicula = pelicula a guardar en la base de datos
     */
    @Override
    public void save(Pelicula pelicula) {
        String sql = "INSERT INTO pelicula (titulo, genero, año, descripcion, director) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pS.setString(1, pelicula.getTitulo());
            pS.setString(2, pelicula.getGenero());
            pS.setInt(3, pelicula.getAge());
            pS.setString(4, pelicula.getDescripcion());
            pS.setString(5, pelicula.getDirector());

            pS.executeUpdate();

            // Obtener el ID generado y asignarlo al objeto Pelicula
            ResultSet generatedKeys = pS.getGeneratedKeys();
            if (generatedKeys.next()) {
                pelicula.setId(generatedKeys.getInt(1)); // Asigna el ID generado a la película
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**
     * Actualiza los datos de una pelicula exixtente
     * @param pelicula = la pelicula a la que modificaremos los datos
     */
    @Override
    public void update(Pelicula pelicula) {
    }


    /**
     * Elimina de la base de datos una pelicula
     * @param pelicula = pelicula a eliminar de la base de datos
     */
    @Override
    public void delete(Pelicula pelicula) {
        String sql = "DELETE FROM pelicula WHERE id = ?";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {

            pS.setInt(1, pelicula.getId());
            pS.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina todas las peliculas que hay en la base de datos almacenadas
     */
    public void deleteAllPeliculas() {
        String sql = "DELETE FROM pelicula";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {
            pS.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
