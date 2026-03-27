package ui;

import service.ParkingService;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private ParkingService service;
    private JButton[] espacios = new JButton[10];

    public MainFrame(ParkingService service) {
        this.service = service;
        initComponents();
        actualizarVista();
    }

    private void initComponents() {
        setTitle("Weze Parking 🚗");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior o Botones
        JPanel panelTop = new JPanel();
        
        JButton btnIngresar = new JButton("Ingresar Auto");
        JButton btnSalida = new JButton("Salida Auto");
        JButton btnPersona = new JButton("Registrar Persona");
        JButton btnMostrar = new JButton("Ver Datos");

        panelTop.add(btnIngresar);
        panelTop.add(btnSalida);
        panelTop.add(btnPersona);
        panelTop.add(btnMostrar);

        add(panelTop, BorderLayout.NORTH);

        // Panel Central (Parqueadero)
        JPanel panelParking = new JPanel();
        panelParking.setLayout(new GridLayout(2, 5, 10, 10));
        panelParking.setBorder(BorderFactory.createTitledBorder("Parqueadero"));

        for (int i = 0; i < 10; i++) {
            espacios[i] = new JButton();
            espacios[i].setEnabled(false);
            panelParking.add(espacios[i]);
        }

        add(panelParking, BorderLayout.CENTER);

        // Eventos

        btnIngresar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Ingrese placa (3 dígitos):");
            if (input != null && input.matches("\\d{3}")) {
                int placa = Integer.parseInt(input);
                JOptionPane.showMessageDialog(this, service.ingresarAuto(placa));
                actualizarVista();
            } else {
                JOptionPane.showMessageDialog(this, "Placa inválida");
            }
        });

        btnSalida.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Ingrese placa:");
            if (input != null && input.matches("\\d{3}")) {
                int placa = Integer.parseInt(input);
                JOptionPane.showMessageDialog(this, service.salidaAuto(placa));
                actualizarVista();
            } else {
                JOptionPane.showMessageDialog(this, "Placa inválida");
            }
        });

        btnPersona.addActionListener(e -> {
            service.registrarPersona();
            JOptionPane.showMessageDialog(this, "Persona registrada");
        });

        btnMostrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Personas en cine: " + service.getPersonas() +
                "\nAutos que salieron: " + service.getSalidas());
        });
    }

    // Optimiza la vista general del parqueadero
    private void actualizarVista() {
        int[] placas = service.getParking().getPlacas();
        boolean[] ocupados = service.getParking().getOcupados();

        for (int i = 0; i < 10; i++) {
            if (ocupados[i]) {
                espacios[i].setText("🚗 " + placas[i]);
                espacios[i].setBackground(Color.RED);
            } else {
                espacios[i].setText("Libre");
                espacios[i].setBackground(Color.GREEN);
            }
        }
    }
}
