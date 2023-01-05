(define (problem prueva1)
(:domain prueva) 
(:objects  
rover0 rover1 - rover
almacen0 almacen1 - almacen
vivienda0 vivienda1 - asentamiento
agua comida  - suministro
medico tecnico - personal
x1 x2 x3 x4 x5 x6 - id
)
(:init
;Tamaño del problema (generación del script)   2

(= (personal_disponible medico vivienda1) 1)
(= (suministro_disponible agua almacen0) 1)
(= (suministro_disponible comida almacen1) 2)



(=(combustible rover0) 35)
(estacionado rover0 vivienda0)
(=(personal_en_rover rover0 ) 0)
(=(suministro_en_rover rover0 ) 0)


(=(combustible rover1) 67)
(estacionado rover1 vivienda1)
(=(personal_en_rover rover1 ) 0)
(=(suministro_en_rover rover1 ) 0)


(=(combustible_total) 102)


(=(peso_total_prioridades_hechas)0)

(contenido_peticion x1 comida)
(destino_peticion x1 vivienda0)
(=(prioridad_peticion x1 ) 2)

(contenido_peticion x2 comida)
(destino_peticion x2 vivienda1)
(=(prioridad_peticion x2 ) 2)

(contenido_peticion x3 agua)
(destino_peticion x3 vivienda0)
(=(prioridad_peticion x3 ) 1)

(contenido_peticion x4 medico)
(destino_peticion x4 vivienda1)
(=(prioridad_peticion x4 ) 1)

(contenido_peticion x5 comida)
(destino_peticion x5 vivienda1)
(=(prioridad_peticion x5 ) 2)

(contenido_peticion x6 medico)
(destino_peticion x6 vivienda1)
(=(prioridad_peticion x6 ) 1)


( = (peticiones_hechas) 0)

(conectado almacen1 vivienda1)
(conectado vivienda0 almacen0)
(conectado almacen1 vivienda0)
(conectado vivienda1 almacen0)
(conectado almacen1 almacen0)
(conectado vivienda0 vivienda1)

)
(:goal (   =(peticiones_hechas) 4     )     )
(:metric maximize (+ (combustible_total) (*(peso_total_prioridades_hechas)9)))
)
