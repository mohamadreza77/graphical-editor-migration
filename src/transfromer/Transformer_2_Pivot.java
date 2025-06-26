package transfromer;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.sirius.diagram.LabelPosition;
import org.eclipse.sirius.diagram.LineStyle;
import org.eclipse.sirius.diagram.ResizeKind;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.DescriptionPackage;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.Layer;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.filter.FilterPackage;
import org.eclipse.sirius.diagram.description.style.CenterLabelStyleDescription;
import org.eclipse.sirius.diagram.description.style.EdgeStyleDescription;
import org.eclipse.sirius.diagram.description.style.NodeStyleDescription;
import org.eclipse.sirius.diagram.description.style.Side;
import org.eclipse.sirius.diagram.description.style.SquareDescription;
import org.eclipse.sirius.diagram.description.style.StyleFactory;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.diagram.description.style.WorkspaceImageDescription;
import org.eclipse.sirius.diagram.description.tool.ToolPackage;
import org.eclipse.sirius.properties.PropertiesPackage;
import org.eclipse.sirius.tools.api.ui.color.EnvironmentSystemColorFactory;
import org.eclipse.sirius.viewpoint.FontFormat;
import org.eclipse.sirius.viewpoint.LabelAlignment;
import org.eclipse.sirius.viewpoint.description.ColorDescription;
import org.eclipse.sirius.viewpoint.description.Group;
import org.eclipse.sirius.viewpoint.description.SystemColor;
import org.eclipse.sirius.viewpoint.description.SystemColors;
import org.eclipse.sirius.viewpoint.description.SytemColorsPalette;
import org.eclipse.sirius.viewpoint.description.impl.ColorDescriptionImpl;
import org.eclipse.sirius.viewpoint.description.validation.ValidationPackage;

//import org.eclipse.sirius.viewpoint.description.DescriptionFactory;
//import org.eclipse.sirius.viewpoint.description.tool.ToolFactory;
//import org.eclipse.sirius.viewpoint.description.representation.RepresentationFactory;
//import org.eclipse.sirius.viewpoint.description.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSDeclaration;
import com.helger.css.decl.CSSSelector;
import com.helger.css.decl.CSSStyleRule;
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.decl.ICSSTopLevelRule;
import com.helger.css.reader.CSSReader;

import parser.Registry;

public class Transformer_2_Pivot extends Transformer {

	public Transformer_2_Pivot() {
		// TODO Auto-generated constructor stub
		super.scanner = new Scanner(System.in);
	}
	
	@Override
	public EmfModel transform(EmfModel model) throws Exception {
		System.out.println("(.css) first and then (.profile.uml) ");
		//String cssPath = scanner.next();
		//String umlPath = scanner.next();
		String cssPath = "C:\\Users\\Pouyan\\workspace-papyrus-real\\Demo\\custom.css";
		String umlPath = "C:\\Users\\Pouyan\\workspace-papyrus-real\\Demo\\extendedfamily.profile.uml";
		
		if (!cssPath.endsWith(".css")) {
			System.out.println("Enter a valid file type for the CSS file!");
			return null;
		}
		if (!umlPath.endsWith(".uml")) {
			System.out.println("Enter a valid file type for the UML profile file!");
			return null;
		}
		
		//parse the css and xml file
		CascadingStyleSheet css = null;
		try{
			 css = CSSReader.readFromFile(new File(cssPath), StandardCharsets.UTF_8, ECSSVersion.CSS30);
		}catch (Exception e) {
           System.out.println("could not load css properly!");
           return null;
	    }
		if (css == null) {
			System.out.println("failed to parse CSS.");
			return null;
		}
		Document doc = null;
		try {
			File xmlFile = new File(umlPath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xmlFile);
            //doc.getDocumentElement().normalize();
		}catch (Exception e) {
           System.out.println("could not load profile properly!");
           return null;
        }
		if (doc == null) {
			System.out.println("failed to parse profile.");
			return null;
		}
		NodeList packagedElements = doc.getElementsByTagName("packagedElement");
		
		EmfModel pivotModel = new EmfModel();
		pivotModel.setName("PivotModel");
		pivotModel.setModelFile("src/generated/transformed_view_source_2_to_pivot.xmi");
		List<String> metamodelUrisForIntermediate = new ArrayList<String>();
		metamodelUrisForIntermediate.add(org.eclipse.sirius.viewpoint.description.DescriptionPackage.eINSTANCE.getNsURI());
		metamodelUrisForIntermediate.add(DescriptionPackage.eINSTANCE.getNsURI());
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
		
				
		// Create a new Group
		org.eclipse.sirius.viewpoint.description.Group group = org.eclipse.sirius.viewpoint.description.DescriptionFactory.eINSTANCE.createGroup();
		Element root = (Element) doc.getDocumentElement();
		String profileName = root.getAttribute("name");
		group.setName(profileName);
		
		// Create the top-level element: a new Viewpoint
		org.eclipse.sirius.viewpoint.description.Viewpoint viewpoint = org.eclipse.sirius.viewpoint.description.DescriptionFactory.eINSTANCE.createViewpoint();
		viewpoint.setName("Custom Viewpoint from CSS Papyrus");
		viewpoint.setModelFileExtension("");
		
		// Create a default DiagramDescription
		DiagramDescription diagram = DescriptionFactory.eINSTANCE.createDiagramDescription();
	    diagram.setName("Cusotm Representation from CSS Papyrus");
	    diagram.setDomainClass(profileName + "::" + findRootInstanceType(packagedElements));

	    // Create a default Layer
	    Layer defaultLayer = DescriptionFactory.eINSTANCE.createLayer();
	    defaultLayer.setName("Default");
	    
	    // Link them together
	    diagram.setDefaultLayer(defaultLayer);
	    viewpoint.getOwnedRepresentations().add(diagram);
	    group.getOwnedViewpoints().add(viewpoint);
	    pivotModel.getResource().getContents().add(group);
 
	    List<Element> edgePackagedElements = new ArrayList<>();
	    List<Element> nodePackagedElements = new ArrayList<>();
        for (int i = 0; i < packagedElements.getLength(); i++) {
            Element element = (Element) packagedElements.item(i);
            String type = element.getAttribute("xmi:type");
            if ("uml:Stereotype".equals(type)) {
                String name = element.getAttribute("name");
                String is_abstract = element.getAttribute("isAbstract");
                if ("true".equals(is_abstract)) continue;
            	//see what this packagedElement is; if its metaclass is Class --> Node; if its metaclass is Association --> Edge                                        
            	NodeList ownedAttributes = element.getElementsByTagName("ownedAttribute");
            	String labelExp = "feature:name";
            	for (int j = 0; j < ownedAttributes.getLength(); j++) {
            		Element ownedAttribute = (Element) ownedAttributes.item(j);
            		String innertype = ownedAttribute.getAttribute("xmi:type");
            		if("uml:Property".equals(innertype)) {
            			Element typeTag = (Element) ownedAttribute.getElementsByTagName("type").item(0);
            			if (typeTag == null) continue;
            			String href = typeTag.getAttribute("href");
            			if (href == null) continue;
            			if (href.contains("String")) {
            				labelExp = "feature:" + ownedAttribute.getAttribute("name");
            			}
            		}
            	}
            	for (int j = 0; j < ownedAttributes.getLength(); j++) {
            		Element ownedAttribute = (Element) ownedAttributes.item(j);
            		String innertype = ownedAttribute.getAttribute("xmi:type");
            		if("uml:Property".equals(innertype)) {
            			Element typeTag = (Element) ownedAttribute.getElementsByTagName("type").item(0);
            			if (typeTag == null) continue;
            			String href = typeTag.getAttribute("href");
            			if (href == null) continue;
            			if (href.contains("Class")) {
            				nodePackagedElements.add(element);
            				
            				NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
                			//set default style
            	        	SquareDescription squareImageStyle = StyleFactory.eINSTANCE.createSquareDescription();
            	        	squareImageStyle.setSizeComputationExpression("4");
            	        	squareImageStyle.setLabelSize(12);
            	        	squareImageStyle.setShowIcon(false);
            	        	squareImageStyle.setLabelExpression(labelExp);
            	        	squareImageStyle.setResizeKind(ResizeKind.NSEW_LITERAL);
            	        	nodeMapping.setStyle(squareImageStyle);
            	        	
            			    nodeMapping.setName(name + "Node");
            			    nodeMapping.setDomainClass(profileName + "::" + element.getAttribute("name"));
            			    //set its properties with what css file has (mapping)
            			    nodeMapping = nodeMapCssToVSM(css, name, nodeMapping, labelExp);
            			    defaultLayer.getNodeMappings().add(nodeMapping);
            			}else if (href.contains("Association")) {
            				edgePackagedElements.add(element);
            				
            				EdgeMapping edgeMapping = DescriptionFactory.eINSTANCE.createEdgeMapping();
            	        	// set default style
            				EdgeStyleDescription simpleEdgeStyle = StyleFactory.eINSTANCE.createEdgeStyleDescription();
            	        	CenterLabelStyleDescription simpleCenterLabelStyleDescription = StyleFactory.eINSTANCE.createCenterLabelStyleDescription();
            	        	simpleCenterLabelStyleDescription.setLabelExpression(name);
            	        	simpleEdgeStyle.setCenterLabelStyleDescription(simpleCenterLabelStyleDescription);
            	        	simpleEdgeStyle.getCenterLabelStyleDescription().setShowIcon(false);
            	            edgeMapping.setStyle(simpleEdgeStyle);
            				
            	            edgeMapping.setName(name + "Edge");
            				//set its properties with what css file has (mapping)
            				edgeMapping = edgeMapCssToVSM(css, name, edgeMapping);
            			    defaultLayer.getEdgeMappings().add(edgeMapping);
            			}
            		}
            	}
            }
        }
        addSourceAndTargetNodesToEdges(defaultLayer.getNodeMappings(), defaultLayer.getEdgeMappings(), edgePackagedElements, nodePackagedElements);
		
		System.out.println("The pivot model was generated under the file name \'transformed_view_source_2_to_pivot.xmi\' within the src/generated/ directory.");
		
		pivotModel.dispose();
		return pivotModel;
	}
	
	private void addSourceAndTargetNodesToEdges(EList<NodeMapping> nodeMappings, EList<EdgeMapping> edgeMappings, List<Element> edgePackagedElements, List<Element> nodePackagedElements) {
		for (EdgeMapping edgeMapping : edgeMappings) {
			String edgeMappingName = edgeMapping.getName();
			for (Element element : edgePackagedElements) {
				if (((String)element.getAttribute("name")+"Edge").equals(edgeMappingName)) {
					NodeList ownedAttributes = element.getElementsByTagName("ownedAttribute");
					for (int j = 0; j < ownedAttributes.getLength(); j++) {
	            		Element ownedAttribute = (Element) ownedAttributes.item(j);
	            		String innertype = ownedAttribute.getAttribute("xmi:type");
	            		if("uml:Property".equals(innertype)) {
	            			if (ownedAttribute.getAttribute("name").contains("from")) {
	            				for (Element element1 : nodePackagedElements) {
	            			        String id = element1.getAttribute("xmi:id");
	            			        if (ownedAttribute.getAttribute("type").equals(id)) {
	            			        	String nodeName = element1.getAttribute("name") + "Node";
	            			        	for (NodeMapping nodeMapping : nodeMappings) {
	    									if (nodeMapping.getName().equals(nodeName)) {
	    										edgeMapping.getSourceMapping().add(nodeMapping);
	    									}
	    								}
	            			        }
	            			    }
	            			}
	            			else if (ownedAttribute.getAttribute("name").contains("to")) {
	            				for (Element element1 : nodePackagedElements) {
	            			        String id = element1.getAttribute("xmi:id");
	            			        if (ownedAttribute.getAttribute("type").equals(id)) {
	            			        	String nodeName = element1.getAttribute("name") + "Node";
	            			        	for (NodeMapping nodeMapping : nodeMappings) {
	    									if (nodeMapping.getName().equals(nodeName)) {
	    										edgeMapping.getTargetMapping().add(nodeMapping);
	    									}
	    								}
	            			        }
	            			    }
	            			}
	            			else if (ownedAttribute.getAttribute("name").contains("expr")) {
	            				edgeMapping.setTargetFinderExpression("aql:self."+ownedAttribute.getAttribute("name").split("_")[1]);
	            			}
	            		}
	            	}
				}
			}
		}
		
	}

	private String findRootInstanceType(NodeList packagedElements) {
		String rootDomainClass = "";
		for (int i = 0; i < packagedElements.getLength(); i++) {
            Element element = (Element) packagedElements.item(i);
            String type = element.getAttribute("xmi:type");
            if ("uml:Stereotype".equals(type)) {
                String name = element.getAttribute("name");
                NodeList ownedAttributes = element.getElementsByTagName("ownedAttribute");
            	for (int j = 0; j < ownedAttributes.getLength(); j++) {
            		Element ownedAttribute = (Element) ownedAttributes.item(j);
            		String innertype = ownedAttribute.getAttribute("xmi:type");
            		if("uml:Property".equals(innertype)) {
            			Element typeTag = (Element) ownedAttribute.getElementsByTagName("type").item(0);
            			if (typeTag == null) continue;
            			String href = typeTag.getAttribute("href");
            			if (href == null) continue;
            			if (href.contains("Package")) {
            				rootDomainClass = name;
            				return rootDomainClass;
            			}
            		}
            	}
            }
		}
		return rootDomainClass;
	}

	public NodeMapping nodeMapCssToVSM(CascadingStyleSheet css, String name, NodeMapping nodeMapping, String labelExp) {
		if (nodeMapping != null) {
	        for (ICSSTopLevelRule rule : css.getAllRules()) {
	            if (rule instanceof CSSStyleRule) {
	                CSSStyleRule styleRule = (CSSStyleRule) rule;
	                for (CSSSelector selector : styleRule.getAllSelectors()) {
	                    String selText = selector.getAsCSSString();
	                    // check if selector includes [appliedStereotypes~="..."]
	                    if (selText.contains("[appliedStereotypes~=\"")) {
	                        //for every stereotype in css file, get the name and check if it equals to the profile name; if so, set the properties
	                        String stereotypeName = selText.substring(selText.indexOf("\"")+1, selText.lastIndexOf("\""));
	                        if (name.equals(stereotypeName)) {
	                        	WorkspaceImageDescription workspaceImageStyle = null;
	                            for (CSSDeclaration declaration : styleRule.getAllDeclarations()) {
	                            	String cssKey = declaration.getProperty().toLowerCase();
	                            	String cssValue = declaration.getExpression().getAsCSSString().toLowerCase();
	                                if (cssKey.equals("svgfile")) {
	                                	workspaceImageStyle = StyleFactory.eINSTANCE.createWorkspaceImageDescription();
	                                	workspaceImageStyle.setWorkspacePath(cssValue); 
	                                	workspaceImageStyle.setSizeComputationExpression("4"); 
	                                	workspaceImageStyle.setShowIcon(false);
	                                	workspaceImageStyle.setLabelExpression(labelExp);
	                                	workspaceImageStyle.setResizeKind(ResizeKind.NSEW_LITERAL);
	                                }
	                            }
	                            if (workspaceImageStyle != null) {
		                            for (CSSDeclaration declaration : styleRule.getAllDeclarations()) {
		                            	String cssKey = declaration.getProperty();
		                            	String cssValue = declaration.getExpression().getAsCSSString();
		                                if(cssKey.equals("fontheight")) {
		                                	workspaceImageStyle.setLabelSize(Integer.parseInt(cssValue));
		                                }else if(cssKey.equals("bold") && Boolean.parseBoolean(cssValue)==true) {
		                                	workspaceImageStyle.getLabelFormat().add(FontFormat.BOLD_LITERAL);
		                                }else if(cssKey.equals("underline") && Boolean.parseBoolean(cssValue)==true) {
		                                	workspaceImageStyle.getLabelFormat().add(FontFormat.UNDERLINE_LITERAL);
		                                }else if(cssKey.equals("italic") && Boolean.parseBoolean(cssValue)==true) {
		                                	workspaceImageStyle.getLabelFormat().add(FontFormat.ITALIC_LITERAL);
		                                }else if(cssKey.equals("borderstyle")) {
		                                	if (cssValue.equals("dash"))
		                                		workspaceImageStyle.setBorderLineStyle(LineStyle.DASH_LITERAL);
		                                	else if (cssValue.equals("dot"))
		                                		workspaceImageStyle.setBorderLineStyle(LineStyle.DOT_LITERAL);
		                                	else 
		                                		workspaceImageStyle.setBorderLineStyle(LineStyle.SOLID_LITERAL);
		                                }else if(cssKey.equals("linecolor")) {
//		                                	org.eclipse.sirius.viewpoint.description.SystemColor borderColor = org.eclipse.sirius.viewpoint.
//		                                			description.DescriptionFactory.eINSTANCE.createSystemColor();
//		                                	borderColor.setRed(0);
//		                                	borderColor.setGreen(0);
//		                                	borderColor.setBlue(0);
//		                                	if (cssValue.contains("red")) {
//		                                		borderColor.setRed(255);
//		                                		borderColor.setName("red");
//		                                	}
//		                                	if (cssValue.contains("blue")) {
//		                                		borderColor.setBlue(255);
//		                                		borderColor.setName("blue");
//		                                	}
//		                                	if (cssValue.contains("green")) {
//		                                		borderColor.setGreen(255);
//		                                		borderColor.setName("green");
//		                                	}
//		                                	workspaceImageStyle.setBorderColor(borderColor);
		                                }else if(cssKey.equals("fontcolor")) {
//		                                	org.eclipse.sirius.viewpoint.description.SystemColor labelColor = org.eclipse.sirius.viewpoint.
//		                                	description.DescriptionFactory.eINSTANCE.createSystemColor();
//		                                	labelColor.setRed(0);
//		                                	labelColor.setGreen(0);
//		                                	labelColor.setBlue(0);
//		                                	labelColor.setName("black");
//		                                	if (cssValue.contains("red")) {
//		                                		System.out.println("red found");
//		                                		labelColor.setRed(255);
//		                                		labelColor.setName("red");
//		                                	}
//		                                	if (cssValue.contains("blue")) {
//		                                		System.out.println("blue found");
//		                                		labelColor.setBlue(255);
//		                                		labelColor.setName("blue");
//		                                	}
//		                                	if (cssValue.contains("green")) {
//		                                		System.out.println("green found");
//		                                		labelColor.setGreen(255);
//		                                		labelColor.setName("green");
//		                                	}
//		                                	workspaceImageStyle.setLabelColor(labelColor);
		                                }else if(cssKey.equals("linewidth")) {
		                                	workspaceImageStyle.setBorderSizeComputationExpression(cssValue);
		                                }
		                            }
		                            nodeMapping.setStyle(workspaceImageStyle);   
	                            }
	                        }
	                    }
	                }
	            }
	        }
		}
		return nodeMapping;
	}
	
	public EdgeMapping edgeMapCssToVSM(CascadingStyleSheet css, String name, EdgeMapping edgeMapping) {
		if (edgeMapping != null) {
	        for (ICSSTopLevelRule rule : css.getAllRules()) {
	            if (rule instanceof CSSStyleRule) {
	                CSSStyleRule styleRule = (CSSStyleRule) rule;
	                for (CSSSelector selector : styleRule.getAllSelectors()) {
	                    String selText = selector.getAsCSSString();
	                    // check if selector includes [appliedStereotypes~="..."]
	                    if (selText.contains("[appliedStereotypes~=\"")) {
	                    	//for every stereotype in css file, get the name and properties
	                        String stereotypeName = selText.substring(selText.indexOf("\"")+1, selText.lastIndexOf("\""));
	                        if (name.equals(stereotypeName)) {
	                        	EdgeStyleDescription edgeStyle = StyleFactory.eINSTANCE.createEdgeStyleDescription();
	                        	CenterLabelStyleDescription centerLabelStyleDescription = StyleFactory.eINSTANCE.createCenterLabelStyleDescription();
	                            for (CSSDeclaration declaration : styleRule.getAllDeclarations()) {
	                            	String cssKey = declaration.getProperty().toLowerCase();
	                            	String cssValue = declaration.getExpression().getAsCSSString().toLowerCase();
	                            	if(cssKey.equals("linecolor")) {
//	                                	org.eclipse.sirius.viewpoint.description.SystemColor borderColor = org.eclipse.sirius.viewpoint.description.DescriptionFactory.eINSTANCE.createSystemColor();
//	                                	borderColor.setRed(0);
//	                                	borderColor.setGreen(0);
//	                                	borderColor.setBlue(0);
//	                                	if (cssValue.contains("red")) {
//	                                		borderColor.setRed(255);
//	                                		borderColor.setName("red");
//	                                	}
//	                                	if (cssValue.contains("blue")) {
//	                                		borderColor.setBlue(255);
//	                                		borderColor.setName("blue");
//	                                	}
//	                                	if (cssValue.contains("green")) {
//	                                		borderColor.setGreen(255);
//	                                		borderColor.setName("green");
//	                                	}
//	                                	edgeStyle.setStrokeColor(borderColor);
	                                }else if(cssKey.equals("fontcolor")) {
//	                                	org.eclipse.sirius.viewpoint.description.SystemColor labelColor = org.eclipse.sirius.viewpoint.description.DescriptionFactory.eINSTANCE.createSystemColor();
//	                                	labelColor.setRed(0);
//	                                	labelColor.setGreen(0);
//	                                	labelColor.setBlue(0);
//	                                	if (cssValue.contains("red")) {
//	                                		labelColor.setRed(255);
//	                                		labelColor.setName("red");
//	                                	}
//	                                	if (cssValue.contains("blue")) {
//	                                		labelColor.setBlue(255);
//	                                		labelColor.setName("blue");
//	                                	}
//	                                	if (cssValue.contains("green")) {
//	                                		labelColor.setGreen(255);
//	                                		labelColor.setName("green");
//	                                	}
//	                                	centerLabelStyleDescription.setLabelColor(labelColor);
	                                }else if(cssKey.equals("linestyle")) {
	                                	if (cssValue.equals("dash"))
	                                		edgeStyle.setLineStyle(LineStyle.DASH_LITERAL);
	                                	else if (cssValue.equals("dot"))
	                                		edgeStyle.setLineStyle(LineStyle.DOT_LITERAL);
	                                	else 
	                                		edgeStyle.setLineStyle(LineStyle.SOLID_LITERAL);
	                                }else if(cssKey.equals("linewidth")) {
	                                	edgeStyle.setSizeComputationExpression(cssValue);
	                                }else if(cssKey.equals("fontheight")) {
	                                	centerLabelStyleDescription.setLabelSize(Integer.parseInt(cssValue));
	                                }else if(cssKey.equals("bold") && Boolean.parseBoolean(cssValue)==true) {
	                                	centerLabelStyleDescription.getLabelFormat().add(FontFormat.BOLD_LITERAL);
	                                }else if(cssKey.equals("underline") && Boolean.parseBoolean(cssValue)==true) {
	                                	centerLabelStyleDescription.getLabelFormat().add(FontFormat.UNDERLINE_LITERAL);
	                                }else if(cssKey.equals("italic") && Boolean.parseBoolean(cssValue)==true) {
	                                	centerLabelStyleDescription.getLabelFormat().add(FontFormat.ITALIC_LITERAL);
	                                }
	                            }
	                            centerLabelStyleDescription.setLabelExpression(stereotypeName);
	                            edgeStyle.setCenterLabelStyleDescription(centerLabelStyleDescription);
	                        	edgeStyle.getCenterLabelStyleDescription().setShowIcon(false);
	                            edgeMapping.setStyle(edgeStyle);
	                        }
	                    }
	                }
	            }
	        }
		}
		return edgeMapping;
	}

}
