/*
 * Copyright 2021 Urs Zeidler.
 * All rights reserved.
 * This program and the accompanying materials are made available solely under the BSD license "license.txt".
 * Any referenced or included libraries carry licenses of their respective copyright holders.
 */
package de.urszeidler.ascape;

import java.awt.Color;

import org.ascape.model.CellOccupant;

/**
 * The basic behavior of a yard sale agent. 
 * The agent has wealth, and a color depending on that wealth.
 * And some basic random walk rule to engage in trade.
 */
public abstract class AbstractYardSaleAgent extends CellOccupant {
	private static final long serialVersionUID = 8636605561834666271L;
	private static final Color[] COLORS = { Color.white, Color.gray, Color.red, Color.orange, Color.pink };

	protected double wealth;

	public void scapeCreated() {
		getScape().addInitialRule(MOVE_RANDOM_LOCATION_RULE);
		getScape().addRule(RANDOM_WALK_RULE);
		getScape().addRule(UPDATE_RULE);
		getScape().addRule(PLAY_RANDOM_NEIGHBOR_RULE);
	}

	@Override
	public Color getColor() {
		int w = (int) (wealth / 50);
		if (wealth > 1000)
			return Color.black;

		return w > COLORS.length - 1 ? Color.blue : COLORS[w];
	}

	public double getWealth() {
		return wealth;
	}

	public void setWealth(double wealth) {
		this.wealth = wealth;
	}

}
