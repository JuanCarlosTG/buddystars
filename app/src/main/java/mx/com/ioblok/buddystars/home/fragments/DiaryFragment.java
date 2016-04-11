package mx.com.ioblok.buddystars.home.fragments;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.utils.Utils;

/**
 * Created by kreativeco on 01/02/16.
 */
public class DiaryFragment extends Fragment {

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

    final Calendar myCalendar = Calendar.getInstance();


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

        setListeners();
        setHeaderDate();
        setInitialDates();

        return v;

    }

    private void setListeners(){

        //compactCalendarView Listener
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                strDay      = (String) android.text.format.DateFormat.format("dd", dateClicked);
                strMonth    = (String) android.text.format.DateFormat.format("MM", dateClicked);
                strYear     = (String) android.text.format.DateFormat.format("yyyy", dateClicked);
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

        //textViewCalendar.setText(sdf.format(myCalendar.getTime()));
        Utils.setStringSchedule(strYear + "-" + strMonth + "-"
                + strDay + " " + strHour + ":" + strMinute + ":00");
        textViewCalendar.setText(Utils.getStringSchedule());

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
