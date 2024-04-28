package com.mkyong.handler;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class CustomHandler implements SOAPHandler<SOAPMessageContext> {
    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if(!isRequest){

            try{
                SOAPMessage soapMsg = context.getMessage();
                SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
                SOAPHeader soapHeader = soapEnv.getHeader();

                if (!isBetween10AMAnd8PM()){
                    generateSOAPErrMessage(soapMsg, "Unavailable service.");
                }
                //tracking
                soapMsg.writeTo(System.out);


            }catch(SOAPException e){
                System.err.println(e);
            }catch(IOException e){
                System.err.println(e);
            }

        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    private void generateSOAPErrMessage(SOAPMessage msg, String reason) {
        try {
            SOAPBody soapBody = msg.getSOAPPart().getEnvelope().getBody();
            SOAPFault soapFault = soapBody.addFault();
            soapFault.setFaultString(reason);
            throw new SOAPFaultException(soapFault);
        }
        catch(SOAPException e) { }
    }

    private static boolean isBetween10AMAnd8PM() {
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(10, 0); // 10:00 AM
        LocalTime endTime = LocalTime.of(20, 0);   // 8:00 PM

        return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
    }
}
