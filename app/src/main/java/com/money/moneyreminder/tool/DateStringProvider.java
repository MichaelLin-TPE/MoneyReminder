package com.money.moneyreminder.tool;

import android.os.AsyncTask;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.money.moneyreminder.dialog.SettingDayRangeDialogFragment.FIVE;
import static com.money.moneyreminder.dialog.SettingDayRangeDialogFragment.ONE;

public class DateStringProvider extends AsyncTask<Void,Void, ArrayList<DateDTO>> {


    private OnDateStringProvideListener listener;

    public void setOnDateStringProvideListener(OnDateStringProvideListener listener){
        this.listener = listener;
    }

    @Override
    protected ArrayList<DateDTO> doInBackground(Void... voids) {


        if (UserManager.getInstance().getDayRange().equals(ONE)){
            return getDefaultArray();
        }else if (UserManager.getInstance().getDayRange().equals(FIVE)){
            return getFiveArray();
        }
        return getTenArray();
    }

    private ArrayList<DateDTO> getTenArray(){
        ArrayList<DateDTO> dateObjectArray = new ArrayList<>();

        for (int year = 2020 ; year < 2027 ; year ++){
            for (int month = 1 ; month < 13 ; month ++){
                DateDTO dateDTO = new DateDTO();
                String monthString;
                String nextMonthString;
                if (month < 10){
                    monthString = "0"+month;
                    nextMonthString = "0"+(month+1);
                }else {
                    monthString = month+"";
                    if (month == 12){
                        nextMonthString = "01";
                    }else {
                        nextMonthString = ""+(month+1);
                    }
                }
                dateDTO.setMonth(monthString);
                dateDTO.setYear(year+"");
                dateDTO.setEndDateOfMonth(nextMonthString+"/10");
                dateDTO.setFirstDateOfMonth(monthString+"/10");
                dateDTO.setTabString(year+"\n"+monthString+"/10"+"-"+nextMonthString+"/10");
                dateObjectArray.add(dateDTO);
            }
        }
        return dateObjectArray;
    }

    private ArrayList<DateDTO> getFiveArray(){
        ArrayList<DateDTO> dateObjectArray = new ArrayList<>();

        for (int year = 2020 ; year < 2027 ; year ++){
            for (int month = 1 ; month < 13 ; month ++){
                DateDTO dateDTO = new DateDTO();
                String monthString;
                String nextMonthString;
                if (month < 10){
                    monthString = "0"+month;
                    nextMonthString = "0"+(month+1);
                }else {
                    monthString = month+"";
                    if (month == 12){
                        nextMonthString = "01";
                    }else {
                        nextMonthString = ""+(month+1);
                    }
                }
                dateDTO.setMonth(monthString);
                dateDTO.setYear(year+"");
                dateDTO.setEndDateOfMonth(nextMonthString+"/05");
                dateDTO.setFirstDateOfMonth(monthString+"/05");
                dateDTO.setTabString(year+"\n"+monthString+"/05"+"-"+nextMonthString+"/05");
                dateObjectArray.add(dateDTO);
            }
        }
        return dateObjectArray;
    }

    private ArrayList<DateDTO> getDefaultArray(){
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
