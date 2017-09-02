package org.pitest.mutationtest.engine.gregor.mutators.collections;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.TraceClassVisitor;
import org.pitest.mutationtest.engine.Mutant;
import org.pitest.mutationtest.engine.MutationDetails;
import org.pitest.mutationtest.engine.gregor.MutatorTestBase;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;

public class EmptyCollectionMutatorTest extends MutatorTestBase {

    @Before
    public void setUp() {
        createTesteeWith(EmptyCollectionMutator.EMPTY_COLLECTION_MUTATOR);
    }

    @Test
    public void shouldProvideMeaningfulMutatorName() {
        assertEquals("EMPTY_COLLECTION_MUTATOR", EmptyCollectionMutator.EMPTY_COLLECTION_MUTATOR.getName());
    }

    public static class HasNonEmptyStringCollection implements Callable<String> {

        private List<String> input;

        public HasNonEmptyStringCollection(String... elements) {
            input = new ArrayList<String>(Arrays.asList(elements));
        }

        @Override
        public String call() throws Exception {
            return "" + this.input;
        }
    }

    @Test
    public void shouldReplaceNonEmptyCollectionWithEmptyCollection() throws Exception {
        final Collection<MutationDetails> details = findMutationsFor(HasNonEmptyStringCollection.class);
        final Mutant mutant = getFirstMutant(details);
        printMutant(mutant);
        assertMutantCallableReturns(new HasNonEmptyStringCollection("Hello"), mutant, "[]");
    }

}
