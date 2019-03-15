package com.zagle.service.domain;

public class Mypage {
	
	///field
	private User user;
	private Comment comment;
	private Like like;
	private Scrap scrap;
	private Board board;
	
	
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
	public Comment getComment() {
		return comment;
	}
	public void setCommnet(Comment comment) {
		this.comment = comment;
	}
	public Like getLike() {
		return like;
	}
	public void setLike(Like like) {
		this.like = like;
	}
	public Scrap getScrap() {
		return scrap;
	}
	public void setScrap(Scrap scrap) {
		this.scrap = scrap;
	}
	@Override
	public String toString() {
		return "Mypage [user=" + user + ", comment=" + comment + ", like=" + like + ", scrap=" + scrap + ", board="
				+ board + "]";
	}
	
	

}
