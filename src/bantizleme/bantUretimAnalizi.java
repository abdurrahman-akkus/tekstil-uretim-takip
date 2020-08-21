/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bantizleme;

import bantizleme.destek.baglanti;
import bantizleme.destek.islevseller;
import bantizleme.destek.kutuk;
import static bantizleme.raporDerleyicisi.uretimRaporuXLSX;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author CI
 */
public class bantUretimAnalizi {
    ResultSet rs;
    Connection con;
    public ResultSet opAnalizi(String basTarih, String sonTarih){
        baglanti bagAnahtar = new baglanti();
        String sorgu;
        try {
            
            sorgu = "SELECT model, CAST(AVG(operatorAdedi) AS unsigned) FROM saatliktakip_depo "
                    + "WHERE operatorAdedi!='0' AND tarih BETWEEN '" + basTarih + "' AND '"+sonTarih+"' "
                    + "GROUP BY model ORDER BY model ASC;";
           
            con = bagAnahtar.ac();
            Statement st = (Statement) con.createStatement();
            rs = st.executeQuery(sorgu);

        } catch (SQLException e) {
            kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, e);
            kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+e,Level.SEVERE);
            JOptionPane.showConfirmDialog(null, "Bağlantı Başarısız", "MySQL Bağlantısı", JOptionPane.PLAIN_MESSAGE);
        }
        return rs;
    }
    
        public ResultSet saatlikAdetAnalizi(String basTarih, String sonTarih){
        baglanti bagAnahtar = new baglanti();
        String sorgu;
        try {
            
            sorgu = "SELECT  model as md, MIN(dikimAdet), Max(dikimAdet), ROUND(AVG(dikimAdet/((SUBSTRING(donem,7,2))-(SUBSTRING(donem,1,2))+\n" +
            "((SUBSTRING(donem,10,2))-(SUBSTRING(donem,4,2)))/60))) AS dAort FROM saatliktakip_depo SD WHERE dikimAdet>(\n" +
            "SELECT (ROUND(AVG(dikimAdet))/1.3) FROM saatliktakip_depo SDA WHERE SDA.model=SD.model GROUP BY model) \n" +
            "AND tarih BETWEEN '"+basTarih+"' AND '"+sonTarih+"' GROUP BY model;";
            System.out.println(sorgu);
            con = bagAnahtar.ac();
            Statement st = (Statement) con.createStatement();
            rs = st.executeQuery(sorgu);

        } catch (SQLException e) {
            kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, e);
            kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+e,Level.SEVERE);
            JOptionPane.showConfirmDialog(null, "Bağlantı Başarısız", "MySQL Bağlantısı", JOptionPane.PLAIN_MESSAGE);
        }
        return rs;
    }
    
    public void opRaporla(String basTarih, String bitTarih) throws JRException{
        try {
            baglanti bagAnahtar = new baglanti();
            islevseller islevAnahtar=new islevseller();
            
            String yol = islevAnahtar.ayarAl("raporKayitYeri");
            JFileChooser dosyaSec = new JFileChooser();
            if (yol.isEmpty()) {
            dosyaSec.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            dosyaSec.showDialog(dosyaSec, "SEÇ");
            yol = String.valueOf(dosyaSec.getSelectedFile().getAbsolutePath());
            }
            
            
            con = bagAnahtar.ac();
            // jrxml jasper dosyasına dönüştürülüyor.
            JasperReport opAdetiRaporu = JasperCompileManager.compileReport(islevAnahtar.ayarAl("raporSablonYeri")+"/opAdetiRaporu.jrxml");
  
            // Raporlar için Parametreler
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("basTarih", basTarih);
            parameters.put("bitTarih", bitTarih);

            JasperPrint jasperPrintOp = JasperFillManager.fillReport(opAdetiRaporu, parameters, con);
            
            File ciktiDizini = new File(yol);
            ciktiDizini.mkdirs();

            //KALICI RAPORLAR OLUŞTURULUYOR.
            
            List<JasperPrint> jasperPrints = new ArrayList<>();
            jasperPrints.add(0, jasperPrintOp);
            
            String opAdetiRaporuPDF,opAdetiRaporuXLSX;
            // PDF çıktısı al.
            
                opAdetiRaporuPDF = ciktiDizini.getAbsolutePath() + "/Operatör_Adedi_Raporu_" + basTarih+"_"+bitTarih + ".pdf";
                JRPdfExporter PDFexporter = new JRPdfExporter();
                PDFexporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                PDFexporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, opAdetiRaporuPDF);
                PDFexporter.exportReport();
            //    kullanilacakRapor=opAdetiRaporuPDF;
            
            
            
                // XLSX çıktısı al.
                opAdetiRaporuXLSX = ciktiDizini.getAbsolutePath() + "/Operatör_Adedi_Raporu_" + basTarih+"_"+bitTarih + ".xlsx";
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, opAdetiRaporuXLSX);
                exporter.exportReport();
            //    kullanilacakRapor=uretimRaporuXLSX;}
        } catch (SQLException ex) {
            Logger.getLogger(bantUretimAnalizi.class.getName()).log(Level.SEVERE, null, ex);
        }}
    public void saatlikAdetRaporla(String basTarih, String bitTarih) throws JRException{
        try {
            baglanti bagAnahtar = new baglanti();
            islevseller islevAnahtar=new islevseller();
            
            String yol = islevAnahtar.ayarAl("raporKayitYeri");
            JFileChooser dosyaSec = new JFileChooser();
            if (yol.isEmpty()) {
            dosyaSec.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            dosyaSec.showDialog(dosyaSec, "SEÇ");
            yol = String.valueOf(dosyaSec.getSelectedFile().getAbsolutePath());
            }
            
            
            con = bagAnahtar.ac();
            // jrxml jasper dosyasına dönüştürülüyor.
            JasperReport opAdetiRaporu = JasperCompileManager.compileReport(islevAnahtar.ayarAl("raporSablonYeri")+"/modelSaatlikAdetRaporu.jrxml");
  
            // Raporlar için Parametreler
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("basTarih", basTarih);
            parameters.put("bitTarih", bitTarih);

            JasperPrint jasperPrintOp = JasperFillManager.fillReport(opAdetiRaporu, parameters, con);
            
            File ciktiDizini = new File(yol);
            ciktiDizini.mkdirs();

            //KALICI RAPORLAR OLUŞTURULUYOR.
            
            List<JasperPrint> jasperPrints = new ArrayList<>();
            jasperPrints.add(0, jasperPrintOp);
            
            String opAdetiRaporuPDF,opAdetiRaporuXLSX;
            // PDF çıktısı al.
            
                opAdetiRaporuPDF = ciktiDizini.getAbsolutePath() + "/Saatlik_Adet_Raporu_" + basTarih+"_"+bitTarih + ".pdf";
                JRPdfExporter PDFexporter = new JRPdfExporter();
                PDFexporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                PDFexporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, opAdetiRaporuPDF);
                PDFexporter.exportReport();
            //    kullanilacakRapor=opAdetiRaporuPDF;
            
            
            
                // XLSX çıktısı al.
                opAdetiRaporuXLSX = ciktiDizini.getAbsolutePath() + "/Saatlik_Adet_Raporu_"  + basTarih+"_"+bitTarih + ".xlsx";
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, opAdetiRaporuXLSX);
                exporter.exportReport();
            //    kullanilacakRapor=uretimRaporuXLSX;}
        } catch (SQLException ex) {
            Logger.getLogger(bantUretimAnalizi.class.getName()).log(Level.SEVERE, null, ex);
        }}
    public void kisiBasiAdet(String basTarih, String bitTarih) throws JRException{
        try {
            baglanti bagAnahtar = new baglanti();
            islevseller islevAnahtar=new islevseller();
            
            String yol = islevAnahtar.ayarAl("raporKayitYeri");
            JFileChooser dosyaSec = new JFileChooser();
            if (yol.isEmpty()) {
            dosyaSec.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            dosyaSec.showDialog(dosyaSec, "SEÇ");
            yol = String.valueOf(dosyaSec.getSelectedFile().getAbsolutePath());
            }
            
            
            con = bagAnahtar.ac();
            // jrxml jasper dosyasına dönüştürülüyor.
            JasperReport opAdetiRaporu = JasperCompileManager.compileReport(islevAnahtar.ayarAl("raporSablonYeri")+"/kisiBasiAdet.jrxml");
  
            // Raporlar için Parametreler
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("basTarih", basTarih);
            parameters.put("bitTarih", bitTarih);

            JasperPrint jasperPrintOp = JasperFillManager.fillReport(opAdetiRaporu, parameters, con);
            
            File ciktiDizini = new File(yol);
            ciktiDizini.mkdirs();

            //KALICI RAPORLAR OLUŞTURULUYOR.
            
            List<JasperPrint> jasperPrints = new ArrayList<>();
            jasperPrints.add(0, jasperPrintOp);
            
            String opAdetiRaporuPDF,opAdetiRaporuXLSX;
            // PDF çıktısı al.
            
                opAdetiRaporuPDF = ciktiDizini.getAbsolutePath() + "/Kişi_Başı_Adet_Raporu_" + basTarih+"_"+bitTarih + ".pdf";
                JRPdfExporter PDFexporter = new JRPdfExporter();
                PDFexporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                PDFexporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, opAdetiRaporuPDF);
                PDFexporter.exportReport();
            //    kullanilacakRapor=opAdetiRaporuPDF;
            
            
            
                // XLSX çıktısı al.
                opAdetiRaporuXLSX = ciktiDizini.getAbsolutePath() + "/Kişi_Başı_Adet_Raporu_" + basTarih+"_"+bitTarih + ".xlsx";
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, opAdetiRaporuXLSX);
                exporter.exportReport();
            //    kullanilacakRapor=uretimRaporuXLSX;}
        } catch (SQLException ex) {
            Logger.getLogger(bantUretimAnalizi.class.getName()).log(Level.SEVERE, null, ex);
        }}
    public void grafikHazirla(String tarih,String sqlTablosu) throws JRException{
        try {
            
            baglanti bagAnahtar = new baglanti();
            islevseller islevAnahtar=new islevseller();
            
            String yol = islevAnahtar.ayarAl("raporKayitYeri");
            JFileChooser dosyaSec = new JFileChooser();
            if (yol.isEmpty()) {
            dosyaSec.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            dosyaSec.showDialog(dosyaSec, "SEÇ");
            yol = String.valueOf(dosyaSec.getSelectedFile().getAbsolutePath());
            }
            
            
            con = bagAnahtar.ac();
            // jrxml jasper dosyasına dönüştürülüyor.
            JasperReport grafik = JasperCompileManager.compileReport(islevAnahtar.ayarAl("raporSablonYeri")+"/dikimGrafikleri.jrxml");
  
            // Raporlar için Parametreler
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tarih", tarih);
            parameters.put("sqlTablosu", sqlTablosu);
            

            JasperPrint jasperPrintOp = JasperFillManager.fillReport(grafik, parameters, con);
            
            File ciktiDizini = new File(yol);
            ciktiDizini.mkdirs();

            //KALICI RAPORLAR OLUŞTURULUYOR.
            
            List<JasperPrint> jasperPrints = new ArrayList<>();
            jasperPrints.add(0, jasperPrintOp);
            
            String opAdetiRaporuPDF,opAdetiRaporuXLSX;
            // PDF çıktısı al.
            
                opAdetiRaporuPDF = ciktiDizini.getAbsolutePath() + "/Dikim_Grafikleri" + tarih+ ".pdf";
                JRPdfExporter PDFexporter = new JRPdfExporter();
                PDFexporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                PDFexporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, opAdetiRaporuPDF);
                PDFexporter.exportReport();
            //    kullanilacakRapor=opAdetiRaporuPDF;
            
            
            
                // XLSX çıktısı al.
                opAdetiRaporuXLSX = ciktiDizini.getAbsolutePath() + "/Dikim_Grafikleri" + tarih+ ".xlsx";
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, opAdetiRaporuXLSX);
                exporter.exportReport();
            //    kullanilacakRapor=uretimRaporuXLSX;}
        } catch (SQLException ex) {
            Logger.getLogger(bantUretimAnalizi.class.getName()).log(Level.SEVERE, null, ex);
        }}
   
}
