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

import org.pidragon.forceagile.common.FABaseDaoImpl;
import org.pidragon.forceagile.model.TestSuite;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;

@Repository("TestSuiteDao")
@Transactional("TransactionManagerData")
public class TestSuiteDaoImpl extends FABaseDaoImpl implements TestSuiteDao{

	@Override
	public void save(RestRequest request, RestResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(RestRequest request, RestResponse response) throws Exception {
		TestSuite testSuite = (TestSuite) entityManagerDataSvc.getInstance().getReference(TestSuite.class, new Long((Integer) request.getParam("id")));
		entityManagerDataSvc.getInstance().remove(testSuite);
	}
	
}
