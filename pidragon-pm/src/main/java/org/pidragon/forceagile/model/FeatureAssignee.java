/*
 * Copyright (C) 2016 Pi Dragon LLC
 *
 * Licensed under the Pi Dragon, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://pidragron.com/licenses/LICENSE-1.0
 *
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
@Table(name = "fa_feature_assignee")
public class FeatureAssignee extends FaBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Feature feature;
	private Long assigneeRefId;
	private boolean notifyChange;
	// add a role
	
	// Constructor
	public FeatureAssignee() {
		super();
	}
	
	public FeatureAssignee(Feature feature, Long assigneeRefId) {
		super();
		setFeature(feature);
		setAssigneeRefId(assigneeRefId);
	}
	
	// getters and setters
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
