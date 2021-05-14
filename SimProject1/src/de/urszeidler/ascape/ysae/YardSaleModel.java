/**
 * 
 */
package de.urszeidler.ascape.ysae;

import java.awt.Color;

import org.ascape.model.HostCell;
import org.ascape.model.Scape;
import org.ascape.model.event.ScapeEvent;
import org.ascape.model.space.Array2DVonNeumann;
import org.ascape.model.space.Coordinate1DDiscrete;
import org.ascape.model.space.Coordinate2DDiscrete;
import org.ascape.util.data.StatCollector;
import org.ascape.util.data.StatCollectorCond;
import org.ascape.view.vis.ChartView;
import org.ascape.view.vis.Overhead2DView;

/**
 * Example implementation for:
 * 
 * Boghosian, B.M., Johnson, M. & Marcq, J.A. An H Theorem for 
 * Boltzmann’s Equation for the Yard-Sale Model of Asset Exchange. 
 * J Stat Phys 161, 1339–1350 (2015). https://doi.org/10.1007/s10955-015-1316-8
 *
 */
public class YardSaleModel extends Scape {
	protected int nPlayers = 500;
	protected int latticeHeight = 30;
	protected int latticeWidth = 30;
	Scape lattice;
	Scape players;
	private Overhead2DView overheadView;

	public YardSaleModel() {
		super();
	}

	@SuppressWarnings("serial")
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

		StatCollector countPoor = new StatCollectorCond("poor") {
			public boolean meetsCondition(Object object) {
				return (((YardSaleAgent) object).getWealth() < 10.0);
			}
		};
		StatCollector countSuperRitch = new StatCollectorCond("super ritch") {
			public boolean meetsCondition(Object object) {
				return (((YardSaleAgent) object).getWealth() > 1000.0);
			}
		};
		players.addStatCollector(countPoor);
		players.addStatCollector(countSuperRitch);

	}

	public void createGraphicViews() {
		super.createGraphicViews();
		ChartView chart = new ChartView();
		players.addView(chart);
		chart.addSeries("Count medium poor", Color.red);
		chart.addSeries("Count poor", Color.blue);
		chart.addSeries("Count ritch", Color.CYAN);
		chart.addSeries("Count super ritch", Color.gray);
		overheadView = new Overhead2DView();
		overheadView.setCellSize(15);
		lattice.addView(overheadView);
	}

	public void scapeSetup(ScapeEvent scapeEvent) {
		((Scape) players).setExtent(nPlayers);
	}

	// populate the model with agents
	public void onSetup() {
		((Scape) players).setExtent(new Coordinate1DDiscrete(nPlayers));
	}

	public int getnPlayers() {
		return nPlayers;
	}

	public void setnPlayers(int NewnPlayers) {
		nPlayers = NewnPlayers;

	}

}
