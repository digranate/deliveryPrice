import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


import static org.junit.jupiter.api.Assertions.*;

@Nested
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DeliveryPriceTest {

    DeliveryPrice deliveryPrice = new DeliveryPrice();

    @ParameterizedTest(name = "Test case: distance = {0}, isLarge = {1}, isFragile = {2}, LoadType = {3}.")
    @CsvFileSource(resources = "/Pairwise_valid_cases.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Pairwise combinations for valid cases.")
    void deliveryPriceValidCasesTest(int distance, boolean isLarge, boolean isFragile, LoadType loadType, String expectedResult) {
        assertEquals(Double.parseDouble(expectedResult), deliveryPrice.get(distance, isLarge, isFragile, loadType), 0.000001);
    }

    @ParameterizedTest(name = "Test case: distance = {0}, isLarge = {1}, isFragile = {2}, LoadType = {3}.")
    @CsvFileSource(resources = "/Pairwise_distance_invalid.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Pairwise combinations for non-positive distance .")
    void deliveryPriceNonPositiveDistanceTest(int distance, boolean isLarge, boolean isFragile, LoadType loadType) {
        Assertions.assertThrows(DeliveryPriceException.class, () -> deliveryPrice.get(distance, isLarge, isFragile, loadType));
    }

    @ParameterizedTest(name = "Test case: distance = {0}, isLarge = {1}, isFragile = {2}, LoadType = {3}.")
    @CsvFileSource(resources = "/Pairwise_distance_fragile_invalid.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Pairwise combinations for invalid fragile and long distance.")
    void deliveryPriceFragileAndLongDistanceTest(int distance, boolean isLarge, boolean isFragile, LoadType loadType) {
        Assertions.assertThrows(DeliveryPriceException.class, () -> deliveryPrice.get(distance, isLarge, isFragile, loadType));
    }
}