package woo.products.services.type;

import java.io.Serializable;

/**
 * Represents express product's service type.
 */
public class Express implements ServiceType, Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;


    /**
     * Gets a string representation of the Express.
     *
     * @return return a visual representation of the Express as a string
     */
    public String toString(){
        return "EXPRESS";
    }

}