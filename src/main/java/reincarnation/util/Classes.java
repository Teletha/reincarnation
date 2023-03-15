/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.util;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import kiss.model.Model;

public class Classes {

    /**
     * Check whether the given member is static or not.
     * 
     * @param member A target member to test.
     * @return A result.
     */
    public static boolean isAbstract(Member member) {
        return member != null && Modifier.isAbstract(member.getModifiers());
    }

    /**
     * Check whether the given member is static or not.
     * 
     * @param member A target member to test.
     * @return A result.
     */
    public static boolean isStatic(Member member) {
        return member != null && Modifier.isStatic(member.getModifiers());
    }

    /**
     * Check whether the given class is static or not.
     * 
     * @param clazz A target class to test.
     * @return A result.
     */
    public static boolean isStatic(Class clazz) {
        return clazz != null && Modifier.isStatic(clazz.getModifiers());
    }

    /**
     * Check whether the given member is NOT static or not.
     * 
     * @param member A target member to test.
     * @return A result.
     */
    public static boolean isNonStatic(Member member) {
        return !isStatic(member);
    }

    /**
     * Check whether the given class is NOT static or not.
     * 
     * @param clazz A target class to test.
     * @return A result.
     */
    public static boolean isNonStatic(Class clazz) {
        return !isStatic(clazz);
    }

    /**
     * Helper to check member-like type.
     * 
     * @param clazz A target to check.
     * @return A result.
     */
    public static boolean isMemberLike(Class clazz) {
        return clazz.isAnonymousClass() || clazz.isLocalClass() || clazz.isMemberClass();
    }

    /**
     * Check member type.
     * 
     * @return
     */
    public static boolean isMember(Class encloser, Class target) {
        return enclosings(target).contains(encloser);
    }

    /**
     * Check whether the given class is sealed sub class or not.
     * 
     * @param clazz
     * @return
     */
    public static boolean isSealedSubclass(Class clazz) {
        for (Class type : Model.collectTypes(clazz)) {
            if (type.isSealed()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculate root of enclosing class.
     * 
     * @param clazz A target class.
     * @return A root of enclosing class.
     */
    public static Class enclosingRoot(Class clazz) {
        LinkedList<Class> list = enclosings(clazz);

        return list.isEmpty() ? clazz : list.getFirst();
    }

    /**
     * Collect enclosing classes.
     * 
     * @param clazz A target class.
     * @return A list of enclosing classes.
     */
    public static LinkedList<Class> enclosings(Class clazz) {
        LinkedList<Class> list = new LinkedList();

        while (clazz.getEnclosingClass() != null) {
            clazz = clazz.getEnclosingClass();
            list.addFirst(clazz);
        }
        return list;
    }

    public static Set<Class> inner(Class clazz) {
        Set<Class> set = new HashSet();
        inner(clazz, set);
        return set;
    }

    private static void inner(Class clazz, Set<Class> set) {
        set.add(clazz);

        for (Class declared : clazz.getDeclaredClasses()) {
            inner(declared, set);
        }
    }
}