/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.fotoherfert4school_admin.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 20100226
 */
@Entity
@Table(name = "rezl")
public class Rezl implements Serializable {

    @Id
    @Column(name = "rezl_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "rezl_lfd")
    private int lfd;

    @Column(name = "rezl_vk_preis", scale = 2, precision = 10, updatable = false)
    private BigDecimal vk_preis;

    @Column(name = "rezl_gueltig_von")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_von = new Date();

    @Column(name = "rezl_gueltig_bis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_bis = null;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rezl_reko")
    private Reko reko;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rezl_artikel")
    private Artikel artikel;

    public Rezl() {
    }

    public Rezl(int lfd, Reko reko, Artikel artikel) {
        this.lfd = lfd;
        this.vk_preis = artikel.getArtikeltyp().getPreis();
        this.reko = reko;
        this.artikel = artikel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLfd() {
        return lfd;
    }

    public void setLfd(int lfd) {
        this.lfd = lfd;
    }

    public BigDecimal getVk_preis() {
        return vk_preis;
    }

    public void setVk_preis(BigDecimal vk_preis) {
        this.vk_preis = vk_preis;
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

    public Reko getReko() {
        return reko;
    }

    public void setReko(Reko reko) {
        this.reko = reko;
    }

    public Artikel getArtikel() {
        return artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
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
        final Rezl other = (Rezl) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
