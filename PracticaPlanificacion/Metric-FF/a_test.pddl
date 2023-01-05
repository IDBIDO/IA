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

(= (personal_disponible medico vivienda0) 1)
(= (suministro_disponible agua almacen0) 1)
(= (suministro_disponible comida almacen0) 1)
(= (suministro_disponible comida almacen1) 1)



(=(combustible rover0) 59)
(estacionado rover0 almacen0)
(=(personal_en_rover rover0 ) 0)
(=(suministro_en_rover rover0 ) 0)


(=(combustible rover1) 39)
(estacionado rover1 vivienda0)
(=(personal_en_rover rover1 ) 0)
(=(suministro_en_rover rover1 ) 0)


(=(combustible_total) 98)


(=(peso_total_prioridades_hechas)0)

(contenido_peticion x1 comida)
(destino_peticion x1 vivienda1)
(=(prioridad_peticion x1 ) 1)

(contenido_peticion x2 comida)
(destino_peticion x2 vivienda0)
(=(prioridad_peticion x2 ) 1)

(contenido_peticion x3 medico)
(destino_peticion x3 vivienda1)
(=(prioridad_peticion x3 ) 1)

(contenido_peticion x4 agua)
(destino_peticion x4 vivienda1)
(=(prioridad_peticion x4 ) 2)


( = (peticiones_hechas) 0)

(conectado almacen1 vivienda0)
(conectado vivienda1 almacen1)
(conectado vivienda1 almacen0)
(conectado almacen0 vivienda0)
(conectado almacen0 almacen1)
(conectado vivienda1 vivienda0)

)
(:goal (   =(peticiones_hechas) 6     )     )
(:metric maximize (+ (combustible_total) (*(peso_total_prioridades_hechas)10)))
)
