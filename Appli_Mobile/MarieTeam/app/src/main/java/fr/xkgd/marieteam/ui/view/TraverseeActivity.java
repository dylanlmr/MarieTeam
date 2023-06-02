package fr.xkgd.marieteam.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.xkgd.marieteam.R;
import fr.xkgd.marieteam.adapter.OnItemClickListener;
import fr.xkgd.marieteam.adapter.TraverseeAdapter;
import fr.xkgd.marieteam.model.Capitaine;
import fr.xkgd.marieteam.model.Traversee;
import fr.xkgd.marieteam.networking.NetworkStateChecker;
import fr.xkgd.marieteam.ui.viewmodel.TraverseeViewModel;
import fr.xkgd.marieteam.ui.viewmodel.TraverseeViewModelFactory;

public class TraverseeActivity extends AppCompatActivity {

    private static final String TAG = "TraverseeActivity";
    private Capitaine capitaine;
    private TraverseeViewModel viewModel;
    private RecyclerView recyclerView;
    private TraverseeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traversee);

        init();
    }

    /**
     * Initialisation de l'activité.
     */
    private void init() {
        capitaine = (Capitaine) getIntent().getSerializableExtra("capitaine");
        setTitle("Traversées de " + capitaine.getPrenom() + " !");

        TraverseeViewModelFactory viewModelFactory = new TraverseeViewModelFactory(getApplication(), capitaine.getId());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(TraverseeViewModel.class);

        recyclerView = findViewById(R.id.recyclerViewTraversees);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TraverseeAdapter(new ArrayList<>(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Traversee traversee = adapter.getTraversee(position);
                Intent intent = new Intent(TraverseeActivity.this, InformationTraverseeActivity.class);
                intent.putExtra("traversee", traversee)
                        .putExtra("index", position);
                getResult.launch(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        viewModel.getTraversees().observe(this, new Observer<List<Traversee>>() {
            @Override
            public void onChanged(List<Traversee> traversees) {
                adapter.setTraversees(traversees);
            }
        });
    }

    ActivityResultLauncher<Intent> getResult =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            if (result.getData() != null) {
                                Traversee modifiedTraversee = (Traversee) result.getData().getSerializableExtra("traversee");
                                int index = (int) result.getData().getSerializableExtra("index");
                                adapter.editTraversee(index, modifiedTraversee);
                                viewModel.updateLocalDbTraversee(modifiedTraversee);
                            }
                        }
                    }
            );
}