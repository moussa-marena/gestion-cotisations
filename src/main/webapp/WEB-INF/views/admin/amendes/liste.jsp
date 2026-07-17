<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Amendes — SunuAssos</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/sunuassos-theme.css">
    <style>
        body { background-color: #EDF2F4; color: #2B2D42; }

        .page-header {
            background: #0D3B66;
            color: white;
            padding: 20px 24px;
            border-radius: 12px;
            margin-bottom: 24px;
        }
        .page-header h2 { margin: 0; font-size: 22px; font-weight: 600; }
        .page-header small { opacity: 0.8; }

        .card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(13,59,102,0.08);
        }

        .table thead th {
            background-color: #0D3B66;
            color: white;
            border: none;
            font-weight: 500;
        }
        .table tbody tr:hover { background-color: #EDF2F4; }

        .badge-en-attente {
            background-color: #F4A261;
            color: #2B2D42;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 11px;
            font-weight: 600;
        }
        .badge-payee {
            background-color: #1B8A5A;
            color: white;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 11px;
            font-weight: 600;
        }
        .badge-annulee {
            background-color: #8D99AE;
            color: white;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 11px;
            font-weight: 600;
        }

        .btn-retour {
            background: transparent;
            border: 1.5px solid white;
            color: white;
            border-radius: 8px;
            padding: 6px 16px;
            font-size: 14px;
        }
        .btn-retour:hover { background: white; color: #0D3B66; }

        .btn-generer {
            background-color: #F4A261;
            border: none;
            color: #2B2D42;
            border-radius: 8px;
            padding: 6px 16px;
            font-size: 14px;
            font-weight: 600;
        }
        .btn-generer:hover { background-color: #e08c42; color: #2B2D42; }

        .montant-due { color: #F4A261; font-weight: 700; }
    </style>
</head>
<body>

<!-- NAVBAR -->
<nav class="navbar navbar-dark px-4 mb-4"
     style="background-color: #0D3B66;">
    <a class="navbar-brand d-flex align-items-center gap-2"
       href="${pageContext.request.contextPath}/dashboard/admin">
        <img src="${pageContext.request.contextPath}/images/logo.png"
             alt="SunuAssos" style="height:36px;">
        SunuAssos
    </a>
    <div class="ms-auto">
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-sm btn-outline-light">🚪 Déconnexion</a>
    </div>
</nav>

<div class="container-fluid px-4">

    <!-- En-tête -->
    <div class="page-header d-flex justify-content-between align-items-center">
        <div>
            <h2>⚠️ Gestion des Amendes</h2>
            <small>
                En attente : <strong>${nbEnAttente}</strong> |
                Total dû : <strong>${totalEnAttente} FCFA</strong>
            </small>
        </div>
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/dashboard/admin"
               class="btn-retour">← Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/amendes?action=generer"
               class="btn-generer">⚡ Générer les amendes</a>
        </div>
    </div>

    <!-- Messages -->
    <c:if test="${param.succes != null}">
        <div class="alert alert-dismissible fade show mb-3"
             style="background-color:#d4edda; border-left: 4px solid #1B8A5A; border-radius:8px;">
            ✅ ${param.succes}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>
    <c:if test="${param.erreur != null}">
        <div class="alert alert-dismissible fade show mb-3"
             style="background-color:#fde8e8; border-left: 4px solid #c0392b; border-radius:8px;">
            ❌ ${param.erreur}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <!-- Tableau -->
    <div class="card">
        <div class="card-body p-0">
            <table class="table table-hover mb-0">
                <thead>
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
                                    class="text-center py-4"
                                    style="color:#8D99AE;">
                                    Aucune amende enregistrée.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="a" items="${amendes}">
                                <tr>
                                    <td style="color:#8D99AE;">${a.id}</td>
                                    <td>
                                        <strong style="color:#0D3B66;">
                                            ${a.membre.prenom} ${a.membre.nom}
                                        </strong>
                                    </td>
                                    <td>${a.motif}</td>
                                    <td>
                                        <span class="montant-due">
                                            ${a.montant} FCFA
                                        </span>
                                    </td>
                                    <td>${a.dateGeneration}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${a.datePaiement != null}">
                                                ${a.datePaiement}
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color:#8D99AE;">—</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${a.statut == 'EN_ATTENTE'}">
                                                <span class="badge-en-attente">
                                                    EN ATTENTE
                                                </span>
                                            </c:when>
                                            <c:when test="${a.statut == 'PAYEE'}">
                                                <span class="badge-payee">
                                                    PAYÉE
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge-annulee">
                                                    ANNULÉE
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <c:if test="${a.statut == 'EN_ATTENTE'}">
                                            <a href="${pageContext.request.contextPath}/admin/amendes?action=payer&id=${a.id}"
                                               class="btn btn-sm"
                                               style="background-color:#1B8A5A; color:white;"
                                               onclick="return confirm('Marquer cette amende comme payée ?')">
                                                💵
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/amendes?action=annuler&id=${a.id}"
                                               class="btn btn-sm"
                                               style="background-color:#8D99AE; color:white;"
                                               onclick="return confirm('Annuler cette amende ?')">
                                                ✕
                                            </a>
                                        </c:if>
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