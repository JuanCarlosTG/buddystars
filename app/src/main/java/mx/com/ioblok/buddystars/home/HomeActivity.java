package mx.com.ioblok.buddystars.home;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.BoolRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.SectionActivity;
import mx.com.ioblok.buddystars.adapter.CustomMenuAdapter;
import mx.com.ioblok.buddystars.customviews.CustomTextViewRegular;
import mx.com.ioblok.buddystars.home.fragments.AddDataBaseFragment;
import mx.com.ioblok.buddystars.home.fragments.BetaReportsFragment;
import mx.com.ioblok.buddystars.home.fragments.AlphaReportsFragment;
import mx.com.ioblok.buddystars.home.fragments.DataBaseFragment;
import mx.com.ioblok.buddystars.home.fragments.DiaryFragment;
import mx.com.ioblok.buddystars.home.fragments.PointsFragment;
import mx.com.ioblok.buddystars.home.fragments.PortabilityFragment;
import mx.com.ioblok.buddystars.home.fragments.RegisterFragment;
import mx.com.ioblok.buddystars.home.fragments.RegisterSimFragment;
import mx.com.ioblok.buddystars.home.fragments.SupportFragment;
import mx.com.ioblok.buddystars.utils.Constants;
import mx.com.ioblok.buddystars.utils.PermissionUtils;
import mx.com.ioblok.buddystars.utils.User;

public class HomeActivity extends SectionActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    public ImageButton btnMenu;
    public ImageView imageHeaderList;
    public CustomTextViewRegular txtHeaderList;

    private DrawerLayout mDrawer;
    private ListView mDrawerOptions;
    CustomMenuAdapter adapterActivity;
    FragmentManager manager;

    private static final int ANY_FRAGMENT = -1;
    private static final int DIARY_FRAGMENT = 1;
    private static final int REGISTER_FRAGMENT = 2;
    private static final int PORTABILITY_FRAGMENT = 3;

    String vacio = "vacio";
    String name = "";
    String lastname = "";
    String call = "llamando";
    String numberCall = "56789045";


    private static final int CALL_PERMISSION_REQUEST_CODE = 1;
    private boolean mShowPermissionDeniedDialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        overridePendingTransition(R.anim.fade_in, R.anim.static_motion);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        View header = getLayoutInflater().inflate(R.layout.header_list, null);
        View footer = getLayoutInflater().inflate(R.layout.footer_list, null);

        imageHeaderList = (ImageView) header.findViewById(R.id.image_header_profile);
        txtHeaderList = (CustomTextViewRegular) header.findViewById(R.id.txt_username);
        String userName = User.get("last_name", this) + "\n" + User.get("first_name", this);
        String avatar = User.get("avatar", this);
        txtHeaderList.setText(userName);

        final int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 80, getResources().getDisplayMetrics());

        Glide.with(this).load(avatar).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageHeaderList) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageHeaderList.setImageDrawable(circularBitmapDrawable);
                imageHeaderList.getLayoutParams().height = px;
                imageHeaderList.getLayoutParams().width = px;
            }
        });

        mDrawerOptions = (ListView) findViewById(R.id.left_drawer);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        adapterActivity = new CustomMenuAdapter(this, R.layout.custom_menu_item, Constants.getCustomListView());
        mDrawerOptions.addHeaderView(header);
        mDrawerOptions.addFooterView(footer);
        mDrawerOptions.setAdapter(adapterActivity);

        mDrawerOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (position == 1) {
                    dataBaseFragment(arg1);
                } else if (position == 2) {
                    addDataBaseFragment(arg1);
                } else if (position == 3) {
                    diaryFragment(arg1);
                } else if (position == 4) {
                    registerFragment(arg1);
                } else if (position == 5) {
                    registerFragment(arg1);
                } else if (position == 6) {
                    portabilityFragment(arg1);
                } else if (position == 7) {
                    registerSimFragment(arg1);
                } else if (position == 8) {
                    betaReportFragment(arg1);
                } else if (position == 9) {
                    pointsFragment(arg1);
                } else if (position == 10) {
                    supportFragment(arg1);
                }
                mDrawer.closeDrawers();
            }
        });

        btnMenu = (ImageButton) findViewById(R.id.i_btn_header);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(mDrawerOptions);
            }
        });

        if (findViewById(R.id.flContent) != null) {

            if (savedInstanceState != null) {
                return;
            }

            AddDataBaseFragment addDataBaseFragment = new AddDataBaseFragment();
            manager = getFragmentManager();
            manager.beginTransaction().add(R.id.flContent, addDataBaseFragment).commit();
        }

        setTitle("Agreagr");
        setMainImage(R.drawable.icon_database);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            name = bundle.getString("name");
            lastname = bundle.getString("lastname");

            if (name != null) {
                Log.e("nombre", name.toString());
                Log.e("apellido", lastname.toString());
            } else {
                Log.e("Vacio", vacio);
            }
        }

    }

    public void closeDrawer(View view) {
        mDrawer.closeDrawers();
    }

    public void addDataBaseFragment(View view) {

        setTitle("Agregar");
        setMainImage(R.drawable.icon_database);
        final AddDataBaseFragment replaceDataBaseFragment = new AddDataBaseFragment();
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.flContent, replaceDataBaseFragment).commit();
    }


    public void betaReportFragment(View view){

        setTitle("Reportes");
        setMainImage(R.drawable.icon_reports);
        final BetaReportsFragment alphaReportsFragment = new BetaReportsFragment();
        getFragmentManager().beginTransaction().add(R.id.flContent, alphaReportsFragment).commit();

    }

    public void alphaReportFragment(View view){

        setTitle("Reportes");
        setMainImage(R.drawable.icon_reports);
        final AlphaReportsFragment betaReportsFragment = new AlphaReportsFragment();
        getFragmentManager().beginTransaction().add(R.id.flContent, betaReportsFragment).commit();

    }

    public void dataBaseFragment(View view) {

        setTitle("Base de Datos");
        setMainImage(R.drawable.icon_database);
        final DataBaseFragment dataBaseFragment = new DataBaseFragment();
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.flContent, dataBaseFragment).commit();
    }

    public void diaryFragment(View view) {

        setTitle("Agenda");
        setMainImage(R.drawable.icon_calendar);
        final DiaryFragment diaryFragment = new DiaryFragment();
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.flContent, diaryFragment).commit();

    }

    public void pointsFragment(View view) {

        setTitle("Puntos");
        setMainImage(R.drawable.icon_points);
        final PointsFragment pointsFragment = new PointsFragment();
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.flContent, pointsFragment).commit();

    }

    public void portabilityFragment(View view) {

        setTitle("ALTA");
        setMainImage(R.drawable.icon_add);
        final PortabilityFragment portabilityFragment = new PortabilityFragment();
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.flContent, portabilityFragment).commit();

    }

    public void registerFragment(View view) {

        setTitle("ALTA");
        setMainImage(R.drawable.icon_add);
        RegisterFragment registerFragment = new RegisterFragment();
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.flContent, registerFragment).commit();
    }

    public void registerSimFragment(View view) {

        setTitle("ALTA");
        setMainImage(R.drawable.icon_add);
        RegisterSimFragment registerSimFragment = new RegisterSimFragment();
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.flContent, registerSimFragment).commit();
    }

    public void supportFragment(View view) {

        setTitle("Soporte");
        setMainImage(R.drawable.icon_support);
        final SupportFragment supportFragment = new SupportFragment();
        getFragmentManager().beginTransaction().add(R.id.flContent, supportFragment).commit();

    }

    public void logOut(View view) {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("¿Desea cerrar sesión?");
        dialogo1.setCancelable(false);
        dialogo1.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                removeUser();
            }
        });
        dialogo1.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
            }
        });
        dialogo1.show();

    }

    private void removeUser() {

        User.clear(this);
        Constants.setUserOffline(true);
        finish();
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(i);

        finish();

        SupportFragment supportFragment = new SupportFragment();
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.flContent, supportFragment).commit();

    }

    public void setNumberCall(String numberCall) {
        this.numberCall = numberCall;
    }

    public void call() {
        Log.e("esperando llamada" , call);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + numberCall));
        startActivity(callIntent);
    }

    public void askForPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e("checkSelfPermission", "request 2");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                Log.e("shouldShowRequest", "request 2");
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
            } else {

                // No explanation needed, we can request the permission.
                Log.e("request permission", "request 2");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        CALL_PERMISSION_REQUEST_CODE);

            }
        }else{
            call();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (requestCode != CALL_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, results,
                Manifest.permission.CALL_PHONE)) {
            call();
        } else {
            mShowPermissionDeniedDialog = true;
        }

    }

    /*@Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }*/
}
