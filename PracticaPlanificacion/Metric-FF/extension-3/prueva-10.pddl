;;./ff -o prueva/prueva.pddl -f prueva/prueva-10.pddl
;; ./ff -o rovers/domain.pddl -f rovers/pfile01
;; ./ff -o SmartBus-domain-fluents-v0.pddl -f SmartBus-prob1-fluents-v0.pddl
(define (problem prueva1) 
(:domain prueva)
(:objects   rover0 - rover
            vivienda0 vivienda1 vivienda2 - asentamiento
            almacen0 almacen1 almacen2 - almacen
            agua comida  - suministro
            medico tecnico - personal
            x001 x002 x003 x004 x005 - id
)

(:init
    ;;posicion de las peticiones ;; base de peticion  ;; 
    (= (personal_disponible medico vivienda0) 1)
    (= (personal_disponible tecnico vivienda1) 1)
    ;;(= (transportable_disponible agua almacen0) 2)
    ;;(= (transportable_disponible comida almacen1) 2)
    (= (suministro_disponible agua almacen2) 2)
    (= (suministro_disponible comida almacen2) 1)

    
    (=(combustible rover0) 100)
    (estacionado rover0 vivienda0)
    (=(personal_en_rover rover0 ) 0)        
    (=(suministro_en_rover rover0) 0)

    ;(=(combustible rover1) 100)
    ;(estacionado rover1 almacen1)
    ;(=(personal_en_rover rover1 ) 0)        
    ;(=(suministro_en_rover rover1) 0)


    ;; PETICIONES
    (=(peso_total_prioridades_hechas)0)

    (contenido_peticion x001 agua)     
    (destino_peticion x001 vivienda0)
    (=(prioridad_peticion x001) 3)

    (contenido_peticion x003 medico)     
    (destino_peticion x003 vivienda1) 
    (=(prioridad_peticion x003) 3)

    (contenido_peticion x002 comida)     
    (destino_peticion x002 vivienda2) 
    (=(prioridad_peticion x002) 3)

    (contenido_peticion x004 tecnico)     
    (destino_peticion x004 vivienda2) 
    (=(prioridad_peticion x004) 3)

    (contenido_peticion x005 agua)     
    (destino_peticion x005 vivienda2) 
    (=(prioridad_peticion x005) 1)

    ( = (peticiones_hechas) 0)

    ;; definir el grafo de conexiones
    (conectado vivienda0 vivienda1) (conectado vivienda0 vivienda2) (conectado vivienda0 almacen0)
    (conectado almacen0 almacen1) (conectado almacen0 almacen2)

)

;; no se puede envia ningun suministro mas
(:goal (   =(peticiones_hechas) 4     )     )


;;(forall (?t - transportable) (servido ?t))

;un-comment the following line if metric is needed
;(:metric minimize (???))

;;(:metric maximize (+  (combustible rover0) (combustible rover1)))
(:metric maximize (*(combustible rover0)(peso_total_prioridades_hechas)



                  )




)              

)
