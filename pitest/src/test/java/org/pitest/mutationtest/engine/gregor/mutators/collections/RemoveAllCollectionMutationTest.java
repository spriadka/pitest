package org.pitest.mutationtest.engine.gregor.mutators.collections;

import org.junit.Before;
import org.junit.Test;
import org.pitest.mutationtest.engine.Mutant;
import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.mutationtest.engine.gregor.MutatorTestBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class RemoveAllCollectionMutationTest extends MutatorTestBase {

    @Before
    public void setUp() {
        createTesteeWith(new RemoveAllCollectionMutator());
    }

    private static class RemoveAllAnonymousCollection implements Callable<String> {

        private String[] elements;

        public RemoveAllAnonymousCollection(String... elements) {
            this.elements = elements;
        }


        @Override
        public String call() throws Exception {
            List<String> beforeRemovAll = Arrays.asList(elements);
            beforeRemovAll.removeAll(Arrays.asList(elements));
            return "" + beforeRemovAll;
        }
    }

    @Test
    public void shouldMutateAnonymousCollection() throws Exception {
        Collection<MutationDetails> mutationDetails = findMutationsFor(RemoveAllAnonymousCollection.class);
        final Mutant mutant = getFirstMutant(mutationDetails);
        final String[] elements = new String[]{"Hello","World"};
        final String expected = Arrays.asList(elements).toString();
        assertMutantCallableReturns(new RemoveAllAnonymousCollection(elements), mutant,
                expected);
    }

    private static class RemoveAllVariableReference implements Callable<String> {

        private String[] elements;

        public RemoveAllVariableReference(String... elements) {
            this.elements = elements;
        }

        @Override
        public String call() throws Exception {
            List<String> beforeRemoveAll = Arrays.asList(elements);
            List<String> variableReference = new ArrayList<String>(beforeRemoveAll);
            beforeRemoveAll.removeAll(variableReference);
            return "" + beforeRemoveAll;
        }
    }

    @Test
    public void shouldMutateCollectionPassesAsVariableReference() throws Exception {
        Collection<MutationDetails> mutationDetails = findMutationsFor(RemoveAllVariableReference.class);
        final Mutant mutant = getFirstMutant(mutationDetails);
        final String[] elements = new String[]{"Hello","World"};
        final String expected = Arrays.asList(elements).toString();
        assertMutantCallableReturns(new RemoveAllVariableReference(elements), mutant, expected);
    }

}
