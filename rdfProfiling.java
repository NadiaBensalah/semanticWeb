package semantic;


import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.core.DatasetImpl;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;


public class rdfProfiling {
	public rdfProfiling() {
	}
	
	public ResultSet executeQuery(String queryString) throws Exception {

		 QueryExecution exec =  QueryExecutionFactory.create(QueryFactory.create(queryString), new
				 DatasetImpl(ModelFactory.createDefaultModel()));
		 return exec.execSelect();

}
public static void main(String[] args) throws Exception {
		
	rdfProfiling test = new rdfProfiling();
	      String service = "http://www.semantic-systems-biology.org/biogateway/endpoint";
		String queryString1 ="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
	            "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
	            "prefix owl: <http://www.w3.org/2002/07/owl#>"+
	            "SELECT *"+
	            "WHERE {"+
	            "{"+
	            "?class1 rdf:type owl:Class."+
	            "?DatatypeProperty rdf:type rdf:Property."+
	            "?DatatypeProperty a owl:DatatypeProperty."+
	            "?DatatypeProperty rdfs:domain ?class1."+
	            "}"+
	            "union"+
	            "{"+
	            "?class rdf:type owl:Class."+
	            "?ObjectProperty rdf:type rdf:Property."+
	            "?ObjectProperty a owl:ObjectProperty."+
	            "?ObjectProperty rdfs:domain ?class."+
	            "?ObjectProperty rdfs:range ?class2."+
	            "}"+
	            "}";		
	   String queryString2="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" + 
	   		"prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
	   		"prefix owl: <http://www.w3.org/2002/07/owl#>\n" + 
	   		"SELECT ?class  (count(?instance) as ?count) \n" + 
	   		"WHERE {\n" + 
	   		"?class rdf:type owl:Class.\n" + 
	   		"?instance a ?class.\n" + 
	   		"}";
	   
	   String queryString3="select ?p (count(?instance) as ?count) \n" + 
	   		"where\n" + 
	   		"{\n" + 
	   		"?instance ?p ?o\n" + 
	   		"}";
	   
	   
	   String queryString4="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" + 
	   		"prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
	   		"prefix owl: <http://www.w3.org/2002/07/owl#>\n" + 
	   		"select ?p ?cardinality \n" + 
	   		"where\n" + 
	   		"{\n" + 
	   		"?c a owl:Class;\n" + 
	   		"rdfs:subClassOf\n" + 
	   		"[a owl:Restriction;\n" + 
	   		"owl:onProperty ?p;\n" + 
	   		"owl:cardinality ?cardinality \n" + 
	   		"].\n" + 
	   		"}";
	   
	   String queryString5=
	   		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 
	   		"prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + 
	   		"prefix owl: <http://www.w3.org/2002/07/owl#>" + 
	   		"select  ?p " + 
	   		"where" + 
	   		"{" + 
	   		"?p a ?t." + 
	   		"filter(?t in (owl:FunctionalProperty) )" + 
	   		"}";
	   String queryString6="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" + 
	   		"prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
	   		"prefix owl: <http://www.w3.org/2002/07/owl#>\n" + 
	   		"prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" + 
	   		"\n" + 
	   		"select  (?p AS ?Inversefuctional )\n" + 
	   		"where\n" + 
	   		"{\n" + 
	   		"?p a ?t.\n" + 
	   		"filter(?t in (owl:InverseFunctionalProperty) )\n" + 
	   		"}";
       Query query = QueryFactory.create(queryString4,Syntax.syntaxARQ);

       QueryEngineHTTP qexec = QueryExecutionFactory.createServiceRequest(service, query);
       ResultSet result = qexec.execSelect();
      // for ( ; result.hasNext() ; ) {
      // QuerySolution soln = result.nextSolution() ;
      // System.out.println(soln);}
       ResultSetFormatter.outputAsJSON(result);

       
       
       

     }

}
