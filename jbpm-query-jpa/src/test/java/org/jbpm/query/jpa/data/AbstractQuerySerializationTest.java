/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.jbpm.query.jpa.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.jbpm.query.jpa.data.QueryCriteria;
import org.jbpm.query.jpa.data.QueryWhere;
import org.jbpm.query.jpa.data.QueryWhere.ParameterType;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.task.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractQuerySerializationTest {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractQuerySerializationTest.class );
    
    abstract <T> T testRoundTrip(T in) throws Exception;
    abstract void addSerializableClass(Class objClass);
   
    @Before
    public void before() {
        addSerializableClass(Status.class);
    }
    
    @Test
    public void queryParameterTest() throws Exception { 
       QueryCriteria criteria = new QueryCriteria();
       criteria.setListId("one");
       criteria.setUnion(false);
       criteria.setType(ParameterType.RANGE);
//       criteria.addParameter(new Date());
       Thread.sleep(1000);
//       criteria.addParameter(new Date());
       criteria.addParameter(Status.Ready);
       
       QueryCriteria copyCrit = testRoundTrip(criteria);
       
       assertEquals( "union", criteria.isUnion(), copyCrit.isUnion());
       assertEquals( "parameter type", criteria.getType(), copyCrit.getType() );
       assertEquals( "value list size", criteria.getParameters().size(), copyCrit.getParameters().size() );
       for( int i = 0; i < criteria.getParameters().size(); ++i ) { 
           assertEquals( "value " + i, criteria.getParameters().get(i), copyCrit.getParameters().get(i) );
       }
      
       criteria.getParameters().clear();
       criteria.getParameters().add(new Integer(23));
       criteria.getParameters().add(new Integer(42));
       criteria.getParameters().add(new Long(55555));
       criteria.getParameters().add("ceci ce ne pas un string!");
       
       copyCrit = testRoundTrip(criteria);
       
       for( int i = 0; i < criteria.getParameters().size(); ++i ) { 
           assertEquals( "value " + i, criteria.getParameters().get(i), copyCrit.getParameters().get(i) );
       }
       
       QueryWhere queryWhere = new QueryWhere();
       queryWhere.addAppropriateParam("test", "asdf");
       
       QueryWhere copyWhere = testRoundTrip(queryWhere);
       
       assertEquals( "union", queryWhere.isUnion(), copyWhere.isUnion());
       for( int i = 0; i < queryWhere.getCriteria().size(); ++i ) {
           QueryCriteria origCrit = queryWhere.getCriteria().get(i);
           copyCrit = queryWhere.getCriteria().get(i);
           for( int j = 0; j < origCrit.getParameters().size(); ++j ) { 
               assertEquals( "value " + i, origCrit.getParameters().get(i), copyCrit.getParameters().get(i) );
           }
       };
    }
    
    @Test
    public void nestedQueryParameterTest() throws Exception { 
       QueryCriteria criteria = new QueryCriteria();
       criteria.setListId("one");
       criteria.setUnion(false);
       criteria.setType(ParameterType.RANGE);
//       criteria.addParameter(new Date());
       
       QueryCriteria subCrit = new QueryCriteria();
       subCrit.addParameter(Status.Ready);
       subCrit.setUnion(true);
       subCrit.setListId("sub one");
       criteria.addParameter(subCrit);
      
       subCrit = new QueryCriteria();
       subCrit.addParameter(Status.Completed);
       subCrit.setUnion(true);
       subCrit.setListId("sub two");
       criteria.addParameter(subCrit);
       
       QueryCriteria copyCrit = testRoundTrip(criteria);
    } 
}
