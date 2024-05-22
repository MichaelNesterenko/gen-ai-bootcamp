#  Vector Databases

## 📑 About
* In this task you need to implement a simple application that stores embeddings in a database.
* You can use Spring AI library for this task. This is not mandatory.
* You can use any vector database for storing embeddings.
  * for example, `Postgres` with `pgvector` plugin. [Pgvector store autoconfiguration](https://github.com/spring-projects/spring-ai/blob/main/vector-stores/spring-ai-pgvector-store/src/main/java/org/springframework/ai/vectorstore/PgVectorStore.java)
* You can use any LLM client for embeddings.
  * for example, Azure OpenAI from previous modules
  * for example, [OLlama](https://ollama.com/) for using free LLM for free.

## Acceptance Criteria
Implement application with REST API with following functionality.
* Build embedding from the text.
* Build embedding from the PDF-file.
* Build and store embedding from the text.
* Build and store embedding from the PDF-file.
  * Screenshot with creating and storing embeddings in the database must be attached to the task on learn.epam.com
* Search for the closest embeddings in the database based on the input text.
  * Screenshot with searching for the closest embeddings in the database must be attached to the task on learn.epam.com

## Self-test questions
You can index and test your knowledge by answering the following questions.
* What is embedding?
* How input with embedding differs from input in a text format?
* Can be picture converted to the embedding?
* What is Diversity measurement? What is the Distance? What is Similarity in vectors?
* What is more important for in text embedding: keep short distance for the similar meaning, or keep short distance for semantically similar words?
* What is Clustering? What is Classification?
* What for vector DBs are used in GenAI?
* What is dimensionality of the vector? What is dimensionality of the model? What is dimensionality of the vector DB?  
* Does vector build with one model can be used with another model?