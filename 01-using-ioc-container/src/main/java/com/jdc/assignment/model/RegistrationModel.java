package com.jdc.assignment.model;

import java.util.List;

import com.jdc.assignment.domain.Registration;

public interface RegistrationModel {
	
	List<Registration> findAllByOpenClassId(int openClassId);
		
	void create(Registration registration);

}
