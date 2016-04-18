package mx.com.ioblok.buddystars.home.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.customviews.CustomButtonRegular;
import mx.com.ioblok.buddystars.customviews.CustomEditTextRegular;
import mx.com.ioblok.buddystars.home.activities.DiaryActivity;
import mx.com.ioblok.buddystars.home.activities.PortabilityActivity;
import mx.com.ioblok.buddystars.home.activities.RegisterActivity;
import mx.com.ioblok.buddystars.home.activities.RegisterSimActivity;
import mx.com.ioblok.buddystars.utils.Constants;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class AddDataBaseFragment extends Fragment implements WebBridge.WebBridgeListener {

    View v;
    private CustomEditTextRegular et_name, et_lastname, et_telephone, et_email, et_diary, et_code_register, et_code_portability, et_alta_sim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_add_data_base, null);

        et_name = (CustomEditTextRegular) v.findViewById(R.id.et_name);
        et_lastname = (CustomEditTextRegular) v.findViewById(R.id.et_lastname);
        et_telephone = (CustomEditTextRegular) v.findViewById(R.id.et_telephone);
        et_email = (CustomEditTextRegular) v.findViewById(R.id.et_email);
        et_diary = (CustomEditTextRegular) v.findViewById(R.id.et_diary);
        et_code_register = (CustomEditTextRegular) v.findViewById(R.id.et_code_register);
        et_code_portability = (CustomEditTextRegular) v.findViewById(R.id.et_code_portability);
        et_alta_sim = (CustomEditTextRegular)v.findViewById(R.id.et_alta_sim);

        Button button = (CustomButtonRegular) v.findViewById(R.id.btn_send_reg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRegister();

            }
        });

        et_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDate();
            }
        });


        et_code_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAltaCode();
            }
        });
        et_alta_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAltaSim();
            }
        });
        et_code_portability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codePortability();
            }
        });

        return v;
    }


    public void sendRegister() {

        ArrayList<String> errors = new ArrayList<String>();
        if (et_name == null || et_name.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_name));
        if (et_lastname == null || et_lastname.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_last_name));
        if (et_email == null || et_email.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_mail));

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "* " + s + "\n";
            }
            new AlertDialog.Builder(getActivity()).setTitle(R.string.txt_alert).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }


        HashMap<String, Object> params = new HashMap<>();
        params.put("name", et_name.getText().toString());
        params.put("surename", et_lastname.getText().toString());
        params.put("phone", et_telephone.getText().toString());
        params.put("email", et_email.getText().toString());
        params.put("schedule", et_diary.getText().toString());
        params.put("code_new", et_code_register.getText().toString());
        params.put("code_portability", et_code_portability.getText().toString());
        params.put("alta_chip", et_alta_sim.getText().toString());

        WebBridge.send("/register-add", params, "Enviando", getActivity(), this);
    }

    private void addDate() {

        Intent diaryIntent = new Intent(getActivity(), DiaryActivity.class);

        ArrayList<String> errors = new ArrayList<String>();
        if (et_name == null || et_name.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_name));
        if (et_lastname == null || et_lastname.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_last_name));
        if (et_email == null || et_email.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_mail));
        if (et_telephone == null || et_telephone.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_phone));

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "* " + s + "\n";
            }
            new AlertDialog.Builder(getActivity()).setTitle(R.string.txt_alert).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        Constants.setRegisterName(et_name.getText().toString());
        Constants.setRegisterLastName(et_lastname.getText().toString());
        Constants.setRegisterPhone(et_telephone.getText().toString());
        Constants.setRegisterMail(et_email.getText().toString());

        startActivity(diaryIntent);

    }

    public void addAltaCode() {

        Intent registerIntent = new Intent(getActivity(), RegisterActivity.class);

        ArrayList<String> errors = new ArrayList<String>();
        if (et_name == null || et_name.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_name));
        if (et_lastname == null || et_lastname.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_last_name));
        if (et_email == null || et_email.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_mail));
        if (et_telephone == null || et_telephone.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_phone));
        if (et_diary == null || et_diary.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_diary));

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "* " + s + "\n";
            }
            new AlertDialog.Builder(getActivity()).setTitle(R.string.txt_alert).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        Constants.setRegisterName(et_name.getText().toString());
        Constants.setRegisterLastName(et_lastname.getText().toString());
        Constants.setRegisterPhone(et_telephone.getText().toString());
        Constants.setRegisterMail(et_email.getText().toString());
        Constants.setRegisterSchedule(et_diary.getText().toString());

        startActivity(registerIntent);
    }

    public void addAltaSim() {

        Intent registerIntent = new Intent(getActivity(), RegisterSimActivity.class);

        ArrayList<String> errors = new ArrayList<String>();
        if (et_name == null || et_name.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_name));
        if (et_lastname == null || et_lastname.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_last_name));
        if (et_email == null || et_email.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_mail));
        if (et_telephone == null || et_telephone.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_phone));
        if (et_diary == null || et_diary.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_diary));

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "* " + s + "\n";
            }
            new AlertDialog.Builder(getActivity()).setTitle(R.string.txt_alert).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        Constants.setRegisterName(et_name.getText().toString());
        Constants.setRegisterLastName(et_lastname.getText().toString());
        Constants.setRegisterPhone(et_telephone.getText().toString());
        Constants.setRegisterMail(et_email.getText().toString());
        Constants.setRegisterSchedule(et_diary.getText().toString());

        startActivity(registerIntent);
    }


    public void codePortability() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
        dialogo1.setTitle("Selecciona una opción");
        dialogo1.setCancelable(false);
        dialogo1.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Eliminar();
            }
        });
        dialogo1.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Cancelar();
            }
        });
        dialogo1.setNeutralButton("Editar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Editar();
            }
        });
        dialogo1.show();
    }


    public void Editar() {

        Intent portabilityIntent = new Intent(getActivity(), PortabilityActivity.class);


        ArrayList<String> errors = new ArrayList<String>();
        if (et_name == null || et_name.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_name));
        if (et_lastname == null || et_lastname.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_last_name));
        if (et_email == null || et_email.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_mail));
        if (et_telephone == null || et_telephone.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_phone));
        if (et_diary == null || et_diary.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_diary));
        if (et_code_register == null || et_code_register.getText().toString().equals(""))
            errors.add(getString(R.string.txt_error_code));

        if (errors.size() != 0) {
            String msg = "";
            for (String s : errors) {
                msg += "* " + s + "\n";
            }
            new AlertDialog.Builder(getActivity()).setTitle(R.string.txt_alert).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
            return;
        }

        Constants.setRegisterName(et_name.getText().toString());
        Constants.setRegisterLastName(et_lastname.getText().toString());
        Constants.setRegisterPhone(et_telephone.getText().toString());
        Constants.setRegisterMail(et_email.getText().toString());
        Constants.setRegisterSchedule(et_diary.getText().toString());
        Constants.setRegisterCode(et_code_register.getText().toString());

        startActivity(portabilityIntent);
    }

    public void Eliminar() {
        et_code_portability = (CustomEditTextRegular) v.findViewById(R.id.et_code_portability);
        et_code_portability.setKeyListener(null);
        et_code_portability.setText("");
    }

    public void Cancelar() {

    }

    public void exitSendData() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
        dialogo1.setTitle(R.string.title_gracias);
        dialogo1.setMessage(R.string.cliente_exitoso);
        dialogo1.setNeutralButton(R.string.cerrar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                et_name.setText("");
                et_lastname.setText("");
                et_telephone.setText("");
                et_email.setText("");
                et_diary.setText("");
                et_code_register.setText("");
                et_code_portability.setText("");
                et_alta_sim.setText("");

                Constants.clear();
            }
        });
        dialogo1.show();
    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        exitSendData();
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("mal", response);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onPause", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        et_name.setText(Constants.getRegisterName());
        et_lastname.setText(Constants.getRegisterLastName());
        et_telephone.setText(Constants.getRegisterPhone());
        et_email.setText(Constants.getRegisterMail());
        et_diary.setText(Constants.getRegisterSchedule());
        et_code_register.setText(Constants.getRegisterCode());
        et_code_portability.setText(Constants.getRegisterPortability());
        et_alta_sim.setText(Constants.getRegisterSim());
    }
}
