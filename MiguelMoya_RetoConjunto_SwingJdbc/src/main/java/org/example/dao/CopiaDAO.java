package org.example.dao;

import org.example.model.Copia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CopiaDAO implements DAO<Copia> {

    /**
     * Deulve una lisat con todas las copias de la base de datos
     * @return
     */
    @Override
    public List<Copia> findAll() {
        List<Copia> copias = new ArrayList<>();
        String sql = "SELECT * FROM copia";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {

            ResultSet rs = pS.executeQuery();

            while (rs.next()) {
                Copia copia = new Copia();
                copia.setId(rs.getInt("id"));
                copia.setIdPelicula(rs.getInt("id_pelicula"));
                copia.setIdUsuario(rs.getInt("id_usuario"));
                copia.setEstado(rs.getString("estado"));
                copia.setSoporte(rs.getString("soporte"));
                copias.add(copia);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return copias;
    }


    /**
     * Busca una copia por su id y la devuelve
     * @param id = id de la copia a buscar
     * @return
     */
    @Override
    public Copia findById(Integer id) {
        Copia copia = null;
        String sql = "SELECT * FROM copia WHERE id = ?";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {

            pS.setInt(1, id);
            ResultSet rs = pS.executeQuery();

            if (rs.next()) {
                copia = new Copia();
                copia.setId(rs.getInt("id"));
                copia.setIdPelicula(rs.getInt("id_pelicula"));
                copia.setIdUsuario(rs.getInt("id_usuario"));
                copia.setEstado(rs.getString("estado"));
                copia.setSoporte(rs.getString("soporte"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return copia;
    }

    /**
     * Guardamos una copia en la base de datos
     * @param copia = copia a almacenar
     */
    @Override
    public void save(Copia copia) {
        // Verificar que el idUsuario no sea nulo
        if (copia.getIdUsuario() != null) {
            String sql = "INSERT INTO copia (id_pelicula, id_usuario, estado, soporte) VALUES (?, ?, ?, ?)";

            try (Connection conn = JdbcUtils.getConn();
                 PreparedStatement pS = conn.prepareStatement(sql)) {

                pS.setInt(1, copia.getIdPelicula());
                pS.setInt(2, copia.getIdUsuario());
                pS.setString(3, copia.getEstado());
                pS.setString(4, copia.getSoporte());

                pS.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No se puede guardar la copia. El idUsuario es nulo.");
        }
    }



    /**
     * Actualizamos la información de una copia
     * @param copia = Copia a modificar
     */
    @Override
    public void update(Copia copia) {

    }

    /**
     * Borramos una coipa en especificado
     * @param copia = Copia a borrar
     */
    @Override
    public void delete(Copia copia) {
        String sql = "DELETE FROM copia WHERE id = ?";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {

            pS.setInt(1, copia.getId());
            pS.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Elimina todas las copias asociadas en la base de datos
     */
    public void deleteAllCopias() {
        String sql = "DELETE FROM copia";

        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement pS = conn.prepareStatement(sql)) {

            pS.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
