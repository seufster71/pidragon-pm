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

package org.pidragon.forceagile.common;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.pidragon.forceagile.model.FaBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toasthub.core.common.EntityManagerDataSvc;
import org.toasthub.core.common.UtilSvc;
import org.toasthub.core.general.model.GlobalConstant;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;
import org.toasthub.core.general.utils.Utils;
import org.toasthub.social.model.UserRef;

@Repository("FABaseDao")
@Transactional("TransactionManagerData")
public class FABaseDaoImpl implements FABaseDao{

	@Autowired 
	protected EntityManagerDataSvc entityManagerDataSvc;
	@Autowired
	protected UtilSvc utilSvc;

	
	public void items(RestRequest request, RestResponse response) throws Exception {
		String itemName = (String) request.getParam(GlobalConstant.ITEMNAME);
		String queryStr = "";
		if (request.containsParam(GlobalConstant.COLUMNS)){
			String[] columns = (String[]) request.getParam(GlobalConstant.COLUMNS);
			String c = Utils.arrayToComma(columns);
			queryStr += "SELECT new "+ itemName + "( id," + c + ")";
		}
		queryStr += " FROM " + itemName;
		boolean and = false;
		if (request.containsParam(GlobalConstant.SEARCHVALUE) && !request.getParam(GlobalConstant.SEARCHVALUE).equals("")){
			if (!and) { queryStr += " WHERE "; }
			queryStr += "lower(" + request.getParam(GlobalConstant.SEARCHCOLUMN) + ") LIKE :searchValue"; 
			and = true;
		}
		if (request.containsParam(GlobalConstant.OWNER)) {
			if (and) { queryStr += " AND "; } else { queryStr += " WHERE "; }
			if (request.containsParam(FaBaseEntity.INCLUDEACQUAINTANCE) && (Boolean) request.getParam(FaBaseEntity.INCLUDEACQUAINTANCE)){
				queryStr += "ownerRefId =:uid OR ownerRefId in (SELECT a.acquaintance.id FROM Acquaintance as a WHERE a.userRefId = :uid)) ";
			} else {
				queryStr += "ownerRefId =:uid";
			}
			and = true;
		}
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			if (and) { queryStr += " AND "; } else { queryStr += " WHERE "; }
			queryStr += "active =:active";
		}
		if (request.containsParam(GlobalConstant.ORDERCOLUMN)) {
			String direction = "DESC";
			if (request.containsParam(GlobalConstant.ORDERDIR)) {
				direction = (String) request.getParam(GlobalConstant.ORDERDIR);
			}
			queryStr += " ORDER BY "+(String) request.getParam(GlobalConstant.ORDERCOLUMN)+" "+direction;
		}
		Query query = entityManagerDataSvc.getInstance().createQuery(queryStr);
		if (request.containsParam(GlobalConstant.SEARCHVALUE) && !request.getParam(GlobalConstant.SEARCHVALUE).equals("")){
			query.setParameter(GlobalConstant.SEARCHVALUE, "%"+((String)request.getParam(GlobalConstant.SEARCHVALUE)).toLowerCase()+"%");
		}
		if (request.containsParam(GlobalConstant.OWNER)) {
			query.setParameter("uid", new Long((String) request.getParam(GlobalConstant.OWNER)));
		} 
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			query.setParameter("active", (Boolean) request.getParam(GlobalConstant.ACTIVE));
		} else {
			query.setParameter("active", true);
		}
		if (request.containsParam(GlobalConstant.PAGELIMIT) && (Integer) request.getParam(GlobalConstant.PAGELIMIT) != 0){
			query.setFirstResult((Integer) request.getParam(GlobalConstant.PAGESTART));
			query.setMaxResults((Integer) request.getParam(GlobalConstant.PAGELIMIT));
		}
		response.addParam(GlobalConstant.ITEMS, (List<?>) query.getResultList());
	}
	
	public void itemCount(RestRequest request, RestResponse response) throws Exception {
		String itemName = (String) request.getParam(GlobalConstant.ITEMNAME);
		String queryStr = "SELECT COUNT(*) FROM " + itemName;
		
		boolean and = false;
		if (request.containsParam(GlobalConstant.SEARCHVALUE) && !request.getParam(GlobalConstant.SEARCHVALUE).equals("")){
			if (!and) { queryStr += " WHERE "; }
			queryStr += "lower(" + request.getParam(GlobalConstant.SEARCHCOLUMN) + ") LIKE :searchValue"; 
			and = true;
		}
		if (request.containsParam(GlobalConstant.OWNER)) {
			if (and) { queryStr += " AND "; } else { queryStr += " WHERE "; }
			if (request.containsParam(FaBaseEntity.INCLUDEACQUAINTANCE) && (Boolean) request.getParam(FaBaseEntity.INCLUDEACQUAINTANCE)){
				queryStr += "ownerRefId =:uid OR ownerRefId in (SELECT a.acquaintance.id FROM Acquaintance as a WHERE a.userRefId = :uid)) ";
			} else {
				queryStr += "ownerRefId =:uid";
			}
			and = true;
		}
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			if (and) { queryStr += " AND "; } else { queryStr += " WHERE "; }
			queryStr += "active =:active";
		}
		
		Query query = entityManagerDataSvc.getInstance().createQuery(queryStr);
		if (request.containsParam(GlobalConstant.SEARCHVALUE) && !request.getParam(GlobalConstant.SEARCHVALUE).equals("")){
			query.setParameter(GlobalConstant.SEARCHVALUE, "%"+((String) request.getParam(GlobalConstant.SEARCHVALUE)).toLowerCase()+"%");
		}
		if (request.containsParam(GlobalConstant.OWNER)) {
			query.setParameter("uid", new Long((String) request.getParam(GlobalConstant.OWNER)));
		} 
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			query.setParameter("active", (Boolean) request.getParam(GlobalConstant.ACTIVE));
		} else {
			query.setParameter("active", true);
		}
		Long count = (Long) query.getSingleResult();
		if (count == null){
			count = 0l;
		}
		response.addParam(GlobalConstant.ITEMCOUNT, count);
	
	}
	
	public void item(RestRequest request, RestResponse response) throws Exception {
		String tableName = (String) request.getParam(GlobalConstant.ITEMNAME);
		if (tableName != null){
			String queryStr = "FROM " + tableName + " WHERE id = :id";
			Query query = entityManagerDataSvc.getInstance().createQuery(queryStr)
					.setParameter("id",new Long((Integer) request.getParam(GlobalConstant.ITEMID)));
			response.addParam(GlobalConstant.ITEM, query.getSingleResult());
		} else {
			
		}
	}
	/*
	public AttachmentMeta getAttachmentMeta(Long metaId) {
		AttachmentMeta attachmentMeta = (AttachmentMeta) entityManagerDataSvc.getInstance().createQuery("SELECT NEW AttachmentMeta(a.id,a.title,a.fileName,a.filePath,a.size,a.contentType) FROM AttachmentMeta a WHERE a.id = :id").setParameter("id",metaId).getSingleResult();
		return attachmentMeta;
	}
	
	public AttachmentMeta getAttachment(Long id) {
		AttachmentMeta attachmentMeta = (AttachmentMeta) entityManagerDataSvc.getInstance().createQuery("FROM AttachmentMeta a INNER JOIN FETCH a.data WHERE a.id = :id").setParameter("id",id).getSingleResult();
		return attachmentMeta;
	}
	*/
	public void saveAttachment(RestRequest request, RestResponse response) throws Exception {
		
	}
	
	public void deleteAttachment(RestRequest request, RestResponse response) throws Exception {
		
	}
	
	public void itemCheck(RestRequest request, RestResponse response) throws Exception {	
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
	    cal.setTime(d);
	    cal.add(Calendar.MINUTE, -30);
	    String itemName = (String) request.getParam(GlobalConstant.ITEMNAME);
	    String queryStr = "SELECT id FROM "+ itemName;

		boolean and = false;
		if (request.containsParam(GlobalConstant.OWNER)) {
			if (and) { queryStr += " AND "; } else { queryStr += " WHERE "; }
			if (request.containsParam(FaBaseEntity.INCLUDEACQUAINTANCE) && (Boolean) request.getParam(FaBaseEntity.INCLUDEACQUAINTANCE)){
				queryStr += "ownerRefId =:uid OR ownerRefId in (SELECT a.acquaintance.id FROM Acquaintance as a WHERE a.userRefId = :uid)) ";
			} else {
				queryStr += "ownerRefId =:uid";
			}
			and = true;
		}
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			if (and) { queryStr += " AND "; } else { queryStr += " WHERE "; }
			queryStr += "active =:active";
		}
		if (and) { queryStr += " AND "; } else { queryStr += " WHERE "; }
		queryStr += "created > :fromdate";
		if (request.containsParam(GlobalConstant.ORDERCOLUMN)) {
			String direction = "DESC";
			if (request.containsParam(GlobalConstant.ORDERDIR)) {
				direction = (String) request.getParam(GlobalConstant.ORDERDIR);
			}
			queryStr += " ORDER BY "+(String) request.getParam(GlobalConstant.ORDERDIR)+" "+direction;
		}
		
		
		Query query = entityManagerDataSvc.getInstance().createQuery(queryStr);
		query.setParameter("uid", ((UserRef) request.getParam(GlobalConstant.USERREF)).getId());
		query.setParameter("fromdate", cal.getTime());
		
		if (request.containsParam(GlobalConstant.OWNER)) {
			query.setParameter("uid", new Long((String) request.getParam(GlobalConstant.OWNER)));
		} 
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			query.setParameter("active", (Boolean) request.getParam(GlobalConstant.ACTIVE));
		} else {
			query.setParameter("active", true);
		}
		
		List<Long> items = (List<Long>) query.getResultList();
		
		
		response.addParam(GlobalConstant.ITEMS, items);
	}
}
