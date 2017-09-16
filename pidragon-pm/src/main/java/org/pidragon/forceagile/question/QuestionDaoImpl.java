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

package org.pidragon.forceagile.question;

import org.pidragon.forceagile.common.FABaseDaoImpl;
import org.pidragon.forceagile.model.Question;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;

public class QuestionDaoImpl extends FABaseDaoImpl implements QuestionDao {


	public void save(RestRequest request, RestResponse response) throws Exception {
		Question question = (Question) request.getParam(Question.QUESTION);
		if (question == null) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Missing param", response);
		}
		entityManagerDataSvc.getInstance().merge(question);
		response.addParam(Question.ID, question.getId());
	}
	
	public void delete(RestRequest request, RestResponse response) throws Exception {
		Question question = (Question) entityManagerDataSvc.getInstance().getReference(Question.class, new Long((Integer) request.getParam("id")));
		entityManagerDataSvc.getInstance().remove(question);
	}
}
