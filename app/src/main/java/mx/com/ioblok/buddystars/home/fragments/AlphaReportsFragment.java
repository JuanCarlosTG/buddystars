package mx.com.ioblok.buddystars.home.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.adapter.AlphaReportElementAdapter;
import mx.com.ioblok.buddystars.customviews.CustomTextViewRegular;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class AlphaReportsFragment extends Fragment  implements WebBridge.WebBridgeListener  {

    View v;
    CustomTextViewRegular new_container, new_portability;
    private RecyclerView recyclerViewReports;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_alpha_reports, null);

        new_container = (CustomTextViewRegular) v.findViewById(R.id.new_container);
        new_portability = (CustomTextViewRegular) v.findViewById(R.id.new_portability);

        getData();
        initialize();
        return v;

    }

    public void initialize() {

        recyclerViewReports = (RecyclerView) v.findViewById(R.id.recycler_view_blog);
        recyclerViewReports.setHasFixedSize(false);
        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewReports.setLayoutManager(rvLayoutManager);


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

        RecyclerView.Adapter rvAdapter = new AlphaReportElementAdapter(b, getActivity());
        recyclerViewReports.setAdapter(rvAdapter);

    }
}
