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
(conectado almacen0 vivienda0)
(conectado almacen3 almacen1)
(conectado almacen0 almacen3)
(conectado vivienda3 almacen1)
(conectado vivienda1 almacen1)
(conectado vivienda2 almacen2)
(conectado vivienda0 almacen3)
(conectado vivienda0 almacen2)
(conectado almacen0 almacen1)
(conectado almacen2 almacen1)
(conectado almacen0 vivienda2)
(conectado vivienda2 almacen3)
(conectado vivienda0 vivienda1)
(conectado almacen2 vivienda3)
(conectado almacen0 vivienda1)
(conectado almacen0 vivienda3)
(conectado almacen2 almacen3)
(conectado almacen1 vivienda0)

)
;(:goal (   =(peticiones_hechas) 6     )     )
;(:metric maximize (+ (combustible_total) (*(peso_total_prioridades_hechas)10)))
)

