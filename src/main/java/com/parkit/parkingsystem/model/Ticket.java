
package com.parkit.parkingsystem.model;

import java.time.LocalDateTime;


public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private LocalDateTime inTime;
    private LocalDateTime outTime;
    /**
     * Classe permettant de construire un Ticket.
     * Getter et Setter d'un ticket.
     */


    /**
     * retourne l'ID de l'utilisateur
     * @return l'identifiant de l'utilisateur
     */
    public final int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * retourne la place de parking disponible
     * @return ParkingSpot
     */
    public final ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    /**
     * Met a jour la disponibilité de la place de parking
     * @param parkingSpot
     * @see ParkingSpot
     */
    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    /**
     * Retourne la plaque d'immatriculation du véhicule
     * @return la plaque d'immatricultion sous forme de chaine de caractères
     */
    public final String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    /**
     * Met a jour la plaque d'immatriculation du véhicule
     * @param vehicleRegNumber
     */
    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    /**
     * retourne le prix à payer par l'utilisateur
     * @return le prix
     */
    public final double getPrice() {
        return price;
    }

    /**
     * met a jour la prix a payer par l'utilisateur
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;

    }

    /**
     * retourne l'heure d'entrée du véhicule dans le parking
     * @return l'heure d'entrée
     */
    public final LocalDateTime getInTime() {
       return inTime;
    }

    /**
     * Met a jour l'heure d'entrée du véhicule dans le parking
     * @param inTime
     */
    public void setInTime(LocalDateTime inTime) {
      this.inTime = inTime;
    }

    /**
     * définit l'heure de sortie du véhicule dans le parking
     * @return l'heure de sortie
     */
    public final LocalDateTime getOutTime() {
        return outTime;
    }

    /**
     * Met a jour l'heure de sortie du parking
     * @param outTime
     */
    public void setOutTime(LocalDateTime outTime) {
       this.outTime = outTime;
    }


}
