package flowershop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FlowerShop entry point.
 *
 * @author nmenego 2016/11/28
 */
public class ShopRunner {
    private static final String DEFAULT_FILE = "files/flowershop.data";

    public static void main(String[] args) throws IOException {
        // reading from file input as argument
        String fileName;
        if (args != null && args.length > 0) {
            fileName = args[0];
        } else {
            fileName = DEFAULT_FILE;
        }

        // read from file
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> inputs = new ArrayList<>();

        String str;
        while ((str = br.readLine()) != null) {
            // check inputs..
            if (str.matches("^\\d+\\s+[A-Z]\\d{2}$")) {
                inputs.add(str);
            } else {
                br.close();
                throw new IOException("Invalid input found in file.");
            }
        }
        br.close();

        //initialize Shop
        Shop fs = new Shop();
        // process the inputs...
        for (String input : inputs) {
            String[] split = input.split("\\s+");
            int quantity = Integer.parseInt(split[0]);
            String code = split[1];
            fs.issueOrder(quantity, code);
        }

    }
}
