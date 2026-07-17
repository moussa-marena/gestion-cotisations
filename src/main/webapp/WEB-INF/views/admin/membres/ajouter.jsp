<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un Membre — SunuAssos</title>
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

        .btn-enregistrer {
            background-color: #0D3B66;
            border: none;
            color: white;
            border-radius: 8px;
            padding: 8px 20px;
            font-weight: 600;
        }
        .btn-enregistrer:hover { background-color: #082a4a; color: white; }

        .btn-annuler {
            background: transparent;
            border: 1.5px solid #8D99AE;
            color: #8D99AE;
            border-radius: 8px;
            padding: 8px 20px;
            text-decoration: none;
        }
        .btn-annuler:hover { border-color: #2B2D42; color: #2B2D42; }
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

<div class="container px-4" style="max-width:700px">

    <!-- En-tête -->
    <div class="page-header d-flex justify-content-between align-items-center">
        <h2>➕ Ajouter un membre</h2>
        <a href="${pageContext.request.contextPath}/admin/membres"
           class="btn-retour">← Retour</a>
    </div>

    <!-- Message d'erreur -->
    <c:if test="${erreur != null}">
        <div class="alert alert-dismissible fade show mb-3"
             style="background-color:#fde8e8; border-left:4px solid #c0392b; border-radius:8px;">
            ❌ ${erreur}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <div class="card">
        <div class="card-body p-4">
            <form action="${pageContext.request.contextPath}/admin/membres"
                  method="post" novalidate>
                <input type="hidden" name="action" value="ajouter">

                <div class="row g-3">

                    <div class="col-md-6">
                        <label class="form-label">Nom *</label>
                        <input type="text" name="nom" class="form-control"
                               value="${nom}" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Prénom *</label>
                        <input type="text" name="prenom" class="form-control"
                               value="${prenom}" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Email *</label>
                        <input type="email" name="email" class="form-control"
                               value="${email}" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Téléphone</label>
                        <input type="text" name="telephone" class="form-control"
                               value="${telephone}">
                    </div>

                    <div class="col-12">
                        <label class="form-label">Adresse</label>
                        <input type="text" name="adresse" class="form-control"
                               value="${adresse}">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Mot de passe *</label>
                        <input type="password" name="motDePasse"
                               class="form-control" required minlength="6">
                        <small style="color:#8D99AE;">Minimum 6 caractères</small>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Rôle *</label>
                        <select name="role" class="form-select" required>
                            <option value="MEMBRE" selected>Membre</option>
                            <option value="ADMIN">Administrateur</option>
                        </select>
                    </div>

                </div>

                <div class="d-flex gap-2 mt-4">
                    <button type="submit" class="btn-enregistrer">
                        ✅ Enregistrer
                    </button>
                    <a href="${pageContext.request.contextPath}/admin/membres"
                       class="btn-annuler">Annuler</a>
                </div>

            </form>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>