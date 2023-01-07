(define (problem prueva1)
(:domain prueva) 
(:objects  
rover0 rover1 rover2 rover3 - rover
almacen0 almacen1 almacen2 almacen3 - almacen
vivienda0 vivienda1 vivienda2 vivienda3 - asentamiento
agua comida  - suministro
medico tecnico - personal
x1 x2 x3 x4 x5 x6 x7 x8 - id
)
(:init
;Tamaño del problema (generación del script)   4

(= (personal_disponible tecnico vivienda1) 4)
(= (personal_disponible medico vivienda1) 4)
;(= (personal_disponible medico vivienda2) 1)
(= (personal_disponible medico vivienda2) 4)
(= (personal_disponible tecnico vivienda3) 4)
(= (suministro_disponible agua almacen0) 4)
(= (suministro_disponible agua almacen1) 4)
(= (suministro_disponible comida almacen3) 4)



(=(combustible rover0) 134)
(estacionado rover0 vivienda1)
(=(personal_en_rover rover0 ) 0)
(=(suministro_en_rover rover0 ) 0)


(=(combustible rover1) 164)
(estacionado rover1 almacen1)
(=(personal_en_rover rover1 ) 0)
(=(suministro_en_rover rover1 ) 0)


(=(combustible rover2) 116)
(estacionado rover2 almacen3)
(=(personal_en_rover rover2 ) 0)
(=(suministro_en_rover rover2 ) 0)


(=(combustible rover3) 103)
(estacionado rover3 vivienda1)
(=(personal_en_rover rover3 ) 0)
(=(suministro_en_rover rover3 ) 0)


(=(combustible_total) 517)


(=(peso_total_prioridades_hechas)0)

(contenido_peticion x1 agua)
(destino_peticion x1 vivienda0)
(=(prioridad_peticion x1 ) 1)

(contenido_peticion x2 tecnico)
(destino_peticion x2 vivienda0)
(=(prioridad_peticion x2 ) 1)

(contenido_peticion x3 medico)
(destino_peticion x3 vivienda0)
(=(prioridad_peticion x3 ) 2)

(contenido_peticion x4 medico)
(destino_peticion x4 vivienda0)
(=(prioridad_peticion x4 ) 1)

(contenido_peticion x5 tecnico)
(destino_peticion x5 vivienda2)
(=(prioridad_peticion x5 ) 2)

(contenido_peticion x6 medico)
(destino_peticion x6 vivienda1)
(=(prioridad_peticion x6 ) 2)

(contenido_peticion x7 comida)
(destino_peticion x7 vivienda2)
(=(prioridad_peticion x7 ) 1)

(contenido_peticion x8 agua)
(destino_peticion x8 vivienda0)
(=(prioridad_peticion x8 ) 1)


( = (peticiones_hechas) 0)

(conectado almacen3 vivienda1)
(conectado vivienda3 vivienda1)
(conectado vivienda0 vivienda2)
(conectado vivienda2 vivienda1)
(conectado almacen0 almacen2)
(conectado vivienda2 almacen1)
(conectado almacen3 vivienda3)
(conectado vivienda2 vivienda3)
(conectado vivienda1 almacen2)
(conectado vivienda0 vivienda3)


(conectado almacen0 vivienda1)
(conectado almacen0 vivienda3)
(conectado almacen2 almacen3)
(conectado almacen1 vivienda0)

)
(:goal (   =(peticiones_hechas) 5     )     )
(:metric maximize (+ (peso_total_prioridades_hechas) (*( combustible_total)12)))
)
