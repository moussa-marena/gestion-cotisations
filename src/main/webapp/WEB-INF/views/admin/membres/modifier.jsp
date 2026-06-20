<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier un Membre</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container py-4" style="max-width: 700px">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>✏️ Modifier un membre</h2>
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
                  method="post">
                <input type="hidden" name="action" value="modifier">
                <input type="hidden" name="id"     value="${membre.id}">

                <div class="row g-3">

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Nom *</label>
                        <input type="text" name="nom" class="form-control"
                               value="${membre.nom}" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Prénom *</label>
                        <input type="text" name="prenom" class="form-control"
                               value="${membre.prenom}" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Email *</label>
                        <input type="email" name="email" class="form-control"
                               value="${membre.email}" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Téléphone</label>
                        <input type="text" name="telephone" class="form-control"
                               value="${membre.telephone}">
                    </div>

                    <div class="col-12">
                        <label class="form-label fw-semibold">Adresse</label>
                        <input type="text" name="adresse" class="form-control"
                               value="${membre.adresse}">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Rôle *</label>
                        <select name="role" class="form-select" required>
                            <option value="MEMBRE"
                                ${membre.role == 'MEMBRE' ? 'selected' : ''}>
                                Membre
                            </option>
                            <option value="ADMIN"
                                ${membre.role == 'ADMIN' ? 'selected' : ''}>
                                Administrateur
                            </option>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Statut</label>
                        <input type="text" class="form-control"
                               value="${membre.statut}" disabled>
                        <small class="text-muted">
                            Modifier via le bouton 🔒/🔓 dans la liste
                        </small>
                    </div>

                </div>

                <div class="d-flex gap-2 mt-4">
                    <button type="submit" class="btn btn-warning">
                        💾 Enregistrer les modifications
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