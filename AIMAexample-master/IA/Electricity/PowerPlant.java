package IA.Electricity;

/**
 * Created by bejar on 17/01/17.
 */
public abstract class PowerPlant {

    int minProduction, maxProduction;
    int priceSlope,priceB;
    int stopCost;
    boolean working=false;



    public static double computeNeededSupply(int supply,Position from, Position to){
        double distance = Position.euclidianDistance(from,to);
        if(distance<10)return 0;
        else if(distance<25)return 0.1;
        else if(distance<50)return 0.2;
        else if(distance<75)return 0.4;
        else return 0.6;
    }

    public void assign(int minProduction,int maxProduction,int priceSlope,int priceB, int stopCost){
        this.minProduction=minProduction;
        this.maxProduction=maxProduction;
        this.priceSlope=priceSlope;
        this.priceB=priceB;
        this.stopCost=stopCost;
    }

    public int getMinProduction(){
        return minProduction;
    }

    public int getMaxProduction() {
        return maxProduction;
    }

    public int getPriceB() {
        return priceB;
    }

    public int getPriceSlope() {
        return priceSlope;
    }

    public int getStopCost() {
        return stopCost;
    }

    public boolean isWorking() {
        return working;
    }
    public void startPlant(){
        working=true;
    }
}

