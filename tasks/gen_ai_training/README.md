# API

API requests are contained in bruno collection in api folder:
* create-chat-session - to create a new session to communicate with a model
* submit-chat-message - to submit a new message to a model
* get-session-messages - to retrieve conversation log
* support-vailable-models - to list all available models
* support-weather - to get weather information

# Compiling

```bash
./mvnw clean package
```

# Running

```bash
client_openai_key='<your-key>' weather_api_key='<your-key-from-weatherapi.com>' ./mvnw spring-boot:run
```