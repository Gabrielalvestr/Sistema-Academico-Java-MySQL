import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;

public class SistemaAcademicoGUI extends JFrame {

    // --- Componentes da Aba 1: Dados Pessoais ---
    private JTextField txtRgm, txtNome, txtEmail, txtEnd, txtMun;
    private JFormattedTextField txtDataNasc, txtCpf, txtCelular;
    private JComboBox<String> cbUf;

    // --- Componentes da Aba 2: Curso ---
    private JComboBox<String> cbCurso, cbCampus;
    private JRadioButton rbMatutino, rbVespertino, rbNoturno;
    private ButtonGroup grupoPeriodo;

    // --- Componentes da Aba 3: Notas e Faltas ---
    private JTextField txtRgmNotas, txtNomeNotas, txtCursoNotas, txtFaltas;
    private JComboBox<String> cbDisciplina, cbSemestre, cbNota;

    // --- Componentes da Aba 4: Boletim ---
    private JTextArea txtAreaBoletim;

    // --- Configuração do Banco de Dados MySQL ---
    private final String URL = "jdbc:mysql://localhost:3306/sistema_academico";
    private final String USER = "root";
    private final String PASSWORD = "1234"; // Coloque a sua senha do MySQL aqui

    public SistemaAcademicoGUI() {
        // 1. Configurações da Janela Principal
        setTitle("Sistema Académico de Cadastro");
        setSize(680, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 2. Criação da Barra de Menus Superior (igual às imagens)
        configurarMenuSuperior();

        // 3. Criação do Painel de Abas (JTabbedPane)
        JTabbedPane painelDeAbas = new JTabbedPane();

        // 4. Inicialização de cada uma das 4 abas
        painelDeAbas.addTab("Dados Pessoais", criarAbaDadosPessoais());
        painelDeAbas.addTab("Curso", criarAbaCurso());
        painelDeAbas.addTab("Notas e Faltas", criarAbaNotasFaltas());
        painelDeAbas.addTab("Boletim", criarAbaBoletim());

        add(painelDeAbas);
    }

    // --- ABA 1: DADOS PESSOAIS ---
    private JPanel criarAbaDadosPessoais() {
        JPanel painel = new JPanel(null);

        try {
            MaskFormatter mData = new MaskFormatter("##/##/####");
            MaskFormatter mCpf = new MaskFormatter("###.###.###-##");
            MaskFormatter mCelular = new MaskFormatter("(##)#####-####");

            JLabel lblRgm = new JLabel("RGM:"); lblRgm.setBounds(20, 20, 40, 25);
            txtRgm = new JTextField(); txtRgm.setBounds(60, 20, 150, 25);

            JLabel lblNome = new JLabel("Nome:"); lblNome.setBounds(230, 20, 50, 25);
            txtNome = new JTextField(); txtNome.setBounds(280, 20, 350, 25);

            JLabel lblData = new JLabel("Data de Nascimento:"); lblData.setBounds(20, 60, 130, 25);
            txtDataNasc = new JFormattedTextField(mData); txtDataNasc.setBounds(150, 60, 100, 25);

            JLabel lblCpf = new JLabel("CPF:"); lblCpf.setBounds(280, 60, 40, 25);
            txtCpf = new JFormattedTextField(mCpf); txtCpf.setBounds(320, 60, 150, 25);

            JLabel lblEmail = new JLabel("Email:"); lblEmail.setBounds(20, 100, 50, 25);
            txtEmail = new JTextField(); txtEmail.setBounds(60, 100, 570, 25);

            JLabel lblEnd = new JLabel("End.:"); lblEnd.setBounds(20, 140, 50, 25);
            txtEnd = new JTextField(); txtEnd.setBounds(60, 140, 570, 25);

            JLabel lblMun = new JLabel("Município:"); lblMun.setBounds(20, 180, 70, 25);
            txtMun = new JTextField(); txtMun.setBounds(90, 180, 150, 25);

            JLabel lblUf = new JLabel("UF:"); lblUf.setBounds(260, 180, 30, 25);
            cbUf = new JComboBox<>(new String[]{"SP", "RJ", "MG", "ES", "PR", "SC", "RS"});
            cbUf.setBounds(290, 180, 60, 25);

            JLabel lblCelular = new JLabel("Celular:"); lblCelular.setBounds(370, 180, 50, 25);
            txtCelular = new JFormattedTextField(mCelular); txtCelular.setBounds(420, 180, 210, 25);

            painel.add(lblRgm); painel.add(txtRgm); painel.add(lblNome); painel.add(txtNome);
            painel.add(lblData); painel.add(txtDataNasc); painel.add(lblCpf); painel.add(txtCpf);
            painel.add(lblEmail); painel.add(txtEmail); painel.add(lblEnd); painel.add(txtEnd);
            painel.add(lblMun); painel.add(txtMun); painel.add(lblUf); painel.add(cbUf);
            painel.add(lblCelular); painel.add(txtCelular);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return painel;
    }

    // --- ABA 2: CURSO ---
    private JPanel criarAbaCurso() {
        JPanel painel = new JPanel(null);

        JLabel lblCurso = new JLabel("Curso:"); lblCurso.setBounds(20, 30, 50, 25);
        cbCurso = new JComboBox<>(new String[]{"Analise e Desenvolvimento de Sistemas", "Ciência da Computação", "Engenharia de Software", "Sistemas de Informação"});
        cbCurso.setBounds(80, 30, 450, 25);

        JLabel lblCampus = new JLabel("Campus:"); lblCampus.setBounds(20, 80, 60, 25);
        cbCampus = new JComboBox<>(new String[]{"Tatuapé", "Itaquera", "República", "Paulista", "Santo Amaro"});
        cbCampus.setBounds(80, 80, 450, 25);

        JLabel lblPeriodo = new JLabel("Período:"); lblPeriodo.setBounds(20, 130, 60, 25);
        rbMatutino = new JRadioButton("Matutino"); rbMatutino.setBounds(90, 130, 90, 25);
        rbVespertino = new JRadioButton("Vespertino"); rbVespertino.setBounds(190, 130, 100, 25);
        rbNoturno = new JRadioButton("Noturno"); rbNoturno.setBounds(300, 130, 90, 25);
        rbNoturno.setSelected(true);

        grupoPeriodo = new ButtonGroup();
        grupoPeriodo.add(rbMatutino); grupoPeriodo.add(rbVespertino); grupoPeriodo.add(rbNoturno);

        painel.add(lblCurso); painel.add(cbCurso); painel.add(lblCampus); painel.add(cbCampus);
        painel.add(lblPeriodo); painel.add(rbMatutino); painel.add(rbVespertino); painel.add(rbNoturno);

        return painel;
    }

    // --- ABA 3: NOTAS E FALTAS ---
    private JPanel criarAbaNotasFaltas() {
        JPanel painel = new JPanel(null);

        JLabel lblRgm = new JLabel("RGM:"); lblRgm.setBounds(20, 20, 40, 25);
        txtRgmNotas = new JTextField(); txtRgmNotas.setBounds(60, 20, 150, 25);

        
        txtNomeNotas = new JTextField("deverá mostrar o nome do aluno");
        txtNomeNotas.setBounds(230, 20, 400, 25);
        txtNomeNotas.setEditable(false);

        txtCursoNotas = new JTextField("deverá mostrar o curso do aluno");
        txtCursoNotas.setBounds(20, 60, 610, 25);
        txtCursoNotas.setEditable(false);

        JLabel lblDisc = new JLabel("Disciplina:"); lblDisc.setBounds(20, 100, 70, 25);
        cbDisciplina = new JComboBox<>(new String[]{"Programação Orientada a Objetos", "Estrutura de Dados", "Banco de Dados", "Engenharia de Requisitos"});
        cbDisciplina.setBounds(95, 100, 535, 25);

        JLabel lblSemestre = new JLabel("Semestre:"); lblSemestre.setBounds(20, 140, 70, 25);
        cbSemestre = new JComboBox<>(new String[]{"2026-1", "2026-2", "2027-1", "2027-2"});
        cbSemestre.setBounds(95, 140, 100, 25);

        JLabel lblNota = new JLabel("Nota:"); lblNota.setBounds(210, 140, 40, 25);
        cbNota = new JComboBox<>(new String[]{"0,0", "1,0", "2,5", "5,0", "7,5", "10,0"});
        cbNota.setBounds(250, 140, 80, 25);

        JLabel lblFaltas = new JLabel("Faltas:"); lblFaltas.setBounds(350, 140, 50, 25);
        txtFaltas = new JTextField(); txtFaltas.setBounds(400, 140, 100, 25);

        painel.add(lblRgm); painel.add(txtRgmNotas); painel.add(txtNomeNotas); painel.add(txtCursoNotas);
        painel.add(lblDisc); painel.add(cbDisciplina); painel.add(lblSemestre); painel.add(cbSemestre);
        painel.add(lblNota); painel.add(cbNota); painel.add(lblFaltas); painel.add(txtFaltas);

        return painel;
    }

    // --- ABA 4: BOLETIM (Criatividade visual solicitada) ---
    private JPanel criarAbaBoletim() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("=== Histórico Académico Consolido ===", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        txtAreaBoletim = new JTextArea("Consulte um aluno utilizando o menu superior para gerar o Boletim.");
        txtAreaBoletim.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtAreaBoletim.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtAreaBoletim);

        painel.add(lblTitulo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    // --- CONFIGURAÇÃO DO MENU SUPERIOR (Ações CRUD) ---
    private void configurarMenuSuperior() {
        JMenuBar barraMenu = new JMenuBar();

        JMenu menuAluno = new JMenu("Aluno");
        JMenuItem miSalvar = new JMenuItem("Salvar");
        JMenuItem miAlterar = new JMenuItem("Alterar");
        JMenuItem miConsultar = new JMenuItem("Consultar");
        JMenuItem miExcluir = new JMenuItem("Excluir");
        JMenuItem miSair = new JMenuItem("Sair");

        menuAluno.add(miSalvar); menuAluno.add(miAlterar); menuAluno.add(miConsultar);
        menuAluno.add(miExcluir); menuAluno.addSeparator(); menuAluno.add(miSair);

        JMenu menuNotas = new JMenu("Notas e Faltas");
        JMenuItem miSalvarNota = new JMenuItem("Salvar Nota");
        menuNotas.add(miSalvarNota);

        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem miSobre = new JMenuItem("Sobre");
        menuAjuda.add(miSobre);

        barraMenu.add(menuAluno); barraMenu.add(menuNotas); barraMenu.add(menuAjuda);
        setJMenuBar(barraMenu);

        // --- Vinculação das Funções do Banco aos Cliques do Menu ---
        miSalvar.addActionListener(e -> salvarAlunoBancodedados());
        miConsultar.addActionListener(e -> consultarAlunoBancodedados());
        miExcluir.addActionListener(e -> excluirAlunoBancodedados());
        miAlterar.addActionListener(e -> alterarAlunoBancodedados());
        miSalvarNota.addActionListener(e -> salvarNotasFaltasBancodedados());
        miSair.addActionListener(e -> System.exit(0));
        miSobre.addActionListener(e -> JOptionPane.showMessageDialog(this, "Sistema Académico v2.0\nDesenvolvedor: Gabriel Alves"));
    }

    // --- OPERAÇÕES DO BANCO DE DADOS (JDBC) ---

    private void salvarAlunoBancodedados() {
        String query = "INSERT INTO alunos VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, txtRgm.getText());
            ps.setString(2, txtNome.getText());
            ps.setString(3, txtDataNasc.getText());
            ps.setString(4, txtCpf.getText());
            ps.setString(5, txtEmail.getText());
            ps.setString(6, txtEnd.getText());
            ps.setString(7, txtMun.getText());
            ps.setString(8, cbUf.getSelectedItem().toString());
            ps.setString(9, txtCelular.getText());
            ps.setString(10, cbCurso.getSelectedItem().toString());
            ps.setString(11, cbCampus.getSelectedItem().toString());
            
            String periodo = rbMatutino.isSelected() ? "Matutino" : rbVespertino.isSelected() ? "Vespertino" : "Noturno";
            ps.setString(12, periodo);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar aluno (Verifique se o RGM já existe): " + ex.getMessage());
        }
    }

    private void consultarAlunoBancodedados() {
        String rgmParaBusca = txtRgm.getText().trim();
        if (rgmParaBusca.isEmpty()) {
            rgmParaBusca = txtRgmNotas.getText().trim();
        }

        if (rgmParaBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Insira um RGM no campo para realizar a consulta.");
            return;
        }

        String queryAluno = "SELECT * FROM alunos WHERE rgm = ?";
        String queryNotas = "SELECT * FROM notas_faltas WHERE rgm_aluno = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement psAluno = conn.prepareStatement(queryAluno);
             PreparedStatement psNotas = conn.prepareStatement(queryNotas)) {

            psAluno.setString(1, rgmParaBusca);
            ResultSet rsAluno = psAluno.executeQuery();

            if (rsAluno.next()) {
                // Preenche Aba Dados Pessoais
                txtRgm.setText(rsAluno.getString("rgm"));
                txtNome.setText(rsAluno.getString("nome"));
                txtDataNasc.setText(rsAluno.getString("data_nascimento"));
                txtCpf.setText(rsAluno.getString("cpf"));
                txtEmail.setText(rsAluno.getString("email"));
                txtEnd.setText(rsAluno.getString("endereco"));
                txtMun.setText(rsAluno.getString("municipio"));
                cbUf.setSelectedItem(rsAluno.getString("uf"));
                txtCelular.setText(rsAluno.getString("celular"));

                // Preenche Aba Curso
                cbCurso.setSelectedItem(rsAluno.getString("curso"));
                cbCampus.setSelectedItem(rsAluno.getString("campus"));
                String p = rsAluno.getString("periodo");
                if (p.equals("Matutino")) rbMatutino.setSelected(true);
                else if (p.equals("Vespertino")) rbVespertino.setSelected(true);
                else rbNoturno.setSelected(true);

                // Atualiza espelhos da Aba Notas
                txtRgmNotas.setText(rsAluno.getString("rgm"));
                txtNomeNotas.setText(rsAluno.getString("nome"));
                txtCursoNotas.setText(rsAluno.getString("curso"));

                // Constrói Aba Boletim Dinamicamente
                StringBuilder boletimText = new StringBuilder();
                boletimText.append("RGM: ").append(rsAluno.getString("rgm")).append("\n");
                boletimText.append("Aluno: ").append(rsAluno.getString("nome")).append("\n");
                boletimText.append("Curso: ").append(rsAluno.getString("curso")).append("\n");
                boletimText.append("--------------------------------------------------\n");
                boletimText.append(String.format("%-30s %-10s %-6s %-6s\n", "Disciplina", "Semestre", "Nota", "Faltas"));
                boletimText.append("--------------------------------------------------\n");

                psNotas.setString(1, rgmParaBusca);
                ResultSet rsNotas = psNotas.executeQuery();
                boolean temNotas = false;
                while (rsNotas.next()) {
                    temNotas = true;
                    boletimText.append(String.format("%-30s %-10s %-6s %-6s\n",
                            rsNotas.getString("disciplina"),
                            rsNotas.getString("semestre"),
                            rsNotas.getString("nota"),
                            rsNotas.getString("faltas")));
                }
                if (!temNotas) {
                    boletimText.append("Nenhuma nota ou falta lançada para este aluno ainda.\n");
                }

                txtAreaBoletim.setText(boletimText.toString());
                JOptionPane.showMessageDialog(this, "Consulta realizada! Dados carregados nas abas.");

            } else {
                JOptionPane.showMessageDialog(this, "Aluno não localizado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar: " + ex.getMessage());
        }
    }

    private void alterarAlunoBancodedados() {
        String query = "UPDATE alunos SET nome=?, data_nascimento=?, cpf=?, email=?, endereco=?, municipio=?, uf=?, celular=?, curso=?, campus=?, periodo=? WHERE rgm=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, txtNome.getText());
            ps.setString(2, txtDataNasc.getText());
            ps.setString(3, txtCpf.getText());
            ps.setString(4, txtEmail.getText());
            ps.setString(5, txtEnd.getText());
            ps.setString(6, txtMun.getText());
            ps.setString(7, cbUf.getSelectedItem().toString());
            ps.setString(8, txtCelular.getText());
            ps.setString(9, cbCurso.getSelectedItem().toString());
            ps.setString(10, cbCampus.getSelectedItem().toString());
            String periodo = rbMatutino.isSelected() ? "Matutino" : rbVespertino.isSelected() ? "Vespertino" : "Noturno";
            ps.setString(11, periodo);
            ps.setString(12, txtRgm.getText());

            int modificado = ps.executeUpdate();
            if (modificado > 0) JOptionPane.showMessageDialog(this, "Cadastro atualizado!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar: " + ex.getMessage());
        }
    }

    private void excluirAlunoBancodedados() {
        String query = "DELETE FROM alunos WHERE rgm = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, txtRgm.getText());
            int deletado = ps.executeUpdate();
            if (deletado > 0) {
                JOptionPane.showMessageDialog(this, "Aluno removido. Notas eliminadas em cascata!");
                // Limpa campos da tela
                txtRgm.setText(""); txtNome.setText(""); txtCpf.setText(""); txtEmail.setText("");
                txtNomeNotas.setText(""); txtCursoNotas.setText(""); txtRgmNotas.setText("");
                txtAreaBoletim.setText("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar: " + ex.getMessage());
        }
    }

    private void salvarNotasFaltasBancodedados() {
        String query = "INSERT INTO notas_faltas (rgm_aluno, disciplina, semestre, nota, faltas) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, txtRgmNotas.getText());
            ps.setString(2, cbDisciplina.getSelectedItem().toString());
            ps.setString(3, cbSemestre.getSelectedItem().toString());
            
            String notaFormatada = cbNota.getSelectedItem().toString().replace(",", ".");
            ps.setDouble(4, Double.parseDouble(notaFormatada));
            ps.setInt(5, Integer.parseInt(txtFaltas.getText()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Desempenho salvo com sucesso!");
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar notas (Certifique-se que o RGM existe e faltas é um número): " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            // Aplica o design nativo do sistema operacional para ficar limpo e moderno
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SistemaAcademicoGUI tela = new SistemaAcademicoGUI();
        tela.setVisible(true);
    }
}