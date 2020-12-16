package woo.products;

import woo.products.services.type.ServiceType;
import woo.suppliers.Supplier;

/**
 * Represents a box.
 */
public class Box extends Product {

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202009192006L;

  /** Box's type of service */
  private ServiceType type;


  /**
   * Box class constructor
   *
   * @param id box's id
   * @param type box's service type
   * @param supplier box's supplier
   * @param price box's price
   * @param criticalValue box's critical value
   */
  public Box(String id,  ServiceType type, Supplier supplier, int price, int criticalValue){
      super(id, supplier, price, criticalValue,5);
      this.type = type ;
  }


  /**
   * Box class constructor
   *
   * @param id box's id
   * @param type box's service type
   * @param supplier box's supplier
   * @param price box's price
   * @param criticalValue box's critical value
   * @param stock box's stock
   */
  public Box(String id,  ServiceType type, Supplier supplier, int price, int criticalValue, int stock ){
      super(id, supplier, price, criticalValue, stock,5);
      this.type = type ;
  }


  /**
   * Gets a string representation of the box.
   *
   * @return return a visual representation of the box as a string
   */
  @Override
  public String toString(){
  return "BOX|" + this.getId() + "|" + this.getSupplier().getId() + "|" + this.getPrice()
  + "|" + this.getCritical() + "|" + this.getStock() + "|" + this.type;
  }

}