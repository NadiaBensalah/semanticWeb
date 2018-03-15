package semantic;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

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
import com.hp.hpl.jena.util.tuple.TupleSet;


public class patterns {
	public patterns() {
	}
	
	public ResultSet executeQuery(String queryString) throws Exception {

		 QueryExecution exec =  QueryExecutionFactory.create(QueryFactory.create(queryString), new
				 DatasetImpl(ModelFactory.createDefaultModel()));
		 return exec.execSelect();

}
public static void main(String[] args) throws Exception {
		
		patterns test = new patterns();
		int profondeurMax=3;
		for(int max=2;max<profondeurMax+1;max++)
		{
		String s="?r1 rdf:type ?c1.\n"+"?r1 ?p1 ?r2.\n";
		String s1="select  ?r1 ?p1 ";

		for(int i=2;i<max+1;i++) {
			int i1=i+1;
			s=s+"?r"+i+" rdf:type ?c"+i+".\n"+"?r"+i+" ?p"+i+" ?r"+i1+".\n";
			s1=s1+"?r"+i+" ?p"+i+" ";
            i1+=1;
		}
		int i=max+1;
		s=s+"?r"+i+" rdf:type ?c"+i+".\n";
		s1=s1+"?r"+i+"\n";
	    String service = "http://dbpedia.org/sparql";
		String queryString ="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" + 
				"prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"prefix owl: <http://www.w3.org/2002/07/owl#>\n" + 
				"prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +s1 + 
				"where{\n" + 
				s + 
				"}";	 
      Query query = QueryFactory.create(queryString,Syntax.syntaxARQ);
       QueryEngineHTTP qexec = QueryExecutionFactory.createServiceRequest(service, query);
       ResultSet result = qexec.execSelect();
       
       
           String nomFichier="profondeur"+max+".txt";
           File test1=new File(nomFichier);
           if(!test1.exists()){
           test1.createNewFile();
                             }          
            FileOutputStream fos=new FileOutputStream(test1,false);  
            Writer out = new OutputStreamWriter(new BufferedOutputStream(fos));
       
         
       for ( ; result.hasNext() ; ) {
       QuerySolution soln = result.nextSolution() ;
       out.append(soln.toString());
       out.append("\n");
      }
           
            
     // ResultSetFormatter.outputAsJSON(result);  
      

     
		}
	      System.out.println("Refresh and check results");

     }
}
