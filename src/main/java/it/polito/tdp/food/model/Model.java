package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<Portion, DefaultWeightedEdge> grafo;
	private FoodDao dao;
	private Map<String, Portion> idMap;
	
	private List<Portion> percorsoMigliore;
	private int max;
	
	public Model() {
		dao = new FoodDao();
		idMap =new HashMap<>();
		dao.listAllPortions(idMap);
	}
	
	public void creaGrafo(int calorie) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 
		Graphs.addAllVertices(grafo, dao.getVertici(idMap, calorie));
	
		for (Adiacenze a: dao.getAdiacenze(idMap, calorie)) {
			if (this.grafo.containsVertex(a.getP1())&& this.grafo.containsVertex(a.getP2()))
			Graphs.addEdgeWithVertices(grafo, a.getP1(), a.getP2(), a.getPeso());
		}
		
		System.out.format("Grafo creato con %d vertici e %d archi\n",
 				this.grafo.vertexSet().size(), this.grafo.edgeSet().size()); 
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<String> getVertici(){
		List<String> vertici = new ArrayList<>();
		for (Portion p: grafo.vertexSet()) {
			vertici.add(p.getPortion_display_name());
		}
		return vertici;
	}
	
	public List<ArcoPeso> getConnessi(String tipo){
		List<ArcoPeso> result = new ArrayList<>();
		Portion porzione = idMap.get(tipo);
		for (Portion p: Graphs.neighborListOf(grafo, porzione)) {
			DefaultWeightedEdge e = grafo.getEdge(porzione, p);
			result.add(new ArcoPeso(p, (int) grafo.getEdgeWeight(e)));
		}
		return result;
	}
	
	public List<Portion> getPercorso(String partenza, int n){
		this.percorsoMigliore= new ArrayList<>();
		List<Portion> parziale =  new ArrayList<>();
		max=0;
		Portion p = idMap.get(partenza);
		parziale.add(p);
		
		cerca(n, parziale);
		
		
		return this.percorsoMigliore;
	}

	private void cerca(int n, List<Portion> parziale) {
		
		Portion ultimo = parziale.get(parziale.size()-1);
		if (parziale.size()==n) {
			if (calcolaPeso(parziale)>max) {
				this.percorsoMigliore= new ArrayList<>(parziale);
				max = calcolaPeso(parziale);
			}
		}
		
		for (Portion p: Graphs.neighborListOf(grafo, ultimo)) {
			if (!parziale.contains(p)){
				parziale.add(p);
				cerca(n,parziale);
				parziale.remove(parziale.size()-1);
			}
		}
	}

	private int calcolaPeso(List<Portion> parziale) {
		Integer pesoTot=0;
		for (int i=1; i<parziale.size(); i++) {
			Portion p1= parziale.get(i-1);
			Portion p2= parziale.get(i);
			
			pesoTot+= (int) grafo.getEdgeWeight(grafo.getEdge(p1, p2));
		}
		return pesoTot;
	}
	
	public int getPesoMax() {
		return max;
	}
	

	
}
