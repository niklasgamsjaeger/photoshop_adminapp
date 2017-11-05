/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.fotoherfert4school_admin.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 20100226
 */
@Entity
@Table(name = "artikel")
public class Artikel implements Serializable {

    @Id
    @Column(name = "artikel_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "artikel_url", nullable = false)
    private String url;

    @Column(name = "artikel_hashUrl", length = 255, nullable = false)
    private String hashUrl;
    
    @Column(name = "artikel_knr")
    private int knr;

    @Column(name = "artikel_master")
    private boolean master;    
    
    @Column(name = "artikel_gueltig_von")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_von = new Date();

    @Column(name = "artikel_gueltig_bis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_bis = null;
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "artikel_artikeltyp")
    private Artikeltyp artikeltyp;

    @ManyToOne(optional = false)
    @JoinColumn(name = "artikel_klasse")
    private Klasse klasse;
    
    @OneToMany(mappedBy = "artikel")
    private List<Rezl> rezls = new ArrayList<>();

    public Artikel() {
    }

    public Artikel(String url, int knr, boolean master, Artikeltyp artikeltyp, Klasse klasse) {
        this.url = url;
        this.knr = knr;
        this.master = master;
        this.artikeltyp = artikeltyp;
        this.klasse = klasse;
    }

    public Artikel(String url, String hashUrl, int knr, boolean master, Artikeltyp artikeltyp, Klasse klasse) {
        this.url = url;
        this.hashUrl = hashUrl;
        this.knr = knr;
        this.master = master;
        this.artikeltyp = artikeltyp;
        this.klasse = klasse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHashUrl() {
        return hashUrl;
    }

    public void setHashUrl(String hashUrl) {
        this.hashUrl = hashUrl;
    }

    public int getKnr() {
        return knr;
    }

    public void setKnr(int knr) {
        this.knr = knr;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public Date getGueltig_von() {
        return gueltig_von;
    }

    public void setGueltig_von(Date gueltig_von) {
        this.gueltig_von = gueltig_von;
    }

    public Date getGueltig_bis() {
        return gueltig_bis;
    }

    public void setGueltig_bis(Date gueltig_bis) {
        this.gueltig_bis = gueltig_bis;
    }

    public Artikeltyp getArtikeltyp() {
        return artikeltyp;
    }

    public void setArtikeltyp(Artikeltyp artikeltyp) {
        this.artikeltyp = artikeltyp;
    }

    public Klasse getKlasse() {
        return klasse;
    }

    public void setKlasse(Klasse klasse) {
        this.klasse = klasse;
    }

    public List<Rezl> getRezls() {
        return rezls;
    }

    public void setRezls(List<Rezl> rezls) {
        this.rezls = rezls;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Artikel other = (Artikel) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
