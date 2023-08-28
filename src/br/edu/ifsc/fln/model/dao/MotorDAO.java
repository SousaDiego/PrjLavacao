package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.ETipoCombustivel;
import br.edu.ifsc.fln.model.domain.Marca;
import br.edu.ifsc.fln.model.domain.Modelo;
import br.edu.ifsc.fln.model.domain.Motor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MotorDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

//    public boolean inserir(Motor motor) {
//        String sql = "INSERT INTO motor(potencia,tipoCombustivel=?) VALUES(?,?)";
//        try {
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setInt(1, motor.getPotencia());
////            stmt.setInt(2, motor.getModelo().getId());
//            stmt.setString(2, motor.getSituacao().getDescricao());
//            stmt.execute();
//            return true;
//        } catch (SQLException ex) {
//            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//    }
    public boolean inserir(Motor motor) {
        
       String sql = "INSERT INTO motor(id_modelo) VALUES(?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, motor.getModelo().getId());
            stmt.execute();
            alterar(motor);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
   /* public boolean inserir(Motor motor) {;
        
       String sql = "INSERT INTO motor(id_modelo) VALUES(?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, motor.getModelo().getId());
            stmt.execute();
            alterar(motor);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }*/
    

//    public boolean alterar(Motor motor) {
//        String sql = "UPDATE motor SET potencia=?, tipoCombustivel=? WHERE id_modelo=?";        
//        try {
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setInt(1, motor.getPotencia());
//            stmt.setString(2, motor.getSituacao().name());
//            stmt.setInt(3, motor.getModelo().getId());
//            stmt.execute();
//            return true;
//        } catch (SQLException ex) {
//            Logger.getLogger(MotorDAO.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//    }
    
    public boolean alterar(Motor motor) {
        String sql = "UPDATE motor SET potencia=?, tipoCombustivel=? WHERE id_modelo=?";
       
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, motor.getPotencia());
            stmt.setString(2, motor.getSituacao().name());
            stmt.setInt(3,  motor.getModelo().getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
//    public boolean alterar(Motor motor) {
//        String sql = "UPDATE motor SET id_modelo=?, potencia=?, tipoCombustivel=? WHERE id_modelo=?";        
//        try {
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setInt(1, motor.getModelo().getId());
//            stmt.setInt(2, motor.getPotencia());
//            stmt.setString(3, motor.getSituacao().getDescricao());
//            stmt.setInt(4, motor.getModelo().getId());
//            stmt.execute();
//            return true;
//        } catch (SQLException ex) {
//            Logger.getLogger(MotorDAO.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//    }

    public boolean remover(Motor motor) {
        String sql = "DELETE FROM motor WHERE id_modelo=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, motor.getModelo().getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<Motor> listar() {
        String sql = "SELECT * FROM motor";
        List<Motor> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Motor motor = new Motor();
                motor.getModelo().setId(resultado.getInt("id_modelo"));
                motor.setPotencia(resultado.getInt("potencia"));
                motor.setSituacao(Enum.valueOf(ETipoCombustivel.class, resultado.getString("tipoCombustivel")));
                retorno.add(motor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarcaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Motor buscar(Modelo modelo) {
        String sql = "SELECT * FROM motor WHERE id_modelo=?";
        Motor retorno = new Motor();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, modelo.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno = populateVO(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
     private Motor populateVO(ResultSet rs) throws SQLException {
        
//
//        modelo.setId(rs.getInt("id"));
//        modelo.setDescricao(rs.getString("descricao"));
//        Marca marca = new Marca();
//        marca.setId(rs.getInt("id_marca"));
//        MarcaDAO marcaDAO = new MarcaDAO();
//        marcaDAO.setConnection(connection);
//        marca = marcaDAO.buscar(marca);        
//        modelo.setMarca(marca);
        Motor motor = new Motor();
        motor.setPotencia(rs.getInt("potencia"));
        motor.setSituacao(Enum.valueOf(ETipoCombustivel.class, rs.getString("TipoCombustivel")));
        return motor;
    }

    
    
}
