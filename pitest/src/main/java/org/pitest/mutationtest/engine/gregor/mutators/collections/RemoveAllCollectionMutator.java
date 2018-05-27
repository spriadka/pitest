package org.pitest.mutationtest.engine.gregor.mutators.collections;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

import java.util.Collection;

import static org.objectweb.asm.Opcodes.ASM6;

public class RemoveAllCollectionMutator implements MethodMutatorFactory {
    @Override
    public MethodVisitor create(MutationContext context, MethodInfo methodInfo, MethodVisitor methodVisitor) {
        return new RemoveAllCollectionVisitor(context, this, methodVisitor);
    }

    @Override
    public String getGloballyUniqueId() {
        return getClass().getName();
    }

    @Override
    public String getName() {
        return "COLLECTION_REMOVE_ALL_MUTATOR";
    }

    private static class RemoveAllCollectionVisitor extends MethodVisitor {

        private static final String REMOVE_ALL_METHOD = "removeAll";

        private MutationContext context;
        private MethodMutatorFactory factory;

        public RemoveAllCollectionVisitor(final MutationContext mutationContext,
            final MethodMutatorFactory factory, final MethodVisitor visitor) {
            super(ASM6, visitor);
            this.context = mutationContext;
            this.factory = factory;
        }

        @Override
        public void visitMethodInsn(int instructionCode, String owner, String methodName,
            String argumentsDescription, boolean isInterfaceMethod) {
            if (shouldMutate(owner, methodName)) {
                String mutationDescription = "Replaced removeAll argument with empty collection";
                final MutationIdentifier identifier = this.context.registerMutation(this.factory, mutationDescription);
                if (this.context.shouldMutate(identifier)) {
                    mutate(instructionCode, owner, methodName, argumentsDescription, isInterfaceMethod);
                } else {
                    this.mv.visitMethodInsn(instructionCode, owner, methodName, argumentsDescription, isInterfaceMethod);
                }
            } else {
                this.mv.visitMethodInsn(instructionCode, owner, methodName, argumentsDescription, isInterfaceMethod);
            }
        }

        private void mutate(int instructionCode, String owner, String methodName, String argumentsDescription,
            boolean isInterfaceMethod) {
            popArgumentFromMethodCall();
            this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/Collections",
                "emptyList", "()Ljava/util/List;", false);
            this.mv.visitMethodInsn(instructionCode, owner, methodName, argumentsDescription, isInterfaceMethod);
        }

        private void popArgumentFromMethodCall() {
            this.mv.visitInsn(Opcodes.POP);
        }

        private boolean shouldMutate(String owner, String methodName) {
            return isOwnerAssignableToCollection(owner) && methodName.equals(REMOVE_ALL_METHOD);
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
    }
}