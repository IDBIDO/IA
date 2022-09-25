package IA.Electricity;



//This file comes out of decompiling a .class provided by the subject's teachers.
public class VEnergia {
    private static final double[][] precios = new double[][]{{400.0, 300.0, 50.0}, {500.0, 400.0, 50.0}, {600.0, 500.0, 50.0}};
    private static final double[][] costes = new double[][]{{50.0, 20000.0, 15000.0}, {80.0, 10000.0, 5000.0}, {150.0, 5000.0, 1500.0}};
    private static final double[][] perdida = new double[][]{{10.0, 0.0}, {25.0, 0.1}, {50.0, 0.2}, {75.0, 0.4}, {1000.0, 0.6}};

    public VEnergia() {
    }

    public static double getTarifaClienteGarantizada(int var0) throws Exception {
        if (var0 >= 0 && var0 <= 2) {
            return precios[var0][0];
        } else {
            throw new Exception("Tipo fuera de rango");
        }
    }

    public static double getTarifaClienteNoGarantizada(int var0) throws Exception {
        if (var0 >= 0 && var0 <= 2) {
            return precios[var0][1];
        } else {
            throw new Exception("Tipo fuera de rango");
        }
    }

    public static double getTarifaClientePenalizacion(int var0) throws Exception {
        if (var0 >= 0 && var0 <= 2) {
            return precios[var0][2];
        } else {
            throw new Exception("Tipo fuera de rango");
        }
    }

    public static double getCosteProduccionMW(int var0) throws Exception {
        if (var0 >= 0 && var0 <= 2) {
            return costes[var0][0];
        } else {
            throw new Exception("Tipo fuera de rango");
        }
    }

    public static double getCosteMarcha(int var0) throws Exception {
        if (var0 >= 0 && var0 <= 2) {
            return costes[var0][1];
        } else {
            throw new Exception("Tipo fuera de rango");
        }
    }

    public static double getCosteParada(int var0) throws Exception {
        if (var0 >= 0 && var0 <= 2) {
            return costes[var0][2];
        } else {
            throw new Exception("Tipo fuera de rango");
        }
    }

    public static double getPerdida(double var0) {
        int index;
        for(index = 0;index <perdida.length && perdida[index][0]<var0;++index){}
        if(index==perdida.length)index=index-1;
        return perdida[index][1];
    }

    public static double euclidea(double var0, double var2, double var4, double var6) {
        return Math.sqrt((var0 - var4) * (var0 - var4)) + (var2 - var6) * (var2 - var6);
    }

    public static double getPerdida(double coordX1, double coordY1,double coordX2, double coordY2){
        return getPerdida(euclidea(coordX1,coordY1,coordX2,coordY2));
    }
}
