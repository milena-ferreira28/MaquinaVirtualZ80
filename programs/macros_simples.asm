; Macros simples: sem parametros e com parametros

ZEROS MACRO
    LD A, 0
    LD B, 0
ENDM

SOMA MACRO x, y
    LD A, x
    LD B, y
    ADD A, B
ENDM

    ORG $0000
    ZEROS
    SOMA 5, 3
    HALT