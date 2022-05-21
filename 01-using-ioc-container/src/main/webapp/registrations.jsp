<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Using IoC Container</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</head>
<body>
<div class="container mt-4">
		
		<h1>Using IoC Container</h1>
		
		<h3>Registration for ${ openClass.course.name }</h3>		

		<div>
			<c:url var="addNew" value="/registration-edit">
				<c:param name="openClassId" value="${ openClass.id }"></c:param>
			</c:url>
			
			<a class="btn btn-primary" href="${addNew}">Add New Student Registration</a>
		</div>
		

		<c:choose>
			<c:when test="${ empty registration }">
				<div class="alert alert-warning">
					There is no student for ${ openClass.course.name }. Please register.
				</div>
			</c:when>
			
			<c:otherwise>
				
				<table class="table table-striped">
				
					<thead>
						<tr>
							<th>ID</th>
							<th>Student</th>
							<th>Phone</th>
							<th>Email</th>
						</tr>
					</thead>
					
					<tbody>
						
						<c:forEach var="r" items="${ registration }">
							
							<tr>
								<td>${ r.id }</td>
								<td>${ r.student }</td>
								<td>${ r.phone }</td>
								<td>${ r.email }</td>
							</tr>
						
						</c:forEach>
					</tbody>
				
				</table>
			
			</c:otherwise>
		
		</c:choose>	
		<div>
			<a href="/" >Home</a>
		</div>
	</div>
</body>
</html>