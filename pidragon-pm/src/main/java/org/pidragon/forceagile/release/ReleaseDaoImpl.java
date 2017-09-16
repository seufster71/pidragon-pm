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

package org.pidragon.forceagile.release;

import org.pidragon.forceagile.common.FABaseDaoImpl;
import org.pidragon.forceagile.model.Release;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;

@Repository("ReleaseDao")
@Transactional("TransactionManagerData")
public class ReleaseDaoImpl extends FABaseDaoImpl implements ReleaseDao {
	
	public void save(RestRequest request, RestResponse response) throws Exception {
		Release release = (Release) request.getParam(Release.CODEREPO);
		if (release == null) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Missing param", response);
		}
		entityManagerDataSvc.getInstance().merge(release);
		response.addParam(Release.ID, release.getId());	
	}
	
	public void delete(RestRequest request, RestResponse response) throws Exception {
		Release release = (Release) entityManagerDataSvc.getInstance().getReference(Release.class, new Long((Integer) request.getParam("id")));
		entityManagerDataSvc.getInstance().remove(release);
	}
}
