package com.parkit.parkingsystem.integration.DAOTest;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketDAOTestIT {
    private static TicketDAO ticketDAO;
    private static ParkingSpotDAO parkingSpotDAO;
    private static final String VEHICULEREGNUMBER  = "ABCDEF";

    @Mock
    private static InputReaderUtil inputReaderUtil;
    private static DataBasePrepareService dataBasePrepareService;


    private Ticket getTicket(ParkingType parkingType){
        Ticket ticket = new Ticket();
        int parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
        ParkingSpot parkingSpot = new ParkingSpot(parkingNumber,parkingType,true);
        ticket.setVehicleRegNumber(VEHICULEREGNUMBER);
        ticket.setParkingSpot(parkingSpot);
        ticket.setInTime(LocalDateTime.now());
        return  ticket;
    }

    @BeforeAll
    public static  void setUp() throws SQLException, ClassNotFoundException {
        DataBaseConfig dataBaseConfig = mock(DataBaseConfig.class);
        DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
        Connection connection = dataBaseTestConfig.getConnection();
        when(dataBaseConfig.getConnection()).thenReturn(connection);
        parkingSpotDAO = new ParkingSpotDAO(dataBaseConfig);
        ticketDAO = new TicketDAO(dataBaseConfig);
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        dataBasePrepareService.clearDataBaseEntries();
    }


    @Test
    void getTicket(){
        Ticket newTicket = ticketDAO.getTicket(VEHICULEREGNUMBER);
        assertNull(newTicket);
        saveTicket();
        Ticket gettedTicket = ticketDAO.getTicket(VEHICULEREGNUMBER);
        assertNotNull(gettedTicket);

        assertEquals(VEHICULEREGNUMBER,gettedTicket.getVehicleRegNumber());


    }

    @Test
    void saveTicket()  {
        dataBasePrepareService.clearDataBaseEntries();
        Ticket unexistingTicket = ticketDAO.getTicket(VEHICULEREGNUMBER);
        assertNull(unexistingTicket);
        Ticket ticketToSave = getTicket(ParkingType.CAR);

        boolean result = ticketDAO.saveTicket(ticketToSave);
        assertTrue(result);

        Ticket savedTicket = ticketDAO.getTicket(VEHICULEREGNUMBER);
        assertNotNull(savedTicket);
        assertEquals(VEHICULEREGNUMBER,savedTicket.getVehicleRegNumber());
        assertEquals(ticketToSave.getParkingSpot().getId(),savedTicket.getParkingSpot().getId());
        assertEquals(savedTicket.getInTime().toLocalDate(),ticketToSave.getInTime().toLocalDate());

    }

    @Test
    void updateTicket() {
        Ticket ticket = getTicket(ParkingType.CAR);
        boolean result = ticketDAO.saveTicket(ticket);
        assertTrue(result);

        Ticket ticketToUpdate = ticketDAO.getTicket(ticket.getVehicleRegNumber());
        assertNotNull(ticketToUpdate);
        ticketToUpdate.setOutTime(LocalDateTime.now().plusMinutes(30));
        boolean result2 = ticketDAO.updateTicket(ticketToUpdate);
        assertTrue(result2);

        Ticket updatedTicket = ticketDAO.getTicket(ticketToUpdate.getVehicleRegNumber());
        assertNull(updatedTicket,"getTicket ne retourne qu'un ticket qui a un OUT-TIME non défini.");

    }

    @Test
    void countInsideVehicle() {

        int countVehicle = ticketDAO.countInsideVehicle(VEHICULEREGNUMBER,ParkingType.CAR);
        assertEquals(0,0);
        Ticket vehicle = getTicket(ParkingType.CAR);
        boolean result = ticketDAO.saveTicket(vehicle);
        assertTrue(result);
        int countedVehicle = ticketDAO.countInsideVehicle(VEHICULEREGNUMBER,ParkingType.CAR);
        assertEquals(1,1);


        assertNotEquals(countVehicle,countedVehicle);


    }

    @Test
    void verifyExistingVehicle() {

        boolean unexistionVehicle = ticketDAO.verifyExistingVehicle(VEHICULEREGNUMBER,ParkingType.CAR);
        assertFalse(unexistionVehicle);

        Ticket vehicle = getTicket(ParkingType.CAR);
        boolean result = ticketDAO.saveTicket(vehicle);
        assertTrue(result);

        boolean existingVehicle = ticketDAO.verifyExistingVehicle(VEHICULEREGNUMBER,ParkingType.CAR);
        assertTrue(existingVehicle);

    }
}