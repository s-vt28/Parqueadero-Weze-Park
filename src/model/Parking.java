package model;

public class Parking {
    private int[] placas = new int[10];
    private boolean[] ocupados = new boolean[10];
    private int totalSalidas = 0;
    private int personasCine = 0;

    public int[] getPlacas() { return placas; }
    public boolean[] getOcupados() { return ocupados; }

    public int getTotalSalidas() { return totalSalidas; }
    public void incrementarSalida() { totalSalidas++; }

    public int getPersonasCine() { return personasCine; }
    public void agregarPersona() { personasCine++; }
}
