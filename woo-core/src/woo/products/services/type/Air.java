package woo.products.services.type;

import java.io.Serializable;

/**
 * Represents air product's service type.
 */
public class Air implements ServiceType, Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;


    /**
     * Gets a string representation of the Air.
     *
     * @return return a visual representation of the Air as a string
     */
    public String toString(){
        return "AIR";
    }

}