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
public class IncorrectLoginParticularsException extends Exception {

    /**
     * Creates a new instance of <code>IncorrectLoginParticularsException</code>
     * without detail message.
     */
    public IncorrectLoginParticularsException() {
    }

    /**
     * Constructs an instance of <code>IncorrectLoginParticularsException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public IncorrectLoginParticularsException(String msg) {
        super(msg);
    }
}
