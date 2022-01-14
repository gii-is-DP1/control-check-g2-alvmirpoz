package org.springframework.samples.petclinic.feeding;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.PetType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedingService {

    private FeedingRepository feedingRepository;
    @Autowired
    public FeedingService(FeedingRepository feedingRepository){
        this.feedingRepository = feedingRepository;
    }

    public List<Feeding> getAll(){
        return this.feedingRepository.findAll();
    }

    public List<FeedingType> getAllFeedingTypes(){
        return this.feedingRepository.findAllFeedingTypes();
    }

    public FeedingType getFeedingType(String typeName) {
        return this.feedingRepository.findFeedingTypeByName(typeName);
    }

    @Transactional(rollbackFor = UnfeasibleFeedingException.class)
    public Feeding save(Feeding p) throws UnfeasibleFeedingException {
        PetType petTypePet = p.getPet().getType();
        PetType petTypeFeedingType = p.getFeedingType().getPetType();

        if(petTypePet == petTypeFeedingType) return this.feedingRepository.save(p);
        else throw new UnfeasibleFeedingException();
               
    }
}
