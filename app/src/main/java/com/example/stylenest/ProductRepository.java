package com.example.stylenest;

import android.graphics.Color;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {
    private static ProductRepository instance;
    private final List<ProductItem> allProducts;
    private final FirebaseFirestore db;

    private ProductRepository() {
        allProducts = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        initializeProducts();
        loadProductsFromFirestore();
    }

    public static synchronized ProductRepository getInstance() {
        if (instance == null) instance = new ProductRepository();
        return instance;
    }

    public List<ProductItem> getAllProducts() { return allProducts; }

    public List<ProductItem> getProductsByCategory(String category) {
        List<ProductItem> categoryProducts = new ArrayList<>();
        for (ProductItem item : allProducts) {
            if (item.category != null && item.category.equalsIgnoreCase(category)) {
                categoryProducts.add(item);
            }
        }
        return categoryProducts;
    }

    private void loadProductsFromFirestore() {
        db.collection("products")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    if (value != null) {
                        for (com.google.firebase.firestore.DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == com.google.firebase.firestore.DocumentChange.Type.ADDED) {
                                ProductItem item = dc.getDocument().toObject(ProductItem.class);
                                item.id = dc.getDocument().getId();
                                boolean exists = false;
                                for (ProductItem p : allProducts) {
                                    if (item.id != null && item.id.equals(p.id)) {
                                        exists = true;
                                        break;
                                    }
                                }
                                if (!exists) allProducts.add(0, item);
                            }
                        }
                    }
                });
    }

    public void addProductToFirestore(ProductItem item, Runnable onSuccess, Runnable onFailure) {
        Map<String, Object> productMap = new HashMap<>();
        productMap.put("name", item.name);
        productMap.put("price", item.price);
        productMap.put("category", item.category);
        productMap.put("description", item.description);
        productMap.put("imageSource", item.imageSource);
        productMap.put("allImages", item.allImages);
        productMap.put("inStock", item.inStock);
        productMap.put("timestamp", FieldValue.serverTimestamp());

        db.collection("products").add(productMap)
                .addOnSuccessListener(ref -> { item.id = ref.getId(); onSuccess.run(); })
                .addOnFailureListener(e -> onFailure.run());
    }

    private void initializeProducts() {
        int[] colors = {Color.BLACK, Color.WHITE, Color.GRAY};
        String[] names = {"Black", "White", "Grey"};

        // --- MEN'S STORE ---
        allProducts.add(new ProductItem("Classic Street Shirt", "KES 3,200", "MEN'S STORE", R.drawable.shirt5, true, colors, names, "Premium cotton streetwear."));
        allProducts.add(new ProductItem("Modern Polo Shirt", "KES 3,100", "MEN'S STORE", R.drawable.shirt6, true, colors, names, "Breathable and stylish modern polo."));
        allProducts.add(new ProductItem("Urban Denim Jacket", "KES 5,800", "MEN'S STORE", R.drawable.jacket1, true, colors, names, "Rugged urban denim jacket."));
        allProducts.add(new ProductItem("Urban Slim Jeans", "KES 2,200", "MEN'S STORE", R.drawable.jeans, true, colors, names, "Tailored slim-fit streetwear jeans."));
        allProducts.add(new ProductItem("Classic Oxford Shirt", "KES 3,500", "MEN'S STORE", R.drawable.classicoxfordshirt, true, colors, names, "Formal classic button-down shirt."));
        allProducts.add(new ProductItem("Utility Cargo Pants", "KES 3,800", "MEN'S STORE", R.drawable.cargopant1, true, colors, names, "Heavy-duty outdoor cargo pants."));
        allProducts.add(new ProductItem("Tech Cargo Pants", "KES 4,000", "MEN'S STORE", R.drawable.cargopant2, true, colors, names, "Modern techwear inspired cargo pants."));
        allProducts.add(new ProductItem("Quilted Winter Jacket", "KES 7,200", "MEN'S STORE", R.drawable.jacket2, true, colors, names, "Warm quilted jacket for cold weather."));
        allProducts.add(new ProductItem("Summer Chino Shorts", "KES 1,900", "MEN'S STORE", R.drawable.short1, true, colors, names, "Comfortable lightweight chino shorts."));
        allProducts.add(new ProductItem("Athletic Mesh Shorts", "KES 1,500", "MEN'S STORE", R.drawable.short2, true, colors, names, "Breathable shorts for sports and gym."));
        allProducts.add(new ProductItem("Classic Denim Shorts", "KES 2,400", "MEN'S STORE", R.drawable.short3, true, colors, names, "Rugged denim shorts for casual style."));

        // --- WOMEN'S STORE ---
        allProducts.add(new ProductItem("Floral Print Jeans", "KES 4,500", "WOMEN'S STORE", R.drawable.girl1, true, colors, names, "Unique floral print denim jeans."));
        allProducts.add(new ProductItem("Pink Graphic Hoodie", "KES 2,800", "WOMEN'S STORE", R.drawable.girl2, true, colors, names, "Cozy and bright graphic hoodie."));
        allProducts.add(new ProductItem("Denim Mini Skirt", "KES 3,200", "WOMEN'S STORE", R.drawable.girl3, true, colors, names, "Stylish urban denim mini skirt."));
        allProducts.add(new ProductItem("Urban Cargo Pants", "KES 4,200", "WOMEN'S STORE", R.drawable.girl4, true, colors, names, "Comfortable and trendy cargo pants."));
        allProducts.add(new ProductItem("Black Pleated Skirt", "KES 4,800", "WOMEN'S STORE", R.drawable.girl5, true, colors, names, "Versatile urban pleated mini skirt."));
        allProducts.add(new ProductItem("Casual Summer Dress", "KES 5,000", "WOMEN'S STORE", R.drawable.girl6, true, colors, names, "Light and breezy summer dress."));
        allProducts.add(new ProductItem("White Ruched Top", "KES 5,500", "WOMEN'S STORE", R.drawable.girl7, true, colors, names, "Elegant evening summer top."));
        allProducts.add(new ProductItem("Boho Maxi Dress", "KES 6,200", "WOMEN'S STORE", R.drawable.girl8, true, colors, names, "Beautiful flowy bohemian maxi dress."));
        allProducts.add(new ProductItem("Satin Blouse", "KES 3,800", "WOMEN'S STORE", R.drawable.girl9, true, colors, names, "Smooth and elegant satin blouse."));
        allProducts.add(new ProductItem("Oversized Blazer", "KES 7,500", "WOMEN'S STORE", R.drawable.girl10, true, colors, names, "Modern oversized professional blazer."));
        allProducts.add(new ProductItem("Crop Top Hoodie", "KES 2,500", "WOMEN'S STORE", R.drawable.girl11, true, colors, names, "Trendy streetwear crop top hoodie."));
        allProducts.add(new ProductItem("High-Waist Shorts", "KES 2,200", "WOMEN'S STORE", R.drawable.girl12, true, colors, names, "Classic high-waisted denim shorts."));
        allProducts.add(new ProductItem("Leather Biker Jacket", "KES 8,500", "WOMEN'S STORE", R.drawable.girl13, true, colors, names, "Premium leather biker jacket."));
        allProducts.add(new ProductItem("Knitted Sweater", "KES 3,500", "WOMEN'S STORE", R.drawable.girl14, true, colors, names, "Warm and cozy knitted winter sweater."));
        allProducts.add(new ProductItem("A-Line Party Dress", "KES 6,800", "WOMEN'S STORE", R.drawable.girl15, true, colors, names, "Elegant A-line dress for parties."));

        // --- KIDS STORE ---
        allProducts.add(new ProductItem("Kids Toddler Overalls", "KES 2,500", "KIDS STORE", R.drawable.kid1, true, colors, names, "Durable playwear for active kids."));
        allProducts.add(new ProductItem("Kids Graphic Tee", "KES 1,200", "KIDS STORE", R.drawable.kid2, true, colors, names, "Fun and comfortable kid's essential."));
        allProducts.add(new ProductItem("Summer Floral Dress", "KES 2,800", "KIDS STORE", R.drawable.kid3, true, colors, names, "Beautiful floral dress for kids."));
        allProducts.add(new ProductItem("Kids Striped Cotton Tee", "KES 1,500", "KIDS STORE", R.drawable.kid4, true, colors, names, "Classic striped t-shirt for daily wear."));
        allProducts.add(new ProductItem("Kids Denim Jacket", "KES 3,200", "KIDS STORE", R.drawable.kid5, true, colors, names, "Sturdy and stylish denim jacket."));
        allProducts.add(new ProductItem("Kids Dino Hoodie", "KES 2,400", "KIDS STORE", R.drawable.kid6, true, colors, names, "Fun dinosaur themed hooded sweatshirt."));
        allProducts.add(new ProductItem("Kids Floral Summer Set", "KES 2,800", "KIDS STORE", R.drawable.kid7, true, colors, names, "Cute two-piece floral summer outfit."));
        allProducts.add(new ProductItem("Kids Sport Shorts", "KES 1,800", "KIDS STORE", R.drawable.kid8, true, colors, names, "Breathable shorts for active play."));
        allProducts.add(new ProductItem("Kids Tracksuit Set", "KES 3,200", "KIDS STORE", R.drawable.kid9, true, colors, names, "Comfortable green tracksuit set for active play."));
        allProducts.add(new ProductItem("Kids Bunny Vest", "KES 2,400", "KIDS STORE", R.drawable.kid10, true, colors, names, "Adorable pink bunny-themed hooded vest."));
        allProducts.add(new ProductItem("Kids Suspenders Outfit", "KES 3,800", "KIDS STORE", R.drawable.kid11, true, colors, names, "Smart formal shirt with matching suspenders."));
        allProducts.add(new ProductItem("Kids Cloud Pajamas", "KES 2,200", "KIDS STORE", R.drawable.kid12, true, colors, names, "Soft purple sleepwear set with cloud patterns."));
        allProducts.add(new ProductItem("Kids Sport Sneakers", "KES 3,500", "KIDS STORE", R.drawable.kid13, true, colors, names, "Durable black sneakers for daily adventures."));
        allProducts.add(new ProductItem("Kids Sparkle Sneakers", "KES 3,600", "KIDS STORE", R.drawable.kid14, true, colors, names, "Trendy pink sneakers with a touch of sparkle."));
        allProducts.add(new ProductItem("Kids Bow Shoes", "KES 2,800", "KIDS STORE", R.drawable.kid15, true, colors, names, "Elegant pink shoes with a decorative bow."));
        allProducts.add(new ProductItem("Kids Velcro Sneakers", "KES 3,400", "KIDS STORE", R.drawable.kid16, true, colors, names, "Easy-to-wear white velcro sneakers."));

        // --- SHOES ---
        allProducts.add(new ProductItem("Midnight Strappy Heels", "KES 6,200", "SHOES", R.drawable.shoe1, true, colors, names, "Elegant evening black strappy high heels."));
        allProducts.add(new ProductItem("Beige High Heels", "KES 6,500", "SHOES", R.drawable.shoe2, true, colors, names, "Sophisticated beige high heels for formal occasions."));
        allProducts.add(new ProductItem("Classic Beige Loafers", "KES 7,800", "SHOES", R.drawable.shoe3, true, colors, names, "Comfortable and stylish beige leather loafers."));
        allProducts.add(new ProductItem("Tan Leather Chelsea Boots", "KES 11,500", "SHOES", R.drawable.shoe4, true, colors, names, "Durable tan leather chelsea boots for outdoor adventures."));
        allProducts.add(new ProductItem("Brown Chelsea Boots", "KES 10,800", "SHOES", R.drawable.shoe5, true, colors, names, "Classic brown Chelsea boots with elastic side panels."));
        allProducts.add(new ProductItem("Pink Winter Boots", "KES 5,500", "SHOES", R.drawable.shoe6, true, colors, names, "Cozy and stylish pink winter boots for chilly days."));
        allProducts.add(new ProductItem("Formal Leather Oxford Shoes", "KES 12,500", "SHOES", R.drawable.shoe7, true, colors, names, "Premium leather Oxford shoes for formal events."));
        allProducts.add(new ProductItem("Suede Desert Boots", "KES 9,200", "SHOES", R.drawable.shoe8, true, colors, names, "Classic suede boots with a comfortable sole."));
        allProducts.add(new ProductItem("Classic White Sneakers", "KES 11,000", "SHOES", R.drawable.shoe9, true, colors, names, "Premium urban white sneakers for everyday style."));
        allProducts.add(new ProductItem("Chunky Beige Sneakers", "KES 8,500", "SHOES", R.drawable.shoe10, true, colors, names, "Modern retro-inspired chunky beige sneakers."));
        allProducts.add(new ProductItem("Black High-Top Sneakers", "KES 7,800", "SHOES", R.drawable.shoe11, true, colors, names, "Urban black high-top sneakers with a sleek profile."));
        allProducts.add(new ProductItem("Elegant Purple Heels", "KES 8,200", "SHOES", R.drawable.shoe12, true, colors, names, "Beautiful purple high heels for special events."));
        allProducts.add(new ProductItem("Purple Futuristic Sneakers", "KES 12,800", "SHOES", R.drawable.shoe13, true, colors, names, "Futuristic purple sneakers with a techwear aesthetic."));
        allProducts.add(new ProductItem("Black Futuristic Sneakers", "KES 11,800", "SHOES", R.drawable.shoe14, true, colors, names, "Sleek black techwear sneakers for a stealthy urban look."));
        allProducts.add(new ProductItem("B&W Basketball Sneakers", "KES 14,500", "SHOES", R.drawable.shoe15, true, colors, names, "High-performance black-and-white basketball sneakers."));
        allProducts.add(new ProductItem("Urban Green Sneakers", "KES 5,800", "SHOES", R.drawable.shoe16, true, colors, names, "Casual and comfortable green sneakers for daily style."));
        allProducts.add(new ProductItem("Neon Performance Runners", "KES 6,800", "SHOES", R.drawable.shoe20, true, colors, names, "High-performance sneakers with vibrant accents."));
        allProducts.add(new ProductItem("Urban Daily Sneakers", "KES 4,500", "SHOES", R.drawable.shoe21, true, colors, names, "Versatile sneakers for everyday urban wear."));
        allProducts.add(new ProductItem("Premium Leather Oxfords", "KES 11,200", "SHOES", R.drawable.shoe22, true, colors, names, "Handcrafted leather shoes for formal excellence."));
        allProducts.add(new ProductItem("Suede Streetwear Boots", "KES 9,800", "SHOES", R.drawable.shoe23, true, colors, names, "Stylish suede boots with a modern streetwear twist."));
        allProducts.add(new ProductItem("Lightweight Mesh Trainers", "KES 5,400", "SHOES", R.drawable.shoe24, true, colors, names, "Breathable and light trainers for active lifestyle."));
        allProducts.add(new ProductItem("Classic Party Stilettos", "KES 7,500", "SHOES", R.drawable.shoe25, true, colors, names, "Elegant high-heeled stilettos for evening parties."));

        // --- ACCESSORIES ---
        allProducts.add(new ProductItem("Retro Sunglasses", "KES 4,500", "ACCESSORIES", R.drawable.es1, true, colors, names, "Classic street shades."));
        allProducts.add(new ProductItem("Slim Bifold Wallet", "KES 2,200", "ACCESSORIES", R.drawable.es2, true, colors, names, "Minimalist slim leather wallet."));
        allProducts.add(new ProductItem("Silver Executive Watch", "KES 1,800", "ACCESSORIES", R.drawable.es3, true, colors, names, "Premium executive wristwatch."));
        allProducts.add(new ProductItem("Silk Pocket Square", "KES 1,200", "ACCESSORIES", R.drawable.es4, true, colors, names, "Elegant silk pocket square for suits."));
        allProducts.add(new ProductItem("Leather Crossbody Bag", "KES 5,800", "ACCESSORIES", R.drawable.es5, true, colors, names, "Stylish leather crossbody bag for daily use."));
        allProducts.add(new ProductItem("Gold Executive Watch", "KES 8,500", "ACCESSORIES", R.drawable.es6, true, colors, names, "Premium gold-toned chronograph watch."));
        allProducts.add(new ProductItem("Minimalist Card Holder", "KES 1,500", "ACCESSORIES", R.drawable.es7, true, colors, names, "Slim leather wallet for essential cards."));
        allProducts.add(new ProductItem("Luxury Silk Scarf", "KES 2,800", "ACCESSORIES", R.drawable.es8, true, colors, names, "Soft premium silk scarf with elegant patterns."));
        allProducts.add(new ProductItem("Gold Pendant Necklace", "KES 3,200", "ACCESSORIES", R.drawable.es9, true, colors, names, "Beautiful gold-plated pendant necklace."));
        allProducts.add(new ProductItem("Silk Business Tie", "KES 2,200", "ACCESSORIES", R.drawable.es10, true, colors, names, "Professional silk necktie for business wear."));
        allProducts.add(new ProductItem("Gold Hoop Earrings", "KES 1,800", "ACCESSORIES", R.drawable.es11, true, colors, names, "Classic gold-plated hoop earrings."));
        allProducts.add(new ProductItem("Designer Perfume", "KES 9,500", "ACCESSORIES", R.drawable.es12, true, colors, names, "Signature fragrance in a luxury bottle."));
        allProducts.add(new ProductItem("Urban Baseball Cap", "KES 1,800", "ACCESSORIES", R.drawable.es13, true, colors, names, "Stylish urban cap for casual outings."));
        allProducts.add(new ProductItem("Premium Cotton Socks", "KES 900", "ACCESSORIES", R.drawable.es14, true, colors, names, "Comfortable and breathable cotton crew socks."));
        allProducts.add(new ProductItem("Leather Designer Keychain", "KES 1,200", "ACCESSORIES", R.drawable.es15, true, colors, names, "Premium leather keychain with metal hardware."));
        allProducts.add(new ProductItem("Elegant Charm Bracelet", "KES 2,500", "ACCESSORIES", R.drawable.bracelet, true, colors, names, "Beautiful gold-plated charm bracelet."));
    }

    public static class ProductItem {
        public String id, name, price, category, description;
        public Object imageSource;
        public List<Object> allImages = new ArrayList<>();
        public boolean inStock;
        public int[] colors;
        public String[] colorNames;
        public Map<String, Object> colorImageMap = new HashMap<>();
        public Map<String, Boolean> colorStockMap = new HashMap<>();

        public ProductItem() {}

        public ProductItem(String name, String price, String category, Object imageSource, boolean inStock, int[] colors, String[] colorNames, String description) {
            this.name = name; this.price = price; this.category = category;
            this.imageSource = imageSource; this.inStock = inStock;
            this.colors = colors; this.colorNames = colorNames;
            this.description = description;
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
