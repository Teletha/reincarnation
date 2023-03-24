/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import java.util.List;

public interface NodeCreator {
    /**
     * Create a new node before the specified node.
     *
     * @param index The index node before which to create the new node.
     * @param connectable Whether the new node should be connectable.
     * @return The newly created node.
     */
    Node createNodeBefore(Node index, boolean connectable);

    /**
     * Create a new node with the specified initial operand before the specified node.
     * 
     * @param index The index node before which to create the new node.
     * @param initial The initial operand for the new node.
     * @return The newly created node.
     */
    default Node createNodeBefore(Node index, Operand initial) {
        return createNodeBefore(index, true).addOperand(initial);
    }

    /**
     * Create a new splitter node before the specified node, with the specified incoming nodes.
     * 
     * @param index The index node before which to create the new node.
     * @param incomings The incoming nodes for the new splitter node.
     * @return The newly created splitter node.
     */
    default Node createSplitterNodeBefore(Node index, List<Node> incomings) {
        Node created = createNodeBefore(index, false);

        for (Node incoming : incomings) {
            incoming.disconnect(index);
            incoming.connect(created);
        }
        created.connect(index);

        return created;
    }

    /**
     * Create a new node after the specified node.
     * 
     * @param index The index node after which to create the new node.
     * @param connectable Whether the new node should be connectable.
     * @return The newly created node.
     */
    Node createNodeAfter(Node index, boolean connectable);

    /**
     * Create a new node with the specified initial operand after the specified node.
     * 
     * @param index The index node after which to create the new node.
     * @param initial The initial operand for the new node.
     * @return The newly created node.
     */
    default Node createNodeAfter(Node index, Operand initial) {
        return createNodeAfter(index, true).addOperand(initial);
    }

    /**
     * Create a new splitter node after the specified node, with the specified outgoing nodes.
     * 
     * @param index The index node after which to create the new node.
     * @param outgoings The outgoing nodes for the new splitter node.
     * @return The newly created splitter node.
     */
    default Node createSplitterNodeAfter(Node index, List<Node> outgoings) {
        Node created = createNodeBefore(index, false);

        for (Node outgoing : outgoings) {
            index.disconnect(outgoing);
            created.connect(outgoing);
        }
        index.connect(created);

        return created;
    }
}