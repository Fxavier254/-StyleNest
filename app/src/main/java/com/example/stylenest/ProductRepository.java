package com.example.stylenest;

import android.graphics.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {
    private static ProductRepository instance;
    private final List<ProductItem> allProducts;

    private ProductRepository() {
        allProducts = new ArrayList<>();
        initializeProducts();
    }

    public static synchronized ProductRepository getInstance() {
        if (instance == null) instance = new ProductRepository();
        return instance;
    }

    public List<ProductItem> getAllProducts() { return allProducts; }

    public List<ProductItem> getProductsByCategory(String category) {
        List<ProductItem> categoryProducts = new ArrayList<>();
        for (ProductItem item : allProducts) {
            if (item.category.equalsIgnoreCase(category)) categoryProducts.add(item);
        }
        return categoryProducts;
    }

    private void initializeProducts() {
        // Color Palettes
        int[] streetColors = {Color.BLACK, Color.GRAY, Color.parseColor("#1A237E"), Color.parseColor("#B71C1C")};
        String[] streetNames = {"Black", "Grey", "Navy", "Red"};
        int[] denimColors = {Color.parseColor("#1A237E"), Color.BLACK, Color.GRAY};
        String[] denimNames = {"Blue", "Black", "Grey"};
        int[] basicColors = {Color.BLACK, Color.WHITE, Color.GRAY};
        String[] basicNames = {"Black", "White", "Grey"};
        int[] formalColors = {Color.BLACK, Color.parseColor("#1A237E"), Color.parseColor("#3E2723")};
        String[] formalNames = {"Black", "Navy", "Brown"};
        int[] kidColors = {Color.parseColor("#FFEB3B"), Color.parseColor("#03A9F4"), Color.parseColor("#E91E63")};
        String[] kidNames = {"Yellow", "Blue", "Pink"};

        // --- MEN'S STORE (14 items) ---
        allProducts.add(new ProductItem("Classic Street Shirt", "KES 3,200", "MEN'S STORE", R.drawable.shirt5, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Urban Graphic Tee", "KES 1,800", "MEN'S STORE", R.drawable.men1, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Modern Polo Shirt", "KES 3,100", "MEN'S STORE", R.drawable.shirt6, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Smart Casual Blazer", "KES 8,500", "MEN'S STORE", R.drawable.men2, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Street Wear Jacket", "KES 6,800", "MEN'S STORE", R.drawable.men3, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Urban Denim Jacket", "KES 5,800", "MEN'S STORE", R.drawable.jacket1, true, denimColors, denimNames));
        allProducts.add(new ProductItem("Modern Puffer Jacket", "KES 7,500", "MEN'S STORE", R.drawable.jacket2, true, streetColors, streetNames));
        
        ProductItem menJeans = new ProductItem("Urban Slim Jeans", "KES 2,200", "MEN'S STORE", R.drawable.jeans, true, denimColors, denimNames);
        menJeans.colorImageMap.put("Blue", R.drawable.jeans);
        menJeans.colorImageMap.put("Black", R.drawable.jeans1);
        allProducts.add(menJeans);

        allProducts.add(new ProductItem("Modern Chino Pants", "KES 3,500", "MEN'S STORE", R.drawable.men4, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Slim Fit Cargo Pants", "KES 3,800", "MEN'S STORE", R.drawable.cargopant1, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Utility Street Joggers", "KES 3,600", "MEN'S STORE", R.drawable.cargopant2, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Urban Sweatpants", "KES 2,900", "MEN'S STORE", R.drawable.sweatpant, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Casual Cargo Shorts", "KES 2,400", "MEN'S STORE", R.drawable.short1, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Navy Business Suit", "KES 15,000", "MEN'S STORE", R.drawable.suit, true, formalColors, formalNames));

        // --- WOMEN'S STORE (17 items) ---
        allProducts.add(new ProductItem("Urban Crop Top", "KES 1,200", "WOMEN'S STORE", R.drawable.crop, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Casual Crop Hoodie", "KES 2,500", "WOMEN'S STORE", R.drawable.crop2, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Street Style Hoodie", "KES 3,500", "WOMEN'S STORE", R.drawable.woodie1, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Oversized Urban Hoodie", "KES 3,800", "WOMEN'S STORE", R.drawable.woodie2, true, streetColors, streetNames));
        
        ProductItem womenJeans = new ProductItem("High-Waist Skinny Jeans", "KES 2,900", "WOMEN'S STORE", R.drawable.jeans1, true, denimColors, denimNames);
        womenJeans.colorImageMap.put("Black", R.drawable.jeans1);
        womenJeans.colorImageMap.put("Blue", R.drawable.jeans);
        allProducts.add(womenJeans);

        allProducts.add(new ProductItem("Wide-Leg Denim", "KES 4,200", "WOMEN'S STORE", R.drawable.wideleg, true, denimColors, denimNames));
        allProducts.add(new ProductItem("Utility Cargo Pants", "KES 3,800", "WOMEN'S STORE", R.drawable.cargo, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Cozy Matching Set", "KES 4,500", "WOMEN'S STORE", R.drawable.set, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Bohemian Maxi Dress", "KES 5,500", "WOMEN'S STORE", R.drawable.dress1, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Casual Day Dress", "KES 3,800", "WOMEN'S STORE", R.drawable.dress2, true, basicColors, basicNames));
        allProducts.add(new ProductItem("A-Line Party Dress", "KES 6,200", "WOMEN'S STORE", R.drawable.dress3, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Formal Evening Dress", "KES 8,500", "WOMEN'S STORE", R.drawable.dress4, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Elegant Mini Dress", "KES 3,500", "WOMEN'S STORE", R.drawable.mini2, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Mini Street Dress", "KES 3,200", "WOMEN'S STORE", R.drawable.mini1, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Floral Summer Dress", "KES 4,800", "WOMEN'S STORE", R.drawable.dresstwo, true, new int[]{Color.WHITE, Color.parseColor("#F8BBD0")}, new String[]{"White", "Pink"}));
        allProducts.add(new ProductItem("Silk Evening Gown", "KES 10,500", "WOMEN'S STORE", R.drawable.dressone, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Casual Denim Shorts", "KES 1,800", "WOMEN'S STORE", R.drawable.short5, true, denimColors, denimNames));

        // --- SHOES (17 items) ---
        allProducts.add(new ProductItem("Retro Runner Sneaker", "KES 5,800", "SHOES", R.drawable.shoe1, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Formal Black Derbies", "KES 11,200", "SHOES", R.drawable.formalblackderbies, true, new int[]{Color.BLACK}, new String[]{"Black"}));
        allProducts.add(new ProductItem("Urban Lifestyle Sneaker", "KES 6,200", "SHOES", R.drawable.shoe3, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Sporty Comfort Trainers", "KES 5,500", "SHOES", R.drawable.shoe4, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Classic Canvas Loafers", "KES 3,800", "SHOES", R.drawable.shoe5, true, streetColors, streetNames));
        allProducts.add(new ProductItem("High-Top Street Sneakers", "KES 7,200", "SHOES", R.drawable.shoe7, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Generation Classic Sneaker", "KES 4,200", "SHOES", R.drawable.gen, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Urban Gen Kicks", "KES 3,900", "SHOES", R.drawable.gen1, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Modern Gen Trainers", "KES 5,400", "SHOES", R.drawable.gen2, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Leather Oxford Shoes", "KES 12,500", "SHOES", R.drawable.leatheroxfordshoes, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Chelsea Suede Boots", "KES 14,200", "SHOES", R.drawable.chelseaboots, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Classic Leather Boots", "KES 15,000", "SHOES", R.drawable.longboots, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Elegant Evening Heels", "KES 4,800", "SHOES", R.drawable.heels1, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Classic Pointed Heels", "KES 4,500", "SHOES", R.drawable.heel, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Strappy Summer Sandals", "KES 2,800", "SHOES", R.drawable.strappy, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Urban Flat Shoes", "KES 2,200", "SHOES", R.drawable.flat, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Tan Suede Loafers", "KES 9,800", "SHOES", R.drawable.tansuedeloafers, true, new int[]{Color.parseColor("#8D6E63")}, new String[]{"Tan"}));

        // --- ACCESSORIES (14 items) ---
        allProducts.add(new ProductItem("Modern Quartz Watch", "KES 8,500", "ACCESSORIES", R.drawable.watch1, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Luxury Rose Gold Watch", "KES 18,000", "ACCESSORIES", R.drawable.watch2, true, new int[]{Color.parseColor("#E91E63")}, new String[]{"Rose Gold"}));
        allProducts.add(new ProductItem("Diamond Heart Necklace", "KES 12,000", "ACCESSORIES", R.drawable.necklace, true, new int[]{Color.WHITE}, new String[]{"Silver"}));
        allProducts.add(new ProductItem("Elegant Charm Bracelet", "KES 4,200", "ACCESSORIES", R.drawable.bracelet, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Elegant Earrings", "KES 1,200", "ACCESSORIES", R.drawable.earring, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Urban Leather Pouch", "KES 2,500", "ACCESSORIES", R.drawable.pouch, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Minimalist Slim Wallet", "KES 3,200", "ACCESSORIES", R.drawable.wallet, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Silk Pattern Scarf", "KES 1,800", "ACCESSORIES", R.drawable.scarf, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Urban Silk Tie", "KES 1,500", "ACCESSORIES", R.drawable.tie, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Silk Pocket Square", "KES 1,200", "ACCESSORIES", R.drawable.pocketsquare, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Urban Aviator Sunglasses", "KES 2,400", "ACCESSORIES", R.drawable.sunglass, true, new int[]{Color.BLACK}, new String[]{"Black"}));
        allProducts.add(new ProductItem("Leather Laptop Bag", "KES 8,200", "ACCESSORIES", R.drawable.leatherlaptopbag, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Elegant Leather Handbag", "KES 9,500", "ACCESSORIES", R.drawable.handbag, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Signature Perfume", "KES 6,200", "ACCESSORIES", R.drawable.perfume, true, null, null));

        // --- KIDS STORE (6 unique items) ---
        allProducts.add(new ProductItem("Trendy Kids Hoodie", "KES 2,800", "KIDS STORE", R.drawable.trendykidswoodie, true, kidColors, kidNames));
        allProducts.add(new ProductItem("Urban Denim Overall", "KES 2,500", "KIDS STORE", R.drawable.urbandenimoverall, true, denimColors, denimNames));
        allProducts.add(new ProductItem("Casual Kids Tee", "KES 1,200", "KIDS STORE", R.drawable.casualkidstee, true, kidColors, kidNames));
        allProducts.add(new ProductItem("Stylish Summer Dress", "KES 3,200", "KIDS STORE", R.drawable.stylishsummerdress, true, kidColors, kidNames));
        allProducts.add(new ProductItem("Cozy Baby Jumpsuit", "KES 1,900", "KIDS STORE", R.drawable.cozybabyjumpsuit, true, kidColors, kidNames));
        allProducts.add(new ProductItem("Kids Fashion Sneakers", "KES 3,800", "KIDS STORE", R.drawable.kidsfashionsneakers, true, streetColors, streetNames));

        // --- HOME & KITCHEN (9 items) ---
        allProducts.add(new ProductItem("Digital Air Fryer Pro", "KES 12,500", "HOME & KITCHEN", R.drawable.airfryerpro, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Electric Glass Kettle", "KES 3,800", "HOME & KITCHEN", R.drawable.electrickettle, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Pressure Cooker 6L", "KES 8,900", "HOME & KITCHEN", R.drawable.pressurecooker, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Crystal Table Lamp", "KES 4,500", "HOME & KITCHEN", R.drawable.crystaltablelamp, true, null, null));
        allProducts.add(new ProductItem("Digital Microwave Oven", "KES 15,000", "HOME & KITCHEN", R.drawable.digitalmicrowave, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Double Door Fridge", "KES 85,000", "HOME & KITCHEN", R.drawable.doubledoorfridge, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Silver Cutlery Set", "KES 2,400", "HOME & KITCHEN", R.drawable.silverculteryset, true, null, null));
        allProducts.add(new ProductItem("Stainless Sufuria", "KES 1,800", "HOME & KITCHEN", R.drawable.stainlesssufuria, true, null, null));
        allProducts.add(new ProductItem("Professional Sufuria Set", "KES 7,500", "HOME & KITCHEN", R.drawable.stainlesssufuriaset, true, null, null));
    }

    public static class ProductItem {
        public String name, price, category;
        public Object imageSource;
        public List<Object> allImages = new ArrayList<>();
        public boolean inStock;
        public int[] colors;
        public String[] colorNames;
        public Map<String, Object> colorImageMap = new HashMap<>();
        public Map<String, Boolean> colorStockMap = new HashMap<>();

        public ProductItem(String name, String price, String category, Object imageSource, boolean inStock, int[] colors, String[] colorNames) {
            this.name = name; this.price = price; this.category = category;
            this.imageSource = imageSource; this.inStock = inStock;
            this.colors = colors; this.colorNames = colorNames;
            this.allImages.add(imageSource);
            if (colorNames != null) {
                for (String c : colorNames) {
                    colorImageMap.put(c, imageSource);
                    colorStockMap.put(c, inStock);
                }
            }
        }
    }
}
