<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Login</title>
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
        </head>

        <body class="bg-light">

            <!-- NAVBAR -->
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <div class="container">
                    <a class="navbar-brand" href="#">Mi App</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item">
                                <a class="nav-link active text-primary fw-bold" href="#">Login</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/register">Registro</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <div class="container mt-5">
                <div class="row justify-content-center">
                    <div class="col-xxl-4 col-md-10 col-lg-7 col-xl-5 col-sm-10">
                        <h1 class="text-primary mb-4 text-center">Login</h1>

                        <c:if test="${not empty error}">
                            <p class="text-danger text-center">${error}</p>
                        </c:if>

                        <form action="/login" method="POST" class="border p-4 rounded bg-white shadow">

                            <div class="mb-3">
                                <label for="email" class="form-label">Correo</label>
                                <input type="email" id="email" name="correo" class="form-control"
                                    placeholder="Ingresa tu correo" required />
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label">Contraseña</label>
                                <input type="password" id="password" name="contrasena" class="form-control"
                                    placeholder="Ingresa tu contraseña" required />
                            </div>

                            <div class="d-flex justify-content-center">
                                <button type="submit" class="btn btn-primary w-100">Iniciar sesión</button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>