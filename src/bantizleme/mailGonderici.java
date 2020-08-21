/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bantizleme;


import bantizleme.destek.islevseller;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;

public class mailGonderici {

/**
 *
 * @author xyz
     * @param kullanici maili gönderecek kişi
     * @param sifre maili gönderecek kişinin mail şifresi
     * @param alici maili alacakların listesi
     * @param satirSayisi maili alacakların sayısı
     * @param tarih hangi tarihin raporu olacağı
     * @return true veya false
 */


    public boolean mailGonderici(String kullanici,String sifre,String[][] alici, int satirSayisi, String tarih) {
        islevseller islevAnahtar=new islevseller();
        veriGirisi vgAnahtar=new veriGirisi();
        String mailSunucuAdresi=islevAnahtar.ayarAl("mailSunucuAdresi");
        String mailPort=islevAnahtar.ayarAl("mailPort");
        
//        CC veya BCC eklenmek istenirse aktif hale getirilebilir
//        String ccId = "a.rahmanakkus@hotmail.com.tr";
//        String bccId = "a.rahmanakkus@gmail.com";
        try {
            MultiPartEmail email = new HtmlEmail();
            email.setSmtpPort(Integer.parseInt(mailPort));
            email.setAuthenticator(new DefaultAuthenticator(kullanici, sifre));
            email.setDebug(true);
            email.setHostName(mailSunucuAdresi);
            for (int i = 0; i < satirSayisi; i++) {
            email.addTo(alici[i][1]);
            }
//            CC veya BCC eklenmek istenirse aktif hale getirilebilir
            email.addCc(kullanici);
//            email.addBcc(bccId);
            email.setFrom(kullanici);
            email.setCharset("utf-8");
            email.setSubject(islevAnahtar.ayarAl("mailKonu"));
            email.setMsg("<font face='calibri' size='3'>"+islevAnahtar.ayarAl("mailGovdesi")+"</font><font face='calibri' size='1'>\n\n\n\n\n\n\n\n\n\n\n\n\n\nBu mail otomatik olarak gönderilmiştir.\n"
                    + "Üretim İzleme Yazılımı©2016\n"
                    + "Abdurrahman AKKUŞ </font>");

            // add the attachment
            EmailAttachment attachment = new EmailAttachment();
            attachment.setPath(islevAnahtar.ayarAl("raporKayitYeri")+"\\"+islevAnahtar.ayarAl("raporAdi")+"_"+tarih.replaceAll("-", "_")
                            +islevAnahtar.ayarAl("ekinFormati"));
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            email.attach(attachment);
            
            // send the email
            email.send();
            
            return true;
        } catch (EmailException e) {
            JOptionPane.showMessageDialog(vgAnahtar, alici+" adresine mail iletilememiştir.\nHata: "+e,
                    "GÖNDERİM HATASI",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
