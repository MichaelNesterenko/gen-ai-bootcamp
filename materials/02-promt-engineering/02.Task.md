# Prompt Engineering Fundamentals  

## 📚 Learning Objectives
 - Understand the importance of prompt engineering
 - Learn how to use prompts with Semantic Kernel
 - Learn how to parameterize the prompts


## 📑 Task 

### Open "Lab2" Project

For this task please use the "Lab1" project located in the [`tasks/lab1` folder](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/tree/main/gen-ai-bootcamp/tasks/lab1/gen_ai_training) of your course materials. This project contains the initial setup required for this task, including the necessary project configuration and dependencies.

### Configure the Application:
You'll need to configure the [application](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/tree/main/gen-ai-bootcamp/tasks/lab1/gen_ai_training) to use the Azure OpenAI chat completion service. You can do this by adding the following settings to the [application.properties](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/blob/main/gen-ai-bootcamp/tasks/lab2/gen_ai_training/src/main/resources/config/application.properties) file:

```yaml
client-azureopenai-key: your-key
client-azureopenai-endpoint: https:/your.ai-api.com/ 
client-azureopenai-deployment-name: gpt-xx-turbo
```

### Implement more complex code:
Initialize PromptExecutionSettings and fill it with the following settings:

```
put("gpt-xx-turbo", PromptExecutionSettings.builder()
           .withMaxTokens(1_000)
           .withTemperature(0d)
           .build());
```
Try to increase the temperature value, what was changed?

Get ChatCompletionService and initiate ChatHistory for storing all user and system messages to ChatHistory.

```csharp
ChatCompletionService chat = OpenAIChatCompletion.builder()
            .withModelId(MODEL_ID)
            .withOpenAIAsyncClient(client)
            .build();

ChatHistory history = new ChatHistory();
history.addUserMessage"Hi, I'm looking for book suggestions");
```

Setup function for chatting with OpenAI based on information of ChatHistory.

Validate the result and print it to the console.

### Closing

Create a pull request in your branch and check that the pipeline has passed.  
