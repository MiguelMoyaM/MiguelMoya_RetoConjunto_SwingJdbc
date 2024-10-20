package org.example;

import org.example.dao.UsuarioDAO;
import org.example.model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    JFrame ventana=new JFrame();
    private JPanel panel1;
    private JTextArea textArea1;
    private JPasswordField passwordField1;
    private JButton iniciarSesiónButton;
    private JLabel textAreaError;


    public Login(){
        ventana.add(panel1);
        ventana.setSize(400,300);
        ventana.setLocationRelativeTo(null);

        iniciarSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Aqui validamos que el usuario y contraseña que hay en los text area coinciden con alguna exixtente en la base de datos
                String nombre= textArea1.getText();
                String pass=passwordField1.getText();
                System.out.println("El nombre registrado es "+ nombre+ ", y la contraseña es "+pass);
                UsuarioDAO usuarioDAO=new UsuarioDAO();
               if ( usuarioDAO.buscarUsuario(nombre,pass)!=null){
                 iniciarSesion( usuarioDAO.buscarUsuario(nombre,pass));
                   System.out.println("Usuario encontrado");
                }else {
                   System.out.println("El usuario no exixte o la contraseña es incorrecta");
                   textAreaError.setVisible(true);
               }

            }
        });
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }


    /**
     * Abrimos otra ventana agregando el usuario como informacion a esa nueva ventana
     * @param usuario = usuario que se ha regitrado y que pasaremos a la siguiente ventana
     */
    public void iniciarSesion(Usuario usuario){
        Principal principal=new Principal(usuario);
        principal.ventana.setVisible(true);
        ventana.dispose();
    }
}
