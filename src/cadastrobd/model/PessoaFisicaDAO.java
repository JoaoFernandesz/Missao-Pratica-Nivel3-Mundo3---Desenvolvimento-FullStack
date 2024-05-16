package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {

    public PessoaFisica getPessoa(int id) {
        String sql = "SELECT p.*, pf.CPF FROM Pessoa p JOIN PessoaFisica pf ON p.ID_Pessoa = pf.ID_Pessoa WHERE p.ID_Pessoa = ?";
        PessoaFisica pessoa = null;
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = ConectorBD.getPrepared(conn, sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = ConectorBD.getSelect(stmt)) {
                if (rs.next()) {
                    pessoa = new PessoaFisica(
                            rs.getInt("ID_Pessoa"),
                            rs.getString("Nome"),
                            rs.getString("Logradouro"),
                            rs.getString("Cidade"),
                            rs.getString("Estado"),
                            rs.getString("Telefone"),
                            rs.getString("Email"),
                            rs.getString("CPF")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoa;
    }

    public List<PessoaFisica> getPessoas() {
        String sql = "SELECT p.*, pf.CPF FROM Pessoa p JOIN PessoaFisica pf ON p.ID_Pessoa = pf.ID_Pessoa";
        List<PessoaFisica> pessoas = new ArrayList<>();
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = ConectorBD.getPrepared(conn, sql);
             ResultSet rs = ConectorBD.getSelect(stmt)) {

            while (rs.next()) {
                PessoaFisica pessoa = new PessoaFisica(
                        rs.getInt("ID_Pessoa"),
                        rs.getString("Nome"),
                        rs.getString("Logradouro"),
                        rs.getString("Cidade"),
                        rs.getString("Estado"),
                        rs.getString("Telefone"),
                        rs.getString("Email"),
                        rs.getString("CPF")
                );
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoas;
    }

    public boolean incluir(PessoaFisica pessoa) {
        boolean result = false;
        String sqlPessoa = "INSERT INTO Pessoa (ID_Pessoa, Nome, Telefone, Logradouro, Cidade, Estado, Email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlPessoaFisica = "INSERT INTO PessoaFisica (ID_Pessoa, CPF) VALUES (?, ?)";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa);
             PreparedStatement stmtPessoaFisica = ConectorBD.getPrepared(conn, sqlPessoaFisica)) {

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

            stmtPessoaFisica.setInt(1, nextId);
            stmtPessoaFisica.setString(2, pessoa.getCpf());
            stmtPessoaFisica.executeUpdate();

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

    public boolean alterar(PessoaFisica pessoa) {
        boolean result = false;
        String sqlPessoa = "UPDATE Pessoa SET Nome = ?, Telefone = ?, Logradouro = ?, Cidade = ?, Estado = ?, Email = ? WHERE ID_Pessoa = ?";
        String sqlPessoaFisica = "UPDATE PessoaFisica SET CPF = ? WHERE ID_Pessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa);
             PreparedStatement stmtPessoaFisica = ConectorBD.getPrepared(conn, sqlPessoaFisica)) {

            conn.setAutoCommit(false);
            stmtPessoa.setString(1, pessoa.getNome());
            stmtPessoa.setString(2, pessoa.getTelefone());
            stmtPessoa.setString(3, pessoa.getLogradouro());
            stmtPessoa.setString(4, pessoa.getCidade());
            stmtPessoa.setString(5, pessoa.getEstado());
            stmtPessoa.setString(6, pessoa.getEmail());
            stmtPessoa.setInt(7, pessoa.getId());
            stmtPessoa.executeUpdate();

            stmtPessoaFisica.setString(1, pessoa.getCpf());
            stmtPessoaFisica.setInt(2, pessoa.getId());
            stmtPessoaFisica.executeUpdate();

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
        String sqlPessoaFisica = "DELETE FROM PessoaFisica WHERE ID_Pessoa = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE ID_Pessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmtPessoaFisica = ConectorBD.getPrepared(conn, sqlPessoaFisica);
             PreparedStatement stmtPessoa = ConectorBD.getPrepared(conn, sqlPessoa)) {

            conn.setAutoCommit(false);
            stmtPessoaFisica.setInt(1, id);
            stmtPessoaFisica.executeUpdate();

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
