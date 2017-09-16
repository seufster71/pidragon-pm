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
@Table(name = "fa_issue_score")
public class IssueScore extends FaBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private int value;	// 1 or 0
	private Long ownerRefId;
	private Issue issue;
	
	// constructor
	public IssueScore() {
		super();
	}
	
	// getters and setters
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "item_value")
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	@JsonView({View.Admin.class})
	@Column(name = "owner_id")
	public Long getOwnerRefId() {
		return ownerRefId;
	}
	public void setOwnerRefId(Long ownerRefId) {
		this.ownerRefId = ownerRefId;
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
	
	
}
