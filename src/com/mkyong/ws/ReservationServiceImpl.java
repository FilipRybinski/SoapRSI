package com.mkyong.ws;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.mkyong.database.Data;
import com.mkyong.models.Flight;
import com.mkyong.models.Reservation;
import com.mkyong.models.Ticket;
import com.sun.istack.internal.ByteArrayDataSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.MTOM;

// Service Implementation Bean
@MTOM
@WebService(endpointInterface = "com.mkyong.ws.ReservationService")
@HandlerChain(file="handler-chain.xml")
public class ReservationServiceImpl implements ReservationService {

    @Resource
    WebServiceContext wsctx;

    @Override
    public Flight[] allFlights() {
        List<Flight> flightsList = Data.getFlights();

        Flight[] flightsArray = new Flight[flightsList.size()];
        flightsArray = flightsList.toArray(flightsArray);

        return flightsArray;
    }

    @Override
    public String searchFlights(String from, String to, String date) {
        StringBuilder details = new StringBuilder();
        for (Flight flight : Data.getFlights()) {
            if (flight.getFromCity().equalsIgnoreCase(from) &&
                    flight.getToCity().equalsIgnoreCase(to) &&
                    flight.getDate().equals(date)) {
                details.append(flight.toString());
                details.append(System.lineSeparator());
            }
        }
        return details.toString();
    }

    @Override
    public String bookTicket(String flightId, String customerName) {
        Flight selectedFlight = Data.getFlights().stream()
                .filter(flight -> flight.getId().equals(flightId))
                .findFirst()
                .orElse(null);

        if (selectedFlight != null) {
            String ticketId = UUID.randomUUID().toString();
            boolean confirmed = false;

            Ticket newTicket = new Ticket(ticketId, selectedFlight, customerName, confirmed);
            Data.getTickets().add(newTicket);

            return ticketId;
        } else {
            return "Flight not found.";
        }
    }

    @Override
    public String confirmPurchase(String ticketId) {
        for (Ticket ticket : Data.getTickets()) {
            if (ticket.getId().equals(ticketId)) {
                ticket.setConfirmed(true);
                return "Purchase confirmed for ticket: " + ticketId;
            }
        }
        return "Ticket not found.";
    }

    @Override
    public Reservation checkReservation(String reservationId) throws Exception {
        for (Reservation reservation : Data.getReservations()) {
            if (reservation.getId().equals(reservationId)) {
                return  reservation;
            }
        }
        throw new Exception();
    }

    @Override
    public byte[] generateTicketPDF(String ticketId) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            generatePDF(outputStream, ticketId);
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void generatePDF(ByteArrayOutputStream outputStream, String ticketId) throws Exception {
        Ticket result=null;
        for (Ticket ticket : Data.getTickets()) {
            if (ticket.getId().equals(ticketId)) {
                result=ticket;
            }
        }
        if(result == null){
            throw new Exception();
        }
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph title = new Paragraph("Flight Ticket", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        // Add Ticket Details
        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        addEmptyLine(document, 1);
        document.add(new Paragraph("Ticket ID: " + result.getId(), infoFont));
        document.add(new Paragraph("Passenger Name: " + result.getCustomerName(), infoFont));
        document.add(new Paragraph("Departure Date: " + result.getFlight().getDate(), infoFont));
        document.add(new Paragraph("Origin: " + result.getFlight().getFromCity(), infoFont));
        document.add(new Paragraph("Destination: " + result.getFlight().getToCity(), infoFont));
        document.add(new Paragraph("Confrimed: " + (result.isConfirmed() ? "YES" : "NO"), infoFont));

        document.close();
    }

    private static void addEmptyLine(Document document, int number) throws DocumentException {
        for (int i = 0; i < number; i++) {
            document.add(Chunk.NEWLINE);
        }
    }
}
