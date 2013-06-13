package org.grails.plugins.rest.ssl

import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Implements a Basic <i>KeyStore Factory</i> to generate a {@link java.security.KeyStore} loaded with either
 * a Key Store and/or Trust Store files into it if such store files are available.
 * See the specific   {@link SimpleKeyStoreFactory#getKeyStoreModel(ConfigObject)}
 * and   {@link SimpleKeyStoreFactory#getTrustStoreModel(ConfigObject)}
 * methods for details.
 * @author berngp
 */
class SimpleKeyStoreFactory implements KeyStoreFactory {

  private static final Logger log = LoggerFactory.getLogger(SimpleKeyStoreFactory); 
  /** Default Key Store file.   */
  private static final String DEFAULT_KEYSTORE = ".keystore"
  /** Default Trust Store Classpath file.  */
  private static final String DEFAULT_CLASSPATH_TRUSTSTORE = "/truststore.jks"
  /** Set of common default Key/Trust Store files passwords.   */
  private static final def COMMON_PASSWORDS = Collections.unmodifiableSet(['', 'changeit', 'changeme'] as LinkedHashSet)

  /** Loads a resource from the FileSystem given a specific path.  */
  protected def getResourceFromFile = { String path ->
    def resource = new FileSystemResource(path)
    try {
      return resource.URL ? resource : null
    } catch (FileNotFoundException e) {
      log.trace "Unable to load ${path} through the File System.", e
    }
    // --
    return null
  }

  /** Loads a resource from the ClassPath given a specific path.  */
  protected def getResourceFromClassPath = { String path ->
    def resource = new ClassPathResource(path)
    try {
      return resource.URL ? resource : null
    } catch (FileNotFoundException e) {
      log.trace "Unable to load ${path} through the Class Loader.", e
    }
    // --
    return null
  }

  /**
   * if a correct password was found that means that we were able to open the resource and add it successfully to our keyStore,
   *  if so proceed and deliver a model of our Key Store which includes...
   *     <ul>
   *        <li>path:Path from for the keystore.</li>
   *        <li>URL:Actual URL as given by either the Classi Loader or the File System.</li>
   *        <li>password:The password used to open such store.</li>
   *        <li>keystore:<code>java.security.KeyStore</code>.</li>
   *     </ul>
   * if not return null.
   *
   */
  private Map getKeyStoreInternal( def path, def knownPasswd ){
    // We need a ref to a Resource
    def resource
    // lets try first through the path
    if (path) {
      //lets see if a resource is available through the Classpath.
      //resource = getResourceFromClassPath(path)
      if (!(resource = getResourceFromClassPath(path))) {
        //if not lets check the file system.
        resource = getResourceFromFile(path)
      }
    }
    // if we have a Resource
    if (resource) {
      //obtain an Instance of a javax.security.KeyStore used to host our keystore
      def keyStore = KeyStore.getInstance(KeyStore.defaultType)
      //and define a set passwords we will use to open such store, if non are defined we will use a default set
      def keyStorePasswds = knownPasswd ? [knownPasswd] as LinkedHashSet : COMMON_PASSWORDS

      String correctPasswd
      for (String passwd: keyStorePasswds) {
        try {
          keyStore.load(resource.inputStream, passwd.toCharArray())
          correctPasswd = passwd;
          break;
        } catch (CertificateException e) {
          log.debug e.message,e
        } catch (NoSuchAlgorithmException e) {
          log.debug e.message,e
        } catch (FileNotFoundException e) {
          log.debug e.message,e
        } catch (IOException e) {
          log.debug e.message,e
        }
      }
      return correctPasswd ? [path: path, URL: resource.URL.toString(), keystore: keyStore, password: correctPasswd] : null

    }
    // if not
    return [ : ]
  }

  /**
   * Used to specify a getter for the default directory hosting the KeyStore. Currently it is set to the <i>User's Home</i>
   * as seen by System.getProperty('user.home').
   */
  protected getDefaultKeyStoreHome() { return System.getProperty('user.home') }

  /**
   * Looks for the <i>Key Store</i> file, loads it, and generates a Map from it as specified in the  {@link #getKeyStoreInternal}   method.
   * The <code>config:ConfigObject</code> may specify a path for the Key Store through <i>https.keystore.path</i>, if none is specified it
   * will use a path defined as "<i>Default Key Store Home ( see  {@link #getDefaultKeyStoreHome()}  )</i><b>/</b><i>( value of  {@link #DEFAULT_KEYSTORE} )</i>" .
   * In the same manner a password for such Key Store might be specified through <i>https.keystore.pass</i>,
   * If none we will try to guess using common default keystore passwords as defined by the  {@link #COMMON_PASSWORDS}  set.
   */
  Map getKeyStoreModel(groovy.util.ConfigObject config) {
    def path = config?.https?.keystore?.path ?: "${this.defaultKeyStoreHome}/${DEFAULT_KEYSTORE}"
    def passwd = config?.https?.keystore?.pass

    getKeyStoreInternal(path, passwd)
  }
  /**
   * Looks for the <i>Trust Store</i> file, loads it, and generates a Map from it as specified in the   {@link #getKeyStoreInternal}   method.
   * The <code>config:ConfigObject</code> may specify a path for the Trust Store through <i>https.truststore.path</i>, if none is specified it
   * will use   {@link #DEFAULT_CLASSPATH_TRUSTSTORE}  . In the same manner a password for such Trust Store might be specified through <i>https.truststore.pass</i>,
   * If none we will try to guess using common default keystore passwords.
   */
  Map getTrustStoreModel(groovy.util.ConfigObject config) {

    // Lets try to get a path from the config and if not set it to a default.
    def path = config?.https?.truststore?.path ?: DEFAULT_CLASSPATH_TRUSTSTORE
    def passwd = config?.https?.truststore?.pass

    getKeyStoreInternal(path, passwd)
  }
}

