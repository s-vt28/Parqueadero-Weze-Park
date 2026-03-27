package service;

import model.Parking;

public class ParkingService {

    private Parking parking;

    public ParkingService(Parking parking) {
        this.parking = parking;
    }

    public String ingresarAuto(int placa) {
        for (int i = 0; i < 10; i++) {
            if (!parking.getOcupados()[i]) {
                parking.getPlacas()[i] = placa;
                parking.getOcupados()[i] = true;
                return "Auto ingresado con placa: " + placa;
            }
        }
        return "Parqueadero lleno";
    }

    // 🔥 AQUÍ PUEDES PEGARLO (debajo de este método por ejemplo)
    public String ingresarAutoEnPosicion(int placa, int posicion) {
        if (!parking.getOcupados()[posicion]) {
            parking.getPlacas()[posicion] = placa;
            parking.getOcupados()[posicion] = true;
            return "Auto ingresado en el puesto " + (posicion + 1);
        }
        return "El puesto ya está ocupado";
    }

    public String salidaAuto(int placa) {
        for (int i = 0; i < 10; i++) {
            if (parking.getOcupados()[i] && parking.getPlacas()[i] == placa) {
                parking.getOcupados()[i] = false;
                parking.incrementarSalida();
                return "Auto con placa " + placa + " salió";
            }
        }
        return "Placa no encontrada";
    }

    public void registrarPersona() {
        parking.agregarPersona();
    }

    public int getPersonas() {
        return parking.getPersonasCine();
    }

    public int getSalidas() {
        return parking.getTotalSalidas();
    }

    public Parking getParking() {
        return parking;
    }
}
