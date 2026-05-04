package maquinavirtualz80;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;

public class GUI extends JFrame {

    private CPU cpu;
    
    private boolean programLoaded = false;

    // Labels
    private JLabel labelRegs, labelFlags, labelPC, labelOpcode, labelStatus;
    private JTextArea memoryArea, stackArea;

    public GUI(CPU cpu) {
        this.cpu = cpu;

        setTitle("Z80 Virtual Machine");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // =========================
        // PAINEL PRINCIPAL (FUNDO)
        // =========================
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 243, 204)); // amarelo claro
        mainPanel.setBorder(new LineBorder(Color.ORANGE, 3));

        add(mainPanel);

        // =========================
        // TOPO (REGISTRADORES + FLAGS)
        // =========================
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.setOpaque(false);

        // REGISTRADORES
        labelRegs = new JLabel();
        JPanel panelRegs = createBox("REGISTRADORES", labelRegs);

        // FLAGS
        labelFlags = new JLabel();
        JPanel panelFlags = createBox("FLAGS", labelFlags);

        topPanel.add(panelRegs);
        topPanel.add(panelFlags);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // =========================
        // CENTRO (INFO + MEMÓRIA + STACK)
        // =========================
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        centerPanel.setOpaque(false);

        // INFO CPU
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBorder(createTitledBorder("CPU"));
        infoPanel.setOpaque(false);

        labelPC = new JLabel();
        labelOpcode = new JLabel();
        labelStatus = new JLabel();

        infoPanel.add(labelPC);
        infoPanel.add(labelOpcode);
        infoPanel.add(labelStatus);

        // MEMÓRIA
        memoryArea = new JTextArea();
        memoryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane memScroll = new JScrollPane(memoryArea);
        memScroll.setBorder(createTitledBorder("MEMÓRIA"));

        // STACK
        stackArea = new JTextArea();
        stackArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane stackScroll = new JScrollPane(stackArea);
        stackScroll.setBorder(createTitledBorder("STACK"));

        centerPanel.add(infoPanel);
        centerPanel.add(memScroll);
        centerPanel.add(stackScroll);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // =========================
        // BOTÕES (embaixo)
        // =========================
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton btnStep = new JButton("STEP");
        JButton btnRun = new JButton("RUN");
        JButton btnReset = new JButton("RESET");
        JButton btnLoad = new JButton("LOAD");

        buttonPanel.add(btnStep);
        buttonPanel.add(btnRun);
        buttonPanel.add(btnReset);
        buttonPanel.add(btnLoad);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // =========================
        // AÇÕES
        // =========================

        btnStep.addActionListener(e -> {
            if (!programLoaded) return;
            
            this.cpu.step();
            updateDisplay();
        });

        btnRun.addActionListener(e -> {
            if (!programLoaded) return;
            
            new Thread(() -> {
                while (!cpu.halted) {
                    cpu.step();

                    try {
                        Thread.sleep(200); // velocidade
                    } catch (InterruptedException ex) {}

                    SwingUtilities.invokeLater(() -> updateDisplay());
                }
            }).start();
        });

        btnReset.addActionListener(e -> {
            this.cpu = new CPU();
            updateDisplay();
        });

        btnLoad.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(new File("."));
            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                System.out.println("Arquivo selecionado: " + file.getAbsolutePath());
                ProgramLoader.load(file.getAbsolutePath(), this.cpu.getMemory());
                this.cpu.getRegisters().PC = 0;
                this.cpu.halted = false;
                
                programLoaded = true;
                
                updateDisplay();
            }
        });

        updateDisplay();
        setVisible(true);
    }

    // =========================
    // CRIAR CAIXAS COM TÍTULO
    // =========================
    private JPanel createBox(String title, JLabel content) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(createTitledBorder(title));
        panel.setOpaque(false);

        content.setFont(new Font("Monospaced", Font.BOLD, 14));
        panel.add(content, BorderLayout.CENTER);

        return panel;
    }

    private TitledBorder createTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
                new LineBorder(Color.DARK_GRAY, 2), title,
                TitledBorder.CENTER, TitledBorder.TOP
        );
        return border;
    }

    // =========================
    // UPDATE DISPLAY
    // =========================
    private void updateDisplay() {
        Registers r = cpu.getRegisters();
        
        if (!programLoaded) {

            labelOpcode.setText("Opcode atual: (sem programa)");
            labelStatus.setText("STATUS: AGUARDANDO LOAD");

            labelRegs.setText(
                "<html>" +
                "A: " + hex(r.A) + "  B: " + hex(r.B) + "  C: " + hex(r.C) + "<br>" +
                "D: " + hex(r.D) + "  E: " + hex(r.E) + "<br>" +
                "H: " + hex(r.H) + "  L: " + hex(r.L) + "<br><br>" +
                "PC: " + hex16(r.PC) + "<br>" +
                "SP: " + hex16(r.SP) +
                "</html>"
            );

            return; // 👈 SAI DO MÉTODO AQUI
        } 
        // REGISTRADORES
        labelRegs.setText(
                "<html>" +
                "A: " + hex(r.A) + "  B: " + hex(r.B) + "  C: " + hex(r.C) + "<br>" +
                "D: " + hex(r.D) + "  E: " + hex(r.E) + "<br>" +
                "H: " + hex(r.H) + "  L: " + hex(r.L) + "<br><br>" +
                "PC: " + hex16(r.PC) + "<br>" +
                "SP: " + hex16(r.SP) +
                "</html>"
        );

        // FLAGS
        labelFlags.setText(
                "<html>" +
                "Z: " + r.flags.getZero() + "<br>" +
                "S: " + r.flags.getSign() + "<br>" +
                "C: " + r.flags.getCarry() + "<br>" +
                "H: " + r.flags.getHalfCarry() + "<br>" +
                "PV: " + r.flags.getParityOverflow() + "<br>" +
                "N: " + r.flags.getSubtraction() +
                "</html>"
        );

        // CPU INFO
        labelPC.setText("PC: " + hex16(r.PC));

        if (r.PC >= 0 && r.PC < 65536) {
            int opcode = cpu.getMemory().read(r.PC);
            labelOpcode.setText("Opcode atual: " + hex(opcode));
        } else {
            labelOpcode.setText("Opcode atual: --");
        }

        labelStatus.setText("STATUS: " + (cpu.halted ? "HALTED" : "IDLE"));

        // MEMÓRIA (20 posições)
        StringBuilder mem = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            if (i >= 0 && i < 6553) {
                mem.append(String.format("%04X: %02X\n", i, cpu.getMemory().read(i)));
            }
        }
        memoryArea.setText(mem.toString());

        // STACK (10 posições a partir do SP)
        StringBuilder stack = new StringBuilder();
        int sp = r.SP;
        for (int i = 0; i < 10; i++) {
            int addr = sp + i;
            if(addr >= 0 && addr < 65536) {
            stack.append(String.format("%04X: %02X\n", addr, cpu.getMemory().read(addr)));
            }
        }
        stackArea.setText(stack.toString());
    }

    private String hex(int value) {
        return String.format("%02X", value & 0xFF);
    }

    private String hex16(int value) {
        return String.format("%04X", value & 0xFFFF);
    }
}