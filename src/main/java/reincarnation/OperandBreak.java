/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.util.Optional;

import reincarnation.coder.Coder;

/**
 * @version 2018/10/26 22:43:10
 */
class OperandBreak extends Operand {

    /** The target label. */
    private final String label;

    /**
     * Build break statement.
     * 
     * @param label A target name.
     */
    OperandBreak(String label) {
        this.label = label;
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
    public void write(Coder coder) {
        coder.writeBreak(Optional.ofNullable(label));
    }
}
