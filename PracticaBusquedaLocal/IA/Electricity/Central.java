package IA.Electricity;

import java.util.ArrayList;

public class Central {
    public static final int CENTRALA = 0;
    public static final int CENTRALB = 1;
    public static final int CENTRALC = 2;
    private int id;
    private int Tipo;
    private double Produccion;
    private int CoordX;
    private int CoordY;

    public Central(int type, double production, int coordX, int coordY, int id) {
        this.Tipo = type;
        this.Produccion = production;
        this.CoordX = coordX;
        this.CoordY = coordY;
        this.id = id;
    }

    public Central(Central central){
        this.Tipo = central.getTipo();
        this.Produccion = central.getProduccion();
        this.CoordX = central.getCoordX();
        this.CoordY = central.getCoordY();
        //Servings are assigned latter
    }

    public int getCoordX() {
        return this.CoordX;
    }

    public void setCoordX(int var1) {
        this.CoordX = var1;
    }


    public int getCoordY() {
        return this.CoordY;
    }

    public void setCoordY(int var1) {
        this.CoordY = var1;
    }

    public int getTipo() {
        return this.Tipo;
    }

    public void setTipo(int var1) {
        this.Tipo = var1;
    }

    public double getProduccion() {
        return this.Produccion;
    }

    public void setProduccion(double var1) {
        this.Produccion = var1;
    }

    public int getId() {
        return id;
    }

    public void print() {
        System.out.println("Coordenadas: ("+ getCoordX()+" "+getCoordY()+") ");
        System.out.println("Capacidad max: "+getProduccion());
    }
}