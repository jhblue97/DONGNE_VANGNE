package com.zagle.service.domain;

import java.sql.Date;

public class Report {
	
	///Field
	
	private String reportNo;
	private User reportingUserNo;
	
	private User reportedUserNo;
	private Board reportedBoardNo;
	
	private String reportReason;
	
	private Comment reportedCommentNo;
	private Comment reportedComment;
	
	private int hardleNo;
	private Date reportDate;
	
	///Constructor
	public Report() {
	}
	
	public Board getReportedBoardNo() {
		return reportedBoardNo;
	}

	public void setReportedBoardNo(Board reportedBoardNo) {
		this.reportedBoardNo = reportedBoardNo;
	}

	public Comment getReportedCommentNo() {
		return reportedCommentNo;
	}

	public void setReportedCommentNo(Comment reportedCommentNo) {
		this.reportedCommentNo = reportedCommentNo;
	}

	public int getHardleNo() {
		return hardleNo;
	}

	public void setHardleNo(int hardleNo) {
		this.hardleNo = hardleNo;
	}

	

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public User getReportingUserNo() {
		return reportingUserNo;
	}

	public void setReportingUserNo(User reportingUserNo) {
		this.reportingUserNo = reportingUserNo;
	}

	public User getReportedUserNo() {
		return reportedUserNo;
	}

	public void setReportedUserNo(User reportedUserNo) {
		this.reportedUserNo = reportedUserNo;
	}

	public String getReportReason() {
		return reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	public Comment getReportedComment() {
		return reportedComment;
	}

	public void setReportedComment(Comment reportedComment) {
		this.reportedComment = reportedComment;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
	

	@Override
	public String toString() {
		return "Report [reportNo=" + reportNo + ", reportingUserNo=" + reportingUserNo + ", reportedUserNo="
				+ reportedUserNo + ", reportedBoardNo=" + reportedBoardNo + ", reportReason=" + reportReason
				+ ", reportedCommentNo=" + reportedCommentNo + ", reportedComment=" + reportedComment + ", hardleNo="
				+ hardleNo + ", reportDate=" + reportDate + "]";
	}

	
	

}
