;;./ff -o prueva/prueva.pddl -f prueva/prueva-10.pddl
;; ./ff -o rovers/domain.pddl -f rovers/pfile01
;; ./ff -o SmartBus-domain-fluents-v0.pddl -f SmartBus-prob1-fluents-v0.pddl
(define (problem prueva1) 
(:domain prueva)
(:objects   rover0 - rover
            vivienda0 vivienda1 vivienda2 - asentamiento
            almacen0 almacen1 almacen2 - almacenen
            agua comida  - suministro
            medico tecnico - personal
            x001 x002 x003 x004 x005 - id
)

(:init
    ;;posicion de las peticiones ;; base de peticion  ;; 
    (= (transportable_disponible medico vivienda0) 1)
    (= (transportable_disponible tecnico vivienda0) 1)
    ;;(= (transportable_disponible agua almacen0) 2)
    ;;(= (transportable_disponible comida almacen1) 2)
    (= (transportable_disponible agua almacen2) 1)
    (= (transportable_disponible comida almacen2) 1)

    (estacionado rover0 almacen0)

    ;; PETICIONES
    (peticion x001 agua vivienda0)

    (peticion x003 medico vivienda1)

    (peticion x002 comida vivienda2)
    (peticion x004 tecnico vivienda2)
    (peticion x005 agua vivienda2)

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
)
