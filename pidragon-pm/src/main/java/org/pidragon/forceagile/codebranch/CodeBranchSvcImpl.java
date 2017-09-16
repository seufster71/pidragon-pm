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

package org.pidragon.forceagile.codebranch;

import org.pidragon.forceagile.model.CodeBranch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.toasthub.core.common.UtilSvc;
import org.toasthub.core.general.handler.ServiceProcessor;
import org.toasthub.core.general.model.GlobalConstant;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.core.preference.model.AppCachePageUtil;

@Service("CodeBranchSvc")
public class CodeBranchSvcImpl implements ServiceProcessor, CodeBranchSvc {

	@Autowired
	@Qualifier("CodeBranchDao")
	CodeBranchDao codeBranchDao;
	
	@Autowired
	UtilSvc utilSvc;
	
	@Autowired
	AppCachePageUtil appCachePageUtil;
	
	@Override
	public void process(RestRequest request, RestResponse response) {
		String action = (String) request.getParams().get(GlobalConstant.ACTION);
		
		Long count = 0l;
		switch (action) {
		case "INIT": 
			request.addParam(appCachePageUtil.APPPAGEPARAMLOC, appCachePageUtil.RESPONSE);
			appCachePageUtil.getPageInfo(request,response);
			this.itemCount(request, response);
			count = (Long) response.getParam(GlobalConstant.ITEMCOUNT);
			if (count != null && count > 0){
				this.items(request, response);
			}
			response.addParam(GlobalConstant.ITEMNAME, request.getParam(GlobalConstant.ITEMNAME));
			break;
		case "LIST":
			request.addParam(appCachePageUtil.APPPAGEPARAMLOC, appCachePageUtil.RESPONSE);
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
			codeBranchDao.itemCount(request, response);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Save Failed", response);
			e.printStackTrace();
		}
	}
	
	protected void items(RestRequest request, RestResponse response) {
		try {
			codeBranchDao.items(request, response);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Save Failed", response);
			e.printStackTrace();
		}
	}
	
	protected void item(RestRequest request, RestResponse response) {
		try {
			codeBranchDao.item(request, response);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Save Failed", response);
			e.printStackTrace();
		}
	}
	
	//@Authorize
	protected void save(RestRequest request, RestResponse response){
		try {
			// validate
			utilSvc.validateParams(request, response);
			
			if ((Boolean) request.getParam(GlobalConstant.VALID) == false) {
				utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Validation Error", response);
				return;
			}
			// get existing item
			if (request.containsParam(GlobalConstant.ITEMID) && !request.getParam(GlobalConstant.ITEMID).equals("")) {
				codeBranchDao.item(request, response);
				request.addParam(GlobalConstant.ITEM, response.getParam(GlobalConstant.ITEM));
				response.getParams().remove(GlobalConstant.ITEM);
			} else {
				request.addParam(GlobalConstant.ITEM, new CodeBranch());
			}
			// marshall
			utilSvc.marshallFields(request, response);
						
			codeBranchDao.save(request, response);
			
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Save Failed", response);
			e.printStackTrace();
		}
	}
	
	//@Authorize
	protected void delete(RestRequest request, RestResponse response){
		try {
			codeBranchDao.delete(request, response);
			utilSvc.addStatus(RestResponse.INFO, RestResponse.SUCCESS, "Delete successful", response);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Delete Failed", response);
			e.printStackTrace();
		}
	}
}
