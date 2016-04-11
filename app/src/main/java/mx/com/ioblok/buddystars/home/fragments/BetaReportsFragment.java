package mx.com.ioblok.buddystars.home.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONObject;

import java.util.ArrayList;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class BetaReportsFragment extends Fragment implements WebBridge.WebBridgeListener {

    View v;
    TextView new_container, new_portability, tv_required_person;
    private LinearLayout mainLayout;
    private PieChart mChart;
    PieChart pieChart;
    String percent_total, percent_point;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_beta_reports, null);

        new_container = (TextView) v.findViewById(R.id.new_container);
        new_portability = (TextView) v.findViewById(R.id.new_portability);
        tv_required_person = (TextView) v.findViewById(R.id.tv_required_person);

        getData();
        return v;

    }

    public void getData() {
        WebBridge.send("/report-beta", "Enviando", getActivity(), this);
    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("Bien", json.toString());
        try {

            JSONObject main = json.getJSONObject("data");
            String e = main.getString("count_new");
            new_container.setText(e);
            new_container.setTextColor(getResources().getColor(R.color.azulMovistar));
            String f = main.getString("count_portability");
            new_portability.setText(f);
            new_portability.setTextColor(getResources().getColor(R.color.azulMovistar));
            String r = main.getString("required_total");
            tv_required_person.setText("Te faltan " + r + " personas para llegar a la meta");
            percent_total = main.getString("percent_total");
            percent_point = main.getString("percent_point");

            getCircle(percent_total,percent_point);

        } catch (Exception e) {
            Log.e("Exception Wey", e.toString());
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("Mal", response);
    }

    public void getCircle(String percent_point, String percent_total){

        String z = percent_point.substring(0,2);
        String y = percent_point.substring(0,2);
        final int p = Integer.parseInt(z);
        final int q = Integer.parseInt(y);

        pieChart = (PieChart)v.findViewById(R.id.chart);
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(p, 0));
        entries.add(new Entry(q, 1));

        PieDataSet dataset = new PieDataSet(entries, null);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add(percent_total);
        labels.add(percent_point);


        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c: ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c: ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        PieData data = new PieData(labels, dataset); // initialize Piedata
        dataset.setColors(new int[]{R.color.azulMovistar, R.color.colorPrimaryDark}, getActivity());
        pieChart.setData(data);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(15);
        pieChart.animateY(5000);
        pieChart.setHoleRadius(50);
        pieChart.setDescription("");
        dataset.setDrawValues(false);
        pieChart.getLegend().setEnabled(false);
    }

}
