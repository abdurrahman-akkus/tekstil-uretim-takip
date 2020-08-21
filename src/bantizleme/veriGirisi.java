/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bantizleme;

import bantizleme.destek.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableColumnModel;
import javax.swing.text.*;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Abdurrahman AKKUŞ
 */
public final class veriGirisi extends javax.swing.JFrame {

    Connection con;
    String sorgu;
    Integer secilenSatirIndexDonem, secilenSatirIndexSef, secilenSatirIndexDuzelme, secilenSatirIndexEposta, secilenSatirIndexHedef;
    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form veriGirisi
     */
    public veriGirisi() {
        initComponents();

        //JFrame'in ikonunu değiştir
        ImageIcon img = new ImageIcon("C:\\bantIzleme_v1.2\\src\\resimler\\raporla3.png");
        this.setIconImage(img.getImage());
        //saatliktakip tablosundan saatliktakip_depo tablosuna veriler aktarıldı.
        tablolarArasiVeriAktar veriAktarAnahtar = new tablolarArasiVeriAktar();
        veriAktarAnahtar.aktarma();
        ekPaneli.setVisible(false);
        sdFormat.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
        //Tarih Seçicisinin başlangıç değerini "bugün" olarak ayarladık.
        java.util.Date tarih = new java.util.Date();
        tarihDC.setDate(tarih);
        tarihDC1.setDate(tarih);
        tarihDC2.setDate(tarih);

        donemModelSecCbxDoldur();
        donemComboBoxDoldur();
        sefComboBoxDoldur(sefCBx1);
        sefComboBoxDoldur(sefCBx2);
        sefComboBoxDoldur(sefCBx3);
        sefComboBoxDoldur(sefCBx4);
        sefComboBoxDoldur(sefCBx5);
        sefComboBoxDoldur(sefCBx6);
        sefComboBoxDoldur(sefCBx7);
        sefComboBoxDoldur(sefCBx8);
        sefComboBoxDoldur(sefCBx9);
        sefComboBoxDoldur(sefCBx10);
        sefComboBoxDoldur(sefCBx11);
        sefComboBoxDoldur(sefCBx12);
        sefComboBoxDoldur(sefCBx13);

        //Donem Tipi etiketini oluşturur.
        donemTipiEtiketi.setText("LİSTE:" + baslangicDonemTipi());

        varsayilanAl();
        //DONEM GİRİŞİ
        TabloDoldur(donemTablosu, "donem", "SIRA", "BAŞLANGIÇ", "BİTİŞ", "sira", "donemBasla", "donemBit");
        final ListSelectionModel lsmodel = donemTablosu.getSelectionModel();
        lsmodel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsmodel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!lsmodel.isSelectionEmpty()) {
                    secilenSatirIndexDonem = lsmodel.getMinSelectionIndex();
                }

            }
        });

        //ŞEF TABLOSUNU DOLDUR
        TabloDoldur(sefTablosu, "bantsefleri", "BANT ŞEFİNİN ADI", null, null, "sefAdi", null, null);
        final ListSelectionModel lsmodel2 = sefTablosu.getSelectionModel();
        lsmodel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsmodel2.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!lsmodel2.isSelectionEmpty()) {
                    secilenSatirIndexSef = lsmodel2.getMinSelectionIndex();
                }

            }
        });

        //Duzeltme Tablosunu Doldur
        duzeltmeTablosuDoldur();
        final ListSelectionModel lsmodel3 = duzeltmeTablosu.getSelectionModel();
        lsmodel3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsmodel3.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (lsmodel3.isSelectionEmpty()) {
                    bantNoDzl.setText("");
                    tarihDzl.setText("");
                    donemDzl.setText("");
                    modelDzl.setText("");
                    opSayisiDzl.setText("");
                    dikimAdetDzl.setText("");
                    koliAdetDzl.setText("");
                    dikimHedefDzl.setText("");
                    koliHedefDzl.setText("");
                    bantSefiDzl.setText("");
                    duyuruDzl.setText("");
                } else {
                    secilenSatirIndexDuzelme = lsmodel3.getMinSelectionIndex();
                    bantNoDzl.setText((String) duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 1));
                    tarihDzl.setText(String.valueOf(duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 2)));
                    donemDzl.setText((String) duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 3));
                    modelDzl.setText((String) duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 4));
                    opSayisiDzl.setText(String.valueOf(duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 5)));
                    dikimAdetDzl.setText(String.valueOf(duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 6)));
                    koliAdetDzl.setText(String.valueOf(duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 7)));
                    dikimHedefDzl.setText(String.valueOf(duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 8)));
                    koliHedefDzl.setText(String.valueOf(duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 9)));
                    bantSefiDzl.setText((String) duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 10));
                    duyuruDzl.setText((String) duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 11));
                }
            }
        });

        //E-POSTA TABLOSUNU DOLDUR
        TabloDoldur(epostaTablosu, "mail_listesi", "E-POSTA SAHİBİ", "E-POSTA ADRESİ", null, "mailSahibi", "mailAdresi", null);
        final ListSelectionModel lsmodel4 = epostaTablosu.getSelectionModel();
        lsmodel4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsmodel4.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!lsmodel4.isSelectionEmpty()) {
                    secilenSatirIndexEposta = lsmodel4.getMinSelectionIndex();
                }

            }
        });

        //HEDEF TABLOSUNU DOLDUR
        TabloDoldur(hedefTablosu, "hedeflertablosu", "MODEL ADI", "DİKİM HEDEFİ", "KOLİ HEDEFİ", "modelAdi", "dikimHedef", "koliHedef");
        final ListSelectionModel lsmodel5 = hedefTablosu.getSelectionModel();
        lsmodel5.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsmodel5.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (lsmodel5.isSelectionEmpty()) {
                    modelHdf.setText("");
                    dikimHdf.setText("");
                    koliHdf.setText("");
                } else {
                    secilenSatirIndexHedef = lsmodel5.getMinSelectionIndex();
                    modelHdf.setText(String.valueOf(hedefTablosu.getValueAt(secilenSatirIndexHedef, 0)));
                    dikimHdf.setText(String.valueOf(hedefTablosu.getValueAt(secilenSatirIndexHedef, 1)));
                    koliHdf.setText(String.valueOf(hedefTablosu.getValueAt(secilenSatirIndexHedef, 2)));
                }

            }
        });

        //ANALİZ TABLOSUNU DOLDUR
        final ListSelectionModel lsmodel6 = epostaTablosu.getSelectionModel();
        lsmodel6.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsmodel6.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!lsmodel6.isSelectionEmpty()) {
                    secilenSatirIndexEposta = lsmodel6.getMinSelectionIndex();
                }

            }
        });

        tumSpinnerlariAyarla();

        //Sağ Tıklayınca açılan menüler
        sagTikMenuDuzeltmeTablosu();
        sagTiklaSilDonemTablosu();

        //Otomatik model öneren yazı alanları oluşturur.
        ArrayList<String> ws = modelListesiAl();
        otoOner otoOnerAnahtar = new otoOner();
        otoOnerAnahtar.otoOner(model1, ws);
        otoOnerAnahtar.otoOner(model2, ws);
        otoOnerAnahtar.otoOner(model3, ws);
        otoOnerAnahtar.otoOner(model4, ws);
        otoOnerAnahtar.otoOner(model5, ws);
        otoOnerAnahtar.otoOner(model6, ws);
        otoOnerAnahtar.otoOner(model7, ws);
        otoOnerAnahtar.otoOner(model8, ws);
        otoOnerAnahtar.otoOner(model9, ws);
        otoOnerAnahtar.otoOner(model10, ws);
        otoOnerAnahtar.otoOner(model11, ws);
        otoOnerAnahtar.otoOner(model12, ws);
        otoOnerAnahtar.otoOner(model13, ws);
        //ekModellere de alta açılır şeçke ekle
        otoOnerAnahtar.otoOner(ekModel1, ws);
        otoOnerAnahtar.otoOner(ekModel2, ws);
        otoOnerAnahtar.otoOner(ekModel3, ws);
        otoOnerAnahtar.otoOner(ekModel4, ws);
        otoOnerAnahtar.otoOner(ekModel5, ws);
        otoOnerAnahtar.otoOner(ekModel6, ws);
        otoOnerAnahtar.otoOner(ekModel7, ws);
        otoOnerAnahtar.otoOner(ekModel8, ws);
        otoOnerAnahtar.otoOner(ekModel9, ws);
        otoOnerAnahtar.otoOner(ekModel10, ws);
        otoOnerAnahtar.otoOner(ekModel11, ws);
        otoOnerAnahtar.otoOner(ekModel12, ws);
        otoOnerAnahtar.otoOner(ekModel13, ws);

        islevseller islevAnahtar = new islevseller();
        try {
            raporDosTxtField.setText(islevAnahtar.ayarAl("raporKayitYeri"));
            dbKullaniciTxtField.setText(islevAnahtar.ayarAl("kullanici"));
            dbSifreTxtField.setText(islevAnahtar.ayarAl("parola"));
            dbBaglantiYoluTxtField.setText(islevAnahtar.ayarAl("baglanti"));
            sunucuAdresiTxtField.setText(islevAnahtar.ayarAl("mailSunucuAdresi"));
            portTxtField.setText(islevAnahtar.ayarAl("mailPort"));
            epostaKonuTxtField.setText(islevAnahtar.ayarAl("mailKonu"));
            epostaMesajTxtField.setText(islevAnahtar.ayarAl("mailGovdesi"));
            raporAdiTxtField.setText(islevAnahtar.ayarAl("raporAdi"));
            if (islevAnahtar.ayarAl("ekinFormati").equals(".pdf")) {
                pdf.setSelected(true);
            }
            if (islevAnahtar.ayarAl("ekinFormati").equals(".xlsx")) {
                xlsx.setSelected(true);
            }
            if (islevAnahtar.ayarAl("raporYazdirmaSecenegi").equals("göster")) {
                goster.setSelected(true);
            }
            if (islevAnahtar.ayarAl("raporYazdirmaSecenegi").equals("seç")) {
                sec.setSelected(true);
            }
            if (islevAnahtar.ayarAl("raporYazdirmaSecenegi").equals("sadeceYazdır")) {
                sadeceYazdir.setSelected(true);
            }
        } catch (Exception ex) {
            new kurtarma(ex);
        }
        epostaBilgileriDoldur();

        tumHedefleriAyarla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        ekinFormati = new javax.swing.ButtonGroup();
        yazdirmaSecenegi = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        adetTab = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jPanel17 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        model1 = new javax.swing.JTextField();
        jCheckBox2 = new javax.swing.JCheckBox();
        model2 = new javax.swing.JTextField();
        jCheckBox3 = new javax.swing.JCheckBox();
        model3 = new javax.swing.JTextField();
        jCheckBox4 = new javax.swing.JCheckBox();
        model4 = new javax.swing.JTextField();
        jCheckBox5 = new javax.swing.JCheckBox();
        model5 = new javax.swing.JTextField();
        jCheckBox6 = new javax.swing.JCheckBox();
        model6 = new javax.swing.JTextField();
        jCheckBox7 = new javax.swing.JCheckBox();
        model7 = new javax.swing.JTextField();
        jCheckBox8 = new javax.swing.JCheckBox();
        model8 = new javax.swing.JTextField();
        jCheckBox9 = new javax.swing.JCheckBox();
        model9 = new javax.swing.JTextField();
        jCheckBox10 = new javax.swing.JCheckBox();
        model10 = new javax.swing.JTextField();
        jCheckBox11 = new javax.swing.JCheckBox();
        model11 = new javax.swing.JTextField();
        jCheckBox12 = new javax.swing.JCheckBox();
        model12 = new javax.swing.JTextField();
        jCheckBox13 = new javax.swing.JCheckBox();
        model13 = new javax.swing.JTextField();
        ekPanelAcBtn = new javax.swing.JToggleButton();
        ekPaneli = new javax.swing.JPanel();
        ekModel1 = new javax.swing.JTextField();
        ekDA1 = new javax.swing.JTextField();
        ekKA1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        ekModel2 = new javax.swing.JTextField();
        ekDA2 = new javax.swing.JTextField();
        ekKA2 = new javax.swing.JTextField();
        ekModel3 = new javax.swing.JTextField();
        ekDA3 = new javax.swing.JTextField();
        ekKA3 = new javax.swing.JTextField();
        ekModel4 = new javax.swing.JTextField();
        ekDA4 = new javax.swing.JTextField();
        ekKA4 = new javax.swing.JTextField();
        ekDA5 = new javax.swing.JTextField();
        ekModel5 = new javax.swing.JTextField();
        ekKA5 = new javax.swing.JTextField();
        ekModel6 = new javax.swing.JTextField();
        ekDA6 = new javax.swing.JTextField();
        ekKA6 = new javax.swing.JTextField();
        ekDA7 = new javax.swing.JTextField();
        ekModel7 = new javax.swing.JTextField();
        ekKA7 = new javax.swing.JTextField();
        ekModel8 = new javax.swing.JTextField();
        ekDA8 = new javax.swing.JTextField();
        ekKA8 = new javax.swing.JTextField();
        ekModel9 = new javax.swing.JTextField();
        ekKA9 = new javax.swing.JTextField();
        ekDA9 = new javax.swing.JTextField();
        ekModel10 = new javax.swing.JTextField();
        ekDA10 = new javax.swing.JTextField();
        ekKA10 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        ekOp1 = new javax.swing.JTextField();
        ekOp2 = new javax.swing.JTextField();
        ekOp3 = new javax.swing.JTextField();
        ekOp4 = new javax.swing.JTextField();
        ekOp5 = new javax.swing.JTextField();
        ekOp6 = new javax.swing.JTextField();
        ekOp7 = new javax.swing.JTextField();
        ekOp8 = new javax.swing.JTextField();
        ekOp9 = new javax.swing.JTextField();
        ekOp10 = new javax.swing.JTextField();
        ekModel11 = new javax.swing.JTextField();
        ekDA11 = new javax.swing.JTextField();
        ekKA11 = new javax.swing.JTextField();
        ekOp11 = new javax.swing.JTextField();
        ekModel12 = new javax.swing.JTextField();
        ekDA12 = new javax.swing.JTextField();
        ekKA12 = new javax.swing.JTextField();
        ekOp12 = new javax.swing.JTextField();
        ekModel13 = new javax.swing.JTextField();
        ekDA13 = new javax.swing.JTextField();
        ekKA13 = new javax.swing.JTextField();
        ekOp13 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        sefCBx1 = new javax.swing.JComboBox<>();
        sefCBx2 = new javax.swing.JComboBox<>();
        sefCBx3 = new javax.swing.JComboBox<>();
        sefCBx4 = new javax.swing.JComboBox<>();
        sefCBx5 = new javax.swing.JComboBox<>();
        sefCBx6 = new javax.swing.JComboBox<>();
        sefCBx7 = new javax.swing.JComboBox<>();
        sefCBx8 = new javax.swing.JComboBox<>();
        sefCBx9 = new javax.swing.JComboBox<>();
        sefCBx10 = new javax.swing.JComboBox<>();
        kH10 = new javax.swing.JTextField();
        kH9 = new javax.swing.JTextField();
        kH8 = new javax.swing.JTextField();
        kH7 = new javax.swing.JTextField();
        kH6 = new javax.swing.JTextField();
        kH5 = new javax.swing.JTextField();
        kH4 = new javax.swing.JTextField();
        kH3 = new javax.swing.JTextField();
        kH2 = new javax.swing.JTextField();
        kH1 = new javax.swing.JTextField();
        dH10 = new javax.swing.JTextField();
        dH9 = new javax.swing.JTextField();
        dH8 = new javax.swing.JTextField();
        dH7 = new javax.swing.JTextField();
        dH6 = new javax.swing.JTextField();
        dH5 = new javax.swing.JTextField();
        dH4 = new javax.swing.JTextField();
        dH3 = new javax.swing.JTextField();
        dH2 = new javax.swing.JTextField();
        dH1 = new javax.swing.JTextField();
        kA10 = new javax.swing.JTextField();
        dA10 = new javax.swing.JTextField();
        kA9 = new javax.swing.JTextField();
        dA9 = new javax.swing.JTextField();
        kA8 = new javax.swing.JTextField();
        dA8 = new javax.swing.JTextField();
        kA7 = new javax.swing.JTextField();
        dA7 = new javax.swing.JTextField();
        kA6 = new javax.swing.JTextField();
        dA6 = new javax.swing.JTextField();
        kA5 = new javax.swing.JTextField();
        dA5 = new javax.swing.JTextField();
        kA4 = new javax.swing.JTextField();
        dA4 = new javax.swing.JTextField();
        kA3 = new javax.swing.JTextField();
        dA3 = new javax.swing.JTextField();
        kA1 = new javax.swing.JTextField();
        dA1 = new javax.swing.JTextField();
        kA2 = new javax.swing.JTextField();
        dA2 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        opAdet1 = new javax.swing.JTextField();
        opAdet2 = new javax.swing.JTextField();
        opAdet3 = new javax.swing.JTextField();
        opAdet4 = new javax.swing.JTextField();
        opAdet5 = new javax.swing.JTextField();
        opAdet6 = new javax.swing.JTextField();
        opAdet7 = new javax.swing.JTextField();
        opAdet8 = new javax.swing.JTextField();
        opAdet9 = new javax.swing.JTextField();
        opAdet10 = new javax.swing.JTextField();
        dA11 = new javax.swing.JTextField();
        kA11 = new javax.swing.JTextField();
        dH11 = new javax.swing.JTextField();
        kH11 = new javax.swing.JTextField();
        opAdet11 = new javax.swing.JTextField();
        sefCBx11 = new javax.swing.JComboBox<>();
        dA12 = new javax.swing.JTextField();
        kA12 = new javax.swing.JTextField();
        dH12 = new javax.swing.JTextField();
        kH12 = new javax.swing.JTextField();
        opAdet12 = new javax.swing.JTextField();
        sefCBx12 = new javax.swing.JComboBox<>();
        dA13 = new javax.swing.JTextField();
        kA13 = new javax.swing.JTextField();
        dH13 = new javax.swing.JTextField();
        kH13 = new javax.swing.JTextField();
        opAdet13 = new javax.swing.JTextField();
        sefCBx13 = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        kilit = new javax.swing.JToggleButton();
        tarihDC = new com.toedter.calendar.JDateChooser();
        donemCBx = new javax.swing.JComboBox<>();
        donemModelSec = new javax.swing.JComboBox<>();
        donemTipiSecBtn = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        sure = new javax.swing.JTextField();
        dHGir = new javax.swing.JTextField();
        kHGir = new javax.swing.JTextField();
        hedefleriGir = new javax.swing.JButton();
        varsayilanBtn = new javax.swing.JButton();
        yardim = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        veriKaydet = new javax.swing.JButton();
        rapolaBtn = new javax.swing.JButton();
        yazdirBtn = new javax.swing.JButton();
        gosterBtn = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        mailMesajları = new javax.swing.JTextPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        mesajAlani = new javax.swing.JTextArea();
        duyuruTab = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        duyuru5 = new javax.swing.JCheckBox();
        jLabel24 = new javax.swing.JLabel();
        duyuru5sira = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        duyuruTArea5 = new javax.swing.JTextArea();
        jToolBar5 = new javax.swing.JToolBar();
        italik4 = new javax.swing.JButton();
        altCizgili4 = new javax.swing.JButton();
        paragraf4 = new javax.swing.JButton();
        kalin4 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        tarihDC2 = new com.toedter.calendar.JDateChooser();
        donemCBx2 = new javax.swing.JComboBox<>();
        duyuruKaydet = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        duyuru2 = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        duyuru2sira = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        duyuruTArea2 = new javax.swing.JTextArea();
        jToolBar3 = new javax.swing.JToolBar();
        italik1 = new javax.swing.JButton();
        altCizgili1 = new javax.swing.JButton();
        paragraf1 = new javax.swing.JButton();
        kalin1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        duyuru3 = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        duyuru3sira = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        duyuruTArea3 = new javax.swing.JTextArea();
        jToolBar4 = new javax.swing.JToolBar();
        italik2 = new javax.swing.JButton();
        altCizgili2 = new javax.swing.JButton();
        paragraf2 = new javax.swing.JButton();
        kalin2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        duyuru4 = new javax.swing.JCheckBox();
        jScrollPane8 = new javax.swing.JScrollPane();
        duyuruTArea4 = new javax.swing.JTextArea();
        jLabel23 = new javax.swing.JLabel();
        duyuru4sira = new javax.swing.JTextField();
        jToolBar2 = new javax.swing.JToolBar();
        italik3 = new javax.swing.JButton();
        altCizgili3 = new javax.swing.JButton();
        paragraf3 = new javax.swing.JButton();
        kalin3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        duyuru6 = new javax.swing.JCheckBox();
        jScrollPane10 = new javax.swing.JScrollPane();
        duyuruTArea6 = new javax.swing.JTextArea();
        jLabel25 = new javax.swing.JLabel();
        duyuru6sira = new javax.swing.JTextField();
        jToolBar6 = new javax.swing.JToolBar();
        italik5 = new javax.swing.JButton();
        altCizgili5 = new javax.swing.JButton();
        paragraf5 = new javax.swing.JButton();
        kalin5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        duyuru1 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        duyuru1sira = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        duyuruTArea1 = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();
        italik = new javax.swing.JButton();
        altCizgili = new javax.swing.JButton();
        paragraf = new javax.swing.JButton();
        kalin = new javax.swing.JButton();
        duzeltmeTab = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        duzeltmeTablosu = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        duyuruDzl = new javax.swing.JTextArea();
        jPanel11 = new javax.swing.JPanel();
        bantSefiDzl = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        koliAdetDzl = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        koliHedefDzl = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        dikimAdetDzl = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        donemDzl = new javax.swing.JTextField();
        dikimHedefDzl = new javax.swing.JTextField();
        modelDzl = new javax.swing.JTextField();
        bantNoDzl = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        tarihDzl = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        opSayisiDzl = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        filtreBtn = new javax.swing.JButton();
        tarihDC1 = new com.toedter.calendar.JDateChooser();
        donemCBx1 = new javax.swing.JComboBox<>();
        bugunKayiti = new javax.swing.JRadioButton();
        gecmisKayiti = new javax.swing.JRadioButton();
        jPanel13 = new javax.swing.JPanel();
        silBtnDzl = new javax.swing.JButton();
        kopyalaBtnDzl = new javax.swing.JButton();
        degistirBtnDzl = new javax.swing.JButton();
        donemECTab = new javax.swing.JPanel();
        donem3Chbx = new javax.swing.JCheckBox();
        donem4Chbx = new javax.swing.JCheckBox();
        donem5Chbx = new javax.swing.JCheckBox();
        donem6Chbx = new javax.swing.JCheckBox();
        donem7Chbx = new javax.swing.JCheckBox();
        donem8Chbx = new javax.swing.JCheckBox();
        donem9Chbx = new javax.swing.JCheckBox();
        donem10Chbx = new javax.swing.JCheckBox();
        donem11Chbx = new javax.swing.JCheckBox();
        donem12Chbx = new javax.swing.JCheckBox();
        donem13Chbx = new javax.swing.JCheckBox();
        donem14Chbx = new javax.swing.JCheckBox();
        donem15Chbx = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        donem1Chbx = new javax.swing.JCheckBox();
        donem2Chbx = new javax.swing.JCheckBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        donemTablosu = new javax.swing.JTable();
        donemTipiEtiketi = new javax.swing.JLabel();
        basla1 = new javax.swing.JSpinner();
        basla2 = new javax.swing.JSpinner();
        basla3 = new javax.swing.JSpinner();
        basla4 = new javax.swing.JSpinner();
        basla5 = new javax.swing.JSpinner();
        basla6 = new javax.swing.JSpinner();
        basla7 = new javax.swing.JSpinner();
        basla8 = new javax.swing.JSpinner();
        basla9 = new javax.swing.JSpinner();
        basla10 = new javax.swing.JSpinner();
        basla11 = new javax.swing.JSpinner();
        basla12 = new javax.swing.JSpinner();
        basla13 = new javax.swing.JSpinner();
        basla14 = new javax.swing.JSpinner();
        basla15 = new javax.swing.JSpinner();
        bit1 = new javax.swing.JSpinner();
        bit2 = new javax.swing.JSpinner();
        bit3 = new javax.swing.JSpinner();
        bit4 = new javax.swing.JSpinner();
        bit5 = new javax.swing.JSpinner();
        bit6 = new javax.swing.JSpinner();
        bit7 = new javax.swing.JSpinner();
        bit8 = new javax.swing.JSpinner();
        bit9 = new javax.swing.JSpinner();
        bit10 = new javax.swing.JSpinner();
        bit11 = new javax.swing.JSpinner();
        bit12 = new javax.swing.JSpinner();
        bit13 = new javax.swing.JSpinner();
        bit14 = new javax.swing.JSpinner();
        bit15 = new javax.swing.JSpinner();
        jPanel22 = new javax.swing.JPanel();
        donemSilBtn = new javax.swing.JButton();
        yeniDonemEkleBtn = new javax.swing.JButton();
        yeniDonemAdi = new javax.swing.JTextField();
        donemKaydet = new javax.swing.JButton();
        sefECTab = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        bantSefininAdiTxt = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        sefTablosu = new javax.swing.JTable();
        sefSil = new javax.swing.JButton();
        sefKaydet = new javax.swing.JButton();
        hedeflerTab = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        hedefTablosu = new javax.swing.JTable();
        hedefSil = new javax.swing.JButton();
        hedefKaydet = new javax.swing.JButton();
        hedefDegistir = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        modelHdf = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        koliHdf = new javax.swing.JTextField();
        dikimHdf = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        analizTab = new javax.swing.JPanel();
        analizBasTarih = new com.toedter.calendar.JDateChooser();
        analizBitTarih = new com.toedter.calendar.JDateChooser();
        jToolBar7 = new javax.swing.JToolBar();
        opSayisiGoster = new javax.swing.JButton();
        modelSaatlikAdetAnaliziGoster = new javax.swing.JButton();
        opSayisiRaporla = new javax.swing.JButton();
        modelSaatlikAdetAnaliziRaporla = new javax.swing.JButton();
        kisiBasiAdetRaporla = new javax.swing.JButton();
        grafikHazirla = new javax.swing.JButton();
        jScrollPane16 = new javax.swing.JScrollPane();
        analizTablosu = new javax.swing.JTable();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        ayarlarTab = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        epostaTablosu = new javax.swing.JTable();
        kaydetEposta = new javax.swing.JButton();
        mailSahibi = new javax.swing.JTextField();
        mailAdresi = new javax.swing.JTextField();
        silEposta = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        sunucuAdresiTxtField = new javax.swing.JTextField();
        epostaKonuTxtField = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        pdf = new javax.swing.JRadioButton();
        xlsx = new javax.swing.JRadioButton();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        epostaMesajTxtField = new javax.swing.JTextArea();
        portTxtField = new javax.swing.JTextField();
        epostaAyarlaBtn = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        raporAdiTxtField = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        goster = new javax.swing.JRadioButton();
        sec = new javax.swing.JRadioButton();
        sadeceYazdir = new javax.swing.JRadioButton();
        raporAyarlaBtn = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        raporDosTxtField = new javax.swing.JTextField();
        dosyaSecBtn = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        dbKullaniciTxtField = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        dbBaglantiYoluTxtField = new javax.swing.JTextField();
        dbSifreTxtField = new javax.swing.JPasswordField();
        baglantiYoluSecBtn = new javax.swing.JButton();
        genelAyarlaBtn = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        ePostaAdresiTxtField = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        ePostaSifreTxtField = new javax.swing.JPasswordField();
        jLabel48 = new javax.swing.JLabel();
        ePostaKullaniciAdiTxtField = new javax.swing.JTextField();

        buttonGroup1.add(bugunKayiti);
        buttonGroup1.add(gecmisKayiti);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Üretim İzleme v1.2");
        setBackground(new java.awt.Color(204, 204, 255));
        setMaximumSize(new java.awt.Dimension(1072, 643));

        jLabel2.setBackground(new java.awt.Color(153, 153, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("MODEL ADI");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setOpaque(true);

        jCheckBox1.setText("1. BANT");

        model1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                model1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                model1FocusLost(evt);
            }
        });

        jCheckBox2.setText("2. BANT");

        model2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                model2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                model2FocusLost(evt);
            }
        });

        jCheckBox3.setText("3. BANT");

        model3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                model3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                model3FocusLost(evt);
            }
        });

        jCheckBox4.setText("4. BANT");

        model4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                model4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                model4FocusLost(evt);
            }
        });

        jCheckBox5.setText("5. BANT");

        model5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                model5FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                model5FocusLost(evt);
            }
        });

        jCheckBox6.setText("6. BANT");

        model6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                model6FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                model6FocusLost(evt);
            }
        });

        jCheckBox7.setText("7. BANT");

        model7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                model7FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                model7FocusLost(evt);
            }
        });

        jCheckBox8.setText("8. BANT");

        model8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                model8FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                model8FocusLost(evt);
            }
        });

        jCheckBox9.setText("9. BANT");

        model9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                model9FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                model9FocusLost(evt);
            }
        });

        jCheckBox10.setText("10. BANT");

        model10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                model10FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                model10FocusLost(evt);
            }
        });

        jCheckBox11.setText("11. BANT");

        model11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                model11FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                model11FocusLost(evt);
            }
        });

        jCheckBox12.setText("12. BANT");

        model12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                model12FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                model12FocusLost(evt);
            }
        });

        jCheckBox13.setText("EĞİTİM");

        model13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                model13FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                model13FocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jCheckBox13, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(model13, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(model1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jCheckBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(model10, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jCheckBox9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jCheckBox7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jCheckBox6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jCheckBox5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jCheckBox3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jCheckBox4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jCheckBox8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(model2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(model3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(model4, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(model5, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(model6, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(model7, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(model8, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(model9, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jCheckBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(model11, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jCheckBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(model12, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(model1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox2)
                    .addComponent(model2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox3)
                    .addComponent(model3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox4)
                    .addComponent(model4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox5)
                    .addComponent(model5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox6)
                    .addComponent(model6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox7)
                    .addComponent(model7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox8)
                    .addComponent(model8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox9)
                    .addComponent(model9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox10)
                    .addComponent(model10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox11)
                    .addComponent(model11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox12)
                    .addComponent(model12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox13)
                    .addComponent(model13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        ekPanelAcBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/sagaOk.png"))); // NOI18N
        ekPanelAcBtn.setIconTextGap(0);
        ekPanelAcBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ekPanelAcBtnActionPerformed(evt);
            }
        });

        ekModel1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekModel1FocusGained(evt);
            }
        });

        ekDA1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekDA1FocusGained(evt);
            }
        });

        ekKA1.setText("0");
        ekKA1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekKA1FocusGained(evt);
            }
        });

        jLabel27.setBackground(new java.awt.Color(153, 153, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("MODEL ADI");
        jLabel27.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel27.setOpaque(true);

        jLabel28.setBackground(new java.awt.Color(153, 153, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("D.A.");
        jLabel28.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel28.setOpaque(true);

        jLabel29.setBackground(new java.awt.Color(153, 153, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("K.A.");
        jLabel29.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel29.setOpaque(true);

        ekModel2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekModel2FocusGained(evt);
            }
        });

        ekDA2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekDA2FocusGained(evt);
            }
        });

        ekKA2.setText("0");
        ekKA2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekKA2FocusGained(evt);
            }
        });

        ekModel3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekModel3FocusGained(evt);
            }
        });

        ekDA3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekDA3FocusGained(evt);
            }
        });

        ekKA3.setText("0");
        ekKA3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekKA3FocusGained(evt);
            }
        });

        ekModel4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekModel4FocusGained(evt);
            }
        });

        ekDA4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekDA4FocusGained(evt);
            }
        });

        ekKA4.setText("0");
        ekKA4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekKA4FocusGained(evt);
            }
        });

        ekDA5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekDA5FocusGained(evt);
            }
        });

        ekModel5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekModel5FocusGained(evt);
            }
        });

        ekKA5.setText("0");
        ekKA5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekKA5FocusGained(evt);
            }
        });

        ekModel6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekModel6FocusGained(evt);
            }
        });

        ekDA6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekDA6FocusGained(evt);
            }
        });

        ekKA6.setText("0");
        ekKA6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekKA6FocusGained(evt);
            }
        });

        ekDA7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekDA7FocusGained(evt);
            }
        });

        ekModel7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekModel7FocusGained(evt);
            }
        });

        ekKA7.setText("0");
        ekKA7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekKA7FocusGained(evt);
            }
        });

        ekModel8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekModel8FocusGained(evt);
            }
        });

        ekDA8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekDA8FocusGained(evt);
            }
        });

        ekKA8.setText("0");
        ekKA8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekKA8FocusGained(evt);
            }
        });

        ekModel9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekModel9FocusGained(evt);
            }
        });

        ekKA9.setText("0");
        ekKA9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekKA9FocusGained(evt);
            }
        });

        ekDA9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekDA9FocusGained(evt);
            }
        });

        ekModel10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekModel10FocusGained(evt);
            }
        });

        ekDA10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekDA10FocusGained(evt);
            }
        });

        ekKA10.setText("0");
        ekKA10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekKA10FocusGained(evt);
            }
        });

        jLabel30.setBackground(new java.awt.Color(153, 153, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("OP.A.");
        jLabel30.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel30.setOpaque(true);

        ekModel11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekModel11FocusGained(evt);
            }
        });

        ekDA11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekDA11FocusGained(evt);
            }
        });

        ekKA11.setText("0");
        ekKA11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekKA11FocusGained(evt);
            }
        });

        ekModel12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekModel12FocusGained(evt);
            }
        });

        ekDA12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekDA12FocusGained(evt);
            }
        });

        ekKA12.setText("0");
        ekKA12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekKA12FocusGained(evt);
            }
        });

        ekModel13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekModel13FocusGained(evt);
            }
        });

        ekDA13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekDA13FocusGained(evt);
            }
        });

        ekKA13.setText("0");
        ekKA13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ekKA13FocusGained(evt);
            }
        });

        javax.swing.GroupLayout ekPaneliLayout = new javax.swing.GroupLayout(ekPaneli);
        ekPaneli.setLayout(ekPaneliLayout);
        ekPaneliLayout.setHorizontalGroup(
            ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ekPaneliLayout.createSequentialGroup()
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ekModel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ekModel3)
                    .addComponent(ekModel4)
                    .addComponent(ekModel5)
                    .addComponent(ekModel6)
                    .addComponent(ekModel7)
                    .addComponent(ekModel8)
                    .addComponent(ekModel9)
                    .addComponent(ekModel10)
                    .addComponent(ekModel1)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(ekModel11)
                    .addComponent(ekModel12)
                    .addComponent(ekModel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ekPaneliLayout.createSequentialGroup()
                        .addComponent(ekDA11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ekKA11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ekOp11, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                    .addGroup(ekPaneliLayout.createSequentialGroup()
                        .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ekDA9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekDA8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekDA7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekDA6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekDA5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekDA4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekDA3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekDA2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekDA1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ekDA10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ekKA9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekKA8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekKA7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekKA6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekKA5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekKA4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekKA3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekKA2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ekKA1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ekKA10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ekOp9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                            .addComponent(ekOp8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ekOp7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ekOp6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ekOp5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ekOp4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ekOp3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ekOp2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(ekOp1)
                            .addComponent(ekOp10)))
                    .addGroup(ekPaneliLayout.createSequentialGroup()
                        .addComponent(ekDA12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ekKA12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ekOp12))
                    .addGroup(ekPaneliLayout.createSequentialGroup()
                        .addComponent(ekDA13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ekKA13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ekOp13)))
                .addContainerGap())
        );
        ekPaneliLayout.setVerticalGroup(
            ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ekPaneliLayout.createSequentialGroup()
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ekModel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekDA1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekKA1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekOp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ekModel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekDA2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekKA2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekOp2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ekModel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekDA3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekKA3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekOp3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ekModel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekDA4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekKA4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekOp4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ekModel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekDA5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekKA5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekOp5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ekModel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekDA6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekKA6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekOp6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ekModel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekDA7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekKA7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekOp7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ekModel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekDA8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekKA8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekOp8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ekModel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekDA9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekKA9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekOp9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ekModel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekDA10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekKA10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekOp10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ekModel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekDA11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekKA11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekOp11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ekModel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekDA12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekKA12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekOp12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ekPaneliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ekModel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekDA13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekKA13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ekOp13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel3.setBackground(new java.awt.Color(153, 153, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DİKİM ADET");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setOpaque(true);

        jLabel4.setBackground(new java.awt.Color(153, 153, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("KOLİ ADET");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel4.setOpaque(true);

        jLabel1.setBackground(new java.awt.Color(153, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DİKİM HEDEF");
        jLabel1.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(153, 153, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("KOLİ HEDEF");
        jLabel5.setOpaque(true);

        jLabel6.setBackground(new java.awt.Color(153, 153, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("BANT ŞEFİ");
        jLabel6.setOpaque(true);

        kA10.setText("0");
        kA10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kA10FocusGained(evt);
            }
        });

        dA10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dA10FocusGained(evt);
            }
        });

        kA9.setText("0");
        kA9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kA9FocusGained(evt);
            }
        });

        dA9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dA9FocusGained(evt);
            }
        });

        kA8.setText("0");
        kA8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kA8FocusGained(evt);
            }
        });

        dA8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dA8FocusGained(evt);
            }
        });

        kA7.setText("0");
        kA7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kA7FocusGained(evt);
            }
        });

        dA7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dA7FocusGained(evt);
            }
        });

        kA6.setText("0");
        kA6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kA6FocusGained(evt);
            }
        });

        dA6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dA6FocusGained(evt);
            }
        });

        kA5.setText("0");
        kA5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kA5FocusGained(evt);
            }
        });

        dA5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dA5FocusGained(evt);
            }
        });

        kA4.setText("0");
        kA4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kA4FocusGained(evt);
            }
        });

        dA4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dA4FocusGained(evt);
            }
        });

        kA3.setText("0");
        kA3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kA3FocusGained(evt);
            }
        });

        dA3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dA3FocusGained(evt);
            }
        });

        kA1.setText("0");
        kA1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kA1FocusGained(evt);
            }
        });

        dA1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dA1FocusGained(evt);
            }
        });

        kA2.setText("0");
        kA2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kA2FocusGained(evt);
            }
        });

        dA2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dA2FocusGained(evt);
            }
        });

        jLabel26.setBackground(new java.awt.Color(153, 153, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("OP. ADETİ");
        jLabel26.setOpaque(true);

        opAdet1.setBackground(new java.awt.Color(204, 255, 204));
        opAdet1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        opAdet2.setBackground(new java.awt.Color(204, 255, 204));

        opAdet3.setBackground(new java.awt.Color(204, 255, 204));

        opAdet4.setBackground(new java.awt.Color(204, 255, 204));

        opAdet5.setBackground(new java.awt.Color(204, 255, 204));

        opAdet6.setBackground(new java.awt.Color(204, 255, 204));

        opAdet7.setBackground(new java.awt.Color(204, 255, 204));

        opAdet8.setBackground(new java.awt.Color(204, 255, 204));

        opAdet9.setBackground(new java.awt.Color(204, 255, 204));

        opAdet10.setBackground(new java.awt.Color(204, 255, 204));

        dA11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dA11FocusGained(evt);
            }
        });

        kA11.setText("0");
        kA11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kA11FocusGained(evt);
            }
        });

        opAdet11.setBackground(new java.awt.Color(204, 255, 204));

        dA12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dA12FocusGained(evt);
            }
        });

        kA12.setText("0");
        kA12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kA12FocusGained(evt);
            }
        });

        opAdet12.setBackground(new java.awt.Color(204, 255, 204));

        dA13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dA13FocusGained(evt);
            }
        });

        kA13.setText("0");
        kA13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kA13FocusGained(evt);
            }
        });

        opAdet13.setBackground(new java.awt.Color(204, 255, 204));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(dA11, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dA10, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                        .addComponent(dA9)
                        .addComponent(dA8)
                        .addComponent(dA7)
                        .addComponent(dA6)
                        .addComponent(dA5)
                        .addComponent(dA1)
                        .addComponent(dA2)
                        .addComponent(dA3)
                        .addComponent(dA4)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dA12, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dA13, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(kA9, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kA8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kA7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kA6, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kA5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kA4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kA3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kA2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, Short.MAX_VALUE)
                    .addComponent(kA1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kA10)
                    .addComponent(kA11, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kA12, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kA13, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dH2)
                    .addComponent(dH1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dH9)
                    .addComponent(dH8)
                    .addComponent(dH7)
                    .addComponent(dH6)
                    .addComponent(dH5)
                    .addComponent(dH4)
                    .addComponent(dH3)
                    .addComponent(dH10)
                    .addComponent(dH11)
                    .addComponent(dH12)
                    .addComponent(dH13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(kH9, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kH8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kH7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kH6, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kH5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kH4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kH3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kH2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, Short.MAX_VALUE)
                    .addComponent(kH1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kH10)
                    .addComponent(kH11, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kH12, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kH13, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(opAdet1)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 64, Short.MAX_VALUE)
                    .addComponent(opAdet2)
                    .addComponent(opAdet3)
                    .addComponent(opAdet4)
                    .addComponent(opAdet5)
                    .addComponent(opAdet6)
                    .addComponent(opAdet7)
                    .addComponent(opAdet8)
                    .addComponent(opAdet9)
                    .addComponent(opAdet10)
                    .addComponent(opAdet11)
                    .addComponent(opAdet12)
                    .addComponent(opAdet13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(sefCBx9, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sefCBx8, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sefCBx7, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sefCBx6, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sefCBx5, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sefCBx4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sefCBx3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sefCBx2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sefCBx1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sefCBx10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(sefCBx11, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx12, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx13, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dA1, dA10, dA2, dA3, dA4, dA5, dA6, dA7, dA8, dA9, dH1, dH10, dH2, dH3, dH4, dH5, dH6, dH7, dH8, dH9, jLabel1, jLabel26, jLabel3, jLabel4, jLabel5, kA1, kA10, kA2, kA3, kA4, kA5, kA6, kA7, kA8, kA9, kH1, kH10, kH2, kH3, kH4, kH5, kH6, kH7, kH8, kH9, opAdet1, opAdet10, opAdet2, opAdet3, opAdet4, opAdet5, opAdet6, opAdet7, opAdet8, opAdet9});

        jPanel9Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel6, sefCBx1, sefCBx10, sefCBx2, sefCBx3, sefCBx4, sefCBx5, sefCBx6, sefCBx7, sefCBx8, sefCBx9});

        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel26))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jLabel1))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dA1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kA1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dH1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kH1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opAdet1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dA2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kA2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kH2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opAdet2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dA3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kA3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dH3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kH3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opAdet3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dA4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kA4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dH4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kH4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opAdet4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dA5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kA5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dH5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kH5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opAdet5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dA6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kA6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dH6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kH6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opAdet6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dA7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kA7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dH7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kH7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opAdet7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dA8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kA8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dH8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kH8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opAdet8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dA9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kA9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dH9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kH9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opAdet9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dA10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kA10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dH10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kH10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opAdet10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dA11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kA11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dH11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kH11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opAdet11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dA12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kA12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dH12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kH12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opAdet12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dA13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kA13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dH13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kH13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opAdet13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefCBx13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        kilit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/kilitle.png"))); // NOI18N
        kilit.setText("<html>SABiT ALAN KİLİDİ</html>");
        kilit.setToolTipText("Model adı, bant adı, bant şefi gibi gün içinde fazla değişmeyen bölmeleri kilitler.");
        kilit.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        kilit.setMargin(new java.awt.Insets(1, 1, 1, 1));
        kilit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kilitActionPerformed(evt);
            }
        });

        tarihDC.setDateFormatString("yyyy-MM-dd");

        donemCBx.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                donemCBxİtemStateChanged(evt);
            }
        });

        donemTipiSecBtn.setText("<html><center>DÖNEM ADI SEÇ</center></html>");
        donemTipiSecBtn.setIconTextGap(1);
        donemTipiSecBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        donemTipiSecBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donemTipiSecBtnActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(0, 153, 153));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel8MousePressed(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("SÜRE(SAAT)>");

        sure.setToolTipText("Girilecek sayıların virgüllü değil noktalı yazılması gerekmektedir.");
        sure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sureActionPerformed(evt);
            }
        });

        dHGir.setForeground(new java.awt.Color(153, 153, 153));
        dHGir.setText("DİKİM");
        dHGir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dHGirFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dHGirFocusLost(evt);
            }
        });

        kHGir.setForeground(new java.awt.Color(153, 153, 153));
        kHGir.setText("KOLİ");
        kHGir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                kHGirFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                kHGirFocusLost(evt);
            }
        });
        kHGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kHGirActionPerformed(evt);
            }
        });

        hedefleriGir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/tamam.png"))); // NOI18N
        hedefleriGir.setText("<html><center>HEDEFLERİ<br>GİR</center></html>");
        hedefleriGir.setMargin(new java.awt.Insets(1, 1, 1, 1));
        hedefleriGir.setMinimumSize(new java.awt.Dimension(90, 35));
        hedefleriGir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hedefleriGirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(dHGir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kHGir, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(sure))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hedefleriGir, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dHGir, kHGir});

        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(hedefleriGir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dHGir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kHGir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dHGir, kHGir, sure});

        varsayilanBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/varsayilan.png"))); // NOI18N
        varsayilanBtn.setText("VARSAYILAN");
        varsayilanBtn.setMargin(new java.awt.Insets(2, 2, 2, 2));
        varsayilanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                varsayilanBtnActionPerformed(evt);
            }
        });

        yardim.setBackground(new java.awt.Color(255, 255, 255));
        yardim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/soru_isaretiIcon.jpg"))); // NOI18N
        yardim.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        yardim.setIconTextGap(0);
        yardim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yardimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kilit, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(tarihDC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(donemModelSec, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donemCBx, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(donemTipiSecBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(yardim, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(varsayilanBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel10Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {donemCBx, donemModelSec});

        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(yardim)
                        .addGap(4, 4, 4)
                        .addComponent(varsayilanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(donemModelSec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kilit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tarihDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(donemCBx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donemTipiSecBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        veriKaydet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/kaydet.png"))); // NOI18N
        veriKaydet.setText("KAYDET");
        veriKaydet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                veriKaydetActionPerformed(evt);
            }
        });

        rapolaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/raporla.png"))); // NOI18N
        rapolaBtn.setText("RAPORLA");
        rapolaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rapolaBtnActionPerformed(evt);
            }
        });

        yazdirBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/yazdir2.png"))); // NOI18N
        yazdirBtn.setText("YAZDIR");
        yazdirBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yazdirBtnActionPerformed(evt);
            }
        });

        gosterBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/goz.png"))); // NOI18N
        gosterBtn.setText("GÖSTER");
        gosterBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gosterBtnActionPerformed(evt);
            }
        });

        jScrollPane13.setViewportView(mailMesajları);

        mesajAlani.setColumns(20);
        mesajAlani.setRows(5);
        jScrollPane11.setViewportView(mesajAlani);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(0, 139, Short.MAX_VALUE)
                        .addComponent(veriKaydet, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rapolaBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(yazdirBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gosterBtn)
                        .addGap(0, 139, Short.MAX_VALUE))
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel16Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {gosterBtn, rapolaBtn, veriKaydet, yazdirBtn});

        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(veriKaydet)
                    .addComponent(rapolaBtn)
                    .addComponent(yazdirBtn)
                    .addComponent(gosterBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                    .addContainerGap(38, Short.MAX_VALUE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(23, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ekPanelAcBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addGap(52, 52, 52)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel17Layout.createSequentialGroup()
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(59, 59, 59)
                            .addComponent(ekPaneli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(70, Short.MAX_VALUE)))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ekPanelAcBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)))
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ekPaneli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(213, Short.MAX_VALUE)))
        );

        jScrollPane17.setViewportView(jPanel17);

        javax.swing.GroupLayout adetTabLayout = new javax.swing.GroupLayout(adetTab);
        adetTab.setLayout(adetTabLayout);
        adetTabLayout.setHorizontalGroup(
            adetTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17)
        );
        adetTabLayout.setVerticalGroup(
            adetTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adetTabLayout.createSequentialGroup()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Adet Girişi", adetTab);

        jPanel5.setBackground(new java.awt.Color(255, 255, 211));

        duyuru5.setBackground(new java.awt.Color(51, 255, 153));
        duyuru5.setText("DUYURU");

        jLabel24.setText("Slayt Sırası");

        duyuruTArea5.setColumns(20);
        duyuruTArea5.setRows(5);
        duyuruTArea5.setMaximumSize(new java.awt.Dimension(171, 171));
        duyuruTArea5.setMinimumSize(new java.awt.Dimension(171, 171));
        duyuruTArea5.setName(""); // NOI18N
        duyuruTArea5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                duyuruTArea5KeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(duyuruTArea5);

        jToolBar5.setFloatable(false);
        jToolBar5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar5.setRollover(true);

        italik4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/italik.png"))); // NOI18N
        italik4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                italik4ActionPerformed(evt);
            }
        });
        jToolBar5.add(italik4);

        altCizgili4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/altCizgili.png"))); // NOI18N
        altCizgili4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altCizgili4ActionPerformed(evt);
            }
        });
        jToolBar5.add(altCizgili4);

        paragraf4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/paragraf.png"))); // NOI18N
        paragraf4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paragraf4ActionPerformed(evt);
            }
        });
        jToolBar5.add(paragraf4);

        kalin4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/bp20.png"))); // NOI18N
        kalin4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kalin4ActionPerformed(evt);
            }
        });
        jToolBar5.add(kalin4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jToolBar5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(duyuru5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(duyuru5sira, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane9))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(duyuru5)
                    .addComponent(jLabel24)
                    .addComponent(duyuru5sira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane9))
                .addContainerGap())
        );

        tarihDC2.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(tarihDC2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(donemCBx2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(donemCBx2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tarihDC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        duyuruKaydet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/kaydet.png"))); // NOI18N
        duyuruKaydet.setText("KAYDET");
        duyuruKaydet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                duyuruKaydetActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 211));

        duyuru2.setBackground(new java.awt.Color(51, 255, 153));
        duyuru2.setText("DUYURU");

        jLabel9.setText("Slayt Sırası");

        duyuruTArea2.setColumns(20);
        duyuruTArea2.setRows(5);
        duyuruTArea2.setMaximumSize(new java.awt.Dimension(171, 171));
        duyuruTArea2.setMinimumSize(new java.awt.Dimension(171, 171));
        duyuruTArea2.setName(""); // NOI18N
        duyuruTArea2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                duyuruTArea2KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(duyuruTArea2);

        jToolBar3.setFloatable(false);
        jToolBar3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar3.setRollover(true);

        italik1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/italik.png"))); // NOI18N
        italik1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                italik1ActionPerformed(evt);
            }
        });
        jToolBar3.add(italik1);

        altCizgili1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/altCizgili.png"))); // NOI18N
        altCizgili1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altCizgili1ActionPerformed(evt);
            }
        });
        jToolBar3.add(altCizgili1);

        paragraf1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/paragraf.png"))); // NOI18N
        paragraf1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paragraf1ActionPerformed(evt);
            }
        });
        jToolBar3.add(paragraf1);

        kalin1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/bp20.png"))); // NOI18N
        kalin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kalin1ActionPerformed(evt);
            }
        });
        jToolBar3.add(kalin1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(duyuru2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(duyuru2sira, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(duyuru2)
                    .addComponent(jLabel9)
                    .addComponent(duyuru2sira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 211));

        duyuru3.setBackground(new java.awt.Color(51, 255, 153));
        duyuru3.setText("DUYURU");

        jLabel10.setText("Slayt Sırası");

        duyuruTArea3.setColumns(20);
        duyuruTArea3.setRows(5);
        duyuruTArea3.setMaximumSize(new java.awt.Dimension(171, 171));
        duyuruTArea3.setMinimumSize(new java.awt.Dimension(171, 171));
        duyuruTArea3.setName(""); // NOI18N
        duyuruTArea3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                duyuruTArea3KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(duyuruTArea3);

        jToolBar4.setFloatable(false);
        jToolBar4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar4.setRollover(true);

        italik2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/italik.png"))); // NOI18N
        italik2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                italik2ActionPerformed(evt);
            }
        });
        jToolBar4.add(italik2);

        altCizgili2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/altCizgili.png"))); // NOI18N
        altCizgili2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altCizgili2ActionPerformed(evt);
            }
        });
        jToolBar4.add(altCizgili2);

        paragraf2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/paragraf.png"))); // NOI18N
        paragraf2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paragraf2ActionPerformed(evt);
            }
        });
        jToolBar4.add(paragraf2);

        kalin2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/bp20.png"))); // NOI18N
        kalin2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kalin2ActionPerformed(evt);
            }
        });
        jToolBar4.add(kalin2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(duyuru3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(duyuru3sira, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(duyuru3)
                    .addComponent(jLabel10)
                    .addComponent(duyuru3sira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 211));

        duyuru4.setBackground(new java.awt.Color(51, 255, 153));
        duyuru4.setText("DUYURU");

        duyuruTArea4.setColumns(20);
        duyuruTArea4.setRows(5);
        duyuruTArea4.setMaximumSize(new java.awt.Dimension(171, 171));
        duyuruTArea4.setMinimumSize(new java.awt.Dimension(171, 171));
        duyuruTArea4.setName(""); // NOI18N
        duyuruTArea4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                duyuruTArea4KeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(duyuruTArea4);

        jLabel23.setText("Slayt Sırası");

        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar2.setRollover(true);

        italik3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/italik.png"))); // NOI18N
        italik3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                italik3ActionPerformed(evt);
            }
        });
        jToolBar2.add(italik3);

        altCizgili3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/altCizgili.png"))); // NOI18N
        altCizgili3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altCizgili3ActionPerformed(evt);
            }
        });
        jToolBar2.add(altCizgili3);

        paragraf3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/paragraf.png"))); // NOI18N
        paragraf3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paragraf3ActionPerformed(evt);
            }
        });
        jToolBar2.add(paragraf3);

        kalin3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/bp20.png"))); // NOI18N
        kalin3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kalin3ActionPerformed(evt);
            }
        });
        jToolBar2.add(kalin3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(duyuru4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(duyuru4sira, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane8))
                .addGap(13, 13, 13))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(duyuru4)
                    .addComponent(jLabel23)
                    .addComponent(duyuru4sira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane8))
                .addGap(2, 2, 2))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 211));

        duyuru6.setBackground(new java.awt.Color(51, 255, 153));
        duyuru6.setText("DUYURU");

        duyuruTArea6.setColumns(20);
        duyuruTArea6.setRows(5);
        duyuruTArea6.setMaximumSize(new java.awt.Dimension(171, 171));
        duyuruTArea6.setMinimumSize(new java.awt.Dimension(171, 171));
        duyuruTArea6.setName(""); // NOI18N
        duyuruTArea6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                duyuruTArea6KeyPressed(evt);
            }
        });
        jScrollPane10.setViewportView(duyuruTArea6);

        jLabel25.setText("Slayt Sırası");

        jToolBar6.setFloatable(false);
        jToolBar6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar6.setRollover(true);

        italik5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/italik.png"))); // NOI18N
        italik5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                italik5ActionPerformed(evt);
            }
        });
        jToolBar6.add(italik5);

        altCizgili5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/altCizgili.png"))); // NOI18N
        altCizgili5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altCizgili5ActionPerformed(evt);
            }
        });
        jToolBar6.add(altCizgili5);

        paragraf5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/paragraf.png"))); // NOI18N
        paragraf5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paragraf5ActionPerformed(evt);
            }
        });
        jToolBar6.add(paragraf5);

        kalin5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/bp20.png"))); // NOI18N
        kalin5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kalin5ActionPerformed(evt);
            }
        });
        jToolBar6.add(kalin5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jToolBar6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(duyuru6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(duyuru6sira, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane10))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(duyuru6)
                    .addComponent(duyuru6sira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane10))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 211));

        duyuru1.setBackground(new java.awt.Color(51, 255, 153));
        duyuru1.setText("DUYURU");

        jLabel8.setText("Slayt Sırası");

        duyuruTArea1.setColumns(20);
        duyuruTArea1.setRows(5);
        duyuruTArea1.setMaximumSize(new java.awt.Dimension(171, 171));
        duyuruTArea1.setMinimumSize(new java.awt.Dimension(171, 171));
        duyuruTArea1.setName(""); // NOI18N
        duyuruTArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                duyuruTArea1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(duyuruTArea1);

        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);

        italik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/italik.png"))); // NOI18N
        italik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                italikActionPerformed(evt);
            }
        });
        jToolBar1.add(italik);

        altCizgili.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/altCizgili.png"))); // NOI18N
        altCizgili.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altCizgiliActionPerformed(evt);
            }
        });
        jToolBar1.add(altCizgili);

        paragraf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/paragraf.png"))); // NOI18N
        paragraf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paragrafActionPerformed(evt);
            }
        });
        jToolBar1.add(paragraf);

        kalin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/bp20.png"))); // NOI18N
        kalin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kalinActionPerformed(evt);
            }
        });
        jToolBar1.add(kalin);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(duyuru1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(duyuru1sira, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(duyuru1)
                    .addComponent(jLabel8)
                    .addComponent(duyuru1sira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(2, 2, 2))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(duyuruKaydet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(duyuruKaydet)
                .addContainerGap())
        );

        jPanel15Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel3, jPanel6});

        jPanel15Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel1, jPanel4});

        javax.swing.GroupLayout duyuruTabLayout = new javax.swing.GroupLayout(duyuruTab);
        duyuruTab.setLayout(duyuruTabLayout);
        duyuruTabLayout.setHorizontalGroup(
            duyuruTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(duyuruTabLayout.createSequentialGroup()
                .addGap(204, 204, 204)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(517, Short.MAX_VALUE))
        );
        duyuruTabLayout.setVerticalGroup(
            duyuruTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(duyuruTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Duyuru Girişi", duyuruTab);

        jScrollPane6.setMaximumSize(new java.awt.Dimension(1072, 643));

        duzeltmeTablosu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(duzeltmeTablosu);

        duyuruDzl.setColumns(20);
        duyuruDzl.setRows(5);
        duyuruDzl.setMaximumSize(new java.awt.Dimension(104, 64));
        jScrollPane7.setViewportView(duyuruDzl);

        jLabel22.setText("Model Adı");

        jLabel20.setText("Koli Adet");

        jLabel19.setText("Dikim Hedef");

        jLabel15.setText("Bant Şefi");

        jLabel14.setText("Bant No");

        jLabel17.setText("Tarih");

        jLabel18.setText("Dikim Adet");

        jLabel16.setText("Dönem");

        jLabel21.setText("Koli Hedef");

        jLabel31.setText("Op. Sayısı");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bantNoDzl, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bantSefiDzl, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modelDzl, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(donemDzl, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tarihDzl, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dikimAdetDzl, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dikimHedefDzl, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opSayisiDzl, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(koliAdetDzl, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(koliHedefDzl, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel14)
                    .addComponent(bantNoDzl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(donemDzl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(dikimHedefDzl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(opSayisiDzl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(bantSefiDzl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(tarihDzl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(koliAdetDzl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel22)
                    .addComponent(modelDzl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(dikimAdetDzl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(koliHedefDzl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4))
        );

        jPanel12.setBackground(new java.awt.Color(0, 153, 153));

        filtreBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/filtre.png"))); // NOI18N
        filtreBtn.setText("FİLTRELE");
        filtreBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtreBtnActionPerformed(evt);
            }
        });

        tarihDC1.setDateFormatString("yyyy-MM-dd");
        tarihDC1.setEnabled(false);

        bugunKayiti.setBackground(new java.awt.Color(204, 255, 204));
        bugunKayiti.setSelected(true);
        bugunKayiti.setText("Bugüne Ait Kayıtlar");
        bugunKayiti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bugunKayitiActionPerformed(evt);
            }
        });

        gecmisKayiti.setBackground(new java.awt.Color(204, 255, 204));
        gecmisKayiti.setText("Geçmişe Ait Kayıtlar");
        gecmisKayiti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gecmisKayitiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(182, 182, 182)
                .addComponent(gecmisKayiti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bugunKayiti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tarihDC1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(donemCBx1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filtreBtn)
                .addContainerGap(304, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(bugunKayiti)
                    .addComponent(gecmisKayiti)
                    .addComponent(tarihDC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donemCBx1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filtreBtn))
                .addContainerGap())
        );

        silBtnDzl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/sil.png"))); // NOI18N
        silBtnDzl.setText("SİL");
        silBtnDzl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                silBtnDzlActionPerformed(evt);
            }
        });

        kopyalaBtnDzl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/kopyala.png"))); // NOI18N
        kopyalaBtnDzl.setText("KOPYALA");
        kopyalaBtnDzl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kopyalaBtnDzlActionPerformed(evt);
            }
        });

        degistirBtnDzl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/degistir.png"))); // NOI18N
        degistirBtnDzl.setText("DEĞİŞTİR");
        degistirBtnDzl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                degistirBtnDzlActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(kopyalaBtnDzl, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(silBtnDzl)
                .addGap(8, 8, 8)
                .addComponent(degistirBtnDzl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {degistirBtnDzl, kopyalaBtnDzl, silBtnDzl});

        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(degistirBtnDzl)
                    .addComponent(silBtnDzl)
                    .addComponent(kopyalaBtnDzl))
                .addContainerGap())
        );

        javax.swing.GroupLayout duzeltmeTabLayout = new javax.swing.GroupLayout(duzeltmeTab);
        duzeltmeTab.setLayout(duzeltmeTabLayout);
        duzeltmeTabLayout.setHorizontalGroup(
            duzeltmeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(duzeltmeTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(duzeltmeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(duzeltmeTabLayout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(543, Short.MAX_VALUE))
                    .addGroup(duzeltmeTabLayout.createSequentialGroup()
                        .addGroup(duzeltmeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        duzeltmeTabLayout.setVerticalGroup(
            duzeltmeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(duzeltmeTabLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(duzeltmeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Düzeltme", duzeltmeTab);

        donem3Chbx.setText("Dönem 3");
        donem3Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem3ChbxActionPerformed(evt);
            }
        });

        donem4Chbx.setText("Dönem 4");
        donem4Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem4ChbxActionPerformed(evt);
            }
        });

        donem5Chbx.setText("Dönem 5");
        donem5Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem5ChbxActionPerformed(evt);
            }
        });

        donem6Chbx.setText("Dönem 6");
        donem6Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem6ChbxActionPerformed(evt);
            }
        });

        donem7Chbx.setText("Dönem 7");
        donem7Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem7ChbxActionPerformed(evt);
            }
        });

        donem8Chbx.setText("Dönem 8");
        donem8Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem8ChbxActionPerformed(evt);
            }
        });

        donem9Chbx.setText("Dönem 9");
        donem9Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem9ChbxActionPerformed(evt);
            }
        });

        donem10Chbx.setText("Dönem10");
        donem10Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem10ChbxActionPerformed(evt);
            }
        });

        donem11Chbx.setText("Dönem11");
        donem11Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem11ChbxActionPerformed(evt);
            }
        });

        donem12Chbx.setText("Dönem12");
        donem12Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem12ChbxActionPerformed(evt);
            }
        });

        donem13Chbx.setText("Dönem 13");
        donem13Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem13ChbxActionPerformed(evt);
            }
        });

        donem14Chbx.setText("Dönem 14");
        donem14Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem14ChbxActionPerformed(evt);
            }
        });

        donem15Chbx.setText("Dönem 15");
        donem15Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem15ChbxActionPerformed(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Başlangıç");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Bitiş");

        donem1Chbx.setText("Dönem 1");
        donem1Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem1ChbxActionPerformed(evt);
            }
        });

        donem2Chbx.setText("Dönem 2");
        donem2Chbx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donem2ChbxActionPerformed(evt);
            }
        });

        donemTablosu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(donemTablosu);

        donemTipiEtiketi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        donemTipiEtiketi.setForeground(new java.awt.Color(255, 51, 0));
        donemTipiEtiketi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        donemTipiEtiketi.setText("-");

        donemSilBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/sil.png"))); // NOI18N
        donemSilBtn.setText("SİL");
        donemSilBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donemSilBtnActionPerformed(evt);
            }
        });

        yeniDonemEkleBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/ekle.png"))); // NOI18N
        yeniDonemEkleBtn.setText("YENİ DÖNEME EKLE");
        yeniDonemEkleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yeniDonemEkleBtnActionPerformed(evt);
            }
        });

        yeniDonemAdi.setForeground(new java.awt.Color(153, 153, 153));
        yeniDonemAdi.setText("Yeni Dönem Adı");
        yeniDonemAdi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                yeniDonemAdiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                yeniDonemAdiFocusLost(evt);
            }
        });

        donemKaydet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/kaydet.png"))); // NOI18N
        donemKaydet.setText("KAYDET");
        donemKaydet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                donemKaydetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(yeniDonemAdi)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(donemKaydet, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                        .addComponent(donemSilBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                        .addComponent(yeniDonemEkleBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel22Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {donemKaydet, donemSilBtn, yeniDonemEkleBtn});

        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(donemKaydet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(donemSilBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yeniDonemEkleBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yeniDonemAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout donemECTabLayout = new javax.swing.GroupLayout(donemECTab);
        donemECTab.setLayout(donemECTabLayout);
        donemECTabLayout.setHorizontalGroup(
            donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(donemECTabLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(donem1Chbx, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donem2Chbx, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donem3Chbx, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donem4Chbx)
                    .addComponent(donem5Chbx)
                    .addComponent(donem6Chbx)
                    .addComponent(donem7Chbx, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donem8Chbx, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donem9Chbx, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donem10Chbx, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donem11Chbx, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donem12Chbx, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donem13Chbx, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donem14Chbx, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donem15Chbx, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(basla15)
                    .addComponent(basla14)
                    .addComponent(basla13)
                    .addComponent(basla12)
                    .addComponent(basla11)
                    .addComponent(basla10)
                    .addComponent(basla9)
                    .addComponent(basla8)
                    .addComponent(basla7)
                    .addComponent(basla6)
                    .addComponent(basla5)
                    .addComponent(basla4)
                    .addComponent(basla3)
                    .addComponent(basla2)
                    .addComponent(basla1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bit14, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(bit15)
                    .addComponent(bit3)
                    .addComponent(bit2)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bit1)
                    .addComponent(bit12)
                    .addComponent(bit11)
                    .addComponent(bit10)
                    .addComponent(bit9)
                    .addComponent(bit8)
                    .addComponent(bit7)
                    .addComponent(bit6)
                    .addComponent(bit4)
                    .addComponent(bit5)
                    .addComponent(bit13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(donemTipiEtiketi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
        );

        donemECTabLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {donem10Chbx, donem11Chbx, donem12Chbx, donem13Chbx, donem14Chbx, donem15Chbx, donem1Chbx, donem2Chbx, donem3Chbx, donem4Chbx, donem5Chbx, donem6Chbx, donem7Chbx, donem8Chbx, donem9Chbx});

        donemECTabLayout.setVerticalGroup(
            donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(donemECTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(donemTipiEtiketi, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addComponent(jLabel11))
                .addGap(3, 3, 3)
                .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(donemECTabLayout.createSequentialGroup()
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem1Chbx)
                                .addComponent(basla1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem2Chbx)
                                .addComponent(basla2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem3Chbx)
                                .addComponent(basla3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem4Chbx)
                                .addComponent(basla4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem5Chbx)
                                .addComponent(basla5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem6Chbx)
                                .addComponent(basla6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem7Chbx)
                                .addComponent(basla7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem8Chbx)
                                .addComponent(basla8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem9Chbx)
                                .addComponent(basla9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem10Chbx)
                                .addComponent(basla10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem11Chbx)
                                .addComponent(basla11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem12Chbx)
                                .addComponent(basla12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem13Chbx)
                                .addComponent(basla13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem14Chbx)
                                .addComponent(basla14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(donemECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(donem15Chbx)
                                .addComponent(basla15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bit15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jScrollPane4))
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        donemECTabLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {donem10Chbx, donem11Chbx, donem12Chbx, donem13Chbx, donem14Chbx, donem15Chbx, donem4Chbx, donem5Chbx, donem6Chbx, donem7Chbx, donem8Chbx, donem9Chbx});

        jTabbedPane1.addTab("Dönem Ekle/Çıkar", donemECTab);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("BANT ŞEFİNİN İSMİ");

        sefTablosu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(sefTablosu);

        sefSil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/sil.png"))); // NOI18N
        sefSil.setText("SİL");
        sefSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sefSilActionPerformed(evt);
            }
        });

        sefKaydet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/kaydet.png"))); // NOI18N
        sefKaydet.setText("KAYDET");
        sefKaydet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sefKaydetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sefECTabLayout = new javax.swing.GroupLayout(sefECTab);
        sefECTab.setLayout(sefECTabLayout);
        sefECTabLayout.setHorizontalGroup(
            sefECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sefECTabLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(sefECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sefECTabLayout.createSequentialGroup()
                        .addGroup(sefECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sefECTabLayout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(bantSefininAdiTxt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sefKaydet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sefSil)))
                .addContainerGap())
        );

        sefECTabLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {sefKaydet, sefSil});

        sefECTabLayout.setVerticalGroup(
            sefECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sefECTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sefECTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bantSefininAdiTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sefSil)
                    .addComponent(sefKaydet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Bant Şefi Ekle/Çıkar", sefECTab);

        hedefTablosu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane15.setViewportView(hedefTablosu);

        hedefSil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/sil.png"))); // NOI18N
        hedefSil.setText("SİL");
        hedefSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hedefSilActionPerformed(evt);
            }
        });

        hedefKaydet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/kaydet.png"))); // NOI18N
        hedefKaydet.setText("KAYDET");
        hedefKaydet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hedefKaydetActionPerformed(evt);
            }
        });

        hedefDegistir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/degistir.png"))); // NOI18N
        hedefDegistir.setText("DEĞİŞTİR");
        hedefDegistir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hedefDegistirActionPerformed(evt);
            }
        });

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("KOLİ HEDEF");

        koliHdf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                koliHdfFocusGained(evt);
            }
        });

        dikimHdf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dikimHdfFocusGained(evt);
            }
        });

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("DİKİM HEDEF");

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("MODEL ADI");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modelHdf)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dikimHdf, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(koliHdf)
                    .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jPanel23Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dikimHdf, jLabel49, koliHdf});

        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(koliHdf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dikimHdf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(modelHdf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34)
                            .addComponent(jLabel49))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout hedeflerTabLayout = new javax.swing.GroupLayout(hedeflerTab);
        hedeflerTab.setLayout(hedeflerTabLayout);
        hedeflerTabLayout.setHorizontalGroup(
            hedeflerTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hedeflerTabLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(hedeflerTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(hedeflerTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(hedefDegistir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hedefKaydet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hedefSil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(732, Short.MAX_VALUE))
        );
        hedeflerTabLayout.setVerticalGroup(
            hedeflerTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hedeflerTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(hedeflerTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(hedeflerTabLayout.createSequentialGroup()
                        .addComponent(hedefKaydet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hedefSil)
                        .addGap(7, 7, 7)
                        .addComponent(hedefDegistir)))
                .addContainerGap(241, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Saatlik Hedefler", hedeflerTab);

        analizBasTarih.setDateFormatString("yyyy-MM-dd");

        analizBitTarih.setDateFormatString("yyyy-MM-dd");

        jToolBar7.setFloatable(false);
        jToolBar7.setRollover(true);

        opSayisiGoster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/goz.png"))); // NOI18N
        opSayisiGoster.setText("Operatör Sayısı");
        opSayisiGoster.setToolTipText("Her bir model için gerekli olan ortalama operatör adedi bilgisini göster. Belli bir tarih aralığı gerekir.");
        opSayisiGoster.setFocusable(false);
        opSayisiGoster.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        opSayisiGoster.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        opSayisiGoster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opSayisiGosterActionPerformed(evt);
            }
        });
        jToolBar7.add(opSayisiGoster);

        modelSaatlikAdetAnaliziGoster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/goz.png"))); // NOI18N
        modelSaatlikAdetAnaliziGoster.setText("Saatlik Adet");
        modelSaatlikAdetAnaliziGoster.setToolTipText("Model temelli saatlik üretim raporunu gösterir. Belli bir tarih aralığı gerekir.");
        modelSaatlikAdetAnaliziGoster.setFocusable(false);
        modelSaatlikAdetAnaliziGoster.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        modelSaatlikAdetAnaliziGoster.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        modelSaatlikAdetAnaliziGoster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelSaatlikAdetAnaliziGosterActionPerformed(evt);
            }
        });
        jToolBar7.add(modelSaatlikAdetAnaliziGoster);

        opSayisiRaporla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/raporla.png"))); // NOI18N
        opSayisiRaporla.setText("Operatör Sayısı");
        opSayisiRaporla.setToolTipText("Her bir model için gerekli olan ortalama operatör adedi bilgisini raporlar. Rapor dosyasına yazar. Belli bir tarih aralığı gerekir.");
        opSayisiRaporla.setFocusable(false);
        opSayisiRaporla.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        opSayisiRaporla.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        opSayisiRaporla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opSayisiRaporlaActionPerformed(evt);
            }
        });
        jToolBar7.add(opSayisiRaporla);

        modelSaatlikAdetAnaliziRaporla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/raporla.png"))); // NOI18N
        modelSaatlikAdetAnaliziRaporla.setText("Saatlik Adet");
        modelSaatlikAdetAnaliziRaporla.setToolTipText("Model temelli saatlik üretim raporunu hazırlar. Rapor dosyasına yazar. Belli bir tarih aralığı gerekir.");
        modelSaatlikAdetAnaliziRaporla.setFocusable(false);
        modelSaatlikAdetAnaliziRaporla.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        modelSaatlikAdetAnaliziRaporla.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        modelSaatlikAdetAnaliziRaporla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelSaatlikAdetAnaliziRaporlaActionPerformed(evt);
            }
        });
        jToolBar7.add(modelSaatlikAdetAnaliziRaporla);

        kisiBasiAdetRaporla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/raporla.png"))); // NOI18N
        kisiBasiAdetRaporla.setText("Adet/Operatör");
        kisiBasiAdetRaporla.setToolTipText("Kişi başına üretilen adet raporunu oluşturur. Rapor dosyasına yazar. Belli bir tarih aralığı gerekir.");
        kisiBasiAdetRaporla.setFocusable(false);
        kisiBasiAdetRaporla.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        kisiBasiAdetRaporla.setIconTextGap(0);
        kisiBasiAdetRaporla.setVerifyInputWhenFocusTarget(false);
        kisiBasiAdetRaporla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kisiBasiAdetRaporlaActionPerformed(evt);
            }
        });
        jToolBar7.add(kisiBasiAdetRaporla);

        grafikHazirla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/raporla2.png"))); // NOI18N
        grafikHazirla.setText("Dikim Grafikleri");
        grafikHazirla.setToolTipText("Dikim ve koli fark grafikleri oluşturur. \"Başlangıç Tarihi\" belirlenerek tek bir gün için grafik alınır.");
        grafikHazirla.setFocusable(false);
        grafikHazirla.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        grafikHazirla.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        grafikHazirla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grafikHazirlaActionPerformed(evt);
            }
        });
        jToolBar7.add(grafikHazirla);

        jScrollPane16.setMaximumSize(new java.awt.Dimension(1072, 643));

        analizTablosu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane16.setViewportView(analizTablosu);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel50.setText(" Başlangıç Tarihi");
        jLabel50.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText(" Bitiş Tarihi");
        jLabel51.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout analizTabLayout = new javax.swing.GroupLayout(analizTab);
        analizTab.setLayout(analizTabLayout);
        analizTabLayout.setHorizontalGroup(
            analizTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analizTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(analizTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1357, Short.MAX_VALUE)
                    .addGroup(analizTabLayout.createSequentialGroup()
                        .addGroup(analizTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jToolBar7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(analizTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, analizTabLayout.createSequentialGroup()
                                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(analizTabLayout.createSequentialGroup()
                                    .addComponent(analizBasTarih, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(analizBitTarih, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        analizTabLayout.setVerticalGroup(
            analizTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analizTabLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(analizTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jLabel51))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(analizTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(analizBitTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(analizBasTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(265, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Analiz", analizTab);

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "E-POSTA GÖNDERİM LİSTESİ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel47.setText("<html> <align=\"right\">E-posta Gönderim Listesi</html>");
        jLabel47.setToolTipText("");
        jLabel47.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        epostaTablosu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane14.setViewportView(epostaTablosu);

        kaydetEposta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/kaydet.png"))); // NOI18N
        kaydetEposta.setText("KAYDET");
        kaydetEposta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kaydetEpostaActionPerformed(evt);
            }
        });

        mailSahibi.setForeground(new java.awt.Color(153, 153, 153));
        mailSahibi.setText("E-posta Sahibi");
        mailSahibi.setToolTipText("");
        mailSahibi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mailSahibiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                mailSahibiFocusLost(evt);
            }
        });

        mailAdresi.setForeground(new java.awt.Color(153, 153, 153));
        mailAdresi.setText("Adresi (ornek@beybo.com.tr)");
        mailAdresi.setToolTipText("");
        mailAdresi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mailAdresiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                mailAdresiFocusLost(evt);
            }
        });

        silEposta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/sil.png"))); // NOI18N
        silEposta.setText("SİL");
        silEposta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                silEpostaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(silEposta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kaydetEposta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(mailSahibi, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mailAdresi, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(silEposta))
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kaydetEposta)
                    .addComponent(mailSahibi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mailAdresi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "E-POSTA AYARLARI", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel39.setText("Sunucu Adresi");
        jLabel39.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel40.setText("E-posta Konusu");
        jLabel40.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("E-posta Mesajı");
        jLabel41.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("Sunucu Portu");
        jLabel42.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        ekinFormati.add(pdf);
        pdf.setText("PDF (pdf)");

        ekinFormati.add(xlsx);
        xlsx.setText("Microsoft Excel (xlsx)");

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel32.setText("Gönderilen Dosya Türü");

        epostaMesajTxtField.setColumns(20);
        epostaMesajTxtField.setRows(5);
        jScrollPane12.setViewportView(epostaMesajTxtField);

        epostaAyarlaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/ayarla.png"))); // NOI18N
        epostaAyarlaBtn.setText("AYARLA");
        epostaAyarlaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                epostaAyarlaBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 125, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sunucuAdresiTxtField)
                            .addComponent(epostaKonuTxtField)
                            .addComponent(jScrollPane12)
                            .addComponent(portTxtField))
                        .addGap(39, 39, 39))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(pdf)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xlsx)
                        .addContainerGap(203, Short.MAX_VALUE))))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(epostaAyarlaBtn)
                .addContainerGap(232, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(sunucuAdresiTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(portTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(epostaKonuTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pdf)
                    .addComponent(xlsx)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(epostaAyarlaBtn)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RAPOR AYARLARI", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel43.setText("Rapor Adı");
        jLabel43.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel44.setText("Rapor Yazdırma Seçeneği");
        jLabel44.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        yazdirmaSecenegi.add(goster);
        goster.setText("Göster");
        goster.setToolTipText("Yazdırılacak olan belgeleri açarak gösterir, sonrasında belge yazdırılabilir.");

        yazdirmaSecenegi.add(sec);
        sec.setText("Seç");
        sec.setToolTipText("Sadece genel yazdırma şeçeneklerini şeçmeyi sağlar.");

        yazdirmaSecenegi.add(sadeceYazdir);
        sadeceYazdir.setText("Sadece Yazdır");
        sadeceYazdir.setToolTipText("Ayarlarla ilgili bir şey sormadan yazdırır.");

        raporAyarlaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/ayarla.png"))); // NOI18N
        raporAyarlaBtn.setText("AYARLA");
        raporAyarlaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                raporAyarlaBtnActionPerformed(evt);
            }
        });

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel35.setText("Raporlar Dosyası");
        jLabel35.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        raporDosTxtField.setEditable(false);

        dosyaSecBtn.setText("...");
        dosyaSecBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dosyaSecBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(raporAyarlaBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(goster)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sec)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sadeceYazdir)
                        .addGap(0, 110, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(raporDosTxtField)
                            .addComponent(raporAdiTxtField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dosyaSecBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel20Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {goster, sadeceYazdir, sec});

        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(raporDosTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dosyaSecBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(raporAdiTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(goster)
                    .addComponent(sec)
                    .addComponent(sadeceYazdir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(raporAyarlaBtn))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GENEL AYARLAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel36.setText(" Veri Tabanı Kullanıcı Adı");
        jLabel36.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel37.setText("Veri Tabanı Kullanıcı Şifresi");
        jLabel37.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel38.setText("Bağlantı Yolu");
        jLabel38.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        dbBaglantiYoluTxtField.setEditable(false);
        dbBaglantiYoluTxtField.setMaximumSize(new java.awt.Dimension(214, 214));

        dbSifreTxtField.setText("jPasswordField1");

        baglantiYoluSecBtn.setText("...");
        baglantiYoluSecBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baglantiYoluSecBtnActionPerformed(evt);
            }
        });

        genelAyarlaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/ayarla.png"))); // NOI18N
        genelAyarlaBtn.setText("AYARLA");
        genelAyarlaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genelAyarlaBtnActionPerformed(evt);
            }
        });

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel45.setText("E-posta Adresi");
        jLabel45.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel46.setText("E-posta Kullanıcı Şifresi");
        jLabel46.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        ePostaSifreTxtField.setText("jPasswordField1");

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel48.setText("E-posta Kullanıcı Adı");
        jLabel48.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ePostaKullaniciAdiTxtField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dbKullaniciTxtField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dbSifreTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ePostaAdresiTxtField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ePostaSifreTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dbBaglantiYoluTxtField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(baglantiYoluSecBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(genelAyarlaBtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dbBaglantiYoluTxtField, dbKullaniciTxtField, dbSifreTxtField, ePostaAdresiTxtField, ePostaKullaniciAdiTxtField, ePostaSifreTxtField});

        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(dbBaglantiYoluTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(baglantiYoluSecBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(dbKullaniciTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(dbSifreTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(ePostaKullaniciAdiTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(ePostaAdresiTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(ePostaSifreTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(genelAyarlaBtn))
        );

        javax.swing.GroupLayout ayarlarTabLayout = new javax.swing.GroupLayout(ayarlarTab);
        ayarlarTab.setLayout(ayarlarTabLayout);
        ayarlarTabLayout.setHorizontalGroup(
            ayarlarTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ayarlarTabLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(ayarlarTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ayarlarTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ayarlarTabLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel18, jPanel19});

        ayarlarTabLayout.setVerticalGroup(
            ayarlarTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ayarlarTabLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(ayarlarTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ayarlarTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel19.getAccessibleContext().setAccessibleDescription("");

        jTabbedPane1.addTab("Ayarlar", ayarlarTab);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );

        getAccessibleContext().setAccessibleName("Üretim İzleme v1.2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void hedefAyarla(JTextField dH, JTextField kH) {
        String dikimHedef = String.valueOf(Float.parseFloat(dH.getText()) * Float.parseFloat(sure.getText()));
        String koliHedef = String.valueOf(Float.parseFloat(kH.getText()) * Float.parseFloat(sure.getText()));
        dH.setText(dikimHedef);
        kH.setText(koliHedef);
    }

    public void swingAyarla(Boolean bl) {
        //Dikim hedeflerinin erişilebilirliği
        dH1.setEnabled(bl);
        dH2.setEnabled(bl);
        dH3.setEnabled(bl);
        dH4.setEnabled(bl);
        dH5.setEnabled(bl);
        dH6.setEnabled(bl);
        dH7.setEnabled(bl);
        dH8.setEnabled(bl);
        dH9.setEnabled(bl);
        dH10.setEnabled(bl);
        dH11.setEnabled(bl);
        dH12.setEnabled(bl);
        dH13.setEnabled(bl);
        //Koli hedeflerinin erişilebilirliği
        kH1.setEnabled(bl);
        kH2.setEnabled(bl);
        kH3.setEnabled(bl);
        kH4.setEnabled(bl);
        kH5.setEnabled(bl);
        kH6.setEnabled(bl);
        kH7.setEnabled(bl);
        kH8.setEnabled(bl);
        kH9.setEnabled(bl);
        kH10.setEnabled(bl);
        kH11.setEnabled(bl);
        kH12.setEnabled(bl);
        kH13.setEnabled(bl);
        //Model adlarının erişilebilirliği
        model1.setEnabled(bl);
        model2.setEnabled(bl);
        model3.setEnabled(bl);
        model4.setEnabled(bl);
        model5.setEnabled(bl);
        model6.setEnabled(bl);
        model7.setEnabled(bl);
        model8.setEnabled(bl);
        model9.setEnabled(bl);
        model10.setEnabled(bl);
        model11.setEnabled(bl);
        model12.setEnabled(bl);
        model13.setEnabled(bl);
        //Bant şeflerinin erişilebilirliği
        sefCBx1.setEnabled(bl);
        sefCBx2.setEnabled(bl);
        sefCBx3.setEnabled(bl);
        sefCBx4.setEnabled(bl);
        sefCBx5.setEnabled(bl);
        sefCBx6.setEnabled(bl);
        sefCBx7.setEnabled(bl);
        sefCBx8.setEnabled(bl);
        sefCBx9.setEnabled(bl);
        sefCBx10.setEnabled(bl);
        sefCBx11.setEnabled(bl);
        sefCBx12.setEnabled(bl);
        sefCBx13.setEnabled(bl);
        //Operatör edetlerinin erişilebilirliği
        opAdet1.setEnabled(bl);
        opAdet2.setEnabled(bl);
        opAdet3.setEnabled(bl);
        opAdet4.setEnabled(bl);
        opAdet5.setEnabled(bl);
        opAdet6.setEnabled(bl);
        opAdet7.setEnabled(bl);
        opAdet8.setEnabled(bl);
        opAdet9.setEnabled(bl);
        opAdet10.setEnabled(bl);
        opAdet11.setEnabled(bl);
        opAdet12.setEnabled(bl);
        opAdet13.setEnabled(bl);
        //Bant adlarının erişilebilirliği
        jCheckBox1.setEnabled(bl);
        if (jCheckBox1.isSelected()) {
            jCheckBox1.setForeground(Color.BLACK);
        } else if (kilit.isSelected()) {
            dA1.setEnabled(false);
            kA1.setEnabled(false);
            jCheckBox1.setForeground(Color.GRAY);
        }
        jCheckBox2.setEnabled(bl);
        if (jCheckBox2.isSelected()) {
            jCheckBox2.setForeground(Color.BLACK);
        } else if (kilit.isSelected()) {
            dA2.setEnabled(false);
            kA2.setEnabled(false);
            jCheckBox2.setForeground(Color.GRAY);
        }
        jCheckBox3.setEnabled(bl);
        if (jCheckBox3.isSelected()) {
            jCheckBox3.setForeground(Color.BLACK);
        } else if (kilit.isSelected()) {
            dA3.setEnabled(false);
            kA3.setEnabled(false);
            jCheckBox3.setForeground(Color.GRAY);
        }
        jCheckBox4.setEnabled(bl);
        if (jCheckBox4.isSelected()) {
            jCheckBox4.setForeground(Color.BLACK);
        } else if (kilit.isSelected()) {
            dA4.setEnabled(false);
            kA4.setEnabled(false);
            jCheckBox4.setForeground(Color.GRAY);
        }
        jCheckBox5.setEnabled(bl);
        if (jCheckBox5.isSelected()) {
            jCheckBox5.setForeground(Color.BLACK);
        } else if (kilit.isSelected()) {
            dA5.setEnabled(false);
            kA5.setEnabled(false);
            jCheckBox5.setForeground(Color.GRAY);
        }
        jCheckBox6.setEnabled(bl);
        if (jCheckBox6.isSelected()) {
            jCheckBox6.setForeground(Color.BLACK);
        } else if (kilit.isSelected()) {
            dA6.setEnabled(false);
            kA6.setEnabled(false);
            jCheckBox6.setForeground(Color.GRAY);
        }
        jCheckBox7.setEnabled(bl);
        if (jCheckBox7.isSelected()) {
            jCheckBox7.setForeground(Color.BLACK);
        } else if (kilit.isSelected()) {
            dA7.setEnabled(false);
            kA7.setEnabled(false);
            jCheckBox7.setForeground(Color.GRAY);
        }
        jCheckBox8.setEnabled(bl);
        if (jCheckBox8.isSelected()) {
            jCheckBox8.setForeground(Color.BLACK);
        } else if (kilit.isSelected()) {
            dA8.setEnabled(false);
            kA8.setEnabled(false);
            jCheckBox8.setForeground(Color.GRAY);
        }
        jCheckBox9.setEnabled(bl);
        if (jCheckBox9.isSelected()) {
            jCheckBox9.setForeground(Color.BLACK);
        } else if (kilit.isSelected()) {
            dA9.setEnabled(false);
            kA9.setEnabled(false);
            jCheckBox9.setForeground(Color.GRAY);
        }
        jCheckBox10.setEnabled(bl);
        if (jCheckBox10.isSelected()) {
            jCheckBox10.setForeground(Color.BLACK);
        } else if (kilit.isSelected()) {
            dA10.setEnabled(false);
            kA10.setEnabled(false);
            jCheckBox10.setForeground(Color.GRAY);
        }
        jCheckBox11.setEnabled(bl);
        if (jCheckBox11.isSelected()) {
            jCheckBox11.setForeground(Color.BLACK);
        } else if (kilit.isSelected()) {
            dA11.setEnabled(false);
            kA11.setEnabled(false);
            jCheckBox11.setForeground(Color.GRAY);
        }
        jCheckBox12.setEnabled(bl);
        if (jCheckBox12.isSelected()) {
            jCheckBox12.setForeground(Color.BLACK);
        } else if (kilit.isSelected()) {
            dA12.setEnabled(false);
            kA12.setEnabled(false);
            jCheckBox12.setForeground(Color.GRAY);
        }
        jCheckBox13.setEnabled(bl);
        if (jCheckBox13.isSelected()) {
            jCheckBox13.setForeground(Color.BLACK);
        } else if (kilit.isSelected()) {
            dA13.setEnabled(false);
            kA13.setEnabled(false);
            jCheckBox13.setForeground(Color.GRAY);
        }

    }

    private void sefKaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sefKaydetActionPerformed
        try {
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            st.execute("INSERT INTO bantsefleri(sefAdi) VALUES ('" + bantSefininAdiTxt.getText() + "')");
        } catch (SQLException ex) {
            //kutu/k kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        }
        yenileFonk();
    }//GEN-LAST:event_sefKaydetActionPerformed

    private void sefSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sefSilActionPerformed
        veriGirisi veriGirisiAnahtar = new veriGirisi();
        veriGirisiAnahtar.sefSil(secilenSatirIndexSef);
        yenileFonk();
    }//GEN-LAST:event_sefSilActionPerformed

    private void silBtnDzlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_silBtnDzlActionPerformed
        try {
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            //   bant adını eklemeyi unutma
            String cekilecekTablo;
            if (bugunKayiti.isSelected()) {
                cekilecekTablo = "saatliktakip";
            } else {
                cekilecekTablo = "saatliktakip_depo";
            }
            st.executeUpdate("DELETE FROM " + cekilecekTablo + " WHERE id='" + String.valueOf(duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 0)) + "'");

            duzeltmeTablosuYenileFonksiyonu();

        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_silBtnDzlActionPerformed

    private void degistirBtnDzlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_degistirBtnDzlActionPerformed
        try {
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            String cekilecekTablo;
            if (bugunKayiti.isSelected()) {
                cekilecekTablo = "saatliktakip";
            } else {
                cekilecekTablo = "saatliktakip_depo";
            }
            System.out.println("UPDATE " + cekilecekTablo + " SET bantAd='" + bantNoDzl.getText() + "',bantSef='" + bantSefiDzl.getText() + "',"
                    + "donem='" + donemDzl.getText() + "',tarih='" + tarihDzl.getText() + "',model='" + modelDzl.getText() + "',"
                    + "duyuru='" + duyuruDzl.getText() + "',dikimAdet='" + dikimAdetDzl.getText() + "',"
                    + "koliAdet='" + koliAdetDzl.getText() + "',dikimHedef='" + dikimHedefDzl.getText() + "',koliHedef='" + koliHedefDzl.getText() + "', "
                    + "operatorAdedi='" + opSayisiDzl.getText() + "' "
                    + "WHERE id='" + String.valueOf(duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 0)) + "'");

            st.executeUpdate("UPDATE " + cekilecekTablo + " SET bantAd='" + bantNoDzl.getText() + "',bantSef='" + bantSefiDzl.getText() + "',"
                    + "donem='" + donemDzl.getText() + "',tarih='" + tarihDzl.getText() + "',model='" + modelDzl.getText() + "',"
                    + "duyuru='" + duyuruDzl.getText() + "',dikimAdet='" + dikimAdetDzl.getText() + "',"
                    + "koliAdet='" + koliAdetDzl.getText() + "',dikimHedef='" + dikimHedefDzl.getText() + "',koliHedef='" + koliHedefDzl.getText() + "', "
                    + "operatorAdedi='" + opSayisiDzl.getText() + "' "
                    + "WHERE id='" + String.valueOf(duzeltmeTablosu.getValueAt(secilenSatirIndexDuzelme, 0)) + "'");

            duzeltmeTablosuYenileFonksiyonu();
        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_degistirBtnDzlActionPerformed

    private void filtreBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtreBtnActionPerformed
        try {
            duzeltmeTablosuDoldur();
        } catch (Exception e) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, e);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+e,Level.SEVERE);
            bugunKayiti.setSelected(true);
            sdFormat.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
            //Tarih Seçicisinin başlangıç değerini "bugün" olarak ayarladık.
            java.util.Date tarih = new java.util.Date();
            tarihDC1.setDate(tarih);
            duzeltmeTablosuDoldur();
            JOptionPane.showMessageDialog(rootPane, "Şöyle bir hata oluşmuş olabilir:\n1-Boş bir tablo seçildi\n2-Sebebi belirlenemeyen hata"
                    + "\nBUGÜNE AİT VERİLERE GERİ DÖNÜLDÜ", "UYARI", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_filtreBtnActionPerformed

    public void donemTipiSec() {
        String donemModelSecTampon = (String) donemModelSec.getSelectedItem();
        baglanti bagAnahtar = new baglanti();
        try {

            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            st.executeUpdate("UPDATE donem SET aktif='0'");
            con.close();
            con = bagAnahtar.ac();
            Statement st2 = con.createStatement();
            st2.executeUpdate("UPDATE donem SET aktif='1' WHERE donemModeli='" + donemModelSecTampon + "'");
        } catch (SQLException ex) {
            // kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        yenileDonemModelSecCbx();
        donemModelSec.setSelectedItem(donemModelSecTampon);
    }

    private void duyuruKaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_duyuruKaydetActionPerformed
        sdFormat.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
        String onayMesaji = "SEÇİLEN\nTARİH : " + sdFormat.format(tarihDC2.getDate()) + "\nDÖNEM : " + donemCBx2.getSelectedItem();
        int onay = JOptionPane.showOptionDialog(rootPane, onayMesaji, "UYARI", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new String[]{" EVET ", "HAYIR"}, " EVET ");
        if (onay == JOptionPane.YES_OPTION) {

            try {
                baglanti bagAnahtar = new baglanti();
                con = bagAnahtar.ac();
                Statement st = con.createStatement();
                //Bant adları ile slayt sıraları karışmasın diye yapılan uygulama
                Random rastgele = new Random();
                rastgele.ints();
                if (duyuru1.isSelected()) {
                    st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                            + "koliAdet,dikimHedef,koliHedef) values('" + duyuru1sira.getText() + "." + String.valueOf(rastgele.nextInt(50) + 1) + "','YOK','"
                            + (String) donemCBx2.getSelectedItem() + "','" + sdFormat.format(tarihDC2.getDate()) + "','YOK','"
                            + duyuruTArea1.getText() + "','0','0','0','0')");
                }
                if (duyuru2.isSelected()) {
                    st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                            + "koliAdet,dikimHedef,koliHedef) values('" + duyuru2sira.getText() + "." + String.valueOf(rastgele.nextInt(50) + 1) + "','YOK','"
                            + (String) donemCBx2.getSelectedItem() + "','" + sdFormat.format(tarihDC2.getDate()) + "','YOK','"
                            + duyuruTArea2.getText() + "','0','0','0','0')");
                }
                if (duyuru3.isSelected()) {
                    st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                            + "koliAdet,dikimHedef,koliHedef) values('" + duyuru3sira.getText() + "." + String.valueOf(rastgele.nextInt(50) + 1) + "','YOK','"
                            + (String) donemCBx2.getSelectedItem() + "','" + sdFormat.format(tarihDC2.getDate()) + "','YOK','"
                            + duyuruTArea3.getText() + "','0','0','0','0')");
                }
                if (duyuru4.isSelected()) {
                    st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                            + "koliAdet,dikimHedef,koliHedef) values('" + duyuru4sira.getText() + "." + String.valueOf(rastgele.nextInt(50) + 1) + "','YOK','"
                            + (String) donemCBx2.getSelectedItem() + "','" + sdFormat.format(tarihDC2.getDate()) + "','YOK','"
                            + duyuruTArea4.getText() + "','0','0','0','0')");
                }
                if (duyuru5.isSelected()) {
                    st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                            + "koliAdet,dikimHedef,koliHedef) values('" + duyuru5sira.getText() + "." + String.valueOf(rastgele.nextInt(50) + 1) + "','YOK','"
                            + (String) donemCBx2.getSelectedItem() + "','" + sdFormat.format(tarihDC2.getDate()) + "','YOK','"
                            + duyuruTArea5.getText() + "','0','0','0','0')");
                }
                if (duyuru6.isSelected()) {
                    st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                            + "koliAdet,dikimHedef,koliHedef) values('" + duyuru6sira.getText() + "." + String.valueOf(rastgele.nextInt(50) + 1) + "','YOK','"
                            + (String) donemCBx2.getSelectedItem() + "','" + sdFormat.format(tarihDC2.getDate()) + "','YOK','"
                            + duyuruTArea6.getText() + "','0','0','0','0')");
                }
                duzeltmeTablosuYenileFonksiyonu();

            } catch (SQLException ex) {
                //kutuk kutukAnahtar=new kutuk();
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
                //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
            } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }

    }//GEN-LAST:event_duyuruKaydetActionPerformed

    private void kopyalaBtnDzlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kopyalaBtnDzlActionPerformed
        try {
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            String cekilecekTablo;
            if (bugunKayiti.isSelected()) {
                cekilecekTablo = "saatliktakip";
            } else {
                cekilecekTablo = "saatliktakip_depo";
            }
            /*System.out.println("INSERT INTO " + cekilecekTablo + "(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,koliAdet,dikimHedef,koliHedef,operatorAdedi) VALUES('" + bantNoDzl.getText() + "','" + bantSefiDzl.getText()
                    + "','" + donemDzl.getText() + "','" + tarihDzl.getText() + "','" + modelDzl.getText() + "','"
                    + duyuruDzl.getText() + "','" + dikimAdetDzl.getText() + "','"
                    + koliAdetDzl.getText() + "','" + dikimHedefDzl.getText() + "','" + koliHedefDzl.getText() + "','" + opSayisiDzl.getText() + "')");*/

            st.executeUpdate("INSERT INTO " + cekilecekTablo + "(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,koliAdet,dikimHedef,koliHedef,operatorAdedi) VALUES('" + bantNoDzl.getText() + "','" + bantSefiDzl.getText() + "','"
                    + donemDzl.getText() + "','" + tarihDzl.getText() + "','" + modelDzl.getText() + "','"
                    + duyuruDzl.getText() + "','" + dikimAdetDzl.getText() + "','"
                    + koliAdetDzl.getText() + "','" + dikimHedefDzl.getText() + "','" + koliHedefDzl.getText() + "','" + opSayisiDzl.getText() + "')");
            duzeltmeTablosuYenileFonksiyonu();
        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_kopyalaBtnDzlActionPerformed

    private void italikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_italikActionPerformed
        //Seçilen kelime italik hale gelir
        duyuruTArea1.replaceSelection("<i>" + duyuruTArea1.getSelectedText() + "</i>");
    }//GEN-LAST:event_italikActionPerformed

    private void altCizgiliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altCizgiliActionPerformed
        //Seçilen kelime altı çizili hale gelir
        duyuruTArea1.replaceSelection("<u>" + duyuruTArea1.getSelectedText() + "</u>");
    }//GEN-LAST:event_altCizgiliActionPerformed

    private void paragrafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paragrafActionPerformed
        //alt satıra geçer
        duyuruTArea1.insert("<br>", duyuruTArea1.getCaretPosition());
    }//GEN-LAST:event_paragrafActionPerformed

    private void italik1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_italik1ActionPerformed
        duyuruTArea2.replaceSelection("<i>" + duyuruTArea2.getSelectedText() + "</i>");
    }//GEN-LAST:event_italik1ActionPerformed

    private void kalin2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kalin2ActionPerformed
        //Beautiful Product değeri girmek için kullanılır.
        duyuruTArea3.setText("bp=");
    }//GEN-LAST:event_kalin2ActionPerformed

    private void altCizgili1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altCizgili1ActionPerformed
        duyuruTArea2.replaceSelection("<u>" + duyuruTArea2.getSelectedText() + "</u>");
    }//GEN-LAST:event_altCizgili1ActionPerformed

    private void paragraf1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paragraf1ActionPerformed
        duyuruTArea2.insert("<br>", duyuruTArea2.getCaretPosition());
    }//GEN-LAST:event_paragraf1ActionPerformed

    private void duyuruTArea1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_duyuruTArea1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Enter basılınca <br> etiketi atar
            duyuruTArea1.insert("<br>", duyuruTArea1.getCaretPosition());
        }
    }//GEN-LAST:event_duyuruTArea1KeyPressed

    private void duyuruTArea2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_duyuruTArea2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Enter basılınca <br> etiketi atar
            duyuruTArea2.insert("<br>", duyuruTArea2.getCaretPosition());
        }
    }//GEN-LAST:event_duyuruTArea2KeyPressed

    private void duyuruTArea3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_duyuruTArea3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Enter basılınca <br> etiketi atar
            duyuruTArea3.insert("<br>", duyuruTArea3.getCaretPosition());
        }
    }//GEN-LAST:event_duyuruTArea3KeyPressed

    private void duyuruTArea4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_duyuruTArea4KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Enter basılınca <br> etiketi atar
            duyuruTArea4.insert("<br>", duyuruTArea4.getCaretPosition());
        }
    }//GEN-LAST:event_duyuruTArea4KeyPressed

    private void duyuruTArea5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_duyuruTArea5KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Enter basılınca <br> etiketi atar
            duyuruTArea5.insert("<br>", duyuruTArea5.getCaretPosition());
        }
    }//GEN-LAST:event_duyuruTArea5KeyPressed

    private void duyuruTArea6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_duyuruTArea6KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Enter basılınca <br> etiketi atar
            duyuruTArea6.insert("<br>", duyuruTArea6.getCaretPosition());
        }
    }//GEN-LAST:event_duyuruTArea6KeyPressed

    private void paragraf2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paragraf2ActionPerformed
        duyuruTArea3.insert("<br>", duyuruTArea3.getCaretPosition());
    }//GEN-LAST:event_paragraf2ActionPerformed

    private void italik2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_italik2ActionPerformed
        duyuruTArea3.replaceSelection("<i>" + duyuruTArea3.getSelectedText() + "</i>");
    }//GEN-LAST:event_italik2ActionPerformed

    private void kalin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kalin1ActionPerformed
        //Beautiful Product değeri girmek için kullanılır.
        duyuruTArea2.setText("bp=");
    }//GEN-LAST:event_kalin1ActionPerformed

    private void altCizgili2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altCizgili2ActionPerformed
        duyuruTArea3.replaceSelection("<u>" + duyuruTArea3.getSelectedText() + "</u>");
    }//GEN-LAST:event_altCizgili2ActionPerformed

    private void paragraf3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paragraf3ActionPerformed
        duyuruTArea4.insert("<br>", duyuruTArea4.getCaretPosition());
    }//GEN-LAST:event_paragraf3ActionPerformed

    private void paragraf4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paragraf4ActionPerformed
        duyuruTArea5.insert("<br>", duyuruTArea5.getCaretPosition());
    }//GEN-LAST:event_paragraf4ActionPerformed

    private void paragraf5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paragraf5ActionPerformed
        duyuruTArea6.insert("<br>", duyuruTArea6.getCaretPosition());
    }//GEN-LAST:event_paragraf5ActionPerformed

    private void italik3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_italik3ActionPerformed
        duyuruTArea4.replaceSelection("<i>" + duyuruTArea4.getSelectedText() + "</i>");
    }//GEN-LAST:event_italik3ActionPerformed

    private void kalin3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kalin3ActionPerformed
        //Beautiful Product değeri girmek için kullanılır.
        duyuruTArea4.setText("bp=");
    }//GEN-LAST:event_kalin3ActionPerformed

    private void altCizgili3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altCizgili3ActionPerformed
        duyuruTArea4.replaceSelection("<u>" + duyuruTArea4.getSelectedText() + "</u>");
    }//GEN-LAST:event_altCizgili3ActionPerformed

    private void italik4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_italik4ActionPerformed
        duyuruTArea5.replaceSelection("<i>" + duyuruTArea5.getSelectedText() + "</i>");
    }//GEN-LAST:event_italik4ActionPerformed

    private void kalin4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kalin4ActionPerformed
        //Beautiful Product değeri girmek için kullanılır.
        duyuruTArea5.setText("bp=");
    }//GEN-LAST:event_kalin4ActionPerformed

    private void altCizgili4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altCizgili4ActionPerformed
        duyuruTArea5.replaceSelection("<u>" + duyuruTArea5.getSelectedText() + "</u>");
    }//GEN-LAST:event_altCizgili4ActionPerformed

    private void italik5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_italik5ActionPerformed
        duyuruTArea6.replaceSelection("<i>" + duyuruTArea6.getSelectedText() + "</i>");
    }//GEN-LAST:event_italik5ActionPerformed

    private void kalin5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kalin5ActionPerformed
        //Beautiful Product değeri girmek için kullanılır.
        duyuruTArea6.setText("bp=");
    }//GEN-LAST:event_kalin5ActionPerformed

    private void altCizgili5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altCizgili5ActionPerformed
        duyuruTArea6.replaceSelection("<u>" + duyuruTArea6.getSelectedText() + "</u>");
    }//GEN-LAST:event_altCizgili5ActionPerformed

    private void yeniDonemAdiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_yeniDonemAdiFocusLost
        if (yeniDonemAdi.getText().isEmpty()) {
            yeniDonemAdi.setForeground(Color.decode("#999999"));
            yeniDonemAdi.setText("Yeni Dönem Adı");
        }
    }//GEN-LAST:event_yeniDonemAdiFocusLost

    private void yeniDonemAdiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_yeniDonemAdiFocusGained
        yeniDonemAdi.setText("");
        yeniDonemAdi.setForeground(Color.BLACK);
    }//GEN-LAST:event_yeniDonemAdiFocusGained

    private void yeniDonemEkleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniDonemEkleBtnActionPerformed
        //Yeni dönemi oluştur ve girilmiş bilgileri veritabanına işle
        donemGirisi("yeniDonem");
        yenileDonemModelSecCbx();
    }//GEN-LAST:event_yeniDonemEkleBtnActionPerformed

    private void donemSilBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donemSilBtnActionPerformed
        veriGirisi veriGirisiAnahtar = new veriGirisi();
        veriGirisiAnahtar.donemSil(secilenSatirIndexDonem, donemTipiEtiketi.getText().substring(6));
        yenileDonemModelSecCbx();
    }//GEN-LAST:event_donemSilBtnActionPerformed

    private void donemKaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donemKaydetActionPerformed
        donemGirisi("simdikiDonem");
        yenileDonemModelSecCbx();
    }//GEN-LAST:event_donemKaydetActionPerformed

    private void donem1ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem1ChbxActionPerformed
        if (donem1Chbx.isSelected()) {
            basla1.setEnabled(true);
            bit1.setEnabled(true);
        } else {
            basla1.setEnabled(false);
            bit1.setEnabled(false);
        }
    }//GEN-LAST:event_donem1ChbxActionPerformed

    private void donem2ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem2ChbxActionPerformed
        if (donem2Chbx.isSelected()) {
            basla2.setEnabled(true);
            bit2.setEnabled(true);
        } else {
            basla2.setEnabled(false);
            bit2.setEnabled(false);
        }
    }//GEN-LAST:event_donem2ChbxActionPerformed

    private void donem3ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem3ChbxActionPerformed
        if (donem3Chbx.isSelected()) {
            basla3.setEnabled(true);
            bit3.setEnabled(true);
        } else {
            basla3.setEnabled(false);
            bit3.setEnabled(false);
        }
    }//GEN-LAST:event_donem3ChbxActionPerformed

    private void donem4ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem4ChbxActionPerformed
        if (donem4Chbx.isSelected()) {
            basla4.setEnabled(true);
            bit4.setEnabled(true);
        } else {
            basla4.setEnabled(false);
            bit4.setEnabled(false);
        }
    }//GEN-LAST:event_donem4ChbxActionPerformed

    private void donem5ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem5ChbxActionPerformed
        if (donem5Chbx.isSelected()) {
            basla5.setEnabled(true);
            bit5.setEnabled(true);
        } else {
            basla5.setEnabled(false);
            bit5.setEnabled(false);
        }
    }//GEN-LAST:event_donem5ChbxActionPerformed

    private void donem6ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem6ChbxActionPerformed
        if (donem6Chbx.isSelected()) {
            basla6.setEnabled(true);
            bit6.setEnabled(true);
        } else {
            basla6.setEnabled(false);
            bit6.setEnabled(false);
        }
    }//GEN-LAST:event_donem6ChbxActionPerformed

    private void donem7ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem7ChbxActionPerformed
        if (donem7Chbx.isSelected()) {
            basla7.setEnabled(true);
            bit7.setEnabled(true);
        } else {
            basla7.setEnabled(false);
            bit7.setEnabled(false);
        }
    }//GEN-LAST:event_donem7ChbxActionPerformed

    private void donem8ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem8ChbxActionPerformed
        if (donem8Chbx.isSelected()) {
            basla8.setEnabled(true);
            bit8.setEnabled(true);
        } else {
            basla8.setEnabled(false);
            bit8.setEnabled(false);
        }
    }//GEN-LAST:event_donem8ChbxActionPerformed

    private void donem9ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem9ChbxActionPerformed
        if (donem9Chbx.isSelected()) {
            basla9.setEnabled(true);
            bit9.setEnabled(true);
        } else {
            basla9.setEnabled(false);
            bit9.setEnabled(false);
        }
    }//GEN-LAST:event_donem9ChbxActionPerformed

    private void donem10ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem10ChbxActionPerformed
        if (donem10Chbx.isSelected()) {
            basla10.setEnabled(true);
            bit10.setEnabled(true);
        } else {
            basla10.setEnabled(false);
            bit10.setEnabled(false);
        }
    }//GEN-LAST:event_donem10ChbxActionPerformed

    private void donem11ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem11ChbxActionPerformed
        if (donem11Chbx.isSelected()) {
            basla11.setEnabled(true);
            bit11.setEnabled(true);
        } else {
            basla11.setEnabled(false);
            bit11.setEnabled(false);
        }
    }//GEN-LAST:event_donem11ChbxActionPerformed

    private void donem12ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem12ChbxActionPerformed
        if (donem12Chbx.isSelected()) {
            basla12.setEnabled(true);
            bit12.setEnabled(true);
        } else {
            basla12.setEnabled(false);
            bit12.setEnabled(false);
        }
    }//GEN-LAST:event_donem12ChbxActionPerformed

    private void donem13ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem13ChbxActionPerformed
        if (donem13Chbx.isSelected()) {
            basla13.setEnabled(true);
            bit13.setEnabled(true);
        } else {
            basla13.setEnabled(false);
            bit13.setEnabled(false);
        }
    }//GEN-LAST:event_donem13ChbxActionPerformed

    private void donem14ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem14ChbxActionPerformed
        if (donem14Chbx.isSelected()) {
            basla14.setEnabled(true);
            bit14.setEnabled(true);
        } else {
            basla14.setEnabled(false);
            bit14.setEnabled(false);
        }
    }//GEN-LAST:event_donem14ChbxActionPerformed

    private void donem15ChbxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donem15ChbxActionPerformed
        if (donem15Chbx.isSelected()) {
            basla15.setEnabled(true);
            bit15.setEnabled(true);
        } else {
            basla15.setEnabled(false);
            bit15.setEnabled(false);
        }
    }//GEN-LAST:event_donem15ChbxActionPerformed

    private void gecmisKayitiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gecmisKayitiActionPerformed
        if (gecmisKayiti.isSelected()) {
            tarihDC1.setEnabled(true);
        }
    }//GEN-LAST:event_gecmisKayitiActionPerformed

    private void bugunKayitiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bugunKayitiActionPerformed
        if (bugunKayiti.isSelected()) {
            tarihDC1.setEnabled(false);
        }
        sdFormat.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
        //Tarih Seçicisinin başlangıç değerini "bugün" olarak ayarladık.
        java.util.Date tarih = new java.util.Date();
        tarihDC1.setDate(tarih);
    }//GEN-LAST:event_bugunKayitiActionPerformed

    private void dosyaSecBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dosyaSecBtnActionPerformed

        String raporlarDosyaYolu = raporDosTxtField.getText();
        File dosya = new File(raporlarDosyaYolu);
        if (dosya.isDirectory()) {
            JFileChooser dosyaSec = new JFileChooser(raporlarDosyaYolu);
            dosyaSec.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int geriBesleme = dosyaSec.showDialog(dosyaSec, "SEÇ");
            if (geriBesleme == JFileChooser.APPROVE_OPTION) {
                raporDosTxtField.setText(String.valueOf(dosyaSec.getSelectedFile().getAbsolutePath()));
            }

        } else {
            JFileChooser dosyaSec = new JFileChooser();
            dosyaSec.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            dosyaSec.showDialog(dosyaSec, "SEÇ");
            int geriBesleme = dosyaSec.showDialog(dosyaSec, "SEÇ");
            if (geriBesleme == JFileChooser.APPROVE_OPTION) {
                raporDosTxtField.setText(String.valueOf(dosyaSec.getSelectedFile().getAbsolutePath()));
            }
        }
    }//GEN-LAST:event_dosyaSecBtnActionPerformed

    private void baglantiYoluSecBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baglantiYoluSecBtnActionPerformed
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final JFrame baglantiYolu = new JFrame("Bağlantı Yolu Belirleme");
        baglantiYolu.setLayout(new GridLayout(3, 3));
        JLabel surucuEtiketi = new JLabel("Sürücü Adı");
        JLabel sunucuEtiketi = new JLabel("Sunucu ve Port");
        JLabel dbAdiEtiketi = new JLabel("Veri Tabanı Adı");
        JLabel surucu = new JLabel("jdbc:mysql://");
        final JTextField sunucu = new JTextField();
        final JTextField dbAdi = new JTextField();
        dbAdi.setToolTipText("ornek.com veya 127.0.0.1:80 şeklinde yazılır.");

        baglantiYolu.setResizable(false);
        baglantiYolu.add(surucuEtiketi);

        baglantiYolu.add(sunucuEtiketi);
        baglantiYolu.add(dbAdiEtiketi);
        baglantiYolu.add(surucu);
        baglantiYolu.add(sunucu);
        baglantiYolu.add(dbAdi);
        JButton kabulBtn = new JButton("KABUL");

        kabulBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dbBaglantiYoluTxtField.setText("jdbc:mysql://" + sunucu.getText() + "/" + dbAdi.getText());
                baglantiYolu.dispose();
            }
        });
        baglantiYolu.add(new JLabel());
        baglantiYolu.add(kabulBtn);
        baglantiYolu.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        baglantiYolu.setBounds(baglantiYoluSecBtn.getX() - 40, baglantiYoluSecBtn.getY() + 30, 370, 100);
        baglantiYolu.setVisible(true);

    }//GEN-LAST:event_baglantiYoluSecBtnActionPerformed

    private void genelAyarlaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genelAyarlaBtnActionPerformed
        try {
            islevseller islevAnahtar = new islevseller();
            islevAnahtar.ayarla("baglanti", dbBaglantiYoluTxtField.getText());
            islevAnahtar.ayarla("kullanici", dbKullaniciTxtField.getText());
            islevAnahtar.ayarla("parola", dbSifreTxtField.getText());
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            st.execute("DELETE FROM mail_listesi WHERE aktifKullanici='1'");
            st.execute("INSERT INTO mail_listesi(mailSahibi, mailAdresi, sifre, aktifKullanici) VALUES('"
                    + ePostaKullaniciAdiTxtField.getText() + "','"
                    + ePostaAdresiTxtField.getText() + "','" + ePostaSifreTxtField.getText() + "','1')");

        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_genelAyarlaBtnActionPerformed

    private void epostaAyarlaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_epostaAyarlaBtnActionPerformed
        islevseller islevAnahtar = new islevseller();
        islevAnahtar.ayarla("mailSunucuAdresi", sunucuAdresiTxtField.getText());
        islevAnahtar.ayarla("mailPort", portTxtField.getText());
        islevAnahtar.ayarla("mailKonu", epostaKonuTxtField.getText());
        islevAnahtar.ayarla("mailGovdesi", epostaMesajTxtField.getText());
        if (pdf.isSelected()) {
            islevAnahtar.ayarla("ekinFormati", ".pdf");
        }
        if (xlsx.isSelected()) {
            islevAnahtar.ayarla("ekinFormati", ".xlsx");
        }

    }//GEN-LAST:event_epostaAyarlaBtnActionPerformed

    private void raporAyarlaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_raporAyarlaBtnActionPerformed
        islevseller islevAnahtar = new islevseller();
        islevAnahtar.ayarla("raporKayitYeri", raporDosTxtField.getText());
        islevAnahtar.ayarla("raporAdi", raporAdiTxtField.getText());
        if (goster.isSelected()) {
            islevAnahtar.ayarla("raporYazdirmaSecenegi", "göster");
        }
        if (sec.isSelected()) {
            islevAnahtar.ayarla("raporYazdirmaSecenegi", "seç");
        }
        if (sadeceYazdir.isSelected()) {
            islevAnahtar.ayarla("raporYazdirmaSecenegi", "sadeceYazdır");
        }
    }//GEN-LAST:event_raporAyarlaBtnActionPerformed

    private void silEpostaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_silEpostaActionPerformed
        veriGirisi veriGirisiAnahtar = new veriGirisi();
        veriGirisiAnahtar.epostaSil(secilenSatirIndexEposta);
        yenileEpostaTablosuFonksiyonu();
    }//GEN-LAST:event_silEpostaActionPerformed

    private void kaydetEpostaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kaydetEpostaActionPerformed
        try {
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            st.execute("INSERT INTO mail_listesi(mailSahibi,mailAdresi) VALUES ('" + mailSahibi.getText() + "','" + mailAdresi.getText() + "')");
        } catch (SQLException ex) {
            // kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        yenileEpostaTablosuFonksiyonu();
    }//GEN-LAST:event_kaydetEpostaActionPerformed

    private void mailSahibiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mailSahibiFocusGained
        mailSahibi.setText("");
        mailSahibi.setForeground(Color.BLACK);
    }//GEN-LAST:event_mailSahibiFocusGained

    private void mailSahibiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mailSahibiFocusLost
        if (mailSahibi.getText().isEmpty()) {
            mailSahibi.setForeground(Color.decode("#999999"));
            mailSahibi.setText("E-posta Sahibi");
        }
    }//GEN-LAST:event_mailSahibiFocusLost

    private void mailAdresiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mailAdresiFocusGained
        mailAdresi.setText("");
        mailAdresi.setForeground(Color.BLACK);
    }//GEN-LAST:event_mailAdresiFocusGained

    private void mailAdresiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mailAdresiFocusLost
        if (mailAdresi.getText().isEmpty()) {
            mailAdresi.setForeground(Color.decode("#999999"));
            mailAdresi.setText("Adresi (ornek@beybo.com.tr)");
        }
    }//GEN-LAST:event_mailAdresiFocusLost

    private void hedefSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hedefSilActionPerformed
        veriGirisi veriGirisiAnahtar = new veriGirisi();
        veriGirisiAnahtar.hedefSil(secilenSatirIndexHedef);
        hedefYenileFonk();

    }//GEN-LAST:event_hedefSilActionPerformed

    private void hedefKaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hedefKaydetActionPerformed
        try {
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            st.execute("INSERT INTO hedeflertablosu(modelAdi,dikimHedef,koliHedef) "
                    + "VALUES ('" + modelHdf.getText() + "','" + dikimHdf.getText() + "','" + koliHdf.getText() + "')");
        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        hedefYenileFonk();

    }//GEN-LAST:event_hedefKaydetActionPerformed

    private void hedefDegistirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hedefDegistirActionPerformed
        try {
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();

            System.out.println("UPDATE hedeflertablosu SET modelAdi='" + modelHdf.getText() + "',dikimHedef='" + dikimHdf.getText() + "',"
                    + "koliHedef='" + koliHdf.getText() + "' "
                    + "WHERE modelAdi='" + String.valueOf(hedefTablosu.getValueAt(secilenSatirIndexHedef, 0)) + "'");

            st.executeUpdate("UPDATE hedeflertablosu SET modelAdi='" + modelHdf.getText() + "',dikimHedef='" + dikimHdf.getText() + "',"
                    + "koliHedef='" + koliHdf.getText() + "' "
                    + "WHERE modelAdi='" + String.valueOf(hedefTablosu.getValueAt(secilenSatirIndexHedef, 0)) + "'");

            hedefYenileFonk();

        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_hedefDegistirActionPerformed

    private void dikimHdfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dikimHdfFocusGained
        dikimHdf.selectAll();
    }//GEN-LAST:event_dikimHdfFocusGained

    private void koliHdfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_koliHdfFocusGained
        koliHdf.selectAll();
    }//GEN-LAST:event_koliHdfFocusGained

    private void kalinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kalinActionPerformed
        //Beautiful Product değeri girmek için kullanılır.
        duyuruTArea1.setText("bp=");
    }//GEN-LAST:event_kalinActionPerformed

    private void opSayisiGosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opSayisiGosterActionPerformed
        try {
            bantUretimAnalizi BUAanahtar = new bantUretimAnalizi();
            ResultSet rs = BUAanahtar.opAnalizi(sdFormat.format(analizBasTarih.getDate()),
                    sdFormat.format(analizBitTarih.getDate()));
            analizTablosu.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumnModel tcm = analizTablosu.getColumnModel();
            tcm.getColumn(0).setHeaderValue("MODEL");
            tcm.getColumn(1).setHeaderValue("ORTALAMA OPERATÖR ADEDİ");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Tarih Girilmemiş");
        }
    }//GEN-LAST:event_opSayisiGosterActionPerformed

    private void opSayisiRaporlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opSayisiRaporlaActionPerformed
        try {

            bantUretimAnalizi BUAanahtar = new bantUretimAnalizi();

            BUAanahtar.opRaporla(sdFormat.format(analizBasTarih.getDate()),
                    sdFormat.format(analizBitTarih.getDate()));
        } catch (JRException ex) {
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Tarih Girilmemiş");
        }

    }//GEN-LAST:event_opSayisiRaporlaActionPerformed

    private void modelSaatlikAdetAnaliziGosterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelSaatlikAdetAnaliziGosterActionPerformed
        try {
            bantUretimAnalizi BUAanahtar = new bantUretimAnalizi();
            ResultSet rs = BUAanahtar.saatlikAdetAnalizi(sdFormat.format(analizBasTarih.getDate()),
                    sdFormat.format(analizBitTarih.getDate()));
            analizTablosu.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumnModel tcm = analizTablosu.getColumnModel();
            tcm.getColumn(0).setHeaderValue("MODEL");
            tcm.getColumn(1).setHeaderValue("MIN(dikimAdet)");
            tcm.getColumn(2).setHeaderValue("MAX(dikimAdet)");
            tcm.getColumn(3).setHeaderValue("ORTALAMA DIKIM ADEDİ");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Tarih Girilmemiş");
        }
    }//GEN-LAST:event_modelSaatlikAdetAnaliziGosterActionPerformed

    private void modelSaatlikAdetAnaliziRaporlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelSaatlikAdetAnaliziRaporlaActionPerformed
        try {
            bantUretimAnalizi BUAanahtar = new bantUretimAnalizi();

            BUAanahtar.saatlikAdetRaporla(sdFormat.format(analizBasTarih.getDate()),
                    sdFormat.format(analizBitTarih.getDate()));
        } catch (JRException ex) {
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Tarih Girilmemiş");
        }
    }//GEN-LAST:event_modelSaatlikAdetAnaliziRaporlaActionPerformed

    private void kisiBasiAdetRaporlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kisiBasiAdetRaporlaActionPerformed
        try {
            bantUretimAnalizi BUAanahtar = new bantUretimAnalizi();

            BUAanahtar.kisiBasiAdet(sdFormat.format(analizBasTarih.getDate()),
                    sdFormat.format(analizBitTarih.getDate()));
        } catch (JRException ex) {
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Tarih Girilmemiş");
        }

    }//GEN-LAST:event_kisiBasiAdetRaporlaActionPerformed

    private void grafikHazirlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grafikHazirlaActionPerformed
        try {
            bantUretimAnalizi BUAanahtar = new bantUretimAnalizi();
            sdFormat.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
            String tarih = sdFormat.format(analizBasTarih.getDate());

            String sqlTabloAdi;

            //Tarih Seçicisinin başlangıç değerini "bugün" olarak ayarladık.
            java.util.Date bugun = new java.util.Date();
            String bugunString = sdFormat.format(bugun);
            if (bugunString.equals(tarih)) {
                sqlTabloAdi = "saatliktakip";
            } else {
                sqlTabloAdi = "saatliktakip_depo";
            }

            BUAanahtar.grafikHazirla(tarih, sqlTabloAdi);
        } catch (JRException ex) {
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Tarih Girilmemiş");
        }
    }//GEN-LAST:event_grafikHazirlaActionPerformed

    private void gosterBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gosterBtnActionPerformed
        String yol = null, tarih = sdFormat.format(tarihDC.getDate()).replaceAll("-", "_");

        islevseller islevAnahtar = new islevseller();
        if (".pdf".equals(islevAnahtar.ayarAl("ekinFormati"))) {
            yol = islevAnahtar.ayarAl("raporKayitYeri") + "/" + islevAnahtar.ayarAl("raporAdi") + "_" + tarih + ".pdf";
        } else if (".xlsx".equals(islevAnahtar.ayarAl("ekinFormati"))) {
            yol = islevAnahtar.ayarAl("raporKayitYeri") + "/" + islevAnahtar.ayarAl("raporAdi") + "_" + tarih + ".xlsx";
        } else {
            JOptionPane.showMessageDialog(rootPane, "Dosya Tanımlanamadı veya Bulunamadı.");
        }
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(yol);
                Desktop.getDesktop().open(myFile);
            } catch (IOException | IllegalArgumentException ex) {
                //kutuk kutukAnahtar=new kutuk();
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
                //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
                JOptionPane.showMessageDialog(rootPane, "Dosya yok yada başka bir işlem için kullanılıyor. \n" + ex,
                        "HATA", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_gosterBtnActionPerformed

    private void yazdirBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yazdirBtnActionPerformed
        try {

            sdFormat.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
            String tarih = sdFormat.format(tarihDC.getDate());
            raporDerleyicisi rdAhantar = new raporDerleyicisi();
            String sqlTabloAdi;

            //Tarih Seçicisinin başlangıç değerini "bugün" olarak ayarladık.
            java.util.Date bugun = new java.util.Date();
            String bugunString = sdFormat.format(bugun);
            if (bugunString.equals(tarih)) {
                sqlTabloAdi = "saatliktakip";
            } else {
                sqlTabloAdi = "saatliktakip_depo";
            }

            rdAhantar.raporla(tarih, sqlTabloAdi, true);

        } catch (JRException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        }
    }//GEN-LAST:event_yazdirBtnActionPerformed

    private void rapolaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rapolaBtnActionPerformed
        try {

            sdFormat.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
            String tarih = sdFormat.format(tarihDC.getDate());
            raporDerleyicisi rdAhantar = new raporDerleyicisi();
            String sqlTabloAdi;

            //Tarih Seçicisinin başlangıç değerini "bugün" olarak ayarladık.
            java.util.Date bugun = new java.util.Date();
            String bugunString = sdFormat.format(bugun);
            if (bugunString.equals(tarih)) {
                sqlTabloAdi = "saatliktakip";
            } else {
                sqlTabloAdi = "saatliktakip_depo";
            }

            rdAhantar.raporla(tarih, sqlTabloAdi, false);
            //Mail gönderim modülünü çalıştır.
            mailGonder(tarih);

        } catch (JRException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            // kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        }
    }//GEN-LAST:event_rapolaBtnActionPerformed

    private void veriKaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_veriKaydetActionPerformed
        sdFormat.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
        String onayMesaji = "SEÇİLEN\nTARİH : " + sdFormat.format(tarihDC.getDate()) + "\nDÖNEM : " + donemCBx.getSelectedItem();
        int onay = JOptionPane.showOptionDialog(rootPane, onayMesaji, "UYARI", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new String[]{" EVET ", "HAYIR"}, " EVET ");
        if (onay == JOptionPane.YES_OPTION) {

            try {
                baglanti bagAnahtar = new baglanti();
                con = bagAnahtar.ac();
                Statement st = con.createStatement();
                String donem = (String) donemCBx.getSelectedItem();
                String tarih = sdFormat.format(tarihDC.getDate());
                //                int sureBilgisi = sureHesaplamasiGonder(donem.substring(0, 4), donem.substring(6, 10));
                mesajAlani.setForeground(Color.BLACK);
                mesajAlani.setText("Mesaj:\n");
                String mesaj = "";
                boolean kontrol = true;
                if (jCheckBox1.isSelected()) {
                    if (model1.getText().isEmpty() || dA1.getText().isEmpty() || kA1.getText().isEmpty()
                            || dH1.getText().isEmpty() || kH1.getText().isEmpty() || opAdet1.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Doldurulması gereken boşluklar var!\n"
                                + "Gerekli boşluklar doldurulmadığı için kayıt işlemi gerçekleştirilemedi");
                        kontrol = false;
                    }

                }
                if (jCheckBox2.isSelected()) {
                    if (model2.getText().isEmpty() || dA2.getText().isEmpty() || kA2.getText().isEmpty()
                            || dH2.getText().isEmpty() || kH2.getText().isEmpty() || opAdet2.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Doldurulması gereken boşluklar var!\n"
                                + "Gerekli boşluklar doldurulmadığı için kayıt işlemi gerçekleştirilemedi");
                        kontrol = false;
                    }

                }
                if (jCheckBox3.isSelected()) {
                    if (model3.getText().isEmpty() || dA3.getText().isEmpty() || kA3.getText().isEmpty()
                            || dH3.getText().isEmpty() || kH3.getText().isEmpty() || opAdet3.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Doldurulması gereken boşluklar var!\n"
                                + "Gerekli boşluklar doldurulmadığı için kayıt işlemi gerçekleştirilemedi");
                        kontrol = false;
                    }

                }
                if (jCheckBox4.isSelected()) {
                    if (model4.getText().isEmpty() || dA4.getText().isEmpty() || kA4.getText().isEmpty()
                            || dH4.getText().isEmpty() || kH4.getText().isEmpty() || opAdet4.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Doldurulması gereken boşluklar var!\n"
                                + "Gerekli boşluklar doldurulmadığı için kayıt işlemi gerçekleştirilemedi");
                        kontrol = false;
                    }

                }
                if (jCheckBox5.isSelected()) {
                    if (model5.getText().isEmpty() || dA5.getText().isEmpty() || kA5.getText().isEmpty()
                            || dH5.getText().isEmpty() || kH5.getText().isEmpty() || opAdet5.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Doldurulması gereken boşluklar var!\n"
                                + "Gerekli boşluklar doldurulmadığı için kayıt işlemi gerçekleştirilemedi");
                        kontrol = false;
                    }

                }
                if (jCheckBox6.isSelected()) {
                    if (model6.getText().isEmpty() || dA6.getText().isEmpty() || kA6.getText().isEmpty()
                            || dH6.getText().isEmpty() || kH6.getText().isEmpty() || opAdet6.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Doldurulması gereken boşluklar var!\n"
                                + "Gerekli boşluklar doldurulmadığı için kayıt işlemi gerçekleştirilemedi");
                        kontrol = false;
                    }

                }
                if (jCheckBox7.isSelected()) {
                    if (model7.getText().isEmpty() || dA7.getText().isEmpty() || kA7.getText().isEmpty()
                            || dH7.getText().isEmpty() || kH7.getText().isEmpty() || opAdet7.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Doldurulması gereken boşluklar var!\n"
                                + "Gerekli boşluklar doldurulmadığı için kayıt işlemi gerçekleştirilemedi");
                        kontrol = false;
                    }

                }
                if (jCheckBox8.isSelected()) {
                    if (model8.getText().isEmpty() || dA8.getText().isEmpty() || kA8.getText().isEmpty()
                            || dH8.getText().isEmpty() || kH8.getText().isEmpty() || opAdet8.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Doldurulması gereken boşluklar var!\n"
                                + "Gerekli boşluklar doldurulmadığı için kayıt işlemi gerçekleştirilemedi");
                        kontrol = false;
                    }

                }
                if (jCheckBox9.isSelected()) {
                    if (model9.getText().isEmpty() || dA9.getText().isEmpty() || kA9.getText().isEmpty()
                            || dH9.getText().isEmpty() || kH9.getText().isEmpty() || opAdet9.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Doldurulması gereken boşluklar var!\n"
                                + "Gerekli boşluklar doldurulmadığı için kayıt işlemi gerçekleştirilemedi");
                        kontrol = false;
                    }

                }
                if (jCheckBox10.isSelected()) {
                    if (model10.getText().isEmpty() || dA10.getText().isEmpty() || kA10.getText().isEmpty()
                            || dH10.getText().isEmpty() || kH10.getText().isEmpty() || opAdet10.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Doldurulması gereken boşluklar var!\n"
                                + "Gerekli boşluklar doldurulmadığı için kayıt işlemi gerçekleştirilemedi");
                        kontrol = false;
                    }

                }
                if (jCheckBox11.isSelected()) {
                    if (model11.getText().isEmpty() || dA11.getText().isEmpty() || kA11.getText().isEmpty()
                            || dH11.getText().isEmpty() || kH11.getText().isEmpty() || opAdet11.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Doldurulması gereken boşluklar var!\n"
                                + "Gerekli boşluklar doldurulmadığı için kayıt işlemi gerçekleştirilemedi");
                        kontrol = false;
                    }

                }
                if (jCheckBox12.isSelected()) {
                    if (model12.getText().isEmpty() || dA12.getText().isEmpty() || kA12.getText().isEmpty()
                            || dH12.getText().isEmpty() || kH12.getText().isEmpty() || opAdet12.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Doldurulması gereken boşluklar var!\n"
                                + "Gerekli boşluklar doldurulmadığı için kayıt işlemi gerçekleştirilemedi");
                        kontrol = false;
                    }

                }
                if (jCheckBox13.isSelected()) {
                    if (model13.getText().isEmpty() || dA13.getText().isEmpty() || kA13.getText().isEmpty()
                            || dH13.getText().isEmpty() || kH13.getText().isEmpty() || opAdet13.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Doldurulması gereken boşluklar var!\n"
                                + "Gerekli boşluklar doldurulmadığı için kayıt işlemi gerçekleştirilemedi");
                        kontrol = false;
                    }

                }

                if (kontrol) {
                    if (jCheckBox1.isSelected()) {
                        st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('01','" + (String) sefCBx1.getSelectedItem() + "','"
                                + donem + "','" + tarih + "','"
                                + model1.getText() + "','boş','" + dA1.getText() + "','" + kA1.getText() + "','" + dH1.getText() + "','"
                                + kH1.getText() + "','" + opAdet1.getText() + "')");
                        mesaj += "1. Bant>>\tDönem:" + donem + "\tModel1:" + model1.getText() + "\tDikim Adet:" + dA1.getText() + "\tKoli Adet:" + kA1.getText() + "\n";

                        if (ekPanelAcBtn.isSelected() && !(ekModel1.getText()).isEmpty() && !(ekDA1.getText()).isEmpty() && !(ekKA1.getText()).isEmpty()) {
                            st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                    + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('01','" + (String) sefCBx1.getSelectedItem() + "','"
                                    + donem + "','" + tarih + "','"
                                    + ekModel1.getText() + "','boş','" + ekDA1.getText() + "','" + ekKA1.getText() + "','" + dH1.getText() + "','"
                                    + kH1.getText() + "','" + ekOp1.getText() + "')");
                            mesaj += "\tDönem:" + donem + "\tModel2:" + ekModel1.getText() + "\tDikim Adet:" + ekDA1.getText() + "\tKoli Adet:" + ekKA1.getText() + "\n";
                        }
                    }
                    if (jCheckBox2.isSelected()) {
                        st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('02','" + (String) sefCBx2.getSelectedItem() + "','"
                                + donem + "','" + tarih + "','"
                                + model2.getText() + "','boş','" + dA2.getText() + "','" + kA2.getText() + "','" + dH2.getText() + "','"
                                + kH2.getText() + "','" + opAdet2.getText() + "')");
                        mesaj += "2. Bant>>\tDönem:" + donem + "\tModel1:" + model2.getText() + "\tDikim Adet:" + dA2.getText() + "\tKoli Adet:" + kA2.getText() + "\n";

                        if (ekPanelAcBtn.isSelected() && !(ekModel2.getText()).isEmpty() && !(ekDA2.getText()).isEmpty() && !(ekKA2.getText()).isEmpty()) {
                            st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                    + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('02','" + (String) sefCBx2.getSelectedItem() + "','"
                                    + donem + "','" + tarih + "','"
                                    + ekModel2.getText() + "','boş','" + ekDA2.getText() + "','" + ekKA2.getText() + "','" + dH2.getText() + "','"
                                    + kH2.getText() + "','" + ekOp2.getText() + "')");
                            mesaj += "\tDönem:" + donem + "\tModel2:" + ekModel2.getText() + "\tDikim Adet:" + ekDA2.getText() + "\tKoli Adet:" + ekKA2.getText() + "\n";
                        }
                    }
                    if (jCheckBox3.isSelected()) {
                        st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('03','" + (String) sefCBx3.getSelectedItem() + "','"
                                + donem + "','" + tarih + "','"
                                + model3.getText() + "','boş','" + dA3.getText() + "','" + kA3.getText() + "','" + dH3.getText() + "','"
                                + kH3.getText() + "','" + opAdet3.getText() + "')");
                        mesaj += "3. Bant>>\tDönem:" + donem + "\tModel1:" + model3.getText() + "\tDikim Adet:" + dA3.getText() + "\tKoli Adet:" + kA3.getText() + "\n";

                        if (ekPanelAcBtn.isSelected() && !(ekModel3.getText()).isEmpty() && !(ekDA3.getText()).isEmpty() && !(ekKA3.getText()).isEmpty()) {
                            st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                    + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('03','" + (String) sefCBx3.getSelectedItem() + "','"
                                    + donem + "','" + tarih + "','"
                                    + ekModel3.getText() + "','boş','" + ekDA3.getText() + "','" + ekKA3.getText() + "','" + dH3.getText() + "','"
                                    + kH3.getText() + "','" + ekOp3.getText() + "')");
                            mesaj += "\tDönem:" + donem + "\tModel2:" + ekModel3.getText() + "\tDikim Adet:" + ekDA3.getText() + "\tKoli Adet:" + ekKA3.getText() + "\n";
                        }
                    }
                    if (jCheckBox4.isSelected()) {
                        st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('04','" + (String) sefCBx4.getSelectedItem() + "','"
                                + donem + "','" + tarih + "','"
                                + model4.getText() + "','boş','" + dA4.getText() + "','" + kA4.getText() + "','" + dH4.getText() + "','"
                                + kH4.getText() + "','" + opAdet4.getText() + "')");
                        mesaj += "4. Bant>>\tDönem:" + donem + "\tModel1:" + model4.getText() + "\tDikim Adet:" + dA4.getText() + "\tKoli Adet:" + kA4.getText() + "\n";

                        if (ekPanelAcBtn.isSelected() && !(ekModel4.getText()).isEmpty() && !(ekDA4.getText()).isEmpty() && !(ekKA4.getText()).isEmpty()) {
                            st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                    + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('04','" + (String) sefCBx4.getSelectedItem() + "','"
                                    + donem + "','" + tarih + "','"
                                    + ekModel4.getText() + "','boş','" + ekDA4.getText() + "','" + ekKA4.getText() + "','" + dH4.getText() + "','"
                                    + kH4.getText() + "','" + ekOp4.getText() + "')");
                            mesaj += "\tDönem:" + donem + "\tModel2:" + ekModel4.getText() + "\tDikim Adet:" + ekDA4.getText() + "\tKoli Adet:" + ekKA4.getText() + "\n";
                        }
                    }
                    if (jCheckBox5.isSelected()) {
                        st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('05','" + (String) sefCBx5.getSelectedItem() + "','"
                                + donem + "','" + tarih + "','"
                                + model5.getText() + "','boş','" + dA5.getText() + "','" + kA5.getText() + "','" + dH5.getText() + "','"
                                + kH5.getText() + "','" + opAdet5.getText() + "')");
                        mesaj += "5. Bant>>\tDönem:" + donem + "\tModel1:" + model5.getText() + "\tDikim Adet:" + dA5.getText() + "\tKoli Adet:" + kA5.getText() + "\n";

                        if (ekPanelAcBtn.isSelected() && !(ekModel5.getText()).isEmpty() && !(ekDA5.getText()).isEmpty() && !(ekKA5.getText()).isEmpty()) {
                            st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                    + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('05','" + (String) sefCBx5.getSelectedItem() + "','"
                                    + donem + "','" + tarih + "','"
                                    + ekModel5.getText() + "','boş','" + ekDA5.getText() + "','" + ekKA5.getText() + "','" + dH5.getText() + "','"
                                    + kH5.getText() + "','" + ekOp5.getText() + "')");
                            mesaj += "\tDönem:" + donem + "\tModel2:" + ekModel5.getText() + "\tDikim Adet:" + ekDA5.getText() + "\tKoli Adet:" + ekKA5.getText() + "\n";
                        }
                    }
                    if (jCheckBox6.isSelected()) {
                        st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('06','" + (String) sefCBx6.getSelectedItem() + "','"
                                + donem + "','" + tarih + "','"
                                + model6.getText() + "','boş','" + dA6.getText() + "','" + kA6.getText() + "','" + dH6.getText() + "','"
                                + kH6.getText() + "','" + opAdet6.getText() + "')");
                        mesaj += "6. Bant>>\tDönem:" + donem + "\tModel1:" + model6.getText() + "\tDikim Adet:" + dA6.getText() + "\tKoli Adet:" + kA6.getText() + "\n";

                        if (ekPanelAcBtn.isSelected() && !(ekModel6.getText()).isEmpty() && !(ekDA6.getText()).isEmpty() && !(ekKA6.getText()).isEmpty()) {
                            st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                    + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('06','" + (String) sefCBx6.getSelectedItem() + "','"
                                    + donem + "','" + tarih + "','"
                                    + ekModel6.getText() + "','boş','" + ekDA6.getText() + "','" + ekKA6.getText() + "','" + dH6.getText() + "','"
                                    + kH6.getText() + "','" + ekOp6.getText() + "')");
                            mesaj += "\tDönem:" + donem + "\tModel2:" + ekModel6.getText() + "\tDikim Adet:" + ekDA6.getText() + "\tKoli Adet:" + ekKA6.getText() + "\n";
                        }
                    }
                    if (jCheckBox7.isSelected()) {
                        st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('07','" + (String) sefCBx7.getSelectedItem() + "','"
                                + donem + "','" + tarih + "','"
                                + model7.getText() + "','boş','" + dA7.getText() + "','" + kA7.getText() + "','" + dH7.getText() + "','"
                                + kH7.getText() + "','" + opAdet7.getText() + "')");
                        mesaj += "7. Bant>>\tDönem:" + donem + "\tModel1:" + model7.getText() + "\tDikim Adet:" + dA7.getText() + "\tKoli Adet:" + kA7.getText() + "\n";

                        if (ekPanelAcBtn.isSelected() && !(ekModel7.getText()).isEmpty() && !(ekDA7.getText()).isEmpty() && !(ekKA7.getText()).isEmpty()) {
                            st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                    + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('07','" + (String) sefCBx7.getSelectedItem() + "','"
                                    + donem + "','" + tarih + "','"
                                    + ekModel7.getText() + "','boş','" + ekDA7.getText() + "','" + ekKA7.getText() + "','" + dH7.getText() + "','"
                                    + kH7.getText() + "','" + ekOp7.getText() + "')");
                            mesaj += "\tDönem:" + donem + "\tModel2:" + ekModel7.getText() + "\tDikim Adet:" + ekDA7.getText() + "\tKoli Adet:" + ekKA7.getText() + "\n";
                        }
                    }
                    if (jCheckBox8.isSelected()) {
                        st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('08','" + (String) sefCBx8.getSelectedItem() + "','"
                                + donem + "','" + tarih + "','"
                                + model8.getText() + "','boş','" + dA8.getText() + "','" + kA8.getText() + "','" + dH8.getText() + "','"
                                + kH8.getText() + "','" + opAdet8.getText() + "')");
                        mesaj += "8. Bant>>\tDönem:" + donem + "\tModel1:" + model8.getText() + "\tDikim Adet:" + dA8.getText() + "\tKoli Adet:" + kA8.getText() + "\n";

                        if (ekPanelAcBtn.isSelected() && !(ekModel8.getText()).isEmpty() && !(ekDA8.getText()).isEmpty() && !(ekKA8.getText()).isEmpty()) {
                            st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                    + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('08','" + (String) sefCBx8.getSelectedItem() + "','"
                                    + donem + "','" + tarih + "','"
                                    + ekModel8.getText() + "','boş','" + ekDA8.getText() + "','" + ekKA8.getText() + "','" + dH8.getText() + "','"
                                    + kH8.getText() + "','" + ekOp8.getText() + "')");
                            mesaj += "\tDönem:" + donem + "\tModel2:" + ekModel8.getText() + "\tDikim Adet:" + ekDA8.getText() + "\tKoli Adet:" + ekKA8.getText() + "\n";
                        }
                    }
                    if (jCheckBox9.isSelected()) {
                        st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('09','" + (String) sefCBx9.getSelectedItem() + "','"
                                + donem + "','" + tarih + "','"
                                + model9.getText() + "','boş','" + dA9.getText() + "','" + kA9.getText() + "','" + dH9.getText() + "','"
                                + kH9.getText() + "','" + opAdet9.getText() + "')");
                        mesaj += "9. Bant>>\tDönem:" + donem + "\tModel1:" + model9.getText() + "\tDikim Adet:" + dA9.getText() + "\tKoli Adet:" + kA9.getText() + "\n";

                        if (ekPanelAcBtn.isSelected() && !(ekModel9.getText()).isEmpty() && !(ekDA9.getText()).isEmpty() && !(ekKA9.getText()).isEmpty()) {
                            st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                    + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('09','" + (String) sefCBx9.getSelectedItem() + "','"
                                    + donem + "','" + tarih + "','"
                                    + ekModel9.getText() + "','boş','" + ekDA9.getText() + "','" + ekKA9.getText() + "','" + dH9.getText() + "','"
                                    + kH9.getText() + "','" + ekOp9.getText() + "')");
                            mesaj += "\tDönem:" + donem + "\tModel2:" + ekModel9.getText() + "\tDikim Adet:" + ekDA9.getText() + "\tKoli Adet:" + ekKA9.getText() + "\n";
                        }

                    }
                    if (jCheckBox10.isSelected()) {
                        st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('10','" + (String) sefCBx10.getSelectedItem() + "','"
                                + donem + "','" + tarih + "','"
                                + model10.getText() + "','boş','" + dA10.getText() + "','" + kA10.getText() + "','" + dH10.getText() + "','"
                                + kH10.getText() + "','" + opAdet10.getText() + "')");
                        mesaj += "10. Bant>>\tDönem:" + donem + "\tModel1:" + model10.getText() + "\tDikim Adet:" + dA10.getText() + "\tKoli Adet:" + kA10.getText() + "\n";

                        if (ekPanelAcBtn.isSelected() && !(ekModel10.getText()).isEmpty() && !(ekDA10.getText()).isEmpty() && !(ekKA10.getText()).isEmpty()) {
                            st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                    + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('10','" + (String) sefCBx10.getSelectedItem() + "','"
                                    + donem + "','" + tarih + "','"
                                    + ekModel10.getText() + "','boş','" + ekDA10.getText() + "','" + ekKA10.getText() + "','" + dH10.getText() + "','"
                                    + kH10.getText() + "','" + ekOp10.getText() + "')");
                            mesaj += "\tDönem:" + donem + "\tModel2:" + ekModel10.getText() + "\tDikim Adet:" + ekDA10.getText() + "\tKoli Adet:" + ekKA10.getText() + "\n";
                        }
                    }
                    if (jCheckBox11.isSelected()) {
                        st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('11','" + (String) sefCBx11.getSelectedItem() + "','"
                                + donem + "','" + tarih + "','"
                                + model11.getText() + "','boş','" + dA11.getText() + "','" + kA11.getText() + "','" + dH11.getText() + "','"
                                + kH11.getText() + "','" + opAdet11.getText() + "')");
                        mesaj += "11. Bant>>\tDönem:" + donem + "\tModel1:" + model11.getText() + "\tDikim Adet:" + dA11.getText() + "\tKoli Adet:" + kA11.getText() + "\n";

                        if (ekPanelAcBtn.isSelected() && !(ekModel11.getText()).isEmpty() && !(ekDA11.getText()).isEmpty() && !(ekKA11.getText()).isEmpty()) {
                            st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                    + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('11','" + (String) sefCBx11.getSelectedItem() + "','"
                                    + donem + "','" + tarih + "','"
                                    + ekModel11.getText() + "','boş','" + ekDA11.getText() + "','" + ekKA11.getText() + "','" + dH11.getText() + "','"
                                    + kH11.getText() + "','" + ekOp11.getText() + "')");
                            mesaj += "\tDönem:" + donem + "\tModel2:" + ekModel11.getText() + "\tDikim Adet:" + ekDA11.getText() + "\tKoli Adet:" + ekKA11.getText() + "\n";
                        }
                    }
                    if (jCheckBox12.isSelected()) {
                        st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('12','" + (String) sefCBx12.getSelectedItem() + "','"
                                + donem + "','" + tarih + "','"
                                + model12.getText() + "','boş','" + dA12.getText() + "','" + kA12.getText() + "','" + dH12.getText() + "','"
                                + kH12.getText() + "','" + opAdet12.getText() + "')");
                        mesaj += "12. Bant>>\tDönem:" + donem + "\tModel1:" + model12.getText() + "\tDikim Adet:" + dA12.getText() + "\tKoli Adet:" + kA12.getText() + "\n";

                        if (ekPanelAcBtn.isSelected() && !(ekModel12.getText()).isEmpty() && !(ekDA12.getText()).isEmpty() && !(ekKA12.getText()).isEmpty()) {
                            st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                    + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('12','" + (String) sefCBx12.getSelectedItem() + "','"
                                    + donem + "','" + tarih + "','"
                                    + ekModel12.getText() + "','boş','" + ekDA12.getText() + "','" + ekKA12.getText() + "','" + dH12.getText() + "','"
                                    + kH12.getText() + "','" + ekOp12.getText() + "')");
                            mesaj += "\tDönem:" + donem + "\tModel2:" + ekModel12.getText() + "\tDikim Adet:" + ekDA12.getText() + "\tKoli Adet:" + ekKA12.getText() + "\n";
                        }
                    }
                    if (jCheckBox13.isSelected()) {
                        st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('EĞİTİM','" + (String) sefCBx13.getSelectedItem() + "','"
                                + donem + "','" + tarih + "','"
                                + model13.getText() + "','boş','" + dA13.getText() + "','" + kA13.getText() + "','" + dH13.getText() + "','"
                                + kH13.getText() + "','" + opAdet13.getText() + "')");
                        mesaj += "EĞİTİM B.>>\tDönem:" + donem + "\tModel1:" + model13.getText() + "\tDikim Adet:" + dA13.getText() + "\tKoli Adet:" + kA13.getText() + "\n";

                        if (ekPanelAcBtn.isSelected() && !(ekModel13.getText()).isEmpty() && !(ekDA13.getText()).isEmpty() && !(ekKA13.getText()).isEmpty()) {
                            st.executeUpdate("INSERT INTO saatliktakip(bantAd,bantSef,donem,tarih,model,duyuru,dikimAdet,"
                                    + "koliAdet,dikimHedef,koliHedef,operatorAdedi) values('EĞİTİM','" + (String) sefCBx13.getSelectedItem() + "','"
                                    + donem + "','" + tarih + "','"
                                    + ekModel13.getText() + "','boş','" + ekDA13.getText() + "','" + ekKA13.getText() + "','" + dH13.getText() + "','"
                                    + kH13.getText() + "','" + ekOp13.getText() + "')");
                            mesaj += "\tDönem:" + donem + "\tModel2:" + ekModel13.getText() + "\tDikim Adet:" + ekDA13.getText() + "\tKoli Adet:" + ekKA13.getText() + "\n";
                        }
                    }
                    //              this.sure.setText(String.valueOf(sureBilgisi));
                    varsayilanOlustur();
                    mesajAlani.append(mesaj);
                    duzeltmeTablosuYenileFonksiyonu();

                    onay = JOptionPane.showOptionDialog(rootPane, onayMesaji, "RAPORLAMA", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new String[]{"RAPORLA", "HAYIR"}, "RAPORLA");
                    if (onay == JOptionPane.YES_OPTION) {
                        rapolaBtnActionPerformed(evt);
                    }
                }

            } catch (SQLException ex) {
                //kutuk kutukAnahtar=new kutuk();
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
                //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+Arrays.toString(ex.getStackTrace()),Level.SEVERE);
                mesajAlani.setForeground(Color.RED);
                mesajAlani.append("KAYIT GERÇEKLEŞTİRİLEMEDİ\nYAPILAN VERİ GİRİŞLERİNİ KONTROL EDİNİZ.");
            } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
    }//GEN-LAST:event_veriKaydetActionPerformed

    private void yardimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yardimActionPerformed
        yardim yardimAnahtar = new yardim("VERİ GİRİŞİ", "C:\\bantIzleme_v1.2\\src\\bantizleme\\destek\\yardimDosyasi.html");
        yardimAnahtar.setVisible(true);
    }//GEN-LAST:event_yardimActionPerformed

    private void varsayilanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_varsayilanBtnActionPerformed
        varsayilanAl();
    }//GEN-LAST:event_varsayilanBtnActionPerformed

    private void jPanel8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MousePressed
        float hesap = 1 / Float.parseFloat(sure.getText());
        sure.setText(String.valueOf(hesap));
    }//GEN-LAST:event_jPanel8MousePressed

    private void hedefleriGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hedefleriGirActionPerformed
        try {
            String dikimHedef, koliHedef;
            //System.out.println(String.valueOf(Float.parseFloat(dHGir.getText()) * Float.parseFloat(sure.getText())));
            dikimHedef = String.valueOf(Float.parseFloat(dHGir.getText()) * Float.parseFloat(sure.getText()));
            koliHedef = String.valueOf(Float.parseFloat(kHGir.getText()) * Float.parseFloat(sure.getText()));
            dH1.setText(dikimHedef);
            dH2.setText(dikimHedef);
            dH3.setText(dikimHedef);
            dH4.setText(dikimHedef);
            dH5.setText(dikimHedef);
            dH6.setText(dikimHedef);
            dH7.setText(dikimHedef);
            dH8.setText(dikimHedef);
            dH9.setText(dikimHedef);
            dH10.setText(dikimHedef);
            dH11.setText(dikimHedef);
            dH12.setText(dikimHedef);
            dH13.setText(dikimHedef);
            kH1.setText(koliHedef);
            kH2.setText(koliHedef);
            kH3.setText(koliHedef);
            kH4.setText(koliHedef);
            kH5.setText(koliHedef);
            kH6.setText(koliHedef);
            kH7.setText(koliHedef);
            kH8.setText(koliHedef);
            kH9.setText(koliHedef);
            kH10.setText(koliHedef);
            kH11.setText(koliHedef);
            kH12.setText(koliHedef);
            kH13.setText(koliHedef);
        } catch (NumberFormatException ex) {
            hedefAyarla(dH1, kH1);
            hedefAyarla(dH2, kH2);
            hedefAyarla(dH3, kH3);
            hedefAyarla(dH4, kH4);
            hedefAyarla(dH5, kH5);
            hedefAyarla(dH6, kH6);
            hedefAyarla(dH7, kH7);
            hedefAyarla(dH8, kH8);
            hedefAyarla(dH9, kH9);
            hedefAyarla(dH10, kH10);
            hedefAyarla(dH11, kH11);
            hedefAyarla(dH12, kH12);
            hedefAyarla(dH13, kH13);
            //kutuk kutukAnahtar=new kutuk();
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.INFO);
        }
    }//GEN-LAST:event_hedefleriGirActionPerformed

    private void kHGirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kHGirActionPerformed
        //hedef girme operasyonu yapar
        this.hedefleriGirActionPerformed(evt);
    }//GEN-LAST:event_kHGirActionPerformed

    private void kHGirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kHGirFocusLost
        if (kHGir.getText().isEmpty()) {
            kHGir.setForeground(Color.decode("#999999"));
            kHGir.setText("KOLİ");
        }
    }//GEN-LAST:event_kHGirFocusLost

    private void kHGirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kHGirFocusGained
        kHGir.setText("");
        kHGir.setForeground(Color.BLACK);
    }//GEN-LAST:event_kHGirFocusGained

    private void dHGirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dHGirFocusLost
        if (dHGir.getText().isEmpty()) {
            dHGir.setForeground(Color.decode("#999999"));
            dHGir.setText("DİKİM");
        }
    }//GEN-LAST:event_dHGirFocusLost

    private void dHGirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dHGirFocusGained
        dHGir.setText("");
        dHGir.setForeground(Color.BLACK);
    }//GEN-LAST:event_dHGirFocusGained

    private void sureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sureActionPerformed
        hedefleriGirActionPerformed(evt);
    }//GEN-LAST:event_sureActionPerformed

    private void donemTipiSecBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donemTipiSecBtnActionPerformed
        donemTipiSec();
    }//GEN-LAST:event_donemTipiSecBtnActionPerformed

    private void donemCBxİtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_donemCBxİtemStateChanged
        String donem = (String) donemCBx.getSelectedItem();
        //System.out.println(donem);
        if (donem != null) {
            float sureBilgisi = sureHesaplamasiGonder(donem.substring(0, 5), donem.substring(6));
            //System.out.println(sureBilgisi);
            sure.setText(String.valueOf(sureBilgisi));
            tumHedefleriAyarla();
        }
    }//GEN-LAST:event_donemCBxİtemStateChanged

    private void kilitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kilitActionPerformed
        // Toggle Button aktif mi?
        if (kilit.isSelected()) {
            // Aktifte yapılacaklar
            kilit.setBackground(Color.RED);
            swingAyarla(false);
        } else {
            // Pasifte yapılacaklar
            dA1.setEnabled(true);
            kA1.setEnabled(true);
            dA2.setEnabled(true);
            kA2.setEnabled(true);
            dA3.setEnabled(true);
            kA3.setEnabled(true);
            dA4.setEnabled(true);
            kA4.setEnabled(true);
            dA5.setEnabled(true);
            kA5.setEnabled(true);
            dA6.setEnabled(true);
            kA6.setEnabled(true);
            dA7.setEnabled(true);
            kA7.setEnabled(true);
            dA8.setEnabled(true);
            kA8.setEnabled(true);
            dA9.setEnabled(true);
            kA9.setEnabled(true);
            dA10.setEnabled(true);
            kA10.setEnabled(true);
            dA11.setEnabled(true);
            kA11.setEnabled(true);
            dA12.setEnabled(true);
            kA12.setEnabled(true);
            dA13.setEnabled(true);
            kA13.setEnabled(true);
            jCheckBox1.setForeground(Color.BLACK);
            jCheckBox2.setForeground(Color.BLACK);
            jCheckBox3.setForeground(Color.BLACK);
            jCheckBox4.setForeground(Color.BLACK);
            jCheckBox5.setForeground(Color.BLACK);
            jCheckBox6.setForeground(Color.BLACK);
            jCheckBox7.setForeground(Color.BLACK);
            jCheckBox8.setForeground(Color.BLACK);
            jCheckBox9.setForeground(Color.BLACK);
            jCheckBox10.setForeground(Color.BLACK);
            jCheckBox11.setForeground(Color.BLACK);
            jCheckBox12.setForeground(Color.BLACK);
            jCheckBox13.setForeground(Color.BLACK);

            kilit.setBackground(Color.LIGHT_GRAY);
            swingAyarla(true);
        }
    }//GEN-LAST:event_kilitActionPerformed

    private void kA12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kA12FocusGained
        kA12.selectAll();
    }//GEN-LAST:event_kA12FocusGained

    private void dA12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dA12FocusGained
        dA12.selectAll();
    }//GEN-LAST:event_dA12FocusGained

    private void kA11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kA11FocusGained
        kA11.selectAll();
    }//GEN-LAST:event_kA11FocusGained

    private void dA11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dA11FocusGained
        dA11.selectAll();
    }//GEN-LAST:event_dA11FocusGained

    private void dA2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dA2FocusGained
        dA2.selectAll();
    }//GEN-LAST:event_dA2FocusGained

    private void kA2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kA2FocusGained
        kA2.selectAll();
    }//GEN-LAST:event_kA2FocusGained

    private void dA1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dA1FocusGained
        dA1.selectAll();
    }//GEN-LAST:event_dA1FocusGained

    private void kA1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kA1FocusGained
        kA1.selectAll();
    }//GEN-LAST:event_kA1FocusGained

    private void dA3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dA3FocusGained
        dA3.selectAll();
    }//GEN-LAST:event_dA3FocusGained

    private void kA3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kA3FocusGained
        kA3.selectAll();
    }//GEN-LAST:event_kA3FocusGained

    private void dA4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dA4FocusGained
        dA4.selectAll();
    }//GEN-LAST:event_dA4FocusGained

    private void kA4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kA4FocusGained
        kA4.selectAll();
    }//GEN-LAST:event_kA4FocusGained

    private void dA5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dA5FocusGained
        dA5.selectAll();
    }//GEN-LAST:event_dA5FocusGained

    private void kA5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kA5FocusGained
        kA5.selectAll();
    }//GEN-LAST:event_kA5FocusGained

    private void dA6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dA6FocusGained
        dA6.selectAll();
    }//GEN-LAST:event_dA6FocusGained

    private void kA6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kA6FocusGained
        kA6.selectAll();
    }//GEN-LAST:event_kA6FocusGained

    private void dA7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dA7FocusGained
        dA7.selectAll();
    }//GEN-LAST:event_dA7FocusGained

    private void kA7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kA7FocusGained
        kA7.selectAll();
    }//GEN-LAST:event_kA7FocusGained

    private void dA8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dA8FocusGained
        dA8.selectAll();
    }//GEN-LAST:event_dA8FocusGained

    private void kA8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kA8FocusGained
        kA8.selectAll();
    }//GEN-LAST:event_kA8FocusGained

    private void dA9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dA9FocusGained
        dA9.selectAll();
    }//GEN-LAST:event_dA9FocusGained

    private void kA9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kA9FocusGained
        kA9.selectAll();
    }//GEN-LAST:event_kA9FocusGained

    private void dA10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dA10FocusGained
        dA10.selectAll();
    }//GEN-LAST:event_dA10FocusGained

    private void kA10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kA10FocusGained
        kA10.selectAll();
    }//GEN-LAST:event_kA10FocusGained

    private void ekKA12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekKA12FocusGained
        ekKA12.selectAll();
    }//GEN-LAST:event_ekKA12FocusGained

    private void ekDA12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekDA12FocusGained
        ekDA12.selectAll();
    }//GEN-LAST:event_ekDA12FocusGained

    private void ekModel12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekModel12FocusGained
        ekModel12.selectAll();
    }//GEN-LAST:event_ekModel12FocusGained

    private void ekKA11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekKA11FocusGained
        ekKA11.selectAll();
    }//GEN-LAST:event_ekKA11FocusGained

    private void ekDA11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekDA11FocusGained
        ekDA11.selectAll();
    }//GEN-LAST:event_ekDA11FocusGained

    private void ekModel11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekModel11FocusGained
        ekModel11.selectAll();
    }//GEN-LAST:event_ekModel11FocusGained

    private void ekKA10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekKA10FocusGained
        ekKA10.selectAll();
    }//GEN-LAST:event_ekKA10FocusGained

    private void ekDA10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekDA10FocusGained
        ekDA10.selectAll();
    }//GEN-LAST:event_ekDA10FocusGained

    private void ekModel10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekModel10FocusGained
        ekModel10.selectAll();
    }//GEN-LAST:event_ekModel10FocusGained

    private void ekDA9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekDA9FocusGained
        ekDA9.selectAll();
    }//GEN-LAST:event_ekDA9FocusGained

    private void ekKA9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekKA9FocusGained
        ekKA9.selectAll();
    }//GEN-LAST:event_ekKA9FocusGained

    private void ekModel9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekModel9FocusGained
        ekModel9.selectAll();
    }//GEN-LAST:event_ekModel9FocusGained

    private void ekKA8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekKA8FocusGained
        ekKA8.selectAll();
    }//GEN-LAST:event_ekKA8FocusGained

    private void ekDA8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekDA8FocusGained
        ekDA8.selectAll();
    }//GEN-LAST:event_ekDA8FocusGained

    private void ekModel8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekModel8FocusGained
        ekModel8.selectAll();
    }//GEN-LAST:event_ekModel8FocusGained

    private void ekKA7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekKA7FocusGained
        ekKA7.selectAll();
    }//GEN-LAST:event_ekKA7FocusGained

    private void ekModel7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekModel7FocusGained
        ekModel7.selectAll();
    }//GEN-LAST:event_ekModel7FocusGained

    private void ekDA7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekDA7FocusGained
        ekDA7.selectAll();
    }//GEN-LAST:event_ekDA7FocusGained

    private void ekKA6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekKA6FocusGained
        ekKA6.selectAll();
    }//GEN-LAST:event_ekKA6FocusGained

    private void ekDA6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekDA6FocusGained
        ekDA6.selectAll();
    }//GEN-LAST:event_ekDA6FocusGained

    private void ekModel6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekModel6FocusGained
        ekModel6.selectAll();
    }//GEN-LAST:event_ekModel6FocusGained

    private void ekKA5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekKA5FocusGained
        ekKA5.selectAll();
    }//GEN-LAST:event_ekKA5FocusGained

    private void ekModel5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekModel5FocusGained
        ekModel5.selectAll();
    }//GEN-LAST:event_ekModel5FocusGained

    private void ekDA5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekDA5FocusGained
        ekDA5.selectAll();
    }//GEN-LAST:event_ekDA5FocusGained

    private void ekKA4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekKA4FocusGained
        ekKA4.selectAll();
    }//GEN-LAST:event_ekKA4FocusGained

    private void ekDA4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekDA4FocusGained
        ekDA4.selectAll();
    }//GEN-LAST:event_ekDA4FocusGained

    private void ekModel4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekModel4FocusGained
        ekModel4.selectAll();
    }//GEN-LAST:event_ekModel4FocusGained

    private void ekKA3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekKA3FocusGained
        ekKA3.selectAll();
    }//GEN-LAST:event_ekKA3FocusGained

    private void ekDA3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekDA3FocusGained
        ekDA3.selectAll();
    }//GEN-LAST:event_ekDA3FocusGained

    private void ekModel3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekModel3FocusGained
        ekModel3.selectAll();
    }//GEN-LAST:event_ekModel3FocusGained

    private void ekKA2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekKA2FocusGained
        ekKA2.selectAll();
    }//GEN-LAST:event_ekKA2FocusGained

    private void ekDA2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekDA2FocusGained
        ekDA2.selectAll();
    }//GEN-LAST:event_ekDA2FocusGained

    private void ekModel2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekModel2FocusGained
        ekModel2.selectAll();
    }//GEN-LAST:event_ekModel2FocusGained

    private void ekKA1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekKA1FocusGained
        ekKA1.selectAll();
    }//GEN-LAST:event_ekKA1FocusGained

    private void ekDA1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekDA1FocusGained
        ekDA1.selectAll();
    }//GEN-LAST:event_ekDA1FocusGained

    private void ekModel1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekModel1FocusGained
        ekModel1.selectAll();
    }//GEN-LAST:event_ekModel1FocusGained

    private void ekPanelAcBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ekPanelAcBtnActionPerformed
        if (ekPanelAcBtn.isSelected()) {
            ekPaneli.setVisible(true);
            ekPanelAcBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/solaOk.png")));
        } else {
            ekPaneli.setVisible(false);
            ekPanelAcBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resimler/sagaOk.png")));
        }
    }//GEN-LAST:event_ekPanelAcBtnActionPerformed

    private void model12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model12FocusLost
        hedefleriAyarla(model12.getText(), dH12, kH12);
    }//GEN-LAST:event_model12FocusLost

    private void model12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model12FocusGained
        model12.selectAll();
    }//GEN-LAST:event_model12FocusGained

    private void model11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model11FocusLost
        hedefleriAyarla(model11.getText(), dH11, kH11);
    }//GEN-LAST:event_model11FocusLost

    private void model11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model11FocusGained
        model11.selectAll();
    }//GEN-LAST:event_model11FocusGained

    private void model10FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model10FocusLost
        hedefleriAyarla(model10.getText(), dH10, kH10);
    }//GEN-LAST:event_model10FocusLost

    private void model10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model10FocusGained
        model10.selectAll();
    }//GEN-LAST:event_model10FocusGained

    private void model9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model9FocusLost
        hedefleriAyarla(model9.getText(), dH9, kH9);
    }//GEN-LAST:event_model9FocusLost

    private void model9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model9FocusGained
        model9.selectAll();
    }//GEN-LAST:event_model9FocusGained

    private void model8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model8FocusLost
        hedefleriAyarla(model8.getText(), dH8, kH8);
    }//GEN-LAST:event_model8FocusLost

    private void model8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model8FocusGained
        model8.selectAll();
    }//GEN-LAST:event_model8FocusGained

    private void model7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model7FocusLost
        hedefleriAyarla(model7.getText(), dH7, kH7);
    }//GEN-LAST:event_model7FocusLost

    private void model7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model7FocusGained
        model7.selectAll();
    }//GEN-LAST:event_model7FocusGained

    private void model6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model6FocusLost
        hedefleriAyarla(model6.getText(), dH6, kH6);
    }//GEN-LAST:event_model6FocusLost

    private void model6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model6FocusGained
        model6.selectAll();
    }//GEN-LAST:event_model6FocusGained

    private void model5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model5FocusLost
        hedefleriAyarla(model5.getText(), dH5, kH5);
    }//GEN-LAST:event_model5FocusLost

    private void model5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model5FocusGained
        model5.selectAll();
    }//GEN-LAST:event_model5FocusGained

    private void model4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model4FocusLost
        hedefleriAyarla(model4.getText(), dH4, kH4);
    }//GEN-LAST:event_model4FocusLost

    private void model4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model4FocusGained
        model4.selectAll();
    }//GEN-LAST:event_model4FocusGained

    private void model3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model3FocusLost
        hedefleriAyarla(model3.getText(), dH3, kH3);
    }//GEN-LAST:event_model3FocusLost

    private void model3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model3FocusGained
        model3.selectAll();
    }//GEN-LAST:event_model3FocusGained

    private void model2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model2FocusLost
        hedefleriAyarla(model2.getText(), dH2, kH2);
    }//GEN-LAST:event_model2FocusLost

    private void model2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model2FocusGained
        model2.selectAll();
    }//GEN-LAST:event_model2FocusGained

    private void model1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model1FocusLost
        hedefleriAyarla(model1.getText(), dH1, kH1);
    }//GEN-LAST:event_model1FocusLost

    private void model1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model1FocusGained
        model1.selectAll();
    }//GEN-LAST:event_model1FocusGained

    private void ekModel13FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekModel13FocusGained
        ekModel13.selectAll();
    }//GEN-LAST:event_ekModel13FocusGained

    private void ekDA13FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekDA13FocusGained
        ekDA13.selectAll();
    }//GEN-LAST:event_ekDA13FocusGained

    private void ekKA13FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ekKA13FocusGained
        ekKA13.selectAll();
    }//GEN-LAST:event_ekKA13FocusGained

    private void model13FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model13FocusGained
        model13.selectAll();
    }//GEN-LAST:event_model13FocusGained

    private void model13FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_model13FocusLost
        hedefleriAyarla(model13.getText(), dH13, kH13);
    }//GEN-LAST:event_model13FocusLost

    private void dA13FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dA13FocusGained
        dA13.selectAll();
    }//GEN-LAST:event_dA13FocusGained

    private void kA13FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_kA13FocusGained
        dA12.selectAll();
    }//GEN-LAST:event_kA13FocusGained

    public void hedefleriAyarla(String modelAdi, JTextField dikim, JTextField koli) {
        if (!modelAdi.isEmpty()) {
            try {
                baglanti bagAnahatar = new baglanti();
                con = bagAnahatar.ac();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM hedeflertablosu "
                        + "WHERE modelAdi='" + modelAdi + "'");

                while (rs.next()) {
                    System.out.println("dA:" + rs.getString("dikimHedef") + " kA:" + rs.getString("koliHedef") + "sure:" + sure.getText());
                    dikim.setText(String.valueOf(Integer.parseInt(rs.getString("dikimHedef")) * Float.parseFloat(sure.getText())));
                    koli.setText(String.valueOf(Integer.parseInt(rs.getString("koliHedef")) * Float.parseFloat(sure.getText())));

                }
            } catch (SQLException ex) {
                //kutuk kutukAnahtar=new kutuk();
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
                //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
            } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
    }

    public void tumHedefleriAyarla() {
        hedefleriAyarla(model1.getText(), dH1, kH1);
        hedefleriAyarla(model2.getText(), dH2, kH2);
        hedefleriAyarla(model3.getText(), dH3, kH3);
        hedefleriAyarla(model4.getText(), dH4, kH4);
        hedefleriAyarla(model5.getText(), dH5, kH5);
        hedefleriAyarla(model6.getText(), dH6, kH6);
        hedefleriAyarla(model7.getText(), dH7, kH7);
        hedefleriAyarla(model8.getText(), dH8, kH8);
        hedefleriAyarla(model9.getText(), dH9, kH9);
        hedefleriAyarla(model10.getText(), dH10, kH10);
        hedefleriAyarla(model11.getText(), dH11, kH11);
        hedefleriAyarla(model12.getText(), dH12, kH12);
        hedefleriAyarla(model13.getText(), dH13, kH13);
    }

    public void mailGonder(String tarih) {
        mailGonderici mailAnahtar = new mailGonderici();
        //kutuk kutukAnahtar=new kutuk();
        StyledDocument doc = mailMesajları.getStyledDocument();
        Style style = mailMesajları.addStyle("myStyle", null);

        String[][] mailDizisi = null;
        String mailListesi = "", kullanici = null, sifre = null;
        int anlikSatirNo = 0, satirSayisi = 0;
        try {
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM mail_listesi");
            //Satır sayısını bulmak için kullanılan kod
            rs.last();
            satirSayisi = rs.getRow();
            rs.beforeFirst();

            mailDizisi = new String[satirSayisi][2];
            while (rs.next()) {
                if (rs.getString("aktifKullanici").equals("1")) {
                    kullanici = rs.getString("mailAdresi");
                    sifre = rs.getString("sifre");
                    satirSayisi--;
                } else {
                    mailDizisi[anlikSatirNo][0] = rs.getString("mailSahibi");
                    mailDizisi[anlikSatirNo][1] = rs.getString("mailAdresi");
                    mailListesi += "\n" + mailDizisi[anlikSatirNo][0] + " \t{" + mailDizisi[anlikSatirNo][1] + "}";
                    anlikSatirNo++;
                }

            }
            int onay = JOptionPane.showOptionDialog(rootPane, "Raporlama Başarılı!\n\nListedeki adreslere mail göndermek ister misiniz?" + mailListesi,
                    "MAİL GÖNDERİMİ", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, new String[]{" EVET ", "HAYIR"}, " EVET ");
            if (onay == JOptionPane.YES_OPTION) {
                boolean cevap = mailAnahtar.mailGonderici(kullanici, sifre, mailDizisi, satirSayisi, tarih);

                if (cevap) {
                    String gonderildiKutuk = "E-postalar gönderilmiştir(Bu bilgi e-postanın iletildiğini garanti etmez.)";
                    StyleConstants.setForeground(style, Color.BLACK);
                    mailMesajları.setText("");
                    doc.insertString(doc.getLength(), "✓ " + gonderildiKutuk, style);
                    //kutukAnahtar.kutukTut(gonderildiKutuk,Level.INFO);
                } else {
                    String gonderilmediKutuk = "× E-posta gönderimi başarısız.";
                    mailMesajları.setText("");
                    StyleConstants.setForeground(style, Color.RED);
                    doc.insertString(doc.getLength(), "× " + gonderilmediKutuk, style);
                    //kutukAnahtar.kutukTut(gonderilmediKutuk,Level.INFO);
                }

            }
        } catch (SQLException | BadLocationException ex) {
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @param bas
     * @param son
     * @return
     */
    public int sureHesaplamasiGonder(JSpinner bas, JSpinner son) {
        SimpleDateFormat saatFormati = new SimpleDateFormat("HH:mm");
        String basSaat = String.valueOf(saatFormati.format(bas.getValue()));
        String sonSaat = String.valueOf(saatFormati.format(son.getValue()));
        int saat, dakika;
        saat = Integer.parseInt(sonSaat.substring(0, 1)) - Integer.parseInt(basSaat.substring(0, 1));
        dakika = Integer.parseInt(sonSaat.substring(3, 4)) - Integer.parseInt(basSaat.substring(3, 4));
        dakika /= 60;
        return saat + dakika;
    }

    /**
     *
     * @param basSaat
     * @param sonSaat
     * @return
     */
    public float sureHesaplamasiGonder(String basSaat, String sonSaat) {
        float saat, dakika;
        /*System.out.println(sonSaat.substring(0, 2));
        System.out.println(basSaat.substring(0, 2));
        System.out.println(sonSaat.substring(3));
        System.out.println(basSaat.substring(3));*/
        saat = Float.parseFloat(sonSaat.substring(0, 2)) - Float.parseFloat(basSaat.substring(0, 2));
        dakika = Float.parseFloat(sonSaat.substring(3)) - Float.parseFloat(basSaat.substring(3));
        dakika /= 60;
        return saat + dakika;
    }

    private void donemGirisi(String tercih) {
        String donemModeliAdi = null;
        SimpleDateFormat saatFormati = new SimpleDateFormat("HH:mm");
        try {
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            if (tercih.equals("simdikiDonem")) {
                donemModeliAdi = donemTipiEtiketi.getText().substring(6);
            } else if (tercih.equals("yeniDonem")) {
                donemModeliAdi = yeniDonemAdi.getText();
            }
            if (donem1Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('1','"
                        + saatFormati.format(basla1.getValue()) + "','" + saatFormati.format(bit1.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem2Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('2','"
                        + saatFormati.format(basla2.getValue()) + "','" + saatFormati.format(bit2.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem3Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('3','"
                        + saatFormati.format(basla3.getValue()) + "','" + saatFormati.format(bit3.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem4Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('4','"
                        + saatFormati.format(basla4.getValue()) + "','" + saatFormati.format(bit4.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem5Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('5','"
                        + saatFormati.format(basla5.getValue()) + "','" + saatFormati.format(bit5.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem6Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('6','"
                        + saatFormati.format(basla6.getValue()) + "','" + saatFormati.format(bit6.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem7Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('7','"
                        + saatFormati.format(basla7.getValue()) + "','" + saatFormati.format(bit7.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem8Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('8','"
                        + saatFormati.format(basla8.getValue()) + "','" + saatFormati.format(bit8.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem9Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('9','"
                        + saatFormati.format(basla9.getValue()) + "','" + saatFormati.format(bit9.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem10Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('10','"
                        + saatFormati.format(basla10.getValue()) + "','" + saatFormati.format(bit10.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem11Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('11','"
                        + saatFormati.format(basla11.getValue()) + "','" + saatFormati.format(bit11.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem12Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('12','"
                        + saatFormati.format(basla12.getValue()) + "','" + saatFormati.format(bit12.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem13Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('13','"
                        + saatFormati.format(basla13.getValue()) + "','" + saatFormati.format(bit13.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem14Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('14','"
                        + saatFormati.format(basla14.getValue()) + "','" + saatFormati.format(bit14.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }
            if (donem15Chbx.isSelected()) {
                sorgu = "INSERT INTO donem(sira,donemBasla,donemBit,donemModeli,aktif) values('15','"
                        + saatFormati.format(basla15.getValue()) + "','" + saatFormati.format(bit15.getValue()) + "','" + donemModeliAdi + "','1')";
                st.execute(sorgu);
            }

        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */

        try {

            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(veriGirisi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        // MAterail Tasarım GUİ Güzel işler çıkabilir
//        try {
//            UIManager.setLookAndFeel(new MaterialLookAndFeel());
//        } catch (UnsupportedLookAndFeelException e) {
//            e.printStackTrace();
//        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new veriGirisi().setVisible(true);
                } catch (Exception e) {
                    new kurtarma(e).setVisible(true);
                }
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel adetTab;
    private javax.swing.JButton altCizgili;
    private javax.swing.JButton altCizgili1;
    private javax.swing.JButton altCizgili2;
    private javax.swing.JButton altCizgili3;
    private javax.swing.JButton altCizgili4;
    private javax.swing.JButton altCizgili5;
    private com.toedter.calendar.JDateChooser analizBasTarih;
    private com.toedter.calendar.JDateChooser analizBitTarih;
    private javax.swing.JPanel analizTab;
    private javax.swing.JTable analizTablosu;
    private javax.swing.JPanel ayarlarTab;
    private javax.swing.JButton baglantiYoluSecBtn;
    private javax.swing.JTextField bantNoDzl;
    private javax.swing.JTextField bantSefiDzl;
    private javax.swing.JTextField bantSefininAdiTxt;
    private javax.swing.JSpinner basla1;
    private javax.swing.JSpinner basla10;
    private javax.swing.JSpinner basla11;
    private javax.swing.JSpinner basla12;
    private javax.swing.JSpinner basla13;
    private javax.swing.JSpinner basla14;
    private javax.swing.JSpinner basla15;
    private javax.swing.JSpinner basla2;
    private javax.swing.JSpinner basla3;
    private javax.swing.JSpinner basla4;
    private javax.swing.JSpinner basla5;
    private javax.swing.JSpinner basla6;
    private javax.swing.JSpinner basla7;
    private javax.swing.JSpinner basla8;
    private javax.swing.JSpinner basla9;
    private javax.swing.JSpinner bit1;
    private javax.swing.JSpinner bit10;
    private javax.swing.JSpinner bit11;
    private javax.swing.JSpinner bit12;
    private javax.swing.JSpinner bit13;
    private javax.swing.JSpinner bit14;
    private javax.swing.JSpinner bit15;
    private javax.swing.JSpinner bit2;
    private javax.swing.JSpinner bit3;
    private javax.swing.JSpinner bit4;
    private javax.swing.JSpinner bit5;
    private javax.swing.JSpinner bit6;
    private javax.swing.JSpinner bit7;
    private javax.swing.JSpinner bit8;
    private javax.swing.JSpinner bit9;
    private javax.swing.JRadioButton bugunKayiti;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField dA1;
    private javax.swing.JTextField dA10;
    private javax.swing.JTextField dA11;
    private javax.swing.JTextField dA12;
    private javax.swing.JTextField dA13;
    private javax.swing.JTextField dA2;
    private javax.swing.JTextField dA3;
    private javax.swing.JTextField dA4;
    private javax.swing.JTextField dA5;
    private javax.swing.JTextField dA6;
    private javax.swing.JTextField dA7;
    private javax.swing.JTextField dA8;
    private javax.swing.JTextField dA9;
    private javax.swing.JTextField dH1;
    private javax.swing.JTextField dH10;
    private javax.swing.JTextField dH11;
    private javax.swing.JTextField dH12;
    private javax.swing.JTextField dH13;
    private javax.swing.JTextField dH2;
    private javax.swing.JTextField dH3;
    private javax.swing.JTextField dH4;
    private javax.swing.JTextField dH5;
    private javax.swing.JTextField dH6;
    private javax.swing.JTextField dH7;
    private javax.swing.JTextField dH8;
    private javax.swing.JTextField dH9;
    private javax.swing.JTextField dHGir;
    private javax.swing.JTextField dbBaglantiYoluTxtField;
    private javax.swing.JTextField dbKullaniciTxtField;
    private javax.swing.JPasswordField dbSifreTxtField;
    private javax.swing.JButton degistirBtnDzl;
    private javax.swing.JTextField dikimAdetDzl;
    private javax.swing.JTextField dikimHdf;
    private javax.swing.JTextField dikimHedefDzl;
    private javax.swing.JCheckBox donem10Chbx;
    private javax.swing.JCheckBox donem11Chbx;
    private javax.swing.JCheckBox donem12Chbx;
    private javax.swing.JCheckBox donem13Chbx;
    private javax.swing.JCheckBox donem14Chbx;
    private javax.swing.JCheckBox donem15Chbx;
    private javax.swing.JCheckBox donem1Chbx;
    private javax.swing.JCheckBox donem2Chbx;
    private javax.swing.JCheckBox donem3Chbx;
    private javax.swing.JCheckBox donem4Chbx;
    private javax.swing.JCheckBox donem5Chbx;
    private javax.swing.JCheckBox donem6Chbx;
    private javax.swing.JCheckBox donem7Chbx;
    private javax.swing.JCheckBox donem8Chbx;
    private javax.swing.JCheckBox donem9Chbx;
    private javax.swing.JComboBox<String> donemCBx;
    private javax.swing.JComboBox<String> donemCBx1;
    private javax.swing.JComboBox<String> donemCBx2;
    private javax.swing.JTextField donemDzl;
    private javax.swing.JPanel donemECTab;
    private javax.swing.JButton donemKaydet;
    private javax.swing.JComboBox<String> donemModelSec;
    private javax.swing.JButton donemSilBtn;
    private javax.swing.JTable donemTablosu;
    private javax.swing.JLabel donemTipiEtiketi;
    private javax.swing.JButton donemTipiSecBtn;
    private javax.swing.JButton dosyaSecBtn;
    private javax.swing.JCheckBox duyuru1;
    private javax.swing.JTextField duyuru1sira;
    private javax.swing.JCheckBox duyuru2;
    private javax.swing.JTextField duyuru2sira;
    private javax.swing.JCheckBox duyuru3;
    private javax.swing.JTextField duyuru3sira;
    private javax.swing.JCheckBox duyuru4;
    private javax.swing.JTextField duyuru4sira;
    private javax.swing.JCheckBox duyuru5;
    private javax.swing.JTextField duyuru5sira;
    private javax.swing.JCheckBox duyuru6;
    private javax.swing.JTextField duyuru6sira;
    private javax.swing.JTextArea duyuruDzl;
    private javax.swing.JButton duyuruKaydet;
    private javax.swing.JTextArea duyuruTArea1;
    private javax.swing.JTextArea duyuruTArea2;
    private javax.swing.JTextArea duyuruTArea3;
    private javax.swing.JTextArea duyuruTArea4;
    private javax.swing.JTextArea duyuruTArea5;
    private javax.swing.JTextArea duyuruTArea6;
    private javax.swing.JPanel duyuruTab;
    private javax.swing.JPanel duzeltmeTab;
    private javax.swing.JTable duzeltmeTablosu;
    private javax.swing.JTextField ePostaAdresiTxtField;
    private javax.swing.JTextField ePostaKullaniciAdiTxtField;
    private javax.swing.JPasswordField ePostaSifreTxtField;
    private javax.swing.JTextField ekDA1;
    private javax.swing.JTextField ekDA10;
    private javax.swing.JTextField ekDA11;
    private javax.swing.JTextField ekDA12;
    private javax.swing.JTextField ekDA13;
    private javax.swing.JTextField ekDA2;
    private javax.swing.JTextField ekDA3;
    private javax.swing.JTextField ekDA4;
    private javax.swing.JTextField ekDA5;
    private javax.swing.JTextField ekDA6;
    private javax.swing.JTextField ekDA7;
    private javax.swing.JTextField ekDA8;
    private javax.swing.JTextField ekDA9;
    private javax.swing.JTextField ekKA1;
    private javax.swing.JTextField ekKA10;
    private javax.swing.JTextField ekKA11;
    private javax.swing.JTextField ekKA12;
    private javax.swing.JTextField ekKA13;
    private javax.swing.JTextField ekKA2;
    private javax.swing.JTextField ekKA3;
    private javax.swing.JTextField ekKA4;
    private javax.swing.JTextField ekKA5;
    private javax.swing.JTextField ekKA6;
    private javax.swing.JTextField ekKA7;
    private javax.swing.JTextField ekKA8;
    private javax.swing.JTextField ekKA9;
    private javax.swing.JTextField ekModel1;
    private javax.swing.JTextField ekModel10;
    private javax.swing.JTextField ekModel11;
    private javax.swing.JTextField ekModel12;
    private javax.swing.JTextField ekModel13;
    private javax.swing.JTextField ekModel2;
    private javax.swing.JTextField ekModel3;
    private javax.swing.JTextField ekModel4;
    private javax.swing.JTextField ekModel5;
    private javax.swing.JTextField ekModel6;
    private javax.swing.JTextField ekModel7;
    private javax.swing.JTextField ekModel8;
    private javax.swing.JTextField ekModel9;
    private javax.swing.JTextField ekOp1;
    private javax.swing.JTextField ekOp10;
    private javax.swing.JTextField ekOp11;
    private javax.swing.JTextField ekOp12;
    private javax.swing.JTextField ekOp13;
    private javax.swing.JTextField ekOp2;
    private javax.swing.JTextField ekOp3;
    private javax.swing.JTextField ekOp4;
    private javax.swing.JTextField ekOp5;
    private javax.swing.JTextField ekOp6;
    private javax.swing.JTextField ekOp7;
    private javax.swing.JTextField ekOp8;
    private javax.swing.JTextField ekOp9;
    private javax.swing.JToggleButton ekPanelAcBtn;
    private javax.swing.JPanel ekPaneli;
    private javax.swing.ButtonGroup ekinFormati;
    private javax.swing.JButton epostaAyarlaBtn;
    private javax.swing.JTextField epostaKonuTxtField;
    private javax.swing.JTextArea epostaMesajTxtField;
    private javax.swing.JTable epostaTablosu;
    private javax.swing.JButton filtreBtn;
    private javax.swing.JRadioButton gecmisKayiti;
    private javax.swing.JButton genelAyarlaBtn;
    private javax.swing.JRadioButton goster;
    private javax.swing.JButton gosterBtn;
    private javax.swing.JButton grafikHazirla;
    private javax.swing.JButton hedefDegistir;
    private javax.swing.JButton hedefKaydet;
    private javax.swing.JButton hedefSil;
    private javax.swing.JTable hedefTablosu;
    private javax.swing.JPanel hedeflerTab;
    private javax.swing.JButton hedefleriGir;
    private javax.swing.JButton italik;
    private javax.swing.JButton italik1;
    private javax.swing.JButton italik2;
    private javax.swing.JButton italik3;
    private javax.swing.JButton italik4;
    private javax.swing.JButton italik5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JToolBar jToolBar5;
    private javax.swing.JToolBar jToolBar6;
    private javax.swing.JToolBar jToolBar7;
    private javax.swing.JTextField kA1;
    private javax.swing.JTextField kA10;
    private javax.swing.JTextField kA11;
    private javax.swing.JTextField kA12;
    private javax.swing.JTextField kA13;
    private javax.swing.JTextField kA2;
    private javax.swing.JTextField kA3;
    private javax.swing.JTextField kA4;
    private javax.swing.JTextField kA5;
    private javax.swing.JTextField kA6;
    private javax.swing.JTextField kA7;
    private javax.swing.JTextField kA8;
    private javax.swing.JTextField kA9;
    private javax.swing.JTextField kH1;
    private javax.swing.JTextField kH10;
    private javax.swing.JTextField kH11;
    private javax.swing.JTextField kH12;
    private javax.swing.JTextField kH13;
    private javax.swing.JTextField kH2;
    private javax.swing.JTextField kH3;
    private javax.swing.JTextField kH4;
    private javax.swing.JTextField kH5;
    private javax.swing.JTextField kH6;
    private javax.swing.JTextField kH7;
    private javax.swing.JTextField kH8;
    private javax.swing.JTextField kH9;
    private javax.swing.JTextField kHGir;
    private javax.swing.JButton kalin;
    private javax.swing.JButton kalin1;
    private javax.swing.JButton kalin2;
    private javax.swing.JButton kalin3;
    private javax.swing.JButton kalin4;
    private javax.swing.JButton kalin5;
    private javax.swing.JButton kaydetEposta;
    private javax.swing.JToggleButton kilit;
    private javax.swing.JButton kisiBasiAdetRaporla;
    private javax.swing.JTextField koliAdetDzl;
    private javax.swing.JTextField koliHdf;
    private javax.swing.JTextField koliHedefDzl;
    private javax.swing.JButton kopyalaBtnDzl;
    private javax.swing.JTextField mailAdresi;
    private javax.swing.JTextPane mailMesajları;
    private javax.swing.JTextField mailSahibi;
    private javax.swing.JTextArea mesajAlani;
    private javax.swing.JTextField model1;
    private javax.swing.JTextField model10;
    private javax.swing.JTextField model11;
    private javax.swing.JTextField model12;
    private javax.swing.JTextField model13;
    private javax.swing.JTextField model2;
    private javax.swing.JTextField model3;
    private javax.swing.JTextField model4;
    private javax.swing.JTextField model5;
    private javax.swing.JTextField model6;
    private javax.swing.JTextField model7;
    private javax.swing.JTextField model8;
    private javax.swing.JTextField model9;
    private javax.swing.JTextField modelDzl;
    private javax.swing.JTextField modelHdf;
    private javax.swing.JButton modelSaatlikAdetAnaliziGoster;
    private javax.swing.JButton modelSaatlikAdetAnaliziRaporla;
    private javax.swing.JTextField opAdet1;
    private javax.swing.JTextField opAdet10;
    private javax.swing.JTextField opAdet11;
    private javax.swing.JTextField opAdet12;
    private javax.swing.JTextField opAdet13;
    private javax.swing.JTextField opAdet2;
    private javax.swing.JTextField opAdet3;
    private javax.swing.JTextField opAdet4;
    private javax.swing.JTextField opAdet5;
    private javax.swing.JTextField opAdet6;
    private javax.swing.JTextField opAdet7;
    private javax.swing.JTextField opAdet8;
    private javax.swing.JTextField opAdet9;
    private javax.swing.JTextField opSayisiDzl;
    private javax.swing.JButton opSayisiGoster;
    private javax.swing.JButton opSayisiRaporla;
    private javax.swing.JButton paragraf;
    private javax.swing.JButton paragraf1;
    private javax.swing.JButton paragraf2;
    private javax.swing.JButton paragraf3;
    private javax.swing.JButton paragraf4;
    private javax.swing.JButton paragraf5;
    private javax.swing.JRadioButton pdf;
    private javax.swing.JTextField portTxtField;
    private javax.swing.JButton rapolaBtn;
    private javax.swing.JTextField raporAdiTxtField;
    private javax.swing.JButton raporAyarlaBtn;
    private javax.swing.JTextField raporDosTxtField;
    private javax.swing.JRadioButton sadeceYazdir;
    private javax.swing.JRadioButton sec;
    private javax.swing.JComboBox<String> sefCBx1;
    private javax.swing.JComboBox<String> sefCBx10;
    private javax.swing.JComboBox<String> sefCBx11;
    private javax.swing.JComboBox<String> sefCBx12;
    private javax.swing.JComboBox<String> sefCBx13;
    private javax.swing.JComboBox<String> sefCBx2;
    private javax.swing.JComboBox<String> sefCBx3;
    private javax.swing.JComboBox<String> sefCBx4;
    private javax.swing.JComboBox<String> sefCBx5;
    private javax.swing.JComboBox<String> sefCBx6;
    private javax.swing.JComboBox<String> sefCBx7;
    private javax.swing.JComboBox<String> sefCBx8;
    private javax.swing.JComboBox<String> sefCBx9;
    private javax.swing.JPanel sefECTab;
    private javax.swing.JButton sefKaydet;
    private javax.swing.JButton sefSil;
    private javax.swing.JTable sefTablosu;
    private javax.swing.JButton silBtnDzl;
    private javax.swing.JButton silEposta;
    private javax.swing.JTextField sunucuAdresiTxtField;
    private javax.swing.JTextField sure;
    private com.toedter.calendar.JDateChooser tarihDC;
    private com.toedter.calendar.JDateChooser tarihDC1;
    private com.toedter.calendar.JDateChooser tarihDC2;
    private javax.swing.JTextField tarihDzl;
    private javax.swing.JButton varsayilanBtn;
    private javax.swing.JButton veriKaydet;
    private javax.swing.JRadioButton xlsx;
    private javax.swing.JButton yardim;
    private javax.swing.JButton yazdirBtn;
    private javax.swing.ButtonGroup yazdirmaSecenegi;
    private javax.swing.JTextField yeniDonemAdi;
    private javax.swing.JButton yeniDonemEkleBtn;
    // End of variables declaration//GEN-END:variables

    private void donemComboBoxDoldur() {
        try {

            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            //donemCbxlar doldurulur. Duzeltme için olana (donemCbx1) filtre için HEPSİ eklenir
            ResultSet rs = st.executeQuery("SELECT * FROM donem WHERE aktif='1' ORDER BY sira ASC");
            ResultSetMetaData rsmd = rs.getMetaData();
            if (!rs.next()) {
                rs.close();
                baslangicDonemTipi();
                rs = st.executeQuery("SELECT * FROM donem WHERE aktif='1' ORDER BY sira ASC");
            } else {
                rs.previous();
            }
            //donemCbxlar doldurulur. Duzeltme için olana (donemCbx1) filtre için HEPSİ eklenir
            donemCBx1.addItem("HEPSİ");
            while (rs.next()) {
                donemCBx.addItem(rs.getString("donemBasla") + "-" + rs.getString("donemBit"));
                donemCBx1.addItem(rs.getString("donemBasla") + "-" + rs.getString("donemBit"));
                donemCBx2.addItem(rs.getString("donemBasla") + "-" + rs.getString("donemBit"));

            }
            con.close();

        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void sefComboBoxDoldur(JComboBox<String> jC) {
        try {
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM bantsefleri ORDER BY sefAdi ASC");

            while (rs.next()) {
                jC.addItem(rs.getString("sefAdi"));
            }
            con.close();
            rs.close();
        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void varsayilanOlustur() {
        float sureSayisal = Float.parseFloat(sure.getText());
        Object[][] varsayilan = new Object[][]{
            {jCheckBox1.isSelected(), model1.getText(), dH1.getText(), kH1.getText(), sefCBx1.getSelectedItem(), opAdet1.getText()},
            {jCheckBox2.isSelected(), model2.getText(), dH2.getText(), kH2.getText(), sefCBx2.getSelectedItem(), opAdet2.getText()},
            {jCheckBox3.isSelected(), model3.getText(), dH3.getText(), kH3.getText(), sefCBx3.getSelectedItem(), opAdet3.getText()},
            {jCheckBox4.isSelected(), model4.getText(), dH4.getText(), kH4.getText(), sefCBx4.getSelectedItem(), opAdet4.getText()},
            {jCheckBox5.isSelected(), model5.getText(), dH5.getText(), kH5.getText(), sefCBx5.getSelectedItem(), opAdet5.getText()},
            {jCheckBox6.isSelected(), model6.getText(), dH6.getText(), kH6.getText(), sefCBx6.getSelectedItem(), opAdet6.getText()},
            {jCheckBox7.isSelected(), model7.getText(), dH7.getText(), kH7.getText(), sefCBx7.getSelectedItem(), opAdet7.getText()},
            {jCheckBox8.isSelected(), model8.getText(), dH8.getText(), kH8.getText(), sefCBx8.getSelectedItem(), opAdet8.getText()},
            {jCheckBox9.isSelected(), model9.getText(), dH9.getText(), kH9.getText(), sefCBx9.getSelectedItem(), opAdet9.getText()},
            {jCheckBox10.isSelected(), model10.getText(), dH10.getText(), kH10.getText(), sefCBx10.getSelectedItem(), opAdet10.getText()},
            {jCheckBox11.isSelected(), model11.getText(), dH11.getText(), kH11.getText(), sefCBx11.getSelectedItem(), opAdet11.getText()},
            {jCheckBox12.isSelected(), model12.getText(), dH12.getText(), kH12.getText(), sefCBx12.getSelectedItem(), opAdet12.getText()},
            {jCheckBox13.isSelected(), model13.getText(), dH13.getText(), kH13.getText(), sefCBx13.getSelectedItem(), opAdet13.getText()},
            {donemCBx.getSelectedItem(), dHGir.getText(), kHGir.getText(), sureSayisal, "", ""}};

        //.properties dosyasına ulaşmak için anahtar oluşturuluyor.
        Properties prop = new Properties();

        try {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 14; j++) {
                    // config dosyasında tutulması istenilen özellikler set ediliyor
                    prop.setProperty(String.valueOf(j) + "_" + String.valueOf(i), String.valueOf(varsayilan[j][i]));
                }
            }

            // bilgiler config.properties dosyasına kaydediliyor
            prop.store(new FileOutputStream(new File("varsayilan.properties").getAbsolutePath()), null);

        } catch (FileNotFoundException fileNotFoundEx) {
            JOptionPane.showMessageDialog(rootPane, fileNotFoundEx.getMessage(), "HATA", JOptionPane.CANCEL_OPTION);
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, fileNotFoundEx);
            new kurtarma(fileNotFoundEx);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+fileNotFoundEx,Level.SEVERE);
        } catch (IOException ioEx) {
            JOptionPane.showMessageDialog(rootPane, ioEx.getMessage(), "HATA", JOptionPane.CANCEL_OPTION);
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ioEx);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ioEx,Level.SEVERE);
            new kurtarma(ioEx);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void varsayilanAl() {
        islevseller islevAnahtar = new islevseller();
        try {
            //Chechboxları ayarla
            if ("true".equals(islevAnahtar.varsayilanAyarAl("0_0"))) {
                jCheckBox1.setSelected(true);
            }
            if ("true".equals(islevAnahtar.varsayilanAyarAl("1_0"))) {
                jCheckBox2.setSelected(true);
            }
            if ("true".equals(islevAnahtar.varsayilanAyarAl("2_0"))) {
                jCheckBox3.setSelected(true);
            }
            if ("true".equals(islevAnahtar.varsayilanAyarAl("3_0"))) {
                jCheckBox4.setSelected(true);
            }
            if ("true".equals(islevAnahtar.varsayilanAyarAl("4_0"))) {
                jCheckBox5.setSelected(true);
            }
            if ("true".equals(islevAnahtar.varsayilanAyarAl("5_0"))) {
                jCheckBox6.setSelected(true);
            }
            if ("true".equals(islevAnahtar.varsayilanAyarAl("6_0"))) {
                jCheckBox7.setSelected(true);
            }
            if ("true".equals(islevAnahtar.varsayilanAyarAl("7_0"))) {
                jCheckBox8.setSelected(true);
            }
            if ("true".equals(islevAnahtar.varsayilanAyarAl("8_0"))) {
                jCheckBox9.setSelected(true);
            }
            if ("true".equals(islevAnahtar.varsayilanAyarAl("9_0"))) {
                jCheckBox10.setSelected(true);
            }
            if ("true".equals(islevAnahtar.varsayilanAyarAl("10_0"))) {
                jCheckBox11.setSelected(true);
            }
            if ("true".equals(islevAnahtar.varsayilanAyarAl("11_0"))) {
                jCheckBox12.setSelected(true);
            }
            if ("true".equals(islevAnahtar.varsayilanAyarAl("12_0"))) {
                jCheckBox13.setSelected(true);
            }

            //model textfieldlerini ayarla
            model1.setText(islevAnahtar.varsayilanAyarAl("0_1"));
            model2.setText(islevAnahtar.varsayilanAyarAl("1_1"));
            model3.setText(islevAnahtar.varsayilanAyarAl("2_1"));
            model4.setText(islevAnahtar.varsayilanAyarAl("3_1"));
            model5.setText(islevAnahtar.varsayilanAyarAl("4_1"));
            model6.setText(islevAnahtar.varsayilanAyarAl("5_1"));
            model7.setText(islevAnahtar.varsayilanAyarAl("6_1"));
            model8.setText(islevAnahtar.varsayilanAyarAl("7_1"));
            model9.setText(islevAnahtar.varsayilanAyarAl("8_1"));
            model10.setText(islevAnahtar.varsayilanAyarAl("9_1"));
            model11.setText(islevAnahtar.varsayilanAyarAl("10_1"));
            model12.setText(islevAnahtar.varsayilanAyarAl("11_1"));
            model13.setText(islevAnahtar.varsayilanAyarAl("12_1"));

            //dikimHedef(dH) textfieldlerini ayarla
            dH1.setText(islevAnahtar.varsayilanAyarAl("0_2"));
            dH2.setText(islevAnahtar.varsayilanAyarAl("1_2"));
            dH3.setText(islevAnahtar.varsayilanAyarAl("2_2"));
            dH4.setText(islevAnahtar.varsayilanAyarAl("3_2"));
            dH5.setText(islevAnahtar.varsayilanAyarAl("4_2"));
            dH6.setText(islevAnahtar.varsayilanAyarAl("5_2"));
            dH7.setText(islevAnahtar.varsayilanAyarAl("6_2"));
            dH8.setText(islevAnahtar.varsayilanAyarAl("7_2"));
            dH9.setText(islevAnahtar.varsayilanAyarAl("8_2"));
            dH10.setText(islevAnahtar.varsayilanAyarAl("9_2"));
            dH11.setText(islevAnahtar.varsayilanAyarAl("10_2"));
            dH12.setText(islevAnahtar.varsayilanAyarAl("11_2"));
            dH13.setText(islevAnahtar.varsayilanAyarAl("12_2"));

            //dikimHedef(dH) textfieldlerini ayarla
            kH1.setText(islevAnahtar.varsayilanAyarAl("0_3"));
            kH2.setText(islevAnahtar.varsayilanAyarAl("1_3"));
            kH3.setText(islevAnahtar.varsayilanAyarAl("2_3"));
            kH4.setText(islevAnahtar.varsayilanAyarAl("3_3"));
            kH5.setText(islevAnahtar.varsayilanAyarAl("4_3"));
            kH6.setText(islevAnahtar.varsayilanAyarAl("5_3"));
            kH7.setText(islevAnahtar.varsayilanAyarAl("6_3"));
            kH8.setText(islevAnahtar.varsayilanAyarAl("7_3"));
            kH9.setText(islevAnahtar.varsayilanAyarAl("8_3"));
            kH10.setText(islevAnahtar.varsayilanAyarAl("9_3"));
            kH11.setText(islevAnahtar.varsayilanAyarAl("10_3"));
            kH12.setText(islevAnahtar.varsayilanAyarAl("11_3"));
            kH13.setText(islevAnahtar.varsayilanAyarAl("12_3"));

            comboBoxVarsayilan();

            //opAdet textfieldlerini ayarla
            opAdet1.setText(islevAnahtar.varsayilanAyarAl("0_5"));
            opAdet2.setText(islevAnahtar.varsayilanAyarAl("1_5"));
            opAdet3.setText(islevAnahtar.varsayilanAyarAl("2_5"));
            opAdet4.setText(islevAnahtar.varsayilanAyarAl("3_5"));
            opAdet5.setText(islevAnahtar.varsayilanAyarAl("4_5"));
            opAdet6.setText(islevAnahtar.varsayilanAyarAl("5_5"));
            opAdet7.setText(islevAnahtar.varsayilanAyarAl("6_5"));
            opAdet8.setText(islevAnahtar.varsayilanAyarAl("7_5"));
            opAdet9.setText(islevAnahtar.varsayilanAyarAl("8_5"));
            opAdet10.setText(islevAnahtar.varsayilanAyarAl("9_5"));
            opAdet11.setText(islevAnahtar.varsayilanAyarAl("10_5"));
            opAdet12.setText(islevAnahtar.varsayilanAyarAl("11_5"));
            opAdet13.setText(islevAnahtar.varsayilanAyarAl("12_5"));

            sure.setText(islevAnahtar.varsayilanAyarAl("13_3"));
        } catch (NumberFormatException e) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, e);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+e,Level.SEVERE);
        }

    }

    public void comboBoxVarsayilan() {
        islevseller islevAnahtar = new islevseller();
        //sefCBx ayarla
        try {
            sefCBx1.setSelectedItem(islevAnahtar.varsayilanAyarAl("0_4"));
            sefCBx2.setSelectedItem(islevAnahtar.varsayilanAyarAl("1_4"));
            sefCBx3.setSelectedItem(islevAnahtar.varsayilanAyarAl("2_4"));
            sefCBx4.setSelectedItem(islevAnahtar.varsayilanAyarAl("3_4"));
            sefCBx5.setSelectedItem(islevAnahtar.varsayilanAyarAl("4_4"));
            sefCBx6.setSelectedItem(islevAnahtar.varsayilanAyarAl("5_4"));
            sefCBx7.setSelectedItem(islevAnahtar.varsayilanAyarAl("6_4"));
            sefCBx8.setSelectedItem(islevAnahtar.varsayilanAyarAl("7_4"));
            sefCBx9.setSelectedItem(islevAnahtar.varsayilanAyarAl("8_4"));
            sefCBx10.setSelectedItem(islevAnahtar.varsayilanAyarAl("9_4"));
            sefCBx11.setSelectedItem(islevAnahtar.varsayilanAyarAl("10_4"));
            sefCBx12.setSelectedItem(islevAnahtar.varsayilanAyarAl("11_4"));
            sefCBx13.setSelectedItem(islevAnahtar.varsayilanAyarAl("12_4"));
            //            donem ayarla
            donemCBx.setSelectedItem(islevAnahtar.varsayilanAyarAl("13_0"));
        } catch (IllegalArgumentException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
            JOptionPane.showMessageDialog(null, "Program varsayilan değerlerindeki hatadan dolayı başlatılamıyor.");
        }
    }

    //TABLO İŞLEMLERİ
    /**
     *
     * @param tablo doldurulacak tablo
     * @param tabloAdi tabloyu dolduracak sql tablosunun adı
     * @param baslik1 1. tablo sütununun başlığı
     * @param baslik2 2. tablo sütununun başlığı
     * @param baslik3 3. tablo sütununun başlığı
     * @param sutun1 1. tablo sütununun başlığı
     * @param sutun2 2. tablo sütununun başlığı
     * @param sutun3 3. tablo sütununun başlığı
     * @see
     */
    public void TabloDoldur(JTable tablo, String tabloAdi, String baslik1, String baslik2, String baslik3,
            String sutun1, String sutun2, String sutun3) {
        baglanti bagAnahtar = new baglanti();
        try {

            String sutunAdlari = "";
            if (sutun1 != null) {
                sutunAdlari = sutunAdlari + sutun1;
            }
            if (sutun2 != null) {
                sutunAdlari = sutunAdlari + "," + sutun2;
            }
            if (sutun3 != null) {
                sutunAdlari = sutunAdlari + "," + sutun3;
            }
            if (tablo == donemTablosu) {
                sorgu = "SELECT " + sutunAdlari + " FROM " + tabloAdi + " WHERE donemModeli='" + donemModelSec.getSelectedItem() + "' ORDER BY " + sutun1 + " ASC";
            } else if (tablo == sefTablosu || tablo == hedefTablosu || tablo == analizTablosu) {
                sorgu = "SELECT " + sutunAdlari + " FROM " + tabloAdi + " ORDER BY " + sutun1 + " ASC";
            } else {
                sorgu = "SELECT " + sutunAdlari + " FROM " + tabloAdi + " WHERE aktifKullanici='0' ORDER BY " + sutun1 + " ASC";
            }
            System.out.println(sorgu);
            con = bagAnahtar.ac();
            Statement st = (Statement) con.createStatement();
            ResultSet rs = st.executeQuery(sorgu); //Veritabanındaki tabloya bağlandık

            tablo.setModel(DbUtils.resultSetToTableModel(rs));

            TableColumnModel tcm = tablo.getColumnModel();
            if (sutun1 != null) {
                tcm.getColumn(0).setHeaderValue(baslik1);
            }
            if (sutun2 != null) {
                tcm.getColumn(1).setHeaderValue(baslik2);
            }
            if (sutun3 != null) {
                tcm.getColumn(2).setHeaderValue(baslik3);
            }

            rs.close();

            con.close();
        } catch (SQLException e) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, e);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+e,Level.SEVERE);
            JOptionPane.showConfirmDialog(rootPane, "Bağlantı Başarısız", "MySQL Bağlantısı", JOptionPane.PLAIN_MESSAGE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void duzeltmeTablosuDoldur() {
        baglanti bagAnahtar = new baglanti();
        secilenSatirIndexDuzelme = 0;
        try {
            sdFormat.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));

            if ((donemCBx1.getSelectedItem()).equals("HEPSİ") && bugunKayiti.isSelected()) {
                sorgu = "SELECT id,bantAd,tarih,donem,model,operatorAdedi,"
                        + "dikimAdet,koliAdet,dikimHedef,koliHedef,bantSef,duyuru FROM saatliktakip WHERE tarih='" + sdFormat.format(tarihDC1.getDate()) + "' ORDER BY donem DESC";
            } else if (bugunKayiti.isSelected()) {
                sorgu = "SELECT id,bantAd,tarih,donem,model,operatorAdedi,"
                        + "dikimAdet,koliAdet,dikimHedef,koliHedef,bantSef,duyuru FROM saatliktakip WHERE "
                        + "tarih='" + sdFormat.format(tarihDC1.getDate()) + "' AND donem='" + donemCBx1.getSelectedItem() + "' ORDER BY donem DESC";
            } else if ((donemCBx1.getSelectedItem()).equals("HEPSİ") && gecmisKayiti.isSelected()) {
                sorgu = "SELECT id,bantAd,tarih,donem,model,operatorAdedi,"
                        + "dikimAdet,koliAdet,dikimHedef,koliHedef,bantSef,duyuru FROM saatliktakip_depo WHERE tarih='" + sdFormat.format(tarihDC1.getDate()) + "' ORDER BY donem DESC";
            } else if (gecmisKayiti.isSelected()) {
                sorgu = "SELECT id,bantAd,tarih,donem,model,operatorAdedi,"
                        + "dikimAdet,koliAdet,dikimHedef,koliHedef,bantSef,duyuru FROM saatliktakip_depo WHERE "
                        + "tarih='" + sdFormat.format(tarihDC1.getDate()) + "' AND donem='" + donemCBx1.getSelectedItem() + "' ORDER BY donem DESC";
            }

            con = bagAnahtar.ac();
            Statement st = (Statement) con.createStatement();
            try (ResultSet rs = st.executeQuery(sorgu)) { //Veritabanındaki tabloya bağlandık

                duzeltmeTablosu.setModel(DbUtils.resultSetToTableModel(rs));

                TableColumnModel tcm = duzeltmeTablosu.getColumnModel();

                tcm.getColumn(0).setHeaderValue("KAYIT NO");
                tcm.getColumn(1).setHeaderValue("BANT NO");
                tcm.getColumn(2).setHeaderValue("TARİH");
                tcm.getColumn(3).setHeaderValue("DÖNEM");
                tcm.getColumn(4).setHeaderValue("MODEL ADI");
                tcm.getColumn(5).setHeaderValue("OP. SAYISI");
                tcm.getColumn(6).setHeaderValue("DİKİM ADET");
                tcm.getColumn(7).setHeaderValue("KOLİ ADET");
                tcm.getColumn(8).setHeaderValue("DİKİM HEDEF");
                tcm.getColumn(9).setHeaderValue("KOLİ HEDEF");
                tcm.getColumn(10).setHeaderValue("BANT ŞEFİ");
                tcm.getColumn(11).setHeaderValue("DUYURU");

                rs.close();
            }
        } catch (SQLException e) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, e);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+e,Level.SEVERE);
            JOptionPane.showConfirmDialog(rootPane, "Bağlantı Başarısız", "MySQL Bağlantısı", JOptionPane.PLAIN_MESSAGE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //SEÇİLİ MODELİ SİLER
    public void donemSil(int secilenSIndex, String whereIfadesi) {
        baglanti bagAnahtar = new baglanti();
        try {
            con = bagAnahtar.ac();
//            BU KODU ÇOK DİKKATLİ KULLAN

            Statement st = con.createStatement();

            st.executeUpdate("DELETE FROM donem WHERE donemBasla='" + (String) donemTablosu.getValueAt(secilenSIndex, 1)
                    + "' AND donemBit='" + donemTablosu.getValueAt(secilenSIndex, 2) + "' AND donemModeli='" + whereIfadesi + "'");
            Properties prop = new Properties();
            if (donemTablosu.getRowCount() != 0) {
                donemTablosu.convertRowIndexToView(secilenSIndex);
            } else {
                prop.setProperty("donem", "0");
                donemTipiSec();
            }
            System.out.println("DELETE FROM donem WHERE donemBasla='" + donemTablosu.getValueAt(secilenSIndex, 1)
                    + "' AND donemBit='" + donemTablosu.getValueAt(secilenSIndex, 2) + "' AND donemModeli='" + whereIfadesi + "'");

            con.close();
        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void sefSil(int secilenSIndex) {
        baglanti bagAnahtar = new baglanti();
        try {
            con = bagAnahtar.ac();
//            BU KODU ÇOK DİKKATLİ KULLAN
            Statement st = (Statement) con.createStatement();
            st.executeUpdate("DELETE FROM bantsefleri WHERE sefAdi='" + sefTablosu.getValueAt(secilenSIndex, 0) + "'");
            sefTablosu.convertRowIndexToView(secilenSIndex);
            System.out.println("DELETE FROM bantsefleri WHERE sefAdi='" + sefTablosu.getValueAt(secilenSIndex, 0) + "'");

            con.close();
        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void hedefSil(int secilenSIndex) {
        baglanti bagAnahtar = new baglanti();
        try {
            con = bagAnahtar.ac();
//            BU KODU ÇOK DİKKATLİ KULLAN
            Statement st = (Statement) con.createStatement();
            st.executeUpdate("DELETE FROM hedeflertablosu WHERE modelAdi='" + hedefTablosu.getValueAt(secilenSIndex, 0) + "'");
            hedefTablosu.convertRowIndexToView(secilenSIndex);
            System.out.println("DELETE FROM hedeflertablosu WHERE modelAdi='" + hedefTablosu.getValueAt(secilenSIndex, 0) + "'");

            con.close();
        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void donemModelSecCbxDoldur() {
        baglanti bagAnahtar = new baglanti();
        try {
            con = bagAnahtar.ac();

            Statement st = (Statement) con.createStatement();
            try (ResultSet rs = st.executeQuery("SELECT donemModeli, aktif FROM donem GROUP BY donemModeli")) {
                while (rs.next()) {
                    donemModelSec.addItem(rs.getString("donemModeli"));
//                    if((rs.getString("aktif")).equals('1')) donemModelSec.setSelectedItem(rs.getString("donemModeli"));
                }

            }
            con.close();
        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private ArrayList<String> modelListesiAl() {
        //create list for dictionary this in your case might be done via calling a method which queries db and returns results as arraylist
        ArrayList<String> words = new ArrayList<>();
        try {
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT modelAdi FROM hedeflertablosu ORDER BY modelAdi ASC");
            while (rs.next()) {
                words.add(rs.getString("modelAdi"));
            }
            rs.close();

        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return words;
    }

    private void sagTikMenuDuzeltmeTablosu() {
        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem sil = new JMenuItem("Sil");
        JMenuItem kopyala = new JMenuItem("Kopyala");
        JMenuItem degistir = new JMenuItem("Değiştir");
        sil.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                silBtnDzlActionPerformed(e);
            }
        });
        kopyala.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                kopyalaBtnDzlActionPerformed(e);
            }
        });
        degistir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                degistirBtnDzlActionPerformed(e);
            }
        });
        popupMenu.add(sil);
        popupMenu.add(kopyala);
        popupMenu.add(degistir);
        popupMenu.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = duzeltmeTablosu.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), duzeltmeTablosu));
                        if (rowAtPoint > -1) {
                            duzeltmeTablosu.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
        duzeltmeTablosu.setComponentPopupMenu(popupMenu);
    }

    private void sagTiklaSilDonemTablosu() {
        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem sil = new JMenuItem("Sil");

        sil.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                donemSilBtnActionPerformed(e);
            }
        });

        popupMenu.add(sil);

        popupMenu.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = donemTablosu.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), donemTablosu));
                        if (rowAtPoint > -1) {
                            donemTablosu.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
        donemTablosu.setComponentPopupMenu(popupMenu);
    }

    private String baslangicDonemTipi() {
        String ilkSeciliDonem = "GENEL";
        try {
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT donemModeli FROM donem WHERE aktif='1' GROUP BY donemModeli");
            while (rs.next()) {
                ilkSeciliDonem = rs.getString("donemModeli");
            }
            rs.close();

            donemModelSec.setSelectedItem(ilkSeciliDonem);

        } catch (SQLException ex) {
            try {
                Statement st = con.createStatement();
                st.executeUpdate("UPDATE donem SET aktif='1' WHERE donemModeli='" + ilkSeciliDonem + "'");
                con.close();

                //kutuk kutukAnahtar=new kutuk();
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
                //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
                JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!\n'GENEL' Dönem Tipi Seçildi");
            } catch (SQLException ex1) {
                //kutuk kutukAnahtar=new kutuk();
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex1);
                //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex1,Level.SEVERE);
            }
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ilkSeciliDonem;
    }

    public String periyodSureHesapla(String donemBasi, String donemSonu) {
        try {

            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO ");

        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }

    private void spinnerAyarla(javax.swing.JSpinner spinner) {
        SpinnerDateModel model = new SpinnerDateModel();
        //Başlangıç saati için olan spinnerları ayarla
        spinner.setModel(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "HH:mm"));
    }

    public void jspinnerSetEnabledFalse(JSpinner js1, JSpinner js2) {
        js1.setEnabled(false);
        js2.setEnabled(false);
    }

    private void tumSpinnerlariAyarla() {
        //Spinnerların formatlarını ayarlar
        jspinnerSetEnabledFalse(basla1, bit1);
        jspinnerSetEnabledFalse(basla2, bit2);
        jspinnerSetEnabledFalse(basla3, bit3);
        jspinnerSetEnabledFalse(basla4, bit4);
        jspinnerSetEnabledFalse(basla5, bit5);
        jspinnerSetEnabledFalse(basla6, bit6);
        jspinnerSetEnabledFalse(basla7, bit7);
        jspinnerSetEnabledFalse(basla8, bit8);
        jspinnerSetEnabledFalse(basla9, bit9);
        jspinnerSetEnabledFalse(basla10, bit10);
        jspinnerSetEnabledFalse(basla11, bit11);
        jspinnerSetEnabledFalse(basla12, bit12);
        jspinnerSetEnabledFalse(basla13, bit13);
        jspinnerSetEnabledFalse(basla14, bit14);
        jspinnerSetEnabledFalse(basla15, bit15);
        //Spinnerların formatını ayarla
        spinnerAyarla(basla1);
        spinnerAyarla(basla2);
        spinnerAyarla(basla3);
        spinnerAyarla(basla4);
        spinnerAyarla(basla5);
        spinnerAyarla(basla6);
        spinnerAyarla(basla7);
        spinnerAyarla(basla8);
        spinnerAyarla(basla9);
        spinnerAyarla(basla10);
        spinnerAyarla(basla11);
        spinnerAyarla(basla12);
        spinnerAyarla(basla13);
        spinnerAyarla(basla14);
        spinnerAyarla(basla15);
        spinnerAyarla(bit1);
        spinnerAyarla(bit2);
        spinnerAyarla(bit3);
        spinnerAyarla(bit4);
        spinnerAyarla(bit5);
        spinnerAyarla(bit6);
        spinnerAyarla(bit7);
        spinnerAyarla(bit8);
        spinnerAyarla(bit9);
        spinnerAyarla(bit10);
        spinnerAyarla(bit11);
        spinnerAyarla(bit12);
        spinnerAyarla(bit13);
        spinnerAyarla(bit14);
        spinnerAyarla(bit15);
    }

    public void mesajAlaniAyarla(String ekYazi) {
        mesajAlani.append(ekYazi);
    }

    private void epostaBilgileriDoldur() {
        try {
            baglanti bagAnahtar = new baglanti();
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM mail_listesi WHERE aktifKullanici='1'");
            while (rs.next()) {
                ePostaKullaniciAdiTxtField.setText(rs.getString("mailSahibi"));
                ePostaAdresiTxtField.setText(rs.getString("mailAdresi"));
                ePostaSifreTxtField.setText(rs.getString("sifre"));
            }
        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void epostaSil(int secilenSatirIndexEposta) {
        baglanti bagAnahtar = new baglanti();
        try {
            con = bagAnahtar.ac();
            Statement st = con.createStatement();
            st.executeUpdate("DELETE FROM mail_listesi WHERE mailSahibi='" + epostaTablosu.getValueAt(secilenSatirIndexEposta, 0)
                    + "' AND mailAdresi='" + epostaTablosu.getValueAt(secilenSatirIndexEposta, 1) + "'");
            con.close();
        } catch (SQLException ex) {
            //kutuk kutukAnahtar=new kutuk();
            Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
        } finally {            
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class yenile implements Runnable {

        @Override
        public void run() {
            donemTablosu.removeAll();
            sefTablosu.removeAll();
            donemCBx.removeAllItems();
            donemCBx1.removeAllItems();
            sefCBx1.removeAllItems();
            sefCBx2.removeAllItems();
            sefCBx3.removeAllItems();
            sefCBx4.removeAllItems();
            sefCBx5.removeAllItems();
            sefCBx6.removeAllItems();
            sefCBx7.removeAllItems();
            sefCBx8.removeAllItems();
            sefCBx9.removeAllItems();
            sefCBx10.removeAllItems();
            sefCBx11.removeAllItems();
            sefCBx12.removeAllItems();
            sefCBx13.removeAllItems();
            TabloDoldur(donemTablosu, "donem", "SIRA", "BAŞLANGIÇ", "BİTİŞ", "sira", "donemBasla", "donemBit");
            TabloDoldur(sefTablosu, "bantsefleri", "BANT ŞEFİNİN ADI", null, null, "sefAdi", null, null);

            donemComboBoxDoldur();
            sefComboBoxDoldur(sefCBx1);
            sefComboBoxDoldur(sefCBx2);
            sefComboBoxDoldur(sefCBx3);
            sefComboBoxDoldur(sefCBx4);
            sefComboBoxDoldur(sefCBx5);
            sefComboBoxDoldur(sefCBx6);
            sefComboBoxDoldur(sefCBx7);
            sefComboBoxDoldur(sefCBx8);
            sefComboBoxDoldur(sefCBx9);
            sefComboBoxDoldur(sefCBx10);
            sefComboBoxDoldur(sefCBx11);
            sefComboBoxDoldur(sefCBx12);
            sefComboBoxDoldur(sefCBx13);
            comboBoxVarsayilan();
        }
    }

    public void yenileFonk() {
        yenile y = new yenile();
        Thread thd = new Thread(y);
        thd.start();
    }

    public class donemModeliYenile implements Runnable {

        String donemModeliAdi;

        @Override
        public void run() {
            if (donemModelSec.getSelectedItem() != null) {
                donemModeliAdi = (String) donemModelSec.getSelectedItem();
            } else {
                donemModeliAdi = donemModelSec.getItemAt(0);
            }
            donemCBx.removeAllItems();
            donemCBx1.removeAllItems();
            donemCBx2.removeAllItems();
            donemModelSec.removeAllItems();
            donemModelSecCbxDoldur();
            try {
                donemModelSec.setSelectedItem(donemModeliAdi);
            } catch (ArrayIndexOutOfBoundsException ex) {
                //kutuk kutukAnahtar=new kutuk();
                Logger.getLogger(veriGirisi.class.getName()).log(Level.SEVERE, null, ex);
                //kutukAnahtar.kutukTut("YAKALANAN İSTİSNA : "+ex,Level.SEVERE);
                baslangicDonemTipi();
            }
            donemComboBoxDoldur();
            donemTablosu.removeAll();
            TabloDoldur(donemTablosu, "donem", "SIRA", "BAŞLANGIÇ", "BİTİŞ", "sira", "donemBasla", "donemBit");
            donemTipiEtiketi.setText("LİSTE:" + donemModelSec.getSelectedItem());
        }
    }

    public void yenileDonemModelSecCbx() {
        donemModeliYenile d = new donemModeliYenile();
        Thread thd2 = new Thread(d);
        thd2.start();
    }

    public class duzeltmeTablosuYenile implements Runnable {

        @Override
        public void run() {
            duzeltmeTablosu.removeAll();
            duzeltmeTablosuDoldur();
        }
    }

    public void duzeltmeTablosuYenileFonksiyonu() {
        duzeltmeTablosuYenile d = new duzeltmeTablosuYenile();
        Thread thd = new Thread(d);
        thd.start();
    }

    public class yenileEpostaTablosu implements Runnable {

        @Override
        public void run() {
            epostaTablosu.removeAll();
            TabloDoldur(epostaTablosu, "mail_listesi", "E-POSTA SAHİBİ", "E-POSTA ADRESİ", null, "mailSahibi", "mailAdresi", null);
        }
    }

    public void yenileEpostaTablosuFonksiyonu() {
        yenileEpostaTablosu d = new yenileEpostaTablosu();
        Thread thd3 = new Thread(d);
        thd3.start();
    }

    public class hedefYenile implements Runnable {

        @Override
        public void run() {
            hedefTablosu.removeAll();
            TabloDoldur(hedefTablosu, "hedeflertablosu", "MODEL ADI", "DİKİM HEDEFİ", "KOLİ HEDEFİ", "modelAdi", "dikimHedef", "koliHedef");
        }
    }

    public void hedefYenileFonk() {
        hedefYenile d = new hedefYenile();
        Thread thd4 = new Thread(d);
        thd4.start();
    }

}
