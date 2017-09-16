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
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "fa_user_story")
public class UserStory extends FaBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String USERSTORIES = "userStories";
	public static final String ID = "userStoryId";
	
	private String title;
	private String desc;
	private Long ownerRefId;
	private String status; // Request, Active, Complete, Delete, Differed
	private Feature feature;
	private Requirement requirement;
	private TestSuite testSuite;
	
	// Constructor
	public UserStory(){
		super();
	}
	
	public UserStory(String title, String desc, Long ownerRefId) {
		super();
		this.setTitle(title);
		this.setDesc(desc);
		this.setOwnerRefId(ownerRefId);
	}
	
	// Constructor for ajax
	public UserStory(RestRequest request, RestResponse response, String formName) {
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
	@Column(name = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	@ManyToOne(targetEntity = Requirement.class)
	@JoinColumn(name = "requirement_id")
	public Requirement getRequirement() {
		return requirement;
	}
	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@ManyToOne(targetEntity = TestSuite.class)
	@JoinColumn(name = "test_suite_id")
	public TestSuite getTestSuite() {
		return testSuite;
	}
	public void setTestSuite(TestSuite testSuite) {
		this.testSuite = testSuite;
	}
}
