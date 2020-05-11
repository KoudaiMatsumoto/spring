package jp.co.internous.ecsite.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.ecsite.model.dao.GoodsRepository;
import jp.co.internous.ecsite.model.dao.PurchaseRepository;
import jp.co.internous.ecsite.model.dao.UserRepository;
import jp.co.internous.ecsite.model.dto.HistoryDto;
import jp.co.internous.ecsite.model.dto.LoginDto;
import jp.co.internous.ecsite.model.entity.Goods;
import jp.co.internous.ecsite.model.entity.Purchase;
import jp.co.internous.ecsite.model.entity.User;
import jp.co.internous.ecsite.model.form.CartForm;
import jp.co.internous.ecsite.model.form.HistoryForm;
import jp.co.internous.ecsite.model.form.LoginForm;

@Controller
@RequestMapping("/ecsite")
public class IndexController {
	
//UserエンティティからuserテーブルにアクセスするDAO
	@Autowired
	private UserRepository userRepos;
	
//GoodsエンティティからgoodsテーブルにアクセスするDAO
	@Autowired
	private GoodsRepository goodsRepos;
	
//PurchaseエンティティからpurchaseテーブルにアクセスするDAO
	@Autowired
	private PurchaseRepository purchaseRepos;
	
//WebサービスAPIとして作成するためJSON形式を扱えるようGsonをインスタンス化しておく
	private Gson gson = new Gson();
	
//goodsテーブルから取得した商品エンティティの一覧を、フロントに渡すModelに追加している。
	@RequestMapping("/")
	public String index(Model m) {
		List<Goods> goods =goodsRepos.findAll();
		m.addAttribute("goods", goods);
		
		return "index";
	}
	
	@ResponseBody
	@PostMapping("/api/login")
	public String loginApi(@RequestBody LoginForm form) {
		//DBテーブル（user）から、ユーザ名とパスワードで検索し、結果を取得
		List<User> users = userRepos.findByUserNameAndPassword(form.getUserName(), form.getPassword());
		
		//その後、DTOをゲストの情報で初期化し、もし検索結果が存在していた場合のみ、実在のユーザ情報をDTOに詰める
		LoginDto dto = new LoginDto(0, null, null, "ゲスト");
		if(users.size() > 0) {
			dto = new LoginDto(users.get(0));
		}
		
		//最終的に、DTOをJSONオブジェクトとして画面側に返している
		return gson.toJson(dto);
	}
	
	@ResponseBody
	@PostMapping("/api/purchase")
	public String purchaseApi(@RequestBody CartForm f) {
		
		f.getCartList().forEach((c) -> {
			long total = c.getPrice() * c.getCount();
			purchaseRepos.persist(f.getUserId(), c.getId(), c.getGoodsName(), c.getCount(), total);
		});
		
		return String.valueOf(f.getCartList().size());
	}
	
	@ResponseBody
	@PostMapping("/api/history")
	public String historyApi(@RequestBody HistoryForm form) {
		String userId = form.getUserId();
		List<Purchase> history = purchaseRepos.findHistory(Long.parseLong(userId));
		List<HistoryDto> historyDtoList = new ArrayList<>();
		history.forEach((v) -> {
			HistoryDto dto = new HistoryDto(v);
			historyDtoList.add(dto);
		});
		
		return gson.toJson(historyDtoList);
	}
	
}
