package woo.products.services.quality;

import java.io.Serializable;


/**
 * Represents DL service quality.
 */
public class Dl implements ServiceQuality, Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202009192006L;


    /**
     * Gets a string representation of the DL.
     *
     * @return return a visual representation of the DL as a string
     */
    public String toString(){ return "DL"; }

}