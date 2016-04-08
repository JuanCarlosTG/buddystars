package mx.com.ioblok.buddystars.home.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import mx.com.ioblok.buddystars.R;

/**
 * Created by kreativeco on 01/02/16.
 */
public class RegisterFragment extends Fragment {
    String vacio = "vacio" , name_full;
    String strtext = "" , name ="" ,lastname = "";
    View v;

    private EditText et_name_full, et_code_operation, et_cost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_register, null);

        name = getArguments().getString("name");
        lastname = getArguments().getString("lastname");
        strtext = getArguments().getString("fromActivity");
        name_full = name + lastname;

        et_name_full = (EditText)v.findViewById(R.id.et_name_full);
        et_name_full.setKeyListener(null);
        et_name_full.setText(name_full);
        et_code_operation = (EditText)v.findViewById(R.id.et_code_operation);
        et_cost = (EditText)v.findViewById(R.id.et_cost);


        Button button = (Button) v.findViewById(R.id.btn_send_new);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                completeData();

            }
        });

        return v;

    }

    public void completeData(){

        final AddDataBaseFragment addDataBaseFragment = new AddDataBaseFragment();

        Bundle data = new Bundle();
        data.putString("code_operation" , et_code_operation.getText().toString());
        data.putString("cost" , et_cost.getText().toString());
        addDataBaseFragment.setArguments(data);
        getFragmentManager().beginTransaction().replace(R.id.flContent, addDataBaseFragment).commit();
    }

}
