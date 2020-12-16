package woo.products.services.quality;

import java.io.Serializable;

/**
 * Represents B4 service quality.
 */
public class B4 implements ServiceQuality, Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;


    /**
     * Gets a string representation of the B4.
     *
     * @return return a visual representation of the B4 as a string
     */
    public String toString(){
        return "B4";
    }

}