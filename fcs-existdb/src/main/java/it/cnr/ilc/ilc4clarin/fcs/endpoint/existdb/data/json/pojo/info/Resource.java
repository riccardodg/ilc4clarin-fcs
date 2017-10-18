/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.ilc4clarin.fcs.endpoint.existdb.data.json.pojo.info;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class Resource {

    @JsonProperty("name")
    private String name;

    @JsonProperty("path")
    private String path;

    @JsonProperty("mimetype")
    private String mimetype;

    @JsonProperty("size")
    private int size;

    /**
     * @return the name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the path
     */
    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the mimetype
     */
    @JsonProperty("mimetype")
    public String getMimetype() {
        return mimetype;
    }

    /**
     * @param mimetype the mimetype to set
     */
    @JsonProperty("mimetype")
    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    /**
     * @return the size
     */
    @JsonProperty("size")
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    @JsonProperty("size")
    public void setSize(int size) {
        this.size = size;
    }

}
