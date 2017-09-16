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
@Table(name = "fa_test_case")
public class TestCase extends FaBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String TESTCASES = "testCases";
	public static final String ID = "testCaseId";
	
	
	private String title;
	private String desc;
	private Long ownerRefId;
	private String status; // Request, Active, Complete, Delete, Differed
	private String unitTestRef;
	private String integrationTestRef;
	private UserStory userStory;
	private Feature feature;
	private Requirement requirement;
	private TestSuite testSuite;
	//private String appActor;
	//private String preConfig;
	private String priority;	// low, medium, high
	private String probability;  // low, medium, high
	
	// Constructor
	public TestCase(){
		super();
	}
	
	public TestCase(String title, String desc, Long ownerRefId) {
		super();
		this.setTitle(title);
		this.setDesc(desc);
		this.setOwnerRefId(ownerRefId);
	}
	// Constructor for ajax
	public TestCase(RestRequest request, RestResponse response, String formName) {
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
	@Column(name = "unit_test_ref")
	public String getUnitTestRef() {
		return unitTestRef;
	}
	public void setUnitTestRef(String unitTestRef) {
		this.unitTestRef = unitTestRef;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "integration_test_ref")
	public String getIntegrationTestRef() {
		return integrationTestRef;
	}
	public void setIntegrationTestRef(String integrationTestRef) {
		this.integrationTestRef = integrationTestRef;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@ManyToOne(targetEntity = UserStory.class)
	@JoinColumn(name = "user_story_id")
	public UserStory getUserStory() {
		return userStory;
	}
	public void setUserStory(UserStory userStory) {
		this.userStory = userStory;
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
		this.requirement= requirement;
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

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "probability")
	public String getProbability() {
		return probability;
	}
	public void setProbability(String probability) {
		this.probability = probability;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "priority")
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}


}
