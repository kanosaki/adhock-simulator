package adsim.visualize;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import adsim.core.Simulator;

public class SimulatorFrame extends JFrame {

    private JPanel _contentPane;
    private Simulator model;

    /**
     * Create the frame.
     */
    public SimulatorFrame(Simulator model) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        _contentPane = new JPanel();
        _contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        _contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(_contentPane);
        
        SessionView sessionView = new SessionView(model.getCurrentSession());
        _contentPane.add(sessionView, BorderLayout.CENTER);
    }

}
