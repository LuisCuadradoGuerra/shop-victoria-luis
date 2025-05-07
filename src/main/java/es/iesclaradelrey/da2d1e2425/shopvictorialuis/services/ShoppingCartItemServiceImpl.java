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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ShoppingCartItemServiceImpl(ShoppingCartItemRepository shoppingCartItemRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public long count() {
        return shoppingCartItemRepository.count();
    }

    @Override
    public void save(ShoppingCartItem trolley) {
        shoppingCartItemRepository.save(trolley);
    }

    @Override
    public List<ShoppingCartItem> findAll() {
        return shoppingCartItemRepository.findAll();
    }

    @Override
    public List<ShoppingCartItem> findByAppUserId(Long appUserId) {
        return shoppingCartItemRepository.findAllByAppUserAppUserId(appUserId);
    }

    @Override
    public int addToTrolley(Long productId, Long quantity) {
//      Check if that product exists
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with id %d not found", productId), "The id of the product is not in our database"));

//      Check if that product is already in the shopping cart and add the quantity
        ShoppingCartItem shoppingCartItem = shoppingCartItemRepository
                .findByProductId(productId)
                .orElse(new ShoppingCartItem(0L, product));
        shoppingCartItem.setItemsCount(shoppingCartItem.getItemsCount() + quantity);

//      Check stock available
        if (product.getProductStock() < shoppingCartItem.getItemsCount() || product.getProductStock() == 0) {
            throw new OutOfStockException(String.format("Out of stock for product with id %d", productId), "The product is out of stock");
        }

//      Save changes
        shoppingCartItemRepository.save(shoppingCartItem);
        return shoppingCartItemRepository.findAll().stream().mapToInt(item -> item.getItemsCount().intValue()).sum();

    }

    @Override
    public void removeFromTrolley(Long productId) {
        ShoppingCartItem item = shoppingCartItemRepository.findByProductId(productId).orElse(null);
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
        shoppingCartItemRepository.findByProductId(productId).ifPresent(shoppingCartItemRepository::delete);
    }

    @Override
    public void clearTrolley() {
        shoppingCartItemRepository.deleteAll();
    }

    @Override
    public AppShoppingCartDto getShoppingCartToApp() {
//        todo Cuando ponga usuarios, no hacer un findAll
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemRepository.findAll();
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
