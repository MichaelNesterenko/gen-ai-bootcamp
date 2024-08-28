# Introduction to Generative AI and Large Language Models

## 📚 Learning Objectives
 - Understanding what Generative AI-based applications is and how Large Language Models (LLMs) work.
 - Learn which frameworks we have in Java and how they can be used
 - Implement a basic application that uses Semantic Kernel to generate text

## 📑 Task

### Open "Lab1" Project

Open the "Lab1" project located in the [`tasks/lab1` folder](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/tree/main/gen-ai-bootcamp/tasks/lab1/gen_ai_training) of your course materials. This project contains the initial setup required for this task, including the necessary project configuration and dependencies.

### Configure the Application:

You'll need to configure the [application](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/tree/main/gen-ai-bootcamp/tasks/lab1/gen_ai_training) to use the Azure OpenAI chat completion service. You can do this by adding the following settings to the [application.properties](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/blob/main/gen-ai-bootcamp/tasks/lab1/gen_ai_training/src/main/resources/config/application.properties) file:

```yaml
client-azureopenai-key: your-key
client-azureopenai-endpoint: https:/your.ai-api.com/ 
client-azureopenai-deployment-name: gpt-xx-turbo
```
  
### Implement more complex code:

#### Create a controller that will accept a prompt as a request parameter and return the response as a response body   
```json
{ "input": "I want to find top-10 books about world history" }
```

#### Use the version of Semantic Kernel starting from 1.2 or higher
Semantic Kernel as everything connected with AI rapidly evolves.
It is important to use the latest version of the library to get experience and understand of latest tools.

#### Create an example of usage ChatRequestFunctionMessage  as a context message associated with this chat completions request.  

Validate the result and print it to the console.  

### Closing
Create a pull request in your branch and check that the pipeline has passed.  


### Free practice:
Modify the application to use the Semantic Kernel to generate a response to a user prompt.
You can play with different prompts and see how the Semantic Kernel responds to them. 
Also you can try to use different settings for OpenAI chat completion service and see how it affects the generated responses.

### Evaluation Criteria
1. Configuration of Application Properties
- Correctly added Azure OpenAI settings to application.properties file (10%)
- Values are placeholders and not hardcoded sensitive information (10%)

2. Controller Implementation
- Controller correctly accepts a prompt as a request parameter (15%)
- Controller returns the response in the correct JSON format (10%)

3. Usage of ChatRequestFunctionMessage
- Correctly created an example of using ChatRequestFunctionMessage (10%)
- Validated the result and printed it to the console (10%)

4. Semantic Kernel Integration
- Modified the application to use the Semantic Kernel for generating responses (10%)
- Demonstrated the ability to play with different prompts and settings(10%)

5. Functionality Testing
- Application runs without errors (10%)
- Responses are generated correctly and are relevant to the prompts (5%)