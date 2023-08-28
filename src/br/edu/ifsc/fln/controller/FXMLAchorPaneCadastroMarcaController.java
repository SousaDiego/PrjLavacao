/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.MarcaDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Marca;
import java.io.IOException;
import java.sql.Connection;
import java.net.URL;
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
public class FXMLAchorPaneCadastroMarcaController implements Initializable {

    /**
     * Initializes the controller class.
     */
        @FXML
    private Button btAlterar;

    @FXML
    private Button btExcluir;

    @FXML
    private Button btInserir;

     @FXML
    private Label lbId;

    @FXML
    private Label lbNome;

    @FXML
    private TableColumn<Marca, String> tableColumnCategoriaDescricao;

    @FXML
    private TableView<Marca> tableViewMarca;
    private List<Marca> listaCategorias;
    private ObservableList<Marca> observableListCategorias;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final MarcaDAO marcaDAO = new MarcaDAO();
    
    @FXML
    public void handleBtAlterar() throws IOException {
        Marca marca = tableViewMarca.getSelectionModel().getSelectedItem();
        if (marca != null) {  boolean btConfirmarClicked = showFXMLAnchorPaneCadastroMarcaDialog(marca);
            if (btConfirmarClicked) {
                marcaDAO.alterar(marca);
                carregarTableViewMarca();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde uma Cor na tabela ao lado");
            alert.show();
        }
    }

    @FXML
    public void handleBtExcluir() throws IOException {
        Marca marca = tableViewMarca.getSelectionModel().getSelectedItem();
        if (marca != null) {
            marcaDAO.remover(marca);
            carregarTableViewMarca();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde uma Cor na tabela ao lado");
            alert.show();
        }
    }

    @FXML
    public void handleBtInserir() throws IOException {
        Marca marca = new Marca();
        boolean btConfirmarClicked = showFXMLAnchorPaneCadastroMarcaDialog(marca);
        if (btConfirmarClicked) {
          marcaDAO.inserir(marca);
            carregarTableViewMarca();
        }
    }

    private boolean showFXMLAnchorPaneCadastroMarcaDialog(Marca marca) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(FXMLAchorPaneCadastroMarcaController.class.getResource("../view/FXMLAchorPaneCadastroMarcaDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        //criação de um estágio de diálogo (StageDialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Categoria");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //enviando o obejto categoria para o controller
        FXMLAchorPaneCadastroMarcaDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMarca(marca);

        //apresenta o diálogo e aguarda a confirmação do usuário
        dialogStage.showAndWait();

        return controller.isBtConfirmarClicked();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          marcaDAO.setConnection(connection);
        carregarTableViewMarca();

        tableViewMarca.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue)
                        -> selecionarItemTableViewMarca(newValue));

    }

    public void carregarTableViewMarca() {
        tableColumnCategoriaDescricao.setCellValueFactory(
                new PropertyValueFactory<>("nome"));

        listaCategorias = marcaDAO.listar();

        observableListCategorias
                = FXCollections.observableArrayList(listaCategorias);
        tableViewMarca.setItems(observableListCategorias);
    }

    public void selecionarItemTableViewMarca(Marca marca) {
        if (marca != null) {
            lbId.setText(String.valueOf(marca.getId()));
            lbNome.setText(marca.getNome());
        } else {
            lbId.setText("");
            lbNome.setText("");
        }

    }  
    
}
