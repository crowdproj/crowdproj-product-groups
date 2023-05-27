package com.crowdproj.marketplace.product.group.biz.general

import com.crowdproj.kotlin.cor.handlers.CorChainDsl
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.marketplace.product.group.common.PrgrpContext
import com.crowdproj.marketplace.product.group.common.helpers.errorAdministration
import com.crowdproj.marketplace.product.group.common.helpers.fail
import com.crowdproj.marketplace.product.group.common.models.PrgrpWorkMode
import repo.IPrgrpRepository

fun CorChainDsl<PrgrpContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Calculation of the main working repository depending on the required operating mode        
    """.trimIndent()
    handle {
        prgrpRepo = when {
            workMode == PrgrpWorkMode.TEST -> settings.repoTest
            workMode == PrgrpWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != PrgrpWorkMode.STUB && prgrpRepo == IPrgrpRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}