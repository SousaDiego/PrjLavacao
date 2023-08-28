package br.edu.ifsc.fln.model.dao;

import br.edu.ifsc.fln.model.domain.Cliente;
import br.edu.ifsc.fln.model.domain.Cor;
import br.edu.ifsc.fln.model.domain.Marca;
import br.edu.ifsc.fln.model.domain.Modelo;
import br.edu.ifsc.fln.model.domain.PessoaFisica;
import br.edu.ifsc.fln.model.domain.PessoaJuridica;
import br.edu.ifsc.fln.model.domain.Veiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VeiculoDAO {

    private Connection connection;
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Veiculo veiculo) {
        String sql = "INSERT INTO veiculo(placa, observacoes,id_modelo, id_cor, id_cliente) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getObservacoes());
            stmt.setInt(3, veiculo.getModelo().getId());
            stmt.setInt(4, (int) veiculo.getCor().getId());
            stmt.setInt(5, veiculo.getCliente().getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Veiculo veiculo) {
        String sql = "UPDATE veiculo SET placa=?, observacoes=?, id_modelo=?,id_cor=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getObservacoes());
            stmt.setInt(3, veiculo.getModelo().getId());
            stmt.setInt(4, (int) veiculo.getCor().getId());
            stmt.setInt(5, veiculo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Veiculo veiculo) {
        String sql = "DELETE FROM veiculo WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, veiculo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Veiculo> listar() {
        String sql =  "SELECT v.id as veiculo_id, v.placa as veiculo_placa, v.observacoes as veiculo_observacoes, "
                     +" m.id as id_modelo, m.descricao as modelo_descricao, m.id_marca as modelo_idMarca, "
                     +" c.id as id_cor, c.nome as cor_nome, v.id_cliente as cliente_id "//c.id as cliente_id
                     +"FROM veiculo v "
                     +"INNER JOIN modelo m ON m.id = v.id_modelo "
                     +"INNER JOIN cor c ON c.id = v.id_cor;";
        List<Veiculo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Veiculo veiculo = populateVO(resultado);
                retorno.add(veiculo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    
    
    private Veiculo populateVO(ResultSet rs) throws SQLException {
        Veiculo veiculo = new Veiculo();
        Modelo modelo = new Modelo();
        Cor cor = new Cor();
        
        veiculo.setCor(cor);
        veiculo.setModelo(modelo);

        veiculo.setId(rs.getInt("veiculo_id"));
        veiculo.setPlaca(rs.getString("veiculo_placa"));
        veiculo.setObservacoes(rs.getString("veiculo_Observacoes"));
        modelo.setId(rs.getInt("id_modelo"));
        modelo.setDescricao(rs.getString("modelo_descricao"));
        
        Marca marca = new Marca();
        marca.setId(rs.getInt("modelo_idMarca"));
        MarcaDAO marcaDAO = new MarcaDAO();
        marcaDAO.setConnection(connection);
        marca = marcaDAO.buscar(marca);        
        modelo.setMarca(marca);

        
        cor.setId(rs.getInt("id_cor"));
        cor.setNome(rs.getString("cor_nome"));
        
        int idCliente = rs.getInt("cliente_id");
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.setConnection(connection);
        Cliente cliente = clienteDAO.buscar(idCliente);
        veiculo.setCliente(cliente);
        
//        Cliente cliente;
//        if (rs.getString("nif") == null) {
//            cliente = new PessoaFisica();
//            ((PessoaFisica)cliente).setCpf(rs.getString(14));
//        } else {
//            cliente = new PessoaJuridica();
//            ((PessoaJuridica)cliente).setCnpj(rs.getString(16));
//            //((PessoaJuridica)cliente).setPais(rs.getString(17));
//        }
//        cliente.setId(rs.getInt(6));
//        cliente.setNome(rs.getString(10));
//        cliente.setEmail(rs.getString(11));
//        cliente.setCelular(rs.getString(12));
//        veiculo.setCliente(cliente);
       
        return veiculo;
    }
public Veiculo buscar(Veiculo veiculo) {
        String sql =  "SELECT v.id as veiculo_id, v.placa as veiculo_placa, v.observacoes as veiculo_observacoes, m.id as modelo_id, m.descricao as modelo_descricao, c.id as cor_id, c.nome as cor_nome, cl.id as cliente_id, cl.nome as cliente_nome, cl.celular as cliente_celular, cl.email as cliente_email, cl.endereco as cliente_endereco, cl.dataCadastro as cliente_dataCadastro, pf.cpf as cliente_cpf, pf.dataNascimento as cliente_dataNascimentoPF, pj.cnpj as cliente_cnpj, pj.inscricaoEstadual as cliente_inscricaoEstadual, pj.dataNascimento as cliente_dataNascimentoPJ FROM veiculo v INNER JOIN modelo m ON m.id = v.id_modelo INNER JOIN cor c ON c.id = v.id_cor INNER JOIN cliente cl on cl.id=v.id_cliente LEFT JOIN pessoa_fisica pf on pf.id_cliente = cl.id LEFT JOIN pessoa_juridica pj on pj.id_cliente = cl.id WHERE v.id = ?;";
        Veiculo retorno = new Veiculo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, veiculo.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno = populateSingleVO(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }


private Veiculo populateSingleVO(ResultSet rs) throws SQLException {
        Veiculo veiculo = new Veiculo();
        Modelo modelo = new Modelo();
        Cor cor = new Cor();
        Cliente cliente;
        veiculo.setModelo(modelo);
        veiculo.setCor(cor);
        
        //dados do veiculo
        veiculo.setId(rs.getInt("veiculo_id"));
        veiculo.setPlaca(rs.getString("veiculo_placa"));
        veiculo.setObservacoes(rs.getString("veiculo_observacoes"));
        modelo.setId(rs.getInt("modelo_id"));
        modelo.setDescricao(rs.getString("modelo_descricao"));
        cor.setId(rs.getInt("cor_id"));
        cor.setNome(rs.getString("cor_nome"));
        if (rs.getString("cliente_cnpj") == null || rs.getString("cliente_cnpj").length() <= 0) 
        {
            //é um cliente PessoaFisica
            cliente =  new PessoaFisica();
            ((PessoaFisica)cliente).setCpf(rs.getString("cliente_cpf"));
            ((PessoaFisica)cliente).setDataNascimento(rs.getDate("cliente_dataNascimentoPF").toLocalDate());
        }
        else
        {
           //é um cliente PessoaJuridica
           cliente =  new PessoaJuridica();
           ((PessoaJuridica)cliente).setCnpj(rs.getString("cliente_cnpj"));
           ((PessoaJuridica)cliente).setInscricaoEstadual(rs.getString("cliente_inscricaoEstadual"));
           ((PessoaJuridica)cliente).setDataNascimento(rs.getDate("cliente_dataNascimentoPJ").toLocalDate());
        }
        veiculo.setCliente(cliente);
        cliente.setId(rs.getInt("cliente_id"));
        cliente.setNome(rs.getString("cliente_nome"));
        cliente.setCelular(rs.getString("cliente_celular"));
        cliente.setEmail(rs.getString("cliente_email"));
        cliente.setEndereco(rs.getString("cliente_endereco"));
        cliente.setDataCadastro(rs.getDate("cliente_dataCadastro").toLocalDate());
        cliente.add(veiculo);
        
        
        return veiculo;        
    }
}

