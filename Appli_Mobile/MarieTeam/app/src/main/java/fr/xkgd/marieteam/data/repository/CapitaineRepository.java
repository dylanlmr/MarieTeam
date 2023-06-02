package fr.xkgd.marieteam.data.repository;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import fr.xkgd.marieteam.api.ApiResponse;
import fr.xkgd.marieteam.api.RetrofitClient;
import fr.xkgd.marieteam.api.service.CapitaineService;
import fr.xkgd.marieteam.data.local.dao.CapitaineDao;
import fr.xkgd.marieteam.data.local.database.AppDatabase;
import fr.xkgd.marieteam.model.Capitaine;
import fr.xkgd.marieteam.model.LoginRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CapitaineRepository {

    private static final String TAG = "CapitaineRepository";
    private static CapitaineRepository INSTANCE;
    private CapitaineService mCapitaineService;
    private CapitaineDao mCapitaineDao;

    private CapitaineRepository(Application application) {
        mCapitaineService = RetrofitClient.getInstance()
                .create(CapitaineService.class);

        AppDatabase db = AppDatabase.getDatabase(application);
        mCapitaineDao = db.capitaineDao();
    }

    /**
     * Singleton permettant d'obtenir une instance de CapitaineRepository
     * @param application Application
     * @return instance de CapitaineRepository
     */
    public static synchronized CapitaineRepository getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new CapitaineRepository(application);
        }
        return INSTANCE;
    }

    // Local Database methods

    /**
     * Récupère un capitaine en fonction de son id, dans la base de données locale
     * @param id id du capitaine
     * @return Capitaine
     */
    public Capitaine getCapitaine(int id) {
        return mCapitaineDao.getCapitaine(id);
    }

    /**
     * Récupère un capitaine enregistré en fonction de son mail, dans la base de données locale
     * @param mail mail du capitaine
     * @return Capitaine enregistré
     */
    public Capitaine getRegisteredCapitaine(String mail) {
        return mCapitaineDao.getRegisteredCapitaine(mail);
    }

    /**
     * Insère un capitaine dans la base de données locale
     * @param capitaine Capitaine à insérer
     */
    public void insert(Capitaine capitaine) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                mCapitaineDao.insert(capitaine));
    }

    /**
     * Met à jour un capitaine dans la base de données locale
     * @param capitaine Capitaine à mettre à jour
     */
    public void update(Capitaine capitaine) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                mCapitaineDao.update(capitaine));
    }

    // Distant Database methods

    /**
     * Récupère un capitaine enregistré dans la base de données distante, en fonction de son mail
     * @param mail mail du capitaine
     * @param password mot de passe du capitaine
     * @return Capitaine enregistré
     */
    public CompletableFuture<Capitaine> login(String mail, String password) {
        CompletableFuture<Capitaine> future = new CompletableFuture<>();
        Call<ApiResponse<Capitaine>> call = mCapitaineService.login(new LoginRequest(mail, password));
        call.enqueue(new Callback<ApiResponse<Capitaine>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Capitaine>> call, @NonNull Response<ApiResponse<Capitaine>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    Capitaine capitaine = response.body().getData();
                    Log.d(TAG, "OOOOOOOOO : " + capitaine.getMail());
                    future.complete(capitaine);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Capitaine>> call, @NonNull Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }
}
