package mx.com.ioblok.buddystars.home.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import mx.com.ioblok.buddystars.R;

/**
 * Created by kreativeco on 01/02/16.
 */
public class RegisterFragment extends Fragment {

    String name_full = "", strtext = "", name = "", lastname = "", phone = "", email = " ", fecha = " ", cost = "" , vacio ="vacio";
    View v;
    Spinner spinner_code;
    private EditText et_name_full, et_code_operation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_register, null);

        Bundle bundle = getArguments();

        if (bundle != null) {

            name = getArguments().getString("name");
            lastname = getArguments().getString("surename");
            phone = getArguments().getString("phone");
            email = getArguments().getString("email");
            fecha = getArguments().getString("schedule");
            strtext = getArguments().getString("fromActivity");
            name_full = name + " " + lastname;
        }

        if (name.length() == 0) {
            et_name_full = (EditText) v.findViewById(R.id.et_name_full);
            et_code_operation = (EditText) v.findViewById(R.id.et_code_operation);
            spinner_code = (Spinner) v.findViewById(R.id.et_cost);
            // Create an ArrayAdapter using the string array and a default spinner
            ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(getContext(), R.array.mont_add, android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spinner_code.setAdapter(staticAdapter);
            cost = spinner_code.getSelectedItem().toString();
        }

        et_name_full = (EditText) v.findViewById(R.id.et_name_full);
        et_name_full.setKeyListener(null);
        et_name_full.setText(name_full);

        et_code_operation = (EditText) v.findViewById(R.id.et_code_operation);

        spinner_code = (Spinner) v.findViewById(R.id.et_cost);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(getContext(), R.array.mont_add, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_code.setAdapter(staticAdapter);
        cost = spinner_code.getSelectedItem().toString();

        EditText et_code_register = (EditText) v.findViewById(R.id.et_name_full);
        et_code_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addNewUser();
            }
        });

        Button button = (Button) v.findViewById(R.id.btn_send_new);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeData();

            }
        });


        return v;

    }

    public void addNewUser(){
        if (et_name_full.getText().toString().trim().length() == 0){
            Log.e("vacio",vacio);
        }else
        {
            Log.e("tiene algo" ,et_name_full.getText().toString());
        }

    }
    public void completeData() {

        final AddDataBaseFragment addDataBaseFragment = new AddDataBaseFragment();

        Bundle data = new Bundle();
        data.putString("name", name);
        data.putString("surename", lastname);
        data.putString("phone", phone);
        data.putString("email", email);
        data.putString("schedule", fecha);
        data.putString("code_new", et_code_operation.getText().toString());
        data.putString("cost", cost);
        addDataBaseFragment.setArguments(data);
        getFragmentManager().beginTransaction().replace(R.id.flContent, addDataBaseFragment).commit();
    }

}
