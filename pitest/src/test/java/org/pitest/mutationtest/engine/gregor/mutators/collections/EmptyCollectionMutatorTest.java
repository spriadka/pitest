package org.pitest.mutationtest.engine.gregor.mutators.collections;

import org.junit.Test;
import org.pitest.mutationtest.engine.gregor.MutatorTestBase;

import static org.junit.Assert.assertEquals;

public class EmptyCollectionMutatorTest extends MutatorTestBase{

    @Test
    public void shouldProvideMeaningfulMutatorName(){
        assertEquals("EMPTY_COLLECTION_MUTATOR", EmptyCollectionMutator.EMPTY_COLLECTION_MUTATOR.getName());
    }

}
