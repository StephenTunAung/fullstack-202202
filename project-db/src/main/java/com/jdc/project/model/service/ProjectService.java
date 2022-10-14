package com.jdc.project.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jdc.project.model.dto.Project;
import com.jdc.project.model.service.utils.ProjectHelper;

@Service
public class ProjectService {

	@Autowired
	private SimpleJdbcInsert projectInsert;

	@Autowired
	private ProjectHelper projectHelper;

	@Autowired
	private NamedParameterJdbcOperations template;

	private RowMapper<Project> rowMapper;

	@Value("${sql.project.findById}")
	private String findById;

	@Value("${sql.project.search}")
	private String search;

	@Value("${sql.project.update}")
	private String update;

	@Value("${sql.project.delete}")
	private String delete;

	public ProjectService() {
		super();
		this.rowMapper = new BeanPropertyRowMapper<>(Project.class);
	}

	public int create(Project project) {
		// TODO Clear all test for create method
		projectHelper.validate(project);

		return projectInsert.executeAndReturnKey(projectHelper.insertParams(project)).intValue();
	}

	public Project findById(int id) {
		var params = new HashMap<String, Object>();
		params.put("id", id);
		return template.queryForStream(findById, params, rowMapper).findFirst().orElseGet(() -> null);
	}

	public List<Project> search(String project, String manager, LocalDate dateFrom, LocalDate dateTo) {
		var params = new HashMap<String, Object>();
		var sb = new StringBuffer(search);
		
		if (StringUtils.hasLength(project)) {
			sb.append(" and lower(p.name) like :project");
			params.put("project", project.toLowerCase().concat("%"));
		}
		if (StringUtils.hasLength(manager)) {
			sb.append(" and lower(m.name) like :manager");
			params.put("manager", manager.toLowerCase().concat("%"));
		}
		if (null != dateFrom) {
			sb.append(" and p.start >= :dateFrom");
			params.put("dateFrom", Date.valueOf(dateFrom));
		}
		if (null != dateTo) {
			sb.append(" and p.start <= :dateTo");
			params.put("dateTo", Date.valueOf(dateTo));
		}

		return template.queryForStream(sb.toString(), params, rowMapper).toList();
	}

	public int update(int id, String name, String description, LocalDate startDate, int month) {
		var params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("name", name);
		params.put("description", description);
		params.put("startDate",Date.valueOf(startDate));
		params.put("months", month);

		return template.update(update, params);
	}

	public int deleteById(int id) {
		return template.update(delete, Map.of("id", id));
	}

}
