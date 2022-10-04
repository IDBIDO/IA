package IA.Electricity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Centrales{
    private static final long serialVersionUID = 1L;
    private Random myRandom;
    private static final double[][] prod = new double[][]{{500.0, 250.0}, {150.0, 100.0}, {90.0, 10.0}};
    private static final int[] TIPO = new int[]{0, 1, 2};

    Map<Integer,Central> centrales;

    public Centrales(int[] powerPlantsPerType, int seed) throws Exception {
        centrales = new LinkedHashMap<Integer,Central>();
        if (powerPlantsPerType.length != 3) {
            throw new Exception("Vector Centrales de tamaño incorrecto");
        } else {
            this.myRandom = new Random((long)seed);
            int count =0;

            for(int i = 0; i < 3; ++i) {
                for(int j = 0; j < powerPlantsPerType[i]; ++j) {
                    double var4 = this.myRandom.nextDouble() * prod[i][0] + prod[i][1];
                    Central central = new Central(TIPO[i], truncate(var4), this.myRandom.nextInt(100), this.myRandom.nextInt(100),count);
                    this.centrales.put(count,central);
                    ++count;
                }
            }
        }
    }


    public Central get(int id){
        return this.centrales.get(id);
    }

    public int size(){
        return this.size();
    }

    public Centrales(Centrales centrales){
        for(int i=0;i<centrales.size();++i){
            this.centrales.put(centrales.get(i).getId(),new Central(centrales.get(i)));
        }
    }


    public void print(){
        System.out.println("CENTRALES:");
        for(int i=0;i<this.centrales.size();++i){
            System.out.print("Coordenadas: ("+ this.centrales.get(i).getCoordX()+" "+this.centrales.get(i).getCoordY()+") ");
            System.out.print("Capacidad max: "+this.centrales.get(i).getProduccion()+" Capacidad usada: "+this.centrales.get(i).totalServedWithLoss()+"\n");
        }
    }

    private static double truncate(double var0) {
        return Math.floor(var0 * 100.0) / 100.0;
    }
}
