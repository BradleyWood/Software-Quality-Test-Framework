package org.sqtf;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.io.IOException;

final class TestCaseCellRenderer implements TreeCellRenderer {

    private JLabel label;
    private ImageIcon pass;
    private ImageIcon fail;
    private ImageIcon inProgress;

    TestCaseCellRenderer() {
        label = new JLabel();
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            pass = new ImageIcon(ImageIO.read(cl.getResourceAsStream("checkmark.png")),
                    "Test Passed");
            fail = new ImageIcon(ImageIO.read(cl.getResourceAsStream("fail.png")),
                    "Test Failed");
            inProgress = new ImageIcon(ImageIO.read(cl.getResourceAsStream("progress.gif")),
                    "Waiting");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean selected,
                                                  final boolean expanded, final boolean leaf, final int row,
                                                  final boolean hasFocus) {
        DefaultMutableTreeNode node = ((DefaultMutableTreeNode) value);
        Object o = node.getUserObject();
        if (o instanceof TestCase) {
            TestCase tc = (TestCase) o;
            label.setText(tc.getName() == null ? tc.getOwner() : tc.getName());

            switch (tc.getTestStatus()) {
                case PASSED:
                    label.setIcon(pass);
                    break;
                case FAILED:
                    label.setIcon(fail);
                    break;
                case WAITING:
                    label.setIcon(inProgress);
                    break;
            }
            return label;
        }
        label.setIcon(null);
        label.setText(value.toString());
        return label;
    }
}
