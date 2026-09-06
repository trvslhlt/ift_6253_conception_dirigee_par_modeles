package geodes.sms.diningroom.demo;

import java.io.File;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import DiningRoom.Chair;
import DiningRoom.DiningRoomFactory;
import DiningRoom.Room;

public class EcoreDemo {

	public static void main(String[] args) throws Exception {
		String metamodelFile = "metamodel/DiningRoom.ecore";
		String xmiFile = "models/EcoreDemo.xmi";

		// --- Loading a metamodel in Ecore ---
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());
		ResourceSet metamodelResourceSet = new ResourceSetImpl();
		Resource metamodelResource = metamodelResourceSet.createResource(URI.createFileURI(metamodelFile));
		metamodelResource.load(null);
		System.out.println("Loaded metamodel root: " + metamodelResource.getContents().get(0));
		System.out.println();

		// --- Creating a model with generated EMF code ---
		DiningRoomFactory drFactory = DiningRoomFactory.eINSTANCE;
		Room room = drFactory.createRoom();
		Chair c1 = drFactory.createChair();
		c1.setName("C1");
		c1.setOrder(1);
		room.getFurniture().add(c1);

		ResourceSet modelResourceSet = new ResourceSetImpl();
		modelResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		URI fileURI = URI.createFileURI(new File(xmiFile).getAbsolutePath());
		Resource resource = modelResourceSet.createResource(fileURI);
		resource.getContents().add(room);
		System.out.println("Created model from factory.");
		System.out.println();

		// --- Modifying and saving a model (in-memory, via StringWriter) ---
		StringWriter stringWriter = new StringWriter();
		URIConverter.WriteableOutputStream outputStream = new URIConverter.WriteableOutputStream(stringWriter, "UTF-8");
		Map<String, String> options = new HashMap<>();
		resource.save(outputStream, options);
		System.out.println("In-memory XMI:\n" + stringWriter.toString());
		System.out.println();

		// --- Saving the model to disk ---
		resource.save(Collections.emptyMap());
		System.out.println("Saved to: " + fileURI);
		System.out.println();
	}
}
