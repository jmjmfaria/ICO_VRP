package main.java.com.ico.vrp.model;

import io.jenetics.*;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.lang.Math.hypot;

public class VehicleRoutingProblem implements Problem<ISeq<Visitable>, EnumGene<Visitable>, Double> {

    //private final ISeq<Customer> customers;
    private final Visitable warehouse;
    private final ISeq<Visitable> locationsToVisit;
    private final Vehicle[] vehicles;

    /*public VehicleRoutingProblem(Location warehouse, ISeq<Customer> customers) {
        this.warehouse = warehouse;
        this.customers = requireNonNull(customers);
    }*/

    public VehicleRoutingProblem(Visitable warehouse, Visitable[] customers, Vehicle[] vehicles) {
        this.warehouse = warehouse;
        List<Visitable> locations = new LinkedList(Arrays.asList(customers));
        //locations.add(new Warehouse(warehouse.getLatitude(), warehouse.getLongitude(), warehouse.getTimeWindow()));
        //locations.add(new Warehouse(warehouse.getLatitude(), warehouse.getLongitude(), new int[]{0,0}));
        this.locationsToVisit = ISeq.of(locations);
        this.vehicles = vehicles;
    }

    @Override
    public Function<ISeq<Visitable>, Double> fitness() {
        AtomicReference<Double> time = new AtomicReference<>((double) 0);

        return l -> IntStream.range(0, l.length() - 1)
                .mapToDouble(i -> {
                    Visitable l1;
                    Visitable l2;

                    /*Location l1 = l.get(i);
                    Location l2 = l.get(i+1);*/

                    if(i == 0 || i == l.length() - 1) {
                        l1 = warehouse;
                        l2 = l.get(i);
                    }
                    else {
                        l1 = l.get(i);
                        l2 = l.get(i+1);
                    }

                    double distance = distance(l1.getLatitude(),l1.getLongitude(), l2.getLatitude(), l2.getLongitude(), "K");
                    double distanceCost = vehicles[0].getCustoDist()*distance;

                    double travelTime = (distance/30)*60;

                    double penalty = 0;

                    if(i == 0) {
                        time.set(l2.getTimeWindow()[0]);
                    } else if((time.get() + travelTime) <= l2.getTimeWindow()[1]) {
                        time.set(time.get()+travelTime);
                    } else if((time.get() + travelTime) > l2.getTimeWindow()[1]) {
                        time.set(time.get()+travelTime);
                        penalty += 500;
                    }

                    double timeCost = time.get()*vehicles[0].getCustoHora();

                    return distanceCost + timeCost + penalty;
                }).sum();
    }

    @Override
    public Codec<ISeq<Visitable>, EnumGene<Visitable>> codec() {
        return Codecs.ofPermutation(locationsToVisit);
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

}
