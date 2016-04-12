package mx.com.ioblok.buddystars.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;

/**
 * Created by kreativeco on 22/02/16.
 */
public class DiaryElementAdapter extends RecyclerView.Adapter<DiaryElementAdapter.ContactsViewHolder> {

    private JSONArray jsonArrayContacts;
    Activity activity;
    private static Context context;

    public static class ContactsViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewContactName;
        public TextView textViewTime;

        public ContactsViewHolder(View itemView) {
            super(itemView);

            CardView cardViewItem = (CardView) itemView;
            cardViewItem.setCardBackgroundColor(Color.argb(0, 255, 255, 255));

            textViewTime = (TextView) cardViewItem.findViewById(R.id.tv_time);
            textViewContactName = (TextView) cardViewItem.findViewById(R.id.tv_contact_name);

            /*btnContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenuActions(v);
                }
            });*/
        }

        public void showMenuActions(View v){

        }

    }

    public DiaryElementAdapter(JSONArray contacts, Activity activity) {
        this.jsonArrayContacts = contacts;
        this.activity = activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_diary, parent, false);
        return new ContactsViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, int position) {

        try {
            JSONObject contacts = jsonArrayContacts.getJSONObject(position);

            String contactName = contacts.getString("name");
            String contactPhone = contacts.getString("phone");
            String contactEMail = contacts.getString("email");
            String contactSchedule = contacts.getString("schedule");

            /*photoString = photoString.equals("null") ? "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg" : photoString;

            Glide.with(activity).load(photoString).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.farmerImage) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.farmerImage.setImageDrawable(circularBitmapDrawable);
                }
            });*/

            holder.textViewContactName.setText(contactName);
            String [] strSplit = contactSchedule.split(" ");
            Log.e("splt 1", strSplit[0]);
            Log.e("splt 2", strSplit[1]);
            String strHour = strSplit[1];

            holder.textViewTime.setText(strHour);

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return jsonArrayContacts.length();
    }

    private void showMenuActions(){
    }

}
