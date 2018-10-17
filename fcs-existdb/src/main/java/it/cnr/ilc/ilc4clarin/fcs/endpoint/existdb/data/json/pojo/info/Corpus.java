/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.ilc4clarin.fcs.endpoint.existdb.data.json.pojo.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class Corpus {

    @JsonProperty("corpusname")
    private String corpusname;

    @JsonProperty("corpuspath")
    private String corpuspath;

    @JsonProperty("resources")
    private List<Resource> resources = new ArrayList<Resource>();

    @JsonProperty("files")
    private int files;

    @JsonProperty("found")
    private boolean found;

    /**
     * @return the corpusname
     */
    @JsonProperty("corpusname")
    public String getCorpusname() {
        return corpusname;
    }

    /**
     * @param corpusname the corpusname to set
     */
    @JsonProperty("corpusname")
    public void setCorpusname(String corpusname) {
        this.corpusname = corpusname;
    }

    /**
     * @return the corpuspath
     */
    @JsonProperty("corpuspath")
    public String getCorpuspath() {
        return corpuspath;
    }

    /**
     * @param corpuspath the corpuspath to set
     */
    @JsonProperty("corpuspath")
    public void setCorpuspath(String corpuspath) {
        this.corpuspath = corpuspath;
    }

    /**
     * @return the files
     */
    @JsonProperty("files")
    public int getFiles() {
        return files;
    }

    /**
     * @param files the files to set
     */
    @JsonProperty("files")
    public void setFiles(int files) {
        this.files = files;
    }

    /**
     * @return the found
     */
    @JsonProperty("found")
    public boolean isFound() {
        return found;
    }

    /**
     * @param found the found to set
     */
    @JsonProperty("found")
    public void setFound(boolean found) {
        this.found = found;
    }
    
    @JsonProperty("resources")
    public List<Resource> getResources() {
        return resources;
    }

    /**
     * @param resources the resources to set
     */
    @JsonProperty("resources")
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

}
