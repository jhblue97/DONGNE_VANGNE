package example.dongne.mypage;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zagle.service.domain.Board;
import com.zagle.service.domain.Mypage;

import java.util.ArrayList;

import example.dongne.R;
import example.dongne.board.ListViewItem;

public class MypageListViewAdapter extends  BaseAdapter {
    private ArrayList<MypageListViewItem> listViewItems;

    private Context context;
    private TextView  boardDetailText;
    private TextView boardRegDate;
    private TextView boardNo;



    public MypageListViewAdapter(Context aContext, ArrayList<MypageListViewItem> listViewItems) {
        this.listViewItems = listViewItems;
        this.context = aContext;

    }

    @Override
    public int getCount() {
        return this.listViewItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MypageListViewItem listViewItem = listViewItems.get(position);
        if(convertView==null){
                if(listViewItem.getFlag().equals("0")){
                    System.out.println("여기는 보드 어뎁터"+listViewItem.getBoardRegDate());
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_mypagelistboard_one,null);
            boardDetailText = (TextView)convertView.findViewById(R.id.boardDetailText);
            boardRegDate = (TextView)convertView.findViewById(R.id.boardRegDate);
            boardNo = (TextView)convertView.findViewById(R.id.boardNo);
            boardDetailText.setText(Html.fromHtml(listViewItem.getBoardDetailText()).toString());
            boardRegDate.setText(listViewItem.getBoardRegDate());
            boardNo.setText(String.valueOf(listViewItem.getNo()));

                }else if(listViewItem.getFlag().equals("1")){
                    System.out.println("여기는 스크랩 어뎁터");

                    convertView = LayoutInflater.from(context).inflate(R.layout.activity_mypagelistscrap_one,null);
                    boardDetailText = (TextView)convertView.findViewById(R.id.boardDetailText);
                    boardRegDate = (TextView)convertView.findViewById(R.id.boardRegDate);
                    boardNo = (TextView)convertView.findViewById(R.id.boardNo);

                    boardDetailText.setText(Html.fromHtml(listViewItem.getBoardDetailText()).toString());
                    boardNo.setText(String.valueOf(listViewItem.getNo()));
                    boardRegDate.setText(listViewItem.getBoardRegDate());

                }else{
                    System.out.println("여기는 댓글 어뎁터");
                    convertView = LayoutInflater.from(context).inflate(R.layout.activity_mypagelistcomment_one,null);
                    boardDetailText = (TextView)convertView.findViewById(R.id.boardDetailText);
                    boardRegDate = (TextView)convertView.findViewById(R.id.boardRegDate);
                    boardNo = (TextView)convertView.findViewById(R.id.boardNo);

                    boardDetailText.setText(Html.fromHtml(listViewItem.getBoardDetailText()).toString());
                    boardNo.setText(String.valueOf(listViewItem.getNo()));
                    boardRegDate.setText(listViewItem.getBoardRegDate());

                }
        }
        return convertView;
    }

    public void addItem(String boardDetailText,String boardRegDate,String No2,String Flag){
        System.out.println("어뎁터additem생성자"+boardDetailText+boardRegDate+No2);

        MypageListViewItem item = new MypageListViewItem();
        item.setBoardDetailText(boardDetailText);
        item.setBoardRegDate(boardRegDate);
        item.setNo(Integer.parseInt(No2));
        item.setFlag(Flag);
        System.out.println("mypageitem에 넣어준 후 "+item.getBoardDetailText()+item.getBoardRegDate());
        listViewItems.add(item);
    }
    public void addItem(String boardDetailText,String boardRegDate,String No2,String Flag,String boardNo){
        System.out.println("어뎁터additem생성자"+boardDetailText+boardRegDate+No2);

        MypageListViewItem item = new MypageListViewItem();
        item.setBoardDetailText(boardDetailText);
        item.setBoardRegDate(boardRegDate);
        item.setNo(Integer.parseInt(No2));
        item.setFlag(Flag);
        item.setBoardNo(boardNo);
        System.out.println("mypageitem에 넣어준 후 "+item.getBoardDetailText()+item.getBoardRegDate());
        listViewItems.add(item);
    }

    public void addItem(String boardDetailText,String boardRegDate,String No2,String Flag,String boardNo,String userNo){
        System.out.println("어뎁터additem생성자"+boardDetailText+boardRegDate+No2);

        MypageListViewItem item = new MypageListViewItem();
        item.setBoardDetailText(boardDetailText);
        item.setBoardRegDate(boardRegDate);
        item.setNo(Integer.parseInt(No2));
        item.setFlag(Flag);
        item.setBoardNo(boardNo);
        item.setUserNo(userNo);
        System.out.println("mypageitem에 넣어준 후 "+item.getBoardDetailText()+item.getBoardRegDate());
        listViewItems.add(item);
    }

    public void addItem(String boardDetailText,String boardRegDate,String No2,String Flag,String boardNo,String userNo,String checkLike){
        System.out.println("어뎁터additem생성자"+boardDetailText+boardRegDate+No2);

        MypageListViewItem item = new MypageListViewItem();
        item.setBoardDetailText(boardDetailText);
        item.setBoardRegDate(boardRegDate);
        item.setNo(Integer.parseInt(No2));
        item.setFlag(Flag);
        item.setBoardNo(boardNo);
        item.setUserNo(userNo);
        item.setCheckLike(checkLike);
        System.out.println("mypageitem에 넣어준 후 "+item.getCheckLike());
        listViewItems.add(item);
    }
    public void addItem(String boardDetailText, String boardRegDate, String No2, String Flag, String boardNo, String userNo, String checkLike, Board board){
        System.out.println("어뎁터additem생성자5"+boardDetailText+boardRegDate+No2);

        MypageListViewItem item = new MypageListViewItem();
        item.setBoardDetailText(boardDetailText);
        item.setBoardRegDate(boardRegDate);
        item.setNo(Integer.parseInt(No2));
        item.setFlag(Flag);
        item.setBoardNo(boardNo);
        item.setUserNo(userNo);
        item.setCheckLike(checkLike);
        item.setBoard(board);
        System.out.println("mypageitem에 넣어준 후 "+item.getCheckLike());
        listViewItems.add(item);
    }

}
