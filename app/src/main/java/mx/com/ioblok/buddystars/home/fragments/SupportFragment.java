package mx.com.ioblok.buddystars.home.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.home.HomeActivity;
import mx.com.ioblok.buddystars.utils.PermissionUtils;

public class SupportFragment extends Fragment {

    View v;
    Button btn_call, btn_mail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_support, null);

        btn_call = (Button) v.findViewById(R.id.btn_call);
        btn_mail = (Button) v.findViewById(R.id.btn_mail);

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
                //call();

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

    /*@Override
    public void onResume() {
        super.onResume();
        if (mShowPermissionDeniedDialog) {
            PermissionUtils.PermissionDeniedDialog.newInstance(false).show(getFragmentManager(), "dialog");
            mShowPermissionDeniedDialog = false;
        }
    }*/


