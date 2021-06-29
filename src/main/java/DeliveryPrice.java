public class DeliveryPrice {

    int calculateWithDistance(int distance) {
        if (distance <= 0) {
            throw new DeliveryPriceException("Distance can't be non-positive!");
        }
        if (distance < 2) {
            return 50;
        }
        if (distance < 10) {
            return 100;
        }
        if (distance < 30) {
            return 200;
        } else {
            return 300;
        }

    }

    int calculateWithSize(boolean isLarge) {
        return (isLarge) ? 200 : 100;
    }

    int calculateWithFragile(boolean isFragile, int distance) {
        if (distance > 30 && isFragile)
            throw new DeliveryPriceException("Distance can't be more than 30 for fragile delivery!");
        return (isFragile) ? 300 : 0;
    }

    double calculateWithLoadType(LoadType loadType) {

        switch (loadType) {
            case EXTREME:
                return 1.6;
            case VERY_HIGH:
                return 1.4;
            case HIGH:
                return 1.2;
            default:
                return 1;
        }
    }

    double applyMinimumBoundary(double deliveryPrice) {
        return (deliveryPrice < 400) ? 400 : deliveryPrice;
    }

    public double get(int distance, boolean isLarge, boolean isFragile, LoadType loadType) {
        return applyMinimumBoundary(
                (calculateWithDistance(distance)
                        + calculateWithSize(isLarge)
                        + calculateWithFragile(isFragile, distance))
                        * calculateWithLoadType(loadType));

    }
}
