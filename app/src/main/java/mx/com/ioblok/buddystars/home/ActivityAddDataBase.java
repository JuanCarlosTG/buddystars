package mx.com.ioblok.buddystars.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.home.fragments.DiaryFragment;
import mx.com.ioblok.buddystars.home.fragments.PortabilityFragment;
import mx.com.ioblok.buddystars.home.fragments.RegisterFragment;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class ActivityAddDataBase extends Activity implements WebBridge.WebBridgeListener {

    View v;
    String code_portability = "";
    String name ="" ,lastname = "", phone = "" ,email = "" ,fecha = " ", code_new = " ";
    private EditText et_name, et_lastname, et_telephone, et_email, et_diary, et_code_register,et_code_portability,et_alta_sim;
    TextView tv_name, tv_last_name ,tv_code, tv_agen, tv_email, tv_telephone,tv_code_portability;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_data_base);

        Bundle bundle = getIntent().getExtras();

        Typeface font = Typeface.createFromAsset(this.getAssets(), "telefonica_regular.otf");

        if (bundle != null) {
            name = bundle.getString("name");
            lastname = bundle.getString("surename");
            phone = bundle.getString("phone");
            email = bundle.getString("email");
            fecha = bundle.getString("schedule");
            code_new = bundle.getString("code_new");
            code_portability = bundle.getString("code_portability");
        }


        if (name.length()==0 && lastname.length()==0 && phone.length()==0 && email.length()==0 && fecha.length()==0){

            et_name = (EditText)findViewById(R.id.et_name);
            et_name.setTypeface(font);
            et_lastname = (EditText)findViewById(R.id.et_lastname);
            et_lastname.setTypeface(font);
            et_telephone = (EditText)findViewById(R.id.et_telephone);
            et_telephone.setTypeface(font);
            et_email = (EditText)findViewById(R.id.et_email);
            et_email.setTypeface(font);

            et_diary = (EditText)findViewById(R.id.et_diary);
            et_diary.setTypeface(font);
            et_diary.setKeyListener(null);

            et_code_register = (EditText)findViewById(R.id.et_code_register);
            et_code_register.setKeyListener(null);
            et_code_register.setTypeface(font);

            et_code_portability = (EditText) findViewById(R.id.et_code_portability);
            et_code_portability.setKeyListener(null);
            et_code_portability.setTypeface(font);

        } else {
            et_name = (EditText)findViewById(R.id.et_name);
            et_name.setText(name);
            et_name.setTypeface(font);

            et_lastname = (EditText)findViewById(R.id.et_lastname);
            et_lastname.setText(lastname);
            et_lastname.setTypeface(font);

            et_telephone = (EditText)findViewById(R.id.et_telephone);
            et_telephone.setText(phone);
            et_telephone.setTypeface(font);

            et_email = (EditText)findViewById(R.id.et_email);
            et_email.setText(email);
            et_email.setTypeface(font);

            et_diary = (EditText)findViewById(R.id.et_diary);
            et_diary.setKeyListener(null);
            et_diary.setText(fecha);
            et_diary.setTypeface(font);

            et_code_register = (EditText)findViewById(R.id.et_code_register);
            et_code_register.setKeyListener(null);
            et_code_register.setText(code_new);
            et_code_register.setTypeface(font);

            et_code_portability = (EditText) findViewById(R.id.et_code_portability);
            et_code_portability.setKeyListener(null);
            et_code_portability.setText(code_portability);
            et_code_portability.setTypeface(font);
        }

        Button button = (Button) findViewById(R.id.btn_send_reg);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sendRegister();

            }
        });

        EditText et_diary = (EditText) findViewById(R.id.et_diary);
        et_diary.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addDate();
            }
        });


        EditText et_code_register = (EditText) findViewById(R.id.et_code_register);
        et_code_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addAltaCode();
            }
        });

        EditText codePortability = (EditText) findViewById(R.id.et_code_portability);
        codePortability.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                codePortability();
            }
        });
        initialize();
    }

    public void initialize(){

        Typeface font = Typeface.createFromAsset(this.getAssets(), "telefonica_bold.otf");

        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_name.setTypeface(font);

        tv_last_name = (TextView)findViewById(R.id.tv_last_name);
        tv_last_name.setTypeface(font);

        tv_code = (TextView)findViewById(R.id.tv_code);
        tv_code.setTypeface(font);

        tv_agen = (TextView)findViewById(R.id.tv_agen);
        tv_agen.setTypeface(font);

        tv_email = (TextView)findViewById(R.id.tv_email);
        tv_email.setTypeface(font);

        tv_telephone = (TextView)findViewById(R.id.tv_telephone);
        tv_telephone.setTypeface(font);

        tv_code_portability = (TextView)findViewById(R.id.tv_code_portability);
        tv_code_portability.setTypeface(font);


    }

    public void sendRegister(){

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
            new AlertDialog.Builder(this).setTitle(R.string.txt_alert).setMessage(msg.trim()).setNeutralButton(R.string.bt_close, null).show();
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
        params.put("alta_sim",et_alta_sim.getText().toString());

        WebBridge.send("/register-add", params, "Enviando", this, this);
    }

    private void addDate() {
        //final DiaryFragment diaryFragment  = new DiaryFragment();

        Bundle data = new Bundle();
        data.putString("name" , et_name.getText().toString());
        data.putString("surename", et_lastname.getText().toString());
        data.putString("phone", et_telephone.getText().toString());
        data.putString("email", et_email.getText().toString());

        Intent intent = new Intent();
        intent.setClass(ActivityAddDataBase.this,ActivityRegister.class);
        intent.putExtras(data);
        startActivity(intent);
        //diaryFragment.setArguments(data);
        //getFragmentManager().beginTransaction().add(R.id.flContent, diaryFragment).commit();

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
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
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
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
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
