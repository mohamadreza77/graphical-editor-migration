# GEM Migration Tooling
<!-- This is the main project title (Level 1 heading) -->

This project provides tooling support for the **GEM migration framework**.  
It was developed using the Eclipse Runtime (**Version: 2021-03**) and relies on several Eclipse Modeling technologies.
<!-- Use bold for important terms; hard wrap lines for readability -->

## Requirements
<!-- Level 2 heading for a new section -->

To compile and run this project, the following dependencies must be installed:

- **Java** (with Maven support)
- **Eclipse Modeling Framework (EMF)**  
  [https://eclipse.dev/emf/](https://eclipse.dev/emf/)
- **Sirius Desktop**  
  [https://eclipse.dev/sirius/download.html](https://eclipse.dev/sirius/download.html)
<!-- Use bullet points and hyperlinks with Markdown syntax -->

All dependencies are publicly available and can be downloaded and installed manually.

## How to Run
<!-- Another Level 2 heading -->

To launch the application:

1. Navigate to the `parser` package.
2. Run the `main` method in the `ViewModelTransformerWithPivot` class.
<!-- Numbered list for step-by-step instructions -->

The program will prompt the user to provide:

- The path to the input model.
- The target migration environment.

Once the paths are provided, the tool will perform the transformation and store the generated models in the `generated` folder.
<!-- Break text into readable short paragraphs -->

## Transformation Logic
<!-- Final section -->

- Integrated transformers are located in the `etl` and `transformer` folders.
- All Java-based transformers must implement a `transform` method that accepts an `EMFModel` as input and returns a transformed `EMFModel`.
- ETL-based transformations follow the same behavior and are implemented using the **Epsilon Transformation Language (ETL)**.
<!-- Use inline code for class/method names or file paths -->
