package com.association.servlet.admin;

import com.association.dao.LoginHistoryDAO;
import com.association.model.LoginHistory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/historique")
public class HistoriqueServlet extends HttpServlet {

    private LoginHistoryDAO historyDAO;

    @Override
    public void init() {
        historyDAO = new LoginHistoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<LoginHistory> historiques = historyDAO.findAll();
        long nbSucces  = historyDAO.countSucces();
        long nbEchecs  = historyDAO.countEchecs();

        request.setAttribute("historiques", historiques);
        request.setAttribute("nbSucces",    nbSucces);
        request.setAttribute("nbEchecs",    nbEchecs);
        request.setAttribute("nbTotal",     nbSucces + nbEchecs);

        request.getRequestDispatcher(
            "/WEB-INF/views/admin/historique.jsp")
            .forward(request, response);
    }
}