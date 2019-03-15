package example.dongne.mypage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zagle.service.domain.Board;
import com.zagle.service.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.dongne.R;
import example.dongne.RestHttpClient;
import example.dongne.board.ListBoardOneActivity;
import example.dongne.board.MainActivity;

public class MypageListScrap extends AppCompatActivity {
    private ListView listView;
    private Board board;
    private MypageListViewAdapter adapter;
    private ArrayList<MypageListViewItem> mypage_itemArrayList= new ArrayList<MypageListViewItem>();
   // private ArrayList<MypageListViewItem> listViewItems = new ArrayList<MypageListViewItem>();
   int j=0;
    private User user;
    private Handler handler = new Handler(){
        public void handleMessage(Message message) {
            if (message.what ==0) {
                Map<String, Object> map = new HashMap<String, Object>();
                map = (HashMap)message.obj;
                System.out.println(map);
                List<Board> listBoard = new ArrayList<Board>();
                ArrayList<String> listBoardNo = new ArrayList<String>();

                listBoard = (ArrayList<Board>) map.get("listMyBoard");
                System.out.println("헨들러 에서 확인"+listBoard);
                for (int i = 0; i < listBoard.size(); i++) {
                        j=j+1;
                    adapter.addItem(listBoard.get(i).getBoardDetailText()
                            ,String.valueOf(listBoard.get(i).getBoardRegDate())
                            ,String.valueOf(j),"1"
                            ,String.valueOf(listBoard.get(i).getBoardNo())
                            ,user.getUserNo()
                            ,listBoard.get(i).getCheckLike()
                            ,listBoard.get(i));
                    System.out.println("어뎁터가 뭔데 시바");
                    listView.setAdapter(adapter);
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("좋아요한 게시물");
        setContentView(R.layout.activity_mypagelistscrap);
        Toolbar tool = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        Intent intent = MypageListScrap.this.getIntent();
        user = (User)intent.getSerializableExtra("user");

        listView = (ListView)findViewById(R.id.my_listview);
        adapter = new MypageListViewAdapter(this,mypage_itemArrayList);

        AsyncTask.execute(new Runnable() {

            @Override
            public void run() {
                try {

                    Map<String, Object> map = new HashMap<String, Object>();
                    System.out.println("httprest보내기전 확인"+user.getUserNo());
                    map = RestHttpClient.listMyScrap_JsonSimple(user.getUserNo());
                    ArrayList<Board> boards = new ArrayList<Board>();
                    boards =(ArrayList<Board>) map.get("listMyBoard");
                    board = new Board();
                    String checkLike;
                    for(int i=0;i<boards.size();i++){
                        board = boards.get(i);
                        if (RestHttpClient.getCheckLike(user.getUserNo(),board.getBoardNo()).equals("1")){
                            board.setCheckLike("1");
                        }else {
                            board.setCheckLike("0");
                        }
                    }
                    System.out.println("httprest다녀온후 확인"+map);
                    Message message = new Message();
                    message.what=0;
                    message.obj=map;
                    handler.sendMessage(message);
                   // MypageListBoard.this.handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                final MypageListViewItem item = (MypageListViewItem)parent.getItemAtPosition(position);
               /* Intent intent = new Intent(MypageListBoard.this,ListBoardOneActivity.class);
                intent.putExtra("board",item.getBoard());
                startActivity(intent);
                finish();*/
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Map<String, Object> map = new HashMap<String, Object>();
                            System.out.println("httprest보내기전 확인"+item.getBoardNo());
                            Board board = RestHttpClient.getBoard(item.getBoard().getBoardNo());
                            Intent intent = new Intent(MypageListScrap.this,ListBoardOneActivity.class);
                            board.setLikeUserNo(user.getUserNo());
                            item.getBoard().setUser(user);
                            intent.putExtra("board",item.getBoard());
                            intent.putExtra("user",user);
                            intent.putExtra("flag","listMyLike");
                            startActivity(intent);

                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
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
              //  Toast.makeText(this,"위에거클릭!",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MypageListScrap.this,MainActivity.class);
                intent.putExtra("user",user);
                setResult(RESULT_OK,intent);
                finish();
                break;

        }
        return true;
    }
}
