let now = new Date()

db['accounts'].insertMany([
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
        _class: 'com.jeferro.products.accounts.infrastructure.adapters.mongo.dtos.AccountMongoDTO'
    }
])
