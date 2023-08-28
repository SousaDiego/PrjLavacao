/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.CorDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Cor;
import java.sql.Connection;
import java.io.IOException;
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
public class FXMLAchorPaneCadastroCorController implements Initializable {

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
    private TableColumn<Cor, String> tableColumCorDescricao;

    @FXML
    private TableView<Cor> tableViewCor;
    
    private List<Cor> listaCor;
    private ObservableList<Cor> observableListCor;
    
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final CorDAO corDAO = new CorDAO();
    
    @FXML
    public void handleBtAlterar() throws IOException {
        Cor cor= tableViewCor.getSelectionModel().getSelectedItem();
        if (cor != null) {  boolean btConfirmarClicked = showFXMLAnchorPaneCadastroCorDialog(cor);
            if (btConfirmarClicked) {
                corDAO.alterar(cor);
                carregarTableViewCor();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde uma Cor na tabela ao lado");
            alert.show();
        }
    }

    @FXML
    public void handleBtExcluir() throws IOException {
        Cor cor = tableViewCor.getSelectionModel().getSelectedItem();
        if (cor != null) {
            corDAO.remover(cor);
            carregarTableViewCor();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde uma Cor na tabela ao lado");
            alert.show();
        }
    }

    @FXML
    public void handleBtInserir() throws IOException {
        Cor cor = new Cor();
        boolean btConfirmarClicked = showFXMLAnchorPaneCadastroCorDialog(cor);
        if (btConfirmarClicked) {
            corDAO.inserir(cor);
            carregarTableViewCor();
        }
    }

    private boolean showFXMLAnchorPaneCadastroCorDialog(Cor cor) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(FXMLAchorPaneCadastroCorController.class.getResource("../view/FXMLAchorPaneCadastroCorDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        //criação de um estágio de diálogo (StageDialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Categoria");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //enviando o obejto categoria para o controller
        FXMLAchorPaneCadastroCorDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCor(cor);

        //apresenta o diálogo e aguarda a confirmação do usuário
        dialogStage.showAndWait();

        return controller.isBtConfirmarClicked();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        corDAO.setConnection(connection);
        carregarTableViewCor();

        tableViewCor.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue)
                        -> selecionarItemTableViewCor(newValue));

    }

    public void carregarTableViewCor() {
        tableColumCorDescricao.setCellValueFactory( new PropertyValueFactory<>("Nome"));

        listaCor = corDAO.listar();

        observableListCor = FXCollections.observableArrayList(listaCor);
        tableViewCor.setItems(observableListCor);
    }

    public void selecionarItemTableViewCor(Cor cor) {
        if (cor != null) {
            lbId.setText(String.valueOf(cor.getId()));
            lbNome.setText(cor.getNome());
        } else {
            lbId.setText("");
            lbNome.setText("");
        }

    }

}
