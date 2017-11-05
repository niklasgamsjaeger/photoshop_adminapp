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
import at.htlstp.fotoherfert4school_admin.others.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author 20110386
 */
public class HibernateDAO implements IDAO {

    @Override
    public boolean insertKunde(Kunde kunde) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean ret = false;
        try {
            tx = s.beginTransaction();
            s.save(kunde);
            tx.commit();
            ret = true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public boolean insertReko(Reko reko) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean ret = false;
        try {
            tx = s.beginTransaction();
            s.save(reko);
            tx.commit();
            ret = true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public boolean insertBezahlmethode(Bezahlmethode bezahlmethode) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean ret = false;
        try {
            tx = s.beginTransaction();
            s.save(bezahlmethode);
            tx.commit();
            ret = true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public boolean insertRezl(Rezl rezl) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean ret = false;
        try {
            tx = s.beginTransaction();
            s.save(rezl);
            tx.commit();
            ret = true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public boolean insertArtikel(Artikel artikel) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean ret = false;
        try {
            tx = s.beginTransaction();
            s.save(artikel);
            tx.commit();
            ret = true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public boolean insertKlasse(Klasse klasse) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean ret = false;
        try {
            tx = s.beginTransaction();
            s.save(klasse);
            tx.commit();
            ret = true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public boolean insertSchule(Schule schule) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean ret = false;
        try {
            tx = s.beginTransaction();
            s.save(schule);
            tx.commit();
            ret = true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public boolean insertArtikeltyp(Artikeltyp artikeltyp) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean ret = false;
        try {
            tx = s.beginTransaction();
            s.save(artikeltyp);
            tx.commit();
            ret = true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public boolean insertBenutzer(Benutzer benutzer) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        boolean ret = false;
        try {
            tx = s.beginTransaction();
            s.save(benutzer);
            tx.commit();
            ret = true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public List<Artikeltyp> liefereArtikeltypen() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Artikeltyp> ret = null;
        try {
            Query qu = s.createQuery("from Artikeltyp a where a.gueltig_bis is null");
            ret = qu.list();
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public Artikeltyp liefereArtikeltyp(String abkz) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Artikeltyp ret = null;
        try {
            Query qu = s.createQuery("from Artikeltyp a where a.gueltig_bis is null AND "
                    + "a.abkz = :abkz");
            qu.setString("abkz", abkz);
            ret = (Artikeltyp) qu.uniqueResult();
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public List<Artikel> liefereArtikel() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Artikel> ret = null;
        try {
            Query qu = s.createQuery("from Artikel a where a.gueltig_bis is null");
            ret = qu.list();
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public List<Schule> liefereSchulen() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Schule> ret = null;
        try {
            Query qu = s.createQuery("from Schule s where s.gueltig_bis is null");
            ret = qu.list();
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public List<Reko> liefereOffeneRechnungen() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Reko> ret = null;
        try {
            Query qu = s.createQuery("from Reko r where r.gueltig_bis is null AND "
                    + "r.bezahldatum is null");
            ret = qu.list();
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public List<Reko> liefereBestaetigteRechnungen() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Reko> ret = null;
        try {
            Query qu = s.createQuery("from Reko r where r.gueltig_bis is null AND "
                    + "r.urlAblaufdatum > current_date()");
            ret = qu.list();
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public List<Reko> liefereRekos() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Reko> ret = null;
        try {
            Query qu = s.createQuery("from Reko r where r.gueltig_bis is null");
            ret = qu.list();
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public List<Kunde> liefereKunden() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Kunde> ret = null;
        try {
            Query qu = s.createQuery("from Kunde k where k.gueltig_bis is null");
            ret = qu.list();
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public List<Bezahlmethode> liefereBezahlmethoden() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Bezahlmethode> ret = null;
        try {
            Query qu = s.createQuery("from Bezahlmethode bm where bm.gueltig_bis is null");
            ret = qu.list();
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        } finally {
            s.close();
        }
        return ret;
    }

    @Override
    public boolean updateArtikeltyp(Artikeltyp artikeltyp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(artikeltyp);
            tx.commit();
        } catch (HibernateException hex) {
            artikeltyp = null;
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(hex.getMessage());
        } finally {
            session.close();
        }
        return artikeltyp != null;
    }

    @Override
    public boolean updateSchule(Schule schule) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(schule);
            tx.commit();
        } catch (HibernateException hex) {
            schule = null;
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(hex.getMessage());
        } finally {
            session.close();
        }
        return schule != null;
    }

    @Override
    public boolean updateKlasse(Klasse klasse) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(klasse);
            tx.commit();
        } catch (HibernateException hex) {
            klasse = null;
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(hex.getMessage());
        } finally {
            session.close();
        }
        return klasse != null;
    }

    @Override
    public boolean updateReko(Reko reko) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(reko);
            tx.commit();
        } catch (HibernateException hex) {
            reko = null;
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(hex.getMessage());
        } finally {
            session.close();
        }
        return reko != null;
    }

    @Override
    public boolean updateBenutzer(Benutzer benutzer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(benutzer);
            tx.commit();
        } catch (HibernateException hex) {
            benutzer = null;
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(hex.getMessage());
        } finally {
            session.close();
        }
        return benutzer != null;
    }

    @Override
    public Schule deleteSchule(Schule schule) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            // Schule auf ungültig setzen
            schule.setGueltig_bis(new Date());
            session.update(schule);

            for (Klasse klasse : schule.getKlassen()) {
                // Klasse auf ungültig setzen
                klasse.setGueltig_bis(new Date());
                session.update(klasse);

                for (Artikel artikel : klasse.getArtikel()) {
                    // Artikel auf ungültig setzen
                    artikel.setGueltig_bis(new Date());
                    session.update(artikel);
                }
            }

            Query q = session.createQuery("from Reko reko where reko.schule = :schule and reko.gueltig_bis is null");
            q.setParameter("schule", schule);
            List<Reko> rekos = q.list();

            for (Reko reko : rekos) {
                // Reko auf ungültig setzen
                reko.setGueltig_bis(new Date());
                session.update(reko);

                for (Rezl rezl : reko.getRezls()) {
                    // Rezl auf ungültig setzen
                    if (rezl.getGueltig_bis() == null) {
                        rezl.setGueltig_bis(new Date());
                        session.update(rezl);
                    }
                }
            }
            tx.commit();
        } catch (HibernateException hex) {
            schule = null;
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(hex.getMessage());
        } finally {
            session.close();
        }
        return schule;
    }

    @Override
    public Klasse deleteKlasse(Klasse klasse) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // Klasse auf ungültig setzen
            klasse.setGueltig_bis(new Date());
            session.update(klasse);

            for (Artikel artikel : klasse.getArtikel()) {
                // Artikel auf ungültig setzen
                artikel.setGueltig_bis(new Date());
                session.update(artikel);   
            }
            
            Schule schule = klasse.getSchule();
                Query q = session.createQuery("from Reko reko where reko.schule = :schule and reko.gueltig_bis is null");
                q.setParameter("schule", schule);
                List<Reko> rekos = q.list();

                for (Reko reko : rekos) {
                    // Reko auf ungültig setzen
                    reko.setGueltig_bis(new Date());
                    session.update(reko);

                    for (Rezl rezl : reko.getRezls()) {
                        // Rezl auf ungültig setzen
                        if (rezl.getGueltig_bis() == null) {
                            rezl.setGueltig_bis(new Date());
                            session.update(rezl);
                        }
                    }
                }
            tx.commit();
        } catch (HibernateException hex) {
            klasse = null;
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(hex.getMessage());
        } finally {
            session.close();
        }
        return klasse;
    }

    @Override
    public Artikel deleteArtikel(Artikel artikel) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            // Artikel auf ungültig setzen
            artikel.setGueltig_bis(new Date());
            session.update(artikel);

            for (Rezl rezl : artikel.getRezls()) {
                // Rezl auf ungültig setzen
                if (rezl.getGueltig_bis() == null) {
                    rezl.setGueltig_bis(new Date());
                    session.update(rezl);
                }

                Reko reko = rezl.getReko();
                // Reko auf ungültig setzen
                if (reko.getGueltig_bis() == null) {
                    reko.setGueltig_bis(new Date());
                    session.update(reko);
                }
            }
            tx.commit();
        } catch (HibernateException hex) {
            artikel = null;
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(hex.getMessage());
        } finally {
            session.close();
        }
        return artikel;
    }

    @Override
    public Artikeltyp deleteArtikeltyp(Artikeltyp artikeltyp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        artikeltyp.setGueltig_bis(new Date());

        try {
            tx = session.beginTransaction();
            session.update(artikeltyp);
            tx.commit();
        } catch (HibernateException hex) {
            artikeltyp = null;
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(hex.getMessage());
        } finally {
            session.close();
        }
        return artikeltyp;
    }

    @Override
    public Reko deleteReko(Reko reko) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            // Reko auf ungültig setzen
            reko.setGueltig_bis(new Date());
            session.update(reko);

            for (Rezl rezl : reko.getRezls()) {
                // Rezl auf ungültig setzen
                if (rezl.getGueltig_bis() == null) {
                    rezl.setGueltig_bis(new Date());
                    session.update(rezl);
                }
            }
            tx.commit();
        } catch (HibernateException hex) {
            reko = null;
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(hex.getMessage());
        } finally {
            session.close();
        }
        return reko;
    }

    @Override
    public Benutzer loginBenutzer(String email
    ) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Benutzer ben = null;
        try {
            Query qu = session.createQuery("from Benutzer where email = :email");
            qu.setString("email", email);
            ben = (Benutzer) qu.uniqueResult();
        } catch (HibernateException hex) {
            System.err.println(hex.getMessage());
        } finally {
            session.close();
        }
        return ben;
    }

    @Override
    public Klasse login(String code
    ) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Klasse klasse = null;
        try {
            Query qu = session.createQuery("from Klasse where code = :code");
            qu.setString("code", code);
            klasse = (Klasse) qu.uniqueResult();
        } catch (HibernateException hex) {
            System.err.println(hex.getMessage());
        } finally {
            session.close();
        }
        return klasse;
    }

}
