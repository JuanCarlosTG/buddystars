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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;

/**
 * Created by kreativeco on 22/02/16.
 */
public class DataBaseElementAdapter extends RecyclerView.Adapter<DataBaseElementAdapter.ContactsViewHolder> {

    private JSONArray jsonArrayContacts;
    Activity activity;
    private static Context context;

    public static class ContactsViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewContactName;
        public Button btnContact;
        public String contactPhone;
        public String contactMail;

        public ContactsViewHolder(View itemView) {
            super(itemView);

            CardView cardViewItem = (CardView) itemView;
            cardViewItem.setCardBackgroundColor(Color.argb(255, 255, 255, 255));

            btnContact = (Button) cardViewItem.findViewById(R.id.btn_contact);
            textViewContactName = (TextView) cardViewItem.findViewById(R.id.tv_contact_name);

            btnContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenuActions(contactPhone);
                }
            });
        }

        public void showMenuActions(String phone){
            Toast.makeText(context, phone, Toast.LENGTH_LONG).show();
        }

    }

    public DataBaseElementAdapter(JSONArray contacts, Activity activity) {
        this.jsonArrayContacts = contacts;
        this.activity = activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_data_base, parent, false);
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

            holder.textViewContactName.setText(contactName);
            holder.contactPhone = contactPhone;
            holder.contactMail = contactEMail;

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return jsonArrayContacts.length();
    }


}
