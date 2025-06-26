package parser;

import java.io.File;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.etl.EtlModule;

public class DomainModelTransformer {
	
	public static void main(String[] args) throws Exception {
		
		EmfModel sourceModel = new EmfModel();
	    sourceModel.setName("EcoreModel");
	    sourceModel.setMetamodelUri(EcorePackage.eNS_URI);
	    sourceModel.setModelFile("D:\\ms\\University-PhD\\RA\\Thesis-related Projects\\input_output_files\\extendedfamily.ecore");
	    sourceModel.setReadOnLoad(true);
	    sourceModel.setStoredOnDisposal(false);
	    sourceModel.load();
	    
	    
	    Resource targetMetamodelResource = Registry.register("src/ecore/web_domain.ecore");
        Registry.declareNameSpace(targetMetamodelResource);

        EmfModel targetModel = new EmfModel();
        targetModel.setName("DomainModel");
        targetModel.setMetamodelFileUri(targetMetamodelResource.getURI());
        targetModel.setModelFile(new File("src/generated/transformed_domain.xmi").getAbsolutePath());
        targetModel.setReadOnLoad(false);
        targetModel.setStoredOnDisposal(true);
        targetModel.load();

        EtlModule module = new EtlModule();
        module.parse(new File("src/etl/transformation_metamodel.etl"));
        module.getContext().getModelRepository().addModel(sourceModel);
        module.getContext().getModelRepository().addModel(targetModel);
        module.execute();
        module.getContext().getModelRepository().dispose();
	}
	
}
