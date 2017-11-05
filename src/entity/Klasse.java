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
@Table(name = "klasse")
public class Klasse implements Serializable {

    @Id
    @Column(name = "klasse_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "klasse_name", length = 10, nullable = false)
    private String name;

    @Column(name = "klasse_code", length = 50, nullable = false)
    private String code;

    @Column(name = "klasse_gueltig_von")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_von = new Date();

    @Column(name = "klasse_gueltig_bis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_bis = null;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "klasse_schule")
    private Schule schule;
    
    @OneToMany(mappedBy = "klasse", 
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private List<Artikel> artikel = new ArrayList<>();

    public Klasse() {
    }

    public Klasse(String name, String code, Schule schule) {
        this.name = name;
        this.code = code;
        this.schule = schule;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Schule getSchule() {
        return schule;
    }

    public void setSchule(Schule schule) {
        this.schule = schule;
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
        hash = 71 * hash + this.id;
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
        final Klasse other = (Klasse) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
