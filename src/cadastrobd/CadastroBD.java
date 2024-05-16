package cadastrobd;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;

import java.util.List;
import java.util.Scanner;

public class CadastroBD {

    public static void main(String[] args) {
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("==================================");
            System.out.println("1 - Incluir Pessoa");
            System.out.println("2 - Alterar Pessoa");
            System.out.println("3 - Excluir Pessoa");
            System.out.println("4 - Buscar pelo Id");
            System.out.println("5 - Exibir Todos");
            System.out.println("0 - Finalizar Programa");
            System.out.println("==================================");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (opcao) {
                case 1:
                    System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
                    String tipoPessoa = scanner.nextLine();
                    if (tipoPessoa.equalsIgnoreCase("F")) {
                        PessoaFisica pessoaFisica = lerDadosPessoaFisica(scanner);
                        pessoaFisicaDAO.incluir(pessoaFisica);
                    } else if (tipoPessoa.equalsIgnoreCase("J")) {
                        PessoaJuridica pessoaJuridica = lerDadosPessoaJuridica(scanner);
                        pessoaJuridicaDAO.incluir(pessoaJuridica);
                    } else {
                        System.out.println("Tipo inválido!");
                    }
                    break;
                case 2:
                    System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
                    tipoPessoa = scanner.nextLine();
                    System.out.println("Digite o ID:");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (tipoPessoa.equalsIgnoreCase("F")) {
                        PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);
                        if (pessoaFisica != null) {
                            System.out.println("Dados atuais:");
                            pessoaFisica.exibir();
                            System.out.println("Digite os novos dados:");
                            pessoaFisica = lerDadosPessoaFisica(scanner, id);
                            pessoaFisicaDAO.alterar(pessoaFisica);
                        } else {
                            System.out.println("Pessoa não encontrada.");
                        }
                    } else if (tipoPessoa.equalsIgnoreCase("J")) {
                        PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
                        if (pessoaJuridica != null) {
                            System.out.println("Dados atuais:");
                            pessoaJuridica.exibir();
                            System.out.println("Digite os novos dados:");
                            pessoaJuridica = lerDadosPessoaJuridica(scanner, id);
                            pessoaJuridicaDAO.alterar(pessoaJuridica);
                        } else {
                            System.out.println("Pessoa não encontrada.");
                        }
                    } else {
                        System.out.println("Tipo inválido!");
                    }
                    break;
                case 3:
                    System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
                    tipoPessoa = scanner.nextLine();
                    System.out.println("Digite o ID:");
                    id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (tipoPessoa.equalsIgnoreCase("F")) {
                        pessoaFisicaDAO.excluir(id);
                    } else if (tipoPessoa.equalsIgnoreCase("J")) {
                        pessoaJuridicaDAO.excluir(id);
                    } else {
                        System.out.println("Tipo inválido!");
                    }
                    break;
                case 4:
                    System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
                    tipoPessoa = scanner.nextLine();
                    System.out.println("Digite o ID:");
                    id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (tipoPessoa.equalsIgnoreCase("F")) {
                        PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);
                        if (pessoaFisica != null) {
                            pessoaFisica.exibir();
                        } else {
                            System.out.println("Pessoa não encontrada.");
                        }
                    } else if (tipoPessoa.equalsIgnoreCase("J")) {
                        PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);
                        if (pessoaJuridica != null) {
                            pessoaJuridica.exibir();
                        } else {
                            System.out.println("Pessoa não encontrada.");
                        }
                    } else {
                        System.out.println("Tipo inválido!");
                    }
                    break;
                case 5:
                    System.out.println("F - Pessoa Fisica | J - Pessoa Juridica");
                    tipoPessoa = scanner.nextLine();

                    if (tipoPessoa.equalsIgnoreCase("F")) {
                        List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
                        for (PessoaFisica pf : pessoasFisicas) {
                            pf.exibir();
                        }
                    } else if (tipoPessoa.equalsIgnoreCase("J")) {
                        List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
                        for (PessoaJuridica pj : pessoasJuridicas) {
                            pj.exibir();
                        }
                    } else {
                        System.out.println("Tipo inválido!");
                    }
                    break;
                case 0:
                    System.out.println("Finalizando programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static PessoaFisica lerDadosPessoaFisica(Scanner scanner) {
        System.out.println("Digite o nome:");
        String nome = scanner.nextLine();
        System.out.println("Digite o logradouro:");
        String logradouro = scanner.nextLine();
        System.out.println("Digite a cidade:");
        String cidade = scanner.nextLine();
        System.out.println("Digite o estado:");
        String estado = scanner.nextLine();
        System.out.println("Digite o telefone:");
        String telefone = scanner.nextLine();
        System.out.println("Digite o email:");
        String email = scanner.nextLine();
        System.out.println("Digite o CPF:");
        String cpf = scanner.nextLine();
        return new PessoaFisica(0, nome, logradouro, cidade, estado, telefone, email, cpf);
    }

    private static PessoaFisica lerDadosPessoaFisica(Scanner scanner, int id) {
        System.out.println("Digite o nome:");
        String nome = scanner.nextLine();
        System.out.println("Digite o logradouro:");
        String logradouro = scanner.nextLine();
        System.out.println("Digite a cidade:");
        String cidade = scanner.nextLine();
        System.out.println("Digite o estado:");
        String estado = scanner.nextLine();
        System.out.println("Digite o telefone:");
        String telefone = scanner.nextLine();
        System.out.println("Digite o email:");
        String email = scanner.nextLine();
        System.out.println("Digite o CPF:");
        String cpf = scanner.nextLine();
        return new PessoaFisica(id, nome, logradouro, cidade, estado, telefone, email, cpf);
    }

    private static PessoaJuridica lerDadosPessoaJuridica(Scanner scanner) {
        System.out.println("Digite o nome:");
        String nome = scanner.nextLine();
        System.out.println("Digite o logradouro:");
        String logradouro = scanner.nextLine();
        System.out.println("Digite a cidade:");
        String cidade = scanner.nextLine();
        System.out.println("Digite o estado:");
        String estado = scanner.nextLine();
        System.out.println("Digite o telefone:");
        String telefone = scanner.nextLine();
        System.out.println("Digite o email:");
        String email = scanner.nextLine();
        System.out.println("Digite o CNPJ:");
        String cnpj = scanner.nextLine();
        return new PessoaJuridica(0, nome, logradouro, cidade, estado, telefone, email, cnpj);
    }

    private static PessoaJuridica lerDadosPessoaJuridica(Scanner scanner, int id) {
        System.out.println("Digite o nome:");
        String nome = scanner.nextLine();
        System.out.println("Digite o logradouro:");
        String logradouro = scanner.nextLine();
        System.out.println("Digite a cidade:");
        String cidade = scanner.nextLine();
        System.out.println("Digite o estado:");
        String estado = scanner.nextLine();
        System.out.println("Digite o telefone:");
        String telefone = scanner.nextLine();
        System.out.println("Digite o email:");
        String email = scanner.nextLine();
        System.out.println("Digite o CNPJ:");
        String cnpj = scanner.nextLine();
        return new PessoaJuridica(id, nome, logradouro, cidade, estado, telefone, email, cnpj);
    }
}
