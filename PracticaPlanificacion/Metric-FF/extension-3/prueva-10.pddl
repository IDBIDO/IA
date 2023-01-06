
(define (problem prueva1)           ;; nombre del problema, da igual que le pongas
(:domain prueva)                    ;; nomble del dominio
(:objects   rover0 rover1 - rover         
            vivienda0 vivienda1 vivienda2 - asentamiento
            almacen0 almacen1 almacen2 - almacen
            agua comida  - suministro
            medico tecnico - personal
            x001 x002 x003 x004 x005 - id           ;; id de peticiones (creo que tiene que empezar con letra)
)

(:init

    ;; TODO SUMINISTRO O PERSONAL DECLARADO TIENE QUE CORRESPONDER CON UNO O MAS PETICIONES!
    ;; es decir, siempre habra igual o mas peticiones que suministro/personal. 

    ;; en que asentamiento estan los personales definidos    
    (= (personal_disponible medico vivienda0) 1)        ;; hay 1 medico en la vivivienda0
    (= (personal_disponible tecnico vivienda1) 1)

    ;; en que almacen estan los suministros definidos
    (= (suministro_disponible agua almacen2) 2)         ;; hay 2 unidad de agua en almacen2
    (= (suministro_disponible comida almacen2) 1)

    
    ;; definicion del estado del rover0
    (=(combustible rover0) 100)         ;; tiene 100 unidad de combustible (puede hace 100 movimientos) 
    (estacionado rover0 vivienda0)      ;; esta estacionado en la vivienda0
    (=(personal_en_rover rover0 ) 0)    ;; al principio no puede llevar ningun personal
    (=(suministro_en_rover rover0) 0)   ;; al principio no puede llevar ningun suministro


    (=(combustible rover1) 100)
    (estacionado rover1 almacen1)
    (=(personal_en_rover rover1 ) 0)        
    (=(suministro_en_rover rover1) 0)

    (=(combustible_total) 200)          ;; suma de todo combustible de rovers

    ;; PETICIONES: el destino de las peticiones tiene que ser un asentamiento. 
    (=(peso_total_prioridades_hechas)0)     ;; al principio no hay ninguna peticion hecha, por lo que la acumulacion de prioridades es 0

    (contenido_peticion x001 agua)          ;; definir el contenido de la peticion
    (destino_peticion x001 vivienda0)       ;; definir el destino de la peticion
    (=(prioridad_peticion x001) 3)          ;; definir la prioridad de la peticio (1, 2, 3)

    (contenido_peticion x002 comida)        
    (destino_peticion x002 vivienda2) 
    (=(prioridad_peticion x002) 3)

    (contenido_peticion x003 medico)     
    (destino_peticion x003 vivienda1) 
    (=(prioridad_peticion x003) 3)

    (contenido_peticion x004 tecnico)     
    (destino_peticion x004 vivienda2) 
    (=(prioridad_peticion x004) 3)

    (contenido_peticion x005 agua)     
    (destino_peticion x005 vivienda2) 
    (=(prioridad_peticion x005) 1)

    ( = (peticiones_hechas) 0)          ;; al principio no hay ninguna peticion hecha

    ;; definir el grafo de conexiones (no dirigido)
    (conectado vivienda0 vivienda1) (conectado vivienda0 vivienda2) (conectado vivienda0 almacen0)
    (conectado almacen0 almacen1) (conectado almacen0 almacen2)

)

;; no se puede envia ningun suministro mas
(:goal (   =(peticiones_hechas) 5     )     )       ;; este numero tiene que ser igual a la suma de 
                                                    ;; personal_disponible y suministro_disponible declarados al principio, le llamamos sum
                                                    ;; sum tiene que ser menor o igual al numero de peticiones
                                                    
;;(forall (?t - transportable) (servido ?t))

;un-comment the following line if metric is needed
;(:metric minimize (???))

;;(:metric maximize (+  (combustible rover0) (combustible rover1)))

;; comustible + 2*prioridades_hechas
;; combustible = combustible_rover0 + combustible_rover1 ....
(:metric maximize (+ (combustible_total) (*(peso_total_prioridades_hechas)10)     ;; esto habra que probarlo en el juego de pruebas
                  )




)              

)
