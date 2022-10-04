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

    private ArrayList<Cliente> servingNotGuaranteed;
    private ArrayList<Cliente> servingGuaranteed;

    public Central(int type, double production, int coordX, int coordY, int id) {
        this.Tipo = type;
        this.Produccion = production;
        this.CoordX = coordX;
        this.CoordY = coordY;
        this.servingNotGuaranteed = new ArrayList<Cliente>();
        this.servingGuaranteed = new ArrayList<Cliente>();
        this.id = id;
    }

    public Central(Central central){
        this.Tipo = central.getTipo();
        this.Produccion = central.getProduccion();
        this.CoordX = central.getCoordX();
        this.CoordY = central.getCoordY();
        //Servings are assigned latter
    }

    public ArrayList<Cliente> getServing(){
        ArrayList<Cliente> aux = new ArrayList<Cliente>();
        aux.addAll(servingNotGuaranteed);
        aux.addAll(servingGuaranteed);
        return aux;
    };

    public int getCoordX() {
        return this.CoordX;
    }

    public void setCoordX(int var1) {
        this.CoordX = var1;
    }

    public double totalServedWithLoss(){
        double served=0;
        for(int i=0;i<servingGuaranteed.size() && served<Produccion;++i){
            served+=(servingGuaranteed.get(i).getConsumo()*(1+VEnergia.getPerdida(getCoordX(),getCoordY(),servingGuaranteed.get(i).getCoordX(),servingGuaranteed.get(i).getCoordY())));
        }
        for(int i=0;i<servingNotGuaranteed.size() && served<Produccion;++i) {
            served += (servingNotGuaranteed.get(i).getConsumo()*(1+VEnergia.getPerdida(getCoordX(),getCoordY(),servingNotGuaranteed.get(i).getCoordX(),servingNotGuaranteed.get(i).getCoordY())));
        }
        return served;
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
}