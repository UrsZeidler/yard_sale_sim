/*
 * Copyright 2021 Urs Zeidler.
 * All rights reserved.
 * This program and the accompanying materials are made available solely under the BSD license "license.txt".
 * Any referenced or included libraries carry licenses of their respective copyright holders.
 */
package de.urszeidler.ascape;

import java.awt.Color;

import org.ascape.model.Scape;
import org.ascape.model.event.ScapeEvent;
import org.ascape.util.data.StatCollector;
import org.ascape.util.data.StatCollectorCSAMM;
import org.ascape.util.data.StatCollectorCond;
import org.ascape.view.vis.ChartView;
import org.ascape.view.vis.Overhead2DView;

/**
 * @author urs
 *
 */
public abstract class AbstractYardSaleModel extends Scape {
	private static final long serialVersionUID = -4242260594178308300L;

	private final class StatCollectorWealth extends StatCollectorCSAMM {
		private static final long serialVersionUID = 8599200513717550780L;

		public StatCollectorWealth() {
			super("wealth");
		}

		public double getValue(Object object) {
			return ((AbstractYardSaleAgent) object).getWealth();
		}
	}

	private final class StatCollectorSuperRitch extends StatCollectorCond {
		private static final long serialVersionUID = -1545296357297192457L;

		private StatCollectorSuperRitch() {
			super("super ritch");
		}

		public boolean meetsCondition(Object object) {
			return (((AbstractYardSaleAgent) object).getWealth() > 1000.0);
		}
	}

	private final class StatCollectorPoor extends StatCollectorCond {
		private static final long serialVersionUID = -9009273352726404126L;

		private StatCollectorPoor() {
			super("poor");
		}

		public boolean meetsCondition(Object object) {
			return (((AbstractYardSaleAgent) object).getWealth() < 10.0);
		}
	}

	protected int nAgents = 500;
	protected int latticeHeight = 35;
	protected int latticeWidth = 35;
	protected Scape lattice;
	protected Scape players;
	private Overhead2DView overheadView;

	protected void addBasicStatistic(Scape players) {
		StatCollector countPoor = new StatCollectorPoor();
		StatCollector countSuperRitch = new StatCollectorSuperRitch();
		StatCollectorCSAMM wealthCollector = new StatCollectorWealth();

		players.addStatCollector(countPoor);
		players.addStatCollector(countSuperRitch);
		players.addStatCollector(wealthCollector);
	}

	public int getnAgents() {
		return nAgents;
	}

	public void setnAgents(int agentNumber) {
		nAgents = agentNumber;
	}

	public void createGraphicViews() {
		super.createGraphicViews();
		ChartView chart = new ChartView();
		players.addView(chart);
		chart.addSeries("Minimum wealth", Color.red);
		chart.addSeries("Maximum wealth", Color.blue);
		chart.addSeries("Average wealth", Color.green);

		chart = new ChartView();
		players.addView(chart);
		chart.addSeries("Count poor", Color.blue);
		chart.addSeries("Count super ritch", Color.gray);
		overheadView = new Overhead2DView();
		overheadView.setCellSize(15);
		lattice.addView(overheadView);
	}

	public void scapeSetup(ScapeEvent scapeEvent) {
		((Scape) players).setExtent(nAgents);
	}

}
