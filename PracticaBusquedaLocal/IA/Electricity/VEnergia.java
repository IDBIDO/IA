package IA.Electricity;



//This file comes out of decompiling a .class provided by the subject's teachers.
public class VEnergia {
    private static final double[][] precios = new double[][]{{400.0, 300.0, 50.0}, {500.0, 400.0, 50.0}, {600.0, 500.0, 50.0}};
    private static final double[][] costes = new double[][]{{50.0, 20000.0, 15000.0}, {80.0, 10000.0, 5000.0}, {150.0, 5000.0, 1500.0}};
    private static final double[][] perdida = new double[][]{{10.0, 0.0}, {25.0, 0.1}, {50.0, 0.2}, {75.0, 0.4}, {1000.0, 0.6}};

    public VEnergia() {
    }

    public static double getTarifaClienteGarantizada(int tipo) throws Exception {
        if (tipo >= 0 && tipo <= 2) {
            return precios[tipo][0];
        } else {
            throw new Exception("Tipo fuera de rango");
        }
    }

    public static double getTarifaClienteNoGarantizada(int tipo) throws Exception {
        if (tipo >= 0 && tipo <= 2) {
            return precios[tipo][1];
        } else {
            throw new Exception("Tipo fuera de rango");
        }
    }

    public static double getTarifaClientePenalizacion(int tipo) throws Exception {
        if (tipo >= 0 && tipo <= 2) {
            return precios[tipo][2];
        } else {
            throw new Exception("Tipo fuera de rango");
        }
    }

    public static double getCosteProduccionMW(int tipo) throws Exception {
        if (tipo >= 0 && tipo <= 2) {
            return costes[tipo][0];
        } else {
            throw new Exception("Tipo fuera de rango");
        }
    }

    public static double getCosteMarcha(int tipo) throws Exception {
        if (tipo >= 0 && tipo <= 2) {
            return costes[tipo][1];
        } else {
            throw new Exception("Tipo fuera de rango");
        }
    }

    public static double getCosteParada(int tipo) throws Exception {
        if (tipo >= 0 && tipo <= 2) {
            return costes[tipo][2];
        } else {
            throw new Exception("Tipo fuera de rango");
        }
    }

    public static double getPerdida(double tipo) {
        if(tipo<=perdida[0][0])
            return perdida[0][1];

        if(tipo<=perdida[1][0])
            return perdida[1][1];

        if(tipo<=perdida[2][0])
            return perdida[2][1];

        if(tipo<=perdida[3][0])
            return perdida[3][1];

        return perdida[4][1];
    }

    public static double euclidea(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static double getPerdida(double coordX1, double coordY1,double coordX2, double coordY2){
        return getPerdida(euclidea(coordX1,coordY1,coordX2,coordY2));
    }
}
