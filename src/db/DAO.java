///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package at.htlstp.fotoherfert4school_admin.db;
//
//import at.htlstp.fotoherfert4school_admin.entity.Artikel;
//import at.htlstp.fotoherfert4school_admin.entity.Artikeltyp;
//import at.htlstp.fotoherfert4school_admin.entity.Benutzer;
//import at.htlstp.fotoherfert4school_admin.entity.Bezahlmethode;
//import at.htlstp.fotoherfert4school_admin.entity.Klasse;
//import at.htlstp.fotoherfert4school_admin.entity.Kunde;
//import at.htlstp.fotoherfert4school_admin.entity.Reko;
//import at.htlstp.fotoherfert4school_admin.entity.Rezl;
//import at.htlstp.fotoherfert4school_admin.entity.Schule;
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//import javax.ejb.Stateless;
//import javax.ejb.TransactionAttribute;
//import javax.ejb.TransactionAttributeType;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import javax.persistence.TypedQuery;
//
///**
// *
// * @author 20100226
// */
//@Stateless
//public class DAO implements IDAO, Serializable {
//
//    @PersistenceContext(unitName = "fotoherfert4schoolPU")
//    private EntityManager em;
//
//    // Methode: liefereArtikeltypen
//    // Liefert alle Artikeltypen
//    private final String q1 = "FROM Artikeltyp arttyp WHERE arttyp.gueltig_bis "
//            + "IS null";
//
//    // Methode: liefereArtikeltyp
//    // Liefert alle Artikeltypen mit der übergebenen Abkürzung
//    private final String q2 = "FROM Artikeltyp arttyp WHERE arttyp.abkz = :abkz "
//            + "AND arttyp.gueltig_bis IS null";
//
//    // Methode: liefereArtikel
//    // Liefert alle Artikekl
//    private final String q3 = "FROM Artikel a WHERE a.gueltig_bis IS null";
//
//    // Methode: liefereSchulen
//    // liefert alle Schulen
//    private final String q4 = "FROM Schule s WHERE s.gueltig_bis IS null";
//    // liefert alle Klassen einer Schule
//    private final String q5 = "FROM Klasse k WHERE k.schule = :schule "
//            + "AND k.gueltig_bis IS null";
//    // liefert alle Artikel einer Klasse
//    private final String q6 = "FROM Artikel a WHERE a.klasse = :klasse "
//            + "AND a.gueltig_bis IS null";
//    // liefert den Artikeltyp eines Artikels
//    private final String q7 = "FROM Artikeltyp arttyp WHERE arttyp.artikel = "
//            + ":artikel AND arttyp.gueltig_bis IS null";
//
//    // Methode: liefereOffeneRechnungen
//    // Liefert alle offenen Rechnungen
//    private final String q8 = "FROM Reko rek WHERE rek.bezahldatum IS null "
//            + "AND rek.gueltig_bis IS null";
//
//    // Methode: deleteSchule
//    // Liefert die Klassen, der zu löschenden Schule
//    private final String q9 = "FROM Klasse k WHERE k.schule = :del_schule";
//    // Liefert die Artikel, der zu löschenden Klasse
//    private final String q10 = "FROM Artikel a WHERE a.klasse = :del_klasse";
//
//    // Methode: deleteKlasse
//    // Liefert die Artikel, der zu löschenden Klasse
//    private final String q11 = "FROM Artikel a WHERE a.klasse = :del_klasse";
//
//    // Methode: login (Benutzer)
//    // Liefert den Benutzer, bei dem die email und das passwort übereinstimmen
//    private final String q12 = "FROM Benutzer ben WHERE ben.email = :email "
//            + "AND ben.gueltig_bis IS null";
//
//    // Methode: login (Schueler)
//    // Liefert die Klasse dem der Zugangscode übereinstimmt
//    private final String q13 = "FROM Klasse k WHERE k.code = :code "
//            + "AND k.gueltig_bis IS null";
//    // Liefert die Artikel der Klasse
//    private final String q14 = "FROM Artikel a WHERE a.klasse = :klasse "
//            + "AND a.gueltig_bis IS null";
//    // Liefert den Artikeltyp des Artikels
//    private final String q15 = "FROM Artikeltyp arttyp WHERE arttyp.artikel = "
//            + ":artikel AND arttyp.gueltig_bis IS null";
//
//    // Methode: liefereKunden
//    // Liefert alle Kunden
//    private final String q16 = "FROM Kunde kunde WHERE kunde.gueltig_bis IS null";
//
//    // Methode: liefereBezahlmethoden
//    // Liefert alle Bezahlmethoden
//    private final String q17 = "FROM Bezahlmethode bm WHERE bm.gueltig_bis IS null";
//
//    // Methode: liefereRekos
//    // Liefert alle Rekos
//    private final String q18 = "FROM Reko rek WHERE rek.gueltig_bis IS null";
//
//    private final String q19 = "FROM Reko rek WHERE rek.bezahldatum IS NOT null "
//            + "AND rek.gueltig_bis IS null";
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean insertKunde(Kunde kunde) {
//        try {
//            em.persist(kunde);
//            return true;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean insertReko(Reko reko) {
//        try {
//            em.persist(reko);
//            return true;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean insertBezahlmethode(Bezahlmethode bezahlmethode) {
//        try {
//            em.persist(bezahlmethode);
//            return true;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean insertRezl(Rezl rezl) {
//        try {
//            em.persist(rezl);
//            return true;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean insertArtikel(Artikel artikel) {
//        try {
//            em.persist(artikel);
//            return true;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean insertKlasse(Klasse klasse) {
//        try {
//            em.persist(klasse);
//            return true;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean insertSchule(Schule schule) {
//        try {
//            em.persist(schule);
//            return true;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean insertArtikeltyp(Artikeltyp artikeltyp) {
//        try {
//            em.persist(artikeltyp);
//            return true;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean insertBenutzer(Benutzer benutzer) {
//        try {
//            em.persist(benutzer);
//            return true;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    public List<Artikeltyp> liefereArtikeltypen() {
//        try {
//            Query qu = em.createQuery(q1);
//            return qu.getResultList();
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public Artikeltyp liefereArtikeltyp(String abkz) {
//        if (abkz.trim().isEmpty()) {
//            return null;
//        }
//
//        try {
//            TypedQuery<Artikeltyp> qu = em.createQuery(q2, Artikeltyp.class);
//            qu.setParameter("abkz", abkz);
//            return qu.getSingleResult();
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//
//    }
//
//    @Override
//    public List<Artikel> liefereArtikel() {
//        try {
//            Query qu = em.createQuery(q3);
//            return qu.getResultList();
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public List<Schule> liefereSchulen() {
//        try {
//            Query qu1 = em.createQuery(q4);
//            List<Schule> schulen = qu1.getResultList();
//
//            for (Schule s : schulen) {
//                Query qu2 = em.createQuery(q5);
//                qu2.setParameter("schule", s);
//                List<Klasse> klassen = qu2.getResultList();
//
//                for (Klasse k : klassen) {
//                    Query qu3 = em.createQuery(q6);
//                    qu3.setParameter("klasse", k);
//                    List<Artikel> artikel = qu3.getResultList();
//
//                    for (Artikel a : artikel) {
//                        Query qu4 = em.createQuery(q7);
//                        qu4.setParameter("artikel", a);
//                        Artikeltyp artikeltyp = (Artikeltyp) qu4.getSingleResult();
//
//                        a.setArtikeltyp(artikeltyp);
//                    }
//                    k.setArtikel(artikel);
//                }
//                s.setKlassen(klassen);
//            }
//
//            return schulen;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public List<Reko> liefereOffeneRechnungen() {
//        try {
//            Query qu = em.createQuery(q8);
//            return qu.getResultList();
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public List<Reko> liefereBestaetigteRechnungen() {
//        try {
//            Query qu = em.createQuery(q19);
//            return qu.getResultList();
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public List<Reko> liefereRekos() {
//        try {
//            Query qu = em.createQuery(q18);
//            return qu.getResultList();
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public List<Kunde> liefereKunden() {
//        try {
//            Query qu = em.createQuery(q16);
//            return qu.getResultList();
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public List<Bezahlmethode> liefereBezahlmethoden() {
//        try {
//            Query qu = em.createQuery(q17);
//            return qu.getResultList();
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean updateArtikeltyp(Artikeltyp artikeltyp) {
//        try {
//            if (em.find(Artikeltyp.class, artikeltyp.getId()) != null) {
//                em.merge(artikeltyp);
//                return true;
//            }
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean updateSchule(Schule schule) {
//        try {
//            if (em.find(Schule.class, schule.getId()) != null) {
//                em.merge(schule);
//                return true;
//            }
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean updateKlasse(Klasse klasse) {
//        try {
//            if (em.find(Klasse.class, klasse.getId()) != null) {
//                em.merge(klasse);
//                return true;
//            }
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean updateReko(Reko reko) {
//        try {
//            if (em.find(Reko.class, reko.getId()) != null) {
//                em.merge(reko);
//                return true;
//            }
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public boolean updateBenutzer(Benutzer benutzer) {
//        try {
//            if (em.find(Benutzer.class, benutzer.getId()) != null) {
//                em.merge(benutzer);
//                return true;
//            }
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return false;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public Schule deleteSchule(Schule schule) {
//        try {
//            // Alle Klassen der zu löschenden Schule holen
//            Schule del_schule = em.find(Schule.class, schule.getId());
//            Query qu1 = em.createQuery(q9);
//            qu1.setParameter("del_schule", del_schule);
//
//            List<Klasse> del_klassen = qu1.getResultList();
//
//            // Klassen der zu löschenden Schule werden auf ungültig gesetzt
//            if (!del_klassen.isEmpty()) {
//                for (Klasse k : del_klassen) {
//                    Query qu2 = em.createQuery(q10);
//                    qu2.setParameter("del_klasse", k);
//
//                    List<Artikel> del_artikel = qu2.getResultList();
//
//                    // Artikel der zu löschenden Schule werden auf ungültig gesetzt
//                    for (Artikel a : del_artikel) {
//                        a.setGueltig_bis(new Date());
//                        em.merge(a);
//                    }
//                    k.setGueltig_bis(new Date());
//                    em.merge(k);
//                }
//            }
//
//            // Schule auf ungültig setzen
//            del_schule.setGueltig_bis(new Date());
//            em.merge(del_schule);
//            return schule;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public Klasse deleteKlasse(Klasse klasse) {
//        try {
//            // Alle Artikel der zu löschenden Klasse holen
//            Klasse del_klasse = em.find(Klasse.class, klasse.getId());
//            Query qu = em.createQuery(q11);
//            qu.setParameter("del_klasse", del_klasse);
//
//            List<Artikel> del_artikel = qu.getResultList();
//
//            // Artikel der zu löschenden Klasse werden auf ungültig gesetzt
//            if (!del_artikel.isEmpty()) {
//                for (Artikel a : del_artikel) {
//                    a.setGueltig_bis(new Date());
//                    em.merge(a);
//                }
//                // Klasse auf ungültig setzen
//                del_klasse.setGueltig_bis(new Date());
//                em.merge(del_klasse);
//            }
//            return klasse;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public Artikel deleteArtikel(Artikel artikel) {
//        try {
//            artikel.setGueltig_bis(new Date());
//            em.merge(artikel);
//            return artikel;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    public Artikeltyp deleteArtikeltyp(Artikeltyp artikeltyp) {
//        try {
//            artikeltyp.setGueltig_bis(new Date());
//            em.merge(artikeltyp);
//            return artikeltyp;
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public Benutzer loginBenutzer(String email) {
//        if (email.isEmpty()) {
//            return null;
//        }
//
//        try {
//            TypedQuery<Benutzer> qu = em.createQuery(q12, Benutzer.class);
//            qu.setParameter("email", email);
//            return qu.getSingleResult();
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public Klasse login(String code) {
//        if (code.trim().isEmpty()) {
//            return null;
//        }
//
//        Klasse klasse = null;
//
//        try {
//            TypedQuery<Klasse> qu1 = em.createQuery(q13, Klasse.class);
//            qu1.setParameter("code", code);
//            klasse = qu1.getSingleResult();
//        } catch (Exception ex) {
//            klasse = null;
//            System.err.println(ex.getMessage());
//        }
//
//        try {
//            if (klasse != null) {
//                Query qu2 = em.createQuery(q14);
//                qu2.setParameter("klasse", klasse);
//                List<Artikel> artikel = qu2.getResultList();
//
//                for (Artikel a : artikel) {
//                    TypedQuery<Artikeltyp> qu3 = em.createQuery(q15, Artikeltyp.class);
//                    qu3.setParameter("artikel", a);
//                    Artikeltyp artikeltyp;
//
//                    try {
//                        artikeltyp = qu3.getSingleResult();
//                    } catch (Exception ex) {
//                        artikeltyp = null;
//                        System.err.println(ex.getMessage());
//                    }
//
//                    if (artikeltyp != null) {
//                        a.setArtikeltyp(artikeltyp);
//                    }
//                }
//                klasse.setArtikel(artikel);
//                return klasse;
//            }
//        } catch (Exception ex) {
//            System.err.println(ex.getMessage());
//        }
//        return null;
//    }
//}
