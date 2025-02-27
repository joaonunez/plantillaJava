<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Dashboard</title>
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        </head>

        <body class="bg-light">

            <!-- NAVBAR -->
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <div class="container">
                    <a class="navbar-brand fw-bold" href="/dashboard">Postres</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item"><a class="nav-link active text-primary fw-bold"
                                    href="/dashboard">Inicio</a></li>
                            <li class="nav-item"><a class="nav-link" href="/my-desserts">Mis Postres</a></li>
                            <li class="nav-item"><a class="nav-link" href="/add-dessert">Agregar Postre</a></li>
                            <li class="nav-item"><a class="nav-link" href="/favorites">⭐ Favoritos</a></li>
                            <li class="nav-item"><a class="nav-link text-danger fw-bold" href="/logout">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </nav>

            <!-- CONTENIDO DEL DASHBOARD -->
            <div class="container mt-5">
                <h1 class="text-center">Bienvenido de vuelta ${sessionScope.userName}!</h1>

                <!-- Formulario de Búsqueda -->
                <div class="d-flex justify-content-center mt-4">
                    <form action="/search-desserts" method="GET" class="d-flex w-50">
                        <input type="text" name="query" class="form-control me-2" placeholder="Buscar postres..."
                            required>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-search"></i> Buscar
                        </button>
                    </form>
                </div>

                <!-- Lista de Postres -->
                <div class="mt-4">
                    <c:forEach var="dessert" items="${desserts}">
                        <div class="card mb-3 shadow-sm">
                            <div class="row g-0">
                                <div class="col-md-2">
                                    <img src="${dessert.imageUrl}" class="img-fluid rounded-start"
                                        alt="${dessert.name}">
                                </div>
                                <div class="col-md-10">
                                    <div class="card-body">
                                        <h5 class="card-title d-flex justify-content-between align-items-center">
                                            ${dessert.name}
                                            <div>
                                                <c:choose>
                                                    <c:when test="${dessert.favorite}">
                                                        <!-- Formulario para eliminar de favoritos -->
                                                        <form action="/favorites/remove/${dessert.id}" method="POST"
                                                            style="display:inline;">
                                                            <input type="hidden" name="_method" value="DELETE">
                                                            <button type="submit" class="btn btn-link p-0 border-0">
                                                                <i class="fas fa-heart text-danger"
                                                                    style="font-size: 1.5rem;"></i>
                                                            </button>
                                                        </form>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <!-- Formulario para agregar a favoritos -->
                                                        <form action="/favorites/add/${dessert.id}" method="POST"
                                                            style="display:inline;">
                                                            <button type="submit" class="btn btn-link p-0 border-0">
                                                                <i class="far fa-heart text-secondary"
                                                                    style="font-size: 1.5rem;"></i>
                                                            </button>
                                                        </form>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </h5>
                                        <p class="card-text"><strong>Autor:</strong> ${dessert.creator.firstName}
                                            ${dessert.creator.lastName}</p>
                                        <p class="card-text"><strong>Tiempo de preparación:</strong>
                                            ${dessert.preparationTime} minutos</p>

                                        <!-- BOTON "VER DETALLES" -->
                                        <a href="/dessert-detail/${dessert.id}" class="btn btn-info mt-2">
                                            <i class="fas fa-eye"></i> Ver Detalles
                                        </a>

                                        <c:if test="${dessert.creator.id == loggedUser.id}">
                                            <a href="/edit-dessert/${dessert.id}" class="btn btn-warning mt-2">
                                                <i class="fas fa-edit"></i> Editar
                                            </a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

        </body>

        </html>