# Code Organization Approaches

## 📚 Learning Objectives
- Understand how code can be organized for applications using Semantic Kernel
- Learn different types of SK plugins
- Implement code that uses different types of SK plugins

## 📑 Task

### Open "Lab4" Project
For this task and for each subsequent task, use the results from the previous task as a basis for new changes.

#### Use the version of Semantic Kernel starting from 1.2 or higher
Semantic Kernel as everything connected with AI rapidly evolves.
It is important to use the latest version of the library to get experience and understand of latest tools.

### Call function from custom plugin:
Review the custom plugin - AgeCalculatorPlugin code from the  [plugin](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/tree/main/gen-ai-bootcamp/tasks/lab4/gen_ai_training/src/main/java/com/epam/training/gen/ai/semantic/plugin) folder.
Or use [this](https://devblogs.microsoft.com/semantic-kernel/using-semantic-kernel-to-create-a-time-plugin-with-java/) is an example

Your task is to use the AgeCalculatorPlugin into the existing system. 
Once integrated, the system should be able to calculate ages based on user-provided birthdays.
Upon successful integration, the system should accurately calculate and display ages based on user-provided birthdays, enhancing its functionality.

### Wikipedia Search URL Integration
Your task is to integrate the `getWikipediaSearchUrl` function from the provided
SearchUrlPlugin into the existing system. This function takes a search query
as input and generates a URL for searching that query on Wikipedia. The
integration should seamlessly incorporate this functionality into the system,
allowing users to generate Wikipedia search URLs easily.

Call the plugin with the next parameters:
```csharp
    { "query", "Europe" },
```
Output chat history and validate that it contains the uri for "Europe" page on Wikipedia.

### Implement custom plugin
Your task is to implement the BingSearchUrl function within the existing system.
This function should generate a URL for Bing search queries based on the provided
search query input. The integration should seamlessly incorporate this functionality
into the system, allowing users to generate Bing search URLs effortlessly.

Call the plugin with the next parameters:
```csharp
    { "query", "cute kittens on vespas" },
```
Output chat history and validate that it contains the uri to review cute kittens on vespas.

### Closing
Create pull requests to your repository.

Attach link to your PR to your tasks on learn.epam.com.

### Free practice:
Review plugins code from official [Semantic Kernel repository](https://github.com/microsoft/semantic-kernel/tree/main/dotnet/src/Plugins), implement your own plugin and test it with your application.

### Evaluation Criteria
1. Configuration for Azure OpenAI Chat Completion Service
- Correctly modified the application.properties file to include the necessary settings for the Azure OpenAI chat completion service (10%)

2. Integration of AgeCalculatorPlugin
- Successfully integrated the AgeCalculatorPlugin into the existing system (10%)
- System accurately calculates and displays ages based on user-provided birth dates (10%)

3. Integration of Wikipedia Search URL Function
- Successfully integrated the getWikipediaSearchUrl function from the SearchUrlPlugin into the existing system (10%)
- System correctly generates and displays Wikipedia search URLs based on user input (10%)
- Validated that the chat history contains the URI for the "Europe" page on Wikipedia (10%)

4. Implementation and Integration of BingSearchUrl Function
- Correctly implemented the BingSearchUrl function within the existing system (10%)
- System correctly generates and displays Bing search URLs based on user input (10%)

5. Functionality Testing
- Application runs without errors (10%)
- Responses are generated correctly and are relevant to the prompts (10%)
