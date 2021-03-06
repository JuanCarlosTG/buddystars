package mx.com.ioblok.buddystars.home.activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.customviews.CustomButtonRegular;
import mx.com.ioblok.buddystars.customviews.CustomTextViewRegular;
import mx.com.ioblok.buddystars.utils.Constants;

/**
 * Created by omar on 4/17/16.
 */
public class DiaryActivity extends Activity{

    String [] daysOfWeek = {"Lunes",
            "Martes",
            "Miércoles",
            "Jueves",
            "Viernes",
            "Sábado",
            "Domingo"
    };

    private CompactCalendarView compactCalendarView;
    private CustomTextViewRegular textViewCalendar;
    private ImageButton imageButtonAdd, imageButtonDown;
    private CustomButtonRegular btnPreviousMonth, btnNextMonth;
    private Date date;
    private String strYear;
    private String strMonth;
    private String strDay;
    private String strHour;
    private String strMinute;
    private boolean isCalendarEnable = true;

    final Calendar myCalendar = Calendar.getInstance();

    String name = "", lastname = "", phone = "", email = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);


        btnPreviousMonth    = (CustomButtonRegular) findViewById(R.id.btn_previous_month);
        btnNextMonth        = (CustomButtonRegular) findViewById(R.id.btn_next_month);
        textViewCalendar    = (CustomTextViewRegular) findViewById(R.id.tv_current_date);
        imageButtonAdd      = (ImageButton) findViewById(R.id.btn_add);
        imageButtonDown      = (ImageButton) findViewById(R.id.btn_header_back);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compact_calendar_view);

        compactCalendarView.drawSmallIndicatorForEvents(true);
        compactCalendarView.setDayColumnNames(daysOfWeek);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setShouldDrawDaysHeader(true);
        compactCalendarView.shouldScrollMonth(true);
        //compactCalendarView.invalidate();

        setListeners();
        setHeaderDate();
        setInitialDates();

        imageButtonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });

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

                //setDatesOnRecyclerView();
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
                new TimePickerDialog(DiaryActivity.this, timeSetListener,
                        myCalendar.get(Calendar.HOUR),
                        myCalendar.get(Calendar.MINUTE), true).show();
            }
        });
    }

    private void setInitialDates() {

        date     = compactCalendarView.getFirstDayOfCurrentMonth();

        strDay      = (String) android.text.format.DateFormat.format("dd", date);
        strMonth    = (String) android.text.format.DateFormat.format("MM", date);
        strYear     = (String) android.text.format.DateFormat.format("yyyy", date);

    }

    public void updateTime() {

        String hourFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(hourFormat, Locale.US);

        strHour     = Integer.toString(myCalendar.get(Calendar.HOUR));
        strMinute   = Integer.toString(myCalendar.get(Calendar.MINUTE));

        Constants.setRegisterSchedule(strYear + "-" + strMonth + "-"
                + strDay + " " + strHour + ":" + strMinute + ":00");

        finish();
        DiaryActivity.this.overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
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

}
