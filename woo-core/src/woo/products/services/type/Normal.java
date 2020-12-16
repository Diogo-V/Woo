package woo.products.services.type;

import java.io.Serializable;

/**
 * Represents normal product's service type.
 */
public class Normal implements ServiceType, Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;

    /**
     * Gets a string representation of the Normal.
     *
     * @return return a visual representation of the Normal as a string
     */
    public String toString(){
        return "NORMAL";
    }

}