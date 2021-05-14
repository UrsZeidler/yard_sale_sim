/**
 * 
 */
package de.urszeidler.ascape.ysae;

import java.awt.Color;

import org.ascape.model.Agent;
import org.ascape.model.CellOccupant;

/**
 * Example implementation for:
 * 
 * Boghosian, B.M., Johnson, M. & Marcq, J.A. An H Theorem for 
 * Boltzmann’s Equation for the Yard-Sale Model of Asset Exchange. 
 * J Stat Phys 161, 1339–1350 (2015). https://doi.org/10.1007/s10955-015-1316-8
 *
 */
public class YardSaleAgent extends CellOccupant {

	private double wealth = 100.0;
	protected Color myColor;
	private double factor = 0.1;
	
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
		double beta = factor  * ( randomIs() ? 1.0 : -1.0);
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
	public double getFactor() {
		return factor;
	}
	public void setFactor(double factor) {
		this.factor = factor;
	}
}