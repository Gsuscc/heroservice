package com.reactheroes.userservice.dao.implementations;

import com.reactheroes.userservice.dao.interfaces.HeroCardDao;
import com.reactheroes.userservice.entity.HeroCard;
import com.reactheroes.userservice.entity.UserDetail;
import com.reactheroes.userservice.repository.HeroCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class HeroCardJpa implements HeroCardDao {

    private final HeroCardRepository heroCardRepository;

    @Autowired
    public HeroCardJpa(HeroCardRepository heroCardRepository) {
        this.heroCardRepository = heroCardRepository;
    }

    @Override
    public void gatherXp(Long amount, Long id) {
        Long currentXp = heroCardRepository.getHeroCardXp(id);
        heroCardRepository.incrementHeroCardXp(currentXp + amount, id);
    }

    @Override
    public void addCard(HeroCard heroCard) {
        heroCardRepository.save(heroCard);
    }

    @Override
    public Page<HeroCard> getHeroCardsPageForUser(UserDetail userDetail, Integer page) {
        return heroCardRepository.findAllByUserDetailIs(userDetail, PageRequest.of(page, 18, Sort.by("xp").descending().and(Sort.by("cardId").ascending())));
    }

    @Override
    public Page<HeroCard> getMergeableCards(int page, UserDetail userDetail, Long cardId, Long id) {
        return heroCardRepository.findAllByUserDetailIsAndCardIdIsAndIdNot(PageRequest.of(page, 18, Sort.by("xp").descending()), userDetail,cardId, id);
    }

    @Override
    public HeroCard getHeroCardByCardId(Long cardId) {
        return heroCardRepository.findById(cardId).get();
    }


}
