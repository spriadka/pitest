package asm.org.pitest.mutationtest.engine.gregor.mutators.collections;

import java.io.FileOutputStream;
import org.objectweb.asm.*;

public class CollectionTestClassDump implements Opcodes {

    public static void dump(String className) throws Exception {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(V1_6, ACC_PUBLIC + ACC_SUPER, "org/pitest/mutationtest/engine/gregor/mutators/collections/EmptyCollectionMutatorTest$HasNonEmptyStringCollection", "Ljava/lang/Object;Ljava/util/concurrent/Callable<Ljava/lang/String;>;", "java/lang/Object", new String[] { "java/util/concurrent/Callable" });

        cw.visitSource("EmptyCollectionMutatorTest.java", null);

        cw.visitInnerClass("org/pitest/mutationtest/engine/gregor/mutators/collections/EmptyCollectionMutatorTest$HasNonEmptyStringCollection", "org/pitest/mutationtest/engine/gregor/mutators/collections/EmptyCollectionMutatorTest", "HasNonEmptyStringCollection", ACC_PUBLIC + ACC_STATIC);

        {
            fv = cw.visitField(ACC_PRIVATE, "input", "Ljava/util/List;", "Ljava/util/List<Ljava/lang/String;>;", null);
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_VARARGS, "<init>", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(35, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(36, l1);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(NEW, "java/util/ArrayList");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "asList", "([Ljava/lang/Object;)Ljava/util/List;", false);
            mv.visitInsn(POP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
            mv.visitFieldInsn(PUTFIELD, "org/pitest/mutationtest/engine/gregor/mutators/collections/EmptyCollectionMutatorTest$HasNonEmptyStringCollection", "input", "Ljava/util/List;");
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLineNumber(37, l2);
            mv.visitInsn(RETURN);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLocalVariable("this", "Lorg/pitest/mutationtest/engine/gregor/mutators/collections/EmptyCollectionMutatorTest$HasNonEmptyStringCollection;", null, l0, l3, 0);
            mv.visitLocalVariable("elements", "[Ljava/lang/String;", null, l0, l3, 1);
            mv.visitMaxs(4, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "call", "()Ljava/lang/String;", null, new String[] { "java/lang/Exception" });
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(41, l0);
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "org/pitest/mutationtest/engine/gregor/mutators/collections/EmptyCollectionMutatorTest$HasNonEmptyStringCollection", "input", "Ljava/util/List;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitInsn(ARETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lorg/pitest/mutationtest/engine/gregor/mutators/collections/EmptyCollectionMutatorTest$HasNonEmptyStringCollection;", null, l0, l1, 0);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "call", "()Ljava/lang/Object;", null, new String[] { "java/lang/Exception" });
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(31, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "org/pitest/mutationtest/engine/gregor/mutators/collections/EmptyCollectionMutatorTest$HasNonEmptyStringCollection", "call", "()Ljava/lang/String;", false);
            mv.visitInsn(ARETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lorg/pitest/mutationtest/engine/gregor/mutators/collections/EmptyCollectionMutatorTest$HasNonEmptyStringCollection;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        cw.visitEnd();
        FileOutputStream fos = new FileOutputStream(className + ".class");
        try {
            fos.write(cw.toByteArray());
        }
        finally {
            fos.close();
        }
    }

    public static void main(String[] args) throws Exception {
        dump("Sample");
    }
}

