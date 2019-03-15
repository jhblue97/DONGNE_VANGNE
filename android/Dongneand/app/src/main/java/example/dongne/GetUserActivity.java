package example.dongne;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import com.zagle.service.domain.User;

import example.dongne.board.MainActivity;

public class GetUserActivity extends AppCompatActivity {

    private TextView userNickname;
    private TextView userAccount;
    private TextView userBirth;
    private TextView userGrade;
    private TextView userName;
    private TextView userAddr;
    private Button btn_userUpdate;
    private ImageView userProfile;

    private User user;


    private Handler handler = new Handler(){
        public void handleMessage(Message message) {
            if (message.what == 100) {
                User user = (User) message.obj;
                System.out.println("GETUSER 100 헨들러 나오나요????" + user);
                userAccount.setText(user.getAccount());
                userNickname.setText(user.getUserNickname());
                userBirth.setText(String.valueOf(user.getUserBirth()));
                userName.setText(user.getUserName());
                Glide.with(GetUserActivity.this).load("http://192.168.0.43:8080/common/images/profile/"+user.getProfile()).into(userProfile);

                if (message.what == 500) {
                }
            }
        }
    };




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getuser);
        Intent intent = GetUserActivity.this.getIntent();
        user = (User)intent.getSerializableExtra("user");
        System.out.println("겟유저 널 체크 "+user);
        Toolbar tool = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tool);
      //  this.UISetting(user);


        btn_userUpdate = (Button)findViewById(R.id.btn_userUpdate);
        btn_userUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(GetUserActivity.this, UpdateuserActivity.class);
                intent.putExtra("user",user);
                startActivityForResult(intent,1005);
            }

        });


  /*      Intent intent = this.getIntent(); //intent 불러와서
        String message = intent.getStringExtra("user");*/


            //final String snsNo = intent.getStringExtra("user");
            //System.out.println("sns 넘버 가져오니?? getUserActivity"+snsNo);
           // System.out.println("getuser.........."+intent.getStringExtra("user"));

            this.userAccount = (TextView)findViewById(R.id.userAccount);
            this.userNickname = (TextView)findViewById(R.id.userNickname);
            this.userBirth = (TextView)findViewById(R.id.userBirth);
            this.userGrade = (TextView)findViewById(R.id.userGrade);
            this.userName = (TextView)findViewById(R.id.userName);
            this.userAddr = (TextView)findViewById(R.id.userAddr);
            this.userProfile = (ImageView) findViewById(R.id.userProfile);


        /*this.userAccount = (TextView)findViewById(R.id.userAccount);
        userAccount.setText(intent.getStringExtra("userAccount"));

        this.userNickname = (TextView)findViewById(R.id.userNickname);
        userNickname.setText(intent.getStringExtra("userNickname"));

        this.userBirth = (TextView)findViewById(R.id.userBirth);
        userBirth.setText(intent.getStringExtra("userBirth"));

        this.userGrade = (TextView)findViewById(R.id.userGrade);
        userGrade.setText(intent.getStringExtra("userGrade"));

        this.userName = (TextView)findViewById(R.id.userName);
        userName.setText(intent.getStringExtra("userName"));

        this.userProfile = (ImageView) findViewById(R.id.userProfile);
       System.out.println("왜 안떠??겟유저 프로파일"+intent.getStringExtra("userProfile"));
*/
        if(user.getAccount()!=null){
            userAccount.setText(user.getAccount());
        }else{
            userAccount.setText("");
        }

            userNickname.setText(user.getUserNickname());
            userBirth.setText(String.valueOf(user.getUserBirth()));
            if(user.getGrade().equals("0")){
                userGrade.setText("당신은 흙수저입니다");
            }else if(user.getGrade().equals("1")){
                userGrade.setText("당신은 동수저입니다");
            }else if(user.getGrade().equals("2")){
                userGrade.setText("당신은 은수저입니다");
            }else if(user.getGrade().equals("3")){
                userGrade.setText("당신은 금수저입니다");
            }
            //userGrade.setText(user.getGrade());
            userName.setText(user.getUserName());
            userAddr.setText(user.getUserAddr());
      Glide.with(GetUserActivity.this).load("http://192.168.0.43:8080/common/images/profile/"+user.getProfile()).into(userProfile);
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(userProfile);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("엑티비티리절트" + requestCode);
        System.out.println("인텐트 데이타" + data.getStringExtra("userNo"));
        final String userNo = data.getStringExtra("userNo");

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
        // LoginActivity 에서 요청할 때 보낸 요청 코드 (3000)
                case 3000:
                    System.out.println("3000입니다~!~!~!~!");
                    AsyncTask.execute(new Runnable() {

                        @Override
                        public void run() {

                            RestHttpClient restHttpClient = new RestHttpClient();
                            try {

                                System.out.println("중복체크하기전 닉넴 불러오기" + userNickname.getText().toString());
                                User user = restHttpClient.getJsonUser03(userNo);

                                Message message = new Message();
                                message.what = 100;
                                message.obj = user;
                                GetUserActivity.this.handler.sendMessage(message);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                case 1005:
                    System.out.println("왜ㅐ 여기로 오는거지 1005");
                    AsyncTask.execute(new Runnable() {

                        @Override
                        public void run() {

                            RestHttpClient restHttpClient = new RestHttpClient();
                            try {

                                System.out.println("수정완료후 겟유저" + userNo);
                                user = restHttpClient.getJsonUser01(userNo);
                                System.out.println("다시 찍어본다 지역변수"+user);
                                Message message = new Message();
                                message.what = 100;
                                message.obj = user;
                                GetUserActivity.this.handler.sendMessage(message);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    break;

            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

 @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.miCompose:
                //Toast.makeText(this,"위에거클릭!",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(GetUserActivity.this,MainActivity.class);
               intent.putExtra("user",user);
              // setResult(RESULT_OK,intent);
               startActivity(intent);
                finish();
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        System.out.println("back키 도착");
        super.onBackPressed();

    }
}
