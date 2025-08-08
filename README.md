GEM Migration Tooling
This project provides tooling support for the GEM migration framework. It was developed using the Eclipse Runtime (Version: 2021-03) and relies on several Eclipse Modeling technologies.

Requirements
To compile and run this project, the following dependencies must be installed:

Java (with Maven support)

Eclipse Modeling Framework (EMF)
https://eclipse.dev/emf/

Sirius Desktop
https://eclipse.dev/sirius/download.html

All dependencies are publicly available and can be downloaded and installed manually.

How to Run
To launch the application:

Navigate to the parser package.

Run the main method in the ViewModelTransformerWithPivot class.

The program will prompt the user to provide:

The path to the input model.

The target migration environment.

Once the paths are provided, the tool will perform the transformation and store the generated models in the generated folder.

Transformation Logic
Integrated transformers are located in the etl and transformer folders.

All Java-based transformers must implement a transform method that accepts an EMFModel as input and returns a transformed EMFModel.

ETL-based transformations follow the same behavior and are implemented using the Epsilon Transformation Language (ETL).

