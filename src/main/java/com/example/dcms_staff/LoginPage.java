package com.example.dcms_staff;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class LoginPage extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences sharedPreferences;
    Button enter, forgot_password;
    ImageView test1;
    CircleImageView test2;
    EditText username, password;
    String username2, password2;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__page);

        username = findViewById(R.id.UserName);
        password = findViewById(R.id.password);
        enter = findViewById(R.id.enter);
        forgot_password = findViewById(R.id.forgotPassword);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username2 = username.getText().toString().trim();
                password2 = password.getText().toString().trim();
                // Check for empty data in the form
                if ("".equals(username) && !password2.isEmpty()) {
                    // login user
                    checkLogin();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(), "Please enter your information!", Toast.LENGTH_LONG).show();
                }
            }
        });


        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginPage.this);
                alertDialogBuilder.setTitle("Forget Password");
                alertDialogBuilder.setMessage("Kindly contact 019-4491837, Siti Noor Roskam Shamiza");

                final Dialog dialog = new Dialog(LoginPage.this);

                alertDialogBuilder.setPositiveButton("Okay",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dialog.setCanceledOnTouchOutside(true);


                                                try {
                                                    if (Settings.Global.getInt(getContentResolver(), Settings.Global.AUTO_TIME) == 0) {

                                                        Toast.makeText(getApplicationContext(),
                                                                "Please set Automatic Date & Time to ON in the Settings",
                                                                Toast.LENGTH_LONG).show();

                                                        startActivityForResult(
                                                                new Intent(Settings.ACTION_DATE_SETTINGS), 0);
                                                    } else if (Settings.Global.getInt(getContentResolver(),
                                                            Settings.Global.AUTO_TIME_ZONE) == 0) {

                                                        Toast.makeText(getApplicationContext(),
                                                                "Please set Automatic Time Zone to ON in the Settings",
                                                                Toast.LENGTH_LONG).show();

                                                        startActivityForResult(
                                                                new Intent(Settings.ACTION_DATE_SETTINGS), 0);
                                                    }else {


                                                        Toast.makeText(getApplicationContext(),
                                                                "OKAY OKAY",
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                } catch (Settings.SettingNotFoundException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                        });


                                alertDialogBuilder.setOnCancelListener(
                                        new DialogInterface.OnCancelListener() {
                                            @Override
                                            public void onCancel(DialogInterface dialog) {

                                            }
                                        }
                                );

                                //Showing the alert dialog
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

            }
        });

    }

    private void checkLogin(){
        //Getting values from edit texts
        final ProgressDialog loading = ProgressDialog.show(this,"Please Wait","Contacting Server",false,false);
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://dialysiscentremanagementsystem.000webhostapp.com/loginPatient.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //If we are getting success from server
                        if(response.equalsIgnoreCase("success")){
                            //Creating a shared preference

                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            loading.dismiss();
                            Toast.makeText(LoginPage.this, "Invalid ID or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        loading.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(LoginPage.this,"No internet . Please check your connection",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{

                            Toast.makeText(LoginPage.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //Adding parameters to request
                params.put("username", username2);
                params.put("password", password2);

                //returning parameter
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}