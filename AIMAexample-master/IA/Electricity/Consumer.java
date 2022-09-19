package IA.Electricity;

/**
 * Created by bejar on 17/01/17.
 */
public abstract class Consumer {

    int guaranteed,nonGuaranteed;
    int compensation=50;
    int min,max;
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


    protected void assign(Position position) {
        this.position = position;
    }

    public void setSupplier(PowerPlant powerPlant){
        supplier = powerPlant;
    }
}

