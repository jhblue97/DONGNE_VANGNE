package example.dongne.board;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.zagle.service.domain.User;

import example.dongne.GetUserActivity;
import example.dongne.LoginActivity;
import example.dongne.R;
import example.dongne.mypage.MypageListBoard;
import example.dongne.mypage.MypageListComment;
import example.dongne.mypage.MypageListScrap;


public class MainActivity extends AppCompatActivity {
    private ImageButton btnLogout;
    private ImageButton btnGetUesr;
    private ImageButton btnListBoard;
    private ImageButton btnMyList;
    private ImageButton btnMyScrap;
    private ImageButton btnMyComment;
    private User user;
    private LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tool = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        setTitle("Dongne Vangne");
        System.out.println("=============여긴 메인 엑티비티 입니다==================");
        Intent intent = MainActivity.this.getIntent();
        user = (User)intent.getSerializableExtra("user");
        System.out.println("메인엑티비티 첫번째 널체크"+user);
        btnLogout = (ImageButton)findViewById(R.id.btn_logout);
        btnGetUesr = (ImageButton)findViewById(R.id.btn_user);
        btnListBoard = (ImageButton)findViewById(R.id.btn_list_board);
        btnMyList = (ImageButton)findViewById(R.id.btnMyList);
        btnMyScrap = (ImageButton)findViewById(R.id.btnMyScrap);
        btnMyComment = (ImageButton)findViewById(R.id.btnMyComment);

        btnMyComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MypageListComment.class);
                intent.putExtra("user",user);
                startActivity(intent);

            }
        });



        btnMyScrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MypageListScrap.class);
                intent.putExtra("user",user);
                startActivity(intent);

            }
        });

        btnMyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,MypageListBoard.class);
                    intent.putExtra("user",user);
                    startActivity(intent);

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);*/
              //  LoginActivity.kakaoLog_out();
               // LoginActivity.signOut();
               // MainActivity.this.finish();
                MainActivity.this.finish();

            }
        });
        btnGetUesr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent1 = new Intent(MainActivity.this,GetUserActivity.class);
                intent1.putExtra("user",user);
                //startActivityForResult(intent1,1004);
                startActivity(intent1);
            }
        });
        btnListBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListBoardActivity.class);
                 intent.putExtra("user",user);
                startActivity(intent);
            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("메인 엑티비티리절트"+requestCode);
       // System.out.println("인텐트 데이타"+data.getStringExtra("userName"));
        //final String userName = data.getStringExtra("userName");
            switch (requestCode){
                case 1004:
                    System.out.println("Main 왜ㅐ 여기로 오는거지 1004");
                   user = (User)data.getSerializableExtra("user");
                    System.out.println("user정보"+user);
                    break;
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
                Toast.makeText(this,"위에거클릭!",Toast.LENGTH_LONG).show();
                break;

        }
        return true;
    }
}
