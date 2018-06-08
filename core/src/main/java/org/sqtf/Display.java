package org.sqtf;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import java.awt.*;

final class Display extends JFrame {

    private JTree tree;
    private TreeModel model;
    private JProgressBar progressBar;
    private Color PASS_GREEN = new Color(10, 100, 20);
    private Color FAIL_RED = new Color(100, 0, 20);

    Display(TreeModel model) {
        super("Test Results");
        this.model = model;
    }

    void setProgressBar(int progress, boolean pass) {
        progressBar.setValue(progress);
        if (!pass) {
            progressBar.setForeground(FAIL_RED);
        }
    }

    void initComponents() {
        tree = new JTree(model);
        tree.putClientProperty("JTree.lineStyle", "None");
        tree.setCellRenderer(new TestCaseCellRenderer());

        progressBar = new JProgressBar();
        progressBar.setValue(0);

        JMenuBar toolBar = new JMenuBar();
        toolBar.add(progressBar);
        progressBar.setBackground(Color.DARK_GRAY);
        progressBar.setForeground(PASS_GREEN);

        setJMenuBar(toolBar);
        add(tree);
    }
}
