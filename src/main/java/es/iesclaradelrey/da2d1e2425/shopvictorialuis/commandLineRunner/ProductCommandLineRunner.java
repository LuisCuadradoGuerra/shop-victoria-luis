package es.iesclaradelrey.da2d1e2425.shopvictorialuis.commandLineRunner;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Category;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Product;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.CategoryService;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.services.ProductService;
import org.springframework.boot.CommandLineRunner;
import java.util.List;


//@Component
//@Order(2)
public class ProductCommandLineRunner implements CommandLineRunner {
    private  final ProductService productService;
    private final CategoryService categoryService;

    public ProductCommandLineRunner(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        Category potions = categoryService.save( new Category("Potions", "Step into our enchanted apothecary of mystical elixirs, where each potion is meticulously " +
                "brewed under the light of the full moon. Our master alchemists combine rare ingredients with ancient wisdom to create powerful concoctions that will transform your magical practice.", "/categories/category1.jpg"));
        Category ingredients = categoryService.save( new Category("Ingredients", "Discover our carefully curated collection of" +
                "                            rare and exotic ingredients, from dragon&#39;s blood to phoenix feathers. Each component is" +
                "                            ethically sourced from the most mystical corners of the world, ensuring the highest quality" +
                "                            for your magical crafting needs.", "/categories/category2.jpg"));
        Category brooms  = categoryService.save (new Category("Brooms", "Elevate your magical transportation with our" +
                "                            selection of enchanted brooms. Each masterfully crafted piece combines traditional" +
                "                            craftsmanship with powerful levitation enchantments, ensuring both style and supernatural" +
                "                            performance.", "/categories/category3.jpg"));
        Category tarot = categoryService.save(new Category("Tarot", "Explore our mystical collection of tarot decks," +
                "                            from traditional Rider-Waite to unique artisanal designs. Each deck is imbued with ancient" +
                "                            wisdom and powerful divination energy, perfect for both beginners and advanced readers.", "/categories/category4.jpg"));
         Category spellBooks= categoryService.save(new Category("Spell Books", "Uncover the secrets of the ages within our" +
                "                            collection of rare and powerful grimoires. From ancient scrolls to contemporary magical" +
                "                            manuscripts, each book holds centuries of arcane knowledge waiting to be discovered by" +
                "                            worthy practitioners.", "/categories/category5.jpg"));



        Product potionOne = new Product("OneLovelyNight", 60D,"Increments the possibilities but not totally charm your crush. Earn it helping your self, tiger", "/products/OneLovelyNight.jpg", List.of(potions));
        Product potionTwo = new Product("Multyjuicy Potion", 1000D,"USE ONLY WITH HUMAN HAIR. The last one who didn´t can´t warn you with its tentacles", "/products/Multyjuicy Potion.jpg", List.of(potions));
        Product potionTree = new Product("Krafkala", 2D,"Only krafkalas. Crab crap crappity crap", "/products/Krafkala.jpg", List.of(potions));

        Product ingredientsOne = new Product("Unicorn Horn", 700D,"The rarest horn in the nature. Only on your trusty bunion.", "/products/Unicorn Horn.jpg", List.of(ingredients));
        Product ingredientTwo = new Product("Dragon Scale", 30D,"Not that dangerous than one could imagine. It´s simple to get some if you are sneaky enough", "/products/Dragon Scale.png", List.of(ingredients));
        Product ingredientTree = new Product("Bone Dust", 10D,"Tovakin, it´s your chance to get some dust without dance with some draugs", "/products/Bone Dust.png", List.of(ingredients));

        Product broomOne = new Product("Broom Broom", 150D,"Our new broom perfect for begineers, mostly named 'BB', with moderated speed, highly savefty and we garantee you can hit some trees before break the broom", "/products/Broom Broom.jpg", List.of(brooms));
        Product broomTwo = new Product("Nimbus 2000", 676D,"The best wood ever to got a good broom. To good to go, baby", "/products/Nimbus 2000.png", List.of(brooms));
        Product broomTree = new Product("Fire Arrow", 1000D,"Maybe you need to open your Gringot´s security room to pay this, but is worth it", "/products/Fire Arrow.jpg", List.of(brooms));

        productService.save(broomOne);
        productService.save(broomTwo);
        productService.save(broomTree);
        productService.save(potionOne);
        productService.save(potionTwo);
        productService.save(potionTree);
        productService.save(ingredientsOne);
        productService.save(ingredientTwo);
        productService.save(ingredientTree);

        List<Product> testMap = productService
                .findAll()
                .stream()
                .filter(product -> product.getProductId()<5).
                toList();

        System.out.println(testMap);

    }
}
