let now = new Date()

db['products'].insertMany([
    {
        _id: "1",
        title: "Apple",
        description: "Description about apples",
        enabled: true,
        metadata: {
            createdBy: 'user',
            createdAt: now,
            updatedBy: 'user',
            updatedAt: now
        },
         _class: 'com.jeferro.products.products.infrastructure.adapters.mongo.dtos.ProductMongoDTO'
    },
    {
        _id: "2",
        title: "Kiwi",
        description: "Description about kiwis",
        enabled: true,
        metadata: {
            createdBy: 'user',
            createdAt: now,
            updatedBy: 'user',
            updatedAt: now
        },
        _class: 'com.jeferro.products.products.infrastructure.adapters.mongo.dtos.ProductMongoDTO'
    },
    {
        _id: "3",
        title: "Banana",
        description: "Description about bananas",
        enabled: false,
        metadata: {
            createdBy: 'user',
            createdAt: now,
            updatedBy: 'user',
            updatedAt: now
        },
        _class: 'com.jeferro.products.products.infrastructure.adapters.mongo.dtos.ProductMongoDTO'
    }
])
