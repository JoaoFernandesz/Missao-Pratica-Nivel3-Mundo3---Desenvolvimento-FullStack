package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {

    public PessoaJuridica getPessoa(int id) {
        String sql = "SELECT p.*, pj.CNPJ FROM Pessoa p JOIN PessoaJuridica pj ON p.ID_Pessoa = pj.ID_Pessoa WHERE p.ID_Pessoa = ?";
        PessoaJuridica pessoa = null;
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = ConectorBD.getPrepared(conn, sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = ConectorBD.getSelect(stmt)) {
                if (rs.next()) {
                    pessoa = new PessoaJuridica(
                            rs.getInt("ID_Pessoa"),
                            rs.getString("Nome"),
                            rs.getString("Logradouro"),
                            rs.getString("Cidade"),
                            rs.getString("Estado"),
                            rs.getString("Telefone"),
                            rs.getString("Email"),
                            rs.getString("CNPJ")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoa;
    }

    public List<PessoaJuridica> getPessoas() {
        String sql = "SELECT p.*, pj.CNPJ FROM Pessoa p JOIN PessoaJuridica pj ON p.ID_Pessoa = pj.ID_Pessoa";
        List<PessoaJuridica> pessoas = new ArrayList<>();
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = ConectorBD.getPrepared(conn, sql);
             ResultSet rs = ConectorBD.getSelect(stmt)) {

            while (rs.next()) {
                PessoaJuridica pessoa = new PessoaJuridica(
                        rs.getInt("ID_Pessoa"),
                        rs.getString("Nome"),
                        rs.getString("Logradouro"),
                        rs.getString("Cidade"),
                        rs.getString("Estado"),
                        rs.getString("Telefone"),
                        rs.getString("Email"),
                        rs.getString("CNPJ")
                );
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoas;
    }

    public boolean incluir(PessoaJuridica pessoa) {
        boolean result = false;
        String sqlPessoa = "INSERT INTO Pessoa (ID_Pessoa, Nome, Telefone, Logradouro, Cidade, Estado, Email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPessoaJuridica = "INSERT INTO PessoaJuridica (ID_Pessoa, CNPJ) VALUES (?, ?)";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa);
             PreparedStatement stmtPessoaJuridica = ConectorBD.getPrepared(conn, sqlPessoaJuridica)) {

            conn.setAutoCommit(false);
            int nextId = SequenceManager.getValue("Seq_ID_Pessoa");
            stmtPessoa.setInt(1, nextId);
            stmtPessoa.setString(2, pessoa.getNome());
            stmtPessoa.setString(3, pessoa.getTelefone());
            stmtPessoa.setString(4, pessoa.getLogradouro());
            stmtPessoa.setString(5, pessoa.getCidade());
            stmtPessoa.setString(6, pessoa.getEstado());
            stmtPessoa.setString(7, pessoa.getEmail());
            stmtPessoa.executeUpdate();

            stmtPessoaJuridica.setInt(1, nextId);
            stmtPessoaJuridica.setString(2, pessoa.getCnpj());
            stmtPessoaJuridica.executeUpdate();

            conn.commit();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection conn = ConectorBD.getConnection()) {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public boolean alterar(PessoaJuridica pessoa) {
        boolean result = false;
        String sqlPessoa = "UPDATE Pessoa SET Nome = ?, Telefone = ?, Logradouro = ?, Cidade = ?, Estado = ?, Email = ? WHERE ID_Pessoa = ?";
        String sqlPessoaJuridica = "UPDATE PessoaJuridica SET CNPJ = ? WHERE ID_Pessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa);
             PreparedStatement stmtPessoaJuridica = ConectorBD.getPrepared(conn, sqlPessoaJuridica)) {

            conn.setAutoCommit(false);
            stmtPessoa.setString(1, pessoa.getNome());
            stmtPessoa.setString(2, pessoa.getTelefone());
            stmtPessoa.setString(3, pessoa.getLogradouro());
            stmtPessoa.setString(4, pessoa.getCidade());
            stmtPessoa.setString(5, pessoa.getEstado());
            stmtPessoa.setString(6, pessoa.getEmail());
            stmtPessoa.setInt(7, pessoa.getId());
            stmtPessoa.executeUpdate();

            stmtPessoaJuridica.setString(1, pessoa.getCnpj());
            stmtPessoaJuridica.setInt(2, pessoa.getId());
            stmtPessoaJuridica.executeUpdate();

            conn.commit();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection conn = ConectorBD.getConnection()) {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public boolean excluir(int id) {
        boolean result = false;
        String sqlPessoaJuridica = "DELETE FROM PessoaJuridica WHERE ID_Pessoa = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE ID_Pessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoaJuridica = ConectorBD.getPrepared(conn, sqlPessoaJuridica);
             PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa)) {

            conn.setAutoCommit(false);
            stmtPessoaJuridica.setInt(1, id);
            stmtPessoaJuridica.executeUpdate();

            stmtPessoa.setInt(1, id);
            stmtPessoa.executeUpdate();

            conn.commit();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection conn = ConectorBD.getConnection()) {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
