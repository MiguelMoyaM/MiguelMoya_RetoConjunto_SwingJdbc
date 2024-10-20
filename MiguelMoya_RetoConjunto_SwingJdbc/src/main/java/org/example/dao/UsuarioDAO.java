package org.example.dao;

import org.example.model.Pelicula;
import org.example.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {

    // Método para obtener todas las películas asociadas a un usuario por su ID
    public List<Pelicula> obtenerPeliculasPorUsuarioId(Integer idUsuario) {
        List<Pelicula> peliculas = new ArrayList<>();
        String sql = "SELECT p.id, p.titulo, p.genero, p.año, p.descripcion, p.director, c.estado, c.soporte " +
                "FROM pelicula p " +
                "JOIN copia c ON p.id = c.id_pelicula " +
                "JOIN usuario u ON u.id = c.id_usuario " +
                "WHERE u.id = ?";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {

            pS.setInt(1, idUsuario);
            ResultSet rs = pS.executeQuery();

            while (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setId(rs.getInt("id"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setGenero(rs.getString("genero"));
                pelicula.setAge(rs.getInt("año"));
                pelicula.setDescripcion(rs.getString("descripcion"));
                pelicula.setDirector(rs.getString("director"));
                pelicula.setEstado(rs.getString("estado")); // Estado de la copia
                pelicula.setSoporte(rs.getString("soporte")); // Soporte de la copia

                peliculas.add(pelicula);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return peliculas;
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {

            ResultSet rs = pS.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre_usuario"));
                usuario.setPass(rs.getString("contraseña"));
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    @Override
    public Usuario findById(Integer id) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {

            pS.setInt(1, id);
            ResultSet rs = pS.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre_usuario"));
                usuario.setPass(rs.getString("contraseña"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }


    /**
     * Método para guardar un nuevo usuario en la base de datos
     * @param usuario = el usuario que se insertara en la base de datos
     */
    @Override
    public void save(Usuario usuario) {
        if (!existeNombreUsuario(usuario.getNombre())) {
            String sql = "INSERT INTO usuario (nombre_usuario, contraseña) VALUES (?, ?)";

            try (Connection conn = JdbcUtils.getConn();
                 PreparedStatement pS = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                pS.setString(1, usuario.getNombre());
                pS.setString(2, usuario.getPass());

                pS.executeUpdate();

                ResultSet generatedKeys = pS.getGeneratedKeys();
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Ya exixte un usuario con el nombre '" + usuario.getNombre() +"', prueba con otro");
        }
    }

    /**
     *Actualiza la informacion de un usuario de la base de datos
     * @param usuario = Usuario a la que modificaremos su informacion
     */
    @Override
    public void update(Usuario usuario) {

    }

    /**
     * Borramos de la base de datos un usuario en especifico
     * @param usuario = Usuario a eliminar
     */
    @Override
    public void delete(Usuario usuario) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {

            pS.setInt(1, usuario.getId());
            pS.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Borramos todos los usuarios que hay almacenados en la base de datos
     */
    public void deleteAllUsuarios() {
        String sql = "DELETE FROM usuario";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {
             pS.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Buscamos un usuario que su nombre y contraseña coincidan, usado a la hora de hacer el login, son los datos que el
     * usuario debe introducir y el programa debe buscar al usuario con esos datos
     * @param nombreUsuario = nombre de usuario a buscar (El nombre de usuario no puede repetise y es unico por usuario)
     * @param contraseña = contraseña del usuario qye hemos introducido
     * @return
     */
    public Usuario buscarUsuario(String nombreUsuario, String contraseña) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE nombre_usuario = ? AND contraseña = ?";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {

            pS.setString(1, nombreUsuario);
            pS.setString(2, contraseña);
            ResultSet rs = pS.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre_usuario"));
                usuario.setPass(rs.getString("contraseña"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    /**
     * Metodo con el que verificamos si un usuario de nuestra base de datos ya tiene un nombre en especifico
     * @param nombreUsuario = nombre a verificar
     * @return
     */
    public boolean existeNombreUsuario(String nombreUsuario) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE nombre_usuario = ?";
        boolean existe = false;

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {

            pS.setString(1, nombreUsuario);
            ResultSet rs = pS.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                existe = count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return existe;
    }

}
