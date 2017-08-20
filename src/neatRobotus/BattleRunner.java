package neatRobotus;

import robocode.control.*;
import robocode.control.events.*;


public class BattleRunner {
	
	private static Genoma genoma = null;
	
	public BattleRunner( Genoma genoma){
		BattleRunner.genoma = genoma;
	}
	
	public static Genoma getGenoma(){
		return BattleRunner.genoma;
	}

    public void startBatalha() {
        // Disable log messages from Robocode
        RobocodeEngine.setLogMessagesEnabled(true);

        // Create the RobocodeEngine
        //RobocodeEngine engine = new RobocodeEngine(); // Run from current working directory
        RobocodeEngine engine = new RobocodeEngine(new java.io.File("/home/joao/robocode"));
        // Add our own battle listener to the RobocodeEngine 
        engine.addBattleListener(new BattleObserver());

        // Show the Robocode battle view
        engine.setVisible(false);

        // Setup the battle specification

        int numberOfRounds = 10;
        BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
        RobotSpecification[] selectedRobots = engine.getLocalRepository("/home/joao/workspace/Robotus_Codus_Cognitus/src/neatRobotus.RobotusCodus,sample.Corners");

        BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);

        // Run our specified battle and let it run till it is over
        engine.runBattle(battleSpec, true); // waits till the battle finishes

        // Cleanup our RobocodeEngine
        engine.close();
    }
}

//
// Our private battle listener for handling the battle event we are interested in.
//
class BattleObserver extends BattleAdaptor {
	
	private Genoma genoma = BattleRunner.getGenoma();

    // Called when the battle is completed successfully with battle results
    public void onBattleCompleted(BattleCompletedEvent e) {
        System.out.println("-- Battle has completed --");
        
        // Print out the sorted results with the robot names
        System.out.println("Battle results:");
        for (robocode.BattleResults result : e.getSortedResults()) {
        		System.out.println("  " + result.getTeamLeaderName() + ": " + result.getScore());
        }
        robocode.BattleResults robs[] = e.getSortedResults();
        for( int i = 0; i < 2; i++)
        {
        	if(robs[i].getTeamLeaderName().equals("sampleex.Alien"))
        		this.genoma.setFitness(robs[i].getScore());
        }
    }
    
    // Called when the game sends out an information message during the battle
    public void onBattleMessage(BattleMessageEvent e) {
        System.out.println("Msg> " + e.getMessage());
    }

    // Called when the game sends out an error message during the battle
    public void onBattleError(BattleErrorEvent e) {
        System.out.println("Err> " + e.getError());
    }
    
    public void setGenoma( Genoma genoma){
    	this.genoma = genoma;
    }
}
