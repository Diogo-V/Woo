package woo.products;

import woo.products.services.type.ServiceType;
import woo.products.services.quality.ServiceQuality;
import woo.suppliers.Supplier;

/**
 * Represents a container.
 */
public class Container extends Product {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;

  /** Container's type of service */
  private ServiceType type;

  /** Container's type of service's quality */
  private ServiceQuality quality;


  /**
   * Container class constructor
   *
   * @param id container's id
   * @param type container's type
   * @param quality container's quality
   * @param supplier container's supplier
   * @param price container's price
   * @param criticalValue container's critical value
   */
  public Container(String id, ServiceType type, ServiceQuality quality, Supplier supplier,
                   int price, int criticalValue){
    super(id, supplier, price, criticalValue,8);
    this.type = type;
    this.quality = quality;
  }


  /**
   * Container class constructor
   *
   * @param id container's id
   * @param type container's type
   * @param quality container's quality
   * @param supplier container's supplier
   * @param price container's price
   * @param criticalValue container's critical value
   * @param stock container's stock
   */
  public Container(String id, ServiceType type, ServiceQuality quality, Supplier supplier,
                   int price, int criticalValue, int stock){
    super(id, supplier, price, criticalValue, stock, 8);
    this.type = type;
    this.quality = quality;
  }


  /**
	 * Gets a string representation of the container.
	 * 
	 * @return return a visual representation of the container as a string
	 */
  @Override
  public String toString(){
		return "CONTAINER|" + this.getId() + "|" + this.getSupplier().getId() + "|" + this.getPrice()
        + "|" + this.getCritical() + "|" + this.getStock() + "|" + this.type + "|" + this.quality;
    }
  
}