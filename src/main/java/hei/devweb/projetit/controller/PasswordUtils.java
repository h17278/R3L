package hei.devweb.projetit.controller;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PasswordUtils {
    private static final int LONGUEUR_SEL = 128;
    private static final int LONGUEUR_HASH = 128;

    private static final int NOMBRE_ITERATIONS = 5;
    private static final int MEMOIRE = 65536;
    private static final int PARALLELISME = 1;

    static final Logger LOGGER = LogManager.getLogger();

    public static Argon2 instancierArgon2() {
        return Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i, LONGUEUR_SEL, LONGUEUR_HASH);
    }

    public static String genererMotDePasse(String motDePasse) {
        LOGGER.debug("method genrerMotDePasse called");
        return instancierArgon2().hash(NOMBRE_ITERATIONS, MEMOIRE, PARALLELISME, motDePasse);
    }

    public static boolean validerMotDePasse(String motDePasse, String hashCorrect) {
        LOGGER.debug("method validerMotDePasse called");
        return instancierArgon2().verify(hashCorrect, motDePasse);
    }

    public static void main(String[] args) {
        System.out.println(genererMotDePasse("mdp"));
    }
}
