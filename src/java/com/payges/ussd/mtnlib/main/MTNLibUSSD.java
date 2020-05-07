/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.util.UssdConstants;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author ptrack
 */
public class MTNLibUSSD extends HttpServlet {

    Logger logger = Logger.getLogger(this.getClass());
    final String respMsg = "Service temporarily closed for WAEC Resitter until the next exam. Service will be re-opened for 2019 exams";
    
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
        //http://IP:PORT/URI?dialogueid=51873&amp;msisdndigits=23188465431&amp;msisdnno=23188465431&amp;shortcode=126&amp;ussdstring=*126#
        //above is no longer used. check below for the valid format of the request.
        ///MTNLibApp/paygesussd?MSISDN=231881439175&SERVICE_CODE=737&DIALOGUEID=32878&TEXT=517&USER_ID=5656566&PASSWORD=tytyty
        logger.info("new request received: " + request.getQueryString());
        try (PrintWriter out = response.getWriter()) {
            //final String respMessage = UssdConstants.MESSAGES.getProperty(UssdConstants.MAIN_MENU);
            try{
                final String dialogueid = getField(request, "DIALOGUEID");
                final String msisdn = getField(request, "MSISDN");
                final String shortcode = getField(request, "SERVICE_CODE");
                final String ussdstring = getField(request, "TEXT");
                final String ussdresponse;
                MDC.put("session", msisdn);
                logger.info("0|"+msisdn+"|"+dialogueid+"|"+shortcode+"|"+ussdstring);
//                block for temporarily closed message
//                logger.error("1|2,"+respMsg);
//                out.println(UssdConstants.END+respMsg);

///////////////////////////////BEGIN BLOCK FOR WHITELISTED NOs/////////////////////////////////////////////////////////////
//                if(!msisdn.matches("231888921776|231881439175|231886501444")){
//                    logger.info(msisdn+" not allowed to use the shortcode at this time");
//                    out.println(UssdConstants.END+"You are not allowed to use the service at this time. Please try again later.");
//                    MDC.remove("session");
//                    return;
//                }
///////////////////////////////END BLOCK FOR WHITELISTED NOs/////////////////////////////////////////////////////////////

                if(ussdstring.equals("517")){
                    UssdSession.sessions.remove(msisdn);
                    UssdSession usersession = new UssdSession();
                    usersession.setMsisdn(msisdn);
                    usersession.setMenuLevel(0);
                    usersession.setSelectedOperation("MAIN_MENU");
                    ussdresponse = UssdConstants.CONTINUE+UssdConstants.MESSAGES.getProperty(UssdConstants.MAIN_MENU);
                    UssdSession.sessions.put(msisdn, usersession);
                }else{
                    final UssdSession existingSession = UssdSession.sessions.get(msisdn);
                    if(null==existingSession){
                        Logger.getLogger(this.getClass()).error("invalid request received from: " + msisdn);
                        ussdresponse = UssdConstants.END+UssdConstants.MESSAGES.getProperty(UssdConstants.BAD_REQUEST);
                    }else{
                        Logger.getLogger(this.getClass()).info("continue request received from: " + msisdn);
                        existingSession.setMenuLevel(existingSession.getMenuLevel()+1);
                        MenuActions action = MenuActions.getMenuAction(existingSession.getSelectedOperation());
                        ussdresponse = action.execute(existingSession, ussdstring);
                    }
                }
                logger.info("1|"+msisdn+"|"+dialogueid + "|"+ ussdresponse.replace("\n", "|"));
                out.println(ussdresponse);
            } catch (Exception ex) {
                logger.error("Exception thrown. Reason: " + ex.getMessage());
                logger.error("1|2,Internal Error occured. Please try again.");
                out.println(UssdConstants.END+"Internal Error occured. Please try again.");
            }
            MDC.remove("session");
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
