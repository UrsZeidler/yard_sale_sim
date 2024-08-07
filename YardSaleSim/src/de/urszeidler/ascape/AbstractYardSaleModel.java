/*
 * Copyright 2021 Urs Zeidler.
 * All rights reserved.
 * This program and the accompanying materials are made available solely under the BSD license "license.txt".
 * Any referenced or included libraries carry licenses of their respective copyright holders.
 */
package de.urszeidler.ascape;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.ascape.model.HostCell;
import org.ascape.model.Scape;
import org.ascape.model.event.ScapeEvent;
import org.ascape.model.space.Array2DVonNeumann;
import org.ascape.model.space.Coordinate2DDiscrete;
import org.ascape.util.data.StatCollector;
import org.ascape.util.data.StatCollectorCSAMM;
import org.ascape.util.data.StatCollectorCalculated;
import org.ascape.util.data.StatCollectorCond;
import org.ascape.view.vis.ChartView;
import org.ascape.view.vis.Overhead2DView;

/**
 * The basic behavior for a yard sale model. We collect some data and provide
 * player and a playfield.
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

	private final class StatCollectorSuperrich extends StatCollectorCond {
		private static final long serialVersionUID = -1545296357297192457L;

		private StatCollectorSuperrich() {
			super("super rich");
		}

		public boolean meetsCondition(Object object) {
			return (((AbstractYardSaleAgent) object).getWealth() > richValue);
		}
	}

	private final class StatCollectorPoor extends StatCollectorCond {
		private static final long serialVersionUID = -9009273352726404126L;

		private StatCollectorPoor() {
			super("poor");
		}

		public boolean meetsCondition(Object object) {
			return (((AbstractYardSaleAgent) object).getWealth() < poorValue);
		}
	}

	private final class StatCollectorGini extends StatCollectorCalculated {
		private static final long serialVersionUID = 819462462218968871L;

		public StatCollectorGini() {
			super("gini");
		}

		public double calculateValue() {
			return calculateGini();
		}
	}

	protected int nAgents = 500;
	protected double startWealth = 100;

	protected int latticeHeight = 35;
	protected int latticeWidth = 35;
	// statistic
	protected double richValue;
	protected double poorValue;
	protected double richfactor = 10.0;
	protected double poorfactor = 0.1;

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

		richValue = startWealth * richfactor;
		poorValue = startWealth * poorfactor;

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

		chart = new ChartView("poor and rich tracker");
		agentScape.addView(chart);
		chart.addSeries("Count poor", Color.blue);
		chart.addSeries("Count super rich", Color.gray);

		chart = new ChartView("gini");
		agentScape.addView(chart);
		chart.addSeries("Sum gini", Color.yellow);

		overheadView = new Overhead2DView();
		overheadView.setCellSize(15);
		lattice.addView(overheadView);
	}

	public void scapeSetup(ScapeEvent scapeEvent) {
		((Scape) agentScape).setExtent(nAgents);
	}

	protected void addBasicStatistic(Scape players) {
		StatCollector countPoor = new StatCollectorPoor();
		StatCollector countSuperrich = new StatCollectorSuperrich();
		StatCollectorCSAMM wealthCollector = new StatCollectorWealth();

		StatCollectorGini statCollectorGini = new StatCollectorGini();
		players.addStatCollector(statCollectorGini);

		players.addStatCollector(countPoor);
		players.addStatCollector(countSuperrich);
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

	public double getRichfactor() {
		return richfactor;
	}

	public void setRichfactor(double richfactor) {
		this.richfactor = richfactor;
	}

	public double getPoorfactor() {
		return poorfactor;
	}

	public void setPoorfactor(double poorfactor) {
		this.poorfactor = poorfactor;
	}

	private double calculateGini() {
		Double completeWealth = nAgents * startWealth;
		List<Double> agentWealth = new ArrayList<>(nAgents);
		agentScape.iterator().forEachRemaining(a -> {
			AbstractYardSaleAgent ysa = (AbstractYardSaleAgent) a;
			agentWealth.add(ysa.wealth);
		});
		if (agentWealth.size() < 100) {
			System.err.println("less than 100 agents skip gini calculation");
			return 0.0D;
		}
		List<Double> agentWealthSortedByWealth = agentWealth.stream().sorted().collect(Collectors.toList());
		Map<Integer, List<Double>> wealthByPercent = wealthByPercent(agentWealthSortedByWealth);

		List<Double> precentWealth = IntStream.range(0, wealthByPercent.size()).mapToObj(i -> {
			Double reduce = wealthByPercent.get(i).stream().reduce(0.0D, (a, b) -> a + (b / completeWealth));
			return reduce;
		}).collect(Collectors.toList());
		return lorenzToGini(precentWealth);
	}

	private double lorenzToGini(List<Double> precentWealth) {
		double gini = 0.0D;
		double currentWealth = 0.0D;
		for (int i = 0; i < 100; i++) {
			Double wealth = precentWealth.get(i);
			Double normWealth = ((double) i + 1) / 100;
			currentWealth = currentWealth + wealth;
			Double diff = normWealth - currentWealth;
//			gini = gini + 2 * diff / 100;//do this once not all time
			gini = gini + diff;
		}
		return gini / 50.0D; // shortcut for multiply by 2 and divide by 100
	}

	private Map<Integer, List<Double>> wealthByPercent(List<Double> list) {
		Map<Integer, List<Double>> wealthByPercent = new HashMap<>();
		IntStream.range(0, list.size()).forEach(i -> {
			Double currentPercent = ((double) i / (double) nAgents) * 100;
			List<Double> orDefault = wealthByPercent.getOrDefault(currentPercent.intValue(), new ArrayList<>());
			wealthByPercent.putIfAbsent(currentPercent.intValue(), orDefault);
			orDefault.add(list.get(i));
		});
		return wealthByPercent;
	}

}
