/**
 * Classe permettan de communiquer avec la base de données afin d'avoir une place de parking dispo
 * et de mettre a jour sa dsponibilité.
 */
package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkingSpotDAO {
    private static final Logger LOGGER = LogManager.getLogger("ParkingSpotDAO");

    public DataBaseConfig dataBaseConfig = new DataBaseConfig();

    public ParkingSpotDAO(DataBaseConfig dataBaseConfig) {
        this.dataBaseConfig = dataBaseConfig;
    }

    public ParkingSpotDAO() {
    }

    public int getNextAvailableSlot(ParkingType parkingType){
        Connection con = null;
        int result = -1;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
            ps.setString(1, parkingType.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);;
            }
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
        } catch (Exception ex) {
           LOGGER.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeConnection(con);
        }
        return result;
    }
    //TODO créer methode getParking(). mettre à jour sa dispo, update et récupérer et s'assurer que la dispo est mise à jour dans la DB.

    public boolean updateParking (final ParkingSpot parkingSpot) {
        //update the availability for that parking slot
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT);
            ps.setBoolean(1, parkingSpot.isAvailable());
            ps.setInt(2, parkingSpot.getId());
            int updateRowCount = ps.executeUpdate();
            dataBaseConfig.closePreparedStatement(ps);
            return (updateRowCount == 1);
        } catch (Exception ex) {
            LOGGER.error("Error updating parking info", ex);
            return false;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                    LOGGER.info("Closing Prepared Statement");
                } catch (SQLException e) {
                    LOGGER.error("Error while closing prepared statement", e);
                }
            }
            dataBaseConfig.closeConnection(con);
        }
    }

}
