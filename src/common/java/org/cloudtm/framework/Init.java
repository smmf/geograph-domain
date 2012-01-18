package org.cloudtm.framework;

import pt.ist.fenixframework.Config;

/**
 * @file Init.java
 *
 * @brief Brief file description.
 *
 * Detailed file description.
 *
 * @author Fabio Cottefoglie <cottefoglie@algorithmica.it>
 * @date Dec 29, 2011
 */
/**
 * @class Init
 *
 * @brief Init brief description.
 *
 * Init detailed description.
 *
 * @note Type class notes here.
 * @todo Type todo here.
 */
public class Init {

  /* The following method is invoked to initialize the TxSystem.
  It has no code, but by invoking it this class gets loaded and the previous static code runs once */
  public static void initializeTxSystem(Config config, CloudtmConfig.Framework framework) {
    TxSystem.initialize(config, framework);
  }
}
