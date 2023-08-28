package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.ECategoria;
import br.edu.ifsc.fln.model.domain.ETipoCombustivel;
import br.edu.ifsc.fln.model.domain.Modelo;
import br.edu.ifsc.fln.model.domain.Marca;
import br.edu.ifsc.fln.model.domain.Motor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeloDAO {

    private Connection connection;
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Modelo modelo) {
        String sql = "INSERT INTO modelo(descricao, id_marca, categoria) VALUES(?,?,?)";
        final String sqlEstoque = "INSERT INTO motor(id_modelo, potencia, tipoCombustivel) VALUES((SELECT max(id) FROM modelo), ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getDescricao());
            stmt.setInt(2, modelo.getMarca().getId());
            stmt.setString(3, modelo.getCategoria().name());
            stmt.execute();
            stmt = connection.prepareStatement(sqlEstoque);
            stmt.setInt(1, modelo.getMotor().getPotencia());
            stmt.setString(2, modelo.getMotor().getSituacao().getDescricao());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }/*
    public boolean inserir(Modelo modelo) {
        final String sql = "INSERT INTO modelo(descricao, id_marca) VALUES(?,?);";
        final String sqlMotor = "INSERT INTO motor(id_modelo, potencia, tipoCombustivel) VALUES((SELECT max(id) FROM modelo), ?, ?)";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getDescricao());
            stmt.setInt(2, modelo.getMarca().getId());
            stmt.execute();
            stmt = connection.prepareStatement(sqlMotor);
            stmt.setInt(1, modelo.getMotor().getPotencia());
            stmt.setString(2, modelo.getMotor().getSituacao().getDescricao());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
*/
    public boolean alterar(Modelo modelo) {
        String sql = "UPDATE modelo SET descricao=?,id_marca=?,categoria=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, modelo.getDescricao());
            stmt.setInt(4,  modelo.getId());
            stmt.setString(3, modelo.getCategoria().name());
            stmt.setInt(2, modelo.getMarca().getId());
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Modelo modelo) {
        String sql = "DELETE FROM modelo WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1,  modelo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

  public List<Modelo> listar() {
        String sql = "SELECT * FROM modelo";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Modelo modelo = populateVO(resultado);
                retorno.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    } /* 
    public List<Modelo> listar() {
        String sql =  "SELECT m.id as modelo_id, m.descricao as modelo_nome, "
                + "mr.id as marca_id, mr.nome as marca_descricao "
                + "FROM modelo m INNER JOIN marca mr ON m.id_marca = mr.id;";
        List<Modelo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Modelo modelo = populateVO(resultado);
                retorno.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }*//*
    public Modelo buscar(Modelo modelo) {
        String sql =  "SELECT mt.id_modelo as motor_idModelo, mt.potencia as motor_potencia, mt.tipoCombustivel as motor_comb,  m.id as modelo_id, m.descricao as modelo_nome, "
                + "mr.id as marca_id, mr.nome as marca_descricao "
                + "FROM modelo m INNER JOIN marca mr INNER JOIN motor mt ON m.id_marca = mr.id AND mt.id_modelo = m.id WHERE m.id = ?;";

        Modelo retorno = new Modelo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, modelo.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno = populateVO(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }*/
    public Modelo buscar(Modelo modelo) {
        String sql = "SELECT * FROM modelo WHERE id=?";
        Modelo retorno = new Modelo();
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
   /* private Modelo populateVO(ResultSet rs, boolean comMarca) throws SQLException {
        Modelo modelo = new Modelo();
        //produto.setCategoria(categoria);

        modelo.setId(rs.getInt("id"));
        modelo.setDescricao(rs.getString("descricao"));
       
        
        if (comMarca) {
            Marca marca = new Marca();
            marca.setId(rs.getInt("marca"));
            MarcaDAO marcaDAO = new MarcaDAO();
            marcaDAO.setConnection(connection);
            marca = marcaDAO.buscar(marca);
            modelo.setMarca(marca);
        }

        return modelo;
    }*/
    

    private Modelo populateVO(ResultSet rs) throws SQLException {
        Modelo modelo = new Modelo();

        modelo.setId(rs.getInt("id"));
        modelo.setDescricao(rs.getString("descricao"));
        modelo.setCategoria(Enum.valueOf(ECategoria.class, rs.getString("ECategoria")));
        
        Marca marca = new Marca();
        marca.setId(rs.getInt("id_marca"));
        MarcaDAO marcaDAO = new MarcaDAO();
        marcaDAO.setConnection(connection);
        marca = marcaDAO.buscar(marca);        
        modelo.setMarca(marca);
        MotorDAO motorDAO = new MotorDAO();
        motorDAO.setConnection(connection);
        modelo.setMotor(motorDAO.buscar(modelo));   
        
        //modelo.setMotor(motor);


        return modelo;
    }/*
    private Modelo populateVO(ResultSet rs) throws SQLException {
        Modelo modelo = new Modelo();
        Marca marca = new Marca();
        modelo.setMarca(marca);
        MarcaDAO marcaDAO = new MarcaDAO();
        modelo.setDescricao(rs.getString("marca_descricao"));
        marcaDAO.setConnection(connection);
        marca = marcaDAO.buscar(marca);        
        marca.setId(rs.getInt("marca_id"));
        modelo.setId(rs.getInt("modelo_id"));
        //modelo.setId(rs.getInt("id"));
        //modelo.getMotor().setModelo(modelo);
        modelo.getMotor().setPotencia(rs.getInt("motor_potencia"));
        modelo.getMotor().setSituacao(ETipoCombustivel.valueOf(rs.getString("motor_comb")));
        
        return modelo;
    } */  
}
