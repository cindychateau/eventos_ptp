<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<nav class="d-flex justify-content-between align-items-center">
			<h1>¡Bienvenid@ ${usuarioEnSesion.nombre}!</h1>
			<a href="/logout" class="btn btn-danger">Cerrar Sesión</a>
		</nav>
		<div class="row">
			<h2>Eventos en mi estado</h2>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Evento</th>
						<th>Fecha</th>
						<th>Ubicación</th>
						<th>Estado</th>
						<th>Host</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${eventosMiEstado}" var="evento">
						<tr>
							<td>${evento.nombre}</td>
							<td>${evento.fecha}</td>
							<td>${evento.ubicacion}</td>
							<td>${evento.estado}</td>
							<td>${evento.host.nombre}</td>
							<td></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row">
			<h2>Eventos en otros estados</h2>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Evento</th>
						<th>Fecha</th>
						<th>Ubicación</th>
						<th>Estado</th>
						<th>Host</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${eventosOtroEstado}" var="evento">
						<tr>
							<td>${evento.nombre}</td>
							<td>${evento.fecha}</td>
							<td>${evento.ubicacion}</td>
							<td>${evento.estado}</td>
							<td>${evento.host.nombre}</td>
							<td></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row">
			<h2>Crear Evento</h2>
			<form:form action="/crear" method="post" modelAttribute="nuevoEvento">
				<div>
					<form:label path="nombre">Nombre:</form:label>
					<form:input path="nombre" class="form-control" />
					<form:errors path="nombre" class="text-danger" />
				</div>
				<div>
					<form:label path="fecha">Fecha:</form:label>
					<form:input type="date" path="fecha" class="form-control" />
					<form:errors path="fecha" class="text-danger" />
				</div>
				<div>
					<form:label path="ubicacion">Ubicación:</form:label>
					<form:input path="ubicacion" class="form-control" />
					<form:errors path="ubicacion" class="text-danger" />
				</div>
				<div>
					<form:label path="estado">Estado</form:label>
					<form:select path="estado" class="form-select">
						<c:forEach items="${estados}" var="estado">
							<form:option value="${estado}">${estado}</form:option>
						</c:forEach>
					</form:select>
				</div>
				<form:hidden value="${usuarioEnSesion.id}" path="host" />
				<input type="submit" value="Crear Evento" class="btn btn-success" />
			</form:form>
		</div>
	</div>
</body>
</html>