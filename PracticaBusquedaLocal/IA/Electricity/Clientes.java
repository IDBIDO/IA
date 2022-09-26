package IA.Electricity;

import java.util.ArrayList;
import java.util.Random;

public class Clientes extends ArrayList<Cliente> {
    private static final long serialVersionUID = 1L;
    private Random myRandom;
    private static final int[] TIPOCL = new int[]{0, 1, 2};
    private static final int[] TIPOCNT = new int[]{0, 1};
    private static final double[][] consumos = new double[][]{{15.0, 5.0}, {3.0, 2.0}, {2.0, 1.0}};

    public Clientes(int numberOfCustomers, double[] proportionsOfTypes, double guaranteed, int seed) throws Exception {
        if (proportionsOfTypes.length != 3) {
            throw new Exception("Vector proporciones tipos clientes de tamaño incorrecto");
        } else if (proportionsOfTypes[0] + proportionsOfTypes[1] + proportionsOfTypes[2] != 1.0) {
            throw new Exception("Vector proporciones tipos clientes no suma 1");
        } else if (guaranteed < 0.0 && guaranteed > 1.0) {
            throw new Exception("Proporcion garantizado fuera de limites");
        } else {
            this.myRandom = new Random((long)(seed + 1));

            for(int i = 0; i < numberOfCustomers; ++i) {
                double typeRandom = this.myRandom.nextDouble();
                byte var11;
                if (typeRandom < proportionsOfTypes[0]) {
                    var11 = 0;
                } else if (typeRandom < proportionsOfTypes[0] + proportionsOfTypes[1]) {
                    var11 = 1;
                } else {
                    var11 = 2;
                }

                typeRandom = this.myRandom.nextDouble();
                byte var12;
                if (typeRandom < guaranteed) {
                    var12 = 0;
                } else {
                    var12 = 1;
                }

                double var6 = this.myRandom.nextDouble() * consumos[var11][0] + consumos[var11][1];
                this.add(new Cliente(TIPOCL[var11], truncate(var6), TIPOCNT[var12], this.myRandom.nextInt(100), this.myRandom.nextInt(100)));
            }

        }
    }

    public void print(){
        System.out.println("CLIENTES:");
        for(int i=0;i<this.size();++i){
            System.out.print("Coordenadas: ("+ this.get(i).getCoordX()+" "+this.get(i).getCoordY()+")");
            System.out.print("Es garantizado: "+this.get(i).isGuaranteed()+ " Esta servido: "+this.get(i).estaServido()+" ");
            System.out.print("Consumo: "+this.get(i).getConsumo());
            if(get(i).estaServido()){
                System.out.print(" --- Le sirve la central en las coordenadas: ("+this.get(i).getServer().getCoordX()+ ", "+this.get(i).getServer().getCoordY()+")");
            }
            System.out.print("\n");
        }
    }

    private static double truncate(double var0) {
        return Math.floor(var0 * 100.0) / 100.0;
    }
}
