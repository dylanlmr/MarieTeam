package fr.xkgd.marieteam.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.xkgd.marieteam.model.Traversee;

@Dao
public interface TraverseeDao {

    /**
     * Récupère toutes les traversées d'un capitaine
     * @param id id du capitaine
     * @return liste des traversées
     */
    @Query("SELECT * FROM traversee WHERE id_capitaine = :id")
    List<Traversee> getCapitaineTraversees(int id);

    /**
     * Insert une traversée
     * @param traversee traversée à insérer
     */
    @Insert
    void insert(Traversee traversee);

    /**
     * Insert une liste de traversées
     * @param traversees liste de traversées à insérer
     */
    @Insert
    void insertAll(List<Traversee> traversees);

    /**
     * Met à jour une traversée
     * @param traversee traversée à mettre à jour
     */
    @Update
    void update(Traversee traversee);

    /**
     * Met à jour une liste de traversées
     * @param traversees liste de traversées à mettre à jour
     */
    @Update
    void updateAll(List<Traversee> traversees);
}
