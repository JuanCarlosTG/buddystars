package mx.com.ioblok.buddystars.home.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.customviews.CustomButtonRegular;
import mx.com.ioblok.buddystars.customviews.CustomEditTextRegular;
import mx.com.ioblok.buddystars.home.HomeActivity;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class SupportFragment extends Fragment implements WebBridge.WebBridgeListener{

    View v;
    CustomButtonRegular btn_call, btn_mail;
    Dialog sendEMailDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_support, null);

        btn_call = (CustomButtonRegular) v.findViewById(R.id.btn_call);
        btn_mail = (CustomButtonRegular) v.findViewById(R.id.btn_mail);

        btn_call.setOnClickListener(action);
        btn_mail.setOnClickListener(action);

        return v;

    }

    View.OnClickListener action = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_call:
                    callSupport();
                    break;

                case R.id.btn_mail:
                    sendMail();
                    break;
            }
        }
    };

    public void callSupport() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
        dialogo1.setTitle(R.string.confirmar_llamada);
        dialogo1.setPositiveButton(R.string.llamar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                ((HomeActivity) getActivity()).setNumberCall("5550015821");
                ((HomeActivity) getActivity()).askForPermissions();                //askForPermissions();
            }
        });

        dialogo1.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
            }
        });
        dialogo1.show();
    }


    public void sendMail() {

        sendEMailDialog = new Dialog(getActivity());
        sendEMailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sendEMailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        sendEMailDialog.setContentView(R.layout.dialog_send_e_mail);
        sendEMailDialog.show();

        CustomButtonRegular btnClose = (CustomButtonRegular) sendEMailDialog.findViewById(R.id.btn_close_dialog);
        final CustomEditTextRegular txtComment = (CustomEditTextRegular) sendEMailDialog.findViewById(R.id.txt_comment);
        CustomButtonRegular btnSendComment = (CustomButtonRegular) sendEMailDialog.findViewById(R.id.btn_send_comment);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEMailDialog.dismiss();
            }
        });

        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment(txtComment);
            }
        });

    }

    public void sendComment(CustomEditTextRegular txtComment){
        ArrayList<String> errors = new ArrayList<String>();
        if (txtComment.getText().toString().length() < 10 || txtComment.getText().toString().equals(""))
            errors.add(getString(R.string.txt_support_error));

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "* " + s + "\n";
            }
            new AlertDialog.Builder(getActivity()).setTitle(R.string.txt_alert).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("question", txtComment.getText().toString());

        WebBridge.send("/contact", params, "Enviando", getActivity(), this);
    }


    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("JSON", json.toString());
        try {
            if(json.getBoolean("success")){
                sendEMailDialog.dismiss();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {

    }
}


