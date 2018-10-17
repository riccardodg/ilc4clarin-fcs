/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.ilc4clarin.fcs.endpoint.existdb.data.json.pojo.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.cnr.ilc.ilc4clarin.fcs.endpoint.existdb.utils.Vars;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
/*
{
	"xqueryengine": "3.1",
	"existdbversion": "3.5.0",
	"root": "/db/ilc4clarin/data",
	"corpora": ["test", "panacea"],
	"time": 0.001
}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "xqueryengine",
    "existdbversion",
    "root",
    "corpora",
    "time"
})
public class ServiceInfo {

    @JsonProperty("corpora")
    private List<String> corpora = new ArrayList<String>();

    @JsonProperty("xqueryengine")
    private String xqueryengine;

    @JsonProperty("existdbversion")
    private String existdbversion;

    @JsonProperty("time")
    private Double time;

    @JsonProperty("root")
    private String root;

    private static final List<String> EXISTDB_CORPORA = Vars.EXISTDB_CORPORA;

    /**
     * @return the corpora
     */
    @JsonProperty("corpora")
    public List<String> getCorpora() {
        return corpora;
    }

    /**
     * @param corpora the corpora to set
     */
    @JsonProperty("corpora")
    public void setCorpora(List<String> corpora) {
        this.corpora = corpora;
    }

    /**
     * @return the xqueryengine
     */
    @JsonProperty("xqueryengine")
    public String getXqueryengine() {
        return xqueryengine;
    }

    /**
     * @param xqueryengine the xqueryengine to set
     */
    @JsonProperty("xqueryengine")
    public void setXqueryengine(String xqueryengine) {
        this.xqueryengine = xqueryengine;
    }

    /**
     * @return the time
     */
    @JsonProperty("time")
    public Double getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    @JsonProperty("time")
    public void setTime(Double time) {
        this.time = time;
    }

    /**
     * the code *
     */
    public static List<String> getExistDbCorpora() {
        ObjectMapper mapper = new ObjectMapper();

        ServiceInfo si = null;
        final String wsString = "http://existdb.ilc4clarin.ilc.cnr.it/exist/rest/db/ilc4clarin/queries/ilc4clarinse.xq?";
        final String queryString = "command=info";
        try {
            URL ilc4clarise = new URL(wsString + queryString);
            si = mapper.readerFor(ServiceInfo.class).readValue(ilc4clarise.openStream());

        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<String> existDbCorpora = new ArrayList<String>();

        for (String corpus : si.getCorpora()) {
            existDbCorpora.add(corpus);

        }
        return existDbCorpora;
    }

    /**
     * @return the existdbversion
     */
    @JsonProperty("existdbversion")
    public String getExistdbversion() {
        return existdbversion;
    }

    /**
     * @param existdbversion the existdbversion to set
     */
    @JsonProperty("existdbversion")
    public void setExistdbversion(String existdbversion) {
        this.existdbversion = existdbversion;
    }

    /**
     * @return the root
     */
    @JsonProperty("root")
    public String getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    @JsonProperty("root")
    public void setRoot(String root) {
        this.root = root;
    }

}
