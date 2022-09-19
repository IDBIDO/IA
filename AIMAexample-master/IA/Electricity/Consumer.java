package IA.Electricity;

/**
 * Created by bejar on 17/01/17.
 */
public abstract class Consumer {

    int guaranteed,nonGuaranteed;
    int compensation=50;
    double demand;
    boolean isGuaranteed;
    Position position;

    PowerPlant supplier=null;

    //The type may be changed to an enum
    //A factory may be used to avoid coupling

    public int getGuaranteed(){
        return guaranteed;
    }
    public int getNonGuaranteed(){
        return guaranteed;
    }
    public int getCompensation() {
        return compensation;
    }


    protected void assign(Position position, boolean guaranteed,double demand) {
        this.position = position;
        this.isGuaranteed = guaranteed;
        this.demand = demand;
    }

    public void setSupplier(PowerPlant powerPlant){
        supplier = powerPlant;
    }

    public boolean isGuaranteed() {
        return isGuaranteed;
    }
}

