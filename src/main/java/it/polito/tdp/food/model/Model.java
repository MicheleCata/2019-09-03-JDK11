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
	
	public Model() {
		dao = new FoodDao();
		idMap =new HashMap<>();
		dao.listAllPortions(idMap);
	}
	
	public void creaGrafo(int calorie) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 
		Graphs.addAllVertices(grafo, dao.getVertici(idMap, calorie));
	
		for (Adiacenze a: dao.getAdiacenze(idMap, calorie)) {
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

	
}
