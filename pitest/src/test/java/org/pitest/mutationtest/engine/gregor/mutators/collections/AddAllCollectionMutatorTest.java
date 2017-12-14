package org.pitest.mutationtest.engine.gregor.mutators.collections;

import org.junit.Before;
import org.junit.Test;
import org.pitest.mutationtest.engine.Mutant;
import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.mutationtest.engine.gregor.MutatorTestBase;

import java.util.*;
import java.util.concurrent.Callable;

public class AddAllCollectionMutatorTest extends MutatorTestBase {

    @Before
    public void setUp() {
        createTesteeWith(new AddAllCollectionMutator());
    }

    private static class AddAllVariable implements Callable<String> {

        private List<String> toBeAdded;

        public AddAllVariable(String... elements) {
            toBeAdded = Arrays.asList(elements);
        }

        @Override
        public String call() throws Exception {
            List<String> output = new ArrayList<String>();
            output.addAll(toBeAdded);
            return "" + output;
        }
    }

    @Test
    public void shouldMutateAddAllWithCollectionVariableReference() throws Exception {
        Collection<MutationDetails> mutationDetails = findMutationsFor(AddAllVariable.class);
        Mutant mutant = getFirstMutant(mutationDetails);
        assertMutantCallableReturns(new AddAllVariable("Hello","World"), mutant, "[]");
    }

    private static class AddAllAnonymous implements Callable<String> {

        private String[] elements;

        public AddAllAnonymous(String... elements) {
            this.elements = elements;
        }

        @Override
        public String call() throws Exception {
            List<String> output = new ArrayList<String>();
            output.addAll(Arrays.asList(elements));
            return "" + output;
        }
    }

    @Test
    public void shouldMutateAddAllWithAnonymousCollectionReference() throws Exception {
        Collection<MutationDetails> mutationDetails = findMutationsFor(AddAllAnonymous.class);
        Mutant mutant = getFirstMutant(mutationDetails);
        assertMutantCallableReturns(new AddAllAnonymous("Hello","World"), mutant, "[]");
    }

}
