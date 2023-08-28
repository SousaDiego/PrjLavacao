/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.VeiculoDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Veiculo;
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
public class FXMLAchorPaneCadastroVeiculoController implements Initializable {

    @FXML
    private Button btAlterar;

    @FXML
    private Button btInserir;

    @FXML
    private Button btRemover;

    @FXML
    private Label lbCor;

    @FXML
    private Label lbVeiculoId;

    @FXML
    private Label lbVeiculoMarca;

    @FXML
    private Label lbVeiculoModelo;

    @FXML
    private Label lbVeiculoObservacao;

    @FXML
    private Label lbVeiculoPlaca;
    
    @FXML
    private Label lbVeiculoCliente;

    @FXML
    private TableColumn<Veiculo, String> tableColumnNome;

    @FXML
    private TableView<Veiculo> tableView;
    
    private List<Veiculo> listaVeiculo;
    private ObservableList<Veiculo> observableListVeiculo;

    //acesso ao banco de dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        veiculoDAO.setConnection(connection);

        carregarTableView();

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableView(newValue));
    } 
    
    public void carregarTableView() {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("placa"));
       // tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        
        listaVeiculo = veiculoDAO.listar();
        
        observableListVeiculo = FXCollections.observableArrayList(listaVeiculo);
        tableView.setItems(observableListVeiculo);
    }
    
    public void selecionarItemTableView(Veiculo veiculo) {
        //DecimalFormat df = new DecimalFormat("0.00");
        if (veiculo != null) {
            lbVeiculoId.setText(Integer.toString(veiculo.getId()));
            lbVeiculoPlaca.setText(veiculo.getPlaca());
            lbVeiculoObservacao.setText(veiculo.getObservacoes());
            lbVeiculoModelo.setText(veiculo.getModelo().getDescricao());
            lbCor.setText(veiculo.getCor().getNome());
            lbVeiculoMarca.setText(veiculo.getModelo().getMarca().getNome());
            lbVeiculoCliente.setText(veiculo.getCliente().getNome());
        } else {
            lbVeiculoId.setText("");
            lbVeiculoPlaca.setText("");
            lbVeiculoObservacao.setText("");
            lbVeiculoModelo.setText("");
            lbCor.setText("");
            lbVeiculoCliente.setText("");
        }
    }
    
    @FXML
    void handleBtAlterar()throws IOException {
        Veiculo veiculo = tableView.getSelectionModel().getSelectedItem();
        if (veiculo != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosVeiculoDialog(veiculo);
            if (buttonConfirmarClicked) {
                veiculoDAO.alterar(veiculo);
                carregarTableView();
    }
            } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um produto na Tabela.");
            alert.show();
        }
        }
    

    @FXML
    public void handleBtInserir()throws IOException {
        Veiculo veiculo = new Veiculo();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosVeiculoDialog(veiculo);
        if (buttonConfirmarClicked) {
            veiculoDAO.inserir(veiculo);
            carregarTableView();
        }
    }

    @FXML
    void handleBtRemover() throws IOException {
        Veiculo veiculo = tableView.getSelectionModel().getSelectedItem();
        if (veiculo != null) {
            veiculoDAO.remover(veiculo);
            carregarTableView();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um veiculo na Tabela.");
            alert.show();
        }

    }
    
public boolean showFXMLAnchorPaneCadastrosVeiculoDialog(Veiculo veiculo) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAchorPaneCadastroVeiculoDialogController.class.getResource( 
            "../view/FXMLAchorPaneCadastroVeiculoDialog.fxml"));
        AnchorPane page = (AnchorPane)loader.load();
        
        //criando um estágio de diálogo  (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de veiculo");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        //Setando o produto ao controller
        FXMLAchorPaneCadastroVeiculoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVeiculo(veiculo);
        
        dialogStage.showAndWait();
        
        return controller.isButtonConfirmarClicked();
    }    
    
}
