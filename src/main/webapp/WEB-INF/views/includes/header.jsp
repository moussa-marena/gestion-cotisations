<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark navbar-sunuassos px-4">
    <a class="navbar-brand d-flex align-items-center gap-2"
       href="${pageContext.request.contextPath}/dashboard/admin">
        <img src="${pageContext.request.contextPath}/images/logo.png"
             alt="SunuAssos" class="sa-logo">
        SunuAssos
    </a>
    <c:if test="${not empty membreConnecte}">
        <div class="ms-auto d-flex align-items-center gap-3">
            <span class="text-white-50 small">
                Connecté : <strong class="text-white">
                    ${membreConnecte.nomComplet}
                </strong>
            </span>
            <a href="${pageContext.request.contextPath}/logout"
               class="btn btn-sm btn-outline-light">
                🚪 Déconnexion
            </a>
        </div>
    </c:if>
</nav>