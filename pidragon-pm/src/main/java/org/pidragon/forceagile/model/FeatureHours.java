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
@Table(name = "fa_feature_hours")
public class FeatureHours extends FaBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date startTime;
	private Date endTime;
	private double hours;
	private Feature feature;
	private Long ownerRefId;

	// Constructor
	public FeatureHours() {
		super();
	}
	
	public FeatureHours(Date startTime, Feature feature, Long ownerRefId){
		super();
		setStartTime(startTime);
		setFeature(feature);
		setOwnerRefId(ownerRefId);
	}
	
	public FeatureHours(double hours, Feature feature, Long ownerRefId){
		super();
		setHours(hours);
		setFeature(feature);
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
	@JoinColumn(name = "feature_id")
	public Feature getFeature() {
		return feature;
	}
	public void setFeature(Feature feature) {
		this.feature = feature;
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
