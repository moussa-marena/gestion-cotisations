<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Enregistrer une cotisation</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container py-4" style="max-width:700px">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>➕ Enregistrer une cotisation</h2>
        <a href="${pageContext.request.contextPath}/admin/cotisations"
           class="btn btn-outline-secondary">← Retour</a>
    </div>

    <c:if test="${erreur != null}">
        <div class="alert alert-danger">❌ ${erreur}</div>
    </c:if>

    <div class="card">
        <div class="card-body p-4">
            <form action="${pageContext.request.contextPath}/admin/cotisations"
                  method="post">
                <input type="hidden" name="action" value="ajouter">

                <div class="row g-3">

                    <div class="col-12">
                        <label class="form-label fw-semibold">Membre *</label>
                        <select name="membreId" class="form-select" required>
                            <option value="">-- Sélectionner un membre --</option>
                            <c:forEach var="m" items="${membres}">
                                <option value="${m.id}">
                                    ${m.prenom} ${m.nom} — ${m.email}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Mois *</label>
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
                        <label class="form-label fw-semibold">Année *</label>
                        <input type="number" name="annee"
                               class="form-control"
                               value="${anneeCourante}"
                               min="2020" max="2030" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">
                            Mode de paiement *
                        </label>
                        <select name="modePaiement" class="form-select" required>
                            <option value="ESPECES">Espèces</option>
                            <option value="VIREMENT">Virement</option>
                            <option value="CHEQUE">Chèque</option>
                            <option value="MOBILE_MONEY">Mobile Money</option>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">
                            Référence / N° reçu
                        </label>
                        <input type="text" name="reference"
                               class="form-control"
                               placeholder="Optionnel">
                    </div>

                    <div class="col-12">
                        <div class="alert alert-info mb-0">
                            💡 Montant de la cotisation :
                            <strong>5 000 FCFA</strong>
                        </div>
                    </div>

                </div>

                <div class="d-flex gap-2 mt-4">
                    <button type="submit" class="btn btn-primary">
                        ✅ Enregistrer
                    </button>
                    <a href="${pageContext.request.contextPath}/admin/cotisations"
                       class="btn btn-outline-secondary">Annuler</a>
                </div>

            </form>
        </div>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>