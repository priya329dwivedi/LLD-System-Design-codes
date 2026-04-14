
package org.DSA.JunkLLD.SubscriptionModel.Repository;

import org.DSA.JunkLLD.SubscriptionModel.model.Content;

import java.util.HashMap;
import java.util.Map;

public class ContentRepository {
    Map<String, Content> contentRepo = new HashMap<>();

    public void save(Content content){
        contentRepo.put(content.getId(),content);
    }
    public Content getContent(String contentId){
        return contentRepo.get(contentId);
    }
}
