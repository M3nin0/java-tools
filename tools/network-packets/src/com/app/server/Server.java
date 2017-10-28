package com.app.server;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Server extends JFrame {

	private List<DataInputStream> readeds = new Vector<>();

	private JButton setOn = new JButton("Ligar servidor");
	private JButton setOff = new JButton("Desligar servidor");

	private ServerSocket server;
	private DataInputStream reader;

	private static final int PORT = 5678;

	public void initializeServer() {
		try {
			server = new ServerSocket(PORT);
			JOptionPane.showMessageDialog(null, "Servidor iniciado com sucesso!");

			while (true) {
				Socket socket = server.accept();

				new Thread() {
					@Override
					public void run() {
						try {
							reader = new DataInputStream(socket.getInputStream());
							reader.read();
							readeds.add(reader.read(), reader);

						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Impossível alocar memória");
						}

					}
				};
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas na inicialização do servidor");
		}
	}

	public void addEvent() {
		setOn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initializeServer();
			}
		});
		setOff.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					server.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Impossível parar o serviço");
				}
			}
		});
	}

	public Server() {

		setTitle("Server side");
		setSize(360, 300);
		setLayout(new GridLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(setOn);
		add(setOff);

		addEvent();

		setVisible(true);

	}

	public static void main(String[] args) {
		new Server();
	}

}
