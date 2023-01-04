(define (domain prueva)

    (:requirements :typing :adl)

    (:types base rover - object
            asentamiento almacen - base
            suministro personal - transportable
            
    )

    ;;(:functions
    ;;    (suministro_disponible ?al - almacenen ?s - suministro)
    ;;    (personal_disponible ?as - asentamiento ?p - personal)

    ;;)

    (:predicates
        
        (en ?t - transportable ?b - base)              ;; transportable p esta en la base b
        (estacionado ?r - rover ?b - base)             ;; rover r estacionado en la base b
        (destino ?t - transportable ?b - base)           ;; hay una peticion del transportable t con destino b

        ;; estados del transportable
        (pendiente ?t - transportable)                 ;; transportable t pendiente para ser cogido por un rover
        (servido ?t - transportable)                      ;; transportable t sevido      

        (cogido ?r - rover ?t - transportable)      ;; rover r ha cogido el transportable t

        ;;(reservado ?b - base ?t - transportable)

        (conectado ?b1 ?b2 - base)
        
    )

    (:action cargar_transportable
        :parameters (?r - rover ?b - base ?t - transportable)
        :precondition (and (pendiente ?t) (en ?t ?b) 
                            (estacionado ?r ?b) 
                        )
    
        
        :effect (and    (cogido ?r ?t) 
                        (not ( en ?t ?b ))
                        (not ( pendiente ?t))
                )
    )

    (:action descargar_transportable
        :parameters (?r - rover ?b - base ?t - transportable)
        :precondition (and  (cogido ?r ?t)     
                            (estacionado ?r ?b)
                            (destino ?t ?b)
                        )
        :effect (and    (en ?t ?b) 
                        (not (cogido ?r ?t))
                        (servido ?t)
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