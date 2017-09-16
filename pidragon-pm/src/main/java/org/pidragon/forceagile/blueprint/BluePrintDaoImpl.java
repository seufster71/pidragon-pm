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

package org.pidragon.forceagile.blueprint;

import org.pidragon.forceagile.common.FABaseDaoImpl;
import org.pidragon.forceagile.model.BluePrint;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toasthub.core.general.model.GlobalConstant;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;

@Repository("BluePrintDao")
@Transactional("TransactionManagerData")
public class BluePrintDaoImpl extends FABaseDaoImpl implements BluePrintDao {

	public void save(RestRequest request, RestResponse response) throws Exception {
		BluePrint bluePrint = (BluePrint) request.getParam(BluePrint.BLUEPRINT);
		if (bluePrint == null) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Missing param", response);
		}
		entityManagerDataSvc.getInstance().merge(bluePrint);
		response.addParam(BluePrint.ID, bluePrint.getId());
	}
	
	public void delete(RestRequest request, RestResponse response) throws Exception {
		BluePrint bluePrint = (BluePrint) entityManagerDataSvc.getInstance().getReference(BluePrint.class, new Long((Integer) request.getParam(GlobalConstant.ID)));
		entityManagerDataSvc.getInstance().remove(bluePrint);
	}
}
