import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryPrice2Test {
    DeliveryPrice deliveryPrice = new DeliveryPrice();

    @ParameterizedTest
    @CsvSource({
            "1,        50",
            "2,        100",
            "3,        100",
            "5,        100",
            "9,        100",
            "10,        200",
            "11,        200",
            "15,        200",
            "29,        200",
            "30,        300",
            "31,        300",
            "500,        300",
    })
    void calculateWithDistanceValidValues(int distance, int expectedResult) {
        assertEquals(expectedResult, deliveryPrice.calculateWithDistance(distance));
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "0"
    })
    void calculateWithDistanceInvalidValues(int distance) {
        Assertions.assertThrows(DeliveryPriceException.class, () -> deliveryPrice.calculateWithDistance(distance));
    }

    @ParameterizedTest
    @CsvSource({
            "true,         200",
            "false,        100"
    })
    void calculateWithSize(boolean isLarge, int expectedResult) {
        assertEquals(expectedResult, deliveryPrice.calculateWithSize(isLarge));
    }

    @ParameterizedTest
    @CsvSource({
            "10,	true,	300",
            "10,	false,	0",
            "29,	false,	0",
            "29,	true,	300",
            "30,	false,	0",
            "30,	true,   300",
            "31,	false,	0",
            "500,	false,	0"

    })
    void calculateWithFragileValidValues(int distance, boolean isFragile, int expectedResult) {
        assertEquals(expectedResult, deliveryPrice.calculateWithFragile(isFragile, distance));
    }

    @ParameterizedTest
    @CsvSource({
            "31,	true",
            "500,	true",
    })
    void calculateWithFragileNotValidValues(int distance, boolean isFragile) {
        Assertions.assertThrows(DeliveryPriceException.class, () -> deliveryPrice.calculateWithFragile(isFragile, distance));

    }

    @ParameterizedTest
    @CsvSource({
            "EXTREME,         1.6",
            "VERY_HIGH,        1.4",
            "HIGH,         1.2",
            "NORMAL,        1",
    })
    void calculateWithLoadType(LoadType loadType, double expectedResult) {
        assertEquals(expectedResult, deliveryPrice.calculateWithLoadType(loadType), 0.000001);
    }

    @ParameterizedTest
    @CsvSource({
            "100,        400",
            "399,        400",
            "400,        400",
            "401,        401",
            "700,        700",

    })
    void applyMinimumBoundary(double deliveryPrice, double expectedResult) {
        assertEquals(expectedResult, this.deliveryPrice.applyMinimumBoundary(deliveryPrice));
    }

    @Test
    void get() {

        int distance = 50;
        boolean isFragile = false;
        boolean isLarge = true;
        LoadType loadType = LoadType.HIGH;

        DeliveryPrice deliveryPriceSpy = spy(deliveryPrice);
        when(deliveryPriceSpy.calculateWithDistance(distance)).thenReturn(100);
        when(deliveryPriceSpy.calculateWithFragile(isFragile, distance)).thenReturn(200);
        when(deliveryPriceSpy.calculateWithSize(isLarge)).thenReturn(400);
        when(deliveryPriceSpy.calculateWithLoadType(loadType)).thenReturn(2d);
        when(deliveryPriceSpy.applyMinimumBoundary(1400)).thenReturn(1500d);
        double deliveryPrice = deliveryPriceSpy.get(distance, isLarge, isFragile, loadType);
        assertEquals(1500, deliveryPrice);
        verify(deliveryPriceSpy).calculateWithDistance(distance);
        verify(deliveryPriceSpy, times(1)).calculateWithSize(isLarge);
        verify(deliveryPriceSpy, times(1)).calculateWithFragile(isFragile, distance);
        verify(deliveryPriceSpy, times(1)).calculateWithLoadType(loadType);
        verify(deliveryPriceSpy, times(1)).applyMinimumBoundary(1400);
    }
}