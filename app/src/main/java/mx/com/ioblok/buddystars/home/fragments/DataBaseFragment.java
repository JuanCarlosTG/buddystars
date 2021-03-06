package mx.com.ioblok.buddystars.home.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.adapter.DataBaseElementAdapter;
import mx.com.ioblok.buddystars.utils.WebBridge;

/**
 * Created by kreativeco on 01/02/16.
 */
public class DataBaseFragment extends Fragment implements WebBridge.WebBridgeListener {

    View v;
    RecyclerView recyclerViewDataBase;


    TextView txt_registers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_data_base, null);
        recyclerViewDataBase = (RecyclerView) v.findViewById(R.id.recycler_view_data_base);

        recyclerViewDataBase.setHasFixedSize(false);

        RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerViewDataBase.setLayoutManager(rvLayoutManager);

        WebBridge.send("/register-list", "Cargando", getActivity(), this);
        initialize();

        return v;

    }

    public void initialize() {

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "telefonica_regular.otf");

        txt_registers = (TextView)v.findViewById(R.id.txt_registers);
        txt_registers.setTypeface(font);
        txt_registers.setTextColor(getResources().getColor(R.color.azulMovistar));

    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {

        Log.e("json", json.toString());
        try {
            if (json.getBoolean("success")) {
                JSONArray jsonArrayContacts = json.getJSONArray("data");
                RecyclerView.Adapter rvAdapter = new DataBaseElementAdapter(jsonArrayContacts, getActivity());
                recyclerViewDataBase.setAdapter(rvAdapter);
            } else {
                String error = json.getJSONArray("error_message").getString(0);
                new AlertDialog.Builder(getActivity().getBaseContext()).setTitle(R.string.txt_error).setMessage(error).setNeutralButton(R.string.bt_close, null).show();
            }

        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {

    }

}
