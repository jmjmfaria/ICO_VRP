package com.ico.vrp.model;

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

    public VehicleRoutingProblem(ISeq<Location> locations) {
        this.locations = requireNonNull(locations);
    }

    @Override
    public Function<ISeq<Location>, Double> fitness() {
        return l -> IntStream.range(1, l.length())
                .mapToDouble(i -> {
                    final Location l1 = l.get(i-1);
                    final Location l2 = l.get(i);
                    return hypot(l1.getLatitude() - l2.getLatitude(), l1.getLongitude() - l2.getLongitude());
                }).sum();
    }

    @Override
    public Codec<ISeq<Location>, EnumGene<Location>> codec() {
        return Codecs.ofPermutation(locations);
    }

}
