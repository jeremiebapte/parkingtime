
package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;
/**
 * Classe de calcul des prix pour ParkingSytem.
 * Cette classe prend en compte les promotions et offres du service.
 *
 */


public class FareCalculatorService  {

      private TicketDAO ticketDAO;

    public FareCalculatorService() {
        this.ticketDAO = new TicketDAO();
    }

    public FareCalculatorService( TicketDAO dao) {
        this.ticketDAO = dao;
    }

    /**
     * Cette méthode calcule la durée pendant laquelle le véhicule est resté stationné
     * @param ticket
     */
    public void calculateFare(final Ticket ticket) {
        if ((ticket.getOutTime() != null) && (ticket.getOutTime()
                .isBefore(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:"
                    + ticket.getOutTime().toString());
        }

        int countvehicle = ticketDAO.countInsideVehicle(
                ticket.getVehicleRegNumber(),
                ticket.getParkingSpot().getParkingType());
        Duration dur = Duration.between(ticket.getInTime(),
                ticket.getOutTime());
        Long durtime = dur.getSeconds();
        System.out.println("Durée du véhicule : " + durtime);

        final double time = 60d;
        double duration = durtime /  time;
        double price = 0;
        System.out.println("Durée du véhicule en heures : " + duration);

        switch (ticket.getParkingSpot().getParkingType()) {

            case CAR: {
               this.processCalculationForCar(duration, countvehicle, ticket);
                break;
            }

            case BIKE: {
                this.processeCalculationForBike(duration, countvehicle, ticket);
                break;
            }
            default: throw new IllegalArgumentException("Unknown Parking Type");
        }


    }

    /**
     * Cette methode permet de calculer le montant à régler lors de la sortie du parking pour une voiture
     * elle prend en compte le temps passé dans le parking, applique une réduction si il y a récurrence et
     * donne le montant à régler
     * @param duration
     * @param countvehicle
     * @param ticket
     */
    private void processCalculationForCar(
            final double duration, final int countvehicle,
            final Ticket ticket) {
        final int freeTime = 30; // 30 mins offertes
        double price = 0;

        if (duration <= freeTime) {
            System.out.println("Vous bénéficiez des 30 minutes gratuites !!");

        } else {
            final int hourToDivide = 60;
            price = duration
                    * Fare.CAR_RATE_PER_HOUR / hourToDivide;
            // client récurrent bénéficiant d'une réduction
            final int recurrentCustomer = 6;
            if (countvehicle >= recurrentCustomer) {
                // réduction pour client récurrent
                final double promotion = 0.05;
                price = price - (price * promotion);
            }


        }
        ticket.setPrice(price);


    }
    /**
     * Cette methode permet de calculer le montant à régler lors de la sortie du parking pour une moto
     * elle prend en compte le temps passé dans le parking, applique une réduction si il y a récurrence et
     * donne le montant à régler
     * @param duration
     * @param countvehicle
     * @param ticket
     */
    private void processeCalculationForBike(
            final double duration, final int countvehicle,
            final Ticket ticket) {

        final int freeTime = 30; // 30 mins offertes
        double price = 0;

        if (duration <= freeTime) {
            System.out.println("Vous bénéficiez des 30 minutes gratuites !!");

        }   else {
            final int hourToDivide = 60;
            price = duration * Fare.BIKE_RATE_PER_HOUR / hourToDivide;
            // client récurrent bénéficiant d'une réduction
            final int recurrentCustomer = 6;
            if (countvehicle >= recurrentCustomer) {
                // réduction pour client récurrent
                final double promotion = 0.05;
                price = price - (price * promotion);
            }
        }
        ticket.setPrice(price);
    }

}


