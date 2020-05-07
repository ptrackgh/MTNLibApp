/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;

/**
 *
 * @author ptrack
 */
@WebServlet(name = "DebitCallbackProcessor", urlPatterns = {"/debitcallback"})
public class DebitCallbackProcessor extends HttpServlet {

    Logger logger = Logger.getLogger(getClass());
    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    @PersistenceContext(unitName = "MTNLibAppPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //final String req = IOUtils.toString(request.getInputStream());
            PaymentCompletedXMLParser xml = new PaymentCompletedXMLParser();
            saxParserFactory.newSAXParser().parse(request.getInputStream(), xml);
            Runnable runnable = new DebitCompletedProcessor(xml.getTrace(),xml.getTransid(),xml.getExternalid(),xml.getStatus(),xml.getStatusdesc());
            MiscFunctions.threadsExecutor.execute(runnable);
            out.println(response);
        } catch (Exception ex) {
            logger.error("Exception thrown. Reason: " + ex.getMessage());
        }
    }

    final String response = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
            + "<soapenv:Body><requestPaymentCompletedResponse xmlns=\"http://www.csapi.org/schema/momopayment/local/v1_0\">"
            + "<result><resultCode xmlns=\"\">00000000</resultCode><resultDescription xmlns=\"\">success</resultDescription>"
            + "</result><extensionInfo><item xmlns=\"\"><key>result</key><value>success</value></item></extensionInfo></requestPaymentCompletedResponse>"
            + "</soapenv:Body></soapenv:Envelope>";

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            logger.error("exception caught"+ e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
