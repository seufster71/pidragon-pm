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

package org.pidragon.forceagile.requirement;

import org.pidragon.forceagile.common.FABaseDao;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;

public interface RequirementDao extends FABaseDao {
	
	public void items(RestRequest request, RestResponse response) throws Exception;
	public void itemCount(RestRequest request, RestResponse response) throws Exception;
	public void item(RestRequest request, RestResponse response) throws Exception;
	public void save(RestRequest request, RestResponse response) throws Exception;
	public void delete(RestRequest request, RestResponse response) throws Exception;
	public void getThumbNail(RestRequest request, RestResponse response) throws Exception;
	public void itemCheck(RestRequest request, RestResponse response) throws Exception;
}
