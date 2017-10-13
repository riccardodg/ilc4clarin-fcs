/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.fcs.endpoint.existdb;

import eu.clarin.sru.server.SRUConfigException;
import eu.clarin.sru.server.SRUServerConfig;
import eu.clarin.sru.server.fcs.EndpointDescription;
import eu.clarin.sru.server.fcs.utils.SimpleEndpointDescriptionParser;
import eu.clarin.sru.server.utils.SRUServerServlet;
import it.cnr.ilc.ilc4clarin.fcs.endpoint.existdb.ExistDbEndpointSearchEngine;
import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.testing.ServletTester;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class ExistDbEndpointSearchEngineTest {
    private String jsonString = "{ \"xqueryengine\" : \"3.1\", \"existdbversion\" : \"3.5.0\", \"root\" : \"/db/ilc4clarin/data\", \"corpora\" : [\"test\", \"panacea\"], \"time\" : 0.001 }";
    private static SRUServerConfig config;
    private static EndpointDescription sed;
    private static ExistDbEndpointSearchEngine existdbse;
    private static ServletTester tester;
    private static ServletHolder holder;
    private static HashMap<String, String> params;
    
    @BeforeClass
    public static void parseEndpointDescription() throws SRUConfigException {
    try {
	    sed = SimpleEndpointDescriptionParser.parse(new File("test-classes/it/cnr/ilc/fcs/endpoint/existdb/endpoint-description-test.xml").toURI().toURL());
	    assertEquals("http://clarin.eu/fcs/capability/basic-search", sed.getCapabilities().get(0).toString());
	    assertEquals("http://clarin.eu/fcs/capability/advanced-search", sed.getCapabilities().get(1).toString());
	} catch (MalformedURLException mue) {
	    throw new SRUConfigException("Malformed URL");
	}
    tester = new ServletTester();
        //tester.setContextPath("/");
	tester.setContextPath("http://localhost:9090/sru-server");
	tester.setResourceBase("src/main/webapp");
	tester.setClassLoader(SRUServerServlet.class.getClassLoader());
        holder = tester.addServlet(SRUServerServlet.class, "/sru");
	params = new HashMap<String, String>();
	params.put(SRUServerConfig.SRU_TRANSPORT, "http");
	params.put(SRUServerConfig.SRU_HOST, "127.0.0.1");
	params.put(SRUServerConfig.SRU_PORT, "8082");
	params.put(SRUServerConfig.SRU_DATABASE, "sru-server");
	params.put(SRUServerServlet.SRU_SERVER_CONFIG_LOCATION_PARAM, "src/main/webapp/WEB-INF/sru-server-config.xml");

    }
    
}
