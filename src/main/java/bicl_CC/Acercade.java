/*
 * Created on 17-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bicl_CC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Juanky
 * <p>
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Acercade extends JDialog {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTextArea Info;
    private JToggleButton aceptar;

    /**
     * @param frame
     * @param b
     */
    public Acercade(JFrame frame, boolean b) {
        // TODO Auto-generated constructor stub
        super(frame, b);
        initComponents();
    }

    /**
     *
     */
    private void initComponents() {
        // TODO Auto-generated method stub
        Info = new javax.swing.JTextArea();
        aceptar = new javax.swing.JToggleButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Acerca de...");
        setName("Acerca de...");
        Info.setBackground(java.awt.Color.lightGray);
        Info.setEditable(false);
        Info.setText("Biclustering of Cheng and Church V.1.0\n\nImplementation of the Algorithm of Biclustering of Cheng and Church" +
                ", \nthat is based in the one erased and insert of nodes to find " +
                "submatrix \nin the microarray that show a low residual value." +
                "\n\nThis implementation is alpha for what we thank all type of suggestions \nand the bugs notification and problems" +
                "\n\nTo draw the graphic we use Jfreechart." +
                "\n\nSupport:\n	oneclick18@gmail.com\n	ramonmateos@gmail.com" +
                "\n\nRealise by:	Juan Carlos Prieto Silos & Ramon Mateos Tena\n\n");
        getContentPane().add(Info, java.awt.BorderLayout.CENTER);
        aceptar.setText("Ok");
        aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        getContentPane().add(aceptar, java.awt.BorderLayout.SOUTH);
        pack();
    }
}
