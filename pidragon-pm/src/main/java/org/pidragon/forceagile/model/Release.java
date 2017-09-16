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
import javax.persistence.Table;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "fa_release")
public class Release extends FaBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String RELEASES = "releases";
	public static final String ID = "releaseId";
	
	private String name;
	private String desc;
	private Long ownerRefId;
	private Date startDate;
	private Date actualStartDate;
	private Date endDate;
	private Date actualEndDate;
	private Date deployDate;
	private Date actualDeployDate;
	
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	@Column(name = "deploy_date")
	public Date getDeployDate() {
		return deployDate;
	}
	public void setDeployDate(Date deployDate) {
		this.deployDate = deployDate;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "actual_start_date")
	public Date getActualStartDate() {
		return actualStartDate;
	}
	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "actual_end_date")
	public Date getActualEndDate() {
		return actualEndDate;
	}
	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}
	
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "actual_deploy_date")
	public Date getActualDeployDate() {
		return actualDeployDate;
	}
	public void setActualDeployDate(Date actualDeployDate) {
		this.actualDeployDate = actualDeployDate;
	}
}
