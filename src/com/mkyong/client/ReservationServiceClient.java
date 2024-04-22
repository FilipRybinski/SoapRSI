package com.mkyong.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.mkyong.ws.ReservationService;

public class ReservationServiceClient {

    private static final String WS_URL = "http://localhost:8888/ws/reservation?wsdl";

    public static void main(String[] args) throws Exception {
        URL url = new URL(WS_URL);
        QName qname = new QName("http://ws.mkyong.com/", "ReservationServiceImplService");

        Service service = Service.create(url, qname);
        ReservationService reservationService = service.getPort(ReservationService.class);

        System.out.println("Szukanie lotów:");
        System.out.println(reservationService.searchFlights("New York", "London", "2024-04-21"));

        System.out.println("Rezerwacja biletu:");
        String ticketId = reservationService.bookTicket("0", "Jan Kowalski");
        System.out.println("ID biletu: " + ticketId);

        System.out.println("Potwierdzenie zakupu:");
        String confirmation = reservationService.confirmPurchase(ticketId);
        System.out.println(confirmation);

        System.out.println("Sprawdzenie rezerwacji:");
        String reservationDetails = reservationService.checkReservation("0");
        System.out.println(reservationDetails);
    }
}
