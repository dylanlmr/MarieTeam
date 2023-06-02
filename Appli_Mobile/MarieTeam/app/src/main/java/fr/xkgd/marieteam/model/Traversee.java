package fr.xkgd.marieteam.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import fr.xkgd.marieteam.adapter.serialization.BooleanAdapter;
import fr.xkgd.marieteam.adapter.serialization.LocalDateAdapter;
import fr.xkgd.marieteam.adapter.serialization.LocalDateTimeAdapter;
import fr.xkgd.marieteam.adapter.serialization.LocalTimeAdapter;

@Entity(tableName = "traversee", foreignKeys = @ForeignKey(entity = Capitaine.class,
        parentColumns = "id", childColumns = "id_capitaine"),
        indices = @Index(value = "id_capitaine"))
public class Traversee implements Serializable {

    @PrimaryKey
    private int id;
    @JsonAdapter(LocalDateAdapter.class)
    @SerializedName("date")
    @ColumnInfo(name = "date_depart")
    private LocalDate dateDepart;
    @JsonAdapter(LocalTimeAdapter.class)
    @SerializedName("heure_depart")
    @ColumnInfo(name = "heure_depart")
    private LocalTime heureDepart;
    @JsonAdapter(LocalDateTimeAdapter.class)
    @SerializedName("date_heure_arrivee")
    @ColumnInfo(name = "date_heure_arrivee")
    private LocalDateTime dateHeureArrivee;
    @JsonAdapter(BooleanAdapter.class)
    private boolean terminee;
    @SerializedName("etat_mer")
    @ColumnInfo(name = "etat_mer")
    private String etatMer;
    private String commentaire;
    @SerializedName("id_capitaine")
    @ColumnInfo(name = "id_capitaine")
    private int idCapitaine;
    private int status;

    public Traversee() {}

    public String getStrDepart() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime dateHeureDepart = LocalDateTime.of(dateDepart, heureDepart);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm");
            return "Le " + dateHeureDepart.format(formatter);
        }
        return null;
    }

    public String getStrArrivee() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm");
            return "Au " + dateHeureArrivee.format(formatter);
        }
        return null;
    }

    public String getRetard() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(dateHeureArrivee, now);
            long minutes = duration.toMinutes();
            if (minutes > 60) {
                long hours = duration.toHours();
                long remainingMinutes = minutes - (hours * 60);
                if (hours < 24) {
                    return (int) hours + "h " + remainingMinutes + "min";
                } else {
                    long days = duration.toDays();
                    long remainingHours = hours - (days * 24);
                    return (int) days + "j " + remainingHours + "h " + remainingMinutes + "min";
                }
            } else {
                return "0 min";
            }
        }
        return null;
    }

    /**
     * Méthode permettant de vérifier si deux traversées sont identiques de par leurs attributs.
     * @param obj Traversée à comparer
     * @return true si les deux traversées sont identiques, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Traversee))
            return false;

        Traversee other = (Traversee) obj;
        return this.id == other.id &&
                Objects.equals(this.dateDepart, other.dateDepart) &&
                Objects.equals(this.heureDepart, other.heureDepart) &&
                Objects.equals(this.dateHeureArrivee, other.dateHeureArrivee) &&
                Objects.equals(this.terminee, other.terminee) &&
                Objects.equals(this.etatMer, other.etatMer) &&
                Objects.equals(this.commentaire, other.commentaire) &&
                Objects.equals(this.idCapitaine, other.idCapitaine) &&
                Objects.equals(this.status, other.status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDate dateDepart) {
        this.dateDepart = dateDepart;
    }

    public LocalTime getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(LocalTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public LocalDateTime getDateHeureArrivee() {
        return dateHeureArrivee;
    }

    public void setDateHeureArrivee(LocalDateTime dateHeureArrivee) {
        this.dateHeureArrivee = dateHeureArrivee;
    }

    public boolean isTerminee() {
        return terminee;
    }

    public void setTerminee(boolean terminee) {
        this.terminee = terminee;
    }

    public String getEtatMer() {
        return etatMer;
    }

    public void setEtatMer(String etatMer) {
        this.etatMer = etatMer;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getIdCapitaine() {
        return idCapitaine;
    }

    public void setIdCapitaine(int idCapitaine) {
        this.idCapitaine = idCapitaine;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
