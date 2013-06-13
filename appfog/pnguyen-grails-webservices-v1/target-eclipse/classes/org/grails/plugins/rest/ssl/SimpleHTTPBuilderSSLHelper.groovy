package org.grails.plugins.rest.ssl

import groovyx.net.http.HTTPBuilder
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.UnrecoverableKeyException
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory
import static org.grails.plugins.rest.ssl.HTTPBuilderSSLConstants.*

/**
 * Through its main method   {@link SimpleHTTPBuilderSSLHelper#addSSLSupport}   and using a   {@link KeyStoreFactory}
 * and a <code>org.apache.http.conn.ssl.SSLSocketFactory</code>
 * it registers an SSL Scheme containing a java.security.KeyStore, loaded with either a <i>keystore</i> and/or a
 * <i>truststore</i>, into the Builder.Client's Connection Manager.
 * By default the it will use a  {@link SimpleKeyStoreFactory}  .
 * Please review the   {@link SimpleKeyStoreFactory}   documentation to be aware of its configuration
 * keys hosted inside the <i>ConfigObject</i>.
 * @see org.apache.http.conn.ssl.SSLSocketFactory
 * @see org.apache.http.conn.scheme.Scheme
 * @see org.grails.plugins.rest.ssl.SimpleKeyStoreFactory
 * @author berngp
 * @since 0.6
 */
class SimpleHTTPBuilderSSLHelper implements HTTPBuilderSSLHelper {

  /**
   * Defines the KeyStoreFactory used if connecting to an endpoints behind SSL.
   * @todo Make sure that there exists a clear mechanism to inject the restPluginKeyStoreFactory through the Spring Context
   */
  KeyStoreFactory restPluginKeyStoreFactory = new SimpleKeyStoreFactory()

  /**
   * If a <i>Key Store</i> and/or a <i>Trust Store</i> are defined it creates a <code>org.apache.http.conn.ssl.SSLSocketFactory</code> attaching
   * it to the given <code>builder.client.connectionManager</code>.
   * The <code>config:ConfigObject</code> may specify the following value(s) among others used by the <i>KeyStoreFactory</i>:
   *   <ul>
   *      <li>https.cert.hostnameVerifier:  As defined by <code>org.apache.http.conn.ssl.SSLSocketFactory</code>, eg.
   *             <code>https.cert.hostnameVerifier=allow_all</code>. If none is specified the default SSLSocketFactory's strategy will be used.</li>
   *   </ul>
   * @see org.apache.http.conn.ssl.SSLSocketFactory
   * @see org.apache.http.conn.scheme.Scheme
   * @see org.grails.plugins.rest.ssl.SimpleKeyStoreFactory
   */
  HTTPBuilder addSSLSupport(ConfigObject config, HTTPBuilder builder) {
    if (!builder) {
      throw new IllegalArgumentException("builder:HTTPBuilder can't be null.")
    }

    def keyStoreModel = restPluginKeyStoreFactory.getKeyStoreModel(config)

    def trustStoreModel = restPluginKeyStoreFactory.getTrustStoreModel(config)

    try {
      def sslSocketFactory
      if (keyStoreModel?.keystore && trustStoreModel?.keystore) {
        sslSocketFactory = new SSLSocketFactory(
                keyStoreModel.keystore, keyStoreModel.password, trustStoreModel.keystore)

      } else if (trustStoreModel?.keystore) {
        sslSocketFactory = new SSLSocketFactory(trustStoreModel.keystore)

      } else if (keyStoreModel?.keystore) {
        sslSocketFactory = new SSLSocketFactory(
                keyStoreModel.keystore,
                keyStoreModel.password,
                keyStoreModel.keystore)
      }

      if (sslSocketFactory) {
        // Set the hostname verifier for the trusted certificates...
        if (config?.https?.cert?.hostnameVerifier) {
          switch (config.https.cert.hostnameVerifier.toUpperCase()) {
            case CERT_HOSTNAME_VERIFIER_STRICT:
              sslSocketFactory.hostnameVerifier = SSLSocketFactory.STRICT_HOSTNAME_VERIFIER
              break;
            case CERT_HOSTNAME_VERIFIER_ALLOW_ALL:
              sslSocketFactory.hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER
              break;
            case CERT_HOSTNAME_VERIFIER_BROWSER_COMPATIBLE:
              sslSocketFactory.hostnameVerifier = SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER
              break;
            default:
              throw new IllegalArgumentException(
                      "The https.cert.hostnameVerifier doesn't match any of the following ${CERT_HOSTNAME_VERIFIERS.join(', ')}")

          }
        }
        //finally register the HTTPS Scheme...
        builder.client.connectionManager.schemeRegistry.register(
                new Scheme(HTTPS, sslSocketFactory, SSL_DEFAULT_PORT)
        )
      } else if (config?.https?.sslSocketFactory?.enforce) {
        throw new IllegalStateException("Unable to load a SSL Socket Factory!")
      }

    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e)

    } catch (KeyManagementException e) {
      throw new IllegalStateException(e)

    } catch (KeyStoreException e) {
      throw new IllegalArgumentException(e)

    } catch (UnrecoverableKeyException e) {
      throw new IllegalArgumentException(e)
    }

    return builder
  }
}

