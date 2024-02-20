let now = new Date()

db['product-reviews'].insertMany([
    {
        _id: "one-product:user",
        comment: "Comment about product one by user",
        metadata: {
            createdBy: 'user',
            createdAt: now,
            updatedBy: 'user',
            updatedAt: now
        },
         _class: 'com.jeferro.products.components.products.mongo.products.dtos.ProductReviewMongoDTO'
    }
])
