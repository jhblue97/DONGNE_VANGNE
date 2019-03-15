package example.dongne.board;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zagle.service.domain.Board;

import org.w3c.dom.Text;

import java.util.ArrayList;

import example.dongne.R;


public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItems;
    private LayoutInflater layoutInflater;
    private View.OnClickListener onClickListener;
    public ListViewAdapter(Context aContext,ArrayList<ListViewItem> listViewItems,View.OnClickListener clickListener){
        this.listViewItems=listViewItems;
        this.layoutInflater=LayoutInflater.from(aContext);
        this.onClickListener=clickListener;
    }

    @Override
    public int getCount() {
        return listViewItems.size();
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
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            if (layoutInflater==null){
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            convertView = layoutInflater.inflate(R.layout.activity_list_board_one, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView userProfile = (ImageView) convertView.findViewById(R.id.user_profile) ;
        TextView userNickname = (TextView) convertView.findViewById(R.id.user_nickName) ;
        ImageView imageBoard = (ImageView) convertView.findViewById(R.id.imageBoard) ;
        TextView countLike = (TextView) convertView.findViewById(R.id.likeCount);
        ImageView commentUser = (ImageView) convertView.findViewById(R.id.commentUser);
        TextView commentText = (TextView) convertView.findViewById(R.id.textComment);
        TextView boardText = (TextView) convertView.findViewById(R.id.boardDetailText);
        ImageButton btnLike = (ImageButton) convertView.findViewById(R.id.btnLike);
        imageBoard.setMaxHeight(300);
        imageBoard.setMinimumHeight(0);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewItem listViewItem = listViewItems.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        Glide.with(context).load("http://192.168.0.43:8080/common/images/profile/"+listViewItem.getProfile()).into(userProfile);


        userNickname.setText(listViewItem.getUserNickname());
        if (listViewItem.getImageBoard()!=null) {
            boardText.setText("");
            if ((listViewItem.getImageBoard().substring(listViewItem.getImageBoard().length() - 3)).equals("gif")) {
                Glide.with(context).load(listViewItem.getImageBoard()).asGif().into(imageBoard);
            } else {
                Glide.with(context).load(listViewItem.getImageBoard()).into(imageBoard);
            }
        }else if (listViewItem.getImageBoard()==null){
            imageBoard.setImageDrawable(null);
            boardText.setText(Html.fromHtml(listViewItem.getBoardText()).toString());
        }
        countLike.setText(String.valueOf(listViewItem.getBoard().getLikeCount()));
        Glide.with(context).load("http://192.168.0.43:8080/common/images/profile/"+listViewItem.getCommentUser()).into(commentUser);
        commentText.setText(listViewItem.getCommentText());
        btnLike.setTag(position);
        btnLike.setOnClickListener(listViewItem.getOnClickListener());
        if (listViewItem.getBoard().getCheckLike().equals("0")){
            btnLike.setImageResource(R.drawable.like1);
        }else if(listViewItem.getBoard().getCheckLike().equals("1")){
            btnLike.setImageResource(R.drawable.like2);
        }else if (listViewItem.getBoard().getCheckLike().equals("2")){
            btnLike.setImageResource(R.drawable.like1);
        }

        return convertView;
    }

    public void addItem(Board board, String profile, String imageBoard
            , String userNickname, String boardLike
            , String commentUser, String commentText, String boardText, ImageButton btnLike, View.OnClickListener onClickListener,String checkLike){
        ListViewItem item = new ListViewItem();

        item.setBoard(board);
        item.setProfile(profile);
        item.setImageBoard(imageBoard);
        item.setUserNickname(userNickname);
        item.setBoardLike(boardLike);
        item.setCommentUser(commentUser);
        item.setCommentText(commentText);
        item.setBoardText(boardText);
        item.setBtnLike(btnLike);
        item.setOnClickListener(onClickListener);
        item.setCheckLike(checkLike);

        listViewItems.add(item);
    }

    public void addItem(Board board, String profile, String imageBoard
            , String userNickname, String boardLike
            , String commentUser, String commentText, String boardText, ImageButton btnLike, View.OnClickListener onClickListener,String checkLike,String userTheme){
        ListViewItem item = new ListViewItem();

        item.setBoard(board);
        item.setProfile(profile);
        item.setImageBoard(imageBoard);
        item.setUserNickname(userNickname);
        item.setBoardLike(boardLike);
        item.setCommentUser(commentUser);
        item.setCommentText(commentText);
        item.setBoardText(boardText);
        item.setBtnLike(btnLike);
        item.setOnClickListener(onClickListener);
        item.setCheckLike(checkLike);
        item.setUserTheme(userTheme);
        listViewItems.add(item);
    }
}
