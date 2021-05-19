/*
 * Copyright 2021 Urs Zeidler.
 * All rights reserved.
 * This program and the accompanying materials are made available solely under the BSD license "license.txt".
 * Any referenced or included libraries carry licenses of their respective copyright holders.
 */
package de.urszeidler.ascape.ysae_distubution;

import org.ascape.model.Agent;

import de.urszeidler.ascape.AbstractYardSaleAgent;

/**
 * Example implementation for: Oligarchy as a Phase Transition: The effect of
 * wealth-attained advantage in a Fokker-Planck description of asset exchange
 * redistribution function Bruce M. Boghosian, Adrian Devitt-Lee, Merek Johnson,
 * Jeremy A. Marcq, and Hongyan Wang Department of Mathematics, Tufts
 * University, Medford, Massachusetts 02155, USA (Dated: October 8, 2018)
 */
public class YardSaleAgent extends AbstractYardSaleAgent {
	private static final long serialVersionUID = 8782427748882471750L;
	private double deltaT = 1.0;
	private double gamma = 0.01;
	private double tau = 0.01;

	private double wealthPropotion = wealth;// W/N --> W = startWealh * N

	@Override
	public void play(Agent agent) {
		YardSaleAgent ysa1 = this;
		YardSaleAgent ysa2 = (YardSaleAgent) agent;
		double beta = Math.sqrt(deltaT * gamma) * (randomIs() ? 1.0 : -1.0);
		double dwealth_static = beta * Math.min(ysa1.wealth, ysa2.wealth);
		double redist1 = tau * deltaT * (wealthPropotion - ysa1.getWealth());
		double redist2 = tau * deltaT * (wealthPropotion - ysa2.getWealth());

		ysa2.wealth = ysa2.wealth + dwealth_static + redist2;
		ysa1.wealth = ysa1.wealth - dwealth_static + redist1;
	}
}