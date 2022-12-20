;;./ff -o prueva/prueva.pddl -f prueva/prueva-10.pddl
(define (problem prueva1) 
(:domain prueva)
(:objects   rv0 - rover
            as0 as1 as2 - asentamiento
            alm0 alm1 alm2 - almacenen
            sum0 sum1 sum2 - suministro
            per0 per1 per2 - personal

)

(:init
    ;;posicion de las peticiones ;; base de peticion  ;; 
    (en sum0 alm0) (pendiente sum0) (destino sum0 alm1)     ;; enviar alm0 a alm1
    (en sum1 alm0) (pendiente sum1) (destino sum1 alm2);;
    (en sum2 alm0) (pendiente sum2) (destino sum2 alm1);;
    (en per0 as0)  (pendiente per0) (destino per0 as1)
    (en per1 as0)  (pendiente per1) (destino per1 as2)
    (en per2 as0)  (pendiente per2) (destino per2 as1)
    (estacionado rv0 as2)  ;; posicion del rover

    ;; definir el grafo de conexiones
    (conectado as0 as1) (conectado as0 as2) (conectado as0 alm0)
    (conectado alm0 alm1) (conectado alm0 alm2)

)

;; no se puede envia ningun suministro mas
(:goal (forall (?t - transportable) (servido ?t))
)

;un-comment the following line if metric is needed
;(:metric minimize (???))
)
