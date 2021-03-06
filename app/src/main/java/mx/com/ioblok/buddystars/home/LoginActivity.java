package mx.com.ioblok.buddystars.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.customviews.CustomEditTextRegular;
import mx.com.ioblok.buddystars.customviews.CustomTextViewRegular;
import mx.com.ioblok.buddystars.utils.Constants;
import mx.com.ioblok.buddystars.utils.User;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class LoginActivity extends Activity implements WebBridge.WebBridgeListener{

    private CustomEditTextRegular editTextUser, editTextPass;
    private ImageButton btnBackHeader;
    private CustomTextViewRegular txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);

        btnBackHeader = (ImageButton) findViewById(R.id.btn_back_login);
        btnBackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.isUserOffline()){
                    Intent selectLogin = new Intent(LoginActivity.this, SelectActivity.class);
                    startActivity(selectLogin);
                    finish();
                    LoginActivity.this.overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
                }else
                {
                    finish();
                    LoginActivity.this.overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);

                }
            }
        });

        txtTitle = (CustomTextViewRegular) findViewById(R.id.txt_title);
        txtTitle.setText("Ingresar");

        editTextUser = (CustomEditTextRegular) findViewById(R.id.txt_username);
        editTextPass = (CustomEditTextRegular) findViewById(R.id.txt_password);

        editTextUser.setText("buddy1");
        editTextPass.setText("123456");
    }

    public void login(View view){

        ArrayList<String> errors = new ArrayList<String>();
        if (editTextUser.getText().length() < 1) errors.add(getString(R.string.txt_error_user));
        if (editTextPass.getText().length() < 1)
            errors.add(getString(R.string.txt_error_pass));

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "- " + s + "\n";
            }
            new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("username", editTextUser.getText().toString());
        params.put("password", editTextPass.getText().toString());
        WebBridge.send("/login", params, "Enviando", this, this);
        /*Intent home = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(home);*/
    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("JSON", json.toString());

        try {
            if (json.getBoolean("success")){

                Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                User.logged(true, this, json.getJSONObject("data").getString("active"));
                User.set("role", json.getJSONObject("data").getString("role"), this);
                User.set("username", json.getJSONObject("data").getString("username"), this);
                User.set("avatar", json.getJSONObject("data").getString("avatar"), this);
                User.set("first_name", json.getJSONObject("data").getString("first_name"), this);
                User.set("last_name", json.getJSONObject("data").getString("last_name"), this);

                startActivity(home);
                if(SelectActivity.selectActivity != null)
                    SelectActivity.selectActivity.finish();

                finish();
                LoginActivity.this.overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("EXCEPTION", e.toString());
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("JSON", response);
    }


    public void clickBack(View v) {
        finish();
        overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
    }

    @Override
    public void onBackPressed() {
        clickBack(null);
    }
}
