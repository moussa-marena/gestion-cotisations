<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mon Profil — SunuAssos</title>
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
            margin-bottom: 20px;
        }
        .card-header-custom {
            background-color: #0D3B66;
            color: white;
            font-weight: 600;
            border-radius: 12px 12px 0 0 !important;
            padding: 12px 16px;
        }

        .form-label { color: #2B2D42; font-weight: 600; }

        .form-control:focus,
        .form-select:focus {
            border-color: #0D3B66;
            box-shadow: 0 0 0 3px rgba(13,59,102,0.1);
        }
        .form-control:disabled {
            background-color: #EDF2F4;
            color: #8D99AE;
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

        .btn-sauvegarder {
            background-color: #0D3B66;
            border: none;
            color: white;
            border-radius: 8px;
            padding: 8px 20px;
            font-weight: 600;
        }
        .btn-sauvegarder:hover { background-color: #082a4a; color: white; }

        .btn-changer-mdp {
            background-color: #F4A261;
            border: none;
            color: #2B2D42;
            border-radius: 8px;
            padding: 8px 20px;
            font-weight: 600;
        }
        .btn-changer-mdp:hover { background-color: #e08c42; color: #2B2D42; }
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
    <div class="ms-auto">
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-sm btn-outline-light">🚪 Déconnexion</a>
    </div>
</nav>

<div class="container px-4" style="max-width:700px">

    <!-- En-tête -->
    <div class="page-header d-flex justify-content-between align-items-center">
        <h2>👤 Mon Profil</h2>
        <a href="${pageContext.request.contextPath}/dashboard/membre"
           class="btn-retour">← Mon espace</a>
    </div>

    <!-- Messages -->
    <c:if test="${param.succes != null}">
        <div class="alert alert-dismissible fade show mb-3"
             style="background-color:#d4edda; border-left:4px solid #1B8A5A; border-radius:8px;">
            ✅ ${param.succes}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <!-- Informations personnelles -->
    <div class="card">
        <div class="card-header-custom">✏️ Modifier mes informations</div>
        <div class="card-body p-4">

            <c:if test="${erreur != null}">
                <div class="alert mb-3"
                     style="background-color:#fde8e8; border-left:4px solid #c0392b; border-radius:8px;">
                    ❌ ${erreur}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/membre/profil"
                  method="post">
                <input type="hidden" name="action" value="modifierProfil">

                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label">Nom</label>
                        <input type="text" class="form-control"
                               value="${membre.nom}" disabled>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Prénom</label>
                        <input type="text" class="form-control"
                               value="${membre.prenom}" disabled>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Email</label>
                        <input type="email" class="form-control"
                               value="${membre.email}" disabled>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Téléphone</label>
                        <input type="text" name="telephone"
                               class="form-control"
                               value="${membre.telephone}">
                    </div>
                    <div class="col-12">
                        <label class="form-label">Adresse</label>
                        <input type="text" name="adresse"
                               class="form-control"
                               value="${membre.adresse}">
                    </div>
                </div>

                <small style="color:#8D99AE;" class="d-block mt-2">
                    Le nom, prénom et email ne peuvent être modifiés que par un administrateur.
                </small>

                <div class="mt-4">
                    <button type="submit" class="btn-sauvegarder">
                        💾 Sauvegarder
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Changer mot de passe -->
    <div class="card">
        <div class="card-header-custom">🔒 Changer mon mot de passe</div>
        <div class="card-body p-4">

            <c:if test="${erreurMdp != null}">
                <div class="alert mb-3"
                     style="background-color:#fde8e8; border-left:4px solid #c0392b; border-radius:8px;">
                    ❌ ${erreurMdp}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/membre/profil"
                  method="post">
                <input type="hidden" name="action" value="changerMdp">

                <div class="row g-3">
                    <div class="col-12">
                        <label class="form-label">Ancien mot de passe *</label>
                        <input type="password" name="ancienMdp"
                               class="form-control" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Nouveau mot de passe *</label>
                        <input type="password" name="nouveauMdp"
                               class="form-control" required minlength="6">
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Confirmer *</label>
                        <input type="password" name="confirmMdp"
                               class="form-control" required minlength="6">
                    </div>
                </div>

                <div class="mt-4">
                    <button type="submit" class="btn-changer-mdp">
                        🔒 Changer le mot de passe
                    </button>
                </div>
            </form>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>