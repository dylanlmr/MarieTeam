package fr.xkgd.marieteam.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TraverseeViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private int idCapitaine;

    public TraverseeViewModelFactory(Application application, int idCapitaine) {
        this.application = application;
        this.idCapitaine = idCapitaine;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TraverseeViewModel(application, idCapitaine);
    }
}
