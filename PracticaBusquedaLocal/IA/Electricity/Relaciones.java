package IA.Electricity;


import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.floor;


public class Relaciones{
    double brutoTotal;
    double costeTotal;
    double desperdiciadoTotal;
    double indemnizaciones;
    ArrayList<Integer> relaciones;
    ArrayList<Double> mwUsados;
    public Relaciones(Centrales centrales, Clientes clientes) throws Exception {
        this.relaciones = new ArrayList<Integer>();
        this.mwUsados = new ArrayList<Double>();
        this.brutoTotal = 0;
        this.desperdiciadoTotal=0;
        this.indemnizaciones = 0;
        for (Map.Entry<Integer, Central> entry : centrales.entrySet()) {
            mwUsados.add(0.0);
            this.costeTotal+=VEnergia.getCosteParada(entry.getValue().getTipo());
        }
        for (int i=0;i<clientes.size();++i) {
            relaciones.add(-1);
        }

    }

    public Relaciones(Relaciones relaciones){
        this.relaciones = new ArrayList<Integer>();
        this.relaciones.addAll(relaciones.getClientes());
        this.mwUsados = new ArrayList<Double>();
        this.mwUsados.addAll(relaciones.getMwUsados());
        this.brutoTotal = relaciones.getBrutoTotal();
        this.costeTotal = relaciones.getCosteTotal();
        this.desperdiciadoTotal = relaciones.getDesperdiciadoTotal();
        this.indemnizaciones = relaciones.getIndemnizaciones();
    }



    public double getIndemnizaciones() {
        return indemnizaciones;
    }

    public ArrayList<Integer> getClientes() {
        return relaciones;
    }

    public ArrayList<Double> getMwUsados() {
        return mwUsados;
    }


    public double getDesperdiciadoTotal() {
        return desperdiciadoTotal;
    }


    public double getBrutoTotal() {
        return brutoTotal;
    }

    public double getCosteTotal(){
        return costeTotal;
    }


    public void asignaCliente(Cliente cliente, Central central) throws Exception {
        double perdida = VEnergia.getPerdida(central.getCoordX(),central.getCoordY(),cliente.getCoordX(),cliente.getCoordY());
        //System.out.println(perdida);
        /*
        System.out.println(brutoTotal - costeTotal);
        System.out.println(cliente.getConsumo());
        System.out.println(cliente.getPrecio());
        System.out.println(cliente.getId());
        */
        boolean vacioAntes = floor(abs(mwUsados.get(central.getId())))==0;

        brutoTotal+=addCliente(central.getId(),cliente.getId(),cliente.getPrecio(),perdida*cliente.getConsumo(), cliente.getConsumo());
        desperdiciadoTotal +=perdida*cliente.getConsumo();
        if(vacioAntes){
            costeTotal=costeTotal+(VEnergia.getCosteMarcha(central.getTipo())-VEnergia.getCosteParada(central.getTipo()));
            costeTotal=costeTotal+(VEnergia.getCosteProduccionMW(central.getTipo())* central.getProduccion());
        }
        if(!cliente.isGuaranteed()) {
            indemnizaciones -= VEnergia.getTarifaClientePenalizacion(cliente.getTipo())*cliente.getConsumo();
        }
        /*
        System.out.println(brutoTotal - costeTotal);
        System.out.println("\n\n\n");
         */
    }

    private double addCliente(int idCentral, int id, double precio, double perdida, double venta) {
        relaciones.set(id,idCentral);
        mwUsados.set(idCentral,mwUsados.get(idCentral)+(perdida+venta));
        return (venta*precio);
    }

    public void quitarCliente(Cliente cliente, Central central) throws Exception {
        double perdida = VEnergia.getPerdida(central.getCoordX(),central.getCoordY(),cliente.getCoordX(),cliente.getCoordY());

        brutoTotal+=deleteCliente(central.getId(),cliente.getId(),cliente.getPrecio(),perdida*cliente.getConsumo(), cliente.getConsumo());
        desperdiciadoTotal -=perdida*cliente.getConsumo();
        if(floor(abs(mwUsados.get(central.getId())))==0){
            costeTotal=costeTotal-(VEnergia.getCosteMarcha(central.getTipo())-VEnergia.getCosteParada(central.getTipo()));
            costeTotal=costeTotal-(VEnergia.getCosteProduccionMW(central.getTipo())* central.getProduccion());
            mwUsados.set(central.getId(),0.0);
        }
        if(!cliente.isGuaranteed()) {
            indemnizaciones += VEnergia.getTarifaClientePenalizacion(cliente.getTipo())*cliente.getConsumo();
        }
    }

    private double deleteCliente(int idCentral, int id, double precio, double perdida, double venta) {
        relaciones.set(id,-1);
        mwUsados.set(idCentral,mwUsados.get(idCentral)-(perdida+venta));
        return (-venta*precio);
    }

    public void mueveCliente(Cliente cliente, Central central) throws Exception {
        if (this.puedeAsignarse(cliente, central)) {
            this.quitarCliente(cliente, central);
            this.asignaCliente(cliente, central);
        }
    }

    public boolean puedeQuitarse(Cliente cliente) {
        return false;
    }

    public boolean puedeAsignarse(Cliente cliente, Central central){
        double perdida = VEnergia.getPerdida(central.getCoordX(),central.getCoordY(),cliente.getCoordX(),cliente.getCoordY());
        double gasto = cliente.getConsumo()*(1+perdida);
        double consumido = getMWUsadosCentral(central.getId());
        return ((consumido+gasto)<=central.getProduccion());
    }

    public boolean puedeCambiarse(Cliente cliente1, Cliente cliente2, Central central1) {
        double perdida1 = VEnergia.getPerdida(central1.getCoordX(),central1.getCoordY(),cliente1.getCoordX(),cliente1.getCoordY());
        double gasto1 = cliente1.getConsumo()*(1+perdida1);

        double perdida2 = VEnergia.getPerdida(central1.getCoordX(),central1.getCoordY(),cliente1.getCoordX(),cliente1.getCoordY());
        double gasto2 = cliente2.getConsumo()*(1+perdida2);

        double capacidad = central1.getProduccion();
        double restante = capacidad-(mwUsados.get(central1.getId()));
        return (capacidad-restante-(gasto1-gasto2))>0;
    }

    public void print(Clientes clientes, Centrales centrales) {
        Set<Integer> clientesServidos = new HashSet<Integer>();
        for (Map.Entry<Integer,Central> entry : centrales.entrySet()) {
            System.out.println("Capacidad usada: "+mwUsados.get(entry.getKey())+"/"+entry.getValue().getProduccion());
            System.out.println("Coordenadas: ("+entry.getValue().getCoordX()+","+entry.getValue().getCoordY()+")");
            int i=0;
            for(Integer centralid: relaciones){
                if(centralid ==entry.getKey()) {
                    Cliente cliente = clientes.get(i);
                    cliente.print();
                    clientesServidos.add(i);
                }
                ++i;
            }
        }
        System.out.println("\nClientes no asignados a central: ");
        for (Map.Entry<Integer, Cliente> entry : clientes.entrySet()) {
            if(!clientesServidos.contains(entry.getKey())){
                entry.getValue().print();
            }
        }
    }

    public double getMWUsadosCentral(Integer key) {
        return mwUsados.get(key);
    }

    public void setIndemnizacion(double indemnizar) {
        this.indemnizaciones = indemnizar;
    }
}
