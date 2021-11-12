package com.parkit.parkingsystem;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.service.InteractiveShell;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class InteractiveShellTest {

    @Mock
    InputReaderUtil inputReaderUtil;
    @Mock
    InteractiveShell interactiveShell;
    @Mock

    boolean isMenuLoaded = false;


    /* public void loadMenuTest() throws Exception {
        assertFalse(isMenuLoaded);
       // PowerMockito.spy(InteractiveShell.class);
        //PowerMockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                isMenuLoaded = true;
                return null;
            }
        }).when(InteractiveShell.class, "loadMenu");
        InteractiveShell.loadInterface();
        assertTrue(isMenuLoaded);


    }*/


}