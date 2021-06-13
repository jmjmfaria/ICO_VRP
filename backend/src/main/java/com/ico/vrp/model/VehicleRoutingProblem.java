package main.java.com.ico.vrp.model;

import io.jenetics.EnumGene;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

import java.util.function.Function;
import java.util.stream.IntStream;

import static java.lang.Math.hypot;
import static java.util.Objects.requireNonNull;

public class VehicleRoutingProblem implements Problem<ISeq<Location>, EnumGene<Location>, Double> {

    private final ISeq<Location> locations;
    private final Location warehouse;

    public VehicleRoutingProblem(Location warehouse, ISeq<Location> locations) {
        this.warehouse = warehouse;
        this.locations = requireNonNull(locations);
    }

    @Override
    public Function<ISeq<Location>, Double> fitness() {
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

                    /*final Location l1 = l.get(i-1);
                    final Location l2 = l.get(i);*/
                    return hypot(l1.getLatitude() - l2.getLatitude(), l1.getLongitude() - l2.getLongitude());
                }).sum();
    }

    @Override
    public Codec<ISeq<Location>, EnumGene<Location>> codec() {
        return Codecs.ofPermutation(locations);
    }

}
