package IA.Electricity;

import java.security.spec.ECField;

public class Cliente {
    public static final int CLIENTEXG = 0;
    public static final int CLIENTEMG = 1;
    public static final int CLIENTEG = 2;
    public static final int GARANTIZADO = 0;
    public static final int NOGARANTIZADO = 1;
    private int Tipo;
    private int CoordX;
    private int CoordY;
    private double Consumo;
    private int Contrato;

    private int id;

    private boolean served;

    public Cliente(int tipo, double consumo, int contrato, int coordX, int coordY, int id) {
        this.Tipo =tipo;
        this.Consumo = consumo;
        this.Contrato = contrato;
        this.CoordX = coordX;
        this.CoordY = coordY;
        this.served = false;
        this.id = id;
    }

    public Cliente(Cliente cliente){
        this.Tipo = cliente.getTipo();
        this.Consumo = cliente.getConsumo();
        this.Contrato = cliente.getContrato();
        this.CoordX = cliente.getCoordX();
        this.CoordY = cliente.getCoordY();
        this.id = cliente.getId();
        this.served= cliente.estaServido();
    }

    public int getId() {
        return id;
    }

    public int getTipo() {
        return this.Tipo;
    }

    public int getCoordX() {
        return this.CoordX;
    }

    public int getCoordY() {
        return this.CoordY;
    }

    public double getConsumo() {
        return this.Consumo;
    }

    public int getContrato() {
        return this.Contrato;
    }

    public void setTipo(int var1) {
        this.Tipo = var1;
    }

    public void setCoordX(int var1) {
        this.CoordX = var1;
    }

    public void setCoordY(int var1) {
        this.CoordY = var1;
    }

    public void setConsumo(double var1) {
        this.Consumo = var1;
    }

    public void setContrato(int var1) {
        this.Contrato = var1;
    }

    public void asignarCentral(){
        this.served = false;
    }
    public void quitarCentral(){
        this.served=false;
    }

    public boolean estaServido(){
        return served;
    }

    public boolean isGuaranteed() {
        return Contrato==GARANTIZADO;
    }

    public double getPrecio(){
        try {
            if (isGuaranteed()) {
                return VEnergia.getTarifaClienteGarantizada(this.Tipo);
            } else {
                return VEnergia.getTarifaClienteNoGarantizada(this.Tipo);
            }
        }
        catch(Exception e){
            System.out.println("Excepcion: "+e.toString());
            return 0;
        }
    }

    public void print() {
        System.out.println("    Cliente: "+this.id);
        System.out.println("    Coordenadas: ("+ getCoordX()+" "+getCoordY()+")");
        System.out.println("    Es garantizado: "+isGuaranteed()+ " Esta servido: "+estaServido()+" ");
        System.out.println("    Consumo: "+getConsumo()+"\n");
    }
}
