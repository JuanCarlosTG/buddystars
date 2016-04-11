package mx.com.ioblok.buddystars.home.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class AlphaReportsFragment extends Fragment  implements WebBridge.WebBridgeListener  {

    View v;
    TextView new_container, new_portability;

    LinearLayout llDynamic;
    LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_alpha_reports, null);

        new_container = (TextView) v.findViewById(R.id.new_container);
        new_portability = (TextView) v.findViewById(R.id.new_portability);

        getData();
        initialize();
        return v;

    }

    public void initialize() {
        llDynamic = (LinearLayout) v.findViewById(R.id.ll_content);
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
            new_container.setTextColor(getResources().getColor(R.color.azulMovistar));
            String f = main.getString("total_portability");
            new_portability.setText(f);
            new_portability.setTextColor(getResources().getColor(R.color.azulMovistar));
            JSONArray b = main.getJSONArray("buddies");

            showResults(b);

            for (int i=0 ; i<b.length();i++){
                JSONObject buddies = b.getJSONObject(i);
                Log.e("valor de buddies",buddies.toString());
            }

        } catch (Exception e) {
            Log.e("Exception Wey", e.toString());
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("Mal" , response);
    }

    public void showResults(JSONArray b) {
        inflater = LayoutInflater.from(getContext());
        llDynamic.removeAllViews();

        for (int i = 0; i < b.length(); i++) {

            try {
                JSONObject c = b.getJSONObject(i);
                LinearLayout llCell = (LinearLayout) inflater.inflate(R.layout.ui_buddies, null);

                String a = c.getString("avatar");
                String fn = c.getString("full_name");
                String pp = c.getString("percent_total");

                ImageView iv_avatar = (ImageView) llCell.findViewById(R.id.iv_avatar);
                Glide.with(this).load(a).into(iv_avatar);
                TextView tv_full_name = (TextView) llCell.findViewById(R.id.tv_full_name);
                tv_full_name.setText(fn);
                tv_full_name.setTextColor(getResources().getColor(R.color.grisMovistar));
                TextView tv_percent_point = (TextView) llCell.findViewById(R.id.tv_percent_point);
                tv_percent_point.setText(pp);
                tv_percent_point.setTextColor(getResources().getColor(R.color.azulMovistar));


                llDynamic.addView(llCell);
            } catch (Exception e) {
                Log.e("Exception show result", e.toString());
            }

        }
    }
}
