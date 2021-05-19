/*
 * Copyright 2021 Urs Zeidler.
 * All rights reserved.
 * This program and the accompanying materials are made available solely under the BSD license "license.txt".
 * Any referenced or included libraries carry licenses of their respective copyright holders.
 */
package de.urszeidler.ascape.ysae_distubution;

import de.urszeidler.ascape.AbstractYardSaleModel;

/**
 * Example implementation for: Oligarchy as a Phase Transition: The effect of
 * wealth-attained advantage in a Fokker-Planck description of asset exchange
 * with redistribution function Bruce M. Boghosian, Adrian Devitt-Lee, Merek
 * Johnson, Jeremy A. Marcq, and Hongyan Wang Department of Mathematics, Tufts
 * University, Medford, Massachusetts 02155, USA (Dated: October 8, 2018)
 */
public class YardSaleModel extends AbstractYardSaleModel {
	private static final long serialVersionUID = 6742573957490905687L;
	private double deltaT = 1.0;
	private double gamma = 0.01;
	private double tau = 0.01;
	private double wealthPropotion = startWealth;// W/N --> W = startWealh * N


	protected YardSaleAgent createAgent() {
		return new YardSaleAgent(startWealth, deltaT, gamma, tau, wealthPropotion);
	}


	public double getDeltaT() {
		return deltaT;
	}


	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;
	}


	public double getGamma() {
		return gamma;
	}


	public void setGamma(double gamma) {
		this.gamma = gamma;
	}


	public double getTau() {
		return tau;
	}


	public void setTau(double tau) {
		this.tau = tau;
	}


	public double getWealthPropotion() {
		return wealthPropotion;
	}


	public void setWealthPropotion(double wealthPropotion) {
		this.wealthPropotion = wealthPropotion;
	}
}
