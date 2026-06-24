; Definicao aninhada: GERADOR define DOBRA e a chama internamente

GERADOR MACRO base
    DOBRA MACRO n
        LD A, n
        ADD A, n
    ENDM
    DOBRA base
ENDM

    ORG $0000
    GERADOR 4
    DOBRA 7
    HALT