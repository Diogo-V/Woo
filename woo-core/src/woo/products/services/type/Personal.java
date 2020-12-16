package woo.products.services.type;

import java.io.Serializable;

/**
 * Represents personal product's service type.
 */
public class Personal implements ServiceType, Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /**
     * Gets a string representation of the Personal.
     *
     * @return return a visual representation of the Personal as a string
     */
    public String toString(){
        return "PERSONAL";
    }

}