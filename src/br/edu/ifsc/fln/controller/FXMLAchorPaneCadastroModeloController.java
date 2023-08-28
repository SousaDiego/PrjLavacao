/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.ModeloDAO;
import br.edu.ifsc.fln.model.dao.MotorDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Modelo;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aluno
 */
public class FXMLAchorPaneCadastroModeloController implements Initializable {

    @FXML
    private Button btAlterar;

    @FXML
    private Button btInserir;

    @FXML
    private Button btRemover;

    @FXML
    private Label lbMarca;

    @FXML
    private Label lbDescricao;
    
    @FXML
    private Label lbModeloCategoria;

    @FXML
    private Label lbId;
    
    @FXML
    private Label lbModeloMotorPotencia;
    
    @FXML
    private Label lbModeloMotorCombustivel;

    @FXML
    private TableColumn<Modelo, String> tableColumnModeloDescricao;

    @FXML
    private TableView<Modelo> tableViewModelo;
    
    private List<Modelo> listaModelo;
    private ObservableList<Modelo> observableListModelo;

    //acesso ao banco de dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ModeloDAO modeloDAO = new ModeloDAO();
    private final MotorDAO motorDAO = new MotorDAO();

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       modeloDAO.setConnection(connection);
       motorDAO.setConnection(connection);

        carregarTableView();

        tableViewModelo.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));

    }
    public void carregarTableView() {
        tableColumnModeloDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
                
        listaModelo = modeloDAO.listar();
        
        observableListModelo = FXCollections.observableArrayList(listaModelo);
        tableViewModelo.setItems(observableListModelo);
    }
    
    public void selecionarItemTableView(Modelo modelo) {
        //DecimalFormat df = new DecimalFormat("0.00");
        if (modelo != null) {
            lbId.setText(Integer.toString(modelo.getId()));
            
            lbDescricao.setText(modelo.getDescricao());
            
            lbModeloCategoria.setText(modelo.getCategoria().name());
            
            lbMarca.setText(modelo.getMarca().getNome());
            
            lbModeloMotorPotencia.setText(Integer.toString(modelo.getMotor().getPotencia()));
            
            lbModeloMotorCombustivel.setText(modelo.getMotor().getSituacao().getDescricao());
            
        } else {
            lbId.setText("");
           
            lbDescricao.setText("");
            
            lbMarca.setText("");
            
            lbModeloCategoria.setText("");
            
            lbModeloMotorPotencia.setText("");
            
            lbModeloMotorCombustivel.setText("");
        }

    }  
    @FXML
    public void handleBtAlterar() throws IOException {
        Modelo modelo = tableViewModelo.getSelectionModel().getSelectedItem();
        if (modelo != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosModeloDialog(modelo);
            if (buttonConfirmarClicked) {
                modeloDAO.alterar(modelo);
                motorDAO.alterar(modelo.getMotor());
                carregarTableView();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um modelo na Tabela.");
            alert.show();
        }
    }

    @FXML
    public void handleBtInserir() throws IOException {
        Modelo modelo = new Modelo();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosModeloDialog(modelo);
        if (buttonConfirmarClicked) {
            modeloDAO.inserir(modelo);
            carregarTableView();
        }
    }

    @FXML
    public void handleBtRemover() throws IOException {
        Modelo modelo = tableViewModelo.getSelectionModel().getSelectedItem();
        if (modelo != null) {
            modeloDAO.remover(modelo);
            carregarTableView();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um modelo na Tabela.");
            alert.show();
        }
    }
    public boolean showFXMLAnchorPaneCadastrosModeloDialog(Modelo modelo) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastroModeloDialogController.class.getResource( 
            "../view/FXMLAnchorPaneCadastroModeloDialog.fxml"));
        AnchorPane page = (AnchorPane)loader.load();
        
        //criando um estágio de diálogo  (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de modelo");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        //Setando o produto ao controller
        FXMLAnchorPaneCadastroModeloDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setModelo(modelo);
        
        dialogStage.showAndWait();
        
        return controller.isButtonConfirmarClicked();
    }
}