package mx.com.ioblok.buddystars.home.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.home.HomeActivity;
import mx.com.ioblok.buddystars.utils.User;
import mx.com.ioblok.buddystars.utils.WebBridge;

/**
 * Created by kreativeco on 01/02/16.
 */
public class AddDataBaseFragment extends Fragment implements WebBridge.WebBridgeListener {

    View v;
    String vacio = "vacio";
    String code_operation = "" , cost = "";

    private EditText et_name, et_lastname, et_telephone, et_email, et_diary, et_code_register,et_code_portability;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_add_data_base, null);


        //code_operation = getArguments().getString("code_operation");
        //cost = getArguments().getString("cost");

        et_name = (EditText)v.findViewById(R.id.et_name);
        et_lastname = (EditText)v.findViewById(R.id.et_lastname);
        et_telephone = (EditText)v.findViewById(R.id.et_telephone);
        et_email = (EditText)v.findViewById(R.id.et_email);
        et_diary = (EditText)v.findViewById(R.id.et_diary);
        et_code_register = (EditText)v.findViewById(R.id.et_code_register);
        //et_code_register.setText(code_operation);
        et_code_register.setKeyListener(null);
        et_code_portability = (EditText)v.findViewById(R.id.et_code_portability);

        Button button = (Button) v.findViewById(R.id.btn_send_reg);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendRegister();

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

        return v;
    }
    public void sendRegister(){

        HashMap<String, Object> params = new HashMap<>();
        params.put("name", et_name.getText().toString());
        params.put("surename", et_lastname.getText().toString());
        params.put("phone", et_telephone.getText().toString());
        params.put("email", et_email.getText().toString());
        params.put("code_new", et_diary.getText().toString());
        //params.put("code_new_amount", et_code_register.getText().toString());
        params.put("code_portability", et_code_portability.getText().toString());
        params.put("schedule", et_diary.getText().toString());

        WebBridge.send("/register-add", params, "Enviando", getActivity(), this);
    }

    public void addAltaCode(){
        if (et_code_register.getText().toString().trim().length() == 0){

            final RegisterFragment registerFragment = new RegisterFragment();

            Bundle data = new Bundle();
            data.putString("name" , et_name.getText().toString());
            data.putString("lastname", et_lastname.getText().toString());
            data.putString("fromActivity", "Me enviaste desde activity");
            registerFragment.setArguments(data);

            getFragmentManager().beginTransaction().add(R.id.flContent, registerFragment).commit();

        } else {
            Log.e("Contiene algo", et_code_register.getText().toString());
        }
    }


    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("bien" ,json.toString());
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("mal" , response.toString());
    }
}
