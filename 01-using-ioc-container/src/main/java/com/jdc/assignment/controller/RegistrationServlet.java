package com.jdc.assignment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdc.assignment.domain.Registration;
import com.jdc.assignment.model.OpenClassModel;
import com.jdc.assignment.model.RegistrationModel;

@WebServlet(urlPatterns = {
		"/registrations",
		"/registration-edit"
})
public class RegistrationServlet extends AbstractBeanFactoryServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String CLASS = "registrations";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		var openClassId = req.getParameter("openClassId");
		
		// Find Open Class 
		var openClassModel = getBean("openClassModel", OpenClassModel.class);
		var openClass = openClassModel.findOpenClassById(Integer.parseInt(openClassId));
		
		req.setAttribute("openClass", openClass);
		
		var page = switch(req.getServletPath()) {
		case "/registrations" -> {
			var model = getBean("registrationModel", RegistrationModel.class);
			req.setAttribute("registration", model.findAllByOpenClassId(Integer.parseInt(openClassId)));
			yield "registrations";
		}
		default -> "registration-edit";
		};
		
		getServletContext().getRequestDispatcher("/%s.jsp".formatted(page)).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var openClassId = req.getParameter("openClassId");
		var studentName = req.getParameter("student");
		var phone = req.getParameter("phone");
		var email = req.getParameter("email");
		
		var openClassModel = getBean("openClassModel", OpenClassModel.class);
		var openClass = openClassModel.findOpenClassById(Integer.parseInt(openClassId));
		
		var registration = new Registration();
		registration.setOpenClass(openClass);
		registration.setStudent(studentName);
		registration.setPhone(phone);
		registration.setEmail(email);
		
		var registrationModel = getBean("registrationModel", RegistrationModel.class);
		registrationModel.create(registration);
		
		resp.sendRedirect(req.getContextPath().concat(CLASS + "/openClassId=" + openClassId));
	}
	
	

}
