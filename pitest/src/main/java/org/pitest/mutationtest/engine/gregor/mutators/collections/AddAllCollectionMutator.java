package org.pitest.mutationtest.engine.gregor.mutators.collections;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

import java.util.Collection;

import static org.objectweb.asm.Opcodes.ASM6;
import static org.objectweb.asm.Opcodes.POP;

public class AddAllCollectionMutator implements MethodMutatorFactory {

    private static final String ADD_ALL_METHOD_NAME = "addAll";

    @Override
    public MethodVisitor create(MutationContext context, MethodInfo methodInfo, MethodVisitor methodVisitor) {
        return new AddAllCollectionVisitor(context, methodVisitor, this);
    }

    @Override
    public String getGloballyUniqueId() {
        return getClass().getName();
    }

    @Override
    public String getName() {
        return "COLLECTION_ADD_ALL_MUTATOR";
    }

    private static class AddAllCollectionVisitor extends MethodVisitor {

        private final MutationContext context;
        private final MethodMutatorFactory factory;

        public AddAllCollectionVisitor(final MutationContext context, final MethodVisitor methodVisitor,
                                       final MethodMutatorFactory methodMutatorFactory) {
            super(ASM6, methodVisitor);
            this.context = context;
            this.factory = methodMutatorFactory;
        }

        @Override
        public void visitMethodInsn(int instructionCode, String returnType, String methodName,
                                    String argumentsDescription, boolean isInterfaceMethod) {
            if (isMutable(returnType, methodName)) {
                String mutationDescription = String.format("Replaced addAll() parameter with empty collection : %s",
                        returnType);
                final MutationIdentifier mutationIdentifier = this.context.registerMutation(this.factory,
                        mutationDescription);
                if (this.context.shouldMutate(mutationIdentifier)) {
                    mutate(instructionCode, returnType, methodName, argumentsDescription, isInterfaceMethod);
                } else {
                    this.mv.visitMethodInsn(instructionCode, returnType, methodName, argumentsDescription, isInterfaceMethod);
                }
            } else {
                this.mv.visitMethodInsn(instructionCode, returnType, methodName,
                        argumentsDescription, isInterfaceMethod);
            }
        }

        private void mutate(int instructionCode, String returnType, String methodName,
                            String argumentsDescription, boolean isInterface) {
            popArgument();
            invokeEmptyListMethod();
            this.mv.visitMethodInsn(instructionCode, returnType, methodName, argumentsDescription, isInterface);
        }

        private void invokeEmptyListMethod() {
            this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/Collections",
                    "emptyList", "()Ljava/util/List;", false);
        }

        private void popArgument() {
            this.mv.visitInsn(POP);
        }

        private boolean isMutable(String returnType, String methodName) {
            return isReturnTypeAssignableToCollection(returnType) && isAddAllMethod(methodName);
        }

        private boolean isAddAllMethod(String methodName) {
            return methodName.equals(ADD_ALL_METHOD_NAME);
        }

        private boolean isReturnTypeAssignableToCollection(String returnType) {
            String qualifiedName = returnType.replace("/", ".");
            try {
                Class ownerClass = Class.forName(qualifiedName);
                return Collection.class.isAssignableFrom(ownerClass);
            } catch (ClassNotFoundException classNotFound) {
                return false;
            }
        }

    }
}
