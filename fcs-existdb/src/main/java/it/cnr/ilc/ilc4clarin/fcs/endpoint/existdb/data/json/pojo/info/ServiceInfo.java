/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.ilc4clarin.fcs.endpoint.existdb.data.json.pojo.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
@JsonInclude(JsonInclude.Include.NON_NULL) 
@JsonPropertyOrder({
	"collection",
	"files",
})
public class ServiceInfo {
    
    @JsonProperty("collection")
    private List<String> collection = new ArrayList<String>();
    
    @JsonProperty("collection")
    private List<String> files = new ArrayList<String>();
    
    
}
