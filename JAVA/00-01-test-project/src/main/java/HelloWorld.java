public class HelloWorld {
    public static void main(String[] args) {
        switch (args.length) {
            case 0:
                System.out.println("No input parameters provided");
                break;
            
            default:
                for(String arg : args)
                    System.out.println(arg);
                break;
        }
    }
}