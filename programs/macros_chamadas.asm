; Chamadas aninhadas: SOMA chama LOAD internamente

LOAD MACRO reg, val
    LD reg, val
ENDM

SOMA MACRO x, y
    LOAD A, x
    LOAD B, y
    ADD A, B
ENDM

    ORG $0000
    SOMA 10, 5
    HALT