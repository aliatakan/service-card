# service-card
repository for a sample shopping card restfull service

There are lots of article about micro services architecture (and also restfull services). The microservice architecture which is an approach to divide up business needs into the small piece of deployable services is becoming so popular. That is why i tried to write and give a simple restfull service sample by using spring boot which is a de dacto standart in the industry for the java world.

#### Why do we need to change our architecture to the microservices ?

1. For instance, you are the part of a internet company and your company have a high traffic load. You need scalability ! Microservice architecture solve this problem because your services are minimal, deployable and loosely coupled (or if you can achieve, decoupled is better)

2. When a microservice goes down, your system is still alive.

3. Microservices are minimal so that code is minimal so that even a junior developer can easily undestand the code (if you obey the clean code / Code Review standards).

4. You can use any language while developing a service. You can use java in a service and you can prefer scala/.net/etc for the another one. Because your tech-stack have a lot of microservice and their coding language would be different.

#### Cons ?

Data management ! There would be a headache to sustain data consistency. For example, two microservices use RDMS and both of them have to had their own data store. There is a CUSTOMER table and both RDMS has this table. Both CUSTOMER tables must be up to date. This is a big issue you have to deal with.

#### Let's talk about the service-card

1. It is a Java 8 spring boot project

2. There are controller, entity, mapper, repository and service folders

3. It uses postgresql to hold the product information and redis to hold shopping card. Service must access the shopping card as soon as possible. If you use RDBMS instead of redis (or maybe cassandra), you will be slow in the market. All connection settings are in the resources/application.properties

4. CardController is a rest controller and it has addToCard and getCard methods
- getCard : it serves the shopping card by requesting /sepet/{id}. GET
- addToCard : it adds a product to the card. This product must be in the postgre and have at least 1 stock. POST

5. CardController uses CardService to access the data

6. CardService uses ProductRepository for postgre data and RedisRepository for redis data

Not : Product entity has stock and productTitlePrice that are should not be in this class but i have limited time (i could not seperate and refactor, sorry) and somebody may change some part of the project. Please focus on the concept.


```
/sepet/{id}. POST
{
	"productId" : 262,
	"count" : 2
}

/sepet/{id}. GET
{
    "cardId": "1",
    "cartProducts": [
        {
            "productId": 262,
            "productTitle": "Arçelik M344 Otomatik Çamaşır Makinası",
            "count": 2,
            "price": 1350,
            "productTotalPrice": 2700,
            "stock": 10
        }
    ],
    "cardTotalPrice": 2700,
    "cardDate": null
}
```
