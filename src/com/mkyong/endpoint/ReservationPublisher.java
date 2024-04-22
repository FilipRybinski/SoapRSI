package com.mkyong.endpoint;
 
import javax.xml.ws.Endpoint;
import com.mkyong.ws.ReservationServiceImpl;
 
//Endpoint publisher
public class ReservationPublisher {
 
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8888/ws/reservation", new ReservationServiceImpl());
		System.out.println("Service published at http://localhost:8888/ws/reservation");    }
 
}