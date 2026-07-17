<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Cotisations — SunuAssos</title>
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

        .badge-payee {
            background-color: #1B8A5A;
            color: white;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 11px;
            font-weight: 600;
        }

        .montant {
            color: #1B8A5A;
            font-weight: 700;
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

        .btn-retard {
            background-color: #F4A261;
            border: none;
            color: #2B2D42;
            border-radius: 8px;
            padding: 6px 16px;
            font-size: 14px;
            font-weight: 600;
            text-decoration: none;
        }
        .btn-retard:hover { background-color: #e08c42; color: #2B2D42; }

        .btn-enregistrer {
            background-color: white;
            border: none;
            color: #0D3B66;
            border-radius: 8px;
            padding: 6px 16px;
            font-size: 14px;
            font-weight: 600;
            text-decoration: none;
        }
        .btn-enregistrer:hover { background-color: #EDF2F4; color: #0D3B66; }

        .btn-filtrer {
            background-color: #0D3B66;
            border: none;
            color: white;
            border-radius: 8px;
            padding: 8px 20px;
            font-weight: 600;
        }
        .btn-filtrer:hover { background-color: #082a4a; color: white; }

        .form-control:focus,
        .form-select:focus {
            border-color: #0D3B66;
            box-shadow: 0 0 0 3px rgba(13,59,102,0.1);
        }

        .form-label { color: #2B2D42; font-weight: 600; }
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
            <h2>💰 Gestion des Cotisations</h2>
            <small>
                ${nomMois} ${annee} —
                Total encaissé : <strong>${totalEncaisse} FCFA</strong> |
                En retard : <strong>${nbEnRetard}</strong>
            </small>
        </div>
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/dashboard/admin"
               class="btn-retour">← Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/cotisations?action=retard&mois=${mois}&annee=${annee}"
               class="btn-retard">⚠️ Membres en retard</a>
            <a href="${pageContext.request.contextPath}/admin/cotisations?action=ajouter"
               class="btn-enregistrer">+ Enregistrer</a>
        </div>
    </div>

    <!-- Messages -->
    <c:if test="${param.succes != null}">
        <div class="alert alert-dismissible fade show mb-3"
             style="background-color:#d4edda; border-left:4px solid #1B8A5A; border-radius:8px;">
            ✅ ${param.succes}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>
    <c:if test="${param.erreur != null}">
        <div class="alert alert-dismissible fade show mb-3"
             style="background-color:#fde8e8; border-left:4px solid #c0392b; border-radius:8px;">
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
                    <label class="form-label mb-1">Mois</label>
                    <select name="mois" class="form-select">
                        <c:forEach begin="1" end="12" var="m">
                            <option value="${m}" ${m == mois ? 'selected' : ''}>
                                ${m}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <label class="form-label mb-1">Année</label>
                    <input type="number" name="annee" class="form-control"
                           value="${annee}" min="2020" max="2030">
                </div>
                <button type="submit" class="btn-filtrer">
                    🔍 Filtrer
                </button>
            </form>
        </div>
    </div>

    <!-- Tableau -->
    <div class="card">
        <div class="card-body p-0">
            <table class="table table-hover mb-0">
                <thead>
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
                                    class="text-center py-4"
                                    style="color:#8D99AE;">
                                    Aucune cotisation pour ${nomMois} ${annee}.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="c" items="${cotisations}">
                                <tr>
                                    <td style="color:#8D99AE;">${c.id}</td>
                                    <td>
                                        <strong style="color:#0D3B66;">
                                            ${c.membre.prenom} ${c.membre.nom}
                                        </strong>
                                    </td>
                                    <td>${c.periode}</td>
                                    <td>
                                        <span class="montant">
                                            ${c.montant} FCFA
                                        </span>
                                    </td>
                                    <td>${c.modePaiement}</td>
                                    <td>${c.datePaiement}</td>
                                    <td>${c.reference}</td>
                                    <td>
                                        <span class="badge-payee">
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