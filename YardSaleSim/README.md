# Simple implementation of the yard sale model in economics

This software is a basic implementation of the defined models in the papers:

> Boghosian, B.M., Johnson, M. & Marcq, J.A. 
An H Theorem for Boltzmann’s Equation for the Yard-Sale Model of Asset Exchange. 
J Stat Phys 161, 1339–1350 (2015). https://doi.org/10.1007/s10955-015-1316-8

> Bruce M. Boghosian, Adrian Devitt-Lee, Merek Johnson, Jeremy A. Marcq, Hongyan Wang 
Oligarchy as a Phase Transition: The effect of wealth-attained advantage in a Fokker-Planck description of asset exchange. 
last revised 20 Feb 2016  https://arxiv.org/abs/1511.00770v2

The sim uses the ascape api for gent based simulations. (http://ascape.sourceforge.net/)

The agents move on the plane (a discrete 2D VonNeumann space) and interact with each other defined by the corresponding algorithm. The all started out with the same amount of wealth, so the GiniQuifizent is 0, at the beginning. All models share the same basic data and structures defined by the abstract class _de.urszeidler.ascape.AbstractYardSaleModel_. These are _nAgents_ , _startWealth_  and the dimensions ( _latticeHeight_ , _latticeWidth_ ) of the space. The basic agent behavior is defined by the abstract class _de.urszeidler.ascape.AbstractYardSaleAgent_ and includes the _wealth_ of the agent also the behavior set, like moving in the plane and interact and some style definition (color) depending on the individual wealth. The system is stable in any way, the wealth is constant over time, no agent leaves.

The predefined values are:

nAgent: 500  
startWealth: 100  
latticeHeight: 35  
latticeWidth: 35  
space: 1225 = latticeHeight * latticeWidth  
spaceOcc: 40.82% = space / nAgent  
completeWealth: 50.000 = nAgents * startWealth  

 
* de.urszeidler.ascape.ysae.YardSaleModel 
* de.urszeidler.ascape.ysae1.YardSaleModel 
* de.urszeidler.ascape.ysae_distubution.YardSaleModel 

All model collect some basic statistic about the economics of the system.

* number of poor agents 
* number of super rich agents 
* maximum, minimum and average wealth

An agent is counted as rich if it wealth is greater than richfactor * startWealth  and as poor when the wealth is lower than poorfactor * startWealth . The default value are 1.000, ten times the average,and 10 ten times less than average.

You can tweak these values between model runs. For details see http://ascape.sourceforge.net/index.html#Exploring


To use and try the sim, use the eclipse installer and the etc/ide/SimYardSale.setup Oohmp setup, which will install and configure a eclipse installation, and checkout the code and import it.

* download the installer, get the setup file and install
