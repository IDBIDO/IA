import random

# Clases para representar las bases y los rovers
class Vivienda:
    def __init__(self, nombre):
        self.nombre = nombre
        self.suministros = []

class Rover:
    def __init__(self, nombre, base_inicial,combustible):
        self.nombre = nombre
        self.base_actual = base_inicial
        self.combustible = combustible

class Almacen:
    def __init__(self, nombre, base_inicial):
        self.nombre = nombre
        self.base_actual = base_inicial
        self.suministros = []

class Suministro:
    def __init__(self, nombre, base_inicial,unidades):
        self.nombre = nombre
        self.base_actual = base_inicial
        self.unidades = unidades
        
class Peticion:
    def __init__(self, id, base_inicial,base_final,prioridad):
        self.id = id
        self.base_actual = base_inicial
        self.base_final = base_final
        self.prioridad = prioridad

# Función para generar un juego de prueba aleatorio
def generar_juego_prueba():
    # Generamos las bases
    tamañoDelProblema = random.randrange(2,10)
    viviendas = []
    for i in range(0,tamañoDelProblema):
        base = Vivienda("vivienda"+str(i))
        viviendas.append(base)
    
    almacenes = []
    for i in range(0,tamañoDelProblema):
        base = Almacen("almacen"+str(i), [])
        almacenes.append(base)
        
    # Generamos los rovers y los aparcamos en una base aleatoria
    rovers = []
    for i in range(0,tamañoDelProblema):
        rover = Rover(f"r{i+1}", random.choice(almacenes+viviendas),random.randrange(10,len(almacenes+viviendas)*10*tamañoDelProblema))
        rovers.append(rover)

    # Generamos las conexiones entre las bases
    conexiones = []
    for i in range(tamañoDelProblema*tamañoDelProblema*10):
        base1 = random.choice(almacenes+viviendas)
        base2 = random.choice(almacenes+viviendas)
        if (base1, base2) in conexiones or (base2, base1) in conexiones or base1 == base2:
            continue
        conexiones.append((base1, base2))
        
    suministros = []
    for i in range(0,tamañoDelProblema*2):
        nombres = ["agua","comida","tecnico","medico"]
        tipo = random.choice(nombres)
        if tipo =="agua" or tipo == "comida":
            suministro = Suministro(tipo, random.choice(almacenes), random.randrange(1,tamañoDelProblema))
        else:
            suministro = Suministro(tipo, random.choice(viviendas), random.randrange(1,tamañoDelProblema))

        suministro.base_actual.suministros.append(suministro)
        suministros.append(suministro)
    
    peticiones = []
    for i in range(0,len(suministros)):
        peticion = Peticion(i, suministros[i].base_actual, random.choice(viviendas), random.randrange(1,3))
        peticiones.append(peticion)
    
    
    print("(:objects  ")
    for i in range(0,len(rovers)):
        print("rover"+str(i)+" ",end='')
    print("- rover")
    
    for i in range(0,len(almacenes)):
        print("almacen"+str(i)+" ",end='')
    print("- almacen")
    
    for i in range(0,len(viviendas)):
        print("asentamiento"+str(i)+" ",end='')
    print("- asentamiento")
    
    print("agua comida  - suministro")
    print("medico tecnico - personal")
    
    for i in range(0,len(peticiones)):
        print("x"+str(i+1)+" ",end='')
    print("- id\n)")
    
    print("(:init")
    print(";Tamaño del problema (generación del script)   "+str(tamañoDelProblema)+"\n")


    for i in range(0,len(viviendas)):
        for j in range(0,len(viviendas[i].suministros)):
            print("= (personal_disponible "+viviendas[i].suministros[j].nombre+" vivienda"+str(i)+") 1)")
    
    for i in range(0,len(almacenes)):
        for j in range(0,len(almacenes[i].suministros)):
            print("= (suministro_disponible "+almacenes[i].suministros[j].nombre+" almacen"+str(i)+") 1)")

    print("\n\n")
    
    for i in range(0,len(rovers)):
        print("(=(combustible rover"+str(i)+") "+str(rovers[i].combustible)+")")
        print("(estacionado rover"+str(i)+" "+rovers[i].base_actual.nombre+")")
        print("(=(personal_en_rover rover"+str(i)+" ) 0)")
        print("(=(suministro_en_rover rover"+str(i)+" ) 0)")
        print("\n")
        
    print("(=(combustible_total) "+str(sum([i.combustible for i in rovers]))+")")
    print("\n")
    print("(=(peso_total_prioridades_hechas)0)\n")
    
    
    for i in range(0,len(peticiones)):
        print("(contenido_peticion x"+str(i+1)+" "+suministros[i].nombre+")")
        print("(destino_peticion x"+str(i+1)+" "+peticiones[i].base_final.nombre+")")
        print("(=(prioridad_peticion x"+str(i+1)+" "+") "+str(peticiones[i].prioridad)+")\n")
    
    print("\n( = (peticiones_hechas) 0)\n")
    
    
    for (base1,base2) in conexiones:
        print("(conectado "+base1.nombre+" "+base2.nombre+")")
    print("\n)")
    
    print("(:goal (   =(peticiones_hechas) "+str(len(peticiones))+"     )     )")

# Creamos un juego de prueba con 3 bases, 2 rovers y 3 conexiones
juego_prueba = generar_juego_prueba()
