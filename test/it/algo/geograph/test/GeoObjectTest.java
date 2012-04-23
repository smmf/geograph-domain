/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.algo.geograph.test;

import it.algo.geograph.domain.Agent;
import java.util.Iterator;
import java.io.IOException;
import org.cloudtm.framework.CloudtmConfig.Framework;
import pt.ist.fenixframework.Config;
import it.algo.geograph.domain.Root;
import org.cloudtm.framework.TransactionalCommand;
import org.cloudtm.framework.Init;
import org.cloudtm.framework.TxManager;
import org.cloudtm.framework.TxSystem;
import it.algo.geograph.domain.GeoObject;
import java.math.BigDecimal;
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
    if (txManager != null) {
      return;
    }

    Config config = new Config() {

      {
        domainModelPath = "src/common/dml/geograph.dml";
        dbAlias = "config/infinispanFile.xml";
        repositoryType = pt.ist.fenixframework.Config.RepositoryType.INFINISPAN;
        rootClass = Root.class;
      }
    };

    Init.initializeTxSystem(config, Framework.ISPN);
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
        BigDecimal lat = new BigDecimal("42.438878");
        BigDecimal lon = new BigDecimal("-71.119277");
        geoObject.setLatitude(lat);
        geoObject.setLongitude(lon);
        txManager.save(geoObject);
        Root root = (Root) txManager.getRoot();
        geoObject.setRoot(root);
        root.setNumGeoObjectIds(1);
        return VOID;
      }
    });


    txManager.withTransaction(new TransactionalCommand<GeoObject>() {
      // load the persisted geo object and check latitude and longitude

      @Override
      public GeoObject doIt() {
        Root root = (Root) txManager.getRoot();
        GeoObject go = (GeoObject) root.getGeoObjects().toArray()[0];
        assertEquals(new BigDecimal("42.438878"), go.getLatitude());
        assertEquals(new BigDecimal("-71.119277"), go.getLongitude());
        return go;
      }
    });
  }

  @Test
  public void edgeAssociations() throws IOException {
    txManager.withTransaction(new TransactionalCommand<Void>() {

      @Override
      public Void doIt() {
        // Create two geo objects
        GeoObject geoObject1 = new GeoObject();
        BigDecimal lat1 = new BigDecimal("42.438878");
        BigDecimal lon1 = new BigDecimal("-71.119277");
        geoObject1.setLatitude(lat1);
        geoObject1.setLongitude(lon1);
        txManager.save(geoObject1);
        Root root = (Root) txManager.getRoot();
        geoObject1.setRoot(root);

        GeoObject geoObject2 = new GeoObject();
        BigDecimal lat2 = new BigDecimal("43.438878");
        BigDecimal lon2 = new BigDecimal("-71.119277");
        geoObject2.setLatitude(lat2);
        geoObject2.setLongitude(lon2);
        txManager.save(geoObject2);
        geoObject2.setRoot(root);

        // Associate geo objects with a link
        geoObject1.addIncoming(geoObject2);
        assertEquals(lat1, ((GeoObject) geoObject2.getOutcoming().toArray()[0]).getLatitude());
//        txManager.save(edge);
        return VOID;
      }
    });

    txManager.withTransaction(new TransactionalCommand<Void>() {

      @Override
      public Void doIt() {
        Root root = (Root) txManager.getRoot();
        Iterator iter = root.getGeoObjects().iterator();
        while (iter.hasNext()) {
          GeoObject go = (GeoObject) iter.next();
          if(go.getIncomingCount() > 0){
            GeoObject goFrom = (GeoObject) go.getIncoming().toArray()[0];
            assertEquals(go, goFrom.getOutcoming().toArray()[0]);
          }
        }
        Agent agent = new Agent();
        agent.removeGeoObjects(null);
        return VOID;
      }
    });

  }

  @Test
  public void agentAssociations() throws IOException {
    txManager.withTransaction(new TransactionalCommand<Void>() {

      @Override
      public Void doIt() {
        Root root = (Root) txManager.getRoot();

        // Create the agent
        Agent agent = new Agent();
        txManager.save(agent);
        agent.setRoot(root);


        // Create the geo objects
        GeoObject geoObject = new GeoObject();
        BigDecimal lat1 = new BigDecimal("42.438878");
        BigDecimal lon1 = new BigDecimal("-71.119277");
        geoObject.setLatitude(lat1);
        geoObject.setLongitude(lon1);
        txManager.save(geoObject);
        geoObject.setRoot(root);

        // associate the geo obj to the agent
        agent.addGeoObjects(geoObject);
        assertEquals(geoObject, agent.getGeoObjects().toArray()[0]);

        // remove geo object from agent
        agent.removeGeoObjects(geoObject);
        assertEquals(0, agent.getGeoObjectsCount());

        root.removeGeoObjects(geoObject);
        Iterator iter = root.getGeoObjects().iterator();
        while(iter.hasNext()) {
          GeoObject go = (GeoObject) iter.next();
          assertNotSame(geoObject, go);
        }
        root.removeAgents(agent);
        return VOID;
      }
    });

    txManager.withTransaction(new TransactionalCommand<Void>() {

      @Override
      public Void doIt() {
        Root root = (Root) txManager.getRoot();

        // Create the agent
        Agent agent = new Agent();
        txManager.save(agent);
        agent.setRoot(root);


        // Create the geo objects
        GeoObject geoObject = new GeoObject();
        BigDecimal lat1 = new BigDecimal("42.438878");
        BigDecimal lon1 = new BigDecimal("-71.119277");
        geoObject.setLatitude(lat1);
        geoObject.setLongitude(lon1);
        txManager.save(geoObject);
        geoObject.setRoot(root);

        // associate the geo obj to the agent
        agent.addGeoObjects(geoObject);
        assertEquals(geoObject, agent.getGeoObjects().toArray()[0]);

        // remove agent
        root.removeAgents(agent);
        assertEquals(0, root.getAgentsCount());

        root.removeGeoObjects(geoObject);
        Iterator iter = root.getGeoObjects().iterator();
        while(iter.hasNext()) {
          GeoObject go = (GeoObject) iter.next();
          assertNotSame(geoObject, go);
        }
        return VOID;
      }
    });

  }
}
