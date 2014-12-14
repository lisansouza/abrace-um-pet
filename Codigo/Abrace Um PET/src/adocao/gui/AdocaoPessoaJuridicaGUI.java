package adocao.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import usuario.gui.TelaInicialGUI;
import usuario.service.SessaoUsuario;
import adocao.dominio.Adocao;
import adocao.service.AdocaoService;
import adotante.dominio.Adotante;
import adotante.dominio.PessoaJuridica;
import adotante.service.PessoaJuridicaService;
import animal.dominio.Animal;
import animal.service.AnimalService;

public class AdocaoPessoaJuridicaGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private JPanel	contentPane;
	private JTextField textRGA;
	private JFormattedTextField jFormattedTextCnpj;
	private JLabel lblMostrarNome;
	private JLabel lblMostrarAnimal;
	private Animal animal;
	private PessoaJuridica pessoaJuridica;
	
	/**
	 * Create the frame.
	 */
	public AdocaoPessoaJuridicaGUI(){
		setTitle("Ado\u00E7\u00E3o");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 645,455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAdooPessoa = new JLabel("Ado\u00E7\u00E3o de Pessoa Juridica");
		lblAdooPessoa.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
		lblAdooPessoa.setBounds(10, 52, 190, 27);
		contentPane.add(lblAdooPessoa);
		
		JLabel lblCpf = new JLabel("CNPJ:");
		lblCpf.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		lblCpf.setBounds(39, 131, 46, 14);
		contentPane.add(lblCpf);
		
		JLabel lblRga = new JLabel("RGA:");
		lblRga.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		lblRga.setBounds(39, 244, 46, 14);
		contentPane.add(lblRga);
		
		textRGA = new JTextField();
		textRGA.setBounds(117, 242, 152, 20);
		contentPane.add(textRGA);
		textRGA.setColumns(10);
		
		JButton btnConsultarCnpj = new JButton("Consultar");
		btnConsultarCnpj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pessoaJuridica = new PessoaJuridica();
				PessoaJuridicaService pessoaJuridicaService = new PessoaJuridicaService();
				pessoaJuridica = pessoaJuridicaService.consultarPessoaJuridica(jFormattedTextCnpj.getText());
				lblMostrarNome.setText(pessoaJuridica.getPessoa().getNome());
			}
		});
		btnConsultarCnpj.setBounds(297, 128, 89, 23);
		contentPane.add(btnConsultarCnpj);
		
		JButton btnConsultarRga = new JButton("Consultar");
		btnConsultarRga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animal = new Animal();
				AnimalService animalService = new AnimalService();
				animal = animalService.consultarAnimal(textRGA.getText());
				lblMostrarAnimal.setText(animal.getNome());
			}
		});
		btnConsultarRga.setBounds(297, 241, 89, 23);
		contentPane.add(btnConsultarRga);
		
		JButton btnAdotar = new JButton("Adotar");
		btnAdotar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdocaoService adocaoService = new AdocaoService();
				Adocao adocao = new Adocao();
				Adotante adotante = new Adotante();
				adotante = adocaoService.consultarAdotante(pessoaJuridica.getPessoa());
				adocao.setAnimal(animal);
				adocao.setAdotante(adotante);
				adocaoService.adicionarAdocaoService(adocao);
				SessaoUsuario sessao = SessaoUsuario.getInstancia();
				sessao.setAdocao(adocao);
				JOptionPane.showMessageDialog(null, "Ado��o realizada com sucesso!!");
				
				TelaInicialGUI ti = new TelaInicialGUI();
				ti.setVisible(true);
				dispose();
			}
		});
		btnAdotar.setBounds(162, 382, 89, 23);
		contentPane.add(btnAdotar);
		
		JButton btnVotar = new JButton("Votar");
		btnVotar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaInicialGUI telaInicialGUI = new TelaInicialGUI();
				telaInicialGUI.setVisible(true);
				dispose();
			}
		});
		btnVotar.setBounds(297, 382, 89, 23);
		contentPane.add(btnVotar);
		
		MaskFormatter mascaraCnpj = null;
		try {
			mascaraCnpj = new MaskFormatter("##.###.###/####-##");
			mascaraCnpj.setPlaceholderCharacter('_');
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		jFormattedTextCnpj = new JFormattedTextField(mascaraCnpj);
		jFormattedTextCnpj.setBounds(117, 129, 152, 20);
		contentPane.add(jFormattedTextCnpj);
		
		lblMostrarNome = new JLabel("");
		lblMostrarNome.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		lblMostrarNome.setBounds(117, 162, 152, 27);
		contentPane.add(lblMostrarNome);
		
		lblMostrarAnimal = new JLabel("");
		lblMostrarAnimal.setFont(new Font("Microsoft YaHei", Font.BOLD, 12));
		lblMostrarAnimal.setBounds(117, 281, 152, 27);
		contentPane.add(lblMostrarAnimal);
	}
}
