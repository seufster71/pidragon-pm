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

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Query;

import org.pidragon.forceagile.common.FABaseDaoImpl;
import org.pidragon.forceagile.model.Requirement;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toasthub.core.general.model.GlobalConstant;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.social.model.AttachmentMeta;
import org.toasthub.social.model.Directory;
import org.toasthub.social.model.UserRef;

@Repository("GroupDao")
@Transactional("TransactionManagerData")
public class RequirementDaoImpl extends FABaseDaoImpl implements RequirementDao {

	@Override
	public void save(RestRequest request, RestResponse response) throws Exception {
		Requirement requirement = (Requirement) request.getParam(Requirement.REQUIREMENT);
		UserRef userRef = (UserRef) entityManagerDataSvc.getInstance().getReference(UserRef.class, ((UserRef) request.getParam(GlobalConstant.USERREF)).getId());
		requirement.setOwner(userRef);
		Requirement r = entityManagerDataSvc.getInstance().merge(requirement);
		request.addParam(Requirement.REQUIREMENT, r);
		request.addParam(Requirement.ID, r.getId());
	}

	@Override
	public void delete(RestRequest request, RestResponse response) throws Exception {
		Requirement requirement = (Requirement) entityManagerDataSvc.getInstance().getReference(Requirement.class, new Long((Integer) request.getParam("id")));
		entityManagerDataSvc.getInstance().remove(requirement);
	}


	@Override
	public void saveAttachment(RestRequest request, RestResponse response) throws Exception {
		AttachmentMeta attachment = (AttachmentMeta) request.getParam("attachmentMeta");
		Long id = Long.parseLong((String) request.getParam(Requirement.ID));
		UserRef userRef = (UserRef) entityManagerDataSvc.getInstance().getReference(UserRef.class, ((UserRef) request.getParam(GlobalConstant.USERREF)).getId());
		attachment.setUserRef(userRef);
		Directory directory = null;
		try {
			directory = (Directory) entityManagerDataSvc.getInstance().createQuery("FROM Directory AS d WHERE d.name = :name").setParameter("name",Requirement.BASEDIRECTORY).getSingleResult();
		} catch (Exception e) {
			// no directory found 
		}
		if (directory == null){
			Directory myDir = new Directory(Requirement.BASEDIRECTORY,userRef);
			attachment.setDirectory(myDir);
		} else {
			attachment.setDirectory(directory);
		}
		// add 
		Requirement requirement = (Requirement) entityManagerDataSvc.getInstance().getReference(Requirement.class, id);
		if (requirement.getAttachments() == null){
			requirement.setAttachments(new HashSet<AttachmentMeta>());
		}
		requirement.getAttachments().add(attachment);
		entityManagerDataSvc.getInstance().merge(requirement);
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

	public void itemCheck(RestRequest request, RestResponse response) throws Exception {	
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(d);
	    cal.add(Calendar.MINUTE, -30);
		
		String HQLQuery = "SELECT f.id FROM Feature AS f WHERE f.active = :active AND ";
		HQLQuery += "f.owner.id in (SELECT a.acquaintance.id FROM Acquaintance as a WHERE a.user.id = :uid) AND f.created > :fromdate ";
		HQLQuery += "ORDER BY f.created DESC ";
		
		Query query = entityManagerDataSvc.getInstance().createQuery(HQLQuery);
		query.setParameter("uid", ((UserRef) request.getParam(GlobalConstant.USERREF)).getId());
		query.setParameter("fromdate", cal.getTime());
		if (request.containsParam("active")) {
			query.setParameter("active", request.getParam("active"));
		} else {
			query.setParameter("active", true);
		}
		
		List<Long> items = (List<Long>) query.getResultList();
		
		
		response.addParam(Requirement.REQUIREMENTS, items);
	}
}
