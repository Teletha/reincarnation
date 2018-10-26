/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.java;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import kiss.Variable;
import reincarnation.Operand;
import reincarnation.Reincarnation;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;
import reincarnation.coder.CodingOption;
import reincarnation.coder.DelegatableCoder;
import reincarnation.coder.Join;
import reincarnation.operator.AccessMode;
import reincarnation.operator.AssignOperator;
import reincarnation.operator.BinaryOperator;
import reincarnation.operator.UnaryOperator;

/**
 * @version 2018/10/21 21:36:48
 */
public class JavaCoder extends Coder<JavaCodingOption> {

    /** The current type. (maybe null in debug context) */
    private final Variable<Class> current = Variable.empty();

    /** The import manager. */
    final Imports imports = new Imports();

    /**
     * 
     */
    public JavaCoder() {
        super();
    }

    /**
     * @param appendable
     */
    public JavaCoder(Appendable appendable) {
        super(appendable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Reincarnation reincarnation) {
        imports.setBase(reincarnation.clazz);

        writePackage(reincarnation.clazz.getPackage());

        if (options.writeMemberFromTopLevel && Classes.isMemberLike(reincarnation.clazz)) {
            Hierarchy hierarchy = Hierarchy.calculate(reincarnation);

            writeImport(hierarchy.classes);
            writeHierarchy(hierarchy);
        } else {
            writeImport(reincarnation.classes);
            writeOne(reincarnation);
        }
    }

    private void writeOne(Reincarnation reincarnation) {
        writeType(reincarnation.clazz, () -> {
            List<Field> statics = new ArrayList();
            List<Field> fields = new ArrayList();
            for (Field field : reincarnation.clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    statics.add(field);
                } else {
                    fields.add(field);
                }
            }

            statics.forEach(this::writeStaticField);
            reincarnation.staticInitializer.forEach(this::writeStaticInitializer);
            fields.forEach(this::writeField);
            reincarnation.initializer.forEach(this::writeInitializer);
            reincarnation.constructors.entrySet().forEach(e -> writeConstructor(e.getKey(), e.getValue()));
            reincarnation.methods.entrySet().forEach(e -> writeMethod(e.getKey(), e.getValue()));
            reincarnation.anonymous.forEach(e -> writeOne(Reincarnation.exhume(e)));
        });
    }

    /**
     * Write hierarchy skelton.
     * 
     * @param hierarchy
     */
    private void writeHierarchy(Hierarchy hierarchy) {
        if (hierarchy.skelton) {
            writeType(hierarchy.clazz, () -> {
                for (Hierarchy child : hierarchy.children) {
                    writeHierarchy(child);
                }
            });
        } else {
            writeOne(Reincarnation.exhume(hierarchy.clazz));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writePackage(Package info) {
        line("package", space, info.getName(), ";");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeImport(Set<Class> classes) {
        if (!classes.isEmpty()) {
            imports.add(classes);

            line();
            for (Class clazz : imports.imported) {
                line("import", space, clazz.getCanonicalName(), ";");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeType(Class type, Runnable inner) {
        current.set(type);

        String kind;
        String extend = "";
        Join<Class> implement;
        Join accessor = accessor(type.getModifiers());

        if (type.isInterface()) {
            kind = "interface";
            implement = join(type.getInterfaces()).prefix(" extends ").converter(this::name);
            accessor.remove("static", "abstract");
        } else if (type.isEnum()) {
            kind = "enum";
            implement = join(type.getInterfaces()).prefix(" implements ").converter(this::name);
        } else {
            kind = "class";
            extend = type.getSuperclass() == Object.class ? "" : " extends " + name(type.getSuperclass());
            implement = join(type.getInterfaces()).prefix(" implements ").converter(this::name);
        }

        line();
        line(accessor, kind, space, simpleName(type), extend, implement, space, "{");
        indent(inner::run);
        line("}");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeField(Field field) {
        writeStaticField(field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStaticField(Field field) {
        if (current.is(Class::isInterface)) {
            // ignore, write fields in static initializer
        } else {
            line(accessor(field.getModifiers()), name(field.getType()), space, field.getName(), ";");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInitializer(Code code) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStaticInitializer(Code code) {
        if (current.is(Class::isInterface)) {
            code.write(new InterfaceCoder(this));
        } else {
            line();
            line("static {");
            indent(code::write);
            line("}");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeConstructor(Constructor c, Code code) {
        Join<Parameter> params = join(c.getParameters()).converter(p -> name(p.getType()) + space + p.getName()).separator(", ");

        line();
        line(accessor(c.getModifiers()), simpleName(c.getDeclaringClass()), "(", params, ")", space, "{");
        indent(code::write);
        line("}");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethod(Method method, Code code) {
        // ignore compiler generated method
        if (method.isSynthetic()) {
            return;
        }

        Join<Parameter> params = join(method.getParameters()).converter(p -> name(p.getType()) + space + p.getName()).separator(", ");

        line();
        if (method.isSynthetic()) {
            line(accessor(method.getModifiers()), name(method.getReturnType()), space, method.getName(), "(", params, ")", space, "{");
        } else {
            line(accessor(method.getModifiers()), name(method.getReturnType()), space, method.getName(), "(", params, ")", space, "{");
        }
        indent(code::write);
        line("}");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStatement(Code code) {
        line(code, ";");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeReturn(Optional<Code> code) {
        if (code.isEmpty()) {
            write("return");
        } else {
            write("return", space, code.get());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeBoolean(boolean code) {
        write(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeChar(char code) {
        write("'", code, "'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInt(int code) {
        write(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLong(long code) {
        write(code, "L");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeFloat(float code) {
        write(code, "F");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeDouble(double code) {
        write(code, "D");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeString(String code) {
        write("\"", code, "\"");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeClass(Class code) {
        write(name(code), ".class");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeThis() {
        write("this");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeNull() {
        write("null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeEnclose(Runnable code) {
        write("(");
        code.run();
        write(")");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAssignOperation(Code left, AssignOperator operator, Code right) {
        write(left, space, operator, space, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeBinaryOperation(Code left, BinaryOperator operator, Code right) {
        write(left, space, operator, space, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeUnaryOperation(Code code, UnaryOperator operator) {
        switch (operator) {
        case POST_DECREMENT:
        case POST_INCREMENT:
            write(code, operator);
            break;

        default:
            write(operator, code);
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInstanceof(Code code, Class type) {
        write(code, space, "instanceof", space, name(type));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLocalVariable(String name) {
        write(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLocalVariableDeclaration(Class type, String name) {
        write(name(type), space, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessField(Field field, Code context, AccessMode mode) {
        if (Modifier.isStatic(field.getModifiers())) {
            if (current.is(field.getDeclaringClass())) {
                write(field.getName());
            } else {
                write(context, ".", field.getName());
            }
        } else {
            switch (mode) {
            case THIS:
                write(context, ".", field.getName());
                break;

            case SUPER:
                write("super.", field.getName());
                break;

            default:
                write("((", name(field.getDeclaringClass()), ")", space, context, ").", field.getName());
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessType(Class type) {
        write(name(type));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessArray(Code array, Code index) {
        write(array, "[", index, "]");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessArrayLength(Code array) {
        write(array, ".length");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeConstructorCall(Constructor constructor, List<? extends Code> params) {
        write("new", space, name(constructor.getDeclaringClass()), buildParameter(constructor, params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeSuperConstructorCall(Constructor constructor, List<? extends Code> params) {
        write("super", buildParameter(constructor, params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeThisConstructorCall(Constructor constructor, List<? extends Code> params) {
        write("this", buildParameter(constructor, params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethodCall(Method method, Code context, List<? extends Code> params, AccessMode mode) {
        if (method.isSynthetic()) {
            write(context, ".", method.getName(), buildParameter(method, params));
        } else {
            if (mode == AccessMode.SUPER) {
                write("super.", method.getName(), buildParameter(method, params));
            } else {
                write(context, ".", method.getName(), buildParameter(method, params));
            }
        }
    }

    /**
     * Build parameter expresison.
     * 
     * @param executable
     * @param params
     * @return
     */
    private Join buildParameter(Executable executable, List<? extends Code> params) {
        Join concat = new Join().ignoreEmpty(false).prefix("(").suffix(")").separator("," + space);
        Parameter[] parameters = executable.getParameters();

        for (int i = 0; i < params.size(); i++) {
            Code param = params.get(i);

            if (param == Operand.Null) {
                concat.add(new InferedCode(parameters[i].getType(), param));
            } else {
                concat.add(param);
            }
        }
        return concat;
    }

    /**
     * @version 2018/10/26 14:10:50
     */
    private static class InferedCode implements Code {

        private final Class type;

        private final Code code;

        /**
         * @param type
         * @param code
         */
        private InferedCode(Class type, Code code) {
            this.type = type;
            this.code = code;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(Coder coder) {
            coder.writeCast(type, code);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCreateArray(Class type, List<Code> dimensions) {
        write("new", space, name(type), join("[", dimensions, "][", "]"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCreateArray(Class type, List<Code> dimensions, List<Code> initialValues) {
        write("new", space, name(type), join("[", dimensions, "][", "]"), space, join("{", initialValues, ", ", "}"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTernary(Code condition, Code then, Code elze) {
        write(condition, space, "?", space, then, space, ":", space, elze);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCast(Class type, Code code) {
        write("(", name(type), ")", space, "(", code, ")");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeThrow(Code code) {
        write("throw", space, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeIf(Code condition, Code then, Code elze) {
        line("if", space, "(", condition, ")", space, "{");
        indent(coder -> {
            write(then);
        });

        if (elze == null) {
            line("}");
        } else {
            line("}", space, "else", space, "{");
            indent(coder -> {
                write(elze);
            });
            line("}");
        }
    }

    /**
     * Qualified name.
     * 
     * @param type
     * @return
     */
    private String name(Class type) {
        return imports.name(type);
    }

    /**
     * Simple name.
     * 
     * @param type
     * @return
     */
    private String simpleName(Class type) {
        return computeSimpleName(type);
    }

    /**
     * Accessor keyword.
     * 
     * @param modifier
     * @return
     */
    private Join accessor(int modifier) {
        Join joiner = new Join().separator(" ").suffix(" ");

        if (Modifier.isPublic(modifier)) {
            joiner.add("public");
        } else if (Modifier.isProtected(modifier)) {
            joiner.add("protected");
        } else if (Modifier.isPrivate(modifier)) {
            joiner.add("private");
        }

        if (Modifier.isStatic(modifier)) {
            joiner.add("static");
        }

        if (Modifier.isAbstract(modifier)) {
            joiner.add("abstract");
        } else if (Modifier.isFinal(modifier)) {
            joiner.add("final");
        } else if (Modifier.isNative(modifier)) {
            joiner.add("native");
        }

        if (Modifier.isStrict(modifier)) {
            joiner.add("strict");
        }
        if (Modifier.isTransient(modifier)) {
            joiner.add("transient");
        }
        if (Modifier.isVolatile(modifier)) {
            joiner.add("volatile");
        }
        return joiner;
    }

    /**
     * Compute the fully qualified class name of the specified class.
     * 
     * @param clazz A target class.
     * @return A fully qualified class name.
     */
    public static final String computeName(Class clazz) {
        if (clazz.isAnonymousClass() || clazz.isLocalClass()) {
            String name = clazz.getName();
            return clazz.getEnclosingClass().getName() + "$" + name.substring(name.lastIndexOf(".") + 1);
        } else {
            return clazz.getName();
        }
    }

    /**
     * Compute the simple class name of the specified class.
     * 
     * @param clazz A target class.
     * @return A simple class name.
     */
    public static final String computeSimpleName(Class clazz) {
        if (clazz.isAnonymousClass() || clazz.isLocalClass()) {
            String name = clazz.getName();
            return name.substring(name.lastIndexOf(".") + 1);
        } else {
            return clazz.getSimpleName();
        }
    }

    /**
     * @version 2018/10/25 20:03:24
     */
    private class InterfaceCoder extends DelegatableCoder<CodingOption> {

        /**
         * @param coder
         */
        private InterfaceCoder(Coder coder) {
            super(coder);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeAssignOperation(Code left, AssignOperator operator, Code right) {
            write(left, space, operator, space, right);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeAccessField(Field field, Code context, AccessMode mode) {
            write(name(field.getType()), space, field.getName());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeStatement(Code code) {
            line(code, ";");
        }
    }
}