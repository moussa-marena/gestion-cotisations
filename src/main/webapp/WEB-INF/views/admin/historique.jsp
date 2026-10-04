<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Historique Connexions — SunuAssos</title>
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

        .stat-card {
            border: none;
            border-radius: 12px;
            padding: 20px 24px;
            color: white;
        }
        .sc-blue   { background: linear-gradient(135deg,#0D3B66,#1d5d99); }
        .sc-green  { background: linear-gradient(135deg,#1B8A5A,#25a86f); }
        .sc-orange { background: linear-gradient(135deg,#F4A261,#e08c42); }
        .stat-card .number { font-size: 28px; font-weight: 700; }
        .stat-card .label  { font-size: 13px; opacity: 0.85; }

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

        .badge-succes {
            background-color: #1B8A5A;
            color: white;
            padding: 4px 10px;
            border-radius: 20px;
            font-size: 11px;
            font-weight: 600;
        }
        .badge-echec {
            background-color: #c0392b;
            color: white;
            padding: 4px 10px;
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
            text-decoration: none;
        }
        .btn-retour:hover { background: white; color: #0D3B66; }
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
            <h2>🔐 Historique des Connexions</h2>
            <small>Toutes les tentatives de connexion</small>
        </div>
        <a href="${pageContext.request.contextPath}/dashboard/admin"
           class="btn-retour">← Dashboard</a>
    </div>

    <!-- Statistiques -->
    <div class="row g-3 mb-4">
        <div class="col-md-4">
            <div class="stat-card sc-blue">
                <div class="number">${nbTotal}</div>
                <div class="label">Total connexions</div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="stat-card sc-green">
                <div class="number">${nbSucces}</div>
                <div class="label">Connexions réussies</div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="stat-card sc-orange">
                <div class="number">${nbEchecs}</div>
                <div class="label">Tentatives échouées</div>
            </div>
        </div>
    </div>

    <!-- Tableau -->
    <div class="card">
        <div class="card-body p-0">
            <div style="overflow-x: auto;">
            <table class="table table-hover mb-0">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Membre</th>
                        <th>Email</th>
                        <th>Adresse IP</th>
                        <th>Date & Heure</th>
                        <th>Statut</th>
                        <th>Message</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty historiques}">
                            <tr>
                                <td colspan="7"
                                    class="text-center py-4"
                                    style="color:#8D99AE;">
                                    Aucune connexion enregistrée.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="h" items="${historiques}">
                                <tr>
                                    <td style="color:#8D99AE;">${h.id}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${h.membre != null}">
                                                <strong style="color:#0D3B66;">
                                                    ${h.membre.nomComplet}
                                                </strong>
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color:#8D99AE;">
                                                    Inconnu
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${h.email}</td>
                                    <td>
                                        <code>${h.adresseIp}</code>
                                    </td>
                                    <td>${h.dateConnexion}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${h.statut == 'SUCCES'}">
                                                <span class="badge-succes">
                                                    ✅ SUCCÈS
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge-echec">
                                                    ❌ ÉCHEC
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td style="color:#8D99AE;">
                                        ${h.message}
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>