package fr.xkgd.marieteam.ui.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.xkgd.marieteam.R;
import fr.xkgd.marieteam.model.Traversee;

public class InformationTraverseeActivity extends AppCompatActivity {

    private static final String TAG = "InformationTraverseeAct";
    private Traversee traversee;
    private TextView textViewRetard;
    private Spinner spinnerEtatTraversee;
    private Spinner spinnerEtatMer;
    private EditText editTextCommentaire;
    private Button btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_traversee);
        init();
    }

    /**
     * Initialise les composants de la vue.
     */
    public void init() {
        traversee = (Traversee) getIntent().getSerializableExtra("traversee");
        int index = (int) getIntent().getSerializableExtra("index");
        setTitle("Traversee n° " + (index + 1));

        textViewRetard = findViewById(R.id.textViewRetard);
        spinnerEtatTraversee = findViewById(R.id.spinner_etat_traversee);
        spinnerEtatMer = findViewById(R.id.spinner_etat_mer);
        editTextCommentaire = findViewById(R.id.edit_text_commentaire);
        btnValider = findViewById(R.id.btnValider);

        initEtatTraversee();
        initEtatMer();

        btnValider.setOnClickListener(v -> {

            if (editTextCommentaire.getText().toString().isEmpty()) {
                editTextCommentaire.setText("Aucun commentaire.");
            }

            if (spinnerEtatTraversee.getSelectedItemPosition() == 0) {
                traversee.setTerminee(false);
                traversee.setEtatMer(spinnerEtatMer.getSelectedItem().toString());
                traversee.setCommentaire(editTextCommentaire.getText().toString());
            } else {
                traversee.setTerminee(true);
                traversee.setEtatMer("");
                traversee.setCommentaire("");
            }

            traversee.setStatus(0);

            setResult(RESULT_OK, getIntent()
                    .putExtra("traversee", traversee)
                    .putExtra("index", index));
            finish();
        });
    }

    /**
     * Initialise le spinner de l'état de la traversée.
     */
    public void initEtatTraversee() {

        String[] etatTraversee = {"En cours", "Terminée"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, etatTraversee);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEtatTraversee.setAdapter(adapter);
        if (traversee.isTerminee()) spinnerEtatTraversee.setSelection(1);

        spinnerEtatTraversee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    textViewRetard.setText(traversee.getRetard());
                    spinnerEtatMer.setEnabled(true);
                    editTextCommentaire.setEnabled(true);
                }
                else {
                    textViewRetard.setText("0 min");
                    spinnerEtatMer.setEnabled(false);
                    spinnerEtatMer.setSelection(0);
                    editTextCommentaire.setEnabled(false);
                    editTextCommentaire.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Initialise le spinner de l'état de la mer.
     */
    public void initEtatMer() {
        String[] etatsMers = {"Calme", "Peu agitée", "Agitée", "Très agitée"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, etatsMers);

        spinnerEtatMer.setSelection(traversee.getEtatMer() == null ? 0 : adapter.getPosition(traversee.getEtatMer()));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEtatMer.setAdapter(adapter);
    }
}