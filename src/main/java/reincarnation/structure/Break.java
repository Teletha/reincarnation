/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.structure;

import reincarnation.Node;
import reincarnation.coder.Coder;

public class Break extends Jumpable<Breakable> {

    /**
     * Build break statement.
     * 
     * @param that The node which indicate 'this' variable.
     */
    public Break(Node that, Breakable breakable) {
        super(that, breakable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCode(Coder coder) {
        coder.writeBreak(breakable.label(), omitLabel.v);
    }
}