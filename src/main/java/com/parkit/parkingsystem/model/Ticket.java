/**
 * Classe permettant de construire un Ticket.
 * Getter et Setter d'un ticket.
 */
package com.parkit.parkingsystem.model;

import java.time.LocalDateTime;


public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private LocalDateTime inTime;
    private LocalDateTime outTime;



    public final int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public final ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public final String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public final double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;

    }

    public final LocalDateTime getInTime() {
       return inTime;
    }

    public void setInTime(LocalDateTime inTime) {
      this.inTime = inTime;
    }

    public final LocalDateTime getOutTime() {
        return outTime;
    }

    public void setOutTime(LocalDateTime outTime) {
       this.outTime = outTime;
    }


}
