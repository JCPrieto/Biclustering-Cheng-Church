/*
 * Created on 25-jul-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bicl_CC;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Juanky
 * <p>
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Paint extends java.awt.Frame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static String nombre;
    private static String ruta;

    public Paint() {
        initComponents();
    }

    /**
     * @param f
     */
    public static void dibujar(File f) {
        Paint grafica = new Paint();
        nombre = f.getName();
        ruta = f.getAbsolutePath();
        grafica.setSize(900, 690);
        grafica.setVisible(true);
    }

    /**
     *
     */
    private void initComponents() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                //setVisible(false);
                dispose();
            }
        });

        pack();
    }

    public BufferedImage creaImagen() {
        List<String[]> lineas = new ArrayList<String[]>();
        String linea;
        String[] palabras;
        int x;
        String aux;
        double dato;
        XYSeriesCollection grafico = new XYSeriesCollection();
        try {
            FileReader reader = new FileReader(ruta);
            BufferedReader br = new BufferedReader(reader);
            try {
                while ((linea = br.readLine()) != null) {
                    palabras = linea.split(" ");
                    lineas.add(palabras);
                }
                String[] cad = lineas.get(0);
                x = cad.length;
                for (int i = 1; i < x; i++) {
                    XYSeries series = new XYSeries("Line" + (i));
                    for (int j = 0; j < lineas.size(); j++) {
                        cad = lineas.get(j);
                        aux = cad[i];
                        dato = Double.parseDouble(aux);
                        series.add(j, dato);
                        grafico.addSeries(series);
                    }
                }
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JFreeChart chart = ChartFactory.createXYLineChart(nombre, "", "", grafico, PlotOrientation.VERTICAL, false, false, true);
        BufferedImage image = chart.createBufferedImage(850, 640);
        return image;
    }

    public void paint(java.awt.Graphics g) {
        //super.paint(g);

        Image grafica = null;
        if (grafica == null) {
            grafica = this.creaImagen();
        }
        g.drawImage(grafica, 30, 30, null);
    }


}
