package mx.com.ioblok.buddystars.home.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.utils.Constants;

/**
 * Created by omar on 4/17/16.
 */
public class RegisterSimActivity extends Activity{

    private static final int RESULT_OK = 1;
    String name_full = "", strtext = "", name = "", lastname = "", phone = "", email = " ", fecha = " ",
            cost = "", result_listUser, register_id, type_new = "1", type_amount, ciennueve = "199", trescuatronueve = "349",
            cuatrocuatronueve = "449", cincocuatronueve = "549", sietecuatronueve = "749", nuvenuevenueve = "999";
    Spinner spinner_code;
    Button button_send;
    private EditText et_name_full;
    private EditText et_code_operation;
    ImageButton btnWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_sim);

        name = Constants.getRegisterName();
        lastname = Constants.getRegisterLastName();
        phone = Constants.getRegisterPhone();
        email = Constants.getRegisterMail();
        fecha = Constants.getRegisterSchedule();
        name_full = name + " " + lastname;

        et_name_full = (EditText) findViewById(R.id.et_name_full);
        et_name_full.setText(name_full);

        et_code_operation = (EditText) findViewById(R.id.et_code_operation);

        spinner_code = (Spinner) findViewById(R.id.et_cost);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(RegisterSimActivity.this, R.array.mont_add, android.R.layout.simple_spinner_item);
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

        button_send = (Button) findViewById(R.id.btn_send_new);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeData();
            }
        });

        btnWebView = (ImageButton) findViewById(R.id.btn_trademark);
        btnWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.movistar.com.mx/promociones/cambiate-a-movistar/?utm_source=google_p&utm_medium=sem_desk&utm_content=m_portabilidad_exacta&utm_campaign=product_marca_generica_portabilidad&gclid=CK2S9Obq68sCFQUMaQodciABdQ&gclsrc=aw.ds&dclid=CJLAj-fq68sCFQ90AQodcHUDBA"));
                startActivity(browserIntent);
            }
        });
    }

    public void completeData() {

        Constants.setRegisterCode(et_code_operation.getText().toString());
        finish();

    }

}
