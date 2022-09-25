package IA.Electricity;

import java.util.ArrayList;
import java.util.Random;

public class Centrales extends ArrayList<Central> {
    private static final long serialVersionUID = 1L;
    private Random myRandom;
    private static final double[][] prod = new double[][]{{500.0, 250.0}, {150.0, 100.0}, {90.0, 10.0}};
    private static final int[] TIPO = new int[]{0, 1, 2};

    public Centrales(int[] powerPlantsPerType, int seed) throws Exception {
        if (powerPlantsPerType.length != 3) {
            throw new Exception("Vector Centrales de tamaño incorrecto");
        } else {
            this.myRandom = new Random((long)seed);

            for(int i = 0; i < 3; ++i) {
                for(int j = 0; j < powerPlantsPerType[i]; ++j) {
                    double var4 = this.myRandom.nextDouble() * prod[i][0] + prod[i][1];
                    Central var3 = new Central(TIPO[i], truncate(var4), this.myRandom.nextInt(100), this.myRandom.nextInt(100));
                    this.add(var3);
                }
            }
        }
    }


    public void print(){
        System.out.println("CENTRALES:");
        for(int i=0;i<this.size();++i){
            System.out.print("Coordenadas: ("+ this.get(i).getCoordX()+" "+this.get(i).getCoordY()+") ");
            System.out.print("Capacidad max: "+this.get(i).getProduccion()+" Capacidad usada: "+this.get(i).totalServedWithLoss()+"\n");
        }
    }

    private static double truncate(double var0) {
        return Math.floor(var0 * 100.0) / 100.0;
    }
}
