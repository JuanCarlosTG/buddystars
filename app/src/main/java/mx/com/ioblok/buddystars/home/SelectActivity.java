package mx.com.ioblok.buddystars.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import mx.com.ioblok.buddystars.R;

public class SelectActivity extends Activity {

    public static SelectActivity selectActivity;
    ImageButton btnNormalLogin, btnFBLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        selectActivity = this;
        btnNormalLogin  = (ImageButton) findViewById(R.id.btn_buddystars);
        btnFBLogin      = (ImageButton) findViewById(R.id.bt_home_guests);

    }

    public void selectLogin(View view){
        Intent login = null;
        switch (view.getId()){
            case R.id.btn_buddystars:
                login = new Intent(SelectActivity.this, LoginActivity.class);
                break;
            case R.id.bt_home_guests:
                login = new Intent(SelectActivity.this, BlogActivity.class);
                break;
        }

        if (login == null) return;
        startActivity(login);
    }

}
