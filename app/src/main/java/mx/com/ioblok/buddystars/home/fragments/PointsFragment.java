package mx.com.ioblok.buddystars.home.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class PointsFragment extends Fragment implements WebBridge.WebBridgeListener {

    View v;
    TextView tv_total_pts, tv_portability, tv_new;
    ProgressBar pBarNew, pBarPor;
    private ProgressTask task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        task = new ProgressTask();

        v = inflater.inflate(R.layout.fragment_points, null);

        tv_total_pts = (TextView) v.findViewById(R.id.tv_total_pts);
        tv_new = (TextView) v.findViewById(R.id.tv_new);
        tv_portability = (TextView) v.findViewById(R.id.tv_portability);

        pBarNew = (ProgressBar) v.findViewById(R.id.progress_new);
        pBarPor = (ProgressBar) v.findViewById(R.id.progress_portability);

        showProgress();

        getData();
        return v;

    }


    public void getData() {
        WebBridge.send("/report-alpha", "Enviando", getActivity(), this);
    }


    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        try {

            JSONObject main = json.getJSONObject("data");
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
        Log.e("Mal", response);
    }


    private class ProgressTask extends AsyncTask<Integer, Integer, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            pBarNew.setMax(100);
            pBarPor.setMax(100);

        }

        protected void onCancelled() {
            Log.v("Progress", "Cancelled");
        }

        protected Void doInBackground(Integer... params) {
            int start = params[0];
            for (int i = start; i <= 100; i += 1) {
                try {
                    boolean cancelled = isCancelled();

                    if (!cancelled) {
                        Log.e("for con valor de i ", String.valueOf(i));
                        if (i == 15){
                            task.cancel(true);
                            pBarPor.setMax(20);
                            pBarNew.setMax(60);
                        }
                        pBarNew.setProgress(i);
                        pBarPor.setProgress(i);
                        SystemClock.sleep(80);
                    }

                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
            }
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            pBarNew.setProgress(1);
            pBarPor.setProgress(1);
        }

        protected void onPostExecute(Void result) {
            Log.v("Progress", "Finished");
        }

    }

    public void showProgress() {
        task.execute(1);
    }

}
