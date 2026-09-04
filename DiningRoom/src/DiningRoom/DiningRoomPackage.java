/**
 */
package DiningRoom;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see DiningRoom.DiningRoomFactory
 * @model kind="package"
 * @generated
 */
public interface DiningRoomPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "DiningRoom";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "dr";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "geodes.sms.diningroom";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DiningRoomPackage eINSTANCE = DiningRoom.impl.DiningRoomPackageImpl.init();

	/**
	 * The meta object id for the '{@link DiningRoom.impl.RoomImpl <em>Room</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DiningRoom.impl.RoomImpl
	 * @see DiningRoom.impl.DiningRoomPackageImpl#getRoom()
	 * @generated
	 */
	int ROOM = 0;

	/**
	 * The feature id for the '<em><b>Furniture</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM__FURNITURE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM__NAME = 1;

	/**
	 * The number of structural features of the '<em>Room</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Room</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link DiningRoom.impl.FurnitureImpl <em>Furniture</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DiningRoom.impl.FurnitureImpl
	 * @see DiningRoom.impl.DiningRoomPackageImpl#getFurniture()
	 * @generated
	 */
	int FURNITURE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FURNITURE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Furniture</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FURNITURE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Furniture</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FURNITURE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link DiningRoom.impl.TableImpl <em>Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DiningRoom.impl.TableImpl
	 * @see DiningRoom.impl.DiningRoomPackageImpl#getTable()
	 * @generated
	 */
	int TABLE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__NAME = FURNITURE__NAME;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__X = FURNITURE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__Y = FURNITURE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Around</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__AROUND = FURNITURE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_FEATURE_COUNT = FURNITURE_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_OPERATION_COUNT = FURNITURE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link DiningRoom.impl.ChairImpl <em>Chair</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see DiningRoom.impl.ChairImpl
	 * @see DiningRoom.impl.DiningRoomPackageImpl#getChair()
	 * @generated
	 */
	int CHAIR = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAIR__NAME = FURNITURE__NAME;

	/**
	 * The feature id for the '<em><b>Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAIR__ORDER = FURNITURE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Chair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAIR_FEATURE_COUNT = FURNITURE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Chair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAIR_OPERATION_COUNT = FURNITURE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link DiningRoom.Room <em>Room</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Room</em>'.
	 * @see DiningRoom.Room
	 * @generated
	 */
	EClass getRoom();

	/**
	 * Returns the meta object for the containment reference list '{@link DiningRoom.Room#getFurniture <em>Furniture</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Furniture</em>'.
	 * @see DiningRoom.Room#getFurniture()
	 * @see #getRoom()
	 * @generated
	 */
	EReference getRoom_Furniture();

	/**
	 * Returns the meta object for the attribute '{@link DiningRoom.Room#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see DiningRoom.Room#getName()
	 * @see #getRoom()
	 * @generated
	 */
	EAttribute getRoom_Name();

	/**
	 * Returns the meta object for class '{@link DiningRoom.Furniture <em>Furniture</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Furniture</em>'.
	 * @see DiningRoom.Furniture
	 * @generated
	 */
	EClass getFurniture();

	/**
	 * Returns the meta object for the attribute '{@link DiningRoom.Furniture#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see DiningRoom.Furniture#getName()
	 * @see #getFurniture()
	 * @generated
	 */
	EAttribute getFurniture_Name();

	/**
	 * Returns the meta object for class '{@link DiningRoom.Table <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table</em>'.
	 * @see DiningRoom.Table
	 * @generated
	 */
	EClass getTable();

	/**
	 * Returns the meta object for the attribute '{@link DiningRoom.Table#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see DiningRoom.Table#getX()
	 * @see #getTable()
	 * @generated
	 */
	EAttribute getTable_X();

	/**
	 * Returns the meta object for the attribute '{@link DiningRoom.Table#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see DiningRoom.Table#getY()
	 * @see #getTable()
	 * @generated
	 */
	EAttribute getTable_Y();

	/**
	 * Returns the meta object for the reference list '{@link DiningRoom.Table#getAround <em>Around</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Around</em>'.
	 * @see DiningRoom.Table#getAround()
	 * @see #getTable()
	 * @generated
	 */
	EReference getTable_Around();

	/**
	 * Returns the meta object for class '{@link DiningRoom.Chair <em>Chair</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Chair</em>'.
	 * @see DiningRoom.Chair
	 * @generated
	 */
	EClass getChair();

	/**
	 * Returns the meta object for the attribute '{@link DiningRoom.Chair#getOrder <em>Order</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Order</em>'.
	 * @see DiningRoom.Chair#getOrder()
	 * @see #getChair()
	 * @generated
	 */
	EAttribute getChair_Order();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DiningRoomFactory getDiningRoomFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link DiningRoom.impl.RoomImpl <em>Room</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DiningRoom.impl.RoomImpl
		 * @see DiningRoom.impl.DiningRoomPackageImpl#getRoom()
		 * @generated
		 */
		EClass ROOM = eINSTANCE.getRoom();

		/**
		 * The meta object literal for the '<em><b>Furniture</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOM__FURNITURE = eINSTANCE.getRoom_Furniture();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOM__NAME = eINSTANCE.getRoom_Name();

		/**
		 * The meta object literal for the '{@link DiningRoom.impl.FurnitureImpl <em>Furniture</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DiningRoom.impl.FurnitureImpl
		 * @see DiningRoom.impl.DiningRoomPackageImpl#getFurniture()
		 * @generated
		 */
		EClass FURNITURE = eINSTANCE.getFurniture();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FURNITURE__NAME = eINSTANCE.getFurniture_Name();

		/**
		 * The meta object literal for the '{@link DiningRoom.impl.TableImpl <em>Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DiningRoom.impl.TableImpl
		 * @see DiningRoom.impl.DiningRoomPackageImpl#getTable()
		 * @generated
		 */
		EClass TABLE = eINSTANCE.getTable();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE__X = eINSTANCE.getTable_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE__Y = eINSTANCE.getTable_Y();

		/**
		 * The meta object literal for the '<em><b>Around</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE__AROUND = eINSTANCE.getTable_Around();

		/**
		 * The meta object literal for the '{@link DiningRoom.impl.ChairImpl <em>Chair</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see DiningRoom.impl.ChairImpl
		 * @see DiningRoom.impl.DiningRoomPackageImpl#getChair()
		 * @generated
		 */
		EClass CHAIR = eINSTANCE.getChair();

		/**
		 * The meta object literal for the '<em><b>Order</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHAIR__ORDER = eINSTANCE.getChair_Order();

	}

} //DiningRoomPackage
