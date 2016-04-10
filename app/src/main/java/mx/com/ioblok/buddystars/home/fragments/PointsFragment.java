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

public class PointsFragment extends Fragment implements WebBridge.WebBridgeListener {

    View v;
    TextView tv_total_pts, tv_portability, tv_new;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_points, null);

        tv_total_pts = (TextView) v.findViewById(R.id.tv_total_pts);
        tv_new = (TextView) v.findViewById(R.id.tv_new);
        tv_portability = (TextView) v.findViewById(R.id.tv_portability);

        getData();
        return v;

    }

    public void getData(){
        WebBridge.send("/report-alpha", "Enviando", getActivity(), this);
    }


    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("Bien", json.toString());

        try {

            JSONObject main  = json.getJSONObject("data");
            String tp = main.getString("total_points");
            String tn = main.getString("total_new");
            String tpb = main.getString("total_portability");

            tv_total_pts.setText(tp);
            tv_total_pts.setTextColor(getResources().getColor(R.color.azulMovistar));

            tv_new.setText(tn);
            tv_new.setTextColor(getResources().getColor(R.color.azulMovistar));

            tv_portability.setText(tpb);
            tv_portability.setTextColor(getResources().getColor(R.color.azulMovistar));


        } catch (Exception e) {
            Log.e("Exception Wey", e.toString());
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("Mal" ,response);
    }

}
