package fr.xkgd.marieteam.ui.viewmodel;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.Network;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Transaction;

import java.util.concurrent.CompletableFuture;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.xkgd.marieteam.data.repository.CapitaineRepository;
import fr.xkgd.marieteam.model.Capitaine;
import fr.xkgd.marieteam.networking.NetworkStateChecker;

public class CapitaineViewModel extends AndroidViewModel {

    private static final String TAG = "CapitaineViewModel";
    private Application application;
    private CapitaineRepository mRepository;
    private NetworkStateChecker networkStateChecker;
    private boolean isNetworkAvailable;

    public CapitaineViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        mRepository = CapitaineRepository.getInstance(application);
        networkStateChecker = new NetworkStateChecker(application);

        networkStateChecker.startNetworkStateChecker(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                Log.d(TAG, "onAvailable: ");
                isNetworkAvailable = true;
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                Log.d(TAG, "onLost: ");
                isNetworkAvailable = false;
            }
        });
    }

    /**
     * Callback for login
     */
    public interface LoginCallback {
        void onLoginSuccess(Capitaine capitaine, String message);
        void onLoginFailure(String message);
    }

    /**
     * Methode pour se connecter à l'application avec un mail et un mot de passe
     * @param mail mail du capitaine
     * @param password mot de passe du capitaine
     * @param callback callback pour le retour de la requête
     */
    public void login(String mail, String password, LoginCallback callback) {
        if (isNetworkAvailable) {
            mRepository.login(mail, password).thenAccept(capitaine -> {
                if (capitaine != null) {
                    Log.d("zz", "OOOOOOOOO : " + capitaine.getMail());
                    updateOrInsertCapitaine(capitaine);
                    callback.onLoginSuccess(capitaine, "Connexion réussie");
                    networkStateChecker.stopNetworkStateChecker();
                } else {
                    callback.onLoginFailure("Erreur lors de la connexion");
                }
            });
        } else {
            CompletableFuture.supplyAsync(() -> mRepository.getRegisteredCapitaine(mail))
                    .thenAccept(registeredCapitaine -> {
                        if (registeredCapitaine != null) {
                            if (arePasswordsTheSame(password, registeredCapitaine.getPassword())) {
                                callback.onLoginSuccess(registeredCapitaine, "Connexion réussie");
                                networkStateChecker.stopNetworkStateChecker();
                            }
                            else callback.onLoginFailure("Mot de passe incorrect");
                        }
                        else {
                            callback.onLoginFailure("Erreur lors de la connexion");
                        }
                    });
        }
    }

    @Transaction
    private void updateOrInsertCapitaine(Capitaine capitaine) {

        Log.d("UOI", " : " +capitaine);

        CompletableFuture.supplyAsync(() -> mRepository.getCapitaine(capitaine.getId()))
                .thenAccept(dbCapitaine -> {

                    Log.d("UOI", dbCapitaine + " : " +capitaine);

                    if (dbCapitaine == null) {
                        mRepository.insert(capitaine);
                        Log.d("CapitaineViewModel", "updateOrInsertCapitaine: Capitaine inserted");
                    } else if (!dbCapitaine.equals(capitaine)) {
                        mRepository.update(capitaine);
                        Log.d("CapitaineViewModel", "updateOrInsertCapitaine: Capitaine updated");
                    } else {
                        Log.d("CapitaineViewModel", "updateOrInsertCapitaine: Capitaine already up to date");
                    }
                });
    }

    private boolean arePasswordsTheSame(String password, String dbPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), dbPassword);
        return result.verified;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        networkStateChecker.stopNetworkStateChecker();
    }
}
