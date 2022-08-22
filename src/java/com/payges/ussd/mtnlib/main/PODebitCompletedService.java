/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.ericsson.legacy.restmodels.Debitcompletedrequest;
import com.payges.ussd.mtnlib.ericsson.legacy.restmodels.Debitcompletedresponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;

/**
 *
 * @author ptrack
 */
@WebServlet(name = "PODebitCompletedService", urlPatterns = {"/debitcompleted"})
public class PODebitCompletedService extends HttpServlet {

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
            JAXBContext jContext = JAXBContext.newInstance(Debitcompletedrequest.class);
            //creating the unmarshall object
            Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
            //calling the unmarshall method
            Debitcompletedrequest xml=(Debitcompletedrequest) unmarshallerObj.unmarshal(request.getInputStream());
            //getExternaltransactionid is our internal txID sent to PO
            Runnable runnable = new PODebitCompletedProcessor(xml.getExternaltransactionid(),""+xml.getTransactionid(),xml.getStatus());
            MiscFunctions.threadsExecutor.execute(runnable);
            final StringWriter sw = new StringWriter();
            JAXB.marshal(new Debitcompletedresponse(), sw);
            out.println(sw.toString());
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

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            logger.error("exception caught"+ e.getMessage());
            logger.error(Arrays.toString(e.getStackTrace()).replaceAll(", ", "\n"));
            throw new RuntimeException(e);
        }
    }

}
