package IA.Electricity;

import aima.util.Pair;

import java.text.DecimalFormat;
import java.util.*;

public class Status {
    Centrales centrales;
    Clientes clientes;
    Relaciones relaciones;

    long generacion;
    public Status(int seed) throws Exception {
        int test = 1;
        centrales= new Centrales(new int[]{5*test,10*test,25*test},seed);
        clientes = new Clientes(1000*test,new double[]{0.25,0.3,0.45},0.75,seed);
        relaciones = new Relaciones(centrales,clientes);
        long startTime = (System.nanoTime());
        //initialSolution2(false);//Comment out for test 5
        generacion = (System.nanoTime() - startTime); //To measure how long the program takes to generate the initial solution

        calculaIndemnizaciones();
    }

    private void calculaIndemnizaciones() throws Exception {
        ArrayList<Integer> clientesCentral = relaciones.getClientes();
        double indemnizar = 0;
        for(int i=0;i<clientesCentral.size();++i){
            if(clientesCentral.get(i)==-1){
                indemnizar+=VEnergia.getTarifaClientePenalizacion(clientes.get(i).getTipo())*clientes.get(i).getConsumo();
            }
        }
        relaciones.setIndemnizacion(indemnizar);
    }

    public Status(Status status) {
        this.centrales=status.getCentrales();
        this.clientes=status.getClientes();
        this.relaciones=new Relaciones(status.getRelaciones());
        this.generacion = status.getGeneracion();
    }
    //Adds the guaranteed or not guaranteed customers to a random available power plant
    void initialSolution1(boolean includeNoGuaranteed, int seed) throws Exception {

        Random r = new Random(seed);
        ArrayList<Integer>centralIds = centrales.getIds();
        int actualCentralIndex = r.nextInt(centrales.size());

        for (Map.Entry<Integer,Cliente> entry : clientes.entrySet()) {
            {
                if (entry.getValue().isGuaranteed()) {
                    Central actualCentral = centrales.get(actualCentralIndex);
                    while (!canServe(entry.getValue(),actualCentral)) {
                        actualCentralIndex = r.nextInt(centrales.size());
                        actualCentral = centrales.get(actualCentralIndex);
                    }
                    relaciones.asignaCliente(entry.getValue(),actualCentral);
                }
                actualCentralIndex = r.nextInt(centrales.size());
            }
        }
        if(includeNoGuaranteed) {
            for (Map.Entry<Integer, Cliente> entry : clientes.entrySet()) {
                {
                    if (!entry.getValue().isGuaranteed()) {
                        Central actualCentral = centrales.get(actualCentralIndex);
                        while (!canServe(entry.getValue(),actualCentral)) {
                            actualCentralIndex = r.nextInt(centrales.size());
                            actualCentral = centrales.get(actualCentralIndex);
                        }
                        relaciones.asignaCliente(entry.getValue(),actualCentral);
                    }
                    actualCentralIndex = r.nextInt(centrales.size());
                }
            }
        }
        System.out.println("------------------------------------------ ");
        System.out.println("Initial solutions: ");
        //relaciones.print(clientes,centrales);
        System.out.println("Beneficio: "+String.valueOf(beneficioPorCentral()));
    }
    //Adds the customers to powerplants in such a way that tries to minimize the distance (and then the lost energy)
    //It is unlikely that this greedy algorithm yields the optimal distance configuration, though it may be close (or not) to it.
    void initialSolution2(boolean includeNoGuaranteed) throws Exception {
        ArrayList<Integer>centralIds = centrales.getIds();
        for (Map.Entry<Integer,Cliente> entry : clientes.entrySet()) {
            {
                if (entry.getValue().isGuaranteed()) {
                    double min = -1;
                    int idCentral =0;
                    for(int i=0;i<centralIds.size();++i){
                        double perdida = VEnergia.getPerdida(centrales.get(i).getCoordX(),
                                centrales.get(i).getCoordY(),entry.getValue().getCoordX(),entry.getValue().getCoordY());
                        if((min==-1 || min>perdida)&&(canServe(entry.getValue(),centrales.get(i)))){
                            min = perdida;
                            idCentral = i;
                        }
                    }
                    relaciones.asignaCliente(entry.getValue(),centrales.get(idCentral));
                }
            }
        }
        if(includeNoGuaranteed) {
            for (Map.Entry<Integer, Cliente> entry : clientes.entrySet()) {
                {
                    if (!entry.getValue().isGuaranteed()) {
                        double max = -1;
                        int idCentral =0;
                        for(int i=0;i<centralIds.size();++i){
                            double perdida = VEnergia.getPerdida(centrales.get(i).getCoordX(),
                                    centrales.get(i).getCoordY(),entry.getValue().getCoordX(),entry.getValue().getCoordY());
                            if((max==-1 || max>perdida)&&(canServe(entry.getValue(),centrales.get(i)))){
                                max = perdida;
                                idCentral = i;
                            }
                        }
                        relaciones.asignaCliente(entry.getValue(),centrales.get(idCentral));
                    }
                }
            }
        }
        System.out.println("------------------------------------------ ");
        System.out.println("Initial solutions: ");
        //relaciones.print(clientes,centrales);
        System.out.println("Beneficio: "+String.valueOf(beneficioPorCentral()));
    }
    //Adds clients to power plants in such a way that minimises the number of power plants working
    void initialSolution3(boolean includeNoGuaranteed) throws Exception {
        ArrayList<Integer>centralIds = centrales.getIds();
        Collections.sort(centralIds, new Comparator<Integer>() {
            @Override
            public int compare(Integer central1, Integer central2) {
                if(centrales.get(central1).getProduccion()>centrales.get(central2).getProduccion())return -1;
                else if (centrales.get(central1).getProduccion()<centrales.get(central2).getProduccion()) return 1;
                else{
                    if(central1<central2)return -1;
                    else return 1;
                }
            }
        });
        int central =0;
        for (Map.Entry<Integer,Cliente> entry : clientes.entrySet()) {
            {
                if (entry.getValue().isGuaranteed()) {
                    while(!canServe(entry.getValue(),centrales.get(central)))++central;
                    relaciones.asignaCliente(entry.getValue(),centrales.get(central));
                }
            }
        }
        if(includeNoGuaranteed) {
            for (Map.Entry<Integer, Cliente> entry : clientes.entrySet()) {
                {
                    if (!entry.getValue().isGuaranteed()) {
                        while(central<centralIds.size() && !canServe(entry.getValue(),centrales.get(central)))++central;
                        if(central>=centralIds.size())break;
                        relaciones.asignaCliente(entry.getValue(),centrales.get(central));
                    }
                }
            }
        }
        System.out.println("------------------------------------------ ");
        System.out.println("Initial solutions: ");
        //relaciones.print(clientes,centrales);
        System.out.println("Beneficio: "+String.valueOf(beneficioPorCentral()));
    }
    //Tries to have a uniform assignation of power plants and clients. Though it may be impossible.
    void initialSolution4(boolean includeNoGuaranteed) throws Exception {
        ArrayList<Integer>centralIds = centrales.getIds();
        int central =0;
        for (Map.Entry<Integer,Cliente> entry : clientes.entrySet()) {
            {
                if (entry.getValue().isGuaranteed()) {
                    while(!canServe(entry.getValue(),centrales.get(central))){
                        ++central;
                        if(central==centrales.size())central=0;
                    }
                    relaciones.asignaCliente(entry.getValue(),centrales.get(central));
                    ++central;
                    if(central==centrales.size())central=0;
                }
            }
        }
        central =0;
        if(includeNoGuaranteed) {
            for (Map.Entry<Integer, Cliente> entry : clientes.entrySet()) {
                {
                    if (!entry.getValue().isGuaranteed()) {
                        while(central<centralIds.size() && !canServe(entry.getValue(),centrales.get(central)))++central;
                        if(central>=centralIds.size())break;
                        relaciones.asignaCliente(entry.getValue(),centrales.get(central));
                        ++central;
                    }
                }
            }
        }
        System.out.println("------------------------------------------ ");
        System.out.println("Initial solutions: ");
        //relaciones.print(clientes,centrales);
        System.out.println("Beneficio: "+String.valueOf(beneficioPorCentral()));
    }
    //Assigns power plants to customers so that a customer of type x is assigned to a power plant of type x, if possible.
    void initialSolution5(boolean includeNoGuaranteed) throws Exception {
        ArrayList<Integer> centralIds = centrales.getIds();
        int central = -1;
        for (Map.Entry<Integer, Cliente> entry : clientes.entrySet()) {
                if (entry.getValue().isGuaranteed()) {
                    int type = entry.getValue().getTipo();
                    for (int i = 0; i < centralIds.size(); ++i) {
                        if (centrales.get(centralIds.get(i)).getTipo() == type) {
                            if (canServe(entry.getValue(), centrales.get(centralIds.get(i)))) {
                                central = i;
                                break;
                            }
                        }
                    }
                    if (central == -1) {
                        central = 0;
                        while (!canServe(entry.getValue(), centrales.get(central))) ++central;
                    }
                    relaciones.asignaCliente(entry.getValue(), centrales.get(centralIds.get(central)));
                    central = -1;
                }
            }
            central = -1;
            if (includeNoGuaranteed) {
                for (Map.Entry<Integer, Cliente> entry : clientes.entrySet()) {
                    {
                        int type = entry.getValue().getTipo();
                        for (int i = 0; i < centralIds.size(); ++i) {
                            if (centrales.get(centralIds.get(i)).getTipo() == type) {
                                if (canServe(entry.getValue(), centrales.get(centralIds.get(i)))) {
                                    central = i;
                                    break;
                                }
                            }
                        }
                        if (central == -1) {
                            central = 0;
                            while (!canServe(entry.getValue(), centrales.get(central))) ++central;
                        }
                        relaciones.asignaCliente(entry.getValue(), centrales.get(centralIds.get(central)));
                        central = -1;
                    }
                }
            }
            System.out.println("------------------------------------------ ");
            System.out.println("Initial solutions: ");
            //relaciones.print(clientes,centrales);
            System.out.println("Beneficio: " + String.valueOf(beneficioPorCentral()));
    }

    public ArrayList<Cliente> getAsignedClientes() {
        ArrayList<Cliente> ret = new ArrayList<>();
        for (int i = 0; i < relaciones.getClientes().size(); ++i) {
            if (relaciones.getClientes().get(i) != -1) {
                Cliente cliente = clientes.get(i);
                ret.add(cliente);
            }
        }
        return ret;
    }

    public ArrayList<Cliente> getNoAsignedClientes() {
        ArrayList<Cliente> ret = new ArrayList<>();
        for (int i = 0; i < relaciones.getClientes().size(); ++i) {
            if (relaciones.getClientes().get(i) == -1) {
                Cliente cliente = clientes.get(i);
                ret.add(cliente);
            }
        }
        return ret;
    }

    public ArrayList<Cliente> getNoGuaranteeAsignedClientes() {
        ArrayList<Cliente> ret = new ArrayList<>();
        for (int i = 0; i < relaciones.getClientes().size(); ++i) {
            if (relaciones.getClientes().get(i) != -1) {
                Cliente cliente = clientes.get(i);
                if (!cliente.isGuaranteed()) {
                    ret.add(cliente);
                }
            }
        }
        return ret;
    }

    public void printState() throws Exception {
        relaciones.print(clientes,centrales);
        System.out.println("Beneficio: "+beneficioPorCentral());
    }

    public void printState2() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        for (int i = 0; i < relaciones.mwUsados.size(); ++i) {
            System.out.println("Consumido/MaxProduccion:" + df.format(relaciones.mwUsados.get(i)) + " / " + centrales.get(i).getProduccion());
        }
    }

    public boolean canServe(Cliente cliente,Central central) {
        return relaciones.puedeAsignarse(cliente,central);
    }

    public Central puedeAsignarAlgunCentral(Cliente cliente) {

        for (Map.Entry<Integer, Central> centralIter : centrales.entrySet()) {
            if (relaciones.getClientes().get(cliente.getId()) != centralIter.getKey() && this.canServe(cliente, centralIter.getValue())) {
                return centralIter.getValue();
            }
        }
        return null;
    }

    public boolean canChange(Cliente cliente1, Cliente cliente2,Central central){
        return relaciones.puedeCambiarse(cliente1, cliente2,central);
    }

    //Funcion que calcule el beneficio
    public double beneficioPorCentral(){
        return relaciones.getBrutoTotal()-relaciones.getCosteTotal()-relaciones.getIndemnizaciones();
    }

    //operadores

    public Clientes getClientes(){
        return clientes;
    }
    public Centrales getCentrales(){
        return centrales;
    }
    public Relaciones getRelaciones(){
        return relaciones;
    }

    public double getMwTotal() {
        double sum = 0;
        for (int i = 0; i < centrales.size(); ++i) {
            sum += centrales.get(i).getProduccion();
        }
        return sum;
    }

    public double getMwUsadoTotal() {
        ArrayList<Double> mwUsadoPorCentral = relaciones.getMwUsados();
        double sum = 0;
        for (int i = 0; i < mwUsadoPorCentral.size(); ++i) {
            sum += mwUsadoPorCentral.get(i);
        }
        return sum;
    }


    public double heuristic1() throws Exception {
        return beneficioPorCentral();
    }

    public double heuristic2() throws Exception{
        return beneficioPorCentral()-totalDesperdiciado();
    }

    public double heuristic3() throws Exception{
        return beneficioPorCentral()-totalDesperdiciado()*300;
    }
    //This heuristic penalises having non-assigned guaranteed customers
    public double heuristic4() throws Exception{
        return beneficioPorCentral()-totalDesperdiciado()*300-garantizadosNoAsignados()*0-centralesApagadas()*42500;
    }

    public int centralesApagadas() {
        return relaciones.getCentralesApagadas();
    }

    private int garantizadosNoAsignados() {
        return relaciones.getGarantizadosNo();
    }

    public double beneficioCentral(int keyCentral) {
        ArrayList<Integer> clienteCentral = relaciones.getClientes();
        double brutoTotal = 0.0;
        double costeTotal = 0.0;
        for (int i = 0; i < clienteCentral.size(); ++i) {

            if (clienteCentral.get(i) == keyCentral) {
                Cliente cliente = clientes.get(i);
                Central central = centrales.get(clienteCentral.get(i));
                brutoTotal += cliente.getConsumo()*cliente.getPrecio();
                double perdida = VEnergia.getPerdida(central.getCoordX(),central.getCoordY(),cliente.getCoordX(),cliente.getCoordY());
                costeTotal += perdida*cliente.getConsumo();
            }
        }
        return brutoTotal-costeTotal;
    }

    public double heuristicEntropy2() throws  Exception {

        Map<Integer, Central> centralMap = centrales.getCentrales();

        double act = 0.0;

        double mediaIndemnizacionesCentral = relaciones.indemnizaciones/centrales.size();
        for (int i = 0; i < centrales.size(); ++i) {
            double aux = Math.max(0.0, beneficioCentral(i)-mediaIndemnizacionesCentral);
            double p = aux/(getMwTotal()*600);

            //System.out.println(p);
            if (p > 0)
                act += p*Math.log(p);
            //System.out.println(act);
        }
        System.out.println(act);
        return act*10000000;

    }

    public double heuristicEntropy() throws  Exception {

        double p = beneficioPorCentral()/getMwTotal()*600;
        return p*Math.log(p);
    }

    private double totalDesperdiciado() {
        return relaciones.getDesperdiciadoTotal();
    }


    public boolean is_goal(){
        return false;
    }

    void quitarCliente(Cliente cliente, Central central) throws Exception {
        relaciones.quitarCliente(cliente,central);
    }

    void asignarCliente(Cliente cliente, Central central) throws Exception {
        relaciones.asignaCliente(cliente,central);
    }

    void swapCliente(Cliente cliente1, Central central1, Cliente cliente2, Central central2) throws Exception {
        relaciones.quitarCliente(cliente1,central1);
        relaciones.quitarCliente(cliente2,central2);

        relaciones.asignaCliente(cliente1,central2);
        relaciones.asignaCliente(cliente2,central1);
    }

    public double costeTotal() {
        return relaciones.getCosteTotal();
    }

    public double brutoTotal() {
        return relaciones.getBrutoTotal();
    }

    public boolean makesSenseChange(Cliente cliente1, Cliente cliente2, Central central1, Central central2) {
        double perdidaActual =VEnergia.getPerdida(central1.getCoordX(),central1.getCoordY(),cliente1.getCoordX(),cliente1.getCoordY())
                              + VEnergia.getPerdida(central2.getCoordX(),central2.getCoordY(),cliente2.getCoordX(),cliente2.getCoordY());
        double perdidaNueva = VEnergia.getPerdida(central1.getCoordX(),central1.getCoordY(),cliente2.getCoordX(),cliente2.getCoordY())
                              + VEnergia.getPerdida(central2.getCoordX(),central2.getCoordY(),cliente1.getCoordX(),cliente1.getCoordY());

        return perdidaNueva<=perdidaActual;
    }

    public ArrayList<Pair> getCanSwapCentral() {
        Clientes clientesaux = getClientes();
        Centrales centralesaux = getCentrales();
        //Pair pair = new Pair(1, "One");
        ArrayList<Cliente> clientes = new ArrayList<Cliente>(clientesaux.getClientes().values());
        ArrayList<Central> centrales = new ArrayList<Central>(centralesaux.getCentrales().values());

        Map<Integer,ArrayList<Integer>> centralesClientes= new HashMap<Integer,ArrayList<Integer>>();
        for(int i=0;i<centrales.size();++i){
            centralesClientes.put(centrales.get(i).getId(),new ArrayList<Integer>());
        }
        for(int i=0;i<relaciones.getClientes().size();++i){
            if(relaciones.getClientes().get(i)!=-1){
                ArrayList<Integer> lista = centralesClientes.get(relaciones.getClientes().get(i));
                lista.add(i);
                centralesClientes.put(relaciones.getClientes().get(i),lista);
            }
        }
        ArrayList<Pair> aux = new ArrayList<>();
        for (int i = 0; i < centrales.size(); ++i) {
            for (int j = i+1; j < centrales.size(); ++j) {
                if(centrales.get(i).getTipo()==centrales.get(j).getTipo()) {
                    if (canSwapCentral(centrales.get(i), centrales.get(j), centralesClientes.get(i), centralesClientes.get(j))) {
                        Pair a = new Pair(i, j);
                        aux.add(a);
                    }
                }
            }
        }


        return aux;
    }

    public boolean canSwapCentral(Central central1, Central central2, ArrayList<Integer> clientes1, ArrayList<Integer> clientes2) {
        double consumo1=0;
        double consumo2=0;

        //double mwUsados1 = relaciones.getMWUsadosCentral(central1.getId());
        //double mwUsados2 = relaciones.getMWUsadosCentral(central2.getId());

        for(int i=0;i<clientes1.size();++i){
            consumo1=consumo1+((1+VEnergia.getPerdida(central2.getCoordX(),central2.getCoordY(),
                    clientes.get(clientes1.get(i)).getCoordX(),clientes.get(clientes1.get(i)).getCoordY())))*clientes.get(clientes1.get(i)).getConsumo();
        }
        if(consumo1>central2.getProduccion())return false;

        for(int i=0;i<clientes2.size();++i){
            consumo2=consumo2+((1+VEnergia.getPerdida(central1.getCoordX(),central1.getCoordY(),
                    clientes.get(clientes2.get(i)).getCoordX(),clientes.get(clientes2.get(i)).getCoordY())))*clientes.get(clientes2.get(i)).getConsumo();
        }
        if(consumo2>central1.getProduccion())return false;
        return true;
    }

    public boolean canSwapCentralAndMakesSense(Central central1, Central central2, ArrayList<Integer> clientes1, ArrayList<Integer> clientes2) {
        double consumo1=0;
        double consumo2=0;

        double mwUsados1 = relaciones.getMWUsadosCentral(central1.getId());
        double mwUsados2 = relaciones.getMWUsadosCentral(central2.getId());

        for(int i=0;i<clientes1.size();++i){
            consumo1=consumo1+((1+VEnergia.getPerdida(central2.getCoordX(),central2.getCoordY(),
                    clientes.get(clientes1.get(i)).getCoordX(),clientes.get(clientes1.get(i)).getCoordY())))*clientes.get(clientes1.get(i)).getConsumo();
        }
        if(consumo1>central2.getProduccion())return false;

        for(int i=0;i<clientes2.size();++i){
            consumo2=consumo2+((1+VEnergia.getPerdida(central1.getCoordX(),central1.getCoordY(),
                    clientes.get(clientes2.get(i)).getCoordX(),clientes.get(clientes2.get(i)).getCoordY())))*clientes.get(clientes2.get(i)).getConsumo();
        }
        if(consumo2>central1.getProduccion())return false;
                        //So that we only generate the successor if the assignation reduces the energy loss
        return ((mwUsados1+mwUsados2)>(consumo1+consumo2));
    }

    public boolean makesSense(Cliente cliente, Central centralNueva, Central centralVieja) {
        double perdida1 = VEnergia.getPerdida(cliente.getCoordX(),cliente.getCoordY(),centralNueva.getCoordX(),centralNueva.getCoordY());
        double perdida2 = VEnergia.getPerdida(cliente.getCoordX(),cliente.getCoordY(),centralVieja.getCoordX(),centralVieja.getCoordY());
        return perdida1<perdida2;
    }

    public boolean canShutDownCentral(Central central1, Central central2, ArrayList<Integer> clientes1, ArrayList<Integer> clientes2) {
        double consumo1=0;
        for(int i=0;i<clientes1.size();++i){
            if(clientes.get(clientes1.get(i)).isGuaranteed())
                consumo1=consumo1+((1+VEnergia.getPerdida(central2.getCoordX(),central2.getCoordY(),
                    clientes.get(clientes1.get(i)).getCoordX(),clientes.get(clientes1.get(i)).getCoordY())))*clientes.get(clientes1.get(i)).getConsumo();
        }
        if(consumo1>central2.getProduccion())return false;

        for(int i=0;i<clientes2.size();++i){
            if(clientes.get(clientes2.get(i)).isGuaranteed())
                consumo1=consumo1+((1+VEnergia.getPerdida(central2.getCoordX(),central2.getCoordY(),
                    clientes.get(clientes2.get(i)).getCoordX(),clientes.get(clientes2.get(i)).getCoordY())))*clientes.get(clientes2.get(i)).getConsumo();
        }
        return consumo1<central2.getProduccion();
    }

    public long getGeneracion() {
        return generacion;
    }
}
