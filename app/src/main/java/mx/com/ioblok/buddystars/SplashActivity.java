package mx.com.ioblok.buddystars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import mx.com.ioblok.buddystars.home.HomeActivity;
import mx.com.ioblok.buddystars.home.SelectActivity;
import mx.com.ioblok.buddystars.utils.User;

public class SplashActivity extends Activity {

    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        activity = this;
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    Log.e("LOGEADO", User.logged(activity) + "");
                    if (User.logged(activity)) {

                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivityForResult(intent, 1);
                    } else {
                        Intent i = new Intent(SplashActivity.this, SelectActivity.class);
                        startActivity(i);
                    }

                }
            }
        };
        timerThread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
