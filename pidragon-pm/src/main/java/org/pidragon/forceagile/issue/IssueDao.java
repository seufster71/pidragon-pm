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

package org.pidragon.forceagile.issue;

import org.pidragon.forceagile.common.FABaseDao;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;

public interface IssueDao extends FABaseDao {
	
	void items(RestRequest request, RestResponse response) throws Exception;
	void itemCount(RestRequest request, RestResponse response) throws Exception;
	void item(RestRequest request, RestResponse response) throws Exception;
	void save(RestRequest request, RestResponse response) throws Exception;
	void delete(RestRequest request, RestResponse response) throws Exception;
	void getThumbNail(RestRequest request, RestResponse response) throws Exception;
	void itemCheck(RestRequest request, RestResponse response) throws Exception;
}
