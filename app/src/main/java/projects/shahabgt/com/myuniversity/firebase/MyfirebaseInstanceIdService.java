package projects.shahabgt.com.myuniversity.firebase;

import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;


public class MyfirebaseInstanceIdService extends FirebaseInstanceIdService {
    SharedPreferences sp;


    @Override
    public void onTokenRefresh() {
        sp= getApplicationContext().getSharedPreferences("logininfo",0);
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("iau_users");
        sp.edit().putString("token",recent_token).apply();
    }
}
