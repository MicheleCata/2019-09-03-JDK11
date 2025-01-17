/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.ArcoPeso;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.Portion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...");
    	
    	String passi = txtPassi.getText();
    	int n ;
    	if(passi == null) {
    		this.txtResult.appendText("INSERISCI NUMERO PASSI!!");
    	}
    	try {
    		n = Integer.parseInt(passi);
    		
    	}catch (NumberFormatException e) {
    		txtResult.setText("Inserisci un valore numerico intero");
    		return;
    	}
    	String tipo = boxPorzioni.getValue();
    	if (tipo==null) {
    		txtResult.appendText("SELEZIONA UN TIPO DI PORZIONE!!");
    	}
    	
    	List<Portion> percorso = model.getPercorso(tipo, n);
    	if (percorso == null) {
    		this.txtResult.appendText("Non è stato trovato nessun percorso a partire dal vertice indicato");
    	}
    	else {
    		this.txtResult.appendText("Trovato percorso di peso max: "+model.getPesoMax()+"\n");
    	for (Portion a: percorso) {
    		this.txtResult.appendText(a.getPortion_display_name()+"\n");
    	}
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...");
    	
    	String tipo = boxPorzioni.getValue();
    	if (tipo==null) {
    		txtResult.appendText("SELEZIONA UN TIPO DI PORZIONE!!");
    	}
    	List<ArcoPeso> connessioni = model.getConnessi(tipo);
    	txtResult.appendText("Componenti connesse al vertice "+ tipo+"\n");
    	for (ArcoPeso a: connessioni) {
    		txtResult.appendText(a.toString()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");
    	
    	String calorie = txtCalorie.getText();
    	if(calorie == null) {
    		txtResult.appendText("INSERISCI IL VALORE DELLE CALORIE\n");
    	}
    	int c;
    	try {
    		c = Integer.parseInt(calorie);
    		
    	}catch (NumberFormatException e) {
    		txtResult.setText("Inserisci un valore numerico");
    		return;
    	}
    	
    	model.creaGrafo(c);
    	txtResult.appendText("Grafo creato\n #VERTICI: "+ model.getNumVertici()+ " \n #ARCHI: "+ model.getNArchi()+"\n");
    	
    	boxPorzioni.getItems().addAll(model.getVertici());
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
