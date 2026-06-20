<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un Membre</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container py-4" style="max-width: 700px">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>➕ Ajouter un membre</h2>
        <a href="${pageContext.request.contextPath}/admin/membres"
           class="btn btn-outline-secondary">← Retour</a>
    </div>

    <!-- Message d'erreur -->
    <c:if test="${erreur != null}">
        <div class="alert alert-danger">❌ ${erreur}</div>
    </c:if>

    <div class="card">
        <div class="card-body p-4">
            <form action="${pageContext.request.contextPath}/admin/membres"
                  method="post" novalidate>
                <input type="hidden" name="action" value="ajouter">

                <div class="row g-3">

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Nom *</label>
                        <input type="text" name="nom" class="form-control"
                               value="${nom}" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Prénom *</label>
                        <input type="text" name="prenom" class="form-control"
                               value="${prenom}" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Email *</label>
                        <input type="email" name="email" class="form-control"
                               value="${email}" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Téléphone</label>
                        <input type="text" name="telephone" class="form-control"
                               value="${telephone}">
                    </div>

                    <div class="col-12">
                        <label class="form-label fw-semibold">Adresse</label>
                        <input type="text" name="adresse" class="form-control"
                               value="${adresse}">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Mot de passe *</label>
                        <input type="password" name="motDePasse"
                               class="form-control" required minlength="6">
                        <small class="text-muted">Minimum 6 caractères</small>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Rôle *</label>
                        <select name="role" class="form-select" required>
                            <option value="MEMBRE" selected>Membre</option>
                            <option value="ADMIN">Administrateur</option>
                        </select>
                    </div>

                </div>

                <div class="d-flex gap-2 mt-4">
                    <button type="submit" class="btn btn-primary">
                        ✅ Enregistrer
                    </button>
                    <a href="${pageContext.request.contextPath}/admin/membres"
                       class="btn btn-outline-secondary">Annuler</a>
                </div>

            </form>
        </div>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>