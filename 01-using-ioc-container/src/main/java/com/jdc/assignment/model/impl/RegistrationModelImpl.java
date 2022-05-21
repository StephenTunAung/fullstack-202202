package com.jdc.assignment.model.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdc.assignment.domain.Registration;
import com.jdc.assignment.model.RegistrationModel;

public class RegistrationModelImpl implements RegistrationModel{
		
	private static final String SELECT_ALL_BY_OPEN_CLASS = """
			select id , student, phone, email
			from registration
			where open_class_id = ? 
			""";
	private static final String INSERT = """
			INSERT INTO registration 
			(open_class_id, student, phone, email)
			 VALUES 
			 (?, ?, ?, ?)
			""";
	
	private DataSource dataSource;

	public RegistrationModelImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	@Override
	public List<Registration> findAllByOpenClassId(int openClassId) {
		var list = new ArrayList<Registration>();
		
		try(var conn = dataSource.getConnection();
			var stmt = conn.prepareStatement(SELECT_ALL_BY_OPEN_CLASS)) {
			stmt.setInt(1, openClassId);
			
			var result = stmt.executeQuery();
			
			while(result.next()) {
				
				var registration = new Registration();
				
				registration.setId(result.getInt("id"));
				registration.setStudent(result.getString("student"));
				registration.setPhone(result.getString("phone"));
				registration.setEmail(result.getString("email"));
				
				list.add(registration);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void create(Registration registration) {
		try (var conn = dataSource.getConnection(); 
			var stmt = conn.prepareStatement(INSERT)) {
			stmt.setInt(1, registration.getOpenClass().getId());
			stmt.setString(2, registration.getStudent());
			stmt.setString(3, registration.getPhone());
			stmt.setString(4, registration.getEmail());
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
