package com.usher.usher.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.usher.usher.MainMenu;
import com.usher.usher.R;
import com.usher.usher.RegisterUser;
import com.usher.usher.interfaces.LoginActivityPresenter;
import com.usher.usher.interfaces.LoginActivityView;
import com.usher.usher.presenters.LoginActivityPresenterImpl;
import com.usher.usher.requests.LoginActivityRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements LoginActivityView {


    TextView tv_registerUser;
    EditText et_userLogin, et_passwordLogin;
    Button btn_loginUser;
    private ProgressBar pr_progresBarLogin;
    private LoginActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_registerUser = findViewById(R.id.tvRegLogin);
        btn_loginUser = findViewById(R.id.btnIniLogin);
        et_userLogin = findViewById(R.id.etUserLogin);
        et_passwordLogin = findViewById(R.id.etPassLogin);
        pr_progresBarLogin = findViewById(R.id.progressLogin);

        //Pasamos metodos del View al Presenter
        presenter = new LoginActivityPresenterImpl(this);

        tv_registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegUser = new Intent(LoginActivity.this, RegisterUser.class);
                LoginActivity.this.startActivity(intentRegUser);
            }
        });

        btn_loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doLogin(et_userLogin.getText().toString(), et_passwordLogin.getText().toString());
                presenter.volleyNoEntiendo(LoginActivity.this);
            }
        });
    }


    @Override
    public void showProgress(boolean option) {
        pr_progresBarLogin.setVisibility(option ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showResult(String result) {
        // Deberia ejecutar el LoginOK
        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginSuccessfullView(String name, String surname, String username, String password) {
        Intent intent = new Intent(LoginActivity.this, MainMenu.class);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        LoginActivity.this.startActivity(intent);
        et_passwordLogin.getText().clear();
    }

    @Override
    public void showLoginFailed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("No te encuentras habilitado por Usher para acceder").setNegativeButton("Reintentar más tarde", null).create().show();
    }

    @Override
    public void showError() {
        Toast.makeText( getApplicationContext(), "Error en la conexion", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPasswordClean() {
        Toast.makeText( getApplicationContext(), "El password no fue ingresado", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUsernameClean() {
        Toast.makeText( getApplicationContext(), "El usuario no fue ingresado", Toast.LENGTH_LONG).show();
    }
}