;;; ---------------------------------------------------------
;;; defOnto.clp
;;; Translated by owl2clips
;;; Translated to CLIPS from ontology defOnto.owl
;;; :Date 06/12/2022 12:35:51

(defclass Ejercicio
    (is-a USER)
    (role concrete)
    (pattern-match reactive)
    (multislot Categoria
        (type SYMBOL)
        (create-accessor read-write))
    (multislot Intensidad
        (type INTEGER)
        (create-accessor read-write))
)

(defclass Duracion
    (is-a Ejercicio)
    (role concrete)
    (pattern-match reactive)
    (multislot Duracion
        (type INTEGER)
        (create-accessor read-write))
)

(defclass Repeticiones
    (is-a Ejercicio)
    (role concrete)
    (pattern-match reactive)
    (multislot Repeticiones
        (type INTEGER)
        (create-accessor read-write))
)

(defclass Enfermedades
    (is-a USER)
    (role concrete)
    (pattern-match reactive)
    (multislot noSeRecomienda
        (type INSTANCE)
        (create-accessor read-write))
    (multislot seRecomienda
        (type INSTANCE)
        (create-accessor read-write))
    (multislot AfectacionMusculoEsqueletica
        (type SYMBOL)
        (create-accessor read-write))
)

(defclass PartesCuerpo
    (is-a USER)
    (role concrete)
    (pattern-match reactive)
)

(defclass Persona
    (is-a USER)
    (role concrete)
    (pattern-match reactive)
    (multislot tieneAfectadas
        (type INSTANCE)
        (create-accessor read-write))
    (multislot tieneDiscapacidad
        (type INSTANCE)
        (create-accessor read-write))
    (multislot Altura
        (type SYMBOL)
        (create-accessor read-write))
    (multislot Deportista
        (type SYMBOL)
        (create-accessor read-write))
    (multislot Edad
        (type SYMBOL)
        (create-accessor read-write))
    (multislot Escala_Borg_Caminar
        (type SYMBOL)
        (create-accessor read-write))
    (multislot Escala_Borg_LevantarP
        (type SYMBOL)
        (create-accessor read-write))
    (multislot Fase
        (type SYMBOL)
        (create-accessor read-write))
    (multislot Fumador
        (type SYMBOL)
        (create-accessor read-write))
    (multislot Genero
        (type SYMBOL)
        (create-accessor read-write))
    (multislot Peso
        (type SYMBOL)
        (create-accessor read-write))
    (multislot PesoALevantar
        (type SYMBOL)
        (create-accessor read-write))
    (multislot ResistenciaAerobica
        (type SYMBOL)
        (create-accessor read-write))
    (multislot ResistenciaMusculoEsqueletica
        (type SYMBOL)
        (create-accessor read-write))
    (multislot ResistenciaRelativa
        (type SYMBOL)
        (create-accessor read-write))
    (multislot Sabe_bicicleta
        (type SYMBOL)
        (create-accessor read-write))
    (multislot Sabe_nadar
        (type SYMBOL)
        (create-accessor read-write))
    (multislot Sobrepeso
        (type SYMBOL)
        (create-accessor read-write))
    (multislot Tiempo_Libre
        (type SYMBOL)
        (create-accessor read-write))
)

(defclass Programa_ejercicios
    (is-a USER)
    (role concrete)
    (pattern-match reactive)
    (multislot TieneEjercicio
        (type INSTANCE)
        (create-accessor read-write))
)

(definstances instances
    ([Abdominales] of Repeticiones
         (Categoria  "Aerobico")
         (Intensidad  8)
    )

    ([Artritis_reumatoide] of Enfermedades
         (noSeRecomienda  [DeltoidesPeso] [PectoralesPeso] [TrapeciosPeso] [TricepsPeso])
         (seRecomienda  [Bicicleta] [Caminata] [Danza])
         (AfectacionMusculoEsqueletica  "true")
    )

    ([Artrosis] of Enfermedades
         (noSeRecomienda  [DeltoidesPeso] [PectoralesPeso] [TrapeciosPeso] [TricepsPeso])
         (seRecomienda  [Bicicleta] [Caminata] [Danza])
         (AfectacionMusculoEsqueletica  "true")
    )

    ([Biceps] of PartesCuerpo
    )

    ([BicepsPeso] of Repeticiones
         (Categoria  "Fuerza")
         (Intensidad  8)
    )

    ([Bicicleta] of Duracion
         (Categoria  "Aerobico")
         (Intensidad  5)
    )

    ([Caminata] of Duracion
         (Categoria  "Aerobico")
         (Intensidad  3)
    )

    ([Cancer] of Enfermedades
         (seRecomienda  [Abdominales] [Bicicleta] [Caminata] [Danza])
         (AfectacionMusculoEsqueletica  "false")
    )

    ([Danza] of Duracion
         (Categoria  "Aerobico")
         (Intensidad  3)
    )

    ([DeltoidesPeso] of Repeticiones
         (Categoria  "Fuerza")
         (Intensidad  8)
    )

    ([Depresion] of Enfermedades
         (seRecomienda  [Bicicleta] [Caminata] [Danza] [Natacion])
         (AfectacionMusculoEsqueletica  "false")
    )

    ([Diabetes] of Enfermedades
         (seRecomienda  [Bicicleta] [Caminata] [Danza] [Natacion])
         (AfectacionMusculoEsqueletica  "false")
    )

    ([Diagonal_cuello] of Repeticiones
         (Categoria  "Calentamiento" "Cooldown")
         (Intensidad  1)
    )

    ([Enfermedad_Pulmonar_Constructiva] of Enfermedades
         (seRecomienda  [Bicicleta] [Caminata] [Danza] [Natacion])
         (AfectacionMusculoEsqueletica  "false")
    )

    ([EstiramientoPectorales] of Repeticiones
         (Categoria  "Calentamiento")
         (Intensidad  1)
    )

    ([EstirarCuadriceps] of Repeticiones
         (Categoria  "Calentamiento" "Flexibilidad")
         (Intensidad  1)
    )

    ([EstirarIsquio] of Repeticiones
         (Categoria  "Calentamiento" "Flexibilidad")
         (Intensidad  1)
    )

    ([EstirarMuneca] of Repeticiones
         (Categoria  "Calentamiento" "Flexibilidad")
         (Intensidad  1)
    )

    ([EstirarPantorrilla] of Repeticiones
         (Categoria  "Calentamiento" "Flexibilidad")
         (Intensidad  1)
    )

    ([EstirarTendonesMuslo] of Repeticiones
         (Categoria  "Calentamiento" "Cooldown" "Flexibilidad")
         (Intensidad  1)
    )

    ([EstirarTobillos] of Repeticiones
         (Categoria  "Calentamiento" "Flexibilidad")
         (Intensidad  1)
    )

    ([EstirarTriceps] of Repeticiones
         (Categoria  "Calentamiento" "Flexibilidad")
         (Intensidad  1)
    )

    ([ExtenderCadera] of Repeticiones
         (Categoria  "Calentamiento" "Cooldown")
         (Intensidad  1)
    )

    ([ExtenderRodilla] of Repeticiones
         (Categoria  "Calentamiento" "Cooldown")
         (Intensidad  1)
    )

    ([ExtenderTriceps] of Repeticiones
         (Categoria  "Calentamiento" "Cooldown")
         (Intensidad  1)
    )

    ([Fibrosis_Quistica] of Enfermedades
         (seRecomienda  [Abdominales] [Bicicleta] [Caminata] [Danza] [Natacion])
         (AfectacionMusculoEsqueletica  "false")
    )

    ([FlexionPlantar] of Repeticiones
         (Categoria  "Calentamiento" "Cooldown")
         (Intensidad  1)
    )

    ([FlexionarCadera] of Repeticiones
         (Categoria  "Calentamiento" "Cooldown")
         (Intensidad  1)
    )

    ([FlexionarHombros] of Repeticiones
         (Categoria  "Calentamiento" "Cooldown")
         (Intensidad  1)
    )

    ([FlexionarRodilla] of Repeticiones
         (Categoria  "Calentamiento" "Cooldown")
         (Intensidad  1)
    )

    ([FlexionarTobillo] of Repeticiones
         (Categoria  "Calentamiento" "Cooldown")
         (Intensidad  1)
    )

    ([Fragilidad] of Enfermedades
         (noSeRecomienda  [BicepsPeso] [DeltoidesPeso] [PectoralesPeso] [TrapeciosPeso] [TricepsPeso])
         (seRecomienda  [Abdominales] [Bicicleta] [Caminata] [Danza] [Natacion])
         (AfectacionMusculoEsqueletica  "true")
    )

    ([GImnasia] of Duracion
         (Categoria  "Aerobico" "Flexibilidad")
         (Intensidad  7)
    )

    ([Hipertension] of Enfermedades
         (noSeRecomienda  [DeltoidesPeso] [PectoralesPeso] [TrapeciosPeso] [TricepsPeso])
         (seRecomienda  [Bicicleta] [Caminata] [Danza] [Natacion])
         (AfectacionMusculoEsqueletica  "false")
    )

    ([Incontinencia_urinaria] of Enfermedades
         (noSeRecomienda  [DeltoidesPeso] [PectoralesPeso] [TrapeciosPeso] [TricepsPeso])
         (AfectacionMusculoEsqueletica  "false")
    )

    ([Insuficiencia_renal_cronica] of Enfermedades
         (noSeRecomienda  [DeltoidesPeso] [PectoralesPeso] [TrapeciosPeso] [TricepsPeso])
         (AfectacionMusculoEsqueletica  "false")
    )

    ([LevantarBrazos] of Repeticiones
         (Categoria  "Calentamiento" "Cooldown")
         (Intensidad  2)
    )

    ([LevantarseSilla] of Repeticiones
         (Categoria  "Equilibrio")
         (Intensidad  3)
    )

    ([Natacion] of Repeticiones
         (Categoria  "Aerobico")
         (Intensidad  3)
    )

    ([Obesidad] of Enfermedades
         (seRecomienda  [Abdominales] [Caminata] [Danza] [Natacion])
         (AfectacionMusculoEsqueletica  "true")
    )

    ([Osteoporosis] of Enfermedades
         (noSeRecomienda  [DeltoidesPeso] [PectoralesPeso] [TrapeciosPeso] [TricepsPeso])
         (seRecomienda  [Bicicleta] [Caminata] [Natacion])
         (AfectacionMusculoEsqueletica  "true")
    )

    ([PectoralesPeso] of Repeticiones
         (Categoria  "Fuerza")
         (Intensidad  8)
    )

    ([PiernasHaciaLados] of Repeticiones
         (Categoria  "Flexibilidad")
         (Intensidad  3)
    )

    ([RotarCadera] of Repeticiones
         (Categoria  "Calentamiento" "Flexibilidad")
         (Intensidad  2)
    )

    ([RotarHombros] of Repeticiones
         (Categoria  "Calentamiento" "Flexibilidad")
         (Intensidad  2)
    )

    ([SillaBiceps] of Repeticiones
         (Categoria  "Fuerza")
         (Intensidad  7)
    )

    ([SubirEscaleras] of Repeticiones
         (Categoria  "Equilibrio")
         (Intensidad  5)
    )

    ([TrapeciosPeso] of Repeticiones
         (Categoria  "Fuerza")
         (Intensidad  8)
    )

    ([TricepsPeso] of Repeticiones
         (Categoria  "Fuerza")
         (Intensidad  8)
    )

)







