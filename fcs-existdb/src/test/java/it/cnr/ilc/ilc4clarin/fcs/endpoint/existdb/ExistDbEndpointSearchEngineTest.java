/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.ilc4clarin.fcs.endpoint.existdb;

import eu.clarin.sru.server.SRUConfigException;
import eu.clarin.sru.server.SRUException;
import eu.clarin.sru.server.SRUQueryParserRegistry;
import eu.clarin.sru.server.SRUServerConfig;
import eu.clarin.sru.server.fcs.EndpointDescription;
import eu.clarin.sru.server.fcs.FCSQueryParser;
import eu.clarin.sru.server.fcs.ResourceInfo;
import eu.clarin.sru.server.fcs.utils.SimpleEndpointDescriptionParser;
import eu.clarin.sru.server.utils.SRUServerServlet;
import it.cnr.ilc.ilc4clarin.fcs.endpoint.existdb.data.json.pojo.info.Corpus;
import it.cnr.ilc.ilc4clarin.fcs.endpoint.existdb.data.json.pojo.info.Resource;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.testing.ServletTester;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class ExistDbEndpointSearchEngineTest {

    private String jsonString = "{ { \"xqueryengine\" : \"3.1\", \"existdbversion\" : \"3.5.0\", \"root\" : \"/db/ilc4clarin/data/\", \"corporalist\" : [\"panacea\", \"test\"], \"corpora\" : [{ \"corpuspath\" : \"/db/ilc4clarin/data/panacea\", \"corpusname\" : \"panacea\", \"files\" : 2, \"found\" : true, \"resources\" : [{ \"name\" : \"2583_minimaltei.xml\", \"path\" : \"/db/ilc4clarin/data/panacea/2583_minimaltei.xml\", \"mimetype\" : \"application/xml\", \"size\" : 4 }, { \"name\" : \"25840_minimaltei.xml\", \"path\" : \"/db/ilc4clarin/data/panacea/25840_minimaltei.xml\", \"mimetype\" : \"application/xml\", \"size\" : 8 }] }, { \"corpuspath\" : \"/db/ilc4clarin/data/test\", \"corpusname\" : \"test\", \"files\" : 1, \"found\" : true, \"resources\" : [{ \"name\" : \"test1.xml\", \"path\" : \"/db/ilc4clarin/data/test/test1.xml\", \"mimetype\" : \"application/xml\", \"size\" : 4 }] }], \"time\" : 0.002 }";
    private static SRUServerConfig config;
    private static EndpointDescription sed;
    private static ExistDbEndpointSearchEngine existdbse;
    private static ServletTester tester;
    private static ServletHolder holder;
    private static HashMap<String, String> params;

    @BeforeClass
    public static void parseEndpointDescription() throws SRUConfigException {
        try {
            sed = SimpleEndpointDescriptionParser.parse(new File("/opt/java/app/ilc4clarin-fcs/fcs-existdb/src/test/resources/it/cnr/ilc/ilc4clarin/fcs/endpoint/existdb/endpoint-description-test.xml").toURI().toURL());
            assertEquals("http://clarin.eu/fcs/capability/basic-search", sed.getCapabilities().get(0).toString());
            // assertEquals("http://clarin.eu/fcs/capability/advanced-search", sed.getCapabilities().get(1).toString());
        } catch (MalformedURLException mue) {
            throw new SRUConfigException("Malformed URL");
        }
        tester = new ServletTester();
        //tester.setContextPath("/");
        tester.setContextPath("http://localhost:8080/ilc4clarin-fcs-existdb");
        tester.setResourceBase("src/main/webapp");
        tester.setClassLoader(SRUServerServlet.class.getClassLoader());
        holder = tester.addServlet(SRUServerServlet.class, "/sru");
        params = new HashMap<String, String>();
        params.put(SRUServerConfig.SRU_TRANSPORT, "http");
        params.put(SRUServerConfig.SRU_HOST, "localhost");
        params.put(SRUServerConfig.SRU_PORT, "8080");
        params.put(SRUServerConfig.SRU_DATABASE, "ilc4clarin-fcs-existdb");
        params.put(SRUServerServlet.SRU_SERVER_CONFIG_LOCATION_PARAM, "src/main/webapp/WEB-INF/sru-server-config.xml");

    }

    @Test
    public void doInit() throws SRUConfigException, ServletException {
        URL url;
        try {
            url
                    = //SRUServerServlet.class.getClassLoader().getResource("META-INF/sru-server-config.xml");
                    new File("src/main/webapp/WEB-INF/sru-server-config.xml").toURI().toURL();

        } catch (MalformedURLException mue) {
            throw new SRUConfigException("Malformed URL");
        }
        if (url == null) {
            throw new ServletException("not found, url == null");
        }
        // other runtime configuration, usually obtained from Servlet context

        config = SRUServerConfig.parse(params, url);
        existdbse = new ExistDbEndpointSearchEngine();
        System.out.println(config.getBaseUrl());
        System.out.println(config.getDatabase());

        //System.out.println(holder.getServlet().getServletInfo());
        existdbse.doInit(config, new SRUQueryParserRegistry.Builder().register(new FCSQueryParser()), params);

        assertNotNull(existdbse.getExistDbCorpora());
        //printstuff();

	assertNotNull(existdbse.getCorporaInfo());
	//assertNotNull(existdbse.getCorporaInfo().getCorpus("/db/ilc4clarin/data/panacea"));
    }
    
     @Test
    public void getCapabilitiesFromDescription() throws SRUConfigException {
        System.out.println(sed.getCapabilities());
	assertEquals("http://clarin.eu/fcs/capability/basic-search", sed.getCapabilities().get(0).toString());
	
    }
    
    @Test
    public void getDataViewsFromDescription() throws SRUConfigException {
	System.out.println(sed.getSupportedDataViews());
	assertEquals("hits", sed.getSupportedDataViews().get(0).getIdentifier());
	assertEquals("SEND_BY_DEFAULT", sed.getSupportedDataViews().get(0).getDeliveryPolicy().toString());
//	assertEquals("application/x-cmdi+xml", sed.getSupportedDataViews().get(2).getMimeType());
//	assertEquals("NEED_TO_REQUEST", sed.getSupportedDataViews().get(2).getDeliveryPolicy().toString());
    }

    @Test
    public void getLayersFromDescription() throws SRUConfigException {
	System.out.println(sed.getSupportedLayers());
	assertEquals("http://localhost:9090/exist/ilc4clarin/fcs/layer/word", sed.getSupportedLayers().get(0).getResultId().toString());
//	assertEquals("lemma", sed.getSupportedLayers().get(1).getType().toString());
    }

    @Test
    public void getResourcesFromDescription() throws SRUException {
       System.out.println("ES "+sed.getResourceList("panacea"));
//	List<ResourceInfo> riList = sed.getResourceList("aa");
//	//System.out.println(riList.get(0).getTitle());
//	assertEquals("hits", riList.get(0).getAvailableDataViews().get(0).getIdentifier());
//	assertEquals("SEND_BY_DEFAULT", riList.get(0).getAvailableDataViews().get(0).getDeliveryPolicy().toString());
//	assertEquals("application/x-clarin-fcs-hits+xml", riList.get(0).getAvailableDataViews().get(0).getMimeType());
//	assertEquals("http://localhost:9090/exist/rest/db/ilc4clarin/data/panacea", riList.get(0).getLandingPageURI());
//	assertTrue(riList.get(0).hasAvailableLayers());
//	assertEquals("word", riList.get(0).getAvailableLayers().get(0).getId());
//	assertEquals("text", riList.get(0).getAvailableLayers().get(0).getType());
//	assertNull(riList.get(0).getAvailableLayers().get(0).getQualifier());
//	assertEquals("it", riList.get(0).getLanguages().get(0));
//	assertFalse(riList.get(0).hasSubResources());
    }

    protected void printstuff() {
        System.err.println("CORPORA " + existdbse.getExistDbCorpora());
        System.err.println("ROOT " + existdbse.getCorporaInfo().getRoot());
        System.err.println("EXISTVERSION " + existdbse.getCorporaInfo().getExistdbversion());
        System.err.println("XQUERYVERSION " + existdbse.getCorporaInfo().getXqueryengine());
        System.err.println("DURATION " + existdbse.getCorporaInfo().getTime());
        System.err.println("CORPORALIST " + existdbse.getCorporaInfo().getCorporalist());
        System.err.println("CORPORA START");
//        for (Corpus corpus : existdbse.getOpenCorporaInfo().getCorpora()) {
//            System.err.println("\tCORPORA NAME " + corpus.getCorpusname());
//            System.err.println("\tCORPORA PATH " + corpus.getCorpuspath());
//            System.err.println("\tCORPORA FILES " + corpus.getFiles());
//            System.err.println("\tCORPORA FOUND " + corpus.isFound());
//            System.err.println("\tRESOURCE START");
//            for(Resource r:corpus.getResources()){
//            System.err.println("\t\tRESOURCE NAME " + r.getName());
//            }
//        }
        for (Map.Entry entry : existdbse.getCorporaInfo().getCorpusmap().entrySet()) {
            Corpus c = (Corpus) entry.getValue();
            System.out.println("key "+entry.getKey() + " has name " + c.getCorpusname());
            System.out.println("key "+entry.getKey() + " has path " + c.getCorpuspath());
            System.out.println("key "+entry.getKey() + " has #files " + c.getFiles());
            System.out.println("key "+entry.getKey() + " has #resources " + c.getResources().size());
            for (Resource r : c.getResources()) {
                System.out.println("\tkey "+entry.getKey() + " has resource " + r.getName());
            }
        }
        System.err.println("CORPORA END");
        
        System.err.println("CORPORA WITH HEY /db/ilc4clarin/data/panacea " +existdbse.getCorporaInfo().getCorpus("/db/ilc4clarin/data/panacea"));
    }

}
