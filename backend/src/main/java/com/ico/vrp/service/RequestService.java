package main.java.com.ico.vrp.service;

import main.java.com.ico.vrp.model.*;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.Limits.bySteadyFitness;
import io.jenetics.util.ISeq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Iterator;

@Service
public class RequestService {

    @Autowired
    public RequestService() {}

    public FullResponse processRequest(Request request) {
        FullResponse finalResult = new FullResponse();

        ISeq<Customer> customers = ISeq.of(request.getClientes());
        Location warehouse = request.getWarehouse();
        VehicleRoutingProblem vrp = new VehicleRoutingProblem(warehouse, customers);
        Engine<EnumGene<Customer>, Double> engine = Engine
                .builder(vrp)
                .optimize(Optimize.MINIMUM)
                .maximalPhenotypeAge(11)
                .populationSize(500)
                .alterers(
                        new SwapMutator<>(0.2),
                        new PartiallyMatchedCrossover<>(0.35)
                ).build();

        EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();

        Phenotype<EnumGene<Customer>, Double> best = engine.stream()
                .limit(bySteadyFitness(25))
                .limit(250)
                .peek(statistics)
                .collect(toBestPhenotype());

        System.out.println(statistics);
        //System.out.println("Known min path length: " + minPathLength);
        System.out.println("Found min path length: " + best.fitness());

        System.out.println(best.genotype().geneCount());
        System.out.println(best.genotype().length());

        Iterator<EnumGene<Customer>> itr = best.genotype().chromosome().iterator();
        Location[] path = new Location[best.genotype().chromosome().length()+2];

        for(int i = 0; i < path.length; i++) {
            if(i == 0 || i == path.length - 1) {
                path[i] = warehouse;
            } else {
                EnumGene<Customer> gene = itr.next();
                path[i] = gene.allele().getLocation();
            }
        }


        // Codigo de Exemplo

        // Varios Clientes
        SingleResponse c1 = new SingleResponse(request.getVeiculos()[0].getId(), path);
        SingleResponse c2 = new SingleResponse(request.getVeiculos()[0].getId(), path);

        finalResult.addResult(c1);
        finalResult.addResult(c2);

        return finalResult;
    }

}
