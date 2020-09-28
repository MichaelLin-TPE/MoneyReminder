package com.money.moneyreminder;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.money.moneyreminder.tool.MoneyReminderApplication;

import java.util.HashMap;
import java.util.Map;

public class LoginHandlerImpl implements LoginHandler {

    private FirebaseUser user;

    private FirebaseAuth mAuth;

    private FirebaseFirestore firebaseFirestore;

    private Gson gson;

    private GoogleSignInClient googleSignInClient;

    private static final String USER = "users";

    private static final String USER_JSON = "user_json";



    private static final int RC_SIGN_IN = 100;

    private OnGoogleLoginListener googleLoginListener;

    public LoginHandlerImpl() {
        gson = new Gson();
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(MoneyReminderApplication.getInstance().getApplicationContext().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(MoneyReminderApplication.getInstance().getApplicationContext(),options);
    }

    @Override
    public boolean isLogin() {
        return user != null;
    }

    @Override
    public void onDoLogin(OnGoogleLoginListener onGoogleLoginListener) {
        this.googleLoginListener = onGoogleLoginListener;
        Intent signIntent = googleSignInClient.getSignInIntent();
        MoneyReminderApplication.getInstance().getMainActivity().startActivityForResult(signIntent,RC_SIGN_IN);
    }

    @Override
    public void onHandleActivityResult(Intent data, int requestCode) {
        try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account == null){
                googleLoginListener.onFail("登入失敗");
                return;
            }
            String loginIdToken = account.getIdToken();
            if (loginIdToken == null){
                googleLoginListener.onFail("Google LoginIdToken is null");
                return;
            }
            AuthCredential authCredential = GoogleAuthProvider.getCredential(loginIdToken,null);
            mAuth.signInWithCredential(authCredential)
                    .addOnCompleteListener(onFirebaseAuthListener);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDoLogOut(final OnGoogleLogoutListener onGoogleLogoutListener) {
        mAuth.signOut();
        googleSignInClient.revokeAccess().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    onGoogleLogoutListener.onLogoutSuccess();
                }
            }
        });
    }

    private OnCompleteListener<AuthResult> onFirebaseAuthListener = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (!task.isSuccessful()){
                googleLoginListener.onFail("AuthResult Task Fail.");
                return;
            }
            user = mAuth.getCurrentUser();

            if (user == null || user.getEmail() == null){
                googleLoginListener.onFail("User Email is Null.");
                return;
            }

            final String userEmail = user.getEmail();

            firebaseFirestore.collection(USER)
                    .document(userEmail)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (!task.isSuccessful() || task.getResult() == null){
                                doRegisterAccount(userEmail);
                                return;
                            }
                            DocumentSnapshot snapshot = task.getResult();

                            if (snapshot == null){
                                doRegisterAccount(userEmail);
                                return;
                            }

                            String userJson  = (String) snapshot.get(USER_JSON);
                            if (userJson == null){
                                doRegisterAccount(userEmail);
                                return;
                            }
                            googleLoginListener.onSuccess();
                        }
                    });



        }

        private void doRegisterAccount(String userEmail) {
            UserData userData = new UserData();
            userData.setUserEmail(userEmail);
            userData.setUserPhoto("");
            userData.setUserName("");
            userData.setUserCreateTimeMiles(System.currentTimeMillis());

            String userJson = gson.toJson(userData);

            Map<String,Object> map = new HashMap<>();
            map.put(USER_JSON,userJson);
            firebaseFirestore.collection(USER)
                    .document(userEmail)
                    .set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                googleLoginListener.onSuccess();
                            }
                        }
                    });
        }
    };
}
