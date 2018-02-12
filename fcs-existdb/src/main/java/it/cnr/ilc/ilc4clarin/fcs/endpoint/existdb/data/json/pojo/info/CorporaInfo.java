package it.cnr.ilc.ilc4clarin.fcs.endpoint.existdb.data.json.pojo.info;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "xqueryengine",
    "existdbversion",
    "root",
    "corpora"
})
public class CorporaInfo {

    @JsonProperty("xqueryengine")
    private String xqueryengine;

    @JsonProperty("existdbversion")
    private String existdbversion;

    @JsonProperty("root")
    private String root;

    @JsonProperty("time")
    private Double time;

    @JsonProperty("corporalist")
    private List<String> corporalist = new ArrayList<String>();

    @JsonProperty("corpora")
    private List<Corpus> corpora = new ArrayList<Corpus>();
    
    
    private Map<String, Corpus> corpusmap = new HashMap<String, Corpus>();
    
    

    /**
     *
     * @param corpora The List&lt;String&gt; corpora values.
     *
     * @return a CorporaInfo instance for all corpora
     */
    public static CorporaInfo getCorporaInfo(final List<String> corpora) {
        ObjectMapper mapper = new ObjectMapper();

        CorporaInfo ci = null;
        final String wsString = "http://existdb.ilc4clarin.ilc.cnr.it/exist/rest/db/ilc4clarin/queries/ilc4clarinse.xq?";
        final String queryString = "command=info&corpus=";
        //"ROMI,PAROLE";
        final String corporaValues = getCorpusParameterValues(corpora);
        try {
            URL ilc4clarise = new URL(wsString + queryString + corporaValues);
            //System.err.println("XXXX " + ilc4clarise.toString());
            ci = mapper.readerFor(CorporaInfo.class).readValue(ilc4clarise.openStream());

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
        //System.err.println("YYYY " + ci.getRoot());
        return ci;
    }

    /**
     *
     * @param values The List&lt;String&gt; values.
     *
     * @return a comma separated String
     */
    public static String getCorpusParameterValues(Collection<String> values) {
        StringBuffer buf = new StringBuffer();
        boolean first = true;
        for (String key : values) {
            if (first) {
                first = false;
            } else {
                buf.append(",");
            }
            buf.append(key);
        }
        return buf.toString();
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
     * @return the corporalist
     */
    @JsonProperty("corporalist")
    public List<String> getCorporalist() {
        return corporalist;
    }

    /**
     * @param corporalist the corporalist to set
     */
    @JsonProperty("corporalist")
    public void setCorporalist(List<String> corporalist) {
        this.corporalist = corporalist;
    }

    /**
     * @return the corpora
     */
    @JsonProperty("corpora")
    public List<Corpus> getCorpora() {
        return corpora;
    }

    /**
     * @param corpora the corpora to set
     */
    @JsonProperty("corpora")
    public void setCorpora(List<Corpus> corpora) {
        this.corpora = corpora;
    }

    /**
     * @return the corpusmap
     */
    public Map<String, Corpus> getCorpusmap() {
        String key;
        for (Corpus c:getCorpora()){
            key=c.getCorpuspath();
            corpusmap.put(key, c);
        }
        return corpusmap;
    }

    /**
     * @param corpusmap the corpusmap to set
     */
    public void setCorpusmap(Map<String, Corpus> corpusmap) {
        this.corpusmap = corpusmap;
    }
    
    /**
     *
     * @param corpusId The corpusId
     * @return Corpus The Corpus with ID corpusId
     */
    public Corpus getCorpus(final String corpusId) {
	return corpusmap.get(corpusId);
    }

    /**
     *
     * @param corpusId The corpusId
     * @param corpus The Corpus for corpus corpusId
     */
    public void setCorpus(final String corpusId, final Corpus corpus) {
	corpusmap.put(corpusId, corpus);
    }

}
