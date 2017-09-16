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
import javax.persistence.Table;

import org.toasthub.core.general.api.View;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "fa_code_branch")
public class CodeBranch extends FaBaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String CODEBRANCHES = "codeBranches";
	public static final String ID = "codeBranchId";

	private String name;
	private String branchRef;  //remote id actual branch

	//Constructor
	public CodeBranch(){
		super();
	}
	
	public CodeBranch(String name){
		super();
		setName(name);
	}
	// Constructor for ajax
	public CodeBranch (RestRequest request, RestResponse response, String formName) {
		//userInputHelper(request, response, formName);
	}
		
	// Setter/Getter
	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@JsonView({View.Member.class,View.Admin.class})
	@Column(name = "branch_ref")
	public String getBranchRef() {
		return branchRef;
	}
	public void setBranchRef(String branchRef) {
		this.branchRef = branchRef;
	}

}
