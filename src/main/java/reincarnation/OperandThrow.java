/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.util.Objects;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/22 10:38:35
 */
public class OperandThrow extends Operand {

    /** The statement. */
    private final Operand value;

    /**
     * Build throw expression.
     * 
     * @param value A thrown value.
     */
    public OperandThrow(Operand value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStatement() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeThrow(value);
    }
}