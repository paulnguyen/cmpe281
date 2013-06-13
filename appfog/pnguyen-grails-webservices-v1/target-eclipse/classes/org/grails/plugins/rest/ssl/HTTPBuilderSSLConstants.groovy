package org.grails.plugins.rest.ssl

/**
 * Constants used across the different SSL Support classes.
 * @author berngp
 */
interface HTTPBuilderSSLConstants {

  /** The URL reqeusted doesn't need to match the URL in the Certificate.  */
  String CERT_HOSTNAME_VERIFIER_ALLOW_ALL = 'ALLOW_ALL'
  /** The URL reqeusted needs to match the URL in the Certificate.  */
  String CERT_HOSTNAME_VERIFIER_STRICT = 'STRICT'
  /** The URL reqeusted must be in the same domain as the one in the Certificate.  */
  String CERT_HOSTNAME_VERIFIER_BROWSER_COMPATIBLE = 'BROWSER_COMPATIBLE'
  /** Certificates Hostnames verfifiers valid options. */
  Set CERT_HOSTNAME_VERIFIERS = Collections.unmodifiableSet(
          [
                  CERT_HOSTNAME_VERIFIER_ALLOW_ALL,
                  CERT_HOSTNAME_VERIFIER_STRICT,
                  CERT_HOSTNAME_VERIFIER_BROWSER_COMPATIBLE
          ] as LinkedHashSet
  )

  /** */
  String HTTPS = "https"
  /** */
  int SSL_DEFAULT_PORT = 443

}

