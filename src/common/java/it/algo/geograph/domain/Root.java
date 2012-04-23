package it.algo.geograph.domain;

import org.cloudtm.framework.TxManager;
import org.cloudtm.framework.TxSystem;

public class Root extends Root_Base {

  public Root() {
    super();
  }

  public static Root createNewRoot() {
    Root r = new Root();
    if (TxSystem.getManager() != null) {
      TxSystem.getManager().setRootIdIfNeeded(r);
    }
    return r;
  }
}
