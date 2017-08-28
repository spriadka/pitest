package org.pitest.mutationtest.engine.gregor.mutators.collections;

import org.objectweb.asm.MethodVisitor;
import org.pitest.mutationtest.engine.gregor.AbstractJumpMutator;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

import java.util.HashMap;
import java.util.Map;

public enum EmptyCollectionMutator implements MethodMutatorFactory {

    EMPTY_COLLECTION_MUTATOR;

    @Override
    public MethodVisitor create(final MutationContext context, final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
        return new EmptyCollectionMutatorVisitor(this,context,methodVisitor);
    }

    @Override
    public String getGloballyUniqueId() {
        return getClass().getName();
    }

    @Override
    public String getName() {
        return name();
    }
}

class EmptyCollectionMutatorVisitor extends AbstractJumpMutator {

    private static final String DESCRIPTION = "changed collection to empty collection";
    private static final Map<Integer,Substitution> MUTATIONS = new HashMap<Integer, Substitution>();

    public EmptyCollectionMutatorVisitor(MethodMutatorFactory factory, MutationContext context, MethodVisitor writer) {
        super(factory, context, writer);
    }

    @Override
    protected Map<Integer, Substitution> getMutations() {
        return MUTATIONS;
    }
}
