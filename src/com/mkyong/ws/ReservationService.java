package com.mkyong.ws;

import com.mkyong.exception.CustomException;
import com.mkyong.models.Flight;
import com.mkyong.models.Reservation;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.ArrayList;
import java.util.List;

//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface ReservationService {
	
	@WebMethod
	public Flight[] allFlights() ;

	@WebMethod
	String searchFlights(String from, String to, String date);

	@WebMethod
	String bookTicket(String flightId, String customerName);

	@WebMethod
	String confirmPurchase(String ticketId);

	@WebMethod
	Reservation checkReservation(String reservationId) throws Exception;

	@WebMethod
	byte[] generateTicketPDF(String ticketId);
	
}