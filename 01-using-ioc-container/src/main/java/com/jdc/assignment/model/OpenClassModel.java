package com.jdc.assignment.model;

import java.util.List;

import com.jdc.assignment.domain.OpenClass;

public interface OpenClassModel {

	List<OpenClass> findByCourse(int courseId);
	
	OpenClass findOpenClassById(int openClassId);
	
	void create(OpenClass openClass);
}
