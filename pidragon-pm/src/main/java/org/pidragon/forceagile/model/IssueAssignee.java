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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "fa_issue_assignee")
public class IssueAssignee extends FaBaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Issue issue;
	private Long assigneeRefId;
	private boolean notifyChange;
	// add a role
	
	// Constructor
	public IssueAssignee(){
		super();
	}
	
	public IssueAssignee(Issue issue, Long assigneeRefId){
		super();
		setIssue(issue);
		setAssigneeRefId(assigneeRefId);
	}

	// getters and setters
	@JsonView({View.Member.class,View.Admin.class})
	@ManyToOne(targetEntity = Issue.class)
	@JoinColumn(name = "issue_id")
	public Issue getIssue() {
		return issue;
	}
	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "assignee_id")
	public Long getAssigneeRefId() {
		return assigneeRefId;
	}
	public void setAssigneeRefId(Long assigneeRefId) {
		this.assigneeRefId = assigneeRefId;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "notify_change")
	public boolean isNotifyChange() {
		return notifyChange;
	}
	public void setNotifyChange(boolean notifyChange) {
		this.notifyChange = notifyChange;
	}
}
