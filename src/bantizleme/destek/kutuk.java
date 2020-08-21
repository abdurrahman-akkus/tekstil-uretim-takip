/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bantizleme.destek;

import java.io.IOException;
import java.util.logging.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CI
 */
public class kutuk {

    public void kutukTut(String mesaj,Level lv) {
        islevseller islevAnahtar = new islevseller();
        Logger kutuk = Logger.getLogger("MyLog");
        FileHandler fh;

        try {
            // This block configure the logger with handler and formatter  
            fh = new FileHandler(islevAnahtar.ayarAl("raporKayitYeri")+"/logs/kutuk.log");
            kutuk.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages  
            kutuk.log(lv, mesaj);
            

        } catch (SecurityException | IOException e) {
            JOptionPane.showMessageDialog(null, "log dosyası hatası: "+e, "HATA",JOptionPane.ERROR_MESSAGE);
        }
    }
}
