/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import at.htlstp.fotoherfert4school_admin.db.HibernateDAO;
import at.htlstp.fotoherfert4school_admin.db.IDAO;
import at.htlstp.fotoherfert4school_admin.entity.Artikel;
import at.htlstp.fotoherfert4school_admin.entity.Artikeltyp;
import at.htlstp.fotoherfert4school_admin.entity.Benutzer;
import at.htlstp.fotoherfert4school_admin.entity.Klasse;
import at.htlstp.fotoherfert4school_admin.entity.Reko;
import at.htlstp.fotoherfert4school_admin.entity.Rezl;
import at.htlstp.fotoherfert4school_admin.entity.Schule;
import at.htlstp.fotoherfert4school_admin.others.HashingUtilities;
import at.htlstp.fotoherfert4school_admin.others.MailUtilities;
import java.io.IOException;
import java.io.InputStream;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.tabview.TabView;
import com.sun.faces.component.visit.FullVisitContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import at.htlstp.fotoherfert4school_admin.others.WasserzeichenRunnable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author 20100226
 */
@Named(value = "adminCon")
@SessionScoped
public class AdminController implements Serializable {

  private static final String JDBCURL = "jdbc:postgresql://193.170.118.29:54322/fotoherfert4school";
  private final String BENUTZERNAME = "5BHIF_15";
  private final String PASSWORT = "scre20";

//    @Inject
  private IDAO dao;

  /**
   * Kapselt den Admin
   */
  private Benutzer benutzer = null;

  /**
   * Kapselt den Admin und wird verwendet wenn er seine Emai bzw. Passwort
   * \u00E4ndert.
   */
  private Benutzer neuerBenutzer;

  /**
   * Kapselt eine Email f\u00FCr \u00C4nderungen der Profileinstellungen
   */
  private String email_wh;

  /**
   * Kaspselt die Passw\u00F6rter f\u00FCr die \u00C4nderungen der
   * Profileinstellungen
   */
  private String passwort_alt;
  private String passwort_wh;

  /**
   * Speichert alle offenen Rechnungen
   */
  private List<Reko> offeneRechnungen;

  /**
   * Kapselt alle best\u00E4tigteRechnungen
   */
  private List<Reko> bestaetigteRechungen;

  /**
   * Kapselt die Rechnungen die aktuell angezeigt werden
   */
  private List<Reko> aktRechnungen;

  /**
   * Kapselt die best\u00E4tigten Rechnungen die aktuell angezeigt werden
   */
  private List<Reko> aktBestaetigteRechnungen;

  /**
   * Kapselt die Preisprofile
   */
  private List<Artikeltyp> preisprofile;

  /**
   * Kapselt die zu verwaltenden Schulen
   */
  private List<Schule> schulen;

  /**
   * Kapselt eine neue Schule
   */
  private Schule neueSchule;

  /**
   * Kapselt eine aktuelle Schule
   */
  private Schule aktSchule;

  /**
   * Boolean der verwendet wird um die Schule zu bearbeiten und die Bearbeitung
   * zu aktivieren
   */
  private boolean schuleBearbeiten;

  /**
   * Kaspelt das heutige Datum
   */
  private Date aktDatum;

  /**
   * Kapselt den Fortschritt beim Uploadvorgang.
   */
  private Long fortschritt;

  @PostConstruct
  public void init() {
    dao = new HibernateDAO();

    neuerBenutzer = new Benutzer();

    aktDatum = new Date();
    offeneRechnungen = dao.liefereOffeneRechnungen();
    if (offeneRechnungen != null) {
      aktRechnungen = new ArrayList<>(offeneRechnungen);
    } else {
      aktRechnungen = new ArrayList<>();
    }
    bestaetigteRechungen = dao.liefereBestaetigteRechnungen();
    if (bestaetigteRechungen != null) {
      aktBestaetigteRechnungen = new ArrayList<>(bestaetigteRechungen);
    } else {
      aktBestaetigteRechnungen = new ArrayList<>();
    }
    preisprofile = dao.liefereArtikeltypen();
    Collections.sort(preisprofile);
    schulen = dao.liefereSchulen();
    neueSchuleZuruecksetzen();
    aktSchule = new Schule();
    schuleBearbeiten = true;

  }

  /**
   * Diese Methode liefert true wenn ein Benutzer angemeldet ist und false wenn
   * noch keiner angemeldet ist.
   *
   * @return
   */
  public boolean isLoggedIn() {
    return benutzer != null;
  }

  /**
   * Dise Methode loggt den Admin aus. Dieser wird zur Login Seite
   * weitergeleitet.
   *
   * @return Liefert die Seite auf die weitergeleitet wird (adminLogin)
   */
  public String logout() {
    benutzer = null;

    FacesContext fc = FacesContext.getCurrentInstance();

    // JDBC-Realm Logout
    HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
    if (request.getUserPrincipal() != null) {
      try {
        request.logout();
      } catch (ServletException ex) {
        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Ein Fehler ist bei der Abarbeitung Ihrer Anfrage aufgetreten!",
                "Ein Fehler ist bei der Abarbeitung Ihrer Anfrage aufgetreten!"));
      }
    }
    HttpSession s = (HttpSession) fc.getExternalContext().getSession(false);

    // Session f\u00FCr ung\u00FCltig erkl\u00E4ren
    // sessionDestroyed im HttpSessionListener wird aufgerufen
    s.invalidate();

    fc.addMessage(null, new FacesMessage("Erfolgreich ausgeloggt!"));

    return "adminLogin";
  }

  private String generiereUniqueHashwert(String filename) {
    String[] path = filename.split("\\.");
    String hash = UUID.randomUUID().toString();
    return hash.replace('-', '_') + "." + path[path.length - 1];
  }

  /**
   * Diese Methode best\u00E4tigt die Zahlung einer Rechnung und l\u00F6scht die
   * Rechnung aus der Liste der offenen Rechnungen.
   *
   * @param rk Rechnungskopf dessen Zahlung best\u00E4tigt werden soll
   */
  public void zahlungBestaetigen(Reko rk) {
    if (rk == null) {
      FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_ERROR,
                      "Die Zahlung konnte nicht best\u00E4tigt werden!",
                      "Der Rechnungskopf ist null, somit konnte "
                      + "keine Zahlung best\u00E4tigt werden!"));
      return;
    }
    rk.setBezahldatum(new Date());
    Schule s = rk.getRezls().get(0).getArtikel().getKlasse().getSchule();
    Date d = new Date(java.sql.Date.valueOf(LocalDate.now()
            .plusDays(s.getDownloadfrist())).getTime());
    rk.setUrlAblaufdatum(d);
    offeneRechnungen.remove(rk);
    zeigeAlleRechnungen();
    dao.updateReko(rk);
    bestaetigteRechungen = dao.liefereBestaetigteRechnungen();
    aktBestaetigteRechnungen = bestaetigteRechungen;

    /*
     PDF-generien
     Zip-Datei erstellen
     Downloadlink generienen
     E-Mail senden
     */
    try {
      String path = erstelleBestellungsPDF(rk);
      if (path != null) {
        erstelleZipDatei(rk);
        sendeBestellungsMail(rk, path);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Zahlung erfolgreich best\u00E4tigt",
                "Die Zahlung wurde erfolgreich best\u00E4tigt."));
      } else {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Es gab einen Fehler bei der Abarbeitung der Zahlung",
                        "Es gab einen Fehler bei der Abarbeitung der Zahlung!"));
      }
    } catch (IOException | SQLException | ClassNotFoundException | JRException ex) {
      FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_ERROR,
                      "Es gab einen Fehler bei der Best\u00E4tigung der Zahlung!",
                      "Es gab einen Fehler bei der Best\u00E4tigung der Zahlung!"));
      Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Diese Methode erstellt ein Bestellungs-PDF und liefert seinen Pfad
   * innerhalb des Projekts zurÃƒÂ¼ck.
   *
   * @param rk Rechnungskopf fÃƒÂ¼r den das Bestellungs-PDF erstellt werden
   * soll.
   * @return Pfad des PDFs innerhalb des Projekts
   * @throws IOException
   * @throws SQLException
   * @throws ClassNotFoundException
   * @throws JRException
   */
  private String erstelleBestellungsPDF(Reko rk) throws IOException, SQLException,
          ClassNotFoundException, JRException {
    JasperPrint jasperPrint;
    Class.forName("org.postgresql.Driver");
    try (Connection con = DriverManager.getConnection(JDBCURL, BENUTZERNAME, PASSWORT)) {
      List<Rezl> rezls = rk.getRezls();
      BigDecimal summe = new BigDecimal(0);
      for (Rezl rezl : rezls) {
        summe = summe.add(rezl.getVk_preis());
      }

      HashMap<String, Object> params = new HashMap<>();
      params.put("reko_id", rk.getId());
      params.put("rezl_sum", summe);
      String logo = FacesContext.getCurrentInstance().getExternalContext().
              getRealPath("/resources/images/herfert-und-herfert-logo.gif");
      params.put("logo_pfad", logo);

      ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
      try (InputStream is = ext.getResourceAsStream("/resources/reports/bestellung_jdbc.jasper")) {
        jasperPrint = JasperFillManager.fillReport(is, params, con);
        JasperExportManager.exportReportToPdfFile(jasperPrint,
                ext.getRealPath("/resources/reports/") + "bestellung_" + rk.getId() + ".pdf");
        return ext.getRealPath("/resources/reports/") + "bestellung_" + rk.getId() + ".pdf";
      }
    }
  }

  /**
   * Erstellt eine ZIP-Datei zu einem bestimmten Rechnungskopf.
   *
   * @param rk Rechnungskopf zu dem eine ZIP-Datei erstellt werden soll.
   * @throws IOException
   */
  private void erstelleZipDatei(Reko rk) throws IOException {
    String speicherpfadZIP = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("speicherpfadZIP");
    Random rd = new Random();
    long randomNumber;
    randomNumber = Math.abs(rd.nextLong());
    String downloadlink = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("kunden_webseiten_url")
            + "/faces/download.xhtml?rId=" + rk.getId() + "&dId=" + randomNumber;

    File f = new File(speicherpfadZIP + randomNumber + ".zip");
    try (FileOutputStream fos = new FileOutputStream(f);
            ZipOutputStream zos = new ZipOutputStream(fos)) {
      int i = 0;
      for (Rezl rz : rk.getRezls()) {
        i++;
        fuegeZipHinzu("/var/fotoherfertData" + rz.getArtikel().getHashUrl(), zos, i);
      }
    }

    rk.setUrl(downloadlink);
    dao.updateReko(rk);
  }

  /**
   * FÃƒÂ¼gt die ÃƒÂ¼bergebene Datei dem ZipOutputStream hinzu.
   *
   * @param fileName Dateinamen der hinzuzufÃƒÂ¼genden Datei.
   * @param zos ZipOutputStream auf die ZIP-Datei.
   * @param nummer Nummer des Bildes bei der Benennung.
   * @throws FileNotFoundException
   * @throws IOException
   */
  private void fuegeZipHinzu(String fileName, ZipOutputStream zos, int nummer)
          throws FileNotFoundException, IOException {

    FileInputStream fis = new FileInputStream(fileName);
    String[] splitted = fileName.split("\\.");
    ZipEntry zipEntry = new ZipEntry("Bild0" + nummer + "." + splitted[splitted.length - 1]);

    zos.putNextEntry(zipEntry);

    byte[] bytes = new byte[1024];
    int length;
    while ((length = fis.read(bytes)) >= 0) {
      zos.write(bytes, 0, length);
    }

    zos.closeEntry();
    fis.close();
  }

  /**
   * Sendet eine E-Mail an den in der Reko ÃƒÂ¼bergebenen Kunden mit der
   * ÃƒÂ¼bergebenen Bestellung im Anhang.
   *
   * @param rk Rechnungskopf zu dem eine Bestellungsmail generiert werden soll.
   * @param bestellung PDF-Datei mit der Bestellung.
   */
  private void sendeBestellungsMail(Reko rk, String bestellung) {
    try {
      Session s = MailUtilities.getGMailSession("fotoherfert4school@gmail.com", "Micmos87");
      String html = "<img alt='Bild' style='float: right;' "
              + "src='http://gfhost.htlstp.ac.at/fotoherfert4school_kunde/web_resources/fotoherfert_logo.gif'/>"
              + "<div><p>\n"
              + "Firma \n<br />"
              + "fotoherfert\n<br />"
              + "He&szlig;stra&szlig;e 16\n<br />"
              + "3100 St. P&ouml;lten\n"
              + "</p></div>\n"
              + "\n"
              + "<div><h2>fotoherfert4school - Webshop f&uuml;r Sch&uuml;lerfotos</h2>\n"
              + "<h3>Zahlungsbest&auml;tigung</h3>\n"
              + "\n"
              + "<p>Vielen Dank f&uuml;r Ihre Bestellung bei fotoherfert4school. Ihre Zahlung ist erfolgreich eingegangen."
              + "Im Anhang finden Sie dazu Ihre Bestellung.</p></div><br />\n";

      html = html + "<p>Unter folgendem Link k&ouml;nnen Sie die Fotos innerhalb der n&auml;chsten ";
      html = html + rk.getRezls().get(0).getArtikel().getKlasse().getSchule().getDownloadfrist();
      html = html + " Tage herunterladen: </p><a href='";
      html = html + rk.getUrl() + "'>Downloadlink</a><br>";
      Schule schule = rk.getRezls().get(0).getArtikel().getKlasse().getSchule();
      if (schule.isPartnerAnzeige()) {
        html = html + "<p>F&uuml;r die Entwicklung Ihrer Bilder empfehlen wir folgende Webseite: </p>";
        html = html + "<a href='" + schule.getPartnerURL() + "'>Fotostudio</a>";
      }
      MailUtilities.postHtmlWithAttachement(s, rk.getKunde().getEmail(), "fotoherfert4school - Zahlungsbest\u00E4tigung",
              html, bestellung, "Bestellung_" + rk.getId() + ".pdf");
      System.out.println("E-Mail gesendet!");
      // L\u00F6schen der Bestellung aus dem Resource-Ordner
      File f = new File(bestellung);
      if (f.delete()) {
        System.out.println("-- Die Datei " + bestellung + " wurde gel\u00F6scht.");
      } else {
        System.out.println("-- Die Datei " + bestellung + " wurde nicht gel\u00F6scht.");
      }

    } catch (MessagingException ex) {
      ex.printStackTrace();
      Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void zahlungLoeschen(Reko reko) {
    zeigeAlleRechnungen();

    FacesContext fc = FacesContext.getCurrentInstance();
    Reko r = dao.deleteReko(reko);
    if (r.getGueltig_bis() != null) {
      offeneRechnungen.remove(reko);
      fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
              "Die Rechnung wurde erfolgreich gel\u00F6scht!",
              "Die Rechnung wurde erfolgreich gel\u00F6scht!"));
    } else {
      fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
              "Fehler: Die Rechnung wurde nicht gel\u00F6scht!",
              "Fehler: Die Rechnung wurde nicht gel\u00F6scht!"));
    }
  }

  /**
   * Diese Methode wird bei einem ValueChangeEvent aufgerufen. Sie zeigt die
   * Rechnung mit der gesuchten Rechnungsnummer an.
   *
   * @param ve Das ausl\u00F6sende ValueChangeEvent
   */
  public void sucheRechnungsnummer(ValueChangeEvent ve) {
    if (ve.getNewValue() != null) {
      InputText in = (InputText) ve.getSource();
      //TabView auslesen
      TabView tv = (TabView) in.getParent().getParent().getParent().getParent();
      int rechnungsnummer = ((Long) ve.getNewValue()).intValue();
      aktRechnungen.clear();
      for (Reko rk : offeneRechnungen) {
        String rkId = String.format("%d", rk.getId());
        if (rkId.contains(String.format("%d", rechnungsnummer))) {
          aktRechnungen.add(rk);
          tv.setActiveIndex(1);
        }
      }
      if (!aktRechnungen.isEmpty()) {
        tv.setActiveIndex(1);
      } else {
//                in.setValue("");
        tv.setActiveIndex(1);
//                FacesContext f = FacesContext.getCurrentInstance();
//                FacesMessage m = new FacesMessage("Die Rechnungsnummer " + rechnungsnummer
//                        + " ist nicht in der Liste vorhanden!");
//                f.addMessage(null, m);
        aktRechnungen = new ArrayList<>(offeneRechnungen);
      }
    } else {
      aktRechnungen = new ArrayList<>(offeneRechnungen);
    }
  }

  /**
   * Diese Methode wird bei einem ValueChangeEvent aufgerufen. Sie zeigt die
   * best\u00E4tigte Rechnung mit der gesuchten Rechnungsnummer an.
   *
   * @param ve Das ausl\u00F6sende ValueChangeEvent
   */
  public void sucheBestaetigteRechnungsnummer(ValueChangeEvent ve) {
    if (ve.getNewValue() != null) {
      InputText in = (InputText) ve.getSource();
      //TabView auslesen
      TabView tv = (TabView) in.getParent().getParent().getParent().getParent();
      int rechnungsnummer = ((Long) ve.getNewValue()).intValue();
      aktBestaetigteRechnungen.clear();
      for (Reko rk : bestaetigteRechungen) {
        String rkId = String.format("%d", rk.getId());
        if (rkId.contains(String.format("%d", rechnungsnummer))) {
          aktBestaetigteRechnungen.add(rk);
          tv.setActiveIndex(1);
        }
      }
      if (!aktBestaetigteRechnungen.isEmpty()) {
        tv.setActiveIndex(1);
      } else {
//                in.setValue("");
        tv.setActiveIndex(1);
//                FacesContext f = FacesContext.getCurrentInstance();
//                FacesMessage m = new FacesMessage("Die Rechnungsnummer " + rechnungsnummer
//                        + " ist nicht in der Liste vorhanden!");
//                f.addMessage(null, m);
        aktBestaetigteRechnungen = new ArrayList<>(bestaetigteRechungen);
      }
    } else {
      aktBestaetigteRechnungen = new ArrayList<>(bestaetigteRechungen);
    }
  }

  /**
   * Diese Methode zeigt alle offenen Rechnunen an.
   */
  public void zeigeAlleRechnungen() {
    UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
    final UIComponent[] comp = new UIComponent[1];
    final String id = "suchfeld";

    root.visitTree(new FullVisitContext(FacesContext.getCurrentInstance()), new VisitCallback() {
      @Override
      public VisitResult visit(VisitContext context, UIComponent component) {
        if (component.getId().equals(id)) {
          comp[0] = component;
          return VisitResult.COMPLETE;
        }
        return VisitResult.ACCEPT;
      }
    });
    InputText input = (InputText) comp[0];
    input.setValue(null);
    aktRechnungen = new ArrayList<>(offeneRechnungen);
  }

  /**
   * Diese Methode zeigt alle best\u00E4tigten Rechnunen an.
   */
  public void zeigeAlleBestaetigteRechnungen() {
    UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
    final UIComponent[] comp = new UIComponent[1];
    final String id = "suchfeld_bestaetigteRechnunen";

    root.visitTree(new FullVisitContext(FacesContext.getCurrentInstance()), new VisitCallback() {
      @Override
      public VisitResult visit(VisitContext context, UIComponent component) {
        if (component.getId().equals(id)) {
          comp[0] = component;
          return VisitResult.COMPLETE;
        }
        return VisitResult.ACCEPT;
      }
    });
    InputText input = (InputText) comp[0];
    input.setValue(null);
    aktBestaetigteRechnungen = new ArrayList<>(bestaetigteRechungen);
  }

  /**
   * Diese Methode rechnet f\u00FCr einen \u00FCbergebenen Rechnungskopf die
   * Summe der Betr\u00E4ge der Rechnugnszeilen aus.
   *
   * @param rk Rechnungskopf
   * @return Die Rechnungssumme des \u00FCbergebenen Rechnungskopfs
   */
  public double rechnungssumme(Reko rk) {
    if (rk == null) {
      FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_ERROR,
                      "Die Rechnungssumme konnte nicht ausgerechnet werden!",
                      "Der Rechnungskopf ist null, somit konnte "
                      + "die Rechnungssumme nicht errechnet werden!"));
      return 0.0;
    }
    BigDecimal sum = new BigDecimal(0);
    for (Rezl rz : rk.getRezls()) {
      sum = sum.add(rz.getVk_preis());
    }
    return sum.doubleValue();
  }

  /**
   * Wird aufgerufen wenn bei der Preisveraltung eine Zeile ge\u00E4ndert wird
   *
   * @param event
   */
  public void onRowEditArtikeltyp(RowEditEvent event) {
    FacesContext fc = FacesContext.getCurrentInstance();

    Artikeltyp arttyp = (Artikeltyp) event.getObject();

    if (!dao.updateArtikeltyp(arttyp)) {
      fc.addMessage(null, new FacesMessage("Editierung fehlgeschlagen", "Das Preisprofil konnte nicht in die Datenbank geschrieben werden!"));
    }

    fc.addMessage(null, new FacesMessage("Editierung erfolgreich", "Das Preisprofil wurde editiert!"));
  }

  /**
   * Wird aufgerufen, wenn bei der Preisverwaltung die \u00C4nderung einer Zeile
   * abgebrochen wird
   *
   * @param event
   */
  public void onRowCancelArtikeltyp(RowEditEvent event) {
    FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage("Editierungt abgebrochen", "Die Editierung wurde abgebrochen!"));
  }

  /**
   * Diese Methode f\u00FCgt eine neue Schule der Datenbank hinzu
   */
  public void neueSchuleHinzufuegen() {
    FacesContext fc = FacesContext.getCurrentInstance();

    if (dao.insertSchule(neueSchule)) {
      schulen = dao.liefereSchulen();
      fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
              "Hinzuf\u00FCgen erfolgreich", "Schule wurde erfolgreich in die Datenbank geschrieben"));
    } else {
      fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
              "Hinzuf\u00FCgen fehlgeschlagen", "Schule wurde nicht in die Datenbank geschrieben"));
    }
    schliesseNeueSchuleDialog();
  }

  /**
   * L\u00F6scht die \u00FCbergebene Sch\u00FCler aus der Datenbank.
   *
   * @param schule
   */
  public void loescheSchule(Schule schule) {
    FacesContext fc = FacesContext.getCurrentInstance();

    Schule s;
    if ((s = dao.deleteSchule(schule)) == null) {
      fc.addMessage(null, new FacesMessage("L\u00F6schen fehlgeschlagen", "Schule konnte nicht aus der Datenbank gel\u00F6scht werden"));
    } else {
      loescheSchuleVonServer(schule);
      loescheDownloadDateien(schule);
      schulen = dao.liefereSchulen();
      fc.addMessage(null, new FacesMessage("L\u00F6schen erfolgreich", "Schule wurde aus der Datenbank gel\u00F6scht"));
    }
  }

  public void loescheDownloadDateien(Schule schule) {
    String speicherPfadZIP = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("speicherpfadZIP");
    List<Reko> rekos = dao.liefereRekos();
    for (Reko reko : rekos) {
      if (schule.equals(reko.getSchule())) {
        String[] help = reko.getUrl().split("=");
        String zipName = help[help.length - 1];
        loescheOrdner(new File(speicherPfadZIP + zipName + ".zip"));
      }
    }
  }

  public void loescheSchuleVonServer(Schule schule) {
    String pfad = FacesContext.getCurrentInstance().
            getExternalContext().getInitParameter("speicherpfad");
    File schulOrdner = new File(pfad + "Schule" + schule.getId());
    loescheOrdner(schulOrdner);
  }

  public void loescheOrdner(File ordner) {
    String[] childs = ordner.list();
    if (childs != null && childs.length > 0) {
      for (String child : childs) {
        File f = new File(ordner.getPath() + "/" + child);
        if (f.isDirectory()) {
          loescheOrdner(f);
        } else {
          f.delete();
        }
      }
    }
    ordner.delete();
  }

  /**
   * \u00D6ffnet den Dialog f\u00FCr das Hinzuf\u00FCgen einer neuen Schule in
   * der Schulverwaltung.
   */
  public void oeffneNeueSchuleDialog() {
    RequestContext.getCurrentInstance().execute("PF('newSch').show()");
  }

  /**
   * Schlie\u00DFt den Dialog f\u00FCr das Hinzuf\u00FCgen einer neuen Schule in
   * der Schulverwaltung.
   */
  public void schliesseNeueSchuleDialog() {
    neueSchuleZuruecksetzen();
    RequestContext.getCurrentInstance().execute("PF('newSch').hide()");
  }

  /**
   * \u00D6ffnet den Dialog f\u00FCr das Bearbeiten einer Schule
   *
   * @param schule
   */
  public void oeffneBearbeiteAktSchuleDialog(Schule schule) {
    aktSchule = schule;
    RequestContext.getCurrentInstance().execute("PF('editSch').show()");
  }

  /**
   * Schlie\u00DFt den Dialog f\u00FCr das Bearbeiten einer Schule
   */
  public void schliesseBearbeiteAktSchuleDialog() {
    aktSchuleZuruecksetzen();
    RequestContext.getCurrentInstance().execute("PF('editSch').hide()");
  }

  /**
   * Wird aufgerufen wenn einer \u00C4nderung einer Schule gepseichert werden
   * soll.
   */
  public void speichereAenderungenAktSchule() {
    FacesContext fc = FacesContext.getCurrentInstance();

    if (dao.updateSchule(aktSchule)) {
      schulen = dao.liefereSchulen();
      fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
              "\u00C4nderung erfolgreich", "Schule wurde erfolgreich ge\u00E4ndert"));
    } else {
      fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
              "\u00C4nderung fehlgeschlagen", "Schule wurde nicht ge\u00E4ndert"));
    }
    schliesseBearbeiteAktSchuleDialog();
  }

  /**
   * Setzt die Variable neueSchule zur\u00FCck. Diese Methode wird aufgerufen,
   * wenn man im Command Dialog neueSchule auf abbrechen klickt
   */
  public void neueSchuleZuruecksetzen() {
    neueSchule = new Schule();

    // Datum setzen
    LocalDate ld = LocalDate.now().plusDays(180);
    Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

    neueSchule.setAnzeigefrist(Date.from(instant));
    neueSchule.setDownloadfrist(30);
  }

  /**
   * Setzt die Variable aktSchule zur\u00FCck.
   */
  public void aktSchuleZuruecksetzen() {
    aktSchule = new Schule();
  }

  /**
   * Diese Methode wird aufgerufen wenn die aktuelle Schule bearbeitet wird.
   */
  public void aktualisiereAktSchule() {
    FacesContext fc = FacesContext.getCurrentInstance();

    if (!dao.updateSchule(aktSchule)) {
      fc.addMessage(null, new FacesMessage("Editierung fehlgeschlagen", "Die Schule konnte nicht in die Datenbank geschrieben werden!"));
    }

    fc.addMessage(null, new FacesMessage("Editierung erfolgreich", "Die Schule wurde editiert!"));
  }

  public void erzeugeZugangscodeAktSchulePDF(Schule aktSchule) throws ClassNotFoundException {
    this.aktSchule = aktSchule;
    JasperPrint jasperPrint;

    Class.forName("org.postgresql.Driver");
    try (Connection con = DriverManager.getConnection(JDBCURL, BENUTZERNAME, PASSWORT)) {

      // ParameterMap definieren
      Map<String, Object> parameter = new HashMap<>();
      ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
      parameter.put("AktSchule_ID", this.aktSchule.getId());
      parameter.put("AktSchule_Name", this.aktSchule.getName());
      parameter.put("AktSchule_Anzeigefrist", this.aktSchule.getAnzeigefrist());
      parameter.put("URL_Kundenwebsite", ext.getInitParameter("kunden_webseiten_url"));
      parameter.put("URL_Logo", ext.getRealPath("/resources/images/herfert-und-herfert-logo.gif"));

      HttpServletResponse response = (HttpServletResponse) ext.getResponse();

      response.reset();
      response.setContentType("application/pdf");
      response.setHeader("Content-Disposition", "attachment; filename=\"Zugangscodes - " + aktSchule.getName() + ".pdf\"");

      try (InputStream is = ext.getResourceAsStream("/resources/reports/zugangscodes.jasper");
              OutputStream output = response.getOutputStream()) {

        // Report compilieren
        // Hier muss der Java-Compiler im Pfad liegen
        // jasperReport = JasperCompileManager.compileReport(ext.getRealPath("/reports/EmployeeReport.jrxml"));
        // System.out.println("Report erfolgreich compiliert");
        // Report mit Daten bef\u00FCllen
        // jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, con);
        jasperPrint = JasperFillManager.fillReport(is, parameter, con);
        System.out.println("Report erfolgreich bef\u00FCllt");

        // Report Exportieren
        JasperExportManager.exportReportToPdfStream(jasperPrint, output);
        // JasperExportManager.exportReportToPdfFile(jasperPrint, "Test.pdf");
        System.out.println("Report erfolgreich erzeugt");
      } catch (JRException | IOException ex) {
        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
      }
    } catch (SQLException ex) {
      Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex1) {
      Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex1);
    }

    FacesContext.getCurrentInstance().responseComplete();
  }

  /**
   * Diese Methode erzeugt den Zugangsschl\u00FCssel f\u00FCr die
   * \u00FCbergebene Klasse.
   *
   * @param klasse
   * @return Liefert bei Erfolg die Klasse mit dem gesetzten
   * Zugangsschl\u00FCssel, bei Misserfolg null
   */
  public Klasse erzeugeZugangscode(Klasse klasse) {
    String code;

    Random rd = new Random();

    code = "";

    for (int i = 0; i < 8; i++) {
      if (i % 3 == 0) {
        code += rd.nextInt(8) + 1;
      } else {
        code += (char) (rd.nextInt(24) + 65);
      }
    }

    code = code.replaceAll("[OQ]", "A");

    klasse.setCode(code);

    return klasse;
  }

  /**
   * Diese Methode wird aufgerufen wenn die Email des angemeldeten Benutzers
   * ge\u00E4ndert wird.
   *
   * @param ae
   */
  public void aendereEmail(ActionEvent ae) {
    FacesContext fc = FacesContext.getCurrentInstance();
    CommandButton cb = (CommandButton) ae.getSource();
    //TabView auslesen
    TabView tv = (TabView) cb.getParent().getParent().getParent();

    // Hashing
    String eingegebenesPasswort = HashingUtilities.hashStringMitSalt(
            neuerBenutzer.getPasswort(), benutzer.getSalt());
    if (neuerBenutzer.getEmail().equals(email_wh)
            && eingegebenesPasswort.equals(benutzer.getPasswort())) {
      String alteEmail = benutzer.getEmail();
      try {
        // JDBC-Realm Berechtigung \u00E4ndern!!
        Class.forName("org.postgresql.Driver");
        try (Connection con = DriverManager.getConnection(JDBCURL, BENUTZERNAME, PASSWORT)) {
          try (PreparedStatement ps = con.prepareStatement(
                  "DELETE FROM benutzer_gruppen WHERE benutzername = ?")) {
            ps.setString(1, alteEmail);
            ps.executeUpdate();
          }
        }

        benutzer.setEmail(neuerBenutzer.getEmail());
        if (dao.updateBenutzer(benutzer)) {

          // JDBC-REALM neue Berechtigung hinzuf\u00FCgen
          try (Connection con = DriverManager.getConnection(JDBCURL, BENUTZERNAME, PASSWORT)) {
            try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO benutzer_gruppen VALUES (?, 'admin')")) {
              ps.setString(1, neuerBenutzer.getEmail());
              ps.executeUpdate();
            }
          }

          fc.addMessage(null, new FacesMessage("Die Email-Adresse wurde erfolgreich ge\u00E4ndert!"));
          neuerBenutzer = new Benutzer();
          email_wh = "";
          tv.setActiveIndex(3);
        } else {
          fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                  "Es gab einen Fehler in der Verarbeitung der Anfrage!",
                  "Es gab einen Fehler in der Verarbeitung der Anfrage!"));

          // JDBC-Realm Berechtigungen \u00E4ndern
          try (Connection con = DriverManager.getConnection(JDBCURL, BENUTZERNAME, PASSWORT)) {
            try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO benutzer_gruppen VALUES (?, 'admin')")) {
              ps.setString(1, alteEmail);
              ps.executeUpdate();
            }
          }
        }
      } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Es gab einen Fehler in der Verarbeitung der Anfrage!",
                "Es gab einen Fehler in der Verarbeitung der Anfrage!"));

        // JDBC-Realm Berechtigungen \u00E4ndern
        try (Connection con = DriverManager.getConnection(JDBCURL, BENUTZERNAME, PASSWORT)) {
          try (PreparedStatement ps = con.prepareStatement(
                  "INSERT INTO benutzer_gruppen VALUES (?, 'admin')")) {
            ps.setString(1, alteEmail);
            ps.executeUpdate();
          }
        } catch (SQLException sqlEx) {
          Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, sqlEx);
        }
      }
    } else {
      fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
              "Fehler",
              "Die Email-Adresse konnte nicht ge\u00E4ndert werden! Bitte \u00FCberpr\u00FCfen "
              + "Sie ob das aktuelle Passwort korrekt ist und ob die "
              + "neuen Email Adressen ident sind!"));
      neuerBenutzer = new Benutzer();
      email_wh = "";
      tv.setActiveIndex(3);
    }
  }

  /**
   * Diese Methode wird aufgerufen wenn das Passwort des angemeldeten Benutzers
   * ge\u00E4ndert wird.
   *
   * @param ae
   */
  public void aenderePasswort(ActionEvent ae) {
    FacesContext fc = FacesContext.getCurrentInstance();
    CommandButton cb = (CommandButton) ae.getSource();
    //TabView auslesen
    TabView tv = (TabView) cb.getParent().getParent().getParent();

    // Hashing
    String eingegebenesPasswort = HashingUtilities.hashStringMitSalt(
            passwort_alt, benutzer.getSalt());
    if (neuerBenutzer.getPasswort().equals(passwort_wh)
            && eingegebenesPasswort.equals(benutzer.getPasswort())) {
      String salt = HashingUtilities.erzeugeSalt(128, 32);
      String hashedPwd = HashingUtilities.hashStringMitSalt(neuerBenutzer.getPasswort(), salt);
      benutzer.setSalt(salt);
      benutzer.setPasswort(hashedPwd);

      if (dao.updateBenutzer(benutzer)) {
        fc.addMessage(null, new FacesMessage("Das Passwort wurde erfolgreich ge\u00E4ndert!"));
        neuerBenutzer = new Benutzer();
        passwort_alt = "";
        passwort_wh = "";
        tv.setActiveIndex(3);
        return;
      }
    }

    fc.addMessage(null, new FacesMessage("Fehler: Das Passwort konnte nicht "
            + "ge\u00E4ndert werden! Bitte \u00FCberpr\u00FCfen Sie ob das aktuelle Passwort "
            + "korrekt ist und ob die neuen Passw\u00F6rter ident sind!"));
    neuerBenutzer = new Benutzer();
    passwort_alt = "";
    passwort_wh = "";
    tv.setActiveIndex(3);
  }

  public void entpackenUndHochladen(FileUploadEvent event) {
        //ordnerstruktur:
    //->ordner                        bezeichnung egal wird uebersprungen
    //       |-> klasse               bezeichnung wie die klasse
    //               |-> bilder       benoetigt Zusatz fuer Klassenfoto(KF)|Geschwisterfoto(GF)
    //                                ansonsten egal
    //       |-> klasse
    //               |-> bilder
    //.
    //.
    //.

    //Progressbar wird auf 0 gesetzt
    this.setFortschritt(0L);
    //pool fuer die Threads zum hinzufuegen des Wasserzeichens
    ExecutorService pool = Executors.newFixedThreadPool(32);
    //erstes bild eines schuelers ist master
    boolean master = false;
    //laufende nummer innerhalb einer klasse
    int knr = 0;
    Klasse aktKlasse = null;
    Artikel aktArtikel;
    String schuleOrdner = "Schule" + aktSchule.getId() + "/";
    String speicherort = FacesContext.getCurrentInstance().
            getExternalContext().getInitParameter("speicherpfad") + schuleOrdner;
    FacesMessage nachricht = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hochladen abgeschlossen",
            "Kehren Sie zurück zur Schulverwaltung");
    List<Artikel> artikel = new ArrayList<>();
    List<Klasse> klassen = new ArrayList<>();

    try (ZipInputStream zis = new ZipInputStream(event.getFile().getInputstream())) {
      //Ausgabeordner anlegen
      File ausgabeOrdner = new File(speicherort);
      if (!ausgabeOrdner.exists()) {
        ausgabeOrdner.mkdirs();
      }

      ZipEntry ze = null;
      if ((ze = zis.getNextEntry()) != null) {
        new File(speicherort + ze.getName()).mkdirs();
      }

      // GesamtgrÃ¶ÃŸe der Datei bestimmen fÃ¼r ProgressBar
      long gesamtSize = event.getFile().getSize();
      long restSize = 0;

      //solange es noch Eintraege in der Zip Datei gibt 
      while ((ze = zis.getNextEntry()) != null) {
        // Aktuelle GrÃ¶ÃŸe des Eintrags bestimmen
        long aktSize = ze.getCompressedSize();
        // Bereits downgeloadete GrÃ¶ÃŸe
        restSize += aktSize;
        //das File des derzeitigeFile aktDatei = new File(speicherort + ze.getName());n Eintrags im Speicherort anlegen
        File aktDatei = new File(speicherort + ze.getName());
        //sollte das aktuelle File die thumbs.db order ein ordner den 
        //das macosx erstellt sein dann ueberspringen
        if (!"thumbs.db".equalsIgnoreCase(aktDatei.getName())
                && !"__MACOSX".equalsIgnoreCase(aktDatei.getName())
                && !aktDatei.getPath().toLowerCase().contains("__MACOSX".toLowerCase())
                && !".DS_Store".equalsIgnoreCase(aktDatei.getName())) {
          //hat das aktuelle file einen . im Name --> .jpg etc 
          //dann ist es ein File ansonsten ein Ordner -> neue Klasse
          String h[] = aktDatei.getName().split("\\.");
          if (h.length > 1) {

            if (aktDatei.getName().toLowerCase().endsWith("jpg")) {//aktDatei.getName().substring(aktDatei.getName().lastIndexOf("\\.")).equalsIgnoreCase("jpg")) {
              //File auf den Server uebertragen
              byte[] buffer = new byte[1024];

              try (FileOutputStream fos = new FileOutputStream(aktDatei)) {
                int len;
                while ((len = zis.read(buffer)) > 0) {
                  fos.write(buffer, 0, len);
                }
              }

              // Fortschrittsanzeige aktualisieren + berechnen
              this.setFortschritt((restSize * 100) / gesamtSize);

              String split[] = aktDatei.getPath().split("[\\\\/]");
              String path = aktDatei.getPath().substring(0, aktDatei.getPath().length() - split[split.length - 1].length());

              File aktDatei_hashed = new File(path + generiereUniqueHashwert(aktDatei.getName()));
              Files.move(aktDatei.toPath(), aktDatei_hashed.toPath(), StandardCopyOption.REPLACE_EXISTING);
              //ist das Bild schwarz wird es uebersprungen
              if (!WasserzeichenRunnable.checkIfBlack(aktDatei_hashed)) {
                Artikeltyp typ = null;
                List<Artikeltyp> typen = dao.liefereArtikeltypen();
                //festlegen des Artikeltyps des aktuellen Bilds mit bezeichnung GF, KF oder nichts im Namen
                String[] s = aktDatei.getName().split("_");
                s = s[s.length - 1].split("\\.");
                for (Artikeltyp a : typen) {
                  if (s[s.length - 2].equalsIgnoreCase(a.getAbkz())) {
                    typ = a;

                    break;
                  }
                }
                //wenn kein spezieller typ im namen vermerkt ist, 
                //dann ist es ein portrait
                if (typ == null) {
                  typ = dao.liefereArtikeltyp("PF");
                }
                String split2[] = ze.getName().split("[\\\\/]");
                String path2 = ze.getName().substring(0, ze.getName().length() - split2[split2.length - 1].length());
                String serverpfad = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("serverPfad");
                //wenn klassenfoto dann ist knr 0
                if ("KF".equalsIgnoreCase(typ.getAbkz())) {
                  aktArtikel = new Artikel(serverpfad + schuleOrdner + ze.getName(), serverpfad + schuleOrdner + path2 + aktDatei_hashed.getName(), 0, master, typ, aktKlasse);
                } else {
                  if (knr == 0) {
                    knr++;
                  }
                  aktArtikel = new Artikel(serverpfad + schuleOrdner + ze.getName(), serverpfad + schuleOrdner + path2 + aktDatei_hashed.getName(), knr, master, typ, aktKlasse);
                  // /bilder/schule+id/schule/2C/foto, /bilder/schule+id/schule/2C/hashfoto
                }
                artikel.add(aktArtikel);
                //Thread zum wasserzeichen hinzufuegen starten
                pool.execute(new WasserzeichenRunnable(aktDatei, aktDatei_hashed,
                        FacesContext.
                        getCurrentInstance().getExternalContext()
                        .getInitParameter("wasserzeichen")));
                master = false;
              } else {
                aktDatei_hashed.delete();
                //wenn das aktuelle Bild schwarz ist, dann muss das naechste ein Master sein
                master = true;
                knr++;
              }
            }
          } else {
            //da die aktuelle Datei ein Ordner ist 
            //wird dieser nun erstellt
            aktDatei.mkdirs();
            aktKlasse = null;
            knr = 0;
            //falls die aktuelle Klasse bereits existiert referenz holen
            for (Klasse k : aktSchule.getKlassen()) {
              if (aktDatei.getName().equalsIgnoreCase(k.getName())) {
                aktKlasse = k;
                break;
              }
            }
            //sollte die aktuelle Klasse noch nicht existieren dann wird sie nun erstellt
            if (aktKlasse == null) {
              aktKlasse = new Klasse(aktDatei.getName().toUpperCase(), "", aktSchule);
              aktKlasse = erzeugeZugangscode(aktKlasse);
              klassen.add(aktKlasse);
//                            dao.insertKlasse(aktKlasse);
            }
          }
        }
        zis.closeEntry();
      }
      this.setFortschritt(100L);
      zis.close();
      for (Klasse kl : klassen) {
        dao.insertKlasse(kl);
      }
      for (Artikel art : artikel) {
        dao.insertArtikel(art);
      }
      schulen = dao.liefereSchulen();
    } catch (ZipException zex) {
      Logger.getLogger(AdminController.class
              .getName()).log(Level.SEVERE, null, zex);
//            System.out.println("Fehler!!!!!!!!");
      nachricht = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Die ZIP-Datei ist fehlerhaft, bitte laden Sie eine neue hoch!");
      loescheSchuleVonServer(aktSchule);
    } catch (IOException ex) {
//            ex.printStackTrace();
      Logger.getLogger(AdminController.class
              .getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Fehler!!!!!!!!");
      nachricht = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Beim Hochladen trat ein Fehler auf!");
      loescheSchuleVonServer(aktSchule);
    }
    FacesContext fc = FacesContext.getCurrentInstance();

    fc.addMessage(null, nachricht);
    pool.shutdown();
  }

  /**
   * Diese Methode sendet erneut eine Mail mit den Rechnungsdaten und dem
   * Downloadlink der Bilder an den Kunden.
   *
   * @param reko
   */
  public void sendeBestellungsMailErneut(Reko reko) {
    try {
      String bestellungsPDF = erstelleBestellungsPDF(reko);
      sendeBestellungsMail(reko, bestellungsPDF);
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
              "Die E-Mail wurde erfolgreich gesendet!",
              "Die E-Mail wurde erfolgreich gesendet!"));
    } catch (IOException | SQLException | ClassNotFoundException | JRException ex) {
      Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
      FacesContext fc = FacesContext.getCurrentInstance();
      fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
              "Fehler! Die E-Mail konnte nicht gesendet werden!",
              "Fehler! Die E-Mail konnte nicht gesendet werden!"));
    }
  }

  /*  Getter und Setter  */
  public Benutzer getBenutzer() {
    return benutzer;
  }

  public void setBenutzer(Benutzer benutzer) {
    this.benutzer = benutzer;
  }

  public List<Reko> getAktRechnungen() {
    return aktRechnungen;
  }

  public void setAktRechnungen(List<Reko> aktRechnungen) {
    this.aktRechnungen = aktRechnungen;
  }

  public List<Reko> getBestaetigteRechungen() {
    return bestaetigteRechungen;
  }

  public void setBestaetigteRechungen(List<Reko> bestaetigteRechungen) {
    this.bestaetigteRechungen = bestaetigteRechungen;
  }

  public List<Reko> getAktBestaetigteRechnungen() {
    return aktBestaetigteRechnungen;
  }

  public void setAktBestaetigteRechnungen(List<Reko> aktBestaetigteRechnungen) {
    this.aktBestaetigteRechnungen = aktBestaetigteRechnungen;
  }

  public List<Reko> getOffeneRechnungen() {
    return offeneRechnungen;
  }

  public void setOffeneRechnungen(List<Reko> offeneRechnungen) {
    this.offeneRechnungen = offeneRechnungen;
  }

  public List<Artikeltyp> getPreisprofile() {
    return preisprofile;
  }

  public void setPreisprofile(List<Artikeltyp> preisprofile) {
    this.preisprofile = preisprofile;
  }

  public List<Schule> getSchulen() {
    return schulen;
  }

  public void setSchulen(List<Schule> schulen) {
    this.schulen = schulen;
  }

  public Schule getNeueSchule() {
    return neueSchule;
  }

  public void setNeueSchule(Schule neueSchule) {
    this.neueSchule = neueSchule;
  }

  public Schule getAktSchule() {
    return aktSchule;
  }

  public void setAktSchule(Schule aktSchule) {
    this.aktSchule = aktSchule;
  }

  public boolean isSchuleBearbeiten() {
    return schuleBearbeiten;
  }

  public void setSchuleBearbeiten(boolean schuleBearbeiten) {
    this.schuleBearbeiten = schuleBearbeiten;
  }

  public Benutzer getNeuerBenutzer() {
    return neuerBenutzer;
  }

  public void setNeuerBenutzer(Benutzer neuerBenutzer) {
    this.neuerBenutzer = neuerBenutzer;
  }

  public String getEmail_wh() {
    return email_wh;
  }

  public void setEmail_wh(String email_wh) {
    this.email_wh = email_wh;
  }

  public String getPasswort_alt() {
    return passwort_alt;
  }

  public void setPasswort_alt(String passwort_alt) {
    this.passwort_alt = passwort_alt;
  }

  public String getPasswort_wh() {
    return passwort_wh;
  }

  public void setPasswort_wh(String passwort_wh) {
    this.passwort_wh = passwort_wh;
  }

  public Date getAktDatum() {
    return aktDatum;
  }

  public void setAktDatum(Date aktDatum) {
    this.aktDatum = aktDatum;
  }

  public Long getFortschritt() {
    return fortschritt;
  }

  public void setFortschritt(Long fortschritt) {
    this.fortschritt = fortschritt;
  }
}
