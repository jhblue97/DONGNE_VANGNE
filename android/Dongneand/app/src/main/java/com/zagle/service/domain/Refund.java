package com.zagle.service.domain;

import java.util.Date;

public class Refund {

	private String refundNo;
	private String streamerNo;
	private String streamerNickname;
	private String account;
	private int price;
	private String bankname;
	private String checkRefund;
	private Date refundDate;
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public String getStreamerNo() {
		return streamerNo;
	}
	public void setStreamerNo(String streamerNo) {
		this.streamerNo = streamerNo;
	}
	public String getStreamerNickname() {
		return streamerNickname;
	}
	public void setStreamerNickname(String streamerNickname) {
		this.streamerNickname = streamerNickname;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getCheckRefund() {
		return checkRefund;
	}
	public void setCheckRefund(String checkRefund) {
		this.checkRefund = checkRefund;
	}
	public Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	@Override
	public String toString() {
		return "Refund [refundNo=" + refundNo + ", streamerNo=" + streamerNo + ", streamerNickname=" + streamerNickname
				+ ", account=" + account + ", price=" + price + ", bankname=" + bankname + ", checkRefund="
				+ checkRefund + ", refundDate=" + refundDate + "]";
	}

	
	
	
	
	
	
}
