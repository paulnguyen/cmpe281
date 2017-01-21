
package tools ;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;

public class KeyPrinter {

    /**
     * to be invoked with these parameters:
     * 
     * [0]:  keystore-password
     * [1]:  filename
     * [2]:  alias
     * [3]:  entry-Password (if necessary)
     */
    public static void main(String[] params)
        throws IOException, GeneralSecurityException
    {
        char[] storePass = params[0].toCharArray();
        String fileName = params[1];
        String alias = params[2];
        KeyStore.ProtectionParameter entryPass;
        if(params.length > 3) {
        entryPass=new KeyStore.PasswordProtection(params[3].toCharArray());
        } else {
            entryPass = null;
        }

        KeyStore store = KeyStore.getInstance("JKS");
        InputStream input = new FileInputStream(fileName);
        store.load(input, storePass);

        KeyStore.Entry entry = store.getEntry(alias, entryPass);
        System.out.println(entry);

    }
}