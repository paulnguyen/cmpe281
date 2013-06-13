package org.grails.plugins.rest.ssl

/**
 *
 * KeyStore Factory contract that should provide a mechanism to create and load a java.security.KeyStore with a
 * <i>keystore</i> and/or <i>truststore</i>.
 *
 * @author berngp
 */
interface KeyStoreFactory {

  /**
   * Loads a given Key Store returning the following model that most contain.
   * <ul>
   *  <li>keystore: Instance of a java.security.KeyStore that has been configured with a specific <i>Key Store</i>.</li>
   *  <li>password: Password used to access such <i>Key Store</i>.</li>
   * </ul>
   */
  Map getKeyStoreModel(ConfigObject config)
  /**
   * Loads a given <i>Trust Store</i> returning the following model that most contain.
   * <ul>
   *  <li>keystore: Instance of a java.security.KeyStore that has been configured with the specific <i>Trust Store</i>.</li>
   * </ul>
   */
  Map getTrustStoreModel(ConfigObject config)

}

