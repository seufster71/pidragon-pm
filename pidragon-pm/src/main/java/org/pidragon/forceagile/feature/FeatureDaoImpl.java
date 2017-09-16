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

package org.pidragon.forceagile.feature;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Query;

import org.pidragon.forceagile.model.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toasthub.core.common.EntityManagerDataSvc;
import org.toasthub.core.common.UtilSvc;
import org.toasthub.core.general.model.GlobalConstant;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.social.model.AttachmentMeta;
import org.toasthub.social.model.Directory;
import org.toasthub.social.model.UserRef;

@Repository("FeatureDao")
@Transactional("TransactionManagerData")
public class FeatureDaoImpl implements FeatureDao {
	
	@Autowired 
	protected EntityManagerDataSvc entityManagerDataSvc;
	@Autowired
	protected UtilSvc utilSvc;
	
	@Override
	public void save(RestRequest request, RestResponse response) throws Exception {
		Feature feature = (Feature) request.getParam(Feature.FEATURE);
		UserRef userRef = (UserRef) entityManagerDataSvc.getInstance().getReference(UserRef.class, ((UserRef) request.getParam(GlobalConstant.USERREF)).getId());
		feature.setOwner(userRef);
		Feature f = entityManagerDataSvc.getInstance().merge(feature);
		request.addParam(Feature.FEATURE, f);
		request.addParam(Feature.ID, f.getId());
	}

	@Override
	public void delete(RestRequest request, RestResponse response) throws Exception {
		Feature feature = (Feature) entityManagerDataSvc.getInstance().getReference(Feature.class, new Long((Integer) request.getParam("id")));
		entityManagerDataSvc.getInstance().remove(feature);
	}


	@Override
	public void saveAttachment(RestRequest request, RestResponse response) throws Exception {
		AttachmentMeta attachment = (AttachmentMeta) request.getParam("attachmentMeta");
		Long id = Long.parseLong((String) request.getParam(Feature.ID));
		UserRef userRef = (UserRef) entityManagerDataSvc.getInstance().getReference(UserRef.class, ((UserRef) request.getParam(GlobalConstant.USERREF)).getId());
		attachment.setUserRef(userRef);
		Directory directory = null;
		try {
			directory = (Directory) entityManagerDataSvc.getInstance()
					.createQuery("FROM Directory AS d WHERE d.name = :name")
					.setParameter("name",Feature.BASEDIRECTORY).getSingleResult();
		} catch (Exception e) {
			// no directory found 
		}
		if (directory == null){
			Directory myDir = new Directory(Feature.BASEDIRECTORY,userRef);
			attachment.setDirectory(myDir);
		} else {
			attachment.setDirectory(directory);
		}
		// add 
		Feature feature = (Feature) entityManagerDataSvc.getInstance().getReference(Feature.class, id);
		if (feature.getAttachments() == null){
			feature.setAttachments(new HashSet<AttachmentMeta>());
		}
		feature.getAttachments().add(attachment);
		entityManagerDataSvc.getInstance().merge(feature);
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
		
		
		response.addParam(Feature.FEATURES, items);
	}

	@Override
	public void items(RestRequest request, RestResponse response) throws Exception {
		String queryStr = "SELECT DISTINCT f FROM Feature AS f ";
		
		boolean and = false;
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			if (!and) { queryStr += " WHERE "; }
			queryStr += "f.active =:active ";
			and = true;
		}
		
		if (request.containsParam(GlobalConstant.SEARCHVALUE) && !request.getParam(GlobalConstant.SEARCHVALUE).equals("")){
			if (!and) { queryStr += " WHERE "; } else { queryStr += " AND "; }
			queryStr += "f.title LIKE :searchValue"; 
			and = true;
		}
		
		Query query = entityManagerDataSvc.getInstance().createQuery(queryStr);
		
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			query.setParameter("active", (Boolean) request.getParam(GlobalConstant.ACTIVE));
		} 
		
		if (request.containsParam(GlobalConstant.SEARCHVALUE) && !request.getParam(GlobalConstant.SEARCHVALUE).equals("")){
			query.setParameter("searchValue", "%"+((String)request.getParam(GlobalConstant.SEARCHVALUE)).toLowerCase()+"%");
		}
		if (request.containsParam(GlobalConstant.PAGELIMIT) && (Integer) request.getParam(GlobalConstant.PAGELIMIT) != 0){
			query.setFirstResult((Integer) request.getParam(GlobalConstant.PAGESTART));
			query.setMaxResults((Integer) request.getParam(GlobalConstant.PAGELIMIT));
		}
		@SuppressWarnings("unchecked")
		List<Feature> features = query.getResultList();

		response.addParam(GlobalConstant.ITEMS, features);
		
	}

	@Override
	public void itemCount(RestRequest request, RestResponse response) throws Exception {
		String queryStr = "SELECT COUNT(*) FROM Feature as f ";
		boolean and = false;
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			if (!and) { queryStr += " WHERE "; }
			queryStr += "f.active =:active ";
			and = true;
		}
		
		if (request.containsParam(GlobalConstant.SEARCHVALUE) && !request.getParam(GlobalConstant.SEARCHVALUE).equals("")){
			if (!and) { queryStr += " WHERE "; } else { queryStr += " AND "; }
			queryStr += "f.title LIKE :searchValue"; 
			and = true;
		}

		Query query = entityManagerDataSvc.getInstance().createQuery(queryStr);
		
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			query.setParameter("active", (Boolean) request.getParam(GlobalConstant.ACTIVE));
		} 
		
		if (request.containsParam(GlobalConstant.SEARCHVALUE) && !request.getParam(GlobalConstant.SEARCHVALUE).equals("")){
			query.setParameter("searchValue", "%"+((String)request.getParam(GlobalConstant.SEARCHVALUE)).toLowerCase()+"%");
		}
		
		Long count = (Long) query.getSingleResult();
		if (count == null){
			count = 0l;
		}
		response.addParam(GlobalConstant.ITEMCOUNT, count);
		
	}

	@Override
	public void item(RestRequest request, RestResponse response) throws Exception {
		if (request.containsParam(GlobalConstant.ITEMID) && !"".equals(request.getParam(GlobalConstant.ITEMID))) {
			String queryStr = "SELECT f FROM Feature AS f WHERE f.id =:id";
			Query query = entityManagerDataSvc.getInstance().createQuery(queryStr);
		
			query.setParameter("id", new Long((String) request.getParam(GlobalConstant.ITEMID)));
			Feature feature = (Feature) query.getSingleResult();
			
			response.addParam(GlobalConstant.ITEM, feature);
		} else {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Missing ID", response);
		}
		
	}
}
