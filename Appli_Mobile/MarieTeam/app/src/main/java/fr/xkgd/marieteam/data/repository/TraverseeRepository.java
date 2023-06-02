package fr.xkgd.marieteam.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.xkgd.marieteam.api.ApiResponse;
import fr.xkgd.marieteam.api.RetrofitClient;
import fr.xkgd.marieteam.api.service.TraverseeService;
import fr.xkgd.marieteam.data.local.dao.TraverseeDao;
import fr.xkgd.marieteam.data.local.database.AppDatabase;
import fr.xkgd.marieteam.model.Traversee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraverseeRepository {

    private static final String TAG = "TraverseeRepository";
    private Application application;
    private int idCapitaine;
    private static TraverseeRepository INSTANCE;
    private final TraverseeService mTraverseeService;
    private final TraverseeDao mtraverseeDao;
    private final ExecutorService initTraverseesExecutor = Executors.newSingleThreadExecutor();

    private TraverseeRepository(Application application, int idCapitaine) {
        this.application = application;
        this.idCapitaine = idCapitaine;
        mTraverseeService = RetrofitClient.getInstance().create(TraverseeService.class);
        AppDatabase db = AppDatabase.getDatabase(application);
        mtraverseeDao = db.traverseeDao();
    }

    /**
     * Singleton permettant de récupérer une instance de TraverseeRepository.
     * @param application Application
     * @param idCapitaine Id du capitaine
     * @return Instance de TraverseeRepository
     */
    public static synchronized TraverseeRepository getInstance(Application application, int idCapitaine) {
        if (INSTANCE == null) {
            INSTANCE = new TraverseeRepository(application, idCapitaine);
        }
        return INSTANCE;
    }

    /**
     * Récupère toutes les traversées du capitaine depuis la base de données locale.
     * @return Liste des traversées
     */
    public List<Traversee> getCapitaineTraversees(int idCapitaine) {
        Log.d(TAG, "getCapitaineTraversees: CALLED");
        return mtraverseeDao.getCapitaineTraversees(idCapitaine);
    }

    /**
     * Récupère toutes les traversées du capitaine depuis la base de données distante.
     * Met à jour le statut des traversées récupérées.
     * @return CompletableFuture permettant de savoir si la requête a réussi
     */
    public CompletableFuture<List<Traversee>> fetchAllViaDistDbCf() {
        CompletableFuture<List<Traversee>> future = new CompletableFuture<>();
        List<Traversee> distDbTraversees = new ArrayList<>();
        Call<ApiResponse<List<Traversee>>> call = mTraverseeService.getCapitaineTraversees(idCapitaine);
        call.enqueue(new Callback<ApiResponse<List<Traversee>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<List<Traversee>>> call, @NonNull Response<ApiResponse<List<Traversee>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    List<Traversee> traversees = response.body().getData();
                    for (Traversee traversee : traversees) {
                        traversee.setStatus(1);
                        distDbTraversees.add(traversee);
                    }
                    Log.d(TAG, "fetchAllViaDistDbCf: SUCCESS");
                    future.complete(distDbTraversees);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<List<Traversee>>> call, @NonNull Throwable t) {
                Log.d(TAG, "fetchAllViaDistDbCf: FAILED");
                future.completeExceptionally(t);
            }
        });
        return future;
    }

    /**
     * Insère toutes les traversées dans la base de données locale.
     * @param distDbTraversees Liste des traversées
     */
    public void insertAllInLocalDb(List<Traversee> distDbTraversees) {
        mtraverseeDao.insertAll(distDbTraversees);
        Log.d(TAG, "insertAllInLocalDb: SUCCESS");
    }

    /**
     * Met à jour toutes les traversées dans la base de données locale.
     * @param traversees Liste des traversées
     */
    public void updateAllInLocalDb(List<Traversee> traversees) {
        mtraverseeDao.updateAll(traversees);
        Log.d(TAG, "updateAllInLocalDb: SUCCESS");
    }

    /**
     * Met à jour toutes les traversées dans la base de données distante.
     * Met à jour le statut des traversées.
     * @param localDbTraverseesToUpdate Liste des traversées
     * @return CompletableFuture permettant de savoir si la requête a réussi
     */
    public CompletableFuture<Void> updateAllInDistDb(List<Traversee> localDbTraverseesToUpdate) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Call<ApiResponse<Void>> call = mTraverseeService.updateAll(localDbTraverseesToUpdate);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Void>> call, @NonNull Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    for (Traversee localDbTraversee : localDbTraverseesToUpdate)
                        localDbTraversee.setStatus(1);
                    Log.d(TAG, "updateAllInDistDb: SUCCESS");
                    future.complete(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Void>> call, @NonNull Throwable t) {
                Log.d(TAG, "updateAllInDistDb: FAILED");
                future.completeExceptionally(t);
            }
        });
        return future;
    }

    /**
     * Met à jour une traversée modifiée dans la base de données locale.
     * @param modifiedTraversee Traversée modifiée
     */
    public void updateLocalDbTraversee(Traversee modifiedTraversee) {
        mtraverseeDao.update(modifiedTraversee);
    }

    public ExecutorService getInitTraverseesExecutor() {
        return initTraverseesExecutor;
    }
}