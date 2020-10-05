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
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.money.moneyreminder.sort.MoneyData;
import com.money.moneyreminder.sort.MoneyObject;
import com.money.moneyreminder.sort_list.IconData;
import com.money.moneyreminder.sort_list.presenter.SortCreateData;
import com.money.moneyreminder.sort_list.presenter.SortData;
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

    private static final String DESCRIPTION = "description";

    private ArrayList<MoneyObject> moneyObjectArray;

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
            Log.i("Michael", "娶不到UserEmail");
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
                    onFireStoreCatchListener.onFail("無法取得資料");
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    String json = (String) snapshot.get("json");
                    if (json == null) {
                        onFireStoreCatchListener.onFail("無法取得資料");
                        return;
                    }
                    userSortData = gson.fromJson(json, SortData.class);
                    if (userSortData == null) {
                        onFireStoreCatchListener.onFail("無法取得資料");
                        return;
                    }
                    onFireStoreCatchListener.onSuccess(userSortData);
                } else {
                    onFireStoreCatchListener.onFail("無法取得資料");
                    Log.i("Michael", "沒資料");
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
                    Log.i("Michael", "沒資料");
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
            Log.i("Michael","描述為空白");
            return;
        }

        firebaseFirestore.collection(DESCRIPTION)
                .document(getUserEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.isSuccessful() || task.getResult() == null){
                            Log.i("Michael","取得Description失敗");
                            createNewDescribe(description);
                            return;
                        }
                        DocumentSnapshot snapshot = task.getResult();
                        if (snapshot == null){
                            Log.i("Michael","取得Description失敗");
                            createNewDescribe(description);
                            return;
                        }
                        String json = (String) snapshot.get("json");
                        if (json == null){
                            Log.i("Michael","取得Description失敗");
                            createNewDescribe(description);
                            return;
                        }
                        ArrayList<String> descriptionArray = gson.fromJson(json,new TypeToken<ArrayList<String>>(){}.getType());
                        if (descriptionArray == null || descriptionArray.isEmpty()){
                            Log.i("Michael","取得Description失敗");
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
                            Log.i("Michael","取得Description失敗");
                            onFireStoreCatchListener.onFail("取得Description失敗");
                            return;
                        }
                        DocumentSnapshot snapshot = task.getResult();
                        if (snapshot == null){
                            Log.i("Michael","取得Description失敗");
                            onFireStoreCatchListener.onFail("取得Description失敗");
                            return;
                        }
                        String json = (String) snapshot.get("json");
                        if (json == null){
                            Log.i("Michael","取得Description失敗");
                            onFireStoreCatchListener.onFail("取得Description失敗");
                            return;
                        }
                        ArrayList<String> descriptionArray = gson.fromJson(json,new TypeToken<ArrayList<String>>(){}.getType());
                        if (descriptionArray == null || descriptionArray.isEmpty()){
                            Log.i("Michael","取得Description失敗");
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
