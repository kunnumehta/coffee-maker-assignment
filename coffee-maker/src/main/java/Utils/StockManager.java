package Utils;

import Model.Beverage;

import java.util.HashMap;
import java.util.Map;

public class StockManager {

    private Map<String, Integer> itemQuantityMap;
    private static StockManager stockManager;
    private StockManager() {
        itemQuantityMap = new HashMap<>();
    }
    public static StockManager getInstance() {
        if (stockManager == null) {
            stockManager = new StockManager();
        }
        return stockManager;
    }
    public void addQuantityToMap(String item, Integer value) {
        if(itemQuantityMap.containsKey(item)) {
            itemQuantityMap.put(item, itemQuantityMap.get(item) + value);
        } else {
            itemQuantityMap.put(item, value);
        }
    }

    public Integer getCurrentQuantity(String item) {
        return itemQuantityMap.getOrDefault(item, 0);
    }

    public synchronized boolean checkAndUpdate(Beverage beverage) {
        Map<String, Integer> ingredientQuantityMap = beverage.getBeverageMap();
        boolean isPossible = true;

        for (String ingredient : ingredientQuantityMap.keySet()) {
            if (getCurrentQuantity(ingredient) == 0) {
                System.out.println(beverage.getComponent().getName() + " cannot be prepared because " + ingredient + " is not available");
                isPossible = false;
                break;
            } else if (ingredientQuantityMap.get(ingredient) > getCurrentQuantity(ingredient)) {
                System.out.println(beverage.getComponent().getName() + " cannot be prepared because " + ingredient + " is not sufficient");
                isPossible = false;
                break;
            }
        }
        if(isPossible) {
            for(String item : ingredientQuantityMap.keySet()) {
                itemQuantityMap.put(item, itemQuantityMap.get(item) - ingredientQuantityMap.get(item));
            }
        }
        return isPossible;
    }

    public void reset() {
        itemQuantityMap.clear();
    }
}
