package mx.com.ioblok.buddystars.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.customviews.CustomTextViewRegular;
import mx.com.ioblok.buddystars.home.HomeActivity;

/**
 * Created by kreativeco on 22/02/16.
 */
public class AlphaReportElementAdapter extends RecyclerView.Adapter<AlphaReportElementAdapter.ReportsViewHolder> {

    private JSONArray jsonArrayReports;

    Activity activity;
    private static Context context;
    private static Activity homeActivity;

    public static class ReportsViewHolder extends RecyclerView.ViewHolder {

        public CustomTextViewRegular textViewReportName;
        public CustomTextViewRegular textViewReportPercent;
        public ImageView imageViewAvatar;

        public ReportsViewHolder(View itemView) {
            super(itemView);

            CardView cardViewItem = (CardView) itemView;
            cardViewItem.setCardBackgroundColor(Color.argb(0, 255, 255, 255));

            textViewReportPercent = (CustomTextViewRegular) cardViewItem.findViewById(R.id.tv_percent_point);
            textViewReportName = (CustomTextViewRegular) cardViewItem.findViewById(R.id.tv_full_name);
            imageViewAvatar = (ImageView) cardViewItem.findViewById(R.id.iv_avatar);

        }

    }

    public AlphaReportElementAdapter(JSONArray reports, Activity activity) {
        this.jsonArrayReports = reports;
        this.activity = activity;
        homeActivity = activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public ReportsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View reportView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ui_buddies, parent, false);
        return new ReportsViewHolder(reportView);
    }

    @Override
    public void onBindViewHolder(final ReportsViewHolder holder, int position) {

        try {
            JSONObject reports = jsonArrayReports.getJSONObject(position);

            String strAvatar = reports.getString("avatar");
            String strFullName = reports.getString("full_name");
            String strPercent = reports.getString("percent_total");

            holder.textViewReportName.setText(strFullName);
            holder.textViewReportPercent.setText(strPercent);
            Glide.with(activity).load(strAvatar).into(holder.imageViewAvatar);

            Glide.with(activity).load(strAvatar).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.imageViewAvatar) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.imageViewAvatar.setImageDrawable(circularBitmapDrawable);
                }
            });

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return jsonArrayReports.length();
    }

}
