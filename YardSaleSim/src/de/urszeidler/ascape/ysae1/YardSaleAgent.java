/*
 * Copyright 2021 Urs Zeidler.
 * All rights reserved.
 * This program and the accompanying materials are made available solely under the BSD license "license.txt".
 * Any referenced or included libraries carry licenses of their respective copyright holders.
 */
package de.urszeidler.ascape.ysae1;

import org.ascape.model.Agent;

import de.urszeidler.ascape.AbstractYardSaleAgent;

/**
 * Example implementation for: Oligarchy as a Phase Transition: The effect of
 * wealth-attained advantage in a Fokker-Planck description of asset exchange
 * Bruce M. Boghosian, Adrian Devitt-Lee, Merek Johnson, Jeremy A. Marcq, and
 * Hongyan Wang Department of Mathematics, Tufts University, Medford,
 * Massachusetts 02155, USA (Dated: October 8, 2018)
 */
public class YardSaleAgent extends AbstractYardSaleAgent {
	private static final long serialVersionUID = 8782427748882471750L;
	private double deltaT;
	private double gamma;

	public YardSaleAgent(double startWealth,double deltaT, double gamma) {
		this.wealth = startWealth;
		this.deltaT = deltaT;
		this.gamma = gamma;
	}

	@Override
	public void play(Agent agent) {
		YardSaleAgent ysa1 = this;
		YardSaleAgent ysa2 = (YardSaleAgent) agent;
		double beta = Math.sqrt(deltaT * gamma) * (randomIs() ? 1.0 : -1.0);
		double dwealth = beta * Math.min(ysa1.wealth, ysa2.wealth);
		ysa2.wealth = ysa2.wealth + dwealth;
		ysa1.wealth = ysa1.wealth - dwealth;
	}

}