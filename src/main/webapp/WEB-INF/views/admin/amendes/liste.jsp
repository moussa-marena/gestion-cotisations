<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Amendes</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container-fluid py-4">

    <!-- En-tête -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="mb-0">⚠️ Gestion des Amendes</h2>
            <small class="text-muted">
                En attente : <strong class="text-danger">${nbEnAttente}</strong> |
                Total dû : <strong class="text-danger">${totalEnAttente} FCFA</strong>
            </small>
        </div>
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/dashboard/admin"
               class="btn btn-outline-secondary">← Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/amendes?action=generer"
               class="btn btn-warning">⚡ Générer les amendes</a>
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

    <!-- Tableau -->
    <div class="card">
        <div class="card-body p-0">
            <table class="table table-hover mb-0">
                <thead class="table-dark">
                    <tr>
                        <th>#</th>
                        <th>Membre</th>
                        <th>Motif</th>
                        <th>Montant</th>
                        <th>Date génération</th>
                        <th>Date paiement</th>
                        <th>Statut</th>
                        <th class="text-center">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty amendes}">
                            <tr>
                                <td colspan="8"
                                    class="text-center py-4 text-muted">
                                    Aucune amende enregistrée.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="a" items="${amendes}">
                                <tr>
                                    <td>${a.id}</td>
                                    <td>
                                        <strong>
                                            ${a.membre.prenom} ${a.membre.nom}
                                        </strong>
                                    </td>
                                    <td>${a.motif}</td>
                                    <td>
                                        <strong class="text-danger">
                                            ${a.montant} FCFA
                                        </strong>
                                    </td>
                                    <td>${a.dateGeneration}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${a.datePaiement != null}">
                                                ${a.datePaiement}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">—</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${a.statut == 'EN_ATTENTE'}">
                                                <span class="badge bg-warning text-dark">
                                                    EN ATTENTE
                                                </span>
                                            </c:when>
                                            <c:when test="${a.statut == 'PAYEE'}">
                                                <span class="badge bg-success">
                                                    PAYÉE
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">
                                                    ANNULÉE
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <!-- Payer -->
                                        <c:if test="${a.statut == 'EN_ATTENTE'}">
                                            <a href="${pageContext.request.contextPath}/admin/amendes?action=payer&id=${a.id}"
                                               class="btn btn-sm btn-success"
                                               onclick="return confirm('Marquer cette amende comme payée ?')">
                                                💵
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/amendes?action=annuler&id=${a.id}"
                                               class="btn btn-sm btn-secondary"
                                               onclick="return confirm('Annuler cette amende ?')">
                                                ✕
                                            </a>
                                        </c:if>
                                        <!-- Supprimer -->
                                        <a href="${pageContext.request.contextPath}/admin/amendes?action=supprimer&id=${a.id}"
                                           class="btn btn-sm btn-danger"
                                           onclick="return confirm('Supprimer définitivement cette amende ?')">
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