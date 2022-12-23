(define (domain prueva)

    (:requirements :typing :adl :fluents)

    (:types base rover id object
            asentamiento almacenen - base
            suministro personal - transportable
            
    )


    (:functions
        ;;(suministro_disponible ?s - suministro ?al - almacenen)  ;;suministro ?s disponible en almacen ?al
        ;;(personal_disponible ?p - personal ?as - asentamiento)  ;; personal ?p disponible en asentamiento ?as

        (transportable_disponible ?t - transportable ?b - base)    
        (peticiones_hechas) 

        ;;(personal_en_rover ?r - rover)
        ;;(suministro_en_rover ?r - rover)
        
    )

    (:predicates
        
        (peticion ?id - id ?t - transportable ?as - asentamiento)       ;; peticion

        (peticion_en_curso ?r - rover ?id - id)      ;; rover r ha cogido el transportable de la peticion ?id

        (estacionado ?r - rover ?b - base)            ;; rover estacionado en base ?b

        (conectado ?b1 ?b2 - base)

        
    )

    (:action cargar_transportable
        :parameters (?id - id               
                    ?t - transportable        
                    ?as - asentamiento
                    ?r - rover
                    ?b - base)

        :precondition (and 
            (peticion ?id ?t ?as)               ;; existe una peticion de ?t hecho por el asentamiento ?as
            (not (peticion_en_curso ?r ?id))    ;; esta peticion no lo ha cogido ningun rover
            (estacionado ?r ?b)                 ;; el rover esta estacionado en la base ?b
            ( > (transportable_disponible ?t ?b) 0 )    ;; en la base ?b donde esta el rover, hay un transportable ?t disponible
        )
        
        :effect (and    (peticion_en_curso ?r ?id)                      ;; guarda el id de la peticion con el rover
                        (decrease (transportable_disponible ?t ?b) 1)   ;; decrementar 
                )
    )

    (:action descargar_transportable
        :parameters (?r - rover
                     ?as - asentamiento
                    ?idr - id
                    ?t - transportable
                    ?asx - asentamiento 
                    ?id - id
                    ?b - base
                    )
        ;;:precondition (and  (cogido ?r ?t)     
        ;;                    (estacionado ?r ?b)
        ;;                    (destino ?t ?b)
        ;;                )

        :precondition (and 
            (estacionado ?r ?as)                ;; rover estacionado en la asentamiento ?as
            (peticion_en_curso ?r ?idr)         ;; rover tiene una peticion cogida con ident. ?idr
            (peticion ?idr ?t ?asx)               ;; el rover tiene transportable ?t //// ?asx no nos importa
            (peticion ?id ?t ?as)               ;; ?as donde esta el rover tiene una peticion de ?t con identificador ?id
        )
        :effect (and    ;;(en ?t ?b) 
                        ;;(not (cogido ?r ?t))
                        ;;(servido ?t)
                        (not (peticion ?id ?t ?as))
                        (not (peticion_en_curso ?r ?idr))
                        (increase (peticiones_hechas) 1)
                )
    )

    (:action mover_rover
        :parameters (?r - rover ?o - base ?d - base)
        :precondition (and  (estacionado ?r ?o) (or (conectado ?o ?d) (conectado ?d ?o))
        )
        :effect (and    (estacionado ?r ?d)
                        (not (estacionado ?r ?o))
        )
    )
    
    
    
    


)