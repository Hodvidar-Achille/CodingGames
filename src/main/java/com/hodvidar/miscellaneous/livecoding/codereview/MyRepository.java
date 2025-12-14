package com.hodvidar.miscellaneous.livecoding.codereview;

import java.util.logging.Logger;

public class MyRepository {

    private static final Logger logger = Logger.getLogger(MyRepository.class.getName());
    /*
    Mettez à jour le code en appliquant les règles suivantes :
    Si une exception est levée par s.execute() alors appeler c.rollback() et propager l'exception, sinon appeler c.commit()
    Dans tous les cas, c.close() doit être appelée avant de quitter la méthode a(Service s, Connection c)
     */
    /**
     * Executes the service with the given connection.
     */
    public static String a(Service s, Connection c) throws Exception {
        // update this code

        try {
            s.setConnection(c);
            s.execute();
            c.commit();
            return "OK";
        } catch (Exception e)  {
            c.rollback();
           return "BAD";
        } finally {
            c.close();
        }
    }

}

/* Interfaces sont ici pour simplifier la lecture du code */
interface Service {
    void execute() throws Exception;
    void setConnection(Connection c);
}

interface Connection extends AutoCloseable {
    void commit();
    void rollback();
    void close();
}

