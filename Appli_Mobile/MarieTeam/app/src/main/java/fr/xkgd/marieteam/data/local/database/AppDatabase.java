package fr.xkgd.marieteam.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.xkgd.marieteam.data.local.dao.CapitaineDao;
import fr.xkgd.marieteam.data.local.dao.TraverseeDao;
import fr.xkgd.marieteam.model.Capitaine;
import fr.xkgd.marieteam.model.Traversee;

@Database(entities = {Traversee.class, Capitaine.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract CapitaineDao capitaineDao();
    public abstract TraverseeDao traverseeDao();
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile AppDatabase INSTANCE;

    /**
     * Singleton de la base de données
     * @param context contexte de l'application
     * @return instance de la base de données
     */
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "marieteam.db")
                            .addTypeConverter(new Converters())
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
