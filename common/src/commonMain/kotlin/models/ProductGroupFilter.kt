package models

data class ProductGroupFilter(
    var id: ProductGroupId = ProductGroupId.NONE,
    var name: String = "",
    var description: String = "",
    var properties: String = "",
    var deleted: Boolean = false
)