package mx.com.ioblok.buddystars.home.fragments;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class PointsFragment extends Fragment implements WebBridge.WebBridgeListener {

    View v;
    TextView tv_total_pts, tv_portability, tv_new;
    ProgressBar pBarNew, pBarPor;
    int progressValue = 0;
    Button stopProgressBtn;

    int pStatus = 0;
    private Handler handler = new Handler();

    ProgressTask task = new ProgressTask();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_points, null);

        tv_total_pts = (TextView) v.findViewById(R.id.tv_total_pts);
        tv_new = (TextView) v.findViewById(R.id.tv_new);
        tv_portability = (TextView) v.findViewById(R.id.tv_portability);

        pBarNew = (ProgressBar) v.findViewById(R.id.progress_new);
        pBarPor = (ProgressBar) v.findViewById(R.id.progress_portability);

        stopProgressBtn = (Button) v.findViewById(R.id.btnCancel);

        showProgress();
        /*
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (pStatus < 100) {
                    pStatus += 1;

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            pBarNew.setProgress(pStatus);
                            pBarPor.setProgress(pStatus);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/

        getData();
        return v;

    }



    public void getData() {
        WebBridge.send("/report-alpha", "Enviando", getActivity(), this);
    }


    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("Bien", json.toString());

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

    private class ProgressTask extends AsyncTask<Integer,Integer,Void>{

        protected void onPreExecute() {
            super.onPreExecute(); ///////???????
            pBarNew.setMax(100); // set maximum progress to 100.
            Log.e("entra aqui" , String.valueOf(progressValue));

        }
        protected void onCancelled() {
            pBarNew.setMax(40); // stop the progress
            Log.v("Progress cancel", String.valueOf(progressValue));
        }

        protected Void doInBackground(Integer... params) {
            int start=params[0];
            for(int i=start;i<=100;i+=1){
                try {
                    boolean cancelled=isCancelled();
                    if (i == 40){
                        stopProgress();
                        Log.e("detente", String.valueOf(i));
                    }else {
                        if (!cancelled) {
                            Log.e("progreso", String.valueOf(i));
                            ///////////////////////////publishProgress(i); ///
                            pBarNew.setProgress(i);
                            Log.v("Progress increment", String.valueOf(i));
                            ///////////////////////////onProgressUpdate(i); ///
                            SystemClock.sleep(500);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
            }
            return null;
        }
        protected void onProgressUpdate(Integer... values) {

            // increment progress bar by progress value
            //////////////////////setProgress(10);
            //////////////////////prgs.setProgress(prgs.getProgress() + 5); // the bar does not fill 100%
            pBarNew.setProgress(1);
            Log.v("Progress","Once");
        }
        protected void onPostExecute(Void result) {
            // async task finished
            Log.v("Progress", "Finished");
        }

    }

    public void showProgress() {
        task.execute(1);
    }

    public void stopProgress() {
        task.cancel(true);
        Log.e("detente", String.valueOf(progressValue));
    }

}
