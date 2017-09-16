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
@Table(name = "fa_blueprint")
public class BluePrint extends FaBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String BLUEPRINTS = "bluePrints";
	public static final String ID = "bluePrintId";
	
	private String title;
	private String desc;
	private Long ownerRefId;
	private String status; // Active, Final
	private int productVersion;
	private BluePrint parent;
	
	// Constructor
	public BluePrint() {
		super();
	}
	
	public BluePrint(String title, String desc, Long ownerRefId, String status) {
		super();
		setTitle(title);
		setDesc(desc);
		setOwnerRefId(ownerRefId);
		setStatus(status);
	}
	// Constructor for ajax
	public BluePrint(RestRequest request, RestResponse response, String formName) {
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
	@JoinColumn(name = "owner_id")
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
	@Column(name = "product_version")
	public int getProductVersion() {
		return productVersion;
	}
	public void setProductVersion(int productVersion) {
		this.productVersion = productVersion;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@ManyToOne(targetEntity = BluePrint.class)
	@JoinColumn(name = "parent_id")
	public BluePrint getParent() {
		return parent;
	}
	public void setParent(BluePrint parent) {
		this.parent = parent;
	}
}
