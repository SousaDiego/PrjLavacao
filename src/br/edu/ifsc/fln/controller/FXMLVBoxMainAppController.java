/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.edu.ifsc.fln.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Aluno
 */
public class FXMLVBoxMainAppController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MenuItem MenuItemCadastroMarca;

    @FXML
    private MenuItem MenuItemCadastroModelo;

    @FXML
    private MenuItem MenuItemCadastroVeiculo;
    
    @FXML
    private MenuItem MenuItemCadastroCliente;

    @FXML
    private MenuItem MenuItemGraficosLavacaoPorMes;

    @FXML
    private MenuItem MenuItemProcessosOrdemDeServico;

    @FXML
    private MenuItem MenuItemRelatorioQuantidadeDeLavacao;

    @FXML
    private MenuItem menuitemCadastroCor;


//    @FXML
//    void hadleItemCadastroVeiculo(ActionEvent event) {
//
//    }
    @FXML
    public void hadleItemCadastroVeiculo()
            throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAchorPaneCadastroVeiculo.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    void hadleItemGraficosLavacaoPorMes(ActionEvent event) {

    }

    @FXML
    void hadleItemProcessosOrdemDeServico() 
            throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneProcessoOrdensDeServico.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void hadleItemRelatorioQuantidadeLavacao() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneRelatorioServico.fxml"));
        anchorPane.getChildren().setAll(a);
    } 
    @FXML
    public void hadleItemCadastroModelo()
            throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAchorPaneCadastroModelo.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void hadleItemCadastroMarca()
            throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAchorPaneCadastroMarca.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemCadastroCor()
            throws IOException {
          AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAchorPaneCadastroCor.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemCadastroCliente()
            throws IOException {
          AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneCadastroCliente.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemCadastroServico()
            throws IOException {
          AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("../view/FXMLAnchorPaneCadastroServico.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
