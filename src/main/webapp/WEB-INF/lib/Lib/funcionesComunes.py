import re
from datetime import date

def Comprobar_DNI_NIE(dni):

    # Comprobamos la longitud
    if len(dni)<9 or len(dni)>9:
        print "Error: Longitud incorrecta, tiene que ser 9."
        return False
    # Convertimos las letras a mayuscula en caso de tener letras en minuscula
    dni = dni.upper()

    # Comprobamos que las caracteristicas del NIE se cumplan
    if re.match("[A-Z]", dni[0]):
        if 'X' in dni[0]:
            dni = dni.replace('X','0')
        elif 'Y' in dni[0]:
            dni = dni.replace('Y','1')
        elif 'Z' in dni[0]:
            dni = dni.replace('Z','2')
        else:
            print "Error: Se ha introducido un NIE incorrecto"
            return False

    # Comprobamos que tenemos 8 digitos numericos
    if not re.match("\d{8}", dni[:8]):
        print "Error: El DNI/NIE necesita tener 8 digitos numericos"
        return False

    # Comprobamos que tenemos una letra al final
    if not re.match("[A-Z]", dni[8]):
        print "Error: El DNI/NIE necesita una letra al final"
        return False

    # Comprobamos si la letra es correcta
    tabla = "TRWAGMYFPDXBNJZSQVHLCKE"
    if tabla[int(dni[:8]) % 23]==dni[8]:
        print "El DNI/NIE introducido es correcto"
        return True
    else:
        print "Error: El DNI/NIE introducido no es correcto"
        return False

def validate_cif(value):
    CIF = re.compile('(?P<letra>[A-H]|[J-N]|[P-S]|[UVW])(?P<numeracion>\d{7})(?P<control>[0-9A-J])')
    CIF_HYPHEN = re.compile('(?P<letra>[A-H]|[J-N]|[P-S]|[UVW])-(?P<numeracion>\d{7})(?P<control>[0-9A-J])')

    value = str(value).upper()
    match = CIF.match(value)
    if match is None:
        match = CIF_HYPHEN.match(value)
    if match is None:
        raise Exception

    LETRAS_CIF = 'ABCDEFGHJKLMNPQRSUVW' # Letras de CIF válidas
    DC_NUMERICO_CIF = 'ABEH' # CIFs con número como dígito de control
    DC_LETRA_CIF = 'KPQS' # CIFs con letra como dígito de control
    ALPHA_CIF = 'JABCDEFGHI' # Correspondencia entre letra y dígito de control
	
    letra = match.group('letra')
    numeracion = match.group('numeracion')
    control = match.group('control')    
	
    # No todas las letras son válidas
    if letra not in LETRAS_CIF:
        raise Exception
    # Obtenemos la información


    # Suma de los pares
    a = reduce(lambda x, y: int(x)+int(y), numeracion[1::2])
    # Suma de la suma de los dígitos de los impares * 2
    b = sum(
        [
            int(reduce(lambda x,y: int(x)+int(y), impar))
            for impar in map(lambda x: str(int(x)*2), numeracion[::2])
        ]
    )
    c = a + b
    e = c % 10
    d = (10-e) % 10

    if letra in DC_NUMERICO_CIF:
        d = str(d) # Es numérico
    elif letra in DC_LETRA_CIF:
        d = ALPHA_CIF[d] # Es una letra
    else:
        # Puede ser tanto letra como número
        if control.isalpha():
            d = ALPHA_CIF[d]
        else:
            d = str(d)
    if d != control:
        raise Exception
    return '%s%s%s' % (letra, numeracion, control)
	
	
def validate_nif(value):
    value = unicode(value).upper()
    match = re.match('(?P<dni>\d{8})(?P<letra>[A-Z])', value)
    if match is None:
        match = re.match('(?P<dni>\d{8})-(?P<letra>[A-Z])', value)
    if match is None:
        raise Expection

    dni = int(match.group('dni'))
    letra = match.group('letra')

    LETRAS_NIF = 'TRWAGMYFPDXBNJZSQVHLCKE'
    if LETRAS_NIF[dni%23] != letra:
        raise Expection
    return unicode('%s%s' % (dni, letra))
    
def edad(value):
	age= 0
	if value != 'null' :
		fecha= value.split('/')
		dob= date(int(fecha[2]),int(fecha[1]),int(fecha[0]))
		today = date.today()
		age = today.year - dob.year
		birthday = date(today.year, dob.month, dob.day)
		if today < birthday :
			age -= 1 
	return age