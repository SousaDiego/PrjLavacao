/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.MarcaDAO;
import br.edu.ifsc.fln.model.dao.MotorDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.ECategoria;
import br.edu.ifsc.fln.model.domain.ETipoCombustivel;
import br.edu.ifsc.fln.model.domain.Marca;
import br.edu.ifsc.fln.model.domain.Modelo;
import br.edu.ifsc.fln.model.domain.Veiculo;
import java.sql.Connection;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FXMLAnchorPaneCadastroModeloDialogController implements Initializable {

   @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private ComboBox<Marca> cbMarca;

    @FXML
    private Label labelModeloDescricao;

    @FXML
    private Label labelModeloMarca;

    @FXML
    private TextField tfDescricao;
    
    @FXML
    private Spinner<Integer> spnPotencia;
    
    @FXML
    private ComboBox<ECategoria> cbCategoria;
    
    int potencia;
    
    @FXML
    private ComboBox<ETipoCombustivel> cbCombustivel;
    
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = (Connection) database.conectar();
    private final MarcaDAO marcaDAO = new MarcaDAO();
    private final MotorDAO motorDAO = new MotorDAO(); 
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Modelo modelo;  

   


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        marcaDAO.setConnection(connection);
        motorDAO.setConnection(connection);
        
        SpinnerValueFactory<Integer> spnFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000);
        
        spnFactory.setValue(0);
        
        spnPotencia.setValueFactory(spnFactory);
        
        carregarComboBoxMarca();
        carregarComboBoxCombustivel();
        carregarComboBoxCategorias();
     
    } 
    
    private List<Marca> listaMarca;
    private ObservableList<Marca> observableListMarca; 
    
    public void carregarComboBoxMarca() {
        listaMarca = marcaDAO.listar();
        observableListMarca = 
                FXCollections.observableArrayList(listaMarca);
        cbMarca.setItems(observableListMarca);
    }  
    
    public void carregarComboBoxCombustivel() {
        cbCombustivel.setItems(FXCollections.observableArrayList( ETipoCombustivel.values()));  
    }
    
    public void carregarComboBoxCategorias() {
        cbCategoria.setItems(FXCollections.observableArrayList(ECategoria.values()));  
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }
    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
        
        tfDescricao.setText(modelo.getDescricao());
        
        cbMarca.getSelectionModel().select(modelo.getMarca());
        
        cbCategoria.getSelectionModel().select(modelo.getCategoria());
        
        
        //spnFactory.setValue(modelo.getMotor().getPotencia());
        spnPotencia.getValueFactory().setValue(modelo.getMotor().getPotencia());
        
        cbCombustivel.getSelectionModel().select(modelo.getMotor().getSituacao());
    
    
    }
    
    @FXML
    private void handleBtConfirmar() {
        if (validarEntradaDeDados()) {
            
            modelo.setDescricao(tfDescricao.getText());
            
            modelo.setMarca(
                    cbMarca.getSelectionModel().getSelectedItem());
            modelo.setCategoria(cbCategoria.getSelectionModel().getSelectedItem());
            modelo.getMotor().setSituacao(cbCombustivel.getSelectionModel().getSelectedItem());
            modelo.getMotor().setPotencia(spnPotencia.getValue());

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }
    @FXML
   private void handleBtCancelar(ActionEvent event) {
        dialogStage.close();

    }
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        
        if (tfDescricao.getText() == null || tfDescricao.getText().isEmpty()) {
            errorMessage += "Descrição inválida!\n";
        }

              
        if (cbMarca.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione uma marca!\n";
        }
        
        if (cbCategoria.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione uma categoria!\n";
        }
        
        if(spnPotencia.getValue() == 0)
        {
            errorMessage += "Potência de Motor inválida!\n";
        }
        
        if (cbCombustivel.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione um tipo de combustível!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campo(s) inválido(s), por favor corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

//    void setVeiculo(Veiculo veiculo) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    
}