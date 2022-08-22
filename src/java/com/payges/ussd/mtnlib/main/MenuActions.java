/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.util.UssdConstants;
import com.payges.ussd.mtnlib.waec.dto.VendStatusResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author ptrack
 */
public enum MenuActions {
    EXIT {
        @Override
        public String execute(UssdSession session, String request) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    },
    NEW_REQUEST {
        @Override
        public String execute(UssdSession session, String request) {
            Logger.getLogger(this.getClass()).info("NEW_REQUEST called");
            return UssdConstants.CONTINUE + UssdConstants.MESSAGES.getProperty(UssdConstants.MAIN_MENU);
        }

    },
    BUY_PIN {
        @Override
        public String execute(UssdSession session, String request) {
            Logger.getLogger(this.getClass()).info("BUY_PIN action called");
            final String response;
            //final MiscFunctions miscFunction = new MiscFunctions();
            switch (request) {
                case "1":
                    Logger.getLogger(this.getClass()).info("user chose to confirm transaction after disclaimer. Going to initiate debit");
                    response = new MiscFunctions().initiateDebit(session);
                    break;
                case "2":
                    Logger.getLogger(this.getClass()).info("user chose to cancel request after disclaimer. Show EXIT message.");
                    response = UssdConstants.END + UssdConstants.MESSAGES.getProperty(UssdConstants.EXIT);
                    break;
                default:
                    response = UssdConstants.END + UssdConstants.MESSAGES.getProperty(UssdConstants.INVALID_INPUT);
            }
            UssdSession.sessions.remove(session.getMsisdn());
            return response;
        }

    },
    MAIN_MENU {
        @Override
        public String execute(UssdSession session, String request) {
            Logger.getLogger(this.getClass()).info("MAIN_MENU action called");
            final String response,msg;
            final MiscFunctions miscFunction = new MiscFunctions();
            switch (request) {
                case "1":
                case "2":
                case "3":
                    VendStatusResponse resp_;
                    if("1".equals(request)){
                        //ensure that mini resitter service is not closed
                        if(isMiniRegistrationClosed(session.getMsisdn())){
                            response = UssdConstants.END + UssdConstants.MESSAGES.getProperty(UssdConstants.SERVICE_CLOSED_MESSAGE);
                            break;
                        }
                        session.setPintype("MINI-REGISTRATION");
                        msg=UssdConstants.MESSAGES.getProperty(UssdConstants.DISCLAIMER_RESITTER);
                        resp_ = WAECUtil.getWAECServiceStatus("01B");
                    }
                    else if("2".equals(request)){
                        //ensure that resitter service is not closed
                        if("YES".equalsIgnoreCase(UssdConstants.MESSAGES.getProperty(UssdConstants.IS_RESULT_CHECKER_CLOSED))){
                            response = UssdConstants.END + UssdConstants.MESSAGES.getProperty(UssdConstants.SERVICE_CLOSED_MESSAGE);
                            break;
                        }
                        session.setPintype("REGISTRATION");
                        msg=UssdConstants.MESSAGES.getProperty(UssdConstants.DISCLAIMER_RESITTER);
                        resp_ = WAECUtil.getWAECServiceStatus("01");
                    }
                    else{
                        //ensure that result checker service is not closed
                        if("YES".equalsIgnoreCase(UssdConstants.MESSAGES.getProperty(UssdConstants.IS_RESULT_CHECKER_CLOSED))){
                            response = UssdConstants.END + UssdConstants.MESSAGES.getProperty(UssdConstants.SERVICE_CLOSED_MESSAGE);
                            break;
                        }
                        session.setPintype("RESULT_CHECKER");
                        msg=UssdConstants.MESSAGES.getProperty(UssdConstants.DISCLAIMER_RESULT_CHECKER);
                        resp_ = WAECUtil.getWAECServiceStatus("03");
                    }
                    Logger.getLogger(this.getClass()).info("going to check waec service status");
                     
                    if (null != resp_) {
                        session.setVendstatus(resp_);
                        session.setSelectedCurrency("LRD");
                        session.setSelectedOperation("BUY_PIN");
                        UssdSession.sessions.put(session.getMsisdn(), session);
                        response = UssdConstants.CONTINUE + msg;
                    } else {
                        response = UssdConstants.END + UssdConstants.MESSAGES.getProperty(UssdConstants.NO_SERIALS_MESSAGE);
                    }
//                    }else if(miscFunction.isAnySerialUnused(session.getMsisdn())){
//                        session.setSelectedCurrency("LRD");
//                        //response = new MiscFunctions().initiateDebit(session);
//                        session.setSelectedOperation("BUY_PIN");
//                        UssdSession.sessions.put(session.getMsisdn(), session);
//                        response = UssdConstants.CONTINUE+UssdConstants.MESSAGES.getProperty(UssdConstants.DISCLAIMER);
//                    }else{
//                        response = UssdConstants.END+UssdConstants.MESSAGES.getProperty(UssdConstants.NO_SERIALS_MESSAGE);
//                    }

                    break;
//                case "2":
//                    session.setSelectedCurrency("USD");
//                    response = new MiscFunctions().initiateDebit(session);
//                    break;
                case "4":
                    response = miscFunction.resendLastPin(session);
                    break;
                default:
                    response = UssdConstants.END + UssdConstants.MESSAGES.getProperty(UssdConstants.INVALID_INPUT);
            }
            if (response.startsWith(UssdConstants.END)) {
                Logger.getLogger(this.getClass()).info("remove session inside MAIN_MENU as final message has been sent");
                UssdSession.sessions.remove(session.getMsisdn());
            }
            return response;
            //return UssdConstants.CONTINUE+UssdConstants.MESSAGES.getProperty(UssdConstants.MAIN_MENU);
        }

        private boolean isMiniRegistrationClosed(String msisdn) {
            if("YES".equalsIgnoreCase(UssdConstants.MESSAGES.getProperty(UssdConstants.IS_MINI_RESULT_CHECKER_CLOSED))){
                return true;
            }
            if(!msisdn.matches("231888921776|231881439175|231886501444|231886501416|231886501416|231880501593|231886501434")){
                return true;
            }
            return false;
        }

    };

    public abstract String execute(UssdSession session, String request);

    public static MenuActions getMenuAction(String menuName) {
        if (null == menuName) {
            return null;
        }
        for (final MenuActions menuoperation : MenuActions.values()) {
            if (menuoperation.toString().equals(menuName)) {
                return menuoperation;
            }
        }
        return null;
    }
}
