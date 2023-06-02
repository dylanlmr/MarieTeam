package fr.xkgd.marieteam.ui.viewmodel;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.Network;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import fr.xkgd.marieteam.data.repository.TraverseeRepository;
import fr.xkgd.marieteam.model.Traversee;
import fr.xkgd.marieteam.networking.NetworkStateChecker;

public class TraverseeViewModel extends AndroidViewModel {

    private static final String TAG = "TraverseeViewModel";
    private int idCapitaine;
    private TraverseeRepository mRepository;
    private MutableLiveData<List<Traversee>> mTraversees;
    private NetworkStateChecker networkStateChecker;
    private boolean isNetworkAvailable;

    public TraverseeViewModel(@NonNull Application application, int idCapitaine) {
        super(application);
        this.idCapitaine = idCapitaine;
        mRepository = TraverseeRepository.getInstance(application, idCapitaine);
        mTraversees = new MutableLiveData<>();

        networkStateChecker = new NetworkStateChecker(application);
        if (!networkStateChecker.isNetworkAvailable()) {
            isNetworkAvailable = false;
            initLocalDbTraversees();
        }
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                Log.d(TAG, "onAvailable: ");
                isNetworkAvailable = true;
                initLocalDbTraversees();
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                Log.d(TAG, "onLost: ");
                isNetworkAvailable = false;
            }
        };
        networkStateChecker.startNetworkStateChecker(networkCallback);

        Log.d(TAG, "ON est là");
    }

    public LiveData<List<Traversee>> getTraversees() {
        return mTraversees;
    }

    /**
     * Initialise la liste des traversées en local.
     * Si le réseau est disponible, on met à jour la base de données distante et on récupère les données.
     * Sinon, on récupère les données en local.
     */
    public void initLocalDbTraversees() {
        if (!isNetworkAvailable) {
            CompletableFuture.supplyAsync(() -> mRepository.getCapitaineTraversees(idCapitaine), mRepository.getInitTraverseesExecutor())
                    .whenComplete((result, throwable) -> {
                        if (throwable != null) {
                            Log.e(TAG, "initLocalDbTraversees: " + throwable);
                        } else {
                            Log.d(TAG, "Mise à jour avant affichage: OKAY");
                            mTraversees.postValue(result);
                        }
                    });
            return;
        }
        CompletableFuture.supplyAsync(() -> mRepository.getCapitaineTraversees(idCapitaine), mRepository.getInitTraverseesExecutor())
                .thenComposeAsync(localDbTraversees -> {
                    if (localDbTraversees == null || localDbTraversees.isEmpty()) {
                        return mRepository.fetchAllViaDistDbCf()
                                .thenApplyAsync(distDbTraversees -> {
                                    mRepository.insertAllInLocalDb(distDbTraversees);
                                    return distDbTraversees;
                                });
                    } else {
                        List<Traversee> localDbTravToUpdate = localDbTraversees.stream()
                                .filter(traversee -> traversee.getStatus() == 0)
                                .collect(Collectors.toList());
                        if (!localDbTravToUpdate.isEmpty()) {
                            return mRepository.updateAllInDistDb(localDbTravToUpdate)
                                    .thenComposeAsync(ignore ->
                                            mRepository.fetchAllViaDistDbCf()
                                                    .thenApplyAsync(distDbTraversees -> {
                                                        mRepository.updateAllInLocalDb(distDbTraversees);
                                                        return distDbTraversees;
                                                    }));
                        } else {
                            return CompletableFuture.completedFuture(localDbTraversees);
                        }
                    }
                })
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        Log.e(TAG, "initLocalDbTraversees: " + throwable);
                    } else {
                        Log.d(TAG, "Mise à jour avant affichage: OKAY");
                        mTraversees.postValue(result);
                    }
                });
    }

    /**
     * Met à jour la base de données locale avec la traversée modifiée.
     * @param modifiedTraversee La traversée modifiée.
     */
    public void updateLocalDbTraversee(Traversee modifiedTraversee) {
        CompletableFuture.runAsync(() -> mRepository.updateLocalDbTraversee(modifiedTraversee))
                .whenComplete((ignore, throwable) -> {
                    if (throwable != null) {
                        Log.e(TAG, "updateLocalDbTraversee: ", throwable);
                    } else {
                        Log.d(TAG, "updateLocalDbTraversee: UPDATED !");
                        initLocalDbTraversees();
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared: ");
        networkStateChecker.stopNetworkStateChecker();
    }
}
