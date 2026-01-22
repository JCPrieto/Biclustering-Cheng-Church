/*
 * Created on 10-abr-2005
 */
package bicl_CC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author BIGS
 */
public class Bicluster extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private JTabbedPane botonera;

    /**
     * Constructor que genera la ventana principal de la aplicacion.
     */
    public Bicluster() {
        //Crea ventana de la aplicacion
        //uamos constructor de Jframe con el titulo de la ventana de
        //argumento
        super("BIGS: Biclustering Cheng & Church");
        //Para cerrar la ventana bien
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        // Caracteristicas de la botonera
        botonera = new JTabbedPane(SwingConstants.LEFT);
        botonera.setBackground(Color.gray);//Color botones no activos.
        botonera.setForeground(Color.BLACK);//color letra

        //Carga elementos botonera
        cargaBot();

        //Carga del menu
        cargaMenu();

        getContentPane().add(botonera);
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        Bicluster bi = new Bicluster();
        bi.pack();
        //tama�o de la venta al crearse
        //(ancho,alto)
        bi.setSize(900, 690);
        bi.setBackground(Color.white);
        bi.setVisible(true);
    }

    /**
     * Carga a la botonera los distintos botones
     * (texto del tab, imagen, enlace, Texto ayuda)
     */
    private void cargaBot() {
        botonera.addTab("  Beginning  ",
                null,
                new Bienvenido(),
                "Beginning of the application");
        botonera.addTab("  Biclustering  ",
                null,
                new Biclustering(),
                "Execution of the application");
        botonera.addTab("  Results  ",
                null,
                new Resultados(),
                "It consults of results");
    }

    private void cargaMenu() {

        JMenuBar bm = new JMenuBar();
        JMenu menu = new JMenu("Archive");
        JMenuItem item0 = new JMenuItem("Exit");
        JMenu ayuda = new JMenu("Help");
        JMenuItem acerca = new JMenuItem("About...");

        //Eventos de la barra menu
        //Archivo/Salir
        item0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        acerca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Acercade acerca1 = new Acercade(new JFrame(), true);
                acerca1.setVisible(true);
            }
        });

        //Crea la estructura de la barra
        menu.add(item0);
        ayuda.add(acerca);
        bm.add(menu);
        bm.add(ayuda);
        setJMenuBar(bm);

    }
}
