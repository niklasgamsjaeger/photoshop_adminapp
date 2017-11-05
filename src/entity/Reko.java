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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 20100226
 */
@Entity
@Table(name = "reko")
@SequenceGenerator(name = "seq_reko", sequenceName = "sequence_reko_id", initialValue = 100000, allocationSize = 1)
public class Reko implements Serializable {

    @Id
    @Column(name = "reko_id")
    @GeneratedValue(generator = "seq_reko")
    private int id;

    @Column(name = "reko_rechnungsdatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rechnungsdatum = new Date();

    @Column(name = "reko_bezahldatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bezahldatum;

    @Column(name = "reko_paypalKey", length = 100)
    private String paypalKey;
    
    @Column(name = "reko_url")
    private String url;
    
    @Column(name = "reko_urlAblaufdatum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date urlAblaufdatum;

    @Column(name = "reko_gueltig_von")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_von = new Date();

    @Column(name = "reko_gueltig_bis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_bis = null;

    @ManyToOne(optional = false,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "reko_kunde")
    private Kunde kunde;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "reko_bezahlmethode")
    private Bezahlmethode bezahlmethode;

    @OneToMany(mappedBy = "reko",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private List<Rezl> rezls = new ArrayList<>();
    
    @ManyToOne(optional = false,
            fetch = FetchType.EAGER)
    private Schule schule;

    public Reko() {
    }

    public Reko(Kunde kunde, Bezahlmethode bezahlmethode, Date bezahldatum, Schule schule) {
        this.kunde = kunde;
        this.bezahlmethode = bezahlmethode;
        this.bezahldatum = bezahldatum;
        this.schule = schule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRechnungsdatum() {
        return rechnungsdatum;
    }

    public void setRechnungsdatum(Date rechnungsdatum) {
        this.rechnungsdatum = rechnungsdatum;
    }

    public Date getBezahldatum() {
        return bezahldatum;
    }

    public void setBezahldatum(Date bezahldatum) {
        this.bezahldatum = bezahldatum;
    }

    public String getPaypalKey() {
        return paypalKey;
    }

    public void setPaypalKey(String paypalKey) {
        this.paypalKey = paypalKey;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUrlAblaufdatum() {
        return urlAblaufdatum;
    }

    public void setUrlAblaufdatum(Date urlAblaufdatum) {
        this.urlAblaufdatum = urlAblaufdatum;
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

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Bezahlmethode getBezahlmethode() {
        return bezahlmethode;
    }

    public void setBezahlmethode(Bezahlmethode bezahlmethode) {
        this.bezahlmethode = bezahlmethode;
    }

    public List<Rezl> getRezls() {
        return rezls;
    }

    public void setRezls(List<Rezl> rezls) {
        this.rezls = rezls;
    }

    public Schule getSchule() {
        return schule;
    }

    public void setSchule(Schule schule) {
        this.schule = schule;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.id;
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
        final Reko other = (Reko) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
