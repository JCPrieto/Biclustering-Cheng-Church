/*
 * Created on 12-abr-2005
 */
package bicl_CC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author BIGS
 */
public class Resultados extends JPanel implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //	 Titulo de la pagina
    private JLabel jl;
    private JFileChooser fc;
    private JButton borrar, aceptar;
    private TabbedPaneController tpc;
    private Component frame;


    public Resultados() {
        setLayout(new BorderLayout());

        // Titulo de la pagina.Estilos de la letra
        setBackground(Color.gray); //Color del fondo del titulo de la pagina.
        jl = new JLabel("BioInformatics Group Seville", JLabel.CENTER);
        jl.setForeground(Color.white);
        jl.setFont(new Font("Times-Roman", Font.BOLD, 17));

        //Creo el FileChooser
        fc = new JFileChooser("Executions");
        fc.addChoosableFileFilter(new InFilter());
        fc.setMultiSelectionEnabled(true);
        fc.setDragEnabled(true);
        fc.setControlButtonsAreShown(false);
        JPanel fcPanel = new JPanel(new BorderLayout());
        fcPanel.add(fc, BorderLayout.CENTER);

        //Creo  botones.
        borrar = new JButton("Clean");
        borrar.addActionListener(this);
        aceptar = new JButton("Show Graph");
        aceptar.addActionListener(this);
        JPanel bPanel = new JPanel(new BorderLayout());
        bPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bPanel.add(borrar, BorderLayout.LINE_END);
        bPanel.add(aceptar);

        //Uno el bPanel y el fcPanel en un panel uPanel.
        JPanel uPanel = new JPanel(new BorderLayout());
        uPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        uPanel.add(fcPanel, BorderLayout.CENTER);
        uPanel.add(bPanel, BorderLayout.PAGE_END);

        //TabbedPaneController controla el panel.Si no hay archivos, 
        //aparece una frase y cuando se arrastra el archivo se sustituye 
        //la frase por ellos.

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel tabPanel = new JPanel(new BorderLayout());
        tabPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tpc = new TabbedPaneController(tabbedPane, tabPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabPanel, uPanel);
        splitPane.setDividerLocation(400);
        splitPane.setPreferredSize(new Dimension(530, 650));

        // monta los componentes
        add(jl, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }

    public void setDefaultButton() {
        getRootPane().setDefaultButton(borrar);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == borrar) {
            tpc.clearAll();
        }
        if (e.getSource() == aceptar) {
            File f = fc.getSelectedFile();
            if (f != null) {
                String s = f.getName();
                int i = s.lastIndexOf('.');

                if (i > 0 && i < s.length() - 1) {
                    String extension = s.substring(i + 1).toLowerCase();
                    if ("dat".equals(extension)) {
                        Paint.dibujar(f);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Only .dat file supported.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

}
