package IA.Electricity;

public class LargeConsumer extends Consumer{
    public LargeConsumer(Position position){
        super.assign(position);
        guaranteed = 600;
        nonGuaranteed = 500;
        min = 2;
        max = 5;
    }
}
