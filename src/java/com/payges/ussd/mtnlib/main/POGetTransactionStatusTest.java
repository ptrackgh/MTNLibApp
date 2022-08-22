/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.ericsson.financial.v1_1.restmodels.Gettransactionstatusresponse;
import com.payges.ussd.mtnlib.util.POUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author ptrack
 */
@WebServlet(name = "PODebitTest", urlPatterns = {"/test/gettransactionstatus"})
public class POGetTransactionStatusTest extends HttpServlet {

    Logger logger = Logger.getLogger(getClass());

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
            final String refNo = getField(request, "refno");
            Gettransactionstatusresponse rsp = POUtil.checkTransactionStatus(refNo);
            if(null !=rsp && null !=rsp.getStatus()){
                out.println(rsp.getStatus().value());
            }else{
                out.println("request failed");
            }
        } catch (Exception ex) {
            logger.error("Exception thrown. Reason: " + ex.getMessage());
            logger.error(Arrays.toString(ex.getStackTrace()).replaceAll(", ", "\n"));
        }
    }

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
    
    String getField(HttpServletRequest request, String param) throws Exception {
        if (request.getParameter(param) != null) {
            final String value = request.getParameter(param);
            //logger.info(param + " has value: " + value);
            return value;
        }
        throw new Exception("No value found for: " + param);
    }

}
