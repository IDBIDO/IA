package IA.Electricity;

public class LargeConsumer extends Consumer{
    public LargeConsumer(Position position, Boolean garantizado, double demand){
        super.assign(position,garantizado,demand);
        guaranteed = 600;
        nonGuaranteed = 500;
    }
}
