package fr.xkgd.marieteam.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import fr.xkgd.marieteam.R;
import fr.xkgd.marieteam.model.Capitaine;
import fr.xkgd.marieteam.ui.viewmodel.CapitaineViewModel;

public class MainActivity extends AppCompatActivity {
    private EditText editTextMail;
    private EditText editTextPassword;
    private Button btnLogin;
    private CapitaineViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    /**
     * Initialise les composants de la vue.
     */
    private void init() {
        editTextMail = findViewById(R.id.editTextMail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        editTextMail.setText("ep@ep.com");
        editTextPassword.setText("Aa123456789!");
        viewModel = new ViewModelProvider(this).get(CapitaineViewModel.class);

        btnLogin.setOnClickListener(v -> {

            String mail = editTextMail.getText().toString();
            String password = editTextPassword.getText().toString();

            Log.d("TTTTT", "ON CLICKED !");

            viewModel.login(mail, password, new CapitaineViewModel.LoginCallback() {
                @Override
                public void onLoginSuccess(Capitaine capitaine, String message) {
                    Log.d("TAG", message);
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        startTraverseeActivity(capitaine);
                    });
                }

                @Override
                public void onLoginFailure(String message) {
                    Log.e("TAG", message);
                    runOnUiThread(() ->
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
                }
            });
        });
    }

    /**
     * Lance l'activité de liste des traversées.
     * @param capitaine le capitaine connecté.
     */
    private void startTraverseeActivity(Capitaine capitaine) {
        Intent intent = new Intent(this, TraverseeActivity.class);
        intent.putExtra("capitaine", capitaine);
        startActivity(intent);
    }
}