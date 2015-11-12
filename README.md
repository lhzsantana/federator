#NoSQL Federator [![Circle CI](https://circleci.com/gh/lhzsantana/federator/tree/master.svg?style=svg)](https://circleci.com/gh/lhzsantana/federator/tree/master)

This project aims to create a middleware in between the Applications API and NoSQL databases. Currently, we support MongoDB, Cassandra, Redis and Neo4J. The start point of Federator use is the rendezvous.yml file, where you can create a mapping between you application and the final NoSQL store.

#Usage (we are still improving this section)

##As a middleware
1. Start the server by issuing
2. Add a new mapping in the endpoint "http://localhost:8085/_mapping"
3. Start adding new entities


##As a framework
1. Import the Maven dependency
2. Change the file mapping in the root of the project
3. Start adding new entities

#Datasources

Theoretically, the Federator can be used with any underlying data source. However,  the current offers out-of-the-box only access to Neo4J, Cassandra, MongoDB and Redis. To use these databases, you have to setup its details in the file datasources.yml. For instance:


```
datasources:
  mongodb:
    host: 127.0.0.1
    port: 27017
    collection: collection
    database: database
  cassandra:
    host: 127.0.0.1
    port: 9042
    keyspace: federator
  neo4j:
    host: 127.0.0.1
    port: 7474
    neo4j.path: /target
  redis:
    redis.host: 127.0.0.1
    redis.port: 6379
```

#Mapping

For using each datasource, the Federator expects to receive a mapping. The mapping contains all the entities that will be stored in the Federator and how this entity should be spanned through the architecture. For instance:

```
entities:
  user: 
    name: 
        type: [string]
        source: ['mongodb','cassandra']
    username:
        type: [string]
        source: ['mongodb']        
    password: 
        type: [string]
        source: ['mongodb','cassandra']
    address: 
        type: [string]
        source: ['mongodb','cassandra']
    email:
        type: [string]
        source: ['cassandra']        
    follows:
        type: [usuario99]
        source: ['entity']
  posts1:
    body: 
        type: [string]
        source: ['mongodb']
    createdAt:
        type: [date]
        source: ['mongodb']
    author:
        type: [usuario]
        source: [entity]
  comments1:
    body: 
        type: [string]
        source: ['mongodb']
    createdAt:
        type: [date]
        source: ['mongodb']
    parent:
        type: [post]
        source: [entity]
```
