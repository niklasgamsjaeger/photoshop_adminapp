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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 20100226
 */
@Entity
@Table(name = "schule")
public class Schule implements Serializable {

    @Id
    @Column(name = "schule_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "schule_name", length = 50, nullable = false)
    private String name;

    @Column(name = "schule_adresse", length = 300)
    private String adresse;

    @Column(name = "schule_ort", length = 50)
    private String ort;

    @Column(name = "schule_plz", length = 10)
    private String plz;

    @Column(name = "schule_uploaddatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploaddatum = null;

    @Column(name = "schule_downloadfrist", updatable = false)
    private int downloadfrist;

    @Column(name = "schule_anzeigefrist", updatable = false)
    @Temporal(TemporalType.DATE)
    private Date anzeigefrist;

    @Column(name = "schule_grossansicht")
    private boolean grossansicht;

    @Column(name = "schule_partnerURL", length = 255)
    private String partnerURL;
    
    @Column(name = "schule_partnerAnzeige")
    private boolean partnerAnzeige;
    
    @Column(name = "schule_gueltig_von")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_von = new Date();

    @Column(name = "schule_gueltig_bis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_bis = null;

    @OneToMany(mappedBy = "schule",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private List<Klasse> klassen = new ArrayList<>();

    public Schule() {
    }

    public Schule(String name, String adresse, String ort, String plz, int downloadfrist, Date anzeigefrist) {
        this.name = name;
        this.adresse = adresse;
        this.ort = ort;
        this.plz = plz;
        this.downloadfrist = downloadfrist;
        this.anzeigefrist = anzeigefrist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public Date getUploaddatum() {
        return uploaddatum;
    }

    public void setUploaddatum(Date uploaddatum) {
        this.uploaddatum = uploaddatum;
    }

    public int getDownloadfrist() {
        return downloadfrist;
    }

    public void setDownloadfrist(int downloadfrist) {
        this.downloadfrist = downloadfrist;
    }

    public Date getAnzeigefrist() {
        return anzeigefrist;
    }

    public void setAnzeigefrist(Date anzeigefrist) {
        this.anzeigefrist = anzeigefrist;
    }

    public boolean isGrossansicht() {
        return grossansicht;
    }

    public void setGrossansicht(boolean grossansicht) {
        this.grossansicht = grossansicht;
    }

    public String getPartnerURL() {
        return partnerURL;
    }

    public void setPartnerURL(String partnerURL) {
        this.partnerURL = partnerURL;
    }

    public boolean isPartnerAnzeige() {
        return partnerAnzeige;
    }

    public void setPartnerAnzeige(boolean partnerAnzeige) {
        this.partnerAnzeige = partnerAnzeige;
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

    public List<Klasse> getKlassen() {
        return klassen;
    }

    public void setKlassen(List<Klasse> klassen) {
        this.klassen = klassen;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
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
        final Schule other = (Schule) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
