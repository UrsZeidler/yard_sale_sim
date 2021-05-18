/*
 * Copyright 2021 Urs Zeidler.
 * All rights reserved.
 * This program and the accompanying materials are made available solely under the BSD license "license.txt".
 * Any referenced or included libraries carry licenses of their respective copyright holders.
 */
package de.urszeidler.ascape.ysae;

import org.ascape.model.HostCell;
import org.ascape.model.Scape;
import org.ascape.model.space.Array2DVonNeumann;
import org.ascape.model.space.Coordinate2DDiscrete;

import de.urszeidler.ascape.AbstractYardSaleModel;

/**
 * Example implementation for:
 * 
 * Boghosian, B.M., Johnson, M. & Marcq, J.A. An H Theorem for Boltzmann’s
 * Equation for the Yard-Sale Model of Asset Exchange. J Stat Phys 161,
 * 1339–1350 (2015). https://doi.org/10.1007/s10955-015-1316-8
 *
 */
public class YardSaleModel extends AbstractYardSaleModel {
	private static final long serialVersionUID = -3038301542901948230L;

	@Override
	public void createScape() {
		super.createScape();
		lattice = new Scape(new Array2DVonNeumann());
		lattice.setPrototypeAgent(new HostCell());
		lattice.setExtent(new Coordinate2DDiscrete(latticeWidth, latticeHeight));

		YardSaleAgent cgplayer = new YardSaleAgent();
		cgplayer.setHostScape(lattice);
		players = new Scape();
		players.setPrototypeAgent(cgplayer);
		players.setExecutionOrder(Scape.RULE_ORDER);

		add(lattice);
		add(players);
		addBasicStatistic(players);
	}
}
