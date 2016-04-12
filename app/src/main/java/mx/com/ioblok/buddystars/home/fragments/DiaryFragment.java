package mx.com.ioblok.buddystars.home.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.CalendarDayEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.adapter.DataBaseElementAdapter;
import mx.com.ioblok.buddystars.utils.Utils;
import mx.com.ioblok.buddystars.utils.WebBridge;

/**
 * Created by kreativeco on 01/02/16.
 */
public class DiaryFragment extends Fragment implements WebBridge.WebBridgeListener{

    View v;
    String [] daysOfWeek = {"Lunes",
            "Martes",
            "Miércoles",
            "Jueves",
            "Viernes",
            "Sábado",
            "Domingo"
    };

    private CompactCalendarView compactCalendarView;
    private TextView textViewCalendar;
    private ImageButton imageButtonAdd;
    private Button btnPreviousMonth, btnNextMonth;
    private Date date;
    private String strYear;
    private String strMonth;
    private String strDay;
    private String strHour;
    private String strMinute;
    private boolean isCalendarEnable = false;

    final Calendar myCalendar = Calendar.getInstance();

    String name = "", lastname = "", phone = "", email = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_diary, null);

        btnPreviousMonth    = (Button) v.findViewById(R.id.btn_previous_month);
        btnNextMonth        = (Button) v.findViewById(R.id.btn_next_month);
        textViewCalendar    = (TextView) v.findViewById(R.id.tv_current_date);
        imageButtonAdd      = (ImageButton) v.findViewById(R.id.btn_add);
        compactCalendarView = (CompactCalendarView) v.findViewById(R.id.compact_calendar_view);

        compactCalendarView.drawSmallIndicatorForEvents(true);
        compactCalendarView.setDayColumnNames(daysOfWeek);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setShouldDrawDaysHeader(true);
        compactCalendarView.shouldScrollMonth(true);
        //compactCalendarView.invalidate();



        Bundle bundle = getArguments();

        if (bundle != null) {

            name = getArguments().getString("name");
            Log.e("name",name );
            lastname = getArguments().getString("surename");
            Log.e("name",name );
            phone = getArguments().getString("phone");
            Log.e("name",name );
            email = getArguments().getString("email");
            Log.e("name",name );

        }else{
            imageButtonAdd.setVisibility(View.INVISIBLE);
            isCalendarEnable = true;
            getDates();
        }

        setListeners();
        setHeaderDate();
        setInitialDates();

        return v;

    }

    private void getDates() {
        WebBridge.send("/register-list", "Cargando", getActivity(), this);
    }

    private void setListeners(){

        //compactCalendarView Listener
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                if(isCalendarEnable){
                    strDay      = (String) android.text.format.DateFormat.format("dd", dateClicked);
                    strMonth    = (String) android.text.format.DateFormat.format("MM", dateClicked);
                    strYear     = (String) android.text.format.DateFormat.format("yyyy", dateClicked);

                }

                setDatesOnRecyclerView();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setHeaderDate();
            }
        });


        //Button listeners, move previous or next month
        btnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showNextMonth();
            }
        });

        btnPreviousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showPreviousMonth();
            }
        });

        final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTime();
            }
        };

        //Lister for show TimePicker
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(getActivity(), timeSetListener,
                        myCalendar.get(Calendar.HOUR),
                        myCalendar.get(Calendar.MINUTE), true).show();
            }
        });
    }

    private void setDatesOnRecyclerView() {

    }

    private void setInitialDates() {

        date     = compactCalendarView.getFirstDayOfCurrentMonth();

        strDay      = (String) android.text.format.DateFormat.format("dd", date);
        strMonth    = (String) android.text.format.DateFormat.format("MM", date);
        strYear     = (String) android.text.format.DateFormat.format("yyyy", date);

    }

    private void updateTime() {

        String hourFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(hourFormat, Locale.US);

        strHour     = Integer.toString(myCalendar.get(Calendar.HOUR));
        strMinute   = Integer.toString(myCalendar.get(Calendar.MINUTE));

        Utils.setStringSchedule(strYear + "-" + strMonth + "-"
                + strDay + " " + strHour + ":" + strMinute + ":00");

        final AddDataBaseFragment addDataBaseFragment = new AddDataBaseFragment();

        Bundle data = new Bundle();
        data.putString("name", name);
        data.putString("surename", lastname);
        data.putString("phone", phone);
        data.putString("email", email);
        data.putString("schedule", Utils.getStringSchedule());

        addDataBaseFragment.setArguments(data);
        getFragmentManager().beginTransaction().replace(R.id.flContent, addDataBaseFragment).commit();

    }

    private void setHeaderDate(){

        date     = compactCalendarView.getFirstDayOfCurrentMonth();
        String txtMonth = setStringMonth((String) android.text.format.DateFormat.format("MM", date));
        String txtYear = (String) android.text.format.DateFormat.format("yyyy", date);

        String currentMonth = txtMonth + " " + txtYear;

        textViewCalendar.setText(currentMonth);

    }

    private String setStringMonth(String stringMonth){

        switch (stringMonth){
            case "01":
                stringMonth = "Enero";
                break;
            case "02":
                stringMonth = "Febrero";
                break;
            case "03":
                stringMonth = "Marzo";
                break;
            case "04":
                stringMonth = "Abril";
                break;
            case "05":
                stringMonth = "Mayo";
                break;
            case "06":
                stringMonth = "Junio";
                break;
            case "07":
                stringMonth = "Julio";
                break;
            case "08":
                stringMonth = "Agosto";
                break;
            case "09":
                stringMonth = "Septiembre";
                break;
            case "10":
                stringMonth = "Octubre";
                break;
            case "11":
                stringMonth = "Noviembre";
                break;
            case "12":
                stringMonth = "Diciembre";
                break;
        }

        return stringMonth;
    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {
        Log.e("json", json.toString());
        try {
            if (json.getBoolean("success")) {
                JSONArray jsonArrayDates = json.getJSONArray("data");

                /*RecyclerView.Adapter rvAdapter = new DataBaseElementAdapter(jsonArrayFarmers, getActivity());
                recyclerViewDataBase.setAdapter(rvAdapter);*/

                List<CalendarDayEvent> calendarDayEventList = new ArrayList<>();
                for (int i = 0; i<jsonArrayDates.length(); i++){

                    String strDate = jsonArrayDates.getJSONObject(i).getString("schedule");
                    String strRegisterId = jsonArrayDates.getJSONObject(i).getString("register_id");;
                    String strName = jsonArrayDates.getJSONObject(i).getString("name");;
                    String strPhone = jsonArrayDates.getJSONObject(i).getString("phone");;
                    String strEMail = jsonArrayDates.getJSONObject(i).getString("email");;


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    try {
                        Date mDate = sdf.parse(strDate);
                        long timeInMilliseconds = mDate.getTime();

                        compactCalendarView.addEvent(new CalendarDayEvent(timeInMilliseconds,  Color.argb(255, 6, 83, 114)), true);
                    } catch (  ParseException e) {
                        e.printStackTrace();
                        Log.e("Exception", e.toString());
                    }

                }

            } else {
                String error = json.getJSONArray("error_message").getString(0);
                new AlertDialog.Builder(getActivity().getBaseContext()).setTitle(R.string.txt_error).setMessage(error).setNeutralButton(R.string.bt_close, null).show();
            }

        } catch (JSONException e) {
            Log.e("Exception", e.toString());
        }
    }

    @Override
    public void onWebBridgeFailure(String url, String response) {

    }
}
