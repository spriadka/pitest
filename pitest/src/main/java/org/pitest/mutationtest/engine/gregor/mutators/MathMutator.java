/*
 * Copyright 2010 Henry Coles
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.mutationtest.engine.gregor.mutators;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.gregor.AbstractInstructionMutator;
import org.pitest.mutationtest.engine.gregor.InstructionSubstitution;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.MutationContext;
import org.pitest.mutationtest.engine.gregor.ZeroOperandMutation;

public enum MathMutator implements MethodMutatorFactory {

  MATH_MUTATOR;

  @Override
  public MethodVisitor create(final MutationContext context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new MathMethodVisitor(this, methodInfo, context, methodVisitor);
  }

  @Override
  public String getGloballyUniqueId() {
    return this.getClass().getName();
  }

  @Override
  public String getName() {
    return name();
  }

}

class MathMethodVisitor extends AbstractInstructionMutator {

  MathMethodVisitor(final MethodMutatorFactory factory,
      final MethodInfo methodInfo, final MutationContext context,
      final MethodVisitor writer) {
    super(factory, methodInfo, context, writer);
  }

  private static final Map<Integer, ZeroOperandMutation> MUTATIONS = new HashMap<Integer, ZeroOperandMutation>();

  static {
    MUTATIONS.put(Opcodes.IADD, new InstructionSubstitution(Opcodes.ISUB,
        "Replaced integer addition with subtraction"));
    MUTATIONS.put(Opcodes.ISUB, new InstructionSubstitution(Opcodes.IADD,
        "Replaced integer subtraction with addition"));
    MUTATIONS.put(Opcodes.IMUL, new InstructionSubstitution(Opcodes.IDIV,
        "Replaced integer multiplication with division"));
    MUTATIONS.put(Opcodes.IDIV, new InstructionSubstitution(Opcodes.IMUL,
        "Replaced integer division with multiplication"));
    MUTATIONS.put(Opcodes.IOR, new InstructionSubstitution(Opcodes.IAND,
        "Replaced bitwise OR with AND"));
    MUTATIONS.put(Opcodes.IAND, new InstructionSubstitution(Opcodes.IOR,
        "Replaced bitwise AND with OR"));
    MUTATIONS.put(Opcodes.IREM, new InstructionSubstitution(Opcodes.IMUL,
        "Replaced integer modulus with multiplication"));
    MUTATIONS.put(Opcodes.IXOR, new InstructionSubstitution(Opcodes.IAND,
        "Replaced XOR with AND"));
    MUTATIONS.put(Opcodes.ISHL, new InstructionSubstitution(Opcodes.ISHR,
        "Replaced Shift Left with Shift Right"));
    MUTATIONS.put(Opcodes.ISHR, new InstructionSubstitution(Opcodes.ISHL,
        "Replaced Shift Right with Shift Left"));
    MUTATIONS.put(Opcodes.IUSHR, new InstructionSubstitution(Opcodes.ISHL,
        "Replaced Unsigned Shift Right with Shift Left"));

    // longs

    MUTATIONS.put(Opcodes.LADD, new InstructionSubstitution(Opcodes.LSUB,
        "Replaced long addition with subtraction"));
    MUTATIONS.put(Opcodes.LSUB, new InstructionSubstitution(Opcodes.LADD,
        "Replaced long subtraction with addition"));
    MUTATIONS.put(Opcodes.LMUL, new InstructionSubstitution(Opcodes.LDIV,
        "Replaced long multiplication with division"));
    MUTATIONS.put(Opcodes.LDIV, new InstructionSubstitution(Opcodes.LMUL,
        "Replaced long division with multiplication"));
    MUTATIONS.put(Opcodes.LOR, new InstructionSubstitution(Opcodes.LAND,
        "Replaced bitwise OR with AND"));
    MUTATIONS.put(Opcodes.LAND, new InstructionSubstitution(Opcodes.LOR,
        "Replaced bitwise AND with OR"));
    MUTATIONS.put(Opcodes.LREM, new InstructionSubstitution(Opcodes.LMUL,
        "Replaced long modulus with multiplication"));
    MUTATIONS.put(Opcodes.LXOR, new InstructionSubstitution(Opcodes.LAND,
        "Replaced XOR with AND"));
    MUTATIONS.put(Opcodes.LSHL, new InstructionSubstitution(Opcodes.LSHR,
        "Replaced Shift Left with Shift Right"));
    MUTATIONS.put(Opcodes.LSHR, new InstructionSubstitution(Opcodes.LSHL,
        "Replaced Shift Right with Shift Left"));
    MUTATIONS.put(Opcodes.LUSHR, new InstructionSubstitution(Opcodes.LSHL,
        "Replaced Unsigned Shift Right with Shift Left"));

    // floats
    MUTATIONS.put(Opcodes.FADD, new InstructionSubstitution(Opcodes.FSUB,
        "Replaced float addition with subtraction"));
    MUTATIONS.put(Opcodes.FSUB, new InstructionSubstitution(Opcodes.FADD,
        "Replaced float subtraction with addition"));
    MUTATIONS.put(Opcodes.FMUL, new InstructionSubstitution(Opcodes.FDIV,
        "Replaced float multiplication with division"));
    MUTATIONS.put(Opcodes.FDIV, new InstructionSubstitution(Opcodes.FMUL,
        "Replaced float division with multiplication"));
    MUTATIONS.put(Opcodes.FREM, new InstructionSubstitution(Opcodes.FMUL,
        "Replaced float modulus with multiplication"));

    // doubles
    MUTATIONS.put(Opcodes.DADD, new InstructionSubstitution(Opcodes.DSUB,
        "Replaced double addition with subtraction"));
    MUTATIONS.put(Opcodes.DSUB, new InstructionSubstitution(Opcodes.DADD,
        "Replaced double subtraction with addition"));
    MUTATIONS.put(Opcodes.DMUL, new InstructionSubstitution(Opcodes.DDIV,
        "Replaced double multiplication with division"));
    MUTATIONS.put(Opcodes.DDIV, new InstructionSubstitution(Opcodes.DMUL,
        "Replaced double division with multiplication"));
    MUTATIONS.put(Opcodes.DREM, new InstructionSubstitution(Opcodes.DMUL,
        "Replaced double modulus with multiplication"));

  }

  @Override
  protected Map<Integer, ZeroOperandMutation> getMutations() {
    return MUTATIONS;
  }

}
