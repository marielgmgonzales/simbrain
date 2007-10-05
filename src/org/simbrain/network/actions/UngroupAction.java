/*
 * Part of Simbrain--a java-based neural network kit
 * Copyright (C) 2005-2006 Jeff Yoshimi <www.jeffyoshimi.net>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.simbrain.network.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.simbrain.network.NetworkPanel;
import org.simbrain.network.nodes.ViewGroupNode;

/**
 * Ungroup group.
 */
public final class UngroupAction
    extends AbstractAction {

    /** Network panel. */
    private final NetworkPanel networkPanel;

    /** Picked node. */
    private final ViewGroupNode node;


    /**
     * Ungroup selected objects.
     *
     * @param networkPanel network panel, must not be null
     */
    public UngroupAction(final NetworkPanel networkPanel, final ViewGroupNode node) {
        super("Ungroup");
        this.node = node;
        if (networkPanel == null) {
            throw new IllegalArgumentException("networkPanel must not be null");
        }

        this.networkPanel = networkPanel;
        putValue(SHORT_DESCRIPTION, "Ungroup objects");
    }


    /** @see AbstractAction */
    public void actionPerformed(final ActionEvent event) {
        networkPanel.unGroup(node, true);
    }
}