package mx.com.ioblok.buddystars.home.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.customviews.CustomButtonRegular;
import mx.com.ioblok.buddystars.home.HomeActivity;

public class SupportFragment extends Fragment {

    View v;
    CustomButtonRegular btn_call, btn_mail;


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
                ((HomeActivity) getActivity()).setNumberCall("56789045");
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
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
        dialogo1.setTitle(R.string.confirmar_mail);
        dialogo1.setPositiveButton(R.string.Escribir, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
            }
        });

        dialogo1.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
            }
        });
        dialogo1.show();
    }


}


