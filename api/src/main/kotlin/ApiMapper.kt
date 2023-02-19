import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

val apiMapper = ObjectMapper().apply {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}