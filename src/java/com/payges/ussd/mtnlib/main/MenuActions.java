/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import com.payges.ussd.mtnlib.util.UssdConstants;
import org.apache.log4j.Logger;

/**
 *
 * @author ptrack
 */
public enum MenuActions {    
    EXIT{
        @Override
        public String execute(UssdSession session, String request) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    },
    NEW_REQUEST{
        @Override
        public String execute(UssdSession session, String request) {
            Logger.getLogger(this.getClass()).info("NEW_REQUEST called");
            return UssdConstants.CONTINUE+UssdConstants.MESSAGES.getProperty(UssdConstants.MAIN_MENU);
        }
        
    },
    MAIN_MENU{
        @Override
        public String execute(UssdSession session, String request) {
            Logger.getLogger(this.getClass()).info("MAIN_MENU action called");
            final String response;
            switch (request) {
                case "1":
                    session.setSelectedCurrency("LRD");
                    response = new MiscFunctions().initiateDebit(session);
                    break;
                case "2":
                    session.setSelectedCurrency("USD");
                    response = new MiscFunctions().initiateDebit(session);
                    break;
                case "3":
                    response = new MiscFunctions().resendLastPin(session);
                    break;
                default:
                    response = UssdConstants.END+UssdConstants.MESSAGES.getProperty(UssdConstants.INVALID_INPUT);
            }
            UssdSession.sessions.remove(session.getMsisdn());
            return response;
            //return UssdConstants.CONTINUE+UssdConstants.MESSAGES.getProperty(UssdConstants.MAIN_MENU);
        }
        
    };
    public abstract String execute(UssdSession session, String request);
    
    public static MenuActions getMenuAction(String menuName){
        if(null==menuName){
            return null;
        }
        for (final MenuActions menuoperation: MenuActions.values()) {
            if (menuoperation.toString().equals(menuName)) {
                return menuoperation;
            }
        }
        return null;
    }
}
