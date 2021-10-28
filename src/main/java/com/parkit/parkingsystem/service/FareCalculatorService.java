package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;


public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() != null) && (ticket.getOutTime().isBefore(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        int inHour = ticket.getInTime().getHour();
        int outHour = ticket.getOutTime().getHour();
        Duration dur = Duration.between(ticket.getInTime(), ticket.getOutTime());
        Long durtime = dur.getSeconds();
        System.out.println("durée du véhicule : " + durtime);
        //TODO: Some tests are failing here. Need to check if this logic is correct
        double duration = durtime/3600d;
        double price = 0;
        System.out.println("Durée du véhicule en heures : "+ duration);

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
             //   price = Math.round(duration * Fare.CAR_RATE_PER_HOUR*100.0)/100.0;
                price = duration * Fare.CAR_RATE_PER_HOUR;
                ticket.setPrice(price);
                break;
            }
            case BIKE: {
               // price = Math.round(duration * Fare.BIKE_RATE_PER_HOUR*100.0)/100.0;
                price = duration * Fare.BIKE_RATE_PER_HOUR;
                ticket.setPrice( price);
                break;
            }
            default: throw new IllegalArgumentException("Unknown Parking Type");
        }
    }


}