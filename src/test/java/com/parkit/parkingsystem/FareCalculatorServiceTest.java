package com.parkit.parkingsystem;


import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FareCalculatorServiceTest {

    @Mock
    TicketDAO ticketDAO;
    FareCalculatorService fareCalculatorService;
    Ticket ticket;


    @BeforeEach
    private void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    @Test
    public void calculateFareCar(){
        // GIVEN
        //when(classUnderTest.calculateFare(Ticket,ticket)).thenReturn(ticket.setPrice(1.5));

        //WHEN
        LocalDateTime inTime = LocalDateTime.now().minusHours(1);
        LocalDateTime outTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);

        //THEN
       // verify(classUnderTest).calculateFare(ticket);
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
    }

    @Test
    public void calculateFareBike(){
        LocalDateTime inTime = LocalDateTime.now().minusHours(1);
        inTime.getHour();
        LocalDateTime outTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR);
    }

    @Test
    public void calculateFareUnknownType(){
        LocalDateTime inTime =LocalDateTime.now();
        inTime.getHour();
        LocalDateTime outTime = LocalDateTime.now();
        outTime.getHour();
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBikeWithFutureInTime(){
        LocalDateTime inTime = LocalDateTime.now().plusHours(1);
        LocalDateTime outTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

    @Test
    public void calculateFareBikeWithLessThanOneHourParkingTime(){
        LocalDateTime inTime = LocalDateTime.now().minusMinutes(45);
        //45 minutes parking time should give 3/4th parking fare
        LocalDateTime outTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);


        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }

    @Test
    public void calculateFareCarWithLessThanOneHourParkingTime(){
        LocalDateTime inTime = LocalDateTime.now().minusMinutes(45);//45 minutes parking time should give 3/4th parking fare
        LocalDateTime outTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        ticket.getPrice();
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (0.75 * Fare.CAR_RATE_PER_HOUR) ,ticket.getPrice() );
    }

    @Test
    public void calculateFareCarWithMoreThanADayParkingTime(){
        LocalDateTime inTime = LocalDateTime.now().minusDays(1);//24 hours parking time should give 24 * parking fare per hour
        LocalDateTime outTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (24 * Fare.CAR_RATE_PER_HOUR) , ticket.getPrice());
    }
    @Test
    public void calculateFareCarWithLessThanHalfHourParkingTime_ShouldReturnFree(){
        LocalDateTime inTime = LocalDateTime.now().minusMinutes(20);// time should be between 0 and 30 minutes

        LocalDateTime outTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);


        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.0 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice() );
    }
    @Test
    public void calculateFareBikeWithLessThanHalfHourParkingTime_ShouldReturnFree(){
        LocalDateTime inTime = LocalDateTime.now().minusMinutes(10);// time should be between 0 and 30 minutes

        LocalDateTime outTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);


        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.0 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice() );
    }
    @Test
    public void calculateFareRecurrentCar(){
        when(ticketDAO.countInsideVehicle(anyString(),any(ParkingType.class))).thenReturn(6);
        fareCalculatorService = new FareCalculatorService(ticketDAO);
        LocalDateTime inTime = LocalDateTime.now().minusHours(1);
        LocalDateTime outTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABC");

        fareCalculatorService.calculateFare(ticket);

        assertTrue(ticket.getPrice() < Fare.CAR_RATE_PER_HOUR);
        assertEquals(ticket.getPrice(),Fare.CAR_RATE_PER_HOUR - (0.05* Fare.CAR_RATE_PER_HOUR));
    }
    @Test
    public void calculateFareRecurrentBike(){
        when(ticketDAO.countInsideVehicle(anyString(),any(ParkingType.class))).thenReturn(6);
        fareCalculatorService = new FareCalculatorService(ticketDAO);
        LocalDateTime inTime = LocalDateTime.now().minusHours(1);
        LocalDateTime outTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABC");

        fareCalculatorService.calculateFare(ticket);

        assertTrue(ticket.getPrice() < Fare.BIKE_RATE_PER_HOUR);
        assertEquals(ticket.getPrice(),Fare.BIKE_RATE_PER_HOUR - (0.05* Fare.BIKE_RATE_PER_HOUR));

    }


}
