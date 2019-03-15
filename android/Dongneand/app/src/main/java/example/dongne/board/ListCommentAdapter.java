package example.dongne.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zagle.service.domain.Comment;
import com.zagle.service.domain.User;

import java.sql.Date;
import java.util.ArrayList;

import example.dongne.R;


public class ListCommentAdapter extends BaseAdapter {
    private ArrayList<Comment> comments;
    private LayoutInflater layoutInflater;
    public ListCommentAdapter(Context aContext, ArrayList<Comment> comments){
        this.comments=comments;
        this.layoutInflater=LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
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

            convertView = layoutInflater.inflate(R.layout.activity_getboard_comment, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView commentUser = (ImageView) convertView.findViewById(R.id.commentUser) ;
        TextView commentText = (TextView) convertView.findViewById(R.id.textComment) ;
        TextView commentRegDate = (TextView) convertView.findViewById(R.id.commentRegDate);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final Comment comment = comments.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        Glide.with(context).load("http://192.168.0.43:8080/common/images/profile/"+comment.getUser().getProfile()).into(commentUser);
        commentText.setText(comment.getCommentDetailText());
        commentRegDate.setText(String.valueOf(comment.getCommentRegDate()));

        return convertView;
    }

    public void addItem(String commentUser, String commentText, Date date){
        Comment comment = new Comment();
        User user = new User();
        user.setProfile(commentUser);

        comment.setUser(user);
        comment.setCommentDetailText(commentText);
        comment.setCommentRegDate(date);

        comments.add(comment);
    }
}
