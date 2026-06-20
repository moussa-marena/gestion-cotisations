<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Admin</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body { background-color: #f0f2f5; }

        .navbar-brand { font-weight: 600; }

        .stat-card {
            border: none;
            border-radius: 12px;
            padding: 24px;
            color: white;
            position: relative;
            overflow: hidden;
        }
        .stat-card .icon {
            font-size: 48px;
            opacity: 0.25;
            position: absolute;
            right: 16px;
            top: 16px;
        }
        .stat-card .number {
            font-size: 36px;
            font-weight: 700;
            line-height: 1;
        }
        .stat-card .label {
            font-size: 14px;
            opacity: 0.85;
            margin-top: 6px;
        }
        .stat-card .sub {
            font-size: 12px;
            opacity: 0.7;
            margin-top: 4px;
        }

        .bg-blue   { background: linear-gradient(135deg, #1a56db, #3b82f6); }
        .bg-green  { background: linear-gradient(135deg, #057a55, #10b981); }
        .bg-red    { background: linear-gradient(135deg, #c81e1e, #f05252); }
        .bg-orange { background: linear-gradient(135deg, #b45309, #f59e0b); }
        .bg-purple { background: linear-gradient(135deg, #5521b5, #8b5cf6); }
        .bg-teal   { background: linear-gradient(135deg, #0694a2, #06b6d4); }

        .quick-link {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 14px 18px;
            border-radius: 10px;
            border: 1px solid #e5e7eb;
            background: white;
            text-decoration: none;
            color: #374151;
            transition: all .2s;
        }
        .quick-link:hover {
            border-color: #1a56db;
            color: #1a56db;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        }
        .quick-link .ql-icon { font-size: 24px; }
        .quick-link .ql-text { font-size: 14px; font-weight: 500; }

        .progress-bar-custom {
            height: 8px;
            border-radius: 4px;
        }
    </style>
</head>
<body>

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark px-4">
    <span class="navbar-brand">🏛 Association — Admin</span>
    <div class="ms-auto d-flex align-items-center gap-3">
        <span class="text-white-50 small">
            Connecté : <strong class="text-white">${membre.nomComplet}</strong>
        </span>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-sm btn-outline-light">
            🚪 Déconnexion
        </a>
    </div>
</nav>

<div class="container-fluid py-4 px-4">

    <!-- Titre -->
    <div class="mb-4">
        <h4 class="mb-0">Tableau de bord</h4>
        <small class="text-muted">
            Statistiques de ${nomMois} ${annee}
        </small>
    </div>

    <!-- CARTES STATISTIQUES -->
    <div class="row g-3 mb-4">

        <!-- Total membres -->
        <div class="col-xl-2 col-md-4 col-sm-6">
            <div class="stat-card bg-blue">
                <div class="icon">👥</div>
                <div class="number">${totalMembres}</div>
                <div class="label">Total membres</div>
                <div class="sub">${membresActifs} actifs</div>
            </div>
        </div>

        <!-- Membres actifs -->
        <div class="col-xl-2 col-md-4 col-sm-6">
            <div class="stat-card bg-green">
                <div class="icon">✅</div>
                <div class="number">${membresActifs}</div>
                <div class="label">Membres actifs</div>
                <div class="sub">sur ${totalMembres} au total</div>
            </div>
        </div>

        <!-- Cotisations à jour -->
        <div class="col-xl-2 col-md-4 col-sm-6">
            <div class="stat-card bg-teal">
                <div class="icon">💰</div>
                <div class="number">${nbAJour}</div>
                <div class="label">À jour ce mois</div>
                <div class="sub">${nomMois} ${annee}</div>
            </div>
        </div>

        <!-- Membres en retard -->
        <div class="col-xl-2 col-md-4 col-sm-6">
            <div class="stat-card bg-red">
                <div class="icon">⏰</div>
                <div class="number">${nbEnRetard}</div>
                <div class="label">En retard</div>
                <div class="sub">${nomMois} ${annee}</div>
            </div>
        </div>

        <!-- Total encaissé -->
        <div class="col-xl-2 col-md-4 col-sm-6">
            <div class="stat-card bg-purple">
                <div class="icon">📈</div>
                <div class="number">${totalEncaisse}</div>
                <div class="label">FCFA encaissés</div>
                <div class="sub">ce mois</div>
            </div>
        </div>

        <!-- Amendes en attente -->
        <div class="col-xl-2 col-md-4 col-sm-6">
            <div class="stat-card bg-orange">
                <div class="icon">⚠️</div>
                <div class="number">${nbAmendes}</div>
                <div class="label">Amendes dues</div>
                <div class="sub">${totalAmendes} FCFA</div>
            </div>
        </div>

    </div>

    <div class="row g-4">

        <!-- Taux de paiement -->
        <div class="col-lg-5">
            <div class="card border-0 shadow-sm h-100">
                <div class="card-body">
                    <h6 class="card-title fw-semibold mb-3">
                        📊 Taux de paiement — ${nomMois} ${annee}
                    </h6>

                    <%-- Calcul du taux --%>
                    <c:set var="total"
                           value="${nbAJour + nbEnRetard}"/>

                    <c:choose>
                        <c:when test="${total > 0}">
                            <%-- Pourcentage à jour --%>
                            <c:set var="pctAJour"
                                   value="${(nbAJour * 100) / total}"/>
                            <c:set var="pctRetard"
                                   value="${(nbEnRetard * 100) / total}"/>

                            <div class="mb-3">
                                <div class="d-flex justify-content-between mb-1">
                                    <small class="text-success fw-semibold">
                                        ✅ À jour (${nbAJour})
                                    </small>
                                    <small class="text-success">
                                        <fmt:formatNumber
                                            value="${pctAJour}"
                                            maxFractionDigits="0"/>%
                                    </small>
                                </div>
                                <div class="progress progress-bar-custom">
                                    <div class="progress-bar bg-success"
                                         style="width:${pctAJour}%"></div>
                                </div>
                            </div>

                            <div class="mb-3">
                                <div class="d-flex justify-content-between mb-1">
                                    <small class="text-danger fw-semibold">
                                        ❌ En retard (${nbEnRetard})
                                    </small>
                                    <small class="text-danger">
                                        <fmt:formatNumber
                                            value="${pctRetard}"
                                            maxFractionDigits="0"/>%
                                    </small>
                                </div>
                                <div class="progress progress-bar-custom">
                                    <div class="progress-bar bg-danger"
                                         style="width:${pctRetard}%"></div>
                                </div>
                            </div>

                        </c:when>
                        <c:otherwise>
                            <p class="text-muted">
                                Aucun membre actif ce mois.
                            </p>
                        </c:otherwise>
                    </c:choose>

                    <hr>
                    <div class="d-flex justify-content-between">
                        <small class="text-muted">Total encaissé</small>
                        <strong class="text-success">
                            ${totalEncaisse} FCFA
                        </strong>
                    </div>
                    <div class="d-flex justify-content-between mt-1">
                        <small class="text-muted">Amendes en attente</small>
                        <strong class="text-danger">
                            ${totalAmendes} FCFA
                        </strong>
                    </div>
                </div>
            </div>
        </div>

        <!-- Accès rapides -->
        <div class="col-lg-7">
            <div class="card border-0 shadow-sm h-100">
                <div class="card-body">
                    <h6 class="card-title fw-semibold mb-3">
                        ⚡ Accès rapides
                    </h6>
                    <div class="row g-2">

                        <div class="col-md-6">
                            <a href="${pageContext.request.contextPath}/admin/membres"
                               class="quick-link">
                                <span class="ql-icon">👥</span>
                                <div>
                                    <div class="ql-text">Gérer les membres</div>
                                    <small class="text-muted">
                                        ${totalMembres} membres
                                    </small>
                                </div>
                            </a>
                        </div>

                        <div class="col-md-6">
                            <a href="${pageContext.request.contextPath}/admin/membres?action=ajouter"
                               class="quick-link">
                                <span class="ql-icon">➕</span>
                                <div>
                                    <div class="ql-text">Ajouter un membre</div>
                                    <small class="text-muted">
                                        Nouveau membre
                                    </small>
                                </div>
                            </a>
                        </div>

                        <div class="col-md-6">
                            <a href="${pageContext.request.contextPath}/admin/cotisations"
                               class="quick-link">
                                <span class="ql-icon">💰</span>
                                <div>
                                    <div class="ql-text">Cotisations</div>
                                    <small class="text-muted">
                                        ${nbAJour} payées ce mois
                                    </small>
                                </div>
                            </a>
                        </div>

                        <div class="col-md-6">
                            <a href="${pageContext.request.contextPath}/admin/cotisations?action=ajouter"
                               class="quick-link">
                                <span class="ql-icon">📝</span>
                                <div>
                                    <div class="ql-text">Enregistrer paiement</div>
                                    <small class="text-muted">
                                        Nouvelle cotisation
                                    </small>
                                </div>
                            </a>
                        </div>

                        <div class="col-md-6">
                            <a href="${pageContext.request.contextPath}/admin/cotisations?action=retard"
                               class="quick-link">
                                <span class="ql-icon">⏰</span>
                                <div>
                                    <div class="ql-text">Membres en retard</div>
                                    <small class="text-danger fw-semibold">
                                        ${nbEnRetard} en retard
                                    </small>
                                </div>
                            </a>
                        </div>

                        <div class="col-md-6">
                            <a href="${pageContext.request.contextPath}/admin/amendes"
                               class="quick-link">
                                <span class="ql-icon">⚠️</span>
                                <div>
                                    <div class="ql-text">Amendes</div>
                                    <small class="text-danger fw-semibold">
                                        ${nbAmendes} en attente
                                    </small>
                                </div>
                            </a>
                        </div>

                        <div class="col-md-6">
                            <a href="${pageContext.request.contextPath}/admin/amendes?action=generer"
                               class="quick-link">
                                <span class="ql-icon">⚡</span>
                                <div>
                                    <div class="ql-text">Générer amendes</div>
                                    <small class="text-muted">
                                        ${nomMois} ${annee}
                                    </small>
                                </div>
                            </a>
                        </div>

                        <div class="col-md-6">
                            <a href="${pageContext.request.contextPath}/logout"
                               class="quick-link">
                                <span class="ql-icon">🚪</span>
                                <div>
                                    <div class="ql-text">Déconnexion</div>
                                    <small class="text-muted">
                                        Quitter la session
                                    </small>
                                </div>
                            </a>
                        </div>

                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>