package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ParkingSpotDAOTestIT {
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    private static DataBasePrepareService dataBasePrepareService;


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
    void getNextAvailableSlotForCar() throws Exception {
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        when(inputReaderUtil.readSelection()).thenReturn(1);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        int parkingNumber = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
        parkingService.processIncomingVehicle();
        int nextParkingNumber = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);

        assertNotEquals(parkingNumber,nextParkingNumber);
    }
    @Test
    void getNextAvailableSlotForCar2() throws Exception {
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        when(inputReaderUtil.readSelection()).thenReturn(2);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        int parkingNumber = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
        parkingService.processIncomingVehicle();
        int nextParkingNumber = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);

        assertEquals(parkingNumber,nextParkingNumber);
    }

    @Test
    void updateParking() {
        dataBasePrepareService.clearDataBaseEntries();
        when(inputReaderUtil.readSelection()).thenReturn(1);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();
        parkingSpot.setAvailable(false);
        boolean result = parkingSpotDAO.updateParking(parkingSpot);

        assertTrue(result);
    }
}