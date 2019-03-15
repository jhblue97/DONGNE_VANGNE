package com.zagle.service.domain;

import java.io.Serializable;
import java.sql.Date;

public class Comment implements Serializable {
	
	private String commentNo;
	private Board board;
	private User user;
	private String commentDetailText;
	private String commentStatus;
	private Date commentRegDate;
	
	public Comment() {
		
	}

	public String getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(String commentNo) {
		this.commentNo = commentNo;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCommentDetailText() {
		return commentDetailText;
	}

	public void setCommentDetailText(String commentDetailText) {
		this.commentDetailText = commentDetailText;
	}

	public Date getCommentRegDate() {
		return commentRegDate;
	}

	public void setCommentRegDate(Date commentRegDate) {
		this.commentRegDate = commentRegDate;
	}

	public String getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}

	@Override
	public String toString() {
		return "Comment [commentNo=" + commentNo + ", board=" + board + ", user=" + user + ", commentDetailText="
				+ commentDetailText + ", commentStatus="+commentStatus+", commentRegDate=" + commentRegDate + "]";
	}
	
	

}
