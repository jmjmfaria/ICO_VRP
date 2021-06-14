package main.java.com.ico.vrp.model;

import io.jenetics.EnumGene;
import io.jenetics.Phenotype;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Constraint;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.lang.Math.hypot;
import static java.util.Objects.requireNonNull;

public class VehicleRoutingProblem implements Problem<ISeq<Visitable>, EnumGene<Visitable>, Double> {

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
        //locations.add(warehouse);
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

}
