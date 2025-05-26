package com.example;

import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

public class HelloServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("init() called - Servlet is initialized");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("service() called - Handling a request");
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("destroy() called - Servlet is being destroyed");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        List<String> items = (List<String>) session.getAttribute("todoList");

        if (items == null) {
            items = new ArrayList<>();
            session.setAttribute("todoList", items);
        }

        // Forward to JSP page to render the list
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        List<String> items = (List<String>) session.getAttribute("todoList");

        if (items == null) {
            items = new ArrayList<>();
            session.setAttribute("todoList", items);
        }

        if ("add".equals(action)) {
            String newItem = req.getParameter("item");
            if (newItem != null && !newItem.trim().isEmpty()) {
                items.add(newItem.trim());
                System.out.println("Added item to session: " + newItem);
            } else {
                System.out.println("No item received to add");
            }
        } else if ("edit".equals(action)) {
            String indexStr = req.getParameter("index");
            String editedItem = req.getParameter("item");
            if (indexStr != null && editedItem != null && !editedItem.trim().isEmpty()) {
                int index = Integer.parseInt(indexStr);
                if (index >= 0 && index < items.size()) {
                    items.set(index, editedItem.trim());
                    System.out.println("Edited item at index " + index + ": " + editedItem);
                }
            }
        } else if ("delete".equals(action)) {
            String indexStr = req.getParameter("index");
            if (indexStr != null) {
                int index = Integer.parseInt(indexStr);
                if (index >= 0 && index < items.size()) {
                    String removed = items.remove(index);
                    System.out.println("Deleted item at index " + index + ": " + removed);
                }
            }
        }

        // Redirect to GET to show updated list (Post-Redirect-Get pattern)
        resp.sendRedirect(req.getContextPath() + "/todo");
    }
}
