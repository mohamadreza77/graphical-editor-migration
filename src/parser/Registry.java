package parser;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import java.io.File;
import java.io.IOException;

public class Registry {
	
	public static Resource register(String filePath) throws IOException {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());

        URI uri = URI.createFileURI(new File(filePath).getAbsolutePath());
        Resource resource = resourceSet.createResource(uri);
        resource.load(null);
        return resource;
    }
    public static void declareNameSpace(Resource resource){
        EPackage ePackage = (EPackage) resource.getContents().get(0);
        EPackage.Registry.INSTANCE.put(
                ePackage.getNsURI(),
                ePackage);
    }

}
