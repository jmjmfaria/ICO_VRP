package main.java.com.ico.vrp.service;

import main.java.com.ico.vrp.model.Location;
import main.java.com.ico.vrp.model.Request;
import main.java.com.ico.vrp.model.VehicleRoutingProblem;
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

    public Location[] processRequest(Request request) {
        ISeq<Location> locations = ISeq.of(request.getLocations());
        Location warehouse = request.getWarehouse();
        VehicleRoutingProblem vrp = new VehicleRoutingProblem(warehouse, locations);
        Engine<EnumGene<Location>, Double> engine = Engine
                .builder(vrp)
                .optimize(Optimize.MINIMUM)
                .maximalPhenotypeAge(11)
                .populationSize(500)
                .alterers(
                        new SwapMutator<>(0.2),
                        new PartiallyMatchedCrossover<>(0.35)
                ).build();

        EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();

        Phenotype<EnumGene<Location>, Double> best = engine.stream()
                .limit(bySteadyFitness(25))
                .limit(250)
                .peek(statistics)
                .collect(toBestPhenotype());

        System.out.println(statistics);
        //System.out.println("Known min path length: " + minPathLength);
        System.out.println("Found min path length: " + best.fitness());

        System.out.println(best.genotype().geneCount());
        System.out.println(best.genotype().length());

        Iterator<EnumGene<Location>> itr = best.genotype().chromosome().iterator();
        Location[] path = new Location[best.genotype().chromosome().length()+2];

        for(int i = 0; i < path.length; i++) {
            if(i == 0 || i == path.length - 1) {
                path[i] = warehouse;
            } else {
                EnumGene<Location> gene = itr.next();
                path[i] = gene.allele();
            }
        }

        return path;
        //return new Response(path);
    }

}
