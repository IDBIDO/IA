(define (domain basico)

    (:requirements :strips :typing :adl :fluents)

    (:types base rover id object
            asentamiento almacen - base
            suministro personal - transportable
            
    )
    (:functions

        (transportable_disponible ?t - transportable ?b - base)      

        ;;(peticiones_hechas)                   ;; para ver si hemos terminado
        
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

               ?rx - rover
        )

        :precondition (and 
                            (estacionado ?r ?b)                     ;; ?r estacionado en ?b
                            (contenido_peticion ?id ?t)             ;; hay una peticion de ?t disponible
                            (> (transportable_disponible ?t ?b) 0)  ;; en ?b hay ?t para cargar

                            (not(peticion_rover ?id ?rx))           ;; ningun otro rover ha cogido el contenido del pedido
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
                     
                        ?t - transportable
                    )


        :precondition (and 
                            (estacionado ?r ?as)
                            (peticion_rover ?id ?r)
                            
                            (contenido_peticion ?id ?t)     ;; rover ?r lleva ?t
                            (destino_peticion ?id ?as)     
        )
        :effect (and    
                            (not (contenido_peticion ?id ?t))
                            (not (destino_peticion ?id ?as))
                            (increase (peticiones_hechas) 1)
                        
                )
    )

    (:action mover_rover
        :parameters (?r - rover ?o - base ?d - base)
        :precondition (and  (estacionado ?r ?o)
                             (or (conectado ?o ?d) (conectado ?d ?o))
             
                             
        )
        :effect (and    (estacionado ?r ?d)
                        (not (estacionado ?r ?o))

                        
        )
    )
    
    
    
    


)