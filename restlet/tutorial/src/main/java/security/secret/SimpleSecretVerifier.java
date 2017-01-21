

package security.secret ;

import org.restlet.security.SecretVerifier;

/**
 * Simple secret verifier that checks a hard-coded username and password.
 */
public class SimpleSecretVerifier extends SecretVerifier {
    @Override
    public int verify(String identifier, char[] secret) {
        return (("chunkylover53".equals(identifier)) && compare(
                "pwd".toCharArray(), secret)) ? RESULT_VALID : RESULT_INVALID;
    }
}
