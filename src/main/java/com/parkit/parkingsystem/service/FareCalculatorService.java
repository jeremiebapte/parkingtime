package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;


public class FareCalculatorService  {

      private TicketDAO ticketDAO;

    public FareCalculatorService() {
        this.ticketDAO = new TicketDAO();
    }

    public FareCalculatorService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() != null) && (ticket.getOutTime().isBefore(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        int countvehicle = ticketDAO.countInsideVehicle(ticket.getVehicleRegNumber(),ticket.getParkingSpot().getParkingType());
        Duration dur = Duration.between(ticket.getInTime(), ticket.getOutTime());
        Long durtime = dur.getSeconds();
        System.out.println("Durée du véhicule : " + durtime);
        //TODO: Some tests are failing here. Need to check if this logic is correct
        double duration = durtime/60d;
        double price = 0;
        System.out.println("Durée du véhicule en heures : "+ duration);

        switch (ticket.getParkingSpot().getParkingType()){

            case CAR: { if ( duration <= 30 ){
                System.out.println("Vous bénéficiez des 30 minutes gratuites !!");
                price = 0;
                ticket.setPrice(price);

            } else {
                price = duration * Fare.CAR_RATE_PER_HOUR/60;
                if (countvehicle >= 6){
                    price = price - (price * 0.05);
                }
                ticket.setPrice(price);


            }

                break;
            }
            case BIKE: { if ( duration <= 30 ) {
                System.out.println("Vous bénéficiez des 30 minutes gratuites !!");
                price = 0;
                ticket.setPrice(price);

            }   else {
                price = duration * Fare.BIKE_RATE_PER_HOUR / 60;
                if ( countvehicle >= 6){
                    price = price - (price * 0.05);
                }
                ticket.setPrice(price);
            }
                break;
            }
            default: throw new IllegalArgumentException("Unknown Parking Type");
        }


    }

}


