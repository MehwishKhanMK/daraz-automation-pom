package mytestproject;

public class DarazTest {
    public static void main(String[] args) throws InterruptedException {
        DarazHomePage homePage = new DarazHomePage();

        homePage.openDaraz();
        homePage.searchElectronics();
        homePage.applyBrandFilter();
        homePage.applyPriceFilter();

        int productCount = homePage.countProducts();
        if (productCount > 0) {
            System.out.println("✅ Products found: " + productCount);
        } else {
            System.out.println("❌ No products found!");
        }

        homePage.openFirstProduct();
        boolean isFreeShipping = homePage.isFreeShippingAvailable();
        if (isFreeShipping) {
            System.out.println("🚚 Free Shipping is available!");
        } else {
            System.out.println("❌ Free Shipping not available!");
        }

        homePage.closeBrowser();
    }
}
