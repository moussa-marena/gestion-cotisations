<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Membres — SunuAssos</title>
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

        .badge-admin {
            background-color: #0D3B66;
            color: white;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 11px;
            font-weight: 600;
        }
        .badge-membre {
            background-color: #8D99AE;
            color: white;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 11px;
            font-weight: 600;
        }
        .badge-actif {
            background-color: #1B8A5A;
            color: white;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 11px;
            font-weight: 600;
        }
        .badge-inactif {
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
            text-decoration: none;
        }
        .btn-retour:hover { background: white; color: #0D3B66; }

        .btn-ajouter {
            background-color: white;
            border: none;
            color: #0D3B66;
            border-radius: 8px;
            padding: 6px 16px;
            font-size: 14px;
            font-weight: 600;
            text-decoration: none;
        }
        .btn-ajouter:hover { background-color: #EDF2F4; color: #0D3B66; }

        .btn-rechercher {
            background-color: #0D3B66;
            border: none;
            color: white;
            border-radius: 8px;
            padding: 8px 20px;
            font-weight: 600;
        }
        .btn-rechercher:hover { background-color: #082a4a; color: white; }

        .btn-effacer {
            background: transparent;
            border: 1.5px solid #8D99AE;
            color: #8D99AE;
            border-radius: 8px;
            padding: 8px 16px;
            text-decoration: none;
        }
        .btn-effacer:hover { border-color: #2B2D42; color: #2B2D42; }

        .form-control:focus {
            border-color: #0D3B66;
            box-shadow: 0 0 0 3px rgba(13,59,102,0.1);
        }
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
            <h2>👥 Gestion des Membres</h2>
            <small>
                Total : <strong>${total}</strong> |
                Actifs : <strong>${actifs}</strong>
            </small>
        </div>
        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/dashboard/admin"
               class="btn-retour">← Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/membres?action=ajouter"
               class="btn-ajouter">+ Ajouter un membre</a>
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

    <!-- Barre de recherche -->
    <div class="card mb-4">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/membres"
                  method="get" class="d-flex gap-2">
                <input type="text" name="search" class="form-control"
                       placeholder="Rechercher par nom, prénom ou email..."
                       value="${keyword}">
                <button type="submit" class="btn-rechercher">
                    🔍 Rechercher
                </button>
                <c:if test="${keyword != null}">
                    <a href="${pageContext.request.contextPath}/admin/membres"
                       class="btn-effacer">✕ Effacer</a>
                </c:if>
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
                                <td colspan="8"
                                    class="text-center py-4"
                                    style="color:#8D99AE;">
                                    Aucun membre trouvé.
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="m" items="${membres}">
                                <tr>
                                    <td style="color:#8D99AE;">${m.id}</td>
                                    <td>
                                        <strong style="color:#0D3B66;">
                                            ${m.prenom} ${m.nom}
                                        </strong>
                                    </td>
                                    <td>${m.email}</td>
                                    <td>${m.telephone}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${m.role == 'ADMIN'}">
                                                <span class="badge-admin">ADMIN</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge-membre">MEMBRE</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${m.statut == 'ACTIF'}">
                                                <span class="badge-actif">ACTIF</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge-inactif">INACTIF</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${m.dateAdhesion}</td>
                                    <td class="text-center">
                                        <!-- Modifier -->
                                        <a href="${pageContext.request.contextPath}/admin/membres?action=modifier&id=${m.id}"
                                           class="btn btn-sm"
                                           style="background-color:#F4A261; color:#2B2D42;">
                                            ✏️
                                        </a>

                                        <!-- Toggle statut -->
                                        <a href="${pageContext.request.contextPath}/admin/membres?action=toggle&id=${m.id}"
                                           class="btn btn-sm"
                                           style="${m.statut == 'ACTIF'
                                               ? 'background-color:#8D99AE; color:white;'
                                               : 'background-color:#1B8A5A; color:white;'}"
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