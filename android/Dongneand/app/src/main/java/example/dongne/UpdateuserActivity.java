package example.dongne;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zagle.service.domain.User;

public class UpdateuserActivity extends AppCompatActivity {
    private EditText userNickname;
    private TextView userAccount;
    private EditText userBirth;
    private TextView userGrade;
    private EditText userName;
    private Button btn_nickNameCheck;
    private Button btn_userUpdateOk;
    private TextView checkMessage;
    private TextView userAddr;
    private User user;


    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            if(message.what==100){
                String fromHostData = (String) message.obj;
                  if(fromHostData.equals("0")){

                    UpdateuserActivity.this.checkMessage = (TextView)findViewById(R.id.checkMessage);
                    checkMessage.setText("중복아닙니다.");
                    btn_userUpdateOk = (Button)findViewById(R.id.btn_userUpdateOk);
                    btn_userUpdateOk.setEnabled(true);
                }else{
                    UpdateuserActivity.this.checkMessage = (TextView)findViewById(R.id.checkMessage);
                    checkMessage.setText("중복입니다.");

                    btn_userUpdateOk = (Button)findViewById(R.id.btn_userUpdateOk);
                    btn_userUpdateOk.setEnabled(false);
                }
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_updateuser);
        Intent intent = UpdateuserActivity.this.getIntent();
        user = (User)intent.getSerializableExtra("user");

        this.userAccount = (TextView)findViewById(R.id.userAccount);
       // userAccount.setText(user.getAccount());
        if(user.getAccount()!=null){
            userAccount.setText(user.getAccount());
        }else{
            userAccount.setText("");
        }

        this.userNickname = (EditText)findViewById(R.id.userNickname);
        userNickname.setText(user.getUserNickname());

        this.userBirth = (EditText)findViewById(R.id.userBirth);
        userBirth.setText(String.valueOf(user.getUserBirth()));

        this.userGrade = (TextView)findViewById(R.id.userGrade);
        userGrade.setText(user.getGrade());

        this.userName = (EditText)findViewById(R.id.userName);
        userName.setText(user.getUserName());


        this.userAddr = (TextView)findViewById(R.id.userAddr);
        userAddr.setText(user.getUserAddr());


        btn_nickNameCheck = (Button)findViewById(R.id.btn_nickNameCheck);

         btn_userUpdateOk = (Button)findViewById(R.id.btn_userUpdateOk);

        btn_userUpdateOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

               // user.setUserNickname(userNickname.getText().toString());
               user.setUserBirth(Integer.parseInt(userBirth.getText().toString()));
               user.setUserNickname(userNickname.getText().toString());
               user.setUserName(userName.getText().toString());

               System.out.println("클릭하는순간===="+user.getUserBirth());
                  AsyncTask.execute(new Runnable() {

                    @Override
                    public void run() {

                        RestHttpClient restHttpClient = new RestHttpClient();
                        try {
                            User user1 = restHttpClient.getJsonUserUpdate(user);
                            final Intent resultIntent = new Intent();
                            resultIntent.putExtra("userNo",user.getUserNo());
                              setResult(RESULT_OK,resultIntent);
                           // startActivityForResult(resultIntent,3000);
                         finish();
                         //   Message message = new Message();
                          //  message.what=500;
                          //  message.obj = user1;
                          //  UpdateuserActivity.this.handler.sendMessage(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });



            }

        });

        btn_nickNameCheck.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                AsyncTask.execute(new Runnable() {

                    @Override
                    public void run() {

                        RestHttpClient restHttpClient = new RestHttpClient();
                        try {

                            System.out.println("중복체크하기전 닉넴 불러오기"+userNickname.getText().toString());
                            String result = restHttpClient.getJsonUserNicknameCheck(userNickname.getText().toString());
                            if(result.equals("0")){

                              //  this.checkMessage = (TextView)findViewById(R.id.checkMessage);
                               //checkMessage.setText("중복아닙니다!!!!!!");
                                Message message = new Message();
                                message.what=100;
                                message.obj = result;
                                UpdateuserActivity.this.handler.sendMessage(message);
                            }else{

                               // this.checkMessage = (TextView)findViewById(R.id.checkMessage);
                              //  checkMessage.setText("중복입니다!!!!!!");
                                Message message = new Message();
                                message.what=100;
                                message.obj = result;
                                UpdateuserActivity.this.handler.sendMessage(message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        }
        });
      //  Intent intent = this.getIntent(); //intent 불러와서

       /* this.userAccount = (TextView)findViewById(R.id.userAccount);
        userAccount.setText(intent.getStringExtra("userAccount"));


        this.userNickname = (EditText)findViewById(R.id.userNickname);
        userNickname.setText(intent.getStringExtra("userNickname"));

        this.userBirth = (EditText)findViewById(R.id.userBirth);
        userBirth.setText(intent.getStringExtra("userBirth"));

        this.userGrade = (TextView)findViewById(R.id.userGrade);
        userGrade.setText(intent.getStringExtra("userGrade"));

        this.userName = (EditText)findViewById(R.id.userName);
        userName.setText(intent.getStringExtra("userName"));*/


    }

}
