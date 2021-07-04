package Utils;

import Model.Beverage;

public class BeverageServingManager implements Runnable{

    private final Beverage beverage;

    public BeverageServingManager(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public void run() {
        StockManager stockManager = StockManager.getInstance();
        String drink = beverage.getComponent().getName();
        if (stockManager.checkAndUpdate(beverage)) {
            System.out.println(drink + " is served");
        }
    }
}
