package cokemachine;

/**
 * Write a description of class v1.CokeMachine here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CokeMachine implements State {
    S0 s0;
    S5 s5;
    S10 s10;
    S15 s15;
    S20 s20;
    S25 s25;
    S30 s30;
    S35 s35;
    S40 s40;
    S45 s45;
    S50 s50;
    CHG chg;
    State current; // current state

    /**
     * Constructor for objects of class v1.CokeMachine
     */
    public CokeMachine() {
        s0 = new S0(this);
        s5 = new S5(this);
        s10 = new S10(this);
        s15 = new S15(this);
        s20 = new S20(this);
        s25 = new S25(this);
        s30 = new S30(this);
        s35 = new S35(this);
        s40 = new S40(this);
        s45 = new S45(this);
        s50 = new S50(this);
        chg = new CHG(this);
        current = s0;
    }

    void showState() {
        System.out.println(current.getClass().getName());
    }

    /**
     * @param s next state to transition to
     */
    void setState(CokeMachineState s) {
        switch (s) {
            case S0:
                current = s0;
                break;
            case S5:
                current = s5;
                break;
            case S10:
                current = s10;
                break;
            case S15:
                current = s15;
                break;
            case S20:
                current = s20;
                break;
            case S25:
                current = s25;
                break;
            case S30:
                current = s30;
                break;
            case S35:
                current = s35;
                break;
            case S40:
                current = s40;
                break;
            case S45:
                current = s45;
                break;
            case S50:
                current = s40;
                break;
            case CHG:
                current = chg;
                break;
        }
    }

    public void insertNickel() {
        current.insertNickel();
    }

    public void insertDime() {
        current.insertDime();
    }

    public void insertQuarter() {
        current.insertQuarter();
    }

    public void ejectCoke() {
        current.ejectCoke();
    }

}
