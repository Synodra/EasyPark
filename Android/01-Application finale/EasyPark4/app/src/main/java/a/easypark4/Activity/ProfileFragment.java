package a.easypark4.Activity;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import a.easypark4.R;
import a.easypark4.app.AppConfig;
import a.easypark4.app.AppController;
import a.easypark4.helper.SQLiteHandler;

import static a.easypark4.R.layout.fragment_profile;
import static a.easypark4.app.AppController.TAG;
import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SQLiteHandler db;           // Déclaration de la base de données local
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView txtName;           // Déclaration de la zone de texte prénom dans account settings
    private TextView txtLastName;          // Déclaration de la zone de texte nom dans account settings
    private TextView txtEmail;          // Déclaration de la zone de text email dans account settings
    private TextView txtPassword;
    private TextView txtNewPassword;
    private TextView txtNewPassword2;
    private Button buttonModify, buttonSave, buttonModifyPassword, buttonSavePassword; // Déclaration des boutons dans account settings
    private ProgressDialog pDialog;
    private RelativeLayout layout_user_password;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        pDialog = new ProgressDialog(this.getActivity());
        pDialog.setCancelable(false);
        //SqLite database handler
        db = new SQLiteHandler(getActivity().getApplicationContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myInflatedView = inflater.inflate(fragment_profile, container,false);

        txtName = (TextView) myInflatedView.findViewById(R.id.prenom);
        txtName.setFocusable(false);
        txtLastName = (TextView) myInflatedView.findViewById(R.id.nom);
        txtLastName.setFocusable(false);
        txtEmail = (TextView) myInflatedView.findViewById(R.id.email);
        txtEmail.setFocusable(false);
        txtPassword = (TextView) myInflatedView.findViewById(R.id.oldPassword);
        txtNewPassword = (TextView) myInflatedView.findViewById(R.id.newPassword1);
        txtNewPassword2 = (TextView) myInflatedView.findViewById(R.id.newPassword2);
        layout_user_password = (RelativeLayout) myInflatedView.findViewById(R.id.layout_user_password);

        //SqLite database handler
        HashMap<String, String> user = db.getUserDetails();

        txtName.setText(user.get("name"));
        txtLastName.setText(user.get("firstname"));
        txtEmail.setText(user.get("email"));

        Button buttonModify = (Button) myInflatedView.findViewById(R.id.modifier);
        Button buttonModifyPassword = (Button) myInflatedView.findViewById(R.id.modifierPassword);
        final Button buttonSave = (Button) myInflatedView.findViewById(R.id.enregistrer);
        final Button buttonSavePassword = (Button) myInflatedView.findViewById(R.id.savePassword);
        buttonSave.setVisibility(INVISIBLE);

        layout_user_password.setVisibility(GONE);

        buttonModify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                buttonSave.setVisibility(VISIBLE);
                txtName.setFocusableInTouchMode(true);
                txtLastName.setFocusableInTouchMode(true);
                txtEmail.setFocusableInTouchMode(true);
                buttonSave.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        modifyUser("changeusername", txtEmail.getText().toString(), txtName.getText().toString(), txtLastName.getText().toString());
                        buttonSave.setVisibility(INVISIBLE);
                        txtName.setFocusableInTouchMode(false);
                        txtLastName.setFocusableInTouchMode(false);
                        txtEmail.setFocusableInTouchMode(false);
                    }
                });

            }
        });

        buttonModifyPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //buttonSavePassword.setVisibility(VISIBLE);
                layout_user_password.setVisibility(VISIBLE);

                buttonSavePassword.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        if(txtNewPassword.getText().toString().equals(txtNewPassword2.getText().toString())) {
                            modifyUserPassword("changepassword", txtEmail.getText().toString(), txtPassword.getText().toString(), txtNewPassword.getText().toString());
                            txtPassword.setText("");
                            txtNewPassword.setText("");
                            txtNewPassword2.setText("");
                            layout_user_password.setVisibility(GONE);

                        }
                        else {
                            Toast.makeText(getActivity().getApplicationContext(), "Password not corresponding !", Toast.LENGTH_LONG).show();
                            txtPassword.setText("");
                            txtNewPassword.setText("");
                            txtNewPassword2.setText("");

                        }

                    }
                });

            }
        });
        return myInflatedView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    /**
     * Function to modify user in MySQL database will post params(action, name,
     * email) to admin_user url
     * */
    private void modifyUser(final String action, final String email, final String name, final String firstname ) {
        // Tag used to cancel the request
        String tag_string_req = "req_modify_user";

        pDialog.setMessage("Modifying user ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADMIN_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String firstname = user.getString("firstname");
                        String email = user.getString("email");
                        String updated_at = user.getString("updated_at");

                        // Inserting row in users table
                        db.modifyUser(name, firstname,email, uid, updated_at);

                        Toast.makeText(getActivity().getApplicationContext(), "User successfully modified !", Toast.LENGTH_LONG).show();


                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity().getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Modification Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("action", action);
                params.put("name", name);
                params.put("firstname", firstname);
                params.put("email", email);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    /**
     * Function to modify user's password in MySQL database will post params(action, email,
     * password, newPassword) to admin_user url
     * */
    private void modifyUserPassword(final String action, final String email, final String password, final String newPassword ) {
        // Tag used to cancel the request
        String tag_string_req = "req_modify_user_password";

        pDialog.setMessage("Modifying user's password ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADMIN_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Modification Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Toast.makeText(getActivity().getApplicationContext(), "Password successfully modified !", Toast.LENGTH_LONG).show();


                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity().getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Modification Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("action", action);
                params.put("email", email);
                params.put("password", password);
                params.put("newpassword", newPassword);


                return params;
            }

        };

        // Adding request to request queue
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
