package cokemachine;

/**
 * Write a description of class v1.S0 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class S30 implements State {
    // reference to v1.State machine
    private CokeMachine m;

    public S30(CokeMachine _m) {
        this.m = _m;
    }


    public void insertNickel() {
        m.setState(CokeMachineState.S35);
    }

    public void insertDime() {
        m.setState(CokeMachineState.S40);
    }

    public void insertQuarter() {
        m.setState(CokeMachineState.CHG);
    }

    public void ejectCoke() {
        System.out.println("Not Enough Money!");
    }

}
