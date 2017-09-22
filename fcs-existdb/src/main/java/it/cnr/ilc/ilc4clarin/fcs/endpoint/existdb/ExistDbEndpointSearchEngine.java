/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
/**
 *
 * @license http://www.gnu.org/licenses/gpl-3.0.txt
 *  GNU General Public License v3
 */
package it.cnr.ilc.ilc4clarin.fcs.endpoint.existdb;


import eu.clarin.sru.server.SRUConfigException;
import eu.clarin.sru.server.SRUDiagnosticList;
import eu.clarin.sru.server.SRUException;
import eu.clarin.sru.server.SRUQueryParserRegistry;
import eu.clarin.sru.server.SRURequest;
import eu.clarin.sru.server.SRUSearchResultSet;
import eu.clarin.sru.server.SRUServerConfig;
import eu.clarin.sru.server.fcs.EndpointDescription;
import eu.clarin.sru.server.fcs.SimpleEndpointSearchEngineBase;
import eu.clarin.sru.server.fcs.utils.SimpleEndpointDescriptionParser;
import it.cnr.ilc.ilc4clarin.fcs.endpoint.existdb.data.json.pojo.info.ServiceInfo;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class ExistDbEndpointSearchEngine extends SimpleEndpointSearchEngineBase {

    private static final String X_FCS_ENDPOINT_DESCRIPTION
            = "x-fcs-endpoint-description";
    private static final String ED_NS
            = "http://clarin.eu/fcs/endpoint-description";
    private static final String ED_PREFIX = "ed";
    private static final int ED_VERSION = 2;
    private static final Logger LOG
            = LoggerFactory.getLogger(ExistDbEndpointSearchEngine.class);
    protected EndpointDescription endpointDescription;
    public static final String RESOURCE_INVENTORY_URL
            = "se.gu.spraakbanken.fcs.korp.sru.resourceInventoryURL";

    @Override
    protected EndpointDescription createEndpointDescription(
            ServletContext context, SRUServerConfig config, Map<String, String> params) throws SRUConfigException {
        try {
            URL url = null;
            String riu = params.get(RESOURCE_INVENTORY_URL);
            if ((riu == null) || riu.isEmpty()) { 
                url = context.getResource("/WEB-INF/endpoint-description.xml");
                LOG.debug("using bundled 'endpoint-description.xml' file");
            } else {
                url = new File(riu).toURI().toURL();
                LOG.debug("using external file '{}'", riu);
            }
            return SimpleEndpointDescriptionParser.parse(url);
            
        } catch (MalformedURLException mue) {
	    throw new SRUConfigException("Malformed URL for initializing resource info inventory", mue);
	}
        
    }

    @Override
    protected void doInit(ServletContext context,
            SRUServerConfig config,
            SRUQueryParserRegistry.Builder queryParserBuilder,
            Map<String, String> params) throws SRUConfigException {
	doInit(config, queryParserBuilder, params);
    }
    
    protected void doInit(SRUServerConfig config,
            SRUQueryParserRegistry.Builder queryParserBuilder,
            Map<String, String> params) throws SRUConfigException {
	LOG.info("ExistDbEndpointSearchEngine::doInit {}", config.getPort());
	//List<String> openCorpora = ServiceInfo.getModernCorpora();
	//openCorporaInfo = CorporaInfo.getCorporaInfo(openCorpora);
    }

    @Override
    public SRUSearchResultSet search(SRUServerConfig srusc, SRURequest srur, SRUDiagnosticList srudl) throws SRUException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
