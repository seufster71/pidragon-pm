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
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.toasthub.core.general.api.View;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.core.general.model.WorkFlow;
import org.toasthub.social.model.AttachmentMeta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "fa_requirement")
public class Requirement extends FaBaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String REQUIREMENTS = "requirements";
	public static final String ID = "requirementId";
	public static final String BASEDIRECTORY = "requirement";
	
	private String title;
	private String desc;
	private Long ownerRefId;
	private double hourEstimate;
	private double actualHours;
	private Date startDate;
	private Date endDate;
	private Long creatorRefId;
	private WorkFlow workflow;
	private Feature feature;
	private Issue issue;
	
	private Set<AttachmentMeta> attachments;
	private List<AttachmentMeta> attachmentList;
	private List<Long> attachmentIdList;
	
	// Constructor
	public Requirement(){
		super();
	}
	
	public Requirement(String title, String desc, Long ownerRefId) {
		super();
		this.setTitle(title);
		this.setDesc(desc);
		this.setOwnerRefId(ownerRefId);
	}

	// Constructor for ajax
	public Requirement(RestRequest request, RestResponse response, String formName) {
		//userInputHelper(request, response, formName);
	}
		
	// Setter/Getter
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "description")
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "owner_id")
	public Long getOwnerRefId() {
		return ownerRefId;
	}
	public void setOwnerRefId(Long ownerRefId) {
		this.ownerRefId = ownerRefId;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "hour_estimate")
	public double getHourEstimate() {
		return hourEstimate;
	}
	public void setHourEstimate(double hourEstimate) {
		this.hourEstimate = hourEstimate;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "actual_hours")
	public double getActualHours() {
		return actualHours;
	}
	public void setActualHours(double actualHours) {
		this.actualHours = actualHours;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "start_date")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "end_date")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "creator_id")
	public Long getCreatorRefId() {
		return creatorRefId;
	}
	public void setCreatorRefId(Long creatorRefId) {
		this.creatorRefId = creatorRefId;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@ManyToOne(targetEntity = WorkFlow.class)
	@JoinColumn(name = "workflow_id")
	public WorkFlow getWorkflow() {
		return workflow;
	}
	public void setWorkflow(WorkFlow workflow) {
		this.workflow = workflow;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@ManyToOne(targetEntity = Feature.class)
	@JoinColumn(name = "feature_id")
	public Feature getFeature() {
		return feature;
	}
	public void setFeature(Feature feature) {
		this.feature = feature;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@ManyToOne(targetEntity = Issue.class)
	@JoinColumn(name = "issue_id")
	public Issue getIssue() {
		return issue;
	}
	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	
	@JsonIgnore
	@OneToMany(targetEntity = AttachmentMeta.class, cascade = CascadeType.ALL)
	@JoinTable(name = "fa_requirement_attachment",joinColumns = @JoinColumn(name = "requirement_id"),inverseJoinColumns = @JoinColumn(name = "attachment_id"))
	public Set<AttachmentMeta> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<AttachmentMeta> attachments) {
		this.attachments = attachments;
	}
	
	@JsonView({View.Member.class})
	@Transient
	public List<AttachmentMeta> getAttachmentList() {
		return attachmentList;
	}
	public void setAttachmentList(List<AttachmentMeta> attachmentList) {
		this.attachmentList = attachmentList;
	}
	
	@JsonView({View.Member.class})
	@Transient
	public List<Long> getAttachmentIdList() {
		return attachmentIdList;
	}
	public void setAttachmentIdList(List<Long> attachmentIdList) {
		this.attachmentIdList = attachmentIdList;
	}

}