package com.zagle.service.domain;

public class Like {
	
	private String likeNo;
	private Board board;
	private User user;
	private String checkLike;
	
	public Like() {
		
	}

	public String getLikeNo() {
		return likeNo;
	}

	public void setLikeNo(String likeNo) {
		this.likeNo = likeNo;
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

	public String getCheckLike() {
		return checkLike;
	}

	public void setCheckLike(String checkLike) {
		this.checkLike = checkLike;
	}

	@Override
	public String toString() {
		return "Like [likeNo=" + likeNo + ", board=" + board + ", user=" + user + ", checkLike=" + checkLike + "]";
	}
	
	

}
