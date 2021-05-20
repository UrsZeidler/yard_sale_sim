/*
 * Copyright 2021 Urs Zeidler.
 * All rights reserved.
 * This program and the accompanying materials are made available solely under the BSD license "license.txt".
 * Any referenced or included libraries carry licenses of their respective copyright holders.
 */
package de.urszeidler.ascape;

import java.awt.Color;

import org.ascape.model.HostCell;
import org.ascape.model.Scape;
import org.ascape.model.event.ScapeEvent;
import org.ascape.model.space.Array2DVonNeumann;
import org.ascape.model.space.Coordinate2DDiscrete;
import org.ascape.util.data.StatCollector;
import org.ascape.util.data.StatCollectorCSAMM;
import org.ascape.util.data.StatCollectorCond;
import org.ascape.view.vis.ChartView;
import org.ascape.view.vis.Overhead2DView;

/**
 * The basic behavior for a yard sale model. We collect some data and provide player and a playfield.
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
	protected double startWealth = 100;

	protected int latticeHeight = 35;
	protected int latticeWidth = 35;
	protected Scape lattice;
	protected Scape agentScape;
	private Overhead2DView overheadView;

	protected abstract AbstractYardSaleAgent createAgent();
	
	@Override
	public void createScape() {
		super.createScape();
		lattice = new Scape(new Array2DVonNeumann());
		lattice.setPrototypeAgent(new HostCell());
		lattice.setExtent(new Coordinate2DDiscrete(latticeWidth, latticeHeight));

		AbstractYardSaleAgent agent = createAgent();
		agent.setHostScape(lattice);
		agentScape = new Scape();
		agentScape.setPrototypeAgent(agent);
		agentScape.setExecutionOrder(Scape.RULE_ORDER);

		add(lattice);
		add(agentScape);
		addBasicStatistic(agentScape);
	}

	@Override
	public void createGraphicViews() {
		super.createGraphicViews();
		ChartView chart = new ChartView("Wealth tracker");
		agentScape.addView(chart);
		chart.addSeries("Minimum wealth", Color.red);
		chart.addSeries("Maximum wealth", Color.blue);
		chart.addSeries("Average wealth", Color.green);
	
		chart = new ChartView("poor and ritch tracker");
		agentScape.addView(chart);
		chart.addSeries("Count poor", Color.blue);
		chart.addSeries("Count super ritch", Color.gray);
		overheadView = new Overhead2DView();
		overheadView.setCellSize(15);
		lattice.addView(overheadView);
	}

	public void scapeSetup(ScapeEvent scapeEvent) {
		((Scape) agentScape).setExtent(nAgents);
	}

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

	public int getLatticeHeight() {
		return latticeHeight;
	}

	public void setLatticeHeight(int latticeHeight) {
		this.latticeHeight = latticeHeight;
	}

	public int getLatticeWidth() {
		return latticeWidth;
	}

	public void setLatticeWidth(int latticeWidth) {
		this.latticeWidth = latticeWidth;
	}

	public double getStartWealth() {
		return startWealth;
	}

	public void setStartWealth(double startWealth) {
		this.startWealth = startWealth;
	}

}
