package main.java.com.ico.vrp.model;

import io.jenetics.*;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Constraint;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.lang.Math.hypot;

public class VehicleRoutingProblem implements Problem<ISeq<Visitable>, EnumGene<Visitable>, Double>, Constraint<EnumGene<Visitable>, Double> {

    //private final ISeq<Customer> customers;
    private final Location warehouse;
    private final ISeq<Visitable> locationsToVisit;

    /*public VehicleRoutingProblem(Location warehouse, ISeq<Customer> customers) {
        this.warehouse = warehouse;
        this.customers = requireNonNull(customers);
    }*/

    public VehicleRoutingProblem(Visitable warehouse, Visitable[] customers) {
        this.warehouse = warehouse;
        List<Visitable> locations = new LinkedList(Arrays.asList(customers));
        locations.add(warehouse);
        locations.add(new Visitable(warehouse.getLatitude(), warehouse.getLongitude(), new int[]{0,0}));
        this.locationsToVisit = ISeq.of(locations);
    }

    @Override
    public Function<ISeq<Visitable>, Double> fitness() {
        return l -> IntStream.range(0, l.length())
                .mapToDouble(i -> {
                    Location l1;
                    Location l2;

                    if(i == 0 || i == l.length() - 1) {
                        l1 = warehouse;
                        l2 = l.get(i);
                    }
                    else {
                        l1 = l.get(i);
                        l2 = l.get(i+1);
                    }

                    return hypot(l1.getLatitude() - l2.getLatitude(), l1.getLongitude() - l2.getLongitude());
                }).sum();
    }

    @Override
    public Codec<ISeq<Visitable>, EnumGene<Visitable>> codec() {
        return Codecs.ofPermutation(locationsToVisit);
    }

    @Override
    public boolean test(Phenotype<EnumGene<Visitable>, Double> phenotype) {
        Iterator<EnumGene<Visitable>> itr = phenotype.genotype().chromosome().iterator();
        boolean first = true;

        while (itr.hasNext()) {
            Visitable location = itr.next().allele();

            if (first && (location.getLatitude() != warehouse.getLatitude() || location.getLongitude() != warehouse.getLongitude())) {
                return false;
            } else if (first) {
                first = false;
            } else if (!itr.hasNext() && (location.getLatitude() != warehouse.getLatitude() || location.getLongitude() != warehouse.getLongitude())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Phenotype<EnumGene<Visitable>, Double> repair(Phenotype<EnumGene<Visitable>, Double> phenotype, long generation) {
        return Phenotype.of(
                Genotype.of(
                        PermutationChromosome.of(
                                ISeq.of(repair((Visitable[]) phenotype.genotype().chromosome().stream().toArray()))
                        )
                ), generation
        );
    }

    private Visitable[] repair(Visitable[] toRepair) {
        Visitable first = toRepair[0];
        Visitable last = toRepair[toRepair.length - 1];

        if (first.getLatitude() != warehouse.getLatitude() || first.getLongitude() != warehouse.getLongitude()) {
            for (int i = 0; i <= toRepair.length; i++) {
                Visitable aux = toRepair[i];

                if (aux.getLatitude() == warehouse.getLatitude() && aux.getLongitude() == warehouse.getLongitude()) {
                    toRepair[0] = aux;
                    toRepair[i] = first;
                }
            }
        }

        if (last.getLatitude() != warehouse.getLatitude() || last.getLongitude() != warehouse.getLongitude()) {
            for (int i = 0; i <= toRepair.length; i++) {
                Visitable aux = toRepair[i];

                if (aux.getLatitude() == warehouse.getLatitude() && aux.getLongitude() == warehouse.getLongitude()) {
                    toRepair[toRepair.length - 1] = aux;
                    toRepair[i] = first;
                }
            }
        }

        return toRepair;
    }

}
