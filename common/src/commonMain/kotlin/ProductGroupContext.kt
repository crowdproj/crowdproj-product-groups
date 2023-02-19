import kotlinx.datetime.Instant
import models.*
import stubs.ProductGroupStubs

data class ProductGroupContext(

    var command: ProductGroupCommand = ProductGroupCommand.NONE,
    var state: ProductGroupState = ProductGroupState.NONE,
    var errors: MutableList<ProductGroupError> = mutableListOf(),
    var workMode: ProductGroupWorkMode = ProductGroupWorkMode.PROD,
    var stubCase: ProductGroupStubs = ProductGroupStubs.NONE,
    var requestId: ProductGroupRequestId = ProductGroupRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var pgRequest: ProductGroup = ProductGroup(),
    var pgFilterRequest: ProductGroupFilter = ProductGroupFilter(),
    var pgResponse: ProductGroup = ProductGroup(),
    var pgsResponse: MutableList<ProductGroup> = mutableListOf()
)
