package example.dongne;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.zagle.service.domain.User;

import java.lang.ref.WeakReference;

import example.dongne.board.MainActivity;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private ImageButton btn_custom_login;
    private ImageButton btn_logout;
    private static final int RC_SIGN_IN = 1000;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private ImageButton btn_kakaologout;
    SignInButton Google_Login;
    private Context mContext;

    public static OAuthLogin mOAuthLoginModule;
    private ImageButton naverButton;

    private OAuthLoginHandler mOAuthLoginHandler = new NaverLoginHandler(this);



    private static class NaverLoginHandler extends OAuthLoginHandler {


        private final WeakReference<LoginActivity> mActivity;

        public NaverLoginHandler(LoginActivity activity) {
            mActivity = new WeakReference<LoginActivity>(activity);
        }


        @Override
        public void run(boolean success) {
            LoginActivity activity = mActivity.get();

            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(activity);
                String refreshToken = mOAuthLoginModule.getRefreshToken(activity);
                long expiresAt = mOAuthLoginModule.getExpiresAt(activity);
                String tokenType = mOAuthLoginModule.getTokenType(activity);
            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(activity).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(activity);
                Toast.makeText(activity, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);


        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                LoginActivity.this,
                "_vnpgFiMPJZnpF7AE2b6",
                "RVjBk1H2W4",
                "clientName"
        );
        naverButton = (ImageButton) findViewById(R.id.naverButton);
        naverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOAuthLoginModule.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);
            }
        });
        btn_custom_login = (ImageButton) findViewById(R.id.btn_custom_login);
        btn_custom_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SampleLoginActivity.class);
                startActivity(intent);
            }
        });

        btn_kakaologout = (ImageButton)findViewById(R.id.btn_kakaologout);


        btn_kakaologout.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "로그아웃성공", Toast.LENGTH_SHORT).show();
            LoginActivity.this.kakaoLog_out();
             /*   UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {

                        Toast.makeText(LoginActivity.this, "로그아웃 성공", Toast.LENGTH_SHORT).show();

                    }
                });*/
            }

        });



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        mAuth = FirebaseAuth.getInstance();

        Google_Login = findViewById(R.id.Google_Login);
        Google_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent,RC_SIGN_IN);
            }
        });
        mContext = getApplicationContext();


        ImageButton btn_logoutGoogle = (ImageButton) findViewById(R.id.btn_logoutGoogle);
        btn_logoutGoogle.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(final View view) {

                Log.v("알림", "구글 LOGOUT");
                signOut();

            }
        });


    }



    public static void kakaoLog_out(){

        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {

                //Toast.makeText(LoginActivity.this, "로그아웃 성공", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("엑티비티리절트"+requestCode);
        System.out.println("인텐트 데이타"+data);

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN) {
            System.out.println("여기까진 왔는데..");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            System.out.println("이거 인증결과"+result);
            if (result.isSuccess()) {
                System.out.println("구글로그인 성공해서 인증해야됨");
                //구글 로그인 성공해서 파베에 인증
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            }else{
                //구글 로그인 실패
            }
        }


    }
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "인증 실패", Toast.LENGTH_SHORT).show();
                        }else{
                            System.out.println("토큰값 이게 맞나??"+acct.getIdToken());
                            System.out.println("이건 뭐지"+acct.getServerAuthCode());
                            System.out.println("이건머야"+acct.getId());

                            Toast.makeText(LoginActivity.this, "구글 로그인 인증 성공", Toast.LENGTH_SHORT).show();
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    RestHttpClient restHttpClient = new RestHttpClient();
                                    try {
                                        User user1 = restHttpClient.getJsonUser02("G@"+acct.getId());
                                        System.out.println("결과 나오니??" + user1.getUserBirth());
                                          final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                         intent.putExtra("user",user1);
        /*                                 intent.putExtra("userNickname",user1.getUserNickname());
                                         intent.putExtra("userBirth",String.valueOf(user1.getUserBirth()));
                                         intent.putExtra("userAccount",user1.getAccount());
                                         intent.putExtra("userName",user1.getUserName());
                                         intent.putExtra("userGrade",String.valueOf(user1.getGrade()));*/
                                        System.out.println("왜 안뜨지???여긴 로그인 에서 인텐트 할려고"+user1.getProfile());

                                        //intent.putExtra("userProfile",user1.getProfile());
                                       // GO(user1);
                                         startActivity(intent);
                                   //     startActivityForResult(intent,3000);
                                        // LoginActivity.this.finish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public void signOut() {

        System.out.println("구글로그아웃 시작");

        mGoogleApiClient.connect();

        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

            @Override

            public void onConnected(@Nullable Bundle bundle) {

                mAuth.signOut();

                if (mGoogleApiClient.isConnected()) {

                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {

                        @Override

                        public void onResult(@NonNull Status status) {

                            if (status.isSuccess()) {

                                Log.v("알림", "로그아웃 성공");

                                setResult(1);

                            } else {

                                setResult(0);

                            }

                            //finish();

                        }

                    });

                }

            }

            @Override
            public void onConnectionSuspended(int i) {

            }


        });
    }
}




