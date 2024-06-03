# Vector Databases

## 📚 Learning Objectives
- Understand how to store embeddings in a database.
- Learn to use vector databases for storing embeddings.
- Implement a simple application that builds and stores embeddings.

## 📑 Task

### Open "Lab5" Project

Open the "Lab5" project located in the [`tasks/lab5` folder](https://git.epam.com/epm-cdp/global-java-foundation-program/java-courses/-/tree/main/gen-ai-bootcamp/tasks/lab5) of your course materials. This project contains the initial setup required for this task, including the necessary project configuration and dependencies.

### Configure the Application

You need to configure the application to use a vector database for storing embeddings. You can use any vector database, such as `Postgres` with the `pgvector` plugin. Refer to the [Pgvector store autoconfiguration](https://github.com/spring-projects/spring-ai/blob/main/vector-stores/spring-ai-pgvector-store/src/main/java/org/springframework/ai/vectorstore/PgVectorStore.java) for guidance.

### Implement REST API Functionality

Your task is to implement a REST API with the following functionality:

1. **Build Embedding from Text**
   - Implement an endpoint to build an embedding from the provided text.

2. **Build Embedding from PDF File**
   - Implement an endpoint to build an embedding from the provided PDF file.

3. **Build and Store Embedding from Text**
   - Implement an endpoint to build and store an embedding from the provided text.

4. **Build and Store Embedding from PDF File**
   - Implement an endpoint to build and store an embedding from the provided PDF file.
   - Attach a screenshot showing the creation and storage of embeddings in the database to your task on learn.epam.com.

5. **Search for Closest Embeddings**
   - Implement an endpoint to search for the closest embeddings in the database based on the input text.
   - Attach a screenshot showing the search for the closest embeddings in the database to your task on learn.epam.com.

### Closing

Create pull requests to the original repository and check that the pipeline has passed.

Create a screenshot of the workflow showing the checks have passed and attach this screenshot to your tasks on learn.epam.com.

### Evaluation Criteria

1. Configuration for Vector Database (20%)
- Correctly configured the application to use a vector database (e.g., Postgres with pgvector plugin).

2. Build Embedding from Text (15%)
- Implemented an endpoint to build an embedding from the provided text.
- Verified that the endpoint correctly generates embeddings from text input.

3. Build Embedding from PDF File (15%)
- Implemented an endpoint to build an embedding from the provided PDF file.
- Verified that the endpoint correctly generates embeddings from PDF input.

4. Build and Store Embedding from Text (15%)
- Implemented an endpoint to build and store an embedding from the provided text.
- Verified that the embedding is correctly stored in the vector database.

5. Build and Store Embedding from PDF File (15%)
- Implemented an endpoint to build and store an embedding from the provided PDF file.
- Verified that the embedding is correctly stored in the vector database.

6. Search for Closest Embeddings (15%)
- Implemented an endpoint to search for the closest embeddings in the database based on the input text.
- Verified that the search functionality correctly identifies and returns the closest embeddings.

7. Functionality Testing (5%)
- Application runs without errors.
- Responses are generated correctly and are relevant to the prompts.

## Self-Test Questions

You can index and test your knowledge by answering the following questions:
- What is embedding?
- How does input with embedding differ from input in a text format?
- Can a picture be converted to an embedding?
- What is Diversity measurement? What is Distance? What is Similarity in vectors?
- What is more important for text embedding: keeping a short distance for similar meaning, or keeping a short distance for semantically similar words?
- What is Clustering? What is Classification?
- What are vector DBs used for in GenAI?
- What is the dimensionality of the vector? What is the dimensionality of the model? What is the dimensionality of the vector DB?
- Can a vector built with one model be used with another model?