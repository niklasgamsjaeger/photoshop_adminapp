/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.fotoherfert4school_admin.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "artikeltyp")
public class Artikeltyp implements Serializable, Comparable {

    @Id
    @Column(name = "artikeltyp_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "artikeltyp_abkz", length = 10)
    private String abkz;
    
    @Column(name = "artikeltyp_bezeichnung", length = 30)
    private String bezeichnung;

    @Column(name = "artikeltyp_preis", scale = 2, precision = 10)
    private BigDecimal preis;

    @Column(name = "artikeltyp_gueltig_von")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_von = new Date();

    @Column(name = "artikeltyp_gueltig_bis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_bis = null;

    @OneToMany(mappedBy = "artikeltyp")
    public List<Artikel> artikel = new ArrayList<>();
    
    public Artikeltyp() {
    }

    public Artikeltyp(String abkz, String bezeichnung, BigDecimal preis) {
        this.abkz = abkz;
        this.bezeichnung = bezeichnung;
        this.preis = preis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbkz() {
        return abkz;
    }

    public void setAbkz(String abkz) {
        this.abkz = abkz;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public BigDecimal getPreis() {
        return preis;
    }

    public void setPreis(BigDecimal preis) {
        this.preis = preis;
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

    public List<Artikel> getArtikel() {
        return artikel;
    }

    public void setArtikel(List<Artikel> artikel) {
        this.artikel = artikel;
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
        final Artikeltyp other = (Artikeltyp) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        return this.getId() - ((Artikeltyp)o).id;
    }
}
