package woo.products.services.quality;

import java.io.Serializable;

/**
 * Represents C5 service quality.
 */
public class C5 implements ServiceQuality, Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;


    /**
     * Gets a string representation of the C5.
     *
     * @return return a visual representation of the C5 as a string
     */
    public String toString(){
        return "C5";
    }

}