package a.easypark4;

/**
 * Created by nico.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import a.easypark4.R;
import a.easypark4.MainActivity;
import a.easypark4.AppController;
import a.easypark4.AppConfig;
import helper.SQLiteHandler;
import helper.SessionManager;
public class RegisterActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputNom;
    private EditText inputPrenom;
    private EditText inputLogin;
    private EditText inputEmail;
    //private EditText inputDate;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputNom = (EditText) findViewById(R.id.nom);
        inputPrenom = (EditText) findViewById(R.id.prenom);
        inputLogin = (EditText) findViewById(R.id.login);
        inputEmail = (EditText) findViewById(R.id.email);
        //inputDate = (EditText) findViewById(R.id.date_naissance);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // manager de session
        session = new SessionManager(getApplicationContext());

        // SQLite bdd handler
        db = new SQLiteHandler(getApplicationContext());

        // Check si l'user est deja connecté ou non
        if (session.isLoggedIn()) {
            // Si il est deja connecté on l'amene au menu principal
            Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Bouton Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nom = inputNom.getText().toString().trim();
                String prenom = inputPrenom.getText().toString().trim();
                String login = inputLogin.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
               // String date_naissance = inputDate.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!nom.isEmpty() && !prenom.isEmpty() && !login.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerUser(nom, prenom, login, email/*, date_naissance*/,  password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Bouton pour la page de login
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        EasyPark.class);
                startActivity(i);
                finish();
            }
        });

    }

    /*
    Fonction pour stocker les info user(nom, prenom...) dans la bdd  MySQL
    */
    private void registerUser(final String nom, final String prenom, final String login, final String email/*, final String date_naissance*/, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User stocké dans la bdd -> stockage dans sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String nom = user.getString("nom");
                        String prenom = user.getString("prenom");
                        String login = user.getString("login");
                        String email = user.getString("email");
                        String date_creation = user.getString("date_creation");
                        String date_naissance = user.getString("date_naissance");

                        // info user utile
                        db.addUser(nom, prenom, login, email, uid, date_creation, date_naissance);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // lancer login activity pour se connecter
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                EasyPark.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Une erreur lors de l'enregistrement de l'user, on recupere l'erreur
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nom", nom);
                params.put("prenom", prenom);
                params.put("login", login);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Ajout requete a la file d'attente de requete
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}