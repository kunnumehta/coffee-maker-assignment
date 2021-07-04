import Controller.CoffeeMachineController;

import java.io.File;

public class Application {

    public static void main(String[] args) throws Exception{
        //System.out.println("Start of the Application");
        if(args.length < 1) {
            System.out.println("input file is required");
            System.exit(-1);
        }
        File file = new File(Application.class.getResource(args[0]).getFile());
        CoffeeMachineController coffeeMachineController = CoffeeMachineController.getInstance(file);
        coffeeMachineController.startProcess();
        coffeeMachineController.reset();
        //System.out.println("End of the application");
    }
}