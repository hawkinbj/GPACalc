/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.hawkinbj.gpacalc.model;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hawkinbj.gpacalc.controller.SystemController;

public class GUIPanel extends JPanel implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1317154632755304524L;
	protected SystemController controller;

	public GUIPanel(SystemController controller) {
		this.controller = controller;
		setMinimumSize(new Dimension(200, 200));

		setName(getClass().getSimpleName());
	}

	protected JButton createButton(String btnName, String btnTxt) {
		JButton newBtn = new JButton(btnTxt);
		newBtn.setActionCommand(btnName);

		newBtn.addMouseListener(this);
		newBtn.addActionListener(this);
		newBtn.setAlignmentX(0.0F);
		newBtn.setMaximumSize(new Dimension(200, 50));
		return newBtn;
	}

	protected JButton createButton(String btnName) {
		JButton newBtn = new JButton(btnName);
		newBtn.setActionCommand(btnName);
		newBtn.setName(btnName);
		newBtn.addMouseListener(this);
		newBtn.addActionListener(this);
		newBtn.setAlignmentX(0.0F);
		newBtn.setMaximumSize(new Dimension(200, 50));
		return newBtn;
	}

	protected void createTitledBorder(JPanel panel, String borderTxt) {
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(1), borderTxt, 1, 2));
	}

	protected JLabel createLabel(String labelName, String panelText) {
		JLabel newLabel = new JLabel(panelText);
		newLabel.setName(labelName);
		newLabel.setAlignmentX(0.0F);
		newLabel.setMaximumSize(new Dimension(200, 50));
		return newLabel;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
	}
}