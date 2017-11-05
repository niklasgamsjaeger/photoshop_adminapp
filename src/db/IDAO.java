/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.fotoherfert4school_admin.db;

import at.htlstp.fotoherfert4school_admin.entity.Artikel;
import at.htlstp.fotoherfert4school_admin.entity.Artikeltyp;
import at.htlstp.fotoherfert4school_admin.entity.Benutzer;
import at.htlstp.fotoherfert4school_admin.entity.Bezahlmethode;
import at.htlstp.fotoherfert4school_admin.entity.Klasse;
import at.htlstp.fotoherfert4school_admin.entity.Kunde;
import at.htlstp.fotoherfert4school_admin.entity.Reko;
import at.htlstp.fotoherfert4school_admin.entity.Rezl;
import at.htlstp.fotoherfert4school_admin.entity.Schule;
import java.util.List;
import javax.enterprise.context.Dependent;

/**
 *
 * @author 20100226
 */
//@Dependent
public interface IDAO {

    /**
     * Fügt der DB einen neuen Kunden hinzu.
     *
     * @param kunde
     * @return liefert bei Erfolg "true", bei Fehler "false"
     */
    boolean insertKunde(Kunde kunde);

    /**
     * Fügt der DB einen neuen Rechnungskopf hinzu.
     *
     * @param reko Rechnungskopf
     * @return liefert bei Erfolg "true", bei Fehler "false"
     */
    boolean insertReko(Reko reko);

    /**
     * Fügt der DB eine neue Bezahlmethode hinzu.
     *
     * @param bezahlmethode
     * @return liefert bei Erfolg "true", bei Fehler "false"
     */
    boolean insertBezahlmethode(Bezahlmethode bezahlmethode);

    /**
     * Fügt der DB eine neue Rechnungszeile hinzu.
     *
     * @param rezl Rechnungszeile
     * @return liefert bei Erfolg "true", bei Fehler "false"
     */
    boolean insertRezl(Rezl rezl);

    /**
     * Fügt der DB einen neuen Artikel bzw. Foto hinzu.
     *
     * @param artikel
     * @return liefert bei Erfolg "true", bei Fehler "false"
     */
    boolean insertArtikel(Artikel artikel);

    /**
     * Fügt der DB eine neue Klasse hinzu.
     *
     * @param klasse
     * @return liefert bei Erfolg "true", bei Fehler "false"
     */
    boolean insertKlasse(Klasse klasse);

    /**
     * Fügt der DB eine neue Schule hinzu.
     *
     * @param schule
     * @return liefert bei Erfolg "true", bei Fehler "false"
     */
    boolean insertSchule(Schule schule);

    /**
     * Fügt der DB einen neuen Artikeltyp hinzu.
     *
     * @param artikeltyp
     * @return liefert bei Erfolg "true", bei Fehler "false"
     */
    boolean insertArtikeltyp(Artikeltyp artikeltyp);

    /**
     * Fügt der DB einen neuen benutzer hinzu.
     *
     * @param benutzer
     * @return liefert bei Erfolg "true", bei Fehler "false"
     */
    boolean insertBenutzer(Benutzer benutzer);

    /**
     * Liefert alle gültigen Artikeltypen.
     *
     * @return Liste von Artikeltypen und null wenn keine existieren
     */
    List<Artikeltyp> liefereArtikeltypen();

    /**
     * Liefert einen Artikeltyp, der dem übergebenen Kriterium entspricht.
     *
     * @param abkz Abkürzung des Artikeltyps
     * @return Artikeltyp bei Erfolg, null falls dieser nicht existiert
     */
    Artikeltyp liefereArtikeltyp(String abkz);

    /**
     * Liefert alle Artikel.
     *
     * @return Liste von Artikel und null wenn keine existieren
     */
    List<Artikel> liefereArtikel();

    /**
     * Liefert alle gültigen Schulen.
     *
     * @return Liste von Schulen und null wenn keine existieren
     */
    List<Schule> liefereSchulen();

    /**
     * Liefert alle offenen Rechnungen die noch nicht bezahlt wurden.
     *
     * @return Liste von Rechnungsköpfen und null wenn keine existieren
     */
    List<Reko> liefereOffeneRechnungen();

    /**
     * Liefert alle bestätigte Rechnungen.
     *
     * @return Liste von Rechnungsköpren und null wenn keine existieren
     */
    List<Reko> liefereBestaetigteRechnungen();

    /**
     * Liefert alle gültige Rechungsköpfe.
     *
     * @return Liste von Rekos und null wenn keine existieren
     */
    List<Reko> liefereRekos();

    /**
     * Liefert alle Kunden.
     *
     * @return Liste von Kunden und null wenn keine existieren
     */
    List<Kunde> liefereKunden();

    /**
     * Liefert alle Bezahlmethoden.
     *
     * @return Liste von Bezahlmethoden und null wenn keine existieren
     */
    List<Bezahlmethode> liefereBezahlmethoden();

    /**
     * Updated einen Artikeltyp.
     *
     * @param artikeltyp
     * @return true bei Erfolg, false bei Fehler
     */
    boolean updateArtikeltyp(Artikeltyp artikeltyp);

    /**
     * Updated eine Schule.
     *
     * @param schule
     * @return true bei Erfolg, false bei Fehler
     */
    boolean updateSchule(Schule schule);

    /**
     * Updated eine Klasse.
     *
     * @param klasse
     * @return true bei Erfolg, false bei Fehler
     */
    boolean updateKlasse(Klasse klasse);

    /**
     * Updated einen Rechnungskopf.
     *
     * @param reko
     * @return true bei Erfolg, false bei Fehler
     */
    boolean updateReko(Reko reko);

    /**
     * Uupdated einen Benutzer.
     *
     * @param benutzer
     * @return true bei Erfolg, false bei Fehler
     */
    boolean updateBenutzer(Benutzer benutzer);

    /**
     * Schule wird mit den zugehörigen Klassen und Artikel auf ungültig gesetzt.
     *
     * @param schule
     * @return gelöschte Schule bei Erfolg, null bei Fehler
     */
    Schule deleteSchule(Schule schule);

    /**
     * Klasse wird mit den zugehörigen Artikeln auf ungültig gesetzt.
     *
     * @param klasse
     * @return gelöschte Klasse bei Erfolg, null bei Fehler
     */
    Klasse deleteKlasse(Klasse klasse);
    
    /**
     * Artikel wird auf ungültig gesetzt.
     *
     * @param artikel
     * @return gelöschter Artikel bei Erfolg, null bei Fehler
     */
    Artikel deleteArtikel(Artikel artikel);

    /**
     * Artikeltyp wird auf ungültig gesetzt.
     *
     * @param artikeltyp
     * @return gelöschter Artikeltyp bei Erfolg, null bei Fehler
     */
    Artikeltyp deleteArtikeltyp(Artikeltyp artikeltyp);

    /**
     * Der Rechnungskopf wird mit den zugehörigen Rechnungszeilen gelöscht.
     * @param reko
     * @return gelöschte Reko bei Erfolg, null bei Fehler
     */
    Reko deleteReko(Reko reko);
    
    /**
     * Liefert bei erfolgreicher Anmeldung den Benutzer zurück.
     *
     * @param email
     * @return Benutzer bei Erfolg, null bei Fehler
     */
    Benutzer loginBenutzer(String email);

    /**
     * Liefert bei erfolgreicher Anmeldung die Klasse zurück.
     *
     * @param code Zugangscode
     * @return Klasse bei Erfolg, null bei Fehler
     */
    Klasse login(String code);

}
