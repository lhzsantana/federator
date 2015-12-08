#NoSQL Federator [![Build Status](https://travis-ci.org/lhzsantana/federator.svg?branch=master)](https://travis-ci.org/lhzsantana/federator)

This project aims to create a middleware in between the Applications API and NoSQL databases. Currently, we support MongoDB, Cassandra, Redis and Neo4J. 

#Usage (we are still improving this section)

0. Build the project issuing "mvn package" in the folder /federator

1. Start the server by issuing "java -jar federator-0.0.1-SNAPSHOT.jar"         
2. It will be started the following endpoints:

* http://localhost:6666/_mapping/1
* http://localhost:6666/_insert/1
* http://localhost:6666/_query/1
* http://localhost:6666/_get/1


3. Start by adding a new mapping in the endpoint "http://localhost:8085/_mapping"
4. Add some new entities based on the mapping (see section below)

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
        type: [user]
        source: ['entity']
  posts:
    body: 
        type: [string]
        source: ['mongodb']
    createdAt:
        type: [date]
        source: ['mongodb']
    author:
        type: [usuario]
        source: [entity]
  comments:
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

#Inserting

All the communications with Federator must be done using JSON. For instance if we want to insert a User in the architecture, we have can do:

```
{
\"user\":{
\"username\":\"luiz\",
\"password\":\"topsecret\",
\"address\":\"Lagoa da Conceição, Florianópolis, Brazil\"
}
}
```
 





