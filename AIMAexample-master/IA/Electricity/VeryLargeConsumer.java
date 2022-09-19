package IA.Electricity;

public class VeryLargeConsumer extends Consumer{
    public VeryLargeConsumer(Position position, Boolean garantizado, double demand){
        super.assign(position,garantizado,demand);
        guaranteed = 500;
        nonGuaranteed = 400;
    }
}
