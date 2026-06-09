package maquinavirtualz80;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;
import maquinavirtualz80.assembler.Assembler;  // NOVO - import do assembler

public class GUI extends JFrame {

    private CPU cpu;
    
    private boolean programLoaded = false;

    // Labels
    private JLabel labelRegs, labelFlags, labelPC, labelOpcode, labelStatus;
    private JTextArea memoryArea, stackArea;
    
    // NOVO - Para mostrar mensagens do assembler
    private JTextArea consoleArea;

    public GUI(CPU cpu) {
        this.cpu = cpu;

        setTitle("Z80 Virtual Machine - Com Montador");
        setSize(900, 700);  // MODIFICADO - aumentado para caber o console
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
        // BOTÕES (em cima) - MODIFICADO
        // =========================
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton btnAssemble = new JButton("MONTAR");  // NOVO
        JButton btnStep = new JButton("STEP");
        JButton btnRun = new JButton("RUN");
        JButton btnReset = new JButton("RESET");
        JButton btnLoad = new JButton("LOAD");

        buttonPanel.add(btnAssemble);  // NOVO - adicionado primeiro
        buttonPanel.add(btnStep);
        buttonPanel.add(btnRun);
        buttonPanel.add(btnReset);
        buttonPanel.add(btnLoad);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

consoleArea = new JTextArea();
consoleArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
consoleArea.setEditable(false);
consoleArea.setBackground(new Color(255, 243, 204));
consoleArea.setForeground(Color.BLACK);
consoleArea.setText("=== Console da Máquina Virtual Z80 ===\nAguardando comandos...\n");
        
        JScrollPane consoleScroll = new JScrollPane(consoleArea);
        consoleScroll.setBorder(createTitledBorder("CONSOLE"));
        consoleScroll.setPreferredSize(new Dimension(880, 120));
        
        mainPanel.add(consoleScroll, BorderLayout.NORTH);

        // =========================
        // AÇÕES
        // =========================

        // MONTAR
        btnAssemble.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(new File("."));
            chooser.setDialogTitle("Selecione o arquivo Assembly (.asm)");
            
            // Filtro para arquivos .asm
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos Assembly (*.asm)", "asm"));
            
            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File asmFile = chooser.getSelectedFile();
                String objFile = asmFile.getAbsolutePath().replace(".asm", ".obj");
                
                consoleArea.append("\n[Montador] Iniciando montagem de: " + asmFile.getName() + "\n");
                
                Assembler assembler = new Assembler();
                boolean success = assembler.assemble(asmFile.getAbsolutePath(), objFile);
                
                if (success) {
                    consoleArea.append("[Montador] Montagem concluída com sucesso!\n");
                    consoleArea.append("[Montador] Arquivo gerado: " + objFile + "\n");
                    
                    // Carrega automaticamente o arquivo montado
                    consoleArea.append("[Loader] Carregando programa na memória...\n");
                    ProgramLoader.load(objFile, cpu.getMemory());
                    cpu.getRegisters().PC = 0;
                    cpu.halted = false;
                    programLoaded = true;
                    
                    consoleArea.append("[Loader] Programa carregado! PC = 0x0000\n");
                    consoleArea.append("[Sistema] Pronto para execução. Use STEP ou RUN.\n");
                    
                    updateDisplay();
                } else {
                    consoleArea.append("[Montador] Erro na montagem:\n");
                    consoleArea.append(assembler.getErrors() + "\n");
                    JOptionPane.showMessageDialog(this, "Erro na montagem! Verifique o console para detalhes.");
                }
            }
        });

        btnStep.addActionListener(e -> {
            if (!programLoaded) {
                consoleArea.append("[Aviso] Nenhum programa carregado. Use LOAD ou MONTAR primeiro.\n");
                return;
            }
            
            if (cpu.halted) {
                consoleArea.append("[Aviso] CPU está parada (HALT). Use RESET para reiniciar.\n");
                return;
            }
            
            consoleArea.append("[STEP] Executando instrução em PC = " + hex16(cpu.getRegisters().PC) + "\n");
            this.cpu.step();
            updateDisplay();
            
            if (cpu.halted) {
                consoleArea.append("[Sistema] Programa finalizado (HALT)\n");
            }
        });

        btnRun.addActionListener(e -> {
            if (!programLoaded) {
                consoleArea.append("[Aviso] Nenhum programa carregado. Use LOAD ou MONTAR primeiro.\n");
                return;
            }
            
            if (cpu.halted) {
                consoleArea.append("[Aviso] CPU está parada (HALT). Use RESET para reiniciar.\n");
                return;
            }
            
            consoleArea.append("[RUN] Executando programa...\n");
            
            new Thread(() -> {
                int steps = 0;
                while (!cpu.halted) {
                    cpu.step();
                    steps++;

                    try {
                        Thread.sleep(100); // velocidade ajustada
                    } catch (InterruptedException ex) {}

                    final int currentSteps = steps;
                    SwingUtilities.invokeLater(() -> {
                        updateDisplay();
                        if (cpu.halted) {
                            consoleArea.append("[RUN] Programa finalizado após " + currentSteps + " instruções (HALT)\n");
                        }
                    });
                }
            }).start();
        });

        btnReset.addActionListener(e -> {
            this.cpu = new CPU();
            programLoaded = false;  // Reset também limpa o flag de programa carregado
            consoleArea.append("[Reset] CPU reiniciada. Registradores zerados.\n");
            updateDisplay();
        });

        btnLoad.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(new File("."));
            chooser.setDialogTitle("Selecione o arquivo objeto (.obj)");
            
            // Filtro para arquivos .obj e .txt
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos Objeto (*.obj, *.txt)", "obj", "txt"));
            
            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                consoleArea.append("[Loader] Carregando arquivo: " + file.getName() + "\n");
                ProgramLoader.load(file.getAbsolutePath(), this.cpu.getMemory());
                this.cpu.getRegisters().PC = 0;
                this.cpu.halted = false;
                
                programLoaded = true;
                
                consoleArea.append("[Loader] ✅ Programa carregado! Tamanho: verificar memória\n");
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
            
            // Limpa memória e stack se não há programa
            memoryArea.setText("(Nenhum programa carregado)");
            stackArea.setText("(Nenhum programa carregado)");

            return;
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

        labelStatus.setText("STATUS: " + (cpu.halted ? "HALTED" : "RUNNING"));

        // MEMÓRIA (primeiros 20 bytes)
        StringBuilder mem = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            mem.append(String.format("%04X: %02X\n", i, cpu.getMemory().read(i)));
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
