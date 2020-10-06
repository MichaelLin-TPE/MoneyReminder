package com.money.moneyreminder.tool;

import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.money.moneyreminder.R;
import com.money.moneyreminder.calendar_fragment.CalendarFragment;
import com.money.moneyreminder.list_fragment.ListFragment;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.user_fragment.UserFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DataProvider {

    private static DataProvider instance = null;

    private SimpleDateFormat dateFormat,yearMonthFormat;

    private DataProvider(){
        dateFormat = new SimpleDateFormat("dd",Locale.TAIWAN);
        yearMonthFormat = new SimpleDateFormat("yyyy/MM",Locale.TAIWAN);
    }

    public static DataProvider getInstance() {
        if (instance == null) {
            instance = new DataProvider();

            return instance;
        }
        return instance;
    }

    public ArrayList<String> getTabTitleArray() {
        ArrayList<String> tabArray = new ArrayList<>();
        tabArray.add(MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.money_list));
        tabArray.add(MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.money_calendar));
        tabArray.add(MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.money_user));
        return tabArray;
    }

    public ArrayList<Drawable> getTabIconPressArray() {
        ArrayList<Drawable> tabIconArray = new ArrayList<>();
        tabIconArray.add(ContextCompat.getDrawable(MoneyReminderApplication.getInstance().getApplicationContext(), R.drawable.list_press));
        tabIconArray.add(ContextCompat.getDrawable(MoneyReminderApplication.getInstance().getApplicationContext(), R.drawable.calendar_press));
        tabIconArray.add(ContextCompat.getDrawable(MoneyReminderApplication.getInstance().getApplicationContext(), R.drawable.user_press));
        return tabIconArray;
    }

    public ArrayList<Drawable> getTabIconNotPressArray() {
        ArrayList<Drawable> tabIconArray = new ArrayList<>();
        tabIconArray.add(ContextCompat.getDrawable(MoneyReminderApplication.getInstance().getApplicationContext(), R.drawable.list_not_press));
        tabIconArray.add(ContextCompat.getDrawable(MoneyReminderApplication.getInstance().getApplicationContext(), R.drawable.calendar_not_press));
        tabIconArray.add(ContextCompat.getDrawable(MoneyReminderApplication.getInstance().getApplicationContext(), R.drawable.user_not_press));
        return tabIconArray;
    }

    public ArrayList<Fragment> getAllFragmentArray() {
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(ListFragment.newInstance());
        fragmentArrayList.add(CalendarFragment.newInstance());
        fragmentArrayList.add(UserFragment.newInstance());
        return fragmentArrayList;
    }

    public ArrayList<String> getSortDataArray() {
        ArrayList<String> sortDataArray = new ArrayList<>();
        sortDataArray.add(MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.sort));
        sortDataArray.add(MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.list));
        return sortDataArray;
    }

    public ArrayList<String> getNumberArray() {
        ArrayList<String> numberArray = new ArrayList<>();
        numberArray.add("7");
        numberArray.add("8");
        numberArray.add("9");
        numberArray.add("4");
        numberArray.add("5");
        numberArray.add("6");
        numberArray.add("1");
        numberArray.add("2");
        numberArray.add("3");
        numberArray.add("C");
        numberArray.add("0");
        numberArray.add(".");
        numberArray.add("OK");
        return numberArray;
    }

    public ArrayList<Drawable> getMathArray() {
        ArrayList<Drawable> mathArray = new ArrayList<>();
        mathArray.add(ContextCompat.getDrawable(MoneyReminderApplication.getInstance().getApplicationContext(), R.drawable.back));
        mathArray.add(ContextCompat.getDrawable(MoneyReminderApplication.getInstance().getApplicationContext(), R.drawable.divide));
        mathArray.add(ContextCompat.getDrawable(MoneyReminderApplication.getInstance().getApplicationContext(), R.drawable.multiply));
        mathArray.add(ContextCompat.getDrawable(MoneyReminderApplication.getInstance().getApplicationContext(), R.drawable.less));
        mathArray.add(ContextCompat.getDrawable(MoneyReminderApplication.getInstance().getApplicationContext(), R.drawable.plus));
        return mathArray;
    }

    public String getCurrentTime() {
        return new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN).format(new Date(System.currentTimeMillis()));
    }

    public ArrayList<String> getWeekDayArray() {
        ArrayList<String> weekDayArray = new ArrayList<>();
        weekDayArray.add("日");
        weekDayArray.add("一");
        weekDayArray.add("二");
        weekDayArray.add("三");
        weekDayArray.add("四");
        weekDayArray.add("五");
        weekDayArray.add("六");
        return weekDayArray;
    }

    public long getTimeMiles(String currentTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd", Locale.TAIWAN);
        try{
            Date date = sdf.parse(currentTime);
            if (date == null){
                return 0;
            }
            return date.getTime();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<String> getDateList(int year, int month) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
        SimpleDateFormat weekFormat = new SimpleDateFormat("u", Locale.TAIWAN);
        String monthString;
        if (month < 10) {
            monthString = "0" + month;
        } else {
            monthString = month + "";
        }

        Date currentDate;
        try {
            currentDate = simpleDateFormat.parse(year + "/" + monthString + "/01");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (currentDate == null) {
            Log.i("Michael", "currentDate is null");
            return null;
        }
        int firstWeekDay = Integer.parseInt(weekFormat.format(currentDate));
        Log.i("Michael", "星期 " + firstWeekDay);
        ArrayList<String> dateList = new ArrayList<>();
        int totalDays = getTotalDay(year, month);
        int weekDay = 1;
        for (int i = 1; i <= totalDays + firstWeekDay; i++) {
            if (i <= firstWeekDay) {
                dateList.add("");
                continue;
            }
            String date;
            if (weekDay < 10) {
                date = "0" + weekDay;
            } else {
                date = weekDay + "";
            }

            dateList.add(date);
            weekDay++;
        }
        return dateList;
    }

    private int getTotalDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }
    public String getTime(long timeMiles){
        return dateFormat.format(new Date(timeMiles));
    }

    public String getYearAndDate(long timeMiles){
        return yearMonthFormat.format(new Date(timeMiles));
    }

    public int getMonthMaxDay(String currentMonth, String currentYear) {
        return getTotalDay(Integer.parseInt(currentYear),Integer.parseInt(currentMonth));
    }

    public ArrayList<String> getAccountItemArray(){
        ArrayList<String> accountItemArray = new ArrayList<>();
        accountItemArray.add(MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.set_time_range));
        accountItemArray.add(MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.sort_type));
        accountItemArray.add(MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.signout));
        return accountItemArray;
    }
}
