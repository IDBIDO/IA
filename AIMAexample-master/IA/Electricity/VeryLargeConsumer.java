package IA.Electricity;

public class VeryLargeConsumer extends Consumer{
    public VeryLargeConsumer(Position position){
        super.assign(position);
        guaranteed = 500;
        nonGuaranteed = 400;
        min = 1;
        max = 2;
    }
}
