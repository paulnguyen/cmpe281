package helma.xmlrpc;

/**
 *  Provides encoding of raw bytes to base64-encoded characters, and
 *  decoding of base64 characters to raw bytes.
 *
 * @author Kevin Kelley (kelley@iguana.ruralnet.net)
 * @version 1.0
 * @date 06 August 1998
 */

public class Base64
{
    /**
     * returns an array of base64-encoded characters to represent the
     * passed data array.
     *
     * @param data the array of bytes to encode
     * @return base64-coded character array.
     */
    
    static public char[] encode(byte[] data) {
	
	char[] out = new char[((data.length + 2) / 3) * 4];
	//
	// 3 bytes encode to 4 chars.  Output is always an even
	// multiple of 4 characters.
	//
	
	for (int i = 0, index = 0; i < data.length; i += 3,
		 index += 4) {
	    boolean quad = false;
	    boolean trip = false;
	    int val = (0xFF & (int) data[i]);
	    
	    val <<= 8;
	    if ((i + 1) < data.length) {
		val |= (0xFF & (int) data[i + 1]);
		trip = true;
	    }
	    val <<= 8;
	    if ((i + 2) < data.length) {
		val |= (0xFF & (int) data[i + 2]);
		quad = true;
	    }
	    out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
	    val >>= 6;
	    out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
	    val >>= 6;
	    out[index + 1] = alphabet[val & 0x3F];
	    val >>= 6;
	    out[index + 0] = alphabet[val & 0x3F];
	}
	return out;
    }



    /**
     * Returns an array of bytes which were encoded in the passed
     * character array.
     *
     * @param data the array of base64-encoded characters
     * @return decoded data array
     */
    static public byte[] decode(byte[] data) {
	int len = ((data.length + 3) / 4) * 3;
	if (data.length > 0 && data[data.length - 1] == '=')
	    --len;
	if (data.length > 1 && data[data.length - 2] == '=')
	    --len;
	byte[] out = new byte[len];

	int shift = 0; // # of excess bits stored in accum
	int accum = 0; // excess bits
	int index = 0;

	for (int ix = 0; ix < data.length; ix++) {
	    int value = codes[data[ix] & 0xFF]; // ignore high byte of char
	    if (value >= 0) { 
		// skip over non-code
		accum <<= 6; // bits shift up by 6 each time thru
		shift += 6; // loop, with new bits being put in
		accum |= value; // at the bottom.
		if (shift >= 8) { 
		    // whenever there are 8 or more shifted in,
		    shift -= 8; // write them out (from the top, leaving any
		    // excess at the bottom for next iteration.
		    out[index++] = (byte)((accum >> shift) & 0xff);
		}
	    }
	}

	if (index != out.length) throw new RuntimeException("Error decoding BASE64 element: miscalculated data length!");

	return out;
    }

    //
    // code characters for values 0..63
    //

    static private char[] alphabet =
               
	"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
	.toCharArray();

    //
    // lookup table for converting base64 characters to value in range 0..63
    //

    static private byte[] codes = new byte[256];
	
    static {
	for (int i = 0; i < 256; i++)
	    codes[i] = -1;
		
	for (int i = 'A'; i <= 'Z'; i++)
	    codes[i] = (byte)(i - 'A');
		
	for (int i = 'a'; i <= 'z'; i++)
	    codes[i] = (byte)(26 + i - 'a');

	for (int i = '0'; i <= '9'; i++)
	    codes[i] = (byte)(52 + i - '0');

	codes['+'] = 62;
	codes['/'] = 63;
    }
}
