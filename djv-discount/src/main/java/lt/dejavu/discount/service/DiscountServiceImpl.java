package lt.dejavu.discount.service;

import lt.dejavu.discount.model.dto.DiscountDto;
import lt.dejavu.discount.model.dto.ProductDiscountDto;
import lt.dejavu.discount.model.mapper.DiscountMapper;
import lt.dejavu.discount.repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;

    public DiscountServiceImpl(DiscountRepository discountRepository, DiscountMapper discountMapper) {
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
    }

    @Override
    public List<DiscountDto> getAllDiscounts() {
        return discountMapper.mapToDto(discountRepository.getAllDiscounts());
    }

    @Override
    public DiscountDto getDiscountById(long id) {
        return discountMapper.mapToDto(discountRepository.getDiscount(id));
    }

    @Override
    public void addDiscount(DiscountDto discount) {
        discountRepository.addDiscount(discountMapper.mapToDiscount(discount));
    }

    @Override
    public void updateDiscount(long id, DiscountDto newDiscount) {
        discountRepository.updateDiscount(id, discountMapper.mapToDiscount(newDiscount));
    }

    @Override
    public void deleteDiscount(long id) {
        discountRepository.deleteDiscount(id);
    }

    @Override
    public ProductDiscountDto getProductDiscount(long productId) {
        return null;
    }
}
