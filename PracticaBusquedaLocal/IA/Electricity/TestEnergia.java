package IA.Electricity;

import IA.Electricity.VEnergia;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestEnergia {

    public static void main(String[] var0) throws Exception {
        Status status= new Status(14);
        status.initialSolution1(false); //RANDOM
        status.beneficioPorCentral();
    }
}
