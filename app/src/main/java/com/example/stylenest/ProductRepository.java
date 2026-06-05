package com.example.stylenest;

import android.graphics.Color;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static ProductRepository instance;
    private List<ProductItem> allProducts;

    private ProductRepository() {
        allProducts = new ArrayList<>();
        initializeProducts();
    }

    public static synchronized ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }

    public List<ProductItem> getProductsByCategory(String category) {
        List<ProductItem> categoryProducts = new ArrayList<>();
        for (ProductItem item : allProducts) {
            if (item.category.equalsIgnoreCase(category)) {
                categoryProducts.add(item);
            }
        }
        return categoryProducts;
    }

    public List<ProductItem> getAllProducts() {
        return allProducts;
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
        allProducts.add(new ProductItem("Classic Oxford Shirt", "KES 3,200", "MEN'S STORE", R.drawable.oxford, true, new int[]{Color.WHITE, Color.parseColor("#BBDEFB")}, new String[]{"White", "Blue"}));
        allProducts.add(new ProductItem("Urban Denim Jacket", "KES 5,800", "MEN'S STORE", R.drawable.jacket1, true, denimColors, denimNames));
        allProducts.add(new ProductItem("Navy Business Suit", "KES 15,000", "MEN'S STORE", R.drawable.suit, true, new int[]{Color.parseColor("#1A237E"), Color.BLACK}, new String[]{"Navy", "Black"}));
        allProducts.add(new ProductItem("Modern Puffer Jacket", "KES 7,500", "MEN'S STORE", R.drawable.jacket2, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Classic Street Shirt", "KES 2,800", "MEN'S STORE", R.drawable.shirt5, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Urban Polo Shirt", "KES 3,100", "MEN'S STORE", R.drawable.shirt6, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Casual Cargo Shorts", "KES 2,400", "MEN'S STORE", R.drawable.short1, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Slim Fit Cargo Pants", "KES 3,800", "MEN'S STORE", R.drawable.cargopant1, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Utility Street Joggers", "KES 3,600", "MEN'S STORE", R.drawable.cargopant2, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Black Leather Biker", "KES 12,000", "MEN'S STORE", R.drawable.jacket, true, new int[]{Color.BLACK}, new String[]{"Black"}));
        allProducts.add(new ProductItem("Urban Slim Jeans", "KES 2,200", "MEN'S STORE", R.drawable.jeans, true, denimColors, denimNames));
        allProducts.add(new ProductItem("Urban Tech Sweatpants", "KES 3,200", "MEN'S STORE", R.drawable.sweatpant, true, streetColors, streetNames));
        allProducts.add(new ProductItem("White Minimalist Tee", "KES 1,500", "MEN'S STORE", "https://images.unsplash.com/photo-1620799140408-edc6dcb6d633?w=500", true, basicColors, basicNames));
        allProducts.add(new ProductItem("Urban Street Shorts", "KES 2,100", "MEN'S STORE", R.drawable.short2, true, streetColors, streetNames));

        // --- WOMEN'S STORE (14 items - Updated names to match images) ---
        allProducts.add(new ProductItem("Urban Crop Top", "KES 1,200", "WOMEN'S STORE", R.drawable.crop, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Sporty Crop Hoodie", "KES 2,500", "WOMEN'S STORE", R.drawable.crop2, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Casual Mini Dress", "KES 3,200", "WOMEN'S STORE", R.drawable.mini1, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Evening Mini Dress", "KES 3,500", "WOMEN'S STORE", R.drawable.mini2, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Street Style Hoodie", "KES 3,500", "WOMEN'S STORE", R.drawable.woodie1, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Oversized Urban Hoodie", "KES 3,800", "WOMEN'S STORE", R.drawable.woodie2, true, streetColors, streetNames));
        allProducts.add(new ProductItem("High-Waist Skinny Jeans", "KES 2,900", "WOMEN'S STORE", R.drawable.jeans1, true, denimColors, denimNames));
        allProducts.add(new ProductItem("Casual Denim Shorts", "KES 1,800", "WOMEN'S STORE", R.drawable.short5, true, denimColors, denimNames));
        allProducts.add(new ProductItem("Silk Evening Gown", "KES 10,500", "WOMEN'S STORE", R.drawable.dressone, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Floral Summer Dress", "KES 4,800", "WOMEN'S STORE", R.drawable.dresstwo, true, new int[]{Color.WHITE, Color.parseColor("#F8BBD0")}, new String[]{"White", "Pink"}));
        allProducts.add(new ProductItem("Satin Wrap Dress", "KES 8,200", "WOMEN'S STORE", "https://images.unsplash.com/photo-1572804013309-59a88b7e92f1?w=500", true, basicColors, basicNames));
        allProducts.add(new ProductItem("Bohemian Maxi Skirt", "KES 5,500", "WOMEN'S STORE", "https://images.unsplash.com/photo-1594633312681-425c7b97ccd1?w=500", true, streetColors, streetNames));
        allProducts.add(new ProductItem("Linen Sun Dress", "KES 3,900", "WOMEN'S STORE", "https://images.unsplash.com/photo-1496747611176-843222e1e57c?w=500", true, basicColors, basicNames));
        allProducts.add(new ProductItem("Designer Blazer White", "KES 7,200", "WOMEN'S STORE", "https://images.unsplash.com/photo-1485968579580-b6d095142e6e?w=500", true, new int[]{Color.WHITE}, new String[]{"White"}));

        // --- SHOES (14 items - Updated names) ---
        allProducts.add(new ProductItem("Urban Flat Shoes", "KES 2,200", "SHOES", R.drawable.flat, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Classic Pointed Heels", "KES 4,500", "SHOES", R.drawable.heel, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Elegant Evening Heels", "KES 4,800", "SHOES", R.drawable.heels1, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Strappy Summer Sandals", "KES 2,800", "SHOES", R.drawable.strappy, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Retro Runner Sneaker", "KES 5,800", "SHOES", R.drawable.shoe1, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Leather Oxford Shoes", "KES 12,500", "SHOES", "https://images.unsplash.com/photo-1614252235316-8c857d38b5f4?w=500", true, formalColors, formalNames));
        allProducts.add(new ProductItem("Classic Leather Boots", "KES 15,000", "SHOES", "https://images.unsplash.com/photo-1560769629-975ec94e6a86?w=500", true, formalColors, formalNames));
        allProducts.add(new ProductItem("Formal Black Derbies", "KES 11,200", "SHOES", "https://images.unsplash.com/photo-1533867617858-e7b97e060509?w=500", true, new int[]{Color.BLACK}, new String[]{"Black"}));
        allProducts.add(new ProductItem("Tan Suede Loafers", "KES 9,800", "SHOES", "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=500", true, new int[]{Color.parseColor("#8D6E63")}, new String[]{"Tan"}));
        allProducts.add(new ProductItem("Urban High-Tops", "KES 7,500", "SHOES", "https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500", true, streetColors, streetNames));
        allProducts.add(new ProductItem("Suede Moccasins", "KES 5,500", "SHOES", "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=500", true, formalColors, formalNames));
        allProducts.add(new ProductItem("Mesh Athletic Trainers", "KES 6,400", "SHOES", "https://images.unsplash.com/photo-1491553895911-0055eca6402d?w=500", true, streetColors, streetNames));
        allProducts.add(new ProductItem("Double Monk Straps", "KES 13,500", "SHOES", "https://images.unsplash.com/photo-1582830359871-d915b3bc9645?w=500", true, formalColors, formalNames));
        allProducts.add(new ProductItem("Chelsea Suede Boots", "KES 14,200", "SHOES", "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500", true, formalColors, formalNames));

        // --- ACCESSORIES (14 items) ---
        allProducts.add(new ProductItem("Urban Leather Pouch", "KES 2,500", "ACCESSORIES", R.drawable.pouch, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Silk Pattern Scarf", "KES 1,800", "ACCESSORIES", R.drawable.scarf, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Minimalist Slim Wallet", "KES 3,200", "ACCESSORIES", R.drawable.wallet, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Modern Quartz Watch", "KES 8,500", "ACCESSORIES", R.drawable.watch2, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Luxury Rose Gold Watch", "KES 18,000", "ACCESSORIES", R.drawable.watch1, true, new int[]{Color.parseColor("#E91E63")}, new String[]{"Rose Gold"}));
        allProducts.add(new ProductItem("Diamond Heart Necklace", "KES 12,000", "ACCESSORIES", R.drawable.necklace, true, new int[]{Color.WHITE}, new String[]{"Silver"}));
        allProducts.add(new ProductItem("Leather Laptop Bag", "KES 8,200", "ACCESSORIES", R.drawable.leatherlaptopbag, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Elegant Leather Handbag", "KES 9,500", "ACCESSORIES", R.drawable.handbag, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Urban Aviator Sunglasses", "KES 2,400", "ACCESSORIES", R.drawable.sunglass, true, new int[]{Color.BLACK}, new String[]{"Black"}));
        allProducts.add(new ProductItem("Silk Pocket Square", "KES 1,200", "ACCESSORIES", R.drawable.pocketsquare, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Urban Silk Tie", "KES 1,500", "ACCESSORIES", R.drawable.tie, true, formalColors, formalNames));
        allProducts.add(new ProductItem("Elegant Earrings", "KES 1,200", "ACCESSORIES", R.drawable.earring, true, basicColors, basicNames));
        allProducts.add(new ProductItem("Wall Clock Designer", "KES 4,200", "ACCESSORIES", "https://images.unsplash.com/photo-1563861826100-9cb868fdbe1c?w=500", true, basicColors, basicNames));
        allProducts.add(new ProductItem("Bluetooth Sound Box", "KES 7,500", "ACCESSORIES", R.drawable.bluetoothspeaker, true, basicColors, basicNames));

        // --- KIDS STORE (14 items - Corrected names for pic1-8) ---
        allProducts.add(new ProductItem("Urban Kids Hoodie", "KES 2,800", "KIDS STORE", R.drawable.pic1, true, kidColors, kidNames));
        allProducts.add(new ProductItem("Denim Overall Set", "KES 2,500", "KIDS STORE", R.drawable.pic2, true, kidColors, kidNames));
        allProducts.add(new ProductItem("Toddler Cotton Tee", "KES 1,200", "KIDS STORE", R.drawable.pic3, true, kidColors, kidNames));
        allProducts.add(new ProductItem("Summer Party Gown", "KES 3,200", "KIDS STORE", R.drawable.pic4, true, kidColors, kidNames));
        allProducts.add(new ProductItem("Classic Boys Polo", "KES 1,900", "KIDS STORE", R.drawable.pic5, true, kidColors, kidNames));
        allProducts.add(new ProductItem("Kids Street Sneakers", "KES 3,800", "KIDS STORE", R.drawable.pic6, true, streetColors, streetNames));
        allProducts.add(new ProductItem("Cozy Pajama Set", "KES 1,800", "KIDS STORE", R.drawable.pic7, true, kidColors, kidNames));
        allProducts.add(new ProductItem("Striped Junior Tee", "KES 1,200", "KIDS STORE", R.drawable.pic8, true, kidColors, kidNames));
        allProducts.add(new ProductItem("Kids Cargo Shorts", "KES 2,400", "KIDS STORE", "https://images.unsplash.com/photo-1471286174890-9c112ffca5b4?w=501", true, kidColors, kidNames));
        allProducts.add(new ProductItem("Baby Wool Sweater", "KES 2,100", "KIDS STORE", "https://images.unsplash.com/photo-1533512930330-4ac257c86793?w=501", true, kidColors, kidNames));
        allProducts.add(new ProductItem("Infant Soft Booties", "KES 1,100", "KIDS STORE", "https://images.unsplash.com/photo-1514989940723-e8e51635b782?w=601", true, basicColors, basicNames));
        allProducts.add(new ProductItem("Junior School Bag", "KES 2,500", "KIDS STORE", "https://images.unsplash.com/photo-1621454523226-eb4f392ce979?w=601", true, streetColors, streetNames));
        allProducts.add(new ProductItem("Kids Parka Coat", "KES 4,800", "KIDS STORE", "https://images.unsplash.com/photo-1513273159393-47da7382218d?w=601", true, streetColors, streetNames));
        allProducts.add(new ProductItem("Baby Sun Hat", "KES 900", "KIDS STORE", "https://images.unsplash.com/photo-1519457431-44ccd64a579b?w=601", true, kidColors, kidNames));
    }

    public static class ProductItem {
        public String name, price, category;
        public Object imageSource;
        public boolean inStock;
        public int[] colors;
        public String[] colorNames;

        public ProductItem(String name, String price, String category, Object imageSource, boolean inStock, int[] colors, String[] colorNames) {
            this.name = name; this.price = price; this.category = category;
            this.imageSource = imageSource; this.inStock = inStock;
            this.colors = colors; this.colorNames = colorNames;
        }
    }
}
