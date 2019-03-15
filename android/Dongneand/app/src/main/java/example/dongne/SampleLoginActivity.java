package example.dongne;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.zagle.service.domain.User;

import java.util.HashMap;
import java.util.Map;

import example.dongne.board.MainActivity;

public class SampleLoginActivity extends Activity {
private com.kakao.usermgmt.LoginButton com_kakao_login;
    private SessionCallback callback;

    /**
     * 로그인 버튼을 클릭 했을시 access token을 요청하도록 설정한다.
     *
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_common_kakao_login);
        System.out.println("session정보 뭔데ㅅㅂ"+savedInstanceState);
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback((ISessionCallback) callback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
         //redirectSignupActivity();
            requestMe();

        }


        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
        }
        public void requestMe() {
            UserManagement.getInstance().requestMe(new MeResponseCallback() {

                @Override
                public void onSessionClosed(ErrorResult errorResult) {

                }

                @Override
                public void onNotSignedUp() {

                }

                public void onSuccess(UserProfile userProfile) {


                    final Map<String, String> properties = new HashMap<String, String>();
                    Log.e("SessionCallback :: ", "onSuccess");


                    String acess = Session.getCurrentSession().getAccessToken();
                    String refresh = Session.getCurrentSession().getRefreshToken();
                    String nickname = userProfile.getNickname();

                    String email = userProfile.getEmail();

                    String profileImagePath = userProfile.getProfileImagePath();

                    String thumnailPath = userProfile.getThumbnailImagePath();

                    String UUID = userProfile.getUUID();

                    long id = userProfile.getId();

                    final String stringId = String.valueOf(id);

                    properties.put("id", stringId);


                    Log.e("acess : ", acess + "");

                    Log.e("refresh : ", refresh + "");

                    Log.e("Profile : ", nickname + "");

                    Log.e("Profile : ", email + "");

                    Log.e("Profile : ", profileImagePath + "");

                    Log.e("Profile : ", thumnailPath + "");

                    Log.e("Profile : ", UUID + "");

                    Log.e("Profile : ", id + "");
          AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    RestHttpClient restHttpClient = new RestHttpClient();
                    try {
                        System.out.println("왜 쓰레드 런 안해 시ㅣㅂ");
                        User user1 = restHttpClient.getJsonUser02("K@"+stringId);
                        System.out.println("카카오 결과 나오니??" + user1.getUserBirth());
                        final Intent intent = new Intent(SampleLoginActivity.this, MainActivity.class);
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
                        SampleLoginActivity.this.finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

                }
            });
        }

        protected void redirectSignupActivity() {
            Intent intent = new Intent(SampleLoginActivity.this, GetUserActivity.class);
       /* intent.putExtra("user",user.getSnsNo().toString());
        intent.putExtra("userNickname",user.getUserNickname());
        intent.putExtra("userBirth",String.valueOf(user.getUserBirth()));
        intent.putExtra("userAccount",user.getAccount());
        intent.putExtra("userName",user.getUserName());
        intent.putExtra("userGrade",String.valueOf(user.getGrade()));
        System.out.println("왜 안뜨지???여긴 로그인 에서 인텐트 할려고"+user.getProfile());
        intent.putExtra("userProfile",user.getProfile());*/
            startActivity(intent);
            finish();
        }
    }







}
