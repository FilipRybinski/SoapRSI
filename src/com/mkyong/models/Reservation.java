package com.mkyong.models;

public class Reservation {
    private String id;
    private Ticket ticket;
    private String reservationCode;

    public Reservation() {
        // Default constructor
    }

    public Reservation(String id, Ticket ticket, String reservationCode) {
        this.id = id;
        this.ticket = ticket;
        this.reservationCode = reservationCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }
    @Override
    public String toString() {
        return "Reservation{" +
                "id='" + id + '\'' +
                ", ticket=" + (ticket == null ? "null" : ticket.toString()) +
                ", reservationCode='" + reservationCode + '\'' +
                '}';
    }
}