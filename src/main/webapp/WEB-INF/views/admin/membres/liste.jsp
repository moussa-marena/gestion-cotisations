<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Membres</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container-fluid py-4">

    <!-- En-tête -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="mb-0">👥 Gestion des Membres</h2>
            <small class="text-muted">
                Total : <strong>${total}</strong> |
                Actifs : <strong>${actifs}</strong>
            </small>
        </div>
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/dashboard/admin"
               class="btn btn-outline-secondary">← Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/membres?action=ajouter"
               class="btn btn-primary">+ Ajouter un membre</a>
        </div>
    </div>

    <!-- Messages -->
    <c:if test="${param.succes != null}">
        <div class="alert alert-success alert-dismissible fade show">
            ✅ ${param.succes}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>
    <c:if test="${param.erreur != null}">
        <div class="alert alert-danger alert-dismissible fade show">
            ❌ ${param.erreur}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <!-- Barre de recherche -->
    <div class="card mb-4">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/membres"
                  method="get" class="d-flex gap-2">
                <input type="text" name="search" class="form-control"
                       placeholder="Rechercher par nom, prénom ou email..."
                       value="${keyword}">
                <button type="submit" class="btn btn-outline-primary">
                    🔍 Rechercher
                </button>
                <c:if test="${keyword != null}">
                    <a href="${pageContext.request.contextPath}/admin/membres"
                       class="btn btn-outline-secondary">✕ Effacer</a>
                </c:if>
            </form>
        </div>
    </div>

    <!-- Tableau -->
    <div class="card">
        <div class="card-body p-0">
            <table class="table table-hover mb-0">
                <thead class="table-dark">
                    <tr>
                        <th>#</th>
                        <th>Nom complet</th>
                        <th>Email</th>
                        <th>Téléphone</th>
                        <th>Rôle</th>
                        <th>Statut</th>
                        <th>Date adhésion</th>
                        <th class="text-center">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty membres}">
                            <tr>
                                <td colspan="8" class="text-center py-4 text-muted">
                                    Aucun membre trouvé.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="m" items="${membres}">
                                <tr>
                                    <td>${m.id}</td>
                                    <td>
                                        <strong>${m.prenom} ${m.nom}</strong>
                                    </td>
                                    <td>${m.email}</td>
                                    <td>${m.telephone}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${m.role == 'ADMIN'}">
                                                <span class="badge bg-danger">ADMIN</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-primary">MEMBRE</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${m.statut == 'ACTIF'}">
                                                <span class="badge bg-success">ACTIF</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">INACTIF</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${m.dateAdhesion}</td>
                                    <td class="text-center">
                                        <!-- Modifier -->
                                        <a href="${pageContext.request.contextPath}/admin/membres?action=modifier&id=${m.id}"
                                           class="btn btn-sm btn-warning">✏️</a>

                                        <!-- Toggle statut -->
                                        <a href="${pageContext.request.contextPath}/admin/membres?action=toggle&id=${m.id}"
                                           class="btn btn-sm ${m.statut == 'ACTIF' ? 'btn-secondary' : 'btn-success'}"
                                           onclick="return confirm('Changer le statut de ce membre ?')">
                                            ${m.statut == 'ACTIF' ? '🔒' : '🔓'}
                                        </a>

                                        <!-- Supprimer -->
                                        <a href="${pageContext.request.contextPath}/admin/membres?action=supprimer&id=${m.id}"
                                           class="btn btn-sm btn-danger"
                                           onclick="return confirm('Supprimer ce membre définitivement ?')">
                                            🗑️
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>