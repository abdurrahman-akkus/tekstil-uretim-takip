/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bantizleme.destek;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CI
 */
public class tablolarArasiVeriAktar {
    public void aktarma(){
        try {
            islevseller islevAnahtar=new islevseller();
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            sdFormat.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
        //Tarih Seçicisinin başlangıç değerini "bugün" olarak ayarladık.
            java.util.Date tarih = new java.util.Date();
            
            String sonVeriAktarimTarihi=islevAnahtar.ayarAl("sonAktarimTarihi");
            if(!sonVeriAktarimTarihi.equals(sdFormat.format(tarih))){
                baglanti bagAnahtar=new baglanti();
                Connection con=bagAnahtar.ac();
                Statement st=con.createStatement();
                st.executeUpdate(
                    "INSERT saatliktakip_depo(bantAd,bantSef,donem,tarih,model,duyuru," +
                    "dikimAdet,koliAdet,dikimHedef,koliHedef,operatorAdedi) " +
                    "SELECT bantAd,bantSef,donem,tarih,model,duyuru," +
                    " dikimAdet,koliAdet,dikimHedef,koliHedef,operatorAdedi\n" +
                    "FROM saatliktakip WHERE tarih!='"+sdFormat.format(tarih)+"' AND duyuru='boş'");
                st.executeUpdate("DELETE FROM saatliktakip WHERE tarih!='"+sdFormat.format(tarih)+"'  OR duyuru!='boş'");
                
                //.properties dosyasına ulaşmak için anahtar oluşturuluyor.
                
                islevAnahtar.ayarla("sonAktarimTarihi", sdFormat.format(tarih));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(tablolarArasiVeriAktar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
