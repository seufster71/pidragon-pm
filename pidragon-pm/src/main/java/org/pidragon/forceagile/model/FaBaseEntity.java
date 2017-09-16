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

import javax.persistence.MappedSuperclass;

import org.toasthub.core.general.model.BaseEntity;



@MappedSuperclass()
public class FaBaseEntity extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String INCLUDEACQUAINTANCE = "includeAcquaintance";
	public static final String CATEGORY = "category";
	public static final String CODEBRANCH = "codeBranch";
	public static final String CODEREPO = "codeRepo";
	public static final String FEATURE = "feature";
	public static final String BLUEPRINT = "bluePrint";
	public static final String ISSUE = "issue";
	public static final String REQUIREMENT = "requirement";
	public static final String BACKLOG = "backLog";
	public static final String QUESTION = "question";
	public static final String USERSTORY = "userStory";
	public static final String TESTSUITE = "testSuite";
	public static final String TESTCASE = "testCase";
	
	//Constructor
	public FaBaseEntity() {
		super();
	}
	
}
