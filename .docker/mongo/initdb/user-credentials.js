let now = new Date()

db['user-credentials'].insertMany([
    {
        _id: "user",
        username: "user",
        encodedPassword: "$2a$10$wWY9tn/jnn66d6gsBpGM3.KZXXn7AmN0U/JVGzioJjtQFOJkCYFVa",
        roles: [
            'user'
        ],
        metadata: {
            createdAt: now,
            createdBy: 'user',
            updatedAt: now,
            updatedBy: 'user'
        },
        _class: 'com.jeferro.products.components.products.mongo.authentications.dtos.UserCredentialsMongoDTO'
    }
])
