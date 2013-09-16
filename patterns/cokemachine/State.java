package cokemachine;

/**
 * Interface for Coke Machine
 *
 * @author (Paul Nguen)
 * @version (1.0)
 */
public interface State {
    public void insertNickel();

    public void insertDime();

    public void insertQuarter();

    public void ejectCoke();

}

