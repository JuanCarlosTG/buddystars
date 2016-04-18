package mx.com.ioblok.buddystars.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import mx.com.ioblok.buddystars.customviews.CustomButtonRegular;
import mx.com.ioblok.buddystars.customviews.CustomTextViewRegular;
import mx.com.ioblok.buddystars.home.HomeActivity;

/**
 * Created by kreativeco on 22/02/16.
 */
public class DataBaseElementAdapter extends RecyclerView.Adapter<DataBaseElementAdapter.ContactsViewHolder> {

    private JSONArray jsonArrayContacts;
    Activity activity;
    private static Context context;
    private static Activity homeActivity;

    public static class ContactsViewHolder extends RecyclerView.ViewHolder {

        public CustomTextViewRegular textViewContactName;
        public CustomButtonRegular btnContact;
        public String contactPhone;
        public String contactMail;

        public ContactsViewHolder(View itemView) {
            super(itemView);

            CardView cardViewItem = (CardView) itemView;
            cardViewItem.setCardBackgroundColor(Color.argb(255, 255, 255, 255));

            btnContact = (CustomButtonRegular) cardViewItem.findViewById(R.id.btn_contact);
            textViewContactName = (CustomTextViewRegular) cardViewItem.findViewById(R.id.tv_contact_name);

            btnContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenuActions(contactPhone);
                }
            });
        }

        public void makeCall(String phone){
            ((HomeActivity) homeActivity).setNumberCall(phone);
            ((HomeActivity) homeActivity).askForPermissions();
        }

        public void showMenuActions(final String phone){

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(homeActivity);
            dialogo1.setTitle("¿Cómo quieres contactar a "+ textViewContactName.getText().toString() + "?");
            dialogo1.setCancelable(false);
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                }
            });
            dialogo1.setPositiveButton("Llamar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    makeCall(phone);
                }
            });
            dialogo1.setNeutralButton("Correo", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{contactMail});
                    try {
                        homeActivity.startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(homeActivity, "¡Ups! No hay un correo electrónico registrado.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialogo1.show();
        }

    }

    public DataBaseElementAdapter(JSONArray contacts, Activity activity) {
        this.jsonArrayContacts = contacts;
        this.activity = activity;
        homeActivity = activity;
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
