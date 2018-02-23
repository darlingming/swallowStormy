package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLogger  {
    protected Logger logger = null;

    public AbstractLogger(Class cls) {
        logger = LoggerFactory.getLogger(cls);
    }

}
