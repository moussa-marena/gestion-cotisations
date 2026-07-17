<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Membres en retard — SunuAssos</title>
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

        .card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(13,59,102,0.08);
        }

        /* Carte en retard */
        .card-retard {
            border-left: 4px solid #F4A261 !important;
        }
        .card-header-retard {
            background-color: #F4A261;
            color: #2B2D42;
            font-weight: 600;
            border-radius: 12px 12px 0 0 !important;
            padding: 12px 16px;
        }
        .badge-retard {
            background-color: white;
            color: #F4A261;
            padding: 3px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 700;
        }

        /* Carte à jour */
        .card-ajour {
            border-left: 4px solid #1B8A5A !important;
        }
        .card-header-ajour {
            background-color: #1B8A5A;
            color: white;
            font-weight: 600;
            border-radius: 12px 12px 0 0 !important;
            padding: 12px 16px;
        }
        .badge-ajour {
            background-color: white;
            color: #1B8A5A;
            padding: 3px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 700;
        }

        .table thead th {
            background-color: #EDF2F4;
            color: #8D99AE;
            font-weight: 600;
            font-size: 12px;
            text-transform: uppercase;
            letter-spacing: .05em;
            border: none;
        }
        .table tbody tr:hover { background-color: #EDF2F4; }

        .btn-retour {
            background: transparent;
            border: 1.5px solid white;
            color: white;
            border-radius: 8px;
            padding: 6px 16px;
            font-size: 14px;
            text-decoration: none;
        }
        .btn-retour:hover { background: white; color: #0D3B66; }

        .btn-payer {
            background-color: #0D3B66;
            border: none;
            color: white;
            border-radius: 6px;
            padding: 4px 12px;
            font-size: 13px;
            font-weight: 600;
            text-decoration: none;
        }
        .btn-payer:hover { background-color: #082a4a; color: white; }
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
        <h2>⚠️ Membres en retard — ${nomMois} ${annee}</h2>
        <a href="${pageContext.request.contextPath}/admin/cotisations"
           class="btn-retour">← Retour</a>
    </div>

    <div class="row g-4">

        <!-- Membres EN RETARD -->
        <div class="col-md-6">
            <div class="card card-retard">
                <div class="card-header-retard">
                    ❌ En retard
                    <span class="badge-retard ms-2">
                        ${membresEnRetard.size()}
                    </span>
                </div>
                <div class="card-body p-0">
                    <table class="table mb-0">
                        <thead>
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
                                            class="text-center py-3"
                                            style="color:#1B8A5A; font-weight:600;">
                                            ✅ Tous les membres sont à jour !
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="m" items="${membresEnRetard}">
                                        <tr>
                                            <td>
                                                <strong style="color:#0D3B66;">
                                                    ${m.prenom} ${m.nom}
                                                </strong>
                                            </td>
                                            <td>
                                                <small style="color:#8D99AE;">
                                                    ${m.email}
                                                </small>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/admin/cotisations?action=ajouter"
                                                   class="btn-payer">
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
            <div class="card card-ajour">
                <div class="card-header-ajour">
                    ✅ À jour
                    <span class="badge-ajour ms-2">
                        ${membresAJour.size()}
                    </span>
                </div>
                <div class="card-body p-0">
                    <table class="table mb-0">
                        <thead>
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
                                            class="text-center py-3"
                                            style="color:#8D99AE;">
                                            Aucun paiement ce mois.
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="m" items="${membresAJour}">
                                        <tr>
                                            <td>
                                                <strong style="color:#0D3B66;">
                                                    ${m.prenom} ${m.nom}
                                                </strong>
                                            </td>
                                            <td>
                                                <small style="color:#8D99AE;">
                                                    ${m.email}
                                                </small>
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