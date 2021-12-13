
package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;

public class ParkingSpot {
    private int number;
    private ParkingType parkingType;
    private boolean isAvailable;
    /**
     * Classe permettant d'avoir une place de parking PARKINGSPOT
     */

    public ParkingSpot(final int number, final ParkingType parkingType,final boolean isAvailable) {
        this.number = number;
        this.parkingType = parkingType;
        this.isAvailable = isAvailable;
    }

    /**
     * Retoune l'ID attribué par la base données
     * @return l'identifiant
     */
    public int getId() {
        return number;
    }

    public void setId(int number) {
        this.number = number;
    }

    /**
     * Retourne le type de véhicule
     * @return un type de véhicule
     */
    public ParkingType getParkingType() {
        return parkingType;
    }

    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    /**
     * Booléen permettant de définir la disponibilité d'une place parking
     * @return true/ false
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "number=" + number +
                ", parkingType=" + parkingType +
                ", isAvailable=" + isAvailable +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return number;
    }
}
