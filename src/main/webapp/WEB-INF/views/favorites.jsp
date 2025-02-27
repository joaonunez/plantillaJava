<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Mis Favoritos</title>
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
                            <li class="nav-item"><a class="nav-link" href="/dashboard">Inicio</a></li>
                            <li class="nav-item"><a class="nav-link" href="/my-desserts">Mis Postres</a></li>
                            <li class="nav-item"><a class="nav-link" href="/add-dessert">Agregar Postre</a></li>
                            <li class="nav-item"><a class="nav-link active text-primary fw-bold" href="/favorites">⭐
                                    Favoritos</a></li>
                            <li class="nav-item"><a class="nav-link text-danger fw-bold" href="/logout">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </nav>

            <!-- CONTENIDO DE FAVORITOS -->
            <div class="container mt-5">
                <h1 class="text-center text-warning">Mis Favoritos</h1>

                <!-- Lista de Postres Favoritos -->
                <div class="mt-4">
                    <c:forEach var="favorite" items="${favorites}">
                        <div class="card mb-3 shadow-sm">
                            <div class="row g-0">
                                <div class="col-md-2">
                                    <img src="${favorite.dessert.imageUrl}" class="img-fluid rounded-start"
                                        alt="${favorite.dessert.name}">
                                </div>
                                <div class="col-md-10">
                                    <div class="card-body">
                                        <h5 class="card-title d-flex justify-content-between align-items-center">
                                            ${favorite.dessert.name}
                                            <!-- BOTÓN DE FAVORITOS -->
                                            <form action="/favorites/remove/${favorite.dessert.id}" method="POST"
                                                class="d-inline">
                                                <input type="hidden" name="_method" value="DELETE">
                                                <button type="submit" class="btn btn-danger">
                                                    <i class="fas fa-heart"></i> Quitar de Favoritos
                                                </button>
                                            </form>
                                        </h5>
                                        <p class="card-text"><strong>Tiempo de preparación:</strong>
                                            ${favorite.dessert.preparationTime} minutos</p>
                                        <p class="card-text"><strong>Creado por:</strong>
                                            ${favorite.dessert.creator.firstName} ${favorite.dessert.creator.lastName}
                                        </p>
                                        <a href="/dessert-detail/${favorite.dessert.id}" class="btn btn-info">
                                            <i class="fas fa-eye"></i> Ver Detalles
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

        </body>

        </html>