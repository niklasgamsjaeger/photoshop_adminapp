/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.fotoherfert4school_admin.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 20100226
 */
@Entity
@Table(name = "benutzer")
public class Benutzer implements Serializable {

    @Id
    @Column(name = "benutzer_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "benutzer_email", length = 255, unique = true)
    private String email;

    @Column(name = "benutzer_passwort", length = 255, nullable = false)
    private String passwort;

    @Column(name = "benutzer_salt", length = 255, nullable = false)
    private String salt;

    @Column(name = "benutzer_gueltig_von")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_von = new Date();

    @Column(name = "benutzer_gueltig_bis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gueltig_bis = null;

    public Benutzer() {
    }

    public Benutzer(String email, String passwort) {
        this.email = email;
        this.passwort = passwort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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
        final Benutzer other = (Benutzer) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
