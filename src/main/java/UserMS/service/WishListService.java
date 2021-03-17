package UserMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import UserMS.controller.userController;
import UserMS.dto.WishlistDTO;
import UserMS.entity.CompositeKey;
import UserMS.entity.Wishlist;
import UserMS.repository.WishlistRepository;


@Service
public class WishListService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	WishlistRepository wishlistrepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	userController controller;
	
	String producturi="http://localhost:8300/api/products";
	
	public  List<WishlistDTO> getAllwishlist()
	{
		logger.info("Fetching All Wishlists ");
		List<Wishlist> wishlist=wishlistrepository.findAll();
		List<WishlistDTO> wishlistDTO= new ArrayList<>();
		for(Wishlist i :wishlist)
		{
			WishlistDTO wishlistdto=WishlistDTO.valueOf(i);
			wishlistDTO.add(wishlistdto);
		}
		return wishlistDTO;
	}
	
	public WishlistDTO getSpecificWishlist(Integer buyerId, Integer prodId) {
		logger.info("Fetching Wishlist ");
		CompositeKey comkey= new CompositeKey();
		comkey.setBuyerId(buyerId);
		comkey.setProdId(prodId);
		WishlistDTO wishlistDTO = null;
		wishlistrepository.findAll();
		Optional<Wishlist> optWishList = wishlistrepository.findById(comkey);
		if(optWishList.isPresent()) {
			Wishlist wishlist = optWishList.get();
			wishlistDTO = WishlistDTO.valueOf(wishlist);
		}
		return wishlistDTO;
	}

	public void deleteSpecificWishlist(Integer buyerId, Integer prodId) {
		logger.info("Deleting Specific Wishlist ");
		CompositeKey comkey= new CompositeKey();
		comkey.setBuyerId(buyerId);
		comkey.setProdId(prodId);
		wishlistrepository.deleteById(comkey);
	}
	public void deleteWishlist() throws Exception{
		logger.info("Deleting Wishlist ");
		wishlistrepository.deleteAll();
	}
	   
	public void addWishlist(WishlistDTO wishlist) {
		logger.info("Adding into Wishlist ");
		Wishlist wish = new Wishlist();
		wish.setBuyerId(wishlist.getBuyerId());
		wish.setProdId(wishlist.getProdId());
		wishlistrepository.save(wish);
	}
	

}