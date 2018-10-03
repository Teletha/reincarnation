/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Type.*;
import static reincarnation.Node.*;
import static reincarnation.Util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.stmt.BlockStmt;

import reincarnation.Node.TryCatchFinallyBlocks;

/**
 * @version 2018/10/04 8:47:28
 */
class JavaMethodDecompiler extends MethodVisitor {

    /**
     * Represents an expanded frame. See {@link ClassReader#EXPAND_FRAMES}.
     */
    private static final int FRAME_NEW = 400;

    /**
     * Represents a compressed frame with complete frame data.
     */
    private static final int FRAME_FULL = 401;

    /**
     * Represents a compressed frame where locals are the same as the locals in the previous frame,
     * except that additional 1-3 locals are defined, and with an empty stack.
     */
    private static final int FRAME_APPEND = 402;

    /**
     * Represents a compressed frame where locals are the same as the locals in the previous frame,
     * except that the last 1-3 locals are absent and with an empty stack.
     */
    private static final int FRAME_CHOP = 403;

    /**
     * Represents a compressed frame with exactly the same locals as the previous frame and with an
     * empty stack.
     */
    private static final int FRAME_SAME = 404;

    /**
     * Represents a compressed frame with exactly the same locals as the previous frame and with a
     * single value on the stack.
     */
    private static final int FRAME_SAME1 = 405;

    /**
     * Represents an expanded frame. See {@link ClassReader#EXPAND_FRAMES}.
     */
    private static final int FRAME = 406;

    /**
     * Represents a jump instruction. A jump instruction is an instruction that may jump to another
     * instruction.
     */
    private static final int JUMP = 410;

    /**
     * Represents a primitive addtion instruction. IADD, LADD, FADD and DADD.
     */
    private static final int ADD = 420;

    /**
     * Represents a primitive substruction instruction. ISUB, LSUB, FSUB and DSUB.
     */
    private static final int SUB = 421;

    /**
     * Represents a constant 0 instruction. ICONST_0, LCONST_0, FCONST_0 and DCONST_0.
     */
    private static final int CONSTANT_0 = 430;

    /**
     * Represents a constant 1 instruction. ICONST_1, LCONST_1, FCONST_1 and DCONST_1.
     */
    private static final int CONSTANT_1 = 431;

    /**
     * Represents a duplicate instruction. DUP and DUP2.
     */
    private static final int DUPLICATE = 440;

    /**
     * Represents a duplicate instruction. DUP_X1 and DUP2_X2.
     */
    private static final int DUPLICATE_AWAY = 441;

    /**
     * Represents a return instruction.
     */
    private static final int RETURNS = 450;

    /**
     * Represents a increment instruction.
     */
    private static final int INCREMENT = 460;

    /**
     * Represents a compare instruction. FCMPL and FCMPG
     */
    private static final int FCMP = 470;

    /**
     * Represents a compare instruction. DCMPL and DCMPG
     */
    private static final int DCMP = 471;

    /**
     * Represents a compare instruction. DCMPL and DCMPG
     */
    private static final int CMP = 472;

    /**
     * Represents a invoke method instruction. INVOKESTATIC, INVOKEVIRTUAL etc
     */
    private static final int INVOKE = 480;

    /** The extra opcode for byte code parsing. */
    private static final int LABEL = 300;

    /** The frequently used operand for cache. */
    private static final OperandNumber ZERO = new OperandNumber(0);

    /** The frequently used operand for cache. */
    private static final OperandNumber ONE = new OperandNumber(1);

    /** The current processing method name. */
    private final String methodName;

    /** The method return type. */
    private final Type returnType;

    /** The method return type. */
    private final Type[] parameterTypes;

    /** The local variable manager. */
    private final LocalVariables variables;

    /** The pool of try-catch-finally blocks. */
    private final TryCatchFinallyBlocks tries = new TryCatchFinallyBlocks();

    /** The method body. */
    private final CallableDeclaration root;

    /** The current processing node. */
    private Node current = null;

    /** The all node list for this method. */
    private List<Node> nodes = new CopyOnWriteArrayList();

    /** The counter for the current processing node identifier. */
    private int counter = 0;

    /** The counter for construction of the object initialization. */
    private int countInitialization = 0;

    /** The record of recent instructions. */
    private int[] records = new int[10];

    /** The current start position of instruction records. */
    private int recordIndex = 0;

    /**
     * {@link Enum#values} produces special bytecode, so we must handle it by special way.
     */
    private Operand[] enumValues = new Operand[2];

    /**
     * <p>
     * Switch statement with enum produces special bytecode, so we must handle it by special way.
     * The following asmfier code is typical code for enum switch.
     * </p>
     * <pre>
     * // invoke compiler generated static method to retrieve the user class specific number array
     * // we should ignore this oeprand
     * mv.visitMethodInsn(INVOKESTATIC, "EnumSwitchUserClass", "$SWITCH_TABLE$EnumClass", "()[I");
     *
     * // load target enum variable
     * mv.visitVarInsn(ALOAD, 1);
     * 
     * // invoke Enum#ordinal method to retieve identical number
     * mv.visitMethodInsn(INVOKEVIRTUAL, "EnumClass", "ordinal", "()I");
     * 
     * // access mapping number array
     * //we should ignore this operand
     * mv.visitInsn(IALOAD);
     * </pre>
     */
    private boolean enumSwitchInvoked;

    /**
     * @param root
     * @param api
     */
    JavaMethodDecompiler(CallableDeclaration root, String name, String description, boolean isStatic) {
        super(ASM7);

        this.root = Objects.requireNonNull(root);
        this.methodName = name;
        this.returnType = Type.getReturnType(description);
        this.parameterTypes = Type.getArgumentTypes(description);
        this.variables = new LocalVariables(isStatic);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitEnd() {
        BlockStmt body = new BlockStmt();

        body.addStatement(nodes.get(0).build());

        if (root instanceof MethodDeclaration) {
            ((MethodDeclaration) root).setBody(body);
        } else if (root instanceof ConstructorDeclaration) {
            ((ConstructorDeclaration) root).setBody(body);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitInsn(int opcode) {
        // recode current instruction
        record(opcode);

        switch (opcode) {
        case ICONST_0:
            current.addOperand(ZERO);
            break;

        case ICONST_1:
            current.addOperand(ONE);
            break;

        case ICONST_2:
            current.addOperand(new OperandNumber(2));
            break;

        case ICONST_3:
            current.addOperand(new OperandNumber(3));
            break;

        case ICONST_4:
            current.addOperand(new OperandNumber(4));
            break;

        case ICONST_5:
            current.addOperand(new OperandNumber(5));
            break;

        case ICONST_M1:
            current.addOperand(new OperandNumber(-1));
            break;

        case IRETURN:
            // Java bytecode represents boolean value as integer value (0 or 1).
            if (match(JUMP, ICONST_0, IRETURN, LABEL, FRAME, ICONST_1, IRETURN) || match(JUMP, LABEL, FRAME, ICONST_0, IRETURN, LABEL, FRAME, ICONST_1, IRETURN)) {
                // Current operands is like the following, so we must remove four operands to
                // represent boolean value.
                //
                // JUMP [Condition] return [Expression] false [Expression] ; [Expression] 1 [Number]
                current.remove(0); // remove "1"
                current.remove(0); // remove ";"
                current.remove(0); // remove "false"
                current.remove(0); // remove "return"

                // remove empty node if needed
                if (current.previous.stack.isEmpty()) dispose(current.previous);
            } else if (match(JUMP, ICONST_1, IRETURN, LABEL, FRAME, ICONST_0, IRETURN) || match(JUMP, LABEL, FRAME, ICONST_1, IRETURN, LABEL, FRAME, ICONST_0, IRETURN)) {
                // Current operands is like the following, so we must remove four operands to
                // represent boolean value.
                //
                // JUMP [Condition] return [Expression] true [Expression] ; [Expression] 0 [Number]
                current.remove(0); // remove "0"
                current.remove(0); // remove ";"
                current.remove(0); // remove "true"
                current.remove(0); // remove "return"

                // remove empty node if needed
                if (current.previous.stack.isEmpty()) dispose(current.previous);

                // invert the latest condition
                current.peek(0).invert();
            }

            Operand operand = current.remove(0);

            if (returnType == BOOLEAN_TYPE) {
                if (operand.toString().equals("0")) {
                    operand = new OperandExpression("false");
                } else if (operand.toString().equals("1")) {
                    operand = new OperandExpression("true");
                } else if (operand instanceof OperandAmbiguousZeroOneTernary) {
                    operand = operand.cast(boolean.class);
                }
            }

            current.addExpression(Return, operand);
            current.destination = Termination;
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitIntInsn(int opcode, int operand) {
        // recode current instruction
        record(opcode);

        // The opcode of the instruction to be visited. This opcode is either BIPUSH, SIPUSH or
        // NEWARRAY.
        switch (opcode) {
        // When opcode is BIPUSH, operand value should be between Byte.MIN_VALUE and
        // Byte.MAX_VALUE.
        case BIPUSH:
            current.addOperand(operand);
            break;

        // When opcode is SIPUSH, operand value should be between Short.MIN_VALUE and
        // Short.MAX_VALUE.
        case SIPUSH:
            current.addOperand(operand);
            break;

        // When opcode is NEWARRAY, operand value should be one of Opcodes.T_BOOLEAN,
        // Opcodes.T_CHAR, Opcodes.T_FLOAT, Opcodes.T_DOUBLE, Opcodes.T_BYTE, Opcodes.T_SHORT,
        // Opcodes.T_INT or Opcodes.T_LONG.
        case NEWARRAY:
            Class type = null;

            switch (operand) {
            case T_INT:
                type = int[].class;
                break;

            case T_LONG:
                type = long[].class;
                break;

            case T_FLOAT:
                type = float[].class;
                break;

            case T_DOUBLE:
                type = double[].class;
                break;

            case T_BOOLEAN:
                type = boolean[].class;
                break;

            case T_BYTE:
                type = byte[].class;
                break;

            case T_CHAR:
                type = char[].class;
                break;

            case T_SHORT:
                type = short[].class;
                break;

            default:
                // If this exception will be thrown, it is bug of this program. So we must rethrow
                // the wrapped error in here.
                throw new Error();
            }

            current.addOperand(new OperandArray(current.remove(0), type));
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitLabel(Label label) {
        // recode current instruction
        record(LABEL);

        // build next node
        Node next = getNode(label);

        if (current != null && current.destination == null) {
            current.connect(next);
            current.destination = next;
        }
        current = link(current, next);

        // store the node in appearing order
        nodes.add(current);

        if (1 < nodes.size()) {
            merge(current.previous);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitMethodInsn(int opcode, String className, String methodName, String desc, boolean access) {
        // recode current instruction
        record(opcode);

        // compute owner class
        Class owner = load(className);

        // compute parameter types
        Type[] types = Type.getArgumentTypes(desc);
        Class[] parameters = new Class[types.length];

        for (int i = 0; i < types.length; i++) {
            parameters[i] = load(types[i]);
        }

        // copy latest operands for this method invocation
        ArrayList<Operand> contexts = new ArrayList(parameters.length + 1);

        for (int i = 0; i < parameters.length; i++) {
            contexts.add(0, current.remove(0));
        }

        // write mode
        Class returnType = load(Type.getReturnType(desc));
        boolean immediately = returnType == void.class;

        // if this method (not constructor and not static initializer) return void, we must
        // write out the expression of method invocation immediatly.
        if (immediately && current.stack.size() != 0) {
            current.addExpression(current.remove(0));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitVarInsn(int opcode, int position) {
        if (true) {
            return;
        }
        // recode current instruction
        record(opcode);

        // retrieve local variable name
        Expression variable = variables.name(position, opcode);

        switch (opcode) {
        case ILOAD:
        case FLOAD:
        case LLOAD:
        case DLOAD:
            if (match(INCREMENT, ILOAD) && current.peek(0) == Node.END) {
                String expression = current.peek(1).toString();

                if (expression.startsWith("++") || expression.startsWith("--")) {
                    if (expression.substring(2).equals(variable)) {
                        current.remove(0);
                        break;
                    }
                }
            }

        case ALOAD:
            current.addOperand(new OperandExpression(variable, variables.type(position)));
            break;

        case ASTORE:
            if (match(FRAME_SAME1, ASTORE) || match(FRAME_FULL, ASTORE)) {
                tries.assignVariableName(current, variable);
            }

        case ISTORE:
        case LSTORE:
        case FSTORE:
        case DSTORE:
            // Increment not-int type doesn't use Iinc instruction, so we must distinguish
            // increment from addition by pattern matching. Post increment code of non-int type
            // leaves characteristic pattern like the following.
            if (match(FLOAD, DUP, FCONST_1, FADD, FSTORE) || match(DLOAD, DUP2, DCONST_1, DADD, DSTORE)) {
                // for float and double
                current.remove(0);
                current.remove(0);

                current.addOperand(variable + "++");
            } else if (match(LLOAD, DUP2, LCONST_1, LADD, LSTORE)) {
                // for long
                current.remove(0);
                current.remove(0);

                current.addOperand(increment(variable, long.class, true, true));
            } else {
                // for other
                if (current.peek(0) != null) {
                    // retrieve and remove it
                    Operand operand = current.remove(0, false);

                    // Enum#values produces special bytecode, so we must handle it by special way.
                    if (match(ASTORE, ICONST_0, ALOAD, ARRAYLENGTH, DUP, ISTORE)) {
                        enumValues[0] = current.remove(0);
                        enumValues[1] = current.remove(0);
                    }

                    // infer local variable type
                    variables.type(position).type(operand.infer().type());

                    OperandExpression assignment = new OperandExpression(variable + "=" + operand);

                    if (!operand.duplicated) {
                        current.addExpression(assignment);
                    } else {
                        operand.duplicated = false;

                        // Enum#values produces special bytecode,
                        // so we must handle it by special way.
                        if (match(ARRAYLENGTH, DUP, ISTORE, ANEWARRAY, DUP, ASTORE)) {
                            current.addOperand(enumValues[1]);
                            current.addOperand(enumValues[0]);
                        }

                        // duplicate pointer
                        current.addOperand(new OperandEnclose(assignment));
                    }
                }
            }
            break;
        }
    }

    /**
     * <p>
     * Helper method to dispose the specified node.
     * </p>
     */
    private final void dispose(Node target) {
        dispose(target, false, true);
    }

    /**
     * <p>
     * Helper method to dispose the specified node.
     * </p>
     * 
     * @param target A target node to dipose.
     * @param clearStack true will clear all operands in target node, false will transfer them into
     *            the previous node.
     * @param recursive true will dispose the previous node if it is empty.
     */
    private final void dispose(Node target, boolean clearStack, boolean recursive) {
        if (nodes.contains(target)) {

            // remove actually
            nodes.remove(target);

            link(target.previous, target.next);

            // Connect from incomings to outgouings
            for (Node out : target.outgoing) {
                for (Node in : target.incoming) {
                    in.connect(out);
                }
            }

            // Remove the target node from its incomings and outgoings.
            for (Node node : target.incoming) {
                node.disconnect(target);

                if (node.destination == target) {
                    node.destination = target.getDestination();
                }

                for (Operand operand : node.stack) {
                    if (operand instanceof OperandCondition) {
                        OperandCondition condition = (OperandCondition) operand;

                        if (condition.then == target) {
                            condition.then = target.getDestination();
                        }

                        if (condition.elze == target) {
                            condition.elze = target.getDestination();
                        }
                    }
                }
            }
            for (Node node : target.outgoing) {
                target.disconnect(node);
            }

            // Copy all operands to the previous node if needed
            if (!clearStack) {
                if (target.previous != null) {
                    target.previous.stack.addAll(target.stack);
                }
            }

            // Delete all operands from the current processing node
            target.stack.clear();

            // switch current node if needed
            if (target == current) {
                current = target.previous;
            }

            // dispose empty node recursively
            if (recursive && target.previous != null) {
                if (target.previous.stack.isEmpty()) {
                    dispose(target.previous, clearStack, recursive);
                }
            }
        }
    }

    /**
     * <p>
     * Create new node after the specified node.
     * </p>
     * 
     * @param index A index node.
     * @return A created node.
     */
    private final Node createNodeAfter(Node index) {
        Node created = new Node(index.id + "+");

        // switch line number
        created.lineNumber = index.lineNumber;
        index.lineNumber = -1;

        // switch previous and next nodes
        // index -> created -> next
        link(index, created, index.next);

        if (index.destination == null) {
            index.destination = created;
        }

        // insert to node list
        nodes.add(nodes.indexOf(index) + 1, created);

        // API definition
        return created;
    }

    /**
     * <p>
     * Retrieve the asossiated node of the specified label.
     * </p>
     * 
     * @param label A label for node.
     * @return An asossiated and cached node.
     */
    private final Node getNode(Label label) {
        Node node = (Node) label.info;

        // search cached node
        if (node == null) {
            label.info = node = new Node(counter++);
        }

        // API definition
        return node;
    }

    /**
     * <p>
     * Link all nodes as order of appearance.
     * </p>
     * 
     * @param nodes A sequence of nodes.
     * @return A last node.
     */
    private final Node link(Node... nodes) {
        int size = nodes.length - 1;

        for (int i = 0; i < size; i++) {
            Node prev = nodes[i];
            Node next = nodes[i + 1];

            if (prev != null) prev.next = next;
            if (next != null) next.previous = prev;
        }
        return nodes[size];
    }

    /**
     * <p>
     * Helper method to merge all conditional operands.
     * </p>
     */
    private final void merge(Node node) {
        if (node == null) {
            return;
        }

        SequentialConditionInfo info = new SequentialConditionInfo(node);

        if (info.conditions.isEmpty()) {
            return;
        }

        // Search and merge the sequencial conditional operands in this node from right to left.
        int start = info.start;
        OperandCondition left = null;
        OperandCondition right = (OperandCondition) node.peek(start);

        for (int index = 1; index < info.conditions.size(); index++) {
            left = (OperandCondition) node.peek(start + index);

            if (info.canMerge(left, right)) {
                // Merge two adjucent conditional operands.
                right = new OperandCondition(left, (OperandCondition) node.remove(--start + index));

                node.set(start + index, right);
            } else {
                right = left;
                left = null;
            }
        }

        // If the previous node is terminated by conditional operand and the target node is started
        // by conditional operand, we should try to merge them.
        if (info.conditionalHead && node.previous != null) {
            Operand operand = node.previous.peek(0);

            if (operand instanceof OperandCondition) {
                OperandCondition condition = (OperandCondition) operand;

                if (info.canMerge(condition, right) && condition.elze == node) {
                    dispose(node);

                    // Merge recursively
                    merge(node.previous);
                }
            }
        }
    }

    /**
     * Record the current instruction.
     */
    private final void record(int opcode) {
        // insert anonymous label at head if the processing method has no label
        if (records[0] == 0 && opcode != LABEL) {
            visitLabel(new Label());
        }

        records[recordIndex++] = opcode;

        if (recordIndex == records.length) {
            recordIndex = 0; // loop index
        }
    }

    /**
     * <p>
     * Pattern matching for the recent instructions.
     * </p>
     * 
     * @param opcodes A sequence of opecodes to match.
     * @return A result.
     */
    private final boolean match(int... opcodes) {
        root: for (int i = 0; i < opcodes.length; i++) {
            int record = records[(recordIndex + i + records.length - opcodes.length) % records.length];

            switch (opcodes[i]) {
            case ADD:
                switch (record) {
                case IADD:
                case LADD:
                case FADD:
                case DADD:
                    continue root;

                default:
                    return false;
                }

            case SUB:
                switch (record) {
                case ISUB:
                case LSUB:
                case FSUB:
                case DSUB:
                    continue root;

                default:
                    return false;
                }

            case CONSTANT_0:
                switch (record) {
                case ICONST_0:
                case LCONST_0:
                case FCONST_0:
                case DCONST_0:
                    continue root;

                default:
                    return false;
                }

            case CONSTANT_1:
                switch (record) {
                case ICONST_1:
                case LCONST_1:
                case FCONST_1:
                case DCONST_1:
                    continue root;

                default:
                    return false;
                }

            case DUPLICATE:
                switch (record) {
                case DUP:
                case DUP2:
                    continue root;

                default:
                    return false;
                }

            case DUPLICATE_AWAY:
                switch (record) {
                case DUP_X1:
                case DUP2_X1:
                    continue root;

                default:
                    return false;
                }

            case RETURNS:
                switch (record) {
                case RETURN:
                case IRETURN:
                case ARETURN:
                case LRETURN:
                case FRETURN:
                case DRETURN:
                    continue root;

                default:
                    return false;
                }

            case JUMP:
                switch (record) {
                case IFEQ:
                case IFGE:
                case IFGT:
                case IFLE:
                case IFLT:
                case IFNE:
                case IFNONNULL:
                case IFNULL:
                case IF_ACMPEQ:
                case IF_ACMPNE:
                case IF_ICMPEQ:
                case IF_ICMPGE:
                case IF_ICMPGT:
                case IF_ICMPLE:
                case IF_ICMPLT:
                case IF_ICMPNE:
                case GOTO:
                    continue root;

                default:
                    return false;
                }

            case CMP:
                switch (record) {
                case IFEQ:
                case IFGE:
                case IFGT:
                case IFLE:
                case IFLT:
                case IFNE:
                case IFNONNULL:
                case IFNULL:
                case IF_ACMPEQ:
                case IF_ACMPNE:
                case IF_ICMPEQ:
                case IF_ICMPGE:
                case IF_ICMPGT:
                case IF_ICMPLE:
                case IF_ICMPLT:
                case IF_ICMPNE:
                    continue root;

                default:
                    return false;
                }

            case FCMP:
                switch (record) {
                case FCMPG:
                case FCMPL:
                    continue root;

                default:
                    return false;
                }

            case DCMP:
                switch (record) {
                case DCMPG:
                case DCMPL:
                    continue root;

                default:
                    return false;
                }

            case FRAME:
                switch (record) {
                case FRAME_APPEND:
                case FRAME_CHOP:
                case FRAME_FULL:
                case FRAME_NEW:
                case FRAME_SAME:
                case FRAME_SAME1:
                    continue root;

                default:
                    return false;
                }

            case INVOKE:
                switch (record) {
                case INVOKEINTERFACE:
                case INVOKESPECIAL:
                case INVOKESTATIC:
                case INVOKEVIRTUAL:
                    continue root;

                default:
                    return false;
                }

            default:
                if (record != opcodes[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * <p>
     * Write increment/decrement code.
     * </p>
     * 
     * @param context A current context value.
     * @param type A current context type.
     * @param increase Increment or decrement.
     * @param post Post or pre.
     * @return A suitable code.
     */
    private final String increment(Object context, Class type, boolean increase, boolean post) {
        if (post) {
            return context + (increase ? "++" : "--");
        } else {
            return (increase ? "++" : "--") + context;
        }
    }

    /**
     * <p>
     * Manage local variables.
     * </p>
     * 
     * @version 2013/01/21 11:09:48
     */
    private static class LocalVariables {

        /** The current processing method is static or not. */
        private final boolean isStatic;

        /** The max size of variables. */
        private int max = 0;

        /** The ignorable variable index. */
        private final List<Integer> ignores = new ArrayList();

        /** The local type mapping. */
        private final Map<Integer, InferredType> types = new HashMap();

        /**
         * @param isStatic
         */
        private LocalVariables(boolean isStatic) {
            this.isStatic = isStatic;
        }

        /**
         * <p>
         * Compute the identified qualified local variable name for ECMAScript.
         * </p>
         * 
         * @param order An order by which this variable was declared.
         * @return An identified local variable name for ECMAScript.
         */
        private Expression name(int order) {
            return name(order, 0);
        }

        /**
         * <p>
         * Compute the identified qualified local variable name for ECMAScript.
         * </p>
         * 
         * @param order An order by which this variable was declared.
         * @return An identified local variable name for ECMAScript.
         */
        private Expression name(int order, int opcode) {
            // ignore long or double second index
            switch (opcode) {
            case LLOAD:
            case LSTORE:
            case DLOAD:
            case DSTORE:
                ignores.add(order + 1);
                break;
            }

            // order 0 means "this", but static method doesn't have "this" variable
            if (!isStatic) {
                order--;
            }

            if (order == -1) {
                return new ThisExpr();
            }

            // Compute local variable name
            return new NameExpr("local" + order);
        }

        /**
         * <p>
         * List up all valid variable names.
         * </p>
         * 
         * @return
         */
        private List<Expression> names() {
            List<Expression> names = new ArrayList();

            for (int i = isStatic ? 0 : 1; i < max; i++) {
                if (!ignores.contains(i)) {
                    names.add(name(i));
                }
            }
            return names;
        }

        /**
         * <p>
         * Find {@link InferredType} for the specified position.
         * </p>
         * 
         * @param position
         * @return
         */
        private InferredType type(int position) {
            InferredType type = types.get(position);

            if (type == null) {
                type = new InferredType();
                types.put(position, type);
            }
            return type;
        }
    }

    /**
     * @version 2018/10/03 15:57:48
     */
    private class SequentialConditionInfo {

        /** The start location of this sequence. */
        private int start;

        /** The base node. */
        private final Node base;

        /** The sequential conditions. */
        private final ArrayList<OperandCondition> conditions = new ArrayList();

        /** The flag whether this node is started by conditional operand or not. */
        private final boolean conditionalHead;

        /** The flag whether this node is started by conditional operand or not. */
        private final boolean conditionalTail;

        /**
         * <p>
         * Search the sequencial conditional operands in the specified node from right to left.
         * </p>
         * 
         * @param node A target node.
         */
        private SequentialConditionInfo(Node node) {
            this.base = node;

            boolean returned = false;

            // Search the sequential conditional operands in the specified node from right to left.
            for (int index = 0; index < node.stack.size(); index++) {
                Operand operand = node.peek(index);
                if (operand instanceof OperandCondition == false) {
                    // non-conditional operand is found
                    if (conditions.isEmpty()) {
                        if (operand == Return) {
                            returned = true;
                        }
                        // conditional operand is not found as yet, so we should continue to search
                        continue;
                    } else {
                        // stop searching
                        break;
                    }
                }

                // conditional operand is found
                OperandCondition condition = (OperandCondition) operand;

                if (conditions.isEmpty()) {
                    // this is first condition
                    start = index;

                    if (!returned && condition.elze == null && condition.then != node.destination) {
                        condition.elze = node.destination;
                    }
                } else {
                    // this is last condition
                }
                conditions.add(condition);

                // if (index + 1 == node.stack.size()) {
                // if (node.peek(index + 1) instanceof OperandCondition) {
                // node = node.previous;
                // index = 0;
                // }
                // }
            }

            this.conditionalHead = node.stack.size() == start + conditions.size();
            this.conditionalTail = start == 0 && !conditions.isEmpty();
        }

        /**
         * <p>
         * Split mixed contents into condition part and non-condition part.
         * </p>
         */
        private void split() {
            int size = conditions.size();

            if (size != 0 && size != base.stack.size()) {
                if (conditionalTail) {
                    // transfer condition operands to the created node
                    // [non-condition] [condition]
                    Node created = createNodeAfter(base);

                    for (int i = 0; i < size; i++) {
                        OperandCondition condition = (OperandCondition) base.stack.pollLast();

                        // disconnect from base node
                        base.disconnect(condition.then);
                        base.disconnect(condition.elze);

                        // connect from created node
                        created.connect(condition.then);
                        created.connect(condition.elze);

                        // transfer operand
                        created.stack.addFirst(condition);
                    }

                    // connect from base to created
                    base.connect(created);
                } else if (conditionalHead) {
                    // transfer non-condition operands to the created node
                    // [condition] [non-condition]
                    Node created = createNodeAfter(base);

                    // transfer operand
                    for (int i = 0; i < start; i++) {
                        created.stack.addFirst(base.stack.pollLast());
                    }

                    // search non-conditional operand's transition
                    Set<Node> transitions = new HashSet(base.outgoing);

                    for (OperandCondition condition : conditions) {
                        transitions.remove(condition.then);
                    }

                    for (Node transition : transitions) {
                        // disconnect from base
                        base.disconnect(transition);

                        // connect from created
                        created.connect(transition);
                    }

                    // connect from base to created
                    base.connect(created);

                    // connect from created to next
                    created.connect(base.destination == null ? created.destination : base.destination);
                }
            }
        }

        /**
         * <p>
         * Test whether target conditions is able to merge.
         * </p>
         * 
         * @param left A left condition.
         * @param right A right condition.
         * @return A result.
         */
        private boolean canMerge(OperandCondition left, OperandCondition right) {
            return left.then == right.then || left.then == right.elze;
        }
    }
}