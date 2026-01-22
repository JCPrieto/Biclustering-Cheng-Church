/*
 * Created on 08-jun-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bicl_CC;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @author practica
 * <p>
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InFilter extends FileFilter {
    final static String arff = "arff";
    final static String txt = "txt";
    final static String dat = "dat";

    // Acepta todos los directorios y los txt, arff y dat.
    public boolean accept(File f) {

        if (f.isDirectory()) {
            return true;
        }

        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            String extension = s.substring(i + 1).toLowerCase();
            if (arff.equals(extension) ||
                    txt.equals(extension) ||
                    dat.equals(extension)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    // Descripcion del filtro
    public String getDescription() {
        return "*.arff, *.txt, *.dat";
    }
}
