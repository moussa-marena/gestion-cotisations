<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Cotisations</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container-fluid py-4">

    <!-- En-tête -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="mb-0">💰 Gestion des Cotisations</h2>
            <small class="text-muted">
                ${nomMois} ${annee} —
                Total encaissé : <strong>${totalEncaisse} FCFA</strong> |
                En retard : <strong class="text-danger">${nbEnRetard}</strong>
            </small>
        </div>
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/dashboard/admin"
               class="btn btn-outline-secondary">← Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/cotisations?action=retard&mois=${mois}&annee=${annee}"
               class="btn btn-warning">⚠️ Membres en retard</a>
            <a href="${pageContext.request.contextPath}/admin/cotisations?action=ajouter"
               class="btn btn-primary">+ Enregistrer</a>
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

    <!-- Filtre mois/année -->
    <div class="card mb-4">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/cotisations"
                  method="get" class="d-flex gap-2 align-items-end">
                <div>
                    <label class="form-label fw-semibold mb-1">Mois</label>
                    <select name="mois" class="form-select">
                        <c:forEach begin="1" end="12" var="m">
                            <option value="${m}" ${m == mois ? 'selected' : ''}>
                                ${m}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <label class="form-label fw-semibold mb-1">Année</label>
                    <input type="number" name="annee" class="form-control"
                           value="${annee}" min="2020" max="2030">
                </div>
                <button type="submit" class="btn btn-outline-primary">
                    🔍 Filtrer
                </button>
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
                        <th>Membre</th>
                        <th>Période</th>
                        <th>Montant</th>
                        <th>Mode paiement</th>
                        <th>Date paiement</th>
                        <th>Référence</th>
                        <th>Statut</th>
                        <th class="text-center">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty cotisations}">
                            <tr>
                                <td colspan="9"
                                    class="text-center py-4 text-muted">
                                    Aucune cotisation pour ${nomMois} ${annee}.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="c" items="${cotisations}">
                                <tr>
                                    <td>${c.id}</td>
                                    <td>
                                        <strong>
                                            ${c.membre.prenom} ${c.membre.nom}
                                        </strong>
                                    </td>
                                    <td>${c.periode}</td>
                                    <td>
                                        <strong>${c.montant} FCFA</strong>
                                    </td>
                                    <td>${c.modePaiement}</td>
                                    <td>${c.datePaiement}</td>
                                    <td>${c.reference}</td>
                                    <td>
                                        <span class="badge bg-success">
                                            ${c.statut}
                                        </span>
                                    </td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/admin/cotisations?action=supprimer&id=${c.id}"
                                           class="btn btn-sm btn-danger"
                                           onclick="return confirm('Supprimer cette cotisation ?')">
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