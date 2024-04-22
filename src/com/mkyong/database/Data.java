package com.mkyong.database;

import java.util.ArrayList;
import java.util.List;
import com.mkyong.models.Flight;
import com.mkyong.models.Ticket;
import com.mkyong.models.Reservation;

public class Data {
    private static final List<Flight> flights = new ArrayList<>();
    private static final List<Ticket> tickets = new ArrayList<>();
    private static final List<Reservation> reservations = new ArrayList<>();

    static {
        flights.add(new Flight("0", "New York", "London", "2024-04-21", "07:00"));
        flights.add(new Flight("1", "Tokyo", "San Francisco", "2024-04-22", "09:30"));
        flights.add(new Flight("2", "Sydney", "Los Angeles", "2024-04-23", "15:45"));


        tickets.add(new Ticket("0", flights.get(0), "John Doe", true));
        tickets.add(new Ticket("1", flights.get(1), "Jane Smith", false));


        reservations.add(new Reservation("0", tickets.get(0), "RES123"));
        reservations.add(new Reservation("1", tickets.get(1), "RES124"));
    }

    public static List<Flight> getFlights() {
        return flights;
    }

    public static List<Ticket> getTickets() {
        return tickets;
    }

    public static List<Reservation> getReservations() {
        return reservations;
    }
}
