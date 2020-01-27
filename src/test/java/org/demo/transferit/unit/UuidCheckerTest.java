package org.demo.transferit.unit;

import io.quarkus.test.junit.QuarkusTest;
import org.demo.transferit.utils.UuidChecker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
public class UuidCheckerTest {

    @Test
    public void isUuid_returnsTrue_whenUuidIsValid() {
        List<String> validUuids = Arrays.asList(
                "95c064c4-da15-4c0c-807c-81706c638e43",
                "175325f2-21ac-4824-af5c-d2225c1f923a",
                "f93a8ac6-1fa7-4b9a-bbee-673ef15af5f3",
                "902dea2a-c7f0-4e56-9a2d-b8273953bcae",
                "1380f507-91ee-4e8f-b37f-ed4edcff8c17",
                "1ba8b975-0b2a-4a5d-84fa-be24d55269ff",
                "d39d5c8d-f667-4e40-87e8-6d8eadd9ceb3",
                "676fb234-f31c-47f0-9d44-d3917c2f15e1",
                "b14e583d-fb5f-4de9-986a-c761dca135d4",
                "72a0cf03-2391-40c8-8d1c-ac8948e99ae3",
                "32ee1a05-0e20-4bb3-a258-c2ba515e12b2",
                "c76905f0-9f9f-43af-afdf-b71fd8eae288",
                "ee2e3e58-4ad2-467c-87a1-c394efe0c9c3",
                "6431df6f-5c22-4617-93af-805c3ef9cf93",
                "21b39973-d856-4528-a879-56da4ff3d6a9"
        );

        validUuids.forEach(uuid -> assertTrue(UuidChecker.isUuid(uuid)));
    }

    @Test
    public void isUuid_returnsFalse_whenUuidIsNotValid() {
        List<String> invalidUuids = Arrays.asList(
                null,
                "",
                "    ",
                "1234",
                "DOESNOTEXIST",
                "95c064c-da15-4c0c-807c-81706c638e43",
                "95c064c4-da1-4c0c-807c-81706c638e43",
                "95c064c4-da15-4c0-807c-81706c638e43",
                "95c064c4-da15-4c0c-807-81706c638e43",
                "95c064c4-da15-4c0c-807c-81706c638e4"
        );

        invalidUuids.forEach(uuid -> assertFalse(UuidChecker.isUuid(uuid)));
    }
}
