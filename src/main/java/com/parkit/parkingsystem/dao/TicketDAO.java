
package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
/**
 * Classe permettant de communiquer l'entrée d'un ticket en Base de données
 */

public class TicketDAO {
    private static final Logger LOGGER = LogManager.getLogger("TicketDAO");

    public DataBaseConfig dataBaseConfig = new DataBaseConfig();

    public TicketDAO(DataBaseConfig dataBaseConfig) {
        this.dataBaseConfig = dataBaseConfig;
    }

    public TicketDAO() {
        this.dataBaseConfig = new DataBaseConfig();
    }


    /**
     * Cette Méthode de type booléen permet d'enregistrer un ticket en base de données
     * @param ticket
     * @return true si toutes les conditions sont remplies
     */
    public boolean saveTicket(Ticket ticket) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.SAVE_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            //ps.setInt(1,ticket.getId());
            ps.setInt(1, ticket.getParkingSpot().getId());
            ps.setString(2, ticket.getVehicleRegNumber());
            ps.setDouble(3, ticket.getPrice());
            ps.setTimestamp(4, Timestamp.valueOf(ticket.getInTime()));
            ps.setTimestamp(5, (ticket.getOutTime() == null) ? null : (Timestamp.valueOf(ticket.getOutTime())));
            ps.execute();
            return true;

        } catch (RuntimeException e){
            LOGGER.error("Error fetching next available slot",e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Error fetching next available slot", e);
            return false;
        } finally {
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);

        }
    }

    /**
     * Cette méthode permet d'obtenir un ticket
     * @param vehicleRegNumber
     * @return un ticket avec toutes les infos nécessaires
     */
    public Ticket getTicket(String vehicleRegNumber) {
        Connection con = null;
        Ticket ticket = null;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBConstants.GET_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            ps.setString(1, vehicleRegNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ticket = new Ticket();
                ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(1), ParkingType.valueOf(rs.getString(6)), false);
                ticket.setParkingSpot(parkingSpot);
                ticket.setId(rs.getInt(2));
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(rs.getDouble(3));
                ticket.setInTime(rs.getTimestamp(4).toLocalDateTime());
                ticket.setOutTime(rs.getTimestamp(5) == null ? null : rs.getTimestamp(5).toLocalDateTime());
            }
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error fetching next available slot", e);
        } finally {
            dataBaseConfig.closeConnection(con);
            return ticket;
        }
    }

    /**
     * Cette méthode permet de mettre à jour un ticket
     * @param ticket
     * @return ticket mis a jour
     */
    public boolean updateTicket(Ticket ticket) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.UPDATE_TICKET);
            ps.setDouble(1, ticket.getPrice());
            ps.setTimestamp(2, Timestamp.valueOf(ticket.getOutTime()));
            ps.setInt(3, ticket.getId());
            ps.execute();
            return true;

        } catch (RuntimeException e){
           LOGGER.error("Error saving ticket info",e);
            return false;

        } catch (Exception e) {
            LOGGER.error("Error saving ticket info", e);
            return false;

        } finally {
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
    }

    /**
     * Cette méthode permet de compter le nombre d'utilisation du parking d'un client
     * afin de lui attribuer une réduction
     * @param vehicleRegNumber
     * @param vehicleType
     * @return un entier
     */
    public int countInsideVehicle(String vehicleRegNumber, ParkingType vehicleType) {
        Connection con = null;
        int entry = 0;
        try {
            con = this.dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBConstants.COUNT_VEHICLE_REG_NUMBER);
            ps.setString(1, vehicleRegNumber);
            ps.setString(2, vehicleType.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                entry = rs.getInt(1);

            }
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);

        } catch (RuntimeException e) {
           LOGGER.error("error count vehicle entry ",e);
        } catch(Exception e) {
            LOGGER.error("Error count vehicle entry", e);
        } finally {
            dataBaseConfig.closeConnection(con);
            return entry;
        }
    }

    /**
     * Cette métthode de type booléen permet de vérifier si un véhicule et deja présent dans le parking
     * @param vehicleRegNumber
     * @param vehicleType
     * @return
     */
    public boolean verifyExistingVehicle(String vehicleRegNumber, ParkingType vehicleType) {

        Connection con = null;
        int entry = 0;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBConstants.VERIFY_VEHICLE_REG_NUMBER);
            ps.setString(1, vehicleRegNumber);
            ps.setString(2, vehicleType.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                entry = rs.getInt(1);

            }
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
        }catch (RuntimeException e){
            LOGGER.error("error count vehicle entry ",e);

        } catch(Exception e) {
            LOGGER.error("Error verify vehicle", e);
        } finally {
            dataBaseConfig.closeConnection(con);
            return entry != 0;
        }
    }


}
