package mx.com.ioblok.buddystars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import mx.com.ioblok.buddystars.home.SelectActivity;
import mx.com.ioblok.buddystars.utils.User;

public class SplashActivity extends Activity {

    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity = this;
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    if (User.logged(activity)) {
                        Intent intent = new Intent(SplashActivity.this, SelectActivity.class);
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
