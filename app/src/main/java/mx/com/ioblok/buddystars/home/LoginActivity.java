package mx.com.ioblok.buddystars.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import mx.com.ioblok.buddystars.R;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        Intent home = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(home);
    }
}
