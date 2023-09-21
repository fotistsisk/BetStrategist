package com.fotistsiskakis.betstrategist.services

import spock.lang.Specification

class UtilsSpec extends Specification{

    def "Get UUID from valid string"() {
        given:
        String validUuidString = "123e4567-e89b-12d3-a456-426614174000"

        when:
        UUID result = Utils.getUuidFromString(validUuidString)

        then:
        result != null
    }

    def "Attempt to get UUID from invalid string"() {
        given:
        String invalidUuidString = "invalid_uuid"

        when:
        Utils.getUuidFromString(invalidUuidString)

        then:
        def exception = thrown(IllegalArgumentException.class)
        exception.message == "Invalid UUID format"
    }

}
