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

    <!-- Erreur générale -->
    <c:if test="${erreurs != null && erreurs['general'] != null}">
        <div class="alert alert-dismissible fade show mb-3"
             style="background-color:#fde8e8;
                    border-left:4px solid #c0392b;
                    border-radius:8px;">
            ❌ ${erreurs['general']}
            <button type="button" class="btn-close"
                    data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <div class="card">
        <div class="card-body p-4">
            <form action="${pageContext.request.contextPath}/admin/membres"
                  method="post" id="formAjouter" novalidate>
                <input type="hidden" name="action" value="ajouter">

                <div class="row g-3">

                    <div class="col-md-6">
                        <label class="form-label">Nom *</label>
                        <input type="text" name="nom" id="nom"
                               class="form-control
                                   ${erreurs != null && erreurs['nom'] != null
                                       ? 'is-invalid' : ''}"
                               value="${nom}" required minlength="2">
                        <c:if test="${erreurs != null
                                      && erreurs['nom'] != null}">
                            <div class="invalid-feedback">
                                ${erreurs['nom']}
                            </div>
                        </c:if>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Prénom *</label>
                        <input type="text" name="prenom" id="prenom"
                               class="form-control
                                   ${erreurs != null && erreurs['prenom'] != null
                                       ? 'is-invalid' : ''}"
                               value="${prenom}" required minlength="2">
                        <c:if test="${erreurs != null
                                      && erreurs['prenom'] != null}">
                            <div class="invalid-feedback">
                                ${erreurs['prenom']}
                            </div>
                        </c:if>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Email *</label>
                        <input type="email" name="email" id="email"
                               class="form-control
                                   ${erreurs != null && erreurs['email'] != null
                                       ? 'is-invalid' : ''}"
                               value="${email}" required>
                        <c:if test="${erreurs != null
                                      && erreurs['email'] != null}">
                            <div class="invalid-feedback">
                                ${erreurs['email']}
                            </div>
                        </c:if>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Téléphone</label>
                        <input type="text" name="telephone" id="telephone"
                               class="form-control
                                   ${erreurs != null
                                       && erreurs['telephone'] != null
                                       ? 'is-invalid' : ''}"
                               value="${telephone}"
                               placeholder="771234567">
                        <c:if test="${erreurs != null
                                      && erreurs['telephone'] != null}">
                            <div class="invalid-feedback">
                                ${erreurs['telephone']}
                            </div>
                        </c:if>
                    </div>

                    <div class="col-12">
                        <label class="form-label">Adresse</label>
                        <input type="text" name="adresse"
                               class="form-control" value="${adresse}">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Mot de passe *</label>
                        <input type="password" name="motDePasse"
                               id="motDePasse"
                               class="form-control
                                   ${erreurs != null
                                       && erreurs['motDePasse'] != null
                                       ? 'is-invalid' : ''}"
                               required minlength="6">
                        <c:if test="${erreurs != null
                                      && erreurs['motDePasse'] != null}">
                            <div class="invalid-feedback">
                                ${erreurs['motDePasse']}
                            </div>
                        </c:if>
                        <!-- Indicateur force du mot de passe -->
                        <div id="mdp-force" class="mt-1"
                             style="height:4px; border-radius:2px;
                                    background:#EDF2F4; display:none;">
                            <div id="mdp-barre"
                                 style="height:100%; border-radius:2px;
                                        transition:width .3s,
                                                   background .3s;">
                            </div>
                        </div>
                        <small id="mdp-texte"
                               style="color:#8D99AE;"></small>
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
                    <button type="submit" class="btn-enregistrer"
                            id="btnSubmit">
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

<!-- VALIDATION JAVASCRIPT -->
<script>
const form     = document.getElementById('formAjouter');
const mdpInput = document.getElementById('motDePasse');
const mdpForce = document.getElementById('mdp-force');
const mdpBarre = document.getElementById('mdp-barre');
const mdpTexte = document.getElementById('mdp-texte');

// ===== Indicateur force mot de passe =====
mdpInput.addEventListener('input', function() {
    const mdp = this.value;
    if (mdp.length === 0) {
        mdpForce.style.display = 'none';
        mdpTexte.textContent = '';
        return;
    }
    mdpForce.style.display = 'block';
    let score = 0;
    if (mdp.length >= 6)  score++;
    if (mdp.length >= 10) score++;
    if (/[A-Z]/.test(mdp)) score++;
    if (/[0-9]/.test(mdp)) score++;
    if (/[^A-Za-z0-9]/.test(mdp)) score++;
    const niveaux = [
        { pct: '20%',  color: '#c0392b', texte: 'Très faible' },
        { pct: '40%',  color: '#F4A261', texte: 'Faible' },
        { pct: '60%',  color: '#f0c040', texte: 'Moyen' },
        { pct: '80%',  color: '#1B8A5A', texte: 'Fort' },
        { pct: '100%', color: '#0D3B66', texte: 'Très fort' }
    ];
    const n = niveaux[Math.min(score, 4)];
    mdpBarre.style.width      = n.pct;
    mdpBarre.style.background = n.color;
    mdpTexte.textContent      = n.texte;
    mdpTexte.style.color      = n.color;
});

// ===== Validation JS avant soumission =====
form.addEventListener('submit', function(e) {
    let valide = true;

    const nom = document.getElementById('nom');
    if (nom.value.trim().length < 2) {
        afficherErreur(nom, 'Le nom doit contenir au moins 2 caractères.');
        valide = false;
    } else { supprimerErreur(nom); }

    const prenom = document.getElementById('prenom');
    if (prenom.value.trim().length < 2) {
        afficherErreur(prenom,
            'Le prénom doit contenir au moins 2 caractères.');
        valide = false;
    } else { supprimerErreur(prenom); }

    const email = document.getElementById('email');
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email.value.trim())) {
        afficherErreur(email, "Format d'email invalide.");
        valide = false;
    } else { supprimerErreur(email); }

    const mdp = document.getElementById('motDePasse');
    if (mdp.value.length < 6) {
        afficherErreur(mdp,
            'Le mot de passe doit contenir au moins 6 caractères.');
        valide = false;
    } else { supprimerErreur(mdp); }

    if (!valide) e.preventDefault();
});

function afficherErreur(input, message) {
    input.classList.add('is-invalid');
    let feedback = input.nextElementSibling;
    if (!feedback || !feedback.classList.contains('invalid-feedback')) {
        feedback = document.createElement('div');
        feedback.className = 'invalid-feedback';
        input.parentNode.insertBefore(feedback, input.nextSibling);
    }
    feedback.textContent = message;
}

function supprimerErreur(input) {
    input.classList.remove('is-invalid');
    input.classList.add('is-valid');
}
</script>
</body>
</html>