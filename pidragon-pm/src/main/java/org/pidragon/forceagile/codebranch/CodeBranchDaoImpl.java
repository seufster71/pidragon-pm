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

import org.pidragon.forceagile.common.FABaseDaoImpl;
import org.pidragon.forceagile.model.CodeBranch;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toasthub.core.general.model.GlobalConstant;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;

@Repository("CodeBranchDao")
@Transactional("TransactionManagerData")
public class CodeBranchDaoImpl extends FABaseDaoImpl implements CodeBranchDao {
	
	public void save(RestRequest request, RestResponse response) throws Exception {
		CodeBranch codeBranch = (CodeBranch) request.getParam(CodeBranch.CODEBRANCH);
		if (codeBranch == null) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Missing param", response);
		}
		entityManagerDataSvc.getInstance().merge(codeBranch);
		response.addParam(CodeBranch.ID, codeBranch.getId());
	}
	
	public void delete(RestRequest request, RestResponse response) throws Exception {
		CodeBranch codeBranch = (CodeBranch) entityManagerDataSvc.getInstance().getReference(CodeBranch.class, new Long((Integer) request.getParam(GlobalConstant.ID)));
		entityManagerDataSvc.getInstance().remove(codeBranch);
	}
}
