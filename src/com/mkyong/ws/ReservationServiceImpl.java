package com.mkyong.ws;

import com.mkyong.database.Data;
import com.mkyong.models.Flight;
import com.mkyong.models.Reservation;
import com.mkyong.models.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

// Service Implementation Bean
@WebService(endpointInterface = "com.mkyong.ws.ReservationService")
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
    public String checkReservation(String reservationId) {
        for (Reservation reservation : Data.getReservations()) {
            if (reservation.getId().equals(reservationId)) {
                return "Reservation details: " + reservation.toString();
            }
        }
        return "Reservation not found.";
    }
}
