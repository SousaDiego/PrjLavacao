/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsc.fln.model.domain;

/**
 *
 * @author Aluno
 */
public class Motor {
    private int potencia;
//    private ETipoCombustivel situacao ;
    ETipoCombustivel situacao = ETipoCombustivel.GASOLINA;
    
    private Modelo modelo;

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public ETipoCombustivel getSituacao() {
        return situacao;
    }

    public void setSituacao(ETipoCombustivel situacao) {
        this.situacao = situacao;
    }

   
    

    

    @Override
    public String toString() {
        return Integer.toString(this.potencia);
    }
    
   
    
}
