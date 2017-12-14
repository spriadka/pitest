package org.pitest.mutationtest.engine.gregor.mutators.collections;

import org.junit.Before;
import org.junit.Test;
import org.pitest.mutationtest.engine.Mutant;
import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.mutationtest.engine.gregor.MutatorTestBase;

import java.util.*;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;

public class CollectionInitializationMutatorTest extends MutatorTestBase {

    @Before
    public void setUp() {
        createTesteeWith(CollectionInitializationMutator.COLLECTION_INITIALIZATION_MUTATOR);
    }

    @Test
    public void shouldProvideMeaningfulMutatorName() {
        assertEquals("COLLECTION_INITIALIZATION_MUTATOR", CollectionInitializationMutator.COLLECTION_INITIALIZATION_MUTATOR.getName());
    }

    private static class EmptyCollectionAnonymous implements Callable<String> {

        private String[] elements;

        public EmptyCollectionAnonymous(String... elements) {
            this.elements = elements;
        }

        @Override
        public String call() throws Exception {
            List<String> input = new ArrayList<String>(Arrays.asList(elements));
            return "" + input;
        }
    }

    @Test
    public void shouldMutateAnonymousCollectionReference() throws Exception {
        final Collection<MutationDetails> details = findMutationsFor(EmptyCollectionAnonymous.class);
        final Mutant mutant = getFirstMutant(details);
        assertMutantCallableReturns(new EmptyCollectionAnonymous("Hello"), mutant, "[]");
    }

    private static class EmptyCollectionVariableReference implements Callable<String> {

        private String[] elements;

        public EmptyCollectionVariableReference(String... elements) {
            this.elements = elements;
        }

        @Override
        public String call() throws Exception {
            List<String> elementsAsList = Arrays.asList(elements);
            List<String> output = new ArrayList<String>(elementsAsList);
            return "" + output;
        }
    }

    @Test
    public void shouldMutateCollectionAsVariableReference() throws Exception {
        final Collection<MutationDetails> mutationDetails = findMutationsFor(EmptyCollectionVariableReference.class);
        final Mutant mutant = getFirstMutant(mutationDetails);
        assertMutantCallableReturns(new EmptyCollectionVariableReference("Hello"), mutant, "[]");
    }

}
