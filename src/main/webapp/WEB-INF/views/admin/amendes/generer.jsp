<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Générer les amendes</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container py-4" style="max-width:600px">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>⚡ Générer les amendes</h2>
        <a href="${pageContext.request.contextPath}/admin/amendes"
           class="btn btn-outline-secondary">← Retour</a>
    </div>

    <!-- Résultats de la génération -->
    <c:if test="${resultats != null}">
        <div class="card mb-4">
            <div class="card-header fw-semibold">
                📋 Résultats de la génération
            </div>
            <div class="card-body">
                <c:forEach var="r" items="${resultats}">
                    <p class="mb-1">${r}</p>
                </c:forEach>
            </div>
        </div>
    </c:if>

    <!-- Formulaire -->
    <div class="card">
        <div class="card-body p-4">

            <div class="alert alert-warning mb-4">
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

                </div>

                <div class="d-flex gap-2 mt-4">
                    <button type="submit" class="btn btn-warning"
                            onclick="return confirm('Générer les amendes pour cette période ?')">
                        ⚡ Générer
                    </button>
                    <a href="${pageContext.request.contextPath}/admin/amendes"
                       class="btn btn-outline-secondary">Annuler</a>
                </div>

            </form>
        </div>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>