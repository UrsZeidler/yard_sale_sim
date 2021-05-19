/*
 * Copyright 2021 Urs Zeidler.
 * All rights reserved.
 * This program and the accompanying materials are made available solely under the BSD license "license.txt".
 * Any referenced or included libraries carry licenses of their respective copyright holders.
 */
package de.urszeidler.ascape.ysae1;

import de.urszeidler.ascape.AbstractYardSaleModel;

/**
 * Example implementation for: Oligarchy as a Phase Transition: The effect of
 * wealth-attained advantage in a Fokker-Planck description of asset exchange
 * Bruce M. Boghosian, Adrian Devitt-Lee, Merek Johnson, Jeremy A. Marcq, and
 * Hongyan Wang Department of Mathematics, Tufts University, Medford,
 * Massachusetts 02155, USA (Dated: October 8, 2018)
 */
public class YardSaleModel extends AbstractYardSaleModel {
	private static final long serialVersionUID = 6742573957490905687L;

	protected YardSaleAgent createAgent() {
		return new YardSaleAgent();
	}
}
