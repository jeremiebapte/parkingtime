package com.parkit.parkingsystem.integration.DAOTest;

import com.parkit.parkingsystem.config.DataBaseConfig;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketDAOTestIT {
    private static TicketDAO ticketDAO;
    private static ParkingSpotDAO parkingSpotDAO;

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
    void getTicket() throws Exception {
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        when(inputReaderUtil.readSelection()).thenReturn(1);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        Ticket ticketNumber = ticketDAO.getTicket("ABCDEF");
        ticketDAO.getTicket("ABCDEF");
        Ticket newTicketNumber = ticketDAO.getTicket("ABC");

        verify(ticketDAO,Mockito.times(1)).getTicket("ABCDEF");
        assertNotEquals(ticketNumber,newTicketNumber);

    }

    @Test
    void saveTicket() {
    }

    @Test
    void updateTicket() {
    }

    @Test
    void countInsideVehicle() {
    }

    @Test
    void verifyExistingVehicle() {
    }
}