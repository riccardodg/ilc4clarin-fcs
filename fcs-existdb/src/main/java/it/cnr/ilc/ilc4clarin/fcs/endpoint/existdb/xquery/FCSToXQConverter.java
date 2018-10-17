/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.ilc4clarin.fcs.endpoint.existdb.xquery;

import eu.clarin.sru.server.SRUConstants;
import eu.clarin.sru.server.SRUException;
import eu.clarin.sru.server.SRUQuery;
import eu.clarin.sru.server.fcs.Constants;
import eu.clarin.sru.server.fcs.parser.QueryNode;
import eu.clarin.sru.server.fcs.parser.QuerySegment;
import eu.clarin.sru.server.fcs.parser.QuerySequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.z3950.zing.cql.CQLBooleanNode;
import org.z3950.zing.cql.CQLNode;
import org.z3950.zing.cql.CQLNotNode;
import org.z3950.zing.cql.CQLTermNode;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class FCSToXQConverter {
    private static final Logger LOG =
            LoggerFactory.getLogger(FCSToXQConverter.class);
    
    /**
     *
     * @param query The CQL query
     * @return The CQP query
     * @throws eu.clarin.sru.server.SRUException If the query is too complex or it 
     * for any other reason cannot be performed
     */
    public static String makeXQueryFromCQL(final SRUQuery<CQLNode> query)
	throws SRUException {
        final CQLNode node = query.getParsedQuery();
        /*
         * Translate the CQL query to a Lucene query. If a CQL feature was used,
         * that is not supported by us, throw a SRU error (with a detailed error
         * message)
         *
         * Right now, we're pretty stupid and only support terms
         */
        if (node instanceof CQLBooleanNode) {
            String operator;
            //if (node instanceof CQLAndNode) {
            //    operator = "AND";
            //} else if (node instanceof CQLOrNode) {
            //    operator = "OR";
            //} else
	    if (node instanceof CQLNotNode) {
                operator = "NOT";
            } else {
                operator = node.getClass().getSimpleName();
	    }
            throw new SRUException(
				   SRUConstants.SRU_UNSUPPORTED_BOOLEAN_OPERATOR,
				   operator,
				   "Unsupported Boolean operator: " + operator);
        } else if (node instanceof CQLTermNode) {
            CQLTermNode ctn = (CQLTermNode) node;
	    
            String[] terms = ctn.getTerm().toLowerCase().split("\\s+");
           if (terms.length > 1) {
	       String phrase = "";
                for (int i = 0; i < terms.length; i++) {
		    if (terms[i].startsWith("\"") || terms[i].startsWith("'") || terms[i].endsWith("\"") || terms[i].endsWith("'")) {
			String tmp = terms[i];
			if (terms[i].startsWith("\"") || terms[i].startsWith("'")) {
			    tmp = tmp.substring(1);
			}
			if (terms[i].endsWith("\"") || terms[i].endsWith("'")) {
			    tmp = tmp.substring(0, tmp.length()-1);
			}
			phrase += "[word = '" + tmp + "']";
		    } else { 
			phrase += "[word = '" + terms[i] + "']";
		    }
                }
                return phrase;
            } else {
                return "[word = '" + terms[0] + "']";
            }
        } else {
            throw new SRUException(
				   SRUConstants.SRU_CANNOT_PROCESS_QUERY_REASON_UNKNOWN,
				   "unknown cql node: " + node);
        }
    }
    
}


