
package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * CLasse Main du projet
 */

public class App {
    private static final Logger LOGGER = LogManager.getLogger("App");
    public static void main(final String args[]) {
        LOGGER.info("Initializing Parking System");
        InteractiveShell.loadInterface();
    }
}
