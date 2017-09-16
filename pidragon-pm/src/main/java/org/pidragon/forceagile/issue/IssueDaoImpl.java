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

import java.util.HashSet;

import javax.persistence.Query;

import org.pidragon.forceagile.common.FABaseDaoImpl;
import org.pidragon.forceagile.model.Issue;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toasthub.core.general.model.GlobalConstant;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.social.model.AttachmentMeta;
import org.toasthub.social.model.Directory;
import org.toasthub.social.model.UserRef;

@Repository("IssueDao")
@Transactional("TransactionManagerData")
public class IssueDaoImpl extends FABaseDaoImpl implements IssueDao {
	
	@Override
	public void save(RestRequest request, RestResponse response) throws Exception {

		Issue issue = (Issue) request.getParam(Issue.ISSUE);
		UserRef userRef = (UserRef) entityManagerDataSvc.getInstance().getReference(UserRef.class, ((UserRef) request.getParam(GlobalConstant.USERREF)).getId());
		issue.setOwner(userRef);
		Issue i = entityManagerDataSvc.getInstance().merge(issue);
		request.addParam(Issue.ISSUE, i);
		request.addParam(Issue.ID, i.getId());
	}

	public void delete(RestRequest request, RestResponse response) throws Exception {
		Issue issue = (Issue) entityManagerDataSvc.getInstance()
				.getReference(Issue.class, new Long((Integer) request.getParam("id")));
		entityManagerDataSvc.getInstance().remove(issue);
	}


	@Override
	public void saveAttachment(RestRequest request, RestResponse response) throws Exception {
		AttachmentMeta attachment = (AttachmentMeta) request.getParam("attachmentMeta");
		Long id = Long.parseLong((String) request.getParam(Issue.ID));
		UserRef userRef = (UserRef) entityManagerDataSvc.getInstance().getReference(UserRef.class, ((UserRef) request.getParam(GlobalConstant.USERREF)).getId());
		attachment.setUserRef(userRef);
		Directory directory = null;
		try {
			directory = (Directory) entityManagerDataSvc.getInstance().createQuery("FROM Directory AS d WHERE d.name = :name").setParameter("name",Issue.BASEDIRECTORY).getSingleResult();
		} catch (Exception e) {
			// no directory found 
		}
		if (directory == null){
			Directory myDir = new Directory(Issue.BASEDIRECTORY,userRef);
			attachment.setDirectory(myDir);
		} else {
			attachment.setDirectory(directory);
		}
		// add 
		Issue issue = (Issue) entityManagerDataSvc.getInstance().getReference(Issue.class, id);
		if (issue.getAttachments() == null){
			issue.setAttachments(new HashSet<AttachmentMeta>());
		}
		issue.getAttachments().add(attachment);
		entityManagerDataSvc.getInstance().merge(issue);
	}

	@Override
	public void deleteAttachment(RestRequest request, RestResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void getThumbNail(RestRequest request, RestResponse response) throws Exception {	
		String HQLQuery = "FROM AttachmentMeta as a inner join fetch a.thumbNail WHERE a.id = :id";
		Query query = entityManagerDataSvc.getInstance().createQuery(HQLQuery);
		query.setParameter("id", Long.parseLong((String)request.getParam("imageId")));
		response.addParam("attachment", (AttachmentMeta) query.getSingleResult());
	}

}
