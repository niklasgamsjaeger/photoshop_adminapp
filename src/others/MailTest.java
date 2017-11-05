/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.fotoherfert4school_admin.others;

import javax.mail.MessagingException;
import javax.mail.Session;

/**
 *
 * @author 20110386
 */
public class MailTest {

    public static void main(String[] args) {
        Session s = MailUtilities.getGMailSession("fotoherfert4school@gmail.com", "Micmos87");
        try {
//            MailUtilities.postMail(s, "m.moschinger@htlstp.at",
//                    "fotoherfert4school - Testmail",
//                    "Hallo! :) Hier sende ich dir ein Testmail! :)\nLg MiMo");
            String html = "<p>\n"
                    + "Firma \n<br>"
                    + "fotoherfert\n<br>"
                    + "He&szlig;stra&szlig;e 16\n<br>"
                    + "3500 St. P&ouml;lten\n"
                    + "</p>\n"
                    + "<img alt='Bild' /> \n"
                    + "\n"
                    + "<h2>fotoherfert4school - Webshop für Schülerfotos</h2>\n"
                    + "<h3>Zahlungsbestätigung</h3>\n"
                    + "\n"
                    + "<p>Vielen Dank für Ihre Bestellung bei fotoherfert4school. Ihre Zahlung ist erfolgreich eingegangen. Im Anhang finden Sie dazu Ihre Bestellung.</p><br>\n"
                    + "<p>Unter folgendem Link k&ouml;nnen Sie die Fotos innerhalb der n&auml;chsten 30 Tage herunterladen: </p><a href='http://facebook.com'>Downloadlink</a>";
//            MailUtilities.postHtmlWithAttachement(s, "m.moschinger@htlstp.at", "fotoherfert4school - Testmail",
//                    html, "E:\\_HTL\\hallo.txt");
            String html1 = "<p>\n"
                    + "Firma <br>\n"
                    + "fotoherfert<br>\n"
                    + "He&szlig;stra&szlig;e 16<br>\n"
                    + "3500 St. P&ouml;lten\n"
                    + "</p>\n"
                    + "<img alt='Bild' /> \n"
                    + "\n"
                    + "<h2>fotoherfert4school - Webshop für Schülerfotos</h2>\n"
                    + "<h3>Zahlungsinformationen</h3>\n"
                    + "\n"
                    + "<p>Vielen Dank für Ihre Bestellung bei fotoherfert4school. Bitte zahlen Sie den angegebenen Betrag unter folgenden Koto ein:</p>\n"
                    + "<p>Empf&auml;nger: Josef Herfert<br>\n"
                    + "IBAN: AT22 7867 9238 8723<br>\n"
                    + "BIC: OWSTA78<br>\n"
                    + "Verwendungszweck: 30062<br>\n"
                    + "Betrag: 20,00 &euro;</p>\n"
                    + "<p>Bitte geben Sie unbedingt den richtigen Verwendungszweck an, sonst kann Ihre Rechnung nicht richtig zugeordnet werden!</p>";
            MailUtilities.postHtml(s, "m.moschinger@htlstp.at", "fotoherfert4school - Testmail",
                    html1);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
