/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.algo.geograph.test;

import java.io.IOException;
import org.infinispan.Cache;
import org.infinispan.manager.CacheManager;
import org.cloudtm.framework.CloudtmConfig.Framework;
import pt.ist.fenixframework.Config;
import it.algo.geograph.domain.Root;
import org.cloudtm.framework.TransactionalCommand;
import org.cloudtm.framework.Init;
import org.cloudtm.framework.TxManager;
import org.cloudtm.framework.TxSystem;
import it.algo.geograph.domain.GeoObject;
import java.math.BigDecimal;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fabio Cottefoglie <cottefoglie@algorithmica.it>
 */
public class GeoObjectTest {

  private static TxManager txManager = null;
  private static final Void VOID = null;

  public GeoObjectTest() {
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Before
  public void setUp() {
    if(txManager != null) return;
    
    Config config = new Config() {

      {
        domainModelPath = "src/common/dml/geograph.dml";
        dbAlias = "config/infinispanNoFile.xml";
        repositoryType = pt.ist.fenixframework.Config.RepositoryType.INFINISPAN;
        rootClass = Root.class;
      }
    };

    Init.initializeTxSystem(config, Framework.FENIX);
    txManager = TxSystem.getManager();
  }

  @After
  public void tearDown() {
  }

  @Test
  public void createAndFind() {
    txManager.withTransaction(new TransactionalCommand<Void>() {
      // store a geo object

      @Override
      public Void doIt() {
        GeoObject geoObject = new GeoObject();
        // BigDecimal lat = new BigDecimal("42.438878");
        // BigDecimal lon = new BigDecimal("-71.119277");
        // geoObject.setLatitude(lat);
        // geoObject.setLongitude(lon);
        geoObject.setLatitude(new Integer(42));
        geoObject.setLongitude(new Integer(71));
        txManager.save(geoObject);
        Root root = (Root) txManager.getRoot();
        geoObject.setRoot(root);
        return VOID;
      }
    });


    GeoObject geoObject = txManager.withTransaction(new TransactionalCommand<GeoObject>() {
      // load the persisted geo object and check latitude and longitude

      @Override
      public GeoObject doIt() {
        Root root = (Root) txManager.getRoot();
        GeoObject go = root.getGeoObjects().get(0);
        assertEquals(new Integer(42), go.getLatitude());
        assertEquals(new Integer(71), go.getLongitude());
        return go;
      }
    });
    assertEquals(new Integer(42), geoObject.getLatitude());
    assertEquals(new Integer(71), geoObject.getLongitude());
  }

  @Test
  public void ispnStandalone() throws IOException {
    CacheManager manager = new DefaultCacheManager("config/infinispanNoFile.xml");
    Cache cache = manager.getCache();
    cache.put("name", "ispn");
    String name = (String) cache.get("name");
    System.out.println("name is " + name);
  }
}
