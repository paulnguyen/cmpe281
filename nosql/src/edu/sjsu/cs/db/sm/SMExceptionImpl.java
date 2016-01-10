package edu.sjsu.cs.db.sm;

public class SMExceptionImpl extends SM.IOException
{
    protected Throwable m_originalException;
    protected String m_msg = null;

    public SMExceptionImpl ( Throwable _e )
    {
        m_originalException = _e;
    }

    public SMExceptionImpl ( Throwable _e, String msg )
    {
        m_originalException = _e;
        m_msg = msg;
    }

    public SMExceptionImpl ( String _message )
    {
        m_originalException = new Exception( _message );
    }

    public String getMessage ()
    {
        if ( m_msg != null )
            return m_msg;
        return m_originalException.getMessage();
    }
}
