(define (problem prueva1)
(:domain prueva) 
(:objects  
rover0 rover1 rover2 - rover
almacen0 almacen1 almacen2 - almacen
vivienda0 vivienda1 vivienda2 - asentamiento
agua comida  - suministro
medico tecnico - personal
x1 x2 x3 x4 x5 x6 x7 x8 x9 - id
)
(:init
;Tamaño del problema (generación del script)   3

(= (personal_disponible tecnico vivienda2) 1)
(= (personal_disponible medico vivienda2) 1)
(= (suministro_disponible comida almacen0) 1)
(= (suministro_disponible agua almacen1) 2)
(= (suministro_disponible comida almacen2) 1)



(=(combustible rover0) 46)
(estacionado rover0 almacen0)
(=(personal_en_rover rover0 ) 0)
(=(suministro_en_rover rover0 ) 0)


(=(combustible rover1) 179)
(estacionado rover1 almacen2)
(=(personal_en_rover rover1 ) 0)
(=(suministro_en_rover rover1 ) 0)


(=(combustible rover2) 115)
(estacionado rover2 almacen2)
(=(personal_en_rover rover2 ) 0)
(=(suministro_en_rover rover2 ) 0)


(=(combustible_total) 340)


(=(peso_total_prioridades_hechas)0)

(contenido_peticion x1 medico)
(destino_peticion x1 vivienda2)
(=(prioridad_peticion x1 ) 2)

(contenido_peticion x2 agua)
(destino_peticion x2 vivienda1)
(=(prioridad_peticion x2 ) 1)

(contenido_peticion x3 comida)
(destino_peticion x3 vivienda2)
(=(prioridad_peticion x3 ) 2)

(contenido_peticion x4 tecnico)
(destino_peticion x4 vivienda0)
(=(prioridad_peticion x4 ) 1)

(contenido_peticion x5 comida)
(destino_peticion x5 vivienda2)
(=(prioridad_peticion x5 ) 1)

(contenido_peticion x6 agua)
(destino_peticion x6 vivienda1)
(=(prioridad_peticion x6 ) 1)

(contenido_peticion x7 agua)
(destino_peticion x7 vivienda1)
(=(prioridad_peticion x7 ) 1)

(contenido_peticion x8 agua)
(destino_peticion x8 vivienda1)
(=(prioridad_peticion x8 ) 1)

(contenido_peticion x9 comida)
(destino_peticion x9 vivienda2)
(=(prioridad_peticion x9 ) 1)


( = (peticiones_hechas) 0)

(conectado almacen2 vivienda0)
(conectado vivienda2 almacen2)
(conectado vivienda0 vivienda2)
(conectado vivienda1 almacen2)
(conectado vivienda0 almacen0)
(conectado vivienda1 vivienda2)
(conectado vivienda1 vivienda0)
(conectado almacen2 almacen0)
(conectado almacen0 vivienda1)
(conectado almacen1 almacen2)
(conectado almacen0 almacen1)
(conectado vivienda1 almacen1)
(conectado almacen1 vivienda0)
(conectado almacen1 vivienda2)
(conectado almacen0 vivienda2)

)
(:goal (   =(peticiones_hechas) 6     )     )
(:metric maximize (+ (combustible_total) (*(peso_total_prioridades_hechas)13)))
)
