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

(= (personal_disponible tecnico vivienda1) 1)
(= (personal_disponible medico vivienda2) 1)
(= (suministro_disponible agua almacen1) 1)
(= (suministro_disponible comida almacen1) 1)
(= (suministro_disponible agua almacen2) 2)



(=(combustible rover0) 141)
(estacionado rover0 almacen1)
(=(personal_en_rover rover0 ) 0)
(=(suministro_en_rover rover0 ) 0)


(=(combustible rover1) 97)
(estacionado rover1 almacen0)
(=(personal_en_rover rover1 ) 0)
(=(suministro_en_rover rover1 ) 0)


(=(combustible rover2) 129)
(estacionado rover2 vivienda1)
(=(personal_en_rover rover2 ) 0)
(=(suministro_en_rover rover2 ) 0)


(=(combustible_total) 367)


(=(peso_total_prioridades_hechas)0)

(contenido_peticion x1 agua)
(destino_peticion x1 vivienda0)
(=(prioridad_peticion x1 ) 2)

(contenido_peticion x2 agua)
(destino_peticion x2 vivienda1)
(=(prioridad_peticion x2 ) 2)

(contenido_peticion x3 comida)
(destino_peticion x3 vivienda0)
(=(prioridad_peticion x3 ) 2)

(contenido_peticion x4 medico)
(destino_peticion x4 vivienda1)
(=(prioridad_peticion x4 ) 1)

(contenido_peticion x5 tecnico)
(destino_peticion x5 vivienda0)
(=(prioridad_peticion x5 ) 2)

(contenido_peticion x6 agua)
(destino_peticion x6 vivienda2)
(=(prioridad_peticion x6 ) 2)

(contenido_peticion x7 tecnico)
(destino_peticion x7 vivienda0)
(=(prioridad_peticion x7 ) 2)

(contenido_peticion x8 comida)
(destino_peticion x8 vivienda0)
(=(prioridad_peticion x8 ) 2)

(contenido_peticion x9 agua)
(destino_peticion x9 vivienda1)
(=(prioridad_peticion x9 ) 2)


( = (peticiones_hechas) 0)

(conectado vivienda2 almacen0)
(conectado vivienda0 vivienda2)
(conectado almacen1 almacen0)
(conectado almacen2 almacen1)
(conectado almacen2 vivienda2)
(conectado almacen2 almacen0)
(conectado almacen1 vivienda1)
(conectado almacen1 vivienda2)
(conectado almacen1 vivienda0)
(conectado vivienda0 almacen2)
(conectado vivienda1 almacen2)
(conectado vivienda1 vivienda2)
(conectado vivienda1 almacen0)
(conectado almacen0 vivienda0)
(conectado vivienda1 vivienda0)

)
(:goal (   =(peticiones_hechas) 6     )     )
(:metric maximize (+ (combustible_total) (*(peso_total_prioridades_hechas)15)))
)
