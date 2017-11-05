/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import at.htlstp.fotoherfert4school_admin.db.HibernateDAO;
import at.htlstp.fotoherfert4school_admin.db.IDAO;
import at.htlstp.fotoherfert4school_admin.entity.Benutzer;
import at.htlstp.fotoherfert4school_admin.entity.Schule;
import at.htlstp.fotoherfert4school_admin.others.HashingUtilities;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 20100226
 */
@Named(value = "adminLoginCon")
@RequestScoped
public class AdminLoginController {

    private IDAO dao;

    @Inject
    private AdminController adminCon;

    private String email;
    private String passwort;

    public AdminLoginController() {
    }

    @PostConstruct
    public void init() {
        dao = new HibernateDAO();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String login() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Benutzer b;

        // Hashing
        if ((b = dao.loginBenutzer(email)) != null) {
            String hashedPwd = HashingUtilities.hashStringMitSalt(passwort, b.getSalt());
            if (b.getPasswort().equals(hashedPwd)) {
                adminCon.setBenutzer(b);

//                HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
//                try {
                    //Überprüfen des Ablaufes der Anzeigefrist
                    List<Schule> schulen = dao.liefereSchulen();
                    for (int i = 0; i < schulen.size(); i++) {
                        Date frist = schulen.get(i).getAnzeigefrist();
                        // Downloadfrist + Anzeigefrist berechnen
                        Date anzeigefristDownloadfrist = new Date(
                                java.sql.Date.valueOf(LocalDate.now().
                                        plusDays(schulen.get(i).getDownloadfrist())).getTime());
                        if (anzeigefristDownloadfrist.before(new Date())) {
                            adminCon.loescheSchule(schulen.get(i));
                        }
                    }

                    //JDBC-Realm Authentifizierung
//                    if (request.getUserPrincipal() != null) {
//                        request.logout();
//                    }
//                    request.login(email, hashedPwd);

                    // Erfolgsmeldung
                    FacesMessage m = new FacesMessage("Login erfolgreich");
                    fc.addMessage(null, m);

                    // Setzt den SessionTimeout auf 30 Minuten
                    HttpSession s = (HttpSession) FacesContext.getCurrentInstance()
                            .getExternalContext().getSession(false);
                    s.setMaxInactiveInterval(1800);

                    adminCon.init();
                    return "adminmenu";

                    // JDBC-Realm
//                } catch (ServletException ex) {
//                    FacesMessage mes = new FacesMessage("Login fehlgeschlagen", "Es gab einen Verarbeitungsfehler!");
//                    mes.setSeverity(FacesMessage.SEVERITY_ERROR);
//                    Logger.getLogger(AdminLoginController.class.getName()).log(Level.SEVERE, null, ex);
//                    fc.addMessage(null, mes);
//                    email = passwort = "";
//                    return "adminLogin.xhtml";
//                }
            }
        }
        // Fehlermeldung
        FacesMessage m = new FacesMessage("Login fehlgeschlagen", "Passwort und/oder Benutzername sind/ist nicht korrekt!");
        m.setSeverity(FacesMessage.SEVERITY_ERROR);
        fc.addMessage(null, m);
        email = passwort = "";
        return "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
