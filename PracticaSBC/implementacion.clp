(defmodule MAIN (export ?ALL))

(defmodule preguntas-individuo
	(import MAIN ?ALL)
	(export ?ALL)
)

(defmodule abstraccion
	(import MAIN ?ALL)
	(export ?ALL)
)

(deftemplate preguntas-individuo::refinamiento
    (multislot recomendacion (type INSTANCE))
)

(deftemplate preguntas-individuo::asociacion
    (multislot recomendados (type INSTANCE))
    (multislot noNoRecomendados (type INSTANCE))
    (slot duracion (type SYMBOL) (default FALSE))
    (slot repeticiones (type SYMBOL) (default FALSE))
)

(deftemplate preguntas-individuo::abstraccion_fases
	(slot sobrepeso (type INTEGER) (default 0)) ;persona tiene sobrepeso
    (slot esqueletico (type INTEGER) (default 0)) ;persona tiene problemas musculo esqueléticos
    (slot resistenciaAerobica (type INTEGER) (default 0)); se ha procesado o no la resistencia aerobica
)

(deftemplate preguntas-individuo::refinamiento
	(multislot ejercicios (type INSTANCE)) ;ejercicios recomendados
)

(deffunction MAIN::pregunta-numerica (?pregunta ?rangini ?rangfi)
	(format t "%s (De %d hasta %d) " ?pregunta ?rangini ?rangfi)
	(bind ?respuesta (read))
	(while (not(and(>= ?respuesta ?rangini)(<= ?respuesta ?rangfi))) do
		(format t "%s (De %d hasta %d) " ?pregunta ?rangini ?rangfi)
		(bind ?respuesta (read))
	)
	?respuesta
)
(deffunction MAIN::pregunta-opciones (?pregunta $?valores-posibles)
    (bind ?linea (format nil "%s" ?pregunta))
    (printout t ?linea crlf)
    (progn$ (?var ?valores-posibles) 
            (bind ?linea (format nil "  %d. %s" ?var-index ?var))
            (printout t ?linea crlf)
    )
    (bind ?respuesta (pregunta-numerica "Escoja una opcion:" 1 (length$ ?valores-posibles)))
	?respuesta
)

(deffunction MAIN::pregunta-si-no (?question)
   (bind ?response (pregunta-opciones ?question si no))
   (if (or (eq ?response si) (eq ?response s))
       then 1 
       else 0)
)

;Question that accepts a value from a list of allowed values
(deffunction pregunta-multiples-opciones (?pregunta $?valors)
    (format t "%s? (%s) " ?pregunta (implode$ ?valors)) (printout t crlf)
    (bind ?resposta (read))
    (while (not (member$ ?resposta ?valors)) do
      (format t "%s? (%s) " ?pregunta (implode$ ?valors))
	  (bind ?resposta (read))
	  )
	  ?resposta
)

(deffunction MAIN::pregunta-multirespuesta (?pregunta $?valores-posibles)
    (printout t ?pregunta crlf)
    (loop-for-count (?i 1 (length$ $?valores-posibles)) do
		(bind ?curr-val (nth$ ?i ?valores-posibles))
        (printout t (str-cat ?i) "-" ?curr-val crlf)
	)
    (format t "%s" "Indica los numeros referentes a las preferencias separados por un espacio: ")
    (bind ?resp (readline))
    (bind ?numeros (explode$ ?resp))
    (bind $?lista (create$))
    (progn$ (?var ?numeros) 
        (if (and (integerp ?var) (and (>= ?var 0) (<= ?var (length$ ?valores-posibles))))
            then 
                (if (not (member$ ?var ?lista))
                    then (bind ?lista (insert$ ?lista (+ (length$ ?lista) 1) ?var))
                )
        ) 
    )
    (if (or(member$ 0 ?lista)(= (length$ ?lista) 0)) then (bind ?lista (create$ ))) 
    ;(if (member$ 0 ?lista) then (bind ?lista (create$ 0)))
    ?lista
)

(defrule MAIN::initialRule "Regla inicial"
	(declare (salience 10))
	=>
  	(printout t"          Rutina de Ejercicios           " crlf)
  	(printout t crlf)  	
	(printout t"¡Bienvenido! A continuacion se le formularan una serie de preguntas para poder recomendarle una rutina adecuada a sus preferencias." crlf)
	(printout t crlf)
	(focus preguntas-individuo)
)

;---------------------------------------PREGUNTAS------------------------------------------------------
(defrule preguntas-individuo::saber-edad "Pregunta por la edad de la persona"
    (declare (salience 10))
    (not (exists (object (is-a Persona))))
    =>
    (bind ?e (pregunta-numerica "¿Que edad tiene usted? " 65 110))
    (make-instance current_user of Persona (Edad ?e))
)


(defrule preguntas-individuo::saber-peso "Pregunta por el peso de la persona"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    =>
    (bind ?p (pregunta-numerica "¿Cuanto pesa? " 50 150))
    (send ?cu put-Peso  ?p)
)

(defrule preguntas-individuo::saber-altura "Pregunta por la altura de la persona"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    =>
    (bind ?p (pregunta-numerica "¿Cuanto mides (en cm)? " 100 250))
    (send ?cu put-Altura  ?p)
)

(defrule preguntas-individuo::saber-genero "Pregunta por el genero de la persona"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    =>
    (bind ?p (pregunta-multiples-opciones "¿Es hombre o mujer? " Hombre Mujer))
    (send ?cu put-Genero  ?p)
)


(defrule preguntas-individuo::saber-tiempo "Pregunta por el tiempo libre de la persona"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    =>
    (bind ?p (pregunta-multiples-opciones "¿Cuanto tiempo libre tiene? " Poco Normal Mucho))
    (send ?cu put-Tiempo_Libre ?p)
)

(defrule preguntas-individuo::saber-fase "Pregunta por la fase en el entrenamiento de una persona"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    =>
    (bind ?p (pregunta-multiples-opciones "¿En que fase te encuentras? " Inicial Mantenimiento Mejora))
    (send ?cu put-Fase ?p)
)

(defrule preguntas-individuo::saber-borg-caminar "Pregunta por esfuerzo al caminar"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    =>
    (bind ?p (pregunta-numerica "¿Cuanto esfuerzo te supone caminar 30 minutos a paso lento (1 muy poco) (10 muchísimo)? " 1 10))
    (send ?cu put-Escala_Borg_Caminar ?p)
)

(defrule preguntas-individuo::saber-borg-levantar "Pregunta por esfuerzo al levantar peso"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    =>
    (bind ?p (pregunta-numerica "¿Cuanto esfuerzo te supone levantar 5kg de peso 10 veces (1 muy poco) (10 muchísimo)? " 1 10))
    (send ?cu put-Escala_Borg_LevantarP ?p)
)

(defrule preguntas-individuo::saber-bicicleta "Pregunta si la persona sabe montar en bicicleta"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    =>
    (bind ?p (pregunta-si-no "¿Sabes montar en bicicleta?"))
    (send ?cu put-Sabe_bicicleta ?p)
)

(defrule preguntas-individuo::saber-nadar "Pregunta si la persona sabe nadar"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    =>
    (bind ?p (pregunta-si-no "¿Sabes nadar?"))
    (send ?cu put-Sabe_nadar ?p)
)

(defrule preguntas-individuo::saber-fumar "Pregunta si la persona fuma o ha fumado"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    =>
    (bind ?p (pregunta-si-no "¿Fumas o has fumado en los últimos 10 años?"))
    (send ?cu put-Fumador ?p)
)

(defrule preguntas-individuo::saber-deportista "Pregunta si la persona ha hecho deporte con regularidad"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    =>
    (bind ?p (pregunta-si-no "¿Has hecho deporte con regularidad a lo largo de tu vida?"))
    (send ?cu put-Deportista ?p)
)


(defrule preguntas-individuo::saber-discapacidad "Pregunta por la discapacidad de la persona"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    =>
    (bind $?discapacidades (find-all-instances ((?c Enfermedades)) TRUE))
    (printout t (length$ $?discapacidades) crlf)
    (bind $?nombre-discapacidades (create$ ))
	(loop-for-count (?i 1 (length$ $?discapacidades)) do
		(bind ?curr-obj (nth$ ?i ?discapacidades))
		(bind ?curr-nom (instance-name ?curr-obj))
		(bind $?nombre-discapacidades(insert$ $?nombre-discapacidades (+ (length$ $?nombre-discapacidades) 1) ?curr-nom))
	)
	(bind $?escogido (pregunta-multirespuesta "Escoja las discapacidades que tiene: " $?nombre-discapacidades))
	(bind $?respuesta (create$ ))
	(loop-for-count (?i 1 (length$ $?escogido)) do
		(bind ?curr-index (nth$ ?i ?escogido))
		(bind ?curr-discapacidad (nth$ ?curr-index ?discapacidades))
		(bind $?respuesta(insert$ $?respuesta (+ (length$ $?respuesta) 1) ?curr-discapacidad))
	)
    (send ?cu put-tieneDiscapacidad $?respuesta)
)


;-----------------------------------ABSTRACCION---------------------------------------------------------------

(deffunction preguntas-individuo::abstraeRango (?valor) "Abstrae un valor de 0 a 1 en un rango"
    (if (and(>= ?valor 0)(<= ?valor 0.2)) then (bind ?resultado "Poco"))
    (if (and(> ?valor 0.2)(< ?valor 0.5)) then (bind ?resultado "Normal"))
    (if (>= ?valor 0.5) then (bind ?resultado "Mucho"))
    ?resultado
)

(defrule preguntas-individuo::saber-sobrepeso "Decide el sobrepeso de la persona"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    (not (abstraccion_fases))
    =>
    (bind ?Peso (string-to-field(implode$ (send ?cu get-Peso))))
    (bind ?Altura (string-to-field(implode$ (send ?cu get-Altura))))
    (bind ?Altura (/ ?Altura 100))
    (bind ?IMC (/ ?Peso (* ?Altura ?Altura)))
    (bind ?NivelSobrepeso "Bajo Peso")
    (if (< ?IMC 18.5) then (bind ?NivelSobrepeso "Bajo Peso"))
    (if (and (>= ?IMC 18.5) (< ?IMC 24.9)) then (bind ?NivelSobrepeso "Normopeso"))
    (if (and (>= ?IMC 24.9) (< ?IMC 26.9)) then (bind ?NivelSobrepeso "SobrepesoI"))
    (if (and (>= ?IMC 26.9) (< ?IMC 29.9)) then (bind ?NivelSobrepeso "SobrepesoII"))
    (if (and (>= ?IMC 29.9) (< ?IMC 34.9)) then (bind ?NivelSobrepeso "ObesidadI"))
    (if (and (>= ?IMC 34.9) (< ?IMC 39.9)) then (bind ?NivelSobrepeso "ObesidadII"))
    (if (and (>= ?IMC 39.9) (< ?IMC 49.9)) then (bind ?NivelSobrepeso "ObesidadIII"))
    (if (>= ?IMC 49.9) then (bind ?NivelSobrepeso "ObesidadIV"))
    (send ?cu put-Sobrepeso ?NivelSobrepeso)
    (assert (abstraccion_fases (sobrepeso 1)))
)

(defrule preguntas-individuo::saber-esqueletico "Abstrae el nivel de problemas esqueléticos"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    ?g <- (abstraccion_fases (esqueletico ?esqueletico))
    (test (= ?esqueletico 0))
    =>
    (bind $?discapacidades (send ?cu get-tieneDiscapacidad))
    (bind $?esqueleticosquetiene (find-all-instances ((?c Enfermedades)) (and (member$ ?c $?discapacidades)(eq "true" (string-to-field(implode$ (send ?c get-AfectacionMusculoEsqueletica)))))))
    (bind $?todosEsqueleticos (find-all-instances ((?c Enfermedades))(eq "true" (string-to-field(implode$ (send ?c get-AfectacionMusculoEsqueletica))))))
    (send ?cu put-ResistenciaMusculoEsqueletica (abstraeRango(/ (length$ $?esqueleticosquetiene)(length$ $?todosEsqueleticos))))
    (assert (abstraccion_fases (esqueletico 1)))
)


(defrule preguntas-individuo::decide-forma "Abstrae resistencia a duracion ejercicios"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    ?g <- (abstraccion_fases (resistenciaAerobica ?resistenciaAerobica))
    (test (= ?resistenciaAerobica 0))
    =>
    (bind ?nivelBorgCaminar (string-to-field(implode$ (send ?cu get-Escala_Borg_Caminar))))
    (send ?cu put-ResistenciaAerobica (abstraeRango(- 1 (/ ?nivelBorgCaminar 10))))
    (assert (abstraccion_fases (resistenciaAerobica 1)))
)


;-----------------------------------ASOCIACION HEURISTICA-----------------------------------------------------

(defrule preguntas-individuo::selecciona-recomendados-y-nonoRecomendados "Encuentra los ejercicios recomendados y no no recomendados para las discapacidades"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    (not (asociacion))
	=>
	;----RECOMENDADOS
	
    (bind $?discapacidadesQueTiene (send ?cu get-tieneDiscapacidad))
    (bind $?recomendados (create$ ))
	(loop-for-count (?i 1 (length$ $?discapacidadesQueTiene)) do
		(bind ?curr-obj (nth$ ?i ?discapacidadesQueTiene))
		(bind $?recomendaciones (send ?curr-obj get-seRecomienda))
        (loop-for-count (?j 1 (length$ $?recomendaciones)) do
            (bind ?curr-recomendacion (nth$ ?j ?recomendaciones))
            (if (not(member$ ?curr-recomendacion $?recomendados)) then (bind $?recomendados (insert$ $?recomendados (+ (length$ $?recomendados) 1) ?curr-recomendacion)))
        )
	)
	(assert (asociacion (recomendados $?recomendados)))
    ;----NO RECOMENDADOS
    
    (bind $?noRecomendados (create$ ))
	(loop-for-count (?i 1 (length$ $?discapacidadesQueTiene)) do
		(bind ?curr-obj (nth$ ?i ?discapacidadesQueTiene))
		(bind $?noRecomendaciones (send ?curr-obj get-noSeRecomienda))
        (loop-for-count (?j 1 (length$ $?noRecomendaciones)) do
            (bind ?curr-no-recomendacion (nth$ ?j ?noRecomendaciones))
            (if (not(member$ ?curr-no-recomendacion $?noRecomendados)) then (bind $?noRecomendados (insert$ $?noRecomendados (+ (length$ $?noRecomendados) 1) ?curr-no-recomendacion)))
        )
	)
    ;----NO NO RECOMENDADOS
    
	(bind $?noNoRecomendados (create$ ))
    (bind $?todosEjercicios (find-all-instances ((?c Ejercicio))TRUE))
    (loop-for-count (?i 1 (length$ $?todosEjercicios)) do
		(bind ?curr-obj (nth$ ?i ?todosEjercicios))
        (if (and (not(member$ ?curr-obj $?noNoRecomendados)) (not(member$ ?curr-obj $?noRecomendados)) ) then (bind $?noNoRecomendados (insert$ $?noNoRecomendados (+ (length$ $?noNoRecomendados) 1) ?curr-obj)))
	)
	(assert (asociacion (noNoRecomendados $?noNoRecomendados)))
)

(defrule preguntas-individuo::decideDuracion "Decide la duracion de los ejercicios"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    ?g <- (asociacion (duracion ?duracion))
    (test (eq ?duracion FALSE))
	=>
	(bind ?TiempoLibre (send ?cu get-Tiempo_Libre))
	(bind ?ResistenciaAerobica (send ?cu get-ResistenciaAerobica))
	(bind ?respuesta "Corto")
	(if (or (eq "Poco" ?ResistenciaAerobica) (eq "Poco" ?TiempoLibre)) then (bind ?respuesta "Corto")
        else
            (if (or (eq "Normal" ResistenciaAerobica) (eq "Normal" ?TiempoLibre)) then (bind ?respuesta "Medio") else (bind ?respuesta "Largo"))
    )
    (assert (asociacion (duracion ?respuesta)))
)

;-----------------------------------REFINAMIENTO------------------------------------------------------------



(deffunction preguntas-individuo::imprimeEjercicio (?ejercicio) "Imprime un ejercicio"
    (printout t "-------------------------" crlf crlf)
    (bind ?categoria (send ?ejercicio get-Categoria))
    (printout t "Ejercicio para" ?categoria crlf)
    (printout t (type ?ejercicio) crlf)
    
    (if (eq (type ?ejercicio) Duracion) then 
    (printout t "Duracion del ejercicio: " (send ?ejercicio get-Duracion) " minutos" crlf))
    
    (if (eq (type ?ejercicio) Repeticiones) then
    (printout t "Repeticiones a hacer: " (send ?ejercicio get-Repeticiones) " repeticiones" crlf))
    (printout t "-------------------------" crlf crlf)
)



(defrule preguntas-individuo::creaLosEjercicios "Crea los ejercicios recomendados para la persona"
    (declare (salience 10))
    ?cu <- (object (is-a Persona))
    ?g <- (asociacion (recomendados $?recomendados))
    (not (refinamiento))
	=>
	(printout t "Recomendados" $?recomendados crlf) ;recomendados está vacía
	(bind $?aRecomendar (create$ ))
    (loop-for-count (?i 1 (length$ $?recomendados)) do
		(bind ?ejercicio (nth$ ?i ?recomendados))
        (if (eq (type ?ejercicio) Duracion) then
        (bind ?nombre (string-to-field (instance-name ?ejercicio)))
        (bind ?nuevo (make-instance [?nombre] of Duracion (Intensidad (send ?ejercicio get-Intensidad))))
        (bind $?aRecomendar (insert$ $?aRecomendar (+ (length$ $?aRecomendar) 1) ?nuevo))
        (imprimeEjercicio ?nuevo))
        
        (if (eq (type ?ejercicio) Repeticiones) then
        (bind ?nombre (string-to-field (instance-name ?ejercicio)))
        (bind ?nuevo (make-instance [?nombre] of Repeticiones (Intensidad (send ?ejercicio get-Intensidad))))
        (bind $?aRecomendar (insert$ $?aRecomendar (+ (length$ $?aRecomendar) 1) ?nuevo)))
    )
    (assert (refinamiento (ejercicios $?aRecomendar)))
    
)