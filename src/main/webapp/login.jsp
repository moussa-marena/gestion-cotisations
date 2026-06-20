<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion — Gestion Cotisations</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f0f2f5;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }
        .login-card {
            width: 100%;
            max-width: 420px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
        }
        .login-header {
            background: #1a56db;
            color: white;
            border-radius: 12px 12px 0 0;
            padding: 30px;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="card login-card">

    <div class="login-header">
        <h4 class="mb-1">🏛 Association</h4>
        <p class="mb-0 opacity-75">Gestion des Cotisations</p>
    </div>

    <div class="card-body p-4">
        <h5 class="card-title mb-4">Connexion</h5>

        <!-- Message d'erreur -->
        <% if (request.getAttribute("erreur") != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <%= request.getAttribute("erreur") %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% } %>

        <!-- Message de succès (ex: après logout) -->
        <% if (request.getParameter("logout") != null) { %>
        <div class="alert alert-success">
            Vous avez été déconnecté avec succès.
        </div>
        <% } %>

        <!-- Message session expirée -->
        <% if (request.getParameter("expired") != null) { %>
        <div class="alert alert-warning">
            <strong>Session expirée.</strong> Veuillez vous reconnecter.
        </div>
        <% } %>

        <!-- Formulaire -->
        <form action="login" method="post">

            <div class="mb-3">
                <label for="email" class="form-label fw-semibold">
                    Email
                </label>
                <input type="email"
                       class="form-control"
                       id="email"
                       name="email"
                       placeholder="exemple@mail.com"
                       value="<%= request.getAttribute("email") != null
                                  ? request.getAttribute("email") : "" %>"
                       required autofocus>
            </div>

            <div class="mb-4">
                <label for="motDePasse" class="form-label fw-semibold">
                    Mot de passe
                </label>
                <input type="password"
                       class="form-control"
                       id="motDePasse"
                       name="motDePasse"
                       placeholder="••••••••"
                       required>
            </div>

            <div class="d-grid">
                <button type="submit" class="btn btn-primary btn-lg">
                    Se connecter
                </button>
            </div>

        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>