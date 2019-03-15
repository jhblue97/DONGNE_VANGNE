package example.dongne.board;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kakao.kakaonavi.KakaoNaviParams;
import com.kakao.kakaonavi.KakaoNaviService;
import com.kakao.kakaonavi.Location;
import com.kakao.kakaonavi.NaviOptions;
import com.kakao.kakaonavi.options.CoordType;
import com.zagle.service.domain.Board;
import com.zagle.service.domain.Comment;
import com.zagle.service.domain.User;

import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import example.dongne.R;
import example.dongne.mypage.MypageListBoard;
import example.dongne.mypage.MypageListComment;
import example.dongne.mypage.MypageListScrap;

public class ListBoardOneActivity extends AppCompatActivity {
    private String flag;
    private User user;
    private EditText addCommentText;
    private ImageButton addCommentBtn;
    private ImageView userProfile;
    private ImageButton btnLike;
    private ImageButton test;
    private TextView userNickname;
    private ImageView photo1;
    private ImageView photo2;
    private ImageView photo3;
    private TextView boardLike;
    private TextView boardDetail;
    private TextView address;
    private ListView listComment;
    private ListCommentAdapter adapter;
    private Board board;
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                Board setBoard = new Board();
                User setUser = new User();
                setBoard =(Board)msg.obj;

                System.out.println("헨들러 리스트보드원"+setBoard);
                System.out.println("헨들러 보드"+board);
                System.out.println("헨들러 유저 "+user);
                Glide.with(ListBoardOneActivity.this).load("http://192.168.0.43:8080/common/images/profile/"+board.getUser().getProfile()).into(userProfile);
                userNickname.setText(setBoard.getUser().getUserNickname());
                if (setBoard.getPhoto1()!=null){
                    Glide.with(ListBoardOneActivity.this).load(setBoard.getPhoto1()).into(photo1);
                    if (setBoard.getPhoto2()!=null){
                        Glide.with(ListBoardOneActivity.this).load(setBoard.getPhoto2()).into(photo2);
                        if (setBoard.getPhoto3()!=null){
                            Glide.with(ListBoardOneActivity.this).load(setBoard.getPhoto3()).into(photo3);
                        }
                    }
                }

                if (setBoard.getLikeUserNo()==null&&(setBoard.getCheckLike()==null||setBoard.getCheckLike().equals("0"))){
                    btnLike.setImageResource(R.drawable.like1);
                }else if (setBoard.getLikeUserNo().equals(user.getUserNo())&&setBoard.getCheckLike().equals("1")){
                    btnLike.setImageResource(R.drawable.like2);
                }else if (setBoard.getLikeUserNo().equals(user.getUserNo())&&setBoard.getCheckLike().equals("2")){
                    btnLike.setImageResource(R.drawable.like1);
                }
                boardLike.setText(String.valueOf(setBoard.getLikeCount()));
                boardDetail.setText(Html.fromHtml(setBoard.getBoardDetailText()).toString());
                address.setText(setBoard.getAddress());
                comments = setBoard.getListComment();
                Comment commentEx = new Comment();
                if (comments==null){
                    comments = new ArrayList<>();
                }else {
                    for (int i=0;i<comments.size();i++){
                        if (comments.size()==0){
                            User userEx = new User();
                            userEx.setProfile("default.png");
                            commentEx.setCommentDetailText("첫번째로 댓글을 입력해주세요!");
                            commentEx.setCommentRegDate(null);
                        }else {
                            commentEx = comments.get(i);
                        }
                        adapter.addItem(commentEx.getUser().getProfile()
                                ,commentEx.getCommentDetailText()
                                ,commentEx.getCommentRegDate());
                        listComment.setAdapter(adapter);
                    }
                }
            }else if (msg.what==1){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
                Date date = new Date();
                simpleDateFormat.format(date);
                java.sql.Date now = new java.sql.Date(date.getTime());

                adapter.addItem(((User)msg.obj).getProfile(),addCommentText.getText().toString(),now);
                addCommentText.setText("");
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = null;
        if (flag.equals("listBoard")){
            intent = new Intent(this,ListBoardActivity.class);
        }else if (flag.equals("listMyBoard")){
            intent = new Intent(this,MypageListBoard.class);
        }else if (flag.equals("listMyComment")){
            intent = new Intent(this,MypageListComment.class);
        }else if (flag.equals("listMyLike")){
            intent = new Intent(this,MypageListScrap.class);
        }
        intent.putExtra("user",user);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_board);
        Intent intent = getIntent();
        board = new Board();
        user = new User();
        flag = intent.getStringExtra("flag");

         board = (Board) intent.getSerializableExtra("board");
         user = (User) intent.getSerializableExtra("user");
        System.out.println("리스트보드원 왔고 여기서 보드 정보"+board);
        test = (ImageButton)findViewById(R.id.test);
        btnLike = (ImageButton)findViewById(R.id.board_btnLike);
        userProfile = (ImageView)findViewById(R.id.board_user_profile);
        userNickname = (TextView)findViewById(R.id.board_user_nickName);
        photo1 = (ImageView)findViewById(R.id.imageBoard1);
        photo2 = (ImageView)findViewById(R.id.imageBoard2);
        photo3 = (ImageView)findViewById(R.id.imageBoard3);
        boardDetail = (TextView)findViewById(R.id.getBoardDetailText);
        boardLike = (TextView)findViewById(R.id.board_likeCount);
        addCommentText = (EditText)findViewById(R.id.addCommentText);
        addCommentBtn = (ImageButton)findViewById(R.id.addCommentBtn);
        listComment = (ListView) findViewById(R.id.list_view_comment);
        address = (TextView)findViewById(R.id.address);
        adapter = new ListCommentAdapter(this,comments);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what=0;
                message.obj=board;

                handler.sendMessage(message);
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String boardNo = board.getBoardNo();
                if (board.getLikeUserNo()==null&&board.getCheckLike().equals("0")){

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                RestHttpClient.addLike(user.getUserNo(),boardNo);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    btnLike.setImageResource(R.drawable.like2);
                    boardLike.setText(String.valueOf(Integer.parseInt(boardLike.getText().toString())+1));
                    board.setLikeUserNo(user.getUserNo());
                    board.setCheckLike("1");
                }else if (board.getLikeUserNo().equals(user.getUserNo())&&board.getCheckLike().equals("1")){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                RestHttpClient.updateLike(board.getLikeUserNo(),boardNo,"1");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    btnLike.setImageResource(R.drawable.like1);
                    boardLike.setText(String.valueOf(Integer.parseInt(boardLike.getText().toString())-1));
                    board.setCheckLike("2");
                }else if (board.getLikeUserNo().equals(user.getUserNo())&&board.getCheckLike().equals("2")){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                RestHttpClient.updateLike(board.getLikeUserNo(),boardNo,"2");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    btnLike.setImageResource(R.drawable.like2);
                    boardLike.setText(String.valueOf(Integer.parseInt(boardLike.getText().toString())+1));
                    board.setCheckLike("1");
                }

            }
        });

        addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addCommentText.getText().equals("")&&addCommentText.getText()==null){
                    Toast.makeText(ListBoardOneActivity.this,"내용을 입력하세요.",Toast.LENGTH_LONG).show();
                }else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println(addCommentText.getText());
                                RestHttpClient.addComment(user.getUserNo(),board.getBoardNo(),addCommentText.getText().toString());
                                user=RestHttpClient.getUser(user.getUserNo());
                                Message message = new Message();
                                message.what=1;
                                message.obj=user;
                                handler.sendMessage(message);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                    Toast.makeText(ListBoardOneActivity.this,"등록완료!",Toast.LENGTH_LONG).show();

                }
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(board.getAddress()==null||board.getAddress().equals("")){
                       // Toast.makeText(ListBoardOneActivity.this,"널입니다",Toast.LENGTH_LONG).show();
                    }else{
                       // Toast.makeText(ListBoardOneActivity.this,"널아닙니다",Toast.LENGTH_LONG).show();
                        String coord = board.getCoord();
                        System.out.println("coord======="+coord);
                        String[] coords = coord.split(",");
                        String x = coords[0];
                        System.out.println("sum====="+x);
                        double X = Double.parseDouble(x);

                        String y= coords[1];
                        System.out.println("sum====="+y);
                        double Y = Double.parseDouble(y);
                        System.out.println("sum====="+Y);
                      //  Location destination = Location.newBuilder(board.getAddress(),Y, X).build();

                        Location destination = Location.newBuilder(board.getAddress(),Y, X).build();
                        KakaoNaviParams.Builder builder = KakaoNaviParams.newBuilder(destination)
                                .setNaviOptions(NaviOptions.newBuilder().setCoordType(CoordType.WGS84).build());

                        KakaoNaviService.getInstance().shareDestination(ListBoardOneActivity.this, builder.build());

                    }
            }
        });

    }
}
