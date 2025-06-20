package es.iesclaradelrey.da2d1e2425.shopvictorialuis.services;

import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.AppShoppingCartDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.dto.app.AppShoppingCartItemDto;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.Product;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.entities.ShoppingCartItem;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions.OutOfStockException;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.exceptions.ProductNotFoundException;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic.ProductRepository;
import es.iesclaradelrey.da2d1e2425.shopvictorialuis.repositories.generic.ShoppingCartItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final AppUserService appUserService;

    public ShoppingCartItemServiceImpl(ShoppingCartItemRepository shoppingCartItemRepository, ProductRepository productRepository, ModelMapper modelMapper, AppUserService appUserService) {
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.appUserService = appUserService;
    }

    @Override
    public long count() {
        return shoppingCartItemRepository.count();
    }

    @Override
    public void save(ShoppingCartItem trolley) {
        shoppingCartItemRepository.save(trolley);
    }
//
//    @Override
//    public List<ShoppingCartItem> findAll() {
//        return shoppingCartItemRepository.findAll();
//    }

    @Override
    public List<ShoppingCartItem> findByAppUserId() {
        return shoppingCartItemRepository.findAllByAppUserAppUserId(appUserService.getCurrentAppUserId());
    }

    @Override
    public int addToTrolley(Long productId, Long quantity) {
//      Check if that product exists
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with id %d not found", productId), "The id of the product is not in our database"));

//      Check if that product is already in the shopping cart and add the quantity
        ShoppingCartItem shoppingCartItem = shoppingCartItemRepository
                .findByProductId(productId,appUserService.getCurrentAppUserId())
                .orElse(new ShoppingCartItem(0L, product, appUserService.getCurrentAppUser()));
        shoppingCartItem.setItemsCount(shoppingCartItem.getItemsCount() + quantity);

//      Check stock available
        if (product.getProductStock() < shoppingCartItem.getItemsCount() || product.getProductStock() == 0) {
            throw new OutOfStockException(String.format("Out of stock for product with id %d", productId), "The product is out of stock");
        }

//      Save changes
        shoppingCartItemRepository.save(shoppingCartItem);
        return shoppingCartItemRepository.findAllByAppUserAppUserId(appUserService.getCurrentAppUserId()).stream().mapToInt(item -> item.getItemsCount().intValue()).sum();

    }

    @Override
    public void removeFromTrolley(Long productId) {
        ShoppingCartItem item = shoppingCartItemRepository.findByProductId(productId,appUserService.getCurrentAppUserId()).orElse(null);
        if (item != null) {
            item.setItemsCount(item.getItemsCount() - 1);
            shoppingCartItemRepository.save(item);
            if (item.getItemsCount() == 0) {
                shoppingCartItemRepository.delete(item);
            }
        }
    }

    @Override
    public void deleteFromTrolley(Long productId) {
        shoppingCartItemRepository.findByProductId(productId,appUserService.getCurrentAppUserId()).ifPresent(shoppingCartItemRepository::delete);
    }

//    Fallo por no ser transactional segun gpt...
    @Override
    @Transactional
    public void clearTrolley() {
        shoppingCartItemRepository.deleteAllByAppUser_AppUserId(appUserService.getCurrentAppUserId());
    }

    @Override
    public AppShoppingCartDto getShoppingCartToApp() {
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemRepository.findAllByAppUserAppUserId(appUserService.getCurrentAppUserId());
        return new AppShoppingCartDto(
                shoppingCartItems
                        .stream()
                        .mapToLong(ShoppingCartItem::getItemsCount).sum(),
                shoppingCartItems
                        .stream()
                        .mapToDouble(
                                item -> item.getItemsCount() * item.getProduct().getPrice()
                        ).sum(),
                shoppingCartItems
                        .stream()
                        .map(
                                item -> modelMapper.map(item, AppShoppingCartItemDto.class)
                        )
                        .collect(Collectors.toList())
        );
    }
}
