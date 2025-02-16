import com.sun.jna.Library;
import com.sun.jna.Native;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.IOException;

public class Main {

    // Interface que representa a DLL, usando JNA
    public interface ImpressoraDLL extends Library {
        ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                "C:/Users/Thainara_branco/Downloads/Projeto_Final_arquivos_anexados_25_de_novembro_de_2024_1937/Projeto_Java/E1_Impressora01.dll", ImpressoraDLL.class); // Caminho completo para a DLL

        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);

        int FechaConexaoImpressora();

        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);

        int Corte(int avanco);

        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);

        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);

        int AvancaPapel(int linhas);

        int StatusImpressora(int param);

        int AbreGavetaElgin();

        int AbreGaveta(int pino, int ti, int tf);

        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);

        int ModoPagina();

        int LimpaBufferModoPagina();

        int ImprimeModoPagina();

        int ModoPadrao();

        int PosicaoImpressaoHorizontal(int posicao);

        int PosicaoImpressaoVertical(int posicao);

        int ImprimeXMLSAT(String dados, int param);

        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    }

    private static boolean conexaoAberta = false;
    private static int tipo;
    private static String modelo;
    private static String conexao;
    private static int parametro;
    private static final Scanner scanner = new Scanner(System.in); // Scanner estático e final

    // Função para capturar entradas do usuário
    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    // Função para configurar conexão
    public static void configurarConexao() {
        if (!conexaoAberta) {
            System.out.println("Digite o tipo de conexao (1 para USB, 2 para Serial, etc.): ");
            tipo = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Digite o modelo da impressora: ");
            modelo = scanner.nextLine();

            System.out.println("Digite a porta de conexão (ex: USB): ");
            conexao = scanner.nextLine();

            System.out.println("Digite o parâmetro adicional (ex: 0 para padrão): ");
            parametro = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Parâmetros de conexão configurados com sucesso.");
        } else {
            System.out.println("Conexão já configurada. Pronta para uso.");
        }
    }

    public static void abrirConexao() {
        // Função para abrir a conexão com a impressora
        if (!conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso.");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Conexão já está aberta.");
        }
    }

    public static void fecharConexao() {
        if (conexaoAberta) {
            ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
            conexaoAberta = false;
            System.out.println("Conexão fechada.");
        } else {
            System.out.println("Nenhuma conexão aberta para fechar.");
        }
    }

    public static void exibirMenu() {
        System.out.println("\n*************************************************");
        System.out.println("**************** MENU IMPRESSORA *******************");
        System.out.println("*************************************************\n");

        System.out.println("1  - Configurar Conexao");
        System.out.println("2  - Abrir Conexao");
        System.out.println("3  - Impressao Texto");
        System.out.println("4  - Impressao QRCode");
        System.out.println("5  - Impressao Cod Barras");
        System.out.println("6  - Impressao XML SAT");
        System.out.println("7  - Impressao XML Canc SAT");
        System.out.println("8  - Abrir Gaveta Elgin");
        System.out.println("9  - Abrir Gaveta");
        System.out.println("10 - Sinal Sonoro");
        System.out.println("0  - Fechar Conexao e Sair");
        System.out.println("--------------------------------------");
    }

    public static void ImpressaoTexto() {
        if (conexaoAberta) {
            ImpressoraDLL.INSTANCE.ImpressaoTexto("Teste de impressao", 1, 4, 0);
            ImpressoraDLL.INSTANCE.Corte(5);
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }
        ImpressoraDLL.INSTANCE.AvancaPapel(10);
    }

    public static void ImpressaoQRCode() {
        if (conexaoAberta) {
            System.out.println("Cole o link do site aqui");
            String dadosQRCode = scanner.nextLine();
            int tamanho = 6;
            int nivelCorrecao = 4;


            int resultado = ImpressoraDLL.INSTANCE.ImpressaoQRCode(dadosQRCode, tamanho, nivelCorrecao);
            ImpressoraDLL.INSTANCE.Corte(5);
            if (resultado == 0) {
                System.out.println("QR Code impresso com sucesso.");
            } else {
                System.out.println("Erro ao imprimir o QR Code. Código de erro: " + resultado);
            }
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }
        ImpressoraDLL.INSTANCE.AvancaPapel(10);
    }
    public static void ImpressaoCodigoBarras() {
        if (conexaoAberta) {
            int tipo = 8;  // Code 128
            System.out.println("Digite os numeros do codigo de barras: ");
            String dados = "{A012345678912";
            int altura = 100;
            int largura = 2;
            int HRI = 3;



            int resultado = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(tipo, dados, altura, largura, HRI);
            ImpressoraDLL.INSTANCE.Corte(5);
            if (resultado == 0) {
                System.out.println("Código de barras impresso com sucesso.");
            } else {
                System.out.println("Erro ao imprimir o código de barras. Código de erro: " + resultado);
            }
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }
        ImpressoraDLL.INSTANCE.AvancaPapel(10);
    }

    public static void ImprimeXMLSAT() {
        // Correctly assign file path to 'dados'
        String filePath = "Caminho_onde esta o arquivo XMLSAT";

        // Read XML file content into 'dados' string
        String dados = "";
        try {
            dados = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.out.println("Erro: Não foi possível ler o arquivo XML. " + e.getMessage());
            return;
        }

        if (dados == null || dados.isEmpty()) {
            System.out.println("Erro: O XML fornecido está vazio.");
            return;
        }

        System.out.println("XML recebido: " + dados);

        int param = 0;

        // Assuming ImpressoraDLL.INSTANCE.Corte is a valid method for cutting the paper

        // Try to print the XML
        int ImprimeXMLSAT = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(dados, param);

        ImpressoraDLL.INSTANCE.Corte(10);


        if (ImprimeXMLSAT == 0) {
            System.out.println("XML SAT impresso com sucesso.");
        } else {
            System.out.println("Erro: " + ImprimeXMLSAT);
        }
        ImpressoraDLL.INSTANCE.AvancaPapel(10);
    }

    public static void AvancarPapel() {
        if (conexaoAberta) {
            System.out.print("Digite o número de linhas para avançar o papel: ");
            int linhas = scanner.nextInt();
            scanner.nextLine();

            int resultado = ImpressoraDLL.INSTANCE.AvancaPapel(linhas);

            if (resultado == 0) {
                System.out.println("Papel avançado com sucesso em " + linhas + " linhas.");
            } else {
                System.out.println("Erro ao avançar papel. Código de erro: " + resultado);
            }
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }

    }
    public static void ImprimeXMLCancelamentoSAT() {
        if (conexaoAberta) {
            System.out.println("Digite o XML de cancelamento do SAT:");

            // Correct file path for the XML
            String filePath = "C:/Users/thainara_branco/Downloads/Projeto_Final_arquivos_anexados_25_de_novembro_de_2024_1937/Projeto_Java/CANC_SAT.xml";

            // Reading the XML content into the 'dados' variable
            String dados = "";
            try {
                dados = new String(Files.readAllBytes(Paths.get(filePath)));
            } catch (IOException e) {
                System.out.println("Erro: Não foi possível ler o arquivo XML de cancelamento. " + e.getMessage());
                e.printStackTrace();
                return;  // Exit the method in case of error
            }

            if (dados.isEmpty()) {
                System.out.println("Erro: O XML de cancelamento está vazio.");
                return;
            }

            System.out.println("Digite a assinatura do QR Code:");
            String assQRCode = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";

            System.out.println("Digite o parâmetro adicional (ex: 0 para padrão):");
            int param = scanner.nextInt();  // Read the integer parameter
            scanner.nextLine();  // Clear the buffer

            // Calling the method to print the XML cancellation with the signature
            int resultado = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(dados, assQRCode, param);

            ImpressoraDLL.INSTANCE.Corte(10);


            if (resultado == 0) {
                System.out.println("Cancelamento SAT impresso com sucesso.");
            } else {
                System.out.println("Erro ao imprimir o cancelamento SAT. Código de erro: " + resultado);
            }
        } else {
            System.out.println("Erro: Conexão não está aberta.");
        }
        ImpressoraDLL.INSTANCE.AvancaPapel(10);
    }

    public static void AbreGavetaElgin() {
        if (conexaoAberta) {

            int resultado = ImpressoraDLL.INSTANCE.AbreGavetaElgin();

            if (resultado == 0) {
                System.out.println("A gaveta foi aberta com sucesso.");
            }else{
                System.out.println("A gaveta não foi aberta. Erro: " + resultado);
            }
        }
    }

    public static void AbreGaveta() {
        if (conexaoAberta) {
            System.out.println("Digite o numero do pino. Ex: 1 ou 2");
            int pino = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Digite o tempo inicial da abertura da gaveta");
            int ti = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Digite o tempo final da gaveta");
            int tf = scanner.nextInt();
            scanner.nextLine();

            int resultado = ImpressoraDLL.INSTANCE.AbreGaveta(pino, ti, tf);
            if (resultado == 0) {
                System.out.println("A gaveta foi aberta com sucesso.");
            } else{
                System.out.println("A gaveta nao foi aberta. Erro: " + resultado);
            }
        }
    }
    public static void SinalSonoro() {
        if (conexaoAberta) {
            System.out.println("Digite quantas vezes a impressora devera fazer barulho");
            int qtd = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Digite o tempo inicial do barulho");
            int tempoInicio = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Digite o tempo final do barulho");
            int tempoFim = scanner.nextInt();
            scanner.nextLine();

            int SinalSonoro = ImpressoraDLL.INSTANCE.SinalSonoro(qtd, tempoInicio, tempoFim);

            if (SinalSonoro == 0) {
                System.out.println("SinalSonoro foi feito com sucesso.");
            } else {
                System.out.println("O sinal sonoro falhou com sucesso. Erro: " + SinalSonoro);
            }
        }
    }




    public static void main(String[] args) {
        while (true) {
            exibirMenu();
            String escolha = capturarEntrada("\nDigite a opção desejada: ");

            if (escolha.equals("0")) {
                fecharConexao();
                System.out.println("Programa encerrado.");
                break;
            } else if (escolha.equals("1")) {
                configurarConexao();
            } else if (escolha.equals("2")) {
                abrirConexao();
            } else if (escolha.equals("3")) {
                ImpressaoTexto();
            } else if (escolha.equals("4")) {
                ImpressaoQRCode();

            }else if (escolha.equals("5")) {
                ImpressaoCodigoBarras();
            }else if (escolha.equals("6")) {
                ImprimeXMLSAT();
            }else if(escolha.equals("7")) {
                ImprimeXMLCancelamentoSAT();
            }else if(escolha.equals("8")) {
                AbreGavetaElgin();
            }else if (escolha.equals("9")) {
                AbreGaveta();
            }else if(escolha.equals("10")){
                SinalSonoro();
            }


            else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close(); // Fecha o scanner ao final do programa
    }
}

