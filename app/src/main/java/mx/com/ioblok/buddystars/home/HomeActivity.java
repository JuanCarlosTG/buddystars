package mx.com.ioblok.buddystars.home;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.SectionActivity;
import mx.com.ioblok.buddystars.adapter.CustomMenuAdapter;
import mx.com.ioblok.buddystars.home.fragments.AddDataBaseFragment;
import mx.com.ioblok.buddystars.home.fragments.BetaReportsFragment;
import mx.com.ioblok.buddystars.home.fragments.AlphaReportsFragment;
import mx.com.ioblok.buddystars.home.fragments.DataBaseFragment;
import mx.com.ioblok.buddystars.home.fragments.DiaryFragment;
import mx.com.ioblok.buddystars.home.fragments.PointsFragment;
import mx.com.ioblok.buddystars.home.fragments.PortabilityFragment;
import mx.com.ioblok.buddystars.home.fragments.RegisterFragment;
import mx.com.ioblok.buddystars.home.fragments.SupportFragment;
import mx.com.ioblok.buddystars.utils.Constants;

public class HomeActivity extends SectionActivity {

    public ImageButton btnMenu;

    private DrawerLayout mDrawer;
    private ListView mDrawerOptions;
    CustomMenuAdapter adapterActivity;

    String vacio = "vacio";
    String name = "";
    String lastname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        overridePendingTransition(R.anim.fade_in, R.anim.static_motion);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        View header = getLayoutInflater().inflate(R.layout.header_list, null);
        View footer = getLayoutInflater().inflate(R.layout.footer_list, null);

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
                    betaReportFragment(arg1);
                } else if (position == 8) {
                    pointsFragment(arg1);
                } else if (position == 9) {
                    supportFragment(arg1);
                }
            }
        });

        btnMenu = (ImageButton) findViewById(R.id.i_btn_header);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(mDrawerOptions);
            }
        });

        final DataBaseFragment dataBaseFragment = new DataBaseFragment();
        getFragmentManager().beginTransaction().add(R.id.flContent, dataBaseFragment).commit();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            name = bundle.getString("name");
            lastname = bundle.getString("lastname");

            if (name != null) {
                Log.e("nombre" , name.toString());
                Log.e("apellido" ,lastname.toString());
            } else {
                Log.e("Vacio" , vacio);
            }
        }

    }

    public void closeDrawer(View view){
        mDrawer.closeDrawers();
    }

    public void addDataBaseFragment(View view){
        final AddDataBaseFragment addDataBaseFragment = new AddDataBaseFragment();
        getFragmentManager().beginTransaction().add(R.id.flContent, addDataBaseFragment).commit();
    }

    public void alphaReportFragment(View view){
        final BetaReportsFragment alphaReportsFragment = new BetaReportsFragment();
        getFragmentManager().beginTransaction().add(R.id.flContent, alphaReportsFragment).commit();

    }

    public void betaReportFragment(View view){
        final AlphaReportsFragment betaReportsFragment = new AlphaReportsFragment();
        getFragmentManager().beginTransaction().add(R.id.flContent, betaReportsFragment).commit();

    }

    public void dataBaseFragment(View view){
        final DataBaseFragment dataBaseFragment = new DataBaseFragment();
        getFragmentManager().beginTransaction().add(R.id.flContent, dataBaseFragment).commit();
    }

    public void diaryFragment(View view){
        final DiaryFragment diaryFragment = new DiaryFragment();
        getFragmentManager().beginTransaction().add(R.id.flContent, diaryFragment).commit();

    }

    public void pointsFragment(View view){
        final PointsFragment pointsFragment = new PointsFragment();
        getFragmentManager().beginTransaction().add(R.id.flContent, pointsFragment).commit();

    }

    public void portabilityFragment(View view){
        final PortabilityFragment portabilityFragment = new PortabilityFragment();
        getFragmentManager().beginTransaction().add(R.id.flContent, portabilityFragment).commit();

    }

    public void registerFragment(View view){

        final RegisterFragment registerFragment = new RegisterFragment();
        getFragmentManager().beginTransaction().add(R.id.flContent, registerFragment).commit();

    }

    public void supportFragment(View view){
        final SupportFragment supportFragment = new SupportFragment();
        getFragmentManager().beginTransaction().add(R.id.flContent, supportFragment).commit();

    }

    public void actionsFromSubMenuItems(View view) {

        Log.e("ID", view.getId() + "");

        /*switch (view.getId()) {

            //listeners for families submenu
            case R.id.rl_parent:

                break;

        }*/

    }

}
