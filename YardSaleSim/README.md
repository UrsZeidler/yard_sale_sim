# Simple implementation of the yard sale model in economics

> “Many men of course became extremely rich, but this was perfectly natural and nothing to be ashamed of because no one was really poor – at least no one worth speaking of.” Douglas Adams, The Ultimate Hitchhiker's Guide to the Galaxy 

This software is a basic implementation of the defined yard sales models in the papers:

> Boghosian, B.M., Johnson, M. & Marcq, J.A. 
An H Theorem for Boltzmann’s Equation for the Yard-Sale Model of Asset Exchange. 
J Stat Phys 161, 1339–1350 (2015). https://doi.org/10.1007/s10955-015-1316-8

> Bruce M. Boghosian, Adrian Devitt-Lee, Merek Johnson, Jeremy A. Marcq, Hongyan Wang 
Oligarchy as a Phase Transition: The effect of wealth-attained advantage in a Fokker-Planck description of asset exchange. 
last revised 20 Feb 2016  https://arxiv.org/abs/1511.00770v2

> Jie Li, Bruce M. Boghosian, Chengli Li,
The Affine Wealth Model: An agent-based model of asset exchange that allows for negative-wealth agents and its empirical validation,
Physica A: Statistical Mechanics and its Applications,
Volume 516, 2019, Pages 423-442, ISSN 0378-4371,https://doi.org/10.1016/j.physa.2018.10.042.(https://www.sciencedirect.com/science/article/pii/S0378437118313906)
https://arxiv.org/pdf/1604.02370.pdf

The sim uses the ascape api for agent based simulations. (http://ascape.sourceforge.net/)

## brief description of the implementation 

The agents move on the plane (a discrete 2D VonNeumann space) and interact with each other defined by the corresponding algorithm. They all started out with the same amount of wealth, so the  Gini coefficient is 0 at the beginning. All models share the same basic data and structures defined by the abstract class _de.urszeidler.ascape.AbstractYardSaleModel_. These are _nAgents_ , _startWealth_  and the dimensions ( _latticeHeight_ , _latticeWidth_ ) of the space. The basic agent behavior is defined by the abstract class _de.urszeidler.ascape.AbstractYardSaleAgent_ and includes the _wealth_ of the agent also the behavior set, like moving in the plane and interact and some style definition (color) depending on the individual wealth. The system is stable in any way, the wealth is constant over time, no agent leaves.  
As agents need to move to change interaction parters, so the space occupation factor starts to impact the original equations when it get crowdy. For example, when the spaceocc. is 100% and there is no movement, the system tends to build local oligarchies. This might be interesting when moving to a more historical perspective, where moving of the capital was some how restricted. The same deviation from the original equations are true for the near oligarchie state, as the 'enviorment' is sucked dry, the few ultra rich wanders the scape, in hope to find another victim to swallow, and the constant fear of being swallowed, so the convergence of the Gini coefficient diverges in the late state of the system.  

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

A simple run of the different model can be found [here](YardSaleSim/doc/README.md "model runs") 

### installation

To use and try the sim, use the eclipse installer and the etc/ide/SimYardSale.setup Oohmp setup, which will install and configure a eclipse installation, and checkout the code and import it.

* download the installer, get the setup file and install


### other refs and license

other yard sale model projects:

http://www.physics.umd.edu/hep/drew/math_general/yard_sale.html



 License
-------

The code is published under the terms of the BSD license see "etc/license.txt".
 
 
<a href="http://with-eclipse.github.io/" target="_blank">
<img alt="with-Eclipse logo" src="http://with-eclipse.github.io/with-eclipse-0.jpg" /></a>