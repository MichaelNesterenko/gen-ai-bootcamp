# API

API requests are contained in bruno collection in api folder:
* create-chat-session - to create a new session to communicate with a model
* submit-chat-message - to submit a new message to a model
* get-session-messages - to retrieve conversation log
* support-vailable-models - to list all available models
* support-weather - to get weather information
* embedding-generat - to generate an embedding using llm model
* embedding-store - to store embedding in the local postgresql database
* embedding-search. - to search for data using an embedding

# Compiling

```bash
./mvnw clean package
```

# Running

```bash
client_openai_key='<your-key>' weather_api_key='<your-key-from-weatherapi.com>' ./mvnw spring-boot:run
```