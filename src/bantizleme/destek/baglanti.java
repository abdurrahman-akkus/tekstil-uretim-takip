/*
 * Copyright (C) 2019 Abdurrahman AKKUŞ <iletisim@algoritimbilisim.com>
 * Bu yazılımın tüm hakları Algoritim Bilişim'e aittir.
 */
package bantizleme.destek;

import com.mysql.jdbc.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Abdurrahman AKKUŞ <iletisim@algoritimbilisim.com>
 */
public class baglanti {

    java.sql.Connection con;

    public Connection ac() throws SQLException {
        try {
            islevseller islevAnahtar = new islevseller();
            Class.forName("com.mysql.jdbc.Connection");
            String linkModifier = "?useUnicode=true&characterEncoding=utf8&"
                    + "useLegacyDatetimeCode=false&"
                    + "serverTimezone=Turkey";
            con = (Connection) DriverManager.getConnection(islevAnahtar.ayarAl("baglanti") + linkModifier, islevAnahtar.ayarAl("kullanici"), islevAnahtar.ayarAl("parola"));
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "HATA", JOptionPane.CANCEL_OPTION);
        }
        return (Connection) con;
    }

    public void kapat(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "HATA", JOptionPane.CANCEL_OPTION);

        }
    }

}
