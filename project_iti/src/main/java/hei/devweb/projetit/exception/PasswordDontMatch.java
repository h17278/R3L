package hei.devweb.projetit.exception;

import org.apache.logging.log4j.LogManager;

public class PasswordDontMatch extends RuntimeException {

    static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();

    @Override
    public void printStackTrace() {
        LOGGER.debug("Password don't match");
    }
}
