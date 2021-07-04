package Controller;

import Model.Beverage;
import Model.Component;
import Model.MachineObject;
import Utils.BeverageServingManager;
import Utils.StockManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CoffeeMachineController {

    private static CoffeeMachineController coffeeMachineController;
    private MachineObject machineObject;
    private StockManager stockManager;
    private ThreadPoolExecutor executor;
    public static CoffeeMachineController getInstance(File inputFile) throws IOException{
        if(coffeeMachineController == null) {
            coffeeMachineController = new CoffeeMachineController(inputFile);
        }
        return coffeeMachineController;
    }

    private CoffeeMachineController(File inputFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        machineObject = objectMapper.readValue(inputFile, MachineObject.class);
        stockManager = StockManager.getInstance();
        int outlets = machineObject.getMachine().getOutlets().getCount();
        executor = new ThreadPoolExecutor(outlets, outlets, 5000,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(50));
    }

    public void startProcess() {
        Map<String, Integer> ingredientsMap = machineObject.getMachine().getIngredientsQuantityMap();
        for(String key : ingredientsMap.keySet()) {
            stockManager.addQuantityToMap(key, ingredientsMap.get(key));
        }
        Map<String, Map<String, Integer>> beveragesMap = machineObject.getMachine().getBeveragesMap();
        for(String key : beveragesMap.keySet()) {
            Component component = new Component(key);
            Beverage beverage = new Beverage(component, beveragesMap.get(key));
            this.fetchBeverage(beverage);
        }
    }

    public void fetchBeverage(Beverage beverage) {
        BeverageServingManager beverageServingManager = new BeverageServingManager(beverage);
        executor.execute(beverageServingManager);
    }

    public void stopProcess() {
        executor.shutdown();
    }

    public void reset(){
        System.out.println("Resetting the machine");
        this.stopProcess();
        stockManager.reset();
    }
}
