package cokemachine;

/**
 * Write a description of class v1.S0 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CHG implements State {
    // reference to v1.State machine
    private CokeMachine m;

    public CHG(CokeMachine _m) {
        this.m = _m;
    }

    public void insertNickel() {
        m.setState(CokeMachineState.CHG);
    }

    public void insertDime() {
        m.setState(CokeMachineState.CHG);
    }

    public void insertQuarter() {
        m.setState(CokeMachineState.CHG);
    }

    public void ejectCoke() {
        m.setState(CokeMachineState.S0);
        System.out.println("Coke Ejected!  Don't forget your change.  Have a nice day!");
    }

}
