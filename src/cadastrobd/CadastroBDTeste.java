package cadastrobd;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;

public class CadastroBDTeste {

    public static void main(String[] args) {
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

        // Instanciar uma pessoa física e persistir no banco de dados
        PessoaFisica pessoaFisica = new PessoaFisica(0, "João", "Rua B", "Cidade A", "RJ", "1121-1111", "joao@rracho.com", "11111121111");
        pessoaFisicaDAO.incluir(pessoaFisica);

        // Alterar os dados da pessoa física no banco
        pessoaFisica.setNome("João Alterado");
        pessoaFisicaDAO.alterar(pessoaFisica);

        // Consultar todas as pessoas físicas do banco de dados e listar no console
        for (PessoaFisica pf : pessoaFisicaDAO.getPessoas()) {
            pf.exibir();
        }

        // Excluir a pessoa física criada anteriormente no banco
        pessoaFisicaDAO.excluir(pessoaFisica.getId());

        // Instanciar uma pessoa jurídica e persistir no banco de dados
        PessoaJuridica pessoaJuridica = new PessoaJuridica(0, "Empresa X", "Rua B", "Cidade B", "PA", "1212-1212", "emaaesa@riacho.com", "11111111121111");
        pessoaJuridicaDAO.incluir(pessoaJuridica);

        // Alterar os dados da pessoa jurídica no banco
        pessoaJuridica.setNome("Empresa X Alterada");
        pessoaJuridicaDAO.alterar(pessoaJuridica);

        // Consultar todas as pessoas jurídicas do banco de dados e listar no console
        for (PessoaJuridica pj : pessoaJuridicaDAO.getPessoas()) {
            pj.exibir();
        }

        // Excluir a pessoa jurídica criada anteriormente no banco
        pessoaJuridicaDAO.excluir(pessoaJuridica.getId());
    }
}
