package main;

import model.Parking;
import service.ParkingService;
import ui.MainFrame;

public class Main {
    public static void main(String[] args) {
        Parking parking = new Parking();
        ParkingService service = new ParkingService(parking);

        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame(service).setVisible(true);
        });
    }
}
