package ui;

import service.ParkingService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class MainFrame extends JFrame {

    private ParkingService service;
    private JPanel[] espacios = new JPanel[10];
    private JLabel[] labels = new JLabel[10];
    private JLabel[] imagenes = new JLabel[10];

    private final String URL_AUTO = "https://cdn-icons-png.flaticon.com/512/744/744465.png";
    private final String URL_LIBRE = "https://cdn-icons-png.flaticon.com/512/1828/1828665.png";

    public MainFrame(ParkingService service) {
        this.service = service;
        initComponents();
        actualizarVista();
    }

    private void initComponents() {
        setTitle("Weze Parking - Interactivo 🚗");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Botones
        JPanel panelTop = new JPanel();

        JButton btnPersonas = new JButton("Registrar Persona");
        JButton btnDatos = new JButton("Ver Estadísticas");

        panelTop.add(btnPersonas);
        panelTop.add(btnDatos);

        add(panelTop, BorderLayout.NORTH);

        // Panel principal (Paqueadero)
        JPanel panelParking = new JPanel(new GridLayout(2, 5, 15, 15));
        panelParking.setBorder(BorderFactory.createTitledBorder("Selecciona un puesto"));

        for (int i = 0; i < 10; i++) {
            final int index = i;

            espacios[i] = new JPanel(new BorderLayout());
            espacios[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            imagenes[i] = new JLabel();
            imagenes[i].setHorizontalAlignment(JLabel.CENTER);

            labels[i] = new JLabel("Libre", JLabel.CENTER);

            espacios[i].add(imagenes[i], BorderLayout.CENTER);
            espacios[i].add(labels[i], BorderLayout.SOUTH);

            // Clic Espacios
            espacios[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    manejarClick(index);
                }
            });

            panelParking.add(espacios[i]);
        }

        add(panelParking, BorderLayout.CENTER);

        // Boton Registrar 
        btnPersonas.addActionListener(e -> {
            service.registrarPersona();
            JOptionPane.showMessageDialog(this, "Persona registrada correctamente");
        });

        // Ver Datos
        btnDatos.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "ESTADÍSTICAS\n\n" +
                    "Personas en cine: " + service.getPersonas() +
                    "Autos que salieron: " + service.getSalidas());
        });
    }

    // Log. Click
    private void manejarClick(int index) {
        boolean ocupado = service.getParking().getOcupados()[index];

        if (!ocupado) {
            String input = JOptionPane.showInputDialog("Ingrese placa (3 dígitos):");
            if (input != null && input.matches("\\d{3}")) {
                int placa = Integer.parseInt(input);
                JOptionPane.showMessageDialog(this,
                        service.ingresarAutoEnPosicion(placa, index));
            }
        } else {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Desea retirar el auto con placa " +
                            service.getParking().getPlacas()[index] + "?");

            if (confirm == JOptionPane.YES_OPTION) {
                int placa = service.getParking().getPlacas()[index];
                JOptionPane.showMessageDialog(this,
                        service.salidaAuto(placa));
            }
        }

        actualizarVista();
    }

    // Imagenes
    private ImageIcon cargarImagen(String url) {
        try {
            ImageIcon icon = new ImageIcon(new URL(url));
            Image img = icon.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return null;
        }
    }

    // Opt. UI
    private void actualizarVista() {
        int[] placas = service.getParking().getPlacas();
        boolean[] ocupados = service.getParking().getOcupados();

        for (int i = 0; i < 10; i++) {
            if (ocupados[i]) {
                labels[i].setText("🚗 " + placas[i] + " (P" + (i + 1) + ")");
                espacios[i].setBackground(new Color(255, 150, 150));
                imagenes[i].setIcon(cargarImagen(URL_AUTO));
            } else {
                labels[i].setText("Puesto " + (i + 1) + " - Libre");
                espacios[i].setBackground(new Color(150, 255, 150));
                imagenes[i].setIcon(cargarImagen(URL_LIBRE));
            }
        }
    }
}
