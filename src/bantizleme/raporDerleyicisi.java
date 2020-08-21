/*
 * Copyright (C) 2019 Abdurrahman AKKUŞ <iletisim@algoritimbilisim.com>
 * Bu yazılımın tüm hakları Algoritim Bilişim'e aittir.
 */
package bantizleme;

import bantizleme.destek.baglanti;
import bantizleme.destek.islevseller;
import java.awt.Desktop;
import java.io.*;
import java.sql.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author Abdurrahman AKKUŞ <iletisim@algoritimbilisim.com>
 */
public class raporDerleyicisi {

    static String uretimRaporuPDF, uretimRaporuXLSX, kullanilacakRapor;

    /**
     *
     * @param tarih Rapor tarihi
     * @param sqlTabloAdi Hangi DB Tablosundan bilgi çekileceğini gösterir
     * @param yazdirsinMi Yazdırma işlemi yapılacaksa true yazdırılmayacaksa
     * false yapılır
     * @throws JRException
     */
    public void raporla(String tarih, String sqlTabloAdi, boolean yazdirsinMi) throws JRException {
        veriGirisi vgAnahtar = new veriGirisi();
        baglanti bagAnahtar = new baglanti();
        islevseller islevAnahtar = new islevseller();

        JFileChooser dosyaSec = new JFileChooser();

        String yol = islevAnahtar.ayarAl("raporKayitYeri");
        if (yol.isEmpty()) {
            dosyaSec.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            dosyaSec.showDialog(dosyaSec, "SEÇ");
            yol = String.valueOf(dosyaSec.getSelectedFile().getAbsolutePath());
        }

        tarih = tarih.replaceAll("-", "_");
        try {
            Connection con = bagAnahtar.ac();
            // jrxml jasper dosyasına dönüştürülüyor.
            JasperReport jasperDikimRaporu = JasperCompileManager.compileReport(islevAnahtar.ayarAl("raporSablonYeri") + "/dikimRaporu.jrxml");
            JasperReport jasperKoliRaporu = JasperCompileManager.compileReport(islevAnahtar.ayarAl("raporSablonYeri") + "/koliRaporu.jrxml");
            // Raporlar için Parametreler
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("tarih", tarih);
            parameters.put("sqlTablosu", sqlTabloAdi);

            JasperPrint jasperPrintDikim = JasperFillManager.fillReport(jasperDikimRaporu, parameters, con);
            JasperPrint jasperPrintKoli = JasperFillManager.fillReport(jasperKoliRaporu, parameters, con);

            File ciktiDizini = new File(yol);
            ciktiDizini.mkdirs();

            //KALICI RAPORLAR OLUŞTURULUYOR.
            List<JasperPrint> jasperPrints = new ArrayList<>();
            jasperPrints.add(0, jasperPrintDikim);
            jasperPrints.add(1, jasperPrintKoli);
            // PDF çıktısı al.
            if (".pdf".equals(islevAnahtar.ayarAl("ekinFormati"))) {
                uretimRaporuPDF = ciktiDizini.getAbsolutePath() + "/" + islevAnahtar.ayarAl("raporAdi") + "_" + tarih + ".pdf";
                JRPdfExporter PDFexporter = new JRPdfExporter();
                PDFexporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                PDFexporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, uretimRaporuPDF);
                PDFexporter.exportReport();
                kullanilacakRapor = uretimRaporuPDF;
            }

            if (".xlsx".equals(islevAnahtar.ayarAl("ekinFormati"))) {
                // XLSX çıktısı al.
                uretimRaporuXLSX = ciktiDizini.getAbsolutePath() + "/" + islevAnahtar.ayarAl("raporAdi") + "_" + tarih + ".xlsx";
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jasperPrints);
                exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, uretimRaporuXLSX);
                exporter.exportReport();
                kullanilacakRapor = uretimRaporuXLSX;
            }

            //YAZDIRMA SEÇENEKLERİ
            String yazdirmaSecenegi = islevAnahtar.ayarAl("raporYazdirmaSecenegi");
            if (yazdirsinMi) {
                switch (yazdirmaSecenegi) {
                    case "göster":
                        if (Desktop.isDesktopSupported()) {
                            try {
                                File myFile = new File(kullanilacakRapor);
                                Desktop.getDesktop().open(myFile);
                            } catch (IOException ex) {

                            }
                        }

                        break;
                    case "seç":
                        //YAZDIR.
                        File myFile = new File(kullanilacakRapor);
                        Desktop.getDesktop().print(myFile);
//                        JasperPrintManager.printReport(kullanilacakRapor, true);
//                        JasperPrintManager.printReport(jasperPrintKoli, true);
                        break;
                    case "sadeceYazdır":
                        //YAZDIR.
                        JasperPrintManager.printReport(jasperPrintDikim, false);
                        JasperPrintManager.printReport(jasperPrintKoli, false);

                        break;
                }
            }

            //Kayıt Yeri Belirle
            islevAnahtar.ayarla("raporKayitYeri", yol);

        } catch (IOException | SQLException | JRException ex) {
            JOptionPane.showMessageDialog(vgAnahtar, "HATA: " + ex + "\nDosyalarınızı Kapatarak Tekrar Deneyiniz",
                    "RAPORLAMA HATASI", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(raporDerleyicisi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
