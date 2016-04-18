package mx.com.ioblok.buddystars.home;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.customviews.CustomTextViewRegular;
import mx.com.ioblok.buddystars.home.fragments.RegisterFragment;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class ListUsers extends Activity implements WebBridge.WebBridgeListener {

    LinearLayout llDynamic;
    LayoutInflater inflater;
    private static final int RESULT_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        listUsers();
        initialize();
    }

    public void initialize() {
        llDynamic = (LinearLayout) findViewById(R.id.ll_content);
    }


    public void listUsers()
    {
        WebBridge.send("/register-list", "Cargando", this, this);
    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        try {

            JSONArray a = json.getJSONArray("data");
            showResults(a);

        } catch (Exception e) {
            Log.e("Exception Wey", e.toString());
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {

    }

    public void showResults(JSONArray a) {
        inflater = LayoutInflater.from(this);
        llDynamic.removeAllViews();

        for (int i = 0; i < a.length(); i++) {

            try {
                JSONObject c = a.getJSONObject(i);
                LinearLayout llCell = (LinearLayout) inflater.inflate(R.layout.fragment_list_user, null);

                final String id = c.getString("register_id");
                String n = c.getString("name");
                final CustomTextViewRegular tv_name_user = (CustomTextViewRegular) llCell.findViewById(R.id.tv_name_user);
                tv_name_user.setText(n);
                tv_name_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent i = new Intent();
                        i.putExtra("list_name_user", tv_name_user.getText().toString());
                        i.putExtra("register_id", id);
                        setResult(RESULT_OK, i);
                        finish();
                        Log.e("Presionaste a: ", tv_name_user.getText().toString());
                    }
                });
                llDynamic.addView(llCell);
            } catch (Exception e) {
                Log.e("Exception show result", e.toString());
            }

        }
    }
}
