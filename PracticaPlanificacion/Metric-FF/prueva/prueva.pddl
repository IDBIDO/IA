(define (domain prueva)

    (:requirements :typing :adl :fluents)

    (:types base rover id object
            asentamiento almacenen - base
            suministro personal - transportable
            
    )

    (:functions

        (transportable_disponible ?t - transportable ?b - base)      
        
        (personal_en_rover ?r - rover)          ;; num persona cargados en rover

        (combustible ?r - rover)

        (peticiones_hechas)                   ;; para ver si hemos terminado
        
    )

    (:predicates
        

        (contenido_peticion ?id - id ?t - transportable)         ;; continido de la peticion ?id
        (destino_peticion ?id - id ?as - asentamiento)           ;; destino de la peticion ?id


        (peticion_rover ?id - id ?r - rover)                    ;; rover ?r carga con el contenido de la peticion ?id

        (estacionado ?r - rover ?b - base)            ;; rover estacionado en base ?b

        (conectado ?b1 ?b2 - base)

        
    )

    (:action cargar_transportable
        :parameters (
                ?r - rover
                ?b - base 
                ?id - id
                ?t - transportable
        )

        :precondition (and 
                            (estacionado ?r ?b)                     ;; ?r estacionado en ?b
                            (contenido_peticion ?id ?t)             ;; hay una peticion de ?t disponible
                            (> (transportable_disponible ?t ?b) 0)  ;; en ?b hay ?t para cargar
                            
        )
        
        ;; rover ?r carga con ?t
        ;; ?t en ?b disminuye una unidad
        :effect (and    
                        (peticion_rover ?id ?r)
                        (decrease (transportable_disponible ?t ?b) 1)
        
                )
    )

    (:action descargar_transportable
        :parameters (
                        ?r - rover
                        ?as - asentamiento
                        ?id - id
                        ?idx - id 
                        ?t - transportable
                    )


        :precondition (and 
                            (estacionado ?r ?as)
                            (peticion_rover ?id ?r)
                            (contenido_peticion ?id ?t)     ;; rover ?r lleva ?t

                            (destino_peticion ?idx ?as)     
                            (contenido_peticion ?idx ?t)    ;; peticion cualquiera con destino donde esta el rover
        )
        :effect (and    
                            (not (contenido_peticion ?id ?t))
                            (not (destino_peticion ?id ?as))
                            (increase (peticiones_hechas) 1)
                )
    )

    (:action mover_rover
        :parameters (?r - rover ?o - base ?d - base)
        :precondition (and  (estacionado ?r ?o) (or (conectado ?o ?d) (conectado ?d ?o))
        )
        :effect (and    (estacionado ?r ?d)
                        (not (estacionado ?r ?o))
                        (decrease (combustible ?r) 1)
                        
        )
    )
    
    
    
    


)