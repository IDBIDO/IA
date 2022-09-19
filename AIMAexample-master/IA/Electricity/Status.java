package IA.Electricity;

public class Status {

    Consumer consumers[];

    PowerPlant powerPlants[];

    public Status(int consumers[],int powerPlants[]){
        this.consumers = new Consumer[consumers[0]+consumers[1]+consumers[2]];
        int aux =0;
        for(int i=0;i<consumers.length();++i){
            for(int j=0;j<consumers[i];++j){
                Consumer consumer;

                boolean guaranteed = Math.random()>=0.5;
                Position position = new Position((int)(Math.random()*100),(int)(Math.random()*100));

                if(i==0)consumer=new LargeConsumer(position,guaranteed,randomRange(1,2));
                else if(i==1)consumer = new VeryLargeConsumer(position,guaranteed,randomRange(2,5));
                else consumer = new ExtraLargeConsumer(position,guaranteed,randomRange(5,20));

                this.consumers[aux]=consumer;

                aux+=1;
            }
        }
        this.powerPlants= new PowerPlant[powerPlants[0]+powerPlants[1]+powerPlants[2]];
        aux = 0;
        for(int i=0;i<powerPlants.length();++i){
            for(int j=0;j<consumers[i];++j){
                PowerPlant powerPlant;

                if(i==0)powerPlant=new APowerPlant();
                else if(i==1)powerPlant = new BPowerPlant();
                else powerPlant = new CPowerPlant();

                this.powerPlants[aux]=powerPlant;

                aux+=1;
            }
        }
    }

    public static double randomRange(double min,double max){
        return (Math.random()*(max-min))+min;
    }

}
