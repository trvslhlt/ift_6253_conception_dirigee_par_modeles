/**
 */
package DiningRoom;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link DiningRoom.Table#getX <em>X</em>}</li>
 *   <li>{@link DiningRoom.Table#getY <em>Y</em>}</li>
 *   <li>{@link DiningRoom.Table#getAround <em>Around</em>}</li>
 * </ul>
 *
 * @see DiningRoom.DiningRoomPackage#getTable()
 * @model annotation="gmf.node color='63,63,63' figure='rectangle' label.icon='false' label='id' label.color='255,255,255' size='200,40'"
 * @generated
 */
public interface Table extends Furniture {
	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(int)
	 * @see DiningRoom.DiningRoomPackage#getTable_X()
	 * @model
	 * @generated
	 */
	int getX();

	/**
	 * Sets the value of the '{@link DiningRoom.Table#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(int value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(int)
	 * @see DiningRoom.DiningRoomPackage#getTable_Y()
	 * @model
	 * @generated
	 */
	int getY();

	/**
	 * Sets the value of the '{@link DiningRoom.Table#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(int value);

	/**
	 * Returns the value of the '<em><b>Around</b></em>' reference list.
	 * The list contents are of type {@link DiningRoom.Chair}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Around</em>' reference list.
	 * @see DiningRoom.DiningRoomPackage#getTable_Around()
	 * @model annotation="gmf.link"
	 * @generated
	 */
	EList<Chair> getAround();

} // Table
