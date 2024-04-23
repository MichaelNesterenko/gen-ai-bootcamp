# GenAI Bootcamp - Lab 5: Embedding

## Prerequisites
* Requires JDK17
* Requires Docker

## Application Setup

### 1. Run PGVector
`docker-compose up`
* test with opening pgadmin `http://localhost:5050. Login is `pgadmin4@pgadmin.org` and password is `admin`.

### 2. Connect to LLM
* Option A: set your own Azure OpenAI key and URL to the system variables
  * `OPENAI_API_KEY` = _your key_
  * `OPENAI_API_URL` = _your url_
  * _note_ properties `client-azureopenai-*` must be enabled
  * _note_ dependecy `spring-ai-azure-openai-spring-boot-starter` must be added to `pom.xml`
* Option B: use local LLL, for example Ollama.
  * [Docs](https://ollama.com/)
  * install and run Ollama
    ```
    # https://github.com/ollama/ollama
    curl -fsSL https://ollama.com/install.sh | sh
    # https://hub.docker.com/r/ollama/ollama
    docker run -d -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
    ```
  * set properties in `application.properties`
    ```
    spring.ai.ollama.base-url=http://localhost:11434
    spring.ai.ollama.embedding.options.model=llama3
    ```
  * _note_ ```client-azureopenai-*``` properties must be disabled, dependency `spring-ai-azure-openai-spring-boot-starter` must be removed from `pom.xml` 
  * _note_ dependecy `spring-ai-ollama-spring-boot-starter` must be added to `pom.xml`

### 3. Launch the application
* `./mvnw spring-boot:run`
* test with 
`curl --location --request GET 'localhost:8080/ai/dimensions'`
`curl --location --request GET 'http://localhost:8080/ai/embedding?message=do-vector-pls'`
`curl --location --request POST 'localhost:8080/ai/embedding' \
  --header 'Content-Type: application/json' \
  --data '{"field":"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry'\''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."}'`
`curl --location --request GET 'localhost:8080/ai/embedding/search?message=lorem'`

  
## API
OpenAPI spec:
```
openapi: 3.0.0
info:
  title: AI Embedding API
  version: 1.0.0
paths:
  /ai/embedding:
    get:
      summary: Get embedding for a message
      parameters:
        - name: message
          in: query
          description: Message to embed
          required: false
          schema:
            type: string
            default: "Tell me a joke"
      responses:
        '200':
          description: A map containing the embedding
          content:
            application/json:
              schema:
                type: object
                properties:
                  embedding:
                    type: object
    post:
      summary: Add a document for embedding
      parameters:
        - name: message
          in: query
          description: Message to add
          required: false
          schema:
            type: string
      requestBody:
        description: Document to add
        required: false
        content:
          text/plain:
            schema:
              type: string
      responses:
        '200':
          description: Document added successfully
  /ai/embedding/search:
    get:
      summary: Search documents for a message
      parameters:
        - name: message
          in: query
          description: Message to search
          required: false
          schema:
            type: string
            default: "prompt engineering"
      responses:
        '200':
          description: A list of matching documents
          content:
            application/json:
              schema:
                type: array
                items:
                  type: string
  /ai/upload-embedding:
    post:
      summary: Upload a file for embedding
      requestBody:
        required: true
        content:
          multipart/form-data:
            schema:
              type: object
              properties:
                file:
                  type: string
                  format: binary
      responses:
        '200':
          description: File uploaded successfully
  /ai/dimensions:
    get:
      summary: Get the dimensionality of the embeddings
      responses:
        '200':
          description: The dimensionality of the embeddings
          content:
            text/plain:
              schema:
                type: integer
```