package transfromer;

import java.util.Scanner;

import org.eclipse.epsilon.emc.emf.EmfModel;

public abstract class Transformer {
	public Scanner scanner;
	abstract public EmfModel transform(EmfModel model) throws Exception;
}
