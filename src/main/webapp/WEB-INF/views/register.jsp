<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>Registro</title>
                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
            </head>

            <body class="bg-light">

                <!-- NAVBAR -->
                <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                    <div class="container">
                        <a class="navbar-brand" href="#">Mi App</a>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarNav">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarNav">
                            <ul class="navbar-nav ms-auto">
                                <li class="nav-item">
                                    <a class="nav-link" href="/login">Login</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link active text-primary fw-bold" href="#">Registro</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>

                <div class="container mt-5">
                    <div class="row justify-content-center">
                        <div class="col-xxl-4 col-md-10 col-lg-7 col-xl-5 col-sm-10">
                            <h1 class="text-primary mb-4 text-center">Registro</h1>

                            <form:form action="/register" method="POST" modelAttribute="user"
                                class="border p-4 rounded bg-white shadow">

                                <!-- Mostrar errores generales -->
                                <c:if test="${not empty error}">
                                    <p class="text-danger text-center">${error}</p>
                                </c:if>

                                <div class="mb-3">
                                    <label for="firstName" class="form-label">Nombre</label>
                                    <form:input path="firstName" class="form-control" placeholder="Ingresa tu nombre"
                                        required="true" />
                                    <form:errors path="firstName" class="text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="lastName" class="form-label">Apellido</label>
                                    <form:input path="lastName" class="form-control" placeholder="Ingresa tu apellido"
                                        required="true" />
                                    <form:errors path="lastName" class="text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="email" class="form-label">Correo</label>
                                    <form:input path="email" type="email" class="form-control"
                                        placeholder="Ingresa un correo válido" required="true" />
                                    <form:errors path="email" class="text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="password" class="form-label">Contraseña</label>
                                    <form:password path="password" class="form-control"
                                        placeholder="Mínimo 8 caracteres" required="true" />
                                    <form:errors path="password" class="text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="confirmPassword" class="form-label">Confirmar Contraseña</label>
                                    <form:password path="confirmPassword" class="form-control"
                                        placeholder="Vuelve a introducir tu contraseña" required="true" />
                                    <form:errors path="confirmPassword" class="text-danger" />
                                </div>

                                <div class="d-flex justify-content-center">
                                    <button type="submit" class="btn btn-primary w-100">Registrarse</button>
                                </div>

                            </form:form>
                        </div>
                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>