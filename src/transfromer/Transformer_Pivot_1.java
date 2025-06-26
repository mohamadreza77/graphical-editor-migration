package transfromer;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.etl.EtlModule;

import parser.Registry;

public class Transformer_Pivot_1 extends Transformer{

	public Transformer_Pivot_1() {
		// TODO Auto-generated constructor stub
		super.scanner = new Scanner(System.in);
	}
	
	@Override
	public EmfModel transform(EmfModel pivotModel) throws Exception {
		pivotModel.setReadOnLoad(true);
		pivotModel.setStoredOnDisposal(false);
		pivotModel.load();
		
		Resource targetMetamodelResource1 = Registry.register("src/ecore/web_view.ecore");
        Resource targetMetamodelResource2 = Registry.register("src/ecore/web_diagram.ecore");
        Registry.declareNameSpace(targetMetamodelResource1);
        Registry.declareNameSpace(targetMetamodelResource2);
        
        EmfModel targetModel = new EmfModel();
        targetModel.setName("ModernModel");
        targetModel.setMetamodelFileUris(Arrays.asList(targetMetamodelResource1.getURI(), targetMetamodelResource2.getURI()));
        targetModel.setModelFile("src/generated/transformed_view_pivot_to_target_1.xmi");
        targetModel.setReadOnLoad(false);
        targetModel.setStoredOnDisposal(true);
        targetModel.load();		
		
        EtlModule etlModuleTransformation2 = new EtlModule();
        etlModuleTransformation2.parse(new File("src/etl/transformation_view_pivot_to_target_1.etl"));
        etlModuleTransformation2.getContext().getModelRepository().addModel(pivotModel);
        etlModuleTransformation2.getContext().getModelRepository().addModel(targetModel);
        etlModuleTransformation2.execute();
        etlModuleTransformation2.getContext().getModelRepository().dispose();
        
        System.out.println("The chosen target model was generated under the file name \'transformed_view_pivot_to_target_1.xmi\' within the src/generated/ directory.");
        
        return targetModel;
	}

}
