#NoSQL Federator

This project aims to create a middleware in between the Applications API and NoSQL databases. Currently we support MongoDB, Cassandra, Redis and Neo4J. The start point of Federator use is the rendezvous.yml file, where you can create a mapping between you application and the final NoSQL store.

#Usage (we are still improving this section)

##As a middleware
1. Start the server by issuing
2. Add a new mapping in the endpoint "http://localhost:8085/_mapping"
3. Start adding new entities


##As a framework
1. Import the Maven dependency
2. Change the file mapping in the root of the project
3. Start adding new entities
