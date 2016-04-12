package mx.com.ioblok.buddystars.home.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONObject;

import java.util.HashMap;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class AddDataBaseFragment extends Fragment implements WebBridge.WebBridgeListener {

    View v;
    String code_portability = "";
    String name ="" ,lastname = "", phone = "" ,email = "" ,fecha = " ", code_new = " ";
    private EditText et_name, et_lastname, et_telephone, et_email, et_diary, et_code_register,et_code_portability;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_add_data_base, null);


        Bundle bundle = getArguments();

        if (bundle != null) {
            name = getArguments().getString("name");
            lastname = getArguments().getString("surename");
            phone = getArguments().getString("phone");
            email = getArguments().getString("email");
            fecha = getArguments().getString("schedule");
            code_new = getArguments().getString("code_new");
            code_portability = getArguments().getString("code_portability");
        }


        if (name.length()==0 && lastname.length()==0 && phone.length()==0 && email.length()==0 && fecha.length()==0){

            et_name = (EditText)v.findViewById(R.id.et_name);
            et_lastname = (EditText)v.findViewById(R.id.et_lastname);
            et_telephone = (EditText)v.findViewById(R.id.et_telephone);
            et_email = (EditText)v.findViewById(R.id.et_email);

            et_diary = (EditText)v.findViewById(R.id.et_diary);
            et_diary.setKeyListener(null);

            et_code_register = (EditText)v.findViewById(R.id.et_code_register);
            et_code_register.setKeyListener(null);

            et_code_portability = (EditText)v.findViewById(R.id.et_code_portability);
            et_code_portability.setKeyListener(null);

        } else {
            et_name = (EditText)v.findViewById(R.id.et_name);
            et_name.setText(name);
            et_lastname = (EditText)v.findViewById(R.id.et_lastname);
            et_lastname.setText(lastname);
            et_telephone = (EditText)v.findViewById(R.id.et_telephone);
            et_telephone.setText(phone);
            et_email = (EditText)v.findViewById(R.id.et_email);
            et_email.setText(email);

            et_diary = (EditText)v.findViewById(R.id.et_diary);
            et_diary.setKeyListener(null);
            et_diary.setText(fecha);

            et_code_register = (EditText)v.findViewById(R.id.et_code_register);
            et_code_register.setKeyListener(null);
            et_code_register.setText(code_new);

            et_code_portability = (EditText)v.findViewById(R.id.et_code_portability);
            et_code_portability.setKeyListener(null);
            et_code_portability.setText(code_portability);
        }

        Button button = (Button) v.findViewById(R.id.btn_send_reg);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendRegister();

            }
        });

        EditText et_diary = (EditText) v.findViewById(R.id.et_diary);
        et_diary.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addDate();
            }
        });


        EditText et_code_register = (EditText) v.findViewById(R.id.et_code_register);
        et_code_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addAltaCode();
            }
        });

        EditText codePortability = (EditText) v.findViewById(R.id.et_code_portability);
        codePortability.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                codePortability();
            }
        });

        return v;
    }


    public void sendRegister(){

        HashMap<String, Object> params = new HashMap<>();
        params.put("name", et_name.getText().toString());
        params.put("surename", et_lastname.getText().toString());
        params.put("phone", et_telephone.getText().toString());
        params.put("email", et_email.getText().toString());
        params.put("schedule", et_diary.getText().toString());
        params.put("code_new", et_code_register.getText().toString());
        params.put("code_portability", et_code_portability.getText().toString());

        WebBridge.send("/register-add", params, "Enviando", getActivity(), this);
    }

    private void addDate() {
        final DiaryFragment diaryFragment  = new DiaryFragment();

        Bundle data = new Bundle();
        data.putString("name" , et_name.getText().toString());
        data.putString("surename", et_lastname.getText().toString());
        data.putString("phone", et_telephone.getText().toString());
        data.putString("email", et_email.getText().toString());

        diaryFragment.setArguments(data);
        getFragmentManager().beginTransaction().add(R.id.flContent, diaryFragment).commit();

    }

    public void addAltaCode(){
        if (et_code_register.getText().toString().trim().length() == 0){

            final RegisterFragment registerFragment = new RegisterFragment();

            Bundle data = new Bundle();
            data.putString("name" , et_name.getText().toString());
            data.putString("surename", et_lastname.getText().toString());
            data.putString("phone", et_telephone.getText().toString());
            data.putString("email", et_email.getText().toString());
            data.putString("schedule", et_diary.getText().toString());
            registerFragment.setArguments(data);

            getFragmentManager().beginTransaction().add(R.id.flContent, registerFragment).commit();

        } else {
            Log.e("Contiene algo", et_code_register.getText().toString());
        }
    }


    public void codePortability(){
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

        Log.e("entra en editar" , code_new);

        final PortabilityFragment portabilityFragment = new PortabilityFragment();

        Bundle data = new Bundle();
        data.putString("name" , name);
        data.putString("surename", lastname);
        data.putString("phone", phone);
        data.putString("email", email);
        data.putString("schedule", fecha);
        data.putString("code_new", code_new);
        portabilityFragment.setArguments(data);
        getFragmentManager().beginTransaction().add(R.id.flContent, portabilityFragment).commit();
    }

    public void Eliminar(){
        et_code_portability = (EditText)v.findViewById(R.id.et_code_portability);
        et_code_portability.setKeyListener(null);
        et_code_portability.setText("");
    }

    public void Cancelar(){

    }

    public void exitSendData(){
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
        Log.e("mal" , response);
    }
}
