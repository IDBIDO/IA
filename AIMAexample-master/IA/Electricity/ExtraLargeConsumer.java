package IA.Electricity;

public class ExtraLargeConsumer extends Consumer{
    public ExtraLargeConsumer(Position position){
        super.assign(position);
        guaranteed = 400;
        nonGuaranteed = 300;
        min = 5;
        max = 20;
    }
}
