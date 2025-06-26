package parser;

import java.util.ArrayList;
import org.eclipse.emf.ecore.resource.Resource;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.m0.*;
import org.eclipse.epsilon.emc.emf.xml.*;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.emc.emf.tools.*;
import org.eclipse.sirius.diagram.description.DescriptionPackage;
import org.eclipse.sirius.diagram.description.filter.FilterPackage;
import org.eclipse.sirius.diagram.description.impl.DescriptionPackageImpl;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.diagram.description.tool.ToolPackage;
import org.eclipse.sirius.properties.PropertiesPackage;
import org.eclipse.sirius.viewpoint.description.style.StyleDescription;
import org.eclipse.sirius.viewpoint.description.tool.ToolDescription;
import org.eclipse.sirius.viewpoint.description.validation.ValidationPackage;
import org.eclipse.epsilon.etl.EtlModule;

public class ViewModelTransformerOneGo {
	
	public static void main(String[] args) throws Exception {
		
		EmfModel sourceModel = new EmfModel();
		sourceModel.setName("LegacyModel");
		sourceModel.setModelFile("D:\\ms\\University-PhD\\RA\\Thesis-related Projects\\input_output_files\\extendedfamily.odesign");
		List<String> metamodelUris = new ArrayList<String>();
		metamodelUris.add(DescriptionPackage.eINSTANCE.getNsURI());
		metamodelUris.add(org.eclipse.sirius.viewpoint.description.DescriptionPackage.eINSTANCE.getNsURI());
		//metamodelUris.add(FilterPackage.eINSTANCE.getNsURI());
		//metamodelUris.add(PropertiesPackage.eINSTANCE.getNsURI());
		metamodelUris.add(StylePackage.eINSTANCE.getNsURI());
		metamodelUris.add(ToolPackage.eINSTANCE.getNsURI());
		metamodelUris.add(org.eclipse.sirius.viewpoint.description.tool.ToolPackage.eINSTANCE.getNsURI());
		//metamodelUris.add(ValidationPackage.eINSTANCE.getNsURI());
		sourceModel.setMetamodelUris(metamodelUris);
		sourceModel.setReadOnLoad(true);
		sourceModel.setStoredOnDisposal(false);
		sourceModel.load();
		
		
		Resource targetMetamodelResource1 = Registry.register("src/ecore/web_view.ecore");
        Resource targetMetamodelResource2 = Registry.register("src/ecore/web_diagram.ecore");
        Registry.declareNameSpace(targetMetamodelResource1);
        Registry.declareNameSpace(targetMetamodelResource2);
        
        EmfModel targetModel = new EmfModel();
        targetModel.setName("ModernModel");
        targetModel.setMetamodelFileUris(Arrays.asList(targetMetamodelResource1.getURI(), targetMetamodelResource2.getURI()));
        targetModel.setModelFile("src/generated/transformed_view.xmi");
        targetModel.setReadOnLoad(false);
        targetModel.setStoredOnDisposal(true);
        targetModel.load();
		
        
        EtlModule etlModule = new EtlModule();
        etlModule.parse(new File("src/etl/transformation_view_one_go.etl"));
        etlModule.getContext().getModelRepository().addModel(sourceModel);
        etlModule.getContext().getModelRepository().addModel(targetModel);
        etlModule.execute();
        etlModule.getContext().getModelRepository().dispose();

	}

}
