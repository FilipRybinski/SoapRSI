package com.mkyong.models;

public class Ticket {
    private String id;
    private Flight flight;
    private String customerName;
    private boolean confirmed;
    public Ticket(String id, Flight flight, String customerName, boolean confirmed) {
        this.id = id;
        this.flight = flight;
        this.customerName = customerName;
        this.confirmed = confirmed;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", flight=" + (flight == null ? "null" : flight.toString()) +
                ", customerName='" + customerName + '\'' +
                ", confirmed='" + confirmed + '\'' +
                '}';
    }
}
