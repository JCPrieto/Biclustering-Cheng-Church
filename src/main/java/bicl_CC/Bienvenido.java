/*
 * Created on 10-abr-2005
 */
package bicl_CC;

import javax.swing.*;
import java.awt.*;

/**
 * Crea la presentacion de la aplicaci�n con una peque�a guia de uso
 *
 * @author BIGS
 */
public class Bienvenido extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // Variables.	   
    // Titulo de la pagina
    private JLabel jl;
    // Variable del area de texto.
    private JTextArea at;

    Bienvenido() {
        setLayout(new BorderLayout());

        // Titulo de la pagina.Estilos de la letra
        setBackground(Color.gray); //Color del fondo del titulo de la pagina.
        jl = new JLabel("BioInformatics Group Seville", JLabel.CENTER);
        jl.setForeground(Color.white);
        jl.setFont(new Font("Times-Roman", Font.BOLD, 17));

        // Inicializa el area de texto.
        at = new JTextArea("	Implementation of the Algorithm of Biclustering of Cheng and Church" +
                ", that is based in the one erased and insert of nodes to find " +
                "submatrix in the microarray that show a low residual value." +
                "\n\n	In the lash Biclustering, select the wanted configuration, and after the execution" +
                " consult the results, to create the graphic coarse ps archive with to select the wanted file and to pulse in showing graph." +
                "\n\n\nThis implementation is alpha for what we thank all type of suggestions and the bugs notification and problems" +
                "\n\nCarried out by:\n	Juan Carlos Prieto Silos---------------oneclick18@gmail.com\n	Ram�n Mateos Tena-------------------ramonmateos@gmail.com");


        // Configuraci�n del texto.(letra, tama�o, no editable,...)
        at.setFont(new Font("SansSerif", Font.PLAIN, 14));
        at.setLineWrap(true);
        at.setWrapStyleWord(true);
        at.setEditable(false);

        // Crea el titulo en el borde del at.
        at.setBorder(BorderFactory.createTitledBorder(
                " Welcome "));
        //monta los objetos
        add(jl, BorderLayout.NORTH);
        add(at, BorderLayout.CENTER);
    }
}
