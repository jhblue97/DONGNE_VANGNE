package com.zagle.service.domain;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {
	
	///Field
	private String userNo;
	private String snsNo;
	private String userName;
	private String userNickname;
	private String phone;
	private String userSex;
	private String userAddr;
	private String profile;
	private String grade;
	private String snsName;
	private String bankName;
	private String account;
	
	private Date regDate;
	
	private int blackCode;
	private int userBirth;
	private int deleteCount;
	private int totalActiveScore;
	
	///////////////////EL ���� ���� �߰��� Field////////////////////////
	private String phone1;
	private String phone2;
	private String phone3;
	
	
	
	
	///Constructor
	public User() {
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getSnsNo() {
		return snsNo;
	}

	public void setSnsNo(String snsNo) {
		this.snsNo = snsNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserAddr() {
		return userAddr;
	}

	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getBlackCode() {
		return blackCode;
	}

	public void setBlackCode(int blackCode) {
		this.blackCode = blackCode;
	}

	public String getSnsName() {
		return snsName;
	}

	public void setSnsName(String snsName) {
		this.snsName = snsName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getUserBirth() {
		return userBirth;
	}

	public void setUserBirth(int userBirth) {
		this.userBirth = userBirth;
	}

	public int getDeleteCount() {
		return deleteCount;
	}

	public void setDeleteCount(int deleteCount) {
		this.deleteCount = deleteCount;
	}

	public int getTotalActiveScore() {
		return totalActiveScore;
	}

	public void setTotalActiveScore(int totalActiveScore) {
		this.totalActiveScore = totalActiveScore;
	}

	@Override
	public String toString() {
		return "User [userNo=" + userNo + ", snsNo=" + snsNo + ", userName=" + userName + ", userNickname="
				+ userNickname + ", phone=" + phone + ", userSex=" + userSex + ", userAddr=" + userAddr + ", profile="
				+ profile + ", grade=" + grade + ", blackCode=" + blackCode + ", snsName=" + snsName + ", bankName="
				+ bankName + ", regDate=" + regDate + ", account=" + account + ", userBirth=" + userBirth
				+ ", deleteCount=" + deleteCount + ", totalActiveScore=" + totalActiveScore + "]";
	}

	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// JSON == > Domain Obejct Binding ���� �߰��� �κ�
	
	

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
}
