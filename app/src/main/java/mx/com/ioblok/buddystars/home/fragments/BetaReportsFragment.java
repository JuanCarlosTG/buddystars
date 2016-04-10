package mx.com.ioblok.buddystars.home.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.utils.WebBridge;

/**
 * Created by kreativeco on 01/02/16.
 */
public class BetaReportsFragment extends Fragment  implements WebBridge.WebBridgeListener  {

    View v;
    TextView new_container, new_portability;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_beta_reports, null);

        new_container = (TextView) v.findViewById(R.id.new_container);
        new_portability = (TextView) v.findViewById(R.id.new_portability);

        getData();

        return v;

    }

    public void getData(){
        WebBridge.send("/report-alpha","Enviando", getActivity(), this);
    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        try {

            JSONObject main  = json.getJSONObject("data");
            String e = main.getString("total_new");
            new_container.setText(e);
            String f = main.getString("total_portability");
            new_portability.setText(f);

        } catch (Exception e) {
            Log.e("Exception Wey", e.toString());
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("Mal" , response);
    }
}
