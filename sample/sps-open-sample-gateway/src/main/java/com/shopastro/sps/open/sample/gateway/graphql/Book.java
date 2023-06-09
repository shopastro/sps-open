package com.shopastro.sps.open.sample.gateway.graphql;

import lombok.Data;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Data
public class Book {
     String id;
     String title;
     String category;
     String authorId;
    
     String text;
}