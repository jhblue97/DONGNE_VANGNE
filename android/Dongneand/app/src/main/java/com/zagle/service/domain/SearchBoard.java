package com.zagle.service.domain;

public class SearchBoard {
	
	private int currentPage;
	private String searchKeyword;
	private int pageSize;
	private int endRowNum;
	private int startRowNum;
	private int searchLike;
	private String local;
	private String loginUserNo;
	
	public SearchBoard() {
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getEndRowNum() {
		return endRowNum;
	}

	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}

	public int getStartRowNum() {
		return startRowNum;
	}

	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}

	public int getSearchLike() {
		return searchLike;
	}

	public void setSearchLike(int searchLike) {
		this.searchLike = searchLike;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}	

	public String getLoginUserNo() {
		return loginUserNo;
	}

	public void setLoginUserNo(String loginUserNo) {
		this.loginUserNo = loginUserNo;
	}

	@Override
	public String toString() {
		return "SearchBoard [currentPage=" + currentPage + ", searchKeyword=" + searchKeyword + ", pageSize=" + pageSize
				+ ", endRowNum=" + endRowNum + ", startRowNum=" + startRowNum + ", searchLike=" + searchLike
				+ ", local="+local+", loginUserNo="+loginUserNo+"]";
	}
	
	

}
