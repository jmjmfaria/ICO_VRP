package main.java.com.ico.vrp.model;

import io.jenetics.EnumGene;
import io.jenetics.Genotype;
import io.jenetics.PermutationChromosome;
import io.jenetics.Phenotype;
import io.jenetics.engine.Constraint;
import io.jenetics.util.ISeq;

import java.util.Iterator;

public class VRPConstraint implements Constraint<EnumGene<Visitable>, Double> {


    @Override
    public boolean test(Phenotype<EnumGene<Visitable>, Double> phenotype) {
        Iterator<EnumGene<Visitable>> itr = phenotype.genotype().chromosome().iterator();
        boolean first = true;

        while (itr.hasNext()) {
            Visitable location = itr.next().allele();

            if (first && !(location instanceof Warehouse)) {
                return false;
            } else if (first) {
                first = false;
            } else if (!itr.hasNext() && !(location instanceof Warehouse)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Phenotype<EnumGene<Visitable>, Double> repair(Phenotype<EnumGene<Visitable>, Double> phenotype, long generation) {
        Visitable[] toVisit = new Visitable[phenotype.genotype().chromosome().length()];
        Iterator<EnumGene<Visitable>> itr = phenotype.genotype().chromosome().iterator();

        for(int i = 0; i < toVisit.length; i++) {
            toVisit[i] = itr.next().allele();
        }

        Phenotype<EnumGene<Visitable>, Double> repairedPhenotype = Phenotype.of(
                Genotype.of(
                        PermutationChromosome.of(
                                ISeq.of(repair(toVisit))
                        )
                ), generation
        );

        System.out.println(generation);

        return repairedPhenotype;
    }

    private static Visitable[] repair(Visitable[] toRepair) {
        Visitable first = toRepair[0];
        Visitable last = toRepair[toRepair.length - 1];

        if (!(first instanceof Warehouse)) {
            System.out.println("-------------------------ENTROU------------------------");
            for (int i = 0; i < toRepair.length; i++) {
                Visitable aux = toRepair[i];

                if (aux instanceof Warehouse) {
                    toRepair[0] = aux;
                    toRepair[i] = first;
                    break;
                }
            }
        }

        if (!(last instanceof Warehouse)) {
            for (int i = 1; i < toRepair.length; i++) {
                Visitable aux = toRepair[i];

                if (aux instanceof Warehouse) {
                    toRepair[toRepair.length - 1] = aux;
                    toRepair[i] = last;
                    break;
                }
            }
        }

        return toRepair;
    }
}
