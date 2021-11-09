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
public class PostDeletionException extends Exception {

    /**
     * Creates a new instance of <code>PostDeletionException</code> without
     * detail message.
     */
    public PostDeletionException() {
    }

    /**
     * Constructs an instance of <code>PostDeletionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PostDeletionException(String msg) {
        super(msg);
    }
}
