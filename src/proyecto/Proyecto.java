/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

/**
 *
 * @author XIZUTH
 */
public class Proyecto {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Password contr = new Password(null, true); //con true blokea la parte de atras y con false no
        contr.setLocationRelativeTo(null);
        contr.setVisible(true);
    }
}
