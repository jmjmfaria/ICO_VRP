package main.java.com.ico.vrp.model;

import io.jenetics.EnumGene;
import io.jenetics.Phenotype;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Constraint;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

import java.util.function.Function;
import java.util.stream.IntStream;

import static java.lang.Math.hypot;
import static java.util.Objects.requireNonNull;

public class VehicleRoutingProblem implements Problem<ISeq<Customer>, EnumGene<Customer>, Double> {

    private final ISeq<Customer> customers;
    private final Location warehouse;

    public VehicleRoutingProblem(Location warehouse, ISeq<Customer> customers) {
        this.warehouse = warehouse;
        this.customers = requireNonNull(customers);
    }

    @Override
    public Function<ISeq<Customer>, Double> fitness() {
        return l -> IntStream.range(0, l.length())
                .mapToDouble(i -> {
                    Location l1;
                    Location l2;

                    if(i == 0 || i == l.length() - 1) {
                        l1 = warehouse;
                        l2 = l.get(i).getLocation();
                    }
                    else {
                        l1 = l.get(i).getLocation();
                        l2 = l.get(i+1).getLocation();
                    }

                    return hypot(l1.getLatitude() - l2.getLatitude(), l1.getLongitude() - l2.getLongitude());
                }).sum();
    }

    @Override
    public Codec<ISeq<Customer>, EnumGene<Customer>> codec() {
        return Codecs.ofPermutation(customers);
    }

}
