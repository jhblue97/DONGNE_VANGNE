package example.dongne.board;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;

import com.zagle.service.domain.Board;

import java.io.Serializable;

public class ListViewItem implements Serializable {
    private Board board;
    private String profile;
    private String imageBoard;
    private String userNickname;
    private String boardLike;
    private String commentUser;
    private String commentText;
    private String boardText;
    private ImageButton btnLike;
    private View.OnClickListener onClickListener;
    private String checkLike;
    private String userTheme;

    public String getUserTheme() {
        return userTheme;
    }

    public void setUserTheme(String userTheme) {
        this.userTheme = userTheme;
    }

    public String getCheckLike() {
        return checkLike;
    }

    public void setCheckLike(String checkLike) {
        this.checkLike = checkLike;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getBoardText() {
        return boardText;
    }

    public void setBoardText(String boardText) {
        this.boardText = boardText;
    }

    public ImageButton getBtnLike() {
        return btnLike;
    }

    public void setBtnLike(ImageButton btnLike) {
        this.btnLike = btnLike;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getImageBoard() {
        return imageBoard;
    }

    public void setImageBoard(String imageBoard) {
        this.imageBoard = imageBoard;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getBoardLike() {
        return boardLike;
    }

    public void setBoardLike(String boardLike) {
        this.boardLike = boardLike;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
