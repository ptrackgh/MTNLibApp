/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payges.ussd.mtnlib.main;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PaymentCompletedXMLParser extends DefaultHandler {

    Logger logger;
    private boolean traceid;
    private boolean processingnumber;
    private boolean momoid;
    private boolean statuscode;
    private boolean sdesc;

    private String trace;
    private String transid;
    private String externalid;
    private String status;
    private String statusdesc;

    public PaymentCompletedXMLParser() {
        this.logger = Logger.getLogger(this.getClass());
        this.sdesc = false;
        this.statuscode = false;
        this.momoid = false;
        this.processingnumber = false;
        this.traceid = false;

    }

    @Override
    public void startDocument() throws SAXException {
        logger.info("starting to read xml string.");
    }

    @Override
    public void endDocument() throws SAXException {
        logger.info("paymentcompleted|" + getTransid() + "|" + getExternalid() + "|" + getTrace() + "|" + getStatus() + "|" + getStatusdesc() + "|");
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        //logger.info("startElement called for "+qName);
        switch (qName) {
            case "ns2:traceUniqueID":
                traceid = true;
                break;
            case "ns3:ProcessingNumber":
                processingnumber = true;
                break;
            case "ns3:MOMTransactionID":
                momoid = true;
                break;
            case "ns3:StatusCode":
                statuscode = true;
                break;
            case "ns3:StatusDesc":
                sdesc = true;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (traceid) {
            trace = new String(ch, start, length);
        } else if (processingnumber) {
            transid = new String(ch, start, length);
        } else if (momoid) {
            externalid = new String(ch, start, length);
        } else if (statuscode) {
            status = new String(ch, start, length);
        } else if (sdesc) {
            statusdesc = new String(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "ns2:traceUniqueID":
                traceid = false;
                break;
            case "ns3:ProcessingNumber":
                processingnumber = false;
                break;
            case "ns3:MOMTransactionID":
                momoid = false;
                break;
            case "ns3:StatusCode":
                statuscode = false;
                break;
            case "ns3:StatusDesc":
                sdesc = false;
                break;
        }
    }

    /**
     * @return the trace
     */
    public String getTrace() {
        return trace;
    }

    /**
     * @return the transid
     */
    public String getTransid() {
        return transid;
    }

    /**
     * @return the externalid
     */
    public String getExternalid() {
        return externalid;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the statusdesc
     */
    public String getStatusdesc() {
        return statusdesc;
    }

}
