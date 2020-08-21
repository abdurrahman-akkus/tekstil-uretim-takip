/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bantizleme.destek;

import bantizleme.raporDerleyicisi;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author CI
 */
public class islevseller {

    /**
     *
     * @param ayar
     * @return ayar değerini .properties dosyasından alarak çeker
     */
    public String ayarAl(String ayar) {
        try {String yol = new File("ayarlar.properties").getAbsolutePath();
        //.properties dosyasına ulaşmak için anaktar oluşturuluyor.
        Properties prop = new Properties();
        
        
            InputStream input = new FileInputStream(yol);
            // load a properties file
            prop.load(input);
            ayar = prop.getProperty(ayar);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage()+ "\nLütfen belirtilen adreste doğru dosyanın bulunduğuna emin olunuz ve programı yeniden çalıştırınız!", "HATA", JOptionPane.CANCEL_OPTION);
            System.exit(0);
            
        }
        return ayar;
    }

    /**
     *
     * @param ayar
     * @return
     */
    public String varsayilanAyarAl(String ayar) {
        try {String yol = new File("varsayilan.properties").getAbsolutePath();
        //.properties dosyasına ulaşmak için anaktar oluşturuluyor.
        Properties prop = new Properties();
        
        
            InputStream input = new FileInputStream(yol);
            // load a properties file
            prop.load(input);
            ayar = prop.getProperty(ayar);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage()+ "\nLütfen belirtilen adreste doğru dosyanın bulunduğuna emin olunuz ve programı yeniden çalıştırınız!", "HATA", JOptionPane.CANCEL_OPTION);
            System.exit(0);
            new bantizleme.kurtarma(ex);
            //System.exit(0);
        } 
        return ayar;
    }

    /**
     *
     * @param anahtar Değere ulaştıran anahtar kelime. Anahtar yoksa anahtar oluşturularak yeni değer girilir.
     * @param deger Ayarlanan değer. 
     */
    public void ayarla(String anahtar, String deger) {
        String yol = new File("ayarlar.properties").getAbsolutePath();
        try {
            //Kayıt Yeri Belirle
            PropertiesConfiguration conf = new PropertiesConfiguration(yol);
            conf.setProperty(anahtar, deger);
            conf.save();
        } catch (ConfigurationException ex) {
            Logger.getLogger(raporDerleyicisi.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }
}
