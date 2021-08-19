
package com.example.synergybackend.services;

import com.example.synergybackend.model.DbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Queue;

@Service
public class SequenceService {
    @Autowired
    private MongoOperations mongoOperations;

    public int getSequence(String seqName){
        Query query = new Query(Criteria.where("id").is(seqName));
        Update update = new Update().inc("seq",1);
        DbSequence counter = mongoOperations
                .findAndModify(query,
                        update, FindAndModifyOptions.options().returnNew(true).upsert(true),
                        DbSequence.class);

        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}