let now = new Date()

db['product-reviews'].insertMany([
    {
        _id: "1:user",
        comment: "Comment about apple by user",
        metadata: {
            createdBy: 'user',
            createdAt: now,
            updatedBy: 'user',
            updatedAt: now
        },
         _class: 'com.jeferro.products.components.products.mongo.products.dtos.ProductReviewMongoDTO'
    }
])
