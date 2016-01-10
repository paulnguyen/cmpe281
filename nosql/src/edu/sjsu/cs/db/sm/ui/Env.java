package edu.sjsu.cs.db.sm.ui;

import edu.sjsu.cs.db.sm.SM;

public class Env
{
    private static Env s_env;
    private SM m_driver;

    protected Env ()
    {

    }

    public static Env getInstance ()
    {
        if ( s_env == null )
        {
            synchronized ( Env.class )
            {
                if ( s_env == null )
                {
                    s_env = new Env();
                }
            }
        }
        return s_env;
    }

    public void registerDriver( SM _sm )
    {
        m_driver = _sm;
    }

    public SM getRegisteredDriver()
    {
        return m_driver;
    }

    public boolean isDriverRegistered()
    {
        if ( m_driver != null)
            return true;
        else
            return false;
    }

}
