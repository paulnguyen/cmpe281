package helma.xmlrpc;

import java.io.InputStream;
import java.io.IOException;

// This class is borrowed from Apache JServ
class ServerInputStream extends InputStream {
    // bytes remaining to be read from the input stream. This is
    // initialized from CONTENT_LENGTH (or getContentLength()).
    // This is used in order to correctly return a -1 when all the
    // data POSTed was read. If this is left to -1, content length is
    // assumed as unknown and the standard InputStream methods will be used
    long available = -1;

    private InputStream in;

    public ServerInputStream(InputStream in, int available) {
        this.in = in;
        this.available = available;
    }

    public int read() throws IOException {
        if (available > 0) {
            available--;
            return in.read();
        } else if (available == -1)
	return in.read ();
        return -1;
    }

    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    public int read(byte b[], int off, int len) throws IOException {
        if (available > 0) {
            if (len > available) {
                // shrink len
                len = (int) available;
            }
            int read = in.read(b, off, len);
            if (read != -1) {
                available -= read;
            } else {
                available = -1;
            }
            return read;
        } else if (available == -1)
            return in.read (b, off, len);
        return -1;
    }

    public long skip(long n) throws IOException {
        long skip = in.skip(n);
        if (available > 0)
            available -= skip;
        return skip;
    }


}
