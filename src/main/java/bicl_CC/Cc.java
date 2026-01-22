/*
 * Created on 25-abr-2005
 */
package bicl_CC;


import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * @author BIGS
 */
public class Cc {
    static Filter m_FMissing = new ReplaceMissingValues();// Objeto para realizar el filtrado de los valores en blanco dentor del DataSet
    public String mv;
    protected double umb, alf;
    protected int gen, ranm, ranM;
    protected Instances m_Data;
    protected double a[][];//matriz original
    private boolean F[];
    private boolean C[];
    //variables para salida
    private double[] rowVar, msrF;
    private String dir;
    private FileWriter fw;
    private BufferedWriter bw;
    private PrintWriter salida;
    private String dirBicl;
    private File f1;  //Carpeta de resultados.
    private int[][] genCon;
    private BufferedWriter tw;
    private PrintWriter gendat;

    public Cc() {
        umb = 300;
        gen = 10;
        alf = 1.2;
        ranm = 0;
        ranM = 800;
        m_Data = null;
        mv = "-1";
    }

    /**
     * @param b
     * @return
     */
    private double rowVar(double[][] b) {
        double media = 0;
        int n = 0;
        for (int j = 0; j < b[0].length; j++) {
            if (F[j]) {
                media += varfila(b, j);
                n++;
            }
        }
        return media / n;
    }

    /**
     * @param b
     * @param j
     * @return
     */
    private double varfila(double[][] b, int j) {
        double media = 0;
        double xcua = 0;
        int n = 0;
        for (int i = 0; i < b.length; i++) {
            if (C[i]) {
                xcua += b[i][j] * b[i][j];
                media += b[i][j];
                n++;
            }
        }
        return (xcua / n) - ((media / n) * (media / n));
    }

    /**
     * Crea el archivo con las opciones para crear ps.
     *
     * @param n :numero de bicluster.
     */
    private void psTxt(int n, int numGen, double max, double min) {

        try {
            fw = new FileWriter("Executions\\" + dir + "\\" + "psbicl" + (n + 1) + ".txt");
            bw = new BufferedWriter(fw);
            salida = new PrintWriter(bw);
            salida.println("set terminal postscript color solid");
            salida.println("set output '" + f1.getAbsolutePath() + "\\" + "GrafBiclus" + (n + 1) + ".ps" + "'");
            salida.println("set title 'psbicl" + (n + 1) + ".txt'");
            salida.println("set yrange [" + min + ":" + max + "]");
            salida.println("set key off");
            salida.println("set style fill solid 0.25 border");
            salida.print("plot ");
            for (int i = 1; i < numGen; i++) {
                salida.print("'" + f1.getAbsolutePath() + dirBicl + "' using 1:" + (i + 1) + "  with linesp lt 7 pt 2,");
            }
            salida.print("'" + f1.getAbsolutePath() + dirBicl + "' using 1:" + (numGen + 1) + "  with linesp lt 7 pt 2");
        } catch (java.io.IOException e) {
        } finally {
            salida.close();
        }
    }

    private double maxgrf(double[][] b) {
        double max = b[0][0];
        for (int i = 0; i < b.length; i++) {
            if (C[i]) {
                for (int j = 0; j < b[0].length; j++) {
                    if (F[i]) {
                        if (b[i][j] > max) {
                            max = b[i][j];
                        }
                    }
                }
            }
        }
        return max;
    }

    /**
     * @param b
     * @return
     */
    private double mingrf(double[][] b) {
        double min = b[0][0];
        for (int i = 0; i < b.length; i++) {
            if (C[i]) {
                for (int j = 0; j < b[0].length; j++) {
                    if (F[i]) {
                        if (b[i][j] < min) {
                            min = b[i][j];
                        }
                    }
                }
            }
        }
        return min;
    }

    /**
     * Genera un numero aleatorio dentro del rango estipulado, son solo enteros
     * positivos y negativos entre 800 y -800(igual q el original)
     */
    private double numAlea() {
        Random m = new Random();
        double numero = m.nextInt(ranM * 2) - ranM;
        while (numero < ranm || numero > ranM)
            numero = m.nextInt(ranM * 2) - ranM;
        return numero;
    }

    /**
     * Crea el directirio de resultados, y el txt.
     */
    @SuppressWarnings("deprecation")
    private String direcSal() {
        Date h = new Date();
        String dirc = m_Data.relationName() + "_" + h.getDate() + "-" + (h.getMonth() + 1) + "-" + (h.getYear() + 1900) + "_" + h.getHours() + "-" + h.getMinutes();
        f1 = new File("Executions\\" + dirc);
        f1.mkdir();
        try {
            fw = new FileWriter("Executions\\" + dirc + "\\" + (dirc + ".txt"));
            bw = new BufferedWriter(fw);
            salida = new PrintWriter(bw);
            salida.println("Date: " + h.getDate() + "\\" + (h.getMonth() + 1) + "\\" + (h.getYear() + 1900) + "    Hour: " + h.getHours() + ":" + h.getMinutes());
            salida.println("Analyzed Dataset: " + m_Data.relationName() + ".arff." + "\nThe archive has "
                    + m_Data.numAttributes() + " genes with "
                    + m_Data.numInstances() + " condition for each gene.");
            salida.println("Threshold Value of the residual: " + umb);
            salida.println("Value for erased multiple: " + alf);
            salida.println("Rank of random numbers. Minimum: " + ranm + " Maximum: " + ranM);
            salida.println("Number of requested Biclusters " + gen);
        } catch (IOException e) {
        } finally {
            salida.close();
        }

        return dirc;
    }

    /**
     * Crea el archivo con datoas para cada bicluster, poniendo
     * las condiciones en filas y los genes en columnas
     *
     * @param i :numero de bicluster
     * @param a :matriz con los datos
     */
    private void salidaGraf(int i, double[][] a) {
        try {
            int f, c;
            dirBicl = "\\bicluster" + (i + 1) + ".dat";
            String dirBicl2 = ("Executions\\" + dir + "\\" + dirBicl);
            fw = new FileWriter(dirBicl2);
            bw = new BufferedWriter(fw);
            salida = new PrintWriter(bw);
            int n = 1;
            for (c = 0; c < (C.length); c++) {
                if (C[c]) {
                    salida.print(n);
                    n++;
                    for (f = 0; f < (F.length); f++) {
                        if (F[f]) {
                            salida.print(" " + a[c][f]);
                        }
                    }
                    salida.print("\n");
                }
            }
        } catch (java.io.IOException e) {
        } finally {
            salida.close();
        }
    }

    /**
     * Para cada p�so por el bucle de ejecucion, guarda cada bicluster
     * en los archivos creados en direcSal()
     */
    private void salidaTxt(int i) {
        int f, c, p = 1, o = 1;
        try {
//			Modo append, para cada poder a�adir por cada bicluster
            tw = new BufferedWriter(new FileWriter("Executions\\" + dir + "\\" + ("GeneralData.txt"), true));
            bw = new BufferedWriter(new FileWriter("Executions\\" + dir + "\\" + (dir + ".txt"), true));
            salida = new PrintWriter(bw);
            gendat = new PrintWriter(tw);
            //Identificacion.
            salida.println("\n*********************");
            salida.println("Bicluster: " + (i + 1));
            salida.println("*********************");
            //Genes que lo forman.
            salida.println("Genes that form it:");
            int contgen = 0;
            for (f = 0; f < (F.length); f++) {
                if (F[f]) {
                    if (p == 0) {
                        salida.print(", " + (f + 1));
                        contgen++;
                    } else {
                        salida.print((f + 1));
                        p--;
                    }
                }
            }
            salida.print(".");
            salida.println("\nNumbers of genes: " + contgen);
            //Condiciones que lo forman.
            salida.println("\nConditions that form it:");
            int contcnd = 0;
            for (c = 0; c < (C.length); c++) {
                if (C[c]) {
                    if (o == 0) {
                        salida.print(", " + (c + 1));
                        contcnd++;
                    } else {
                        salida.print((c + 1));
                        o--;
                    }
                }
            }
            salida.print(".");
            salida.println("\nNumbers of conditions: " + contcnd);
            //MSR
            salida.println("\nMSR: " + msrF[i]);
            //Row Variance
            salida.println("Row Variance: " + rowVar[i]);
            gendat.println((i + 1) + " " + msrF[i] + " " + rowVar[i] + " " + contgen + " " + contcnd);
        } catch (java.io.IOException e) {
        } finally {
            salida.close();
            gendat.close();
        }
    }

    /**
     * Devuelve el numero de filas que no han sido borradas.
     */
    private int numGen() {
        int cont = 0;
        for (int i = 0; i < F.length; i++) {
            if (F[i] == true) {
                cont++;
            }
        }
        return cont;
    }

    /**
     * Devuelve el numero de columnas que no han sido borradas.
     */
    private int numCon() {
        int cont = 0;
        for (int j = 0; j < C.length; j++) {
            if (C[j] == true) {
                cont++;
            }
        }
        return cont;
    }

    private double msrMatriz(double[][] a) {
        double mediaTotal = 0;
        double mediaColumnas[] = new double[a.length];
        double mediaFilas[] = new double[a[0].length];
        int i, j;
        //Calculamos las distintas medias necesarias.
        Arrays.fill(mediaColumnas, 0);
        Arrays.fill(mediaFilas, 0);
        for (i = 0; i < a.length; i++) {
            if (C[i]) {
                for (j = 0; j < a[0].length; j++) {
                    if (F[j]) {
                        mediaTotal += a[i][j];
                        mediaColumnas[i] += a[i][j];
                    }
                }
            }
        }
        for (j = 0; j < a[0].length; j++) {
            if (F[j]) {
                for (i = 0; i < a.length; i++) {
                    if (C[i]) {
                        mediaFilas[j] += a[i][j];
                    }
                }
            }
        }
        mediaTotal = mediaTotal / (numGen() * numCon());
        for (j = 0; j < mediaFilas.length; j++) {
            if (F[j]) {
                mediaFilas[j] = mediaFilas[j] / numCon();
            }
        }
        for (i = 0; i < mediaColumnas.length; i++) {
            if (C[i]) {
                mediaColumnas[i] = mediaColumnas[i] / numGen();
            }
        }
        double residuo = 0;
        for (i = 0; i < a.length; i++) {
            if (C[i]) {
                for (j = 0; j < a[0].length; j++) {
                    if (F[j]) {
                        residuo += (a[i][j] - mediaFilas[j] - mediaColumnas[i] + mediaTotal) * (a[i][j] - mediaFilas[j] - mediaColumnas[i] + mediaTotal);
                    }
                }
            }
        }
        residuo = residuo / (numGen() * numCon());//Dividimos el residuo por las filas y coolumnas no borradas.
        return residuo;
    }

    /**
     * Devuelve el residuo de la columna de la matriz.
     *
     */
    private double msrColumna(double[][] a, int l) {
        double mediaTotal = 0;
        double mediaColumnas = 0;
        double mediaFilas[] = new double[a[0].length];
        int i, j;
        //Calculamos las distintas medias necesarias.
        Arrays.fill(mediaFilas, 0);
        for (i = 0; i < a.length; i++) {
            if (C[i]) {
                for (j = 0; j < a[0].length; j++) {
                    if (F[j]) {
                        mediaTotal += a[i][j];
                        mediaFilas[j] += a[i][j];
                    }
                }
            }
        }
        for (j = 0; j < a[0].length; j++) {
            if (F[j]) {
                mediaColumnas += a[l][j];
            }
        }
        mediaTotal = mediaTotal / (numGen() * numCon());
        for (j = 0; j < mediaFilas.length; j++) {
            if (F[j]) {
                mediaFilas[j] = mediaFilas[j] / numCon();
            }
        }
        mediaColumnas = mediaColumnas / numGen();
        double residuo = 0;
        for (j = 0; j < a[0].length; j++) {
            if (F[j]) {
                residuo += (a[l][j] - mediaFilas[j] - mediaColumnas + mediaTotal) * (a[l][j] - mediaFilas[j] - mediaColumnas + mediaTotal);
            }
        }
        residuo = residuo / numGen();//Dividimos el residuop por las filas y coolumnas no borradas.
        return residuo;
    }

    private double msrFila(double[][] a, int k) {
        //Calculamos las distintas medias necesarias.
        double mediaTotal = 0;
        double mediaColumnas[] = new double[a.length];
        double mediaFilas = 0;
        int i, j;
        //Calculamos las distintas medias necesarias.
        Arrays.fill(mediaColumnas, 0);
        for (i = 0; i < a.length; i++) {
            if (C[i]) {
                mediaFilas += a[i][k];
            }
        }

        for (j = 0; j < a[0].length; j++) {
            if (F[j]) {
                for (i = 0; i < a.length; i++) {
                    if (C[i]) {
                        mediaColumnas[i] += a[i][j];
                        mediaTotal += a[i][j];
                    }
                }
            }
        }
        mediaTotal = mediaTotal / (numGen() * numCon());
        mediaFilas = mediaFilas / numCon();
        for (i = 0; i < mediaColumnas.length; i++) {
            if (C[i]) {
                mediaColumnas[i] = mediaColumnas[i] / numGen();
            }
        }
        double residuo = 0;
        for (i = 0; i < a.length; i++) {
            if (C[i]) {
                residuo += (a[i][k] - mediaFilas - mediaColumnas[i] + mediaTotal) * (a[i][k] - mediaFilas - mediaColumnas[i] + mediaTotal);
            }
        }
        residuo = residuo / numCon();
        return residuo;
    }

    /**
     * El borrado consiste en poner 0 en todas las celdas de la fila o columna
     * que se va a borrar.
     */
    private void borradoMultiple(double[][] a) {
        int cont = 0;
        int contprev = cont;
        double residuoMatriz = msrMatriz(a);
        boolean borrado = true;
        while ((residuoMatriz > umb) && (borrado)) {
            if (numGen() > 100) {
                for (int j = 0; j < a[0].length; j++) {
                    if (F[j]) {
                        if ((msrFila(a, j) > alf * residuoMatriz)) {
                            cont++;
                            F[j] = false;
                        }
                    }
                }
            }
            residuoMatriz = msrMatriz(a);
            if (numCon() > 100) {
                for (int i = 0; i < a.length; i++) {
                    if (C[i]) {
                        if ((msrColumna(a, i) > alf * residuoMatriz)) {
                            cont++;
                            C[i] = false;
                        }
                    }
                }
            }
            if (contprev == cont) {
                borrado = false;
            } else {
                contprev = cont;
                residuoMatriz = msrMatriz(a);
            }
        }
    }

    /**
     * Sigue el mismo sistema de borrado que el borrado multiple.
     */
    private void borradoSimple(double[][] b) {
        double residuoMatriz = msrMatriz(b);
        int i;
        int j;
        double max;
        int iFiMax = 0;
        int iCoMax = 0;
        boolean esFila = false;
        double t1[] = new double[F.length];
        double t2[] = new double[C.length];
        Arrays.fill(t2, 0);
        Arrays.fill(t1, 0);
        for (i = 0; i < C.length; i++) {
            if (C[i]) {
                t2[i] = msrColumna(b, i);
            }
        }
        for (j = 0; j < F.length; j++) {
            if (F[j]) {
                t1[j] = msrFila(b, j);
            }
        }
        /*Creamos 2 tablas auxiliares que donde guardamos los residuos de cada fila y cada columna*/
        while (residuoMatriz >= umb) {
            max = 0;
            iFiMax = 0;
            iCoMax = 0;
            if (esFila) {
                for (i = 0; i < C.length; i++) {
                    if (C[i]) {
                        t2[i] = msrColumna(b, i);
                    }
                }
            } else {
                for (j = 0; j < F.length; j++) {
                    if (F[j]) {
                        t1[j] = msrFila(b, j);
                    }
                }

            }
            /*Le vamos incluyendo valores*/
            for (j = 0; j < t1.length; j++) {
                if (t1[j] > max) {
                    max = t1[j];
                    iFiMax = j;
                    esFila = true;
                }
            }
            /*Guardamos el residuo de la fila mas alto y la fila que es y si es fila*/
            for (i = 0; i < t2.length; i++) {
                if (t2[i] > max) {
                    max = t2[i];
                    iCoMax = i;
                    esFila = false;
                }
            }
            /*Guardamos el residuo de la columna mas alto (si es mas alto que el de la fila)y la columna*/
            if (esFila) {
                F[iFiMax] = false;
                t1[iFiMax] = 0;
            } else {
                C[iCoMax] = false;
                t2[iCoMax] = 0;
            }
            /*Borramos la fila o columna que tenga la fila o columna con el residuo mas alto, en caso de que sean iguales se borra la fila*/
            residuoMatriz = msrMatriz(b);
        }
    }

    /**
     * A�ade filas y columnas para completar una matriz de la misma dimension
     * que la original;
     */
    private void adicion(double[][] c, double[][] org) {
        double residuoMatriz = msrMatriz(c);
        int i, j;
        /*Si una columna ha sido borrada, pero algunos
         * elementos pertenece a filas no borradas,
         * se a�ade esos elementos, que se obtiene
         * de la matriz original, activando esa columna
         * como valida para el bicluster si su residuo es
         * menor o igual al de la matriz.
         */
        for (i = 0; i < C.length; i++) {
            if (!C[i]) {
                if (msrColumna(c, i) <= residuoMatriz) {
                    C[i] = true;
                }
            }
        }
        residuoMatriz = msrMatriz(c);
        double[][] aux = new double[C.length][F.length];
        /*Si una fila ha sido borrada, pero algunos
         * elementos pertenece a columnas no borradas,
         * se a�aden esos elementos, que se obtiene
         * de la matriz original, activando esa fila
         * como valida para el bicluster si su residuo es
         * meno o igual al de la matriz.
         */
        for (j = 0; j < F.length; j++) {
            if (!F[j]) {
                if (msrFila(c, j) <= residuoMatriz) {
                    F[j] = true;
                }
            }
        }
        /* Si aun no han sido a�adido todas las filas,
         * siguiendo el criterio anterior,
         * se a�aden los inversos de los elementos de la
         * matriz original.
         */

        for (i = 0; i <= (C.length) / 2; i++) {
            for (j = 0; j < F.length; j++) {
                aux[i][j] = org[(C.length) - i - 1][j];
            }
        }
        double[] vecaux = new double[C.length];
        for (j = 0; j < F.length; j++) {
            if (!F[j]) {
                Arrays.fill(vecaux, 0);
                for (i = 0; i < C.length; i++) {
                    if (C[i]) {
                        vecaux[i] = c[i][j];
                        c[i][j] = aux[i][j];
                    }
                }
                if (msrFila(c, j) <= residuoMatriz) {
                    F[j] = true;
                } else {
                    for (i = 0; i < C.length; i++) {
                        if (C[i]) {
                            c[i][j] = vecaux[i];
                        }
                    }
                }
            }
        }
    }

    /**
     * Sustituye los elemntos del bicluster por numeros aleatorios.
     */
    private void sustitucion(double[][] org) {
        for (int j = 0; j < F.length; j++) {
            if (F[j]) {
                for (int i = 0; i < C.length; i++) {
                    /*Si el elemento de la matriz original
                     * pertenece ha un bicluster se sustituye
                     * por un numero generado aleatoriamente.
                     */
                    if (C[i]) {
                        org[i][j] = numAlea();
                    }
                }
            }
        }
    }


    //METODOS PUBLICOS.


    /**
     * Ejecuta el algoritmo
     */
    public void ejecucion() {
        //Crear directorio de salida
        dir = direcSal();
        //Creamos los vectores donde almacenamos el msr y rowVariance de cada Bicluster
        msrF = new double[gen];
        rowVar = new double[gen];
        genCon = new int[2][gen];
        Arrays.fill(F, true);
        Arrays.fill(C, true);

        for (int n = 0; n < gen; n++) {
            //				Copiar A
            double[][] b = (double[][]) a.clone();
            borradoMultiple(b);
            borradoSimple(b);
            adicion(b, a);

            //b es el bicluster: pasar datos y escribimos en el fichero de salida
            msrF[n] = msrMatriz(b);
            rowVar[n] = rowVar(b);
            genCon[0][n] = numGen();
            genCon[1][n] = numCon();
            salidaTxt(n);
            salidaGraf(n, b);
            psTxt(n, numGen(), maxgrf(b), mingrf(b));
            crearScript(n);
            //sustituir en A los valores de b por aleatorios
            sustitucion(a);
            //Inicializar de nuevo F y C
            Arrays.fill(F, true);
            Arrays.fill(C, true);
        }
        finAnalisis();
    }


    /**
     * @param gen2
     */
    private void crearScript(int n) {
        File f = new File("Executions\\" + dir + "\\" + "psbicl" + (n + 1) + ".txt");
        File f2 = new File("gnuplot\\bin\\pgnuplot.exe");
        try {
            bw = new BufferedWriter(new FileWriter("Executions\\" + dir + "\\Generator.bat", true));
            salida = new PrintWriter(bw);
            salida.println(f2.getAbsolutePath() + " " + f.getAbsolutePath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            salida.close();
        }
    }

    @SuppressWarnings("deprecation")
    private void finAnalisis() {
        try {
            bw = new BufferedWriter(new FileWriter("Executions\\" + dir + "\\" + (dir + ".txt"), true));
            tw = new BufferedWriter(new FileWriter("Executions\\" + dir + "\\" + ("GeneralData.txt"), true));
            salida = new PrintWriter(bw);
            gendat = new PrintWriter(tw);
            //Identificacion.
            salida.println("\n*********************");
            salida.println("    Resltados");
            salida.println("*********************");
            Date h = new Date();
            String fin = h.getDate() + "/" + (h.getMonth() + 1) + "/" + (h.getYear() + 1900) + " Hour: " + h.getHours() + ":" + h.getMinutes();
            salida.print("Hour of analysis end: ");
            salida.println(fin);

            double medGen = 0, medCon = 0, mMsr = 0, volumen = 0, mV = 0, pc = 0, pg = 0;
            for (int i = 0; i < gen; i++) {
                medGen += genCon[0][i];
                medCon += genCon[1][i];
                mMsr += msrF[i];
                mV += rowVar[i];
            }
            volumen = medGen * medCon;
            pc = ((medCon / gen) * 100) / m_Data.numInstances();
            pg = ((medGen / gen) * 100) / m_Data.numAttributes();

            volumen /= gen;
            medGen /= gen;
            medCon /= gen;
            mMsr /= gen;
            mV /= gen;


            salida.print("Mean of Genes: ");
            salida.println(medGen);
            salida.print("Mean of Conditions: ");
            salida.println(medCon);

            salida.print("Mean of MSR: ");
            salida.println(mMsr);

            salida.print("Mean of Volume: ");
            salida.println(volumen);

            salida.print("Mean of Row Variance: ");
            salida.println(mV);

            salida.print("Percentage of covered genes: ");
            salida.println(pg + "%");

            salida.print("Percentage of covered conditions: ");
            salida.println(pc + "%");
            gendat.println(gen + " " + mMsr + " " + mV + " " + medGen + " " + medCon);
        } catch (java.io.IOException e) {
        } finally {
            salida.close();
            gendat.close();
        }
    }

    /**
     * remplaza en el m_Data los valores perdidos.
     */
    public void replaceMissingValues() throws Exception {
        Instance i_aux = null;
        m_FMissing.setInputFormat(m_Data);
        for (int i = 0; i < m_Data.numInstances(); i++) {
            m_FMissing.input(m_Data.instance(i));
        }
        m_FMissing.batchFinished();
        m_Data.delete();
        m_FMissing.getOutputFormat();
        while ((i_aux = m_FMissing.output()) != null) {
            m_Data.add(i_aux);
        }
    }

    /**
     * Metodo por el cual se coge la Instancia y se pasan los datos
     * a una matriz de double teniendo en las filas los genes y en
     * las columnas las condiciones, ademas en el caso de q sea -1
     * introducimos un numero aleatorio dentro del rango
     */
    public void cargarMatriz() {
        int numGenes, numCond;
        numGenes = m_Data.numAttributes();
        numCond = m_Data.numInstances();

        a = new double[numCond][numGenes];
        for (int i = 0; i < numCond; i++) {
            double aux[] = m_Data.instance(i).toDoubleArray();
            for (int j = 0; j < numGenes; j++) {
                if (aux[j] == -1)
                    a[i][j] = numAlea();
                else
                    a[i][j] = aux[j];
            }
        }
        C = new boolean[a.length];
        F = new boolean[a[0].length];
        Arrays.fill(F, true);
        Arrays.fill(C, true);
    }

    /**
     * Dada la direcci�n del archivo *.arff, carga todos los datos
     * en una Instance
     *
     * @param fInput Direcci�n del archivo.
     */
    public void leeFichero(String fInput) {
        try {
            FileReader reader = new FileReader(fInput);
            m_Data = new Instances(reader);
            if (m_Data.classIndex() > 0) {
                m_Data.setClassIndex(m_Data.numAttributes() - 1);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

