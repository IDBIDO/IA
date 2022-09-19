package IA.Electricity;

public class ExtraLargeConsumer extends Consumer{
    public ExtraLargeConsumer(Position position, Boolean garantizado, double demand){
        super.assign(position,garantizado,demand);
        guaranteed = 400;
        nonGuaranteed = 300;
    }
}
