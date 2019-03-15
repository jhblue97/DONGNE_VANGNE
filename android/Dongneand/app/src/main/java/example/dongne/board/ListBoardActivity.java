package example.dongne.board;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.zagle.service.domain.Board;
import com.zagle.service.domain.Comment;
import com.zagle.service.domain.Local;
import com.zagle.service.domain.Page;
import com.zagle.service.domain.SearchBoard;
import com.zagle.service.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.dongne.R;
import example.dongne.mypage.MypageListBoard;

public class ListBoardActivity extends AppCompatActivity {
    private User user;
    private String countLike;
    private ImageButton btnLike;
    private ListView listView;
    private ListViewAdapter adapter;
    private ArrayList<ListViewItem> listViewItems = new ArrayList<ListViewItem>();
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ListViewItem item = new ListViewItem();
            item = listViewItems.get((Integer)v.getTag());
            final String boardNo = item.getBoard().getBoardNo();
            if (item.getCheckLike().equals("0")){

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            RestHttpClient.addLike("US10001",boardNo);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                item.getBoard().setCheckLike("1");
                item.getBoard().setLikeCount(Integer.parseInt(item.getBoardLike())+1);
            }else if (item.getCheckLike().equals("1")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            RestHttpClient.updateLike("US10001",boardNo,"1");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                item.getBoard().setLikeCount(Integer.parseInt(item.getBoardLike())-1);
                item.getBoard().setCheckLike("2");
            }else if (item.getCheckLike().equals("2")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            RestHttpClient.updateLike("US10001",boardNo,"2");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                item.getBoard().setCheckLike("1");
                item.getBoard().setLikeCount(Integer.parseInt(item.getBoardLike())+1);
            }


            adapter.notifyDataSetChanged();
        }
    };
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                Map<String, Object> map = new HashMap<String, Object>();
                map = (HashMap)msg.obj;
                System.out.println(map);
                List<Board> listBoard = new ArrayList<Board>();
                List<Local> listLocal = new ArrayList<Local>();
                ArrayList<String> listBoardNo = new ArrayList<String>();

                Page resultPage = (Page) map.get("resultPage");
                SearchBoard searchBoard = (SearchBoard) map.get("searchBoard");
                listBoard = (ArrayList<Board>) map.get("listBoard");
                listLocal = (ArrayList<Local>) map.get("listLocal");
                for (int i = 0; i < listBoard.size(); i++) {
                    Board itemBoard = new Board();
                    itemBoard = listBoard.get(i);
                    System.out.println(i+"번째 보드 : "+itemBoard);
                    Comment comment = new Comment();
                    if (itemBoard.getListComment().size()==0){
                        User user = new User();
                        user.setProfile("default.png");
                        comment.setUser(user);
                        comment.setCommentDetailText("댓글을작성해주세요");
                    }else {
                        comment = itemBoard.getListComment().get(itemBoard.getListComment().size()-1);
                    }
                    btnLike = (ImageButton) findViewById(R.id.btnLike);
                    String likeUserCheck = "";
                    if (itemBoard.getLikeUserNo()==null&&itemBoard.getCheckLike().equals("0")){
                        likeUserCheck="0";
                    }else if (itemBoard.getLikeUserNo().equals(user.getUserNo())&&itemBoard.getCheckLike().equals("1")){
                        likeUserCheck="1";
                    }else if (itemBoard.getLikeUserNo().equals(user.getUserNo())&&itemBoard.getCheckLike().equals("2")){
                        likeUserCheck="2";
                    }System.out.println("이게 다 잘 나와줘야됩니다 테마 ==="+itemBoard.getUserTheme());
                    adapter.addItem(itemBoard
                            ,itemBoard.getUser().getProfile()
                            , itemBoard.getPhoto1()
                            , itemBoard.getUser().getUserNickname()
                            , Integer.toString(itemBoard.getLikeCount())
                            ,comment.getUser().getProfile()
                            ,comment.getCommentDetailText()
                            ,itemBoard.getBoardDetailText()
                            ,btnLike
                            ,onClickListener
                            ,likeUserCheck);
                    listView.setAdapter(adapter);
                }
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_board);

        setTitle("DongneVangne");
        Toolbar tool = (Toolbar)findViewById(R.id.toolbarBoard);
        setSupportActionBar(tool);
        user = new User();
        Intent intent = getIntent();
        user=(User) intent.getSerializableExtra("user");
        System.out.println("리스트보드안에 유저 : "+user);
        listView=(ListView)findViewById(R.id.list_view);
        adapter = new ListViewAdapter(this,listViewItems,onClickListener);
        MyThread myThread=new MyThread(handler);
        myThread.start();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListViewItem item = (ListViewItem)parent.getItemAtPosition(position);
                Intent intent = new Intent(ListBoardActivity.this,ListBoardOneActivity.class);
                intent.putExtra("board",item.getBoard());
                intent.putExtra("user",user);
                intent.putExtra("flag","listBoard");
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    class MyThread extends Thread{
        Handler handler;
        public MyThread(Handler handler){
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                Map<String, Object> map = new HashMap<String, Object>();
                map = RestHttpClient.listBoard_JsonSimple(user.getUserNo());

                Message message = new Message();
                message.what=0;
                message.obj=map;

                handler.sendMessage(message);
            }catch (Exception e){
                e.printStackTrace();
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
                //  Toast.makeText(this,"위에거클릭!",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ListBoardActivity.this,MainActivity.class);
                intent.putExtra("user",user);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
        return true;
    }
}
