package IA.Electricity;

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

    private Central server;

    public Cliente(int var1, double var2, int var4, int var5, int var6) {
        this.Tipo = var1;
        this.Consumo = var2;
        this.Contrato = var4;
        this.CoordX = var5;
        this.CoordY = var6;
        this.server = null;
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

    public void setCentral(Central central){
        if(estaServido()){
            if(central==null){
                Central aux = this.server;
                this.server = null;
                aux.deleteClient(this);
            }
            else {
                this.server.deleteClient(this);
            }
        }
        this.server= central;
        if(central!=null)central.addClient(this);
    }

    public void unsetCentral(){
        setCentral(null);
    }

    public boolean estaServido(){
        return server!=null;
    }

    public Central getServer(){
        return server;
    }

    public boolean isGuaranteed() {
        return Contrato==GARANTIZADO;
    }

    public double getCompensation() throws Exception {
        if(!isGuaranteed())return 0;
        else return VEnergia.getTarifaClientePenalizacion(getTipo());
    }
}
