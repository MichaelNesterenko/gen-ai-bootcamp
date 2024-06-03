# Code Organization Approaches

## 📚 Learning Objectives
- Understand how code can be organized for applications using Semantic Kernel
- Learn different types of SK plugins
- Implement code that uses different types of SK plugins

## 📑 Task

### Open "Lab4" Project

Open the "Lab4" project located in the  [`tasks/lab4` folder](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/tree/main/gen-ai-bootcamp/tasks/lab4/gen_ai_training) of your course materials. This project contains the initial setup required for this task, including the necessary project configuration and dependencies.

### Configure the Application:

You'll need to configure the application to use the Azure OpenAI chat completion service. You can do this by adding the following settings to the application.properties file:

```yaml
client-azureopenai-key: your-key
client-azureopenai-endpoint: https:/your.ai-api.com/ 
client-azureopenai-deployment-name: gpt-xx-turbo
```

### Call function from custom plugin:

Review the custom plugin - AgeCalculatorPlugin code from the  [plugin](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/tree/main/gen-ai-bootcamp/tasks/lab4/gen_ai_training/src/main/java/com/epam/training/gen/ai/semantic/plugin) folder.

Your task is to use the AgeCalculatorPlugin into the existing system. 
Once integrated, the system should be able to calculate ages based on user-provided birth dates.
Upon successful integration, the system should accurately calculate and display ages based on user-provided birth dates, enhancing its functionality.

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

Create pull requests to the original repository and check that pipeline has passed.

Create a screenshot of the workflow showing the checks have passed and attach this screenshot to your tasks on learn.epam.com.

### Free practice:
Review plugins code from official [Semantic Kernel repository](https://github.com/microsoft/semantic-kernel/tree/main/dotnet/src/Plugins), implement your own plugin and test it with your application.

### Evaluation Criteria

1. Configuration for Azure OpenAI Chat Completion Service (20%)
- Correctly modified the application.properties file to include the necessary settings for the Azure OpenAI chat completion service.

2. Integration of AgeCalculatorPlugin (20%)
- Successfully integrated the AgeCalculatorPlugin into the existing system.
- System accurately calculates and displays ages based on user-provided birth dates.

3. Integration of Wikipedia Search URL Function (20%)
- Successfully integrated the getWikipediaSearchUrl function from the SearchUrlPlugin into the existing system.
- System correctly generates and displays Wikipedia search URLs based on user input.
- Validated that the chat history contains the URI for the "Europe" page on Wikipedia.

4. Implementation and Integration of BingSearchUrl Function (20%)
- Correctly implemented the BingSearchUrl function within the existing system.
- System correctly generates and displays Bing search URLs based on user input.
- Validated that the chat history contains the URI to review "cute kittens on vespas."

5. Functionality Testing (20%)
- Application runs without errors.
- Responses are generated correctly and are relevant to the prompts.