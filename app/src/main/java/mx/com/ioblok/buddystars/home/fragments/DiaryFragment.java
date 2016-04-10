package mx.com.ioblok.buddystars.home.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mx.com.ioblok.buddystars.R;

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

    CompactCalendarView compactCalendarView;
    int currentMonth, currentyear;
    TextView textViewCalendar;
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_diary, null);

        Button btnPreviousMonth = (Button) v.findViewById(R.id.btn_previous_month);
        Button btnNextMonth = (Button) v.findViewById(R.id.btn_next_month);
        textViewCalendar = (TextView) v.findViewById(R.id.tv_current_date);

        compactCalendarView = (CompactCalendarView) v.findViewById(R.id.compact_calendar_view);
        compactCalendarView.drawSmallIndicatorForEvents(true);
        compactCalendarView.setDayColumnNames(daysOfWeek);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setShouldDrawDaysHeader(true);
        compactCalendarView.shouldScrollMonth(true);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });

        Date date = compactCalendarView.getFirstDayOfCurrentMonth();


        String intMonth = (String) android.text.format.DateFormat.format("MM", date); //06
        String year = (String) android.text.format.DateFormat.format("yyyy", date); //2013

        Log.e("DATE", intMonth + " " + year + "");
        String currentMonth = intMonth + " " + year;

        textViewCalendar.setText(currentMonth);

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
                updateLabel();
            }
        };

        textViewCalendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(getActivity(), timeSetListener,
                        myCalendar.get(Calendar.HOUR),
                        myCalendar.get(Calendar.MINUTE), true).show();
            }
        });

        return v;

    }

    private void updateLabel() {

        String hourFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(hourFormat, Locale.US);
        textViewCalendar.setText(sdf.format(myCalendar.getTime()));

    }

}
