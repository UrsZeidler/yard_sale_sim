/**
 * 
 */
package de.urszeidler.ascape.ysae_distubution;

import java.awt.Color;

import org.ascape.model.Agent;
import org.ascape.model.CellOccupant;

/**
 * Example implementation for:
 * Oligarchy as a Phase Transition: The effect of wealth-attained advantage in a
 * Fokker-Planck description of asset exchange redistribution function
 * Bruce M. Boghosian,  Adrian Devitt-Lee,  Merek Johnson,  Jeremy A. Marcq,  and Hongyan Wang 
 * Department of Mathematics, Tufts University, Medford, Massachusetts 02155, USA
 * (Dated: October 8, 2018)
 */
public class YardSaleAgent extends CellOccupant {
	private static final long serialVersionUID = 8782427748882471750L;
	private double wealth = 200.0;
	private double deltaT = 1.0;
	private double gamma = 0.1;
	
	private Color[] colors = { Color.white, Color.gray, Color.red, Color.orange,Color.pink};
	
	public void initialize() {
		
	}
	public void scapeCreated() {
	      getScape().addInitialRule(MOVE_RANDOM_LOCATION_RULE);
	getScape().addRule(RANDOM_WALK_RULE);
	getScape().addRule(UPDATE_RULE);
	getScape().addRule(PLAY_RANDOM_NEIGHBOR_RULE);
	}
	
	@Override
	public void play(Agent agent) {
		YardSaleAgent ysa1 = this;
		YardSaleAgent ysa2 = (YardSaleAgent)agent;
		double beta = Math.sqrt(deltaT * gamma)  * ( randomIs() ? 1.0 : -1.0);
		double dwealth = beta * Math.min(ysa1.wealth, ysa2.wealth);
		ysa2.wealth = ysa2.wealth + dwealth;
		ysa1.wealth = ysa1.wealth - dwealth;
	}
	
	@Override
	public int getAgentSize() {
		if(wealth>500) {
			int w = (int) (wealth/500);
			return super.getAgentSize()+w;
		}
		return super.getAgentSize();
	}
	
	@Override
	public Color getColor() {
		int w = (int) (wealth/50);
		if(wealth>1000)
			return Color.black;
		
		return w>colors.length-1 ?  Color.blue : colors[w];
	}
	public double getWealth() {
		return wealth;
	}
	public void setWealth(double wealth) {
		this.wealth = wealth;
	}
}