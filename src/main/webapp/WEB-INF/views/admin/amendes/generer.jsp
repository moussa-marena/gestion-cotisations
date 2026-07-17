<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Générer les amendes — SunuAssos</title>
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

        .card-header-resultats {
            background-color: #0D3B66;
            color: white;
            font-weight: 600;
            border-radius: 12px 12px 0 0 !important;
            padding: 12px 16px;
        }

        .alerte-warning {
            background-color: #FFF3E0;
            border-left: 4px solid #F4A261;
            border-radius: 8px;
            padding: 14px 16px;
            color: #2B2D42;
            font-size: 14px;
            margin-bottom: 20px;
        }

        .form-label { color: #2B2D42; font-weight: 600; }

        .form-control:focus,
        .form-select:focus {
            border-color: #0D3B66;
            box-shadow: 0 0 0 3px rgba(13,59,102,0.1);
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

        .btn-generer {
            background-color: #F4A261;
            border: none;
            color: #2B2D42;
            border-radius: 8px;
            padding: 8px 20px;
            font-weight: 600;
        }
        .btn-generer:hover { background-color: #e08c42; color: #2B2D42; }

        .btn-annuler {
            background: transparent;
            border: 1.5px solid #8D99AE;
            color: #8D99AE;
            border-radius: 8px;
            padding: 8px 20px;
            text-decoration: none;
        }
        .btn-annuler:hover { border-color: #2B2D42; color: #2B2D42; }

        /* Résultats */
        .resultat-ok   { color: #1B8A5A; font-weight: 500; }
        .resultat-warn { color: #F4A261; font-weight: 500; }
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

<div class="container px-4" style="max-width:600px">

    <!-- En-tête -->
    <div class="page-header d-flex justify-content-between align-items-center">
        <h2>⚡ Générer les amendes</h2>
        <a href="${pageContext.request.contextPath}/admin/amendes"
           class="btn-retour">← Retour</a>
    </div>

    <!-- Résultats de la génération -->
    <c:if test="${resultats != null}">
        <div class="card mb-4">
            <div class="card-header-resultats">
                📋 Résultats de la génération
            </div>
            <div class="card-body">
                <c:forEach var="r" items="${resultats}">
                    <p class="mb-1
                        ${r.startsWith('✅') ? 'resultat-ok' : 'resultat-warn'}">
                        ${r}
                    </p>
                </c:forEach>
            </div>
        </div>
    </c:if>

    <!-- Formulaire -->
    <div class="card">
        <div class="card-body p-4">

            <div class="alerte-warning">
                ⚠️ Cette action génère une amende de
                <strong>1 000 FCFA</strong> pour chaque membre
                n'ayant pas payé sa cotisation pour la période sélectionnée.
                Les membres ayant déjà une amende pour cette période
                ne seront pas affectés.
            </div>

            <form action="${pageContext.request.contextPath}/admin/amendes"
                  method="post">
                <input type="hidden" name="action" value="generer">

                <div class="row g-3">

                    <div class="col-md-6">
                        <label class="form-label">Mois *</label>
                        <select name="mois" class="form-select" required>
                            <c:forEach begin="1" end="12" var="m">
                                <option value="${m}"
                                    ${m == moisCourant ? 'selected' : ''}>
                                    ${m}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Année *</label>
                        <input type="number" name="annee"
                               class="form-control"
                               value="${anneeCourante}"
                               min="2020" max="2030" required>
                    </div>

                </div>

                <div class="d-flex gap-2 mt-4">
                    <button type="submit" class="btn-generer"
                            onclick="return confirm('Générer les amendes pour cette période ?')">
                        ⚡ Générer
                    </button>
                    <a href="${pageContext.request.contextPath}/admin/amendes"
                       class="btn-annuler">Annuler</a>
                </div>

            </form>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>