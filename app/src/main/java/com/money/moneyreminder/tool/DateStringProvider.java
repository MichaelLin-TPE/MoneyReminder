package com.money.moneyreminder.tool;

import android.os.AsyncTask;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DateStringProvider extends AsyncTask<Void,Void, ArrayList<DateDTO>> {


    private OnDateStringProvideListener listener;

    public void setOnDateStringProvideListener(OnDateStringProvideListener listener){
        this.listener = listener;
    }

    @Override
    protected ArrayList<DateDTO> doInBackground(Void... voids) {


        ArrayList<DateDTO> dateObjectArray = new ArrayList<>();

        for (int year = 2020 ; year < 2027 ; year ++){
            for (int month = 1 ; month < 13 ; month ++){
                DateDTO dateDTO = new DateDTO();
                String monthString;
                if (month < 10){
                    monthString = "0"+month;
                }else {
                    monthString = month+"";
                }
                dateDTO.setMonth(monthString);
                dateDTO.setYear(year+"");
                dateDTO.setEndDateOfMonth(getEndDateOfMonth(year,month));
                dateDTO.setFirstDateOfMonth(getFirstDateOfMonth(year,month));
                dateDTO.setTabString(year+"\n"+getFirstDateOfMonth(year,month)+"-"+getEndDateOfMonth(year,month));
                dateObjectArray.add(dateDTO);
            }
        }
        return dateObjectArray;
    }

    @Override
    protected void onPostExecute(ArrayList<DateDTO> strings) {
        super.onPostExecute(strings);
        listener.onSuccess(strings);
    }

    public interface OnDateStringProvideListener{
        void onSuccess(ArrayList<DateDTO> dateString);
    }

    private String getFirstDateOfMonth(int year,int month){
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd", Locale.TAIWAN);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime());
    }

    private String getEndDateOfMonth(int year,int month){
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd",Locale.TAIWAN);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime());
    }
}
