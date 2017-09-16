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
import javax.validation.constraints.Size;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "fa_question")
public class Question extends FaBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String QUESTIONS = "questions";
	public static final String ID = "questionId";
	
	private String title;
	private Long ownerRefId;
	private String shortText;
	private String fullText;
	private Feature feature;
	private Requirement requirement;
	
	// Constructor
	public Question(){
		super();
	}
	
	public Question(String title, Long ownerRefId, String shortText, String fullText, Feature feature){
		super();
		setTitle(title);
		setOwnerRefId(ownerRefId);
		setShortText(shortText);
		setFullText(fullText);
		setFeature(feature);
	}
	
	public Question(String title, Long ownerRefId, String shortText, String fullText, Requirement requirement){
		super();
		setTitle(title);
		setOwnerRefId(ownerRefId);
		setShortText(shortText);
		setFullText(fullText);
		setRequirement(requirement);
	}
	
	
	// Setter/Getters
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "title", nullable = false)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	@Size(min = 1, max = 255)
	@Column(name = "short_text")
	public String getShortText() {
		return shortText;
	}
	public void setShortText(String shortText) {
		this.shortText = shortText;
	}
		
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "full_text")
	public String getFullText() {
		return fullText;
	}
	public void setFullText(String fullText) {
		this.fullText = fullText;
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
}
