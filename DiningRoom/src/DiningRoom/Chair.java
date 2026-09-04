/**
 */
package DiningRoom;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Chair</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link DiningRoom.Chair#getOrder <em>Order</em>}</li>
 * </ul>
 *
 * @see DiningRoom.DiningRoomPackage#getChair()
 * @model annotation="gmf.node color='255,146,79' figure='ellipse' label.icon='false' label='id' size='50,50'"
 * @generated
 */
public interface Chair extends Furniture {
	/**
	 * Returns the value of the '<em><b>Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Order</em>' attribute.
	 * @see #setOrder(int)
	 * @see DiningRoom.DiningRoomPackage#getChair_Order()
	 * @model
	 * @generated
	 */
	int getOrder();

	/**
	 * Sets the value of the '{@link DiningRoom.Chair#getOrder <em>Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order</em>' attribute.
	 * @see #getOrder()
	 * @generated
	 */
	void setOrder(int value);

} // Chair
