package fr.xkgd.marieteam.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import fr.xkgd.marieteam.model.Capitaine;

@Dao
public interface CapitaineDao {

    /**
     * Retourne le capitaine en fonction de son id
     * @param id l'id du capitaine
     * @return le capitaine
     */
    @Query("SELECT * FROM capitaine WHERE id = :id")
    Capitaine getCapitaine(int id);

    /**
     * Retourne le capitaine en fonction de son mail
     * @param mail le mail du capitaine
     * @return le capitaine
     */
    @Query("SELECT * FROM capitaine WHERE mail = :mail")
    Capitaine getRegisteredCapitaine(String mail);

    /**
     * Insert un capitaine
     * @param capitaine le capitaine à insérer
     */
    @Insert
    void insert(Capitaine capitaine);

    /**
     * Update un capitaine
     * @param capitaine le capitaine à update
     */
    @Update
    void update(Capitaine capitaine);
}
