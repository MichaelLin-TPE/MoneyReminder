package com.money.moneyreminder.tool;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.money.moneyreminder.dialog.SecondSortData;
import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.sort_list.IconData;
import com.money.moneyreminder.sort_list.presenter.SortCreateData;
import com.money.moneyreminder.sort_list.presenter.SortData;
import com.money.moneyreminder.sort_list.presenter.SortRecentlyData;
import com.money.moneyreminder.sort_list.presenter.SortTypeData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FirebaseHandlerImpl implements FirebaseHandler {
    private FirebaseAuth mAuth;

    private FirebaseUser user;

    private FirebaseFirestore firebaseFirestore;

    private static final String MONEY_LIST = "money_list";

    private static final String SORT_LIST = "sort_list";

    private static final String API = "api";

    private static final String ICON_API = "icon_api";

    private static final String SECOND_SORT_LIST = "second_sort_list";

    private static final String DEFAULT_SORT_LIST = "default_sort_list";

    private static final String DESCRIPTION = "description";

    private static final String BUDGET = "budget";

    private ArrayList<MoneyObject> moneyObjectArray;

    private ArrayList<SecondSortData> secondSortDataArray;

    private String description, edContent, iconUrl;

    private SortTypeData sortTypeData;

    private SortData userSortData;

    private boolean isIncome;
    private int totalMoney;
    private long choiceTimeMiles;

    private Gson gson;

    public FirebaseHandlerImpl() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        gson = new Gson();


    }

    @Override
    public String getUserEmail() {
        if (user == null) {
            MichaelLog.i("娶不到UserEmail");
            return "";
        }
        return user.getEmail();
    }

    @Override
    public void saveUserMoneyList(SortTypeData sortType, long choiceTimeMiles, int totalMoney, boolean isIncome, String description) {

        this.sortTypeData = sortType;
        this.choiceTimeMiles = choiceTimeMiles;
        this.totalMoney = totalMoney;
        this.description = description;
        this.isIncome = isIncome;

        firebaseFirestore.collection(MONEY_LIST)
                .document(getUserEmail())
                .get()
                .addOnCompleteListener(onCheckHasUserDataListener);

    }

    @Override
    public void getUserSortData(final OnFireStoreCatchListener<SortData> onFireStoreCatchListener) {

        DocumentReference docRef = firebaseFirestore.collection(SORT_LIST)
                .document(getUserEmail());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    catchDefaultList(onFireStoreCatchListener);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    String json = (String) snapshot.get("json");
                    if (json == null) {
                        catchDefaultList(onFireStoreCatchListener);
                        return;
                    }
                    userSortData = gson.fromJson(json, SortData.class);
                    if (userSortData == null) {
                        catchDefaultList(onFireStoreCatchListener);
                        return;
                    }
                    onFireStoreCatchListener.onSuccess(userSortData);
                } else {
                    catchDefaultList(onFireStoreCatchListener);
                    MichaelLog.i("沒資料");
                }
            }
        });

        firebaseFirestore.collection(SECOND_SORT_LIST)
                .document(getUserEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.isSuccessful() || task.getResult() == null){
                            MichaelLog.i("無法取得次分類表");
                            return;
                        }
                        DocumentSnapshot snapshot = task.getResult();
                        if (snapshot == null){
                            MichaelLog.i("無法取得次分類表");
                            return;
                        }
                        String json = (String) snapshot.get("json");
                        if (json == null || json.isEmpty()){
                            MichaelLog.i("無法取得次分類表");
                            return;
                        }
                        secondSortDataArray = gson.fromJson(json,new TypeToken<ArrayList<SecondSortData>>(){}.getType());

                    }
                });
    }

    private void catchDefaultList(final OnFireStoreCatchListener<SortData> onFireStoreCatchListener) {
        DocumentReference docRef = firebaseFirestore.collection(DEFAULT_SORT_LIST)
                .document("list");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    onFireStoreCatchListener.onFail("");
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    String json = (String) snapshot.get("json");
                    if (json == null) {
                        onFireStoreCatchListener.onFail("");
                        return;
                    }
                    userSortData = gson.fromJson(json, SortData.class);
                    if (userSortData == null) {
                        onFireStoreCatchListener.onFail("");
                        return;
                    }
                    onFireStoreCatchListener.onSuccess(userSortData);
                } else {
                    onFireStoreCatchListener.onFail("");
                    MichaelLog.i("沒資料");
                }
            }
        });
    }

    @Override
    public void getIconApi(final OnFireStoreCatchListener<ArrayList<IconData>> onFireStoreCatchListener) {
        firebaseFirestore.collection(API)
                .document(ICON_API)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.isSuccessful() || task.getResult() == null) {
                            onFireStoreCatchListener.onFail("firebase fail");
                            return;
                        }
                        DocumentSnapshot snapshot = task.getResult();
                        String json = (String) snapshot.get("json");
                        if (json == null) {
                            onFireStoreCatchListener.onFail("firebase fail");
                            return;
                        }
                        ArrayList<IconData> iconDataArrayList = gson.fromJson(json, new TypeToken<ArrayList<IconData>>() {
                        }.getType());
                        if (iconDataArrayList == null || iconDataArrayList.isEmpty()) {
                            onFireStoreCatchListener.onFail("firebase fail");
                            return;
                        }
                        onFireStoreCatchListener.onSuccess(iconDataArrayList);
                    }
                });
    }

    @Override
    public void setPersonalSortType(String edContent, String iconUrl) {
        this.edContent = edContent;
        this.iconUrl = iconUrl;

        if (userSortData == null) {
            createNewSortList();
        } else {
            addSortList();
        }
    }

    @Override
    public void setPersonalRecentlySortType(SortCreateData sortCreateData) {
        SortRecentlyData recentlyData = new SortRecentlyData();
        recentlyData.setIconUrl(sortCreateData.getIconUrl());
        recentlyData.setSortType(sortCreateData.getSortType());

        if (userSortData.getRecentlyData() == null || userSortData.getRecentlyData().isEmpty()){
            ArrayList<SortRecentlyData> recentlyDataArray = new ArrayList<>();
            recentlyDataArray.add(recentlyData);
            userSortData.setRecentlyData(recentlyDataArray);
            String json = gson.toJson(userSortData);
            saveUserSortData(json);
            return;
        }
        boolean isDataRepeat = false;
        for (SortRecentlyData data : userSortData.getRecentlyData()){
            if (data.getIconUrl().equals(recentlyData.getIconUrl()) && data.getSortType().equals(recentlyData.getSortType())){
                isDataRepeat = true;
                break;
            }
        }
        //資料重複什麼都不做
        if (isDataRepeat){
            return;
        }

        userSortData.getRecentlyData().add(recentlyData);
        String json = gson.toJson(userSortData);
        saveUserSortData(json);
    }

    @Override
    public void saveUserSecondSortData(final ArrayList<String> contentArray, final String sortTitle) {
        final SecondSortData secondSortData = new SecondSortData();
        secondSortData.setContentArray(contentArray);
        secondSortData.setSortTitle(sortTitle);
        if (secondSortDataArray == null || secondSortDataArray.isEmpty()){

            secondSortDataArray = new ArrayList<>();
            secondSortDataArray.add(secondSortData);
            saveUserSecondSortList(secondSortDataArray);
            return;
        }
        boolean isTitleRepeat = false;
        for (SecondSortData data : secondSortDataArray){
            if (data.getSortTitle().equals(secondSortData.getSortTitle())){
                for (String content : secondSortData.getContentArray()){
                    boolean isContentRepeat = false;
                    for (String oldContent : data.getContentArray()){
                        if (oldContent.equals(content)){
                            isContentRepeat = true;
                            break;
                        }
                    }
                    if (!isContentRepeat){
                        data.getContentArray().add(content);
                    }
                    isTitleRepeat = true;
                }
                break;
            }
        }
        if (!isTitleRepeat){
            secondSortDataArray.add(secondSortData);
        }
        saveUserSecondSortList(secondSortDataArray);

    }

    @Override
    public void getSecondSortArray(OnFireStoreCatchListener<ArrayList<SecondSortData>> onFireStoreCatchListener) {
        if (secondSortDataArray == null || secondSortDataArray.isEmpty()){
            onFireStoreCatchListener.onFail("無法取得次分類表");
            return;
        }
        onFireStoreCatchListener.onSuccess(secondSortDataArray);
    }

    @Override
    public void getBudgetMoney(final OnFireStoreCatchListener<Long> onCatchBudgetListener) {
        firebaseFirestore.collection(BUDGET)
                .document(getUserEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.isSuccessful() || task.getResult() == null){
                            onCatchBudgetListener.onFail("無預算");
                            return;
                        }
                        DocumentSnapshot snapshot = task.getResult();
                        if (snapshot == null || snapshot.get("budget") == null){
                            onCatchBudgetListener.onFail("無預算");
                            return;
                        }
                        long budgetMoney = (long) snapshot.get("budget");
                        onCatchBudgetListener.onSuccess(budgetMoney);
                    }
                });
    }

    @Override
    public String getUserDisplayName() {
        return user.getDisplayName();
    }

    @Override
    public void saveUserBudgetMoney(int budget) {
        Map<String,Object> map = new HashMap<>();
        map.put("budget",budget);
        firebaseFirestore.collection(BUDGET)
                .document(getUserEmail())
                .set(map);
    }

    private void saveUserSecondSortList(ArrayList<SecondSortData> secondSortDataArray) {
        String json = gson.toJson(secondSortDataArray);
        Map<String,Object> map = new HashMap<>();
        map.put("json",json);
        firebaseFirestore.collection(SECOND_SORT_LIST)
                .document(getUserEmail())
                .set(map);
    }


    private void saveUserSortData(String json) {

        Map<String,Object> map = new HashMap<>();
        map.put("json",json);

        firebaseFirestore.collection(SORT_LIST)
                .document(getUserEmail())
                .set(map);
    }

    @Override
    public void getUserMoneyData(final OnFireStoreCatchListener<ArrayList<MoneyObject>> onGetUserMoneyDataListener) {
        DocumentReference docRef = firebaseFirestore.collection(MONEY_LIST)
                .document(getUserEmail());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    onGetUserMoneyDataListener.onFail("無法取得資料");
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    String json = (String) snapshot.get("json");
                    if (json == null) {
                        onGetUserMoneyDataListener.onFail("無法取得資料");
                        return;
                    }
                    ArrayList<MoneyObject> moneyDataArrayList = gson.fromJson(json, new TypeToken<ArrayList<MoneyObject>>() {
                    }.getType());
                    if (moneyDataArrayList == null || moneyDataArrayList.isEmpty()) {
                        onGetUserMoneyDataListener.onFail("無法取得資料");
                        return;
                    }
                    onGetUserMoneyDataListener.onSuccess(moneyDataArrayList);
                } else {
                    onGetUserMoneyDataListener.onFail("無法取得資料");
                    MichaelLog.i("沒資料");
                }
            }
        });


    }

    @Override
    public void updateUserMoneyList(ArrayList<MoneyObject> moneyArrayList) {
        if (moneyArrayList == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("json", "");
            firebaseFirestore.collection(MONEY_LIST)
                    .document(getUserEmail())
                    .set(map);
            return;
        }
        for (MoneyObject data : moneyArrayList) {

            int incomeMoney = 0, exMoney = 0;
            if (data.getMoneyDataArrayList() == null || data.getMoneyDataArrayList().isEmpty()) {
                data.setInComeMoney(incomeMoney);
                data.setExpenditureMoney(exMoney);
                continue;
            }
            for (MoneyData moneyData : data.getMoneyDataArrayList()) {
                if (moneyData.isIncome()) {
                    incomeMoney += moneyData.getTotalMoney();
                }else {
                    exMoney += moneyData.getTotalMoney();
                }
            }
            data.setExpenditureMoney(exMoney);
            data.setInComeMoney(incomeMoney);
        }
        String json = gson.toJson(moneyArrayList);
        Map<String,Object> map = new HashMap<>();
        map.put("json",json);
        firebaseFirestore.collection(MONEY_LIST)
                .document(getUserEmail())
                .set(map);
    }

    @Override
    public void saveUserDescription(final String description) {

        if (description == null || description.isEmpty()){
            MichaelLog.i("描述為空白");
            return;
        }

        firebaseFirestore.collection(DESCRIPTION)
                .document(getUserEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.isSuccessful() || task.getResult() == null){
                            MichaelLog.i("取得Description失敗");
                            createNewDescribe(description);
                            return;
                        }
                        DocumentSnapshot snapshot = task.getResult();
                        if (snapshot == null){
                            MichaelLog.i("取得Description失敗");
                            createNewDescribe(description);
                            return;
                        }
                        String json = (String) snapshot.get("json");
                        if (json == null){
                            MichaelLog.i("取得Description失敗");
                            createNewDescribe(description);
                            return;
                        }
                        ArrayList<String> descriptionArray = gson.fromJson(json,new TypeToken<ArrayList<String>>(){}.getType());
                        if (descriptionArray == null || descriptionArray.isEmpty()){
                            MichaelLog.i("取得Description失敗");
                            createNewDescribe(description);
                            return;
                        }
                        addDescribe(descriptionArray,description);
                    }
                });
    }

    @Override
    public void getUserDescribeData(final OnFireStoreCatchListener<ArrayList<String>> onFireStoreCatchListener) {
        firebaseFirestore.collection(DESCRIPTION)
                .document(getUserEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.isSuccessful() || task.getResult() == null){
                            MichaelLog.i("取得Description失敗");
                            onFireStoreCatchListener.onFail("取得Description失敗");
                            return;
                        }
                        DocumentSnapshot snapshot = task.getResult();
                        if (snapshot == null){
                            MichaelLog.i("取得Description失敗");
                            onFireStoreCatchListener.onFail("取得Description失敗");
                            return;
                        }
                        String json = (String) snapshot.get("json");
                        if (json == null){
                            MichaelLog.i("取得Description失敗");
                            onFireStoreCatchListener.onFail("取得Description失敗");
                            return;
                        }
                        ArrayList<String> descriptionArray = gson.fromJson(json,new TypeToken<ArrayList<String>>(){}.getType());
                        if (descriptionArray == null || descriptionArray.isEmpty()){
                            MichaelLog.i("取得Description失敗");
                            onFireStoreCatchListener.onFail("取得Description失敗");
                            return;
                        }
                        onFireStoreCatchListener.onSuccess(descriptionArray);
                    }
                });
    }



    private void createNewDescribe(String description) {
        ArrayList<String> describeArray = new ArrayList<>();
        describeArray.add(description);
        String json  = gson.toJson(describeArray);
        Map<String,Object> map = new HashMap<>();
        map.put("json",json);
        firebaseFirestore.collection(DESCRIPTION)
                .document(getUserEmail())
                .set(map);
    }

    private void addDescribe(ArrayList<String> descriptionArray, String description) {
        boolean isDataRepeat = false;
        for (String describe : descriptionArray){
            if (describe.equals(description)){
                isDataRepeat = true;
                break;
            }
        }
        if (isDataRepeat){
            return;
        }
        descriptionArray.add(description);
        String json  = gson.toJson(descriptionArray);
        Map<String,Object> map = new HashMap<>();
        map.put("json",json);
        firebaseFirestore.collection(DESCRIPTION)
                .document(getUserEmail())
                .set(map);
    }

    private void addSortList() {
        SortCreateData createData = new SortCreateData();
        createData.setIconUrl(iconUrl);
        createData.setSortType(edContent);
        userSortData.getCreateData().add(createData);
        String json = gson.toJson(userSortData);
        Map<String, Object> map = new HashMap<>();
        map.put("json", json);
        setSortTypeFirestore(map);
    }

    private void setSortTypeFirestore(Map<String, Object> map) {
        firebaseFirestore.collection(SORT_LIST)
                .document(getUserEmail())
                .set(map);
    }

    private void createNewSortList() {
        userSortData = new SortData();
        ArrayList<SortCreateData> createDataArrayList = new ArrayList<>();
        SortCreateData createData = new SortCreateData();
        createData.setIconUrl(iconUrl);
        createData.setSortType(edContent);
        createDataArrayList.add(createData);
        userSortData.setCreateData(createDataArrayList);
        String json = gson.toJson(userSortData);
        Map<String, Object> map = new HashMap<>();
        map.put("json", json);
        setSortTypeFirestore(map);
    }

    private OnCompleteListener<DocumentSnapshot> onCheckHasUserDataListener = new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (!task.isSuccessful() || task.getResult() == null) {
                createNewData();
                return;
            }
            DocumentSnapshot snapshot = task.getResult();
            String json = (String) snapshot.get("json");
            if (json == null) {
                createNewData();
                return;
            }
            moneyObjectArray = gson.fromJson(json, new TypeToken<ArrayList<MoneyObject>>() {
            }.getType());
            boolean isHaveData = moneyObjectArray != null && !moneyObjectArray.isEmpty();
            if (isHaveData) {
                addNewData();
            } else {
                createNewData();
            }

        }
    };

    private void addNewData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN);
        String saveDate = simpleDateFormat.format(new Date(choiceTimeMiles));
        MoneyData moneyData = new MoneyData();
        moneyData.setSortTypeIcon(sortTypeData.getIconUrl());
        moneyData.setSortType(sortTypeData.getSortType());
        moneyData.setTimeMiles(choiceTimeMiles);
        moneyData.setTotalMoney(totalMoney);
        moneyData.setIncome(isIncome);
        moneyData.setDescription(description);
        boolean isFoundData = false;
        for (MoneyObject object : moneyObjectArray) {
            String date = simpleDateFormat.format(new Date(object.getTimeMiles()));
            if (date.equals(saveDate)) {
                object.getMoneyDataArrayList().add(moneyData);
                if (isIncome) {
                    object.setInComeMoney(object.getInComeMoney() + totalMoney);
                } else {
                    object.setExpenditureMoney(object.getExpenditureMoney() + totalMoney);
                }
                isFoundData = true;
                break;
            }
        }
        if (!isFoundData) {
            MoneyObject moneyObject = new MoneyObject();
            moneyObject.setTimeMiles(choiceTimeMiles);
            if (isIncome) {
                moneyObject.setInComeMoney(totalMoney);
            } else {
                moneyObject.setExpenditureMoney(totalMoney);
            }
            ArrayList<MoneyData> moneyDataArrayList = new ArrayList<>();
            moneyDataArrayList.add(moneyData);
            moneyObject.setMoneyDataArrayList(moneyDataArrayList);
            moneyObjectArray.add(moneyObject);
        }
        String moneyJson = gson.toJson(moneyObjectArray);

        Map<String, Object> map = new HashMap<>();
        map.put("json", moneyJson);
        firebaseFirestore.collection(MONEY_LIST)
                .document(getUserEmail())
                .set(map);
    }

    private void createNewData() {
        MoneyData moneyData = new MoneyData();
        moneyData.setSortTypeIcon(sortTypeData.getIconUrl());
        moneyData.setSortType(sortTypeData.getSortType());
        moneyData.setTimeMiles(choiceTimeMiles);
        moneyData.setTotalMoney(totalMoney);
        moneyData.setIncome(isIncome);
        moneyData.setDescription(description);
        ArrayList<MoneyData> moneyDataArray = new ArrayList<>();
        moneyDataArray.add(moneyData);
        MoneyObject moneyObject = new MoneyObject();
        moneyObject.setTimeMiles(choiceTimeMiles);
        if (isIncome) {
            moneyObject.setInComeMoney(totalMoney);
        } else {
            moneyObject.setExpenditureMoney(totalMoney);
        }
        moneyObject.setMoneyDataArrayList(moneyDataArray);
        moneyObjectArray = new ArrayList<>();
        moneyObjectArray.add(moneyObject);
        String moneyJson = gson.toJson(moneyObjectArray);

        Map<String, Object> map = new HashMap<>();
        map.put("json", moneyJson);
        firebaseFirestore.collection(MONEY_LIST)
                .document(getUserEmail())
                .set(map);
    }
}
