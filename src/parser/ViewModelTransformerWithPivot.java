package parser;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EcorePackage;
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

import transfromer.Transformer;
import transfromer.Transformer_1_Pivot;
import transfromer.Transformer_2_Pivot;
import transfromer.Transformer_Pivot_1;

import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.gmf.mappings.GMFMapPackage;
import org.eclipse.gmf.tooldef.GMFToolPackage;

public class ViewModelTransformerWithPivot {
	
	public static void main(String[] args) throws Exception {
        System.out.println("Choose your source model: ");
		System.out.println("1: Diagram Definition Model (GMF) ");
		System.out.println("2: CSS & Profile (Papyrus) ");
		System.out.println("3: Symbol Specifications (MetaEdit+) ");
		System.out.println("4: Graphical Concrete Syntax (AToM)");
		System.out.println("5: Cusotm Visualization Specification Model");
		
		Scanner scanner = new Scanner(System.in);
		int x = scanner.nextInt();
		
		System.out.print("Enter the path for the model: ");
		
		Transformer transformer;
		EmfModel pivotModel = null;
		if (x==1) {
			transformer = new Transformer_1_Pivot();
			pivotModel = transformer.transform(null);
		}
		if (x==2) {
			transformer = new Transformer_2_Pivot();
			pivotModel = transformer.transform(null);
		}
		
		if (pivotModel==null) {
			return;
		}
		
		System.out.println("Y for proceeding for the second transformation.");
        System.out.println("N for terminating the program.");
        
        String goOrNot = scanner.next();
        if (goOrNot.toLowerCase().equals("y")) {
        	System.out.println("Choose your target model: ");
    		System.out.println("1: View Model (Sirius Web) ");
    		System.out.println("2: Graphical Language Server - Java (Decoupled Architecture) ");
    		System.out.println("3: Graphical Language Server - Python (Decoupled Architecture) ");
    		System.out.println("4: Utensil (JointJS)");
    		System.out.println("5: Cusotm Visualization Specification Model");
    		
    		int y = scanner.nextInt();
    		
    		if (y==1) {
    			transformer = new Transformer_Pivot_1();
    			transformer.transform(pivotModel);
    		}
	        
        }else{
        	System.out.println("You can manipulate the generated pivot model.");
        	System.out.println("Once satisfied, run the one-go app.");
        }
		
	}

}
