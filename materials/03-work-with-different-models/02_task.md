
# Working with Different Models

## 📚 Learning Objectives
- Meet with Azure AI Studio
- Review the compatibility of the Hugging Face
- Write an application that uses different models with Semantic Kernel

## 📑 Task

### Open "Lab1" Project

For this task please use the "Lab1" project located in the [`tasks/lab1` folder](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/tree/main/gen-ai-bootcamp/tasks/lab1/gen_ai_training) of your course materials. This project contains the initial setup required for this task, including the necessary project configuration and dependencies.


#### Configure the Application:
You'll need to configure the [application](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/tree/main/gen-ai-bootcamp/tasks/lab1/gen_ai_training) to use the Azure OpenAI chat completion service. You can do this by adding the following settings to the [application.properties](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/blob/main/gen-ai-bootcamp/tasks/lab2/gen_ai_training/src/main/resources/config/application.properties) file:

```yaml
client-azureopenai-key: your-key
client-azureopenai-endpoint: https:/your.ai-api.com/ 
client-azureopenai-deployment-name: gpt-xx-turbo
```


### Call other models from Dial service:

For calling another model in Dial service deployment name should be changed. Example:

```yaml
client-azureopenai-deployment-name: Mixtral-8x7B-Instruct-v0.1
```

More deployment names can be retrieved from following API:

```shell
curl  -v 'https://ai-proxy.lab.epam.com/openai/deployments' \
-H "Api-Key: $MY_KEY"
```

Compare results for same prompts for different models and with using different PromptExecutionSettings. More details about PromptExecutionSettings are in  "Lab2" in the [`materials/lab2` folder](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/tree/main/gen-ai-bootcamp/materials/02-promt-engineering)

### Free practice:

Modify the application to use the Semantic Kernel to generate images using Imagen model.
