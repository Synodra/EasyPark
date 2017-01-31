package a.easypark4.Activity;

import android.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import a.easypark4.R;
import a.easypark4.app.AppController;
import a.easypark4.helper.SQLiteHandler;

import static a.easypark4.R.layout.fragment_profile;
import static a.easypark4.app.AppController.TAG;
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
    private Button buttonModify, buttonSave; // Déclaration des boutons dans account settings

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

        //SqLite database handler
        HashMap<String, String> user = db.getUserDetails();

        txtName.setText(user.get("name"));
        txtLastName.setText(user.get("firstname"));
        txtEmail.setText(user.get("email"));

        Button buttonModify = (Button) myInflatedView.findViewById(R.id.modifier);
        final Button buttonSave = (Button) myInflatedView.findViewById(R.id.enregistrer);
        buttonSave.setVisibility(INVISIBLE);

        buttonModify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                buttonSave.setVisibility(VISIBLE);
                txtName.setFocusableInTouchMode(true);
                txtLastName.setFocusableInTouchMode(true);
                txtEmail.setFocusableInTouchMode(true);

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
}
