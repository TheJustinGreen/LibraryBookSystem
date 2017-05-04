package com.example.justin.librarybooks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import landing_pages.LandingPage;
import server_communication.Constants;
import server_communication.ServerCom;


public class Login extends AppCompatActivity implements View.OnClickListener
{
    //Declare variables//
    private EditText userID,password;
    public static String username;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
         The state is passed back to onCreate if the activity needs to be recreated (e.g., orientation change)
         so that you don't lose any prior information. If no data was supplied, savedInstanceState is null.
       */
        super.onCreate(savedInstanceState);

        //Set the layout for the activity//
        setContentView(R.layout.activity_login);

        //Instantiate declared variables//
        userID = (EditText) findViewById(R.id.usernname);
        password = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.btn_login);

        //Prevent Crash if button does not display on screen//
        if (loginButton != null)
            loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_login:

                startActivity(new Intent(this, LandingPage.class));
                finish();


                //Added later maybe a loading animation after the user presses login//
/*

                final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();
        try {
            new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    // On complete call either onLoginSuccess or onLoginFailed
                    progressDialog.dismiss();
                }
            }, 2000);
        }
    catch (IllegalArgumentException e)
    {
        Log.i("progressDialog", e.getMessage());
    }
*/

/*
                String query = "username=" + userID.getText().toString().trim() + "&password=" + "'" + password.getText().toString().trim() + "'" ;
                String url = Constants.URL + "index.php?Operation=1";
                String result = ServerCom.talkToServer(url, query);



                Log.i("response", result);


                //pop up message//
                Toast toastMessage = new Toast(getApplicationContext());
                //Set positioning of the pop up message//
                toastMessage.setGravity(Gravity.TOP | Gravity.START, 0, 0);

                if (result.contains("true")) {
                    ServerCom com = new ServerCom();
              //      uname  = username.getText().toString().trim();
                    //Get actual name
                    startActivity(new Intent(this, LandingPage.class));
                    finish();
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        com.SaveToDevice("UserID", jsonObj.getString("userID"),getBaseContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }





                } else if (result.contains("incorrect password"))
                    userID.setError("Please check your ID number for password");

                else if (result.contains("user does not exists"))
                    password.setError("user does not exist");
*/

                break;

        }
    }

}
