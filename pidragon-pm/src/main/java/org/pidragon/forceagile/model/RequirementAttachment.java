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

package org.pidragon.forceagile.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.toasthub.social.model.AttachmentMeta;

@Entity
@Table(name = "fa_requirement_attachment")
public class RequirementAttachment implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Requirement requirement;
	private AttachmentMeta attachment;
	
	// constructors
	public RequirementAttachment() {
		super();
	}
	
	// methods
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = Requirement.class)
	@JoinColumn(name = "requirement_id")
	public Requirement getRequirement() {
		return requirement;
	}
	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}

	@ManyToOne(targetEntity = AttachmentMeta.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "attachment_id")
	public AttachmentMeta getAttachment() {
		return attachment;
	}
	public void setAttachment(AttachmentMeta attachment) {
		this.attachment = attachment;
	}


}
