package com.app.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Cliente extends JFrame {

	// private static final LayoutManager new FlowLayout() = null;
	private JButton btnConectar = new JButton("Conectar");
	private JButton btnDesconectar = new JButton("Desconectar");
	private JTextArea txtEndereco = new JTextArea();
	private JTextArea txtLog = new JTextArea();
	private JMenuBar menu = new JMenuBar();
	private JMenu menuFile = new JMenu("Principal");
	private JMenuItem menuFileExit = new JMenuItem("Sair");

	private byte[] buffer;
	private Socket socket;
	private DataOutputStream writer;

	public Cliente() {

		setTitle("Client Side");
		setSize(new Dimension(450, 250)); // Define o tamanho da janela
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Definições dos botões e txts areas
		txtEndereco.setPreferredSize(new Dimension(20, 30));
		txtEndereco.setBackground(new Color(44, 62, 80));
		txtEndereco.setForeground(Color.white);
		txtEndereco.setFont(new Font("Gulim", Font.PLAIN, 20));

		txtLog.setPreferredSize(new Dimension(447, 248));
		txtLog.setLineWrap(true); // Define que neste text area haverá quebra de linha
		txtLog.setBackground(new Color(52, 73, 94));
		txtLog.setForeground(Color.white);
		txtLog.setFont(new Font("Gulim", Font.PLAIN, 15));

		add(btnConectar, BorderLayout.LINE_START);
		add(txtEndereco, BorderLayout.PAGE_START);
		add(txtLog, BorderLayout.CENTER);
		add(btnDesconectar, BorderLayout.LINE_END);

		menuFile.add(menuFileExit);
		menu.add(menuFile);
		setJMenuBar(menu);

		addActions();
		setVisible(true);
	}

	public void addActions() {

		// Definindo as ações dos botoẽs
		menuFileExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btnConectar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String addr = txtEndereco.getText();
				try {

					buffer = new byte[8192];
					socket = new Socket(addr, 5678);
					writer = new DataOutputStream(socket.getOutputStream());

					int cont = 0;
					while (cont < 5) {
						new Thread() {
							@Override
							public void run() {
								try {
									while(true) {
										txtLog.append("Iniciando thread " + "\n");
										writer.write(buffer);
									}
								} catch (IOException e) {
									JOptionPane.showMessageDialog(null, "Falha no processo");
								}
							}

						}.start();
						cont++;
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erro!");
				}

			}
		});

		btnDesconectar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					socket.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Não foi possível paralizar o serviços");
				}
			}
		});

	}

	public static void main(String[] args) {
		new Cliente();
	}

}
