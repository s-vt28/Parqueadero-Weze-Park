package ui;

import service.ParkingService;
import javax.swing.*;

public class MainFrame extends JFrame {

    private ParkingService service;

    public MainFrame(ParkingService service) {
        this.service = service;
        initComponents();
    }

    private void initComponents() {
        setTitle("Weze Parking 🚗");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnIngresar = new JButton("Ingresar Auto");
        btnIngresar.setBounds(100, 20, 200, 30);

        JButton btnPersona = new JButton("Registrar Persona");
        btnPersona.setBounds(100, 60, 200, 30);

        JButton btnSalida = new JButton("Salida Auto");
        btnSalida.setBounds(100, 100, 200, 30);

        JButton btnMostrar = new JButton("Mostrar Datos");
        btnMostrar.setBounds(100, 140, 200, 30);

        add(btnIngresar);
        add(btnPersona);
        add(btnSalida);
        add(btnMostrar);

        btnIngresar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Ingrese placa (3 dígitos):");
            int placa = Integer.parseInt(input);
            JOptionPane.showMessageDialog(this, service.ingresarAuto(placa));
        });

        btnPersona.addActionListener(e -> {
            service.registrarPersona();
            JOptionPane.showMessageDialog(this, "Persona registrada");
        });

        btnSalida.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Ingrese placa:");
            int placa = Integer.parseInt(input);
            JOptionPane.showMessageDialog(this, service.salidaAuto(placa));
        });

        btnMostrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Personas en cine: " + service.getPersonas() +
                "\nAutos salidos: " + service.getSalidas());
        });
    }
}
