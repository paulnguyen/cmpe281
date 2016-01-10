package edu.sjsu.cs.db.sm;

/**
 * class servs as a factory to get access to SM implementation
 *
 * @author Denis Gefter
 */

public class SMFactory
{
	private static SM s_smImplVersion;
	
	public static SM getSM() {
		return SMFactory.getInstance() ;
	}

	public static SM getInstance()
	{
		if ( s_smImplVersion == null )
		{
			synchronized ( SMImplVersion1.class )
			{
				if ( s_smImplVersion == null )
				{
					//s_smImplVersion = new FileStorage();
					s_smImplVersion = new SMImplVersion2();
				}
			}
		}
        return s_smImplVersion;
	}

    protected SMFactory()
    {

    }
}
