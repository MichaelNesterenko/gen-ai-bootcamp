# Introduction to Generative AI and Large Language Models

## 📚 Learning Objectives
 - Understand what Generative AI is
 - Learn what Semantic Kernel is and how it can be used
 - Implement a basic application that uses Semantic Kernel to generate text

## 📑 Task

### Open "Lab1" Project

Open the "Lab1" project located in the [`tasks/lab1` folder](tasks/lab1) of your course materials. This project contains the initial setup required for this task, including the necessary project configuration and dependencies.

### Configure the Application:

You'll need to configure the [application](tasks/lab1/gen_ai_training) to use the Azure OpenAI chat completion service. You can do this by adding the following settings to the [application.properties](file:tasks/lab1/gen_ai_training/src/main/resources/config/application.properties) file:

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


#### Create an example of usage ChatRequestFunctionMessage  as a context message associated with this chat completions request.  

Validate the result and print it to the console.  

### Closing

Create a pull request in your branch and check that the pipeline has passed.  


### Free practice:
Modify the application to use the Semantic Kernel to generate a response to a user prompt.
You can play with different prompts and see how the Semantic Kernel responds to them. 
Also you can try to use different settings for OpenAI chat completion service and see how it affects the generated responses.
