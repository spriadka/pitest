package org.pitest.mutationtest.engine.gregor.mutators.collections;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnList;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

import java.util.Collection;

public enum EmptyCollectionMutator implements MethodMutatorFactory {

    EMPTY_COLLECTION_MUTATOR {
        @Override
        public MethodVisitor create(MutationContext context, MethodInfo methodInfo, MethodVisitor methodVisitor) {
            return new EmptyCollectionMutatorVisitor(context, methodVisitor, this);
        }
    };


    @Override
    public String getGloballyUniqueId() {
        return getClass().getName();
    }

    @Override
    public String getName() {
        return name();
    }
}

class EmptyCollectionMutatorVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    public EmptyCollectionMutatorVisitor(final MutationContext context
            , final MethodVisitor visitor
            , final MethodMutatorFactory factory) {
        super(Opcodes.ASM6, visitor);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitMethodInsn(int instruction, String owner, String methodName, String argumentsDescription, boolean b) {
        if (isMutable(owner, methodName, argumentsDescription)) {
            String mutationDescription = String.format("created no arg constructor for %s", owner);
            MutationIdentifier mutationIdentifier = this.context
                    .registerMutation(this.factory, mutationDescription);
            if (this.context.shouldMutate(mutationIdentifier)) {
                mutate(instruction,owner,methodName,argumentsDescription,b);
            } else {
                this.mv.visitMethodInsn(instruction, owner, methodName, argumentsDescription, b);
            }
        } else {
            this.mv.visitMethodInsn(instruction, owner, methodName, argumentsDescription, b);
        }
    }

    private boolean isMutable(String owner, String method, String argumentDescription) {
        return isOwnerAssignableToCollection(owner)
                && MethodInfo.isConstructor(method)
                && !hasNoArgConstructor(argumentDescription);
    }

    private boolean isOwnerAssignableToCollection(String owner) {
        String qualifiedName = owner.replace("/", ".");
        try {
            Class ownerClass = Class.forName(qualifiedName);
            return Collection.class.isAssignableFrom(ownerClass);
        } catch (ClassNotFoundException classNotFound) {
            return false;
        }
    }

    private boolean hasNoArgConstructor(String argumentsDescription) {
        return Type.getArgumentTypes(argumentsDescription).length == 0;
    }

    private void mutate(int opcode, String owner, String methodName, String description, boolean b) {
        popArgumentsFromConstructor();
        callWithNoArgConstructor(opcode,owner,methodName,description,b);
    }

    private void popArgumentsFromConstructor() {
        this.mv.visitInsn(Opcodes.POP);
    }

    private void callWithNoArgConstructor(int opcode, String owner, String methodName, String description, boolean b) {
        this.mv.visitMethodInsn(opcode,owner,methodName,"()V",b);
    }
}
