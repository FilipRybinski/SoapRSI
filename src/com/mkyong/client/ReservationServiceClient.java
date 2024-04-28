package com.mkyong.client;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.mkyong.models.Reservation;
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
        Reservation reservationDetails = reservationService.checkReservation("0");
        System.out.println(reservationDetails.toString());

        byte[] pdfContent = reservationService.generateTicketPDF("0");
        File tempFile = File.createTempFile("reservation_ticket", ".pdf");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(pdfContent);
        }
        Desktop.getDesktop().open(tempFile);
    }
}
