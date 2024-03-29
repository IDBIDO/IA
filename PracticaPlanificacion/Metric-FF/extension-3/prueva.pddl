(define (domain prueva)

    (:requirements :strips :typing :adl :fluents)


    (:types base rover id object
            asentamiento almacen - base
            suministro personal - transportable
            
    )

    (:functions
    

        (suministro_disponible ?s - suministro ?alm - almacen)
        (personal_disponible ?p - personal ?ase - asentamiento)

        (personal_en_rover ?r - rover)          ;; num persona cargados en rover
        (suministro_en_rover ?r - rover)        ;; num suministro en rover
        (combustible ?r - rover)

        (combustible_total_gastado)         ;; suma de todo combustible de rovers

        (peticiones_hechas)                   ;; para ver si hemos terminado

        (prioridad_peticion ?id - id)        ;; entre 1-3, 3 la maxima

        (peso_total_prioridades_hechas)             
        
        
    )

    (:predicates
        

        (contenido_peticion ?id - id ?t - transportable)         ;; continido de la peticion ?id
        (destino_peticion ?id - id ?as - asentamiento)           ;; destino de la peticion ?id


        (peticion_rover ?id - id ?r - rover)                    ;; rover ?r carga con el contenido de la peticion ?id
        

        (estacionado ?r - rover ?b - base)            ;; rover estacionado en base ?b

        (conectado ?b1 ?b2 - base)

        
    )

    (:action cargar_suministro
        :parameters (
                ?r - rover
                ?alm - almacen
                ?id - id
                ?s - suministro

               ?rx - rover
               ?asx - asentamiento
        )

        :precondition (and 
                            (= (personal_en_rover ?r) 0)            ;; no hay personal en rover
                            (< (suministro_en_rover ?r) 1)          ;; menos de 1 suministro en rover
                            (estacionado ?r ?alm)                     ;; ?r estacionado en almacen ?alm

                            (contenido_peticion ?id ?s)             ;; hay una peticion de suministro ?s disponible
                            (destino_peticion ?id ?asx)             ;; 

                            (> (suministro_disponible ?s ?alm) 0)  ;; en almacen ?alm hay ?s para cargar

                            (not(peticion_rover ?id ?rx))           ;; ningun otro rover ha cogido el contenido del pedido
        )
        
        ;; rover ?r carga con ?t
        ;; ?t en ?b disminuye una unidad
        :effect (and    
                        (peticion_rover ?id ?r)             ;; rover ?r ha cogido la peticion ?id
                        (decrease (suministro_disponible ?s ?alm) 1)
                        (increase (suministro_en_rover ?r) 1)
                        ;;(increase (peso_total_prioridades_hechas) (prioridad_peticion ?id) )     ;; incrementar peso 
                        (when (=(prioridad_peticion ?id)3) (increase (peso_total_prioridades_hechas) 1))
                        (when (=(prioridad_peticion ?id)2) (increase (peso_total_prioridades_hechas) 2))
                        (when (=(prioridad_peticion ?id)1) (increase (peso_total_prioridades_hechas) 3))
                )
    )

    (:action cargar_personal
        :parameters (
                ?r - rover
                ?as - asentamiento 
                ?id - id
                ?p - personal

               ?rx - rover
               ?asx - asentamiento
        )

        :precondition (and 
                            (= (suministro_en_rover ?r) 0)          ;; no hay suministros en rover
                            (< (personal_en_rover ?r) 2)            ;; menos de 2 personal
                            (estacionado ?r ?as)                     ;; ?r estacionado en un asentamiento ?as

                            (contenido_peticion ?id ?p)             ;; hay una peticion de personal ?p disponible
                            (destino_peticion ?id ?asx)              ;; con destino asentamiento cualquiera -----------------

                            (> (personal_disponible ?p ?as) 0)         ;; en ?as hay ?p para cargar

                            (not(peticion_rover ?id ?rx))           ;; ningun otro rover ha cogido el contenido del pedido
        )
        
        ;; rover ?r carga con ?t
        ;; ?t en ?b disminuye una unidad
        :effect (and    
                        (peticion_rover ?id ?r)             ;; rover ?r ha cogido la peticion ?id
                        (decrease (personal_disponible ?p ?as) 1)
                        (increase (personal_en_rover ?r) 1)
                        ;;(increase (peso_total_prioridades_hechas) (prioridad_peticion ?id) )     ;; incrementar peso 
                        (when (=(prioridad_peticion ?id)3) (increase (peso_total_prioridades_hechas) 1))
                        (when (=(prioridad_peticion ?id)2) (increase (peso_total_prioridades_hechas) 2))
                        (when (=(prioridad_peticion ?id)1) (increase (peso_total_prioridades_hechas) 3))
                )
    )
    


    (:action descargar_suministro
        :parameters (
                        ?r - rover
                        ?as - asentamiento
                        ?id - id
    
                        ?s - suministro
                    )


        :precondition (and 
                            (estacionado ?r ?as)
                            (peticion_rover ?id ?r)         ;; rover ha cogido la peticion ?id

                            (contenido_peticion ?id ?s)     ;; rover ?r lleva el suministro ?s
                            (destino_peticion ?id ?as)     ;; peticion del rover tiene como destino el asentamiento donde esta aparcao


        )
        :effect (and    
                            (not (peticion_rover ?id ?r))   ;; el rover ya no tiene la peticion ?id
                            (not (contenido_peticion ?id ?s))  ;; la peticion ?id esta hecha
                            (not (destino_peticion ?id ?as))   

                            (increase (peticiones_hechas) 1)
                            (decrease (suministro_en_rover ?r) 1)
                            ;;(increase (peso_total_prioridades_hechas) (prioridad_peticion ?id) )     ;; incrementar peso 
                )
    )


    (:action descargar_personal
        :parameters (
                        ?r - rover
                        ?as - asentamiento
                        ?id - id
                        ?p - personal
                    )


        :precondition (and 
                            (estacionado ?r ?as)
                            (peticion_rover ?id ?r)

                            (contenido_peticion ?id ?p)     ;; rover ?r lleva un personal ?p
                            (destino_peticion ?id ?as)     
                            
        )
        :effect (and    
                            (not (peticion_rover ?id ?r))
                            (not (contenido_peticion ?id ?p))   
                            (not (destino_peticion ?id ?as))
                            (increase (peticiones_hechas) 1)
                            (decrease (personal_en_rover ?r) 1)
                            ;;(increase (peso_total_prioridades_hechas) (prioridad_peticion ?id) )     ;; incrementar peso 
                )
    )

    (:action mover_rover
        :parameters (?r - rover ?o - base ?d - base)
        :precondition (and  (estacionado ?r ?o)
                             (or (conectado ?o ?d) (conectado ?d ?o))
                             (> (combustible ?r) 0)
                             
        )
        :effect (and    (estacionado ?r ?d)
                        (not (estacionado ?r ?o))
                        (decrease (combustible ?r) 1)
                        (increase (combustible_total_gastado) 1)
        )
    )
    
    
    
    


)