<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Membres en retard</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container-fluid py-4">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>⚠️ Membres en retard — ${nomMois} ${annee}</h2>
        <a href="${pageContext.request.contextPath}/admin/cotisations"
           class="btn btn-outline-secondary">← Retour</a>
    </div>

    <div class="row g-4">

        <!-- Membres EN RETARD -->
        <div class="col-md-6">
            <div class="card border-danger">
                <div class="card-header bg-danger text-white">
                    ❌ En retard
                    <span class="badge bg-white text-danger ms-2">
                        ${membresEnRetard.size()}
                    </span>
                </div>
                <div class="card-body p-0">
                    <table class="table mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>Membre</th>
                                <th>Email</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty membresEnRetard}">
                                    <tr>
                                        <td colspan="3"
                                            class="text-center py-3 text-success">
                                            ✅ Tous les membres sont à jour !
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="m"
                                               items="${membresEnRetard}">
                                        <tr>
                                            <td>
                                                <strong>
                                                    ${m.prenom} ${m.nom}
                                                </strong>
                                            </td>
                                            <td>
                                                <small>${m.email}</small>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/admin/cotisations?action=ajouter"
                                                   class="btn btn-sm btn-outline-primary">
                                                    💰 Payer
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

        <!-- Membres À JOUR -->
        <div class="col-md-6">
            <div class="card border-success">
                <div class="card-header bg-success text-white">
                    ✅ À jour
                    <span class="badge bg-white text-success ms-2">
                        ${membresAJour.size()}
                    </span>
                </div>
                <div class="card-body p-0">
                    <table class="table mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>Membre</th>
                                <th>Email</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty membresAJour}">
                                    <tr>
                                        <td colspan="2"
                                            class="text-center py-3 text-muted">
                                            Aucun paiement ce mois.
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="m" items="${membresAJour}">
                                        <tr>
                                            <td>
                                                <strong>
                                                    ${m.prenom} ${m.nom}
                                                </strong>
                                            </td>
                                            <td>
                                                <small>${m.email}</small>
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

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>