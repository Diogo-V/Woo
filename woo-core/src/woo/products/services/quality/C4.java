package woo.products.services.quality;

import java.io.Serializable;

/**
 * Represents C4 service quality.
 */
public class C4 implements ServiceQuality, Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;


    /**
     * Gets a string representation of the C4.
     *
     * @return return a visual representation of the C4 as a string
     */
    public String toString(){
        return "C4";
    }

}