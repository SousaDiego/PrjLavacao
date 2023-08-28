/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.ClienteDAO;
import br.edu.ifsc.fln.model.dao.CorDAO;
import br.edu.ifsc.fln.model.dao.ModeloDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Cliente;
import br.edu.ifsc.fln.model.domain.Cor;
import br.edu.ifsc.fln.model.domain.Modelo;
import br.edu.ifsc.fln.model.domain.Veiculo;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aluno
 */
public class FXMLAchorPaneCadastroVeiculoDialogController implements Initializable {

    /**
     * Initializes the controller class.
     */
   @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private ComboBox<Cor> cbCor;

    @FXML
    private ComboBox<Modelo> cbModelo;

    @FXML
    private Label labelVeiculoModelo;

    @FXML
    private Label lbObservacao;

    @FXML
    private Label lbPlaca;

    @FXML
    private TextField tfObservacao;

    @FXML
    private TextField tfPlaca;
    
    @FXML
    private ComboBox<Cliente> cbCliente;

   
    //    private List<Categoria> listaCategorias;
//    private ObservableList<Categoria> observableListCategorias;
        
    //atributos para manipulação de banco de dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ModeloDAO modeloDAO = new ModeloDAO();
    private final CorDAO corDAO = new CorDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Veiculo veiculo;  


    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modeloDAO.setConnection(connection);
        corDAO.setConnection(connection);
        clienteDAO.setConnection(connection);
        carregarComboBoxModelo();
        carregarComboBoxCor();
        carregarComboBoxClientes();
        } 
    
    private List<Modelo> listaModelo;
    private ObservableList<Modelo> observableListModelo;
    private List<Cor> listaCor;
    private ObservableList<Cor> observableListCor;
    
    public void carregarComboBoxModelo() {
        listaModelo = modeloDAO.listar();
        observableListModelo = 
                FXCollections.observableArrayList(listaModelo);
        cbModelo.setItems(observableListModelo);
    }    
    public void carregarComboBoxCor() {
        listaCor = corDAO.listar();
        observableListCor = 
                FXCollections.observableArrayList(listaCor);
        cbCor.setItems(observableListCor);
    } 
    
    private List<Cliente> listaClientes;
    private ObservableList<Cliente> observableListClientes; 
    public void carregarComboBoxClientes() {
                listaClientes = clienteDAO.listar();
        observableListClientes = 
                FXCollections.observableArrayList(listaClientes);
        cbCliente.setItems(observableListClientes);
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
    
    public Veiculo getVeiculo() {
        return veiculo;
    }

    
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
        tfPlaca.setText(veiculo.getPlaca());
        tfObservacao.setText(veiculo.getObservacoes());
        cbModelo.getSelectionModel().select(veiculo.getModelo());
        cbCor.getSelectionModel().select(veiculo.getCor());
        cbCliente.getSelectionModel().select(veiculo.getCliente());
    }   

    
    @FXML
    private void handleBtConfirmar(ActionEvent event) {
        if (validarEntradaDeDados()) {
            veiculo.setPlaca(tfPlaca.getText());
            veiculo.setObservacoes(tfObservacao.getText());
            veiculo.setModelo(cbModelo.getSelectionModel().getSelectedItem());
            veiculo.setCor(cbCor.getSelectionModel().getSelectedItem());
            veiculo.setCliente(cbCliente.getSelectionModel().getSelectedItem());
            buttonConfirmarClicked = true;
            dialogStage.close();
        }

    }
  @FXML
    void handleBtCancelar(ActionEvent event) {
        dialogStage.close();
    }
    
private boolean validarEntradaDeDados() {
        String errorMessage = "";
        
        if (tfPlaca.getText() == null || tfPlaca.getText().isEmpty()) {
            errorMessage += "Placa inválida!\n";
        }

        if (tfObservacao.getText() == null || tfObservacao.getText().isEmpty()) {
            errorMessage += "inválido!\n";
        }
        
        if (cbModelo.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione um modelo!\n";
        }
         if (cbCor.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione uma cor!\n";
        }
        if (cbCliente.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione um cliente!\n";
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
    
}
   
    

