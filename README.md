# 💻 Máquina Virtual Z80

Simulador de uma CPU baseada no Z80, desenvolvido em Java, com suporte a execução de instruções, manipulação de memória, stack, e interface gráfica interativa.

---

## 📑 Índice

- [Tecnologias utilizadas](#-tecnologias-utilizadas)
- [Como executar o projeto](#-como-executar-o-projeto)
- [Interface gráfica](#-interface-gráfica)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [Instruções implementadas](#-instruções-implementadas)
- [Formato do programa](#-formato-do-programa)
- [Exemplo de execução](#-exemplo-de-execução)
- [Autores](#-autores)

---

## 🚀 Tecnologias utilizadas

- **Java JDK 17** - Linguagem de programação
- **NetBeans** - IDE recomendada
- **Java Swing** - Biblioteca para interface gráfica
- **Nenhuma dependência externa** - Projeto 100% Java puro

---

## ▶️ Como executar o projeto

### 🔧 Pré-requisitos

- Java JDK 17 ou superior instalado
- NetBeans (recomendado) ou qualquer IDE Java
- Sistema operacional: Windows, Linux ou macOS
- Nenhuma dependência externa adicional

### 📥 Clonando o repositório

```bash
git clone https://github.com/seu-usuario/maquina-virtual-z80.git
cd maquina-virtual-z80
```

### ▶️ Executando

**Pelo NetBeans:**
1. Abra o projeto no NetBeans
2. Clique em "Run Project" ou pressione F6
3. A interface gráfica será aberta automaticamente

**Pelo terminal:**
```bash
javac *.java
java Main
```

---

## 🖥️ Interface gráfica

A GUI permite visualizar e controlar a execução da CPU em tempo real.

### Funcionalidades:

| Componente | Descrição |
|------------|-----------|
| **Registradores** | Visualização de A, B, C, D, E, H, L, PC, SP |
| **Flags** | Zero, Sign, Carry, Parity, Half-Carry |
| **Memória** | Exibição da memória RAM em formato hexadecimal |
| **Stack** | Visualização da pilha (stack) |
| **Instrução atual** | Opcode e instrução sendo executada |
| **Estado da CPU** | RUNNING / HALTED |

### Botões de controle:

| Botão | Função |
|-------|--------|
| **STEP** | Executa uma instrução por vez (modo passo a passo) |
| **RUN** | Executa continuamente até HALT ou interrupção |
| **RESET** | Reinicia a CPU (registradores e memória) |
| **LOAD** | Carrega um programa a partir de arquivo `.txt` |

---

## 📂 Estrutura do projeto

```
maquina-virtual-z80/
├── src/
│   ├── CPU.java          # Ciclo de execução (fetch, decode, execute)
│   ├── Memory.java       # Gerenciamento da memória (leitura/escrita)
│   ├── Registers.java    # Registradores da CPU (8 bits e 16 bits)
│   ├── Flags.java        # Controle das flags de status
│   ├── Stack.java        # Operações de pilha (push/pop)
│   ├── ProgramLoader.java # Leitura de programas a partir de arquivo
│   ├── GUI.java          # Interface gráfica (Swing)
│   └── Main.java         # Ponto de entrada da aplicação
├── programs/             # Programas exemplo (.txt)
│   ├── exemplo1.txt
│   └── exemplo2.txt
├── images/               # Screenshots da interface
│   └── gui.png
└── README.md
```

---

## 🧠 Instruções implementadas

### 📌 Aritméticas e lógicas

| Instrução | Descrição |
|-----------|-----------|
| `ADD A, n` | Soma valor imediato ao registrador A |
| `ADD A, B` | Soma registrador B ao registrador A |
| `SUB` | Subtrai valor do registrador A |
| `AND` | Operação lógica AND com A |
| `OR` | Operação lógica OR com A |
| `XOR` | Operação lógica XOR com A |
| `CP` | Compara valor com A (afeta flags) |

### 📌 Controle de fluxo

| Instrução | Descrição |
|-----------|-----------|
| `JP` | Salto incondicional para endereço |
| `JR` | Salto relativo (deslocamento) |
| `CALL` | Chama sub-rotina |
| `RET` | Retorna de sub-rotina |

### 📌 Manipulação de registradores

| Instrução | Descrição |
|-----------|-----------|
| `LD A, n` | Carrega valor imediato em A |
| `LD B, n` | Carrega valor imediato em B |
| `INC` | Incrementa registrador |
| `DEC` | Decrementa registrador |

### 📌 Stack

| Instrução | Descrição |
|-----------|-----------|
| `PUSH` | Empilha par de registradores |
| `POP` | Desempilha par de registradores |

### 📌 Outras

| Instrução | Descrição |
|-----------|-----------|
| `HALT` | Para a execução da CPU |

---

## 📄 Formato do programa

Os programas são carregados a partir de arquivos `.txt` contendo opcodes em hexadecimal, **um por linha**.

### Exemplo de arquivo (`programa.txt`):
```
3E
05
06
03
80
C5
C1
FE
08
76
```

### 📖 Tabela de opcodes do exemplo:

| Opcode (hex) | Instrução | Comentário |
|--------------|-----------|------------|
| `3E` | LD A, 5 | Carrega 5 em A |
| `06` | LD B, 3 | Carrega 3 em B |
| `80` | ADD A, B | A = A + B |
| `C5` | PUSH BC | Empilha BC |
| `C1` | POP BC | Desempilha BC |
| `FE` | CP 8 | Compara A com 8 |
| `76` | HALT | Para execução |

> ⚠️ **Importante:** O arquivo deve conter **apenas** os opcodes em hexadecimal, sem espaços ou caracteres adicionais.

---

## 🧪 Exemplo de execução

### Programa carregado:
```asm
LD A, 5    ; A = 5
LD B, 3    ; B = 3
ADD A, B   ; A = 5 + 3 = 8
PUSH BC    ; Empilha BC
POP BC     ; Desempilha BC (restaura)
CP 8       ; Compara A com 8
HALT       ; Para CPU
```

### Resultado esperado:

| Registrador | Valor |
|-------------|-------|
| **A** | 0x08 (8) |
| **B** | 0x03 (3) |
| **C** | 0x00 (0) |
| **PC** | Endereço após HALT |
| **SP** | Topo da stack |

**Flags:**
- ✅ **Zero flag** = `true` (A == 8)
- ❌ **Sign flag** = `false` (A é positivo)
- ❌ **Carry flag** = `false` (sem estouro)

**Estado da CPU:** `HALTED`

---

## 🎯 Objetivo do projeto

Simular o funcionamento básico de uma CPU baseada no Z80, permitindo a execução de instruções, manipulação de memória e análise do comportamento interno da máquina.

Este projeto foi desenvolvido para fins educacionais, com o intuito de:
- Compreender o funcionamento interno de uma CPU
- Estudar arquitetura de computadores na prática
- Implementar um ciclo de execução (fetch-decode-execute)
- Visualizar o estado dos registradores e memória em tempo real

---

## 👩‍💻 Autores

**Gabriel Mendes**
**Guilherme Gonçalves Pereira**
**Iago Kainan Bubolz Braatz**
**Lincon Thiel Retzlaff**
**Milena Alves Ferreira**
**Vitor Teixeira Medina**
