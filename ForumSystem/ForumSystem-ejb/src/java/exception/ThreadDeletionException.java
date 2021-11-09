/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author jiajun
 */
public class ThreadDeletionException extends Exception {

    /**
     * Creates a new instance of <code>ThreadDeletionException</code> without
     * detail message.
     */
    public ThreadDeletionException() {
    }

    /**
     * Constructs an instance of <code>ThreadDeletionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ThreadDeletionException(String msg) {
        super(msg);
    }
}
