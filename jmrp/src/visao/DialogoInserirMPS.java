/*
 * DialogoInserirPLs.java
 * 
 * Created on 18 de Novembro de 2006, 16:56
 */

package visao;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

import controle.*;
import java.util.*;

/**
 * 
 * @author Tiago
 */
@SuppressWarnings("serial")
public class DialogoInserirMPS extends JDialog
{
	// Variables declaration
	private JButton btnInserir;
	private JButton btnCancelar;
	private JPanel pnlBotoes;
	private JScrollPane scrTabela;
	private JTable tblTabela;

	private ControleInsercaoDeMPS controle;
	private boolean done;
	private short instantes;

	public boolean isDone()
	{
		return done;
	}

	/** Creates new form DialogoInserirPLs */
	public DialogoInserirMPS(ControleInsercaoDeMPS controle, Dialog owner)
	{
		super(owner, true);
		this.controle = controle;
		initComponents();
		done = false;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents()
	{
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("Inserir MPS");
		setSize(650, 300);
		setResizable(false);
		setLocation(50, 50);

		GridBagConstraints gridBagConstraints;

		scrTabela = new JScrollPane();
		tblTabela = new JTable();
		pnlBotoes = new JPanel();
		btnInserir = new JButton();
		btnCancelar = new JButton();

		btnInserir.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					handlerBtnInserir(evt);
				}
			});

		btnCancelar.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					handlerBtnCancelar(evt);
				}
			});
		this.addWindowListener(new WindowAdapter()
			{
				public void windowClosing(WindowEvent arg0)
				{
					int escolha = JOptionPane.showConfirmDialog(null,
							"Tem certeza de que deseja cancelar?", "Confima��o",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (escolha == 0) dispose();
				}
			});

		// Tabela
		ArrayList<ArrayList<Object>> itens = controle.getMateriais();

		ArrayList<String> cabecalho = new ArrayList<String>();
		cabecalho.add("Material");
		short tempoInicial = controle.getTempoInicial();
		short tempoFinal = controle.getTempoFinal();
		instantes = (short) (tempoFinal - tempoInicial + 1);

		for (short i = tempoInicial; i <= tempoFinal; i++)
			cabecalho.add(Short.toString(i));
		String[] cabecalhoArray = cabecalho.toArray(new String[] {});

		TableModel mdlTabela = new JMRPTableModel(cabecalhoArray, itens, true);

		tblTabela.setModel(mdlTabela);
		tblTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblTabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrTabela.setViewportView(tblTabela);
		redimensionarTabela();

		// Layout
		getContentPane().setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.ipadx = 500;
		gridBagConstraints.ipady = 200;
		getContentPane().add(scrTabela, gridBagConstraints);

		pnlBotoes.setLayout(new GridLayout(2, 1));

		btnInserir.setText("Inserir");
		pnlBotoes.add(btnInserir);

		btnCancelar.setText("Cancelar");
		pnlBotoes.add(btnCancelar);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.NORTH;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		getContentPane().add(pnlBotoes, gridBagConstraints);
	}

	// redimensionar tabela
	private void redimensionarTabela()
	{
		TableColumn column = null;
		int larguraLote = 400 / instantes;

		for (int i = 0; i <= instantes; i++)
		{
			column = tblTabela.getColumnModel().getColumn(i);
			switch (i)
			{
				case 0:
					column.setPreferredWidth(119);
					break;
				default:
					column.setPreferredWidth(larguraLote);
					break;
			}
		}
	}

	// inserir
	private void handlerBtnInserir(ActionEvent evt)
	{
		int escolha = JOptionPane.showConfirmDialog(null,
				"Confirma inser��o deste MPS?", "Confima��o",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (escolha == 0)
		{
			JMRPTableModel mdlTabela = (JMRPTableModel) tblTabela.getModel();
			ArrayList<ArrayList<Object>> lista = mdlTabela.getData();
			controle.inserir(lista);

			done = true;
			dispose();
		}
	}

	// cancelar
	private void handlerBtnCancelar(ActionEvent evt)
	{
		int escolha = JOptionPane.showConfirmDialog(null,
				"Tem certeza de que deseja cancelar?", "Confima��o",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (escolha == 0) dispose();
	}
}
