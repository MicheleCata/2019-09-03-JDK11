package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Adiacenze;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public void listAllPortions(Map<String, Portion> idMap){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
					Portion p = new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							);
					idMap.put(p.getPortion_display_name(), p);
				
			}
			
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public List<Portion> getVertici(Map<String, Portion> idMap, int calorie){
		String sql = "SELECT DISTINCT p.`portion_display_name` as tipo "
				+ "FROM `portion` p "
				+ "where calories < ? "
				+ "ORDER BY tipo asc";
		
		List<Portion> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection() ;
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, calorie);
			ResultSet res = st.executeQuery() ;
			while(res.next()) {
				result.add(idMap.get(res.getString("tipo")));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenze> getAdiacenze(Map<String, Portion> idMap, int calorie) {
		String sql = "SELECT p1.`portion_display_name` as tipo1, p2.`portion_display_name` as tipo2, Count(*) as peso "
				+ "FROM `portion` p1, `portion` p2 "
				+ "where p1.calories < ? AND p2.`calories`< ? AND p1.`portion_id`>p2.`portion_id` AND  p1.`food_code`=p2.food_code "
				+ "group by tipo1,tipo2";
		List<Adiacenze> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection() ;
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, calorie);
			st.setInt(2, calorie);
			ResultSet res = st.executeQuery() ;
			while(res.next()) {
				Portion p1 = idMap.get(res.getString("tipo1"));
				Portion p2 = idMap.get(res.getString("tipo2"));
				
				if(p1!=null && p2!=null) {
					result.add(new Adiacenze(p1,p2,res.getInt("peso")));
				}
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
