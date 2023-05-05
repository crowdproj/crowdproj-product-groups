import com.crowdproj.marketplace.product.group.logging.common.LoggerProvider

data class PrgrpCorSettings(
    val loggerProvider: LoggerProvider = LoggerProvider(),
) {
    companion object {
        val NONE = PrgrpCorSettings()
    }
}