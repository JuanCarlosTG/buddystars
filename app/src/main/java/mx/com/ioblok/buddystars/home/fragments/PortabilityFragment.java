package mx.com.ioblok.buddystars.home.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import mx.com.ioblok.buddystars.R;

/**
 * Created by kreativeco on 01/02/16.
 */
public class PortabilityFragment extends Fragment {

    String name_full = " ";
    String name ="" ,lastname = "", phone ="" ,email = " " ,fecha = " ",code_new ="";
    private EditText et_name_full, et_code_operation;


    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_portability, null);

        name = getArguments().getString("name");
        lastname = getArguments().getString("surename");
        phone = getArguments().getString("phone");
        email = getArguments().getString("email");
        fecha = getArguments().getString("schedule");
        code_new = getArguments().getString("code_new");

        name_full = name + " " + lastname;

        Log.e("nombre enviado", name);
        Log.e("apellido enviado" , lastname);
        Log.e("phone enviado" , phone);
        Log.e("email enviado" , email);
        Log.e("fecha enviado" , fecha);
        Log.e("codigo enviado" ,code_new);

        et_name_full = (EditText)v.findViewById(R.id.et_name_full);
        et_name_full.setKeyListener(null);
        et_name_full.setText(name_full);

        et_code_operation = (EditText)v.findViewById(R.id.et_code_operation);


        Button button = (Button) v.findViewById(R.id.btn_send_new);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeData();
            }
        });

        return v;

    }

    public void completeData(){

        final AddDataBaseFragment addDataBaseFragment = new AddDataBaseFragment();

        Bundle data = new Bundle();
        data.putString("name", name);
        data.putString("surename", lastname);
        data.putString("phone", phone);
        data.putString("email", email);
        data.putString("schedule" , fecha);
        data.putString("code_new" , code_new);
        data.putString("code_portability", et_code_operation.getText().toString());
        addDataBaseFragment.setArguments(data);
        getFragmentManager().beginTransaction().replace(R.id.flContent, addDataBaseFragment).commit();
    }

}
