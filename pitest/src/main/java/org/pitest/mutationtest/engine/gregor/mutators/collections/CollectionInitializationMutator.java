package org.pitest.mutationtest.engine.gregor.mutators.collections;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

import java.util.Collection;

public enum CollectionInitializationMutator implements MethodMutatorFactory {

    COLLECTION_INITIALIZATION_MUTATOR {
        @Override
        public MethodVisitor create(MutationContext context, MethodInfo methodInfo, MethodVisitor methodVisitor) {
            return new CollectionInitializationMutatorVisitor(context, methodVisitor, this);
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

class CollectionInitializationMutatorVisitor extends MethodVisitor {

    private final MethodMutatorFactory factory;
    private final MutationContext context;

    public CollectionInitializationMutatorVisitor(final MutationContext context
        , final MethodVisitor visitor
        , final MethodMutatorFactory factory) {
        super(Opcodes.ASM6, visitor);
        this.factory = factory;
        this.context = context;
    }

    @Override
    public void visitMethodInsn(int instructionCode, String returnType, String methodName, String argumentsDescription,
        boolean isInterfaceMethod) {
        if (isMutable(returnType, methodName, argumentsDescription)) {
            String mutationDescription = String.format("Created no arg constructor for %s", returnType);
            MutationIdentifier mutationIdentifier = this.context
                .registerMutation(this.factory, mutationDescription);
            if (this.context.shouldMutate(mutationIdentifier)) {
                mutate(instructionCode,returnType,methodName,argumentsDescription,isInterfaceMethod);
            } else {
                this.mv.visitMethodInsn(instructionCode, returnType, methodName, argumentsDescription, isInterfaceMethod);
            }
        } else {
            this.mv.visitMethodInsn(instructionCode, returnType, methodName, argumentsDescription, isInterfaceMethod);
        }
    }

    private boolean isMutable(String returnType, String methodName, String argumentDescription) {
        return isReturnTypeAssignableToCollection(returnType)
            && MethodInfo.isConstructor(methodName)
            && !hasNoArgConstructor(argumentDescription);
    }

    private boolean isReturnTypeAssignableToCollection(String owner) {
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

    private void callWithNoArgConstructor(int opcode, String returnType, String methodName, String description, boolean b) {
        this.mv.visitMethodInsn(opcode,returnType,methodName,"()V",b);
    }
}
