package flowershop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class represents the Flower Shop. The class loads a configuration file to read the pricing for each flower bundle.
 * This will then be saved in a Map containing the list of bundles separated per flower.
 *
 * @author nmenego 2016/11/28
 */
public class Shop {

    /**
     * The configuration file where the prices are loaded. Please change accordingly.
     */
    public static final String CONFIG_FILE = "files/flowershop.config";
    /**
     * The map containing the bundles.
     */
    private Map<String, List<Bundle>> bundles = new HashMap<>();

    /**
     * Constructor.
     *
     * @throws IOException
     */
    public Shop() throws IOException {
        // load config file here.
        File file = new File(CONFIG_FILE);
        BufferedReader br = new BufferedReader(new FileReader(file));

        // read the file.
        String str;
        while ((str = br.readLine()) != null) {
            String[] split = str.split(",");
            List<Bundle> listBundle = bundles.get(split[0]);
            if (listBundle == null) {
                listBundle = new ArrayList<>();
            }
            listBundle.add(new Bundle(Integer.parseInt(split[1]), Double.parseDouble(split[2])));
            bundles.put(split[0], listBundle);
        }
        br.close();

    }

    /**
     * Issue an order to buy a flower with given flower code and quantity.
     * This class uses a dynamic programming similar to coin changing algorithm to retrieve the possible bundle combinations.
     * Bundles are the coin denominations.
     *
     * @param quantity
     * @param code
     */
    public void issueOrder(int quantity, String code) {

        List<Bundle> listBundle = getBundlesWithCode(code);
        // initialize array matrix contents
        // first array maintains the minimum number of bundles to get quantity
        // second array contains info about the denominations used to get the optimal solution
        int arr[][] = new int[2][quantity + 1];
        for (int i = 1; i <= quantity; i++) {
            // Integer.MAX_VALUE as infinity.
            arr[0][i] = Integer.MAX_VALUE - 1;
            arr[1][i] = -1;
        }
        // 0 bundles to form 0 flowers
        arr[0][0] = 0;

        // create the rest of the matrix..
        for (int j = 0; j < listBundle.size(); j++) {
            for (int i = 1; i <= quantity; i++) {
                int bundleSize = listBundle.get(j).getSize();
                if (i >= bundleSize) {
                    // min(infinity, 1 + T(i - bundle[j])
                    if (arr[0][i - bundleSize] + 1 < arr[0][i]) {
                        arr[0][i] = 1 + arr[0][i - bundleSize];
                        arr[1][i] = j;
                    }
                }
            }
        }

        // print the result
        printInvoice(arr[1], quantity, code);
    }

    // Retrieve the bundles for a specific flower code.
    private List<Bundle> getBundlesWithCode(String code) {
        return bundles.get(code);
    }

    // prints the results of the algorithm
    private void printInvoice(int[] denomArr, int quantity, String code) {

        List<Bundle> listBundle = getBundlesWithCode(code);
        // if a solution has been found...
        if (denomArr[denomArr.length - 1] != -1) {

            // Consolidate the bundles and compute total cost.
            double totalCost = 0;
            int x = denomArr.length - 1;
            Map<Bundle, Integer> inItem = new HashMap<>();
            while (x != 0) {
                int bundle = denomArr[x];
                totalCost += listBundle.get(bundle).getPrice();
                Integer bundleCount = inItem.get(listBundle.get(bundle));
                if (bundleCount == null) {
                    bundleCount = 1;
                } else {
                    bundleCount += 1;
                }
                inItem.put(listBundle.get(bundle), bundleCount);
                // deduct quantity with the size
                x = x - listBundle.get(bundle).getSize();
            }

            //print out total cost
            System.out.printf("%d %s $%.2f\n", quantity, code, totalCost);

            // print out details of the bundles bought
            Iterator it = inItem.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                Bundle bundle = (Bundle) pair.getKey();
                Integer count = (Integer) pair.getValue();

                System.out.printf("\t%d x $%.2f\n", bundle.getSize(), bundle.getPrice() * count);
            }
        } else {
            System.out.printf("%d %s : unable to find suitable bundle combination.\n", quantity, code);
        }
    }


}
