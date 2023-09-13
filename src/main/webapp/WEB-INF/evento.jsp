<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Evento</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>${evento.nombre}</h1>
				<p>
					<b>Host:</b> ${evento.host.nombre}
				</p>
				<p>
					<b>Fecha:</b> ${evento.fecha}
				</p>
				<p>
					<b>Ubicación:</b> ${evento.ubicacion}
				</p>
				<p>
					<b>Estado:</b> ${evento.estado}
				</p>
				<p>
					<b>Cantidad de Asistentes:</b> ${evento.asistentes.size()} <!-- .size() me regresa el tamaño de una lista -->
				</p>
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Nombre</th>
							<th>Ubicación</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${evento.asistentes}" var="usuario">
							<tr>
								<td>${usuario.nombre}</td>
								<td>${usuario.ubicacion}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="col-6">
				<h2>Muro de Mensajes</h2>
				<div>
					<!-- Recorremos todos los mensajes que publicaron al evento -->
					<c:forEach items="${evento.mensajesEvento}" var="mensaje">
						<p>
							${mensaje.autor.nombre} dice: ${mensaje.contenido}
						</p>
					</c:forEach>
				</div>
				<form:form action="/crearmensaje" method="post" modelAttribute="mensaje">
					<div class="form-group">
						<form:label path="contenido">Agregar Comentario:</form:label>
						<form:textarea path="contenido" class="form-control" />
						<form:errors path="contenido" class="text-danger" />
						<!-- Enviamos el id del usuario en sesión -->
						<form:hidden path="autor" value="${usuarioEnSesion.id}" />
						<!-- Enviamos el id del evento -->
						<form:hidden path="evento" value="${evento.id}"/>
						<input type="submit" class="btn btn-primary mt-3" value="Enviar" />
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>