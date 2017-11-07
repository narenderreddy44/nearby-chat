package pro.postaru.sandu.nearbychat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import pro.postaru.sandu.nearbychat.R;
import pro.postaru.sandu.nearbychat.utils.DataValidator;

public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener activity;

    private EditText usernameView;
    private EditText passwordView;

    private ProgressBar progressBar;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button mountRegisterFragmentButton = (Button) view.findViewById(R.id.goto_register_btn);
        mountRegisterFragmentButton.setOnClickListener(view1 -> activity.mountRegisterFragment());

        Button loginButton = (Button) view.findViewById(R.id.login_btn);
        loginButton.setOnClickListener(view1 -> loginHandler());

        usernameView = (EditText) view.findViewById(R.id.login_username);
        usernameView.requestFocus();

        passwordView = (EditText) view.findViewById(R.id.login_pass);

        progressBar = (ProgressBar) view.findViewById(R.id.login_progress);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            activity = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    private void loginHandler() {

        usernameView.setError(null);
        passwordView.setError(null);

        String userName = usernameView.getText().toString();
        String userPassword = passwordView.getText().toString();

        View errorView = null;

        if (TextUtils.isEmpty(userName)) {
            usernameView.setError(getString(R.string.error_field_required));
            errorView = usernameView;
        } else if (!DataValidator.isUsernameValid(userName)) {
            usernameView.setError(getString(R.string.error_invalid_username));
            errorView = usernameView;
        }

        if (!DataValidator.isPasswordValid(userPassword)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            errorView = passwordView;
        }

        if (errorView != null) {
            errorView.requestFocus();
        } else {
            activity.requestLogin(userName, userPassword);
        }
    }

    public interface OnFragmentInteractionListener {

        boolean requestLogin(String username, String password);

        void mountRegisterFragment();
    }

}
