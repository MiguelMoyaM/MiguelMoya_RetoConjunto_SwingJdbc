package org.example;

import org.example.dao.UsuarioDAO;
import org.example.model.Pelicula;
import org.example.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Principal {
    JFrame ventana = new JFrame();
    private JPanel panel1;
    private JButton volverButton;
    private JScrollPane scrollPane;
    private Usuario usuario;
    Principal(Usuario usuario) {
        this.usuario =usuario;
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());

        // Inicializar el botón "Volver"
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login=new Login();
                login.ventana.setVisible(true);
                ventana.dispose();
            }
        });

        panel1.add(volverButton, BorderLayout.SOUTH);

        JPanel panelPeliculas = new JPanel();
        panelPeliculas.setLayout(new BoxLayout(panelPeliculas, BoxLayout.Y_AXIS));

        cargarPeliculas(usuario.getId(), panelPeliculas);

        scrollPane = new JScrollPane(panelPeliculas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel1.add(scrollPane, BorderLayout.CENTER);

        // Configuración de la ventana
        ventana.add(panel1);
        ventana.setSize(400, 300);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void cargarPeliculas(Integer idUsuario, JPanel panelPeliculas) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Pelicula> peliculas = usuarioDAO.obtenerPeliculasPorUsuarioId(idUsuario);

        if (peliculas.isEmpty()) {
            JLabel noPeliculas = new JLabel("El usuario "+ usuario.getNombre()+ ", no tiene ninguna pelicula asociada");
            panelPeliculas.add(noPeliculas);
        }

        // Por cada película, añadimos un título y un botón al panel de peliculas
        for (Pelicula pelicula : peliculas) {
            JPanel panelFila = new JPanel();
            panelFila.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel tituloPelicula = new JLabel(pelicula.getTitulo());

            JButton botonDetalles = new JButton("Ver detalles");

            botonDetalles.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(ventana,
                            "Detalles de: " + pelicula.getTitulo() +
                                    "\nDirector: " + pelicula.getDirector() +
                                    "\nDescripción: " + pelicula.getDescripcion() +
                                    "\nSoporte: " + pelicula.getSoporte() +
                                    "\nEstado: " + pelicula.getEstado(),
                            "Detalles de la película",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });

            panelFila.add(tituloPelicula);
            panelFila.add(botonDetalles);

            panelPeliculas.add(panelFila);
        }
    }
}
