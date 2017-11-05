/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.fotoherfert4school_admin.others;

import at.htlstp.fotoherfert4school_admin.entity.Artikel;
import at.htlstp.fotoherfert4school_admin.entity.Artikeltyp;
import at.htlstp.fotoherfert4school_admin.entity.Benutzer;
import at.htlstp.fotoherfert4school_admin.entity.Bezahlmethode;
import at.htlstp.fotoherfert4school_admin.entity.Klasse;
import at.htlstp.fotoherfert4school_admin.entity.Kunde;
import at.htlstp.fotoherfert4school_admin.entity.Reko;
import at.htlstp.fotoherfert4school_admin.entity.Rezl;
import at.htlstp.fotoherfert4school_admin.entity.Schule;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author 20110386
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        try {
            // loads configuration and mappings
            Configuration configuration = new Configuration().configure();
            serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            configuration.addAnnotatedClass(Artikel.class);
            configuration.addAnnotatedClass(Artikeltyp.class);
            configuration.addAnnotatedClass(Benutzer.class);
            configuration.addAnnotatedClass(Bezahlmethode.class);
            configuration.addAnnotatedClass(Klasse.class);
            configuration.addAnnotatedClass(Kunde.class);
            configuration.addAnnotatedClass(Reko.class);
            configuration.addAnnotatedClass(Rezl.class);
            configuration.addAnnotatedClass(Schule.class);
            // builds a session factory from the service registry
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void close() {
        if (serviceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }
}
