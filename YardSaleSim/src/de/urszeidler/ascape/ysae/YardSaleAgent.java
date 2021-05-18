/*
 * Copyright 2021 Urs Zeidler.
 * All rights reserved.
 * This program and the accompanying materials are made available solely under the BSD license "license.txt".
 * Any referenced or included libraries carry licenses of their respective copyright holders.
 */
package de.urszeidler.ascape.ysae;

import org.ascape.model.Agent;

import de.urszeidler.ascape.AbstractYardSaleAgent;

/**
 * Example implementation for:
 * 
 * Boghosian, B.M., Johnson, M. & Marcq, J.A. An H Theorem for Boltzmann’s
 * Equation for the Yard-Sale Model of Asset Exchange. J Stat Phys 161,
 * 1339–1350 (2015). https://doi.org/10.1007/s10955-015-1316-8
 *
 */
public class YardSaleAgent extends AbstractYardSaleAgent {
	private static final long serialVersionUID = -8934875282932069347L;
	private double factor = 0.1;

	@Override
	public void play(Agent agent) {
		YardSaleAgent ysa1 = this;
		YardSaleAgent ysa2 = (YardSaleAgent) agent;
		double beta = factor * (randomIs() ? 1.0 : -1.0);
		double dwealth = beta * Math.min(ysa1.wealth, ysa2.wealth);
		ysa2.wealth = ysa2.wealth + dwealth;
		ysa1.wealth = ysa1.wealth - dwealth;
	}

	@Override
	public int getAgentSize() {
		if (wealth > 500) {
			int w = (int) (wealth / 500);
			return super.getAgentSize() + w;
		}
		return super.getAgentSize();
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}
}