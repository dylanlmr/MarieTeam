package fr.xkgd.marieteam.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "capitaine")
public class Capitaine implements Serializable {

    @PrimaryKey
    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String password;

    public Capitaine() {}

    /**
     * Méthode permettant de vérifier si deux capitaines sont identiques de par leurs attributs.
     * @param obj Capitaine à comparer
     * @return true si les deux capitaines sont identiques, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Capitaine))
            return false;

        Capitaine other = (Capitaine) obj;
        return this.id == other.id &&
                Objects.equals(this.nom, other.nom) &&
                Objects.equals(this.prenom, other.prenom) &&
                Objects.equals(this.mail, other.mail) &&
                Objects.equals(this.password, other.password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
