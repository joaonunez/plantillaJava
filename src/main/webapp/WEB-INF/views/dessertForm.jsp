<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>Agregar Postre</title>
                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">

                <script>
                    function addIngredient() {
                        var container = document.getElementById("ingredients-container");

                        var newField = document.createElement("div");
                        newField.classList.add("row", "mb-2");

                        newField.innerHTML = `
                <div class="col-md-5">
                    <input type="text" name="ingredientKeys" class="form-control" placeholder="Ej. Azúcar" required>
                </div>
                <div class="col-md-5">
                    <input type="text" name="ingredientValues" class="form-control" placeholder="Ej. 100g" required>
                </div>
                <div class="col-md-2 text-center">
                    <button type="button" class="btn btn-danger" onclick="removeIngredient(this)">X</button>
                </div>
            `;

                        container.appendChild(newField);
                    }

                    function removeIngredient(element) {
                        element.parentElement.parentElement.remove();
                    }
                </script>
            </head>

            <body class="bg-light">

                <!-- NAVBAR -->
                <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

                    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                        <div class="container">
                            <a class="navbar-brand fw-bold" href="/dashboard">Postres</a>
                            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                                data-bs-target="#navbarNav">
                                <span class="navbar-toggler-icon"></span>
                            </button>
                            <div class="collapse navbar-collapse" id="navbarNav">
                                <ul class="navbar-nav ms-auto">
                                    <li class="nav-item"><a class="nav-link" href="/dashboard">Inicio</a></li>
                                    <li class="nav-item"><a class="nav-link" href="/my-desserts">Mis Postres</a></li>
                                    <li class="nav-item"><a class="nav-link active text-primary fw-bold"
                                            href="/add-dessert">Agregar Postre</a></li>
                                    <li class="nav-item"><a class="nav-link" href="/favorites">⭐ Favoritos</a></li>
                                    <li class="nav-item"><a class="nav-link text-danger fw-bold"
                                            href="/logout">Logout</a></li>
                                </ul>
                            </div>
                        </div>
                    </nav>


                    <!-- FORMULARIO -->
                    <div class="container mt-5">
                        <h1 class="text-center text-primary">Agregar Nuevo Postre</h1>

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>

                        <form:form action="/add-dessert" method="POST" class="border p-4 rounded bg-white shadow">
                            <div class="mb-3">
                                <label class="form-label">Nombre del Postre</label>
                                <input type="text" name="name" class="form-control" required />
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Descripción</label>
                                <textarea name="description" class="form-control" required></textarea>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">URL de la Imagen</label>
                                <input type="text" name="imageUrl" class="form-control" required />
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Tiempo de Preparación (minutos)</label>
                                <input type="number" name="preparationTime" class="form-control" min="1" required />
                            </div>

                            <h5 class="mt-4">Ingredientes</h5>
                            <div id="ingredients-container"></div>

                            <button type="button" class="btn btn-secondary mt-2" onclick="addIngredient()">+ Agregar
                                Ingrediente</button>

                            <div class="d-flex justify-content-center mt-3">
                                <button type="submit" class="btn btn-primary w-100">Guardar Postre</button>
                            </div>
                        </form:form>
                    </div>

            </body>

            </html>