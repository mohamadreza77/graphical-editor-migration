package transfromer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.gmf.mappings.GMFMapPackage;
import org.eclipse.gmf.tooldef.GMFToolPackage;
import org.eclipse.sirius.diagram.description.DescriptionPackage;
import org.eclipse.sirius.diagram.description.filter.FilterPackage;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.diagram.description.tool.ToolPackage;
import org.eclipse.sirius.properties.PropertiesPackage;
import org.eclipse.sirius.viewpoint.description.validation.ValidationPackage;

public class Transformer_1_Pivot extends Transformer{

	public Transformer_1_Pivot() {
		// TODO Auto-generated constructor stub
		super.scanner = new Scanner(System.in);
	}
	
	@Override
	public EmfModel transform(EmfModel model) throws Exception{
		System.out.println("(.gmfmap) ");
		String sourcePath = scanner.next();
		sourcePath = "C:\\Users\\Pouyan\\eclipse\\epsilon-2-32\\workspace\\org.sabeghi.mohamadreza.sample.gmf_example\\model\\extendedfamily.gmfmap";
		
		if (!sourcePath.endsWith("gmfmap")) {
			System.out.println("Enter a valid file type!");
			return null;
		}
		
		List<String> metamodelUrisForLegacyModel = new ArrayList<String>();
		
		metamodelUrisForLegacyModel.add(GMFMapPackage.eINSTANCE.getNsURI());
		metamodelUrisForLegacyModel.add(GMFToolPackage.eINSTANCE.getNsURI());
		metamodelUrisForLegacyModel.add(EcorePackage.eINSTANCE.getNsURI());
		
		EmfModel sourceModel = new EmfModel();
		sourceModel.setName("LegacyModel");
		sourceModel.setModelFile(sourcePath);
		sourceModel.setMetamodelUris(metamodelUrisForLegacyModel);
		sourceModel.setReadOnLoad(true);
		sourceModel.setStoredOnDisposal(false);
		sourceModel.load();
		
		EmfModel pivotModel = new EmfModel();
		pivotModel.setName("PivotModel");
		pivotModel.setModelFile("src/generated/transformed_view_source_1_to_pivot.xmi");
		List<String> metamodelUrisForIntermediate = new ArrayList<String>();
		metamodelUrisForIntermediate.add(DescriptionPackage.eINSTANCE.getNsURI());
		metamodelUrisForIntermediate.add(org.eclipse.sirius.viewpoint.description.DescriptionPackage.eINSTANCE.getNsURI());
		metamodelUrisForIntermediate.add(FilterPackage.eINSTANCE.getNsURI());
		metamodelUrisForIntermediate.add(PropertiesPackage.eINSTANCE.getNsURI());
		metamodelUrisForIntermediate.add(StylePackage.eINSTANCE.getNsURI());
		metamodelUrisForIntermediate.add(ToolPackage.eINSTANCE.getNsURI());
		metamodelUrisForIntermediate.add(org.eclipse.sirius.viewpoint.description.tool.ToolPackage.eINSTANCE.getNsURI());
		metamodelUrisForIntermediate.add(ValidationPackage.eINSTANCE.getNsURI());
		pivotModel.setMetamodelUris(metamodelUrisForIntermediate);
		pivotModel.setReadOnLoad(false);
		pivotModel.setStoredOnDisposal(true);
		pivotModel.load();
		
		EtlModule etlModuleTransformation1 = new EtlModule();
        etlModuleTransformation1.parse(new File("src/etl/transformation_view_source_1_to_pivot.etl"));
        etlModuleTransformation1.getContext().getModelRepository().addModel(sourceModel);
        etlModuleTransformation1.getContext().getModelRepository().addModel(pivotModel);
        etlModuleTransformation1.execute();
        etlModuleTransformation1.getContext().getModelRepository().dispose();
        
        System.out.println("The pivot model was generated under the file name \'transformed_view_source_1_to_pivot.xmi\' within the src/generated/ directory.");
        
        return pivotModel;
	}

}
