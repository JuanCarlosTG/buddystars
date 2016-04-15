package mx.com.ioblok.buddystars.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.adapter.BlogElementAdapter;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class FBLoginActivity extends Activity implements WebBridge.WebBridgeListener{

    CallbackManager mCallbackManager;
    private String strToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("Success", loginResult.getAccessToken().getToken());
                        strToken = loginResult.getAccessToken().getToken();
                        getBlog();
                        /*Intent home = new Intent(FBLoginActivity.this, HomeActivity.class);
                        startActivity(home);*/
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(FBLoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                        Log.e("onCancel", "Login canceled");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(FBLoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("onError", "Login error" + exception.getMessage());
                    }
                });

        setContentView(R.layout.activity_fblogin);

        ImageButton btn_fb_login = (ImageButton) findViewById(R.id.btn_login_facebook);

        btn_fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(FBLoginActivity.this, Arrays.asList("public_profile", "user_friends"));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mCallbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    public void getBlog(){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("token", strToken);

        WebBridge.send("/facebook", params, "Conectando", this, this);
    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("string", json.toString());
        try {
            if (json.getBoolean("success")) {
                Intent blog = new Intent(FBLoginActivity.this, BlogActivity.class);
                startActivity(blog);
                finish();
            } else {
                String error = json.getJSONArray("error_message").getString(0);
                new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(error).setNeutralButton(R.string.bt_close, null).show();
            }
        }catch (JSONException jsonE){
            Log.e("EXCEPTION", jsonE.toString());
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {

    }

}
