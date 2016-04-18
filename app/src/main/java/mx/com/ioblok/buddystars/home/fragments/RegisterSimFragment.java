package mx.com.ioblok.buddystars.home.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.home.ListUsers;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class RegisterSimFragment extends Fragment implements WebBridge.WebBridgeListener {

    private static final int RESULT_OK = 1;
    String name_full = "", strtext = "", name = "", lastname = "", phone = "", email = " ", fecha = " ",
            cost = "", result_listUser, register_id, type_new = "3", type_amount, ciennueve = "199", trescuatronueve = "349",
            cuatrocuatronueve = "449", cincocuatronueve = "549", sietecuatronueve = "749", nuvenuevenueve = "999";
    View v;
    Spinner spinner_code;
    Button button_send;
    private EditText et_name_full;
    private EditText et_code_operation;
    TextView tv_cliente, tv_code,tv_spinner;
    ImageButton btnWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_register_sim, null);

        et_name_full = (EditText) v.findViewById(R.id.et_name_full);
        et_code_operation = (EditText) v.findViewById(R.id.et_code_operation);

        spinner_code = (Spinner) v.findViewById(R.id.et_cost);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.mont_add, android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_code.setAdapter(staticAdapter);
        spinner_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                cost = spinner_code.getSelectedItem().toString();
                if (cost.equals(ciennueve)) {
                    type_amount = "0";
                } else if (cost.equals(trescuatronueve)) {
                    type_amount = "1";
                } else if (cost.equals(cuatrocuatronueve)) {
                    type_amount = "2";
                } else if (cost.equals(cincocuatronueve)) {
                    type_amount = "3";
                } else if (cost.equals(sietecuatronueve)) {
                    type_amount = "4";
                } else {
                    type_amount = "5";
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        button_send = (Button) v.findViewById(R.id.btn_send_new);

        et_name_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewUser();
            }
        });
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeData();
            }
        });

        initialize();

        btnWebView = (ImageButton) v.findViewById(R.id.btn_trademark);
        btnWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.movistar.com.mx/promociones/cambiate-a-movistar/?utm_source=google_p&utm_medium=sem_desk&utm_content=m_portabilidad_exacta&utm_campaign=product_marca_generica_portabilidad&gclid=CK2S9Obq68sCFQUMaQodciABdQ&gclsrc=aw.ds&dclid=CJLAj-fq68sCFQ90AQodcHUDBA"));
                startActivity(browserIntent);
            }
        });


        return v;

    }

    public void initialize(){

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "telefonica_bold.otf");

        tv_cliente = (TextView)v.findViewById(R.id.tv_cliente);
        tv_cliente.setTypeface(font);

        tv_code = (TextView)v.findViewById(R.id.tv_code);
        tv_code.setTypeface(font);

        tv_spinner = (TextView)v.findViewById(R.id.tv_spinner);
        tv_spinner.setTypeface(font);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                result_listUser = data.getStringExtra("list_name_user");
                register_id = data.getStringExtra("register_id");

                et_name_full = (EditText) v.findViewById(R.id.et_name_full);
                et_name_full.setKeyListener(null);
                et_name_full.setText(result_listUser);

            }
        }
    }

    public void addNewUser() {
        if (et_name_full.getText().toString().trim().length() == 0) {
            Intent intent = new Intent(getActivity(), ListUsers.class);
            startActivityForResult(intent, 1);
        } else {
            Log.e("tiene algo", et_name_full.getText().toString());
        }

    }

    public void completeData() {
        sendData();
    }

    public void sendData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("register_id", register_id);
        params.put("code", et_code_operation.getText().toString());
        params.put("type", type_new);
        params.put("amount", type_amount);

        WebBridge.send("/register-code", params, "Enviando", getActivity(), this);
    }

    public void exitSendData() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
        dialogo1.setTitle(R.string.title_gracias);
        dialogo1.setMessage(R.string.codigo_exitoso);
        dialogo1.setNeutralButton(R.string.cerrar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                et_name_full.setText("");
                et_code_operation.setText("");
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
}