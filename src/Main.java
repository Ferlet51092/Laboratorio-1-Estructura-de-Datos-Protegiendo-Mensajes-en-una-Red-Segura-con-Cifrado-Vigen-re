import java.util.Scanner;

class BigVigenere {
    private
    int[] key;
    char[][] alphabet;

    public BigVigenere() {
        Scanner claveScan = new Scanner(System.in);
        System.out.print("Ingrese clave: ");
        int clave = claveScan.nextInt();

        int [] guardarClave = new int[1];
        // Nuevo arreglo que almacena la clave del cifrado
        guardarClave[0]=clave;

        for (int i = 0; i < key.length; i++) {
            guardarClave[i] = clave;
            // Se le asigna el valor clave a cada elemento de key
        }
        this.key = guardarClave;

        String alfabeto = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";

        this.alphabet = new char[64][64];

        for (int i = 0; i < alfabeto.length(); i++) { // Genera la matriz
            for (int j = 0; j < alfabeto.length(); j++) {
                this.alphabet[i][j] = alfabeto.charAt((i + j ) % alfabeto.length());
                // Asigna un cararcter a cada casillero de la matriz, de manera ciclica y con el % para no salirse de los limites
            }
        }
    }
    public BigVigenere(String numericKey){
        this.key = new int[numericKey.length()];
        for(int i = 0; i < numericKey.length(); i++){
            this.key[i] = Character.getNumericValue(numericKey.charAt(i));
            // Convierte en valor numerico los caracteres
        }
        String alfabeto = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";

        this.alphabet = new char[64][64]; // Genera la matriz
        for (int i = 0; i < alfabeto.length(); i++) {
            for (int j = 0; j < alfabeto.length(); j++) {
                this.alphabet[i][j] = alfabeto.charAt((i + j ) % alfabeto.length());
            }
        }

    }
    public String encrypt(String message) {
        String cifrado = ""; // se inicializa de manera vacia
        String alfabeto = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
        int j = 0;
        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i); // caraacter edel mensaje
            if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9' || ch == 'ñ' || ch == 'Ñ') {
                char aux = alfabeto.charAt((alfabeto.indexOf(ch) + key[j % key.length])%64);
                cifrado += aux; // Se añade el caracter del cifrado
                j++;
            } else {
                cifrado += ch;
            }
        }
        return cifrado;
    }
    public String decrypt(String encryptedMessage){
        String mensaje_cifrado = "";
        String alfabeto = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
        int j = 0; // Recorre key

        for (int i = 0; i < encryptedMessage.length(); i++) {
            char cl = encryptedMessage.charAt(i);
            if ((cl >= 'a' && cl <= 'z') || (cl >= 'A' && cl <= 'Z') || (cl >= '0' && cl <= '9') || cl == 'ñ' || cl == 'Ñ') {
                int index = -1;
                // Se busca el caracter en el alfabeto, el -1 indica que no se encuentra en el
                for (int k = 0; k < alfabeto.length(); k++) {
                    if (alfabeto.charAt(k) == cl) {
                        index = k;
                        break;
                    }
                }
                if (index != -1) {
                    int clave = key[j % key.length];
                    int nuevaPos = (index - clave + 64) % 64;
                    char aux = alfabeto.charAt(nuevaPos);
                    mensaje_cifrado += aux;
                    j++;
                }
            } else {
                mensaje_cifrado += cl;
            }
        }
        return mensaje_cifrado;
    }
    public void reEncrypt(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el mensaje encriptado: ");
        String mensaje_encriptado = sc.nextLine();
        String new_mensaje = decrypt(mensaje_encriptado);// Utilizamos el metodo descrypt
        System.out.print("Ingrese nueva clave: ");
        String new_clave = sc.nextLine();
        this.key = new int[new_mensaje.length()]; // Utiliza la longitud del nuevo mensaje
        for (int i = 0; i < new_mensaje.length(); i++) {
            this.key[i] = Character.getNumericValue(new_clave.charAt(i % new_clave.length()));
            // Convierte los caracteres del nuevo mensaje en valores numericos
        }
        System.out.println("Nuevo mensaje encriptado: " + encrypt(new_mensaje));
    }
    public char search(int position){
        if (position < 0 || position >= 64 * 64) {
            // Verifica si la posicion esta fuera de los limites de la matriz
            System.out.println("La posición no existe");
            return '\0';
        }
        int k = 0;
        for (int i = 0; i < alphabet.length; i++) {
            for (int j = 0; j < alphabet[i].length; j++) {
                if (k == position) {
                    return alphabet[i][j];
                    // El for anidado recorre las casillas hasta encontrar a k
                }
                k++;
            }
        }
        return '\0';
    }
    public char optimalSearch(int position) {
        // Busqueda Binaria
        if (position < 0 || position >= alphabet.length*alphabet[0].length) {
            System.out.println("La posicion no existe");
        }
        int inicio = 0;
        int fin = alphabet.length*alphabet.length - 1;

        while (inicio <= fin) {
            int k = inicio + (fin - inicio) / 2; // Calcula la mitad
            if (k == position) {
                return alphabet[position / alphabet[0].length][position % alphabet[0].length];
            }else if(k<position) { // Busca por la mitad de la derecha
                inicio = k+1;
            }else if(k>position) { // Busca la mitad de la izquierda
                fin = k-1;
            }
        }
        return 0;

    }
}