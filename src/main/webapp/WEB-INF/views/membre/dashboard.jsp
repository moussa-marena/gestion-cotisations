<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mon Espace — SunuAssos</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/sunuassos-theme.css">
    <style>
        body { background-color: #EDF2F4; color: #2B2D42; }

        .stat-card {
            border: none;
            border-radius: 12px;
            padding: 20px 24px;
            color: white;
            position: relative;
            overflow: hidden;
        }
        .stat-card .icon {
            font-size: 40px;
            opacity: 0.25;
            position: absolute;
            right: 16px; top: 16px;
        }
        .stat-card .number {
            font-size: 30px;
            font-weight: 700;
            line-height: 1;
        }
        .stat-card .label {
            font-size: 13px;
            opacity: 0.85;
            margin-top: 6px;
        }
        .sc-blue   { background: linear-gradient(135deg, #0D3B66, #1d5d99); }
        .sc-green  { background: linear-gradient(135deg, #1B8A5A, #25a86f); }
        .sc-orange { background: linear-gradient(135deg, #F4A261, #e08c42); }

        .card {
            border: none;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(13,59,102,0.08);
        }
        .card-header-custom {
            background-color: #0D3B66;
            color: white;
            font-weight: 600;
            border-radius: 12px 12px 0 0 !important;
            padding: 12px 16px;
        }

        .table thead th {
            background-color: #EDF2F4;
            color: #8D99AE;
            font-size: 12px;
            text-transform: uppercase;
            letter-spacing: .05em;
            border: none;
        }

        .badge-payee {
            background-color: #1B8A5A;
            color: white;
            padding: 4px 10px;
            border-radius: 20px;
            font-size: 11px;
        }
        .badge-attente {
            background-color: #F4A261;
            color: #2B2D42;
            padding: 4px 10px;
            border-radius: 20px;
            font-size: 11px;
        }

        .status-ok {
            background-color: #d4edda;
            border-left: 4px solid #1B8A5A;
            border-radius: 8px;
            padding: 14px 16px;
            color: #1B8A5A;
        }
        .status-retard {
            background-color: #FFF3E0;
            border-left: 4px solid #F4A261;
            border-radius: 8px;
            padding: 14px 16px;
            color: #e08c42;
        }
    </style>
</head>
<body>

<!-- NAVBAR -->
<nav class="navbar navbar-dark px-4 mb-4"
     style="background-color: #0D3B66;">
    <a class="navbar-brand d-flex align-items-center gap-2"
       href="${pageContext.request.contextPath}/dashboard/membre">
        <img src="${pageContext.request.contextPath}/images/logo.png"
             alt="SunuAssos" style="height:36px;">
        SunuAssos
    </a>
    <div class="ms-auto d-flex align-items-center gap-3">
        <span class="text-white-50 small">
            <strong class="text-white">${membre.nomComplet}</strong>
        </span>
        <a href="${pageContext.request.contextPath}/membre/profil"
           class="btn btn-sm btn-outline-light">👤 Mon profil</a>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-sm btn-outline-light">🚪 Déconnexion</a>
    </div>
</nav>

<div class="container-fluid px-4">

    <!-- Accès refusé -->
    <c:if test="${acceRefuse}">
        <div class="alert mb-3"
             style="background-color:#fde8e8; border-left:4px solid #c0392b; border-radius:8px;">
            ⛔ Accès refusé. Vous n'avez pas les droits administrateur.
        </div>
    </c:if>

    <!-- Titre -->
    <div class="mb-4">
        <h4 class="mb-0">Bonjour, <strong>${membre.prenom}</strong> 👋</h4>
        <small class="text-muted">
            Votre situation pour ${nomMois} ${annee}
        </small>
    </div>

    <!-- STATUT DU MOIS -->
    <div class="mb-4">
        <c:choose>
            <c:when test="${aPayeCeMois}">
                <div class="status-ok">
                    ✅ <strong>Vous êtes à jour</strong> pour ${nomMois} ${annee} !
                    Votre cotisation a bien été enregistrée.
                </div>
            </c:when>
            <c:otherwise>
                <div class="status-retard">
                    ⚠️ <strong>Cotisation en attente</strong> pour ${nomMois} ${annee}.
                    Contactez l'administrateur pour régulariser votre situation.
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- CARTES STATISTIQUES -->
    <div class="row g-3 mb-4">
        <div class="col-md-4">
            <div class="stat-card sc-blue">
                <div class="icon">💰</div>
                <div class="number">${nbCotisations}</div>
                <div class="label">Cotisations payées</div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="stat-card ${nbAmendes > 0 ? 'sc-orange' : 'sc-green'}">
                <div class="icon">⚠️</div>
                <div class="number">${nbAmendes}</div>
                <div class="label">Amendes en attente</div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="stat-card sc-blue">
                <div class="icon">💸</div>
                <div class="number">${totalAmendesDues}</div>
                <div class="label">FCFA d'amendes dues</div>
            </div>
        </div>
    </div>

    <div class="row g-4">

        <!-- Historique cotisations -->
        <div class="col-lg-7">
            <div class="card">
                <div class="card-header-custom">
                    💰 Historique de mes cotisations
                </div>
                <div class="card-body p-0">
                    <table class="table table-hover mb-0">
                        <thead>
                            <tr>
                                <th>Période</th>
                                <th>Montant</th>
                                <th>Mode</th>
                                <th>Date</th>
                                <th>Statut</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty cotisations}">
                                    <tr>
                                        <td colspan="5"
                                            class="text-center py-3"
                                            style="color:#8D99AE;">
                                            Aucune cotisation enregistrée.
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="c" items="${cotisations}">
                                        <tr>
                                            <td>
                                                <strong style="color:#0D3B66;">
                                                    ${c.periode}
                                                </strong>
                                            </td>
                                            <td style="color:#1B8A5A; font-weight:700;">
                                                ${c.montant} FCFA
                                            </td>
                                            <td>${c.modePaiement}</td>
                                            <td>${c.datePaiement}</td>
                                            <td>
                                                <span class="badge-payee">
                                                    PAYÉE
                                                </span>
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

        <!-- Amendes en attente -->
        <div class="col-lg-5">
            <div class="card">
                <div class="card-header-custom">
                    ⚠️ Mes amendes en attente
                </div>
                <div class="card-body p-0">
                    <table class="table table-hover mb-0">
                        <thead>
                            <tr>
                                <th>Motif</th>
                                <th>Montant</th>
                                <th>Statut</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty amendesEnAttente}">
                                    <tr>
                                        <td colspan="3"
                                            class="text-center py-3"
                                            style="color:#1B8A5A; font-weight:600;">
                                            ✅ Aucune amende en attente !
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="a" items="${amendesEnAttente}">
                                        <tr>
                                            <td>${a.motif}</td>
                                            <td style="color:#F4A261; font-weight:700;">
                                                ${a.montant} FCFA
                                            </td>
                                            <td>
                                                <span class="badge-attente">
                                                    EN ATTENTE
                                                </span>
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