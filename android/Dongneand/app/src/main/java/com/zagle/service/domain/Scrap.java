package com.zagle.service.domain;

public class Scrap {
	
	private String scrapNo;
	private User user;
	private Board board;
	private String checkScrap;
	
	public Scrap() {
	}

	public String getScrapNo() {
		return scrapNo;
	}

	public void setScrapNo(String scrapNo) {
		this.scrapNo = scrapNo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getCheckScrap() {
		return checkScrap;
	}

	public void setCheckScrap(String checkScrap) {
		this.checkScrap = checkScrap;
	}

	@Override
	public String toString() {
		return "Scrap [scrapNo=" + scrapNo + ", user=" + user + ", board=" + board + ", checkScrap=" + checkScrap + "]";
	}


}
