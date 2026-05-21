import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModernCalculator extends JFrame implements ActionListener {

    private JTextField display;

    private double num1 = 0;
    private double num2 = 0;

    private char operator;

    private boolean operatorPressed = false;

    public ModernCalculator() {

        setTitle("Modern Calculator");

        setSize(380, 600);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setResizable(false);

        // Painel principal
        JPanel mainPanel = new JPanel();

        mainPanel.setBackground(new Color(12, 12, 12));

        mainPanel.setLayout(new BorderLayout(20, 20));

        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Display
        display = new JTextField();

        display.setEditable(false);

        display.setBackground(new Color(18, 18, 18));

        display.setForeground(Color.WHITE);

        display.setFont(new Font("Segoe UI", Font.BOLD, 40));

        display.setHorizontalAlignment(SwingConstants.RIGHT);

        display.setBorder(
                BorderFactory.createEmptyBorder(25, 20, 25, 20)
        );

        display.setCaretColor(Color.WHITE);

        display.setPreferredSize(new Dimension(0, 110));

        mainPanel.add(display, BorderLayout.NORTH);

        // Painel de botões
        JPanel buttonsPanel = new JPanel();

        buttonsPanel.setLayout(new GridLayout(5, 4, 15, 15));

        buttonsPanel.setBackground(new Color(12, 12, 12));

        // Linha 1
        addButton(buttonsPanel, createButton("C", new Color(220, 60, 60)));

        addButton(buttonsPanel, createButton("⌫", new Color(40, 40, 40)));

        addButton(buttonsPanel, createButton("%", new Color(40, 40, 40)));

        addButton(buttonsPanel, createButton("÷", new Color(255, 149, 0)));

        // Linha 2
        addButton(buttonsPanel, createButton("7", new Color(32, 32, 32)));

        addButton(buttonsPanel, createButton("8", new Color(32, 32, 32)));

        addButton(buttonsPanel, createButton("9", new Color(32, 32, 32)));

        addButton(buttonsPanel, createButton("×", new Color(255, 149, 0)));

        // Linha 3
        addButton(buttonsPanel, createButton("4", new Color(32, 32, 32)));

        addButton(buttonsPanel, createButton("5", new Color(32, 32, 32)));

        addButton(buttonsPanel, createButton("6", new Color(32, 32, 32)));

        addButton(buttonsPanel, createButton("-", new Color(255, 149, 0)));

        // Linha 4
        addButton(buttonsPanel, createButton("1", new Color(32, 32, 32)));

        addButton(buttonsPanel, createButton("2", new Color(32, 32, 32)));

        addButton(buttonsPanel, createButton("3", new Color(32, 32, 32)));

        addButton(buttonsPanel, createButton("+", new Color(255, 149, 0)));

        // Linha 5
        addButton(buttonsPanel, createButton("0", new Color(32, 32, 32)));

        addButton(buttonsPanel, createButton(".", new Color(32, 32, 32)));

        addButton(buttonsPanel, createButton("=", new Color(0, 122, 255)));

        JPanel empty = new JPanel();

        empty.setBackground(new Color(12, 12, 12));

        buttonsPanel.add(empty);

        mainPanel.add(buttonsPanel, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }

    private void addButton(JPanel panel, JButton button) {

        panel.add(button);
    }

    private JButton createButton(String text, Color color) {

        RoundedButton button = new RoundedButton(text, color);

        button.addActionListener(this);

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        // Números
        if ("0123456789.".contains(command)) {

            display.setText(display.getText() + command);
        }

        // Limpar
        if (command.equals("C")) {

            display.setText("");

            num1 = 0;

            num2 = 0;

            operatorPressed = false;
        }

        // Backspace
        if (command.equals("⌫")) {

            String current = display.getText();

            if (!current.isEmpty()) {

                display.setText(
                        current.substring(0, current.length() - 1)
                );
            }
        }

        // Porcentagem
        if (command.equals("%")) {

            try {

                double value = Double.parseDouble(display.getText());

                value = value / 100;

                display.setText(removeZero(value));

            } catch (Exception ignored) {
            }
        }

        // Operações
        if ("+-×÷".contains(command) && !operatorPressed) {

            try {

                num1 = Double.parseDouble(display.getText());

                operator = command.charAt(0);

                display.setText(
                        display.getText() + " " + command + " "
                );

                operatorPressed = true;

            } catch (Exception ignored) {
            }
        }

        // Resultado
        if (command.equals("=")) {

            try {

                String text = display.getText();

                String[] parts;

                if (text.contains("+")) {

                    parts = text.split("\\+");

                } else if (text.contains("-")) {

                    parts = text.split("-");

                } else if (text.contains("×")) {

                    parts = text.split("×");

                } else {

                    parts = text.split("÷");
                }

                num2 = Double.parseDouble(parts[1].trim());

                double result = 0;

                switch (operator) {

                    case '+':

                        result = num1 + num2;
                        break;

                    case '-':

                        result = num1 - num2;
                        break;

                    case '×':

                        result = num1 * num2;
                        break;

                    case '÷':

                        result = num1 / num2;
                        break;
                }

                display.setText(removeZero(result));

                operatorPressed = false;

            } catch (Exception ex) {

                display.setText("Error");
            }
        }
    }

    private String removeZero(double value) {

        if (value == (long) value) {

            return String.valueOf((long) value);
        }

        return String.valueOf(value);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(ModernCalculator::new);
    }
}

// Botão customizado
class RoundedButton extends JButton {

    private final Color baseColor;

    public RoundedButton(String text, Color color) {

        super(text);

        this.baseColor = color;

        setFont(new Font("Segoe UI", Font.BOLD, 26));

        setForeground(Color.WHITE);

        setFocusPainted(false);

        setBorderPainted(false);

        setContentAreaFilled(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setOpaque(false);

        setBackground(baseColor);

        // Hover
        addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {

                setBackground(baseColor.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {

                setBackground(baseColor);
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(80, 80);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Glow suave
        g2.setColor(new Color(255, 255, 255, 15));

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                28,
                28
        );

        // Sombra
        g2.setColor(new Color(0, 0, 0, 120));

        g2.fillRoundRect(
                5,
                5,
                getWidth() - 5,
                getHeight() - 5,
                28,
                28
        );

        // Gradiente
        GradientPaint gp = new GradientPaint(
                0,
                0,
                getBackground().brighter(),
                0,
                getHeight(),
                getBackground().darker()
        );

        g2.setPaint(gp);

        g2.fillRoundRect(
                0,
                0,
                getWidth() - 5,
                getHeight() - 5,
                28,
                28
        );

        // Texto
        FontMetrics fm = g2.getFontMetrics();

        Rectangle r = new Rectangle(
                0,
                0,
                getWidth() - 5,
                getHeight() - 5
        );

        String text = getText();

        int x = (r.width - fm.stringWidth(text)) / 2;

        int y = (r.height - fm.getHeight()) / 2 + fm.getAscent();

        g2.setColor(getForeground());

        g2.setFont(getFont());

        g2.drawString(text, x, y);

        g2.dispose();
    }
}