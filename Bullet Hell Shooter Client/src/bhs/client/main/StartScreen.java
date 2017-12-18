package bhs.client.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import protocol.Message;

public class StartScreen extends javax.swing.JFrame {

	public StartScreen() {
		initComponents();
	}

	private void initComponents() {

		jPanel2 = new PanelWithImage("/data/background_sun.jpg", "middle");
		jPanel1 = new javax.swing.JPanel();
		jTextField1 = new javax.swing.JTextField();
		jButton1 = new javax.swing.JButton();
		jTextField3 = new javax.swing.JTextField();
		jTextField4 = new javax.swing.JTextField();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jButton6 = new javax.swing.JButton();
		jLabel5 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jPanel1.setBackground(new java.awt.Color(0, 0, 0, 128));
		jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 255), 3));

		jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		jTextField1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField1ActionPerformed(evt);
			}
		});

		jButton1.setBackground(new java.awt.Color(0, 0, 0, 255));
		jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		jButton1.setForeground(new java.awt.Color(0, 255, 255));
		jButton1.setText("CONNECT");
		jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 255), 3));
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jTextField3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		jTextField3.setText("IP");
		jTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				jTextField3FocusGained(evt);
			}

			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextField3FocusLost(evt);
			}
		});
		jTextField3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField3ActionPerformed(evt);
			}
		});

		jTextField4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		jTextField4.setText("50000");
		jTextField4.setPreferredSize(new java.awt.Dimension(110, 20));
		jTextField4.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				jTextField4FocusGained(evt);
			}

			public void focusLost(java.awt.event.FocusEvent evt) {
				jTextField4FocusLost(evt);
			}
		});
		jTextField4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField4ActionPerformed(evt);
			}
		});

		jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		jLabel7.setForeground(new java.awt.Color(255, 51, 255));
		jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel7.setText("CHOOSE A NICKNAME");

		jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		jLabel8.setForeground(new java.awt.Color(0, 255, 255));
		jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel8.setText("ENTER SERVER ADDRESS");

		jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(0, 255, 255));
		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel2.setText("PORT NUMBER IS 50000");

		jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		jLabel3.setForeground(new java.awt.Color(0, 255, 255));
		jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel3.setText("UNLESS SPECIFIED OTHERWISE");

		jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		jLabel4.setForeground(new java.awt.Color(255, 153, 51));
		jLabel4.setBackground(new java.awt.Color(0, 0, 0));
		jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel4.setText(" ");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
										.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 161,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 172,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(40, 40, 40)))
								.addContainerGap())))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 172,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(53, 53, 53)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jLabel7)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel2)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel3)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel4)
						.addGap(11, 11, 11)
						.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jButton6.setBackground(new java.awt.Color(0, 0, 0));
		jButton6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		jButton6.setForeground(new java.awt.Color(255, 51, 255));
		jButton6.setText("EXIT GAME");
		jButton6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 255), 3));
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton6ActionPerformed(evt);
			}
		});

		jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
		jLabel5.setForeground(new java.awt.Color(255, 51, 255));
		jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel5.setText("BULLET HELL SHOOTER");
		jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 255), 3));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
						.addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
								.addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING,
										javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton6,
								javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jPanel2,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));

		pack();
		setLocationRelativeTo(null);
	}// </editor-fold>

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		if (jTextField1.getText().isEmpty()) {
			jLabel4.setText("YOU MUST ENTER A NICKNAME");
		} else if (jTextField3.getText().isEmpty()) {
			jLabel4.setText("A SERVER IP ADDRESS IS REQUIRED");
		} else if (jTextField3.getText().isEmpty()) {
			jLabel4.setText("A SERVER PORT NUMBER IS REQUIRED");
		} else {
			Socket socket = null;
			ObjectOutputStream oos = null;
			ObjectInputStream ois = null;
			Message outgoingMessage;
			Message incomingMessage = null;
			String serverResponse = null;
			try {
				socket = new Socket(jTextField3.getText(), Integer.parseInt(jTextField4.getText()));

				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());

				outgoingMessage = new Message(null, jTextField1.getText());
				oos.writeObject(outgoingMessage);

				try {
					incomingMessage = (Message) ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				serverResponse = (String) incomingMessage.getData();

				if (serverResponse.equals("duplicate")) {
					jLabel4.setText("CHOOSE ANOTHER NICKNAME");
					System.out.println("Duplicate nickname. Choose another nickname.");
				} else if (serverResponse.equals("accepted")) {
					Lobby lobby = new Lobby(socket, oos, ois, jTextField1.getText(), jTextField3.getText());
					lobby.setVisible(true);
					this.dispose();
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				jLabel4.setText("WRONG SERVER PORT");
			} catch (UnknownHostException e) {
				/* error message: address of server is not correct */
				jLabel4.setText("WRONG IP ADDRESS");
			} catch (ConnectException e) {
				jLabel4.setText("SERVER REFUSED CONNECTION");
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void jTextField3FocusGained(java.awt.event.FocusEvent evt) {
		if (jTextField3.getText().equals("IP")) {
			jTextField3.setText("");
		}
	}

	private void jTextField3FocusLost(java.awt.event.FocusEvent evt) {
		if (jTextField3.getText().equals("")) {
			jTextField3.setText("IP");
		}
	}

	private void jTextField4FocusLost(java.awt.event.FocusEvent evt) {
		if (jTextField4.getText().equals("")) {
			jTextField4.setText("PORT");
		}
	}

	private void jTextField4FocusGained(java.awt.event.FocusEvent evt) {
		if (jTextField4.getText().equals("PORT")) {
			jTextField4.setText("");
		}
	}

	private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(1);
	}

	private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
		jButton1.doClick();
	}

	private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {
		jButton1.doClick();
	}

	private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {
		jButton1.doClick();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */

	// Variables declaration - do not modify
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton6;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField3;
	private javax.swing.JTextField jTextField4;
	// End of variables declaration
}
