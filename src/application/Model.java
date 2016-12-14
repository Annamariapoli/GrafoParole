package application;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import bean.Parola;
import db.Dao;

public class Model {
	
	private Dao dao = new Dao();
	
	//il grafo è semplice?
	
	UndirectedGraph<String, DefaultEdge> grafo = new SimpleGraph<String,DefaultEdge>(DefaultEdge.class);
	
	public List<Parola> getParoleLunghezza(int lunghezza){               //funziona
		List<Parola> paroleLunghezza = new LinkedList<Parola>();
		paroleLunghezza = dao.getParoleDiQuestaLunghezza(lunghezza);
		return paroleLunghezza;
	}
	
	public UndirectedGraph<String, DefaultEdge> buildGraph (int lunghezza){                                //ogni grafo è composto da parole della stessa lunghezza
		List<Parola> paroleLunghezza = getParoleLunghezza(lunghezza);     //ke è il metodo di prima, potevo farlo diversamente
		                                                                 //prendo tutte le parole ke hanno la stessa lunghezza e le metto come nodi
		for(int i =0; i<paroleLunghezza.size(); i++){                   //ho messo i nodi
			grafo.addVertex(paroleLunghezza.get(i).getNome());
		}
		
		//archi : tra parole di stessa lunghezza che differiscono di una sola lettera
		for(String p1 : grafo.vertexSet()){               //parole di stessa lunghezza
			for(String p2 : grafo.vertexSet()){          //prendo 2 elementi dalla stessa lista di nodi e le confronto
				if(p1!= p2){       //se le parole sono diverse  //controllo inutile
					if(getParoleSimili(p1, p2)){    //se ritorna true, cioè se differiscono x una sola lettera
						grafo.addEdge(p1, p2);		
					}	
				}
			}
		}
		return grafo;
	}
	
	
	public boolean getParoleSimili(String nome1, String nome2){
		int lun1 = nome1.length();
		int lun2 = nome2.length();
		if(lun1 == lun2){                         //controllo inutile xke le parole messe come nodi sono della stessa lunghezza
			int contatore =0;
			for(int i=0; i<nome1.length() && i<nome2.length(); i++){
				if(nome1.charAt(i)!=(nome2.charAt(i))){
					contatore++;
				}
			}
			if(contatore == 1){
				return true;
			}
		}
		return false;
	}
	
	public List<String> getTrovaVicini(String nome, int lunghezza){                      //nome è un nodo del grafo
		UndirectedGraph <String, DefaultEdge> grafo = buildGraph(lunghezza);            //prendo il grafo
		List<String> vicini = new LinkedList<String>();
		if(grafo.containsVertex(nome)){                                     
			vicini = Graphs.neighborListOf(grafo, nome);
		}
		return vicini;
	}
	
	public List<String> getTrovaConnessi(String nome, int lunghezza){
		UndirectedGraph <String, DefaultEdge> grafo = buildGraph(lunghezza);  
		List<String> connessi = new LinkedList<String>();
		if(grafo.containsVertex(nome)){
			BreadthFirstIterator<String, DefaultEdge> visita = new BreadthFirstIterator<String, DefaultEdge>(grafo, nome);
			while(visita.hasNext()){
				String connesso = visita.next();
				connessi.add(connesso);
			}
		}
		return connessi;
		
	}

}
