package org.pitest.mutationtest.engine.gregor.mutators.collections;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;

import java.util.ArrayList;
import java.util.Collection;

import static org.objectweb.asm.Opcodes.ASM6;

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
        return "ADD_ALL_COLLECTION_MUTATOR";
    }

    private static class AddAllCollectionVisitor extends MethodVisitor {

        private final MutationContext context;
        private final MethodMutatorFactory factory;
        private MethodNode methodNode;
        private boolean wasMutated;
        private int indexOfInvokedMethod;

        public AddAllCollectionVisitor(final MutationContext context, final MethodVisitor methodVisitor,
                                       final MethodMutatorFactory methodMutatorFactory) {
            super(ASM6, methodVisitor);
            this.context = context;
            this.factory = methodMutatorFactory;
            methodNode = new MethodNode(ASM6);
            methodNode.localVariables = new ArrayList();
            wasMutated = false;
        }

        @Override
        public void visitParameter(String s, int i) {
            methodNode.visitParameter(s, i);
        }

        @Override
        public AnnotationVisitor visitAnnotationDefault() {
            return methodNode.visitAnnotationDefault();
        }

        @Override
        public AnnotationVisitor visitLocalVariableAnnotation(int i, TypePath typePath, Label[] labels, Label[] labels1, int[] ints, String s, boolean b) {
            return methodNode.visitLocalVariableAnnotation(i, typePath, labels, labels1, ints, s, b);
        }

        @Override
        public void visitEnd() {
            if (wasMutated) {
                mutate();
            }
            methodNode.accept(this.mv);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String s, boolean b) {
            return methodNode.visitAnnotation(s, b);
        }

        @Override
        public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String s, boolean b) {
            return methodNode.visitTypeAnnotation(i, typePath, s, b);
        }

        @Override
        public AnnotationVisitor visitParameterAnnotation(int i, String s, boolean b) {
            return methodNode.visitParameterAnnotation(i, s, b);
        }

        @Override
        public void visitAttribute(Attribute attribute) {
            methodNode.visitAttribute(attribute);
        }

        @Override
        public void visitCode() {
            methodNode.visitCode();
        }

        @Override
        public void visitFrame(int i, int i1, Object[] objects, int i2, Object[] objects1) {
            methodNode.visitFrame(i, i1, objects, i2, objects1);
        }

        @Override
        public void visitInsn(int i) {
            methodNode.visitInsn(i);
        }

        @Override
        public void visitIntInsn(int i, int i1) {
            methodNode.visitIntInsn(i, i1);
        }

        @Override
        public void visitVarInsn(int i, int i1) {
            methodNode.visitVarInsn(i, i1);
        }

        @Override
        public void visitTypeInsn(int i, String s) {
            methodNode.visitTypeInsn(i, s);
        }

        @Override
        public void visitFieldInsn(int i, String s, String s1, String s2) {
            methodNode.visitFieldInsn(i, s, s1, s2);
        }

        @Override
        public void visitInvokeDynamicInsn(String s, String s1, Handle handle, Object... objects) {
            methodNode.visitInvokeDynamicInsn(s, s1, handle, objects);
        }

        @Override
        public void visitJumpInsn(int i, Label label) {
            methodNode.visitJumpInsn(i, label);
        }

        @Override
        public void visitLabel(Label label) {
            methodNode.visitLabel(label);
        }

        @Override
        public void visitLdcInsn(Object o) {
            methodNode.visitLdcInsn(o);
        }

        @Override
        public void visitIincInsn(int i, int i1) {
            methodNode.visitIincInsn(i, i1);
        }

        @Override
        public void visitTableSwitchInsn(int i, int i1, Label label, Label... labels) {
            methodNode.visitTableSwitchInsn(i, i1, label, labels);
        }

        @Override
        public void visitLookupSwitchInsn(Label label, int[] ints, Label[] labels) {
            methodNode.visitLookupSwitchInsn(label, ints, labels);
        }

        @Override
        public void visitMultiANewArrayInsn(String s, int i) {
            methodNode.visitMultiANewArrayInsn(s, i);
        }

        @Override
        public AnnotationVisitor visitInsnAnnotation(int i, TypePath typePath, String s, boolean b) {
            return methodNode.visitInsnAnnotation(i, typePath, s, b);
        }

        @Override
        public void visitTryCatchBlock(Label label, Label label1, Label label2, String s) {
            methodNode.visitTryCatchBlock(label, label1, label2, s);
        }

        @Override
        public AnnotationVisitor visitTryCatchAnnotation(int i, TypePath typePath, String s, boolean b) {
            return methodNode.visitTryCatchAnnotation(i, typePath, s, b);
        }

        @Override
        public void visitLocalVariable(String s, String s1, String s2, Label label, Label label1, int i) {
            methodNode.visitLocalVariable(s, s1, s2, label, label1, i);
        }

        @Override
        public void visitLineNumber(int i, Label label) {
            methodNode.visitLineNumber(i, label);
        }

        @Override
        public void visitMaxs(int i, int i1) {
            methodNode.visitMaxs(i, i1);
        }

        @Override
        public void visitMethodInsn(int instructionCode, String returnType, String methodName, String argumentsDescription,
                                    boolean isInterfaceMethod) {
            if (isMutable(returnType, methodName)) {
                String mutationDescription = String.format("Replaced argument for addAll with empty collection for %s", returnType);
                MutationIdentifier mutationIdentifier = this.context
                        .registerMutation(this.factory, mutationDescription);
                if (this.context.shouldMutate(mutationIdentifier)) {
                    indexOfInvokedMethod = methodNode.instructions.size();
                    wasMutated = true;
                }
            }
            methodNode.visitMethodInsn(instructionCode, returnType, methodName, argumentsDescription, isInterfaceMethod);
        }

        private void mutate() {
            AbstractInsnNode invokingAddAllMethodInstruction = methodNode.instructions.get(indexOfInvokedMethod);
            MethodInsnNode emptyListMethodCallNode = new MethodInsnNode(Opcodes.INVOKESTATIC, "java/util/Collections",
                    "emptyList", "()Ljava/util/List;", false);
            insertEmptyListCall(invokingAddAllMethodInstruction,emptyListMethodCallNode);
            if (emptyListMethodCallNode.getPrevious() instanceof FieldInsnNode) {
                methodNode.instructions.remove(emptyListMethodCallNode.getPrevious());
                methodNode.instructions.remove(emptyListMethodCallNode.getPrevious());
            } else {
                methodNode.instructions.insertBefore(emptyListMethodCallNode, new InsnNode(Opcodes.POP));
            }
        }

        private void insertEmptyListCall(AbstractInsnNode invokingMethodNode, MethodInsnNode emptyListNode) {
            methodNode.instructions.insertBefore(invokingMethodNode, emptyListNode);
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
