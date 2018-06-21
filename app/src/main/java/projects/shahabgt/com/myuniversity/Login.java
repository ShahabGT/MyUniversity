package projects.shahabgt.com.myuniversity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.marcohc.toasteroid.Toasteroid;


import java.util.HashMap;
import java.util.Map;

import projects.shahabgt.com.myuniversity.classes.Mysingleton;

public class Login extends AppCompatActivity {

    Button signOn;
    EditText id,pass;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    SharedPreferences sp;
    String token,deviceid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        builder = new AlertDialog.Builder(Login.this);
        sp= getApplicationContext().getSharedPreferences("logininfo",0);

        int stat = sp.getInt("stat",88);
        if(stat==1)
        {
            startActivity(new Intent(Login.this,Main.class));
            this.finish();
        }

        id = findViewById(R.id.login_id);
        pass= findViewById(R.id.login_pass);
        signOn = findViewById(R.id.login_signon);
        signOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().isEmpty()||pass.getText().toString().isEmpty())
                {
                    Toasteroid.show(Login.this,"Enter StudentNumber/Password", Toasteroid.STYLES.WARNING);
                }else{
                    token = sp.getString("token","gg");
                    if(token.length()<5){
                        token= FirebaseInstanceId.getInstance().getToken();
                    }
                    FirebaseMessaging.getInstance().subscribeToTopic("myuniversity_users");
                    TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                    deviceid = telephonyManager.getDeviceId();
                    if(deviceid==null){
                        deviceid= Build.SERIAL;
                    }
                    logIn(id.getText().toString(), pass.getText().toString(),token,deviceid);
                }
            }
        });
    }
    private void logIn(final String idtext,final String passtext, final String token,final String deviceid){
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }catch (Exception e){}
        if(checknet()){
            progressDialog= new ProgressDialog(Login.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
            final String url=getResources().getString(R.string.url)+"login.php";
            StringRequest loginrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("ok"))
                    {
                        progressDialog.dismiss();
                        SharedPreferences.Editor e=sp.edit();
                        e.putString("id",idtext);
                        e.putString("deviceid",deviceid);
                        e.putString("password",passtext);
                        e.putInt("stat",1);
                        e.apply();
                        startActivity(new Intent(Login.this,Main.class));
                        Login.this.finish();
                    }else if(response.equals("wrongpass")){
                        progressDialog.dismiss();
                        DisplayMassage("Error!","Wrong Password!");

                    }else if(response.equals("notexist")){
                        progressDialog.dismiss();
                        DisplayMassage("Error!","Student Does Not Exist!");
                    }else if(response.equals("notok")){
                        progressDialog.dismiss();
                        DisplayMassage("Error!","Try Again!");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toasteroid.show(Login.this,"Please Re-open The App", Toasteroid.STYLES.ERROR);
                }
            }){

                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("id",idtext);
                    params.put("password",passtext);
                    params.put("token",token);
                    params.put("deviceid",deviceid);
                    return params;
                }
            };
            Mysingleton.getmInstance(Login.this).addToRequestque(loginrequest);

        }else{
            Toasteroid.show(Login.this,"Check Your Internet Connection!", Toasteroid.STYLES.ERROR);
        }
    }

    private boolean checknet(){
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        return activeInfo != null && activeInfo.isConnected();
    }

    public void DisplayMassage(String title, String message){
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                id.setText("");
                pass.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }
}
