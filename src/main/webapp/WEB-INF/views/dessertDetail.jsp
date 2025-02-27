<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Detalle del Postre</title>
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
                            <li class="nav-item"><a class="nav-link" href="/favorites">⭐ Favoritos</a></li>
                            <li class="nav-item"><a class="nav-link text-danger fw-bold" href="/logout">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </nav>

            <!-- Botón para volver al Dashboard (fuera del card) -->
            <div class="container mt-4 text-center">
                <a href="/dashboard" class="btn btn-secondary btn-lg">
                    <i class="fas fa-arrow-left"></i> Volver al Dashboard
                </a>
            </div>

            <!-- CONTENEDOR PRINCIPAL -->
            <div class="container mt-3">
                <div class="row justify-content-center">
                    <div class="col-md-8 col-lg-6">
                        <div class="card shadow-lg rounded">
                            <div class="card-body text-center">
                                <!-- Imagen con tamaño optimizado -->
                                <img src="${dessert.imageUrl}"
                                    class="card-img-top w-50 w-md-25 rounded-circle mx-auto d-block mb-3"
                                    alt="${dessert.name}">

                                <h1 class="text-primary">${dessert.name}</h1>

                                <p class="text-muted fst-italic">Creado por: ${dessert.creator.firstName}
                                    ${dessert.creator.lastName}</p>
                                <h5 class="fw-bold mt-3">Descripción</h5>
                                <p class="text-justify">${dessert.description}</p>

                                <h5 class="fw-bold">Tiempo de Preparación</h5>
                                <p class="text-success">${dessert.preparationTime} minutos</p>

                                <h5 class="fw-bold mt-3">Ingredientes</h5>
                                <ul class="list-group list-group-flush text-start w-75 mx-auto">
                                    <c:forEach var="entry" items="${dessert.ingredients}">
                                        <li class="list-group-item d-flex justify-content-between">
                                            <span>${entry.key}</span>
                                            <strong>${entry.value}</strong>
                                        </li>
                                    </c:forEach>
                                </ul>

                                <!-- Contador de Likes -->
                                <h5 class="mt-4">Likes: <span id="like-count"
                                        class="badge bg-primary">${likeCount}</span></h5>

                                <!-- Lista desplegable de usuarios que dieron like -->
                                <details class="mb-3">
                                    <summary class="fw-bold text-decoration-underline text-dark">Ver quiénes dieron like
                                    </summary>
                                    <ul class="list-group mt-2 w-75 mx-auto">
                                        <c:forEach var="user" items="${likedUsers}">
                                            <li class="list-group-item">${user.firstName} ${user.lastName}</li>
                                        </c:forEach>
                                    </ul>
                                </details>

                                <!-- Botón de Like / Unlike -->
                                <div class="d-flex justify-content-center gap-2">
                                    <c:if test="${hasLiked}">
                                        <form action="/dessert/${dessert.id}/unlike" method="POST">
                                            <input type="hidden" name="_method" value="DELETE">
                                            <button type="submit" class="btn btn-outline-danger">
                                                <i class="fas fa-thumbs-down"></i> Quitar Like
                                            </button>
                                        </form>
                                    </c:if>
                                    <c:if test="${!hasLiked}">
                                        <form action="/dessert/${dessert.id}/like" method="POST">
                                            <button type="submit" class="btn btn-outline-primary">
                                                <i class="fas fa-thumbs-up"></i> Dar Like
                                            </button>
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- SECCIÓN DE COMENTARIOS -->
            <div class="container mt-5">
                <div class="row justify-content-center">
                    <div class="col-md-8 col-lg-6">
                        <div class="card shadow-sm p-3">
                            <h4 class="text-center fw-bold">Comentarios</h4>

                            <!-- Formulario para agregar comentario -->
                            <div class="mt-3">
                                <form action="/dessert/${dessert.id}/comment" method="POST">
                                    <div class="mb-3">
                                        <textarea name="content" class="form-control"
                                            placeholder="Escribe tu comentario..." required></textarea>
                                    </div>
                                    <div class="text-end">
                                        <button type="submit" class="btn btn-primary">
                                            <i class="fas fa-comment-dots"></i> Comentar
                                        </button>
                                    </div>
                                </form>
                            </div>
                            <!-- Lista de comentarios -->
                            <div class="mt-3">
                                <c:forEach var="comment" items="${comments}">
                                    <div class="card mb-2">
                                        <div class="card-body">
                                            <p class="text-muted"><strong>Autor:</strong> ${comment.author.firstName}
                                                ${comment.author.lastName}</p>
                                            <p>${comment.content}</p>

                                            <!-- Botón para eliminar comentario (si el usuario en sesión es el autor) -->
                                            <c:if test="${loggedUser.id == comment.author.id}">
                                                <form action="/dessert/comment/${comment.id}/delete" method="POST"
                                                    class="text-end">
                                                    <input type="hidden" name="_method" value="DELETE">
                                                    <button type="submit" class="btn btn-sm btn-danger">
                                                        <i class="fas fa-trash"></i> Eliminar
                                                    </button>
                                                </form>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Bootstrap Icons -->
            <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js"></script>

        </body>

        </html>