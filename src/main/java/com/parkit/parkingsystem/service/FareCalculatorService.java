package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;


public class FareCalculatorService  {


    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() != null) && (ticket.getOutTime().isBefore(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        Duration dur = Duration.between(ticket.getInTime(), ticket.getOutTime());
        Long durtime = dur.getSeconds();
        System.out.println("durée du véhicule : " + durtime);
        //TODO: Some tests are failing here. Need to check if this logic is correct
        double duration = durtime/60d;
        double price = 0;
        System.out.println("Durée du véhicule en heures : "+ duration);

        switch (ticket.getParkingSpot().getParkingType()){

            case CAR: { if ( duration <= 30 ){
                System.out.println("Vous bénéficiez des 30 minutes gratuites !!");
                price = duration * 0;
                ticket.setPrice(price);

            } else if (duration >=30){
                price = duration * Fare.CAR_RATE_PER_HOUR/60;
                ticket.setPrice(price);
            }
                break;
            }
            case BIKE: { if ( duration <= 30 ) {
                System.out.println("Vous bénéficiez des 30 minutes gratuites !!");
                price = duration * 0;
                ticket.setPrice(price);

            }   else if (duration >=30) {
                price = duration * Fare.BIKE_RATE_PER_HOUR / 60;
                ticket.setPrice(price);
            }
                break;
            }
            default: throw new IllegalArgumentException("Unknown Parking Type");
        }


    }

}


