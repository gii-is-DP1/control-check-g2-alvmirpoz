package org.springframework.samples.petclinic.feeding;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class FeedingTypeFormatter implements Formatter<FeedingType>{

    @Autowired
    FeedingService feedingService;

    @Override
    public String print(FeedingType object, Locale locale) {
        // TODO Auto-generated method stub
        return object.getName();
    }

    @Override
    public FeedingType parse(String text, Locale locale) throws ParseException {
        // TODO Auto-generated method stub
        FeedingType f = this.feedingService.getFeedingType(text);

        if(f != null) return f;
        else throw new ParseException("FeedingType not found: "+text,0);
    }
    
}
