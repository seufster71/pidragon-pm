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

package org.pidragon.forceagile.testsuite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.toasthub.core.common.UtilSvc;
import org.toasthub.core.general.handler.ServiceProcessor;
import org.toasthub.core.general.model.GlobalConstant;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.core.preference.model.AppCachePageUtil;

public class TestSuiteSvcImpl implements ServiceProcessor, TestSuiteSvc {

	@Autowired
	@Qualifier("TestSuiteDao")
	TestSuiteDao testSuiteDao;
	
	@Autowired
	UtilSvc utilSvc;
	
	@Autowired
	AppCachePageUtil appCachePageUtil;
	
	public void process(RestRequest request, RestResponse response) {
		String action = (String) request.getParams().get(GlobalConstant.ACTION);
		
		Long count = 0l;
		switch (action) {
		case "INIT": 
			request.addParam(AppCachePageUtil.APPPAGEPARAMLOC, AppCachePageUtil.RESPONSE);
			appCachePageUtil.getPageInfo(request,response);
			this.itemCount(request, response);
			count = (Long) response.getParam(GlobalConstant.ITEMCOUNT);
			if (count != null && count > 0){
				this.items(request, response);
			}
			response.addParam(GlobalConstant.ITEMNAME, request.getParam(GlobalConstant.ITEMNAME));
			break;
		case "LIST":
			request.addParam(AppCachePageUtil.APPPAGEPARAMLOC, AppCachePageUtil.RESPONSE);
			appCachePageUtil.getPageInfo(request,response);
			this.itemCount(request, response);
			count = (Long) response.getParam(GlobalConstant.ITEMCOUNT);
			if (count != null && count > 0){
				this.items(request, response);
			}
			response.addParam(GlobalConstant.ITEMNAME, request.getParam(GlobalConstant.ITEMNAME));
			break;
		case "SHOW":
			this.item(request, response);
			break;
		case "SAVE":
			appCachePageUtil.getPageInfo(request,response);
			this.save(request, response);
			break;
		case "DELETE":
			this.delete(request, response);
			break;
		default:
			utilSvc.addStatus(RestResponse.INFO, RestResponse.ACTIONNOTEXIST, "Action not available", response);
			break;
		}
	}
	
	protected void itemCount(RestRequest request, RestResponse response) {
		try {
			testSuiteDao.itemCount(request, response);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Save Failed", response);
			e.printStackTrace();
		}
	}
	
	protected void items(RestRequest request, RestResponse response) {
		try {
			testSuiteDao.items(request, response);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Save Failed", response);
			e.printStackTrace();
		}
	}
	
	protected void item(RestRequest request, RestResponse response) {
		try {
			testSuiteDao.item(request, response);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Save Failed", response);
			e.printStackTrace();
		}
	}
	
	//@Authorize
	protected void save(RestRequest request, RestResponse response){
		try {
			testSuiteDao.save(request, response);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Save Failed", response);
			e.printStackTrace();
		}
	}
	
	//@Authorize
	protected void delete(RestRequest request, RestResponse response){
		try {
			testSuiteDao.delete(request, response);
			utilSvc.addStatus(RestResponse.INFO, RestResponse.SUCCESS, "Delete successful", response);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Delete Failed", response);
			e.printStackTrace();
		}
	}
}
