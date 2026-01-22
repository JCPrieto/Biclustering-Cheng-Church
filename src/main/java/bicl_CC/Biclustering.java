/*
 * Created on 12-abr-2005
 */
package bicl_CC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author BIGS
 */

public class Biclustering extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //Titulo de la pagina
    private JLabel jl;
    //Paneles para poner los objetos
    private JPanel datos, botones;
    //Campos de texto para las entradas.
    private JTextField umbral, generar, alfa, rangomin, rangomax;
    //Etiquetas para las entradas
    private JLabel lumbral, lgenerar, lalfa, lrangomin, lrangomax;
    //Botones
    private JButton aceptar, editar, cargar, exe;
    //Area de texto para informaci�n
    private JTextArea info;
    //Para recoger datos de algoritmo
    private Cc alg = new Cc();
    private JTextField miv;
    private JLabel lmiv;

    public Biclustering() {
        setLayout(new BorderLayout());

        // Titulo de la pagina.Estilos de la letra
        setBackground(Color.gray); //Color del fondo del titulo de la pagina.
        jl = new JLabel("BioInformatics Group Seville", JLabel.CENTER);
        jl.setForeground(Color.white);
        jl.setFont(new Font("Times-Roman", Font.BOLD, 17));

        // Inicializacion area de informacion.
        info = new JTextArea();
        //Caracteristicas de info.
        info.setFont(new Font("SansSerif", Font.PLAIN, 14));
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.setEditable(false);
        info.setBorder(BorderFactory.createTitledBorder(" Information of the algorithm "));
        info.setText("Select the appropriate configuration, the file arff and press to execute." +
                "\nIf it seems that during the execution the program doesn't work, don't worry," +
                "it is alone an effect since if the program is carrying out the analysis, " +
                "keep in mind the great quantity of time that can end up needing in an analysis.");

        //Datos algoritmo
        //Cargando los valores por defectoen textbox
        umbral = new JTextField(String.valueOf(alg.umb));
        generar = new JTextField(String.valueOf(alg.gen));
        alfa = new JTextField(String.valueOf(alg.alf));
        rangomin = new JTextField(String.valueOf(alg.ranm));
        rangomax = new JTextField(String.valueOf(alg.ranM));
        miv = new JTextField(alg.mv);
        // Inicializacion Etiquetas
        lumbral = new JLabel("Threshold Value MSR:");
        lgenerar = new JLabel("Biclusters to generate: ");
        lalfa = new JLabel("Multiple erased parameter: ");
        lrangomin = new JLabel("Minimum value to generate the random numbers: ");
        lrangomax = new JLabel("Maximum value to generate the random numbers: ");
        lmiv = new JLabel("Missing Values: ");

        // Inicializaci�n de los botones
        aceptar = new JButton("Accept current configuration");
        editar = new JButton("Edit data algorithm");
        cargar = new JButton("Load Dataset");
        exe = new JButton("Run");

        //Hot Keys
        aceptar.setMnemonic(65);
        editar.setMnemonic(69);
        cargar.setMnemonic(76);
        exe.setMnemonic(82);

        //Metodos externos que crean los paneles
        consDatosPanel();
        consBotonesPanel();

        // monta los objetos
        add(jl, BorderLayout.NORTH);
        add(info, BorderLayout.CENTER);
        add(datos, BorderLayout.SOUTH);
        add(botones, BorderLayout.EAST);

    }

    /**
     * Metodo para dar acciones cuando se produce algun evento
     */
    public void actionPerformed(ActionEvent evt) {
        //Si se produce la pulsaci�n sobre aceptar, exe,
        //o la introducci�n de algun campo
        if ((evt.getSource() == umbral) || (evt.getSource() == aceptar) || (evt.getSource() == exe)) {
            String umbralText = umbral.getText();
            lumbral.setText("Threshold Value MSR:   " + umbralText);
            //Pasamos el texto a double
            alg.umb = Double.parseDouble(umbralText);
            //After printing text to JLabel, hides the textfield
            umbral.setVisible(false);
        }
        if ((evt.getSource() == generar) || (evt.getSource() == aceptar) || (evt.getSource() == exe)) {
            String generarText = generar.getText();
            lgenerar.setText("Biclusters to generate:   " + generarText);
            //Pasamos el texto a double
            alg.gen = Integer.parseInt(generarText);
            //After printing text to JLabel, hides the textfield
            generar.setVisible(false);
        }
        if ((evt.getSource() == alfa) || (evt.getSource() == aceptar) || (evt.getSource() == exe)) {
            String alfaText = alfa.getText();
            lalfa.setText("Multiple erased parameter:   " + alfaText);
            //Pasamos el texto a double
            alg.alf = Double.parseDouble(alfaText);
            //After printing text to JLabel, hides the textfield
            alfa.setVisible(false);
        }
        if ((evt.getSource() == rangomin) || (evt.getSource() == aceptar) || (evt.getSource() == exe)) {
            String rangominText = rangomin.getText();
            lrangomin.setText("Minimum value to generate the random numbers:   " + rangominText);
            //Pasamos el texto a double
            alg.ranm = Integer.parseInt(rangominText);
            //After printing text to JLabel, hides the textfield
            rangomin.setVisible(false);
        }
        if ((evt.getSource() == rangomax) || (evt.getSource() == aceptar) || (evt.getSource() == exe)) {
            String rangomaxText = rangomax.getText();
            lrangomax.setText("Maximum value to generate the random numbers:   " + rangomaxText);
            //Pasamos el texto a double
            alg.ranM = Integer.parseInt(rangomaxText);
            //After printing text to JLabel, hides the textfield
            rangomax.setVisible(false);
        }
        if (evt.getSource() == miv || evt.getSource() == aceptar || evt.getSource() == exe) {
            String mivText = miv.getText();
            lmiv.setText("Missing Values: " + mivText);
            alg.mv = mivText;
            miv.setVisible(false);
        }

        if (evt.getSource() == aceptar) {
            aceptar.setEnabled(false);
            editar.setEnabled(true);
        }

        if (evt.getSource() == editar) {
            umbral.setVisible(true);
            generar.setVisible(true);
            alfa.setVisible(true);
            rangomin.setVisible(true);
            rangomax.setVisible(true);
            miv.setVisible(true);
            //restablecemos las etiquetas originales
            lumbral.setText("Threshold Value MSR: ");
            lgenerar.setText("Biclusters to generate: ");
            lalfa.setText("Multiple erased parameter: ");
            lrangomin.setText("Minimum value to generate the random numbers: ");
            lrangomax.setText("Maximum value to generate the random numbers: ");
            lmiv.setText("Missing Values: ");
            aceptar.setEnabled(true);
            editar.setEnabled(false);

        }
        if (evt.getSource() == cargar) {
            JFileChooser jfc = new JFileChooser("Datasets");
            jfc.addChoosableFileFilter(new ArffFilter());
            jfc.setSize(400, 300);
            Container parent = cargar.getParent();
            //Abre la ventana para elegir el arff.
            int choice = jfc.showOpenDialog(parent);

            if (choice == JFileChooser.APPROVE_OPTION) {
                String filename = jfc.getSelectedFile().getAbsolutePath();
                alg.leeFichero(filename);
                try {
                    alg.replaceMissingValues();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Se muetra informaci�n de los procesos.
                info.setText("The file: " + alg.m_Data.relationName()
                        + ".arff it has been loaded correctly." + "\nThe file has "
                        + alg.m_Data.numAttributes() + " genes with "
                        + alg.m_Data.numInstances() + " condition for each gene.");
                alg.cargarMatriz();
                info.setText(info.getText() + "\nThe matrix A has been loaded");
                exe.setEnabled(true);
            }
        }
        if (evt.getSource() == exe) {
            //desactivamos los botones
            //del algoritmo durante la ejecuci�n.
            editar.setEnabled(false);                //esto no funciona y no se pq.
            cargar.setEnabled(false);
            aceptar.setEnabled(false);
            exe.setEnabled(false);
            info.setText(info.getText() + "\nRunning....");
            alg.ejecucion();
            info.setText(info.getText() + "\nEnd of the algorithm, to see" +
                    "the results consult the window of results.");
            //una vez terminado volvemos a activarlos
            editar.setEnabled(true);
            cargar.setEnabled(true);
            aceptar.setEnabled(false);
            exe.setEnabled(false);
            //Aqui podemos mostrar caracteristicas generales del resultado
        }
    }

    /**
     * Construye el panel con los datos necesarios para el biclustering
     */
    private void consDatosPanel() {
        datos = new JPanel();
        // Propiedades del panel.
        datos.setBackground(Color.white);
        //GridLayout(Filas, 2, 10, 10)
        datos.setLayout(new GridLayout(6, 2, 10, 0));

        //A�adimos los elementos al panel
        datos.add(lumbral);
        datos.add(umbral);
        datos.add(lgenerar);
        datos.add(generar);
        datos.add(lalfa);
        datos.add(alfa);
        datos.add(lrangomin);
        datos.add(rangomin);
        datos.add(lrangomax);
        datos.add(rangomax);
        datos.add(lmiv);
        datos.add(miv);

        //Creamos los listener para las cuadrod de texto
        umbral.addActionListener(this);
        generar.addActionListener(this);
        alfa.addActionListener(this);
        rangomin.addActionListener(this);
        rangomax.addActionListener(this);
        miv.addActionListener(this);

        //Creamos un borde y un titulo para el panel
        datos.setBorder(BorderFactory.createTitledBorder("Data of the Algorithm"));
    }

    /**
     * Panel de los botones
     */
    private void consBotonesPanel() {
        botones = new JPanel();
        // Propiedades del panel.
        botones.setBackground(Color.white);
        botones.setLayout(new GridLayout(5, 1, 10, 10));
        //Creamos un borde y un titulo para el panel
        botones.setBorder(BorderFactory.createTitledBorder("Options"));

        //A�adimos los elementos al panel
        botones.add(cargar);
        botones.add(editar);
        botones.add(aceptar);
        botones.add(exe);

        // Bordes de los botones
        aceptar.setBorder(BorderFactory.createRaisedBevelBorder());
        editar.setBorder(BorderFactory.createRaisedBevelBorder());
        cargar.setBorder(BorderFactory.createRaisedBevelBorder());
        exe.setBorder(BorderFactory.createRaisedBevelBorder());

        //El boton exe lo desactivamos hasta cargar arff
        exe.setEnabled(false);
        editar.setEnabled(false);

        //Listeners para los botones
        aceptar.addActionListener(this);
        editar.addActionListener(this);
        cargar.addActionListener(this);
        exe.addActionListener(this);
    }
}
