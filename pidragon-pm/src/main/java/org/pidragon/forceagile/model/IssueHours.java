/*
 * Copyright (C) 2016 Pi Dragon LLC
 *
 * Licensed under the Pi Dragon, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://pidragron.com/licenses/LICENSE-1.0
 *
 * @author Edward H. Seufert
 */

package org.pidragon.forceagile.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "fa_issue_hours")
public class IssueHours extends FaBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date startTime;
	private Date endTime;
	private double hours;
	private Issue issue;
	private Long ownerRefId;
	
	// Constructor
	public IssueHours() {
		super();
	}

	public IssueHours(Date startTime, Issue issue, Long ownerRefId){
		super();
		setStartTime(startTime);
		setIssue(issue);
		setOwnerRefId(ownerRefId);
	}
	
	public IssueHours(double hours, Issue issue, Long ownerRefId){
		super();
		setHours(hours);
		setIssue(issue);
		setOwnerRefId(ownerRefId);
	}
	
	// getters and setters
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "start_time")
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "end_time")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "hours")
	public double getHours() {
		return hours;
	}
	public void setHours(double hours) {
		this.hours = hours;
	}
	
	@JsonView({View.Admin.class})
	@ManyToOne(targetEntity = Issue.class)
	@JoinColumn(name = "issue_id")
	public Issue getIssue() {
		return issue;
	}
	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	@JsonView({View.Admin.class})
	@Column(name = "owner_id")
	public Long getOwnerRefId() {
		return ownerRefId;
	}
	public void setOwnerRefId(Long ownerRefId) {
		this.ownerRefId = ownerRefId;
	}
	
	
}
