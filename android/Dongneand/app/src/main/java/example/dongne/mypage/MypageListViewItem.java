package example.dongne.mypage;

import com.zagle.service.domain.Board;

public class MypageListViewItem {
    private Board board;
    private String boardDetailText;
    private String boardRegDate;
    private String boardNo;
    private int No;
    private String Flag;
    private String userNo;
    private  String checkLike;



    public String getCheckLike() {
        return checkLike;
    }

    public void setCheckLike(String checkLike) {
        this.checkLike = checkLike;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }
    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        this.Flag = flag;
    }

    public MypageListViewItem() {

    }
    public MypageListViewItem(String boardDetailText, String boardRegDate) {
        this.boardDetailText = boardDetailText;
        this.boardRegDate = boardRegDate;
    }

    public void setNo(int no) {
        No = no;
    }

    public int getNo() {
        return No;
    }

    public String getBoardDetailText() {
        return boardDetailText;
    }

    public void setBoardDetailText(String boardDetailText) {
        this.boardDetailText = boardDetailText;
    }

    public String getBoardRegDate() {
        return boardRegDate;
    }

    public void setBoardRegDate(String boardRegDate) {
        this.boardRegDate = boardRegDate;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
