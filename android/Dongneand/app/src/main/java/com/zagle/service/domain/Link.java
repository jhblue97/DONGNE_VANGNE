package com.zagle.service.domain;

public class Link {
	
	private String linkNo;
	private String coordX;
	private String coordY;
	private String url;
	private String photo1;
	
	public Link() {
	}

	public String getLinkNo() {
		return linkNo;
	}

	public void setLinkNo(String linkNo) {
		this.linkNo = linkNo;
	}

	public String getCoordX() {
		return coordX;
	}

	public void setCoordX(String coordX) {
		this.coordX = coordX;
	}

	public String getCoordY() {
		return coordY;
	}

	public void setCoordY(String coordY) {
		this.coordY = coordY;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhoto1() {
		return photo1;
	}

	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}

	@Override
	public String toString() {
		return "Link [linkNo=" + linkNo + ", coordX=" + coordX + ", coordY=" + coordY + ", url=" + url + ", photo1="
				+ photo1 + "]";
	}
	
	

}
