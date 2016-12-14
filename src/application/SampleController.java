package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import bean.Parola;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SampleController {
	
	private Model model = new Model();
	
	public void setModel(Model model){
		this.model=model;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtParola;

    @FXML
    private Button btnGeneraGrafo;

    @FXML
    private Button btnTrovaVicini;

    @FXML
    private Button btnTrovaConnessi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnClear;

    @FXML
    void doClear(ActionEvent event) {
    	txtParola.clear();
    	txtNumero.clear();
    	txtResult.clear();
    

    }

    @FXML
    void doGeneraGrafo(ActionEvent event) {  //dato un numero crea il grafo e lo ritorna    //credo funzioni
    	                                    //devo solo stampare i nodi del grafo?
    	txtResult.clear();
    	try{
    	
    		int numero = Integer.parseInt(txtNumero.getText());
    	    //List<Parola> parole = model.getParoleLunghezza(numero);
    	    UndirectedGraph <String, DefaultEdge> grafo = model.buildGraph(numero);
    	    txtResult.appendText(grafo.toString()+"\n");
    	
    	
    	} catch(Exception e ){
    		txtResult.appendText("Inserisci un numero valido \n"); 
    		return;	
    	}
    	btnTrovaVicini.setDisable(false);
    	btnTrovaConnessi.setDisable(false);
    	txtParola.setDisable(false);
    }

    @FXML
    void doTrovaConnessi(ActionEvent event) {
    	int numero = Integer.parseInt(txtNumero.getText());
    	String parola = txtParola.getText();
    	List<String> connessi = model.getTrovaConnessi(parola, numero);
    	txtResult.appendText(connessi.toString()+" \n");
    	

    }

    @FXML
    void doTrovaVicini(ActionEvent event) {
    	String parola = txtParola.getText();
    	if(parola == null){
    		txtResult.appendText("Inserisci una parola ! \n ");
    		return;
    	}
    	for(int i =0; i<parola.length(); i++){
    	if(!Character.isLetter(parola.charAt(i))){
    		txtResult.appendText("Il formato non è corretto! \n ");
    		return;
    	   }
    	}
    	
    	int numero = Integer.parseInt(txtNumero.getText());       //non faccio i controlli sul numero xke li ho fatti in generaGrafo()
    	if(parola.length()!= numero){
    		txtResult.appendText("La lunghezza non è corretta ! \n ");
    		return;
    	}
    	
    	List<String> vicini = model.getTrovaVicini(parola, numero);
    	txtResult.appendText(vicini.toString()+"\n");

    }

    @FXML
    void initialize() {
        assert txtNumero != null : "fx:id=\"txtNumero\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnTrovaConnessi != null : "fx:id=\"btnTrovaConnessi\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Sample.fxml'.";
        
        btnTrovaVicini.setDisable(true);
        btnTrovaConnessi.setDisable(true);
        txtParola.setDisable(true);

    }
}
