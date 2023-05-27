import com.crowdproj.marketplace.product.group.logging.common.LoggerProvider
import repo.IPrgrpRepository

data class PrgrpCorSettings(
    val loggerProvider: LoggerProvider = LoggerProvider(),
    val repoStub: IPrgrpRepository = IPrgrpRepository.NONE,
    val repoTest: IPrgrpRepository = IPrgrpRepository.NONE,
    val repoProd: IPrgrpRepository = IPrgrpRepository.NONE,
) {
    companion object {
        val NONE = PrgrpCorSettings()
    }
}